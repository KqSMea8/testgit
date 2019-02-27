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
<title>应用管理</title>
<h:head pagetype="dialog"  validity="true" select="true" upload="true" tree="true"></h:head>
<script type="text/javascript">
	$(function() {
		
		
		$('#oprform').validity(
				function() {
					$("#name").require("应用名称不能为空").match('username1',
					"应用名称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾").maxLength(10,
					"分类名称不能超过10个字");
			$("#createTime").require('创建时间不能为空');
			var kind = $('#kind').val();
			if(kind=='m'){
			   $("#url").require('接口地址不能为空');
			}
			var type = $('#type').val();
			
				},{type:'iframe'}
			);
		
		$.fn.preview = function(path){
			var xOffset = 10;
			var yOffset = 20;
			var w = $(window).width();
			var h = $(window).height();
			if(path == ''){
				return;
			}
			var imgurl = "${contextPath}" + path;
			$(this).val(findfilename(path));
			$(this).hover(function(e){
				var myDate=new Date();
				var img = imgurl + "?" + myDate.getTime();
				$("#preview").remove();
				$("body").append("<div id='preview'><div><img id='img_' src='"+img+"' onload='$(this).resize();'/></div></div>");
				$("#preview").css({
					position:"absolute",
					padding:"4px",
					border:"1px solid #f3f3f3",
					backgroundColor:"#eeeeee",
					top:(e.pageY - yOffset) + "px",
					zIndex:999
				});
				$("#preview > div").css({
					padding:"5px",
					backgroundColor:"white",
					border:"1px solid #cccccc",
					textAlign:"center"
				});
				$("#preview > div > p").css({
					textAlign:"center",
					fontSize:"12px",
					padding:"8px 0 3px",
					margin:"0"
				});
				$("#preview").css({
					left: e.pageX + xOffset + "px",
					right: "auto"
				}).fadeIn("fast");
				if(e.pageY>h/2){
					$("#preview").css({
						top: e.pageY - yOffset -$("#img").height()+ "px"
					}).fadeIn("fast");
				}else{
					$("#preview").css({
						top: e.pageY + yOffset + "px"
					}).fadeIn("fast");
				}
			},function(){
				$("#preview").remove();
			}).mouseout(function(){
				$("#preview").remove();
			}).mousemove(function(e){
				if(e.pageY>h/2){
					$("#preview").css({
						top: e.pageY - yOffset -$("#img").height()+ "px"
					}).fadeIn("fast");
				}
				if(e.pageY>w/2){
					$("#preview").css({
						top: e.pageX - xOffset -$("#img").width()+ "px"
					}).fadeIn("fast");
				}
				$("#preview").css("left",(e.pageX + yOffset) + "px").css("right","auto");
			});						  
		};
		$.fn.resize=function(){
			$img = $("#img_");
			imgw = $img.width();
			imgh = $img.height();
			if(imgw*1>250){
				imgh = imgh/imgw*250;
				imgw=250;
			}
			if(imgh*1>180){
				imgw = imgw/imgh*180;
				imgh=180;
			}
			$img.css("width",imgw+"px");
			$img.css("height",imgh+"px");
		};
		$("#iconFile").parent().prev().preview("${application.logoPath}"); 
		
		function findfilename(path){ 
			return path.substring(path.lastIndexOf('/')+1);
		}
	});
	

	
	function clearfile(showfile, hidfile){
		$('#'+showfile).parent().prev().val("");
		$('#'+hidfile).val("");
	}
	
	
</script>
</head>
<body>
	<form action="${url }" method="post" id="oprform" name="oprform" enctype="multipart/form-data">
		<input type="hidden" id="siteId" name="siteId" value="${application.siteId }">
		<input type="hidden" id="iid" name="iid" value="${application.iid}">
		<input type="hidden" id="binding" name="binding" value="${application.kind}">
		<input type="hidden" id="logoPath" name="logoPath" value="${application.logoPath}">
		<input type="hidden" id="kind" name="kind" value="${application.kind}">
		<input type="hidden" id="orderId" name="orderId" value="${application.orderId }">
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0"
				class="table">
				<tr>
					<td align="right" class="label">应用名称</td>
					<td class="required">*</td>
					<td><input type="text" id="name" name="name" 
						class="input-text" value="${application.name}" /></td>
				</tr>
				
				<tr>
					<td align="right" class="label">接口地址</td>
					<td class="required" >*</td>
					<td><input type="text" id="url" name="url" 
						class="input-text" value="${application.url}" <c:if test="${!(application.kind eq 'm')}">disabled="disabled"</c:if>/></td>
				</tr>
					<tr>
					<td align="right" class="label">系统域名</td>
					<td class="required" >&nbsp</td>
					<td><input type="text" id="domain" name="domain" 
						class="input-text" value="${application.domain}" <c:if test="${!(application.kind eq 'm')}">disabled="disabled"</c:if>/></td>
				</tr>
				<tr>
					<td align="right" class="label">logo</td>
					<td class="required"></td>
					<td> 
					 <input id="iconFile" type="file" class="input-text" name="iconFile" input-width="200">
					 <input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('iconFile','logoPath')"> 
					</td>
				</tr>
				<tr>
					<td align="right" class="label">用户同步</td>
					<td class="required" width="30px">*</td>
					<td>

							<select name="type" id="type"  data-value="${application.type}" disabled="disabled">
							    <option value="0">统一用户</option>
							    <option value="1">单点登录</option>
							    <option value="2">不校验</option>
							</select>		
					</td>
				</tr> 
				<tr id="s">
					<td align="right" class="label">是否开启 </td>
					<td class="required">&nbsp;</td>
					<td>
					    <input type="radio" name="isOpen" value="1" 
							data-value="${application.isOpen }" checked="true" >是
						<input type="radio" name="isOpen" value="0" 
							data-value="${application.isOpen }" >否
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