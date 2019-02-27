package com.hanweb.complat.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.ExcelUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.PinyinUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.constant.Settings;
import com.hanweb.complat.controller.group.GroupFormBean;
import com.hanweb.complat.dao.GroupDAO;
import com.hanweb.complat.dao.GroupManagerDAO;
import com.hanweb.complat.dao.RoleRelationDAO;
import com.hanweb.complat.entity.Group;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.support.controller.CurrentUser;

/**
 * 机构Service
 * 
 */
public class GroupService {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private GroupDAO groupDAO;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleRelationDAO roleRelationDAO;

	@Autowired
	private GroupManagerDAO groupManagerDAO;

	/**
	 * 通过机构ID获取机构实体
	 * 
	 * @param iid
	 *            机构ID
	 * @return 机构实体
	 */
	public Group findByIid(Integer iid) {
		Group group = groupDAO.findByIid(iid);
		return group;
	}

	/**
	 * 通过机构ID串获取机构
	 * 
	 * @param ids
	 *            机构ID串 如:1,2,3
	 * @return 机构实体集合
	 */
	public List<Group> findByIds(String ids) {
		List<Integer> idsLsit = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idsLsit)) {
			return null;
		}

		List<Group> groupList = groupDAO.findByIds(idsLsit);
		return groupList;
	}

	/**
	 * 通过机构名称获取机构
	 * 
	 * @param name
	 *            机构名称
	 * @return 机构实体列表
	 */
	public List<Group> findByName(String name) {
		if (name == null || name.length() == 0) {
			return null;
		}
		List<Group> groupList = groupDAO.findByName(name);
		return groupList;
	}

	/**
	 * 通过机构标识取得机构
	 * 
	 * @param codeId
	 *            机构标识
	 * @return 机构实体
	 */
	public Group findByCodeId(String codeId) {
		if (StringUtil.isEmpty(codeId)) {
			return null;
		}
		Group group = groupDAO.findByCodeId(codeId);
		return group;
	}

	/**
	 * 通过机构标识获取机构ID
	 * 
	 * @param codeId
	 *            机构标识
	 * @return 机构ID
	 */
	public int findIdByCodeId(String codeId) {
		if (StringUtil.isEmpty(codeId)) {
			return 0;
		}
		int iid = groupDAO.findIdByCodeId(codeId);

		return iid;
	}

	/**
	 * 通过机构ID，获得机构名称
	 * 
	 * @param id
	 *            机构ID
	 * @return 机构名称
	 */
	public String findNameByIid(Integer iid) {
		if (NumberUtil.getInt(iid) == 0) {
			return "";
		}
		String groupName = groupDAO.findNameByIid(iid);
		groupName = groupName == null ? "" : groupName;
		return groupName;
	}

	/**
	 * 通过机构名称及父机构ID获得同名机构名称的个数
	 * 
	 * @param name
	 *            机构名称
	 * @param pid
	 *            父机构ID
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findNumOfSameName(String name, Integer pid) {
		return findNumOfSameName(0, name, pid);
	}

	/**
	 * 通过机构ID、机构名称及父机构ID获得同名机构名称的个数
	 * 
	 * @param id
	 *            机构ID
	 * @param name
	 *            机构名称
	 * @param pid
	 *            父机构ID
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findNumOfSameName(Integer id, String name, Integer pid) {
		if (StringUtil.isEmpty(name)) {
			return 0;
		}
		int num = groupDAO.findNumOfSameName(id, name, pid);
		return num;
	}

	/**
	 * 通过机构名称和父机构ID获取机构
	 * 
	 * @param name
	 *            机构名称
	 * @param pid
	 *            父机构ID
	 * @return 机构实体
	 */
	public Group findByNameAndPId(String name, String pid) {
		if (StringUtil.isEmpty(name)) {
			return null;
		}
		Group group = groupDAO.findByNameAndPId(name, pid);
		return group;
	}

	/**
	 * 通过机构名称及父机构名称 获得 机构实体
	 * 
	 * @param name
	 *            机构名称
	 * @param pName
	 *            父机构名称
	 * @return 机构实体列表
	 */
	public List<Group> findByNameAndPName(String name, String pid) {
		if (StringUtil.isEmpty(name)) {
			return null;
		}
		List<Group> groupList = groupDAO.findByNameAndPName(name, pid);
		return groupList;
	}

	/**
	 * 获得父机构ID
	 * 
	 * @param id
	 * @return
	 */
	public Integer findPidById(int id) {
		if (id == 0)
			return null;

		Integer groupId = groupDAO.findPidById(id);

		return groupId;
	}

	/**
	 * 据机构标识删除机构
	 * 
	 * @param codeId
	 *            机构标识
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean removeByCodeId(String codeId) throws OperationException {
		if (StringUtil.isEmpty(codeId)) {
			return false;
		}

		int iid = this.findIdByCodeId(codeId);

		return this.removeByIds(iid + "");
	}

	/**
	 * 修改机构 并更新机构标识<br/>
	 * (此时系统中机构标识为空 供导入及同步的时使用)
	 * 
	 * @param group
	 *            机构实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws Exception
	 */
	private boolean modifyGroupAndCodeid(Group group) throws OperationException {
		if (group == null || StringUtil.isEmpty(group.getCodeId())) {
			return false;
		}
		int num = this.findNumOfSameName(group.getIid(), group.getName(), group.getPid());
		if (num > 0) {
			throw new OperationException("机构名称已存在,请重新设置！");
		}
		Group groupEn = this.findByCodeId(group.getCodeId());
		if (groupEn != null && !groupEn.getCodeId().equals(group.getCodeId())) {
			throw new OperationException("机构标识已存在,请重新设置！");
		}
		boolean isSuccess = groupDAO.update(group);
		if (isSuccess) {
			isSuccess = groupDAO.updateCodeid(group.getIid(), group.getCodeId());
		}

		return isSuccess;
	}

	/**
	 * 新增机构
	 * 
	 * @param group
	 *            机构实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean add(GroupFormBean group) throws OperationException {
		if (group == null) {
			return false;
		}
		boolean isSuccess = false;
		String codeId = group.getCodeId();
		List<Integer> roleIdList = StringUtil.toIntegerList(group.getRoleIds(), ",");

		Group tempGroup = this.findByCodeId(codeId);
		if (tempGroup != null) {
			throw new OperationException("机构标识已存在,请重新设置！");
		}
		int num = this.findNumOfSameName(group.getName(), group.getPid());
		if (num > 0) {
			throw new OperationException("机构名称已存在,请重新设置！");
		}
		group.setPinYin(PinyinUtil.getHeadByString(group.getName()));

		int iid = groupDAO.insert(group);

		// 新增用户对应的角色
		if (iid > 0 && CollectionUtils.isNotEmpty(roleIdList)) {
			isSuccess = roleService.modifyGroupMembers(roleIdList, iid);
			if (!isSuccess) {
				throw new OperationException("新增机构角色关系失败！");
			}
		}

		return iid > 0 ? true : false;
	}

	/**
	 * 修改机构
	 * 
	 * @param group
	 *            机构实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean modify(GroupFormBean group) throws OperationException {
		if (group == null || NumberUtil.getInt(group.getIid()) == 0) {
			return false;
		}
		Integer iid = group.getIid();
		boolean isSuccess = false;

		List<Integer> groupIdList = new ArrayList<Integer>();
		List<Integer> roleIdList = StringUtil.toIntegerList(group.getRoleIds(), ",");

		groupIdList.add(iid);

		int num = this.findNumOfSameName(iid, group.getName(), group.getPid());
		if (num > 0) {
			throw new OperationException("机构名称已存在,请重新设置！");
		}
		group.setPinYin(PinyinUtil.getHeadByString(group.getName()));

		isSuccess = groupDAO.update(group);

		if (!isSuccess) {
			throw new OperationException("更新操作失败！");
		}

		isSuccess = roleRelationDAO.deleteGroups(null, groupIdList);
		if (!isSuccess) {
			throw new OperationException("删除用户角色关系失败！");
		}
		// 新增用户对应的角色
		if (isSuccess && CollectionUtils.isNotEmpty(roleIdList)) {
			isSuccess = roleService.modifyGroupMembers(roleIdList, iid);
			if (!isSuccess) {
				throw new OperationException("更新机构角色关系失败！");
			}
		}

		return isSuccess;
	}

	/**
	 * 删除机构
	 * 
	 * @param ids
	 *            机构ID串 如:1,2,3
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean removeByIds(String ids) throws OperationException {
		List<Integer> idsLsit = StringUtil.toIntegerList(ids, ",");

		if (CollectionUtils.isEmpty(idsLsit)) {
			return false;
		}
		boolean isSuccess = false;

		boolean hasSubGroup = this.checkSubGroup(ids);
		if (hasSubGroup) {
			throw new OperationException("所选机构存在下属机构,请先删除下属机构!");
		}
		boolean hasSubUser = this.checkSubUser(ids);
		if (hasSubUser) {
			throw new OperationException("所选机构存在用户,请先删除用户!");
		}

		isSuccess = groupDAO.deleteByIds(idsLsit);/* 删除机构 */
		if (!isSuccess) {
			throw new OperationException("删除机构失败!");
		}
		isSuccess = groupManagerDAO.deleteByGroupIds(idsLsit);/* 删除对应用户机构管理关系 */
		if (!isSuccess) {
			throw new OperationException("删除对应用户机构管理关系失败!");
		}
		isSuccess = roleRelationDAO.deleteByGroupIds(idsLsit);/* 删除对应角色机构关系 */
		if (!isSuccess) {
			throw new OperationException("删除对应角色机构关系失败!");
		}

		return isSuccess;
	}

	/**
	 * 检查是否有下属机构
	 * 
	 * @param ids
	 *            机构ID串 如:1,2,3
	 * @return true - 有下属机构<br/>
	 *         false - 无下属机构
	 */
	public boolean checkSubGroup(String ids) {
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idsList)) {
			return false;
		}
		int num = groupDAO.findCountSubGroup(idsList);

		return num > 0 ? true : false;
	}

	/**
	 * 检查机构中是否有用户存在
	 * 
	 * @param ids
	 *            机构ID串 如:1,2,3
	 * @return true - 有用户<br/>
	 *         false - 无用户
	 */
	public boolean checkSubUser(String ids) {
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idsList)) {
			return false;
		}
		int num = groupDAO.findCountSubUser(idsList);

		return num > 0 ? true : false;
	}

	/**
	 * 修改所属父机构ID
	 * 
	 * @param pid
	 *            父机构ID
	 * @param iid
	 *            机构ID
	 * @return
	 */
	public boolean modifyPidById(Integer pid, int iid) {
		if (iid == 0) {
			return false;
		}
		return groupDAO.updatePidById(pid, iid);
	}

	/**
	 * 检查导入的机构信息是否符合要求
	 * 
	 * @param group
	 *            机构实体
	 * @return
	 */
	private String checkImportGroup(Group group) {
		if (group == null) {
			return "";
		}

		String message = "";
		Integer iid = group.getIid(); // 指定ID
		Integer pId = group.getPid(); // 指定父机构ID
		String name = group.getName();
		String pname = group.getPname();
		String codeId = group.getCodeId(); // 指定机构标识
		String parCodeId = group.getParCodeid();// 指定父机构标识
		int numInLevel = 0;
		/* 检查姓名、机构、登录名、密码 是否符合要求 */

		// 当Excel中的父机构ID及父机构名称不为空 或 父机构ID为空，父机构名称为空，父机构编码也为空时
		if ((NumberUtil.getInt(pId) > 0 && StringUtil.isNotEmpty(pname))
				|| (NumberUtil.getInt(pId) == 0 && StringUtil.isEmpty(pname) && StringUtil
						.isEmpty(codeId))) {
			numInLevel = findNumOfSameGroupByPid(iid, name, pId);
		}

		if (StringUtil.isEmpty(name)) {
			message += ":机构名称不能为空";
		} else if (name.length() > 80) {
			message += ":机构名称超长";
		}
		if (pname.length() > 80) {
			message += ":上级机构名称超长";
		}
		if (codeId.length() > 16) {
			message += ":机构标识不能超过16位";
		}
		if (parCodeId.length() > 16) {
			message += ":上级机构标识不能超过16位";
		}
		if (numInLevel > 0)
			message += ":同一层级机构下'" + name + "' 已有同名";
		return message;
	}

	/**
	 * 已存在 机构标识的个数
	 * 
	 * @param iid
	 *            当前操作的机构标识,新增则为空
	 * @param codeId
	 *            机构编号
	 * @return 已存在机构的个数
	 */
	public int findNumOfSameGroupByCodeid(int iid, String codeId) {
		int nReturn = 0;
		if (codeId == null || codeId.length() == 0) {
			return nReturn;
		}

		nReturn = groupDAO.findNumOfSameGroupByCodeid(iid, codeId);

		return nReturn;
	}

	/**
	 * 获得父机构下指定名称的机构id
	 * 
	 * @param name
	 *            机构名称
	 * @param pid
	 *            父机构ID
	 * @return
	 */
	public Integer findIdOfGroupByPid(String name, Integer pid) {
		if (StringUtil.isEmpty(name)) {
			return 0;
		}

		Integer idInLevel;
		name = StringUtil.getString(name);

		idInLevel = groupDAO.findIdOfGroupByPid(name, pid);

		return idInLevel;
	}

	/**
	 * 获得父机构下同名机构的个数
	 * 
	 * @param iid
	 *            机构ID
	 * @param name
	 *            机构名称
	 * @param pid
	 *            父机构ID
	 * @return 0 - 无同名 <br/>
	 *         n - 有n个同名
	 */
	public int findNumOfSameGroupByPid(Integer iid, String name, Integer pid) {
		if (StringUtil.isEmpty(name)) {
			return 0;
		}
		int num = 0;
		name = StringUtil.getString(name);

		num = groupDAO.findNumOfSameGroupByPid(iid, name, pid);

		return num;
	}

	/**
	 * 找出机构下所有下属机构
	 * 
	 * @param id
	 *            构ID
	 * @return 机构实体List
	 */
	public List<Group> findChildrenById(Integer id, Integer siteId) {
		List<Group> groupList = groupDAO.findChildrenById(id, siteId);
		return groupList;
	}

	/**
	 * 通过机构ID获得下属机构集合（树）
	 * 
	 * @param iid
	 *            机构ID
	 * @return List<Group>
	 */
	public List<Group> findChildGroupByIid(Integer iid, Integer siteId) {
		return groupDAO.findChildGroupByIid(iid, siteId);
	}

	/**
	 * 以Excel形式导出机构信息
	 * 
	 * @param ids
	 *            机构ID串 如:1,2,3<br/>
	 *            ids不为空时导出所选机构<br/>
	 *            ids为空时导出pid下的所有子孙机构
	 * @param pid
	 *            父机构ID
	 * @return 导出文件的绝对路径
	 */
	public String exportGroup(String ids, Integer pid, Integer siteId) {
		String filePath = "";
		List<List<String>> rows = new ArrayList<List<String>>();
		List<String> headList = new ArrayList<String>();/* 表头 */
		List<String> valueList = null; /* 机构数据列 */
		List<Group> groupList = null;

		headList.add("机构ID");
		headList.add("机构名称");
		headList.add("机构描述");
		headList.add("机构标识");
		headList.add("上级机构ID");
		headList.add("上级机构名称");
		headList.add("上级机构标识");
		rows.add(headList);

		if (StringUtil.isNotEmpty(ids)) {// 按机构id导出
			groupList = this.findByIds(ids); // 得到所要导出的机构
		} else {
			groupList = this.findAllSeedsById(pid, null, siteId);/* 按父机构导出其下所有子孙机构 */
		}

		for (Group group : groupList) {
			valueList = new ArrayList<String>();
			valueList.add(StringUtil.getString(group.getIid()));
			valueList.add(group.getName());
			valueList.add(group.getSpec());
			valueList.add(group.getCodeId());
			valueList.add(StringUtil.getString(group.getPid()));
			valueList.add(group.getPname());
			valueList.add(group.getParCodeid());
			valueList.add(StringUtil.getString(group.getSiteId()));
			rows.add(valueList);
		}

		/* 写入文件 */
		String fileName = StringUtil.getUUIDString() + ".xls";
		filePath = Settings.getSettings().getFileTmp() + fileName;
		ExcelUtil.writeExcel(filePath, rows);

		return filePath;
	}

	/**
	 * 迭代获得机构下的所有子孙机构
	 * 
	 * @param pid
	 *            机构ID
	 * @param groupList
	 *            机构实体List
	 * @return 机构实体List
	 */
	public List<Group> findAllSeedsById(Integer pid, List<Group> groupList, Integer siteId) {
		if (groupList == null) {
			groupList = new ArrayList<Group>();
		}
		
		
		List<Group> chlidGroupList = this.findChildrenById(pid, siteId);
		if (CollectionUtils.isEmpty(chlidGroupList)) {
			return groupList;
		}else{
			for (Group group : chlidGroupList) {
				groupList.add(group);
				groupList = this.findAllSeedsById(group.getIid(), groupList, siteId);
			}
		}
		return groupList;
	}

	/**
	 * 递归获得父机构下的所有子孙机构
	 * 
	 * @param pid
	 *            父机构ID
	 * @param groupList
	 *            机构实体List
	 * @return 机构实体List
	 */
	public List<Integer> findIdsByPId(Integer pid, List<Integer> idsList) {
		if (idsList == null) {
			idsList = new ArrayList<Integer>();
		}
		List<Map<String, Object>> chlidIdsList = groupDAO.findIdsByPid(pid);

		int id = 0;

		for (Map<String, Object> map : chlidIdsList) {
			id = NumberUtil.getInt(map.get("iid"));
			idsList.add(id);
			idsList = this.findIdsByPId(id, idsList);
		}

		return idsList;
	}

	/**
	 * 从Excel导入机构信息
	 * 
	 * @param filePath
	 *            导入Excel表单的绝对路径
	 * @return 提示信息
	 * @throws OperationException
	 *             错误信息
	 */
	public String importGroup(File file) throws OperationException {
		if (file == null) {
			throw new OperationException("无法找到上传的文件！");
		}
		List<Map<String, String>> rows = ExcelUtil.readExcel(file);
		if (CollectionUtils.isEmpty(rows)) {
			throw new OperationException(SpringUtil.getMessage("import.filetype.error"));
		}

		List<Group> groupList = this.findGroupListByRows(rows);/* 读出数据 */
		String retMessage = "";

		try {
			retMessage = this.importGroups(groupList);/* 循环插入机构信息 */

			if (!retMessage.equals("")) {
				retMessage = "<div style='height:150px;overflow:auto'>导入完毕，存在以下问题：<br/>"
						+ retMessage + "</div>";
			}
			return retMessage;
		} catch (Exception e) {
			logger.error("import group error", e);
			return "导入失败";
		} finally {
			try {
				if (file.exists()) {
					file.delete();
				}
			} catch (Exception e) {
				logger.error("delete file error", e);
			}
		}
	}

	/**
	 * 获得机构实体List
	 * 
	 * @param rows
	 *            机构集合 每个Map为一个机构实体<br/>
	 *            key为表单头 如:"机构名称"<br/>
	 *            vaule为表单值 如:"大汉网络"
	 * @return 机构实体List
	 */
	@SuppressWarnings("deprecation")
    private List<Group> findGroupListByRows(List<Map<String, String>> rows) {
		List<Group> groupList = new ArrayList<Group>(); // 机构集合
		Map<String, String> cell = null;

		Group group = null;

		/* excel记录转换成用户集合 */
		Iterator<Map<String, String>> iterator = rows.iterator();
		while (iterator.hasNext()) {
			cell = iterator.next();
			group = new Group();
			group.setIid(NumberUtil.getInt(cell.get("机构ID")));
			group.setName(StringUtil.getStringTrim(cell.get("机构名称")));
			group.setPid(NumberUtil.getInt(cell.get("上级机构ID")));
			group.setPname(StringUtil.getStringTrim(cell.get("上级机构名称")));
			group.setSpec(StringUtil.getStringTrim(cell.get("机构描述")));
			group.setCodeId(StringUtil.getStringTrim(cell.get("机构标识")));
			group.setParCodeid(StringUtil.getStringTrim(cell.get("上级机构标识")));

			groupList.add(group);
		}

		return groupList;
	}

	/**
	 * 循环插入机构信息
	 * 
	 * @param groupList
	 *            机构实体List
	 * @return 提示信息
	 * @throws OperationException
	 *             界面异常
	 */
	private String importGroups(List<Group> groupList) throws OperationException {
		CurrentUser user = UserSessionInfo.getCurrentUser();
		if (groupList == null) {
			return "";
		}
		Group group = null; // Excel表中的取出的机构
		GroupFormBean validGroup = null; // 待验证并插入的机构对象
		Group tempGroup = null;
		Group tempGroup2 = null;

		StringBuilder result = new StringBuilder();
		Map<String, String> existMap = new HashMap<String, String>();

		Integer iid = 0;
		Integer pid = 0;
		String name = "";
		String codeId = "";
		String message = ""; // 错误提示信息
		Integer idInLevel = 0; // 机构所在的层级
		boolean isSccuess = false;
		String exist = "";/* 记录机构是否被导入过 */

		for (int i = 0; i < groupList.size(); i++) {
			group = groupList.get(i);
			if (group == null || StringUtil.isEmpty(group.getName())) {
				message = "该行机构名称为空";
				this.getReturnMessage(result, i, message);
				continue;
			}
			validGroup = new GroupFormBean();
			validGroup.setSiteId(user.getSiteId());
			iid = NumberUtil.getInt(group.getIid());
			name = group.getName();
			codeId = group.getCodeId();

			/* 检查导入信息是否符合要求 */
			message = this.checkImportGroup(group);
			if (message != null && message.length() > 0) {
				this.getReturnMessage(result, i, message);
				continue;
			}

			try {
				pid = this.findValidGroupPId(group); /* 得到父机构ID */

				validGroup.setIid(iid);
				validGroup.setPid(pid);
				validGroup.setName(name);
				validGroup.setCodeId(codeId);
				validGroup.setSpec(group.getSpec());
				validGroup.setRoleIds("3");	/* 导入机构默认赋予普通用户权限 */

				if (codeId.length() > 0) {/* 存在机构标识 */
					exist = existMap.get(codeId);
					if (StringUtils.equals(exist, "codeId")) {
						message = "机构标识'" + codeId + "'重复";
						this.getReturnMessage(result, i, message);
						continue;
					}
					tempGroup = this.findByCodeId(codeId);

					if (tempGroup == null && iid > 0) {
						tempGroup2 = this.findByIid(iid);
						if (tempGroup2 == null) {
							isSccuess = this.add(validGroup);
						} else if (StringUtil.isNotEmpty(tempGroup2.getCodeId())) {
							message = "机构标识与机构id冲突";
							this.getReturnMessage(result, i, message);
							continue;
						} else { /* 父机构没有机构标识 */
							isSccuess = this.modifyGroupAndCodeid(validGroup);
						}
					} else if (tempGroup == null && iid == 0) {
						idInLevel = this.findIdOfGroupByPid(name, pid);
						if (NumberUtil.getInt(idInLevel) == 0) {
							isSccuess = this.add(validGroup);
						} else {
							validGroup.setIid(idInLevel);
							isSccuess = this.modifyGroupAndCodeid(validGroup);
						}
					} else if (tempGroup != null && iid > 0) {
						if (tempGroup.getIid().equals(iid)) {/* 机构ID相同 即认为是同一个机构 */
							isSccuess = this.modify(validGroup);
						} else if (!tempGroup.getIid().equals(iid)) {
							message = "机构标识与机构id冲突";
							this.getReturnMessage(result, i, message);
							continue;
						}
					} else {
						idInLevel = this.findIdOfGroupByPid(name, pid);
						if (NumberUtil.getInt(idInLevel) == 0) {
							isSccuess = this.add(validGroup);
						} else {
							validGroup.setIid(idInLevel);
							isSccuess = this.modify(validGroup);
						}
					}
					existMap.put(codeId, "codeId");
				} else if (iid > 0) {/* 不存在机构标识,存在机构ID */
					exist = existMap.get(StringUtil.getString(iid));
					if (StringUtils.equals(exist, "iid")) {
						message = "机构ID'" + iid + "'重复";
						this.getReturnMessage(result, i, message);
						continue;
					}
					tempGroup = this.findByIid(iid);
					if (tempGroup == null) {
						isSccuess = this.add(validGroup);
					} else if (!ObjectUtils.equals(tempGroup.getPid(), pid)) {
						message = "对应的机构id不匹配";
						this.getReturnMessage(result, i, message);
						continue;
					} else {
						isSccuess = this.modify(validGroup);
					}
					existMap.put(iid + "", "iid");
				} else if (name.length() > 0) {/* 不存在机构标识,也不存在机构ID */
					exist = existMap.get(name);
					if (StringUtils.equals(exist, "name")) {
						message = "机构名称'" + name + "'重复";
						this.getReturnMessage(result, i, message);
						continue;
					}
					idInLevel = this.findIdOfGroupByPid(name, pid);
					if (idInLevel == null || idInLevel == 0) {
						isSccuess = this.add(validGroup);
					} else {
						validGroup.setIid(idInLevel);
						isSccuess = this.modify(validGroup);
					}
					existMap.put(name, "name");
				}
				if (!isSccuess) {
					message = "导入机构'" + name + "'出现异常";
					this.getReturnMessage(result, i, message);
					continue;
				}
			} catch (OperationException e) {
				this.getReturnMessage(result, i, e.getMessage());
				continue;
			}
		}
		return result.toString();
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
		result.append("[" + SpringUtil.getMessage("import.error", i + 2) + "]" + message);
		result.append("</li>");
	}

	/**
	 * 根据机构找出父机构ID
	 * 
	 * @param group
	 *            机构实体
	 * @return 父机构ID
	 * @throws Exception
	 *             界面异常信息
	 */
	private Integer findValidGroupPId(Group group) throws OperationException {
		Group parGroup = null;
		List<Group> groupList = null;

		Integer pId = null;
		String name = group.getName();
		String pName = group.getPname();
		String parCodeId = group.getParCodeid();
		String message = "";

		if (NumberUtil.getInt(group.getPid()) == 0 && "".equals(pName) && "".equals(parCodeId)) {
			// 父机构标识 、父机构名称及父机构ID均为空的时候 为顶级机构
			return 0;
		}

		if (!"".equals(parCodeId)) {
			parGroup = this.findByCodeId(parCodeId); // 通过父机构标识找父机构
			if (parGroup != null) {
				pId = parGroup.getIid();
				return pId;
			}
		}

		if (group.getPid() != null) { // 通过父机构id找父机构
			parGroup = this.findByIid(group.getPid());
			if (parGroup != null && parGroup.getName().equals(name)) {
				if (parGroup.getName().equals(name)) {
					pId = parGroup.getIid();
					return pId;
				}

			}
		}
		if (!"".equals(pName)) {// 通过父机构名称找父机构
			groupList = this.findByName(pName);
			if (CollectionUtils.isEmpty(groupList)) {
				message = "无法找到上级机构'" + pName + "'";
			} else if (groupList.size() == 1) { // 只存在一个父机构
				parGroup = groupList.get(0);
				pId = parGroup.getIid();
				return pId;
			} else {
				message = "存在多个同名上级机构'" + pName + "'，  无法准确定位";
			}
		}

		message = "无法找到上级机构";
		throw new OperationException(message);
	}

	/**
	 * 通过机构名称或名称首字母缩写查询机构
	 * 
	 * @param keyword
	 *            关键字
	 * @return
	 */
	public List<Group> findByNameOrPinYin(String keyword) {

		List<Group> groupList = groupDAO.findByNameOrPinYin(keyword);

		return groupList;
	}

	/**
	 * 获得角色相关的机构列表
	 * 
	 * @param roleId
	 *            角色ID
	 * @return
	 */
	public List<Group> findGroupsByRoleId(Integer roleId) {
		if (NumberUtil.getInt(roleId) == 0) {
			return null;
		}
		List<Group> groupList = groupDAO.findGroupsByRoleId(roleId);

		return groupList;
	}

}