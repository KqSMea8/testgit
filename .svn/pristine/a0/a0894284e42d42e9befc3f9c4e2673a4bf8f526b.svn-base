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
		 String sql;
		if(dbType == 1){
			sql = "ALTER TABLE jmp_col ADD ditchid NUMBER(11)";
			query = updateService.createQuery(sql);
			updateService.execSql(query);
		}else{
			sql = "ALTER TABLE jmp_col ADD ditchid int(11)";
			query = updateService.createQuery(sql);
			updateService.execSql(query);
		}
		
		sql = "ALTER TABLE jmp_col ADD newlightappid varchar(255)";
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