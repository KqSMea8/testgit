package com.hanweb.jmp.sys.controller.configuration;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.annotation.Permission; 
import com.hanweb.common.util.Properties; 
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState; 
import com.hanweb.complat.constant.Settings;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.StaticValues;
import com.hanweb.jmp.util.CacheUtil;

@Controller
@RequestMapping("manager/jmp/configuration")
public class ConfigController {
	
	/**
	 * 系统参数设置界面
	 * @return  ModelAndView
	 */
	@RequestMapping("modify_show")
	public ModelAndView showModify(){
		ModelAndView modelAndView = new ModelAndView("/jmp/sys/configuration/configuration_opr");
		modelAndView.addObject("setting", Settings.getSettings());
		modelAndView.addObject("config", Configs.getConfigs());
		modelAndView.addObject("url", "update_submit.do");
		return modelAndView;
	}
	
	/**
	 * URL地址验证
	 * @param url  url
	 * @return  JsonResult
	 */
	@SuppressWarnings("deprecation")
    @RequestMapping("testurl")
	@ResponseBody
	public JsonResult testUrl(String url) {
		JsonResult jsonResult = JsonResult.getInstance();
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpGet httpGet = new HttpGet(url);
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);
			HttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) { 
				jsonResult.setSuccess(false);
				jsonResult.set(ResultState.OPR_FAIL);
			} else {
				jsonResult.setSuccess(true);
				jsonResult.set(ResultState.OPR_SUCCESS);
			}
			return jsonResult;
		} catch (ClientProtocolException e) {
			jsonResult.setSuccess(false);
			jsonResult.set(ResultState.OPR_FAIL);
			return jsonResult;
		} catch (IOException e) {
			jsonResult.setSuccess(false);
			jsonResult.set(ResultState.OPR_FAIL);
			return jsonResult;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	/**
	 * 系统参数提交保存
	 * @param configs  configs
	 * @param settings settings
	 * @return   JsonResult
	 */
	@Permission(function="update_submit")
	@RequestMapping("update_submit")
	@ResponseBody
	public JsonResult submitUpdate(Configs configs, Settings settings){
		Settings.getSettings().setBanTimes(settings.getBanTimes());
		Settings.getSettings().setCheckLevel(settings.getCheckLevel());
		Settings.getSettings().setEnableVerifyCode(settings.isEnableVerifyCode());
		Settings.getSettings().setFileTmp(settings.getFileTmp());
		Settings.getSettings().setImageDir(settings.getImageDir());
		Settings.getSettings().setAttachmentDir(settings.getAttachmentDir());
		Settings.getSettings().setLoginError(settings.getLoginError());
		Settings.getSettings().setSessionTime(settings.getSessionTime());
		Settings.getSettings().setJopUrl(settings.getJopUrl());
		Properties properties = new Properties(BaseInfo.getRealPath() + "/WEB-INF/config/complat.properties");
		properties.setProperty("interceptMode", Settings.getSettings().getInterceptMode());
		properties.setProperty("loginError", Settings.getSettings().getLoginError());
		properties.setProperty("banTimes", Settings.getSettings().getBanTimes());
		properties.setProperty("canFeedback", Settings.getSettings().isCanFeedback());
		properties.setProperty("checkLevel", Settings.getSettings().getCheckLevel());
		properties.setProperty("sessionTime", Settings.getSettings().getSessionTime());
		properties.setProperty("fileTmp", Settings.getSettings().getFileTmp());
		properties.setProperty("imageDir", Settings.getSettings().getImageDir());
		properties.setProperty("attachmentDir", Settings.getSettings().getAttachmentDir());
		properties.setProperty("enableVerifyCode", Settings.getSettings().isEnableVerifyCode());
		properties.setProperty("jopUrl", Settings.getSettings().getJopUrl());
		properties.save();
		
		Configs.getConfigs().setJmpUrl(configs.getJmpUrl());
		Configs.getConfigs().setJgetUrl(configs.getJgetUrl());
		Configs.getConfigs().setJgetLogId(configs.getJgetLogId());
		Configs.getConfigs().setJgetPassword(configs.getJgetPassword());
		Configs.getConfigs().setJsearchUrl(configs.getJsearchUrl());
		Configs.getConfigs().setCanAppFactory(configs.getCanAppFactory());
		Configs.getConfigs().setSynPeroid(configs.getSynPeroid());
		Configs.getConfigs().setInfoSaveType(configs.getInfoSaveType());
		Configs.getConfigs().setInfoSaveDays(configs.getInfoSaveDays());
		Configs.getConfigs().setInfoSaveCounts(configs.getInfoSaveCounts());
		Configs.getConfigs().setIntervalTime(configs.getIntervalTime());
		Configs.getConfigs().setSynLastTime(configs.getSynLastTime());
		Configs.getConfigs().setSynInfoNum(configs.getSynInfoNum());
		Configs.getConfigs().setAccessControl(configs.getAccessControl());
		Configs.getConfigs().setAllowIp(configs.getAllowIp());
		Configs.getConfigs().setServiceCode(configs.getServiceCode());
		Configs.getConfigs().setKey(configs.getKey());
		Configs.getConfigs().setWeixinUrl(configs.getWeixinUrl());
		Configs.getConfigs().setWxSupport(configs.getWxSupport());
		Configs.getConfigs().setHtpUrl(configs.getHtpUrl());
		
		Properties   jmpProperties = new Properties(BaseInfo.getRealPath() + "/WEB-INF/config/jmportal.properties");
		jmpProperties.setProperty("jmpUrl", Configs.getConfigs().getJmpUrl());
		jmpProperties.setProperty("jgetUrl", Configs.getConfigs().getJgetUrl());
		jmpProperties.setProperty("jgetLogId", Configs.getConfigs().getJgetLogId());
		jmpProperties.setProperty("jgetPassword", Configs.getConfigs().getJgetPassword());
		jmpProperties.setProperty("jsearchUrl", Configs.getConfigs().getJsearchUrl());
		jmpProperties.setProperty("canAppFactory", Configs.getConfigs().getCanAppFactory());
		jmpProperties.setProperty("synPeroid", Configs.getConfigs().getSynPeroid());
		jmpProperties.setProperty("infoSaveType", Configs.getConfigs().getInfoSaveType());
		jmpProperties.setProperty("infoSaveDays", Configs.getConfigs().getInfoSaveDays());
		jmpProperties.setProperty("infoSaveCounts", Configs.getConfigs().getInfoSaveCounts());
		jmpProperties.setProperty("intervalTime", Configs.getConfigs().getIntervalTime());
		jmpProperties.setProperty("synLastTime", Configs.getConfigs().getSynLastTime());
		jmpProperties.setProperty("synInfoNum", Configs.getConfigs().getSynInfoNum());
		jmpProperties.setProperty("accessControl", Configs.getConfigs().getAccessControl());
		jmpProperties.setProperty("allowIp", Configs.getConfigs().getAllowIp());
		jmpProperties.setProperty("serviceCode", Configs.getConfigs().getServiceCode());
		jmpProperties.setProperty("key", Configs.getConfigs().getKey());
		jmpProperties.setProperty("weixinUrl", Configs.getConfigs().getWeixinUrl());
		jmpProperties.setProperty("wxSupport", Configs.getConfigs().getWxSupport());
		jmpProperties.setProperty("htpUrl", Configs.getConfigs().getHtpUrl());
		jmpProperties.save();
		
		JsonResult jsonResult = JsonResult.getInstance();
		jsonResult.set(ResultState.MODIFY_SUCCESS);
		CacheUtil.removeKey(StaticValues.CACHE_SITE, "jgetXml");
		return jsonResult;
	}
	
}