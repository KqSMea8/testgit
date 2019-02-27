package com.hanweb.jmp.cms.dao.matters.video;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.NumberUtil;

import com.hanweb.jmp.cms.entity.matters.video.VideoCol;
import com.hanweb.jmp.constant.Tables;

public class VideoColDAO extends BaseJdbcDAO<Integer,VideoCol>{

	/**
	 * 根据id获得分类
	 * @param iid
	 * @return
	 */
	public VideoCol findByIid(int iid){
		String sql = getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		return this.queryForEntity(query);
	}
	
	/**
	 * 根据站点id取得该站点下分类
	 * @param siteid
	 * @return
	 */
	public List<VideoCol> findVideoCol(Integer siteid) {
		String sql = this.getEntitySql() + " WHERE siteid=:siteid ORDER BY orderid ASC, iid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", NumberUtil.getInt(siteid));
		return this.queryForEntities(query);
	}
	
	/**
	 * 查询最大排序id
	 * @return Integer
	 */
	public Integer findMaxOrderId() {
		String sql = "SELECT MAX(orderid) FROM " + Tables.VIDEOCOL;
		Query query = createQuery(sql);
		return this.queryForInteger(query);
	}
	
	/**
	 * 根据siteId查找分类数
	 * @param siteId Integer
	 * @return Integer
	 */
	public Integer findNumBySiteId(Integer siteId) {
		String sql = "SELECT COUNT(iid) FROM " + Tables.VIDEOCOL + " WHERE siteid=:siteid";
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
		UpdateSql sql = new UpdateSql(Tables.VIDEOCOL);
		sql.addInt("orderid", orderId);
		sql.setWhere("iid=:iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
	}
	
	/**
	 * 根据siteId查找videoCOL
	 * @param siteId
	 * @return
	 */
	public List<VideoCol> findBySiteId(int siteId){
		String sql = getEntitySql() + " WHERE siteid=:siteid ";
        sql+=" ORDER BY orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		return this.queryForEntities(query);
	} 
	
	/**
	 * 获得视频数
	 * 
	 * @param ids
	 *            视频分类ID串 如:1,2,3
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findCountSubInfo(List<Integer> ids) {
		String sql = "SELECT COUNT(iid) FROM " + Tables.VIDEO + " WHERE classid IN (:ids)";
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		int num = this.queryForInteger(query);
		return num;
	}
	/**
	 * 获得视频数
	 * 
	 * @param ids
	 *            栏目ID串 如:1,2,3
	 * @param siteId          
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findCountRecycleInfo(List<Integer> ids, Integer siteId) {
		String sql = " SELECT COUNT(iid) FROM " + Tables.VIDEO
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

		String sql = " SELECT COUNT(iid) FROM " + Tables.VIDEOCOL + " WHERE name=:name ";
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