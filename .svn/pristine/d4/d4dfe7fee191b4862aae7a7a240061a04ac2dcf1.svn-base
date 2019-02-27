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
<title>应用管理</title>
<h:head pagetype="dialog"  validity="true" select="true" tree="true" upload="true"></h:head>
<script type="text/javascript">

	/**
	 *	分类选择节点点击前回调
	 */
	function beforeClick(treeId, treeNode, clickFlag) {
		if (treeNode.isDisabled)
			return false;
		return (treeNode.id != 0);
	}
	
	/**
	 *	分类选择节点点击回调
	 */
	function onClick(event, treeId, treeNode) {
		if (treeNode.isDisabled)
			return;
		$('#lightType').val(treeNode.id);
		$('#lightTypeName').val(treeNode.name);
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
			$('#lightType').val(treeNode.id);
			$('#lightTypeName').val(treeNode.name);
		}
		$('#lightTypeName_menu').fadeOut(50);
	}
	
	/**
	 *	分类选择 根节点 点击回调
	 */
	function onTopClick() {
		$('#lightType').val('');
		$('#lightTypeName').val("无");
	}

	$(function() {
		$('#lightTypeName').menu({
			width: 310,
			height: 250,
			tree : 'groupmenu',
			init : function(menu, obj, treeJq) {
				var setting = {
					async : {
						enable : true,
						url : '${contextPath}/manager/lightapptype/menu/menufortype_search.do',
						autoParam : [ "id=typeId", "isDisabled" ]
					},
					callback : {
						beforeClick : beforeClick,
						onClick : onClick,
						onDblClick : onDblClick
					}
				};

// 				var rootNode = ${groupMenu};
				//treeJq.tree(setting, rootNode);
				treeJq.tree(setting);
				//treeJq.tree().refreshNode('${rangeId}');
			}
		});
		var iconOldName = "${lightapp.iconOldName}";
		$("#iconFile").parent().prev().val(iconOldName);
		
		var typeId = "${lightapp.appType}";
		setType(typeId);
		$("#oprform").validity(function() {
			$("#name").require("应用名称不能为空").match('username1',
					"应用名称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾").maxLength(33,
					"应用名称不能超过33个字");
			 
			$("#url").maxLength(200, "外链地址不能超过200个字"); 
		},{
			type : 'iframe' 
		}); 

		$('.clean').click(function() {
			<c:choose>
				<c:when test="${rangeId > 0}">
					$('#lightType').val(${rangeId});
					$('#lightTypeName').val('${rangeName}');
				</c:when>
				<c:otherwise>
					$('#lightType').val(0);
					$('#lightTypeName').val('');
				</c:otherwise>
			</c:choose>
		});
	});

	function setType(appType){
		 if(appType==2){
			$("#app_url").hide();
		}else if(appType==1){
			$("#app_url").show();
		}
		}
	
	function selectarea(){
		openDialog('http://221.231.137.197/jmportalwb/interfaces/area_s.do', 750, 500, {  
			title : '城市编码：'
		});	
	}
</script>
</head>
<body>
	<form action="${url }" enctype="multipart/form-data" method="post" id="oprform">
		<input type="hidden" id="siteId" name="siteId" value="${lightapp.siteId}">
		<input type="hidden" id="iid" name="iid" value="${lightapp.iid}"> 
		<input type="hidden" id="orderId" name="orderId" value="${lightapp.orderId }">
		<input type="hidden" id="isDefault" name="isDefault" value="${lightapp.isDefault}">
		<input type="hidden" id="defaultType" name="defaultType" value="${lightapp.defaultType}">
		<input type="hidden" id="lightType" name="lightType" value="${lightapp.lightType}">
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0"
				class="table">
				<tr>
					<td align="right" class="label">应用类型</td>
					<td class="required"></td>
					<td >
					<input type="radio"  value="1" name="appType" id="appType" onchange="setType(this.value)" data-value="${lightapp.appType }">H5
					<input type="radio"  value="2" name="appType" id="appType" onchange="setType(this.value)" data-value="${lightapp.appType }">Native&nbsp;&nbsp;&nbsp;&nbsp;
					    
					</td>
				</tr>
				
				<tr>
					<td align="right" class="label">应用名称</td>
					<td class="required">*</td>
					<td><input type="text" id="name" name="name" 
						class="input-text" value="${lightapp.name}" /></td>
				</tr>
				<tr>
					<td align="right" class="label">应用分类</td>
					<td>&nbsp;</td>
					<td>
						<span class="input-append">
							<input type="text" name="lightTypeName" id="lightTypeName"
								readonly="readonly" style="width:276px;" class="input-text" value="${lightapp.lightTypeName}" /><i class="iconfont add-on inner button clean" style="color:#CCC;" title="清除">&#xf3015;</i>
						</span>
					</td>
				</tr>
				<tr id="app_url" >
					<td align="right" class="label">外链地址</td>
					<td class="required"> </td>
					<td class="required"><input type="text" id="url" name="url" 
						class="input-text" value="${lightapp.url}" title="${lightapp.url}"/>
						<c:if test="${lightapp.defaultType==4}"><i class="iconfont help" onclick="selectarea();" title="点击查看城市编码">󵀻</i></c:if>
					</td>
				</tr>
				  
				<tr id="s">
					<td align="right" class="label">是否开启 </td>
					<td class="required">&nbsp;</td>
					<td>
					    <input type="radio" name="isOpen" value="1" data-value="${lightapp.isOpen}" checked="true" >是
						<input type="radio" name="isOpen" value="0" data-value="${lightapp.isOpen}" >否
					</td>
				</tr>
				
				<tr >
					<td align="right" class="label">是否显示标题区域 </td>
					<td class="required">&nbsp;</td>
					<td>
					    <input type="radio" name="isShowTopView" value="0" data-value="${lightapp.isShowTopView}" checked="true" >是
						<input type="radio" name="isShowTopView" value="1" data-value="${lightapp.isShowTopView}" >否
					</td>
				</tr>
				
				<tr>
					<td align="right" class="label">应用Icon</td>
					<td class="required">*</td>
					<td> 
				     <input id="iconFile" type="file" class="input-text" name="iconFile" input-width="200">
					</td>
				</tr>
			
			</table>
		</div>
		<div id="dialog-toolbar">
			<div id="dialog-toolbar-panel">
				<input type="submit" class="btn btn-primary" value="保存" /> 
				<input type="button" class="btn" value="取消" onclick="closeDialog();" />
			</div>
		</div>
	</form>
</body>
</html>