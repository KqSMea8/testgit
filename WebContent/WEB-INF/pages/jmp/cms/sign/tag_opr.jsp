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
<title>角标管理</title>
<h:head pagetype="dialog" multiselect="true" tree="true" validity="true" select="true" calendar="true"></h:head>
<link rel="stylesheet" href="${contextPath}/ui/widgets/colorpicker/css/colorpicker.css" type="text/css" />
<script type="text/javascript" src="${contextPath}/ui/widgets/colorpicker/colorpicker.js"></script>
<script type="text/javascript">
	$(function() { 
		$("#oprform").validity(function() {
				$("#dname").require("角标名称不能为空").match('username1',
						"角标名称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾").maxLength(10,
						"角标名称不能超过10个字"); 
				$("#color").require("请选择背景颜色");
		},{
			success:function(result){
			if(result.success){
				closeDialog(true);
			}else{
				alert(result.message);
			}
		}
		});
		var color = $("#color").val();
		if(color!=""){
			 $("#colorSelector1").css("background-color",color);
		}
		
		//颜色选择器 
		$('#colorSelector').ColorPicker({
			color: '#008fd5',
			onShow: function (colpkr) {
				$(colpkr).fadeIn(100);
				return false;
			},
			onHide: function (colpkr) {
				$(colpkr).fadeOut(100);
				return false;
			},
			onChange: function (hsb, hex, rgb) {
				$('#colorSelector div').css('backgroundColor', '#' + hex);
				$('#color').val("#"+hex); 
			}
		});
	});
</script>
<style type="text/css">
	#colorSelector{position:relative;width:36px;height:36px;
		background:url(${contextPath}/ui/widgets/colorpicker/images/select2.png);
		display: inline;padding-bottom:20px;}
	#colorSelector div{position:absolute;top:-6px;left:-2px;width:30px;height:30px;
		background:url(${contextPath}/ui/widgets/colorpicker/images/select2.png) center;} 
		.input-textarea {
			resize: none;
		}
</style>
</head>
<body>
	<form action="${url }" method="post" id="oprform">
		<input type="hidden" id="iid" name="iid" value="${sign.iid}">
		<input type="hidden" id="siteId" name="siteId" value="${sign.siteId }">
		<input type="hidden" id="orderId" name="orderId" value="${sign.orderId }">
		<input type="hidden" id="mid" name="mid" value="${sign.mid }">
		<input type="hidden" id="colId" name="colId" value="${sign.colId }">
		<input type="hidden" id="mname" name="mname" value="${sign.mname }">
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0"
				class="table">
				<tr>
					<td align="right" class="label">角标名称</td>
					<td class="required">*</td>
					<td><input type="text" id="dname" name="dname" 
						class="input-text" value="${sign.dname }" /></td>
				</tr> 
				<tr>
					<td align="right" class="label">背景颜色 </td>
					<td class="required">*</td>
					<td><input type="text" id="color" name="color"
						class="input-text" maxlength="7" class="input-text"
						value="${bgColor}" style="width:266px;" />
						<div id="colorSelector"><div id="colorSelector1" style="background-color: #008fd5"></div></div>
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