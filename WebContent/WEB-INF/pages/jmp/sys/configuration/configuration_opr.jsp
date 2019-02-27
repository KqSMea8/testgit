<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="h" uri="/WEB-INF/tag/hanweb-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统参数</title>
<h:head pagetype="page" tabs="true" tip="true" validity="true" select="true" toggle="true"></h:head>
<!--使用${contextPath}可以获得应用路径，并无/结尾-->
<script>
/**
* 表单验证
*/
$(function(){
	var wxSupport = '${config.wxSupport}';
	
	if(wxSupport==0){
		$("#serviceCode").hide();
		$("#key").hide();
		$("#weixinUrl").hide();
	}else{
		$("#serviceCode").show();
		$("#key").show();
		$("#weixinUrl").show();
	}	
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
	$('#enableVerifyCode').toggle({
		onValue : true,
		offValue : false
	});
	$('#oprform').validity(function() {
		//系统参数验证 
		$('#jmpUrl').require('系统域名必须填写');
        //信息同步参数设置 
		$('#synPeroid').require('信息同步周期必须填写').match('intege1','信息同步周期必须为正整数');
		$('#infoSaveType').require('信息保留方式必须填写');
		$('#infoSaveCounts').require('栏目保留最新信息数必须填写').match('intege1','栏目保留最新信息数必须为正整数');	
		$('#infoSaveDays').require('栏目保留最新信息天数必须填写').match('intege1','栏目保留最新信息天数必须为正整数');
        //平台参数 
		$('#loginerror').require('登录错误次数必须填写').match('intege1','登录错误次数必须为正整数').range(1,100,'登录错误次数可输入范围为1-100');
		$('#bantimes').require('登录封停时间必须填写').match('intege1','登录封停时间必须为正整数').range(1,100,'登录封停时间可输入范围为1-100');
		$('#sessiontime').require('session失效时间必须填写').match('intege1','session失效时间必须为正整数').range(1,100,'session失效时间可输入范围为1-100');
		$('#filetmp').require('文件暂存目录必须填写');
		$('#imagedir').require('高级编辑器图片目录必须填写');
		$('#attachmentdir').require('高级编辑器附件目录必须填写');
	},{
		success:function(result){
		if(result.success){
			if(result.success){
				alert('保存成功');
			} else {
				alert('保存失败');
			}
		}
	}
});
});

function showType(typeId){
	if(typeId==0){//按信息数
		$("#infonum").show();
		$("#infoday").hide();
	}else if(typeId==1){//按时间
		$("#infoday").show();
		$("#infonum").hide();
	}
}

function showTabTag(typeId) {
	if(typeId==0){
		$('#tabs').tabs('disableTab','app工厂参数设置');
	}else if(typeId==1){
		$('#tabs').tabs('enableTab','app工厂参数设置');
	}
}

function testUrl(){
	var url = $("#jgetUrl").val();
	if (url == "" || url.length == 0) {
		alert("请填写地址！");
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
function setSearch(){
	var issearch = $("[name$=wxSupport]:checked").val();
	
	if(issearch == '0'){
		$("#serviceCode").hide();
		$("#key").hide();
		$("#weixinUrl").hide();
	}else{
		$("#serviceCode").show();
		$("#key").show();
		$("#weixinUrl").show();
	}
}

</script>
<style type="text/css">
	.input-textarea {
		resize: none;
	}
</style>
</head>
<body>
	<div id="page-title">
		<span id="page-location">系统参数</span>
	</div>
	<div id="page-content">
    	<!--表单主体-->
		<form action="${url}" method="post" id="oprform" name="oprform">
			<div id="tabs" class="easyui-tabs" style="height: 550px;">   
				<div title="基本设置" style="padding:20px;">
					<table border="0" align="left"  class="table">
						<tr>
							<td align="right" class="label">系统域名</td>
							<td class="required" width="15px">*</td>
							<td><input type="text" id="jmpUrl" name="jmpUrl" maxlength="200" 
								class="input-text" value="${config.jmpUrl }" style="width:430px"/></td>
						</tr>
						<tr>
							<td align="right" class="label">全文检索服务器地址</td>
							<td class="required"  width="15px"></td>
							<td><input type="text" id="jsearchUrl" name="jsearchUrl" maxlength="200"
								class="input-text" value="${config.jsearchUrl }" style="width:430px"/></td>
						</tr>
						<tr> 
						    <td align="right" class="label">IP设定</td>
							<td class="required"  width="15px"><h:tip title="提供给第三方PC端系统调用的接口访问IP限制设置，
							多个IP用“#”分割。"></h:tip></td>
							<td><textarea type="text" id="allowIp" name="allowIp" class="input-textarea"
								 style="width:355px;height: 120px">${config.allowIp }</textarea>
							</td>
					    </tr>
					</table>
				</div>
				
				<div title="信息相关设置" style="padding:20px;">
					<table border="0" align="left" cellpadding="10" cellspacing="0" class="table">
						<tr>
							<td align="right" class="label">信息保留方式</td>
							<td class="required"  width="15px">*</td>
							<td>
								<label style="margin-right:20px;"><input type="radio" id="infoSaveType" name="infoSaveType" value="0" data-value="${config.infoSaveType  }" onclick="showType(this.value);"/>按信息数保留</label>
								<label style="margin-right:20px;"><input type="radio" id="infoSaveType" name="infoSaveType" value="1" data-value="${config.infoSaveType  }" onclick="showType(this.value);"/>按时间保留</label>
							</td>
						</tr>
						<tr id="infonum">
							<td align="right" class="label">栏目保留最新信息数</td>
							<td class="required"  width="15px">*</td>
							<td><input type="text" id="infoSaveCounts" name="infoSaveCounts" maxlength="33"
								class="input-text" value="${config.infoSaveCounts  }" />&nbsp;&nbsp;条&nbsp;&nbsp;<h:tip title="无需自动清理信息输入“0”(不推荐) "></h:tip></td>
						</tr>
						<tr id="infoday">
							<td align="right" class="label">栏目保留最新信息天数</td>
							<td class="required"  width="15px">*</td>
							<td><input type="text" id="infoSaveDays" name="infoSaveDays" maxlength="33"
								class="input-text" value="${config.infoSaveDays  }" />&nbsp;&nbsp;天&nbsp;&nbsp;<h:tip title="无需自动清理信息输入“0”(不推荐) "></h:tip></td>
						</tr>
						<tr id="sjjg">
							<td align="right" class="label">离线下载时间间隔</td>
							<td class="required" style="width: 15px">*</td>
							<td><input type="text" id="intervalTime" name="intervalTime" maxlength="33"
								class="input-text" value="${config.intervalTime }" />&nbsp;&nbsp;小时&nbsp;&nbsp;</td>
				        </tr>
						
						
						 <tr>
							<td align="right" class="label">是否支持微信微博分享</td>
							<td class="required">&nbsp;</td>
							<td>
								<input type="radio" name="wxSupport" value="0" 
									data-value="${config.wxSupport }" onclick="setSearch();">不支持
								<input type="radio" name="wxSupport" value="1" 
									data-value="${config.wxSupport }" onclick="setSearch();">支持
							</td>
						</tr>
				
						<tr style="display:none;" id="serviceCode">
							<td align="right" class="label">serviceCode </td>
							<td class="required">*</td>
							<td>
								<input type="text" name="serviceCode" 
									class="input-text" value="${config.serviceCode}" />
							</td>
						</tr>
						<tr style="display:none;" id="key">
							<td align="right" class="label">key </td>
							<td class="required">*</td>
							<td>
								<input type="text" name="key" 
									class="input-text" value="${config.key}" />
							</td>
						</tr>
						<tr style="display:none;" id="weixinUrl">
							<td align="right" class="label">url </td>
							<td class="required">*</td>
							<td>
								<input type="text" name="weixinUrl" 
									class="input-text" value="${config.weixinUrl}" />
							</td>
						</tr>
				
			        </table>  
				</div>
				<div title="平台参数设置" style="padding:20px;">
					<table border="0" align="left" cellpadding="10" cellspacing="0" class="table"> 
						<tr>
							<td align="right" class="label">密码强度等级</td>
							<td>
								<label style="margin-right:20px;"><input type="radio" name="checkLevel" value="0" data-value="${setting.checkLevel}"/>弱</label>
								<label style="margin-right:20px;"><input type="radio" name="checkLevel" value="1" data-value="${setting.checkLevel}"/>中</label>
								<label><input type="radio" name="checkLevel" value="2" data-value="${setting.checkLevel}"/>强</label>
							</td>
						</tr>
						<tr>
							<td align="right" class="label">登录验证码</td>
							<td>
								<input type="hidden" id="enableVerifyCode" name="enableVerifyCode" value="${setting.enableVerifyCode}" />
							</td>
						</tr> 
						<tr>
							<td align="right" class="label">登录错误次数</td>
							<td><input type="text" id="loginerror" name="loginError" maxlength="33"
								class="input-text" value="${setting.loginError }" style="width:355px"/></td> 
						</tr>
						<tr>  
							<td align="right" class="label">登录封停时间（分钟）</td>
							<td><input type="text" id="bantimes" name="banTimes" maxlength="33"
								class="input-text" value="${setting.banTimes }" style="width:355px"/></td>
						</tr>
						<tr>
							<td align="right" class="label">session失效时间（分钟）</td>
							<td><input type="text" id="sessiontime" name="sessionTime" maxlength="33"
								class="input-text" value="${setting.sessionTime }" style="width:355px"/></td>
						</tr> 
						<tr> 
							<td align="right" class="label">文件暂存目录</td>
							<td> 
								<textarea id="filetmp" name="fileTmp" maxlength="200" class="input-textarea" style="width:500px;height:50px;">${setting.fileTmp}</textarea>
							 </td>
						</tr> 
						<!--  
						<tr>
							<td align="right" class="label">高级编辑器图片目录</td>
							<td> 
								<textarea id="imagedir" name="imageDir" maxlength="200" class="input-textarea" style="width:500px;height:50px;">${setting.imageDir}</textarea>
						    </td>
						</tr>
						<tr> 
							<td align="right" class="label">高级编辑器附件目录</td>
							<td> 
								<textarea id="attachmentdir" name="attachmentDir" maxlength="200" class="input-textarea" style="width:500px;height:50px;">${setting.attachmentDir}</textarea>
						    </td>
						</tr>
						-->
					</table>
				</div>
			</div>
        	<!--隐藏变量区-->
			<table border="0" align="center" cellpadding="10" cellspacing="0" class="table"> 
				<tr>
					<td height="40" align="center">
						<input type="submit" class="btn btn-primary" value="保存"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>