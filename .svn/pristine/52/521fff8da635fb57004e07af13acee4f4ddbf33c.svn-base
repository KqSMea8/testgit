package com.hanweb.jmp.pack.service; 

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.Md5Util;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.pack.dao.AppUserDAO;
import com.hanweb.jmp.pack.entity.AppUser;

public class AppUserService { 
	
	/**
	 * userDAO
	 */
	@Autowired
	private AppUserDAO userDAO; 

	/**
	 * 检查是否登录
	 * @param user user
	 * @return    设定参数 .
	 */
	public AppUser checkUserLogin(AppUser user) {
		AppUser user1 = userDAO.findByNameAndPwd(user);
		if(user1!=null){
			String password = Md5Util.md5decode(user1.getPwd());
			if(StringUtils.equals(password, user.getPwd())){
				return user1;
			} else {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 根据名称获取用户实体
	 * @param username username
	 * @return    设定参数 .
	 */
	public AppUser findByName(String username){
		AppUser user = userDAO.findByName(username);
		return user;
	}
	
	/**
	 * 获取相同用户名的个数
	 * @param username
	 *            username
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findNumOfSameName(String username) {
		return userDAO.findNumOfSameName(username);
	}
	
	/**
	 * 新增用户
	 * @param user user
	 * @return    设定参数 .
	 */
	public boolean add(AppUser user) throws OperationException{
		int samenum = userDAO.findNumOfSameName(user.getUsername());
		if(samenum>0){
			throw new OperationException("用户名称已存在,请重新填写！");
		}
		int userId = userDAO.insert(user);
		return userId>0;
	}
	
	/**
	 * 新增用户
	 * @param user user
	 * @return    设定参数 .
	 */
	public Integer addUser(AppUser user){
		int userId = userDAO.insert(user);
		return userId;
	}
	
	/**
	 * 根据uuid获取用户
	 * @param uuid uuid
	 * @return    设定参数 .
	 */
	public AppUser findByUuid(String uuid){
		AppUser user = userDAO.findByUuid(uuid);
		return user;
	}
	
	/**
	 * 根据id获取用户实体
	 * @param iid iid
	 * @return    设定参数 .
	 */
	public AppUser findByid(Integer iid){
		AppUser user = userDAO.findByid(iid);
		return user;
	}
	
	/**
	 * 修改
	 * @param appUser appUser
	 * @return    设定参数 .
	 */
	public boolean modify(AppUser appUser) throws OperationException {
		boolean bl = userDAO.update(appUser);
		return bl;
	}

	/**
	 * 删除打包用户
	 * @param ids
	 *            ID串 如:1,2,3 
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean removeByIds(String ids) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		return userDAO.deleteByIds(idList);
	}
	
}