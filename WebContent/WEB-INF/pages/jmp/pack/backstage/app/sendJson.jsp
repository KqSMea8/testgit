<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="h" uri="/hanweb-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>发送Json</title>
<h:head pagetype="dialog" multiselect="true" tree="true" validity="true" calendar="true"></h:head>  	
</head>
<body>
	<form action="${url }" method="post" id="oprform">
	    <input type="hidden" id="iid" name="iid" value="${client.iid }">
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0"
				class="table">
				<tr>
					<td align="right" class="label">发送Json</td>
					<td class="required">&nbsp;</td>
					<td>
						<textarea name='sendJson' id='sendJson' class='input-textarea'   
						  style="width:355px;height: 400px;" rows='10'>${client.sendJson }</textarea>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>