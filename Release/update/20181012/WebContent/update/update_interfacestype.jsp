<%@page import="com.hanweb.common.BaseInfo"%>
<%@page import="com.hanweb.setup.dao.DataInitDAO"%>
<%@page import="com.hanweb.common.util.SpringUtil"%>
<%@page import="com.hanweb.common.basedao.Query"%>
<%@page import="com.hanweb.jmp.constant.Tables"%>
<%@page import="com.hanweb.setup.service.UpgradeService"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

	out.clear();
	UpgradeService updateService = SpringUtil.getBean(UpgradeService.class);  
	boolean isSuccess = true; 

	try{
	    Query query;
	    String sql = "TRUNCATE TABLE jmp_interfacestype";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO jmp_interfacestype VALUES (1, 'APP首页', '0')";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfacestype VALUES (2, '用户相关', '0')";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfacestype VALUES (3, '导航相关', '0')";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfacestype VALUES (4, '栏目相关', '0')";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfacestype VALUES (5, '信息相关', '0')";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfacestype VALUES (6, '订阅相关', '0')";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfacestype VALUES (7, '离线下载相关', '0')";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfacestype VALUES (8, '意见反馈相关', '0')";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfacestype VALUES (9, '评论相关', '0')";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfacestype VALUES (10, '消息相关', '0')";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfacestype VALUES (11, '应用相关', '0')";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfacestype VALUES (12, '报料相关', '0')";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfacestype VALUES (13, '天气相关', '0')";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfacestype VALUES (14, '通讯录相关', '0')";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfacestype VALUES (15, '阅读相关', '0')";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfacestype VALUES (16, '微博相关', '0')";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
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