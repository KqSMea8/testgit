package com.hanweb.complat.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.permission.PermissionUtil;
import com.hanweb.common.util.CheckPwdStrong;
import com.hanweb.common.util.ExcelUtil;
import com.hanweb.common.util.Md5Util;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.PinyinUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.log.LogWriter;
import com.hanweb.common.util.security.SecurityUtil;
import com.hanweb.complat.constant.Settings;
import com.hanweb.complat.controller.user.UserFormBean;
import com.hanweb.complat.dao.GroupManagerDAO;
import com.hanweb.complat.dao.InterDAO;
import com.hanweb.complat.dao.RoleRelationDAO;
import com.hanweb.complat.dao.UserDAO;
import com.hanweb.complat.entity.Group;
import com.hanweb.complat.entity.Right;
import com.hanweb.complat.entity.Role;
import com.hanweb.complat.entity.User;
import com.hanweb.complat.exception.LoginException;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.interfacesmanage.entity.Interfaces;
import com.hanweb.jmp.sys.dao.role.RoleColDAO;
import com.hanweb.jmp.sys.entity.role.RoleCol;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.support.controller.CurrentUser;

/**
 * 用户Service
 * 
 * @author ZhangC
 * 
 */
public class UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private RoleService roleService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private RoleRelationDAO roleRelationDAO;

	@Autowired
	private GroupManagerService groupManagerService;

	@Autowired
	private GroupManagerDAO groupManagerDAO;

	@Autowired
	private RoleColDAO roleColDAO;

	@Autowired
	private RightService rightService;

	@Autowired
	private InterDAO interfaceDao;
	
	@Autowired
	private SiteService siteService;
	
	/**
	 * 用户登录
	 * 
	 * @param loginName
	 * 
	 * @param pwd
	 * 
	 * @param ip
	 * 
	 * @throws ComplatException
	 * @return CurrentUser
	 */
	public synchronized CurrentUser checkUserLogin(String loginName,
			String pwd, String ip) throws LoginException {
		// 通过登录名获取一个用户的密码、主键id、是否可用
		User user = userDAO.findByLoginName(loginName);
		CurrentUser currentUser = null;
		// 判断是否有此用户
		if (user != null) {
			if (user.getEnable() == 0) {
				throw new LoginException("login.isnotallowed");
			}

			String storePwd = userDAO.findPassword(loginName);

			if (Md5Util.isValidatePwd(pwd, storePwd)) {
				currentUser = wrapLoginUser(user, pwd, ip);
			}
		}
		return currentUser;
	}

	/**
	 * 点击维护时，验证
	 * 
	 * @param loginName
	 * @param pwd
	 * @param ip
	 * @return
	 * @throws LoginException
	 */
	public synchronized CurrentUser checkSiteUserLogin(String loginName,
			String pwd, String ip) throws LoginException {
		// 通过登录名获取一个用户的密码、主键id、是否可用
		User user = userDAO.findByLoginName(loginName);
		CurrentUser currentUser = null;
		// 判断是否有此用户
		if (user != null) {
			if (user.getEnable() == 0) {
				throw new LoginException("login.isnotallowed");
			}
			String storePwd = userDAO.findPassword(loginName);
			if (StringUtil.equals(pwd, storePwd)) {
				currentUser = wrapLoginUser(user, pwd, ip);
			}
		}
		return currentUser;
	}

	/**
	 * 包装登陆的user
	 * 
	 * @param user
	 * @param pwd
	 * @param ip
	 * @return
	 */
	private CurrentUser wrapLoginUser(User user, String pwd, String ip) {
		int iid = user.getIid();
		CurrentUser currentUser = new CurrentUser();
		currentUser.setAddress(user.getAddress());
		currentUser.setAge(user.getAge());
		currentUser.setEmail(user.getEmail());
		currentUser.setEnable(user.getEnable());
		currentUser.setGroupId(user.getGroupId());
		currentUser.setHeadship(user.getHeadship());
		currentUser.setIid(user.getIid());
		currentUser.setLoginName(user.getLoginName());
		currentUser.setMobile(user.getMobile());
		currentUser.setName(user.getName());
		currentUser.setPhone(user.getPhone());
		currentUser.setPwd(user.getPwd());
		currentUser.setUuid(user.getUuid());
		currentUser.setSiteId(user.getSiteId());
		if (StringUtil.isEmpty(pwd)) {
			currentUser.setPwdLevel(3);
		} else {
			currentUser.setPwdLevel(CheckPwdStrong.check(pwd));
		}
		currentUser.setSex(user.getSex());
		currentUser.setUuid(user.getUuid());
		currentUser.setContact(user.getContact());
		currentUser.setIp(ip);
		currentUser.setAccessTime(new Date());

		if (roleService.isSysAdminUser(iid)) {
			currentUser.setSysAdmin(true);
		} else if (roleService.isWebSiteAdminUser(iid)) {
			currentUser.setGroupAdmin(true);
			currentUser.setIsWebSiteAdmin(true);
		}

		Integer rangeId = null;
		String rangeName = null;
		if (roleService.isSysAdminUser(iid)) {
			rangeId = 0;
			currentUser.setSysAdmin(true);
		} else if (roleService.isGroupAdminUser(iid)) {
			rangeId = groupManagerService.findRangeIdByUserId(iid);
			if (rangeId == null) { // 机构管理员 没设置管理范围时， 缺省取其所在机构
				rangeId = user.getGroupId();
			}
			rangeName = groupService.findNameByIid(rangeId);
			currentUser.setGroupAdmin(true);
		}
		currentUser.setRangeId(rangeId);
		currentUser.setRangeName(rangeName);

		List<Role> roleList = new ArrayList<Role>();// 取得用户角色
		roleList = roleService.findUserRoles(iid);
		currentUser.setRoleList(roleList);

		List<Right> rightList = new ArrayList<Right>(); // 取得菜单功能模块权限
		rightList = rightService.findUserRights(iid);
		currentUser.setRightList(rightList);

		PermissionUtil.organizePermissionKey(currentUser);

		Set<String> colids = new HashSet<String>();
		List<RoleCol> colList = roleColDAO.findByUserId(currentUser.getIid(),
				currentUser.getGroupId());
		if (CollectionUtils.isNotEmpty(colList)) {
			for (RoleCol col : colList) {
				if (col == null) {
					continue;
				}
				colids.add(StringUtil.getString(col.getColId()));
				if (NumberUtil.getInt(col.getIsedit()) == 1) {
					colids.add("edit-" + StringUtil.getString(col.getColId()));
				}

				if (NumberUtil.getInt(col.getIsaudit()) == 1) {
					colids.add("audit-" + StringUtil.getString(col.getColId()));
				}

				if (NumberUtil.getInt(col.getIsColManage()) == 1) {
					colids.add("manage-" + StringUtil.getString(col.getColId()));
				}
			}
		}
		currentUser.setColids(colids);

		Set<String> permissions = new HashSet<String>();
		// 模块权限
		rightList = currentUser.getRightList();
		if (CollectionUtils.isNotEmpty(rightList)) {
			for (Right right : rightList) {
				if (StringUtil.isNotEmpty(right.getUrl())) {
					permissions.add(right.getUrl());
				}
			}
		}
		return currentUser;
	}
	
	public String getUserNameById(String ids){
		List<User> users = findByIds(ids);
		StringBuffer userNames = new StringBuffer();
		if(users == null || users.size() <= 0){
			return "";
		}else{
			for(User u : users){
				userNames.append(u.getName() + ",");
			}
			userNames.deleteCharAt(userNames.length()-1);
			return userNames.toString();
		}
	}
	
	public List<User> findByIds(String ids) {
		if (StringUtil.isEmpty(ids))
			return null;
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		List<User> userList = userDAO.findByIds(idsList);
		return userList;
	}

	/**
	 * 为票据方式的单点登录而生
	 * 
	 * @param loginName
	 * @param pwd
	 * @param ip
	 * @return
	 * @throws LoginException
	 */
	public synchronized CurrentUser checkUserLogin(String loginName, String ip)
			throws LoginException {
		// 通过登录名获取一个用户的密码、主键id、是否可用
		User user = userDAO.findByLoginName(loginName);
		CurrentUser currentUser = null;
		// 判断是否有此用户
		if (user != null) {
			if (user.getEnable() == 0) {
				throw new LoginException("login.isnotallowed");
			}
			currentUser = wrapLoginUser(user, null, ip);
		}

		return currentUser;
	}

	/**
	 * 修改用户密码
	 * 
	 * @param user
	 *            user 必须包含iid 和 pwd
	 * @return
	 */
	public boolean modifyPassword(Integer userId, String password) {
		boolean success = false;
		if (userId != null && StringUtil.isNotEmpty(password)) {
			success = userDAO.updatePassword(userId,
					Md5Util.encodePwd(password));
		}
		return success;
	}

	/**
	 * 强制修改用户名、密码
	 * 
	 * @param userId
	 *            用户id
	 * @param loginId
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public boolean modifyLoginIdAndPassword(Integer userId, String loginId,
			String password) {
		boolean success = false;
		if (userId != null && StringUtil.isNotEmpty(loginId)
				&& StringUtil.isNotEmpty(password)) {
			success = userDAO.updateLoginIdAndPassword(userId, loginId,
					Md5Util.encodePwd(password));
		}
		return success;
	}

	/**
	 * 更新用户基本信息
	 * 
	 * @param user
	 * @return
	 */
	public boolean modifySelf(User user) {
		boolean success = false;
		try {
			user.setPinYin(PinyinUtil.getHeadByString(user.getName()));

			success = userDAO.update(user);
			if (success && StringUtil.isNotEmpty(user.getPwd())) {
				success = modifyPassword(user.getIid(), user.getPwd());
			}
		} catch (Exception e) {
			LogWriter.error("modifySelf error", e);
		}
		return success;
	}

	/**
	 * 通过用户ID获得用户实体
	 * 
	 * @param iid
	 *            用户ID
	 * @return
	 */
	public User findByIid(int iid) {
		if (iid == 0)
			return null;
		User user = userDAO.findByIid(iid);
		if (user == null) {
			return null;
		}
		// user.setPwd(Md5Util.md5decode(user.getPwd()));
		return user;
	}

	/**
	 * 通过登录名获得用户实体
	 * 
	 * @param loginName
	 *            用户登录名
	 * @return
	 */
	public User findByLoginName(String loginName) {
		if (StringUtil.isEmpty(loginName))
			return null;
		User user = userDAO.findByLoginName(loginName);
		if (user == null) {
			return null;
		}
		// user.setPwd(Md5Util.md5decode(user.getPwd()));
		return user;
	}

	/**
	 * 获得用户所在机构ID
	 * 
	 * @param iid
	 *            用户ID
	 * @return
	 */
	public Integer findGroupIdByIid(int iid) {
		if (iid == 0)
			return null;

		Integer groupId = userDAO.findGroupIdByIid(iid);

		return groupId;
	}

	/**
	 * 获得指定机构ID串下的所有用户集合
	 * 
	 * @param groupIds
	 *            机构ID串 如:1,2,3
	 * @return
	 */
	public List<User> findByGroupIds(String groupIds) {
		if (StringUtil.isEmpty(groupIds))
			return null;
		List<Integer> groupidList = StringUtil.toIntegerList(groupIds, ",");

		List<User> userList = userDAO.findByGroupIds(groupidList);
		return userList;
	}

	/**
	 * 新增用户
	 * 
	 * @param user
	 *            用户实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean add(UserFormBean userFormBean) throws OperationException {
		if (userFormBean == null) {
			return false;
		}
		User user = userFormBean.getUser();
		boolean isSuccess = false;
		Integer rangeId = userFormBean.getRangeId();
		List<Integer> roleIdList = StringUtil.toIntegerList(
				userFormBean.getRoleIds(), ",");

		int num = userDAO.findIidByLoginName(null, user.getLoginName());
		if (num > 0) {
			throw new OperationException("登录名已存在,请重新设置！");
		}
		user.setPwd(Md5Util.encodePwd(user.getPwd()));
		user.setCreatetime(new Date());
		user.setPinYin(PinyinUtil.getHeadByString(user.getName()));
		user.setUuid(StringUtil.getUUIDString());
		user.setDynamicCodeKey(SecurityUtil.createAuthKey());
		// 设置新增用户所属网站
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		user.setSiteId(currentUser.getSiteId());
		int iid = userDAO.insert(user);

		// 设置用户管理范围
		if (iid > 0 && NumberUtil.getInt(rangeId) > 0) {
			isSuccess = groupManagerService.add(iid, rangeId);
			if (!isSuccess) {
				throw new OperationException("新增用户机构关系失败！");
			}
		}

		// 新增用户对应的角色
		if (iid > 0 && CollectionUtils.isNotEmpty(roleIdList)) {
			isSuccess = roleService.modifyUserMembers(roleIdList,
					user.getGroupId(), iid);
			if (!isSuccess) {
				throw new OperationException("新增用户角色关系失败！");
			}
		}

		return iid > 0 ? true : false;
	}

	/**
	 * 更新用户
	 * 
	 * @param user
	 *            用户实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean modify(UserFormBean userFormBean) throws OperationException {
		if (userFormBean == null) {
			return false;
		}
		User user = userFormBean.getUser();
		Integer iid = user.getIid();
		Integer rangeId = userFormBean.getRangeId();

		List<Integer> userIdList = new ArrayList<Integer>();
		List<Integer> roleIdList = StringUtil.toIntegerList(
				userFormBean.getRoleIds(), ",");

		userIdList.add(iid);

		user.setPinYin(PinyinUtil.getHeadByString(user.getName()));

		boolean isSuccess = userDAO.update(user);

		if (isSuccess && StringUtils.isNotBlank(user.getPwd())) {
			user.setPwd(Md5Util.encodePwd(user.getPwd()));
			isSuccess = userDAO.updatePassword(iid, user.getPwd());
		}

		isSuccess = groupManagerDAO.deleteByUserIds(userIdList);
		if (!isSuccess) {
			throw new OperationException("删除用户机构关系失败！");
		}
		// 设置用户管理范围
		if (NumberUtil.getInt(rangeId) > 0) {
			isSuccess = groupManagerService.add(iid, rangeId);
			if (!isSuccess) {
				throw new OperationException("更新用户机构关系失败！");
			}
		}

		isSuccess = roleRelationDAO.deleteUsers(null, userIdList);
		if (!isSuccess) {
			throw new OperationException("删除用户角色关系失败！");
		}

		// 新增用户对应的角色
		if (CollectionUtils.isNotEmpty(roleIdList)) {

			isSuccess = roleService.modifyUserMembers(roleIdList,
					user.getGroupId(), iid);
			if (!isSuccess) {
				throw new OperationException("新增用户角色关系失败！");
			}
		}
		return isSuccess;
	}

	/**
	 * 更新用户基本信息，不涉及角色权限修改<br/>
	 * 主要供接口使用
	 * 
	 * @param user
	 *            用户实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean modifyUserInfo(User user) {
		if (user == null || user.getIid() <= 0) {
			return false;
		}
		Integer iid = user.getIid();
		user.setPinYin(PinyinUtil.getHeadByString(user.getName()));

		boolean isSuccess = userDAO.update(user);
		if (isSuccess && StringUtils.isNotBlank(user.getPwd())) {
			user.setPwd(Md5Util.encodePwd(user.getPwd()));
			isSuccess = userDAO.updatePassword(iid, user.getPwd());
		}
		return isSuccess;
	}

	/**
	 * 删除用户
	 * 
	 * @param ids
	 *            用户ID串 如:1,2,3
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean removeByIds(String ids) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		boolean isSuccess = userDAO.deleteByIds(idList);
		if (!isSuccess) {
			throw new OperationException("删除用户失败！");
		}

		isSuccess = groupManagerDAO.deleteByUserIds(idList);
		if (!isSuccess) {
			throw new OperationException("删除用户机构关系失败！");
		}

		isSuccess = roleRelationDAO.deleteUsers(null, idList);
		if (!isSuccess) {
			throw new OperationException("删除用户角色关系失败！");
		}

		return isSuccess;
	}

	/**
	 * 更新用户的有效性
	 * 
	 * @param iid
	 *            用户ID
	 * @param enable
	 *            是否有效<br/>
	 *            1 - 有效<br/>
	 *            0 - 无效
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean modifyEnable(int iid, int enable) {
		if (iid == 0) {
			return false;
		}
		return userDAO.updateEnable(iid, enable);
	}

	/**
	 * 以Excel形式导出用户信息
	 * 
	 * @param ids
	 *            用户ID串 如:1,2,3<br/>
	 *            ids不为空时导出所选用户<br/>
	 *            ids为空时导出groupId及其子孙机构下的所有用户
	 * @param groupId
	 *            机构ID
	 * @return 导出文件的绝对路径
	 */
	public String exportUser(String ids, Integer groupId) {

		List<List<String>> rows = new ArrayList<List<String>>();
		List<User> userList = null;/* 存放用户 */
		List<String> headList = new ArrayList<String>();/* 表头 */
		List<String> valueList = null; /* 用户数据列 */
		String filePath = "";

		headList.add("姓名");
		headList.add("登录名");
		headList.add("所属机构");
		headList.add("机构标识");
		headList.add("上级机构");
		headList.add("职务");
		headList.add("固定电话");
		headList.add("移动电话");
		headList.add("电子邮箱");
		headList.add("联系地址");
		headList.add("联系方式");
		headList.add("唯一标识");
		rows.add(headList);

		if (StringUtil.isNotEmpty(ids)) {// 导出已选用户
			List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
			userList = userDAO.findByIds(idsList);
		} else {
			userList = this.findAllByGroupId(groupId); /* 获得当前机构下的所有子孙用户 */
		}

		Map<Integer, Group> tempGroupMap = new HashMap<Integer, Group>();

		for (User user : userList) { /* 写入用户信息 */
			String groupName = null;
			String groupCode = null;
			String parentGroupName = null;
			if (NumberUtil.getInt(user.getGroupId()) > 0) {
				Group tempGroup = tempGroupMap.get(user.getGroupId());
				if (tempGroup == null) {
					tempGroup = groupService.findByIid(user.getGroupId());
					tempGroupMap.put(tempGroup.getIid(), tempGroup);
				}
				groupName = tempGroup.getName();
				groupCode = tempGroup.getCodeId();
				parentGroupName = tempGroup.getPname();
			}
			valueList = new ArrayList<String>();
			valueList.add(user.getName());
			valueList.add(user.getLoginName());
			valueList.add(groupName);
			valueList.add(groupCode);
			valueList.add(parentGroupName);
			valueList.add(user.getHeadship());
			valueList.add(user.getPhone());
			valueList.add(user.getMobile());
			valueList.add(user.getEmail());
			valueList.add(user.getAddress());
			valueList.add(user.getContact());
			valueList.add(user.getUuid());
			rows.add(valueList);
		}

		try {
			/* 写入文件 */
			String fileName = StringUtil.getUUIDString() + ".xls";
			filePath = Settings.getSettings().getFileTmp() + fileName;
			ExcelUtil.writeExcel(filePath, rows);
			return filePath;
		} catch (Exception e) {
			LogWriter.error("exportUsers Error ", e);
			return "";
		}
	}

	/**
	 * 获得机构及其子孙机构下的所有用户集合
	 * 
	 * @param groupId
	 *            机构ID
	 * @return
	 */
	public List<User> findAllByGroupId(Integer groupId) {
		groupId = NumberUtil.getInt(groupId);

		List<User> allUserList = new ArrayList<User>();
		Map<Integer, List<User>> userMap = new LinkedHashMap<Integer, List<User>>();

		List<Integer> groupIdsList = new ArrayList<Integer>();

		groupIdsList = groupService.findIdsByPId(groupId, groupIdsList); // 获得机构ID列表

		for (Integer groupid : groupIdsList) { // 每个机构建立一个用户集
			userMap.put(groupid, new ArrayList<User>());
		}

		List<User> userList = null;

		if (groupId == 0) { // 导出所有
			List<User> allUsers = userDAO.findAllUsers();
			for (User user : allUsers) { // 将每个用户放入机构对应的用户集中
				userList = userMap.get(user.getGroupId());
				userList.add(user);
				userMap.put(user.getGroupId(), userList);
			}

			Iterator<Integer> iterator = userMap.keySet().iterator();
			while (iterator.hasNext()) {
				userList = userMap.get(iterator.next());
				if (userList != null) {
					allUserList.addAll(userList);
				}
			}
		} else {
			userList = this.findByGroupIds(groupId + ""); // 获得当前机构下所有用户
			if (CollectionUtils.isNotEmpty(userList)) {
				allUserList.addAll(userList);
			}

			List<List<Integer>> splitIdsList = this.splitGroupIds(groupIdsList);
			for (List<Integer> eachIdsList : splitIdsList) {// 获得每组机构下所有的用户
				if (CollectionUtils.isEmpty(eachIdsList)) {
					continue;
				}
				userList = userDAO.findByGroupIds(eachIdsList);
				if (CollectionUtils.isNotEmpty(userList)) {
					allUserList.addAll(userList);
				}
			}
		}
		return allUserList;
	}

	/**
	 * 切割机构ID串. 以20个ID为一组的List<Integer>
	 * 
	 * @param groupIdsList
	 *            机构ID串的集合
	 * @return 机构ID串的集合
	 */
	private List<List<Integer>> splitGroupIds(List<Integer> groupIdsList) {
		List<List<Integer>> subIdsList = new ArrayList<List<Integer>>();
		List<Integer> eachIdsList = new ArrayList<Integer>(); // 每组ID的集合

		int num = 20;

		int groupSize = groupIdsList.size() / num;
		int elseSize = groupIdsList.size() % num;

		for (int i = 0; groupSize != 0 && i < groupSize; i++) {
			eachIdsList = groupIdsList.subList(i * num, (i + 1) * num);
			subIdsList.add(eachIdsList);
		}
		if (elseSize != 0) { /* 剩余的为一组 */
			eachIdsList = groupIdsList.subList(groupSize * 10,
					groupIdsList.size());
			subIdsList.add(eachIdsList);
		}
		return subIdsList;
	}

	/**
	 * 从Excel导入用户信息
	 * 
	 * @param filePath
	 *            导入Excel表单的绝对路径
	 * @return 提示信息
	 * @throws OperationException
	 *             错误信息
	 */
	public String importUser(File file) throws OperationException {
		if (file == null) {
			throw new OperationException("找不到导入文件！");
		}
		List<Map<String, String>> rows = ExcelUtil.readExcel(file);
		if (CollectionUtils.isEmpty(rows)) {
			throw new OperationException(
					SpringUtil.getMessage("import.filetype.error"));
		}

		List<Map<String, String>> userList = this.getUserListByRows(rows); /* 获得用户集合 */
		String retMessage = "";

		try {
			retMessage = this.importUsers(userList);/* 循环插入用户信息 */

			if (StringUtil.isNotEmpty(retMessage)) {
				retMessage = "<div style=\"height:100%;overflow:auto\">导入完毕，存在以下问题：<br/>"
						+ retMessage + "</div>";
			}
			return retMessage;
		} catch (Exception e) {
			LogWriter.error("import users error", e);
			return "导入失败";
		} finally {
			try {
				if (file.exists())
					file.delete();
			} catch (Exception e) {
				LogWriter.error("delete file error", e);
			}
		}
	}

	/**
	 * 获得用户实体List
	 * 
	 * @param rows
	 *            用户集合 每个Map为一个用户实体<br/>
	 *            key为表单头 如:"姓名"<br/>
	 *            vaule为表单值 如:"张三"
	 * @return
	 */
	private List<Map<String, String>> getUserListByRows(
			List<Map<String, String>> rows) {
		List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
		Map<String, String> cell = null;

		/* excel记录转换成用户集合 */
		Iterator<Map<String, String>> iterator = rows.iterator();
		while (iterator.hasNext()) {
			cell = iterator.next();

			Map<String, String> userMap = new HashMap<String, String>();
			userMap.put("name", cell.get("姓名"));
			userMap.put("loginName", cell.get("登录名"));
			userMap.put("pwd", cell.get("密码"));
			userMap.put("groupName", cell.get("所属机构"));
			userMap.put("groupCode", cell.get("机构标识"));
			userMap.put("parentGroupName", cell.get("上级机构"));
			userMap.put("headship", cell.get("职务"));
			userMap.put("phone", cell.get("固定电话"));
			userMap.put("mobile", cell.get("移动电话"));
			userMap.put("email", cell.get("电子邮箱"));
			userMap.put("address", cell.get("联系地址"));
			userMap.put("contact", cell.get("联系方式"));
			userMap.put("uuid", cell.get("唯一标识"));
			userList.add(userMap);
		}
		return userList;
	}

	/**
	 * 导入用户
	 * 
	 * @param userList
	 *            用户实体集合
	 * @return 界面提示信息
	 */
	public String importUsers(List<Map<String, String>> userList) {
		if (userList == null) {
			return "";
		}
		Map<String, String> existMap = new HashMap<String, String>();
		StringBuilder result = new StringBuilder();
		Map<String, String> userMap = null;

		Integer groupId = 0;
		String name = "";
		String loginName = "";
		String groupName = "";
		String pwd = "";
		String groupCode = "";
		String parentGroupName = "";
		String message = "";
		boolean isSccuess = false;
		String exist = ""; /* 记录用户是否被导入过 */

		for (int i = 0; i < userList.size(); i++) {
			userMap = userList.get(i);
			if (userMap == null) {
				continue;
			}
			name = userMap.get("name");
			loginName = userMap.get("loginName");
			groupName = userMap.get("groupName");
			groupCode = userMap.get("groupCode");
			pwd = userMap.get("pwd");
			parentGroupName = userMap.get("parentGroupName");

			if (userMap == null || StringUtil.isEmpty(name)
					|| StringUtil.isEmpty(loginName)) {
				message = "该行姓名或登录名为空！";
				this.getReturnMessage(result, i, message);
				continue;
			}
			/* 检查导入信息是否符合要求 */
			message = this.checkImportUser(name, loginName, groupName,
					groupCode);
			if (message != null && message.length() > 0) {
				this.getReturnMessage(result, i, message);
				continue;
			}

			try {
				groupId = this.findValidGroupId(groupCode, groupName,
						parentGroupName);/* 取得对应的机构ID */
			} catch (Exception e) {
				this.getReturnMessage(result, i, e.getMessage());
				continue;
			}

			exist = existMap.get(loginName);
			if (exist != null && exist.equals("loginName")) {
				message = "登录名'" + loginName + "'重复";
				this.getReturnMessage(result, i, message);
				continue;
			}
			User tempUser = this.findByLoginName(loginName);
			if (tempUser == null) {

				// 新增用户初始角色，包括默认角色和机构拥有的角色
				List<Role> groupRoleList = roleService.findGroupRoles(groupId);
				List<Role> defaultRoleList = roleService.findDefaultRoles();

				LinkedHashSet<Integer> selectedRole = new LinkedHashSet<Integer>();
				for (Role role : groupRoleList) {
					selectedRole.add(role.getIid());
				}
				for (Role role : defaultRoleList) {
					selectedRole.add(role.getIid());
				}

				String roleIds = StringUtil.join(selectedRole, ",");

				User user = new User();
				user.setName(name);
				user.setLoginName(loginName);
				user.setGroupId(groupId);
				user.setHeadship(userMap.get("headship"));
				user.setPhone(userMap.get("phone"));
				user.setMobile(userMap.get("mobile"));
				user.setEmail(userMap.get("email"));
				user.setAddress(userMap.get("address"));
				user.setContact(userMap.get("contact"));
				user.setUuid(userMap.get("uuid"));
				user.setEnable(1);
				if (StringUtil.isEmpty(pwd)) {
					// 导入用户的默认密码设为888888
					pwd = "888888";
				}
				user.setPwd(pwd);
				UserFormBean userFormBean = new UserFormBean();
				userFormBean.setUser(user);
				userFormBean.setRoleIds(roleIds);
				try {
					isSccuess = this.add(userFormBean);
				} catch (OperationException e) {
					this.getReturnMessage(result, i, e.getMessage());
				}
			} else {
				message = "登录名为 '" + loginName + "' 的用户已存在";
				this.getReturnMessage(result, i, message);
				continue;
				// user.setIid(tempUser.getIid());
				// isSccuess = this.modifyUserInfo(user);
			}
			existMap.put(loginName, "loginName");

			if (!isSccuess) {
				message = "导入用户'" + loginName + "'出现异常";
				this.getReturnMessage(result, i, message);
				continue;
			}
		}
		String retString = null;
		if (StringUtil.isNotEmpty(result.toString())) {
			retString = "<ul>" + result.toString() + "</ul>";
		}
		return retString;
	}

	/**
	 * 检查导入的用户是否符合标准
	 * 
	 * @param name
	 * @param loginName
	 * @param groupName
	 * @param groupCode
	 * @return
	 */
	private String checkImportUser(String name, String loginName,
			String groupName, String groupCode) {
		String message = "";

		/* 检查 是否符合要求 */
		if (StringUtil.isEmpty(name)) {
			message += ":姓名不能为空";
		} else if (name.length() > 80) {
			message += ":姓名过长";
		}
		if (StringUtil.isEmpty(loginName)) {
			message += ":登录不能为空";
		} else if (loginName.length() > 80) {
			message += ":登录名过长";
		}
		if (StringUtil.isEmpty(groupName) && StringUtil.isEmpty(groupCode)) {
			message += ":机构名称、机构标识不能同时为空";
		} else {
			if (groupCode.length() > 16) {
				message += ":机构标识不能超过16位";
			}
			if (name.length() > 80) {
				message += ":机构名称超长";
			}
		}
		return message;
	}

	/**
	 * 组织导入的错误提示
	 * 
	 * @param result
	 *            错误提示信息集
	 * @param i
	 *            信息在List中的序号
	 * @param message
	 *            错误信息
	 */
	private void getReturnMessage(StringBuilder result, int i, String message) {
		result.append("<li>");
		result.append("[" + SpringUtil.getMessage("import.error", i + 2) + "] "
				+ message);
		result.append("</li>");
	}

	/**
	 * 获得导入用户的有效机构ID
	 * 
	 * @param user
	 *            用户实体
	 * @return
	 * @throws OperationException
	 *             错误信息
	 */
	private Integer findValidGroupId(String groupCode, String groupName,
			String parentGroupName) throws OperationException {
		Group group = new Group();
		List<Group> groupList = null;
		String message = "";
		Integer groupId; // 待返回的用户机构ID

		if (!"".equals(groupCode)) { /* 如果存在机构标识 */
			group = groupService.findByCodeId(groupCode);
			if (group != null) {
				groupId = group.getIid();
				return groupId;
			}
		}
		if (!"".equals(groupName) && !"".equals(parentGroupName)) {/* 所属机构不为空且父机构不为空 */
			groupList = groupService.findByNameAndPName(groupName,
					parentGroupName);
			if (groupList != null && groupList.size() == 1) {
				group = groupList.get(0);
				groupId = group.getIid();
				return groupId;
			}
		}
		if (!"".equals(groupName)) {/* 所属机构不为空 */
			groupList = groupService.findByName(groupName);
			if (CollectionUtils.isEmpty(groupList)) {
				throw new OperationException("无法找到所属机构！");
			} else if (groupList.size() == 1) {
				group = groupList.get(0);
				groupId = group.getIid();
				return groupId;
			} else {
				throw new OperationException("找到多个同名机构'" + groupName
						+ "'， 无法准确定位");
			}
		}

		message = "无法找到所属机构";
		throw new OperationException(message);
	}

	/**
	 * 按姓名、登录名或姓名首字母缩写查询
	 * 
	 * @param keyword
	 *            关键字
	 * @return
	 */
	public List<User> findByNameOrPinYin(String keyword) {
		List<User> userList = userDAO.findByNameOrPinYin(keyword);
		return userList;
	}

	/**
	 * 获得角色相关的用户列表
	 * 
	 * @param roleId
	 *            角色ID
	 * @return
	 */
	public List<User> findUsersByRoleId(Integer roleId) {
		if (NumberUtil.getInt(roleId) == 0) {
			return null;
		}

		List<User> userList = userDAO.findUsersByRoleId(roleId);
		return userList;
	}

	public void addCommonReigon(String loginName, String region) {
		User user = findByLoginName(loginName);
		String oldCommonReigon = user.getCommonRegion();
		String newCommonReigon = null;
		if (StringUtil.isNotEmpty(oldCommonReigon)) {
			if (StringUtils.endsWith(oldCommonReigon, "\n")
					|| StringUtils.endsWith(oldCommonReigon, "\r")) {
				newCommonReigon = oldCommonReigon + region;
			} else {
				newCommonReigon = oldCommonReigon + "\r" + region;
			}
		} else {
			newCommonReigon = region;
		}
		user.setCommonRegion(newCommonReigon);
		userDAO.update(user);
	}

	/**
	 * 通过登录名获取用户实体
	 * 
	 * @param loginName
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public User findByLoginName1(String loginName) {
		if (StringUtil.isEmpty(loginName)) {
			return null;
		}
		User user = userDAO.findByLoginName1(loginName);
		if (user == null) {
			return null;
		}
		user.setPwd(Md5Util.md5decode(user.getPwd()));
		return user;
	}

	/**
	 * 新增用户
	 * 
	 * @param user
	 * @return
	 * @throws OperationException
	 */
	public Integer add(User user) throws OperationException {
		if (user == null) {
			return 0;
		}
		user.setPwd(Md5Util.encodePwd(user.getPwd()));
		user.setCreatetime(new Date());
		user.setPinYin(PinyinUtil.getHeadByString(user.getName()));

		int iid = userDAO.insert(user);
		return iid;
	}

	/**
	 * 更新用户
	 * 
	 * @param user
	 * @return
	 * @throws OperationException
	 */
	public boolean modify(User user) throws OperationException {
		if (user == null || user.getIid() <= 0) {
			return false;
		}
		Integer iid = user.getIid();

		List<Integer> userIdList = new ArrayList<Integer>();

		userIdList.add(iid);

		user.setPinYin(PinyinUtil.getHeadByString(user.getName()));

		boolean isSuccess = userDAO.update(user);

		if (isSuccess && StringUtils.isNotBlank(user.getPwd())) {
			user.setPwd(Md5Util.encodePwd(user.getPwd()));
			isSuccess = userDAO.updatePassword(iid, user.getPwd());
		}

		return isSuccess;
	}

	public boolean modifyInterfaceDomain() {
		List<Interfaces> interfaces = interfaceDao.findInterfacesDomain();
		String method = "";
		boolean isSucces=false;
		for (Interfaces inter : interfaces) {
			String domains = inter.getDomain();
			Integer iid =inter.getIid();
			method = domains.substring(34);
			String domain2 = BaseInfo.getDomain();

			String inter1 = domain2 + method;
			isSucces= interfaceDao.updateInerfacesDomain(inter1,iid);
		}
		return isSucces;
	}

	public void modifyJsDomain(String domain) {
		if(StringUtil.isNotEmpty(domain)){
			domain = StringUtil.convertPath(domain.trim().toLowerCase(), false);
			if(domain.startsWith("http") && !domain.endsWith("/")){
				boolean isSuccess = false;
				
				if(StringUtil.isNotEmpty(domain)){
					String oldstr = "http://jmpotal.hanweb.com/jmp";
					
					String jsUrl = BaseInfo.getRealPath()+"/resources/lightapps/apps/accumulation_money/static/js/app.475fd75b12e7f508cb66.js";
                    isSuccess = siteService.rewriteJs(jsUrl, domain, oldstr);
					
					if(!isSuccess){
						LogWriter.error(BaseInfo.getDomain() + "/resources/lightapps/apps/accumulation_money/static/js/app.475fd75b12e7f508cb66.js, 公积金域名修改失败！");
					}
					
					jsUrl = BaseInfo.getRealPath()+"/resources/lightapps/apps/income_tax/static/js/app.6ed174dcfa119a13bfec.js";
                    isSuccess = siteService.rewriteJs(jsUrl, domain, oldstr);
					if(!isSuccess){
						LogWriter.error(BaseInfo.getDomain() + "/resources/lightapps/apps/income_tax/static/js/app.6ed174dcfa119a13bfec.js, 个人所得税域名修改失败！");
					}
					
					jsUrl = BaseInfo.getRealPath()+"/resources/lightapps/apps/social_security/static/js/app.b6ac7cf3389472e5fd38.js";
                    isSuccess = siteService.rewriteJs(jsUrl, domain, oldstr);
					if(!isSuccess){
						LogWriter.error(BaseInfo.getDomain() + "/resources/lightapps/apps/social_security/static/js/app.b6ac7cf3389472e5fd38.js, 社保域名修改失败！");
					}
					
					jsUrl = BaseInfo.getRealPath()+"/resources/lightapps/apps/mailbook/static/js/app.cc1fca96acea068aee46.js";
                    isSuccess = siteService.rewriteJs(jsUrl, domain, oldstr);
					if(!isSuccess){
						LogWriter.error(BaseInfo.getDomain() + "/resources/lightapps/apps/mailbook/static/js/app.cc1fca96acea068aee46.js, 通讯录域名修改失败！");
					}
					
					jsUrl = BaseInfo.getRealPath()+"/resources/lightapps/apps/rebellion/static/js/app.708238fe31165333af1f.js";
                    isSuccess = siteService.rewriteJs(jsUrl, domain, oldstr);
					if(!isSuccess){
						LogWriter.error(BaseInfo.getDomain() + "/resources/lightapps/apps/rebellion/static/js/app.708238fe31165333af1f.js, 报料域名修改失败！");
					}
					
					jsUrl = BaseInfo.getRealPath()+"/resources/lightapps/apps/weather/static/js/app.59ae9dca37cc866f67b8.js";
                    isSuccess = siteService.rewriteJs(jsUrl, domain, oldstr);
					if(!isSuccess){
						LogWriter.error(BaseInfo.getDomain() + "/resources/lightapps/apps/weather/static/js/app.59ae9dca37cc866f67b8.js, 空气质量域名修改失败！");
					}
					
				}
			}
		}
		}

/**
	 * 当前站点下的用户
	 * @param siteid
	 * @return
	 */
	public List<User> findAllUser(Integer siteid){
		return userDAO.findBySiteId(siteid);
	}
	
	/**
	 * 部门下用户
	 * @param siteid
	 * @return
	 */
	public List<User> findByGroupid(Integer groupid){
		return userDAO.findByGroupid(groupid);
	}

}
