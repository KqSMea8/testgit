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
<title>意见反馈</title>
<h:head pagetype="dialog" multiselect="true" tree="true" validity="true"></h:head>
<script type="text/javascript">
	$(function() { 
		$("#oprform").validity(function() {
		},{
			success:function(result){
				if(result.success){
					closeDialog(true);
				}else{
					alert(result.message);
				}
			}
		});
		if('${isSuccess}'){
			refreshParentWindow();
		}
	});
</script>
<style type="text/css">
	.input-textarea {
		resize: none;
	}
</style>
</head>
<body>
	<form action="${url }" method="post" id="oprform">
	<input type="hidden" id="iid" name="iid" value="${feedBack.iid }">
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" class="table" width="100%" 
			       style="table-layout:fixed;word-wrap:break-word;word-break:break-all">
				<tr>
					<td align="center" class="label" width="150">用户名</td>
					<td  width="4px">&nbsp;</td>
					<td>${feedBack.loginName}</td>
				</tr>
				
				<tr>
					<td align="center" class="label">联系方式</td>
					<td>&nbsp;</td>
					<td>${feedBack.contact}</td>
				</tr>
				
				<tr>
					<td align="center" class="label">客户端类型</td>
					<td>&nbsp;</td>
					<td>
						<c:choose>
							<c:when test="${feedBack.clientType=='2'}">
								iPhone
							</c:when>
							<c:when test="${feedBack.clientType=='3'}">
								Android
							</c:when>
							<c:otherwise>
								iPad
							</c:otherwise>
						</c:choose> 
					</td>
				</tr>
				
				<tr>
					<td align="center" class="label">创建时间</td>
					<td>&nbsp;</td>
					<td>${strTime}</td>
				</tr>
				
				<tr>
					<td align="center" class="label">反馈信息</td>
					<td>&nbsp;</td>
					<td><div style="overflow:auto;height:130px">${feedBack.content}</div></td>
				</tr>
			</table>
		</div>
		<div id="dialog-toolbar">
			<div id="dialog-toolbar-panel">
				<input type="button" class="btn btn-primary" value="取消" onclick="closeDialog();" />
			</div>
		</div>
	</form>
</body>
</html>