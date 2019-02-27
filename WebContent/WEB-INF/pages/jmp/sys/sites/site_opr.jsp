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
<title>站点管理</title>
<h:head pagetype="dialog" multiselect="true" menu="true" tree="true"
	validity="true" checkpwd="true" tabs="true" tip="true" select="true"></h:head>
<link rel="stylesheet" href="${contextPath}/ui/widgets/colorpicker/css/colorpicker.css" type="text/css" />
<script type="text/javascript" src="${contextPath}/ui/widgets/colorpicker/colorpicker.js"></script>
<script>
	var isGroupAdminUser = false;
	var isSysAdminUser = false;

	$(function() {
		var pwdPower = ${checkPasswordLevel};
		var levelNum = 0;
		setSearch();//是否支持全文检索
		setOfflineDownload();//是否支持离线下载
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
		
		var bgColor = $("#color").val();
		if(bgColor!=""){
			 $("#colorSelector1").css("background-color",bgColor);
			}
		
		$("#oprform").validity(
			function() {
				$("#name").require("站点名称不能为空") 
						.match('username1', "站点名称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾")
						.maxLength(33, "站点名称不能超过33个字");
				$("#color").assert(function(){
					var color = $("#color").val();
					if(color == ''){
						return true;
					}
					var re=/^#[a-fA-F0-9]{6}$/g;//匹配IP地址的正则表达式 
					return re.test(color);},"背景颜色不正确");  
				if($("[name$=isSearch]:checked").val() == '1'){
					$("#searchurl").require("索引标识不能为空").maxLength(50, "索引标识不能超过50个字");
					$("#searchwebid").require("索引站点ID不能为空").maxLength(50, "索引站点ID不能超过50个字");
				}
				if($("[name$=isDiscount]:checked").val() == '1'){
					$("#orderCanceld").match('num1', "过期天数只能为0或正整数");
					
				}
				
				$("#loginname").require("登录名不能为空") 
					.match('username1', "登录名只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾")
					.maxLength(33, "登录名不能超过33个字");
				if ('${url }' == 'add_submit.do' || $.trim($("#pwd").val()).length > 0) {
					$("#pwd").require("密码不能为空")
						.minLength(6, "密码最少要6个字")
						.maxLength(18, "密码最多18个字").assert(function(){  
							if(level == "weak"){
								levelNum = 0;
							}else if(level == "medium"){
								levelNum = 1;
							}else if(level == "strong"){
								levelNum = 2;
							}
							if(levelNum < pwdPower){
								return false;
							}else{
								return true;
							}
						},'密码强度低于系统要求');
				}
				$("#username").require("昵称不能为空")
					.match('username1', "昵称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾")
					.maxLength(33, "昵称不能超过33个字");
				if($.trim($("#phone").val()).length > 0){
					$("#phone").match('tel','请填写正确的固定号码');
				}
				if($.trim($("#mobile").val()).length > 0){
					$("#mobile").match('mobile','请填写正确的手机号码');
				}
				
				if($.trim($("#email").val()).length > 0){
					$("#email").match('email','请填写正确的email地址');
				}
				if($.trim($("#qq").val()).length > 0){
					$("#qq").match('qq','请填写正确的qq号码');
				}
				if($.trim($("#msn").val()).length > 0){
					$("#msn").match('email','请填写正确的msn地址');
				}
				
			},{
				success:function(result){
				if(result.success){
					closeDialog(true);
				} else {
					alert(result.message);
				}
			}
		});

		//根据是否支持注册开启注册状态选择器
		if('${siteFrom.site.isSupportReg}' == 0){
			$('#regstate').css('display','none');
			$('#loginstate').css('display','none');
		}

		//根据是否支持记录日志开启日志保留天数
		if('${siteFrom.site.isRecord}' == 0){
			$('#days').css('display','none');
		}				
	});

	//根据是否支持全文检索开启索引标识和索引站点id
	function setSearch(){
		var issearch = $("[name$=isSearch]:checked").val();
		if(issearch == '0'){
			$("#searchurl_trid").hide();
			$("#searchurl_trid1").hide();
		}else{
			$("#searchurl_trid").show();
			$("#searchurl_trid1").show();
		}
	}

	//是否支持离线下载
	function setOfflineDownload(){
		var isOfflineZip = $("[name$=isOfflineZip]:checked").val();
	}
	
	//根据是否支持注册开启注册状态选择器
	function changeRegState(value){
		if(value == 0){
			$('#regstate').hide();
			$('#loginstate').hide();
		}else if(value == 1){
			$('#regstate').show();
			$('#loginstate').show();
		}
	}

	//根据是否记录日志开启保留天数
	function showDays(value){
		if(value == 0){
			$('#days').hide();
		}else{
			$('#days').show();
		}
	}
		
</script>
<style type="text/css">
#colorSelector{position:relative;width:36px;height:36px;
	background:url(${contextPath}/ui/widgets/colorpicker/images/select2.png);
	display: inline;padding-bottom:20px;}
#colorSelector div{position:absolute;top:-6px;left:-2px;width:30px;height:30px;
	background:url(${contextPath}/ui/widgets/colorpicker/images/select2.png) center;} 
.input-textarea {resize: none;}

.ztree li a{display:inline-block}
</style>
</head>
<body>
	<form action="${url }" method="post" id="oprform" name="oprform">
		<input id="siteid" type="hidden" name="site.iid" value="${siteFrom.site.iid }">
		<input type="hidden" name="user.iid" value="${siteFrom.user.iid }">
		<input type="hidden" name="site.overall" value="${siteFrom.site.overall }">
		<div id="dialog-content">
		<div id="tabs" class="easyui-tabs" style="height: 480px;"> 
			<div title="基本属性" style="padding:10px; height:443px; overflow-y:auto;">
			<table border="0" align="center" cellpadding="10" cellspacing="0" class="table">
				<tr>
					<td align="right"  style="width: 80px"  class="label">站点名称</td>
					<td class="required" style="width: 10px">*</td>
					<td><input type="text" id="name" name="site.name" class="input-text" maxlength="33"
							   value="${siteFrom.site.name }" />
					</td>
				</tr>
				<tr>
					<td align="right" class="label">背景颜色 </td>
					<td class="required">*</td>
					<td><input type="text" id="color" name="site.color" class="input-text" maxlength="7"
						       value="${siteFrom.site.color }" style="width:266px;" />
						<div id="colorSelector"><div id="colorSelector1" style="background-color: #008fd5"></div></div>
					</td>
				</tr>
				<tr>
					<td align="right" class="label">全文检索 </td>
					<td class="required">&nbsp;</td>
					<td>
						<input type="radio" name="site.isSearch" value="0" 
							data-value="${siteFrom.site.isSearch }" onclick="setSearch();">不支持
						<input type="radio" name="site.isSearch" value="1" 
							data-value="${siteFrom.site.isSearch }" onclick="setSearch();">支持
					</td>
				</tr>
				<tr style="display:none;" id="searchurl_trid">
					<td align="right" class="label">索引标识 </td>
					<td class="required">*</td>
					<td>
						<input type="text" name="site.searchUrl" id="searchurl" class="input-text" 
						       value="${siteFrom.site.searchUrl}" />
					</td>
				</tr>
				<tr style="display:none;" id="searchurl_trid1">
					<td align="right" class="label">索引站点ID </td>
					<td class="required">*</td>
					<td>
						<input type="text" name="site.searchWebId" id="searchwebid" class="input-text" 
						       value="${siteFrom.site.searchWebId}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="label">离线下载 </td>
					<td class="required">&nbsp;</td>
					<td>
						<input type="radio" name="site.isOfflineZip" value="0" 
							data-value="${siteFrom.site.isOfflineZip }" onclick="setOfflineDownload();">不支持
						<input type="radio" name="site.isOfflineZip" value="1" 
							data-value="${siteFrom.site.isOfflineZip }" onclick="setOfflineDownload();">支持
					</td>
				</tr>
				<tr>
					<td align="right" class="label">手机注册</td>
					<td class="required"></td>
					<td>
						<input type="radio" name="site.isSupportReg" value="0" 
							data-value="${siteFrom.site.isSupportReg }" onclick="changeRegState(this.value);">不支持
						<input type="radio" name="site.isSupportReg" value="1" 
							data-value="${siteFrom.site.isSupportReg }" onclick="changeRegState(this.value);">支持
					</td>
				</tr>
				<tr id="regstate">
					<td align="right" class="label">默认状态 </td>
					<td class="required"></td>
					<td>
						<select name="site.regState" data-value="${siteFrom.site.regState }">
							<option value="1">启用</option>
							<option value="0">停用</option>
						</select>
					</td>
				</tr>	
				<tr id="loginstate">
					<td align="right" class="label">登录方式 </td>
					<td class="required"></td>
					<td>
						<select name="site.loginType" data-value="${siteFrom.site.loginType}">
							<option value="1">手机登录</option>
							<option value="2">普通登录</option>
							<option value="3">第三方登录</option>
							<option value="4">组合登录（支持手机登录和第三方登录）</option>
						</select>
					</td>
				</tr>	
			</table>
		</div>
		<div title="高级属性" style="padding:10px; height:443px; overflow:auto;">
			<table border="0" align="center" cellpadding="10" cellspacing="0"
				class="table">
				<tr>
					<td align="right" class="label">接口日志 </td>
					<td class="required" style="width:20px;">&nbsp;</td>
					<td>
						<input type="radio" name="site.isRecord" value="0" 
							data-value="${siteFrom.site.isRecord }" onclick="showDays(this.value);">不支持
						<input type="radio" name="site.isRecord" value="1" 
							data-value="${siteFrom.site.isRecord }" onclick="showDays(this.value);">支持
					</td>
				</tr>
				<tr id="days">
				    <td align="right" class="label">保留天数</td>
				    <td class="required">&nbsp;</td>
				    <td>
				        <input type="text" name="site.holdTime" id="holdTime" class="input-text"
				        value="${siteFrom.site.holdTime}" }></input>
				    </td>
				</tr>
				<tr>
					<td align="right" class="label">安全控制 </td>
					<td class="required">&nbsp;</td>
					<td>
						<input type="radio" name="site.isSafeControl" value="0" 
							data-value="${siteFrom.site.isSafeControl }" >不支持
						<input type="radio" name="site.isSafeControl" value="1" 
							data-value="${siteFrom.site.isSafeControl }" >支持
					</td>
				</tr>
			</table>
		</div>		
		<div title="站点管理员" style="padding:10px; height:443px; overflow-y:auto" >
     		   <table border="0" align="center" cellpadding="10" cellspacing="0" class="table">
				<tr>
					<td align="right" class="label">登录名</td>
					<c:choose>
						<c:when test="${url=='modify_submit.do'}">
							<td>&nbsp;</td>
							<td><input type="text" name="user.loginName" id="loginname"
								maxlength="33" class="input-text" value="${siteFrom.user.loginName }"
								readonly="readonly" />&nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="required">*</td>
							<td><input type="text" name="user.loginName" id="loginname"
								maxlength="33" class="input-text" value="${siteFrom.user.loginName }" />&nbsp;</td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td align="right" class="label">密码</td>
					<c:choose>
						<c:when test="${url=='modify_submit.do'}">
							<td><h:tip title="不修改时请保持为空"></h:tip></td>
						</c:when>
						<c:otherwise>
							<td class="required">*</td>
						</c:otherwise>
					</c:choose>
					<td><input type="password" name="user.pwd" id="pwd"
						class="input-text" value="" onkeyup="javascript:EvalPwd(this.value);" onkeydown="if(event.keyCode==32) {event.returnValue = false;return false;}" onpaste="return false"/>
					</td>
				</tr>
				<tr>
					<td align="right" class="label">&nbsp;</td>
					<td>&nbsp;</td>
					<td>
						<table cellpadding="0" cellspacing="0" border="0" title="字母加数字加符号就会强" id="pwdpower" style="width: 100%">
							<tr>
								<td id="pweak" style="">弱</td>
								<td id="pmedium" style="">中</td>
								<td id="pstrong" style="">强</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align="right" class="label">昵称</td>
					<td class="required">*</td>
					<td><input type="text" id="username" name="user.name"
						class="input-text" maxlength="33" class="input-text"
						value="${siteFrom.user.name }" />
					</td>
				</tr>
				<tr>
					<td align="right" class="label">职务</td>
					<td>&nbsp;</td>
					<td><input type="text" name="user.headship" id="headship"
						class="input-text" value="${siteFrom.user.headship}" 
						maxlength="33"/>
					</td>
				</tr>
				<tr>
					<td align="right" class="label">固定电话</td>
					<td>&nbsp;</td>
					<td><input type="text" name="user.phone" id="phone"
						class="input-text" value="${siteFrom.user.phone}" 
						maxlength="20"/>
					</td>
				</tr>
				<tr>
					<td align="right" class="label">移动电话</td>
					<td>&nbsp;</td>
					<td><input type="text" name="user.mobile" id="mobile"
						class="input-text" value="${siteFrom.user.mobile}" 
						maxlength="11"/>
					</td>
				</tr>

				<tr>
					<td align="right" class="label">Email</td>
					<td>&nbsp;</td>
					<td><input type="text" name="user.email" id="email"
						class="input-text" value="${siteFrom.user.email}" 
						maxlength="33"/>
					</td>
				</tr>

				<tr>
					<td align="right" class="label">联系方式</td>
					<td>&nbsp;</td>
					<td>
						<textarea name="user.contact" id="contact" class="input-textarea" maxlength="80" 
							onkeydown="checklength(this);" onmousedown="checklength(this);">${siteFrom.user.contact}</textarea>
					</td>
				</tr>
				<tr>
					<td align="right" class="label">联系地址</td>
					<td>&nbsp;</td>
					<td><input type="text" name="user.address" id="address"
						class="input-text" value="${siteFrom.user.address}" 
						maxlength="33"/>
					</td>
				</tr>
			</table>
        </div>  
        </div> 
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