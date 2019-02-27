package com.hanweb.jmp.interfaces.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;

import com.hanweb.jmp.util.CacheUtil; 
import com.hanweb.jmp.annotation.InterfaceCache;
import com.hanweb.jmp.cms.service.channels.ChannelService;
import com.hanweb.jmp.constant.StaticValues;

@Controller
@RequestMapping("interfaces")
public class ChannelController {
	
	/**
	 * channelService
	 */
	@Autowired
	ChannelService channelService;

	/**
	 * 首页接口（接口1.10）
	 * @param siteid 网站ID
	 * @return json
	 */
	@RequestMapping("first")
	@ResponseBody 
	@InterfaceCache
	public String cates(Integer siteid){
        String key = "first_" + NumberUtil.getInt(siteid);
		String jsonString = StringUtil.getString(CacheUtil.getValue(StaticValues.CACHE_REGION, key));
		if(StringUtil.isNotEmpty(jsonString)){ 
			return jsonString;
		}else{
			jsonString = channelService.findCatesJson(siteid);
			if(jsonString!=null && jsonString.indexOf("channels")>-1){
				CacheUtil.setValue(StaticValues.CACHE_REGION, key, jsonString);
			} 
		} 
		return jsonString;
	}
	
}