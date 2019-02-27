package com.hanweb.jmp.apps.dao.broke;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.NumberUtil;

import com.hanweb.jmp.apps.entity.broke.BrokeType;
import com.hanweb.jmp.constant.Tables;

public class BrokeTypeDAO extends BaseJdbcDAO<Integer, BrokeType> {
	
	/**
	 * 根据站点id取得该站点下报料分类
	 * @param siteid Integer
	 * @return List<BrokeType>
	 */
	public List<BrokeType> findBrokeType(Integer siteid) {
		String sql = this.getEntitySql() + " WHERE siteid=:siteid ORDER BY orderid ASC, iid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", NumberUtil.getInt(siteid));
		return this.queryForEntities(query);
	}
	
	/**
	 * 查找审核状态
	 * @param id Integer
	 * @return Integer
	 */
	public Integer findAudit(Integer id) {
		String sql = "SELECT audittype FROM " + Tables.BROKETYPE + " WHERE iid=:id";
		Query query = createQuery(sql);
		query.addParameter("id", id);
		return this.queryForInteger(query);
	}

	/**
	 * 查找最大排序id
	 * @return Integer
	 */
	public Integer findMaxOrderId() {
		String sql = "SELECT MAX(orderid) FROM " + Tables.BROKETYPE;
		Query query = createQuery(sql);
		return this.queryForInteger(query);
	}

	/**
	 * 根据网站id查找
	 * @param siteId Integer
	 * @return Integer
	 */
	public Integer findNumBySiteId(Integer siteId) {
		String sql = "SELECT COUNT(iid) FROM " + Tables.BROKETYPE + " WHERE siteid=:siteid";
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
		UpdateSql sql = new UpdateSql(Tables.BROKETYPE);
		sql.addInt("orderid", orderId);
		sql.setWhere("iid=:id");
		sql.addWhereParamInt("id", iid);
		return this.update(sql);
	}
	
	/**
	 * 获得信息数
	 * @param ids
	 *            报料分类ID串 如:1,2,3
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findCountSubInfo(List<Integer> ids) {
		String sql = "SELECT COUNT(iid) FROM " + Tables.BROKE + " WHERE classid IN (:ids)";
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		int num = this.queryForInteger(query);
		return num;
	}
	
}