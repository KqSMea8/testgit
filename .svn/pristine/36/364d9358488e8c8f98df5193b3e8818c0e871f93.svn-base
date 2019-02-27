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
<title>评论详情</title>
<h:head pagetype="dialog" multiselect="true" tree="true" validity="true"></h:head>
</head>
<body>
	<div id="dialog-content">
		<table border="0" align="center" cellpadding="10" cellspacing="0"class="table">
			<tr>
				<td align="right" class="label">${typeName}</td>
				<td class="required">&nbsp;</td>
				<td><input disabled="disabled" type="text" id="createTime" name="createTime" maxlength="33"
					class="input-text" value="${title }" /></td>
			</tr>
			<tr>
				<td align="right" class="label">内容</td>
				<td>&nbsp;</td>
				<td><textarea style="height: 180px" disabled="disabled" id="content" name="content" class="input-textarea">${comment.content }</textarea>
				</td>
			</tr>
		</table>
	</div>
	<div id="dialog-toolbar"> 
		<div id="dialog-toolbar-panel">
			<input type="button" class="btn" value="关闭" onclick="closeDialog();" />
		</div>
	</div>
</body>
</html>