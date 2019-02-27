package com.hanweb.jmp.apps.dao.survey;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;

import com.hanweb.jmp.apps.entity.survey.Question;
import com.hanweb.jmp.constant.Tables;

/**
 * 调查问题
 * @author wanghuahua
 */
public class QuestionDAO extends BaseJdbcDAO<Integer, Question>{
	
	/**
	 * 通过iid查找问题实体
	 * @param iid
	 * @return
	 */
	public Question findByIid(int iid){
		String sql = this.getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		return this.queryForEntity(query);
	}
	
	/**
	 * 根据网站id和调查名称查询调查问题实体
	 * @param siteId 网站id
	 * @param name 调查名称
	 * @return 调查实体
	 */
	public List<Question> findBySurveyId(Integer siteId, Integer surveyId){
		String sql = getEntitySql() + " WHERE siteid=:siteid AND surveyid=:surveyId ORDER BY orderid DESC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("surveyId", surveyId);
		return this.queryForEntities(query);
	}
	
	/**
	 * 获取网站下面的所有调查问题
	 * @param siteId  网站Id
	 * @return  调查集合
	 */
	public List<Question> findAll(Integer siteId){
		String sql = this.getEntitySql() + " WHERE siteid=:siteid";
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
		String sql = "SELECT MIN(orderid) FROM " + Tables.QUESTION + " WHERE siteid=:siteid";
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
		UpdateSql sql = new UpdateSql(Tables.QUESTION);
		sql.addInt("orderid", orderid);
		sql.setWhere("iid = :iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
	}

}