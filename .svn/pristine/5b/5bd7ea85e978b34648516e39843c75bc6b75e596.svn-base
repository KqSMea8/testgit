<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="h" uri="/hanweb-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>功能管理</title>
<h:head pagetype="dialog" multiselect="true" menu="true" tree="true"
	validity="true" checkpwd="true" tabs="true" tip="true" select="true" upload="true"></h:head>
<script>

	/**
	 *	任务树选择节点点击前回调
	 */
	function beforeClick(treeId, treeNode, clickFlag) {
		if (treeNode.isDisabled)
			return false;
		return (treeNode.id != 0);
	}
	
	/**
	 *	任务树选择节点点击回调
	 */
	function onClick(event, treeId, treeNode) {
		if (treeNode.isDisabled || treeNode.id == 'root'){
			return;
		}
		$('#taskid').val(treeNode.id);
		$('#taskname').val(treeNode.name);
	}

	function zTreeOnCheck(event, treeId, treeNode) {
		var treeObj = $.fn.zTree.getZTreeObj("bookTree");
		var nodes = treeObj.getCheckedNodes(true);
		var ids = '';
		var names = '';
		for ( var i = 0; i < nodes.length; i++) {
			if (nodes[i].id == 'root' || nodes[i].id.indexOf('0')==0) {
				continue;
			}
			ids += "," + nodes[i].id;
			names += "," + nodes[i].name;

		}
		if (ids.length > 0) {
			ids = ids.substring(1);
		}
		if (names.length > 0) {
			names = names.substring(1);
		}
		$('#colids').val(ids);
		$('#colnames').val(names);
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
		$('#pname_menu').fadeOut(50);
	}
	
	$(function() {
		var url = "${url}"; 
		var typeId = "${channel.type}";
	    if(url =="modify_submit.do"){	 
			$("[id^='down_']").show();
			$("[id^='up_']").hide(); 
			
		}

		$("#oprform").validity(
			function() {
				var type=$('input[name="type"]:checked').val() ;
				if(type != '1'){
					$("#name").require("导航名称不能为空") 
					.match('username1', "导航名称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾")
					.maxLength(33, "导航名称不能超过33个字");
				}
				if(type !='3'){
					$('#colids').require("默认栏目不能为空"); 
				}
				$('#firstPicFile').assert(function(){
					if($("#firstPicFile").val().length==0){
						return true;
					}
			        return validateFileType($("#firstPicFile").val(),"${PicFileType}");
				},'首图限于${PicFileType}格式');

			}, {type:'iframe'});
		
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
		$("#firstPicFile").parent().prev().preview("${channel.firstPic}"); 

		$("[id^=file_][type=file]").change(function(){	
			var name = this.id.substring(5);
			var hidid = "file_" + name + "_hid";
			$("#"+hidid).val("/web/site${channel.siteId}/site/channel/channel_first"
					+ new Date().getTime()+".jpg");
		});
		
		$('#colnames').menu({
			height : 220,
			tree : 'bookTree',
			init : function(menu, obj, treeJq) {
				var setting = {
					callback : {
						beforeClick : beforeClick,
						onClick : onClick,
						onDblClick : onDblClick
					},
					check : {
						enable : true,
						chkStyle : "checkbox"
					},
					callback : {
						onCheck : zTreeOnCheck
					},
					view: {
						showTitle: false,
						nameIsHTML: true
					}
				};
				setting.check.chkboxType = { "Y" : "", "N" : "" };
				var rootNode = ${bookTree};
				treeJq.tree(setting, rootNode);
			}
		});
		var flag = 0;
		setType(typeId, flag);
		
		initValue();
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
	
	function setType(channelType, flag){
		if(channelType == '1'){
			$("#tr_name").hide();
			$("#tr_showtype").hide();
			$("#selectCol").show();
		}else if(channelType == '2'){
			$("#tr_name").show();
			$("#tr_showtype").show();
			$("#selectCol").show();
		}else if(channelType == '3'){
			$("#selectCol").hide();
			$("#tr_showtype").hide();
			$("#tr_name").show();
		}else if(channelType == '4'){
			$("#selectCol").show();
			$("#tr_showtype").hide();
			$("#tr_name").show();
		}
		if(flag==0){
			return;
		}
		clearValue();
	}
	
	function success(msg){
		closeDialog(true);
	}
	
	function fail(msg){
		alert(msg);
	}
	
	function clearValue(){
		var treeObj = $.fn.zTree.getZTreeObj("bookTree");
		treeObj.checkAllNodes(false);
		$('#colids').val("");
		$('#colnames').val("");
	}
	
	function initValue(){
		$('#colids').val("${channel.colIds }");
		$('#colnames').val("${channel.colNames }");
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
	.input-textarea {
		resize: none;
	}
</style>
</head>
<body>
	<form action="${url }" method="post" id="oprform" name="oprform" enctype="multipart/form-data">
		<input type="hidden" name="iid" value="${channel.iid }">
		<input type="hidden" name="orderId" value="${channel.orderId }">
		<input type="hidden" id="firstPic" name="firstPic" value="${channel.firstPic}">
		<input type="hidden" id="compoundPic" name="compoundPic" value="${channel.compoundPic}">
		<input type="hidden" name="flag" value="${channel.flag }">
		<input type="hidden" name="state" value="${channel.state }">
		
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0"
				class="table">
				<tr>
					<td align="left" class="label">导航类型</td>
					<td class="required"></td>
					<td >
						<input type="radio"  value="1" name="type" id="type" onchange="setType(this.value)" data-value="${channel.type }" checked="true">单一类型&nbsp;&nbsp;&nbsp;&nbsp;
					    <input type="radio"  value="2" name="type" id="type" onchange="setType(this.value)" data-value="${channel.type }">信息门户&nbsp;&nbsp;&nbsp;&nbsp;
					   	<%-- <input type="radio"  value="3" name="type" id="type" onchange="setType(this.value)" data-value="${channel.type }">用户中心&nbsp;&nbsp;&nbsp;&nbsp; --%>
					   	<input type="radio"  value="4" name="type" id="type" onchange="setType(this.value)" data-value="${channel.type }">综合门户&nbsp;&nbsp;&nbsp;&nbsp;
					   	<input type="radio"  value="3" name="type" id="type" onchange="setType(this.value)" data-value="${channel.type }">用户中心
					   
					</td>
					
				</tr>
				<tr id="selectCol">
					<td align="left" class="label">选择栏目 </td>
					<td class="required">*</td>
					<td>
						<input type="hidden" name="colIds" id="colids" style="display:none;"
							value="${channel.colIds}" />
						<textarea  name="colNames" id="colnames" 
							class="input-textarea" readonly="readonly" >${channel.colNames}</textarea>
							
					</td>
					<td><a style='color:#3498DB;margin-left: -35px' onclick='clearValue();'>清空</a></td>
				</tr>
				<tr id="tr_name" style="display: none;">
					<td align="left" class="label">导航名称 </td>
					<td class="required">*</td>
					<td>
						<input type="text" id="name" name="name" class="input-text" value="${channel.name }"></input>
					</td>
				</tr>
				
				 <tr id="tr_showtype" style="display: none;">
					<td align="left" class="label">布局方式</td>
					<td class="required">&nbsp;</td>
					<td name="channeltype" id="channeltype" >
					    <input type="radio"  value="1" name="channeltype"  checked="true" data-value="${channel.channeltype}">横向切换&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio"  value="2" name="channeltype"  data-value="${channel.channeltype}">平铺&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td align="left" class="label">导航Icon</td>
					<td class="required"><h:tip title="导航Icon尺寸建议128*128"></h:tip></td>
					<td> 
					 <input id="firstPicFile" type="file" class="input-text" name="firstPicFile" input-width="200" >
					 <input type="button" class="btn btn-success btn-small" value="清除" onclick="clearfile('firstPicFile','firstPic')"> 
					 <input type="button" class="btn btn-success btn-small" value="下载" onclick="downpic('${channel.firstPic}')"> 
					</td>
				</tr> 
				
				<c:if test='${isSupportReg}'>
					<tr>
						<td align="right" class="label">导航访问权限</td>
						<td class="required">*</td>
						<td><select name="isVisit" id="isVisit" data-value="${channel.isVisit}">
								<option value="0">匿名访问</option>
								<option value="1">登录访问</option> 
							</select>
	                    </td>
					</tr>
				</c:if>
				
			</table>
		</div>
		<div id="dialog-toolbar">
			<div id="dialog-toolbar-panel">
				<input type="submit" class="btn btn-primary" value="保存" /> <input
					type="button" class="btn" value="取消" onclick="closeDialog();" />
			</div>
		</div>
	</form>
</body>
</html>