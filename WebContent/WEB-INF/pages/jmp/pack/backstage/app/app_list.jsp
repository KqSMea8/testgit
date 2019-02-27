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
<title>应用列表</title>
<h:head pagetype="page" grid="true"></h:head>
<script type="text/javascript">
	function toolbarAction(action) {
		switch (action) {
		case 'audit':
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			} 

			confirm("您确定要审核这" + ids.split(",").length + "条记录吗", function() {
				ajaxSubmit("audit.do?ids=" + ids + "&siteId=${siteId}",{success:function(result){
					if(result.success){
						location.reload();
					}else{
						alert(result.message);
					}
				}});
			});  
			break;
		case 'unaudit':
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			} 
			confirm("您确定要撤审这" + ids.split(",").length + "条记录吗", function() {
				ajaxSubmit("unaudit.do?ids=" + ids + "&siteId=${siteId}",{success:function(result){
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
	function getQrcodepath(iid){
	     openDialog('application/qrc_opr.do?iid=' +iid, 400, 400, {
			title : '二维码查看'
		 });
	}

	function send(iid){
		openDialog('app/send.do?iid=' + iid,600, 550, {title : '查看发送Json'});
	}

	function back(iid){
		openDialog('app/back.do?iid=' + iid,600, 550, {title : '查看返回Json'});
	}

	function callback(iid){
		openDialog('app/callback.do?iid=' + iid,600, 550, {title : '查看回调Json'});
	}	 
	
	function edit(iid, name) {
		var localpage = encodeURIComponent(location.href);  
	    page="modify_show.do?iid="+iid+"&name"+name+"&localpage="+localpage; 
		location.href=page; 
	}

	function restart(iid){
		ajaxSubmit("restart.do?iid=" + iid, {success:function(result){
			if(result.success){
				alert(result.message);
			}else{
				alert(result.message);
			}
		}});
	}
	
</script>
</head>
<body>
	<div id="page-title">
		应用管理
		<c:if test="${app.iid == 0}">
			<span id="page-location">应用管理</span>
		</c:if>
		<c:if test="${app.iid > 0 && app.name != ''}">
			应用管理  - <span id="page-location">${app}</span>
		</c:if>
		<c:if test="${app.iid<0}">
			应用管理  - <span id="page-location">检索结果</span>
		</c:if>
	</div>
	<div id="page-content">
		<h:grid></h:grid>
	</div>
</body>
</html>