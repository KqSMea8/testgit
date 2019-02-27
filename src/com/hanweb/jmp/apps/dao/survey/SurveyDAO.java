package com.hanweb.jmp.apps.dao.survey;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;

import com.hanweb.jmp.apps.entity.survey.Survey;
import com.hanweb.jmp.constant.Tables;

/**
 * 调查
 * @author wanghuahua
 */
public class SurveyDAO extends BaseJdbcDAO<Integer, Survey>{
	
	/**
	 *  通过调查ID获取调查实体
	 * @param iid int 
	 * @return  调查实体
	 */
	public Survey findByIid(int iid) {
		String sql = this.getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		Survey survey = this.queryForEntity(query);
		return survey;	
	} 
	
	/**
	 * 根据网站id和调查名称查询调查实体
	 * @param siteId 网站id
	 * @param name 调查名称
	 * @return 调查实体
	 */
	public Survey findByName(Integer siteId, String name){
		String sql = this.getEntitySql() + " WHERE siteid=:siteid AND name=:name";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("name", name);
		return this.queryForEntity(query);
	}
	
	/**
	 * 获取网站下面的所有调查
	 * @param siteId  网站Id
	 * @return  调查集合
	 */
	public List<Survey> findAll(Integer siteId){
		String sql = this.getEntitySql() + " WHERE siteid=:siteid order by orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		return this.queryForEntities(query);
	}
	
	/**
	 * 根据栏目ID查询最小ORDERID
	 * @param colId     栏目ID
	 * @param tableName     tableName
	 * @return           最大ORDERID
	 */
	public int findMinOrderId(int siteId) {
		int orderId = 0;
		String sql = "SELECT MIN(orderid) FROM " + Tables.SURVEY + " WHERE siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		orderId = this.queryForInteger(query); 
		return orderId;
	}
	
	/**
	 * 更新信息id的orderid
	 * @param orderid
	 *            排序id
	 * @param iid
	 *            信息id
	 * @param tableName
	 *            tableName
	 * @return true/false
	 */
	public boolean updateOrderIdById(Integer orderid, int iid) {
		UpdateSql sql = new UpdateSql(Tables.SURVEY);
		sql.addInt("orderid", orderid);
		sql.setWhere("iid = :iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
	}
	
	/**
	 * 获得指定调查ID串的集合
	 * @param idsList idsList
	 * @param siteId siteId
	 * @return    设定参数 .
	*/
	public List<Survey> findByIds(List<Integer> idsList, Integer siteId) {
		String sql =this.getEntitySql() + " WHERE iid IN (:idsList) AND siteid=:siteId";	 
		Query query = createQuery(sql);
		query.addParameter("siteId", siteId);
		query.addParameter("idsList", idsList);
		return this.queryForEntities(query);
	}

}