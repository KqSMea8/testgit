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
<title>图片管理</title>
<link type="text/css" rel="stylesheet" href="../../ui/css/page.css" />
<h:head pagetype="dialog" calendar="true" validity="true" tip="true"
	select="true" upload="true" tree="true"></h:head>
<script>   

//全局变量
var flashObj;
window.onload = function(){ 
    //创建Flash相关的参数集合
    var flashOptions = {
        container:"flashContainer",                                                    //flash容器id
        url:'${contextPath}/manager/info/picfileupload.do',                            // 上传处理页面的url地址
        ext:'{"param1":"参数值1", "param2":"参数值2"}',                                 //可向服务器提交的自定义参数列表
        fileType:'{"description":"图片", "extension":"*.jpg;*.jpeg;*.png"}',     //上传文件格式限制
        flashUrl:'${contextPath}/resources/jmp/info/upload/script/imageUploader.swf',                                                  //上传用的flash组件地址
        width:520,                         //flash的宽度
        height:280,                        //flash的高度
        gridWidth:101,                     // 每一个预览图片所占的宽度
        gridHeight:80,                    // 每一个预览图片所占的高度
        picWidth:90,                      // 单张预览图片的宽度
        picHeight:65,                     // 单张预览图片的高度
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
			if(result[i].picDesc.length>600){
				alert('图片描述不能超过600个字！');
				return;
			}
			picDesc+="%_&"+result[i].picDesc;
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
	$('#picsize').val(picSize);  
	$('#uploadpic').val(1);  
	$('#picbutton').click();  
}

function saveAndAuditPic(){ 
	var url = "${url}"; 
	if(url !="add_submit.do" ){
		$('#picbutton').click();
	}else{
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
	} 
}

$(function() { 
	var url = "${url}"; 
	if(url =="add_submit.do" ){
		 $("#tdsyntime").hide();
		 $("#tdpicabstract").hide();
		 $("#tdpic").show();
	}else{
		 $("#tdsyntime").show();
		 $("#tdpicabstract").show();
		 $("#tdpic").hide();
	}
	$("#oprform").validity(function() { 
		if(url !="add_submit.do" ){
			$("#synTime").require("发布时间不能为空"); 
			$("#picabstract").maxLength(60,
			"摘要不能超过60个字"); 
		}
	},{
		success:function(result){
			if(result.success){
			   var tb = getDialog(); 
			   refreshParentWindow(); 
			   window.setTimeout(function(){
				  tb.dialog('close');   
				}, 0);
			} else {
				alert(result.message);
			} 
		}
	});
});
 
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
<script type="text/javascript"
	src="${contextPath}/resources/jmp/info/upload/script/suggest.js"></script>
<script type="text/javascript"
	src="${contextPath}/resources/jmp/info/upload/script/tangram.js"></script>
</head>
<body>
	<form action="${url}"  method="post" id="oprform" name="oprform" enctype="multipart/form-data">
	    <input type="hidden" name="iid" id="iid" value="${pic.iid }"/> 
		<input name="picname" id="picname" type="hidden" value="${pic.picname}" />
		<input name="picdesc" id="picdesc" type="hidden" value="${pic.picdesc}" /> 
		<input name="siteId" id="siteId" type="hidden" value="${pic.siteId}" /> 
		<input name="colId" id="colId" type="hidden" value="${pic.colId}" /> 
		<input name="infoId" id="infoId" type="hidden" value="${pic.infoId}" /> 
		<input name="picpath" id="picpath" type="hidden" value="${pic.picpath}" /> 
		<input name="orderId" id="orderId" type="hidden" value="${pic.orderId}" /> 
	    <input type="hidden" name="uploadpic" id="uploadpic" />    
        <div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0"
				class="table" width="100%">
				<tr id="tdpic">
					<td align="right" class="label">选择图片</td>
					<td class="required">*</td>
					<td>
					<div id="flashContainer"></div>
					</td>
				</tr> 
				
				 <tr id="tdsyntime">
				    <td align="right" class="label">发布时间</td>
					<td class="required">*</td>
					<td> 
					  <input id="synTime" name="synTime" type="text" class="jcalendar input-text" 
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d'})" value="${synshowtime}" style="width:355px"/> 
					</td> 
				</tr>
				
				<tr id="tdpicabstract">
				    <td align="right" class="label">摘要</td>
					<td class="required"></td>
					<td>  
					  <textarea class="input-textarea" name="picabstract" id="picabstract" style="width:355px;height:160px">${pic.picabstract}</textarea>
					  
					</td> 
				</tr> 
				
			</table>
		</div>
		<div id="dialog-toolbar">
			<div id="dialog-toolbar-panel">
				<input type="button" class="btn" value="保存" onClick="saveAndAuditPic();"/> 
			    <input type="button" class="btn" value="取消" onclick="closeDialog();" />
			    <input id="picbutton"  name="picbutton" type="submit" style="display:none" class="btn btn-primary"   value="保存"/>  
			</div>
		</div> 
</form>
</body>
</html>