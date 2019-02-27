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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>栏目页</title>
<h:head pagetype="dialog" validity="true" menu="true" tabs="true" tree="true" select="true" upload="true" calendar="true" tip="true"></h:head>
<h:import type="js" path="/resources/jmp/col/script/col.js"></h:import>
<link rel="stylesheet" href="${contextPath}/ui/widgets/colorpicker/css/colorpicker.css" type="text/css" />
<script type="text/javascript" src="${contextPath}/ui/widgets/colorpicker/colorpicker.js"></script>
<script>

function selectApp(siteId, iid){
	openDialog('./col/selectApp.do?siteId=' + siteId +  '&iid=' + iid, 800, 500, {
		title : '选择应用',
		callback : addApp
	});
}

function addApp(users, iid, siteId) {
	var usersArray = new Array();
	
	$.each(users, function(id) {
		usersArray.push(id);
	});
	ajaxSubmit("addApp.do?users=" + encodeURI(JSON.stringify(users)), {
		success:function(result){
			if(result.success){
				document.getElementById("lightAppName").value = result.message;
				document.getElementById("newLightAppId").value = result.code;
			}else{
				alert(result.message);
			}
		}
	});
}

/**
* 表单验证
*/
$(function(){
	var url = "${url}";
	var typeId = "${col.type}";
	if(url =="add_submit.do" ){
		changeType(1);
		changeHDType('${col.hdType}', '1');
		$('#autotime').hide();
	}else if(url =="modify_submit.do"){
		changeType('${col.type}');
		changeHDType('${col.hdType}', '${col.type}');
		$("[id^='down_']").show();
		$("[id^='up_']").hide();
		//审核样式
		var auditType = "${col.auditType}";
	    changeAudit(auditType);
	}
	var bgColor = $("#bgColor").val();
	if(bgColor!=""){
		 $("#colorSelector1").css("background-color",bgColor);
		}
	$('#pname').menu({
		tree : 'colMenu',
		height : 200,
		init : showColTree()
	});
	$('#bannerName').menu({
		tree : 'colBannerMenu',
		height : 200,
		init : showColBannerTree()
	});
	$('#lightAppName1').menu({
		tree : 'lightAppMenu',
		height : 200,
		init : function(){
			showLightAppTree();
		}
	});
	$('#taskName').menu({
		height : 300,
		tree : 'taskTree',
		init : function(menu, obj, treeJq) {
        	return showTaskTree(treeJq); 
		} 
	});

	//颜色选择器 
	$('#colorSelector').ColorPicker({
		color: '#008fd5',
		onShow: function (colpkr) {
			$(colpkr).fadeIn(100);
			return false;
		},
		onHide: function (colpkr) {
			$(colpkr).fadeOut(100);
			return false;
		},
		onChange: function (hsb, hex, rgb) {
			$('#colorSelector div').css('backgroundColor', '#' + hex);
			$('#bgColor').val("#"+hex); 
		}
	});
	$('#oprform').validity(function(){
		$('#type').require('栏目类型必须选择');
		$('#name').require('栏目名称不能为空').match('username1', "栏目名称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾").maxLength(33, "栏目名称不能超过33个字");
		var checkType = $('input[name="type"]:checked').val();
		if($('input[name="type"]:checked').val()  == 3){
			$('#lightAppName').require('请选择应用');
			if($('#lightAppId').val().indexOf("type_")>-1 || $('#lightAppId').val().indexOf("root")>-1){
				$('#lightAppId').match('intege1', '请选择一个应用');
			}
		}
		if($('#type').val() == 4 && ($('#hdType').val() == 4 || $('#hdType').val() == 5
				|| $('#hdType').val() == 1 || $('#hdType').val() == 2 || $('#hdType').val() == 3 || $('#hdType').val() == 7)){
			if($('#hdType').val() == 1 || $('#hdType').val() == 2 || $('#hdType').val() == 3){
				$('#actUrl').require('系统ID不能为空').maxLength(255, "系统ID不能超过255个字符");	
				$('#domain').require('系统域名不能为空').maxLength(255, "系统域名不能超过255个字符");	
				$('#bgColor').require('背景颜色不能为空');	 
			} else {
				$('#actUrl').require('接口地址不能为空').maxLength(400, "接口地址名称不能超过400个字符").match('url',"接口地址格式不对");	 
			}
		}
		if($('#type').val() == 4 && $('#hdType').val() == 7){
			$('#nickName').require('微博昵称不能为空');	 
		}
		$('#synPeriod').match('intege1','任务周期必须为正整数');
		if($('#type').val() == 2){ 
			if($("#taskName").val()!=""){
				 $("#startTime").require('开始时间不能为空'); 
			     $("#synPeriod").require('任务周期不能为空'); 
			}
	    	$("#offlineNum").require('离线信息 条数不能为空').match(/^(100|[1-9]\d|\d)$/,'离线信息条数仅限于0~100的整数');	
	    }
		if($("input[name='type']:checked").val()==2){
			if($("input[name='auditType']:checked").val()==3){
				$('#limitTime').require('限时审核时间必须填写').match('intege1', '限时审核时间只能由正整数组成');
			}else{
				$('#limitTime').match('intege1', '限时审核时间只能由正整数组成');
			}
		}
		if($('#commonType').val() == 2){
			$('#bannerName').require('请绑定banner栏目');	 
		}
		if($('#type').val() == 1 || $('#type').val() == 2){
			if($('#type').val() == 2){
				$('#firstPicShow').require('首图展现方式必须选择');
			}
			$('#synPeriod').match('intege1', '任务周期只能由正整数组成');
		} 
		$("#spec").maxLength(80, "备注不能超过80个字");  
		$('#iconFile').assert(function(){
			if($("#iconFile").val().length==0){
				return true;
			}
	        return validateFileType($("#iconFile").val(),"${PicFileType}");
		},'订阅图标限于${PicFileType}格式');
	},{type:'iframe'});

	$.fn.preview = function(path){
		var xOffset = 10;
		var yOffset = 20;
		var w = $(window).width();
		var h = $(window).height();
		if(path == ''){
			return;
		} 
		var imgurl = "${jmpurl}" + path; 
		$(this).val(findfilename(path));
		$(this).hover(function(e){
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
	 
	$("#iconFile").parent().prev().preview("${col.iconPath}");
	var commType1 = '${col.commonType}';
	changeCommonType(commType1);
});

	function findfilename(path){ 
		return path.substring(path.lastIndexOf('/')+1);
	}
	
	function clearfile(showfile, hidfile){
		$('#'+showfile).parent().prev().val("");
		$('#'+hidfile).val("");
	}
	
	//验证附件格式  type  jpg|bmp|zip|...|rar
	var validateFileType = function(filepath,type){
		var extStart=filepath.lastIndexOf(".")+1; 
	    var ext=filepath.substring(extStart,filepath.length).toUpperCase();
	    if((","+type.toUpperCase()+",").indexOf(","+ext+",") != -1){
	    	return true;
	    }
	    return false;
	};

	/**
	 *	栏目选择节点点击前回调
	 */
	function beforeClickCol(treeId, treeNode, clickFlag) { 
		if (treeNode.isDisabled)
			return false;
		return (treeNode.id != 0);
	}
	
	/**
	 *	栏目选择节点点击前回调
	 */
	function beforeClickLightApp(treeId, treeNode, clickFlag) {
		if (treeNode.isDisabled)
			return false;
		return (treeNode.id != 0);
	}
	
	/**
	 *	栏目选择节点点击回调
	 */
	function onClickLightApp(event, treeId, treeNode) {
		if (treeNode.isDisabled){
			return;
		}
		$('#lightAppId').val(treeNode.id);
		$('#lightAppName').val(treeNode.name.split("(")[0]);
		$('#name').val(treeNode.name.split("(")[0]);
	}

	/**
	 *	栏目选择节点点击回调
	 */
	function onClickCol(event, treeId, treeNode) { 
		if (treeNode.isDisabled)
			return;
		$('#pid').val(treeNode.id);
		$('#pname').val(treeNode.name);
	}
	
	function onClickBannerCol(event, treeId, treeNode) {
		if (treeNode.isDisabled)
			return;
		$('#bannerId').val(treeNode.id);
		$('#bannerName').val(treeNode.name);
	}
	
	/**
	 *	任务选择 根节点 点击回调
	 */
	function onTopClick() {
		$('#pid').val('');
		$('#pname').val("无");
	}

	function onTopBannerClick() {
		$('#bannerId').val('');
		$('#bannerName').val("");
	}

	/**
	 *	任务树选择节点点击前回调
	 */
	function beforeClickTask(treeId, treeNode, clickFlag) {
		if (treeNode.isDisabled)
			return false;
		return (treeNode.id != 0);
	}
	
	/**
	 *	任务树选择节点点击回调
	 */
	function onClickTask(event, treeId, treeNode) { 
		if (treeNode.isDisabled || treeNode.id == 'root'){ 
			return;
		}
		$('#taskId').val(treeNode.id);
		$('#sourceurl').val(treeNode.attr.sourceurl);
		$('#sourcename').val(treeNode.attr.sourcename);
		$('#sourcepwd').val(treeNode.attr.sourcepwd);
		$('#sourcetype').val(treeNode.attr.sourcetype);
		$('#ditchId').val(treeNode.attr.ditchId);
		$('#taskName').val(treeNode.name);
		$("#logomust").show(); 
		$("#periodmust").show(); 
	}

	function showColTree(){ 
		var colMenu = ${colMenu}; 
		var setting = {
				async : {
					enable : true,
					url : '${contextPath}/manager/menu/menuforcol_search.do?type=1',
					autoParam : [ "id=colId", "isDisabled" ],
					otherParam : [ "currentId", $('#iid').val() ]//当前操作任务id  当前类别id
				},
				callback : {
					onAsyncSuccess: function checklength(){var nodes = tree.getNodes();if(nodes.length<=0){$("#colName").unbind("click");};},
					beforeClick : beforeClickCol,
					onClick : onClickCol
				}
			};
		var tree = $.fn.zTree.init($('#colMenu'), setting, colMenu);
		tree.reAsyncChildNodes(tree.getNodes()[0]);
	}

	function showLightAppTree(){
		var lightAppMenu = ${lightAppMenu}; 
		var setting = {
				async : {
					enable : true,
					url : '${contextPath}/manager/menu/menuforlightapp_search.do?type=1',
					autoParam : [ "id=appId", "isDisabled" ],
					otherParam : [ "currentId", $('#iid').val() ]//当前操作任务id  当前类别id
				},
				callback : {
					onAsyncSuccess: function checklength(){
						var nodes = tree.getNodes();
						if(nodes.length<=0){
							$("#appName").unbind("click");
						};
					},
					beforeClick : beforeClickLightApp,
					onClick : onClickLightApp
				}
			};
		//var tree = $.fn.zTree.init($('#lightAppMenu'), null, lightAppMenu);
		$('#lightAppMenu').tree(setting, lightAppMenu);
		//tree.reAsyncChildNodes(tree.getNodes()[0]);
	}

	function showColBannerTree(){
		var colBannerMenu = ${colBannerMenu};
		var setting = {
				async : {
					enable : true,
					url : '${contextPath}/manager/menu/menuforcolname_search.do?',
					autoParam : [ "id=colId", "isDisabled" ],
					otherParam : [ "currentId", $('#iid').val() ]//当前操作任务id  当前类别id
				},
				callback : {
					onAsyncSuccess: function checklength(){var nodes = tree.getNodes();if(nodes.length<=0){$("#bannerName").unbind("click");};},
					beforeClick : beforeClickCol,
					onClick : onClickBannerCol
				}
			};
		var tree = $.fn.zTree.init($('#colBannerMenu'), setting, colBannerMenu);
		tree.reAsyncChildNodes(tree.getNodes()[0]);
	}

	function showTaskTree(treeJq){
		var taskTree = ${taskTree};
		var setting = {
				async: {
					enable: true,
					url: "refleshJgetTree.do"
				},
				callback : {
					beforeClick : beforeClickTask,
					onClick : onClickTask
		        }
			};
			treeJq.tree(setting, taskTree);
	}

	function refleshJgetTree(){
		$("#flesh").hide();
		$("#clear").hide();
		var treeObj = $.fn.zTree.getZTreeObj("taskTree");
		//var rootnode = treeObj.getNodeByParam("id", "root", null);
		treeObj.reAsyncChildNodes(null, "refresh");
		setTimeout("show()",2000);
		
		
	}
	
	function show(){
		$("#flesh").show();
		$("#clear").show();
	}
	
	function clearValue(){
		$('#taskId').val("");
		$('#taskName').val("");
		$('#startTime').val("");
		$("#logomust").hide();
		$("#periodmust").hide();
	}
	
	function downpic(picpath) {
		if(picpath==null || picpath==""){
	        alert("图标为空，暂时无法下载");
		}else{
			window.open("downloadfile.do?filePath="+picpath);
		}
	}
</script>
<style type="text/css">
#colorSelector{position:relative;width:36px;height:36px;
	background:url(${contextPath}/ui/widgets/colorpicker/images/select2.png);
	display: inline;padding-bottom:20px;}
#colorSelector div{position:absolute;top:-6px;left:-2px;width:30px;height:30px;
	background:url(${contextPath}/ui/widgets/colorpicker/images/select2.png) center;} 
	.label {
	 	width : 120px;
	}
	.input-textarea {
		resize: none;
	}
	
	.ztree li a{display:inline-block}
</style>
</head>
<body>
	<form action="${url }" enctype="multipart/form-data" method="post" id="oprform">
    	<!--隐藏变量区-->
		<input type="hidden" name="pid" id="pid" value="${col.pid}" />
		<input type="hidden" name="prevPid" id="prevpid" value="${col.pid}" /> 
		<input type="hidden" name="bannerId" id="bannerId" value="${col.bannerId}" />
		<input type="hidden" name="lightAppId" id="lightAppId" value="${col.lightAppId}" />
		<input type="hidden" name="newLightAppId" id="newLightAppId" value="${col.newLightAppId}" />
		<input type="hidden" name="iid" id="iid" value="${col.iid}" />
		<input type="hidden" name="colRelationIid" id="colRelationIid" value="${col.colRelationIid}" />
		<input type="hidden" name="taskId" id="taskId" value="${col.taskId}" />
		<input type="hidden" name="enable" id="enable" value="${col.enable}" />
		<input type="hidden" name="siteId" id="siteId" value="${siteId}" />
		<input type="hidden" name="userId" id="userId" value="${userId}" />
		<input type="hidden" name="orderId" id="orderId" value="${col.orderId}"/>
		<input type="hidden" name="bookorderId" id="bookorderId" value="${col.bookorderId}"/>
		<input type="hidden" name="iconPath" id="iconPath" value="${col.iconPath}" />
		<input type="hidden" name="lastUpdateTime" id="lastUpdateTime" value="${lastupdatetime}" />
		<input type="hidden" name="infoNum" id="infoNum" value="${col.infoNum}" />  
		<input type="hidden" name="blogType" id="blogType" value="1" />
		<input type="hidden" name="sourceurl" id="sourceurl" value="${col.sourceurl}" />
		<input type="hidden" name="sourcename" id="sourcename" value="${col.sourcename}" />
		<input type="hidden" name="sourcepwd" id="sourcepwd" value="${col.sourcepwd}" />
		<input type="hidden" name="ditchId" id="ditchId" value="${col.ditchId}" />
		<input type="hidden" name="sourcetype" id="sourcetype" value="${col.sourcetype}" />
		<input type="hidden" name="colType" id="colType" value="${col.colType}" />
		<input type="hidden" id="keyValue" name="keyValue" value="${col.keyValue}">  
		
		<div id="tabs" class="easyui-tabs" style="height: 540px; " >
		<!--表单主体-->  
		<div title="基本设置" style="padding:20px;overflow-y:auto;" > 
			<table border="0" align="left" cellpadding="10" cellspacing="0"
				class="table"> 
				<tr>
					<td align="right" class="label">栏目类型</td>
					<td class="required" width="10px">*</td>
						<c:choose>
	              		<c:when test="${url=='add_submit.do'}">
	              			<td <c:if test='${col.type > 0}'>disabled="disabled"</c:if>>
					    		<input type="radio"  value="1" name="type" id="type" onchange="changeType(this.value)" data-value="${col.type }" checked="true">类目&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					    		<input type="radio"  value="2" name="type" id="type" onchange="changeType(this.value)" data-value="${col.type }">信息列表&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio"  value="3" name="type" id="type" onchange="changeType(this.value)" data-value="${col.type }">应用
							</td>
						</c:when>
						
						<c:otherwise>
							<td>
								<input type="hidden" name="type" id="type" value="${col.type}" />
					    		<input type="radio"  value="1" name="type" id="type" data-value="${col.type }" disabled="disabled" >类目&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					    		<input type="radio"  value="2" name="type" id="type" data-value="${col.type }" disabled="disabled" >信息列表&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio"  value="3" name="type" id="type" data-value="${col.type }" disabled="disabled" >应用
							</td>
						    
						</c:otherwise>
						</c:choose> 
				</tr> 
				
				<tr>
					<td align="right" class="label">栏目名称</td>
					<td class="required">*</td>
					<td class="required"><input type="text" id="name" name="name" maxlength="80"
						class="input-text" value="${col.name}" />&nbsp;&nbsp;<h:tip title="单双引号及如下字符不支持：`~!@$%^&*()+<>?:{}\/[]" ></h:tip></td>	
				</tr>
				
				<tr id="b">
					<td align="right" class="label">上级栏目</td>
					<td class="required">&nbsp;</td>
					<td>
					<input type="text" name="pname" id="pname" readonly="readonly" class="input-text" value="${col.pname}"  onclick="showColTree()"/></td>
				</tr>
				
				<tr>
					<td align="right" class="label">栏目Icon</td>
					<td class="required"></td>
					<td> 
				     <input id="iconFile" type="file" class="input-text" name="iconFile" input-width="200">
					 <input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('iconFile','iconPath')"> 
					 <input type="button" class="btn btn-success btn-small" value="下载" onclick="downpic('${col.iconPath}')"> 
					</td>
				</tr>
				
				<tr>
					<td align="right" class="label">栏目说明</td>
					<td class="required">&nbsp;</td>
					<td>
						<textarea  class="input-textarea" name="spec" id="spec">${col.spec}</textarea>		
					</td>
				</tr>
			 </table> 
		</div>
		
		 <div title="高级设置" style="padding:20px;overflow-y:auto">
		 	<table border="0" align="left" cellpadding="10" cellspacing="0" class="table">
				<tr id="a4">
						<td align="right" class="label">布局方式</td>
						<td class="required"></td>
						<td><select  name="colListType" id="colListType" data-value="${col.colListType}">
								 <c:forEach var="type" items="${ColListType}" >
						   		<option value="${type.key }">${type.value }</option> 
						   </c:forEach>
							</select>
	                    </td>
				</tr>
				
				<tr id="x">
					<td align="right" class="label">栏目布局</td>
					<td class="required">&nbsp;</td>
					<td>
						<select  name="commonType" id="commonType"  onchange="changeCommonType(this.value)" data-value="${col.commonType}" >
						   <c:forEach var="type" items="${ColCommonType }" >
						   		<option value="${type.key }">${type.value }</option> 
						   </c:forEach>
						</select>
                    </td>
                </tr>
                	
				<tr id="cardDisplay">
						<td align="right" class="label">卡片展现样式</td>
						<td class="required">*</td>
						<td>
						<select name="showType" id="showType" data-value="${col.showType}">
							<option value="0">显示时间和名称</option>
							<option value="1">只显示时间</option>
							<option value="2">只显示名称</option>
							<option value="3">不显示时间和名称</option> 
						</select> 
						</td>
					</tr>
					
                <tr id="y">
					<td align="right" class="label">绑定banner栏目</td>
					<td class="required">*</td>
					<td>
					<input type="text" name="bannerName" id="bannerName" readonly="readonly" class="input-text" value="${col.bannerName}"  onclick="showColBannerTree()"/></td>
				</tr>
				
                <tr id="u">
					<td align="right" class="label">列表布局</td>
					<td class="required">&nbsp;</td>
					<td><select name="infoListType" id="infoListType" data-value="${col.infoListType}" >
						   <c:forEach var="type" items="${InfoListType }" >
						   		<option value="${type.key }">${type.value }</option> 
						   </c:forEach>
						</select>
                    </td>
				</tr>
					
				<tr id="v">
					<td align="right" class="label">内容页布局 </td>
					<td class="required">&nbsp;</td>
					<td><select name="infoContentType" id="infoContentType" data-value="${col.infoContentType}" >
						   <c:forEach var="type" items="${InfoContentType }" >
						   		<option value="${type.key }">${type.value }</option> 
						   </c:forEach> 
						</select>
                    </td>
                    
				</tr>
				
				<tr id="appLayOutTr">
						<td align="right" class="label">应用布局</td>
						<td class="required"></td>
						<td>
							<select  name="appLayOut" id="appLayOut" data-value="${col.appLayOut}">
								<option value="1">一行2个</option>
								<option value="2">一行4个</option>
							<!--<option value="3">滑动</option>   -->	
							</select>
	                    </td>
				</tr>
				
				<tr id="apptricon">
					<td align="right" class="label">应用Icon</td>
					<td></td>
					<td> 
					     <input id="appIconFile" type="file" class="input-text" name="appIconFile" input-width="200">
					</td>
				</tr>
				
				<tr id="apptr">
					<td align="right" class="label">选择应用</td>
					<td class="required">*</td>
					<td>
						<textarea  class="input-textarea" readonly="readonly" name="lightAppName" id="lightAppName">${col.lightAppName}</textarea>
						<input type="button" class="btn btn-success" value="选择应用" style="margin-bottom: 25px;" onclick="selectApp(${siteId},${iid})"/>
					</td>
				</tr>
								
				<tr id="taskn">
					<td colspan="3"><hr style="height:1px;border-top:1px dashed #CCCCCC;"/></td> 
				</tr>
				
				<tr id="t">
					<td align="right" class="label">审核方式</td>
					<td class="required">&nbsp;</td>
					<td name="auditType" id="auditType">
					    <input type="radio" name="auditType" value="2" 
							data-value="${col.auditType }" checked="true" onchange="changeAudit(this.value)">立即审核
						<input type="radio" name="auditType" value="1" 
							data-value="${col.auditType }" onchange="changeAudit(this.value)">手动审核                             
						<input type="radio" name="auditType" value="3" 
							data-value="${col.auditType }" onchange="changeAudit(this.value)">限时审核
						<span id="autotime">
						<input type="text" name="limitTime" class="input-text" id="limitTime" style="width:50px;" value="${col.limitTime}" maxlength="4"/>分后自动审核
						</span> 
					</td>
				</tr>
				
				<tr id="s">
					<td align="right" class="label">是否评论 </td>
					<td class="required">&nbsp;</td>
					<td>
					    <input type="radio" name="isComment" value="1" 
							data-value="${col.isComment }"" checked="true" >是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="isComment" value="0" 
							data-value="${col.isComment }" >否
					</td>
				</tr>
				
				<tr id="z">
					<td align="right" class="label">是否检索 </td>
					<td class="required">&nbsp;</td>
					<td>
					    <input type="radio" name="issearch" value="1" 
							data-value="${col.issearch}" >是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="issearch" value="0" 
							data-value="${col.issearch}" checked="true">否
					</td>
				</tr>	
				
				<tr id="taskn2">
					<td colspan="3"><hr style="height:1px;border-top:1px dashed #CCCCCC;"/></td> 
				</tr>
				
				<tr id="a">
					<td align="right" class="label">信息渠道绑定</td> 
					<td class="required"></td>
					<td>
						<input type="text" name="taskName" id="taskName" readonly="readonly" class="input-text" value="${col.taskName}" />
						<a style='color:#3498DB' id='flesh' onclick='refleshJgetTree();'>刷新</a>&nbsp;&nbsp;&nbsp;&nbsp;<a style='color:#3498DB' onclick='clearValue();' id='clear'>清空</a>
					</td> 
				</tr>
				
				<!-- 
					<tr id="dhdjd">
					<td align="right" class="label">节点绑定</td> 
					<td class="required"></td>
					<td><input type="text" name="nodeNames" id="nodeNames" readonly="readonly" class="input-text" value="123" />
					</td> 
				</tr>
				 -->
				
				
				<tr id="synPeriod_tr">
					<td align="right" class="label">同步周期</td> 
					<td class="required"><span id="periodmust">*</span></td>
					<td>
						<select name="synPeriod" id="synPeriod" data-value="${col.synPeriod}">
							<option value="1">1分</option>
							<option value="3">3分</option>
							<option value="5">5分</option>
							<option value="30">半小时</option> 
							<option value="60">1小时</option>
							<option value="1440">1天</option> 
						</select> 
					</td> 
				</tr>
				
				<tr id="startTime_tr">
					<td align="right" class="label">开始时间</td> 
					<td class="required"><span id="logomust">*</span></td>
					<td><input type="text" name="startTime" id="startTime" readonly="readonly" class="jcalendar input-text" value="${starttime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
					<h:tip title="若绑定jget任务，首次同步时候必须设置，从jget获取设定的开始时间到当前时间点的信息"></h:tip> 
					</td>  
				</tr>
				
				<tr id="w">
					<td align="right" class="label">互动类型</td>
					<td class="required">*</td>
					<td>
						<c:choose>
		              		<c:when test="${url=='add_submit.do'}">
								<select name="hdType" id="hdType" data-value="${col.hdType}" onchange="changeHDType(this.value, '-1')">
								   <c:forEach var="type" items="${ColHDType }" >
								   		<option value="${type.key }">${type.value }</option> 
								   </c:forEach>
								</select>
							</c:when>
							<c:otherwise>
							    <select name="hdType" id="hdType" data-value="${col.hdType}" disabled="disabled" >
								   <c:forEach var="type" items="${ColHDType }" >
								   		<option value="${type.key }">${type.value }</option> 
								   </c:forEach>
								</select>
							</c:otherwise>
						</c:choose> 
					</td>
				</tr>  
				
				<c:if test='${isSupportReg}'>
					<tr>
						<td align="right" class="label">栏目访问权限</td>
						<td class="required">*</td>
						<td><select name="isVisit" id="isVisit" data-value="${col.isVisit}">
								<option value="0">游客访问</option>
								<option value="1">登录访问</option> 
							</select>
	                    </td>
					</tr>
				</c:if>
				
				<c:if test='${isSupportSearch}'>
				<tr id="r">
					<td align="right" class="label">全文检索</td>
					<td class="required">*</td>
					<td><select name="isJsearch" id="isJsearch" data-value="${col.isJsearch}">
							<option value="0">不支持</option>
							<option value="1">支持</option> 
						</select>
                    </td>
				</tr>
				</c:if>

				<tr id="a2">
					<td align="right" class="label">微博昵称</td>
					<td class="required">*</td>
					<td><input type="text" id="nickName" name="nickName" maxlength="80"
						class="input-text" value="${col.nickName}" /></td>
				</tr>
				
				<tr id="a3">
					<td align="right" class="label">接口地址</td>
					<td class="required">*</td>
					<td><input type="text" id="actUrl" name="actUrl" maxlength="400"
						class="input-text" value="${col.actUrl}" /></td>
				</tr >
				
				<tr id="a5">
					<td align="right" class="label">系统域名</td>
					<td class="required">*</td>
					<td><input type="text" id="domain" name="domain" maxlength="400"
						class="input-text" value="${col.domain}" /></td>
				</tr>
				
				<tr id="a6">
					<td align="right" class="label">背景颜色 </td>
					<td class="required">*<h:tip title="请选择或填写适当的RGB颜色，格式为#ffffff"></h:tip></td>
					<td><input type="text" id="bgColor" name="bgColor"
						class="input-text" maxlength="7" class="input-text"
						value="${bgColor}" style="width:266px;" />
						<div id="colorSelector"><div id="colorSelector1" style="background-color: #008fd5"></div></div>
					</td>
				</tr>								
					
				<c:if test='${isSupportOfflineDownload}'>
				<tr id="lxxz">
					<td align="right" class="label">离线下载条数</td> 
					<td class="required">&nbsp;</td>
					<td>
						<select name="offlineNum" id="offlineNum" data-value="${col.offlineNum}">
						    <option value="0" selected>0条</option>
							<option value="20">20条</option>
							<option value="30">30条</option>
							<option value="50">50条</option>
							<option value="100">100条</option> 
						</select> 
						<h:tip title="设置离线下载信息的条数，默认为0条"></h:tip> 
					</td> 
				</tr>
				</c:if>
			</table>
		 </div>
		</div>
        <!--表单按钮区-->
        <div id="dialog-toolbar" >
			<div id="dialog-toolbar-panel" style="padding:0px 0px 10px 0px ;overflow-y:auto; ">
				<input type="submit" class="btn btn-primary" value="保存" /> 
				<input type="button" class="btn" value="取消" onclick="closeDialog();" />
			</div>
		</div>
	</form>
</body>
</html>