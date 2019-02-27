<%@page import="com.hanweb.common.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="h" uri="/hanweb-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${siteName }</title>
<h:head cookie="true" dialog="true" message="true" iconfont="true"></h:head>
<h:import type="js" path="/ui/script/ui.js"></h:import>
<style type="text/css">
#colpage {
	width: 1000px;
	height: 100%;
}
#page-wrap {
	position: absolute;
	height: 100%;
	margin-left: 200px;
}
#menu {
	width: 200px;
	height: 100%;
}
#menu-wrap {
	position: absolute;
	margin: 0px;
	border-right: 3px solid #EFEFEF;
	height: 100%;
}
html,body {
	height: 100%;
	overflow: hidden;
}

</style>
</head>
<body>
	<div id="page-wrap">
		<iframe name="colpage" src="../../numsense/col/list.do?" id="colpage" frameborder="0" ></iframe>
	</div>
	<div id="menu-wrap">
		<iframe name="menu" class="menu" src="../../numsense/menu/menu_show.do?" id="menu" frameborder="0" ></iframe>
	</div>
</body>
</html>