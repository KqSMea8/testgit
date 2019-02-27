package com.hanweb.jmp.apps.controller.survey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;

import com.hanweb.jmp.apps.entity.survey.Question;
import com.hanweb.jmp.apps.service.survey.QuestionService;
import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping("manager/plugins/survey/question")
public class OprQuestionController {
	
	/**
	 * questionService
	 */
	@Autowired
	QuestionService questionService;

	/**
	 * 新增
	 * @return   ModelAndView
	 */
	@RequestMapping(value = "add_show")
	public ModelAndView showAdd(String surveyId) {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/survey/question_opr");
		Question question = new Question();
		question.setSurveyId(NumberUtil.getInt(surveyId));
		modelAndView.addObject("url", "add_submit.do");
		modelAndView.addObject("question", question);
		return modelAndView;
	}
	
	/**
	 * 插入数据库
	 * @param route  route
	 * @return  JsonResult
	 */
	@RequestMapping(value = "add_submit")
	@ResponseBody
	public String submitAdd(Question question) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		question.setSiteId(currentUser.getSiteId());
		boolean isSuccess = false;
		String message = "";
		Script script = Script.getInstanceWithJsLib();
		try {
			isSuccess = questionService.add(question);
		} catch (OperationException e) {
			message = e.getMessage();
		}
		if (isSuccess) {
			script.addScript("parent.refreshParentWindow();parent.closeDialog();");
		} else {
			script.addAlert("新增失败！" + message);
		}
		return script.getScript();
	}
	
	/**
	 * 删除调查
	 * @param ids
	 *           应用ID串，如1,2,3,4
	 * 
	 * @return  JsonResult
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids, Integer surveyId) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			isSuccess = questionService.removeByIds(ids, surveyId);	
			if (isSuccess) {
				jsonResult.set(ResultState.REMOVE_SUCCESS);
				jsonResult.addParam("remove", ids);
			} else {
				jsonResult.set(ResultState.REMOVE_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;
	}
	
	/**
	 * 修改页面
	 * @param iid  iid
	 * @return   ModelAndView
	 */
	@RequestMapping(value = "modify_show")
	public ModelAndView showModify(Integer iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/survey/question_opr");
		Question question = questionService.findByIid(iid);
		modelAndView.addObject("url", "modify_submit.do");
		modelAndView.addObject("question", question);
		return modelAndView;
	}

	/**
	 * 修改数据库操作
	 * @param route route
	 * @return  JsonResult
	 */
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public String submitModify(Question question) {
		boolean isSuccess = false;
		String message = "";
		Script script = Script.getInstanceWithJsLib();
		try {
			isSuccess = questionService.modify(question);
			
		} catch (OperationException e) {
			message = e.getMessage();
		}
		if (isSuccess) {
			script.addScript("parent.refreshParentWindow();parent.closeDialog();");
		} else {
			script.addAlert("修改失败！" + message);
		}
		return script.getScript();
	}
	
	
	/**
	 * 排序页
	 * @param colId colId
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "sort_show")
	@ResponseBody
	public ModelAndView order(Integer surveyId) {
		ModelAndView modelAndView =new ModelAndView("jmp/apps/survey/question_sort");
		int siteId = UserSessionInfo.getCurrentUser().getSiteId(); 
		List<Question> questionList = questionService.findBySurveyId(siteId, surveyId);
		modelAndView.addObject("url", "sort_submit.do");
		modelAndView.addObject("questionList", questionList);
		return modelAndView;
	}
	
	/**
	 * 排序提交
	 * @param ids ids
	 * @param orderids orderids
	 * @param colid colid
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "sort_submit")
	@ResponseBody
	public JsonResult submitSort(String ids, String orderids) {
		boolean isSuccess = false; 
		JsonResult jsonResult = JsonResult.getInstance();  
		try {
			isSuccess = questionService.modifyOrderIdById(ids, orderids);
			if (isSuccess) {
				jsonResult.set(ResultState.OPR_SUCCESS); 
			} else {
				jsonResult.set(ResultState.OPR_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;   
	}

}