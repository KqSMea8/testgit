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
	$("#oprform").validity(function() {
		$("#goodCount").require("点赞数不能为空");
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
<body >  
	<form action="${url}"  method="post" id="oprform" name="oprform"> 
		<input type="hidden" name="iid" id="iid" value="${infoCount.iid}" /> 
		<input type="hidden" name="titleId" id="infoId" value="${infoCount.titleId}" /> 
		<input type="hidden" name="type" id="type" value="${infoCount.type}" /> 
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0"
				class="table">
					<tr>
					<td align="right" class="label">点赞数</td>
					<td class="required">*</td>
					<td><input type="text" id="goodCount" name="goodCount" value="${infoCount.goodCount }" class="input-text" maxlength="33"></td>
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