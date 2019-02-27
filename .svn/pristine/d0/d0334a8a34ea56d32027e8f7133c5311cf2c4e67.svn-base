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
		var appType="${version.apptype}";
		changeRegState(appType);   
		$("#oprform").validity(
				function() {
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
					/** 
					$('#logoFile').assert(function(){
						if($("#logoFile").parent().prev().val().length > 0){
							return true;
						}
				        return false;
					},'应用图标不能为空');
					
					if(apptype==1){ 
						var clientType=$("#clientType").val();
						if(clientType==2 || clientType==4){ 
							$('#ipaFile').assert(function(){
								if($("#ipaFile").parent().prev().val().length > 0){
									return true;
								}
						        return false;
							},'ipa文件不能为空');
							$('#plistFile').assert(function(){
								if($("#plistFile").parent().prev().val().length > 0){
									return true;
								}
						        return false;
							},'plist文件不能为空');  
						}else{
							$('#apkFile').assert(function(){
								if($("#apkFile").parent().prev().val().length > 0){
									return true;
								}
						        return false;
							},'apk文件不能为空');  
						}
					}
					**/
					$("#msg").require("说明不能为空");  
					if($.trim($("#msg").val()).length > 0){
						$("#msg").maxLength(660,"内容不能超过660个字");
					}  
				}, {type:'iframe'});
		$.fn.preview = function(hiddenId){
			var xOffset = 10;
			var yOffset = 20;
			var w = $(window).width();
			var h = $(window).height();
			
			$(this).val(findfilename($("#"+hiddenId).val()));
			if(hiddenId == 'logoPath') {
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
		
		$("#logoFile").parent().prev().preview("logoPath");
		$("#apkFile").parent().prev().preview("downUrl");
		$("#plistFile").parent().prev().preview("downUrl");
		$("#ipaFile").parent().prev().preview("ipa");	
				
		
});


	function selectShow(appType){
		var value = $('input:radio[name="apptype"]:checked').val();
		if(value == 0){
			$("#apkshow").hide();
			$("#plistshow").hide();
			$("#ipashow").hide(); 
			$("#downshow").show(); 
		}else if(value == 1){
			if(appType==2 || appType==4){
				 $("#apkshow").hide();
				 $("#plistshow").show();
				 $("#ipashow").show(); 
			}else{ 
				 $("#apkshow").show();
				 $("#plistshow").hide();
				 $("#ipashow").hide(); 
			}
		}
	}

	
	function changeRegState(value){
		var clientType=$("#clientType").val();
		if(value == 0){
			$("#apkshow").hide();
			$("#plistshow").hide();
			$("#ipashow").hide(); 
			$("#downshow").show(); 
		}else if(value == 1){
			$("#downshow").hide(); 
			if(clientType==2 || clientType==4){
				 $("#apkshow").hide();
				 $("#plistshow").show();
				 $("#ipashow").show(); 
			}else{ 
				 $("#apkshow").show();
				 $("#plistshow").hide();
				 $("#ipashow").hide(); 
			}
		}
	}
	
</script>
</head>
<body>
	<form action="${url }" method="post" id="oprform" enctype="multipart/form-data">
		<input type="hidden" name="iid" value="${version.iid }"/>
		<input type="hidden" name="siteId" value="${version.siteId}"/>
		<input type="hidden" name="appId" value="${version.appId}"/>
		<input type="hidden" id="logoPath" name="logoPath" value="${version.logoPath}"/>
		<input type="hidden" id="ipa" value="${ipa }">
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0"
				class="table">
				<tr>
					<td align="right" class="label">客户端类型</td>
					<td class="required">&nbsp;</td>
					<td><select id="clientType" name="clientType" data-value="${version.clientType }" onchange=selectShow(this.value)>
						<option value="2">iPhone</option>
						<option value="3">Android</option>
						<option value="4">iPad</option>
					</select></td>
				</tr>
				
				<!--  
				<tr>
					<td align="right" class="label">APP类型</td>
					<td class="required"></td>
					<td>
						<input type="radio" name="apptype" value="0" 
							data-value="${version.apptype }" onclick="changeRegState(this.value);">生产包
						<input type="radio" name="apptype" value="1" 
							data-value="${version.apptype }" onclick="changeRegState(this.value);">企业包
					</td>
				</tr>
				-->
				
				<tr>
					<td align="right" class="label">版本号</td>
					<td class="required">*</td>
					<td><input type="text" id="version" name="version" value="${version.version }" class="input-text" maxlength="33"></td>
				</tr>
				
				<!--  
				<tr id="logoshow">
					<td align="right" class="label">应用图标</td>
					<td class="required"><h:tip title="建议大小114*114，格式只支持png或者jpg"></h:tip>*</td>
					<td>
						<input id="logoFile" type="file" class="input-text"
							name="logoFile" input-width="197">  
					</td>	
				</tr>
				-->
				
				<tr id="downshow">
					<td align="right" class="label">下载地址</td>
					<td class="required">*</td>
					<td><input type="text" id="downUrl" name="downUrl" value="${version.downUrl }" class="input-text" maxlength="500"></td>
				</tr>
				
				<!--  
				<tr id="apkshow">
					<td align="right" class="label">A P K 文件</td>
					<td class="required">*</td>
					<td>
						<input id="apkFile" type="file" class="input-text" name="apkFile" input-width="197">  
					</td>	
				</tr>
				
		
				<tr id="plistshow">
					<td align="right" class="label">PLIST文件</td>
					<td class="required">*</td>
					<td>
						<input id="plistFile" type="file" class="input-text" name="plistFile" input-width="197">  
					</td>
				</tr>
				
		
				<tr id="ipashow">
					<td align="right" class="label">I P A 文件</td>
					<td class="required">*</td>
					<td><input id="ipaFile" type="file" class="input-text" name="ipaFile" input-width="197"> 
					</td>
				</tr>  
				-->
				
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