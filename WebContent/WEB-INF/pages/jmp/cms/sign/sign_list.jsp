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
<title>角标列表</title>
<h:head pagetype="page" grid="true"></h:head> 
<script type="text/javascript">
	function toolbarAction(action) {
		switch (action) {
		case 'add':
			openDialog('sign/add_show.do?mid=${mid}&colId=${colId}', 550, 300, {  
				title : '角标新增'
			});
			break;
			
		case 'remove':
			var ids = getCheckedIds();
			if (ids == '') {
				alert('未选中任何记录');
				return;
			}
			confirm('您确定要删除这' + ids.split(',').length + '条记录吗', function() {
				ajaxSubmit('remove.do?ids='+ids+"&mid=${mid}", {success:function(result){
					if(result.success){
						location.reload();
					}else{
						alert(result.message);
					}
				}});
			}); 
			break;
			
		case 'order': 
			openDialog('sign/sort_show.do?mid=${mid}&colId=${colId}', 470, 540, {
				title : '角标排序'
			});
			break; 
		}
	}
	
	/**
	 * 编辑
	 */
	function edit(iid) {
		openDialog("sign/modify_show.do?iid=" + iid, 550, 340, {
			title : '角标修改'
		});
	}
	
	function goDetail(iid, dname, mid) {
		if(mid == 1){
			openDialog("sign/infolist.do?did=" + iid, 1200, 500, {
			});
		}else if(mid == 2){
			openDialog("sign/subscribecol/list.do?did=" + iid, 1200, 500, {
			});
		}else if(mid == 3){
			openDialog("sign/taginfolist.do?did=" + iid, 1200, 500, {
			});
		}
	}
</script>
</head>
<body>
	<div id="page-title">
		 <span id="page-location">角标定义</span>
	</div>
	<div id="page-content">    
		<h:grid></h:grid>
	</div>
</body>
</html>