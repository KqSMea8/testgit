<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="h" uri="/hanweb-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>信息引用转移页</title>
<h:head tree="true" pagetype="dialog" validity="true"></h:head>
<script type="text/javascript">
	$(function() {
		var zNodes = ${tree};
		var setting = {
			callback : {
			beforeClick : beforeClick,
			onClick : onClick,
			onDblClick : onDblClick
			}
		};
		$('#tree').tree(setting, zNodes);
		$('#searchbtn').click(searchGroup);
		$('#searchtext').keyup(function(event) {
			if (event.keyCode == 13) {
				searchGroup();
			}
		});
		
		function searchGroup() {
			var searchText = $('#searchtext').val();
			if ($.trim(searchText) == '') {
				return;
			}
			top.page.location.href = "";
		}
	});
	
	function beforeClick(treeId, treeNode, clickFlag) {
		if (treeNode.isDisabled)
			return false;
		return (treeNode.id != 0);
	}
	
	function onClick(event, treeId, treeNode) {
		if (treeNode.isDisabled || treeNode.id == '0'){
			return;
		}
		$('#colId').val(treeNode.id);
	}
	
	function onDblClick(event, treeId, treeNode) {
		if(treeNode == null){
			return;
		}
		if (treeNode.isDisabled|| treeNode.id == '0')
			return;
		if (treeNode.id == '${rangeId}'){
			onTopClick();
		} else {
			$('#colId').val(treeNode.id);
		}
		$('#pname_menu').fadeOut(50);
	}
	
	$(function() {
		$("#oprform").validity(function() {
		},{
			success:function(result){
				if(result.success){ 
					closeDialog(true);
				}else{
					alert(result.message);
				}
			}
		});
	});
</script> 
</head>
<body>
	<form action="${url}"  method="post" id="oprform" name="oprform"> 
	<input type="hidden" id="ids" name="ids" value="${ids }"/>
	<input type="hidden" id="colId" name="colId" value="${colId }">
		<div id="dialog-content">
	<div id="treewrap">
		<ul id="tree" class="ztree"></ul>
	</div>
		</div> 
		<div id="dialog-toolbar">
			<div id="dialog-toolbar-panel">
				<input type="submit" class="btn btn-primary" value="确定" /> <input
					type="button" class="btn" value="取消" onclick="closeDialog();" />
			</div>
		</div> 
	</form> 
</body>
</html>