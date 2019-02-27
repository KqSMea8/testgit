package com.hanweb.jmp.global.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.newspush.userdevice.entity.UserDevice;
import com.hanweb.jmp.sys.entity.parameter.Parameter;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.parameter.ParameterService;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.jmp.tencent.xinge.ClickAction;
import com.hanweb.jmp.tencent.xinge.Message;
import com.hanweb.jmp.tencent.xinge.MessageIOS;
import com.hanweb.jmp.tencent.xinge.Style;
import com.hanweb.jmp.tencent.xinge.XingeApp;
import com.hanweb.jmp.util.StrUtil;

public class XgPushService {
	
	/**
	 * siteService
	 */
	@Autowired
	SiteService siteService;
	
	/**
	 * parameterService
	 */
	@Autowired
	ParameterService parameterService;
	
	/**
	 * strUtil
	 */
	private StrUtil strUtil = new StrUtil();
	
	/**
	 * logger
	 */
	private final Log logger = LogFactory.getLog(getClass()); 
	 
	/**
	 * getClient:获得推送client
	 * @param parameter parameter
	 * @param clientType clientType
	 * @return XingeApp
	 * @throws OperationException    设定参数 .
	 */
	private XingeApp getClient(Parameter parameter, int clientType)
		throws OperationException {
		try {
			long appid = 0l;
			String appSecret = "";
			if(clientType == 3){
				appSecret = parameter.getAndroidPushAppSecret();
				appid = NumberUtil.getLong(parameter.getAndroidPushAppKey());
			}else if(clientType == 2){
				appSecret = parameter.getIphonePushAppSecret();
				appid = NumberUtil.getLong(parameter.getIphonePushAppKey());
			}else if(clientType == 4){
				appSecret = parameter.getIpadPushAppSecret();
				appid = NumberUtil.getLong(parameter.getIpadPushAppKey());
			}
			XingeApp xg = new XingeApp(appid, appSecret);
			return xg;
		} catch (Exception e) {
			throw new OperationException("请检查您的推送参数配置！");
		}
	}
	 
	/**
	 * 下发安卓所有设备
	 * @param info 下发安卓所有设备
	 * @return    设定参数 .
	 */
	public boolean androidAllPush(Info info){
		JSONObject json = null;
		Parameter parameter = parameterService.findBySiteId(NumberUtil.getInt(info.getSiteId()));
		XingeApp androidXg;
		try {
			androidXg = getClient(parameter, 3);
		} catch (OperationException e) {
			logger.debug(e.getMessage());
			return false;
		}
		try {
			json = androidXg.pushAllDevice(XingeApp.DEVICE_ALL, findAndroidMessage(info));
			logger.debug(JSONObject.valueToString(json)); 
		    return (json.getInt("ret_code") == 0);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("推送处理服务端错误异常");
			return false;
		}	
	}
	 
	/**
	 * 下发安卓所有设备
	 * @param appid appid
	 * @param appSecret appSecret
	 * @param id id
	 * @param yurl yurl
	 * @param title title
	 * @param content content
	 * @return    设定参数 .
	 */
	public boolean androidAllPush(long appid, String appSecret, int id, String yurl, String title, String content){
		JSONObject json = null;
		
		try {
			XingeApp androidXg = new XingeApp(appid, appSecret);
			json = androidXg.pushAllDevice(XingeApp.DEVICE_ALL, findAndroidMessage(id, yurl, title, content));
			logger.debug(JSONObject.valueToString(json));
		} catch (Exception e) {
			logger.debug("推送处理服务端错误异常");
			return false;
		}
		return true;
	}
	 
	/**
	 * 下发ios所有设备
	 * @param info info
	 * @return    设定参数 .
	 */
	public Map<String, Boolean> iosAllPush(Info info){
		JSONObject json = null;
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		Parameter parameter = parameterService.findBySiteId(NumberUtil.getInt(info.getSiteId()));
		if(StringUtil.getString(parameter.getIphonePushAppKey()).equals(parameter.getIpadPushAppKey())
				&& StringUtil.getString(parameter.getIphonePushAppSecret()).equals(parameter.getIpadPushAppSecret())){
			XingeApp iosXg;
			try {
				iosXg = getClient(parameter, 2);
			} catch (OperationException e) {
				e.printStackTrace();
				map.put("iphone", false);
		    	map.put("ipad", false);
				logger.debug(e.getMessage());
				return map;
			}
			try {
				json = iosXg.pushAllDevice(XingeApp.DEVICE_ALL, findIosMessage(info), Configs.getConfigs().getIosEnvironment());
				if (json.getInt("ret_code") == 0) {
					map.put("iphone", true);
					map.put("ipad", true);
			    } else {
			    	map.put("iphone", false);
			    	map.put("ipad", false);
			    }
				logger.debug(JSONObject.valueToString(json));
				return map;
			} catch (Exception e) {
				e.printStackTrace();
				map.put("iphone", false);
		    	map.put("ipad", false);
				logger.debug("推送处理服务端错误异常");
				return map;
			}
		}else{
			//iphone和ipad的账号不一致，要推送2次
			XingeApp iphoneXg;
			XingeApp ipadXg;
			try {
				iphoneXg = getClient(parameter, 2);
				ipadXg = getClient(parameter, 4);
			} catch (OperationException e) {
				e.printStackTrace();
				logger.debug(e.getMessage());
				return map;
			}
			try {
				json = iphoneXg.pushAllDevice(XingeApp.DEVICE_ALL
						, findIosMessage(info), Configs.getConfigs().getIosEnvironment());
				if (json.getInt("ret_code") == 0) {
					map.put("iphone", true);
			    } else {
			    	map.put("iphone", false);
			    }
				logger.debug(JSONObject.valueToString(json));
				json = ipadXg.pushAllDevice(XingeApp.DEVICE_ALL, findIosMessage(info), Configs.getConfigs().getIosEnvironment());
				logger.debug(JSONObject.valueToString(json));
				if (json.getInt("ret_code") == 0) {
			    	map.put("ipad", true);
			    } else {
			    	map.put("ipad", false);
			    }
				return map;
			} catch (Exception e) {
				e.printStackTrace();
				map.put("iphone", false);
		    	map.put("ipad", false);
				logger.debug("推送处理服务端错误异常");
				return map;
			}
		}
	}
	 
	/**
	 * 下发ios所有设备
	 * @param appid appid
	 * @param appSecret appSecret
	 * @param id id
	 * @param yurl yurl
	 * @param content content
	 * @return    设定参数 .
	*/
	public boolean iosAllPush(long appid, String appSecret, int id, String yurl, String content){
		JSONObject json = null;
		
		try {
			XingeApp iosXg = new XingeApp(appid, appSecret);
			json = iosXg.pushAllDevice(XingeApp.DEVICE_ALL, findIosMessage(id, yurl, content), Configs.getConfigs().getIosEnvironment());
			logger.debug(JSONObject.valueToString(json));
		} catch (Exception e) {
			logger.debug("推送处理服务端错误异常");
			return false;
		}
		return true;
	}
	
	/**
	 * findAndroidMessage
	 * @param info info
	 * @return    设定参数 .
	 */
	private Message findAndroidMessage(Info info){
		Message message = new Message();
		message.setType(Message.TYPE_NOTIFICATION);
		//样式
		Style style = new Style(3, 1, 1, 1, 0);
		//点击事件
		ClickAction action = new ClickAction();
		action.setActionType(ClickAction.TYPE_ACTIVITY);
		action.setActivity("1");
		//附加字段
		Map<String, Object> custom = new HashMap<String, Object>();
		custom.put("id", info.getIid());
		custom.put("url", BaseInfo.getDomain()+"/resources/jmp/html/"+info.getIid() + ".html");//塞模板URL 给客户端	
		//		if(info.getUrl() != null && info.getUrl().trim().length() > 0
//				&& info.getInfoContentType() == 101){
//			custom.put("yurl", strUtil.limitLen(info.getUrl(), 70, "UTF-8"));
//		}
		Site site = siteService.getOneSite(info.getSiteId());
		String siteName = "";
		if(site != null){
			siteName = site.getName();
		}
		message.setTitle(siteName);
		message.setContent(info.getTitle());
		message.setStyle(style);
		message.setAction(action);
		message.setCustom(custom);
		message.setExpireTime(86400);
		return message;
	}
	
	/**
	 * findAndroidMessage
	 * @param id id
	 * @param yurl yurl
	 * @param title title
	 * @param content content
	 * @return    设定参数 .
	 */
	private Message findAndroidMessage(int id, String yurl, String title, String content){
		Message message = new Message();
		message.setType(Message.TYPE_NOTIFICATION);
		//样式
		Style style = new Style(3, 1, 1, 1, 0);
		//点击事件
		ClickAction action = new ClickAction();
		action.setActionType(ClickAction.TYPE_ACTIVITY);
		action.setActivity("1");
		//附加字段
		Map<String, Object> custom = new HashMap<String, Object>();
		custom.put("id", id);
		if(StringUtil.isNotEmpty(yurl)){
			custom.put("yurl", strUtil.limitLen(yurl, 70, "UTF-8"));
		}
		message.setTitle(title);
		message.setContent(content);
		message.setStyle(style);
		message.setAction(action);
		message.setCustom(custom);
		message.setExpireTime(86400);
		return message;
	}
	
	/**
	 * findIosMessage
	 * @param info info
	 * @return    设定参数 .
	 */
	private MessageIOS findIosMessage(Info info){
		MessageIOS messageIOS = new MessageIOS();
		messageIOS.setAlert(strUtil.limitLen(info.getTitle(), 70, "UTF-8"));
		//附加字段
		Map<String, Object> custom = new HashMap<String, Object>();
		custom.put("id", info.getIid());
		custom.put("url", BaseInfo.getDomain()+"/resources/jmp/html/"+info.getIid() + ".html");//塞模板URL 给客户端	
		custom.put("a", info.getIid() + ",");
		messageIOS.setCustom(custom);
		messageIOS.setExpireTime(86400);
		return messageIOS;
	}
	
	/**
	 * findIosMessage
	 * @param id id
	 * @param yurl yurl
	 * @param content content
	 * @return    设定参数 .
	 */
	private MessageIOS findIosMessage(int id, String yurl, String content){
		MessageIOS messageIOS = new MessageIOS();
		messageIOS.setAlert(strUtil.limitLen(content, 70, "UTF-8"));
		//附加字段
		Map<String, Object> custom = new HashMap<String, Object>();
		custom.put("id", id);
		if(StringUtil.isNotEmpty(yurl)){
			custom.put("yurl", strUtil.limitLen(yurl, 70, "UTF-8"));
		}
		messageIOS.setCustom(custom);
		messageIOS.setExpireTime(86400);
		return messageIOS;
	}
	
	public boolean androidPushTokenMessage(Info info, UserDevice ud) {
		JSONObject json = null;
		Parameter parameter = parameterService.findBySiteId(NumberUtil.getInt(info.getSiteId()));
		XingeApp androidXg;
		try {
			androidXg = getClient(parameter, 3);
		} catch (OperationException e) {
			logger.debug(e.getMessage());
			return false;
		}
		try {
			json = androidXg.pushSingleDevice(ud.getUserid(), findAndroidMessage(info));
			logger.debug(JSONObject.valueToString(json));
		    return (json.getInt("ret_code") == 0);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("推送处理服务端错误异常");
			return false;
		}	
	}
	
	
	public Map<String, Boolean> iosPushTokenMessage(Info info, UserDevice ud) {
		JSONObject json = null;
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		Parameter parameter = parameterService.findBySiteId(NumberUtil.getInt(info.getSiteId()));
		if(StringUtil.getString(parameter.getIphonePushAppKey()).equals(parameter.getIpadPushAppKey())
				&& StringUtil.getString(parameter.getIphonePushAppSecret()).equals(parameter.getIpadPushAppSecret())){
			XingeApp iosXg;
			try {
				iosXg = getClient(parameter, 2);
			} catch (OperationException e) {
				e.printStackTrace();
				map.put("iphone", false);
		    	map.put("ipad", false);
				logger.debug(e.getMessage());
				return map;
			}
			try {
				json = iosXg.pushSingleDevice(ud.getUserid(), findIosMessage(info), Configs.getConfigs().getIosEnvironment());
				if (json.getInt("ret_code") == 0) {
					map.put("iphone", true);
					map.put("ipad", true);
			    } else {
			    	map.put("iphone", false);
			    	map.put("ipad", false);
			    }
				logger.debug(JSONObject.valueToString(json));
				return map;
			} catch (Exception e) {
				e.printStackTrace();
				map.put("iphone", false);
		    	map.put("ipad", false);
				logger.debug("推送处理服务端错误异常");
				return map;
			}
		}else{
			//iphone和ipad的账号不一致，要推送2次
			XingeApp iphoneXg;
			XingeApp ipadXg;
			try {
				iphoneXg = getClient(parameter, 2);
				ipadXg = getClient(parameter, 4);
			} catch (OperationException e) {
				e.printStackTrace();
				logger.debug(e.getMessage());
				return map;
			}
			try {
				json = iphoneXg.pushSingleDevice(ud.getUserid(), findIosMessage(info), Configs.getConfigs().getIosEnvironment());
				if (json.getInt("ret_code") == 0) {
					map.put("iphone", true);
			    } else {
			    	map.put("iphone", false);
			    }
				logger.debug(JSONObject.valueToString(json));
				json = ipadXg.pushSingleDevice(ud.getUserid(), findIosMessage(info), Configs.getConfigs().getIosEnvironment());
				logger.debug(JSONObject.valueToString(json));
				if (json.getInt("ret_code") == 0) {
			    	map.put("ipad", true);
			    } else {
			    	map.put("ipad", false);
			    }
				return map;
			} catch (Exception e) {
				e.printStackTrace();
				map.put("iphone", false);
		    	map.put("ipad", false);
				logger.debug("推送处理服务端错误异常");
				return map;
			}
		}
	}
	
}