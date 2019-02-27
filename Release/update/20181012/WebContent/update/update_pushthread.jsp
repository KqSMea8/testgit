<%@page import="com.hanweb.common.BaseInfo"%>
<%@page import="com.hanweb.setup.dao.DataInitDAO"%>
<%@page import="com.hanweb.common.util.SpringUtil"%>
<%@page import="com.hanweb.common.basedao.Query"%>
<%@page import="com.hanweb.jmp.constant.Tables"%>
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
	int dbType = BaseInfo.getDbType();

	try {
		Query query;
		if (dbType == 1) {
			String sql = "ALTER TABLE jmp_pushthread ADD sendtype NUMBER(11)";
			query = updateService.createQuery(sql);
			updateService.execSql(query);

			sql = "ALTER TABLE jmp_pushthread ADD sendtypeid NUMBER(11)";
			query = updateService.createQuery(sql);
			updateService.execSql(query);

			sql = "ALTER TABLE jmp_pushthread ADD ispublic NUMBER(11)";
			query = updateService.createQuery(sql);
			updateService.execSql(query);
		} else {
			String sql = "ALTER TABLE jmp_pushthread ADD sendtype int(11)";
			query = updateService.createQuery(sql);
			updateService.execSql(query);

			sql = "ALTER TABLE jmp_pushthread ADD sendtypeid int(11)";
			query = updateService.createQuery(sql);
			updateService.execSql(query);

			sql = "ALTER TABLE jmp_pushthread ADD ispublic int(11)";
			query = updateService.createQuery(sql);
			updateService.execSql(query);
		}

		String sql = "ALTER TABLE jmp_pushthread ADD userids varchar(255)";
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
