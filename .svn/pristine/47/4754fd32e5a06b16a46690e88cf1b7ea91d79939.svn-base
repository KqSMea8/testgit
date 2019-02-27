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
<title>导航管理</title>
<h:head pagetype="page" grid="true" upload="true"></h:head>
<script type="text/javascript">
	function toolbarAction(action) {
		switch (action) {
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
		case 'add':
			openDialog('channel/add_show.do', 600, 500, { 
				title : '导航新增'
			});
			break;
		case 'order': 
			openDialog('channel/sort_show.do', 470, 540, { 
				title : '导航排序'
			});
			break; 
		}
	}
	
	function edit(iid, name) {
		openDialog('channel/modify_show.do?iid=' + iid, 600, 500, {
			title : '导航编辑' 
		});
	}
	
	function exportSite(id, name){
		confirm("您确定要导出网站【" + name + "】吗", function() {
			window.open("site/export.do?id=" + id + "&name=" + name);
		});
	}

	function modifyEnable(iid, enable) {
		enable = enable == "1" ? "0" : "1";  //切换当前用户状态
		$.ajax({
			type : "POST",
			url : "enable_modify.do",
			data : "iid=" + iid + "&enable=" + enable,
			success : function(msg) {
				if ($.trim(msg) == "")
					window.location.reload();
				else
					alert(msg);
			}
		});
	}

	function deleteSite(id, name){
		confirm("您确定要删除网站【" + name + "】吗", function() {
			ajaxSubmit("remove.do?id=" + id);
		});
	}

	function sort(channelId){
		openDialog('channel/sortCol_show.do?channelId=' + channelId, 470, 540, {
			title : '栏目排序'  
		});
	}
	
	/**
	 * 启用、停用
	 */ 
	function modifyEnable(iid, enable) {
		enable = enable == "1" ? "0" : "1";  //切换当前状态
		ajaxSubmit('enable_modify.do', {
			data : "ids=" + iid + "&enable=" + enable,
			success:function(result){
				if(result.success){
					window.location.reload();
				}else{
					alert(result.message);
				}
			}
		});
	}
</script>
</head>
<body>
    <div id="page-title">
		功能管理 / <span id="page-location">导航管理</span>
	</div> 
	<div id="page-content">
		<h:grid></h:grid>
	</div>
</body>
</html>