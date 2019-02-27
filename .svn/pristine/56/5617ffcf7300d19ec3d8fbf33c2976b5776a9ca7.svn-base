<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="h" uri="/hanweb-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<h:head cookie="false" pagetype="dialog" calendar="false" validity="true" tip="true" select="true" upload="false" tree="true"></h:head> 
<title>APP自动创建工场</title>

</head>
<h:import type="css" path="/resources/app/css/manage.css"></h:import> 
<script src="${contextPath}/resources/app/js/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		function toRegistUser() {
			var loginname = $('#loginname').val();
			var pwd = $('#pwd').val();
			var name = $('#name').val();
			var rpwd = $('#rpwd').val();
			var randcode = $('#randcode').val();
			var siteId = $('#siteId').val();
			$.ajax({
				type : "GET",
				url :' repwd.do',
				data : "loginName=" + loginname + "&name=" + name + "&pwd=" + pwd + "&rpwd=" + rpwd +"&siteId="+siteId+ "&randcode=" + randcode,
				success : function(msg) {
					if (msg == '1'){
						location.href= '${jmpurl}/createapp/appdetail.do';
					}
					else if(msg == '2'){
						$('#s1').html('原密码错误');
					}else{
						$('#s1').html('更新密码失败');
						}
				}
			});
		}
							
</script>  
<style type="text/css"> 
		li.input_name{ background: url(${contextPath}/resources/app/images/manageregist/login_1.png) no-repeat left 15px; padding-left: 42px;}
		li.input_pw{ background: url(${contextPath}/resources/app/images/manageregist/login_2.png) no-repeat left 10px; padding-left: 42px;}
		li.input_pw2{ background: url(${contextPath}/resources/app/images/manageregist/login_2.png) no-repeat left 10px; padding-left: 42px;}
		li.input_nc{ background: url(${contextPath}/resources/app/images/manageregist/login_4.png) no-repeat left 9px; padding-left: 42px; }
	</style>
<body>
	<div class="top">
        <div class="top_line"></div>
        <div class="top_1">
            <div class="top_left">
                <div style="width:300px; font-size:28px; line-height:70px;"><a href="${jmpurl}/appuser/login.do">个人中心</a></div>
            </div>
            <div class="top_right"><p align="center"; style="font-size:20px ; color:#c7c7c7">你好,${username }用户<a href="${jmpurl}/app/index.do" style="font-size:20px"> 退出</a></p></div>
        </div>
        <div class="top_line2"></div>
    
    </div>
    <div class="login">
        <ul class="login_left">
            <div class="chshh">
                <span>网站管理员密码修改</span>
                <p>（该账号用于登陆APP的后台管理）</p>
            </div>
            <li class="input_name"><input style="disabled:true" id="loginname" name="loginname" type="text" onblur="if(this.value==&quot;&quot;){this.value=&quot;输入登录名&quot;}" onclick="if(this.value==&quot;输入登录名&quot;){this.value=&quot;&quot;;this.focus();}" value="${username }"  class="login_form"/></li>
            <li class="input_pw"><input name="pwd" id="pwd" type="text" onblur="if(this.value==&quot;&quot;){this.value=&quot;请输入原密码&quot;}" onclick="if(this.value==&quot;请输入原密码&quot;){this.value=&quot;&quot;;this.focus();}" value="请输入原密码"  class="login_form"/></li>
            <li class="input_pw2"><input id="rpwd" type="text" onblur="if(this.value==&quot;&quot;){this.value=&quot;输入密码&quot;}" onclick="if(this.value==&quot;输入密码&quot;){this.value=&quot;&quot;;this.focus();}" value="输入密码"  class="login_form"/></li>
			<div style="color:red;text-align:center" id="s1" name="s1" value=""></div>
            <div><a href="javascript:toRegistUser()"><img src="${contextPath}/resources/app/images/manageregist/tj.jpg" alt="提交" style="margin-top:32px;"/></a></div>
        </ul>
        <ul class="login_right">
            <div class="chshh"><input type="hidden" id="siteId" name="siteId" value="${siteId }"/></div>
            <li>登录名只能为数字字母下划线或邮箱，长度在5到20位</li>
            <li></li>
            <li></li>
        </ul>
    </div>

 
 

    <div class="banq">
        <div>北京国信大汉科技有限公司版权所有 京ICP13053142号
         <br/>建议使用Chrome浏览器以获得更佳的体验</div>
    </div>
</body>
</html>
