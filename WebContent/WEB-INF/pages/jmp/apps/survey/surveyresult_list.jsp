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
<title>调查结果列表</title>
<h:head pagetype="page" grid="true"></h:head>
<script type="text/javascript">

function goDetail(iid, type) {
	openDialog('plugins/surveyresult/detail_list.do?iid=' + iid + "&type=" + type, 1200, 550, {
		title : '调查问题明细'
	});
}


</script>
</head>
<body>
	<div id="page-title">
		调查结果查看/ 
		<c:if test="${surveyresult.iid == 0}">
			<span id="page-location">调查结果查看</span>
		</c:if>
		<c:if test="${surveyresult.iid > 0}">
			调查结果查看/ <span id="page-location">${surveyresult}</span>
		</c:if>
		<c:if test="${surveyresult.iid<0}">
			调查结果查看 / <span id="page-location">检索结果</span>
		</c:if>
	</div>
	<div id="page-content">
		<h:grid></h:grid>
	</div>
</body>
</html>