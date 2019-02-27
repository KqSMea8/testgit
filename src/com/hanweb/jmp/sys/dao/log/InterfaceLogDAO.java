package com.hanweb.jmp.sys.dao.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;

import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.entity.log.InterfaceLog;

public class InterfaceLogDAO extends BaseJdbcDAO<Integer, InterfaceLog>{

	/**
	 * 根据id获得接口日志实体
	 * @param iid siteId
	 * @param siteId Integer
	 * @return InterfaceLog
	 */
	public InterfaceLog findByIid(int iid, Integer siteId){
		String sql = this.getEntitySql() + " WHERE iid=:iid";
		sql = sql.replace(Tables.INTERFACE_LOG, "jmp_interface_log" + siteId);
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		return this.queryForEntity(query);
	}
	
	/**
	 * 统计每种客户端调用接口记录的条数
	 * @param iid  网站ID
	 * @return List<InterfaceLog>
	 */
	public List<InterfaceLog>  findClientTypeCount(int iid){		
		String sql = " SELECT clienttype, COUNT(clienttype) amount FROM " + Tables.INTERFACE_LOG
			       + " WHERE siteid = :iid  GROUP BY clienttype";
		sql = sql.replace(Tables.INTERFACE_LOG, "jmp_interface_log" + iid);
		Query query = createQuery(sql);
		query.addParameter("iid", iid);			
		return this.queryForEntities(query);
	}
	
	/**
	 * 统计各个时段及接口调用的次数
	 * @param iid  网站ID
	 * @return Map<Integer,Integer>
	 */
	public Map<Integer, Integer>  findInterfaceCountsBySiteId(int iid){
		Map<Integer, Integer> userMap = new HashMap<Integer, Integer>();
		int count = 0;	
		int j = 0;
		for(int i = 1; i < 6; i++){
			String sql = " SELECT COUNT(1) FROM " + Tables.INTERFACE_LOG
			           + " WHERE siteid = :iid "
			           + " AND hour between :start AND :end";
			sql = sql.replace(Tables.INTERFACE_LOG, "jmp_interface_log" + iid);
			Query query = createQuery(sql);
			query.addParameter("start", j);
			query.addParameter("end", (j+4));
			query.addParameter("iid", iid);
			count = this.queryForInteger(query); 
			userMap.put(i, count);
			j=5*i;
		}
		
		return userMap;
	}
	
	/**
	 * 根据iid获取接口名称
	 * @param iid int
	 * @return List<InterfaceLog>
	 */
	public List<InterfaceLog> findCountsOfInterface(int iid){	
		String sql = " SELECT name interfacename, COUNT(name) num FROM " +  Tables.INTERFACE_LOG 
		           + " WHERE siteid = :iid GROUP BY name";
		sql = sql.replace(Tables.INTERFACE_LOG, "jmp_interface_log" + iid);
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		return this.queryForEntities(query);
	}
	
	/**
	 * 增加信息阅读数表
	 * @param tableName 表名
	 * @return boolean
	 */
	public boolean addTable(String tableName){
		return this.createTable(InterfaceLog.class, tableName);
	}
	
	/**
	 * 删除信息阅读数表
	 * @param tableName 表名
	 * @return boolean
	 */
	public boolean deleteTable(String tableName){
		String sql = "DROP TABLE " + Tables.INTERFACE_LOG;
		sql = sql.replace("jmp_interface_log", tableName);
		Query query = createQuery(sql);
		return this.delete(query);
	}
	
	/**
	 * 查找某一时间点前的接口日志
	 * @param endTime 时间
	 * @param siteId  网站id
	 * @return List<InterfaceLog>
	 */
	public List<InterfaceLog> findByEndTime(String endTime, Integer siteId){
		String sql = this.getEntitySql() + "WHERE oprTime<:endtime";
		sql = sql.replace(Tables.INTERFACE_LOG, "jmp_interface_log" + siteId);
		Query query = createQuery(sql);
		query.addParameter("endtime", endTime);
		return this.queryForEntities(query);
	}
	
}