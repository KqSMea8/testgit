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
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	out.clear();
	UpgradeService updateService = SpringUtil
			.getBean(UpgradeService.class);
	boolean isSuccess = true;

	try {
		Query query;
		String sql = "";

		sql = "ALTER TABLE jmp_lightapp ADD qrcodeaddress VARCHAR(255)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		isSuccess = true;

	} catch (Exception e) {
		e.printStackTrace();
		isSuccess = false;
	}
	if (isSuccess) {

		out.print("执行成功！请删除该文件");
	} else {
		out.print("执行失败！");
	}
%>
