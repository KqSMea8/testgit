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
<script src="${contextPath}/resources/app/js/jquery.placeholder.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/app/layer/layer.js" type="text/javascript"></script>
	<script type="text/javascript">
		 $(function () {  
			$('.login_form').placeholder();
	     });  
		function toRegistUser() {
			var reg = "^[A-Za-z0-9\u4e00-\u9fa5]+$";
			var re = new RegExp(reg); 
			var loginname = $('#loginname').val();
			var pwd = $('#pwd').val();
			var name = $('#name').val();
			var pwd1 = $('#spwd').val();
			var siteid = ${siteId};
			if(loginname.replace(/[ ]/g,"")=='' || loginname=='输入登录名' ){
				$('#s1').html('请输入登录名');
				return false;
			}
			if(!re.test(loginname)){
				$('#s1').html('登录名只能由字母、数字、中文组成');
				return false;
				}
			if(pwd.replace(/[ ]/g,"")=='' || pwd=='输入密码' ){
				$('#s1').html('请输入密码');
				return false;
			}
			if(pwd1.replace(/[ ]/g,"")=='' || pwd1=='确认密码' ){
				$('#s1').html('请输入确认密码');
				return false;
			}
			if(name.replace(/[ ]/g,"")=='' || pwd1=='输入昵称' ){
				$('#s1').html('请输入昵称');
				return false;
			}
			if(pwd!=pwd1){
				$('#s1').html('两次密码输入不同');
				$("#spwd").val("");
				return false;
				}
			var res = "^[a-zA-Z0-9]{6,16}$";
			var res1 = "^[A-Z]+$";
			var res2 = "^[a-z]+$";
			var res3 = "^[0-9]*$";
			var re = new RegExp(res); 
			var re1 = new RegExp(res1);
			var re2 = new RegExp(res2);
			var re3 = new RegExp(res3);
			if(re.test(pwd)){
				if(re1.test(pwd)||re2.test(pwd)||re3.test(pwd)){
					layer.alert('密码不能是纯数字或纯字母！', {icon: 2});
					return false;
				}
			}else{
				layer.alert('密码由数字和字母组成且长度在6~16之间！', {icon: 2});
				return false;
			}
			$.ajax({
				type : "GET",
				url :' registmanage.do',
				data : "loginName=" + loginname + "&name=" + name +"&siteid=" +siteid + "&pwd=" + pwd ,
				success : function(msg) {
					if (msg == '1'){
						location.href= '${jmpurl}/ssologin.do?username=' + loginname + '&password=' + pwd;
					}
					else if(msg == '2'){
						$('#s1').html('用户名已存在');
					}else{
						$('#s1').html('初始化网站管理员失败');
						}
				}
			});
		}
							
</script>  
<style type="text/css"> 
		li.input_name{ background: url(${contextPath}/resources/app/images/manageregist/login_1.png) no-repeat left 18px; padding-left: 42px;}
		li.input_pw{ background: url(${contextPath}/resources/app/images/manageregist/login_2.png) no-repeat left 16px; padding-left: 42px;}
		li.input_pw2{ background: url(${contextPath}/resources/app/images/manageregist/login_2.png) no-repeat left 16px; padding-left: 42px;}
		li.input_nc{ background: url(${contextPath}/resources/app/images/manageregist/login_4.png) no-repeat left 12px; padding-left: 42px; }
	</style>
<body>
	<div class="top">
        <div class="top_line"></div>
        <div class="top_1">
            <div class="top_left">
                <div style="width:300px; font-size:28px; line-height:70px;">中国政务APP市场</div>
            </div>
            <div class="top_right"><p align="center"; style="font-size:20px ; color:#c7c7c7">用户：${username}<a href="${jmpurl}/app/index.do" style="font-size:20px"> 退出</a></p></div>
        </div>
        <div class="top_line2"></div>
    
    </div>
    <div class="login">
        <ul class="login_left">
            <div class="chshh">
                <span>初始化网站管理员</span>
                <p>（该账号用于登陆APP的后台管理）</p>
            </div>
            <li class="input_name"><input style="outline:none" maxlength="10" name="loginname" id="loginname" type="text" placeholder="输入登录名" autocomplete="off" class="login_form"/></li>
            <li class="input_pw"><input style="outline:none" maxlength="16" name="pwd" id="pwd" type="password" placeholder="输入密码"  autocomplete="off" class="login_form"/></li>
            <li class="input_pw"><input style="outline:none" maxlength="16" id="spwd" type="password"  placeholder="确认密码"  autocomplete="off" class="login_form"/></li>
            <li class="input_nc"><input style="outline:none" maxlength="10" name="name" id="name" type="text" placeholder="输入昵称"  autocomplete="off" class="login_form"/></li>
			<div style="color:red;text-align:center" id="s1" name="s1" value=""></div>
            <div><a href="javascript:toRegistUser()"><img src="${contextPath}/resources/app/images/manageregist/tj.jpg" alt="提交" style="margin-top:32px;"/></a></div>
        </ul>
        <ul class="login_right">
            <div class="chshh"></div>
            <li>* 登录名只能由字母、数字、中文组成，长度在1到10位</li>
            <li>* 支持数字中文字母，只允许数字或字母开头，最大长度16位</li>
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
