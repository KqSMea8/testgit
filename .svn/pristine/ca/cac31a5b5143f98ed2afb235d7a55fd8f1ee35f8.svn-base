package com.hanweb.jmp.apps.service.survey;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.complat.exception.OperationException;

import com.hanweb.jmp.apps.dao.survey.SurveyAnswerDAO;
import com.hanweb.jmp.apps.entity.survey.SurveyAnswer;

public class SurveyAnswerService {
	
	@Autowired
	SurveyAnswerDAO surveyAnswerDAO;
	
	/**
	 * 调查问答题答案新增
	 * @param route boolean
	 * @return boolean
	 * @throws OperationException 设定参数.
	 */
	public boolean add(SurveyAnswer surveyAnswer){
		boolean isSuccess = false;
		if(surveyAnswer == null){
			return isSuccess;
		}
		Integer iid = surveyAnswerDAO.insert(surveyAnswer);
		return iid > 0;
	}
	
}