package com.hanweb.jmp.sys.dao.feedback;

import java.util.Date;
import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;

import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.entity.feedback.FeedBack;

public class FeedBackDAO extends BaseJdbcDAO<Integer, FeedBack> {
	
	/**
	 * 插入操作
	 * @param feedBack FeedBack
	 * @return boolean
	 */
	public boolean addFeedBack(FeedBack feedBack) {
		int num = this.insert(feedBack);
		if (num > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取反馈内容
	 * @param deviceCode String
	 * @param userLoginName String
	 * @param sinceTime Long
	 * @param nextTime Long
	 * @param linages int
	 * @return List<FeedBack>
	 */
	public List<FeedBack> findFeedBackInfo(String deviceCode, String userLoginName, Long sinceTime, 
			                               Long nextTime, int linages) {
		StringBuffer sql = new StringBuffer(200);
		Query query = createQuery("");
		sql.append(getEntitySql());
		if (StringUtil.isNotEmpty(userLoginName)) {
			sql.append(" WHERE loginname:LoginName");
			query.addParameter("LoginName", userLoginName);
		} else {
			sql.append(" WHERE devicecode=:deviceCode");
			query.addParameter("deviceCode", deviceCode);
		}
		if (sinceTime != 0L && nextTime != 0L) {
			return null;
		} else if (nextTime != 0L) {
			sql.append(" AND createtime<:time ORDER BY createtime DESC");
			String time = DateUtil.dateToString(new Date(nextTime), "yyyy-MM-dd HH:mm:ss");
			query.addParameter("time", time);
			query.setStart(1);
			query.setEnd(linages + 1);
			query.setSql(sql.toString());
			return this.queryForEntities(query);
		} else {
			if (sinceTime == 0L && nextTime == 0L) {
				sql.append(" ORDER BY createtime DESC");
				query.setStart(1);
				query.setEnd(linages + 1);
			} else {
				sql.append(" AND createtime>:time ORDER BY createtime");
				String time = DateUtil.dateToString(new Date(sinceTime), "yyyy-MM-dd HH:mm:ss");
				query.addParameter("time", time);
			}
			query.setSql(sql.toString());
			return this.queryForEntities(query);
		}
	}

	/**
	 * 获取反馈内容
	 * @param deviceCode String
	 * @param userLoginName String
	 * @param sinceTime
	 * @param nextTime
	 * @param linages
	 * @return List<FeedBack>
	 */
	public List<FeedBack> findFeedBackInfo(String deviceCode, String userLoginName) {
		StringBuffer sql = new StringBuffer(200);
		Query query = createQuery("");
		sql.append(getEntitySql());
		if (StringUtil.isNotEmpty(userLoginName)) {
			sql.append(" WHERE loginname:LoginName");
			query.addParameter("LoginName", userLoginName);
		} else {
			sql.append(" WHERE devicecode=:deviceCode");
			query.addParameter("deviceCode", deviceCode);
		}
		sql.append(" ORDER BY createtime DESC");
		query.setSql(sql.toString());
		query.setStart(1);
		query.setEnd(50);
		return this.queryForEntities(query);
	}

	/**
	 * 获取某一站点某一分类下信息列表
	 * @param siteid Integer
	 * @param time Date
     * @param pageSize Integer
     * @param type Integer
     * @param deviceCode String
     * @param loginname String
	 * @return List<FeedBack>
	 */
	public List<FeedBack> findInfoList(Integer siteid, Integer pageSize, Date time, Integer type, 
			                           String deviceCode, String loginname) {
		Query query = createQuery("");
		StringBuffer sql = new StringBuffer(300); 
		sql.append(getEntitySql());
		sql.append(" WHERE  siteid=:siteid"); 
		if(time != null){
			  //刷新
			  if(NumberUtil.getInt(type)==1){
				  sql.append(" AND createtime > :createtime");  
			  }else{
				  sql.append(" AND createtime < :createtime");  
			  } 
		}
		if(StringUtil.isNotEmpty(deviceCode)){
			sql.append(" AND devicecode = :devicecode");  
		}
		if(StringUtil.isNotEmpty(loginname)){
			sql.append(" AND loginname = :loginname");  
		}
		if(NumberUtil.getInt(pageSize)<=0){
			pageSize=15;
		}
		sql.append(" ORDER BY iid DESC");
		query.setStart(0);
		query.setEnd(pageSize); 
		query.addParameter("siteid", siteid); 
		if(time != null){
			query.addParameter("createtime", time);
		}
		if(StringUtil.isNotEmpty(deviceCode)){
			query.addParameter("devicecode", deviceCode); 
		}
		if(StringUtil.isNotEmpty(loginname)){
			query.addParameter("loginname", loginname);
		}
		query.setSql(sql.toString());
		return queryForEntities(query);
	}
	
	/**
	 * 修改已读状态
	 * @param iid Integer
	 * @return boolean
	 */
	public boolean update(Integer iid) {
		UpdateSql updateSql = new UpdateSql(Tables.FEEDBACK);
		updateSql.addInt("isread", 1);
		updateSql.setWhere("iid=:iid");
		updateSql.addWhereParamInt("iid", iid);
		return this.update(updateSql);
	}

}