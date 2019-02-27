<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="h" uri="/WEB-INF/tag/hanweb-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统参数</title>
<h:head pagetype="dialog" tabs="true" tip="true" validity="true" select="true"></h:head>
<script>
	$(function(){
		var url = "${url}";
		if(url =="update_submit.do" ){
			var typeId = "${config.infoSaveType}";
			if(typeId == 0){
				$("#infonum").show();
				$("#infoday").hide();
			}else{
				$("#infonum").hide();
			    $("#infoday").show();
			}
		}
		$('#oprform').validity(function() {
			$('#url').require('接口地址必须填写').maxLength(100, "接口地址不能超过100个字");
			$('#name').require('用户名必须填写').maxLength(24, "用户名不能超过24个字");
			$('#pwd').require('密码必须填写').maxLength(24, "密码不能超过24个字");
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

	//验证url
	function testUrl(){
		var url = $("#url").val();
		if (url == "" || url.length == 0) {
			alert("请填写接口地址！");
		} else {
			ajaxSubmit("testurl.do?url="+url, {
				success:function(result){
					if(result.success){
						alert(result.message);
					}else{
						alert(result.message);
					}
				}
			});
		}
	}
</script>
</head>
<body>
	<form action="${url}" method="post" id="oprform" name="oprform">
		<input type="hidden" name="iid" id="iid" value="${ditch.iid }" /> 
		<input type="hidden" name="siteId" id="siteId" value="${ditch.siteId }" />
		<input type="hidden" name="enable" id="enable" value="${ditch.enable }" />
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0" class="table">
				<tr>
					<td align="right" class="label" width="90px">同步系统类型</td>
					<td class="required">*</td>
					<c:if test="${url=='add_submit.do'}"> 
						<td>
							<select name="syntype" id="syntype" data-value="${ditch.syntype}">
							    <option value="0">JGET</option>
							    <option value="1">JCMS</option>
							</select>
						</td>
						<td width="80px"></td>
					</c:if>
					<c:if test="${url=='modify_submit.do'}">
						<td>
						<select name="syntype" id="syntype" data-value="${ditch.syntype}" disabled="disabled">
						    <option value="0">JGET</option>
						    <option value="1">JCMS</option>
						</select>
						</td>
						<td width="80px"></td>
					</c:if>
					
				</tr>
				
				<tr>
					<td align="right" class="label">接口地址</td>
					<td class="required" width="15px">*</td>
					<td><input type="text" id="url" name="url" class="input-text" value="${ditch.url }" style="width:300px"/></td>
					<td><input type="button" value="验证链接" class="btn btn-primary" onclick="testUrl();"/></td>
				</tr>
				
				<tr>
					<td align="right" class="label">用户名</td>
					<td class="required" width="15px">*</td>
					<td><input type="text" id="name" name="name" class="input-text" value="${ditch.name }" style="width:300px"/></td>
				</tr>
			
				<tr>
		          	<td align="right" class="label">密码</td>
					<td class="required" width="15px">*</td>
					<td><input type="password" id="pwd" name="pwd" class="input-text" value="${ditch.pwd }" style="width:300px"/></td>
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