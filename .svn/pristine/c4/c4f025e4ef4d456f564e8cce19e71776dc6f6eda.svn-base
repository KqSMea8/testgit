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
<title>应用列表</title>
<h:head pagetype="page" grid="true"></h:head> 
<script type="text/javascript">
	function toolbarAction(action) {
		switch (action) {
		case 'add':
			openDialog('application/add_show.do?', 550, 400, {  
				title : '应用新增'
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
						location.reload();
					}else{
						alert(result.message);
					}
				}});
			}); 
			break;
		case 'order': 
			openDialog('application/sort_show.do?', 470, 540, {
				title : '应用排序'
			});
			break; 
		}
	}
	
	/**
	 * 编辑
	 */
	function edit(iid) {
		openDialog("application/modify_show.do?iid=" + iid, 550, 400, {
			title : '应用修改'
		});
	}
	
	</script>
</head>
<body>
	<div id="page-title">
		 <span id="page-location">应用管理/</span>
	</div>
	<div id="page-content">    
		<h:grid></h:grid>
	</div>
</body>
</html>