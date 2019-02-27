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
<title>分类管理</title>
<h:head pagetype="dialog" multiselect="true" tree="true" upload="true" validity="true" calendar="true" select="true" tip="true"></h:head>
<script type="text/javascript">
	function oprSuccess(nodeId){
		var treeObj = Tree.getInstance({domPath:getParentWindow().parent.menu});
		treeObj.refreshNode(nodeId);
		closeDialog(true);
	}
	
	$(function() {
		var type = $('#type').val();
		if( type==2 ){
			$('#z').show();
			}else{
			$('#z').hide();
			}
		$("#oprform").validity(function() {
				$("#name").require("分类名称不能为空").match('username1',
						"分类名称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾").maxLength(33,
						"分类名称不能超过33个字");
				$('#iconFile').assert(function(){
					if($("#iconFile").parent().prev().val().length > 0){
						return validateFileType($("#iconFile").parent().prev().val(),"${PicFileType}");
					}
					return false;
				},'图片不能为空且仅限于${PicFileType}格式');
		},{type:'iframe'});
	
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
	
	$("#iconFile").parent().prev().preview("iconPath");
	
    });

	//验证图片格式  
	var validateFileType = function(filepath,type){
		var extStart=filepath.lastIndexOf(".")+1; 
	    var ext=filepath.substring(extStart,filepath.length).toUpperCase();
	    if((","+type.toUpperCase()+",").indexOf(","+ext+",") != -1){
	    	return true;
	    }
	    return false;
	};

	function changeColType(type){
		if(type==2){
			$('#z').show();
		}else{
			$('#z').hide();
		}
	}
</script>
</head>
<body>
	<form action="${url }" enctype="multipart/form-data" method="post" id="oprform">
		<input type="hidden" name="iid" id="iid" value="${col.iid }" /> 
		<input type="hidden" name="siteId" id="siteId" value="${col.siteId }" /> 
		<input type="hidden" name="pid" id="pid" value="${col.pid}" /> 
		<input type="hidden" name="orderId" id="orderId" value="${col.orderId }" /> 
		<input type="hidden" name="iconPath" id="iconPath" value="${col.iconPath }" /> 
		<div id="dialog-content">
			<table border="0" align="center" class="table">
				<tr>
				    <td align="right" class="label">分类类型</td>
					<td class="required">*</td>
						<td><select name="type" id="type" data-value="${col.type }" onchange="changeColType(this.value)">
					    <option value="1">虚拟类型</option>
					    <option value="2">普通类型</option>
					</select>
					</td>				
				</tr>
				
				<tr>
					<td align="right" class="label">名称</td>
					<td class="required">*</td>
					<td><input type="text" id="name" name="name" maxlength="33"
						class="input-text" value="${col.name }" /></td>
				</tr>
				
				<tr>
					<td align="right" class="label">图片</td>
					<td class="required">*<h:tip title="图片像素100*100，大小不超过1M"></h:tip></td>
					<td><input type="file" id="iconFile" name="iconFile" input-width="200"
						class="input-text" />
				</tr>
						
				<tr id="z">
					<td align="right" class="label">通讯检索 </td>
					<td class="required">*</td>
					<td>
					    <input type="radio" name="issearch" value="1" 
							data-value="${col.issearch}" >支持
						<input type="radio" name="issearch" value="0" 
							data-value="${col.issearch}" checked="true">不支持
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