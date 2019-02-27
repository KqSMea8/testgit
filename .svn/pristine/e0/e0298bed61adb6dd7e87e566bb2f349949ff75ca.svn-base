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
<title>字段成员列表</title>
<h:head pagetype="dialog" grid="true" iconfont="true" select="true"></h:head>
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
				ajaxSubmit('../field_remove.do?ids='+ids+"&siteId=${siteId}", {success:function(result){
					if(result.success){ 
						location.reload();
					}else{
						alert(result.message);
					}
				}});
			});
			break;
			
		case 'add':  
			openDialog('site/field_show.do?siteId=${siteId}', 550, 380, {
				title : '字段新增'
			});
			break; 
			
		case 'order': 
			openDialog('site/sortfield_show.do?siteId=${siteId}', 550, 450, { 
				title : '字段排序'
			});
			break; 
		}
	}

	/**
	 * 编辑
	 */
	function edit(iid, name) { 
		openDialog("site/modifyfield_show.do?iid=" + iid, 550, 380, {
			title : '字段修改'
		});
	} 
</script>
<style>
.user {
	color: #226699;
}

.group {
	color: #F88E32;
}

.user i,.group i {
	margin-right: 5px;
}

html {
	overflow: inherit;
}

body {
	overflow-y: scroll;
	padding: 20px;
}
</style>
</head>
<body>
	<div class="grid-advsearch">
		<form> 
			成员名称<input name="memberName" type="text" class="input-text" value="${memberName}" style="width: 120px; margin: 0 20px 0 10px;" />
			<input type="button" class="btn btn-primary" value="检索" style="margin-right:5px;"/>
			<input type="button" class="btn advsearch-cancel" value="返回" />
			<div class="line-dotted"></div>
		</form>
	</div>
	<h:grid></h:grid>
</body>
</html> 
 