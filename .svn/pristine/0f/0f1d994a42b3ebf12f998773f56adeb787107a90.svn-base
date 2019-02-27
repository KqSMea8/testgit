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
<title>前台用户管理</title>
<h:head pagetype="dialog" validity="true" ipass="true" checkpwd="true"></h:head>
<script type="text/javascript">
$(function(){
	//密码输入控件，防自动填充
	$('#pwd').iPass({keyup:function(){
		EvalPwd($(this).next().val());
	}});
	
	$('#oprform').validity(
		function() { 
			$("#loginname").require("登录名不能为空").maxLength(33,
					"登录名不能超过33个字");

			$("#pwd").require("密码不能为空").maxLength(10,
			"密码不能超过10个字");
			 
		},{
			success:function(result){
				if(result.success){
					closeDialog(true);
				}else{
					alert(result.message);
				}
			}
		}
	);
});

/**
 *	对textarea进行字数控制
 */
function checklength(obj) {
    var max = $(obj).attr('maxlength');
    if(max == null || max == "" || max == undefined) {
        return;
    }
    if(obj.value.length > max) {
        obj.value=obj.value.substring(0,(max-1));
        return;
    }
}
</script>
</head>
<body>
	<form action="${url }" method="post" id="oprform" name="oprform">
		<input type="hidden" name="iid" id="iid" value="${appuser.iid }" />
		<input type="hidden" name="siteId" id="siteId" value="${appuser.siteId }" /> 
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0"
				class="table">
				<tr>
					<td align="right" class="label">用户名</td>
					<td class="required">*</td> 
					<td>
						<c:choose>
							<c:when test="${url=='modify_submit.do'}">
								<input type="text" id="username" name="username"
							class="input-text" maxlength="33" value="${appuser.username}" disabled="disabled"/>&nbsp;
					        </c:when>
							<c:otherwise>
										<input type="text" id="username" name="username"
								class="input-text" maxlength="33" value="${appuser.username}"/>&nbsp;
							</c:otherwise>
						</c:choose>
					</td> 
				</tr>
				 
				<tr>
					<td align="right" class="label">密码</td>
					<td class="required">*</td>
					<td><input type="password" name="pwd" id="pwd"
						class="input-text" maxlength="18" value="" onkeyup="javascript:EvalPwd(this.value)"/></td>
				</tr>
				<tr>
					<td align="right" class="label">密码强度</td>
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