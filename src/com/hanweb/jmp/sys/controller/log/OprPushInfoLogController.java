package com.hanweb.jmp.sys.controller.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.log.PushInfoLogService;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping("manager/pushlog")
public class OprPushInfoLogController {
	
	/**
	 * pushInfoLogService
	 */
	@Autowired
	private PushInfoLogService pushInfoLogService;
	
	/**
	 * pushInfoLogService
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
	public JsonResult remove(String ids) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		isSuccess = pushInfoLogService.remove(ids);
		if (isSuccess) {
			jsonResult.set(ResultState.REMOVE_SUCCESS);
		} else {
			jsonResult.set(ResultState.REMOVE_FAIL);
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
		isSuccess = pushInfoLogService.clean();
		if (isSuccess) {
			jsonResult.set(ResultState.REMOVE_SUCCESS);
		} else {
			jsonResult.set(ResultState.REMOVE_FAIL);
		}
		return jsonResult;  
	}
	 
	/**
	 * 日志图像页面
	 * @param iid iid
	 * @param pushyear pushyear
	 * @return    设定参数 .
	*/
	@RequestMapping("chart")
	public ModelAndView showChart(Integer iid, String pushyear) {
		ModelAndView modelAndView = new ModelAndView("jmp/sys/log/pushlog_chart");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		List<Integer> yearList = new ArrayList<Integer>();
		for(int i = 0; i < 5; i++){
			int year = DateUtil.getToYear();
			yearList.add(year-i);
		}
		modelAndView.addObject("yearList", yearList);
		if (currentUser.isSysAdmin()) {
			modelAndView.addObject("currentUser", true);
			if(pushyear == null){
				pushyear = StringUtil.getString(DateUtil.getToYear());
			}
			List<Site> siteList = siteService.findAll();
			Map<Integer, String> siteName = new HashMap<Integer, String>(); 
			for(Site site : siteList){
				siteName.put(site.getIid(), site.getName());
			}
			modelAndView.addObject("name_map", siteName);
			if(iid == null){
				iid = siteList.get(0).getIid();
				modelAndView.addObject("iid", siteList.get(0).getIid());
			}else{
				modelAndView.addObject("iid", iid);
			}
			Map<Integer, Integer> map = pushInfoLogService.findCountBySiteIdAndYear(pushyear, iid);
			for(int i = 1; i< 13; i++){
				modelAndView.addObject("count"+i, map.get(i));
			}
			modelAndView.addObject("pushyear", pushyear);
			Map<Integer, Integer> outUserMap = pushInfoLogService.
				findOutUserBySiteIdAndYear(pushyear, iid);
			for(int i = 1; i< 13; i++){
				modelAndView.addObject("outuser"+i, outUserMap.get(i));
			}
			Map<Integer, Integer> pushTimeMap = pushInfoLogService.
				findPushTimeBySiteIdAndYear(pushyear, iid);
			for(int i = 1; i< 6; i++){
				modelAndView.addObject("pushtime"+i, pushTimeMap.get(i));
			}
		}else{
			modelAndView.addObject("currentUser", false);
			if(pushyear == null){
				pushyear = StringUtil.getString(DateUtil.getToYear());
			}
			int siteId = UserSessionInfo.getCurrentUser().getSiteId();
			modelAndView.addObject("iid", siteId);
			Map<Integer, Integer> map = pushInfoLogService.findCountBySiteIdAndYear(
						pushyear, siteId);
			for(int i = 1; i< 13; i++){
				modelAndView.addObject("count"+i, map.get(i));
			}
			Map<Integer, Integer> outUserMap = pushInfoLogService
				.findOutUserBySiteIdAndYear(pushyear, siteId);
			for(int i = 1; i< 13; i++){
				modelAndView.addObject("outuser"+i, outUserMap.get(i));
			}
			Map<Integer, Integer> pushTimeMap = pushInfoLogService
				.findPushTimeBySiteIdAndYear(pushyear, siteId);
			for(int i = 1; i< 6; i++){
				modelAndView.addObject("pushtime"+i, pushTimeMap.get(i));
			}
			modelAndView.addObject("pushyear", pushyear);
		}
		return modelAndView;
	}
	
}