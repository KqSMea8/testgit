package com.hanweb.jmp.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;

import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.pack.backstage.entity.Application;
import com.hanweb.jmp.pack.backstage.service.ApplicationService;

@Controller
@RequestMapping("interfaces")
public class ApplicationController {
	
	@Autowired
	private ApplicationService applicationService;

	@RequestMapping("applist")
	@ResponseBody
	public String showApplications(String siteid, String clienttype, String version, String uuid, 
			                       String loginid, String type){
		Map<String, Object> ret = new HashMap<String, Object>();
		List<Application> appList = applicationService.findBySiteId(NumberUtil.getInt(siteid));
		List<HashMap<String, Object>> apps = new ArrayList<HashMap<String, Object>>();
		int isOpen = 0;
		if(appList!=null){
			for(Application app : appList){
				isOpen = NumberUtil.getInt(app.getIsOpen());
				if(isOpen==0){
					continue;
				}
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("resourceid", StringUtil.getString(app.getIid()));
				map.put("resourcename", StringUtil.getString(app.getName()));
				map.put("cateimgurl", StringUtil.getString(Configs.getConfigs().getJmpUrl()+app.getLogoPath()));
				String interfaceurl = "";
				String domain = "";
				interfaceurl = StringUtil.getString(app.getUrl());
				domain = StringUtil.getString(app.getDomain());
				if(StringUtil.isNotEmpty(domain)){
					map.put("loadurl", interfaceurl+"&jactUrl="+domain);
				}else{
					map.put("loadurl", interfaceurl);
				}
				map.put("type", StringUtil.getString(app.getType()));
				map.put("hudongtype", StringUtil.getString(app.getKind()));
				apps.add(map);
			}
			ret.put("resource", apps);
		
		}
		return JsonUtil.objectToString(ret);
	}
	
}