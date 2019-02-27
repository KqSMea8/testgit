package com.hanweb.jmp.sys.dao.version;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.util.NumberUtil;

import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.entity.version.Version;

/**
 * 客户端升级DAO
 * @author WangFei
 */
public class VersionDAO extends BaseJdbcDAO<Integer, Version> {

	/**
	 * 查询客户端最新版本的信息
	 * @param siteId Integer
	 * @param clientType Integer
	 * @return List<Version>
	 */
	public List<Version> findNewVersionClient(Integer siteId, Integer clientType) {
		String sql = getEntitySql() + " WHERE siteid=:siteid AND clienttype=:clienttype";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("clienttype", clientType);
		return this.queryForEntities(query);
	}
	
	/**
	 * 查找是否有重复记录
	 * @param iid Integer
	 * @param clientType Integer
	 * @param version String
	 * @param siteId Integer
	 * @return    设定参数 .
	*/
	public int findNumOfSameName(Integer iid, Integer clientType, String version, Integer siteId) {
		int num = 0;
		String sql = " SELECT COUNT(iid) FROM " + Tables.UPDATE + " WHERE siteid=:siteId " 
				   + " AND clienttype=:clientType AND version=:version";
		if (NumberUtil.getInt(iid) > 0) {
			sql += " AND iid NOT IN(:iid)";
		} 
		Query query = createQuery(sql);
		if (NumberUtil.getInt(iid) > 0) {
			query.addParameter("iid", iid);
		}
		query.addParameter("siteId", siteId);
		query.addParameter("clientType", clientType);
		query.addParameter("version", version); 
		num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 根据网站id和版本类型查找客户端
	 * @param siteId int
	 * @param clientType int
	 * @param version String
	 * @return  Version
	 */
	public Version findByClientAndVersion(int siteId, int clientType, String version){
		String sql = this.getEntitySql() + " WHERE siteid=:siteid AND clienttype=:clienttype "
		           + " AND version=:version";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("clienttype", clientType);
		query.addParameter("version", version);
		return this.queryForEntity(query);
	}
	
	/**
	 * 根据应用id和版本类型查询客户端
	 * @param appId appId
	 * @param clientType clientType
	 * @return List<Client> 
	 */
	public List<Version> findByAppIdAndClientType(Integer appId, Integer clientType){
		String sql = this.getEntitySql() 
		           + " WHERE appid=:appid AND clienttype=:clienttype  ORDER BY iid DESC";
		Query query = createQuery(sql);
		query.addParameter("appid", appId);
		query.addParameter("clienttype", clientType);
		List<Version>  updateList= this.queryForEntities(query);
		return updateList;
	}
	
}