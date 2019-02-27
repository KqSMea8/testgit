<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="h" uri="/hanweb-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>调查列表</title>
<h:head pagetype="page" grid="true"></h:head>
<script type="text/javascript">
function toolbarAction(action) {
	switch (action) {
	case 'remove':
		var ids = getCheckedIds();
		if (ids == "") {
			alert("未选中任何记录");
			return;
		}
		confirm("您确定要删除这" + ids.split(",").length + "条记录吗", function() {
			ajaxSubmit("remove.do?ids=" + ids, {success:function(result){
				if(result.success){
					Tree.getInstance().removeNode(result.params.remove);
					location.reload();
				}else{
					alert(result.message);
				}
			}});
		});
		break;
	case 'add':
		openDialog('plugins/survey/add_show.do', 560, 550, {
			title : '调查新增'
		});
		break;
	case 'order': 
		openDialog('plugins/survey/sort_show.do?', 470, 540, {
			title : '信息排序'
		});
		break; 
	case 'quote':
		var ids = getCheckedIds();
		if (ids == "") {
			alert("未选中任何记录");
			return;
		}
		openDialog('plugins/survey/quote_show.do?ids='+ids, 350, 320, {
			title : '调查引用'
		});
		break;
	}
	
}
function edit(iid, name) {
	openDialog('plugins/survey/modify_show.do?iid=' + iid, 560, 550, {
		title : '调查编辑'
	});
}


</script>
</head>
<body>
	<div id="page-title">
		调查管理/ 
		<c:if test="${survey.iid == 0}">
			<span id="page-location">调查管理</span>
		</c:if>
		<c:if test="${survey.iid > 0 && survey.name != ''}">
			调查管理/ <span id="page-location">${survey}</span>
		</c:if>
		<c:if test="${survey.iid<0}">
			调查管理 / <span id="page-location">检索结果</span>
		</c:if>
	</div>
	<div id="page-content">
		<h:grid></h:grid>
	</div>
</body>
</html>