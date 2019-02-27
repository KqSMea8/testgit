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
<title>新增版本</title>
<h:head pagetype="dialog" multiselect="true" tree="true" validity="true" select="true" upload="true"></h:head>
<script type="text/javascript">
	$(function() { 
		$("#oprform").validity(function() {
			$("#version").require("版本号不能为空").assert(function() {
			    var pattern = /^([0-9.]+)$/; 
				var s = $("#version").val();  
				if (pattern.test(s))  {
					return true;
				} else {
					return false;
				}
			}, '版本号格式不正确');
			$("#downUrl").require("下载地址不能为空");
			$("#msg").require("说明不能为空");  
			if($.trim($("#msg").val()).length > 0){
				$("#msg").maxLength(660,"内容不能超过660个字");
			}
		}, {type:'iframe'});
});
</script>
</head>
<body>
	<form action="${url }" method="post" id="oprform">
		<input type="hidden" name="iid" value="${version.iid }"/>
		<input type="hidden" name="siteId" value="${version.siteId}"/>
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0"
				class="table">
				<tr>
					<td align="right" class="label">客户端类型</td>
					<td class="required">&nbsp;</td>
					<td><select id="clientType" name="clientType" data-value="${version.clientType }">
						<option value="1">Windows</option>
						<option value="2">iPhone</option>
						<option value="3">Android</option>
						<option value="4">iPad</option>
					</select></td>
				</tr>
				<tr>
					<td align="right" class="label">版本号</td>
					<td class="required">*</td>
					<td><input type="text" id="version" name="version" value="${version.version }" class="input-text" maxlength="33"></td>
				</tr>
				<tr>
					<td align="right" class="label">下载地址</td>
					<td class="required">*</td>
					<td><input type="text" id="downUrl" name="downUrl" value="${version.downUrl }" class="input-text" maxlength="500"></td>
				</tr>
				<tr>
					<td align="right" class="label">更新类型</td>
					<td class="required"></td>
					<td><select name="updateType" id="updateType" data-value="${version.updateType}">
							<option value="1">建议更新</option>
							<option value="2">强制更新</option> 
						</select>
                    </td>
				</tr>
				<tr>
					<td align="right" class="label">说明</td>
					<td class="required">*</td>
					<td><textarea id="msg" name="msg" class="input-textarea">${version.msg }</textarea>
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