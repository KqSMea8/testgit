package com.hanweb.jmp.apps.dao.survey;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.util.NumberUtil;

import com.hanweb.jmp.apps.entity.survey.SurveyResult;
import com.hanweb.jmp.constant.Tables;

public class SurveyResultDAO extends BaseJdbcDAO<Integer, SurveyResult>{
	
	/**
	 * 根据iid获取实体
	 * @param iid
	 * @param siteId
	 * @return
	 */
	public SurveyResult findByIid(Integer iid, Integer siteId){
		String sql = this.getEntitySql() + " WHERE iid=:iid AND siteid=:siteId";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		query.addParameter("siteId", siteId);
		return this.queryForEntity(query);
	}
	
	/**
	 * 根据uuid查找答题记录个数
	 * @param surveyId
	 * @param questionId
	 * @param uuid
	 * @return
	 */
	public int findSubmitNumByUUid(Integer surveyId, Integer questionId, String uuid){
		String sql = " SELECT COUNT(1) FROM " + Tables.SURVEYRESULT + " WHERE surveyid=:surveyid AND"
		           + " questionid=:questionId AND uuid=:uuid ";
		Query query = createQuery(sql);
		query.addParameter("surveyid", surveyId);
		query.addParameter("questionId", questionId);
		query.addParameter("uuid", uuid);
		Integer num = this.queryForInteger(query);
		return NumberUtil.getInt(num);
	}
	
	public SurveyResult findByQuestionId(Integer siteId, Integer surveyId, Integer questionId){
		String sql = this.getEntitySql() + " WHERE siteId=:siteId AND surveyId=:surveyId "
		           + " AND questionId=:questionId";
		Query query = createQuery(sql);
		query.addParameter("siteId", siteId);
		query.addParameter("surveyId", surveyId);
		query.addParameter("questionId", questionId);
		return this.queryForEntity(query);
	}
	
}