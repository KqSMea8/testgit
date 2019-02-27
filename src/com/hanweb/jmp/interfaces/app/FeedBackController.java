/*
 * @(#)FeedBackController.java	
 *
 * Copyright 1997-2008  Hanweb CO.,LTD. All rights reserved.
 * HANWEB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.hanweb.jmp.interfaces.app; 

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody; 

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;

import com.hanweb.jmp.constant.InterfaceLogConfig;
import com.hanweb.jmp.global.entity.jsonentity.FeedBackInfo;
import com.hanweb.jmp.sys.entity.feedback.FeedBack;
import com.hanweb.jmp.sys.service.feedback.FeedBackService;

@Controller
@RequestMapping("interfaces/feedback")
public class FeedBackController {

	/**
	 * feedService
	 */
	@Autowired
	private FeedBackService feedService;
	
	/**
	 * 提交接口.
	 * @param siteid siteid
	 * @param clienttype clienttype
	 * @param uuid uuid
	 * @param version version
	 * @param loginname loginname
	 * @param content content
	 * @param contact contact
	 * @return    设定参数 .
	*/
	@RequestMapping(value = "uploadfeed", method = RequestMethod.POST)
	@ResponseBody
	public String uploadBroke(String siteid, String clienttype, String uuid, String version, 
			                  String loginname, String content, String contact) { 
		String result="";
		try {
			// 判断必填参数是否正确
			if (NumberUtil.getInt(siteid)<=0 || NumberUtil.getInt(clienttype)<=0) { 
				result=InterfaceLogConfig.interfaceResult(false, 
						InterfaceLogConfig.MOD_FEEDBACK, InterfaceLogConfig.ERROR_03);
				return result;
			} 

			// 反馈内容判断
			if (StringUtil.isEmpty(content) || content.length() > 600) { 
				result=InterfaceLogConfig.interfaceResult(false, 
						InterfaceLogConfig.MOD_FEEDBACK, InterfaceLogConfig.ERROR_03);
				return result;
			}
			// 反馈联系方式判断
			if (contact.length() > 255) { 
				result=InterfaceLogConfig.interfaceResult(false, 
						InterfaceLogConfig.MOD_FEEDBACK, InterfaceLogConfig.ERROR_03);
				return result;
			}
			// 插入数据库
			FeedBack feedBack = new FeedBack();
			feedBack.setClientType(NumberUtil.getInt(clienttype));
			feedBack.setSiteId(NumberUtil.getInt(siteid));
			feedBack.setCreateTime(new Date());
			feedBack.setDeviceCode(uuid);
			feedBack.setContent(StringUtil.getString(content));
			feedBack.setLoginName(StringUtil.getString(loginname));
			feedBack.setIsRead(0);
			feedBack.setContact(StringUtil.getString(contact));
			Integer iid = feedService.addFeedBack(feedBack);
			if (iid>0) { 
				result=InterfaceLogConfig.interfaceResult(true, 
						InterfaceLogConfig.MOD_FEEDBACK, InterfaceLogConfig.ERROR_10);
				return result;
				
			} else {
				result=InterfaceLogConfig.interfaceResult(false, 
						InterfaceLogConfig.MOD_FEEDBACK, InterfaceLogConfig.ERROR_11);
				return result;
			}
		} catch (Exception e) {
			result=InterfaceLogConfig.interfaceResult(false, 
					InterfaceLogConfig.MOD_FEEDBACK, InterfaceLogConfig.ERROR_11);
			return result;
		}
	}
	
	/**
	 * 获取信息列表接口.
	 * @param siteid 网站id
	 * @param clienttype 客户端类型
	 * @param uuid 手机唯一码
	 * @param version 版本号
	 * @param sendtime sendtime
	 * @param page page
	 * @param type 列表操作1=刷新  2=更多
	 * @param loginname loginname
	 * @return    设定参数 .
	*/
	@RequestMapping(value = "list")
	@ResponseBody
	public String getFeedBackList(Integer siteid, Integer clienttype, String uuid, String version, 
			                      String sendtime, Integer page, Integer type, String loginname) {
		Map<String, Object> result = new HashMap<String, Object>();
		siteid = NumberUtil.getInt(siteid); 
		Date date = null;
		Long time = NumberUtil.getLong(sendtime);
		if(time != null){
			date = new Date();
			date.setTime(NumberUtil.getLong(time));
		}
		page = NumberUtil.getInt(page);
		if(NumberUtil.getInt(siteid) == 0){ 
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_FEEDBACK, InterfaceLogConfig.ERROR_03);
		}
		String deviceCode = uuid;
		List<FeedBackInfo> list = feedService.findInfoList(siteid, page, date, type, deviceCode, loginname);
		result.put("infolist", list);
		return JsonUtil.objectToString(result);
	}
	
}