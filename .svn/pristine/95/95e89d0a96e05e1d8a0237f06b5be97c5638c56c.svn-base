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
<title>调查答案列表</title>
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
		//			Tree.getInstance().removeNode(result.params.remove);
					location.reload();
				}else{
					alert(result.message);
				}
			}});
		});
		break;
	case 'add':
		openDialog('plugins/survey/answer/add_show.do?questionId=${question.iid}&surveyId=${question.surveyId}', 560, 500, {
			title : '调查答案新增'
		});
		break;
	case 'back':
		var url = encodeURIComponent(location.href);
		var listUrl ="${contextPath}/manager/plugins/survey/question/list.do?surveyId=${survey.iid}&fromutl="+url; 
		location.href = listUrl; 
		break;
	}
}

function edit(iid, name) {
	openDialog('plugins/survey/answer/modify_show.do?iid=' + iid, 560, 500, {
		title : '调查答案编辑'
	});
}


</script>
</head>
<body>
	<div id="page-title">
		调查管理 / ${survey.name}/ ${question.name}
		<c:if test="${answer.iid == 0}">
			<span id="page-location">调查管理/ ${survey.name}</span>
		</c:if>
		<c:if test="${answer.iid > 0 && answer.name != ''}">
			调查管理/ <span id="page-location">${question}</span>
		</c:if>
		<c:if test="${answer.iid<0}">
			调查管理 / <span id="page-location">检索结果</span>
		</c:if>
	</div>
	<div id="page-content">
		<h:grid></h:grid>
	</div>
</body>
</html>