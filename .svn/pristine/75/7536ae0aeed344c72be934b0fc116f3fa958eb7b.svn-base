<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="h" uri="/hanweb-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色权限设置</title>
<h:head pagetype="dialog" validity="true" iconfont="true" tree="true" tabs="true"></h:head>
<script>
	$(function() {
		$("#oprform").validity(
			function() {},
			{
				success:function(result){
					if(result.success){
						closeDialog(true);
					}else{
						alert(result.message);
					}
				}
			}
		);

		var colNodes = ${colTree};
		var col_setting = {
			async : {
				enable : true,
				url : '${contextPath}/manager/menu/menuwithurlforcol_search.do',
				autoParam : [ "id=colId", "isDisabled" ]
			},
			check: {
				chkboxType:{'Y':'ps','N':'ps'}, 
	        	chkStyle:'checkbox', 
				enable: true
			},
			callback : {
				beforeClick : beforeClickCol,
				onCheck: onCheckCol
			}
		};
		$("#colTree").tree(col_setting, colNodes);
	
		var colAuditNodes = ${colTreeAudit};
		var col_audit_setting = {
			async : {
				enable : true,
				url : '${contextPath}/manager/menu/menuwithurlforcol_search.do',
				autoParam : [ "id=colId", "isDisabled" ]
			},
			check: {
				chkboxType:{'Y':'ps','N':'ps'}, 
	        	chkStyle:'checkbox', 
				enable: true
			},
			callback : {
				beforeClick : beforeClickAuditCol,
				onCheck: onCheckAuditCol
			}
		};
		$("#colTreeAudit").tree(col_audit_setting, colAuditNodes);

		var colManageNodes = ${colManageTree};
		var col_manage_setting = {
			async : {
				enable : true,
				url : '${contextPath}/manager/menu/menuwithurlforcol_search.do',
				autoParam : [ "id=colId", "isDisabled" ]
			},
			check: {
				chkboxType:{'Y':'ps','N':'ps'}, 
	        	chkStyle:'checkbox', 
				enable: true
			},
			callback : {
				beforeClick : beforeClickColManage,
				onCheck: onCheckColManage
			}
		};
		$("#colManageTree").tree(col_manage_setting, colManageNodes);
		
	});

	/**
	 *	任务选择节点点击前回调
	 */
	function beforeClickCol(treeId, treeNode, clickFlag) {
		if (treeNode.isDisabled)
			return false;
		return (treeNode.id != 0);
	}
	
	/**
	 *	任务选择节点点击前回调
	 */
	function beforeClickPush(treeId, treeNode, clickFlag) {
		if (treeNode.isDisabled)
			return false;
		return (treeNode.id != 0);
	}
	
	/**
	 *	任务选择节点点击前回调
	 */
	function beforeClickAuditCol(treeId, treeNode, clickFlag) {
		if (treeNode.isDisabled)
			return false;
		return (treeNode.id != 0);
	}

	/**
	 *	任务选择节点点击前回调
	 */
	function beforeClickColManage(treeId, treeNode, clickFlag) {
		if (treeNode.isDisabled)
			return false;
		return (treeNode.id != 0);
	}
	
	/**
	 *	任务选择节点点击回调
	 */
	function onCheckCol(event, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("colTree"),
		nodes = zTree.getCheckedNodes(true),
		colIds = "";
		for (var i=0, l=nodes.length; i<l; i++) {
			colIds +=  nodes[i].id+",";
		}
		if(colIds.length > 0){
			colIds  = colIds.substring(2,colIds.length-1);
		}
		$('#colIds').val(colIds);
	}
	
	/**
	 *	任务选择节点点击回调
	 */
	function onCheckAuditCol(event, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("colTreeAudit"),
		nodes = zTree.getCheckedNodes(true),
		colIds = ""; 
		for (var i=0, l=nodes.length; i<l; i++) { 
			colIds +=  nodes[i].id+",";
		} 
		if(colIds.length > 0){
			colIds  = colIds.substring(2,colIds.length-1);
		} 
		$('#colAuditIds').val(colIds);
	}
	
	/**
	 *	任务选择节点点击回调
	 */
	function onCheckPush(event, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("pushTree"),
		nodes = zTree.getCheckedNodes(true),
		pushIds = "";
		for (var i=0, l=nodes.length; i<l; i++) {
			pushIds +=  nodes[i].id+",";
		}
		if(pushIds.length > 0){
			pushIds  = pushIds.substring(2,pushIds.length-1);
		}
		$('#pushIds').val(pushIds);
	}

	/**
	 *	任务选择节点点击回调
	 */
	function onCheckColManage(event, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("colManageTree"),
		nodes = zTree.getCheckedNodes(true),
		colIds = ""; 
		for (var i=0, l=nodes.length; i<l; i++) { 
			colIds +=  nodes[i].id+",";
		} 
		if(colIds.length > 0){
			colIds  = colIds.substring(2,colIds.length-1);
		} 
		$('#colManages').val(colIds);
	}
</script>
</head>
<style>
.rightspan {
	width:120px;  
	float:left; 
	padding:5px 20px;
	overflow:hidden;
	white-space:nowrap;
	text-overflow: ellipsis;
}
</style>
<body>
	<form action="${url }"  method="post" id="oprform" name="oprform">
		<input type="hidden" name="iid" id="iid" value="${iid }" />
		<div id="dialog-content" style="padding: 20px 20px 0 20px; overflow: hidden;">
			<div id="tabs" class="easyui-tabs" style="height: 335px;">  
			
			<div title="功能模块" style="padding: 20px;">
				<c:forEach items="${allRightList }" var="right">
					<span class="rightspan"title="${right.moduleName }" >
						<input type="checkbox" name="rights" class="checkbox" 
							<c:if test="${fn:contains(selectedRightIds, right.custom) }">checked="checked"</c:if>
							value="${right.iid }" />${right.moduleName }
					</span>
				</c:forEach>
			</div>
			
			<div title="栏目编辑" style="padding:20px; overflow-y: auto">
				<input type="hidden" name="colManages" id="colManages" value="${colManages }" />
				<ul id="colManageTree" class="ztree"></ul>
			</div>
			
			<div title="信息编辑" style="padding: 20px; overflow-y: auto">
				<input type="hidden" name="colIds" id="colIds" value="${colIds }" />
				<ul id="colTree" class="ztree"></ul>
			</div>
			
			<div title="信息审核" style="padding:20px;overflow-y :auto">
				<input type="hidden" name="colAuditIds" id="colAuditIds" value="${colAuditIds }" />
				<ul id="colTreeAudit" class="ztree"></ul>
			</div>
			
			<div title="应用管理" style="padding:20px;">
				<c:forEach items="${appRightList }" var="right">
					<span class="rightspan"title="${right.moduleName }" >
						<input type="checkbox" name="rights" class="checkbox" 
							<c:if test="${fn:contains(selectedRightIds, right.custom)}">checked="checked"</c:if>
								  value="${right.iid }" />${right.moduleName }
					</span>
				</c:forEach>
			</div>
			</div>
		</div>
			<div id="dialog-toolbar">
				<div id="dialog-toolbar-panel">
					<input type="submit" class="btn btn-primary" value="保存"/> <input
						type="button" class="btn" value="取消" onclick="closeDialog();" />
				</div>
			</div>
	</form>
</body>
</html>