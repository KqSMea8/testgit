<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="h" uri="/WEB-INF/tag/hanweb-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>数据库升级</title>
<h:head validity="true" loadmask="true" select="true"></h:head>
<h:import type="js" path="/ui/script/ui.js"></h:import>
<h:import type="css" path="/resources/setup/css/main.css"></h:import>
<script type="text/javascript">
function update(obj){
	var input = $(obj).parent().next();
	var url = input.attr('url');
	input.mask('执行中，请稍后...');
	ajaxSubmit('update.do?url='+url, {
		success:function(result){
			input.unmask();
			if(result.success){
				input.html(input.html() + "<font color='green'>（执行成功）</font>");
				$(obj).attr("disabled","disabled"); 
				$(obj).attr("class","btn btn-primary btn-small disabled"); 
			}else{
				input.html(input.html() + "<font color='red'>（"+result.message+"）</font>");
			}
			
		}
	});
}
function oneKeyUpdate(){
	var objs = $('[name=zhi]'); 
	update2(objs, 0);
}
function update2(objs, i){
	if(objs.length == 0){
		alert("请选择正确的升级版本");
		return;
	}
	if(objs.length == i){
		return ;
	}
	var input = $(objs[i]).parent().next();
	if($(objs[i]).attr("disabled")=="disabled"){
		update2(objs, ++i);
	}
	var url = input.attr('url');
	input.mask('执行中，请稍后...');
	ajaxSubmit('update.do?url='+url, {
		success:function(result){
			console.log(result);
			input.unmask();
			if(result.success){
				input.html(input.html() + "<font color='green'>（执行成功）</font>");
				$(objs[i]).attr("disabled","disabled"); 
				$(objs[i]).attr("class","btn btn-primary btn-small disabled"); 
			}else{
				input.html(input.html() + "<font color='red'>（"+result.message+"）</font>");
			}
			update2(objs, ++i);
		}
	});
}

function check(){
	var start = $('#startVersion').val();
	var end = $('#endVersion').val();
	if(1*start >= 1*end){
		alert("历史版本要小于升级版本");
		return false;
	}
	return true;
}
</script>
<style>
#update td{
	padding: 2px 10px 2px 10px;
	border-left:1px dashed #E7E7E7 !important
}
#update td input{
	height: 24px;line-height: 24px;padding: 0 6px;
}
</style>
</head>
<body>
<div class="crumb-path">数据库升级</div>  
<div style="padding: 10px;">
	<form id="mainForm" action="${url}" method="post" onsubmit="return check();">
		<table cellpadding="0" cellspacing="0" width="100%" id="mainTB">
			<tr>
				<td style="width: 100px;">
					选择升级版本
				</td>
				<td>
					<select id="startVersion" name="startVersion" style="width:100px;" data-value="${startVersion }">  
					   <c:forEach items="${updateList }" var="update">
						 <option value="${update.index }">${update.version }</option>
					   </c:forEach>
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp; ~ &nbsp;&nbsp;&nbsp;&nbsp;
					<select id="endVersion" name="endVersion" style="width:100px;" data-value="${endVersion }">  
					    <c:forEach items="${updateList }" var="update">
						 <option value="${update.index }">${update.version }</option>
					   </c:forEach>
					</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" value="确定" class="btn btn-primary"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div style="height:300px; width:700px;    
						border: 1px solid gray;overflow: scroll;font-size: 13px;">
					<table id="update" style="width: 100%"> 
						<c:forEach items="${selectUpdateList }" var="update">
							<c:forEach items="${update.updateItemList }" var="item" varStatus="status">
								<tr> 
									<td width="30"><input name="zhi" type="button" value="执行" onclick="update(this)" class="btn btn-primary btn-small"/></td>
									<td url="${item.url }">(${update.version })${item.value }</td>
								</tr>
							</c:forEach>
					   </c:forEach>
					</table>
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>