package com.hanweb.jmp.apps.controller.survey;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.support.controller.CurrentUser;

import com.hanweb.jmp.apps.entity.survey.Survey;
import com.hanweb.jmp.apps.service.survey.SurveyService;
import com.hanweb.jmp.cms.service.infos.InfoService;
import com.hanweb.jmp.constant.Configs;

@Controller
@RequestMapping("manager/plugins/survey")
public class OprSurveyController {
	
	/**
	 * surveyService
	 */
	@Autowired
	SurveyService surveyService;
	
	/**
	 * infoService
	 */
	@Autowired
	InfoService infoService;
	
	/**
	 * 新增
	 * @return   ModelAndView
	 */
	@RequestMapping(value = "add_show")
	public ModelAndView showAdd() {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/survey/survey_opr");
		Survey survey = new Survey();
		modelAndView.addObject("url", "add_submit.do");
		modelAndView.addObject("PicFileType", Configs.getConfigs().getPicFileType());
		modelAndView.addObject("survey", survey);
		return modelAndView;
	}
	
	/**
	 * 插入数据库
	 * @param route  route
	 * @return  JsonResult
	 */
	@RequestMapping(value = "add_submit")
	@ResponseBody
	public String submitAdd(SurveyFormBean survey) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		survey.setSiteId(currentUser.getSiteId());
		survey.setCreateTime(new Date());
		boolean isSuccess = false;
		String message = "";
		Script script = Script.getInstanceWithJsLib();
		try {
			isSuccess = surveyService.add(survey);
		} catch (OperationException e) {
			message = e.getMessage();
		}
		if (isSuccess) {
			script.refreshNode("survey");
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
	 * @return  JsonResult
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		JsonResult jsonResult = JsonResult.getInstance();
		int siteId = currentUser.getSiteId();
		boolean isSuccess = false;
		try {
			isSuccess = surveyService.removeByIds(ids, siteId);	
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
		ModelAndView modelAndView = new ModelAndView("jmp/apps/survey/survey_opr");
		Survey survey = surveyService.findByIid(iid);
		// 截止时间需要转换
		modelAndView.addObject("endtime", DateUtil.dateToString(survey.getEndTime(),
				DateUtil.YYYY_MM_DD_HH_MM_SS));
		modelAndView.addObject("createTime", DateUtil.dateToString(survey.getCreateTime(),
				DateUtil.YYYY_MM_DD_HH_MM_SS));
		modelAndView.addObject("url", "modify_submit.do");
		modelAndView.addObject("PicFileType", Configs.getConfigs().getPicFileType());
		modelAndView.addObject("survey", survey);
		return modelAndView;
	}

	/**
	 * 修改数据库操作
	 * @param route route
	 * @return  JsonResult
	 */
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public String submitModify(SurveyFormBean survey) {
		boolean isSuccess = false;
		String message = "";
		Script script = Script.getInstanceWithJsLib();
		try {
			isSuccess = surveyService.modify(survey);
			
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
	public ModelAndView order() {
		ModelAndView modelAndView =new ModelAndView("jmp/apps/survey/survey_sort");
		int siteId = UserSessionInfo.getCurrentUser().getSiteId(); 
		List<Survey> surveyList = surveyService.findAll(siteId);
		modelAndView.addObject("url", "sort_submit.do");
		modelAndView.addObject("surveyList", surveyList);
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
			isSuccess = surveyService.modifyOrderIdById(ids, orderids);
			if (isSuccess) {
				jsonResult.set(ResultState.OPR_SUCCESS);
				jsonResult.addParam("refresh", "survey");
			} else {
				jsonResult.set(ResultState.OPR_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;   
	}

}