<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="h" uri="/hanweb-tags"%> 
<!DOCTYPE html>
<html>
    <head>
        <title>APP自动创建工场</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=9">
        <meta name="keywords" content="一键打包、APP自助创建、政务微门户、智慧政府的名片" /> 
		<h:head cookie="false" pagetype="dialog" calendar="false" validity="true" tip="true" select="true" upload="false" tree="true"></h:head>    
		<style type="text/css">  
			html {overflow-y:scroll;}
			body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,textarea,p,blockquote,th,td{margin:0;padding:0;}
			body{ width: 100%;  font-family:微软雅黑;}
			ul,li{ list-style: none;}
			img{ border:0;}
			a{text-decoration:none; color: #000}
			
			.top{ height: 76px; border-bottom: 1px solid #cfcfcf; width: 100%;background: url(${contextPath}/resources/app/images/login/login_15.jpg) repeat-x top;}
			.top .center{ height: 74px; width: 1000px; margin: 0 auto; padding-left: 20px; font-size: 28px; line-height: 74px; margin-bottom: 34px;}
			.title{ width:234px;height:42px;font-size:25px; font-family:微软雅黑; margin-left:10px; margin-bottom:17px; float:left}
			.hello{ float:left; font-size:20px; color:#c7c7c7; margin-left:400px;height:42px; }
			.exit{ float:left;font-size:20px; margin-left:10px;width:40px;height:45px;right:0; }
			.newuser{ float:left; font-size:20px; margin-left:10px;width:100px;height:46px; }
			
			.main{width: 478px; margin: 0 auto; text-align: center;}
			ul.user_main{ height: 124px; background: url(login_16.jpg) no-repeat bottom center; width: 478px; margin-top: 78px; }
			li.user{ float: left;  margin-left: 175px;}
			li.user p{ text-align: center; line-height: 34px;}
			li.change{ margin-left: 19px; margin-top: 19px; float: left; }
			.big_font{ font-size: 26px; line-height: 28px; display: block; margin: 10px 0 20px -34px;}
			
			
			#footer{width:100% ; height:56px ; background-color: #373737; padding-top: 14px; text-align: center; color: #fff; font-size: 13px; bottom: 0px;position: fixed;}
		</style>
    </head>

    <body> 
		<div class="centerall" width="1052px" style=" margin:0 auto; text-align:center" >
		  <div class="top">
		  	<div class="center">
		  		<div class="title"><a >APP自动创建工场</a></div>
		  		<div class="hello" style='font-size:13px; color:#c7c7c7;float:left'>用户:${username}</div>
		      <c:if test="${isCreate}"><div class="newuser"><a href="${jmpurl}/createapp/tocreatefirst.do">创建新应用</a></div></c:if>
		      <div class="exit"><a href="${jmpurl}/app/index.do">退出</a></div>
		  	</div>
		  </div>
		</div>

	<div class="main">
	  <ul class="user_main">
	    <li class="user"><c:if test="${headurl==null}"><img style="width:80px;height:80px;" src="${contextPath}/resources/app/images/login/user.jpg" alt="" /></c:if><c:if test="${headurl!=null}"><img style="width:80px;height:80px;" src="${headurl}" alt="" /></c:if><p>${username}</p></li>
	    <li class="change"><a href="${jmpurl}/app/index.do"><img src="${contextPath}/resources/app/images/login/change.jpg" alt="" /></a></li>
	  </ul>
	  <span class="big_font">点击图标管理应用</span>
	</div>
 <div style="width:${divWidth}px;margin-left:auto;margin-right:auto;height:${divHeight}px;">
  <c:forEach items="${applist }" var="app" varStatus="i"> 
  	 <div style="float:left; width:150px;margin-left:auto;margin-right:auto;margin-bottom:20px;"><a href="${jmpurl}/createapp/appdetail.do?iid=${app.iid}"><img style="width:110px;" src="${jmpurl}/${app.logoPath}" alt=""/></a><br/><p style="width:110px;" align="center">${app.name}</p> </div>
  </c:forEach> 
  </div>
   <div id="footer" style="height:40px;float:left">
  <div>北京国信大汉科技有限公司版权所有 京ICP13053142号
  <br/>建议使用Chrome浏览器以获得更佳的体验</div>
  </div>
    </body>
</html>