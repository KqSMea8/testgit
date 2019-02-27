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

	try {
		Query query;
		String sql = "TRUNCATE TABLE jmp_interfacesparam";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO jmp_interfacesparam VALUES (1, 31, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO jmp_interfacesparam VALUES (2, 31, 'clienttype', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO jmp_interfacesparam VALUES (3, 31, 'uuid', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO jmp_interfacesparam VALUES (4, 31, 'version', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO jmp_interfacesparam VALUES (5, 31, 'maxid', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO jmp_interfacesparam VALUES (6, 31, 'pagesize', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO jmp_interfacesparam VALUES (7, 34, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO jmp_interfacesparam VALUES (8, 34, 'pagesize', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO jmp_interfacesparam VALUES (9, 34, 'maxid', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (10, 34, 'loginid', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (11, 30, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (12, 33, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (13, 33, 'clienttype', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (14, 33, 'uuid', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (15, 33, 'loginid', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (16, 33, 'title', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (17, 33, 'content', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (18, 33, 'classid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (19, 33, 'isopen', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (20, 33, 'picfile', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (21, 33, 'picfile1', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (22, 33, 'picfile2', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (23, 33, 'picfile3', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (24, 33, 'audiofile', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (25, 33, 'videofile', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (26, 33, 'contact', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (27, 33, 'version', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (28, 32, 'infoid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (29, 35, 'title', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (30, 38, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (31, 38, 'clienttype', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (32, 38, 'uuid', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (33, 38, 'version', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (34, 38, 'cateid', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (35, 40, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (36, 40, 'clienttype', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (37, 40, 'uuid', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (38, 40, 'version', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (39, 40, 'cateid', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (40, 40, 'pagenum', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (41, 40, 'pagesize', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (42, 41, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (43, 41, 'clienttype', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (44, 41, 'uuid', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (45, 41, 'version', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (46, 41, 'cateid', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (47, 39, 'clienttype', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (48, 39, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (49, 39, 'uuid', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (50, 39, 'pagesize', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (51, 39, 'pagenum', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (52, 39, 'keyword', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (53, 2, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (54, 1, 'flag', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (55, 1, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (56, 1, 'clienttype', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (57, 4, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (58, 4, 'clienttype', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (59, 4, 'version', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (60, 4, 'uuid', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (61, 4, 'loginid', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (62, 4, 'password', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (63, 4, 'type', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (64, 4, 'phonecode', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (65, 4, 'name', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (66, 4, 'phone', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (67, 4, 'email', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (68, 4, 'headurl', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (69, 4, 'extraattrs', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (70, 3, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (71, 3, 'clienttype', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (72, 3, 'version', 0, 1)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (73, 3, 'uuid', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (74, 3, 'loginid', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (75, 3, 'password', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (76, 3, 'type', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (77, 5, 'channelid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (78, 5, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (83, 8, 'cateid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (84, 8, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (85, 8, 'type', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (86, 8, 'orderid', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (87, 7, 'cateid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (88, 7, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (89, 7, 'type', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (90, 7, 'orderid', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (91, 16, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (92, 16, 'resourceid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (93, 16, 'flag', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (94, 16, 'orderid', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (95, 16, 'type', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (96, 16, 'page', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (97, 15, 'resourceid', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (98, 15, 'num', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (99, 15, 'keyword', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (100, 15, 'page', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (101, 15, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (102, 15, 'type', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (103, 14, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (104, 14, 'uuid', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (105, 14, 'titleid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (106, 14, 'type', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (107, 13, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (108, 13, 'uuid', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (109, 13, 'titleid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (110, 13, 'resourceid', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (111, 13, 'type', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (112, 12, 'titleid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (113, 12, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (114, 12, 'clienttype', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (115, 11, 'titleid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (116, 11, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (117, 11, 'uuid', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (118, 10, 'titleid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (119, 10, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (120, 10, 'clienttype', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (121, 10, 'resourceid', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (122, 9, 'resourceid', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (123, 9, 'page', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (124, 9, 'topid', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (125, 9, 'orderid', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (126, 9, 'time', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (127, 9, 'ordertype', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (128, 9, 'flag', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (129, 9, 'type', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (130, 9, 'isbanner', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (131, 20, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (132, 20, 'type', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (133, 20, 'resourceid', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (134, 20, 'loginid', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (135, 20, 'uuid', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (136, 19, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (137, 19, 'loginid', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (138, 19, 'uuid', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (139, 18, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (140, 18, 'flag', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (141, 17, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (142, 17, 'flag', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (143, 23, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (144, 22, 'resid', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (145, 22, 'ziptime', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (146, 21, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (147, 21, 'resourceid', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (148, 25, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (149, 25, 'clienttype', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (150, 25, 'uuid', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (151, 25, 'version', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (152, 25, 'sendtime', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (153, 25, 'page', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (154, 25, 'type', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (155, 25, 'loginname', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (156, 24, 'siteid', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (157, 24, 'clienttype', 1, 1)";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (158, 24, 'uuid', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (159, 24, 'version', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (160, 24, 'loginname', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (161, 24, 'content', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (162, 24, 'contact', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (163, 27, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (164, 27, 'clienttype', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (165, 27, 'titleid', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (166, 27, 'context', 1, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (167, 27, 'lgname', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (168, 27, 'ctype', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (169, 27, 'address', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (170, 26, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (171, 26, 'titleid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (172, 26, 'commentid', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (173, 26, 'page', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (174, 26, 'ctype', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (175, 26, 'type', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (176, 26, 'uuid', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (177, 28, 'page', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (178, 28, 'sendtime', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (179, 28, 'flag', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (180, 28, 'type', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (181, 28, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (182, 37, 'citycode', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (183, 37, 'cityname', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (184, 36, 'flag', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (185, 36, 'parcode', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (186, 36, 'citycode', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (187, 43, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (188, 43, 'clienttype', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (189, 43, 'uuid', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (190, 43, 'version', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (191, 43, 'resourceid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (192, 42, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (193, 42, 'clienttype', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (194, 42, 'uuid', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (195, 42, 'version', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (196, 42, 'parid', 0, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (197, 44, 'resourceid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (198, 44, 'pageflag', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (199, 44, 'pagetime', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (200, 44, 'reqnum', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (201, 44, 'lastid', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (202, 44, 'count', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (203, 44, 'page', 0, 1) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (204, 29, 'siteid', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (205, 29, 'colId', 1, 0) ";
		query = updateService.createQuery(sql);
		updateService.execSql(query);

		sql = "INSERT INTO  jmp_interfacesparam  VALUES (206, 6, 'channelid', 1, 0) ";
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
