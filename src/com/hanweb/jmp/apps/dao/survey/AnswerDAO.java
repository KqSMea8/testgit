package com.hanweb.jmp.apps.dao.survey;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;

import com.hanweb.jmp.apps.entity.survey.Answer;
import com.hanweb.jmp.constant.Tables;

/**
 * 调查问题答案
 * @author wanghuahua
 *
 */
public class AnswerDAO extends BaseJdbcDAO<Integer, Answer>{
	
	/**
	 * 通过iid查找答案实体
	 * @param iid
	 * @return
	 */
	public Answer findByIid(int iid){
		String sql = this.getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		return this.queryForEntity(query);
	}
	
	/**
	 * 根据网站id和调查答案名称查询调查答案实体
	 * @param siteId 网站id
	 * @param name 调查名称
	 * @return 调查答案实体
	 */
	public Answer findByName(Integer siteId, String name){
		String sql = getEntitySql() + " WHERE siteid=:siteid AND name=:name";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("name", name);
		return this.queryForEntity(query);
	}
	
	/**
	 * 获取网站下面的所有调查答案
	 * @param siteId  网站Id
	 * @return  调查答案集合
	 */
	public List<Answer> findAll(Integer siteId){
		String sql = getEntitySql() + " WHERE siteid=:siteid";
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
		String sql = "SELECT MIN(orderid) FROM " + Tables.ANWSER + " WHERE siteid=:siteid";
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
		UpdateSql sql = new UpdateSql(Tables.ANWSER);
		sql.addInt("orderid", orderid);
		sql.setWhere("iid = :iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
	}
	
	/**
	 * 根据问题Id查找答案
	 * @param siteId         网站Id
	 * @param qusetionId     问题Id
	 * @return
	 */
	public List<Answer> findByQuestionId(Integer siteId, Integer questionId){
		String sql = getEntitySql() + " WHERE siteid=:siteid AND questionid=:questionId";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("questionId", questionId);
		return this.queryForEntities(query);
	}
	
	/**
	 * 通过调查Id查找答案
	 * @param siteId        网站Id
	 * @param SurveyId      调查Id
	 * @return
	 */
	public List<Answer> findBySurveyId(Integer siteId, Integer SurveyId){
		String sql = getEntitySql() + " WHERE siteid=:siteid AND Surveyid=:SurveyId";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("SurveyId", SurveyId);
		return this.queryForEntities(query);
	}
	
	/**
	 * 根据网站id和id串查找集合
	 * @param siteId
	 * @param answerIds
	 * @return
	 */
	public List<Answer> findByIds(Integer siteId, String questionIds){
		String sql = getEntitySql() + " WHERE siteid=:siteid AND questionid in (:questionIds)";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("questionIds", questionIds);
		return this.queryForEntities(query);
	}
	
	/**
	 * 根据id串修改投票数
	 * @param idsList
	 * @return
	 */
	public boolean modifyCount(List<Integer> idsList){
		String sql = "UPDATE "+Tables.ANWSER+" SET count=count+1 WHERE  iid IN (:idsList)";
		Query query = createQuery(sql);
		query.addParameter("idsList", idsList);
		return super.execute(query)>0;
	}
	
}