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
<title>同步字段列表</title>
<h:head pagetype="dialog" grid="true" ></h:head>
<script type="text/javascript">
	function toolbarAction(action) {
		switch (action) {
		case 'add':
			var ditchId = $("#ditchid").val();
			openDialog('synfield/add_show.do?ditchId=' + ditchId , 600, 320, {
				title : '新增字段' 
			});
			break;
		case 'remove':
			var ditchId = $("#ditchid").val();
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			}
			confirm("您确定要删除这" + ids.split(",").length + "条记录吗", function() {
				ajaxSubmit("remove.do?ids=" + ids + "&ditchId=" + ditchId, {success:function(result){
					if(result.success){ 
						location.reload();
					}else{
						alert(result.message);
					}
				}});
			});  
			break;
		case 'order':
			var ditchId = $("#ditchid").val(); 
			openDialog('synfield/sort_show.do?ditchId=' + ditchId, 470, 540, {
				title : '字段排序'
			});
			break; 
		}
	}

	// 编辑页 
	function edit(iid) {
		var ditchId = $("#ditchid").val();
		openDialog('synfield/modify_show.do?iid=' + iid + "&ditchId=" + ditchId, 600, 320, {
			title : '同步字段修改'  
		});
	}		
</script>
<style>
	body {
		overflow-y: scroll;
		padding: 20px;
	}
</style>
</head>
<body>
	<input type="hidden" name="ditchid" id="ditchid" value="${ditchId }"></input>
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
</body>
</html>