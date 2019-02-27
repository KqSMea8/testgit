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
        <script type="text/javascript"> 
				function toFirst(){
					location.href="./create_first.do";
				}
				
				function donextsubmit(){
					location.href="./isms.do";
				}
				function changeType(i){
					$('#modelType').val(i);
					}
		</script>  
		<style type="text/css">  
			body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,textarea,p,blockquote,th,td{margin:0;padding:0;}
			body{ width: 100%; background: url(${contextPath}/resources/app/images/login/login_15.jpg) repeat-x top; font-family:微软雅黑;}
			ul,li{ list-style: none;}
			img{ border:0;}
			a{text-decoration:none; color: #000}
			
			
			.top{width:100%; height:84px; margin:0 auto; border-bottom: 1px solid #e7e7e7}
		.top_main{ height: 84px; width: 1000px; margin: 0 auto;}
		.top_left{width:333px ; height:84px ; float:left }
		.top_center{width:333px; height:84px; float:left}
		.top_right{width:333px; height:84px ; float:left}
			.top .center{ height: 74px; width: 1000px; margin: 0 auto; padding-left: 20px; font-size: 28px; line-height: 74px; margin-bottom: 34px;}
			.title{ width:234px;height:42px;font-size:25px;  margin-left:10px; margin-bottom:17px; float:left}
			.hello{ float:left; font-size:13px; color:#c7c7c7; margin-left:500px;height:42px; }
			.exit{ float:left;font-size:20px; margin-left:10px;width:40px;height:40px; }
			.newuser{ float:left; font-size:20px; margin-left:10px;width:100px;height:46px; }
			
			.main{width: 478px; margin: 0 auto;overflow: hidden; text-align: center;}
			ul.user_main{ height: 124px; background: url(login_16.jpg) no-repeat bottom center; width: 478px; margin-top: 78px; }
			li.user{ float: left;  margin-left: 175px;}
			li.user p{ text-align: center; line-height: 34px;}
			li.change{ margin-left: 19px; margin-top: 19px; float: left; }
			.big_font{ font-size: 26px; line-height: 28px; display: block; margin: 10px 0 20px -34px;}
			
			
			#footer{width:100% ; height:56px ; background-color: #373737; padding-top: 14px; text-align: center; color: #fff; font-size: 13px; position: fixed; bottom: 0px;}
		</style>
    </head>

    <body> 
		<div class="top" >
     		<div class="top_main">
	     	    <div class="top_left">
	            	<p style="margin:30px 5px ; font-size:25px ;font-family:微软雅黑; " align="left">APP自动创建工场</p>
	            </div>
     			<div class="top_center">
         			<p style="margin-top:30px ; margin-left:80px; font-size:23px" align="center"></p>
         		</div>
         
		        <div class="top_right">
		            <p style="margin-top:30px ;margin-left:10px; color:#c7c7c7" align="right">用户:${username} <a href="${jmpurl}/app/index.do" style=" font-size:20px">退出</a></p>
		        </div>
    		 </div>    
 		</div>


<div class="main">
  <ul class="user_main">
        <li class="user"><c:if test="${headurl==null}"><img  style="width:80px;height:80px;" src="${contextPath}/resources/app/images/login/user.jpg" alt="" /></c:if><c:if test="${headurl!=null}"><img style="height:80px;width:80px;" src="${headurl}" alt="" /></c:if><p>${username}</p></li>
    <li class="change"><a href="${jmpurl}/app/index.do"><img src="${contextPath}/resources/app/images/login/change.jpg" alt="" /></a></li>
  </ul>
  <span class="big_font">点击创建自己的专属应用</span>
  <div style=" width: 203px; margin-left:70px;">
    <a href="${jmpurl}/createapp/tocreatefirst.do"><img src="${contextPath}/resources/app/images/login/chuanjian.jpg" alt="" "/></a>
  </div>

</div>

<div id="footer">
  <div>北京国信大汉科技有限公司版权所有 京ICP13053142号
  <br/>建议使用Chrome浏览器以获得更佳的体验</div>
</div>
    </body>
</html>