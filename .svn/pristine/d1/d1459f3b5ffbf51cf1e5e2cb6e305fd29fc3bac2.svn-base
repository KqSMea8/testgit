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
<title>图片信息列表</title>
<h:head pagetype="page" grid="true"></h:head>
<script type="text/javascript">
	function toolbarAction(action) {
		switch (action) {
		case 'add':
			openDialog("matter/picture/add_show.do?classId=${classId}", 980, 600, {
				title : '新增图片'
			});
			break;
			
		case 'recycle':
			url = encodeURIComponent(location.href);  
			page="${contextPath}/manager/matter/picture/recycle/list.do?classId=${classId}&name=${name}";
			location.href=page;
			break;
			
		case 'remove':
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			} 
			openDialog("matter/picture/remove_show.do?ids=" + ids+"&idlength="+ids.split(",").length, 420, 270, {
				title : '图片删除'
			});
			break; 
		}
	}

	// 编辑
	function edit(iid) {
		openDialog('matter/picture/modify_show.do?iid=' + iid, 550, 250, {
			title : '图片编辑'  
		});
	}
	
	//预览
	function previewModel(formData){ 
		var features = '';
		var newwin=window.open(formData,"preview",features);
		newwin.moveTo(0,0);
		newwin.resizeTo(screen.width,screen.height-30);
		newwin.focus();
	}
</script>
</head>
<body>
	<div id="page-title">
	          图片分类 /  <span id="page-location">${name }</span>
	</div>
	<input type="hidden" id="classId" name="classId" value="${classId }">
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