<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="h" uri="/hanweb-tags"%> 
<!DOCTYPE html>
<html >
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<h:head cookie="false" pagetype="dialog" calendar="false" validity="true" tip="true" select="true" upload="false" tree="true"></h:head> 
<title>APP自动创建工场</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" /> 
<h:import type="css" path="/resources/app/css/yundabao.css"></h:import> 
<h:import type="css" path="/resources/app/css/farbtastic.css"></h:import>  
<h:import type="css" path="/resources/app/css/button.css"></h:import> 
    <style type="text/css">
        .peizhi_qidongtu{margin-top:50px}.peizhi_qidongtu span{float:left;color:#838e9e;padding-right:19px}.peizhi_gde{float:left}.peizhi_qidongtu p{color:#838e9e;line-height:44px;font-size:12px;text-align:center}.peizhi_qidongtu_scl{width:158px;float:left;margin:76px 0 0 26px}.fengge_qinshou{margin-top:50px}.fengge_qinshou span{float:left;color:#838e9e;padding-left:7px;padding-right:32px}.tc_mokuai{background:#fff;padding:10px 15px 15px 15px;border:1px solid #d1cfd0;width:720px}.main_tob li{float:left;margin-left:16px}#seleccolor{background:url(/images/pz_2.png) no-repeat;width:24px;height:24px;margin-top:40px;margin-left:107px;cursor:pointer}
        .peizhi_logo{margin-top:30px}.peizhi_logo span{float:left;color:#838e9e;padding-right:19px}.logo_gde{float:left}.peizhi_logo p{color:#838e9e;line-height:44px;font-size:12px;text-align:center}.peizhi_logo_scl{width:158px;float:left;margin:76px 0 0 26px}.fengge_qinshou{margin-top:50px}.fengge_logo span{float:left;color:#838e9e;padding-left:7px;padding-right:32px}.tc_mokuai{background:#fff;padding:10px 15px 15px 15px;border:1px solid #d1cfd0;width:720px}.main_tob li{float:left;margin-left:16px}#seleccolor{background:url(/images/pz_2.png) no-repeat;width:24px;height:24px;margin-top:40px;margin-left:107px;cursor:pointer}
		body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,textarea,p,blockquote,th,td{margin:0;padding:0;}
		body{ width: 100%; background: url(${contextPath}/resources/app/images/stepone/login_15.jpg) repeat-x top; font-family:微软雅黑;}
		.jiben_youbian{ float:right; margin:40px 80px 40px 0; width:325px; height:587px; background:url(${contextPath}/resources/app/images/stepone/shouji.png) no-repeat; position:relative;}
		ul,li{ list-style: none;}
		input, textarea {resize: none; border: 1px solid #ccc;}
		img{ border:0;}
		a{text-decoration:none; color: #494949}
		a:hover{ text-decoration: none; color:#f03f3a; }
		.max{ width:100% ;background:url(bg.png) ; margin:0 auto}
		.top{width:100%; height:84px; margin:0 auto; border-bottom: 1px solid #e7e7e7}
		.top_main{ height: 84px; width: 1000px; margin: 0 auto;}
		.top_left{width:333px ; height:84px ; float:left }
		.top_center{width:333px; height:84px; float:left}
		.top_right{width:333px; height:84px ; float:left}
		.img_tree{height: 137px; width:631px;margin:auto}
		.banq{width:100% ; height:40px ; background-color: #373737; padding-top: 14px; text-align: center; color: #fff;}
		ul.next{ width: 470px; height: 36px; margin: 0 auto;}
		ul.next li{ width: 145px; height: 36px; line-height: 36px; text-align: center; float: left; margin-right: 10px; background: url(${contextPath}/resources/app/images/next.jpg) no-repeat;}
		ul.next li a{color: #fff; font-size: 18px;}
		.grayBtn {
    background-color: #FFF;
    behavior: url("/pie/PIE.htc");
    background-image: -moz-linear-gradient(center top, #FFF, #DADADA);
    background-image: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#FFF), to(#DADADA));
    -pie-background: linear-gradient(top, #FFF, #DADADA);
    border: 1px solid #ccc;
    border-radius: 3px 3px 3px 3px;
    cursor: pointer;
    text-align: center;
}
.grayBtn:hover {
    background-color: #DADADA;
    behavior: url("/pie/PIE.htc");
    -pie-background: linear-gradient(top, #DADADA, #FFF);
    background-image: -moz-linear-gradient(center top, #DADADA, #FFF);
    background-image: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#DADADA), to(#FFF));
}
    </style>
    
</head>
<body>
    <div class="gongneng_center">
    	<div class="top" >
     		<div class="top_main">
	     	    <div class="top_left">
	            	<p style="margin:30px 5px ; font-size:23px ;font-family:微软雅黑; " align="left">APP自动创建工场</p>
	            </div>
     			<div class="top_center">
         			<p style="margin-top:30px ; margin-left:80px; font-size:23px" align="center"></p>
         		</div>
         
		        <div class="top_right">
		            <p style="margin-top:30px ;margin-left:10px; color:#c7c7c7" align="right">用户:${username} <a href="${jmpurl}/app/index.do" style=" font-size:20px">退出</a></p>
		        </div>
    		 </div>    
 		</div>
  		<div class="kj_zj"> 
		    <div class="nav_you">
		        <div class="toul">
		            <div class="img_tree"><img src="${contextPath}/resources/app/images/stepone/tree1.png" alt="" /></div>
		            <div class="jiben_zuobian">
		                <div class="yingyong_name">
		                    <span>应用名称</span>
		                    <input  id="apptitle" style="padding-left:10px;" class="biaoti" value="${app.name}" maxlength="10" onChange="changeTitleValue()"/>
		                    <span style="color: #838e9e; font-size: 12px; padding-left: 16px;"><font style="color: #f00">
		                        *</font>应用名称1至10个字</span>
		                </div>
		                <div class="yingyong_name" style="margin-top: 38px;">
		                    <span>应用说明</span>
		                    <input id="appnote" style="padding-left:10px;" class="biaoti" maxlength="50" value="${app.spec}" onChange="changeSpecValue()"/>
		                    <span style="color: #838e9e; font-size: 12px; padding-left: 20px;">应用说明1至50个字</span>
		                </div>
		                <div class="peizhi_tubiao">
		                    <span style="float: left; color: #838e9e; font-size: 12px; padding-right: 14px;">应用图标</span>
		                    <div style="background: #dc2209; width: 90px; height: 93px; border-radius: 8px; float: left;
		                        margin-right: 20px;" id="iconColorShow">
		                    </div>
		                    <div class="peizhi_tubiao_xq">
		                    <input type="text" id="iconWord" style="padding-left:10px;" class="btl_ttbb" value="${pic}" maxlength="1" placeholder=" 输入图标名,单个汉字或字母" /> 
		                        <button class="grayBtn" id="grayBtn1">上传图标</button>
				                <div  style="width:70px; margin: -25px 160px 0 100px; opacity: 0; filter: alpha(opacity = 0)">
				                    <input id="iconUpload" name="iconUpload" type="file" onchange="readURL(this)" onmouseover="change1($('#grayBtn1'));" onmouseout="change2($('#grayBtn1'))" style="font-weight:inherit;font-style:inherit;font-size:inherit;font-family:inherit"/>
				                </div>
                                <br/>  
		                        <input type="hidden" id="iconImage" />
		                        <div class="Menuboxtubiao">
		                            <ul>
		                                <li id="tubiao1" onclick="setTab('tubiao',1,10)"></li>
		                                <li id="tubiao2" onclick="setTab('tubiao',2,10)"></li>
		                                <li id="tubiao3" onclick="setTab('tubiao',3,10)"></li>
		                                <li id="tubiao4" onclick="setTab('tubiao',4,10)"></li>
		                                <li id="tubiao5" onclick="setTab('tubiao',5,10)"></li>
		                                <li id="tubiao6" onclick="setTab('tubiao',6,10)"></li>
		                                <li id="tubiao7" onclick="setTab('tubiao',7,10)"></li>
		                                <li id="tubiao8" onclick="setTab('tubiao',8,10)"></li>
		                                <li id="tubiao9" onclick="setTab('tubiao',9,10)"></li>
		                                <li id="tubiao10" onclick="setTab('tubiao',10,10)" style="display: none;"></li>
		                            </ul>
		                        </div>
		                   		<div class="peizhi_tubiao_gd">
		                            <div style="display :none" id="seleccolor">
		                            </div>
		                        </div>
		                        <input type="hidden" id="iconColor" value="#dc2209" />
		                        <div id="bgColorDlg" style="position: absolute; left: 632px; top: 500px;">
		                        </div>
		                    </div>
		                    <div style="clear: both">
		                    </div>
		                    <div style="padding-left: 180px; padding-top: 6px;color: #838e9e;">
		                        建议114*114像素，图片大小不要超过50K</div>
		                </div>
		                <div style="margin-top:30px;height:40px">
		                     <span style="float:left;">logo</span>
		                      <div style="float:left;height:40px;padding-left:25px">
		                          <img  style="width:195px;height:30px" id="logoimg"  src="${contextPath}/resources/app/images/stepone/logo.png" />
		                     </div>
		                     <div style="float:left;height:40px;padding-left:25px;margin-top:4px">
		                        <button class="grayBtn" id="grayBtn3">上传应用logo图</button>
				                <div  style="width:70px; margin: -25px 160px 0px 0px; opacity: 0; filter: alpha(opacity = 0)">
				                    <input id="logoUpload" name="logoUpload" onchange="readURL(this)" type="file" onmouseover="change1($('#grayBtn3'));" onmouseout="change2($('#grayBtn3'))" style="font-weight:inherit;font-style:inherit;font-size:inherit;font-family:inherit"/>
				                
				                </div>
				               
		                     </div>
		                     <div style="clear:both;padding-left:50px;color: #838e9e;">
		                            建议522*80像素，图片格式png</div> 
		                </div>
		                
		                
		                <div class="peizhi_qidongtu">
		                    <span>启动图</span>
		                    <div class="peizhi_gde" style="width:119px;">
		                        <img class="peizhi_qidongtu_img" id="dwimg" src="${contextPath}/resources/app/images/stepone/11.png" />
		                        <p style="line-height: 28px;">
		                            <a title="点击选择更多图片" onclick="ShowDlg();" href="javascript:void(0);">选择更多</a></p>
		                    </div>
		                    <div id="defaultwel" style="display: none; padding-top: 15px;">
		                        <div class="main_tob left">
		                            <ul>
		                                <li><a href="javascript:void(0);">
		                                    <img onclick="select(this,11)" alt="默认欢迎图" src="${contextPath}/resources/app/images/stepone/11.png"
		                                        width="100" height="150"></a></li>
		                                <li><a href="javascript:void(0);">
		                                    <img onclick="select(this,12)" alt="默认欢迎图" src="${contextPath}/resources/app/images/stepone/12.png"
		                                        width="100" height="150"></a></li>
		                                <li><a href="javascript:void(0);">
		                                    <img onclick="select(this,13)" alt="默认欢迎图" src="${contextPath}/resources/app/images/stepone/13.png"
		                                        width="100" height="150"></a></li>
		                                <li><a href="javascript:void(0);">
		                                    <img onclick="select(this,14)" alt="默认欢迎图" src="${contextPath}/resources/app/images/stepone/14.png"
		                                        width="100" height="150"></a></li>
		                                <li><a href="javascript:void(0);">
		                                    <img onclick="select(this,15)" alt="默认欢迎图" src="${contextPath}/resources/app/images/stepone/15.png"
		                                        width="100" height="150"></a></li>
		                                <li><a href="javascript:void(0);">
		                                    <img onclick="select(this,16)" alt="默认欢迎图" src="${contextPath}/resources/app/images/stepone/16.png"
		                                        width="100" height="150"></a></li>
		                            </ul>
		                        </div>
		                        <div class="clear">
		                        </div>
		                    </div>
		                    <div class="peizhi_qidongtu_scl">
		                    <button class="grayBtn" id="grayBtn2">上传欢迎图</button>
				                <div  style="width:70px; margin: -25px 160px 0px 0px; opacity: 0; filter: alpha(opacity = 0)">
				                    <input id="splashUpload" name="splashUpload" onchange="readURL(this)" type="file" onmouseover="change1($('#grayBtn2'));" onmouseout="change2($('#grayBtn2'))" style="font-weight:inherit;font-style:inherit;font-size:inherit;font-family:inherit"/>
				                </div>
		                        <input type="hidden" id="splashImg" value="/Upload/welcomeIcon/welcome1/Default.png" />
		                        <p style="line-height: 18px;padding-top: 18px;">
		                            建议640*1136像素,图片格式jpg</p>
		                    </div>
		                    <div style="clear: both">
		                    </div>
		                </div> 
		            </div>
		            <div class="jiben_youbian">
		                <div class="jiben_youbianyi">
		                    <img src="${contextPath}/resources/app/images/stepone/phone_bg.png" />
		                </div>
		                <div class="jiben_youbianer" id="pibg" style="background: #dc2209;">
		                    <dl>
		                        <dt id="picon">汉</dt><dd id="pname">${app.name }</dd>
		                    </dl>
		                </div>
		                <div class="jiben_youbiansan">
		                    <img id="pimg"/>
		                </div>
		            </div>
		            <div style="clear: both">
		            </div>
		            <form action="${url }" method="post" id="oprform" name="oprform">
		            	<input type="hidden" name="iid" value="${app.iid}">
						<input type="hidden" id="appname" name="name" value="${app.name }">
						<input type="hidden" id="appspec" name="spec" value="${app.spec }">
						<input type="hidden" id="logopath" name="logoPath" value="${app.logoPath }">
						<input type="hidden" id="splashIcon" name="splashIcon" value="${app.splashIcon}">  
						<input type="hidden" id="logoIcon" name="logoIcon" value="${app.logoIcon}">  
						<input type="hidden" name="modelType" id="modelType" value="${app.modelType}"/>
						<input type="hidden" name="jmpurl" id="jmpurl" value="${jmpurl}"/>
						<input type="hidden" name="pic" id="pic" value="${pic}"/> 
						<input type="hidden" name="ssp" id="ssp" value="${ssp}"/>
						<input type="hidden" name="iconurl" id="iconurl" value="${iconurl}"/> 
						<div style="width:300px;padding-left:300px;">
					       <ul class="next">
						      <li><a href="javascript:back()">取消创建</a></li>
						      <li><a href="javascript:laststep()">上一步</a></li>
						      <li><a href="javascript:nextstep()">下一步</a></li>
						    </ul>
				      	</div>
					</form> 
		            <div style="clear: both">
		            </div>
		        </div>
		    </div> 
        </div> 
        <script src="${contextPath}/resources/app/js/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${contextPath}/resources/app/js/ajaxfileupload.js" type="text/javascript"></script>
    <script src="${contextPath}/resources/app/js/farbtastic.js" type="text/javascript"></script>
    <script src="${contextPath}/resources/app/layer/layer.js" type="text/javascript"></script>
    <script type="text/javascript">
		$(function(){
			var logoPath = $('#logopath').val();
			var iconurl = $('#iconurl').val();
			var logoIcon=$('#logoIcon').val();
			if(logoIcon!=""){
				$("#logoimg").attr("src","${jmpurl}"+logoIcon + '?' + Math.random());
			}
			$("#iconColorShow").html("<div class='iconFontI'>汉</div>");
			if(logoPath == ''){
				$('#logopath').val(1);
	    		}else if(logoPath!=1&&logoPath!=2&&logoPath!=3&&logoPath!=4&&logoPath!=5&&logoPath!=6&&logoPath!=7&&logoPath!=8&&logoPath!=9){
		    		if(iconurl!=""){
		    			$("#picon").html('<img style="padding:0px;width:55px; height:55px;" src="' + iconurl + '?' + Math.random() + '" />');
			    		$("#iconColorShow").html('<img style="padding:0px;width:90px; height:93px;" src="'+iconurl + '?' + Math.random() + '" />');
			    		}else{
				    		$('#iconColorShow').css("background","#ffffff");
			    			$("#picon").html('<img style="padding:0px;width:55px; height:55px;" src="'+'${contextPath}' + logoPath + '?' + Math.random() + '" />');
				    		$("#iconColorShow").html('<img style="padding:0px;width:90px; height:93px;" src="'+'${contextPath}'+logoPath + '?' + Math.random() + '" />');
				    		}
		    	}else{
					if(logoPath==1){
						$("#iconColorShow,#pibg").css("background-color", "#dc2209");
						$("#iconColorShow").html("<div class='iconFontI'>" + $("#iconWord").val() + "</div>");
		                $("#picon").text($("#iconWord").val());
						}
					if(logoPath==2){
						$("#iconColorShow,#pibg").css("background-color", "#0e81cc");
						$("#iconColorShow").html("<div class='iconFontI'>" + $("#iconWord").val() + "</div>");
						$("#picon").text($("#iconWord").val());
						}
					if(logoPath==3){
						$("#iconColorShow,#pibg").css("background-color", "#7aa020");
						$("#iconColorShow").html("<div class='iconFontI'>" + $("#iconWord").val() + "</div>");
						$("#picon").text($("#iconWord").val());
					}
					if(logoPath==4){
						$("#iconColorShow,#pibg").css("background-color", "#df971f");
						$("#iconColorShow").html("<div class='iconFontI'>" + $("#iconWord").val() + "</div>");
						$("#picon").text($("#iconWord").val());
						}
					if(logoPath==5){
						$("#iconColorShow,#pibg").css("background-color", "#e6c565");
						$("#iconColorShow").html("<div class='iconFontI'>" + $("#iconWord").val() + "</div>");
						$("#picon").text($("#iconWord").val());
					}
					if(logoPath==6){
						$("#iconColorShow,#pibg").css("background-color", "#5a7bca");
						$("#iconColorShow").html("<div class='iconFontI'>" + $("#iconWord").val() + "</div>");
						$("#picon").text($("#iconWord").val());
					}
					if(logoPath==7){
						$("#iconColorShow,#pibg").css("background-color", "#29a0c6");
						$("#iconColorShow").html("<div class='iconFontI'>" + $("#iconWord").val() + "</div>");
						$("#picon").text($("#iconWord").val());
					}
					if(logoPath==8){
						$("#iconColorShow,#pibg").css("background-color", "#e75b5d");
						$("#iconColorShow").html("<div class='iconFontI'>" + $("#iconWord").val() + "</div>");
						$("#picon").text($("#iconWord").val());
						}
					if(logoPath==9){
						$("#iconColorShow,#pibg").css("background-color", "#373737");
						$("#iconColorShow").html("<div class='iconFontI'>" + $("#iconWord").val() + "</div>");
						$("#picon").text($("#iconWord").val());
					}
			    }
			var splashIcon = $('#splashIcon').val();
			var ssp = $('#ssp').val();
			if(ssp!=""){
				$("#dwimg").attr("src",ssp + '?' + Math.random());
				$("#pimg").attr("src",ssp + '?' + Math.random());
				}else if(splashIcon == ''){
				$('#splashIcon').val(11);
	    		}else if(splashIcon!=11&&splashIcon!=12&&splashIcon!=13&&splashIcon!=14&&splashIcon!=15&&splashIcon!=16){
	    			$("#dwimg").attr("src",'${contextPath}'+splashIcon + '?' + Math.random());
		    	}else if(splashIcon==11||splashIcon==12||splashIcon==13||splashIcon==14||splashIcon==15||splashIcon==16){
		    		$("#pimg").attr("src",'${contextPath}/resources/app/images/stepone/'+splashIcon + '.png?' + Math.random());
		    		$("#dwimg").attr("src",'${contextPath}/resources/app/images/stepone/'+splashIcon + '.png?' + Math.random());
			    	}
			});			
        $('#seleccolor').farbtastic('#iconColor');
        function myfun(){
        	$('#iconUpload').hide();
        	}
        $("#bgColorDlg").prepend($("div[class='farbtastic']")).hide();
        var appid = getURLParameter("appid");

        var dlg = null, cindex = 0, curColor = "#dc2209";
        function ShowDlg() {
            layer.open({
                type: 1,
                title: false,
                area: ['735px', '180px'],
           		 content: $('#defaultwel')
            });
        } 
		function back(){
			location.href='${jmpurl}/app/login.do';
			}
		function laststep(){
			location.href='${jmpurl}/app/login.do';
			}
        function nextstep(){
        	var regu = "^[a-zA-Z0-9\u4e00-\u9fa5]+$"; 
        	var re = new RegExp(regu); 
            var s= $('#appname').val();
            var spec = $('#appnote').val();
            var str = $('#iconWord').val();
            var logoIcon=$('#logoIcon').val(); 
            if(s==""){
            	layer.alert('应用名称不能为空', {icon: 2});
             }else if(s!=""&& (s.indexOf('#')>=0 || s.indexOf('&'))){
            	 layer.alert('应用说明不能包含#或&', {icon: 2});
             }else if(!re.test(s)||!re.test(str)){
               	layer.alert('应用名称或图标名不能为空且只能是中文、英文或数字组成', {icon: 2});
             }else if(logoIcon==""){
               	layer.alert('logo不能为空', {icon: 2});
             }else{
               	$('#oprform').submit();
              }
            }
        $(function () {
            $("#apptitle").on("change keyup blur", function () {
                $("#pname").text($(this).val());
                $("#pimg").removeAttr("src");
                if($("#iconColorShow").getHexBackgroundColor()=="#ffffff"){
                	$("#iconColorShow").css("background","#dc2209");
        			$("#logopath").val(1);
                    }
                var icoWord = $(this).val();
                if (icoWord.length > 0 && $("#iconImage").val() == "") {
                    icoWord = icoWord.substring(0, 1);
                    $("#iconWord").val(icoWord);
                    $("#iconWord").change();
                }
            });
            $("#iconWord").on("keyup",function(){
                if ($(this).val().length > 0) {
                    $("#picon").text($(this).val());
                    $("#pic").val($(this).val());
                    if($("#iconColorShow").getHexBackgroundColor()=="#ffffff"){
                    	$("#iconColorShow").css("background","#dc2209");
                        }
                    $("#pimg").removeAttr("src");
                    $("#iconColorShow").html("<div class='iconFontI'>" + $(this).val() + "</div>");
                }
                });
            $("#iconWord").on("change blur", function () {
                if ($(this).val().length > 0) {
                    $("#picon").text($(this).val());
                    if($("#iconColorShow").getHexBackgroundColor()=="#ffffff"){
                    	$("#iconColorShow").css("background","#dc2209");
                        }
                    $("#pic").val($(this).val());
                    $("#pimg").removeAttr("src");
                    $("#iconColorShow").html("<div class='iconFontI'>" + $(this).val() + "</div>");
                }
                if($(this).val().length <=0){
                    var icoWord = $('#apptitle').val();
                    if (icoWord.length > 0 && $("#iconImage").val() == "") {
                        icoWord = icoWord.substring(0, 1);
                        $("#iconWord").val(icoWord);
                        $("#iconWord").change();
                    }
                    }
            });

             

            /* 处理色块选择器隐藏 */
            $("body").mouseup(function (event) {
                var $this = $(event.target);
                var colorTriger1 = $("#seleccolor");
                var colorPicker1 = $("#bgColorDlg");
                //处理第一个色块
                if (!IsHasObj($this.parents(), colorPicker1) && !$this.is(colorPicker1) && !$this.is(colorTriger1)) {
                    colorPicker1.hide();
                }
            });

            /* 处理隐藏结束 */
            $("#seleccolor").on("click", function () {
                if (cindex++ % 2 == 0)
                    $("#bgColorDlg").show();
                else
                    $("#bgColorDlg").hide();
            });

            window.setInterval(function () {
                if (curColor != $("#iconColor").val()) {
                    curColor = $("#iconColor").val();
                    $("#tubiao10").show().css("background-color", curColor).click();
                    $("#iconColorShow").css("background-color", curColor);
                }
            }, 700);
        });

        function select(obj,n) {
            $("#dwimg").attr("src", $(obj).attr("src"));
            $("#splashImg").val($(obj).attr("src"));
            $("#pimg").attr("src", $(obj).attr("src"));
            $("#splashIcon").val(n);
            layer.close(dlg);
        }

        function setTab(name, cursel, n) { 

        	 if ( $("#iconWord").val().length > 0) {
                 $("#picon").text( $("#iconWord").val());
                 $("#pic").val( $("#iconWord").val());
                 $("#pimg").removeAttr("src");
                 $("#iconColorShow").html("<div class='iconFontI'>" + $("#iconWord").val() + "</div>");
             }
             
            for (var i = 1; i <= n; i++) {
                var menu = document.getElementById(name + i);
                var stext = $('#iconWord').val();
                if(stext==''){
                	$("#picon").text("汉");
                    }else{
                    $("#picon").text(stext);
                     }
                menu.className = i == cursel ? "hover" : "";
                if (i == cursel) {
                    var selectedColor = $("#" + name + cursel).getHexBackgroundColor(); 
                    $("#iconColorShow,#pibg").css("background-color", selectedColor);
                    $("#iconColor").val(selectedColor);
                    $("#logopath").val(cursel);
                    $("#picon").html('');
                    curColor = selectedColor;
                }
            }
        }
        //获取某对象背景色的Hex格式颜色值
        $.fn.getHexBackgroundColor = function () {
            var rgb = $(this).css('background-color');
            //if (!$.browser.msie) {
                rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
                function hex(x) {
                    return ("0" + parseInt(x).toString(16)).slice(-2);
                }
                rgb = "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
           // }
            return rgb;
        }
        function getURLParameter(name) {
            return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)', 'i').exec(location.search) || [, ""])[1].replace(/\+/g, '%20')) || null;
        }

        ///遍历元素集合是否包含某对象
        function IsHasObj(collections, tobj) {
            var outBool = false;
            collections.each(function () {
                if ($(this).is(tobj)) { outBool = true; return false; }
            });
            return outBool;
        }
        function readURL(input) {   
            if ($(input).val().length > 0) {
                var ext = input.value.toLocaleLowerCase().match(/\.([^\.]+)$/)[1]; 
                switch (ext) {
                case 'jpg':
                case 'png':
                case 'jpeg': 
                    if ($(input).attr("id") == "iconUpload") {
                        PicUpload("iconUpload", "iconColorShow", "iconImage", "icon");
                    }  else if ($(input).attr("id") == "splashUpload") {
                        PicUpload1("splashUpload", "dwimg", "splashImg", "splash");
                    } else if ($(input).attr("id") == "logoUpload") { 
                        PicUpload2("logoUpload", "dwimg", "splashImg", "splash");
                    }
                    break;
                default:
                    parent.layer.alert("图标格式不正确，只能为PNG或JPG！", 5);
                }
            }
            return false;
        }
        function changeTitleValue(){
			var s1 = $("#apptitle").val();
            $("#pimg").removeAttr("src");
			$("#appname").val(s1);
            }

       function changeSpecValue(){
           var s2 = $("#appnote").val();
           $("#appspec").val(s2);
           }
        function PicUpload(uploadid, imgid, inputid, type) { 
            $.ajaxFileUpload({
                url: "${contextPath}/createapp/appadd.do",
                fileElementId: uploadid, 
                dataType: 'json', 
                success: function (data, status) { 
                    if(data.info==""){
                    	parent.layer.alert(data.error, 5);
                        }else {
                        	 $("#picon").html('<img style="padding:0px;width:55px; height:55px;" src="' + data.pimg + '?' + Math.random() + '" />');
                         	 $("#logopath").val(data.info);
                         	 $("#iconurl").val(data.pimg);
                         	 $("#iconColorShow").html('<img style="padding:0px;width:90px; height:93px;" src="'+data.pimg + '?' + Math.random() + '" />');
                         }
                },
                error: function (data, status, e) { 
                    parent.layer.alert(e, 5);
                }
            });
            return false;
        }
        function PicUpload1(uploadid, imgid, inputid, type) {
            $.ajaxFileUpload({
                url: "${contextPath}/createapp/appadd1.do",
                fileElementId: uploadid, 
                dataType: 'json', 
                success: function (data, status) { 
                    if(data.info==""){
                    	parent.layer.alert(data.error, 5);
                       }else{
	                     $("#splashIcon").val(data.info);
	                     $("#pimg").attr("src",data.splash);
	                     $("#dwimg").attr("src",data.splash);
	                     $("#ssp").val(data.splash);
                      }
                },
                error: function (data, status, e) { 
                    parent.layer.alert(e, 5);
                }
            });
            return false;
        }

        function PicUpload2(uploadid, imgid, inputid, type) { 
            $.ajaxFileUpload({
                url: "${contextPath}/createapp/appadd2.do",
                fileElementId: uploadid, 
                dataType: 'json', 
                success: function (data, status) { 
                    if(data.info==""){
                    	parent.layer.alert(data.error, 5);
                      }else{ 
                     	$("#logoIcon").val(data.info); 
                     	$("#logoimg").attr("src",$("#jmpurl").val()+data.info);
                     }
                },
                error: function (data, status, e) { 
                    parent.layer.alert(e, 5);
                }
            });
            return false;
        }

        function change1(a) {
        	a.css ({
        	    "background-color": "#DADADA",
        	    "behavior": "url('/pie/PIE.htc')",
        	    "-pie-background": "linear-gradient(top, #DADADA, #FFF)",
        	    "background-image": "-moz-linear-gradient(center top, #DADADA, #FFF)",
        	    "background-image": "-webkit-gradient(linear, 0% 0%, 0% 100%, from(#DADADA), to(#FFF))"
        	});
        }
        function change2(a) {
        	a.css ({
        	    "background-color": "#FFF",
        	    "behavior": "url('/pie/PIE.htc')",
        	    "background-image": "-moz-linear-gradient(center top, #FFF, #DADADA)",
        	    "background-image": "-webkit-gradient(linear, 0% 0%, 0% 100%, from(#FFF), to(#DADADA))",
        	    "-pie-background": "linear-gradient(top, #FFF, #DADADA)",
        	    "border": "1px solid #ccc",
        	    "border-radius": "3px 3px 3px 3px",
        	    "cursor": "pointer",
        	    "text-align": "center"
        	});
        }
    </script>
         <div class="banq">
		    <div>北京国信大汉科技有限公司版权所有 京ICP13053142号
		     <br/>建议使用Chrome浏览器以获得更佳的体验
		    </div>
		 </div>
         
<div style="display: none;">
    <script language="javascript" type="text/javascript" src="${contextPath}/resources/app/js/17465663.js"></script>
</div>

    </div>
</body>
</html>
