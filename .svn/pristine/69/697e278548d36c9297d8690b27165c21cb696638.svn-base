package com.hanweb.jmp.cms.dao.channels;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;

import com.hanweb.jmp.cms.entity.channels.Channel;
import com.hanweb.jmp.constant.Tables;

public class ChannelDAO extends BaseJdbcDAO<Integer, Channel>{
	
	/**
	 * 查找最大排序id
	 * @return orderid
	 */
	public int findMaxOrderId(){
		String sql = "SELECT MAX(orderid) FROM " + Tables.CHANNEL;
		Query query = createQuery(sql);
		int orderId = NumberUtil.getInt(this.queryForInteger(query));
		return orderId + 1;
	}
	
	/**
	 * 更新导航首图地址
	 * @param channel Channel
	 */
	public void updatePic(Channel channel){
		UpdateSql updateSql = new UpdateSql(Tables.CHANNEL);
		updateSql.addString("firstpic", channel.getFirstPic());
		updateSql.addString("compoundpic", channel.getCompoundPic());
		updateSql.setWhere("iid=:iid");
		updateSql.addWhereParamInt("iid", channel.getIid());
		super.update(updateSql);
	}
	
	/**
	 * 根据id查导航实体
	 * @param iid Integer
	 * @return Channel
	 */
	public Channel findByIid(Integer iid){
		String sql = " SELECT iid, name, firstpic, type, siteid, orderid, compoundpic, isvisit" 
			       + " , flag,channeltype FROM " + Tables.CHANNEL + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		Channel channel = this.queryForEntity(query);
		return channel;
	}
	
	/**
	 * 导航删除
	 * @param iids 多个导航id 
	 * @param siteId 网站id
	 * @return boolean
	 * @throws OperationException
	 */
	public boolean deleteByIidsAndSiteId(List<Integer> iids, Integer siteId){
		String sql = " DELETE FROM " + Tables.CHANNEL 
		           + " WHERE iid in (:iids) AND siteid = :siteid";
		Query query = createQuery(sql);
		query.addParameter("iids", iids);
		query.addParameter("siteid", siteId);
		return this.delete(query);
	}
	
	/**
	 * 查找所有导航
	 * @param siteId Integer
	 * @return List<Channel>
	 */
	public List<Channel> findAll(Integer siteId){
		String sql = getEntitySql() 
			       + " WHERE siteid =:siteid AND state=1 ORDER BY orderid ASC, iid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
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
		UpdateSql sql = new UpdateSql(Tables.CHANNEL);
		sql.addInt("orderid", orderid);
		sql.setWhere("iid = :iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
	}
	
	/**
	 * 根据导航名称、导航id和网站id查找 导航数目
	 * @param id Integer
	 * @param name String
	 * @param siteId Integer
	 * @return int
	 */
	public int findNumByName(Integer id, String name, Integer siteId){
		String sql = " SELECT COUNT(1) FROM " + Tables.CHANNEL + " WHERE name=:name" 
		           + " AND iid <> :iid AND siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("iid", id);
		query.addParameter("name", name);
		query.addParameter("siteid", siteId);
		Integer num = this.queryForInteger(query);
		return NumberUtil.getInt(num);
	}
	
	/**
	 * 根据栏目id串和网站id删除导航
	 * @param colIds
	 *             栏目id串
	 * @param siteId
	 *             网站Id
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean deleteByColIds(List<Integer> colIds, Integer siteId) {
		String sql = " DELETE FROM " + Tables.CHANNEL + " WHERE iid IN (SELECT channelid FROM "
		           + Tables.CHANNEL_COLUMN + "  WHERE colid IN (:colIds)) AND siteid = :siteId AND type=1";
		Query query = createQuery(sql);
		query.addParameter("colIds", colIds);
		query.addParameter("siteId", siteId);
		return this.delete(query);
	}
	
	/**
	 * 查询
	 * @param channel Channel
	 * @param orderType int
	 * @return List<Channel>
	 */
	public List<Channel> find(Channel channel, int orderType) {
		String sql = getEntitySql();
		if(channel != null && channel.getIid() >0){
			sql += " AND iid=:iid";
		}
		if(channel != null && StringUtil.isNotEmpty(channel.getName())){
			sql += " AND name=:name";
		}
		if(channel != null && channel.getSiteId() >0){
			sql += " AND siteid=:siteid";
		}
		if(orderType==0){
			sql +="";
		} else {
			if(orderType==2){
				sql +=" ORDER BY orderid DESC";
			} else {
				sql +=" ORDER BY orderid ASC";
			}
		}
		Query query = createQuery(sql);
		if(channel != null && channel.getIid() >0){
			query.addParameter("iid", channel.getIid());
		}
		if(channel != null && StringUtil.isNotEmpty(channel.getName())){
			query.addParameter("name", channel.getName());
		}
		if(channel != null && channel.getSiteId() >0){
			query.addParameter("siteid", channel.getSiteId());
		}
		return super.queryForEntities(query);
	}
	
	/**
	 * 通过iid查找导航
	 * @param channelIds String
	 * @return List<Channel>
	 */
	public List<Channel> findByIids(String channelIds) {
		String sql = getEntitySql() + " WHERE iid IN (:channelIds)";
		Query query = createQuery(sql);
		query.addParameter("channelIds", channelIds);
		return super.queryForEntities(query);
	}
	
	/**
	 * 通过网站id查找该网站下导航数目
	 * @param siteId
	 *            网站id
	 * @return 数目
	 */
	public int findChannelNumBySiteId(int siteId) {
		String sql = "SELECT COUNT(1) FROM " + Tables.CHANNEL + " WHERE siteid = :siteid";
		Query query = this.createQuery(sql);
		query.addParameter("siteid", siteId);
		int num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 导航下栏目增删改排序启用停用，标记位+1
	 * @param id 导航id
	 * @return boolean
	 */
	public boolean updateFlag(int id){
		String sql = "UPDATE " + Tables.CHANNEL + " SET flag=flag+1 WHERE iid = :iid";
		Query query = this.createQuery(sql);
		query.addParameter("iid", id);
		execute(query);
		return true;
	}
	
	/**
	 * 导航启用停用
	 * @param ids 导航id
	 * @param state 0=停用1=启用
	 * @return boolean
	 */
	public boolean updateState(List<Integer> ids, int state){
		String sql = "UPDATE " + Tables.CHANNEL + " SET state=:state WHERE iid IN (:ids)";
		Query query = this.createQuery(sql);
		query.addParameter("ids", ids);
		query.addParameter("state", state);
		execute(query);
		return true;
	}
	
}