package com.hanweb.jmp.sys.dao.sites;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.NumberUtil;

import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.entity.sites.Site;

public class SiteDAO extends BaseJdbcDAO<Integer, Site> {

	/**
	 * 网站删除时需要删除的表
	 */
	private static final String[] TABLES = { "jmp_sitedetail", "jmp_sitesplash", "jmp_channel", 
		"jmp_channel_column", "jmp_rolepushtype", "jmp_feedback", "jmp_broketype", "jmp_broke", 
		"jmp_log", "complat_group", "complat_user", "complat_role", "complat_rolerelation", 
		"complat_roleright", "jmp_autoapp_application", "jmp_autoapp_client", "jmp_colfield", "jmp_field"};

	/**
	 * 通过信息ID获取网站实
	 * @param iid
	 *            信息ID
	 * @return 信息实体
	 */
	public Site findByIid(Integer iid) {
		String sql = getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		Site site = this.queryForEntity(query);
		return site;
	}

	/**
	 * 通过信息ID串获取网站实体
	 * @param iids String
	 * @return List<Site>
	 */
	public List<Site> findByIids(String iids) {
		String sql = getEntitySql() + " WHERE iid IN(:iids) ORDER BY FIELD(iid, :iids)";
		Query query = createQuery(sql);
		query.addParameter("iids", iids);
		return this.queryForEntities(query);
	}

	/**
	 * 通过网站名称查找网站数
	 * @param iid 
	 *            网站id
	 * @param name
	 *            网站名称
	 * @return int
	 */
	public int findNumByName(int iid, String name) {
		String sql = "SELECT COUNT(1) FROM " + Tables.SITE + " WHERE name=:name AND iid <> :iid";
		Query query = createQuery(sql);
		query.addParameter("name", name);
		query.addParameter("iid", iid);
		Integer num = this.queryForInteger(query);
		return NumberUtil.getInt(num);
	}

	/**
	 * 根据网站id，删除网站相关的表记录
	 * @param id int
	 * @return boolean 是否删除失败
	 */
	public boolean deleteAll(int id) {
		boolean iSsuccess = true;
		iSsuccess = this.deleteById(id);
		// 网站相关表的删除
		Query query;
		String sql;
		for (int i = 0; i < TABLES.length && iSsuccess; i++) {
			sql = "DELETE FROM " + TABLES[i] + " WHERE siteid = :siteid";
			query = createQuery(sql);
			query.addParameter("siteid", id);
			iSsuccess = this.delete(query);
		}
		return iSsuccess;
	}

	/**
	 * 更新网站url和用户id
	 * @param site Site
	 * @return boolean
	 */
	public boolean updateUrlAndUserId(Site site) {
		UpdateSql updateSql = new UpdateSql(Tables.SITE);
		updateSql.addString("url", site.getUrl());
		updateSql.addInt("userid", site.getUserId());
		updateSql.setWhere("iid=" + site.getIid());
		return super.update(updateSql);
	}

	/**
	 * 更新网站名称和颜色
	 * @param site Site
	 * @return boolean
	 */
	public boolean updateNameAndColor(Site site) {
		UpdateSql updateSql = new UpdateSql(Tables.SITE);
		updateSql.addString("name", site.getName());
		updateSql.addString("color", site.getColor());
		updateSql.setWhere("iid=" + site.getIid());
		return super.update(updateSql);
	}

	/**
	 * 修改是否订阅其他网站
	 * @param isSubscribeOther Integer
	 * @param iid Integer
	 * @return boolean
	 */
	public boolean updateSubscribeOther(Integer isSubscribeOther, Integer iid) {
		UpdateSql updateSql = new UpdateSql(Tables.SITE);
		updateSql.addInt("issubscribeother", isSubscribeOther);
		updateSql.setWhere("iid=:iid");
		updateSql.addWhereParamInt("iid", iid);
		return this.update(updateSql);
	}

	/**
	 * 查询所有信息
	 * @return List<Site>
	 */
	public List<Site> findAll() {
		Query query = this.createQuery(getEntitySql());
		return this.queryForEntities(query);
	}
	
	/**
	 * 根据issearch查询网站实体
	 * @return List<Site>
	 */
	public List<Site> findJsearchSite() {
		String sql = getEntitySql() + " WHERE issearch=1";
		Query query = this.createQuery(sql);
		return this.queryForEntities(query);
	}
	
	/**
	 * 根据是否支持离线下载来获取网站实体
	 * @return List<Site>
	 */
	public List<Site> findOfflineSite() {
		String sql = getEntitySql() + " WHERE isofflinezip=1";
		Query query = this.createQuery(sql);
		return this.queryForEntities(query);
	}

	/**
	 * 查找现有网站的数目
	 * @return Integer
	 */
	public Integer findSiteCount() {
		String sql = "SELECT COUNT(1) FROM " + Tables.SITE;
		Query query = this.createQuery(sql);
		int num = this.queryForInteger(query);
		return num;
	}
	
	/**
     * 修改网站支持jsearch状态
     * @param state int
     * @param siteId int
     * @return boolean
     */
	public boolean updateSearchState(int state, int siteId) {
		UpdateSql updateSql = new UpdateSql(Tables.SITE);
		updateSql.addInt("issearch", state);
		updateSql.setWhere("iid=:siteid AND appfrom=:appfrom");
		updateSql.addWhereParamInt("siteid", siteId);
		updateSql.addWhereParamInt("appfrom", 1);
		return super.update(updateSql);
	}
	
	/**
	 * 查询app来源
	 * @param iid Integer
	 * @return Integer
	 */
	public Integer findAppFrom(Integer iid) {
		String sql = "SELECT appfrom FROM " + Tables.SITE + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		return this.queryForInteger(query);
	}

	/**
	 * 通过iid查询推送标记位
	 * @param iid Integer
	 * @return Integer
	 */
	public Integer findPushFlagById(Integer iid){
		String sql = "SELECT pushflag FROM " + Tables.SITE + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		return this.queryForInteger(query);
	}
	
	/**
	 * 推送标记位+1
	 * @param iid Integer
	 * @return boolean
	 */
	public boolean updatePushFlag(Integer iid){
		String sql = "UPDATE " + Tables.SITE + " SET pushflag=pushflag+1 WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		execute(query);
		return true;
	}
	
	/**
	 * 网站下频道增删改排序启用停用标记位+1
	 * @param iid Integer
	 * @return boolean
	 */
	public boolean updateChanFlag(Integer iid){
		String sql = "UPDATE " + Tables.SITE + " SET chanflag=chanflag+1 WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		execute(query);
		return true;
	}
	
	/**
	 * 网站下栏目增删改排序启用停用标记位+1
	 * @param iid Integer
	 * @return boolean
	 */
	public boolean updateColFlag(Integer iid){
		String sql = "UPDATE " + Tables.SITE + " SET colflag=colflag+1 WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		execute(query);
		return true;
	}
	
	/**
	 * 网站下订阅栏目分类增删改排序标记位+1
	 * @param iid Integer
	 * @return boolean
	 */
	public boolean updateSubscribeColSignFlag(Integer iid){
		String sql = " UPDATE " + Tables.SITE 
			       + " SET bookcoldimensionflag=bookcoldimensionflag+1 WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		execute(query);
		return true;
	}
	
	/**
	 * 网站下订阅栏目操作标记位+1
	 * @param iid Integer
	 * @return boolean
	 */
	public boolean updateSubscribeColFlag(Integer iid){
		String sql = "UPDATE " + Tables.SITE + " SET bookcolflag=bookcolflag+1 WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		execute(query);
		return true;
	}
	
	/**
	 * 网站下卡片维度操作标记位+1
	 * @param iid Integer
	 * @return  boolean
	 */
	public boolean updateCardSignFlag(Integer iid){
		String sql = " UPDATE " + Tables.SITE 
			       + " SET cardsignflag=cardsignflag+1 WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		execute(query);
		return true;
	}
	
	/**
	 * 修改是否订阅其他网站 
	 * @param isDiccount Integer
	 * @param iid Integer
	 * @return boolean
	 */
	public boolean updateDiscount(Integer isDiccount, Integer overall, Integer iid) {
		UpdateSql updateSql = new UpdateSql(Tables.SITE);
		updateSql.addInt("isdiscount", isDiccount);
		updateSql.addInt("overall", overall);
		updateSql.setWhere("iid=:iid");
		updateSql.addWhereParamInt("iid", iid);
		return this.update(updateSql);
	}
	
}