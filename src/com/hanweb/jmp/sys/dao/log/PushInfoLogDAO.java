package com.hanweb.jmp.sys.dao.log;

import java.util.HashMap;
import java.util.Map;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;

import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.entity.log.PushInfoLog;

public class PushInfoLogDAO extends BaseJdbcDAO<Integer, PushInfoLog>{
	
	/**
	 * 根据网站id清除日志信息
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public boolean clean(Integer siteId){
		String sql = "DELETE FROM " + Tables.PUSH_INFO_LOG + " WHERE siteid=:siteId";
		Query query = createQuery(sql);
		query.addParameter("siteId", siteId);
		return delete(query);
	}
	
	/**
	 * 删除推送日志信息
	 * @return    设定参数 .
	 */
	public boolean clean(){
		String sql = "DELETE FROM " + Tables.PUSH_INFO_LOG ;
		Query query = createQuery(sql);
		return delete(query);
	}
	
	/**
	 * 按年份和网站ID查找推送日志的条数
	 * @param year 年份
	 * @param iid  网站ID
	 * @return Map<Integer,Integer>
	 */
	public Map<Integer, Integer>  findByYearAndSiteId(String year, int iid){
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		int count = 0;
		for(int i = 1; i < 13; i++){
			String sql = " SELECT COUNT(1) FROM " + Tables.PUSH_INFO_LOG
			           + " WHERE siteid = :iid AND createtime between :starttime AND :endtime  "
			           + " AND month = :month";
			Query query = createQuery(sql);
			query.addParameter("starttime", year+"-01-01 00:00:00");
			query.addParameter("endtime", year+"-12-31 23:59:59");
			query.addParameter("iid", iid);
			query.addParameter("month", i);
			count = this.queryForInteger(query);
			map.put(i, count);
		}
		return map;
	}
	
	/**
	 * 按年份和网站ID查找新增注册用户
	 * @param year 年份
	 * @param iid  网站ID
	 * @return Map<Integer,Integer>
	 */
	public Map<Integer, Integer>  findOutuserByYearAndSiteId(String year, int iid){
		Map<Integer, Integer> userMap = new HashMap<Integer, Integer>();
		int count = 0;
		for(int i = 1; i < 13; i++){
			String sql = " SELECT COUNT(1) FROM " + com.hanweb.complat.constant.Tables.OUTSIDEUSER
			           + " WHERE siteid = :iid AND createtime between :starttime AND :endtime  ";
			Query query = createQuery(sql);
			if(i < 10){
				query.addParameter("starttime", year+"-0"+i+"-01 00:00:00");
				query.addParameter("endtime", year+"-0"+i+"-31 23:59:59");
			}else{
				query.addParameter("starttime", year+"-"+i+"-01 00:00:00");
				query.addParameter("endtime", year+"-"+i+"-31 23:59:59");
			}
			query.addParameter("iid", iid);
			count = this.queryForInteger(query); 
			userMap.put(i, count);
		}
		return userMap;
	}
	
	/**
	 * 统计各个时段信息统送的次数
	 * @param year 年份
	 * @param iid  网站ID
	 * @return Map<Integer,Integer>
	 */
	public Map<Integer, Integer>  findPushTimeByYearAndSiteId(String year, int iid){
		Map<Integer, Integer> userMap = new HashMap<Integer, Integer>();
		int count = 0;	
		int j = 0;
		for(int i = 1; i < 6; i++){
			String sql = " SELECT COUNT(1) FROM " + Tables.PUSH_INFO_LOG
			           + " WHERE siteid = :iid AND createtime between :starttime AND :endtime  "
			           + " AND hour between :start AND :end";
			Query query = createQuery(sql);
			query.addParameter("starttime", year+"-01-01 00:00:00");
			query.addParameter("endtime", year+"-12-31 23:59:59");
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
	 * 更新推送日志信息
	 * @param androidstatus androidstatus
	 * @param iosstatus iosstatus
	 * @param infoId infoId
	 * @param siteId siteId
	 * @param ipadstatus  ipadstatus
	 * @return    设定参数 .
	*/
	public boolean updatePushInfoLog(int androidstatus, int iosstatus, int ipadstatus, int infoId, int siteId){
		String sql = " UPDATE "+ Tables.PUSH_INFO_LOG 
		           + " SET androidstatus=:androidstatus, iosstatus=:iosstatus, "
		           + " ipadstatus=:ipadstatus WHERE infoid=:infoid AND siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("infoid", infoId);
		query.addParameter("siteid", siteId);
		query.addParameter("androidstatus", androidstatus);
		query.addParameter("iosstatus", iosstatus);
		query.addParameter("ipadstatus", ipadstatus);
		return this.execute(query) > 0;
	}
	
}