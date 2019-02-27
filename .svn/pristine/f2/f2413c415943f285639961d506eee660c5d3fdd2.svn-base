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
<title>号码管理</title>
<h:head pagetype="dialog" multiselect="true" tree="true" upload="true" validity="true" calendar="true" select="true"></h:head>
<link rel="stylesheet" href="${contextPath}/ui/widgets/colorpicker/css/colorpicker.css" type="text/css" />
<script type="text/javascript" src="${contextPath}/ui/widgets/colorpicker/colorpicker.js"></script>
<script type="text/javascript">
	$(function() {
		$("#oprform").validity(function() {
			var s = $('#logoFile').parent().prev().val();
			$('#flag').val(s);
			$("#spec2").val(UE.getEditor('spec').getContent());
			$("#name").require("名称不能为空").match('username1',
					"名称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾").maxLength(33,
					"名称不能超过33个字");
			$('#bgColor').require('背景颜色不能为空');	 
			if($("#phone").val() != "") {
				$("#phone").assert(function() {
					var regu =/(^(\d{3,4}-)?\d{7,8})$/;
					var reg = /^[1-9]\d+$/;
					var s = $("#phone").val(); 
					if (regu.test(s) || reg.test(s))  {
						return true;
					} else {
						return false;
					}
				}, '固定电话格式不正确');
				}
			if($("#tel").val() != "") {
				$("#tel").assert(function() {
					var regu =/(^[0-9]{11}$)/;
					var s = $("#tel").val(); 
					if (regu.test(s))  {
						return true;
					} else {
						return false;
					}
				}, '移动电话格式不正确');
				}
			$("#email").assert(function(){
				var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
				var s = $("#email").val();
				if(reg.test(s) || s == ""){
					return true;
				}else{
					return false;
					}
				},'邮箱格式不正确');
			$("#spec2").maxLength(700, "简介不能超过700个字");
			$("#url").maxLength(200,"URL不能超过200个字");
	},{type:'iframe'}); 

	var bgColor = $("#bgColor").val();
	if(bgColor!=""){
		 $("#colorSelector1").css("background-color",bgColor);
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
			$('#bgColor').val("#"+hex); 
		}
	});
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
	
	$("#logoFile").parent().prev().preview("iconPath");
});
	function selectpoint(){
		openDialog('${contextPath}/ui/widgets/ueditor/infopoint.html?'+new Date(), 850, 650, {
			title : '设置坐标'
		});
	} 
	
	function clearfile(showfile, hidfile){
		$('#'+showfile).parent().prev().val("");
		$('#flag').val("");
		$('#'+hidfile).val("");
	}
	
	function setpoint(point){
		if(point){
			var values = point.split('-');
			$('#address').val(values[1]);
			$('#poilocationtmp').val(values[1]);
		}
	}
</script>
<script type="text/javascript"
	src="${contextPath}/ui/widgets/ueditor/base.config.js"></script>
<script type="text/javascript"
	src="${contextPath}/ui/widgets/ueditor/phoneeditor_api.js"></script> 
<style type="text/css">
#colorSelector{position:relative;width:36px;height:36px;
	background:url(${contextPath}/ui/widgets/colorpicker/images/select2.png);
	display: inline;padding-bottom:20px;}
#colorSelector div{position:absolute;top:-6px;left:-2px;width:30px;height:30px;
	background:url(${contextPath}/ui/widgets/colorpicker/images/select2.png) center;} 
	.label {
	 	width : 120px;
	}
	.input-textarea {
		resize: none;
	}
	
	.ztree li a{display:inline-block}
</style>
</head>
<body>
	<form action="${url }" enctype="multipart/form-data" method="post" id="oprform">
		<input type="hidden" name="iid" id="iid" value="${numsense.iid }" /> 
		<input type="hidden" name="siteId" id="siteId" value="${numsense.siteId }" /> 
		<input type="hidden" name="orderId" id="orderId" value="${numsense.orderId }" /> 
		<input type="hidden" name="colId" id="colId" value="${numsense.colId }" /> 
		<input type="hidden" name="iconPath" id="iconPath" value="${numsense.iconPath}"/>
		<input type="hidden" name="address" id="address" value="${numsense.address}"/>
		<input type="hidden" name="flag" id="flag" value="${numsense.iconPath}"/>
		<div id="dialog-content">
			<table border="0" align="center" class="table">
				<tr>
					<td align="right" class="label">单位名称</td>
					<td class="required">*</td>
					<td><input type="text" id="name" name="name" maxlength="33"
						       class="input-text" value="${numsense.name }" />
					</td>
				</tr>
				
				<tr>
					<td align="right" class="label">固定电话</td>
					<td class="required"></td>
					<td><input type="text" id="phone" name="phone" maxlength="18"
						       class="input-text" value="${numsense.phone }" />
				    </td>
				</tr>
				
				<tr>
					<td align="right" class="label">移动电话</td>
					<td class="required"></td>
					<td><input type="text" id="tel" name="tel" maxlength="11"
						       class="input-text" value="${numsense.tel }" />
					</td>
				</tr>
				
				<tr>
					<td align="right" class="label">邮箱</td>
					<td class="required"></td>
					<td><input type="text" id="email" name="email" maxlength="33"
						       class="input-text" value="${numsense.email }" />
					</td>
				</tr>
								
				<tr>
					<td align="right" class="label">背景颜色 </td>
					<td class="required"><h:tip title="请选择或填写适当的RGB颜色，格式为#ffffff"></h:tip></td>
					<td><input type="text" id="bgColor" name="bgColor"
						class="input-text" maxlength="7" class="input-text"
						value="${bgColor}" style="width:266px;" />
						<div id="colorSelector"><div id="colorSelector1" style="background-color: #008fd5"></div></div>
					</td>
				</tr>
						
				<tr>
					<td align="right" class="label">图片</td>
					<td class="required"><h:tip title="图片像素100*100，大小不超过1M"></h:tip></td>
					<td><input type="file" id="logoFile" name="logoFile" input-width="200" class="input-text" />
						<input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('logoFile','iconPath')"></td>
				</tr>
					
				<tr>
					<td align="right" class="label">LBS</td>
					<td class="required"></td>
					<td><input type="text"  class="input-text" name="poilocationtmp" id="poilocationtmp" disabled="disabled" value="${numsense.address}" style="width:223px"/>
						<input type="button" class="btn btn-success btn-small" value="设置坐标" onClick="selectpoint();" ></td>
				</tr>
					
				<tr>
					<td align="right" class="label">URL</td>
					<td class="required"></td>
					<td><textarea id="url" name="url" class="input-textarea">${numsense.url }</textarea></td>
				</tr>
								
				<tr>
					<td align="right" class="label">简介</td>
					<td class="required"></td>
					<td><input type="hidden" id="spec2" name="spec2" class="input-text"/>
					<textarea id='spec' name='spec'>${numsense.spec}</textarea>
					<script language='javascript'>
										UE.getEditor('spec',{
										    toolbars: [
										               ['source','|', 'undo', 'redo','|', 'bold', 'formatmatch', 'forecolor', 'removeformat','|',
										                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify']
										           ],
										           initialFrameWidth:310,  //初始化编辑器宽度,默认1000
										           initialFrameHeight:120
										           });
									</script></td>
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