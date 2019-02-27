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
		body{ width: 100%; background: url(${contextPath}/resources/app/images/index/login_15.jpg) repeat-x top; font-family:微软雅黑;} 
		.top{ height: 72px; border-bottom: 1px solid #cfcfcf; width: 100%;}
		.top .center{ height: 72px; width: 1033px; margin: 0 auto; padding-left: 20px; font-size: 28px; line-height: 72px; margin-bottom: 34px;}
		.login_main{ width:1000px; margin: 75px auto; height: 370px;}
		.login_left{height: 390px; float: left; width: 550px; border-right: 1px solid #cfcfcf;margin-top:30px}
		.Large_font{ font-size: 28px; color: #000;}
		ul.login_form{ width: 380px;}
		ul.login_code{ width: 280px;}
		li.input_name{ height: 50px; line-height: 60px; background: url(${contextPath}/resources/app/images/index/login_19.png) no-repeat left 18px; border-bottom: 1px solid #cfcfcf; padding-left: 40px;}
		li.input_pw{ height: 50px; line-height: 60px; background: url(${contextPath}/resources/app/images/register/passw.jpg) no-repeat left 7px; border-bottom: 1px solid #cfcfcf; padding-left: 40px;}
		li.input_code{ height: 50px; line-height: 60px; background: url(${contextPath}/resources/app/images/register/passw.jpg) no-repeat left 7px; border-bottom: 1px solid #cfcfcf; padding-left: 40px;}
		.login_form{ border: none; height: 50px; line-height: 60px; font-size: 20px; color: #747474; font-family: 微软雅黑; width: 350px;}
		.login_code{ border: none; height: 50px; line-height: 60px; font-size: 20px; color: #747474; font-family: 微软雅黑; width: 250px;}
		.forget_pw{text-align:right; padding-right:90px; font-size:15px; height: 50px; line-height: 50px;}
		.zhuce{ width: 250px; float: right;margin-top:30px}
		.banq{width:100% ; height:56px ; background-color: #373737; padding-top: 14px; text-align: center; color: #fff; font-size: 13px; position: fixed; bottom: 0px;}
	</style>
	<body> 
		<div class="top">
		  <div class="center">APP自动创建工场</div>
		</div>
		<div class="login_main">
			<form action="${url }" method="post" id="oprform" name="oprform">
			    <div class="login_left">
			      <p class="Large_font">用户登录</p>
			      <ul class="login_form">
			         <li class="input_name"><input style="outline:none" maxlength="10" name="username" id="username" type="text" placeholder="输入登录名" value="${username }"  autocomplete="off" class="login_form"/></li>
			         <li class="input_pw"><input style="outline:none" maxlength="16" name="pwd" id="pwd" type="password" placeholder="输入密码"  autocomplete="off" class="login_form"/></li>
			         <li class="input_code"><input style="outline:none" id="randcode" type="text" name="randcode" placeholder="输入验证码" maxlength="4"  autocomplete="off"  class="login_code"/><h:verifycode url="verifyCode.do" width="70" height="30"></h:verifycode>
												</li>
			         <div style="color:red;text-align:center" id="s1" name="s1" value=""></div>
			         <a onclick="toCheck()"><img src="${contextPath}/resources/app/images/index/login_16.jpg" alt="" style="cursor:pointer;"/></a>
			      </ul>
			    </div>
		    </form> 
		    <div class="login_right" >
		      <div class="zhuce">
		        <p class="Large_font">还没有账号？</p>
		        <a onclick="toRegist()"><img src="${contextPath}/resources/app/images/index/zhuce.jpg" alt="" style="cursor:pointer;margin:16px 0;"/></a>
		        <a href="${sinaUrl}"><img src="${contextPath}/resources/app/images/index/sina_logo.jpg" alt="" /></a>
		        <a href="${qqUrl}"><img src="${contextPath}/resources/app/images/index/qq_logo.jpg" alt="" style="margin-left:40px;"/></a>
		      </div>
		    </div>
  		</div> 
		<div class="banq">
		    <div>北京国信大汉科技有限公司版权所有 京ICP13053142号
		     <br/>建议使用Chrome浏览器以获得更佳的体验</div>
		</div>
	</body>
</html>
