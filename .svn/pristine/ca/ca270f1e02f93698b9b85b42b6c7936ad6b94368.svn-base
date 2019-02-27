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
<title>群组列表</title>
<h:head pagetype="page" grid="true"></h:head>
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
				ajaxSubmit("${contextPath}/manager/peoplelist/remove.do?ids=" + ids, {
					success:function(result){
						if(result.success){
							location.reload();
						}else{
							alert(result.message);
						}
					}
				});
			});
			break;
		case 'add':
			openDialog('peoplelist/add_show.do', 550, 320, {
				title : '群组新增'
			});
			break;
		}
	}
	function edit(id, name) {
		openDialog('peoplelist/modify_show.do?iid=' + id, 550, 320, {
			title : '群组编辑'
		});
	}

		
	function setMembers(id) {
		openDialog('peoplelist/members/list.do?peoplelistid=' + id, 600, 570, {
			title : '群组成员设置'
		});
	}
	
</script>
</head>
<body>
	<div id="page-title">
		<span id="page-location">群组管理</span>
	</div>
	<div id="page-content">
		<h:grid></h:grid>
	</div>
</body>
</html>