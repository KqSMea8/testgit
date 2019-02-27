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
<title>应用分类管理</title>
<h:head pagetype="dialog" multiselect="true" tree="true" validity="true"></h:head>
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
		$('#pid').val(treeNode.id);
		$('#pname').val(treeNode.name);
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

	/**
	 *	分类选择 根节点 点击回调
	 */
	function onTopClick() {
		$('#pid').val('');
		$('#pname').val("无");
	}
	
	$(function() {
		$('#pname').menu({
			width: 310,
			height: 250,
			tree : 'groupmenu',
			init : function(menu, obj, treeJq) {
				var setting = {
					async : {
						enable : true,
						url : '${contextPath}/manager/lightapptype/menu/menufortype_search.do',
						autoParam : [ "id=typeId", "isDisabled" ],
						otherParam : [ "currentId", $('#iid').val() ] //当前操作分类id
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
		
		$("#oprform").validity(function() {
				$("#name").require("分类名称不能为空").match('username1',
						"分类名称只能由字母、数字、下划线、中文组成，不能以下划线开头和结尾").maxLength(33,
						"分类名称不能超过33个字");
				/**if ('${url }' == 'add_submit.do') {
				}*/
				$("#remark").maxLength(85, "备注不能超过85个字");

		},{
			success:function(result){
				if(result.success){
					var treeObj = Tree.getInstance();
					treeObj.refreshNode(result.params.refresh);
					treeObj.removeNode(result.params.remove);
					closeDialog(true);
				}else{
					alert(result.message);
				}
			}
		});
		
		$('.clean').click(function() {
			<c:choose>
				<c:when test="${rangeId > 0}">
					$('#pid').val(${rangeId});
					$('#pname').val('${rangeName}');
				</c:when>
				<c:otherwise>
					$('#pid').val(0);
					$('#pname').val('');
				</c:otherwise>
			</c:choose>
		});
	});
</script>
</head>
<body>
	<form action="${url }" method="post" id="oprform">
		<input type="hidden" name="iid" id="iid" value="${lightapptype.iid }" /> 
		<input type="hidden" name="pid" id="pid" value="${lightapptype.pid}" />
		<input type="hidden" name="siteId" id="siteId" value="${lightapptype.siteId}" />
		<input type="hidden" name="prevPid" id="prevpid" value="${lightapptype.pid}" /> 
		<input type="hidden" id="rangeid" value="${rangeId}" /> 
		<div id="dialog-content">
			<table border="0" align="center" 
				class="table">
				<tr>
					<td align="right" class="label">分类名称</td>
					<td class="required">*</td>
					<td><input type="text" id="name" name="name" maxlength="33"
						class="input-text" value="${lightapptype.name }" /></td>
				</tr>
				<tr>
					<td align="right" class="label">父级分类</td>
					<td>&nbsp;</td>
					<td>
						<span class="input-append">
							<input type="text" name="pname" id="pname"
								readonly="readonly" style="width:276px;" class="input-text" value="${lightapptype.pname}" /><i class="iconfont add-on inner button clean" style="color:#CCC;" title="清除">&#xf3015;</i>
						</span>
					</td>
				</tr>
				<tr>
					<td align="right" class="label">备注</td>
					<td>&nbsp;</td>
					<td><textarea id="remark" name="remark" class="input-textarea">${lightapptype.remark}</textarea>
					</td>
				</tr>
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