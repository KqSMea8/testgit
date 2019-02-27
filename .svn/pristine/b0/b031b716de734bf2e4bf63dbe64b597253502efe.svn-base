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
<title>信息置顶</title>
<h:head pagetype="dialog" calendar="true" validity="true"></h:head> 
<script>   
	$(function() { 
		$("#oprform").validity(function() {
			$("#toptime").require("置顶时间不能为空"); 
		},{
			success:function(result){
			if(result.success){
				var treeObj = Tree.getInstance();  
				closeDialog(true);
			}else{
				alert(result.message);
			}
		}
		});
	});
</script>
</head> 
<body >  
	<form action="${url}"  method="post" id="oprform" name="oprform"> 
		<input type="hidden" name="infoids" id="infoids" value="${infoids}" />  
		<input type="hidden" name="colid" id="colid" value="${colid}" />  
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0"
				class="table">
				<tr>
					<td align="right" class="label">
					信息置顶至：<input id="toptime" name="toptime" type="text" class="jcalendar input-text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'});" value="${currentDate  }" style="width:200px"/> 
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