<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="h" uri="/hanweb-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>群组管理</title>
<h:head pagetype="dialog" validity="true"></h:head>
<script>
	$(function() {
		$("#oprform").validity(function() {
			$("#name").require("群组名称不能为空").match('username1', "角色名称只能由字母、数字、下划线、中文组成").maxLength(33, "角色名称不能超过33个字");
			$("#spec").maxLength(85, "角色简介不能超过85个字");
		}, {
			success : function(result) {
				if (result.success) {
					closeDialog(true);
				} else {
					alert(result.message);
				}
			}
		});
	});
</script>
</head>
<body>
<body>
	<form action="${url }" method="post" id="oprform" name="oprform">
		<input type="hidden" name="iid" id="iid" value="${peoplelist.iid }" />
		<input type="hidden" name="type" id="type" value="${peoplelist.type }" />
		<div id="dialog-content">
			<table border="0" align="center" class="table">
				<tr>
					<td align="right" class="label">群组名称</td>
					<td class="required">*</td>
					<td>
						<input type="text" id="name" name="name" class="input-text" value="${peoplelist.name  }" />&nbsp;
					</td>
				</tr>
				<tr>
					<td align="right" class="label">群组简介</td>
					<td>&nbsp;</td>
					<td>
						<textarea id="spec" name="spec" class="input-textarea">${peoplelist.spec}</textarea>
					</td>
				</tr>
			</table>
		</div>
		<div id="dialog-toolbar">
			<div id="dialog-toolbar-panel">
				<input type="submit" class="btn btn-primary" value="保存" />
				<input type="button" class="btn" value="取消" onclick="closeDialog();" />
			</div>
		</div>
	</form>
</body>
</html>