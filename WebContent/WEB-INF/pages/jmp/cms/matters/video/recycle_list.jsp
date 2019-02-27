<%@page language="java" contentType="text/html; charset=UTF-8"
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
<title>回收站列表</title>
<h:head pagetype="page" grid="true" select="true" calendar="true"></h:head>
<script type="text/javascript">
function toolbarAction(action) {
	switch (action) {
	case 'remove':
		var ids = getCheckedIds();
		if (ids == "") {
			alert("未选中任何记录");
			return;
		}
		confirm("您确定要删除这" + ids.split(",").length + "条记录吗", function() {
			ajaxSubmit("remove.do?ids=" + ids + "&siteId=${siteId}",{success:function(result){
				if(result.success){
					location.reload();
				}else{
					alert(result.message);
				}
			}});
		}); 
		break;
		
	case 'clean': 
		confirm("您确定要清除所有记录吗", function() {
			ajaxSubmit("clean.do?classId=${classId}",{success:function(result){
				if(result.success){
					location.reload();
				}else{
					alert(result.message);
				}
			}});
		});   
		break;
		
	case 'restore': 
		var ids = getCheckedIds();
		if (ids == "") {
			alert("未选中任何记录");
			return;
		} 
		confirm("您确定要还原这" + ids.split(",").length + "条记录吗", function() {
			ajaxSubmit("restore.do?ids=" + ids ,{success:function(result){
				if(result.success){
					location.reload();
				}else{
					alert(result.message);
				}
			}});
		});  
		break;
		 
	case 'back':
		url = encodeURIComponent(location.href);  
		page="${contextPath}/manager/matter/video/list.do?name=${name}&classId=${classId}";
		location.href=page;
		break;
	}
}
</script>
</head>
<body>
	<div id="page-title">
	    <span>
		回收站   / <span id="page-title-text">${name}</span> <i class="icon-caret-down"></i></span>
	</div>
	<div id="page-content">
		<h:grid></h:grid>
	</div>
</body>
</html>