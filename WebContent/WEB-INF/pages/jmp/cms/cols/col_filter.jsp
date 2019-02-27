<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="h" uri="/hanweb-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置过滤规则</title>
<h:head pagetype="dialog" validity="true" iconfont="true" tabs="true"></h:head>
<script>
	$(function() {
		$("#oprform").validity(
			function() {},
			{
				success:function(result){
					if(result.success){
						closeDialog(true);
					}else{
						alert(result.message);
					}
				}
			}
		);
	});

</script>
</head>
<style>
	.rightspan {
		width:120px;  
		float:left; 
		padding:5px 20px;
		overflow:hidden;
		white-space:nowrap;
		text-overflow: ellipsis;
	}
</style>
<body>
	<form action="${url }"  method="post" id="oprform" name="oprform">
		<input type="hidden" name="colId" id="colId" value="${colId }" />
		<div id="dialog-content" style="padding: 20px 20px 0 20px; overflow: hidden;">
			<div id="tabs" class="easyui-tabs" style="height: 335px;">  
				<div title="Html标签" style="padding: 30px 20px 20px 20px;">
					<c:forEach items="${mapList }" var="map">
						<span class="rightspan" style="width: 100px; padding: 5px 0px 5px 0px;" title="${map.name }" >
							<input type="checkbox" name="names" class="checkbox" 
								<c:if test="${fn:contains(selectFilter, map.name) }">checked="checked"</c:if>
								value="${map.name }" />${map.name }
						</span>
					</c:forEach>
				</div>
			</div>
		</div>
		<div id="dialog-toolbar">
			<div id="dialog-toolbar-panel">
				<input type="submit" class="btn btn-primary" value="保存"/> 
				<input type="button" class="btn" value="取消" onclick="closeDialog();" />
			</div>
		</div>
	</form>
</body>
</html>