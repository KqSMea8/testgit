<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@page import="com.hanweb.jmp.sys.entity.sites.Site"%>
<%@page import="com.hanweb.common.BaseInfo"%>
<%@page import="com.hanweb.setup.dao.DataInitDAO"%>
<%@page import="com.hanweb.common.util.SpringUtil"%>
<%@page import="com.hanweb.common.basedao.Query"%>
<%@page import="com.hanweb.jmp.constant.Tables"%>
<%@page import="com.hanweb.common.BaseInfo"%>
<%@page import="com.hanweb.jmp.sys.service.sites.SiteService"%>
<%@page import="com.hanweb.setup.service.UpgradeService"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

	out.clear();
	UpgradeService updateService = SpringUtil.getBean(UpgradeService.class);  
	SiteService siteService = SpringUtil.getBean(SiteService.class);  
	boolean isSuccess = true; 
	
	String domain = BaseInfo.getDomain();
	int dbType = BaseInfo.getDbType();
	try{
	    Query query;
	    String sql = "";
	    int siteid=0;
	    
	    if(dbType == 1){
	    	List<Site> sites=siteService.findAll();
			for(Site site:sites){
				 siteid=site.getIid();
					sql = "INSERT INTO jmp_lightapp(iid,siteid,name,lighttype,lighttypename,apptype,url,isopen,orderid,isdefault,defaulttype,createtime,isshowtopview,keyvalue,groupname,iconoldname,iconnewname,qrcodeaddress) VALUES (NULL, "+siteid+", '个人所得税', 0, NULL, 1, '"+domain+"/resources/lightapps/apps/income_tax/index.html', 1, -1, 1, 1, to_date('2018-08-20 11:03:33','yyyy-mm-dd hh24:mi:ss'), NULL, NULL, NULL, NULL, NULL, NULL)";
					query = updateService.createQuery(sql);
					updateService.execSql(query);

					sql = "INSERT INTO jmp_lightapp(iid,siteid,name,lighttype,lighttypename,apptype,url,isopen,orderid,isdefault,defaulttype,createtime,isshowtopview,keyvalue,groupname,iconoldname,iconnewname,qrcodeaddress) VALUES (NULL, "+siteid+", '社保查询', 0, NULL, 1, '"+domain+"/resources/lightapps/apps/social_security/index.html', 1, -7, 1, 7, to_date('2018-08-20 11:03:33','yyyy-mm-dd hh24:mi:ss'), NULL, NULL, NULL, NULL, NULL, NULL)";
					query = updateService.createQuery(sql);
					updateService.execSql(query);

					sql = "INSERT INTO jmp_lightapp(iid,siteid,name,lighttype,lighttypename,apptype,url,isopen,orderid,isdefault,defaulttype,createtime,isshowtopview,keyvalue,groupname,iconoldname,iconnewname,qrcodeaddress) VALUES (NULL, "+siteid+", '公积金查询', 0, NULL, 1, '"+domain+"/resources/lightapps/apps/accumulation_money/index.html', 1, -8, 1, 8, to_date('2018-08-20 11:03:33','yyyy-mm-dd hh24:mi:ss'), NULL, NULL, NULL, NULL, NULL, NULL)";
					query = updateService.createQuery(sql);
					updateService.execSql(query);
			}
		}else{
			List<Site> sites=siteService.findAll();
			for(Site site:sites){
				 siteid=site.getIid();
					sql = "INSERT INTO jmp_lightapp(iid,siteid,name,lighttype,lighttypename,apptype,url,isopen,orderid,isdefault,defaulttype,createtime,isshowtopview,keyvalue,groupname,iconoldname,iconnewname,qrcodeaddress) VALUES (NULL, "+siteid+", '个人所得税', 0, NULL, 1, '"+domain+"/resources/lightapps/apps/income_tax/index.html', 1, -1, 1, 1, '2018-8-20 11:03:33', NULL, NULL, NULL, NULL, NULL, NULL)";
					query = updateService.createQuery(sql);
					updateService.execSql(query);
					

					sql = "INSERT INTO jmp_lightapp(iid,siteid,name,lighttype,lighttypename,apptype,url,isopen,orderid,isdefault,defaulttype,createtime,isshowtopview,keyvalue,groupname,iconoldname,iconnewname,qrcodeaddress) VALUES (NULL, "+siteid+", '社保查询', 0, NULL, 1, '"+domain+"/resources/lightapps/apps/social_security/index.html', 1, -7, 1, 7, '2018-8-20 11:03:33', NULL, NULL, NULL, NULL, NULL, NULL)";
					query = updateService.createQuery(sql);
					updateService.execSql(query);
					
					sql = "INSERT INTO jmp_lightapp(iid,siteid,name,lighttype,lighttypename,apptype,url,isopen,orderid,isdefault,defaulttype,createtime,isshowtopview,keyvalue,groupname,iconoldname,iconnewname,qrcodeaddress) VALUES (NULL, "+siteid+", '公积金查询', 0, NULL, 1, '"+domain+"/resources/lightapps/apps/accumulation_money/index.html', 1, -8, 1, 8, '2018-8-20 11:03:33', NULL, NULL, NULL, NULL, NULL, NULL)";
					query = updateService.createQuery(sql);
					updateService.execSql(query);
			}
		}
		
		
		
		

		
		isSuccess = true;
		
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