<%@page import="com.hanweb.common.annotation.ColumnType"%>
<%@page import="com.hanweb.common.basedao.Query"%>
<%@page import="com.hanweb.common.util.SpringUtil"%>
<%@page import="com.hanweb.setup.service.UpgradeService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	
	UpgradeService upgradeService = SpringUtil.getBean(UpgradeService.class);
	boolean success = false;
	try{
		success = upgradeService.getDataInitDAO().addColumn("complat_user", "commonregion", ColumnType.VARCHAR);
	}catch(Exception e){
		e.printStackTrace();
	} finally {
		if(success){
			out.println("complat_user 表 commonregion 字段 新增【成功】" + "<br>");
		}else{
			out.println("complat_user 表 commonregion 字段 新增【失败】" + "<br>");
		}
	}
	try{
		success = upgradeService.getDataInitDAO().addColumn("complat_user", "dynamiccodekey", ColumnType.VARCHAR);
	}catch(Exception e){
		e.printStackTrace();
	} finally {
		if(success){
			out.println("complat_user 表 dynamiccodekey 字段 新增【成功】" + "<br>");
		}else{
			out.println("complat_user 表 dynamiccodekey 字段 新增【失败】" + "<br>");
		}
	}
	out.println("用完删除" + "<br>");
%>