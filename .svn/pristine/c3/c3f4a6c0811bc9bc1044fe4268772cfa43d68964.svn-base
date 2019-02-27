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
<title>调查答案管理</title>
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
					$("#name").require("调查答案名称不能为空") 
					.maxLength(100, "调查答案名称不能超过100个字");
				}
				
				$('#firstPicFile').assert(function(){
					if($("#firstPicFile").val().length==0){
						return true;
					}
			        return validateFileType($("#firstPicFile").val(),"${PicFileType}");
				},'首图限于${PicFileType}格式');
			}, {type:'iframe'});
		$.fn.preview = function(hiddenId){
			var xOffset = 10;
			var yOffset = 20;
			var w = $(window).width();
			var h = $(window).height();
			
			$(this).val(findfilename($("#"+hiddenId).val()));
			$(this).hover(function(e){
				var imgurl = $("#"+hiddenId).val();
				if(imgurl == ''){
					return;
				}
				imgurl = "${contextPath}" + imgurl;
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
		
		function findfilename(path){
			return path.substring(path.lastIndexOf('/')+1);
		}
		
		$("#firstPicFile").parent().prev().preview("firstPicPath");
	});

		//验证附件格式  type  jpg|bmp|zip|...|rar
		var validateFileType = function(filepath,type){
			var extStart=filepath.lastIndexOf(".")+1; 
		    var ext=filepath.substring(extStart,filepath.length).toUpperCase();
		    if((","+type.toUpperCase()+",").indexOf(","+ext+",") != -1){
		    	return true;
		    }
		    return false;
		};

		function clearfile(showfile, hidfile){
			$('#'+showfile).parent().prev().val("");
			$('#'+hidfile).val("");
		}

		function findfilename(path){ 
			return path.substring(path.lastIndexOf('/')+1);
		}


</script>
<style type="text/css">
	.input-textarea {
		resize: none;
	}
</style>
</head>
<body>
	<form action="${url }" method="post" id="oprform" name="oprform" enctype="multipart/form-data">
		<input type="hidden" name="iid" value="${answer.iid }">
		<input type="hidden" name="siteId" value="${answer.siteId}">
		<input type="hidden" name="questionId" value="${answer.questionId}">
		<input type="hidden" name="surveyId" value="${answer.surveyId}">
		<input id="firstPicPath" name="firstPicPath" type="hidden" value="${answer.firstPicPath}" /> 
		
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0"
				class="table">
				
				<tr>
					<td align="left" class="label">调查问题答案 </td>
					<td class="required">*</td>
					<td>
						<input type="text" id="name" name="name" class="input-text" value="${answer.name }"></input>
					</td>
				</tr>
				<tr>
					<td align="left" class="label">描述 </td>
					<td class="required"></td>
					<td>
					    <textarea  class="input-textarea" name="abs" id="abs" >${answer.abs}</textarea>
					</td>
				</tr>
				<tr>
					<td align="left" class="label">答案首图</td>
					<td class="required"></td>
					<td> 
					 <input id="firstPicFile" type="file" class="input-text" name="firstPicFile" input-width="200" >
					 <input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('firstPicFile','firstPic')"> 
					</td>
				</tr> 
				<tr>
					<td align="left" class="label">调查投票数 </td>
					<td class="required">*</td>
					<td>
						<input type="text" id="count" name="count" class="input-text" value="${answer.count }"></input>
					</td>
				</tr>
				<tr>
					<td align="left" class="label">URL </td>
					<td class="required">&nbsp;</td>
					<td>
						<input type="text" id="url" name="url" class="input-text" value="${answer.url}"></input>
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