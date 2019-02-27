package com.hanweb.jmp.newspush.userdevice.dao;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.newspush.userdevice.entity.UserDevice;


/**
 * 用户设备关系
 * 
 * @author Wangjw
 * 
 */
public class UserDeviceDAO extends BaseJdbcDAO<Integer, UserDevice> {

	/**
	 * 根据用户Id获得实体 
	 * @param uid 用户id
	 * @return 实体
	 */
	public List<UserDevice> findByUid(Integer uid){
		String sql = this.getEntitySql() + " WHERE usid=:usid";
		Query query = createQuery(sql);
		query.addParameter("usid", uid);
		List<UserDevice> userDevices = this.queryForEntities(query);
		return userDevices;
	}
	
	/**
	 * 根据用户Id集合获得实体 
	 * @param uid 用户id
	 * @return 实体
	 */
	public List<UserDevice> findByUserid(List<String> userIds){
		String sql = this.getEntitySql() + " WHERE userid IN(:userIds)";
		Query query = createQuery(sql);
		query.addParameter("userIds", userIds);
		List<UserDevice> userDevices = this.queryForEntities(query);
		return userDevices;
	}
	
	/**
	 * 根据用户Id获得实体 
	 * @param uid 用户id
	 * @return 实体
	 */
	public UserDevice findByUserid(String userId){
		String sql = this.getEntitySql() + " WHERE userid = :userId";
		Query query = createQuery(sql);
		query.addParameter("userId", userId);
		UserDevice userDevice = queryForEntity(query);
		return userDevice;
	}
	
	/**
	 * 根据uuid获得设备
	 * @param uuid 设备uuid
	 * @return 设备
	 */
	public UserDevice findByUuid(String uuid){
		String sql = this.getEntitySql();
		sql += " WHERE uuid=:uuid";
		Query query = createQuery(sql);
		query.addParameter("uuid", uuid);
		UserDevice userDevice = this.queryForEntity(query);
		return userDevice;
	}
	
	/**
	 * 获得服务器下所有设备
	 * @return
	 */
	public List<UserDevice> findAllDevice(){
		String sql = this.getEntitySql();
		Query query = createQuery(sql);
		List<UserDevice> l = this.queryForEntities(query);
		return l;
	}
	
	/**
	 * 根据uuid删除设备
	 * @param uuid
	 * @return
	 */
	public boolean delByUuid(String uuid){
		String sql = "DELETE FROM " + Tables.USER_DEVICE + " WHERE uuid=:uuid";	
		Query query = createQuery(sql);
		query.addParameter("uuid", uuid);
		super.execute(query); 
		return true;
	}
	
}
