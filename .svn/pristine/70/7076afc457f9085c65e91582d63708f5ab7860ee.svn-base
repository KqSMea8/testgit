	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="h" uri="/WEB-INF/tag/hanweb-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统参数</title>
<h:head pagetype="page" validity="true" toggle="true" upload="true"></h:head>
<!--使用${contextPath}可以获得应用路径，并无/结尾-->
<style>
#uplload_qrcode{
	display: none;
}
</style>
<script>
	/**
	 * 表单验证
	 */
	$(function() {
		$('#oprform').validity(function() {
			$('#loginerror').require('登录错误次数必须填写').match('intege1','登录错误次数必须为正整数').range(1,100,'登录错误次数可输入范围为1-100');
			$('#bantimes').require('登录封停时间必须填写').match('intege1','登录封停时间必须为正整数').range(1,100,'登录封停时间可输入范围为1-100');
			$('#filetmp').require('文件暂存目录必须填写');
			$('#imagedir').require('高级编辑器图片目录必须填写');
			$('#attachmentdir').require('高级编辑器附件目录必须填写');
		},{
			type : 'iframe',
		});

		/* $(function() {
			
			$('#oprform').validity(function () {
				$('#file').require('选择文件')
			}, {
				type : 'iframe'
			});
			
		}); */
		$('#enableVerifyCode,#hasProxy,#qrcodeLogin').toggle({
			onValue : true,
			offValue : false
		});
		$('#dynamicCodeLogin').toggle({
			onValue : true,
			offValue : false,
			toggleAfter:function(){
				showQrcodeUpload();
			}
		});
		showQrcodeUpload();
	});
	
	function showQrcodeUpload(){
		if($('#dynamicCodeLogin').val() == 'true'){
			$('#uplload_qrcode').show();
		}else{
			$('#uplload_qrcode').hide();
		}
	}
	
	/**
	function setIpHead(){
		if($('#hasProxy').val() == 'true'){
			$('#ipHead_box').show();
		}else{
			$('#ipHead_box').hide();
		}
	}*/
	

</script>
</head>
<body>
	<div id="page-title">
		<span id="page-location">系统参数</span>
	</div>
	<div id="page-content">
		<!--表单主体-->
		<form action="${url}" method="post" id="oprform" name="oprform" enctype="multipart/form-data">
			<!--隐藏变量区-->
			<table border="0" align="center" class="table">
				<tr>
					<td align="right" class="label">IP参数名称</td>
					<td>
						<input type="text" id="ipHead" name="ipHead" maxlength="33" class="input-text" value="${setting.ipHead }" />
						<div style="color: #cc0066;text-align: left;">
							配置代理服务器中设置的IP参数名称，一般情况下Nginx为X-Real-IP，但要先去Nginx配置
						</div>
					</td>
				</tr>
				<tr>
					<td align="right" class="label">密码强度等级</td>
					<td>
						<label style="margin-right:20px;"><input type="radio" name="checkLevel" value="0" data-value="${setting.checkLevel}"/>弱</label>
						<label style="margin-right:20px;"><input type="radio" name="checkLevel" value="1" data-value="${setting.checkLevel}"/>中</label>
						<label><input type="radio" name="checkLevel" value="2" data-value="${setting.checkLevel}"/>强</label>
					</td>
				</tr>
				<tr>
					<td align="right" class="label">动态码登陆</td>
					<td>
						<input type="hidden" id="dynamicCodeLogin" name="dynamicCodeLogin" value="${setting.dynamicCodeLogin}"/>
						<div style="color: #cc0066;text-align: left;">
							使用APP、微信小程序生成动态码
						</div>
					</td>
				</tr>
				<tr>
				</tr>
				<tr id="uplload_qrcode">
				    <td align="right" class="label">上传二维码</td>
				    <td >
				         <input id="file" type="file" class="input-text" name="file" accept=".png"/>
				         <div style="color: #cc0066;text-align: left;">
							上传动态码微信小程序二维码，支持png格式
						</div>
				    </td>
				</tr>
				<tr>
				<td></td>
				</tr>
				<tr>
					<td align="right" class="label">登录验证码</td>
					<td>
						<input type="hidden" id="enableVerifyCode" name="enableVerifyCode" value="${setting.enableVerifyCode}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="label">登录错误次数</td>
					<td>
						<input type="text" id="loginerror" name="loginError" maxlength="2" class="input-text" style="width:40px;" value="${setting.loginError }" />
					</td>
				</tr>
				<tr>
					<td align="right" class="label">登录封停时间（分钟）</td>
					<td>
						<input type="text" id="bantimes" name="banTimes" maxlength="2" class="input-text" style="width:40px;" value="${setting.banTimes }" />
					</td>
				</tr>
				<tr style="display:none;">
					<td align="right" class="label">session失效时间（分钟）</td>
					<td>
						<input type="text" id="sessiontime" name="sessionTime" maxlength="33" class="input-text" value="${setting.sessionTime }" />
					</td>
				</tr>
				<tr>
					<td align="right" class="label">文件暂存目录</td>
					<td>
						<textarea id="filetmp" name="fileTmp" maxlength="200" class="input-textarea" style="width:500px;height:50px;">${setting.fileTmp}</textarea>
					</td>
				</tr>
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
				<tr>
					<td></td>
					<td>
						<input type="submit" class="btn btn-primary" value="保存" />&nbsp;
						<input type="reset" class="btn" value="重置" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>