package com.hanweb.jmp.apps.service.manage;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.BaseInfo;
import com.hanweb.jmp.apps.entity.manage.LightApp;
import com.hanweb.jmp.constant.Configs;
 
public class LightInitService {
	
	@Autowired
	LightAppService lightAppService;
	public boolean lightInit(int siteId) {
		
		//初始化报料轻应用
		LightApp lightAppEn=new LightApp();
		lightAppEn=new LightApp();
		lightAppEn.setCreateTime(new Date()); 
		lightAppEn.setDefaultType(2);
		lightAppEn.setIsDefault(1);
		lightAppEn.setIsOpen(1);
		lightAppEn.setName("报料");
		lightAppEn.setOrderId(-2);
		lightAppEn.setSiteId(siteId);
		lightAppEn.setAppType(1);
		lightAppEn.setUrl(BaseInfo.getDomain() + "/resources/lightapps/apps/rebellion/index.html");
		lightAppService.addInterface(lightAppEn);
		
		//初始化通讯录轻应用
		lightAppEn=new LightApp();
		lightAppEn.setCreateTime(new Date()); 
		lightAppEn.setDefaultType(5);
		lightAppEn.setIsDefault(1);
		lightAppEn.setIsOpen(1);
		lightAppEn.setName("通讯录");
		lightAppEn.setOrderId(-5);
		lightAppEn.setSiteId(siteId); 
		lightAppEn.setAppType(1);
		lightAppEn.setUrl(BaseInfo.getDomain() + "/resources/lightapps/apps/mailbook/index.html");
		lightAppService.addInterface(lightAppEn);
		
		//初始化商城轻应用
//		lightAppEn.setCreateTime(new Date()); 
//		lightAppEn.setDefaultType(1);
//		lightAppEn.setIsDefault(1);
//		lightAppEn.setIsOpen(1);
//		lightAppEn.setName("商城");
//		lightAppEn.setOrderId(-1);
//		lightAppEn.setSiteId(siteId); 
//		lightAppService.addInterface(lightAppEn);
		
		
		//初始化通讯录轻应用
//		lightAppEn=new LightApp();
//		lightAppEn.setCreateTime(new Date()); 
//		lightAppEn.setDefaultType(7);
//		lightAppEn.setIsDefault(1);
//		lightAppEn.setIsOpen(1);
//		lightAppEn.setName("微表单");
//		lightAppEn.setOrderId(-7);
//		lightAppEn.setSiteId(siteId); 
//		lightAppService.addInterface(lightAppEn);
		
		lightAppEn=new LightApp();
		lightAppEn.setCreateTime(new Date()); 
		lightAppEn.setDefaultType(1);
		lightAppEn.setIsDefault(1);
		lightAppEn.setIsOpen(1);
		lightAppEn.setName("个人所得税");
		lightAppEn.setOrderId(-1);
		lightAppEn.setSiteId(siteId); 
		lightAppEn.setAppType(1);
		lightAppEn.setUrl(BaseInfo.getDomain() + "/resources/lightapps/apps/income_tax/index.html");
		lightAppService.addInterface(lightAppEn);
		
		lightAppEn=new LightApp();
		lightAppEn.setCreateTime(new Date()); 
		lightAppEn.setDefaultType(7);
		lightAppEn.setIsDefault(1);
		lightAppEn.setIsOpen(1);
		lightAppEn.setName("社保查询");
		lightAppEn.setOrderId(-7);
		lightAppEn.setSiteId(siteId); 
		lightAppEn.setAppType(1);
		lightAppEn.setUrl(BaseInfo.getDomain() + "/resources/lightapps/apps/social_security/index.html");
		lightAppService.addInterface(lightAppEn);
		
		
		lightAppEn=new LightApp();
		lightAppEn.setCreateTime(new Date()); 
		lightAppEn.setDefaultType(8);
		lightAppEn.setIsDefault(1);
		lightAppEn.setIsOpen(1);
		lightAppEn.setName("公积金查询");
		lightAppEn.setOrderId(-8);
		lightAppEn.setSiteId(siteId); 
		lightAppEn.setAppType(1);
		lightAppEn.setUrl(BaseInfo.getDomain() + "/resources/lightapps/apps/accumulation_money/index.html");
		lightAppService.addInterface(lightAppEn);
		
		//初始化阅读轻应用
		lightAppEn=new LightApp();
		lightAppEn.setCreateTime(new Date()); 
		lightAppEn.setDefaultType(6);
		lightAppEn.setIsDefault(1);
		lightAppEn.setIsOpen(1);
		lightAppEn.setName("期刊");
		lightAppEn.setOrderId(-6);
		lightAppEn.setAppType(2);
		lightAppEn.setSiteId(siteId); 
		lightAppService.addInterface(lightAppEn);
		
		//初始化天气预报轻应用
		lightAppEn=new LightApp();
		lightAppEn.setCreateTime(new Date()); 
		lightAppEn.setDefaultType(4);
		lightAppEn.setIsDefault(1);
		lightAppEn.setIsOpen(1);
		lightAppEn.setName("空气质量");
		lightAppEn.setOrderId(-4);
		lightAppEn.setSiteId(siteId);
		lightAppEn.setAppType(1);
		lightAppEn.setUrl(BaseInfo.getDomain() + "/resources/lightapps/apps/weather/index.html");
		lightAppService.addInterface(lightAppEn);
		
		//初始化公交轻应用
		lightAppEn=new LightApp();
		lightAppEn.setCreateTime(new Date()); 
		lightAppEn.setDefaultType(3);
		lightAppEn.setIsDefault(1);
		lightAppEn.setIsOpen(1);
		lightAppEn.setName("公交");
		lightAppEn.setAppType(1);
		lightAppEn.setOrderId(-3);
		lightAppEn.setSiteId(siteId); 
		lightAppEn.setUrl(BaseInfo.getDomain() + "/resources/lightapps/apps/bus/index.html");
		lightAppService.addInterface(lightAppEn);
		
//		//初始化微表单轻应用
//		lightAppEn=new LightApp();
//		lightAppEn.setCreateTime(new Date());
//		lightAppEn.setDefaultType(8);
//		lightAppEn.setIsDefault(1);
//		lightAppEn.setIsOpen(1);
//		lightAppEn.setName("微表单");
//		lightAppEn.setAppType(1);
//		lightAppEn.setOrderId(-8);
//		lightAppEn.setSiteId(siteId); 
//		lightAppService.addInterface(lightAppEn);
		
		return true;
	}
	
}