<%@page language="java" contentType="text/html; charset=UTF-8"
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
<title>应用列表</title>
<h:head pagetype="page" grid="true" dialog="true" validity="true" tree="true"></h:head> 
<script type="text/javascript">
	function toolbarAction(action) {
		switch (action) {
			
		case 'order': 
			openDialog('lightapp/sortforcol_show.do?lightAppIds=${lightAppIds}&colId=${colId}', 470, 540, {
				title : '应用排序'
			});
			break; 
		}
	}
	
</script>
</head>
<body>
	<div id="page-title">
		 <span id="page-location">应用管理</span>
	</div>
	<div id="page-content">    
		<h:grid></h:grid>
	</div>
</body>
</html>