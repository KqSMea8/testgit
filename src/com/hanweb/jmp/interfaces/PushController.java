package com.hanweb.jmp.interfaces;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.jmp.global.service.XgPushService;

@Controller
@RequestMapping("interfaces")
public class PushController {
	
	/**
	 * xgPushService
	 */
	@Autowired
	XgPushService xgPushService;

	/**
	 * pushAll
	 * @param appid appid
	 * @param appSecret appSecret
	 * @param id id
	 * @param yurl yurl
	 * @param title title
	 * @param content content
	 * @return    设定参数 .
	*/
	@RequestMapping("pushAll")
	@ResponseBody
	public String pushAll(Long appid, String appSecret, Integer id, String yurl, String title, String content){
		HashMap<String, Object> ret = new HashMap<String, Object>();
		if(appid == null || StringUtil.isEmpty(appSecret) || StringUtil.isEmpty(content)){
			ret.put("result", "false");
			ret.put("errorcode", "0903");
			ret.put("errormsg", "参数错误！");
		}else{
			boolean bl = xgPushService.androidAllPush(appid, appSecret, id, yurl, title, content);
			bl = bl && xgPushService.iosAllPush(appid, appSecret, id, yurl, content);
			if(bl){
				ret.put("result", "true");
			}else{
				ret.put("result", "false");
				ret.put("errorcode", "0908");
				ret.put("errormsg", "推送失败！");
			}
		}
		return JsonUtil.objectToString(ret);
	}
	
}