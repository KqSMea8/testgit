package com.hanweb.jmp.apps.service.survey;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.apps.dao.survey.QuestionDAO;
import com.hanweb.jmp.apps.entity.survey.Question;
import com.hanweb.jmp.apps.entity.survey.SurveyResult;

public class QuestionService {
	
	/**
	 * questionDAO
	 */
	@Autowired
	private QuestionDAO  questionDAO;
	
	/**
	 * answerService
	 */
	@Autowired
	private AnswerService answerService;
	
	/**
	 * surveyResultService
	 */
	@Autowired
	private SurveyResultService surveyResultService;
	
	/**
	 * 调查新增
	 * @param route boolean
	 * @return boolean
	 * @throws OperationException 设定参数.
	 */
	public boolean add(Question question) throws OperationException{
		boolean isSuccess = false;
		if(question == null){
			return isSuccess;
		}
		int orderId = questionDAO.findMinOrderId(question.getSiteId()) -1;
		question.setOrderId(orderId);
		Integer iid = questionDAO.insert(question);
		return iid > 0;
	}
	
	/**
	 * 通过ID串删除调查
	 * @param ids ids
	 * @return boolean
	 * @throws OperationException 设定参数.
	 */
	public boolean removeByIds(String ids, Integer surveyId) throws OperationException{
		List<Integer> idsLsit = StringUtil.toIntegerList(ids, ",");
		int siteId = UserSessionInfo.getCurrentUser().getSiteId();
		boolean isSuccess = false;
		if(!CollectionUtils.isEmpty(answerService.findAnswersByIds(siteId, ids))){
			throw new OperationException("删除调查问题失败，请先删除调查问题下的答案!");
		}
		String surveyResultIds = "";
		SurveyResult surveyResult = new SurveyResult();
		for (int i = 0, len = idsLsit.size(); i < len; i++) {
			Integer iid = idsLsit.get(i);
			//获取该调查下面的所有调查结果
			surveyResult = surveyResultService.findByQuestionId(siteId, surveyId, iid);
			if(surveyResult != null){
				surveyResultIds += "," + surveyResult.getIid();
			}
		}
		//删除调查结果
		if(StringUtil.isNotEmpty(surveyResultIds)){
			isSuccess = surveyResultService.removeByIds(surveyResultIds.substring(1), siteId);
			if (!isSuccess) {
				throw new OperationException("删除调查结果失败!");
			}
		}
		//删除调查
		isSuccess = questionDAO.deleteByIds(idsLsit);
		if (!isSuccess) {
			throw new OperationException("删除调查问题失败!");
		}
		return isSuccess;
	}
	
	/**
	 * 根据网站id和调查名称查询调查实体
	 * @param siteId 网站id
	 * @param name 调查名称
	 * @return 调查实体
	 */
	public List<Question> findBySurveyId(Integer siteId, Integer surveyId){
		return questionDAO.findBySurveyId(siteId, surveyId); 
	}
	
	/**
	 *  通过调查ID获取调查实体
	 * @param iid iid
	 * @return  调查实体
	 */
	public Question findByIid(int iid){
		return questionDAO.findByIid(iid);
	} 
	
	/**
	 * 获取所有调查
	 * @param siteId   网站Id
	 * @return  调查实体集合
	 */
	public List<Question> findAll(Integer siteId){
		return questionDAO.findAll(siteId);
	}
	
	/**
	 * 修改调查
	 * @param route iid
	 * @return boolean
	 * @throws OperationException 设定参数.
	 */
	public boolean modify(Question question) throws OperationException{
		boolean isSuccess = false;
		if(question == null){
			return false;
		}
		isSuccess = questionDAO.update(question);
		return isSuccess;
	}
	
	/**
	 * 更新信息id的orderid
	 * @param ids ids
	 * @param orderids orderids
	 * @param colid colid
	 * @return boolean boolean
	 * @throws OperationException    设定参数 .
	 */
	public boolean modifyOrderIdById(String ids, String orderids)throws OperationException {
		if (ids == null || ids.length() == 0 || orderids == null || orderids.length() == 0) {
			return false;
		}
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		List<Integer> ordersList = StringUtil.toIntegerList(orderids, ",");
		boolean isSuccess = false;
		for (int i = 0, len = idsList.size(); i < len; i++) {
			isSuccess = questionDAO.updateOrderIdById(ordersList.get(i), idsList.get(i));
		}
		return isSuccess;
	}

}