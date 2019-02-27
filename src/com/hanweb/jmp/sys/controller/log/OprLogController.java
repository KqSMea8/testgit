package com.hanweb.jmp.sys.controller.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState; 
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.log.LogService;
import com.hanweb.jmp.sys.service.sites.SiteService;

import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping("manager/log")
public class OprLogController {
	
	/**
	 * logService
	 */
	@Autowired
	private LogService logService;
	
	/**
	 * siteService
	 */
	@Autowired
	private SiteService siteService;

	/**
	 * 删除
	 * @param ids ids
	 * @return JsonResult
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids){
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = logService.removeByIds(ids);
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

	/**
	 * 清空日志
	 * @return JsonResult
	 */
	@RequestMapping("clean")
	@ResponseBody
	public JsonResult clean() {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = logService.clean();
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

	/**
	 * 日志设置页面
	 * @return ModelAndView
	 */
	@RequestMapping("reset_show")
	public ModelAndView showReset() {
		ModelAndView modelAndView = new ModelAndView("jmp/sys/log/log_opr");
		List<LogConfigFormBean> beanList = logService.findLogConfig();
		modelAndView.addObject("beanList", beanList);
		modelAndView.addObject("url", "reset_submit.do");
		return modelAndView;
	}
	 
	/**
	 * 展示统计图
	 * @param iid iid
	 * @return    设定参数 .
	*/
	@RequestMapping("chart")
	public ModelAndView showChart(Integer iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/sys/log/log_chart");	
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		if (currentUser.isSysAdmin()) {
			modelAndView.addObject("iscurrentUser", true);
			if(iid == null){
				iid = -1;
			}
			List<Site> siteList = siteService.findAll();
			Map<Integer, String> siteName = new HashMap<Integer, String>(); 
			for(Site site : siteList){
				siteName.put(site.getIid(), site.getName());
			}
			modelAndView.addObject("name_map", siteName);
			for(int i = 1; i < 15; i++){
				if(i != 5 || i != 7 || i != 13){
					modelAndView.addObject("module"+i, logService.findCountByModuleId(i, iid));
				}
			}		
			for(int i = 1; i < 19; i++){
				modelAndView.addObject("func"+i, logService.findCountByFuncId(i, iid));
		    }
			modelAndView.addObject("iid", iid);
		}else{
			modelAndView.addObject("iscurrentUser", false);
			int siteId = UserSessionInfo.getCurrentUser().getSiteId();
			for(int i = 1; i < 15; i++){
				if(i != 5 || i != 7 || i != 13){
					modelAndView.addObject("module"+i, logService.findCountByModuleId(i, siteId));
				}
			}		
			for(int i = 1; i < 19; i++){
				modelAndView.addObject("func"+i, logService.findCountByFuncId(i, siteId));
		}
		}
		return modelAndView;
	}

	/**
	 * 日志设置
	 * @param modAndFuncId modAndFuncId
	 * @return JsonResult
	 */
	@RequestMapping("reset_submit")
	@ResponseBody
	public JsonResult submitReset(String modAndFuncId) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			logService.modifyIsLog();
			isSuccess = logService.modifyIsLog(modAndFuncId);
			if (isSuccess) {
				jsonResult.set(ResultState.MODIFY_SUCCESS);
			} else {
				jsonResult.set(ResultState.MODIFY_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;  
	}
	
}