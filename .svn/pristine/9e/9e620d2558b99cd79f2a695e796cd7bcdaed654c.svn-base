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
<title>接口分类</title>
<h:head dialog="true" message="true" iconfont="true" pagetype="dialog" validity="true" 
		checkpwd="true" placeholder="true" upload="true" select="true" tree="true"></h:head>
<script type="text/javascript" charset="utf-8" src="${contextPath}/ui/widgets/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/ui/widgets/ueditor/ueditor.all.js"></script>
<script type="text/javascript">
	$(function(){
$('#pname').menu({
	width: 310,
	height: 250,
	tree : 'groupmenu',
	init : function(menu, obj, treeJq) {
		var setting = {
			async : {
				enable : true,
				url : '${contextPath}/manager/typemenu/menufortype_search.do',
				autoParam : [ "id=typeId", "isDisabled" ],
				otherParam : [ "currentId", $('#iid').val() ]//当前操作机构id
			},
			callback : {
				beforeClick : beforeClick,
				onClick : onClickGroup,
				onDblClick : onDbClickGroup
			}
		};

		treeJq.tree(setting);
	}
});
		
		
		$("#oprform").validity(function() {
			$("#name").require("分类不能为空");
		}, {
			success : function(result) {
				if (result.success) {
					var treeObj = Tree.getInstance();
					treeObj.refreshNode(result.params.refresh);
					treeObj.removeNode(result.params.remove);
					location.reload();
					closeDialog(true);
				} else {
					alert(result.message);
				}
			}
		});
		
		
		 $(".clean").click(function(){
			 $('#pid').val(0);
			 $('#pname').val('');
				 
			 });
		
		
		
		 
		 
		 
	});

	/**
	 *	对textarea进行字数控制
	 */
	function checklength(obj) {
	    var max = $(obj).attr('maxlength');
	    if(max == null || max == "" || max == undefined) {
	        return;
	    }
	    if(obj.value.length > max) {
	        obj.value=obj.value.substring(0,(max-1));
	        return;
	    }
	}
	
	function onClickGroup(event, treeId, treeNode) {
		document.getElementById('pid').value=treeNode.id;
		document.getElementById('pname').value=treeNode.name;
	}

	function onDbClickGroup(event, treeId, treeNode) {
		if (treeNode == null) {
			return;
		}
		
		if (treeNode.isDisabled )//根节点及失效节点双击无效
			return;
		document.getElementById('pid').value=treeNode.id;
		document.getElementById('pname').value=treeNode.name;
		$('#groupname_menu').fadeOut(50);
		
	}
	
	/**
	 *	机构选择节点点击前回调
	 */
	function beforeClick(treeId, treeNode, clickFlag) {
		if (treeNode.isDisabled)
			return false;
		return (treeNode.id != 0);
	}
	
	 
</script>
</head>
<body>
	<form action="${url}" method="post" name="oprform" id="oprform">
		<input type="hidden" name="iid" id="iid" value="${interfaceType.iid}" />
		<input type="hidden" id="pid" name="pid" class="input-text" value="${interfaceType.pid }"></input>
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0"
				class="table">
				<tr>
					<td align="right" class="label">接口分类</td>
					<td class="required">*</td>
					<td><input type="text" id="name" name="name"
						class="input-text" maxlength="33" value="${interfaceType.name}" />&nbsp;</td>
				</tr>
				
				<tr>
					<td align="right" class="label">所属分类</td>
					<td>&nbsp;</td>
					<td>
						<span class="input-append">
							<input type="text" name="pname" id="pname"
								readonly="readonly" style="width:276px;" class="input-text" value="${interfaceType.pname }" /><i class="iconfont add-on inner button clean" style="color:#CCC;" title="清除">&#xf3015;</i>
						</span>
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