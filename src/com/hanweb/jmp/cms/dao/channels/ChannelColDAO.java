package com.hanweb.jmp.cms.dao.channels;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.NumberUtil;

import com.hanweb.jmp.cms.entity.channels.ChannelCol;
import com.hanweb.jmp.constant.Tables;

public class ChannelColDAO extends BaseJdbcDAO<Integer, ChannelCol>{
	
	/**
	 * getSql()
	 */
	private String getSql(){
		String sql = " SELECT iid, channelid, colid, orderid, siteid FROM " 
			       + Tables.CHANNEL_COLUMN;
		return sql;
	}
	
	/**
	 * 根据iid查导航
	 * @param iid int
	 * @return ChannelCol
	 */
	public ChannelCol findByIid(int iid){
		String sql = this.getEntitySql() + " WHERE iid = :iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		return this.queryForEntity(query);
	}
	
	/**
	 * 根据网站id和栏目id获取该导航选择过的栏目id
	 * @param siteId 网站id
	 * @param colIds 栏目id
	 * @return List 网站栏目对应关系list
	 */
	public List<ChannelCol> findByColids(Integer siteId, List<Integer> colIds){
		String sql =this.getEntitySql() + " WHERE siteid=:siteid AND colid IN (:colIds)";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("colIds", colIds);
		return this.queryForEntities(query);
	}
	
	   /**
     * 根据网站id和栏目id获取该导航选择过的栏目id
     * @param siteId 网站id
     * @param colIds 栏目id
     * @return List 网站栏目对应关系list
     */
    public List<ChannelCol> findByColid(Integer siteId, int colId){
        String sql =this.getEntitySql() + " WHERE siteid=:siteid AND colid=:colId";
        Query query = createQuery(sql);
        query.addParameter("siteid", siteId);
        query.addParameter("colId", colId);
        return this.queryForEntities(query);
    }
	
	/**
	 * 查找最大排序id
	 * @return int
	 */
	public int findMaxOrderId(){
		String sql = "SELECT MAX(orderid) FROM " + Tables.CHANNEL_COLUMN;
		Query query = createQuery(sql);
		int orderId = NumberUtil.getInt(this.queryForInteger(query));
		return orderId + 1;
	}
	
	/**
	 * 根据导航id查找导航栏目关系
	 * @param channelId Integer
	 * @return List<ChannelCol>
	 */
	public List<ChannelCol> findByChannelId(Integer channelId){
		String sql = getEntitySql() + " WHERE channelid=:channelid";
		Query query = createQuery(sql);
		query.addParameter("channelid", channelId);
		List<ChannelCol> channelCols = this.queryForEntities(query);
		return channelCols;
	}
	
	/**
	 * 根据导航id和网站id删除
	 * @param channelId 导航id	
	 * @param siteId	网站id
	 * @return boolean
	 */
	public boolean deleteByChannelIidAndSiteId(Integer channelId, Integer siteId){
		String sql = " DELETE FROM " + Tables.CHANNEL_COLUMN 
		           + " WHERE channelid = :channelid AND siteid = :siteid";
		Query query = createQuery(sql);
		query.addParameter("channelid", channelId);
		query.addParameter("siteid", siteId);
		return this.delete(query);
	}
	
	/**
	 * 根据导航ids和网站id删除
	 * @param channelIds 导航id	
	 * @param siteId	网站id
	 * @return boolean
	 */
	public boolean deleteByChannelIidsAndSiteId(List<Integer> channelIds, Integer siteId){
		String sql = " DELETE FROM " + Tables.CHANNEL_COLUMN 
		           + " WHERE channelid in (:channelid) AND siteid = :siteid";
		Query query = createQuery(sql);
		query.addParameter("channelid", channelIds);
		query.addParameter("siteid", siteId);
		return this.delete(query);
	}
	
	/**
	 * 通过栏目ID串删除导航栏目对应关系
	 * @param colIds
	 *            栏目ID串 如:1,2,3
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean deleteByColIds(List<Integer> colIds) {
		String sql = "DELETE FROM " + Tables.CHANNEL_COLUMN + " WHERE colid IN (:colIds)";
		Query query = createQuery(sql);
		query.addParameter("colIds", colIds);
		return this.delete(query);
	}
	
	/**
	 * 根据网站id和导航id获取该导航选择过的栏目id
	 * @param siteId 网站id
	 * @param iid 导航id
	 * @return List 网站栏目对应关系list
	 */
	public List<ChannelCol> findCheckedSiteIds(Integer siteId, Integer iid){
		String sql = " SELECT DISTINCT colid FROM " 
			       + Tables.CHANNEL_COLUMN + " WHERE siteid=:siteid AND channelid=:iid";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("iid", iid);
		return this.queryForEntities(query);
	}
	
	/**
	 * 通过导航id查询
	 * @param siteId Integer
	 * @param channelId Integer
	 * @return List<ChannelCol>
	 */
	public List<ChannelCol> findByChannelId(Integer siteId, Integer channelId){
		String sql = getSql() + " WHERE siteid=:siteid AND channelid=:channelid "
		+ " ORDER BY orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("channelid", channelId);
		return this.queryForEntities(query);
	}
	
	/**
	 * 更新导航id的orderid
	 * @param orderid
	 *            排序id
	 * @param iid
	 *            信息id
	 * @return true/false
	 */
	public boolean updateOrderIdById(Integer orderid, int iid) {
		UpdateSql sql = new UpdateSql(Tables.CHANNEL_COLUMN);
		sql.addInt("orderid", orderid);
		sql.setWhere("iid = :iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
	}
	
	/**
	 * 根据栏目Id查询导航
	 * @param colId Integer
	 * @return Integer
	 */
	public Integer findChannelColOrderId(Integer colId) {
		String sql = "SELECT orderid FROM " + Tables.CHANNEL_COLUMN + " WHERE colid=:colid";
		Query query = createQuery(sql);
		query.addParameter("colid", colId);
		return super.queryForInteger(query);
	}
	
}