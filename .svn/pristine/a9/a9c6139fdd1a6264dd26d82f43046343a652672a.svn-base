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
<title>报料信息列表</title>
<h:head pagetype="page" grid="true"></h:head>
<script type="text/javascript">
	function toolbarAction(action) {
		switch (action) {
		case 'add':
			openDialog("broke/add_show.do?typeId=${typeId}", 550, 450, {
				title : '新增报料'
			});
			break;
			
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
			
		case 'audit':
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			}
			confirm("您确定要审核这" + ids.split(",").length + "条记录吗", function() {
				ajaxSubmit("audit.do?ids=" + ids, {
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
			
		case 'unaudit':
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			}
			confirm("您确定要撤审这" + ids.split(",").length + "条记录吗", function() { 
				ajaxSubmit("unaudit.do?ids=" + ids, {
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
			
		case 'import':
			openDialog('broke/import_show.do?pId=${colId}', 550, 200, {
				title : '报料导入'
			});
			break;
			
		case 'export':
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			}
			window.open("export.do?ids=" + ids + "&siteId=${siteId}");
			break;
		}
	}

	// 编辑
	function edit(iid) {
		openDialog('broke/modify_show.do?iid=' + iid, 550, 550, {
			title : '编辑'  
		});
	}

	// 审核
	function audit(id, state) {
		if (state == 0) {
			ajaxSubmit("audit.do?ids=" + id, {
				success:function(result){
					if(result.success){
						location.reload();
					}else{
						alert(result.message);
					}
				}
			});
		} else if (state == 1) {
			ajaxSubmit("unaudit.do?ids=" + id, {
				success:function(result){
					if(result.success){
						location.reload();
					}else{
						alert(result.message);
					}
				}
			});
		}
	}

	// 关闭公开 
	function isopen(iid) {
		ajaxSubmit("isopen.do?iid=" + iid, {
			success:function(result){
				if(result.success){
					location.reload();
				}else{
					alert(result.message);
				}
			}
		});
	}
	
	//评论
	function comment(iid){
		openDialog('comment/list.do?infoId=' + iid + "&type=2&siteId=${siteId}", 1020, 580, {
			title : '报料评论'
		});
	}
</script>
</head>
<body>
	<div id="page-title">
		<span id="page-location">${typeName}</span>
	</div>
	<input type="hidden" id="typeId" name="typeId" value="${typeId }">
	<div id="page-content">
		<div class="grid-advsearch">
			<form>
				名称<input name="name" type="text" class="input-text" value="${name}" style="width: 120px; margin: 0 30px 0 10px;" /> 
				标识<input name="codeId" type="text" class="input-text" value="${codeId}" style="width: 120px; margin: 0 30px 0 10px;" />
				<input type="button" class="btn btn-primary" value="检索" style="margin-right:5px;"/>
				<input type="button" class="btn advsearch-cancel" value="返回" />
				<div class="line-dotted"></div>
			</form>
		</div>
		<h:grid></h:grid>
	</div>
</body>
</html>