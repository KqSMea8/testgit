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
<title>消息详情列表</title>
<h:head pagetype="dialog" grid="true" select="true"></h:head>
<script type="text/javascript">
	/**
	* 列表页功能
	*/
	function toolbarAction(action) {
		switch (action) {
		case 'remove':
			var ids = getCheckedIds();
			if (ids == '') {
				alert('未选中任何记录');
				return;
			}
			confirm('您确定要删除这' + ids.split(',').length + '条记录吗', function() {
				ajaxSubmit('remove.do?ids='+ids,{
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
		case 'clean':
			confirm('您确定要清空所有记录吗', function() {
				ajaxSubmit('clean.do?infoId=${infoId}',{
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
		}
	} 
	/**
	 * 查看回执
	 */
	function lookReceipt(infodetailId) {
		openDialog('infodetail/receipt.do?iid=' + infodetailId, 1050,600, {
			title : '查看回执'
		});
	}
	
	function show(userId){
		openDialog('user/show.do?iid=' + userId, 480,380, {
			title : '用户详情'
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
	<div id="page-content">
		<div class="grid-simple-search">
			<form>
				消息状态
				<select name="ustate" data-value="${ustate}" style="width: 80px; margin: 0 30px 0 10px;">
					<option value="-1">全部</option>
					<option value="0">未阅读</option>
					<option value="2">已阅读</option>
				</select>
				<%-- 用户状态
				<select name="state" data-value="${state}" style="width: 80px; margin: 0 30px 0 10px;">
					<option value="-1">全部</option>
					<option value="0">离线</option>
					<option value="1">在线</option>
				</select> --%>
			</form>
		</div>
		<h:grid></h:grid>
	</div>
</body>
</html>