<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="h" uri="/WEB-INF/tag/hanweb-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>同步字段</title>
<h:head pagetype="dialog" tabs="true" tip="true" validity="true" select="true" tree="true"></h:head>
<script>
	$(function(){
		$('#oprform').validity(function() {
			$('#name').require('同步字段必须填写').maxLength(24, "同步字段不能超过24个字");
			$('#fieldName').require('数据库字段必须填写');
		},{
			success:function(result){
				if(result.success){
					closeDialog(true);
				} else {
					alert(result.message);
				}
			}
		});
		$('#fieldName').menu({
			tree : 'FieldMenu',
			height : 200,
			init : function(){
				showFieldTree();
			}
		});
	});

	function showFieldTree(){
		var FieldMenu = ${FieldMenu }; 
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
					beforeClick : beforeClickField,
					onClick : onClickField
				}
			};
		$('#FieldMenu').tree(setting, FieldMenu);
	}

	/**
	 *	选择节点点击回调
	 */
	function onClickField(event, treeId, treeNode) {
		if (treeNode.isDisabled){
			return;
		}
		$('#fieldName').val(treeNode.name);
	}

	/**
	 *	选择节点点击前回调
	 */
	function beforeClickField(treeId, treeNode, clickFlag) {
		if (treeNode.isDisabled)
			return false;
		return (treeNode.id != 0);
	}

</script>
</head>
<body>
	<form action="${url }" method="post" id="oprform" name="oprform">
		<input type="hidden" name="ditchid" id="ditchid" value="${ditchid }" />
		<input type="hidden" name="iid" id="iid" value="${synField.iid }" />
		<input type="hidden" name="siteId" id="siteId" value="${synField.siteId }" />
		<input type="hidden" name="ditchId" id="ditchId" value="${synField.ditchId }" />
		<input type="hidden" name="orderId" id="orderId" value="${synField.orderId }" />
		<input type="hidden" name="fieldType" id="fieldType" value="${synField.fieldType }" />
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0" class="table">
				<tr>
					<td align="right" class="label">同步字段名称</td>
					<td class="required" width="15px">*</td>
					<td>
						<input type="text" id="name" name="name" class="input-text" value="${synField.name }" style="width:300px"/>
						<h:tip title="源系统对应字段的名称"></h:tip>
					</td>
				</tr>
				<tr>
					<td align="right" class="label">数据库字段名称</td>
					<td class="required">*</td>
					<td>
						<c:if test="${url=='add_submit.do' }">
							<input type="text" name="fieldName" id="fieldName" readonly="readonly" class="input-text" value="${synField.fieldName}" />
							<h:tip title="此处对应信息列表中新增的数据库字段"></h:tip>
						</c:if>
						<c:if test="${url=='modify_submit.do' }">
							<input type="hidden" name="fieldName" id="fieldName" value="${synField.fieldName }" />
							<input type="text" name="fieldName" id="fieldName" readonly="readonly" class="input-text" disabled="disabled" value="${synField.fieldName}" />
						</c:if>
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