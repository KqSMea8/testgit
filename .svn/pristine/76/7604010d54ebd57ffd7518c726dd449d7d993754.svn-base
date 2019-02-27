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
<title>接口分类列表</title>
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

			if (ids.split(",").length > 1) {
				alert("只能删除一条数据");
				return;
			}

			confirm("您确定要删除这" + ids.split(",").length + "条记录吗", function() {
				ajaxSubmit("remove.do?ids=" + ids,
						{
							success : function(result) {
								if (result.success) {
									Tree.getInstance().removeNode(
											result.params.remove);
									location.reload();
								} else {
									alert(result.message);
								}
							}
						});
			});
			break;
		case 'add':
			openDialog('interfaceType/add_show.do?pid=${typeId}', 500, 300, {
				title : '接口分类新增'
			});
			break;

		}
	}
	function edit(iid, name) {
		openDialog('interfaceType/modify_show.do?iid=' + iid, 550, 300, {
			title : '接口分类编辑'
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
	<div id="page-title">
	接口分类
		<c:if test="${typeId > 0 && typeId != ''}">
			/&nbsp;<span id="page-location">${typeName}</span>
		</c:if>
		<c:if test="${typeId<0}">
			/&nbsp; <span id="page-location">检索结果</span>
		</c:if>
	
	</div>
	
	<div id="page-content">

		<h:grid></h:grid>
	</div>
</body>
</html>