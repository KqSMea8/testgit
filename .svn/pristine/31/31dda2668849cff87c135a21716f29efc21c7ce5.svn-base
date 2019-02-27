package com.hanweb.jmp.pack.dao; 

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;

import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.pack.entity.Client;

public class ClientDAO  extends BaseJdbcDAO<Integer, Client> {
	
	/**
	 * 根据iid获取版本实体
	 * @param iid iid
	 * @return    设定参数 .
	 */
	public Client findByNameAndPwd(Integer iid){
		String sql = this.getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		Client client = this.queryForEntity(query);
		return client;
	}

	/**
	 * 根据应用id和版本类型查询客户端
	 * @param appId appId
	 * @param clientType clientType
	 * @return List<Client> 
	 */
	public List<Client> findByAppIdAndClientType(Integer appId, Integer clientType){
		String sql = this.getEntitySql() + " WHERE appid=:appid AND clienttype=:clienttype  ORDER BY IID DESC";
		Query query = createQuery(sql);
		query.addParameter("appid", appId);
		query.addParameter("clienttype", clientType);
		List<Client>  clientList= this.queryForEntities(query);
		return clientList;
	}
	
	
	/**
	 * 根据版本号，客户端类型，网站id查询版本
	 * @param version version
	 * @param clientType clientType
	 * @param siteId siteId
	 * @return Client
	 */
	public Client findByVersionAndClientType(String version, Integer clientType, Integer siteId){
		String sql = this.getEntitySql() + " WHERE version=:version AND clienttype=:clientType AND siteid=:siteId";
		Query query = createQuery(sql);
		query.addParameter("version", version);
		query.addParameter("clientType", clientType);
		query.addParameter("siteId", siteId);
		Client client = this.queryForEntity(query);
		return client;
	}
	
	/**
	 * 通过应用ID串获取应用
	 * @param appid
	 *            应用ID串 如:1,2,3
	 * @return 应用实体集合
	 */
	public List<Client> findByIds(Integer appid) {
		String sql = this.getEntitySql() + " WHERE appid=:appid";
		Query query = createQuery(sql);
		query.addParameter("appid", appid);
		List<Client> clientList = queryForEntities(query);
		return clientList;
	}
	 
	/**
	 * 修改信息审核状态位
	 * @param version version
	 * @param siteId siteId
	 * @param clientType clientType
	 * @return    设定参数 .
	 */
	public boolean updateStateByVSC(String version, Integer siteId, Integer clientType) {
		String sql = " UPDATE " + Tables.CLIENT + " SET status = 1"
		           + " WHERE version=:version AND siteid=:siteId AND clienttype=:clientType"; 
		Query query = createQuery(sql);
		query.addParameter("version", version);
		query.addParameter("siteId", siteId);
		query.addParameter("clientType", clientType);
		int row = super.execute(query);
		return row > 0;
	}
	
	/**
	 * 通过主键id查找实体
	 * @param iid  主键id
	 * @return Client
	 */
	public Client findByIid(Integer iid){
		String sql = this.getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		return this.queryForEntity(query);
	}
	
	/**
     * 修改信息审核状态位
	 * @param ids 信息ID串 如:1,2,3
	 * @param isaudit 审核状态位
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean updateStateByIds(List<Integer> ids, int isaudit) {
		String sql = " UPDATE " + Tables.CLIENT + " SET isaudit = " + isaudit
		           + " WHERE iid IN (:ids)"; 
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		int row = super.execute(query); 
		return row > 0; 
	}
	
	/**
	 * 根据版本号，客户端类型，网站id查询版本
	 * @param clientType clientType
	 * @param siteId siteId
	 * @return Client
	 */
	public List<Client> findBySiteidAndClientType(Integer clientType, Integer siteId){
		String sql = this.getEntitySql() + " WHERE clienttype=:clientType AND siteid=:siteId ORDER BY IID DESC";
		Query query = createQuery(sql); 
		query.addParameter("clientType", clientType);
		query.addParameter("siteId", siteId);
		List<Client>  clientList= this.queryForEntities(query);
		return clientList;
	}
	
}