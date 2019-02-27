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
<title>用户列表</title>
<h:head pagetype="dialog" grid="true" select="true"></h:head>
<script type="text/javascript">
	function toolbarAction(action) {
		switch (action) {
		case 'remove':
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			}
			removeMembers(ids);
			break;
		case 'add':
			openDialog(contextPath + '/manager/orgselect.do?usids=${usids}&orgType=${orgType}', 800, 500, {
				title: '用户选择',
				callback : addMembers
			});
			break;
		case 'ok':
			var a = '${userIds}';
			var len = a.split(",").length;
			if(len > 200){
				alert('最多选择200个用户！');
				return false;
				break;
			}else{
				closeDialog();
				getParentWindow().f5('${userNames}','${userIds}');
				break;
			}
		}
	}
	
	/**
	 * 新增成员
	 */
	function addMembers(selectedUsers, selectedGroups, selectedRoles, usids) {
		var usersArray = new Array();
		
		$.each(selectedUsers, function(id) {
			usersArray.push(id);
		});
		
		location.href = contextPath + "/manager/userextra/users/list.do?infoId=0&usids=" + usids + "&pageSize=10&userIds=" + usersArray.join();
	}

	/**
	 * 删除成员
	 */
	function removeMembers(ids) {
		var usersArray = new Array();
		
		var idsArray = ids.split(',');
		$.each(idsArray, function(index, id) {
			usersArray.push(id);
		});
		
		location.href = "list.do?infoId=0&pageSize=10&userIds=${userIds}&removeUserIds=" + usersArray.join();
	}
	
</script>
<style>
.user {
	color: #226699;
}

.group {
	color: #F88E32;
}

.user i,.group i {
	margin-right: 5px;
}

html {
	overflow: inherit;
}

body {
	overflow-y: scroll;
	padding: 20px;
}
</style>
</head>
<body>
	<h:grid></h:grid>
</body>
</html>