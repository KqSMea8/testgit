<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="h" uri="/hanweb-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>分类列表树形菜单</title>
<h:head tree="true" iconfont="true"></h:head>
<script type="text/javascript">
$(function() {
	var zNodes = ${tree};
	var setting = {
		async : {
			enable : true,					       
			url : '${contextPath}/manager/typemenu/menuwithurlfortype_search.do',
			autoParam : [ "id=typeId", "isDisabled" ]
		}
	};
	$('#tree').tree(setting, zNodes);

});

		

</script>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	background-color: #F6F6F6;
}
*{
	overflow:hidden;
}

#searchbtn {
	cursor: pointer;
}

/* .ztree-contextmenu { */
/* 	position: absolute; */
/* 	visibility: hidden; */
/* 	top: 0; */
/* 	cursor: pointer; */
/* 	list-style: none outside none; */
/* 	background-color: #F9F9F9; */
/* 	border: 1px solid #CCC; */
/* 	border-radius: 5px; */
/* 	box-shadow: 1px 2px 1px #EFEFEF; */
/* } */

/* .ztree-contextmenu li { */
/* 	width: auto; */
/* 	min-width: 100px; */
/* 	_width: 100px; */
/* 	line-height: 30px; */
/* 	text-align: left; */
/* 	padding: 0 10px; */
/* } */

/* #jqContextMenu { */
/* 	box-shadow: 2px 2px 1px #EFEFEF; */
/* } */

</style>
</head>
<body>
	
	<div id="treewrap"
		style="position: absolute; left: 0; right: 0; overflow: auto;">
		<ul id="tree" class="ztree"></ul>
	</div>
</body>
</html>