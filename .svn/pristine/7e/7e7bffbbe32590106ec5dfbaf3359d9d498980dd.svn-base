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
<title>导航排序</title>
<h:head pagetype="dialog" multiselect="true" tree="true" validity="true"></h:head>
<script type="text/javascript">

var orderids = new Array();
$(function(){
	$('#list option').each(function(){
		orderids.push($(this).attr('order'));
	});
});

function reverse() {
	var optSize = $('#list option').size();
	for(var i = 0; i < optSize - 1; i++) {
		$('#list option:last').insertBefore($('#list option').eq(i));
	}
	resetOptGroup();
}

function moveTop() {
	$('#list option:selected').each(function(i){
		var currIndex = $('#list option').index($(this));
		var minIndex = i;
		if (currIndex != minIndex)
			$(this).insertBefore($('#list option:eq(' + minIndex + ')'));
	});
	resetOptGroup();
}

function moveUp(step) {
	$('#list option:selected').each(function(i){
		var currIndex = $('#list option').index($(this));
		var index = currIndex - step;
		var minIndex = i;
		index = index < minIndex ? minIndex : index;
		if (currIndex != i)
			$(this).insertBefore($('#list option:eq(' + index + ')'));
	});
	resetOptGroup();
}

function moveDown(step) {
	var selectedObj = $('#list option:selected').get();
	var totalSize = $('#list option').size();
	for (var i = selectedObj.length - 1; i >= 0; i-- ) {
		var currIndex = $('#list option').index($(selectedObj[i]));
		var index = currIndex + step;
		var maxIndex = totalSize - (selectedObj.length - i);
		if (currIndex == maxIndex)
			continue;
		index = index > maxIndex ? maxIndex : index;
		$(selectedObj[i]).insertAfter($('#list option:eq(' + index + ')'));
	}
	resetOptGroup();
}

function moveBottom() {
	var selectedObj = $('#list option:selected').get();
	var totalSize = $('#list option').size();
	for (var i = selectedObj.length - 1; i >= 0; i-- ) {
		var currIndex = $('#list option').index($(selectedObj[i]));
		var maxIndex = totalSize - (selectedObj.length - i);
		if (currIndex == maxIndex)
			continue;
		$(selectedObj[i]).insertAfter($('#list option:eq(' + maxIndex + ')'));
	}
	resetOptGroup();
}

function resetOptGroup() {
	$('optgroup').each(function(i){
		var optSize = $('#list option').size();
		var pagePoint = i ;
		pagePoint = pagePoint > optSize - 1 ? optSize - 1 : pagePoint;
		$(this).insertBefore($('#list option:eq(' + pagePoint + ')'));
	});
}

$(function() {
	$("#oprform").validity(function() {
		var ids = new Array();
		$('#list option').each(function(){
			ids.push($(this).val());
		});
		$('#ids').val(ids.join(','));
		$('#orderids').val(orderids.join(','));
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
<form action="${url }" method="post" id="oprform">
<input type="hidden" name="ids" id="ids" />
<input type="hidden" name="orderids" id="orderids" />
<input type="hidden" name="colid" id="colid" value="${colid}"/>

		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0"
				class="table">
				
				<tr>
				<td width="80%">
				
				<span class="select" style="height:340px;"><span class="select-wrap">
				<select id="list" multiple="multiple" style="width:340px;height:352px;">
				<c:forEach items="${channelList}" var="channel"> 
				<option value="${channel.iid}" order="${channel.orderId}">
					${channel.name}
				</option>
				</c:forEach>
				</select></span></span>
				
				</td>
				<td width="20%" align="center">
				<input type="button" class="btn btn-success" value="倒排" style="margin-bottom:10px;width: 70px;text-align:center;"  onclick="reverse();" />
				<input type="button" class="btn btn-success" value="置顶" title="置顶" style="margin-bottom:10px;width: 70px;text-align:center;" onclick="moveTop();" />
				<input type="button" class="btn btn-success" value=" ︽" title="上移10位" onclick="moveUp(10);" style="margin-bottom:10px;width: 70px;text-align:center;"/>
				<input type="button" class="btn btn-success" value=" ︿" title="上移1位" onclick="moveUp(1);" style="margin-bottom:10px;width: 70px;text-align:center;"/>
				<input type="button" class="btn btn-success" value=" ﹀" title="下移1位" onclick="moveDown(1)" style="margin-bottom:10px;width: 70px;text-align:center;"/>
				<input type="button" class="btn btn-success" value=" ︾" title="下移10位" style="margin-bottom:10px;width: 70px;text-align:center;" onclick="moveDown(10)" />
				<input type="button" class="btn btn-success" value="置底" title="置底" onclick="moveBottom()" style="margin-bottom:10px;width: 70px;text-align:center;" />
			    </td>
				</tr>
			</table>
		</div>
		<div id="dialog-toolbar">
			<div id="dialog-toolbar-panel">
				<input type="submit" class="btn btn-primary" value="保存" />  <input
					type="button" class="btn" value="取消" onclick="closeDialog();" />
			</div>
		</div>
	</form>
</body>
</html>