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
<title>开放接口</title>
<h:head pagetype="page" grid="true" toggle="true"></h:head>
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
				ajaxSubmit("remove.do?ids=" + ids, {
					success : function(result) {
						if (result.success) {
							location.reload();
						} else {
							alert(result.message);
						}
					}
				});
			});
			break;
		case 'add':
			openDialog('interface/add_show.do?typeId=${typeId }', 1100, 500, {
				title : '接口新增'
			});
			break;
		}
	}
	function edit(iid, name) {
		openDialog('interface/modify_show.do?iid=' + iid, 1100, 500, {
			title : '接口编辑',
			collapsible:true,
			maximizable:true,
			resizable:true
		});
	}

	function examine(iid){
		openDialog('interface/show_examine.do?iid='+iid, 800, 600, {
			title : '接口请求',
			collapsible:true,
			maximizable:true,
			resizable:true
			
		});
	}

	
	

	$(function() {
		$(':hidden[name=enable]').toggle({
			size : 'small',
			ajax : {
				url : 'enable_modify.do',
				error : function(result) {
					if (result && result.message) {
						alert(result.message, 'warning');
					} else {
						alert('您已退出系统，请重新登录', 'warning');
					}
				}
			}
		});
	});
</script>
</head>
<body>
	<input type="hidden" name="typeId" id="typeId" value="${typeId }" />
	<div id="page-title">
		接口管理
	</div>
	
	
	<div id="page-content">

		<h:grid></h:grid>
	</div>
</body>
</html>