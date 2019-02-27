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
<title>渠道列表</title>
<h:head pagetype="page" grid="true" toggle="true"></h:head>
<script type="text/javascript">
	function toolbarAction(action) {
		switch (action) {
		case 'add':
			openDialog('ditch/add_show.do', 600, 320, {
				title : '新增渠道' 
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
		}
	}

	// 编辑
	function edit(iid) {
		openDialog('ditch/modify_show.do?iid=' + iid, 600, 320, {
			title : '渠道修改'  
		});
	}

	//同步字段列表页
	function synField(iid) {
		openDialog('synfield/list.do?ditchId=' + iid + "&pageSize=10", 850, 525, {
			title : '同步字段列表'  
		});
	}

	$(function() {
		$(':hidden[name=enable]').toggle({
			size: 'small',
			ajax: {
				url: 'modifyenable_submit.do',
				error: function(result) {
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
		<span id="page-location">渠道列表</span>
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