<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="h" uri="/WEB-INF/tag/hanweb-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组件编辑</title>
<h:head dialog="true" message="true" iconfont="true" pagetype="dialog" validity="true" 
		checkpwd="true" placeholder="true"></h:head>
<script>

	$(function(){
		var random = parseInt(Math.random()*100000);
		if("${url }" != ""){
			$('#qrcode').attr('src',('${url }' + '?random=' + random));
		}
	});
	
</script>
</head>
<body>
		<div style="width: 100%; height: 100%;font-size: 16px; color: #306ca2;" align="center">
			<c:if test="${code == 'app'}">
				<img id="qrcode" alt="" src="" style="width: 250px;">
			</c:if>
			<c:if test="${code == 'jssdk'}">
				<c:if test="${variable == 'ios'}">
					<img id="qrcode" alt="" src="${contextPath }/resources/jmopen/images/helper/ios-helper.png" style="width: 250px;">
				</c:if>
				<c:if test="${variable == 'android'}">
					<img id="qrcode" alt="" src="${contextPath }/webapp/ylzs/android.png" style="width: 250px;">
				</c:if>
			</c:if>
		</div>
</body>
</html>