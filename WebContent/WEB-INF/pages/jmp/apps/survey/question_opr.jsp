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
<title>布局管理</title>
<h:head pagetype="dialog" multiselect="true" menu="true" tree="true"
	validity="true" checkpwd="true" tabs="true" tip="true" select="true" upload="true" calendar="true"></h:head>
<link rel="stylesheet" href="${contextPath}/ui/widgets/colorpicker/css/colorpicker.css" type="text/css" />
<script type="text/javascript" src="${contextPath}/ui/widgets/colorpicker/colorpicker.js"></script>
<script>
	
	$(function() {
		var url = "${url}"; 
		$("#oprform").validity(
			function() {
				var type = $('#type').val();   
				if(type != '1'){
					$("#name").require("调查主题名称不能为空") 
					.match('username1', "调查主题名称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾")
					.maxLength(33, "调查主题名称不能超过100个字");
				}
			}, {type:'iframe'});
	});
</script>
<style type="text/css">
	.input-textarea {
		resize: none;
	}
</style>
</head>
<body>
	<form action="${url }" method="post" id="oprform" name="oprform" enctype="multipart/form-data">
		<input type="hidden" name="iid" value="${question.iid }">
		<input type="hidden" name="orderId" value="${question.orderId }">
		<input type="hidden" name="siteId" value="${question.siteId}">
		<input type="hidden" name="surveyId" value="${question.surveyId}">
		
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0"
				class="table">
				
				<tr>
					<td align="left" class="label">调查问题 </td>
					<td class="required">*</td>
					<td>
						<input type="text" id="name" name="name" class="input-text" value="${question.name }"></input>
					</td>
				</tr>
				<tr>
					<td align="left" class="label">描述 </td>
					<td class="required"></td>
					<td>
					    <textarea  class="input-textarea" name="abs" id="abs" >${question.abs}</textarea>
					</td>
				</tr>
				<tr>
					<td align="left" class="label">是否显示</td>
					<td class="required">&nbsp;</td>
					<td>
					 	<select name="isShow" id="isShow" data-value="${question.isShow}">
						    <option value="0">显示</option>
							<option value="1">不显示</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="left" class="label">显示样式</td>
					<td class="required">&nbsp;</td>
					<td>
					 	<select name="type" id="type" data-value="${question.type}">
						    <option value="1">单选</option>
							<option value="2">多选</option>
							<option value="3">文本</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="left" class="label">是否必填</td>
					<td class="required">&nbsp;</td>
					<td>
					 	<select name="isWrite" id="isWrite" data-value="${question.isWrite}">
						    <option value="0">必填</option>
							<option value="2">不必填</option>
						</select>
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