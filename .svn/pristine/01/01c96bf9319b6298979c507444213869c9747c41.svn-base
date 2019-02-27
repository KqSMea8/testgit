<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="h" uri="/hanweb-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户详情</title>
<h:head pagetype="dialog"></h:head>
<style type="text/css">
td{
	overflow:hidden;
	text-overflow:ellipsis;
	white-space:nowrap; 
}
</style>
</head>
<body>
		<div id="dialog-content">
			<table align="center" cellpadding="10" cellspacing="0"
				class="table" style="table-layout:fixed;width:450px;border:1px solid #D5D5D5;" rules="all">     
				<tr>
					<td align="right" class="label">姓名</td>
					<td width="120px" title='${user.name }'>${user.name }</td> 
					<td align="right" class="label">登录名</td>
					<td width="120px" title='${user.loginName }'>${user.loginName }</td>
				</tr> 
				<tr>
					<td align="right" class="label">所属机构</td>
					<td title='${user.groupName}'>${user.groupName}</td>
					<td align="right" class="label"">职务</td>
					<td title='${user.headship}'>${user.headship}</td>
				</tr>
				<tr>
				<td align="right" class="label">固定电话</td>
					<td title='${user.phone}'>${user.phone}</td>
					<td align="right" class="label">Email</td>
					<td title='${user.email}'>${user.email}</td>
				</tr>
				<tr>
				<td align="right" class="label">移动电话</td>
					<td title='${user.mobile}'>${user.mobile}</td>
					<td align="right" class="label">联系方式</td>
					<td title='${user.contact}'>${user.contact}</td>
					
				</tr>
				<tr>
					<td align="right" class="label">联系地址</td>
					<td colspan="3" title='${user.address}'>${user.address}</td> 
				</tr>
			</table>
		</div>
		<div id="dialog-toolbar">
			<div id="dialog-toolbar-panel"><input
					type="button" class="btn" value="关闭" onclick="closeDialog();" />
			</div>
		</div>
</body>
</html>