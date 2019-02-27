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
<title>附件分类列表</title>
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
				ajaxSubmit("remove.do?ids=" + ids , {success:function(result){
					if(result.success){
						Tree.getInstance().removeNode(result.params.remove);
						location.reload();
					}else{
						alert(result.message);
					}
				}});
			});
			break;
			
		case 'add':
			openDialog('matter/doccol/add_show.do', 550, 320, {
				title : '分类新增'
			});
			break;
			
		case 'sort':
			openDialog('matter/doccol/sort_show.do', 550, 510, {
				title : '分类排序 '
			});
			break;
		}
	}

	// 编辑
	function edit(iid, name) {
		openDialog('matter/doccol/modify_show.do?iid=' + iid, 550, 320, {
			title : '分类编辑' 
		});
	}
</script>
</head>
<body>
	<div id="page-title"> 
		素材管理 / <span id="page-location">附件分类</span>   
	</div>
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