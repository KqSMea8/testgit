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
     	<meta name="description" content="" />
		<h:head cookie="false" pagetype="dialog" calendar="false" validity="true"></h:head>   
        <script src="${contextPath}/resources/app/js/jquery-1.7.2.min.js" type="text/javascript"></script> 
	    <script src="${contextPath}/resources/app/layer/layer.js" type="text/javascript"></script>
         <script type="text/javascript">
				
				function toSecond(){
					location.href="./create_second.do";
				}
				function changeType(i){
					$('#modelType').val(i);
					}

				function back(){
					location.href='${jmpurl}/app/login.do';
					}
				function laststep(){
					location.href='${jmpurl}/createapp/tocreatesecond.do';
					}

				function nextstep() {
					var name = $('#appname').val();
					var spec = $('#appspec').val();
					var logoPath = $('#logoPath').val();
					var splashIcon = $('#splashIcon').val();
					var modelType = $('#modelType').val(); 
					var logoIcon=$('#logoIcon').val(); 
					var siteId=$('#siteId').val(); 
					var iid=$('#iid').val(); 
					var waterflow=0; 
					var scene=0;
					var ecommerce=0;
					var weather = 0;
					var map = 0 ;
					var video= 0;
					var baoliao = 0;
					var kuTu = 0;
					var streetView = 0;
					var card=0;
					var leftpic=0;
					var picmix=0;
					var titlemix=0;
					var titlemix1=0;
					var titlemix2=0;
					var intelbus=0;
					var numsense=0;
					var ebook=0; 
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
  
					if($("#card").attr("checked")=="checked"){
						card = $('#card').val();
					}
					if($("#leftpic").attr("checked")=="checked"){
						leftpic = $('#leftpic').val();
					}
					if($("#picmix").attr("checked")=="checked"){
						picmix = $('#picmix').val();
					}
					if($("#titlemix").attr("checked")=="checked"){
						titlemix = $('#titlemix').val();
					}
					if($("#titlemix1").attr("checked")=="checked"){
						titlemix1 = $('#titlemix1').val();
					}
					if($("#titlemix2").attr("checked")=="checked"){
						titlemix2 = $('#titlemix2').val();
					}
					if($("#intelbus").attr("checked")=="checked"){
						intelbus = $('#intelbus').val();
					}
					if($("#numsense").attr("checked")=="checked"){
						numsense = $('#numsense').val();
					}
					if($("#ebook").attr("checked")=="checked"){
						ebook = $('#ebook').val();
					} 
					if($("#waterflow").attr("checked")=="checked"){
						waterflow = $('#waterflow').val();
					}
					if($("#scene").attr("checked")=="checked"){
						scene = $('#scene').val();
					}
					if($("#ecommerce").attr("checked")=="checked"){
						ecommerce = $('#ecommerce').val();
					}
					var overall = $('input[name="overall"]:checked').val();  
					
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
						data :"name="+encodeURI(name)+"&spec="+encodeURI(spec)+"&logoPath="+logoPath
							+"&splashIcon="+splashIcon+"&modelType="+modelType+"&weather="+weather
							+"&map="+map+"&video="+video+"&baoliao="+baoliao+"&kuTu="
							+kuTu+"&streetView="+streetView+"&logoIcon="+logoIcon
							+"&siteId="+siteId+"&iid="+iid+"&card="+card+"&leftpic="+leftpic
							+"&picmix="+picmix+"&titlemix="+titlemix+"&titlemix1="+titlemix1+"&titlemix2="+titlemix2
							+"&intelbus="+intelbus+"&numsense="+numsense+"&ebook="+ebook
							+"&waterflow="+waterflow+"&scene="+scene+"&ecommerce="+ecommerce+"&overall="+overall,
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


				 $(function () {
			            // 全选
			            $("#styleall").bind("click", function () {  
			            	 if($("#styleall").attr("checked")=="checked"){
			            		$("#card").attr("checked", true);
			            		$("#leftpic").attr("checked", true); 
			            		$("#waterflow").attr("checked", true);
			            		$("#scene").attr("checked", true);
			            		$("#picmix").attr("checked", true);
			            		$("#titlemix").attr("checked", true);
			            		$("#titlemix1").attr("checked", true);
			            		$("#titlemix2").attr("checked", true);
			            		$("#map").attr("checked", true);
			            		$("#kuTu").attr("checked", true);
			            		$("#video").attr("checked", true);
				             }else{
				            	$("#card").attr("checked", false);
			            		$("#leftpic").attr("checked", false); 
			            		$("#waterflow").attr("checked", false);
			            		$("#scene").attr("checked", false);
			            		$("#picmix").attr("checked", false);
			            		$("#titlemix").attr("checked", false);
			            		$("#titlemix1").attr("checked", false);
			            		$("#titlemix2").attr("checked", false);
			            		$("#map").attr("checked", false);
			            		$("#kuTu").attr("checked", false);
			            		$("#video").attr("checked", false);
					         }
			            });

			         // 全选
			            $("#componentall").bind("click", function () {  
			            	 if($("#componentall").attr("checked")=="checked"){
			            		$("#intelbus").attr("checked", true);
			            		$("#baoliao").attr("checked", true);
			            		$("#ecommerce").attr("checked", true);
			            		$("#weather").attr("checked", true);
			            		$("#numsense").attr("checked", true);
			            		$("#ebook").attr("checked", true); 
				             }else{
				            	$("#intelbus").attr("checked", false);
			            		$("#baoliao").attr("checked", false);
			            		$("#ecommerce").attr("checked", false);
			            		$("#weather").attr("checked", false);
			            		$("#numsense").attr("checked", false);
			            		$("#ebook").attr("checked", false); 
					         }
			            });
			            
				  });
		</script> 
		<style type="text/css">  
			body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,textarea,p,blockquote,th,td{margin:0;padding:0;}
body{ width: 100%; font-family:微软雅黑;}
html {overflow-y:scroll;}
ul,li{ list-style: none;}
img{ border:0;}
a{text-decoration:none; color: #494949}
a:hover{ text-decoration: none; color:#f03f3a; }
.max{ width:100% ; height:auto;}
.top{width:100%; height:84px; margin:0 auto; border-bottom: 1px solid #e7e7e7 ;background: url(${contextPath}/resources/app/images/stepthird/login_15.jpg) repeat-x top;}
.top_main{ height: 84px; width: 1000px; margin: 0 auto;}
.top_left{width:333px ; height:84px ; float:left }
.top_center{width:333px; height:84px; float:left}
.top_right{width:333px; height:84px ; float:left}
.bz{width:100%; height:120px ; margin:20px auto}
.main{width:910px; height:662px ; margin:0px auto 40px auto;}
.main_top{width:800px ; height:40px; font-size: 18px; color: #373737; font-weight:bold; }
.main_center{width:800px ; height:458px; }
.left{ width:230px ; height:448px ; float:left; margin-top:10px}
.center{width:230px ; height:448px ; float:left; margin-top:10px ; margin-left:55px}
.right{width:230px ; height:448px ; margin-top:10px; float:right}
.left_1{ width:230px; height:360px ; background:#d9d7d7}
.left_2{width:230px ; height:auto}
.xz{width:1000px ; height:80px; margin-top:100px;margin-left:auto;margin-right:auto;clear:both}
.xz_left{width:410px; height: auto; float:left}
.xz_center{width:180px; height:auto; float:left}
.xz_right{width:410px ; height:auto; float:right}
.banq{width:100% ; height:56px ; background-color: #373737; padding-top: 14px; text-align: center; color: #fff; font-size: 13px;}
.big{margin-top:20px;padding-left:10px;width:195px; height:360px; float:left}
.bigother{margin-top:20px;padding-left:10px;width:195px; height:360px; float:left;margin-left:20px;}
.big_top{width: 195px;height: 328px;background-color:#fff;padding:4px;border:1px solid #d5dee9}
.big_bottom{width:195px; height: auto;}
.big_bottom p{text-align:center ; display:block; margin-top:8px;margin-bottom:5px; font-weight:bold; color: #373737}
.big2{width:230px; height:360px; float:left; margin-left:55px;}
.big3{width:230px; height:360px; float:right;}
.big4{ width:230px; height:360px; float:left;padding-top:15px;}
.big5{width:230px; height:360px; float:left; margin-left:55px;padding-top:15px;}
.big6{width:230px; height:360px; float:right; padding-top:15px;}
.top1{width:800px; height:360px;}

.label_radio{margin-left:4px;}

ul.next{ width: 470px; height: 20px; margin: 0 auto;padding-top:20px;}
ul.next li{ width: 145px; height: 36px; line-height: 36px; text-align: center; float: left; margin-right: 10px; background: url(${contextPath}/resources/app/images/stepthird/next.jpg) no-repeat;}
ul.next li a{color: #fff; font-size: 18px;}
#footer{width:100% ; height:56px ; background-color: #373737; padding-top: 14px; text-align: center; color: #fff; font-size: 13px; }
		</style> 
    </head>
    <body>
    <div class="max">
    <div class="top" >
      <div class="top_main">
        <div class="top_left">
          <p style="margin:30px 5px ; font-size:25px ;font-family:微软雅黑;" align="left">APP自动创建工场</p>
          </div>
        <div class="top_center">
          <p style="margin-top:30px ; margin-left:80px; font-size:23px" align="center"></p>
          </div>
          
        <div class="top_right">
          <p style="margin-top:30px ;margin-left:10px; color:#c7c7c7" align="right">用户:${username}  <a href="${jmpurl}/app/index.do" style=" font-size:20px">退出</a></p>
        </div>
      </div>    
  </div>
    	<div class="bz">
   	    <p align="center"><img src="${contextPath}/resources/app/images/stepthird/bz.png" width="662" height="128" /></p>
   	</div>
      <div class="main"> 
           <div style="padding:0 65px 20px 0;font-size:20px;color:#304562;">全局风格</div> 
            <div class="big" style="">
            	<div class="big_top" style="background: url('${contextPath}/resources/app/images/drawer.png'); "></div>
                <div class="big_bottom"><p><input type="radio" id="drawer" name="overall" value="1" data-value="${app.overall}"/><label class="label_radio" for="dr">左边侧滑</label></p></div>
            </div> 
          	<div class="bigother" style="margin-left:20px;">
            	<div class="big_top" style="background: url('${contextPath}/resources/app/images/tabbar.png'); "></div>
                <div class="big_bottom"><p><input type="radio" id="tabbar" name="overall" value="2" data-value="${app.overall}"/><label class="label_radio" for="tar">顶部导航</label></p></div>
            </div>  
            
            <div style="padding:30px 65px 20px 0;font-size:20px;color:#304562;clear:both">信息样式    <input type="checkbox"  id="styleall" style="margin-left:20px"/> <font size="2"> 全选</font> </div>
            
            <div class="big"  >
            	<div class="big_top" style="background: url('${contextPath}/resources/app/images/card.png'); "></div>
                <div class="big_bottom"><p><input type="checkbox" name="card" id="card" value="3" /><label class="label_radio" for="cd">微信卡片式</label></p></div>
            </div> 
             <div class="bigother">
            	<div class="big_top" style="background: url('${contextPath}/resources/app/images/leftpic.png'); "></div>
                <div class="big_bottom"><p><input type="checkbox" name="leftpic" id="leftpic" value="4"/><label class="label_radio" for="lpic">左侧一张图</label></p></div>
            </div> 
            
            <div class="bigother">
            	<div class="big_top" style="background: url('${contextPath}/resources/app/images/waterflow.png'); "></div>
                <div class="big_bottom"><p><input type="checkbox" name="waterflow" id="waterflow" value="18"/><label class="label_radio" for="waf">瀑布流</label></p></div>
            </div>
            <div class="bigother">
            	<div class="big_top" style="background: url('${contextPath}/resources/app/images/scene.png'); "></div>
                <div class="big_bottom"><p><input type="checkbox" name="scene" id="scene" value="19"/><label class="label_radio" for="se">场景式栏目</label></p></div>
            </div>
            <div class="big">
            	<div class="big_top" style="background: url('${contextPath}/resources/app/images/picmix.png'); "></div>
                <div class="big_bottom"><p><input type="checkbox" name="picmix" id="picmix" value="5"/><label class="label_radio" for="pmix">图片新闻</label></p></div>
            </div>
            <div class="bigother">
            	<div class="big_top" style="background: url('${contextPath}/resources/app/images/titlemix.png'); "></div>
                <div class="big_bottom"><p><input type="checkbox" name="titlemix" id="titlemix" value="6"/><label class="label_radio" for="tmix">标题+时间+来源</label></p></div>
            </div>
            <div class="bigother">
            	<div class="big_top" style="background: url('${contextPath}/resources/app/images/titlemix1.png'); "></div>
                <div class="big_bottom"><p><input type="checkbox" name="titlemix1" id="titlemix1" value="7"/><label class="label_radio" for="tmix2">标题+时间+摘要</label></p></div>
            </div>
             <div class="bigother">
            	<div class="big_top" style="background: url('${contextPath}/resources/app/images/titlemix2.png'); "></div>
                <div class="big_bottom"><p><input type="checkbox" name="titlemix2" id="titlemix2" value="8"/><label class="label_radio" for="tmix3">标题+时间+评论数</label></p></div>
            </div>
            <div class="big">
            	<div class="big_top" style="background: url('${contextPath}/resources/app/images/map.png'); "></div>
                <div class="big_bottom"><p><input type="checkbox" name="map" id="map" value="9"/><label class="label_radio" for="kp">地图</label></p></div>
            </div>
            <div class="bigother">
            	<div class="big_top" style="background: url('${contextPath}/resources/app/images/kutu.png'); "></div>
                <div class="big_bottom"><p><input type="checkbox" name="kuTu" id="kuTu" value="10"/><label class="label_radio" for="kutupic">酷图</label></p></div>
            </div>
             <div class="bigother">
            	<div class="big_top" style="background: url('${contextPath}/resources/app/images/shipin.png'); "></div>
                <div class="big_bottom"><p><input type="checkbox" name="video" id="video" value="11"/><label class="label_radio" for="vo">视频</label></p></div>
            </div>
            
            <div style="padding:10px 65px 20px 0;font-size:20px;color:#304562;margin-top:40px;clear:both">独立功能    <input type="checkbox"  id="componentall" style="margin-left:20px"/> <font size="2"> 全选</font> </div>
            
            <div class="big">
            	<div class="big_top" style="background: url('${contextPath}/resources/app/images/intelbus.png'); "></div>
                <div class="big_bottom"><p><input type="checkbox" name="intelbus" id="intelbus" value="12" /><label class="label_radio" for="inbus">智能公交</label></p></div>
            </div> 
            <div class="bigother">
            	<div class="big_top" style="background: url('${contextPath}/resources/app/images/baoliao.png'); "></div>
                <div class="big_bottom"><p><input type="checkbox" name="baoliao" id="baoliao" value="13"/><label class="label_radio" for="bo">互动报料</label></p></div>
            </div>
            <div class="bigother">
            	<div class="big_top" style="background: url('${contextPath}/resources/app/images/ecommerce.png'); "></div>
                <div class="big_bottom"><p><input type="checkbox" name="ecommerce" id="ecommerce" value="20"/><label class="label_radio" for="ec">打折商城</label></p></div>
            </div>
             <div class="bigother">
            	<div class="big_top" style="background: url('${contextPath}/resources/app/images/weather.png'); "></div>
                <div class="big_bottom"><p><input type="checkbox" name="weather" id="weather" value="14"/><label class="label_radio" for="tq">天气预报</label></p></div>
            </div>
            <div class="big">
            	<div class="big_top" style="background: url('${contextPath}/resources/app/images/numsense.png'); "></div>
                <div class="big_bottom"><p><input type="checkbox" name="numsense" id="numsense" value="15"/><label class="label_radio" for="ne">通讯录</label></p></div>
            </div>
            <div class="bigother">
            	<div class="big_top" style="background: url('${contextPath}/resources/app/images/ebook.png'); "></div>
                <div class="big_bottom"><p><input type="checkbox" name="ebook" id="ebook" value="16"/><label class="label_radio" for="ek">阅读</label></p></div>
            </div>
        </div>
  <form action="${url }" method="post" id="oprform" name="oprform">      
	  <input type=hidden name ="logoIcon" id="logoIcon" value="${app.logoIcon}"/>
	  <input type=hidden name ="name" id="appname" value="${app.name}"/>
	  <input type=hidden name ="spec" id="appspec" value="${app.spec}"/>
	  <input type="hidden" id="siteId" name="siteId" value="${app.siteId}">  
	  <input type=hidden name ="logoPath" id="logoPath" value="${app.logoPath}"/>
	  <input type=hidden name ="splashIcon" id="splashIcon" value="${app.splashIcon}"/>
	  <input type=hidden name ="modelType" id="modelType" value="${app.modelType}"/>
	  <input type="hidden" id="iid" name="iid" value="${app.iid}">
	  <div class="xz">
	    <ul class="next">
	      <li><a href="javascript:back()">取消创建</a></li>
		  <li><a href="javascript:laststep()">上一步</a></li>
		  <li><a href="javascript:nextstep()">完成</a></li>
	    </ul>
	  </div>
   </form>  
   <div id="footer" style="height:40px;">
  <div>北京国信大汉科技有限公司版权所有 京ICP13053142号
  <br/>建议使用Chrome浏览器以获得更佳的体验</div>
</div>
</div>
    </body>
</html>