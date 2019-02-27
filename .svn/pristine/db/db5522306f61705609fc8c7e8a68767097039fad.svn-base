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
<title>站点列表</title>
<h:head pagetype="page" grid="true"></h:head> 
<script type="text/javascript">
	function toolbarAction(action) {
		switch (action) {
		case 'add':
			openDialog('site/add_show.do', 650, 650, {  
				title : '站点新增'
			});
			break;
			
		case 'import': 
			openDialog('site/import_show.do', 550, 200, {
				title : '站点导入'
			});
			break;
		}
	}
	
	function edit(iid, name) {
        ajaxSubmit("checkAdmin.do?siteId=" + iid, {success:function(result){
			if(result.success){
				openDialog('site/modify_show.do?iid=' + iid, 650, 650, {
					title : '站点编辑' 
				});
			}else{
				alert(result.message);
				}
			}});
	}
	
	function exportSite(id, name){
		confirm("您确定要导出站点【" + name + "】吗", function() {
			iframeSubmit("export.do?id=" + id + "&name=" + encodeURI(name));
		});
	}

	function deleteSite(id, name){  
		confirm("您确定要删除站点【" + name + "】吗", function() {
			ajaxSubmit("remove.do?id=" + id, {success:function(result){
				if(result.success){ 
					location.reload();
				}else{
					alert(result.message);
				}
			}});
		}); 
	}
	
	function book(id){
		openDialog('site/book_show.do?siteId=' + id, 550, 560, {
			title : '站点订阅'  
		});
	}
	
	function adminLogin(siteId){
		var cookiePath = '${contextPath}/';
		$.cookie('channel', null, {path: cookiePath});
		$.cookie('pageUrl', null, {path: cookiePath});
		$.cookie('menuUrl', null, {path: cookiePath}); 
        ajaxSubmit("adminLogin.do?siteId=" + siteId, {success:function(result){
			if(result.success){
				top.location.href = "../index.do";
			}else{
				alert(result.message);
				}
			}});
		}  
	
	function copyUrl(url){
		if(navigator.userAgent.toLowerCase().indexOf('ie') > -1) {    
	    	window.clipboardData.setData("Text", url); 
	        alert ("该地址已经复制到剪切板");    
	    } else {    
	        prompt("请复制接口地址:", url);    
	    } 
	}

	function showLevel(iid) {
		openDialog("site/sitelevel_show.do?siteId="+iid, 850, 660, {
			title : '等级设置'
		});
	}

	function showField(iid) {
		openDialog("site/field/list.do?siteId="+iid+"&pageSize=10", 850, 580, {
			title : '字段管理'
		});
	}

	function showColField(iid) {
		openDialog("site/colfield/list.do?siteId="+iid+"&pageSize=10", 850, 580, {
			title : '展现类型扩展'
		});
	}
	
	</script>
</head>
<body>
	<div id="page-title">
		<span id="page-location"> 站点管理/</span>
	</div>
	<div id="page-content">    
		<h:grid></h:grid>
	</div>
</body>
</html>