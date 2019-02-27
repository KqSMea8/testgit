package com.hanweb.jmp.pack.dao;
   
import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;  

import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.pack.entity.AppUser;

public class AppUserDAO  extends BaseJdbcDAO<Integer, AppUser> {
	
	/**
	 * 根据用户名获取用户实体
	 * @param user user
	 * @return    设定参数 .
	 */
	public AppUser findByNameAndPwd(AppUser user){
		String sql = this.getEntitySql() + " WHERE username=:username";
		Query query = createQuery(sql);
		query.addParameter("username", user.getUsername());
		AppUser user1 = this.queryForEntity(query);
		return user1;
	}
	
	/**
	 * 根据用户名获取用户实体
	 * @param username username
	 * @return    设定参数 .
	 */
	public AppUser findByName(String username){
		String sql = this.getEntitySql() + " WHERE username=:username";
		Query query = createQuery(sql);
		query.addParameter("username", username);
		AppUser user = this.queryForEntity(query);
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
		int num = 0; 
		String sql = " SELECT COUNT(iid) FROM " + Tables.APPUSER + " WHERE username=:username ";
		Query query = createQuery(sql);
		query.addParameter("username", username); 
		num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 根据uuid回去用户实体
	 * @param uuid uuid
	 * @return    设定参数 .
	 */
	public AppUser findByUuid(String uuid){
		String sql = this.getEntitySql() + " WHERE uuid=:uuid";
		Query query = createQuery(sql);
		query.addParameter("uuid", uuid);
		AppUser user = this.queryForEntity(query);
		return user;
	}

	/**
	 * 根据iid获取用户实体
	 * @param iid iid
	 * @return    设定参数 .
	 */
	public AppUser findByid(int iid){
		String sql = this.getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		AppUser user = this.queryForEntity(query);
		return user;
	}
	
}