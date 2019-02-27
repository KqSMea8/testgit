<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="h" uri="/hanweb-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>栏目列表树形菜单</title>
<h:head tree="true"></h:head>
<script type="text/javascript">
	$(function() {
		var zNodes = ${tree};
		var setting = {
			async : {
				enable : true,
				url : '${contextPath}/manager/menu/menuwithurlforaudit_search.do',
				autoParam : [ "id=colId", "isDisabled" ]
			},view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom
			}
					
		};
		$("#tree").tree(setting, zNodes);
	});

	function addHoverDom(treeId, treeNode) {
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#diyBtn_"+treeNode.id).length>0) return; 
		aObj.attr('title', "栏目id："+treeNode.id+"\n栏目名称: "
				+treeNode.name+"\n栏目状态: "+treeNode.attr.state+"\n栏目类型: "+treeNode.attr.typename); 
	};
	function removeHoverDom(treeId, treeNode) {
		$("#diyBtn_"+treeNode.id).unbind().remove(); 
	};
		
</script>
<style type="text/css">
body {
	margin: 5px;
	background-color: #F6F6F6;
}
.ztree * {
	font-size: 14px;
}

.ztree li a, .ztree li a.curSelectedNode {
	line-height: 23px;
	height: 23px;
}

.ztree li span.button{
	margin-top: 4px;
}
</style>
</head>
<body>
	<ul id="tree" class="ztree"></ul>
</body>
</html>