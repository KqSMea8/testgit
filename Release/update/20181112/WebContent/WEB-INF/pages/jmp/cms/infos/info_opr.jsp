<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="h" uri="/hanweb-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信息管理</title>
<h:head pagetype="dialog" calendar="true" validity="true" tip="true" select="true" upload="true" tree="true"></h:head>
<link type="text/css" rel="stylesheet" href="${contextPath}/ui/css/page.css" />
<script>   

//全局变量
var flashObj;
window.onload = function(){ 
    //创建Flash相关的参数集合
    var flashOptions = {
        container:"flashContainer",                                                    //flash容器id
        url:'${contextPath}/manager/info/picfileupload.do',                                           // 上传处理页面的url地址
        ext:'{"param1":"参数值1", "param2":"参数值2"}',                                 //可向服务器提交的自定义参数列表
        fileType:'{"description":"图片", "extension":"*.jpg;*.jpeg;*.png"}',     //上传文件格式限制
        flashUrl:'${contextPath}/resources/jmp/info/upload/script/imageUploader.swf',                                                  //上传用的flash组件地址
        width:620,                         //flash的宽度
        height:180,                        //flash的高度
        gridWidth:101,                     // 每一个预览图片所占的宽度
        gridHeight:60,                    // 每一个预览图片所占的高度
        picWidth:90,                      // 单张预览图片的宽度
        picHeight:50,                     // 单张预览图片的高度
        uploadDataFieldName:'picdata',     // POST请求中图片数据的key,默认值'picdata'
        picDescFieldName:'picDesc',        // POST请求中图片描述的key,默认值'picDesc'
        maxSize:1,                       // 文件的最大体积,单位M
        compressSize:20,  // 上传前如果图片体积超过该值，会先压缩,单位M
        maxNum:10,                         // 单次最大可上传多少个文件
        backgroundUrl:'',                  // flash界面的背景图片,留空默认
        listBackgroundUrl:'',              // 单个预览框背景，留空默认
        buttonUrl:'',                      // 上传按钮背景，留空默认
        compressSide:2,                    //等比压缩的基准，0为按照最长边，1为按照宽度，2为按照高度 和下面的参数配合使用  
        compressLength:3000                 //能接受的最大边长，超过该值Flash会自动等比压缩
    };
    flashObj = createImageUpLoad(flashOptions);
};

//全部图片上传完时的回调 返回json数组
function allUploadCallback(result){   
	if(result.length == 0){ 
		return;
	}
	var picDesc="";
	var picName="";
	var picSize="";
	var state = ""; 
	for(var i=0; i<result.length; i++){
		if(picName.length>0){
			picName+="%_&"+result[i].picName;
		}else{
			picName+=result[i].picName;
		}
		if(picDesc.length>0){
			picDesc+="%_&"+result[i].picDesc;
			if(result[i].picDesc.length>600){
				alert('图片描述不能超过600个字！');
				return;
			}
		}else{
			if(result[i].picDesc.length>600){
				alert('图片描述不能超过600个字！');
				return;
			}
			picDesc+=result[i].picDesc;
		} 
		if(picSize.length>0){
			picSize+="%_&"+result[i].picSize;
		}else{
			picSize+=result[i].picSize;
		}
	}   
	$('#picname').val(picName); 
	$('#picdesc').val(picDesc);
	$('#uploadpic').val(1);  
	$('#picbutton').click();  
}

function saveAndAuditPic(){ 
	var colType=$('#infoContentType').val();
	var url = "${url}";  
		
	if(colType==4 && url =="add_submit.do"){
		var uploadpic=$('#uploadpic').val(); 
		if(uploadpic!=1){
			flashObj.upload(); 
			//图片未上传，将按钮置为可用
			if(selectedImageCount<=0){
				alert("请添加图片！"); 
				return false;
			}   
		}else{
			$('#picbutton').click();   
		}   
	}else{
		$('#picbutton').click();   
	}
	
}
function saveAndAudit(){
	$('#auditType').val(2);
	$('#savebutton').click();   
}

/**
 *	选择节点点击前回调
 */
function beforeClick(treeId, treeNode, clickFlag) {
	if (treeNode.isDisabled)
		return false;
	return (treeNode.id != 0);
}

/**
 *	选择节点点击回调
 */
function onClick(event, treeId, treeNode) {
	
	if (treeNode.isDisabled)
		return;
	var id = treeNode.id.replace("col_","")
		.replace("channel_","").replace("survey_","");
	if(id=="0"){
		$('#ztName').val("");
		$('#ztId').val(0);
		return;
	}
	var type = treeNode.attr.infoContentType;
	if(type== 8){
		alert(treeNode.name);
		$('#ztName').val("(栏目)"+treeNode.name);
		$('#infoContentType').val(8);
		$('#infoContentType').select('setValue',type);
	}else if(type== 9){
		$('#ztName').val("(频道)"+treeNode.name);
		$('#infoContentType').val(9); 
		$('#infoContentType').select('setValue',type);
	}
	else if(type== 10){
		$('#ztName').val("(调查)"+treeNode.name);
		$('#infoContentType').val(10); 
		$('#infoContentType').select('setValue',type);
	}
	
	$('#ztId').val(id);
}

/**
 *	节点点击回调
 */
function onTagClick(event, treeId, treeNode) {  
	if (treeNode.isDisabled){
		return;
	}
	if (treeNode.id == '0'){
		$('#tagid').val('');
		$('#tagname').val("");
	} else {
		$('#tagid').val(treeNode.id);
		$('#tagname').val(treeNode.name);
	}
}


function onDblClick(event, treeId, treeNode) {
	if(treeNode == null){
		return;
	}
	if (treeNode.isDisabled)
		return;
	if (treeNode.id == '${rangeId}'){
		onTopClick();
	} else {
		$('#pid').val(treeNode.id);
		$('#pname').val(treeNode.name);
	}
	$('#ztname_menu').fadeOut(50);
}

/**
 *	选择 根节点 点击回调
 */
function onTopClick() {
	$('#pid').val('');
	$('#pname').val("无");
}
$(function() {
	changeType(); 
	var url = "${url}";  
	var vedio = $('#vedioFile').parent().prev();
	vedio.removeAttr('readonly');
	var colType=$('#colType').val();
	var infoListType = $("#infoListType").val();
	changeListType(infoListType);
    var typeId = "${info.infoContentType}";
    if(typeId == 1 || typeId == 2 || typeId == 3 || typeId == 4 ){
    	 $("#position").hide();
    }else{
    	$("#position").show();
    }  
    
	if(typeId==4){
		 $("#contentdisplay").hide();
		 if(url =="add_submit.do"){
			 $("#picdisplay").show();
	     }else{
	    	 $("#picdisplay").hide();
		 } 
	}else{
		$("#contentdisplay").show();
		$("#picdisplay").hide();
	}
	if(typeId==6){
		$("#vedioshow").show();
		$("#vedioshow1").show();
		$("#vedioshow2").show();
	}else{
		$("#vedioshow").hide();
		$("#vedioshow1").hide();
		$("#vedioshow2").hide();
	}
	
	vedio.keyup(function(){
		$("#vedioPath").val(vedio.val());
	});
	
 
	$('#ztName').menu({
		tree : 'ztmenu',
		init : function(menu, obj, treeJq) {
			var setting = {
				async : {
					enable : false
				},
				callback : {
					beforeClick : beforeClick,
					onClick : onClick,
					onDblClick : onDblClick
				}
			};

			var rootNode = ${ztMenu};
			treeJq.tree(setting, rootNode);
			treeJq.tree().refreshNode('${rangeId}');
		}
	});

	
	$("#oprform").validity(
		function() { 
			$("#content2").val(UE.getEditor('content').getContent());
			$("#title").require("标题不能为空").maxLength(50, "标题名称不能超过50个字");
			$("#subtitle").match('username1', "副标题只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾").maxLength(33,
			"副标题不能超过85个字");
			$("#url").maxLength(200,"URL不能超过200个字");
			$("#summary").maxLength(50,"摘要不能超过50个字");
			$("#source").maxLength(20,"来源不能超过20个字");
			$("#author").maxLength(20,"作者不能超过20个字");
			var infoContentType=$('#infoContentType').val();
			if(infoContentType!=4){  
				$("#content2").require("内容不能为空"); 
			} 
			var infoListType = $('#infoListType').val();
			if(infoListType==11 || infoListType==12) {
				$('#firstPicFile').assert(function(){
					if($("#firstPicFile").parent().prev().val().length > 0){
						return true;
					}
			        return false;
				},'首图不能为空');  
				}
			$('#firstPicFile').assert(function(){
				if($("#firstPicFile").val().length==0){
					return true;
				}
		        return validateFileType($("#firstPicFile").val(),"${PicFileType}");
			},'首图限于${PicFileType}格式');
			$('#vedioFile').assert(function(){
				if($("#vedioFile").val().length==0){
					return true;
				}
		        return validateFileType($("#vedioFile").val(),"${VideoFileType}");
			},'视频限于${VideoFileType}格式');
			$('#audioFile').assert(function(){
				if($("#audioFile").val().length==0){
					return true;
				}
		        return validateFileType($("#audioFile").val(),"${AudioFileType}");
			},'音频限于${AudioFileType}格式');
			
			var type = $("#infoContentType").val();
			if(type == 8 || type == 9){
				$("#ztName").require("专题不能为空"); 
			}
	    },{type:'iframe'});
	$('#tagname').menu({
		height : 220,
		tree : 'bookTree',
		init : function(menu, obj, treeJq) {
			var setting = { 
					callback : {
						beforeClick : beforeClick,
						onClick : onTagClick,
						onDblClick : onTagDblClick
					}
				 
			};
			 

			var rootNode = ${bookTree};
			treeJq.tree(setting, rootNode);
		}
	}); 
 
    
	$.fn.preview = function(hiddenId){
		var xOffset = 10;
		var yOffset = 20;
		var w = $(window).width();
		var h = $(window).height();
		
		$(this).val(findfilename($("#"+hiddenId).val()));
		$(this).hover(function(e){
			var imgurl = $("#"+hiddenId).val();
			if(imgurl == ''){
				return;
			}
			imgurl = "${jmpurl}" + imgurl;
			var myDate=new Date();
			var img = imgurl + "?" + myDate.getTime();
			$("#preview").remove();
			$("body").append("<div id='preview'><div><img id='img_' src='"+img+"' onload='$(this).resize();'/></div></div>");
			$("#preview").css({
				position:"absolute",
				padding:"4px",
				border:"1px solid #f3f3f3",
				backgroundColor:"#eeeeee",
				top:(e.pageY - yOffset) + "px",
				zIndex:999
			});
			$("#preview > div").css({
				padding:"5px",
				backgroundColor:"white",
				border:"1px solid #cccccc",
				textAlign:"center"
			});
			$("#preview > div > p").css({
				textAlign:"center",
				fontSize:"12px",
				padding:"8px 0 3px",
				margin:"0"
			});
			$("#preview").css({
				left: e.pageX + xOffset + "px",
				right: "auto"
			}).fadeIn("fast");
			if(e.pageY>h/2){
				$("#preview").css({
					top: e.pageY - yOffset -$("#img").height()+ "px"
				}).fadeIn("fast");
			}else{
				$("#preview").css({
					top: e.pageY + yOffset + "px"
				}).fadeIn("fast");
			}
		},function(){
			$("#preview").remove();
		}).mouseout(function(){
			$("#preview").remove();
		}).mousemove(function(e){
			if(e.pageY>h/2){
				$("#preview").css({
					top: e.pageY - yOffset -$("#img").height()+ "px"
				}).fadeIn("fast");
			}
			if(e.pageY>w/2){
				$("#preview").css({
					top: e.pageX - xOffset -$("#img").width()+ "px"
				}).fadeIn("fast");
			}
			$("#preview").css("left",(e.pageX + yOffset) + "px").css("right","auto");
		});						  
	};
	$.fn.resize=function(){
		$img = $("#img_");
		imgw = $img.width();
		imgh = $img.height();
		if(imgw*1>250){
			imgh = imgh/imgw*250;
			imgw=250;
		}
		if(imgh*1>180){
			imgw = imgw/imgh*180;
			imgh=180;
		}
		$img.css("width",imgw+"px");
		$img.css("height",imgh+"px");
	};
	$("#firstPicFile").parent().prev().preview("firstPicPath");
	$("#vedioFile").parent().prev().val(findfilename("${info.vedio}"));
	$("#audioFile").parent().prev().val(findfilename("${info.audio}"));


	$('.clean').click(function() {
		$('#tagid').val('');
		$('#tagname').val("");
	});
});

function findfilename(path){
	if(path.substring(0,4) == '/web'){
		return path.substring(path.lastIndexOf('/')+1);
	}
	return path;
}
function clearfile(showfile, hidfile){
	$('#'+showfile).parent().prev().val("");
	$('#'+hidfile).val("");
	$('#'+showfile).val("");
}
function success(msg){ 
	location.href="${localpage}";
}

function selectpoint(){
	openDialog('${contextPath}/ui/widgets/ueditor/infopoint.html?'+new Date(), 850, 650, {
		title : '设置坐标'
	});
} 
function setpoint(point){
	if(point){
		var values = point.split('-');
		$('#address').val(values[0]);
		$('#poilocationtmp').val(values[1]);
		$('#pointLocation').val(values[1]);
		$('#pointType').val(values[2]);
		$('#poitypename').val(values[3]);
	}
}
var validateFileType = function(filepath,type){
	var extStart=filepath.lastIndexOf(".")+1; 
    var ext=filepath.substring(extStart,filepath.length).toUpperCase();
    if((","+type.toUpperCase()+",").indexOf(","+ext+",") != -1){
    	return true;
    }
    return false;
};

/**
 * 类型切换
 */

function changeType(){
	var type = $("#infoContentType").val();
	var url = "${url}";  
	if(type == 4){ 
		
		if(url =="add_submit.do"){
			$("#contentdisplay").hide();
			$("#picdisplay").show();
		}else{
			$("#picdisplay").hide();
		} 
	}else{
		$("#contentdisplay").show();
		$("#picdisplay").hide();
    }
	if(type == 8 || type == 9 ||type==10){
		$("#tr_zt").show();
		$("#tr_zt1").show();
		$("#tr_zt2").show();
	}else{
		$("#tr_zt").hide();
		$("#tr_zt1").hide();
		$("#tr_zt2").hide();
	}

	if(type==6){
		$("#vedioshow").show();
		$("#vedioshow1").show();
		$("#vedioshow2").show();
	}else{
		$("#vedioshow").hide();
		$("#vedioshow1").hide();
		$("#vedioshow2").hide();
	}
	
	if(type==7){
		$("#positionLocation").show();
		$("#positionLocation1").show();
		$("#positionLocation2").show();
	}else{
		$("#positionLocation").hide();
		$("#positionLocation1").hide();
		$("#positionLocation2").hide();
	}
}    
/**
 *	节点点击前回调
 */
function beforeClick(treeId, treeNode, clickFlag) { 
	if (treeNode.isDisabled){
		return false;
	}
	return true;
}


function onTagDblClick(event, treeId, treeNode) { 
	if(treeNode == null){
		return;
	}
	if (treeNode.isDisabled){
		return;
	}
	
	if (treeNode.id == '0'){
		$('#tagid').val('');
		$('#tagname').val("");
	} else {
		$('#tagid').val(treeNode.id);
		$('#tagname').val(treeNode.name);
	}
	$('#tagnames_menu').fadeOut(50);
}
 

function initValue(){
	$('#colids').val("${channel.colIds }");
	$('#colnames').val("${channel.colNames }");
}

function clearValue(){
	$('#tagid').val("");
	$('#tagname').val("");
}
function changeListType(type) {
	if(type==11 || type==12) {
		$('#firstpic1').hide();
		$('#firstpic').show();
	}else{
		$('#firstpic1').show();
		$('#firstpic').hide();
	}

	if(type==14){
		$("#audioshow").show();
		$("#audioshow1").show();
		$("#audioshow2").show();
	}else{
		$("#audioshow").hide();
		$("#audioshow1").hide();
		$("#audioshow2").hide();
	}
	if(type==13){
		$("#tr_tag").show();
		$("#tr_tag1").show();
		$("#tr_tag2").show();
	}else{
		$("#tr_tag").hide();
		$("#tr_tag1").hide();
		$("#tr_tag2").hide();
	}
	
}
function autoMatch(){
		var infoName = $('#title').val(); 
		var tagNames = '${tagNames}';
		var tagIds = '${tagIds}';
		var names = '';
		var ids = '';
		for(var i = 0; i < tagNames.split(',').length; i++){
			var tagName = tagNames.split(',')[i];
			if(infoName.indexOf(tagName) >= 0 ){
				ids += "," + tagIds.split(',')[i];
				names += "," + tagNames.split(',')[i];
			}
		}
		if (ids.length > 0) {
			ids = ids.substring(1);
		}
		if (names.length > 0) {
			names = names.substring(1);
		}
		$('#tagid').val(ids);
		$('#tagname').val(names);
	}

function downpic(picpath) {
	if(picpath==null || picpath==""){
        alert("图标为空，暂时无法下载");
	}else{
		window.open("downloadfile.do?filePath="+picpath);
	}
}
</script>
<style>
.table td {
	padding-bottom: 13px;
	line-height: 0px;
	vertical-align: middle;
}

.table tr {
	vertical-align: middle;
}

.input-textarea {
	resize: none;
}

</style>
<script type="text/javascript" charset="utf-8" src="${contextPath}/ui/widgets/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/ui/widgets/ueditor/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/ui/widgets/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/ui/widgets/ueditor/lang/en/en.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/ui/widgets/ueditor/plugins/filemanager.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/ui/widgets/ueditor/plugins/fileselector.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/ui/widgets/ueditor/plugins/picture.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/ui/widgets/ueditor/plugins/attach.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/ui/widgets/ueditor/plugins/media.js"></script>
<script type="text/javascript" src="${contextPath}/resources/jmp/info/upload/script/tangram.js"></script>

</head>
<body>
	<form action="${url}"  method="post" id="oprform" name="oprform" enctype="multipart/form-data">
		<input type="hidden" name="iid" id="iid" value="${info.iid }" /> 
		<input type="hidden" name="colId" id="colId" value="${info.colId }" />
		<input type="hidden" name="topId" id="topId" value="${info.topId }" />
		<input type="hidden" name="pushState" id="pushState" value="${info.pushState}" /> 
		<input type="hidden" name="orderid" id="orderid" value="${info.orderid }" />
		<input type="hidden" name="path" id="path" value="${info.path}" />
		<input id="localpage" name="localpage" type="hidden" value="${localpage }" />  
		<input id="auditType" name="auditType" type="hidden" value="${auditType }" />  
		<input id="firstPicPath" name="firstPicPath" type="hidden" value="${info.firstPicPath }" />   
		<input id="vedio" name="vedio" type="hidden" value="${info.vedio }" />   
		<input id="audiotime" name="audiotime" type="hidden" value="${info.audioTime}" />   
		<input id="vedioPath" name="vedioPath" type="hidden" value="${info.vedioPath }" />   
		<input id="audio" name="audio" type="hidden" value="${info.audio }" />
		<input id="jgetId" name="jgetId" type="hidden" value="${info.jgetId}" />
		<input id="pushTime" name="pushTime" type="hidden" value="${pushshowtime}" />
		<input name="picname" id="picname" type="hidden" value="${info.picname}" />
		<input name="picdesc" id="picdesc" type="hidden" value="${info.picdesc}" /> 
	    <input type="hidden" name="uploadpic" id="uploadpic" /> 
	    <input type="hidden" name="colType" id="colType" value="${colType}" />
	    
<div id="dialog-content" style="padding-top: 0; width: 100%">
		    <table border="0" align="left"   cellpadding="0"  cellspacing="0" class="table">
				<tr style="font-size:20px;">
					<td colspan="9">		
						<div id="page-title">
							<span class="switchmenu"><i class="icon-sitemap"></i> ${operatename}
								 -<span id="page-title-text">${operatetype}</span> 
								 <i class="icon-caret-down"></i>
							</span>
						</div>				
					</td>
				</tr>
				<tr>
					<td align="right" class="label" style="width:70px;padding-left:8px">标题</td>
					<td class="required" style="width:30px;padding-left:8px">*</td>
					<td ><input type="text" id="title" name="title"
						class="input-text" value="<c:out value="${info.title}" escapeXml="false"></c:out>" style="width:300px" maxlength="85"/></td>
					
					<td align="right" class="label">摘要</td>
					<td class="required"></td>
					<td colspan="4"><input type="text" id="summary" name="summary"
						class="input-text" value="${info.summary}" style="width:300px"/></td>
					
				</tr> 
				 <tr>
					<td align="right" class="label" >来源</td>
					<td class="required"></td>
					<td ><input type="text" id="source" name="source"
						class="input-text" value="${info.source}" style="width:300px"/></td>
					<td  align="right" class="label">作者</td>
					<td class="required"></td>
					<td ><input type="text" id="author" name="author"
						class="input-text" value="${info.author}" style="width:300px"/></td>
					 
					
				</tr>
				 <tr>
					<td align="right" class="label">文章源地址</td>
					<td class="required">&nbsp;</td>
					
				   <td ><input type="text" name='url' id='url'
						class="input-text" value="${info.url}" style="width:300px"/></td>
					
					<td align="right" class="label">首图</td>
					<td id="firstpic" class="required">*<h:tip title="大图尺寸建议16:9，小图建议4:3"></h:tip></td>
					<td id="firstpic1" class="required">&nbsp<h:tip title="大图尺寸建议16:9，小图建议4:3"></h:tip></td>
					<td colspan="10"> 
						<input id="firstPicFile" type="file" class="input-text" name="firstPicFile" input-width="100">
						<input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('firstPicFile','firstPicPath')">
					    <input type="button" class="btn btn-success btn-small" value="下载" onclick="downpic('${info.firstPicPath}')">
					</td>
					
				</tr>
				
				<tr>
					<td align="right" class="label">列表展现</td>
					<td class="required">&nbsp;</td>
					<td >
						<select name="infoListType" id="infoListType" data-value="${info.infoListType}" style="width:300px" onchange="changeListType(this.value)">
						   <c:forEach var="type" items="${InfoListType }" >
						   		<option value="${type.key }">${type.value }</option> 
						   </c:forEach>
						</select>
					</td>
					
					<td id="tr_tag" align="right" class="label">选择角标 </td>
					<td id="tr_tag1" class="required">&nbsp;</td>
					<td colspan="4" id="tr_tag2">
					<span class="input-append">
						<input type="hidden" name="tagid" id="tagid" style="display:none;"
							value="${info.tagid}" />
						<input name="tagname" id="tagname" class="input-text"
							readonly="readonly" style="width:280px" value="${info.tagname}"/><i class="iconfont add-on inner button clean" style="color:#CCC;" title="清除">&#xf3015;</i>
					</span>
					</td>
					
					<td id="audioshow" align="right" class="label">音频</td>
					<td id="audioshow1" class="required">&nbsp;<h:tip title="格式为MP3,可以手写音频地址或者本地上传音频"></h:tip></td>
					<td id="audioshow2" colspan="7">
						<input id="audioFile" type="file" class="input-text" name="audioFile" input-width="150">
						<input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('audioFile','audio')">
					</td> 
					 
				</tr>
				
				<tr>
					<td align="right" class="label">内容展现</td>
					<td class="required">&nbsp;</td>
					<td >
						<select name="infoContentType" id="infoContentType" data-value="${info.infoContentType}" onchange="changeType()" style="width:300px">
						   <c:forEach var="type" items="${InfoContentType }" >
						   		<option value="${type.key }">${type.value }</option> 
						   </c:forEach> 
						</select>
					</td>
					
					<td id="vedioshow" align="right" class="label">视频</td>
					<td id="vedioshow1" class="required">&nbsp;<h:tip title="格式为MP4,可以手写视频地址或者本地上传视频"></h:tip></td>
					<td id="vedioshow2" colspan="7">
						<input id="vedioFile" type="file" class="input-text" name="vedioFile" input-width="150">
						<input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('vedioFile','vedio')">
					</td>  
					
					<td id="tr_zt" align="right" class="label">专题</td>
					<td id="tr_zt1" class="required" style="width:30px;padding-left:8px">*</td>
					<td id="tr_zt2" colspan="7">
						<input type="hidden" name="ztId" id="ztId" value="${info.ztId}">
						<input type="text" name="ztName" id="ztName" readonly="readonly" class="input-text" style="width:300px" value="${ztName}"/>
					</td>
					
					<td id="positionLocation" align="right" class="label">当前位置</td>
					<td id="positionLocation1" class="required">&nbsp;<h:tip title="点击设置坐标选择地点"></h:tip></td>
					<td id="positionLocation2">
						<input type="hidden" name="pointType" id="pointType" value='${info.pointType}'/>
						<input type="hidden" name="pointLocation" id="pointLocation" value='${info.pointLocation}'/>
						<input type="text"  class="input-text" name="address" id="address" value="${info.address}" style="width:300px;"/><br/>
						<div style="height: 10px;"></div><br/>
						<input type="text" class="input-text" name="poitypename" id="poitypename" disabled="disabled" value='${poitypename}' style="width: 105px;"/>
						<input type="text"  class="input-text" name="poilocationtmp" id="poilocationtmp" disabled="disabled" value="${poilocationtmp}" style="width: 105px"/>
						<input type="button" class="btn btn-success btn-small" value="设置坐标" onClick="selectpoint();" >
					</td>  
				</tr>
				
				<tr>
				<td align="right" class="label">发布时间</td>
					<td class="required"></td>
					<td >
					<input id="synTime" name="synTime" type="text" class="jcalendar input-text" 
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d'})" value="${synshowtime}" style="width:200px"/>
				</td>
				</tr>
				
				<tr id="picdisplay">
					<td align="right" class="label">选择图片</td>
					<td class="required" style="width: 30px; padding-left: 8px">*</td>
					<td colspan="4">
					<div id="flashContainer"></div>
					</td>
				</tr>
		
				<c:forEach items="${customFields}" varStatus="i" var="field">
					<c:if test="${i.index%2==0}"><tr></c:if>
					<td align="right" class="label">${field.name}</td>
					<td class="required" style="width:30px;padding-left:8px"></td>
					<td ><input type="text" id="${field.fieldName}" name="infoExpand['${field.fieldName}']"
						class="input-text" value="${infoExpand[field.fieldName]}" style="width:300px"/>&nbsp;</td> 
					<c:if test="${i.index%2==1}"></tr></c:if>	
				</c:forEach>
				
				
				<tr id="contentdisplay">
					<td align="right" class="label">内容</td>
					<td class="required" style="width: 30px; padding-left: 8px">*</td>
					<td colspan="7">
					 <input type="hidden" id="content2" name="content2" class="input-text"/>
					<textarea id='content' name='content'  >${info.content}</textarea>
					<script language='javascript'>
										UE.getEditor('content');
									</script></td>
				</tr>
			</table>
		</div>
		<div id="dialog-toolbar" style="padding: 0;">
		<div id="dialog-toolbar-panel" align="center">
			<c:if test="${onlylook != 1}">
				<input id="savebutton" type="button" class="btn" value="保存" onClick="saveAndAuditPic();"/>
		    	<c:if test="${auditType != 2}">
		    		<input type="button" class="btn" value="保存并审核" onClick="saveAndAudit();"/>
				</c:if>
				<input type="button" class="btn" value="返回" onclick="history.go(-1);" />
				<input id="picbutton"  name="picbutton" type="submit" style="display:none" class="btn btn-primary"   value="保存"/>
			</c:if>
			<c:if test="${onlylook == 1}">
				<input type="button" class="btn" value="返回" onclick="history.go(-1);" />
			</c:if>	  
		</div>
	</div>
</form>
</body>
</html>