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
import com.hanweb.jmp.constant.InterfaceLogConfig;
import com.hanweb.jmp.sys.entity.log.InterfaceLog;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.log.InterfaceLogService;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping("manager/interfacelog")
public class OprInterfaceLogController {
	
	/**
	 * interfaceLogService
	 */
	@Autowired
	private InterfaceLogService interfaceLogService;

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
	public JsonResult remove(String ids) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		Integer siteId = UserSessionInfo.getCurrentUser().getSiteId();
		try {
			isSuccess = interfaceLogService.removeByIds(ids, siteId);
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
	 * 日志统计图页面
	 * @param iid iid
	 * @return    设定参数 .
	 */
	@RequestMapping("chart")
	public ModelAndView showChart(Integer iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/sys/log/interfacelog_chart");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		if (currentUser.isSysAdmin()) {
			modelAndView.addObject("iscurrentUser", true);
			if(iid == null){
				iid = 0;
			}
			List<Site> siteList = siteService.findAll();
			Map<Integer, String> siteName = new HashMap<Integer, String>(); 
			for(Site site : siteList){
				siteName.put(site.getIid(), site.getName());
			}
			modelAndView.addObject("name_map", siteName);
			//客户端类型统计
			List<InterfaceLog> list = null;
			if(iid != 0){
				list = interfaceLogService.findClientType(iid);
			}		
			Map<Integer, Integer> map1 = new HashMap<Integer, Integer>(); 
			if(list != null){
				for(int i = 0; i < list.size(); i++){
					map1.put(list.get(i).getClientType(), list.get(i).getAmount());	
			    }
			}
			for(int i = 1; i < 5; i++){
				if(map1.containsKey(i)){
					modelAndView.addObject("clienttype"+i, map1.get(i));
				}else{
					modelAndView.addObject("clienttype"+i, 0);
				}	
			}
			Map<Integer, Integer> countMap = interfaceLogService.findInterfaceCountsBySiteId(iid);
			if(countMap != null){
				for(int i = 1; i < 6; i++){
					modelAndView.addObject("count"+i, countMap.get(i));
			    }
			}
			List<InterfaceLog> modList = interfaceLogService.findCountsOfInterface(iid);
			Map<String, Integer> map3 = new HashMap<String, Integer>(); 
			if(modList != null){
				for(int i = 0; i < modList.size(); i++){
					map3.put(modList.get(i).getInterfaceName(), modList.get(i).getNum());	
			    }
			}
			String[][] nameList = InterfaceLogConfig.MOD_ARRAY;
			for(int j = 0; j < nameList.length; j++){
				if(map3.containsKey(nameList[j][1])){
					modelAndView.addObject("counts"+j, map3.get(nameList[j][1]));
				}else{
					modelAndView.addObject("counts"+j, 0);
				}	
			}
			modelAndView.addObject("iid", iid);
		}else{
			modelAndView.addObject("iscurrentUser", false);
			int siteId = UserSessionInfo.getCurrentUser().getSiteId();
			List<InterfaceLog> list = interfaceLogService.findClientType(siteId);
			Map<Integer, Integer> map1 = new HashMap<Integer, Integer>(); 
			for(int i = 0; i < list.size(); i++){
				map1.put(list.get(i).getClientType(), list.get(i).getAmount());	
		    }
			for(int i = 1; i < 5; i++){
				if(map1.containsKey(i)){
					modelAndView.addObject("clienttype"+i, map1.get(i));
				}else{
					modelAndView.addObject("clienttype"+i, 0);
				}	
			}
			Map<Integer, Integer> countMap 
				= interfaceLogService.findInterfaceCountsBySiteId(siteId);
			for(int i = 1; i < 6; i++){
				modelAndView.addObject("count"+i, countMap.get(i));
		    }
			List<InterfaceLog> modList = interfaceLogService.findCountsOfInterface(siteId);
			Map<String, Integer> map3 = new HashMap<String, Integer>(); 
			for(int i = 0; i < modList.size(); i++){
				map3.put(modList.get(i).getInterfaceName(), modList.get(i).getNum());	
		    }
			String[][] nameList = InterfaceLogConfig.MOD_ARRAY;
			for(int j = 0; j < nameList.length; j++){
				if(map3.containsKey(nameList[j][1])){
					modelAndView.addObject("counts"+j, map3.get(nameList[j][1]));
				}else{
					modelAndView.addObject("counts"+j, 0);
				}	
			}
			modelAndView.addObject("iid", siteId);
		}
		return modelAndView;
	}

}