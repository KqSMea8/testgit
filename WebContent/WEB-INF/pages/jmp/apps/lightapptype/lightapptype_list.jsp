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
<title>应用分类列表</title>
<h:head pagetype="page" grid="true" dialog="true"></h:head> 
<script type="text/javascript">
	function toolbarAction(action) {
		switch (action) {
		case 'add':
			openDialog('lightapptype/add_show.do?lightTypeId=${typeId}', 550, 360, {  
				title : '分类新增'
			});
			break;
			
		case 'remove':
			var ids = getCheckedIds();
			if (ids == '') {
				alert('未选中任何记录');
				return;
			}
			confirm('您确定要删除这' + ids.split(',').length + '条记录吗', function() {
				ajaxSubmit('remove.do?ids='+ids, {success:function(result){
					if(result.success){
						Tree.getInstance().removeNode(result.params.remove);
						location.reload();
					}else{
						alert(result.message);
					}
				}});
			}); 
			break;
			
		case 'order': 
			openDialog('lightapptype/sort_show.do?pid=${typeId}', 470, 540, {
				title : '应用排序'
			});
			break; 
		}
	}
	
	/**
	 * 编辑
	 */
	function edit(iid) {
		openDialog("lightapptype/modify_show.do?iid=" + iid, 550, 360, {
			title : '应用修改'
		});
	}
	
</script>
</head>
<body>
	<div id="page-title">
		 <span id="page-location">应用分类管理</span>
	</div>
	<div id="page-content">    
		<h:grid></h:grid>
	</div>
</body>
</html>