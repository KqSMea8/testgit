<%@page import="com.hanweb.common.BaseInfo"%>
<%@page import="com.hanweb.setup.dao.DataInitDAO"%>
<%@page import="com.hanweb.common.util.SpringUtil"%>
<%@page import="com.hanweb.common.basedao.Query"%>
<%@page import="com.hanweb.jmp.constant.Tables"%>
<%@page import="com.hanweb.common.BaseInfo"%>
<%@page import="com.hanweb.setup.service.UpgradeService"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

	out.clear();
	UpgradeService updateService = SpringUtil.getBean(UpgradeService.class);  
	boolean isSuccess = true; 
	
	String domain = BaseInfo.getDomain();
	
	try{
	    Query query;
	    String sql = "TRUNCATE TABLE jmp_interfaces";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO jmp_interfaces VALUES (1, 'APP封面接口', '"+domain+"/interfaces/splash.do', 1, 1)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (2, '首页接口', '"+domain+"/interfaces/first.do', 1, 1)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (3, '登录接口', '"+domain+"/interfaces/login.do', 1, 2)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (4, '注册接口', '"+domain+"/interfaces/regist.do', 1, 2)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (5, '导航下栏目接口', '"+domain+"/interfaces/chancates.do', 1, 3)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (6, '综合首页接口', '"+domain+"/interfaces/getcomppage.do', 1, 3)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (7, '栏目下子栏目接口', '"+domain+"/interfaces/cates.do', 1, 4)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (8, '栏目下一级子栏目接口', '"+domain+"/interfaces/nextlevelcates.do', 1, 4)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (9, '信息列表接口', '"+domain+"/interfaces/infolist.do', 1, 5)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (10, '信息正文接口', '"+domain+"/interfaces/infocontent.do', 1, 5)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (11, '信息分享接口', '"+domain+"/interfaces/shareinfo.do', 2, 5)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (12, '信息图片接口', '"+domain+"/interfaces/pic.do', 1, 5)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (13, '点赞信息提交接口', '"+domain+"/interfaces/goodadd.do', 1, 5)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (14, '信息阅读数/评论数/点赞数接口', '"+domain+"/interfaces/infocount.do', 1, 5)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (15, '全文检索接口', '"+domain+"/interfaces/searchinfolist.do', 1, 5)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (16, '多角标信息列表接口', '"+domain+"/interfaces/cardinfolist.do', 1, 5)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (17, '栏目订阅分类接口', '"+domain+"/interfaces/bookcatesdimension.do', 1, 6)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (18, '栏目订阅列表接口', '"+domain+"/interfaces/bookcateslist.do', 1, 6)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (19, '我的订阅列表接口', '"+domain+"/interfaces/mybookcateslist.do', 1, 6)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (20, '我的栏目订阅/退订接口', '"+domain+"/interfaces/mybookcates.do', 1, 6)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (21, '离线下载zip接口', '"+domain+"/interfaces/offlinedownload.do', 1, 7)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (22, 'zip更新接口', '"+domain+"/interfaces/offlineupdate.do', 1, 7)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (23, '离线下载栏目', '"+domain+"/interfaces/offlinelist.do', 1, 7)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (24, '意见反馈提交接口', '"+domain+"/interfaces/feedback/uploadfeed.do', 0, 8)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (25, '获取反馈列表接口', '"+domain+"/interfaces/feedback/list.do', 1, 8)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (26, '评论列表接口', '"+domain+"/interfaces/commentlist.do', 1, 9)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (27, '提交评论接口', '"+domain+"/interfaces/commentadd.do', 1, 9)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (28, '消息推送接口', '"+domain+"/interfaces/pushinfolist.do', 1, 10)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (29, '获取应用列表接口', '"+domain+"/interfaces/getapplist.do', 1, 11)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (30, '报料分类接口', '"+domain+"/interfaces/broke/group.do', 1, 12)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (31, '报料列表接口', '"+domain+"/interfaces/broke/list.do', 1, 12)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (32, '报料正文接口', '"+domain+"/interfaces/broke/detail.do', 1, 12)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (33, '提交报料接口', '"+domain+"/interfaces/broke/brokeadd.do', 1, 12)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (34, '我的报料接口', '"+domain+"/interfaces/broke/mylist.do', 1, 12)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (35, '查询报料列表接口', '"+domain+"/interfaces/broke/showdetail.do', 1, 12)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (36, '地区接口', '"+domain+"/interfaces/area_new.do', 1, 13)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (37, '天气数据接口', '"+domain+"/interfaces/weather_new.do', 1, 13)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (38, '通讯录分类', '"+domain+"/interfaces/numsensecates.do', 1, 14)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (39, '通讯录检索接口', '"+domain+"/interfaces/childsearch.do', 1, 14)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (40, '号码列表接口', '"+domain+"/interfaces/numsenselist.do', 1, 14)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (41, '号码详情接口', '"+domain+"/interfaces/numsensedetail.do', 1, 14)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (42, '阅读分类接口', '"+domain+"/interfaces/readcates.do', 1, 15)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (43, '阅读书籍接口', '"+domain+"/interfaces/readdetail.do', 1, 15)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);
		
		sql = "INSERT INTO jmp_interfaces VALUES (44, '微博列表接口', '"+domain+"/interfaces/blog_c.do', 1, 16)";
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