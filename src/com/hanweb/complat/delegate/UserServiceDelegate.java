package com.hanweb.complat.delegate;

import com.hanweb.complat.entity.User;
import com.hanweb.support.controller.CurrentUser;

/**
 * UserService的代理，基于UserService的后置方法
 * 
 * @author 李杰
 * 
 */
public interface UserServiceDelegate {
	/**
	 * 登陆后置，比如权限可以在这里进行组织
	 * 
	 * @param user
	 */
	public void checkUserLogin(CurrentUser currentUser);

	/**
	 * 新增后置
	 * 
	 * @param user
	 */
	public void add(User user);

	/**
	 * 修改后置
	 * 
	 * @param user
	 */
	public void modify(User user);

	/**
	 * 删除后置
	 * 
	 * @param ids
	 */
	public void removeByIds(String ids);
}
