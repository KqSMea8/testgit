package com.hanweb.jmp.apps.dao.survey;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.jmp.apps.entity.survey.UserVoteCount;


public class UserVoteCountDAO extends BaseJdbcDAO<Integer, UserVoteCount>{
	
	/**
	 * 根据网站id和调查id获取实体
	 * @param siteId
	 * @param surveyId
	 * @param uuid
	 * @return
	 */
	public UserVoteCount findBySiteIdAndSurveyId(int siteId, int surveyId, String uuid){
		String sql = this.getEntitySql() + " WHERE siteid=:siteId AND surveyid=:surveyId "
		           + " AND uuid=:uuid";
		Query query = createQuery(sql);
		query.addParameter("siteId",siteId);
		query.addParameter("surveyId", surveyId);
		query.addParameter("uuid", uuid);
		return this.queryForEntity(query);
	}
	
}
