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
        <h:import type="css" path="/resources/app/css/button.css"></h:import>    
        <h:import type="css" path="/resources/app/css/common.css"></h:import> 
        <script src="${contextPath}/resources/app/js/jquery-1.7.2.min.js" type="text/javascript"></script>    
        <script src="${contextPath}/resources/app/layer/layer.js" type="text/javascript"></script>
        <script type="text/javascript"> 
        		$(function(){
            		var modelType = $('#modelType').val();
            		if(modelType == ''){
            			$('#modelType').val(1);
            			$('#radio1').attr("checked",true);
                		}
            		});			
				function changeType(i){
					$('#modelType').val(i);
					}
				function back(){
					location.href='${jmpurl}/app/login.do';
					}
				function laststep(){
					location.href='${jmpurl}/createapp/tocreatefirst.do';
					}
		        function nextstep(){
					$('#oprform').submit();
		            }
		        function finish1() { 
					var iid = $('#uid').val();
					var name = $('#appname').val();
					var spec = $('#appspec').val();
					var logoPath = $('#logoPath').val();
					var splashIcon = $('#splashIcon').val();
					var modelType = $('#modelType').val(); 
					var logoIcon = $('#logoIcon').val(); 
					var siteId=$('#siteId').val(); 
					var weather = 0;
					var map = 0 ;
					var video= 0;
					var baoliao = 0;
					var kuTu = 0;
					var streetView = 0;
					if($("#weather").attr("checked")=="checked"){ 
						weather= $('#weather').val();
						}
					if($("#map").attr("checked")=="checked"){
						map = $('#map').val();
						}
					if($("#video").attr("checked")=="checked"){
						video = $('#video').val();
						}
					if($("#baoliao").attr("checked")=="checked"){
						baoliao = $('#baoliao').val();
						}
					if($("#kuTu").attr("checked")=="checked"){
						kuTu = $('#kuTu').val();
						}
					if($("#streetView").attr("checked")=="checked"){
						streetView = $('#streetView').val();
						}
					var loaderID = layer.msg('正在打包中，请稍后...', {
					    icon: 1,
					    time: 1000000 //2秒关闭（如果不配置，默认是3秒）
					}, function(){
					    //do something
					});   
					$.ajax({
						type : "GET",
						url :'createsite.do',
						dataType : 'json', 
						data : "iid="+iid+"&name="+encodeURI(name)+"&spec="+encodeURI(spec)+"&logoPath="+logoPath+"&splashIcon="+splashIcon+"&modelType="+modelType+"&weather="+weather+"&map="+map+"&video="+video+"&baoLiao="+baoliao+"&kuTu="+kuTu+"&streetView="+streetView+"&logoIcon="+logoIcon+"&siteId="+siteId,
						success : function(msg) {
							layer.close(loaderID);
							if (msg == "1"){ 
								location.href= 'appdetail.do';
							}
							else if(msg == "0"){ 
								$('#s1').html('用户名密码错误');
							}else{
								$('#s1').html('验证码错误');
								}
						}
					});
				}
		</script>  
		<style type="text/css">  
			html {overflow-y:scroll;}
			.jiemian_div{float:left;width: 228px;height: 328px;margin: 10px 15px 0 10px;cursor: pointer;position: relative;}
			.jiemian_div p{text-align: center;width: 228px;position: absolute;top: 340px;} 
			ul.next{ width: 470px; height: 36px; margin: 0 auto;}
			ul.next li{ width: 145px; height: 36px; line-height: 36px; text-align: center; float: left; margin-right: 10px; background: url(${contextPath}/resources/app/images/next.jpg) no-repeat;}
			ul.next li a{color: #fff; font-size: 18px;}
			.top{width:100%; height:84px; margin:0 auto; border-bottom: 1px solid #e7e7e7}
			.top_main{ height: 84px; width: 1000px; margin: 0 auto;}
			.top_left{width:333px ; height:84px ; float:left }
			.top_center{width:333px; height:84px; float:left}
			.top_right{width:333px; height:84px ; float:left}
			body{ width: 100%; background: url(${contextPath}/resources/app/images/stepone/login_15.jpg) repeat-x top; font-family:微软雅黑;}
			.img_tree{height: 137px; width:631px;margin:auto}
			.max{ width:100% ;  background:url(bg.png);}
			#footer{width:100% ; height:56px ; background-color: #373737; padding-top: 14px; text-align: center; color: #fff; font-size: 13px; }
		</style>
    </head>

    <body> 
      <div class="max">
		    <div class="top" >
		      <div class="top_main">
		        <div class="top_left">
		          <p style="margin:30px 5px ; font-size:25px ; font-family:微软雅黑;" align="left">APP自动创建工场</p>
		          </div>
		        <div class="top_center">
		          <p style="margin-top:30px ; margin-left:80px; font-size:23px" align="center"></p>
		          </div>
		          
		        <div class="top_right">
		          <p style="margin-top:10px ;margin-left:10px; color:#c7c7c7" align="right">用户:${username}   <a href="${jmpurl}/app/index.do" style=" font-size:20px">退出</a></p>
		        </div>
		      </div>    
		  </div>
	    <div style="height:400px;width:760px;margin-left:auto;margin-right:auto;margin-top:10px">  
	    <div class="img_tree"><img src="${contextPath}/resources/app/images/steptwo/bz.png" alt="" /></div>
	    
	        <div class="jiemian_div" style="background: url('${contextPath}/resources/app/images/i_mode1.png'); ">
	            <p><input id="radio1" type="radio" name="modelType" value="1" data-value="${app.modelType}" onclick="changeType(1)"/>中国红</p>
	        </div>
	
			 <div class="jiemian_div" style="background: url('${contextPath}/resources/app/images/i_mode2.png'); ">
	            <p><input id="radio2" type="radio" name="modelType" value="2" data-value="${app.modelType}" onclick="changeType(2)"/>宝石蓝</p>
	        </div>
	        
	         <div class="jiemian_div" style="background: url('${contextPath}/resources/app/images/i_mode3.png'); ">
	            <p><input id="radio3" type="radio" name="modelType" value="3" data-value="${app.modelType}" onclick="changeType(3)"/>帝王黄</p>
	        </div> 
	    </div>  
	    <div style="clear: both"></div>
        <form action="${url }" method="post" id="oprform" name="oprform" >
        	  <input type=hidden name ="iid" id="uid" value="${app.iid}"/>
	          <input type=hidden name ="name" id="appname" value="${app.name}"/>
	          <input type=hidden name ="spec" id="appspec" value="${app.spec}"/>
	          <input type=hidden name ="logoPath" id="logoPath" value="${app.logoPath}"/>
	          <input type=hidden name ="splashIcon" id="splashIcon" value="${app.splashIcon}"/> 
	    	  <input type="hidden" name="modelType" id="modelType" value="${app.modelType}"/>
	    	  <input type=hidden name ="logoIcon" id="logoIcon" value="${app.logoIcon}"/>
	    	  <input type="hidden" id="siteId" name="siteId" value="${app.siteId}">  
		  	 <div style="height:120px;width:500px;margin-left:auto;margin-right:auto;margin-top:50px;clear: both">
					       <ul class="next">
						      <li><a href="javascript:back()">取消创建</a></li>
						      <li><a href="javascript:laststep()">上一步</a></li>
						     <c:if test="${ismodify==1}"><li><a href="javascript:finish1()">完成</a></li></c:if>
						     <c:if test="${ismodify==0}"><li><a href="javascript:nextstep()">下一步</a></li></c:if>
						    </ul>
				</div>
	    </form> 
    	</div> 
    	 <div id="footer" style="height:40px;">
	        <div>北京国信大汉科技有限公司版权所有 京ICP13053142号
	        <br/>建议使用Chrome浏览器以获得更佳的体验
	   		</div>
	    </div>
    </body>
</html>