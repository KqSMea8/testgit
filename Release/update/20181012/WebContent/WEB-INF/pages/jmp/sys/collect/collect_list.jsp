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
<title>渠道列表</title>
<h:head pagetype="page" grid="true" toggle="true"></h:head>
<script type="text/javascript">

</script>
</head>
<body>
	<div id="page-title">
		<span id="page-location">收藏列表</span>
	</div>
	<div id="page-content">
		<div class="grid-advsearch">
			<form>
				用户名<input name="username" type="text" class="input-text" value="${username}" style="width: 120px; margin: 0 30px 0 10px;" /> 
				信息名称<input name="infoname" type="text" class="input-text" value="${infoname}" style="width: 120px; margin: 0 30px 0 10px;" />
				<input type="button" class="btn btn-primary" value="检索" style="margin-right:5px;"/>
				<input type="button" class="btn advsearch-cancel" value="返回" />
				<div class="line-dotted"></div>
			</form>
		</div>
		<h:grid></h:grid>
	</div>
</body>
</html>