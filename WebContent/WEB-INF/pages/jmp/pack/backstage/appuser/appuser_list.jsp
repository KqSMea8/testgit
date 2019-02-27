<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="h" uri="/hanweb-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>应用列表</title>
<h:head pagetype="page" grid="true"></h:head>
<script type="text/javascript">
	function toolbarAction(action) {
		switch (action) { 
		case 'add':
			openDialog("appuser/add_show.do", 550, 330, {
				title : '新增用户'
			});
			break;
		case 'remove':
			var ids = getCheckedIds();
			var names = getCheckedAttr("namejs");
			if (ids == "") {
				alert("未选中任何记录");
				return;
			}
			confirm("您确定要删除这" + ids.split(",").length + "条记录吗", function() {
				ajaxSubmit("remove.do?ids=" + ids + "&names=" + names, {success:function(result){
					if(result.success){ 
						location.reload();
					}else{
						alert(result.message);
					}
				}});
			});  
			break; 
		}
		
	}
	 
	
	function edit(iid, name) {
		openDialog("appuser/modify_show.do?iid=" + iid, 480, 300, {
			title : '修改'
		});
	} 
</script>
</head>
<body>
	<div id="page-title">
		应用管理
		<c:if test="${app.iid == 0}">
			<span id="page-location">应用管理</span>
		</c:if>
		<c:if test="${app.iid > 0 && app.name != ''}">
			应用管理  - <span id="page-location">${app}</span>
		</c:if>
		<c:if test="${app.iid<0}">
			应用管理  - <span id="page-location">检索结果</span>
		</c:if>
	</div>
	<div id="page-content">
		<h:grid></h:grid>
	</div>
</body>
</html>