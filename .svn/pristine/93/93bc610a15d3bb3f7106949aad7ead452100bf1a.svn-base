<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="h" uri="/hanweb-tags"%> 
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>APP自动创建工场</title>
	</head>
	<h:head cookie="false" pagetype="dialog" placeholder="true" calendar="false" validity="true" tip="true" select="true" upload="false" tree="true"></h:head> 
	<h:import type="css" path="/resources/app/css/common.css"></h:import> 
	<script src="${contextPath}/resources/app/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="${contextPath}/resources/app/js/jquery.placeholder.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function(){
			$('.login_form').placeholder();
			$('.login_code').placeholder();
			});
				function toRegist(){ 
					location.href = './regist.do';
				} 
				function toCheck() {  
					var reg = "^[A-Za-z0-9\u4e00-\u9fa5]+$";
					var re = new RegExp(reg); 
					var username = $('#username').val();
					var pwd = $('#pwd').val();
					var randcode = $('#randcode').val(); 
					if(username.replace(/[ ]/g,"")=='' || username=='输入登录名' ){
						$('#s1').html('请输入登录名');
						return false;
					}
					if(!re.test(username)){
						$('#s1').html('登录名只能由字母、数字、中文组成');
						return false;
						}
					if(pwd.replace(/[ ]/g,"")=='' || pwd=='输入密码' ){
						$('#s1').html('请输入密码');
						return false;
					}
					if(randcode.replace(/[ ]/g,"")=='' || randcode=='输入验证码' ){
						$('#s1').html('请输入验证码');
						return false;
					}
					$.ajax({
						type : "GET",
						url :'checkuser.do',
						dataType : 'json', 
						data : "username=" + username + "&pwd=" + pwd + "&randcode=" + randcode,
						success : function(data) { 
							if (data.msg == "1"){
								location.href= '${jmpurl}/app/login.do';
							}else if(data.msg == "2"){ 
								$('#verifyImg').click();
								$('#s1').html('用户名密码错误');
							}else{
								$('#verifyImg').click();
								$('#s1').html('验证码错误');
							}
						}
					});
				}
		</script>  
	<style type="text/css"> 
	    .sso{ background:url(${contextPath}/resources/app/images/index/background.png) no-repeat center center #505D6E;font-family:微软雅黑;}
	     .logo-box{position:relative;width:500px;height:300px;border-radius:3px;background:#fff;float:left;}
	     .login-wrap{width:900px;height:300px;position: absolute;top: 50%;left: 50%;margin: -175px 0 0 -450px;}
.login-wrap .logo{width:400px;height:300px;background:url(${contextPath}/resources/app/images/index/logo.png) no-repeat left 10px;float:left;}
.login-wrap .logo-box{position:relative;width:500px;height:300px;border-radius:3px;background:#fff;float:left;}
		body{ width: 100%; background: url(${contextPath}/resources/app/images/index/login_15.jpg) repeat-x top; font-family:微软雅黑;} 
		.top{ height: 72px; border-bottom: 1px solid #cfcfcf; width: 100%;}
		.top .center{ height: 72px; width: 1033px; margin: 0 auto; padding-left: 20px; font-size: 28px; line-height: 72px; margin-bottom: 34px;}
		.login_main{ width:1000px; margin: auto;margin-top:100px;margin-left:26%}
		.login_left{ margin-left:30px;margin: auto;}
		.Large_font{ font-size: 28px; color: #000;}
		ul.login_form{ margin-left:40px;margin-top:20px;width: 380px;}
		ul.login_code{ width: 280px;}
		li.input_name{ height: 50px; line-height: 60px; background: url(${contextPath}/resources/app/images/index/login_19.png) no-repeat left 18px; border-bottom: 1px solid #cfcfcf; padding-left: 40px;}
		li.input_pw{ height: 50px; line-height: 60px; background: url(${contextPath}/resources/app/images/register/passw.jpg) no-repeat left 7px; border-bottom: 1px solid #cfcfcf; padding-left: 40px;}
		li.input_code{ margin-bottom:25px;height: 50px; line-height: 60px; background: url(${contextPath}/resources/app/images/register/passw.jpg) no-repeat left 7px; border-bottom: 1px solid #cfcfcf; padding-left: 40px;}
		li.input_login{ margin-bottom:10px;height: 40px; line-height: 40px;  }
		li.input_tip{ margin-bottom:10px;height: 40px; line-height: 40px;   }
		.login_form{ border: none; height: 50px; line-height: 60px; font-size: 20px; color: #747474; font-family: 微软雅黑; width: 350px;}
		.login_code{ border: none; height: 50px; line-height: 60px; font-size: 20px; color: #747474; font-family: 微软雅黑; width: 250px;}
		.forget_pw{text-align:right; padding-right:90px; font-size:15px; height: 50px; line-height: 50px;}
		.zhuce{ width: 250px; float: right;margin-top:30px}
		.banq{width:100% ; height:56px ; background-color: #373737; padding-top: 14px; text-align: center; color: #fff; font-size: 13px; position: fixed; bottom: 0px;}
		
		
		input:-webkit-autofill { 
			-webkit-box-shadow: 0 0 0px 1000px white inset;   
			border-radius: 0 4px 4px 0; 
		} 

	</style>
	<body class="sso">   
	<div class="login-wrap passport-box">
	<div class="logo"></div>
		<div class="logo-box">
			<form action="${url }" method="post" id="oprform" name="oprform">
			    <div class="login_left"> 
			      <ul class="login_form">
			         <li class="input_name"><input style="outline:none" maxlength="10" name="username" id="username" type="text" placeholder="输入登录名" value="${username}"  autocomplete="off" class="login_form"/></li>
			         <li class="input_pw"><input style="outline:none" maxlength="16" name="pwd" id="pwd" type="password" placeholder="输入密码"  autocomplete="off" class="login_form"/></li>
			         <li class="input_code"><input style="outline:none" id="randcode" type="text" name="randcode" placeholder="输入验证码" maxlength="4"  autocomplete="off"  class="login_code"/><h:verifycode url="verifyCode.do" width="70" height="30"></h:verifycode>
												</li>
					 <li class="input_login"> <a onclick="toCheck()"><img src="${contextPath}/resources/app/images/index/login_16.jpg" alt="" style="cursor:pointer;"/></a>
					</li>
			        
			         <li class="input_tip"><span style="padding-left:35%;color:red;height:10px; line-height:10px; text-align:center;" id="s1" name="s1" value=""></span>
												</li>
			         
			      </ul>
			    </div>
			     
		    </form>  
  		</div> 
  		</div> 
	</body>
</html>
