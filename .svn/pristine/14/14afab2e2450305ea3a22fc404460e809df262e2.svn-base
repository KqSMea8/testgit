package com.hanweb.jmp.cms.dao.matters.doc;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.NumberUtil;

import com.hanweb.jmp.cms.entity.matters.doc.DocCol;
import com.hanweb.jmp.constant.Tables;

public class DocColDAO extends BaseJdbcDAO<Integer,DocCol>{

	/**
	 * 根据id获得分类
	 * @param iid
	 * @return
	 */
	public DocCol findByIid(int iid){
		String sql = getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		return this.queryForEntity(query);
	}
	
	/**
	 * 根据站点id取得该站点下附件分类
	 * @param siteid
	 * @return
	 */
	public List<DocCol> findDocCol(Integer siteid) {
		String sql = this.getEntitySql() + " WHERE siteid=:siteid ORDER BY orderid ASC, iid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", NumberUtil.getInt(siteid));
		return this.queryForEntities(query);
	}
	
	/**
	 * 查询排序最大id
	 * @return Integer
	 */
	public Integer findMaxOrderId() {
		String sql = "SELECT MAX(orderid) FROM " + Tables.DOCCOL;
		Query query = createQuery(sql);
		return this.queryForInteger(query);
	}
	
	/**
	 * 根据siteId查找附件分类数
	 * @param siteId Integer
	 * @return Integer
	 */
	public Integer findNumBySiteId(Integer siteId) {
		String sql = "SELECT COUNT(iid) FROM " + Tables.DOCCOL + " WHERE siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("siteid", NumberUtil.getInt(siteId));
		return queryForInteger(query);
	}
	
	/**
	 * 更新排序
	 * @param iid int
	 * @param orderId int
	 * @return boolean
	 */
	public boolean updateOrder(int iid, int orderId) {
		UpdateSql sql = new UpdateSql(Tables.DOCCOL);
		sql.addInt("orderid", orderId);
		sql.setWhere("iid=:iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
	}
	
	/**
	 * 根据siteId查找doccol
	 * @param siteId
	 * @return
	 */
	public List<DocCol> findBySiteId(int siteId){
		String sql = getEntitySql() + " WHERE siteid=:siteid ";
        sql+=" ORDER BY orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		return this.queryForEntities(query);
	} 
	
	/**
	 * 获得附件数
	 * @param ids
	 *            附件分类ID串 如:1,2,3
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findCountSubInfo(List<Integer> ids) {
		String sql = "SELECT COUNT(iid) FROM " + Tables.DOC + " WHERE classid IN (:ids)";
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		int num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 获得附件数
	 * @param ids
	 *            栏目ID串 如:1,2,3
	 * @param siteId          
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findCountRecycleInfo(List<Integer> ids, Integer siteId) {
		String sql = " SELECT COUNT(iid) FROM " + Tables.DOC
		           + " WHERE isremove IN (:ids) ";
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		int num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 判断是否重复
	 * @param iid
	 * @param name
	 * @param siteId
	 * @param pid
	 * @return
	 */
	public int findNumOfSameName(Integer iid, String name, Integer siteId) {
		int num = 0;
		String sql = " SELECT COUNT(iid) FROM " + Tables.DOCCOL + " WHERE name=:name ";
		if (NumberUtil.getInt(iid) > 0) {
			sql += " AND iid NOT IN(:iid)";
		}
		if (NumberUtil.getInt(siteId) > 0) {
			sql += " AND siteid=:siteId";
		}
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		query.addParameter("name", name);
		query.addParameter("siteId", siteId);
		num = this.queryForInteger(query);
		return num;
	}
	
}