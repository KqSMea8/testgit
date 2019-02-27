package com.hanweb.jmp.cms.dao.infos;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO; 
import com.hanweb.common.basedao.Query;  
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.NumberUtil;

import com.hanweb.jmp.cms.entity.infos.InfoOperate;
import com.hanweb.jmp.constant.Tables;

public class InfoOperDAO extends BaseJdbcDAO<Integer, InfoOperate> { 

	/**
	 * 通过ID获取实体
	 * 
	 * @param iid
	 *            ID
	 * @return 实体
	 */
	public InfoOperate findByIid(Integer iid) {
		String sql = getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		InfoOperate infoOperEn = this.queryForEntity(query);
		return infoOperEn;
	}
	
	/**
	 * 查询栏目下信息
	 * @param siteId int
	 * @param colId int
	 * @return List<InfoOperate>
	 */
	public List<InfoOperate> findOfflineByCateId(int siteId, int colId) {
		String sql = getEntitySql() + " WHERE siteid = :siteId AND colid = :colid "
		           + " AND isoffline=0 ORDER BY iid DESC";
		Query query =  createQuery(sql);
		query.addParameter("siteId", siteId);
		query.addParameter("colid", colId);
		return queryForEntities(query);
	}

	/**
	 * 查询栏目下信息
	 * @param siteId int
	 * @param colId int
	 * @return List<InfoOperate>
	 */
	public List<InfoOperate> findJsearchByCateId(int siteId, int colId) {
		String sql = getEntitySql() + " WHERE siteid = :siteId AND colid = :colid AND issearch=0 ORDER BY iid ASC";
		Query query =  createQuery(sql);
		query.addParameter("siteId", siteId);
		query.addParameter("colid", colId);
		return queryForEntities(query);
	}
	
	/**
	 * 查询未返回到索引库的栏目的信息数量
	 * @param siteId int
	 * @param colId int
	 * @return int
	 */
	public int findCountJsearchByCateId(int siteId, int colId) {
		String sql = "SELECT COUNT(1) FROM "+Tables.INFO_OPERATE+" WHERE siteid = :siteId " +
				" AND colid = :colid AND issearch=0";
		Query query =  createQuery(sql);
		query.addParameter("siteId", siteId);
		query.addParameter("colid", colId);
		return NumberUtil.getInt(queryForInteger(query));
	}
	
	/**
	 * 修改打包标记
	 * @param siteId int
	 * @param colId int
	 * @param maxId int
	 * @return boolean
	 */
	public boolean updateIsOfflineByMaxId(int siteId, int colId, int maxId) {
		UpdateSql sql = new UpdateSql(Tables.INFO_OPERATE);
		sql.setWhere(" siteid=:siteid AND colid=:colid AND infoid<:infoid");
		sql.addWhereParamInt("siteid", siteId);
		sql.addWhereParamInt("colid", colId);
		sql.addWhereParamInt("infoid", maxId);
		sql.addInt("isoffline", 1);
		return update(sql);
	}


	/**
	 * 修改索引库状态
	 * @param siteId int
	 * @param colId int
	 * @param iid List<Integer>
	 * @return boolean
	 */
	public boolean updateIsJsearchByMaxId(int siteId, int colId, List<Integer> iid) {
		UpdateSql sql = new UpdateSql(Tables.INFO_OPERATE);
		sql.addInt("issearch", 1);
		sql.setWhere(" siteid=:siteid AND colid=:colid AND iid IN(:iid)");
		sql.addWhereParamInt("siteid", siteId);
		sql.addWhereParamInt("colid", colId);
		sql.addWhereParamList("iid", iid);
		return update(sql);
	}
	
	/**
	 * 根据信息ID删除记录
	 * @param ids List<Integer>
	 * @return boolean
	 */
	public boolean deleteByInfoIds(List<Integer> ids){
		String sql = "DELETE FROM " + Tables.INFO_OPERATE + " WHERE infoid IN (:ids)";
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		return delete(query);
	}
	
}