package com.hanweb.jmp.apps.controller.survey;

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
import com.hanweb.support.controller.CurrentUser;

import com.hanweb.jmp.apps.entity.survey.Answer;
import com.hanweb.jmp.apps.service.survey.AnswerService;
import com.hanweb.jmp.constant.Configs;


@Controller
@RequestMapping("manager/plugins/survey/answer")
public class OprAnswerController {
	
	/**
	 * answerService
	 */
	@Autowired
	AnswerService answerService;
	
	/**
	 * 新增
	 * @return   ModelAndView
	 */
	@RequestMapping(value = "add_show")
	public ModelAndView showAdd(String questionId, String surveyId) {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/survey/answer_opr");
		Answer answer = new Answer();
		answer.setQuestionId(NumberUtil.getInt(questionId));
		answer.setSurveyId(NumberUtil.getInt(surveyId));
		modelAndView.addObject("url", "add_submit.do");
		modelAndView.addObject("PicFileType", Configs.getConfigs().getPicFileType());
		modelAndView.addObject("answer", answer);
		return modelAndView;
	}
	
	/**
	 * 插入数据库
	 * @param route  route
	 * @return  JsonResult
	 */
	@RequestMapping(value = "add_submit")
	@ResponseBody
	public String submitAdd(AnswerFromBean answer) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		answer.setSiteId(currentUser.getSiteId());
		boolean isSuccess = false;
		String message = "";
		Script script = Script.getInstanceWithJsLib();
		try {
			isSuccess = answerService.add(answer);
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
	public JsonResult remove(String ids) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			isSuccess = answerService.removeByIds(ids, currentUser.getSiteId());	
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
		ModelAndView modelAndView = new ModelAndView("jmp/apps/survey/answer_opr");
		Answer answer = answerService.findByIid(iid);
		modelAndView.addObject("url", "modify_submit.do");
		modelAndView.addObject("PicFileType", Configs.getConfigs().getPicFileType());
		modelAndView.addObject("answer", answer);
		return modelAndView;
	}

	/**
	 * 修改数据库操作
	 * @param route route
	 * @return  JsonResult
	 */
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public String submitModify(AnswerFromBean answer) {
		boolean isSuccess = false;
		String message = "";
		Script script = Script.getInstanceWithJsLib();
		try {
			isSuccess = answerService.modify(answer);
			
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
	
}