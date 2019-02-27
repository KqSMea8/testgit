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
<title>号码列表</title>
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
						location.reload();
					}else{
						alert(result.message);
					}
				}});
			});
			break;
			
		case 'add':
			openDialog('plugins/numsense/phone/add_show.do?siteId=${siteId}&colId=${colId}', 600, 550, {
				title : '号码新增'
			});
			break;
			
		case 'order': 
			openDialog('plugins/numsense/phone/sort_show.do?colId=${colId}', 470, 540, {
				title : '号码排序'
			});
			break;
		}
	}

	function edit(iid, name) {
		openDialog('plugins/numsense/phone/modify_show.do?iid=' + iid, 600, 550, {
			title : '号码编辑'
		});
	}
</script>
</head>
<body>
	<div id="page-title">
		 <span id="page-location">号码管理</span>
	</div>
	<div id="page-content">
		<h:grid></h:grid>
	</div>
</body>
</html>