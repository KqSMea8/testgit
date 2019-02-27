<%@page import="com.hanweb.common.BaseInfo"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.hanweb.setup.service.UpgradeService"%>
<%@page import="com.hanweb.common.util.SpringUtil"%>
<%@page import="com.hanweb.common.basedao.Query"%> 
<%
	out.clear();
	UpgradeService updateService = SpringUtil.getBean(UpgradeService.class);  
	boolean isSuccess=true; 
	
	int dbType = BaseInfo.getDbType();
	try {
		Query query;
		
	    String sql = "ALTER TABLE jmp_lightapp ADD iconoldname varchar(255)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "ALTER TABLE jmp_lightapp ADD iconnewname varchar(255)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		isSuccess = true;
	} catch (Exception e){ 
		e.printStackTrace();
		isSuccess = false;
	}
	if (isSuccess){
		out.print("执行成功！请删除此文件");
	} else {
		out.print("执行失败");
	}
%>