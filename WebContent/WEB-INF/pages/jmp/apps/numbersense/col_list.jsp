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
<title>分类列表</title>
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
				ajaxSubmit("remove.do?ids=" + ids, {success:function(result){
					if(result.success){
						var treeObj = Tree.getInstance({domPath:top.dialog_frame.menu});
						treeObj.removeNode(result.params.remove);
						treeObj.refreshNode("c0");
						location.reload();
					}else{
						alert(result.message);
					}
				}});
			});
			break;
			
		case 'add':
			openDialog('plugins/numsense/col/add_show.do?siteId=${siteId}&colId=${colId}', 500, 350, {
				title : '分类新增'
			});
			break;
			
		case 'order': 
			openDialog('plugins/numsense/col/sort_show.do?colId=${colId}', 470, 540, {
				title : '分类排序'
			});
			break;
		}
	}

	function edit(iid, name) {
		openDialog('plugins/numsense/col/modify_show.do?iid=' + iid, 500, 350, {
			title : '分类编辑'
		});
	}
</script>
</head>
<body>
	<div id="page-title">
		<span id="page-location">分类管理</span>
	</div>
	<div id="page-content">
		<h:grid></h:grid>
	</div>
</body>
</html>