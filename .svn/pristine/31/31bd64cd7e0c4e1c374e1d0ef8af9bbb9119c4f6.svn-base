<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="h" uri="/WEB-INF/tag/hanweb-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统参数</title>
<link type="text/css" rel="stylesheet" href="../../ui/css/page.css"/> 
<h:head pagetype="dialog" validity="true" tabs="true" tip="true" upload="true"></h:head> 
<link rel="stylesheet" href="${contextPath}/ui/widgets/colorpicker/css/colorpicker.css" type="text/css" />
<script type="text/javascript" src="${contextPath}/ui/widgets/colorpicker/colorpicker.js"></script>
<!--使用${contextPath}可以获得应用路径，并无/结尾-->
<script>
	/**
	* 表单验证
	*/
	$(function(){
		setPush();
	
	//颜色选择器 
	$('#colorSelector').ColorPicker({
		color: '#31a1f7',
		onShow: function (colpkr) {
			$(colpkr).fadeIn(100);
			return false;
		},
		onHide: function (colpkr) {
			$(colpkr).fadeOut(100);
			return false;
		},
		onChange: function (hsb,
				 hex, rgb) {
			$('#colorSelector div').css('backgroundColor', '#' + hex);
			$('#color').val("#"+hex); 
		}
	});

	var bgColor = $("#color").val();
	if(bgColor!=""){
		 $("#colorSelector1").css("background-color",bgColor);
		}
	$('#oprform').validity(function() {
		$("#name").require("站点名称不能为空") 
			.match('username1', "站点名称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾")
			.maxLength(33, "站点名称不能超过33个字");

		$("#color").assert(function(){
			var color = $("#color").val();
			var re=/^#[a-fA-F0-9]{6}$/g;//匹配IP地址的正则表达式 
			return re.test(color);},"背景颜色不正确");  
		
		$("#file_logo_hid").require("站点图标不能为空");
		
		$("#spec").require("关于本站不能为空").maxLength(200, "关于本站不能超过200个字");  
		
		$("#domain").match('url', "站点域名不正确").maxLength(80, "站点域名不能超过80个字");
		$("#androidurl").match('url', "Android客户端下载地址不正确").maxLength(80, "Android客户端下载地址不能超过80个字");
		$("#iphoneurl").match('url', "Iphone客户端下载地址不正确").maxLength(80, "Iphone客户端下载地址不能超过80个字");
		$("#ipadurl").match('url', "Ipad客户端下载地址不正确").maxLength(80, "Ipad客户端下载地址不能超过80个字");
		
		$("#pushsavenum").match('intege1', "推送信息条数不正确");
		
		
		$("#url1").match('url', "配图链接地址不正确").maxLength(80, "配图链接地址不能超过80个字");
		$("#url2").match('url', "中页链接地址不正确").maxLength(80, "中页链接地址不能超过80个字");
		$("#url3").match('url', "尾页链接地址不正确").maxLength(80, "尾页链接地址不能超过80个字");
		
	},{type:'iframe'});
	
	$("[id^=file_]").change(function(){	
		var name = this.id.substring(5);
		var hidid = "file_" + name + "_hid";
		if(name == 'logo'){
			$("#"+hidid).val("/web/site${site.iid}/site/site_"+name+".png");
		}else{
			//alert($("#"+hidid).val("/web/site${site.iid}/site/site_"+name+".jpg"));
			$("#"+hidid).val("/web/site${site.iid}/site/site_"+name+".jpg");
		}
	});


	$.fn.preview = function(path){
		var xOffset = 10;
		var yOffset = 20;
		var w = $(window).width();
		var h = $(window).height();
		if(path == ''){
			return;
		}   
		var imgurl = "${jmpurl}" + path; 
		$(this).val(findfilename(path));
		$(this).hover(function(e){
			var myDate=new Date();
			var img = imgurl + "?date="+myDate; 
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
	$("#file_logo").parent().prev().preview("${site.parameter.sitePic}");  
	$("#file_iphone1").parent().prev().preview("${site.siteSplash.firstIphonePic}");  
	//$("#file_iphone2").parent().prev().preview("${site.siteSplash.middleIphonePic}"); 
	//$("#file_iphone3").parent().prev().preview("${site.siteSplash.lastIphonePic}"); 
	//$("#file_iphone4").parent().prev().preview("${site.siteSplash.firstIphone4Pic}");  
	//$("#file_iphone5").parent().prev().preview("${site.siteSplash.middleIphone4Pic}"); 
	//$("#file_iphone6").parent().prev().preview("${site.siteSplash.lastIphone4Pic}"); 
	$("#file_android1").parent().prev().preview("${site.siteSplash.firstAndroidPic}"); 
	//$("#file_android2").parent().prev().preview("${site.siteSplash.middleAndroidPic}"); 
	//$("#file_android3").parent().prev().preview("${site.siteSplash.lastAndroidPic}"); 
	$("#file_ipad1").parent().prev().preview("${site.siteSplash.firstIpadPic}"); 
	//$("#file_ipad2").parent().prev().preview("${site.siteSplash.middleIpadPic}"); 
	//$("#file_ipad3").parent().prev().preview("${site.siteSplash.lastIpadPic}"); 
	}); 


	function findfilename(path){ 
		return path.substring(path.lastIndexOf('/')+1);
	}
	
	function clearfile(showfile, hidfile){
		$('#'+showfile).parent().prev().val("");
		$('#'+hidfile).val("");
	}
	
	//验证附件格式  type  jpg|bmp|zip|...|rar
	var validateFileType = function(filepath,type){
		var extStart=filepath.lastIndexOf(".")+1; 
	    var ext=filepath.substring(extStart,filepath.length).toUpperCase();
	    if((":"+type.toUpperCase()+":").indexOf(":"+ext+":") != -1){
	    	return true;
	    }
	    return false;
	};
	
	function success(msg){
		top.location.reload();
	}
	
	function fail(msg){
		alert(msg);
		
	}
	
	function setPush(){
		var ispush = $("input[name='parameter.isPush']:checked").val();
		if(ispush == '0'){
			$("[id^=tr_push_id]").hide();
		}else{
			$("[id^=tr_push_id]").show();
		}
	}
	
	function downpic(picpath) {
		if(picpath==null || picpath==""){
	        alert("图标为空，暂时无法下载");
		}else{
			window.open("downloadfile.do?filePath="+picpath);
		}
	}
</script>
<style type="text/css">
#colorSelector{position:relative;width:36px;height:36px;
	background:url(${contextPath}/ui/widgets/colorpicker/images/select2.png);display: inline;padding-bottom:20px;}
#colorSelector div{position:absolute;top:-6px;left:-2px;width:30px;height:30px;
	background:url(${contextPath}/ui/widgets/colorpicker/images/select2.png) center;} 
.div_file{
	float: left;
}
.input-textarea {
	resize: none;
}
</style>
</head>
<body>
   	<!--表单主体-->
	<form action="${url}" method="post" id="oprform" name="oprform" enctype="multipart/form-data">
	<div id="dialog-content">   
	<div id="page-title" style="margin: -20px 20px 0px 20px">    站点参数</div>
	<div id="tabs" class="easyui-tabs" style=";padding: 20px 20px 0px 20px">    
	<div title="基本设置" style="padding:20px;">
	    <table border="0" align="left" cellpadding="10" cellspacing="0" class="table">
			<tr>
				<td align="left" class="label">站点名称</td>
				<td class="required">*</td>
				<td><input type="text" id="name" name="name" class="input-text" maxlength="33" class="input-text" value="${site.name }" />
				</td>
			</tr>
			
			<tr>
				<td align="left" class="label">背景颜色</td>
				<td class="required"><h:tip title="请选择或填写适当的RGB颜色，格式为#ffffff"></h:tip></td>
				<td><input type="text" id="color" name="color" class="input-text" maxlength="7" class="input-text"
						   value="${site.color }" style="width:266px;" />
					<div id="colorSelector"><div id="colorSelector1" style="background-color: #31a1f7"></div></div>
				</td>
			</tr>
			
			<tr>
				<td align="left" class="label">站点图标</td>
				<td class="required">*</td>
				<td>
					<input id="file_logo" type="file" class="input-text" name="file_logo" input-width="200">
				    <input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('file_logo','file_logo_hid')">  
					<input type="button" class="btn btn-success btn-small" value="下载" onclick="downpic('${site.parameter.sitePic}')"> 
					<input type="hidden" id="file_logo_hid" name="parameter.sitePic" class="input-text" maxlength="33" class="input-text" value="${site.parameter.sitePic }" />
				</td>
			</tr>
			
			<tr>
				<td align="left" class="label">关于本站 </td>
				<td class="required">*</td>
				<td>
					<textarea class="input-textarea" name="parameter.spec" maxlength="200"
						      id="spec" >${site.parameter.spec }</textarea>
				</td>
			</tr>
			
			<tr id="n">
				<td colspan="3"><hr style="height:1px;border-top:1px dashed #CCCCCC;"/></td> 
			</tr>
			
			<tr>
				<td align="left" class="label">站点域名</td>
				<td class="required">&nbsp;</td>
				<td><textarea id="domain" name="parameter.domain" class="input-textarea" 
					          maxlength="80" style="height: 75px">${site.parameter.domain }</textarea>
				</td>
			</tr>
			
			<tr>
				<td align="left" class="label">站点路径</td>
				<td class="required">&nbsp;</td>
				<td><textarea id="url" name="url" maxlength="80" class="input-textarea"
						      disabled="disabled" style="height: 75px">${site.url }</textarea>  
				</td>
			</tr>
			
			<!-- 
			<tr>
				<td align="left" class="label">Android客户端下载地址</td>
				<td class="required">&nbsp;</td>
				<td>
					<textarea class="input-textarea" name="parameter.androidUrl" maxlength="80"
						id="androidurl" >${site.parameter.androidUrl }</textarea>
				</td>
			</tr>
			<tr>
				<td align="left" class="label">Iphone客户端下载地址</td>
				<td class="required">&nbsp;</td>
				<td>
					<textarea class="input-textarea" name="parameter.iphoneUrl" maxlength="80"
						id="iphoneurl" >${site.parameter.iphoneUrl }</textarea>
				</td>
			</tr>
			<tr>
				<td align="left" class="label">Ipad客户端下载地址</td>
				<td class="required">&nbsp;</td>
				<td>
					<textarea class="input-textarea" name="parameter.ipadUrl" maxlength="80"
						id="ipadurl" >${site.parameter.ipadUrl }</textarea>
				</td>
			</tr>
			 -->
		</table>
	    </div>  
	    <div title="高级设置" style="padding:20px;">
	    	<table border="0" align="left" cellpadding="10" cellspacing="0" class="table">
			<tr>
				<td align="left" class="label" style="width:90px;">评论自动审核</td>
				<td class="required">&nbsp;</td>
				<td>
					<input type="radio" name="parameter.commentAutoAudit" value="1" data-value="${site.parameter.commentAutoAudit }">是
					<input type="radio" name="parameter.commentAutoAudit" value="0" data-value="${site.parameter.commentAutoAudit }">否
				</td>
			</tr>
			
			<tr>
				<td align="left" class="label">推送</td>
				<td class="required">&nbsp;</td>
				<td>
					<input type="radio" name="parameter.isPush" value="1" data-value="${site.parameter.isPush }" onclick="setPush()">支持
					<input type="radio" name="parameter.isPush" value="0" data-value="${site.parameter.isPush }" onclick="setPush()">不支持
				</td>
			</tr>
			
			<tr id="tr_push_id1">
				<td align="left" class="label">AndroidAppId</td>
				<td class="required"></td>
				<td><input type="text" id="input_push_id1" name="parameter.androidPushAppKey" class="input-text" maxlength="33" class="input-text"
					       value="${site.parameter.androidPushAppKey }" />
				</td>
			</tr>
			
			<tr id="tr_push_id2">
				<td align="left" class="label">AndroidSecret</td>
				<td class="required"></td>
				<td><input type="text" id="input_push_id2" name="parameter.androidPushAppSecret" class="input-text" 
					       maxlength="33" class="input-text" value="${site.parameter.androidPushAppSecret }" />
				</td>
			</tr>
			
			<tr id="tr_push_id3">
				<td align="left" class="label">IphoneAppId</td>
				<td class="required"></td>
				<td><input type="text" id="input_push_id3" name="parameter.iphonePushAppKey" class="input-text" 
					       maxlength="33" class="input-text" value="${site.parameter.iphonePushAppKey }" />
				</td>
			</tr>
			
			<tr id="tr_push_id4">
				<td align="left" class="label">IphoneSecret</td>
				<td class="required"></td>
				<td><input type="text" id="input_push_id4" name="parameter.iphonePushAppSecret" class="input-text" 
					       maxlength="33" class="input-text" value="${site.parameter.iphonePushAppSecret }" />
				</td>
			</tr>
			
			<tr id="tr_push_id5">
				<td align="left" class="label">IpadAppId</td>
				<td class="required"></td>
				<td><input type="text" id="input_push_id5" name="parameter.ipadPushAppKey" class="input-text" 
					       maxlength="33" class="input-text" value="${site.parameter.ipadPushAppKey }" />
				</td>
			</tr>
			
			<tr id="tr_push_id6">
				<td align="left" class="label">IpadSecret</td>
				<td class="required"></td>
				<td><input type="text" id="input_push_id6" name="parameter.ipadPushAppSecret" class="input-text"
					       maxlength="33" class="input-text" value="${site.parameter.ipadPushAppSecret }" />
				</td>
			</tr>
			<!--  
			<tr id="tr_push_id7">
				<td align="left" class="label">信息条数</td>
				<td class="required"><h:tip title="推送日志分类下信息保存的条数，请填写大于0的整数，不符合要求则默认保留100条"></h:tip></td>
				<td><input type="text" name="parameter.pushSaveNum"  id="pushsavenum"
					class="input-text" maxlength="10" class="input-text"
					value="${site.parameter.pushSaveNum }" />
				</td>
			</tr>-->
		</table> 
	    </div>  
	    <div title="引导页图片" style="padding:20px;">
	    	<table border="0" align="left" cellpadding="10" cellspacing="0" class="table">
			<tr style="display: none">
				<td align="left" class="label" width="100" colspan="3" style="color:red">
				注：引导页图片如需上传，建议同一种客户端的图片一起上传</td>
			</tr>
			<tr>
				<td align="left" class="label">iphone引导图</td>
				<td class="required"><h:tip title="请上传1M以内的图片，格式为JPG或PNG，建议大小1080*1920"></h:tip></td>
				<td>
				<input id="file_iphone1" type="file" class="input-text" name="file_iphone1" input-width="200">
			    <input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('file_iphone1','file_iphone1_hid')">   
				<input type="button" class="btn btn-success btn-small" value="下载" onclick="downpic('${site.siteSplash.firstIphonePic}')">
				<input type="hidden" id="file_iphone1_hid" name="siteSplash.firstIphonePic" class="input-text" 
					   maxlength="33" class="input-text" value="${site.siteSplash.firstIphonePic }" />
				</td>
			</tr>
			
			<tr style="display: none">
				<td align="left" class="label">iphone5引导图</td>
				<td class="required"><h:tip title="请上传1M以内的图片，格式为JPG或PNG，建议大小1080*1920"></h:tip></td>
				<td>
					<input id="file_iphone2" type="file" class="input-text" name="file_iphone2" input-width="200">
			    	<input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('file_iphone2','file_iphone2_hid')">  
			    	<input type="button" class="btn btn-success btn-small" value="下载" onclick="downpic('${site.siteSplash.middleIphonePic}')"> 
					<input type="hidden" id="file_iphone2_hid" name="siteSplash.middleIphonePic" class="input-text" 
						   maxlength="33" class="input-text" value="${site.siteSplash.middleIphonePic }" /> 
				</td>
			</tr>
			
			<tr style="display: none">
				<td align="left" class="label">iphone6引导图</td>
				<td class="required"><h:tip title="请上传1M以内的图片，格式为JPG或PNG，建议大小1080*1920"></h:tip></td>
				<td>
					<input id="file_iphone3" type="file" class="input-text" name="file_iphone3" input-width="200">
			    	<input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('file_iphone3','file_iphone3_hid')">
			    	<input type="button" class="btn btn-success btn-small" value="下载" onclick="downpic('${site.siteSplash.lastIphonePic}')">  
					<input type="hidden" id="file_iphone3_hid" name="siteSplash.lastIphonePic" class="input-text" 
						   maxlength="33" class="input-text" value="${site.siteSplash.lastIphonePic }" /> 
				</td>
			</tr>
			
			<tr style="display: none">
				<td align="left" class="label">iphone6p引导图</td>
				<td class="required"><h:tip title="请上传1M以内的图片，格式为JPG或PNG，建议大小1080*1920"></h:tip></td>
				<td>
				<input id="file_iphone4" type="file" class="input-text" name="file_iphone4" input-width="200">
			    <input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('file_iphone4','file_iphone4_hid')">   
				<input type="button" class="btn btn-success btn-small" value="下载" onclick="downpic('${site.siteSplash.firstIphone4Pic}')">
				<input type="hidden" id="file_iphone4_hid" name="siteSplash.firstIphone4Pic" class="input-text" 
					   maxlength="33" class="input-text" value="${site.siteSplash.firstIphone4Pic }" />
				</td>
			</tr>
			<!-- <tr id="n">
				<td colspan="3"><hr style="height:1px;border-top:1px dashed #CCCCCC;"/></td> 
			</tr>
			<tr>
				<td align="left" class="label">iphone6s引导图</td>
				<td class="required"><h:tip title="请上传1M以内的图片，格式为JPG或PNG，建议大小1080*1920"></h:tip></td>
				<td>
				<input id="file_iphone4" type="file" class="input-text" name="file" input-width="200">
			    <input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('file_iphone4','file_iphone4_hid')">   
				<input type="button" class="btn btn-success btn-small" value="下载" onclick="downpic('${site.siteSplash.firstIphone4Pic}')">
				<input type="hidden" id="file_iphone4_hid" name="siteSplash.firstIphone4Pic" 
						class="input-text" maxlength="33" class="input-text"
						value="${site.siteSplash.firstIphone4Pic }" />
				 
				</td>
			</tr>
			<tr>
				<td align="left" class="label">iphone4中页</td>
				<td class="required"><h:tip title="请上传1M以内的图片，格式为JPG或PNG，建议大小1080*1920"></h:tip></td>
				<td>
					<input id="file_iphone5" type="file" class="input-text" name="file" input-width="200">
			    	<input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('file_iphone5','file_iphone5_hid')">  
			    	<input type="button" class="btn btn-success btn-small" value="下载" onclick="downpic('${site.siteSplash.middleIphone4Pic}')"> 
					<input type="hidden" id="file_iphone5_hid" name="siteSplash.middleIphone4Pic" 
							class="input-text" maxlength="33" class="input-text"
							value="${site.siteSplash.middleIphone4Pic }" /> 
				</td>
			</tr>
			<tr>
				<td align="left" class="label">iphone4尾页</td>
				<td class="required"><h:tip title="请上传1M以内的图片，格式为JPG或PNG，建议大小1080*1920"></h:tip></td>
				<td>
					<input id="file_iphone6" type="file" class="input-text" name="file" input-width="200">
			    	<input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('file_iphone6','file_iphone6_hid')">
			    	<input type="button" class="btn btn-success btn-small" value="下载" onclick="downpic('${site.siteSplash.lastIphone4Pic}')">  
					<input type="hidden" id="file_iphone6_hid" name="siteSplash.lastIphone4Pic" 
						class="input-text" maxlength="33" class="input-text"
						value="${site.siteSplash.lastIphone4Pic }" /> 
				</td>
			</tr> -->
			<tr id="n">
				<td colspan="3"><hr style="height:1px;border-top:1px dashed #CCCCCC;"/></td> 
			</tr>
			<tr>
				<td align="left" class="label">android引导图</td>
				<td class="required"><h:tip title="请上传1M以内的图片，格式为JPG或PNG，建议大小1080*1920"></h:tip></td>
				<td>
					<input id="file_android1" type="file" class="input-text" name="file_android1" input-width="200">
			    	<input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('file_android1','file_android1_hid')"> 
			    	<input type="button" class="btn btn-success btn-small" value="下载" onclick="downpic('${site.siteSplash.firstAndroidPic}')">  
					<input type="hidden" id="file_android1_hid" name="siteSplash.firstAndroidPic" class="input-text" 
						   maxlength="33" class="input-text" value="${site.siteSplash.firstAndroidPic }" /> 
				</td>
			</tr>
			<!-- <tr>
				<td align="left" class="label">android中页</td>
				<td class="required"><h:tip title="请上传1M以内的图片，格式为JPG或PNG，建议大小1080*1920"></h:tip></td>
				<td>
					<input id="file_android2" type="file" class="input-text" name="file" input-width="200">
			    	<input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('file_android2','file_android2_hid')"> 
			    	<input type="button" class="btn btn-success btn-small" value="下载" onclick="downpic('${site.siteSplash.middleAndroidPic}')">
					<input type="hidden" id="file_android2_hid" name="siteSplash.middleAndroidPic" 
						class="input-text" maxlength="33" class="input-text"
						value="${site.siteSplash.middleAndroidPic }" /> 
				</td>
			</tr>
			<tr>
				<td align="left" class="label">android尾页</td>
				<td class="required"><h:tip title="请上传1M以内的图片，格式为JPG或PNG，建议大小1080*1920"></h:tip></td>
				<td>
					<input id="file_android3" type="file" class="input-text" name="file" input-width="200">
			    	<input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('file_android3','file_android3_hid')"> 
			    	<input type="button" class="btn btn-success btn-small" value="下载" onclick="downpic('${site.siteSplash.lastAndroidPic}')">
					<input type="hidden" id="file_android3_hid" name="siteSplash.lastAndroidPic" 
						class="input-text" maxlength="33" class="input-text"
						value="${site.siteSplash.lastAndroidPic }" /> 
				</td>
			</tr> -->
			<tr id="n">
				<td colspan="3"><hr style="height:1px;border-top:1px dashed #CCCCCC;"/></td> 
			</tr>
			<tr>
				<td align="left" class="label">ipad引导图</td>
				<td class="required"><h:tip title="请上传1M以内的图片，格式为JPG或PNG，建议大小1080*1920"></h:tip></td>
				<td>
					<input id="file_ipad1" type="file" class="input-text" name="file_ipad1" input-width="200">
			    	<input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('file_ipad1','file_ipad1_hid')">
			    	<input type="button" class="btn btn-success btn-small" value="下载" onclick="downpic('${site.siteSplash.firstIpadPic}')"> 
					<input type="hidden" id="file_ipad1_hid" name="siteSplash.firstIpadPic" class="input-text" 
						   maxlength="33" class="input-text" value="${site.siteSplash.firstIpadPic }" /> 
				</td> 
			</tr>
			<!-- <tr>
				<td align="left" class="label">ipad中页</td>
				<td class="required"><h:tip title="请上传1M以内的图片，格式为JPG或PNG，建议大小1080*1920"></h:tip></td>
				<td>
					<input id="file_ipad2" type="file" class="input-text" name="file" input-width="200">
			    	<input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('file_ipad2','file_ipad2_hid')"> 
			    	<input type="button" class="btn btn-success btn-small" value="下载" onclick="downpic('${site.siteSplash.middleIpadPic}')"> 
					<input type="hidden" id="file_ipad2_hid" name="siteSplash.middleIpadPic" 
						class="input-text" maxlength="33" class="input-text"
						value="${site.siteSplash.middleIpadPic }" /> 
				</td>
			</tr>
			<tr>
				<td align="left" class="label">ipad尾页</td>
				<td class="required"><h:tip title="请上传1M以内的图片，格式为JPG或PNG，建议大小1080*1920"></h:tip></td>
				<td>
					<input id="file_ipad3" type="file" class="input-text" name="file" input-width="200">
			    	<input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('file_ipad3','file_ipad3_hid')">  
			    	<input type="button" class="btn btn-success btn-small" value="下载" onclick="downpic('${site.siteSplash.lastIpadPic}')"> 
					<input type="hidden" id="file_ipad3_hid" name="siteSplash.lastIpadPic" 
						class="input-text" maxlength="33" class="input-text"
						value="${site.siteSplash.lastIpadPic }" /> 
				</td>
			</tr> -->
			<tr id="n">
				<td colspan="3"><hr style="height:1px;border-top:1px dashed #CCCCCC;"/></td> 
			</tr>
			<tr>
				<td align="left" class="label">引导图标题</td>
				<td class="required">&nbsp;</td>
				<td><input type="text" id="title1" name="siteSplash.firstTitle" class="input-text" maxlength="33" 
					       class="input-text" value="${site.siteSplash.firstTitle }" style="width:400px" />
				</td>
			</tr>
			<!-- <tr>
				<td align="left" class="label">中页标题</td>
				<td class="required">&nbsp;</td>
				<td><input type="text" id="title2" name="siteSplash.middleTitle" 
					class="input-text" maxlength="33" class="input-text"
					value="${site.siteSplash.middleTitle }" style="width:400px" />
				</td>
			</tr>
			<tr>
				<td align="left" class="label">尾页标题</td>
				<td class="required">&nbsp;</td>
				<td><input type="text" id="title3" name="siteSplash.lastTitle" 
					class="input-text" maxlength="33" class="input-text"
					value="${site.siteSplash.lastTitle }" style="width:400px" />
				</td>
			</tr>
			<tr id="n">
				<td colspan="3"><hr style="height:1px;border-top:1px dashed #CCCCCC;"/></td> 
			</tr> -->
			<tr>
				<td align="left" class="label">引导图链接</td>
				<td class="required">&nbsp;</td>
				<td><input type="text" id="url1" name="siteSplash.firstUrl" class="input-text" maxlength="80" 
					       value="${site.siteSplash.firstUrl }" style="width:400px" />
				</td>
			</tr>
			<!-- <tr>
				<td align="left" class="label">中页链接</td>
				<td class="required">&nbsp;</td>
				<td><input type="text" id="url2" name="siteSplash.middleUrl" 
					class="input-text" maxlength="80" class="input-text"
					value="${site.siteSplash.middleUrl }" style="width:400px" />
				</td>
			</tr>
			<tr>
				<td align="left" class="label">尾页链接</td>
				<td class="required">&nbsp;</td>
				<td><input type="text" id="url3" name="siteSplash.lastUrl" 
					class="input-text" maxlength="80" class="input-text"
					value="${site.siteSplash.lastUrl }" style="width:400px" />
				</td>
			</tr> -->
		</table>
	    </div>
	    
	    <div title="应用同步设置" style="padding:20px;">
	    	<table border="0" align="left" cellpadding="10" cellspacing="0" class="table">
			<tr>
				<td align="left" class="label">同步系统域名</td>
				<td class="required">&nbsp;</td>
				<td><input type="text" id="jopUrl" name="siteSplash.jopUrl" class="input-text" maxlength="50" value="${site.siteSplash.jopUrl}" />
				</td>
			</tr>
			</table>
	     </div> 
	</div>
	</div>
	<div id="dialog-toolbar">
		<div id="dialog-toolbar-panel" align="center">
			<input type="submit" class="btn btn-primary" value="保存" />
		</div>
	</div>	
	</form>
</body>
</html>