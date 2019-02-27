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
<title>字段管理</title>
<h:head pagetype="dialog" menu="true"  select="true" validity="true"   tip="true"></h:head>
<script>
	$(function() {
		$("#oprform").validity(
				function() {
					$("#name").require("字段名称不能为空") 
					.match('username1', "字段名称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾")
					.maxLength(4, "字段名称不能超过4个字");
					$("#fieldName").match('username', "数据库字段只能由字母、数字、下划线组成")
					.maxLength(100, "数据库字段名称不能超过80个字");
				},{
					success:function(result){
					if(result.success){
						closeDialog(true);
					} else {
						alert(result.message);
					}
				}
			});
	});
	 
	function success(msg){ 
		closeDialog(true);
	}
	
	function fail(msg){
		alert(msg);
	}
</script>
</head>
<body>
	<form action="${url }" method="post" id="oprform" name="oprform">
		<input type="hidden" name="iid" value="${field.iid }">
		<input type="hidden" name="siteId" id="siteId" value="${field.siteId}" /> 
		<input type="hidden" name="orderId" value="${field.orderId }"> 
		<c:if test="${field.fieldType==2}">	
			<input type="hidden" name="fieldType" value="${field.fieldType}"> 
		</c:if>
		<c:if test="${field.fieldType!=2}">	
			<input type="hidden" name="fieldType" id="fieldType" value="0"></input>
		</c:if>
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0" class="table"> 
				<tr>
					<td align="left" class="label">显示名称 </td>
					<td class="required">*</td>
					<td>
						<input type="text" id="name" name="name" class="input-text" value="${field.name}"></input>
					</td>
				</tr> 	 
				<tr>
					<td align="left" class="label">数据库字段</td>
					<td class="required"></td>
					<td>  
				        <c:choose>
		              		<c:when test="${url=='modifyfield_submit.do'}"> 
			              		 <input type="text" id="fieldName" name="fieldName" class="input-text" value="${field.fieldName}" disabled="disabled"></input>
							</c:when>
							<c:otherwise> 
			              		 <input type="text" id="fieldName" name="fieldName" class="input-text" value=""></input>
			              		 <h:tip title="数据库中对应的字段名称，只能输入数字，字母或者下划线，若不填写将自动生成"></h:tip>
							</c:otherwise>
						</c:choose>  
					</td>
				</tr>
				<tr>
					<td align="left" class="label">长度 </td>
					<td class="required"></td>
					<td>
					<c:if test="${url=='modifyfield_submit.do'}">	
						<select id="fieldLength" name="fieldLength" data-value="${field.fieldLength}" disabled="disabled">
						<option value="255">255</option>
							<option value="600">600</option> 
							<option value="1000">1000</option> 
						</select>
					</c:if>
					<c:if test="${url=='addfield_submit.do'}">	
						<select id="fieldLength" name="fieldLength" data-value="${field.fieldLength}">
						<option value="255">255</option>
							<option value="600">600</option> 
							<option value="1000">1000</option> 
						</select>
					</c:if>	 
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