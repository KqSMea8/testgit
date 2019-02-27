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
<title>展现类型操作</title>
<h:head pagetype="dialog" menu="true"  select="true"
	validity="true"   tip="true"></h:head>
<script>
	$(function() {
		$("#oprform").validity(
				function() {
					$("#fieldName").require("展现名称不能为空")
					.match('username1', "数据库字段只能由字母、数字、下划线组成")
					.maxLength(100, "数据库字段名称不能超过80个字");
					$("#fieldKey").greaterThan(100,'所输入的键值必须是大于100的整数');
					$("#fieldKey").match(/^[0-9]+$/, "所输入的键值只能是数字");
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
	<input type="hidden" name="iid" value="${colfield.iid }">
    <input type="hidden" name="siteId" value="${siteId}">
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0" class="table"> 
				<tr>
					<td align="left" class="label">展现类型</td>
					<td class="required"></td>
					<td>
					<c:if test="${url=='modifycolfield_submit.do'}">	
						<select id="fieldType" name="fieldType" data-value="${colfield.fieldType}" disabled="disabled">
							<option value="1">子栏目展现方式（虚拟栏目）</option> 
							<option value="2">信息布局（普通栏目）</option> 
							<option value="3">信息列表展现方式（普通栏目）</option> 
							<option value="4">信息内容展现方式（普通栏目）</option> 
							<option value="5">互动类型（互动栏目）</option> 
						</select>
					</c:if>
					<c:if test="${url=='addcolfield_submit.do'}">	
						<select id="fieldType" name="fieldType" data-value="${colfield.fieldType}">
							<option value="1">子栏目展现方式（虚拟栏目）</option> 
							<option value="2">信息布局（普通栏目）</option> 
							<option value="3">信息列表展现方式（普通栏目）</option> 
							<option value="4">信息内容展现方式（普通栏目）</option> 
							<option value="5">互动类型（互动栏目）</option> 
						</select>
						</select>
					</c:if>		 
					</td>
				</tr>
				<tr>
					<td align="left" class="label">键值</td>
					<td class="required">*</td>
					<td>
						<input type="text" id="fieldKey" name="fieldKey" class="input-text" value="${colfield.fieldKey}"></input>
						<h:tip title="请输入大于100的整数"></h:tip>
					</td>
				</tr> 	 
				<tr>
					<td align="left" class="label">展现名称 </td>
					<td class="required">*</td>
					<td>
						<input type="text" id="fieldName" name="fieldName" class="input-text" value="${colfield.fieldName}"></input>
					</td>
				</tr> 	 
				<tr>
					<td align="left" class="label">是否启用</td>
					<td class="required"></td>
					<td>
						<input type="checkbox" id="showList" name="showList"
								 value="1" data-value="${colfield.showList}" />
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
