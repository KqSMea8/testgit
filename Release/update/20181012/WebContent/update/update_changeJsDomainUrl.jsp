<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@page import="com.hanweb.common.BaseInfo"%>
<%@page import="com.hanweb.setup.dao.DataInitDAO"%>
<%@page import="com.hanweb.common.util.SpringUtil"%>
<%@page import="com.hanweb.common.basedao.Query"%>
<%@page import="com.hanweb.jmp.constant.Tables"%>
<%@page import="com.hanweb.common.BaseInfo"%>
<%@page import="org.apache.commons.logging.Log"%>
<%@page import="org.apache.commons.logging.LogFactory"%>
<%@page import="com.hanweb.setup.service.UpgradeService"%>
<%@page import="com.hanweb.jmp.sys.service.sites.SiteService"%>
<%@page import="java.util.List"%>
<%@page import="com.hanweb.common.util.StringUtil"%>;
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

	out.clear();
	UpgradeService updateService = SpringUtil.getBean(UpgradeService.class);  
	SiteService siteService = SpringUtil.getBean(SiteService.class);
	Log logger = LogFactory.getLog(getClass());
	
	boolean isSuccess = false; 
	String domain = BaseInfo.getDomain();
	
	try{
		if(StringUtil.isNotEmpty(domain)){
			domain = StringUtil.convertPath(domain.trim().toLowerCase(), false);
			if(domain.startsWith("http") && !domain.endsWith("/")){
				//替换jssdk的域名参数
				String domainUrl = domain;
				System.out.println("domainUrl="+domainUrl);
//				if(domain.startsWith("http://")){
//					domainUrl = domain.substring(7);
//				}else if(domain.startsWith("https://")){
//					domainUrl = domain.substring(8);
//				}
				
				if(StringUtil.isNotEmpty(domainUrl)){
					String oldstr = "http://jmpotal.hanweb.com/jmp";
					
					String jsUrl = BaseInfo.getRealPath()+"/resources/lightapps/apps/accumulation_money/static/js/app.475fd75b12e7f508cb66.js";
                    isSuccess = siteService.rewriteJs(jsUrl, domainUrl, oldstr);
					if(!isSuccess){
						logger.error(BaseInfo.getDomain() + "/resources/lightapps/apps/accumulation_money/static/js/app.475fd75b12e7f508cb66.js, 公积金域名修改失败！");
					}
					
					jsUrl = BaseInfo.getRealPath()+"/resources/lightapps/apps/income_tax/static/js/app.6ed174dcfa119a13bfec.js";
                    isSuccess = siteService.rewriteJs(jsUrl, domainUrl, oldstr);
					if(!isSuccess){
						logger.error(BaseInfo.getDomain() + "/resources/lightapps/apps/income_tax/static/js/app.6ed174dcfa119a13bfec.js, 个人所得税域名修改失败！");
					}
					
					jsUrl = BaseInfo.getRealPath()+"/resources/lightapps/apps/social_security/static/js/app.b6ac7cf3389472e5fd38.js";
                    isSuccess = siteService.rewriteJs(jsUrl, domainUrl, oldstr);
					if(!isSuccess){
						logger.error(BaseInfo.getDomain() + "/resources/lightapps/apps/social_security/static/js/app.b6ac7cf3389472e5fd38.js, 社保域名修改失败！");
					}
					
					jsUrl = BaseInfo.getRealPath()+"/resources/lightapps/apps/mailbook/static/js/app.cc1fca96acea068aee46.js";
                    isSuccess = siteService.rewriteJs(jsUrl, domainUrl, oldstr);
					if(!isSuccess){
						logger.error(BaseInfo.getDomain() + "/resources/lightapps/apps/mailbook/static/js/app.cc1fca96acea068aee46.js, 通讯录域名修改失败！");
					}
					
					jsUrl = BaseInfo.getRealPath()+"/resources/lightapps/apps/rebellion/static/js/app.708238fe31165333af1f.js";
                    isSuccess = siteService.rewriteJs(jsUrl, domainUrl, oldstr);
					if(!isSuccess){
						logger.error(BaseInfo.getDomain() + "/resources/lightapps/apps/rebellion/static/js/app.708238fe31165333af1f.js, 报料域名修改失败！");
					}
					
					jsUrl = BaseInfo.getRealPath()+"/resources/lightapps/apps/weather/static/js/app.59ae9dca37cc866f67b8.js";
                    isSuccess = siteService.rewriteJs(jsUrl, domainUrl, oldstr);
					if(!isSuccess){
						logger.error(BaseInfo.getDomain() + "/resources/lightapps/apps/weather/static/js/app.59ae9dca37cc866f67b8.js, 空气质量域名修改失败！");
					}
					
				}
			}
			isSuccess = true;
		} else {
			isSuccess = false;
		}
		
		
	} catch (Exception e){
	    e.printStackTrace();
		isSuccess = false;
	}
	if(isSuccess){
		out.print("执行成功！请删除该文件");
	}else{
		out.print("执行失败！");
	}
%> 