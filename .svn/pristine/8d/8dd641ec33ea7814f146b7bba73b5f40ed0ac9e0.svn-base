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
<title>附件分类</title>
<h:head pagetype="dialog" multiselect="true" tree="true" validity="true"></h:head>
<script type="text/javascript">
	$(function() { 
		$("#oprform").validity(function() {
				$("#name").require("分类名称不能为空").match('username1',
						"分类名称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾").maxLength(33,
						"分类名称不能超过33个字");
				if($.trim($("#spec").val()).length > 0){
					$("#spec").maxLength(300,"分类描述不能超过300个字");
				}
		},{
			success:function(result){
			if(result.success){
				var treeObj = Tree.getInstance();
				treeObj.refreshNode(result.params.refresh);
				closeDialog(true);
			}else{
				alert(result.message);
			}
		}
		});
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
		<input type="hidden" id="iid" name="iid" value="${docCol.iid}">
		<input type="hidden" id="siteId" name="siteId" value="${docCol.siteId }">
		<input type="hidden" id="orderId" name="orderId" value="${docCol.orderId }">
		<input type="hidden" id="pid" name="pid" value="${docCol.pid}">
		
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0"
				class="table">
				<tr>
					<td align="right" class="label">分类名称</td>
					<td class="required">*</td>
					<td><input type="text" id="name" name="name" maxlength="33"
						class="input-text" value="${docCol.name }" /></td>
				</tr>
				<tr>
					<td align="right" class="label">分类描述</td>
					<td>&nbsp;</td>
					<td><textarea id="spec" name="spec" class="input-textarea">${docCol.spec }</textarea>
					</td>
				</tr>
			</table>
		</div>
		<div id="dialog-toolbar">
			<div id="dialog-toolbar-panel">
				<input type="submit" class="btn btn-primary" value="保存" /> <input
					type="button" class="btn" value="取消" onclick="closeDialog();" />
			</div>
		</div>
	</form>
</body>
</html>