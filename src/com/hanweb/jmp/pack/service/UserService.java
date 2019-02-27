package com.hanweb.jmp.pack.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.Md5Util;
import com.hanweb.common.util.PinyinUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.dao.UserDAO;
import com.hanweb.complat.entity.User;
import com.hanweb.complat.exception.OperationException;

public class UserService {
	
	/**
	 * userDAO
	 */
	@Autowired
	private UserDAO userDAO;
	
	/**
	 * 新增用户  用户网站新建
	 * 
	 * @param user
	 *            用户实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public Integer add(User user) throws OperationException {
		if (user == null) {
			return 0;
		}
		int num = userDAO.findIidByLoginName(user.getIid(), user.getLoginName());
		if (num > 0) {
			throw new OperationException("登录名已存在,请重新设置！");
		}
		user.setPwd(Md5Util.md5encode(user.getPwd()));
		user.setCreatetime(new Date());
		user.setPinYin(PinyinUtil.getHeadByString(user.getName()));

		int iid = userDAO.insert(user);
		return iid;
	}
	
	/**
	 * 通过用户ID获得用户实体
	 * 
	 * @param iid
	 *            用户ID
	 * @return User
	 */
	public User findByIid(int iid) {
		if (iid == 0){
			return null;
		}
		User user = userDAO.findByIid(iid);
		if (user == null) {
			return null;
		}
		user.setPwd(Md5Util.md5decode(user.getPwd()));
		return user;
	}
	
	/**
	 * 通过登录名获得用户实体
	 * 
	 * @param loginName
	 *            用户登录名
	 * @return User
	 */
	public User findByLoginName(String loginName) {
		if (StringUtil.isEmpty(loginName)){
			return null;
		}
		User user = userDAO.findByLoginName(loginName);
		if (user == null) {
			return null;
		}
		user.setPwd(Md5Util.md5decode(user.getPwd()));
		return user;
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
			user.setPwd(Md5Util.md5encode(user.getPwd()));
			isSuccess = userDAO.updatePassword(iid, user.getPwd());
		}

		return isSuccess;
	}
}
