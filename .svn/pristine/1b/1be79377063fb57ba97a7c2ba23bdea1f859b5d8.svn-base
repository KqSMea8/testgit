<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="h" uri="/hanweb-tags"%> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>APP自动创建工场</title>
	</head>
	<h:head cookie="false" pagetype="dialog" calendar="false" validity="true" tip="true" select="true" upload="false" tree="true"></h:head> 
	  <script src="${contextPath}/resources/app/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script type="text/javascript">
				function toRegist1(){
					location.href = './toRegist.do';
				}
				
				function toCheck() {
					var loginname = $('#loginname').val();
					var pwd = $('#pwd').val();
					var randcode = $('#randcode').val();
					var siteid = ${siteId};
					$.ajax({
						type : "POST",
						url :'checkmanageuser.do',
						dataType : 'json', 
						data : "loginName=" + loginname + "&pwd=" + pwd +"&siteid=" +siteid+ "&randcode=" + randcode,
						success : function(msg) {
							if ( msg == "1"){
								location.href= '${contextPath}/jislogin.do?loginuser='+loginname+'&loginpass='+pwd;
							}
							else if( msg == "2"){
								$('#s1').html('用户名密码错误');
							}else{
								$('#s1').html('验证码错误');
								}
						}
					});
				}
							
		</script>  
	<style type="text/css">
	   body{
	   	margin: 0px; 
	   }
	   
	    
	</style>
	<body> 
		 <div style="height:71px;width:100%;position:relative;background-color:#f9f9f9; border-bottom:solid 1px #eeeeee;border-top:4px solid rgb(55, 55, 55);">
		 </div>
		 <div style=" width: 500px;min-height: 300px;margin-bottom: 20px;  min-height: 300px;  margin:auto;">
		 	<form action="${url }" method="post" id="oprform" name="oprform">
			  登录名：<input id="loginname" name="loginname" value=""/><br/>
			  密     码:<input id= "pwd" name="pwd" value="" /><span id="s1" name="s1" value=""></span><br/>
			 			 <tr>
								<td height="45" width="50" align="right" valign="middle">验证码：</td>
								<td align="left" valign="middle">
									<input type="text" id="randcode" maxlength="4" name="randcode" class="input-text" style="width:145px;"/>
									<h:verifycode url="" var="managelogin_rand" height="30" width="70"></h:verifycode>
								</td>
							</tr>
			 <input type="button" value="登录" onclick="toCheck()"/>
			  </form>
		</div>
		 <div  style="height:40px;width:100%;position:absolute; bottom:0; position:fixed;color:#ffffff; background-color:rgb(55, 55, 55);;line-height:40px;font-size: 15px;font-family:Microsoft YaHei,微软雅黑,Helvetica,黑体,Arial,Tahoma;">  
			<span style="float:left;padding-left:10%;">南京大汉网络有限公司版权所有</span> <span style="float:right;padding-right:10%;">建议使用Chrome浏览器以获得更佳的体验</span>
		</div> 
	</body>
</html>
