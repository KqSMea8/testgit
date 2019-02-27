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
<title>信息清空</title>
<h:head pagetype="dialog" multiselect="true" menu="true" tree="true"
	validity="true" checkpwd="true" tabs="true" tip="true" select="true" upload="true"></h:head>
<script>
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
<style type="text/css">
	.input-textarea {
		resize: none;
	}
</style>
</head>
<body>
	<form action="${url}" method="post" id="oprform" name="oprform" enctype="multipart/form-data">
		<input type="hidden" name="colId" value="${colId}"> 
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="15"
				class="table"> 
				<tr> 
					<td>您确认清空该栏目下面的所有信息吗？</td>
				</tr> 
				<tr>  
					<td>
						<input type="checkbox" id="showList" name="showList" value="1" data-value="1" />放入回收站 （若不选中该项，则信息将被彻底清除）
					</td>
				</tr> 
			</table>
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