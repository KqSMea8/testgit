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
<!-- <c:if test="${flag==1}"><meta http-equiv="refresh" content="2"></c:if> -->
<h:head cookie="false" pagetype="dialog" calendar="false" validity="true" tip="true" select="true" upload="false" tree="true"></h:head> 
<h:import type="css" path="/resources/app/css/common.css"></h:import>    
<title>APP自动创建工场</title> 
    <style type="text/css">  
		.main{width:1014px;margin:20px auto;}
		.main_top{width:100% ; height:221px ; border:1px solid #e5e5e5 }
		.main_1{ width:100% ; height:30px ; background:#e5e5e5}
		.left1{width:670px; float:left; font-size: 18px; padding-left: 20px; height: 30px; line-height: 30px;}
		ul.right1{ float:left; height: 30px; }
		ul.right1 li{line-height: 30px;}
		li.right1_1{width:70px; float:left; background: url(${contextPath}/resources/app/images/userinfo/xg.png) no-repeat left 3px; padding-left: 30px; font-size: 15px;}
		li.right1_2{width:70px; float:left; background: url(${contextPath}/resources/app/images/userinfo/mmxg.png) no-repeat left 3px; padding-left: 30px; font-size: 15px;}
		li.right1_3{width:70px; float:left; background: url(${contextPath}/resources/app/images/userinfo/nr.png) no-repeat left 3px; padding-left: 30px; font-size: 15px;}
		.main_2{width:1015px; height:auto}
		.main_2left{width:830px ; height:190px; margin:0 auto; float:left}
		.main_2right{width:185px ; height:190px; float:left; margin-top: 20px;}
		.left-2{width:134px; height:185px; float:left;margin-left:20px}
		.right-2{width:auto; height:185px; float:left}
		
		.App_zt{ height:180px; border: 1px solid #e5e5e5; width: 1014px; margin: 0 auto 18px auto;}
		.App_zt .bt_zt{ height: 32px; background-color:#e5e5e5;}
		.bt_zt li{ height: 32px; line-height: 32px; float: left; width: 110px; text-align: center; margin-top: 0px; font-size: 18px;}
		.Android,.iPhone{ height: 148px; width: 337px; float: left;border-right: 1px solid #e5e5e5; text-align: center;}
		.iPad{ height: 148px; width: 337px; float: left; text-align: center;}
		.Android img,.iPhone img,.iPad img{ margin-top:24px; margin-bottom: 14px;}
		.Android p,.iPhone p,.iPad p{ width: 188px; line-height: 22px; margin: 0 auto; text-align: left; font-size: 14px;} 
	</style>
	<script src="${contextPath}/resources/app/js/jquery-1.7.2.min.js" type="text/javascript"></script>    
	<script src="${contextPath}/resources/app/layer/layer.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function(){
			var andriodstatus = $('#andriodstatus').val();
			var iphonestatus = $('#iphonestatus').val();
			var ipadstatus=$('#ipadstatus').val();
			var message = '${message}';
			if(andriodstatus==1 || iphonestatus==1 || ipadstatus==1){
				layer.alert( message, {icon: 6});
				}
			});
	</script>
</head>

<body>
    <div class="top">
    	<div class="top_line"></div>
        <div class="top_1">
        	<div class="top_left">
            	<div style="width:300px; font-size:25px; font-family:微软雅黑;line-height:70px;">APP自动创建工场</div>
            </div>
            <div class="top_right"><p align="center"; style="font-size:13px; color:#c7c7c7;float:right">用户:${app.creater}<a href="${jmpurl}/app/index.do" style="font-size:20px"> 退出</a></p></div>
        </div>
        <div class="top_line2"></div>
    
    </div>
    <div class="main">
    	<div class="main_top">
        	<div class="main_1">
            	<div class="left1" >APP</div>
                <ul class="right1" style="float: right;padding-right:20px;">
                	<li class="right1_2"><a href="${jmpurl}/app/login.do">个人中心</a></li>
                	<li class="right1_1"><a href="${contextPath}/createapp/tocreatefirst.do?appId=${app.iid}" >应用修改</a></li>
                    <li class="right1_3"><a target="_blank" href="${contextPath}/createapp/appmanage.do?siteId=${app.siteId}">内容管理</a></li>
              </ul>
            </div>
          	<div class="main_2">
            	<div class="main_2left">
               	  <div class="left-2"><p align="center" style="margin-top:40px"><img src="${jmpurl}${app.logoPath}"/></p></div>
                   
                    <div class="right-2"><p align="left" style="margin-top:70px; font-size:30px" >${app.name}</p><p>${app.spec}</p></div>
              </div>
                <div class="main_2right"><p align="center"><c:if test="${qrcode==1}"><img src="${contextPath}/resources/app/down/qrcode.png" width="146" height="156" /></c:if><c:if test="${qrcode==2}"><img src="${jmpurl}${app.qrcodePath}" width="146" height="156" /></c:if><c:if test="${qrcode==3}"><img src="${contextPath}/resources/app/down/qrcode1.png" width="146" height="156" /></c:if></p>
                </div>
            </div>
        </div> 
    </div> 
    <div class="App_zt">
        <ul class="bt_zt">
            <li>APP状态</li>
            <li style="color:#f03f3a">${dabaostate}</li>
        </ul>
        <input type="hidden" id="andriodstatus" name="andriodstatus" value="${andriodStatus}"/>
        <input type="hidden" id="iphonestatus" name="iphonestatus" value="${iphoneStatus}"/>
        <input type="hidden" id="ipadstatus" name="ipadstatus" value="${ipadStatus}"/>
        <div class="Android" >
            <c:if test="${andriodStatus != 2}"><img src="${contextPath}/resources/app/images/userinfo/login_20.jpg" alt="" /></c:if>
            <c:if test="${andriodStatus == 2}"> <a href="${andriodUrl }"><img src="${contextPath}/resources/app/images/userinfo/login_16.jpg" alt="" /></a></c:if>
            <p>版本：${andriodVersion}<br />
                适用：Android&nbsp;&nbsp;2.3 以上
            </p>
        </div>
        <div class="iPhone">
            <c:if test="${iphoneStatus != 2}"><img  src="${contextPath}/resources/app/images/userinfo/login_19.jpg" alt="" /></c:if>
            <c:if test="${iphoneStatus == 2}"><a href="${iphoneIpa}"><img src="${contextPath}/resources/app/images/userinfo/login_17.jpg" alt="" /></a></c:if>
            <p>版本：${iphoneVersion}<br />
                适用：IOS&nbsp;&nbsp;5.0 以上
            </p>
        </div>
       <div class="iPad">
            <c:if test="${ipadStatus != 2}"><img style=" -webkit-filter: grayscale(100%); -moz-filter: grayscale(100%); -ms-filter: grayscale(100%); -o-filter: grayscale(100%); filter: grayscale(100%);filter: gray;" src="${contextPath}/resources/app/images/userinfo/login_18.jpg" alt="" /></c:if>
            <c:if test="${ipadStatus == 2}"><a href="${ipadIpa}"><img src="${contextPath}/resources/app/images/userinfo/login_18.jpg" alt="" /></a></c:if>
            <p>版本：${ipadVersion} <br />
                适用：iPad&nbsp;&nbsp;2.3 以上
            </p>
        </div>  
    </div>

    <div id="footer">
        <div>北京国信大汉科技有限公司版权所有 京ICP13053142号
        <br/>建议使用Chrome浏览器以获得更佳的体验
    </div>
</body>
</html>
