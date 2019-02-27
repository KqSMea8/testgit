package com.hanweb.jmp.global.dao;

import java.util.Date;
import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;

import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.global.entity.PushThread;

public class PushThreadDAO extends BaseJdbcDAO<Integer, PushThread> {

	/**
	 * 获得所有到时间要推送的记录
	 * @param now Date
	 * @return List<PushThread>
	 */
	public List<PushThread> findAllByTime(Date now){
		String sql = getEntitySql() + "  WHERE pushtime < :pushtime ";
		Query query = createQuery(sql);
		query.addParameter("pushtime", now);
		return queryForEntities(query);
	}
	
	/**
	 * 获取未推送的所有信息的数量
	 * @return int
	 */
	public int findAllPushTask(){
		int num = 0;
		String sql = " SELECT COUNT(iid) FROM " + Tables.PUSHTHREAD ;
		Query query = createQuery(sql);
		num = this.queryForInteger(query); 
		return num;
	}
	
}