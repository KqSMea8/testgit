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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消息管理</title> 
<link type="text/css" rel="stylesheet" href="../../ui/css/page.css"/>
<h:head pagetype="dialog" calendar="true" validity="true" tip="true" select="true" upload="true" tree="true"></h:head> 
<script>   

</script>
<style>
	.opr_tr_hidden{
 		display : none ;
 	}
	.opr_tr{
   		display : block ;
 	}
</style>
<script type="text/javascript" src="${contextPath}/ui/widgets/ueditor/base.config.js"></script>
<script type="text/javascript" src="${contextPath}/ui/widgets/ueditor/editor_api.js"></script> 
</head> 
<body> 
	<form action="${url}"  method="post" id="oprform" name="oprform" enctype="multipart/form-data">
		<div id="dialog-content" style="padding-top:0px;">
		    <table border="0px" align="left" cellpadding="10" cellspacing="0" class="table" id="TbData" >
				<tr class="opr_tr" id="tr_content">
					<td align="right" class="label" style="width:100px;padding-left:8px">内容</td>
					<td class="required" style="width:30px;padding-left:8px"> </td>
					<td colspan="4">
						<textarea id='receipt' name='receipt'>${infodetail.receipt}</textarea>
						<script language='javascript'>
							UE.getEditor('receipt');
						</script>
					</td>
				</tr>
			</table>
		</div>
		<div id="dialog-toolbar">
			<div id="dialog-toolbar-panel">
				 <input
					type="button" class="btn" value="取消" onclick="closeDialog();" />
			</div>
		</div>
	</form>	
</body>
</html>