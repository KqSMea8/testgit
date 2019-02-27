<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="h" uri="/hanweb-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>图片信息列表</title>
<h:head pagetype="dialog" validity="true" menu="true" tabs="true" tree="true" select="true" upload="true" tip="true"></h:head>
<script type="text/javascript">
	var classid = 0;
	$(function(){
		$('#lightAppName').menu({
			tree : 'lightAppMenu',
			height : 200,
			init : function(){
				showLightAppTree();
			}
		});
	});

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
		$('#lightAppMenu').tree(setting, lightAppMenu);
	}

	/**
	 *	栏目选择节点点击回调
	 */
	function onClickLightApp(event, treeId, treeNode) {
		if (treeNode.isDisabled){
			return;
		}
		$('#lightAppId').val(treeNode.id);
		$('#lightAppName').val(treeNode.name);
		$('#name').val(treeNode.name);
		classid = treeNode.id;
	}

	/**
	 *	栏目选择节点点击前回调
	 */
	function beforeClickLightApp(treeId, treeNode, clickFlag) {
		if (treeNode.isDisabled)
			return false;
		return (treeNode.id != 0);
	}

	function selectpicture(classid){
		var pc = document.getElementById("lightAppName").value;
		if(pc==''){
			alert('请选择图片分类！');
			return false;
		}
		openDialog("matter/picture/add_show.do?classId="+classid, 980, 600, {
			title : '新增图片',
			callback:refreshPage
		});
	}

	//回调赋值
	function refreshPage(){
		window.location.reload();
		closeDialog(true);
	}
</script>
</head>
<body>
	<form action="${url }" enctype="multipart/form-data" method="post" id="oprform">
		<div id="dialog-content">
		 	<table border="0" align="left" cellpadding="10" cellspacing="0" class="table">
				<tr id="apptr">
					<td align="right" class="label">选择分类</td>
					<td class="required">*</td>
					<td>
					<input type="text" name="lightAppName" id="lightAppName" readonly="readonly" class="input-text" value="${picturecol.name}" /></td>
				</tr>
			</table>
		</div>
		<!--表单按钮区-->
        <div id="dialog-toolbar" >
			<div id="dialog-toolbar-panel">
				<input type="button" class="btn btn-primary" value="保存" onclick="selectpicture(classid)"/> 
				<input type="button" class="btn" value="取消" onclick="closeDialog();" />
			</div>
		</div>
	</form>
</body>
</html>