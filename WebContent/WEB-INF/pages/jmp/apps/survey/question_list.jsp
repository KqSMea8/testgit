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
<title>调查问题列表</title>
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
			ajaxSubmit("remove.do?ids=" + ids + "&name=" + getCheckedAttr("name")+"&surveyId=${survey.iid}", {success:function(result){
				if(result.success){
			//		Tree.getInstance().removeNode(result.params.remove);
					location.reload();
				}else{
					alert(result.message);
				}
			}});
		});
		break;
	case 'add':
		openDialog('plugins/survey/question/add_show.do?surveyId=${survey.iid}', 560, 550, {
			title : '调查问题新增'
		});
		break;
	case 'order': 
		openDialog('plugins/survey/question/sort_show.do?surveyId=${survey.iid}', 470, 540, {
			title : '信息排序'
		});
		break; 
	case 'back':
		var url = encodeURIComponent(location.href);  
		var listUrl ="${contextPath}/manager/plugins/survey/list.do?fromutl="+url; 
		location.href = listUrl; 
		break;
	}
}

function edit(iid, name) {
	openDialog('plugins/survey/question/modify_show.do?iid=' + iid, 560, 550, {
		title : '调查问题编辑'
	});
}


function surveyAnswer(iid, type, surveyid){
	if(type == 3){
		alert("文本类型不能增加答案");
	}else{
		var url = encodeURIComponent(location.href);  
		var listUrl ="${contextPath}/manager/plugins/survey/answer/list.do?questionId="+iid+"&surveyId="+surveyid+"&fromutl="+url; 
		location.href = listUrl; 
	}
}


</script>
</head>
<body>
	<div id="page-title">
		调查管理 / ${survey.name} 
		<c:if test="${question.iid == 0}">
			<span id="page-location">调查管理</span>
		</c:if>
		<c:if test="${question.iid > 0 && question.name != ''}">
			调查管理/ <span id="page-location">${question}</span>
		</c:if>
		<c:if test="${question.iid<0}">
			调查管理 / <span id="page-location">检索结果</span>
		</c:if>
	</div>
	<div id="page-content">
		<h:grid></h:grid>
	</div>
</body>
</html>