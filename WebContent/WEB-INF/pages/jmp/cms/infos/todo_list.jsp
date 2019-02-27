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
<title>待审核列表</title>
<h:head pagetype="page" grid="true" select="true" calendar="true"></h:head>
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
				ajaxSubmit("../info/audit.do?ids=" + ids + "&siteId=${siteId}",{success:function(result){
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
				ajaxSubmit("../info/unaudit.do?ids=" + ids + "&siteId=${siteId}",{success:function(result){
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

	function look(id) {
		var localpage = encodeURIComponent(location.href);  
	    page="../info/modify_show.do?iid=" +id+"&localpage="+localpage+"&onlylook=1"; 
		location.href=page; 
	}
</script>
</head>
<body>
     <div id="page-title">
		待审核信息 / <span id="page-location">${colName}</span>
	</div> 
	<div id="page-content">
		<div class="grid-advsearch">
			<form>
				标题<input name="infoTitle" type="text" class="input-text" value="${infoTitle}" style="width:220px;margin:0 30px 0 10px;"/> 
			          创建时间从<input id="starttime" name="starttime" type="text" class="jcalendar input-text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endtime\')}'});" value="${starttime }" style="width:120px"/> 
						至<input id="endtime" name="endtime" type="text" class="jcalendar input-text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'starttime\')}'});" value="${endtime }" style="width:120px"/>
				<input type="button" class="btn btn-small btn-primary" value="检索" style="margin-right:5px;" />
				<input type="button" class="btn btn-small advsearch-cancel" value="返回" />
				<div class="line-dotted"></div>
			</form>
		</div>
		<h:grid></h:grid>
	</div>
</body>
</html>