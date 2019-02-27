package com.hanweb.jmp.apps.service.survey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.apps.dao.survey.SurveyResultDAO;
import com.hanweb.jmp.apps.entity.survey.SurveyResult;

public class SurveyResultService {
	
	@Autowired
	SurveyResultDAO surveyResultDAO;
	
	/**
	 * 根据iid获取实体
	 * @param iid
	 * @param siteId
	 * @return
	 */
	public SurveyResult findByIid(Integer iid, Integer siteId){
		return surveyResultDAO.findByIid(iid, siteId);
	}
	
	/**
	 * 检查用户是同一个问题啊是否重复提交
	 * @param uuid
	 * @param surveyId
	 * @param questionId
	 * @return
	 */
	public boolean checkRepeatSubmit(String uuid, Integer surveyId, Integer questionId){
		boolean noRepeat = true;
		if(StringUtil.isEmpty(uuid)||NumberUtil.getInt(surveyId)==0||NumberUtil.getInt(questionId)==0){
			return false;
		}
		int num = surveyResultDAO.findSubmitNumByUUid(surveyId, questionId, uuid);
		if(num!=0){
			return false;
		}
		return noRepeat;
	}

	/**
	 * 新增
	 * @param surveyResult
	 * @return
	 */
	public boolean addSurveyResult(SurveyResult surveyResult){
		boolean isSuccess = false;
		int iid = surveyResultDAO.insert(surveyResult);
		if(iid > 0){
			isSuccess = true;
		}
		return isSuccess ; 
	}
	
	
	/**
	 * 根据siteId,surveyId,questionId查找对象
	 * @param surveyResult
	 * @return
	 */
	public SurveyResult findByQuestionId(Integer siteId, Integer surveyId, Integer questionId){
		return surveyResultDAO.findByQuestionId(siteId, surveyId, questionId);
	}
	
	/**
	 * 修改
	 * @param surveyResult
	 * @return
	 */
	public boolean updateSurveyResult(SurveyResult surveyResult){
		return surveyResultDAO.update(surveyResult) ; 
	}
	
	/**
	 * 通过ID串删除调查
	 * @param ids ids
	 * @return boolean
	 * @throws OperationException 设定参数.
	 */
	public boolean removeByIds(String ids, int siteId) throws OperationException{
		List<Integer> idsLsit = StringUtil.toIntegerList(ids, ",");
		boolean isSuccess = false;
		//删除调查
		isSuccess = surveyResultDAO.deleteByIds(idsLsit);
		if(!isSuccess) {
			throw new OperationException("删除调查结果失败!");
		}
		return isSuccess;
	}
	
}