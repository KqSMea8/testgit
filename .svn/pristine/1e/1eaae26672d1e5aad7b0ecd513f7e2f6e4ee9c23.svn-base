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
<title>角标信息列表</title>
<h:head pagetype="page" grid="true"></h:head> 
<script type="text/javascript">
	function toolbarAction(action) {
		switch (action) {
		case 'remove':
			var ids = getCheckedIds();
			if (ids == '') {
				alert('未选中任何记录');
				return;
			}
			confirm('您确定要删除这' + ids.split(',').length + '条记录吗', function() {
				ajaxSubmit('removeinfo.do?ids='+ids, {success:function(result){
					if(result.success){
						location.reload();
					}else{
						alert(result.message);
					}
				}});
			}); 
			break;
		}
	}
</script>
</head>
<body>
	 <div id="page-title"> 
		 <span id="page-location">角标信息管理</span> 
	</div>
	<div id="page-content">    
		<h:grid></h:grid>
	</div>
</body>
</html>