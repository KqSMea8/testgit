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
<title>卡片维度</title>
<h:head pagetype="dialog" multiselect="true" tree="true" validity="true"></h:head>
<script type="text/javascript">
	$(function() {
		var zNodes = ${tree};
		var setting = {
			async : {
				
			},
			callback : {
				onCheck  : onClickCard
			},
			check: {
				enable: true
			}
		};
		setting.check.chkStyle = "checkbox";
		setting.check.chkboxType = { "Y" : "", "N" : "" };
		$("#tree").tree(setting, zNodes);
		
		$("#oprform").validity(
				function() {
					
				},{
					success:function(result){
					if(result.success){
						alert(result.message);
						closeDialog(true);
					} else {
						alert(result.message);
					}
				}
			});
		
	});
	
	function onClickCard(event, treeId, treeNode) {
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getCheckedNodes(true);
		var colIds = '';
		for(var i=0; i<nodes.length; i++){
			colIds += ','+ nodes[i].id;
		}
		$("#colId").val(colIds);
	}
	
</script>
<style type="text/css">
	.input-textarea {
		resize: none;
	}
</style>
</head>
<body>
	<div style="height: 405px;overflow:auto;">
		<ul id="tree" class="ztree"></ul>
	</div>
	<form action="${url }" method="post" id="oprform" name="oprform">
		<input type="hidden" id="colId" name="colId" value="${colId }">
		<input type="hidden" id="dimensionid" name="dimensionid" value="${dimensionid }">
		<div id="dialog-toolbar">
			<div id="dialog-toolbar-panel">
				<input type="submit" class="btn btn-primary" value="保存" />
				<input type="button" class="btn" value="取消" onclick="closeDialog();" />
			</div>
		</div>
	</form>
</body>
</html>