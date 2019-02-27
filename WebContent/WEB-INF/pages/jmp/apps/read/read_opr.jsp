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
<title>阅读管理</title>
<h:head pagetype="dialog" multiselect="true" menu="true" tree="true" upload="true" validity="true" calendar="true" select="true" tip="true"></h:head>  
<script type="text/javascript">
	$(function() {
		var type = $('#type').val();
		changeType(type);
		$("#oprform").validity(function() {
			if($('#type').val()==0){
				
				$("#name").require("名称不能为空").maxLength(33, "名称不能超过33个字");

				$('#picFile').assert(function(){
					if($("#picFile").parent().prev().val().length > 0){
						return true;
					}
			        return false;
				},'封面图不能为空');
				}else{
					$("#name").require("名称不能为空").maxLength(33, "名称不能超过33个字");
					
					$("#author").require("作家 /出版社不能为空").match('username1',
					"作家 /出版社只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾").maxLength(33,
					"作家 /出版社不能超过33个字");

					$('#pubTime').require("出版时间不能为空");	
					
					$('#picFile').assert(function(){
						if($("#picFile").parent().prev().val().length > 0){
							return true;
						}
				        return false;
					},'封面图不能为空');
					$('#bigFile').assert(function(){
						if($("#bigFile").parent().prev().val().length > 0){
							return true;
						}
					        return false;
					},'海报图不能为空');
					$('#bookFile').assert(function(){
						if($("#bookFile").parent().prev().val().length > 0){
							return true;
						}
					        return false;
					},'文件不能为空');	

					$("#spec").require("简介不能为空").maxLength(300, "简介不能超过300个字");
					
					}
	},{type:'iframe'});

	$.fn.preview = function(hiddenId){
		var xOffset = 10;
		var yOffset = 20;
		var w = $(window).width();
		var h = $(window).height();
		$(this).val(findfilename($("#"+hiddenId).val()));
		if(hiddenId != 'filePath') {
			$(this).hover(function(e){
				var imgurl = $("#"+hiddenId).val();
				if(imgurl == ''){
					return;
				}
				imgurl = "${jmpUrl}" + imgurl;
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
			}
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
	
	$("#picFile").parent().prev().preview("picPath");
	$("#bigFile").parent().prev().preview("bigPath");
	$("#bookFile").parent().prev().preview("filePath");
});
	function changeType(type){
		if(type==0){
			$("#author1").hide();
			$("#spec1").hide();
			$("#bigFile1").hide();
			$("#bookFile1").hide();
			$("#pubTime1").hide();
		}else{
			$("#author1").show();
			$("#spec1").show();
			$("#bigFile1").show();
			$("#bookFile1").show();
			$("#pubTime1").show();
			}
	}

	function submitData(){
		$("#oprform").submit();
	}
</script>
</head>
<body>
	<form action="${url }" enctype="multipart/form-data" method="post" id="oprform">
		<input type="hidden" name="iid" id="iid" value="${read.iid }" /> 
		<input type="hidden" name="siteId" id="siteId" value="${read.siteId }" /> 
		<input type="hidden" name="orderId" id="orderId" value="${read.orderId }" /> 
		<input type="hidden" name="pid" id="pid" value="${read.pid }" /> 
		<input type="hidden" name="picPath" id="picPath" value="${read.picPath }"></input>
		<input type="hidden" name="bigPath" id="bigPath" value="${read.bigPath }"></input>
		<input type="hidden" name="filePath" id="filePath" value="${read.filePath }"></input>
		<input type="hidden" name="picsize" id="picsize" value="${read.picsize }"></input>
		<input type="hidden" name="flag" id="flag" value="${read.flag}"></input>
		<input type="hidden" id="changeTime" name="changeTime" type="text" class="jcalendar input-text" 
			   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" value="${changeTime}"/> 
		<div id="dialog-content">
			<table border="0" align="center" class="table">
				<c:choose>
					<c:when test="${url=='add_submit.do'}">
						<tr><td align="right" class="label" style="width:90px">类型</td>
							<td class="required">*</td>
								<td><select name="type" id="type" data-value="${read.type}" onchange="changeType(this.value)">
							    <option value="1">书籍</option>
							    <option value="0">分类</option>
							</select></td>				
						</tr>
					</c:when>
					<c:when test="${url=='modify_submit.do'}">
						<tr><td align="right" class="label" style="width:90px">类型</td>
							<td class="required">*</td>
								<td><select name="type" id="type" data-value="${read.type}" disabled="disabled" "changeType(this.value)">
							    <option value="0">分类</option>
							    <option value="1">书籍</option>
							</select></td>		
						</tr>
					</c:when>
				</c:choose>
				<tr>
					<td align="right" class="label">名称</td>
					<td class="required">*</td>
					<td><input type="text" id="name" name="name" maxlength="33"
						class="input-text" value="${read.name }" /></td>
				</tr>
				<tr id="author1">
					<td align="right" class="label">作家 / 出版社</td>
					<td class="required">*</td>
					<td><input type="text" id="author" name="author" maxlength="33"
						class="input-text" value="${read.author }" /></td>
				</tr>
				<tr id="pubTime1">
					<td align="right" class="label">出版时间</td>
					<td class="required">*</td>
					<td >
					<input id="pubTime" name="pubTime" type="text" class="jcalendar input-text" 
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" value="${pubTime}"/> 
					
				</tr>	
				<tr>
					<td align="right" class="label">封面图</td>
					<td class="required">*<h:tip title="封面图像素160*220，大小不超过1M"></h:tip></td>
					<td><input id="picFile" type="file" class="input-text"
						name="picFile" input-width="200"> 
				</td>
				<tr id="bigFile1">
					<td align="right" class="label">海报图</td>
					<td class="required">*<h:tip title="海报图像素640*360，大小不超过1M"></h:tip></td>
					<td><input id="bigFile" type="file" class="input-text"
						name="bigFile" input-width="200"> 
				</td>		
				</tr>	
				<tr id="bookFile1">
					<td align="right" class="label">文件</td>
					<td class="required">*<h:tip title="文件类型为PDF"></h:tip></td>
					<td><input id="bookFile" type="file" class="input-text"
						name="bookFile" input-width="200"> 
				</td>
				</tr>	
				<tr id="spec1">
					<td align="right" class="label">简介</td>
					<td class="required">*</td>
					<td><textarea id="spec" name="spec" 
						class="input-textarea">${read.spec }</textarea></td>
				</tr>					
			</table>
		</div>
		<div id="dialog-toolbar">
			<div id="dialog-toolbar-panel">
				<input type="button" class="btn btn-primary" value="保存" onclick="submitData();"/> 
				<input type="button" class="btn" value="取消" onclick="closeDialog();" />
			</div>
		</div>
	</form>
</body>
</html>