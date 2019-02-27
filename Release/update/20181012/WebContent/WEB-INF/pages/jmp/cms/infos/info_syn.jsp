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
<title>信息同步</title>
<h:head pagetype="dialog" calendar="true" validity="true"></h:head> 
<script>    
	$(function() {
		$("#sycninfo").hide(); 
		$("#oprform").validity(function() {
			$("#starttime").require("开始时间不能为空");
			$("#endtime").require("结束时间不能为空");
	},{
		success:function(result){
		if(result.success){
			$("#sycninfo").show(); 
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
		<input type="hidden" name="colId" id="colId" value="${info.colId}" />  
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0" class="table">
				<tr>
					<td align="right" class="label">
					操作时间从<input id="starttime" name="starttime" type="text" class="jcalendar input-text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endtime\')}'});" value="${starttime }" style="width:180px"/> 
					至<input id="endtime" name="endtime" type="text" class="jcalendar input-text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'starttime\')}'});" value="${endtime }" style="width:180px"/></td>
				</tr>
				 
				<tr>
					<td align="left" class="label">
					创建信息时间<input id="createtime" name="createtime" type="text" class="jcalendar input-text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'createtime\')}'});" value="${createtime }" style="width:180px"/></td>
				</tr>
				 
				<tr>
					<td align="left" class="label" id="sycninfo" style="display:hidden">
					  <font color="red"> 信息同步中，请耐心等待。。。</font>
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