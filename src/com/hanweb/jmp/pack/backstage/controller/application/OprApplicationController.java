package com.hanweb.jmp.pack.backstage.controller.application;

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
import com.hanweb.jmp.pack.backstage.entity.Application;
import com.hanweb.jmp.pack.backstage.service.ApplicationService;
import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping("manager/application")
public class OprApplicationController {
	
	@Autowired
	private ApplicationService applicationService;

	
	@RequestMapping("add_show")
	public ModelAndView showAdd() {
		ModelAndView modelAndView = new ModelAndView("jmp/pack/backstage/application/application_opr");
	
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		
		Application application = new Application();
		application.setSiteId(siteId);
		String createTime = DateUtil.dateToString(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS);
		modelAndView.addObject("createTime", createTime);
		modelAndView.addObject("url", "add_submit.do");
		modelAndView.addObject("application", application);
		return modelAndView;
	}
	
	@RequestMapping("add_submit")
	@ResponseBody
	public String submitAdd(ApplicationFormBean application) {
		String message = "";
		Script script = Script.getInstanceWithJsLib();
		boolean isSuccess = false;
		try {
			application.setCreateTime(new Date());
			isSuccess = applicationService.add(application);
			if (isSuccess) {
				script.addScript("parent.refreshParentWindow();parent.closeDialog();");
			} else {
				script.addAlert("新增失败！" + message);
			}
		} catch (OperationException  e) {
			script.addAlert(e.getMessage());
		}
		
		
		 return script.getScript(); 
	}
	
	@RequestMapping("remove")
	@ResponseBody
	public JsonResult remove(String ids) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			isSuccess = applicationService.removeByIds(ids);
			
			if (isSuccess) {
				jsonResult.set(ResultState.REMOVE_SUCCESS);

			} else {
				jsonResult.set(ResultState.REMOVE_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult; 
	}
	@RequestMapping("modify_show")
	public ModelAndView showModify(Integer iid) {
		
		ModelAndView modelAndView = new ModelAndView("jmp/pack/backstage/application/application_opr");
		Application application = applicationService.findByIid(iid);
	
		modelAndView.addObject("url", "modify_submit.do");
		modelAndView.addObject("application", application);
		return modelAndView;
	
	}
	
	@RequestMapping("modify_submit")
	@ResponseBody
	public String modifySubmit(ApplicationFormBean application) {
		String message = "";
		Script script = Script.getInstanceWithJsLib();
		boolean isSuccess = false;
		
		try {
			isSuccess = applicationService.modify(application);
			if (isSuccess) {
				script.addScript("parent.refreshParentWindow();parent.closeDialog();");
			} else {
				script.addAlert("修改失败！" + message);
			}
		} catch (OperationException e) {
			script.addAlert(e.getMessage());
		}
		
		return  script.getScript(); 
	
	}
	
	
	/**
	 * order
	 * @param mid   mid
	 * @param colId colId
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "sort_show")
	@ResponseBody
	public ModelAndView order() {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		ModelAndView modelAndView = new ModelAndView("jmp/pack/backstage/application/application_sort");
		 
		List<Application> applicationList = applicationService.findBySiteId(siteId);
		modelAndView.addObject("url", "sort_submit.do");
		modelAndView.addObject("applicationList", applicationList);
		return modelAndView;
	}
	/**
	 * submitSort
	 * @param ids       ids
	 * @param orderids  orderids
	 * @param mid  mid
	 * @return  JsonResult
	 */
	@RequestMapping(value = "sort_submit")
	@ResponseBody
	public JsonResult submitSort(String ids, String orderids, Integer mid) {
		boolean isSuccess = false; 
		JsonResult jsonResult = JsonResult.getInstance();  
		try {
			isSuccess = applicationService.modifyOrderIdById(ids, orderids);
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
