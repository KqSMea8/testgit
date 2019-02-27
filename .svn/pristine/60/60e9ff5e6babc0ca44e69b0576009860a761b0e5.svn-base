package com.hanweb.jmp.pack.backstage.dao;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.pack.backstage.entity.Application;

public class ApplicationDAO extends BaseJdbcDAO<Integer, Application>{
	
	/**
	 * 根据主键id查找实体
	 * @param iid 主键id
	 * @return Application
	 */
	public Application findByIid(int iid){
		String sql = getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		return this.queryForEntity(query);
	}
	
	/**
	 * 根据网站id查找实体
	 * @param siteId
	 * @return
	 */
	public List<Application> findBySiteId(int siteId){
		String sql = getEntitySql() + " WHERE siteid=:siteid ORDER BY orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		return this.queryForEntities(query);
	}
	
	/**
	 * 根据网站id和姓名查找数量
	 * @param iid
	 * @param name
	 * @param siteId
	 * @return
	 */
	public int findNumByName(int iid, String name,  int siteId) {
		String sql = " SELECT COUNT(1) FROM " + Tables.APPLICATIONS + " WHERE "
			       + " siteid=:siteid AND name=:name AND iid <> :iid";
		Query query = createQuery(sql);
		query.addParameter("name", name);
		query.addParameter("iid", iid);
		query.addParameter("siteid", siteId);
		Integer num = this.queryForInteger(query);
		return NumberUtil.getInt(num);
	}
	
	/**
	 * 根据网站id查找最小排序id
	 * @param siteId
	 * @return
	 */
	public int findMinOrderIdBySiteId(int siteId){
		String sql = " SELECT MIN(orderid) FROM " + Tables.APPLICATIONS
			       + " WHERE siteid=:siteid ";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		return this.queryForInteger(query);
	}
	
	/**
	 * 根据iid修改orderid
	 * @param iid int
	 * @param orderId int
	 * @return boolean
	 */
	public boolean updateOrderIdById(int iid, int orderId){
		UpdateSql sql = new UpdateSql(Tables.APPLICATIONS);
		sql.addInt("orderid", orderId);
		sql.setWhere("iid = :iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
	}
	
}