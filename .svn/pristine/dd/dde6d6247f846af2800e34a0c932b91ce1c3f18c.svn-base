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
<title>推送日志列表</title>
<h:head pagetype="page" grid="true" calendar="true"></h:head>
<script type="text/javascript">
	function toolbarAction(action) {
		switch (action) {
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
			
		case 'clean':
			confirm("将要清空所有网站下推送日志信息,是否继续？", function() { 
				ajaxSubmit("clean.do", {success:function(result){
					if(result.success){ 
						location.reload();
					}else{
						alert(result.message);
					}
				}});
			});  
			break;
			
		case 'chart':
			localpage = encodeURIComponent(location.href);  
			page="chart.do?" + "localpage="+localpage;
			location.href=page;
			break;
		}
	}
</script>
</head>
<body>
	<div id="page-title">
		日志管理 /<span id="page-location">推送日志</span>
	</div>
	<div id="page-content">
				<div class="grid-advsearch">
			<form>
				推送时间&nbsp;&nbsp;
				<input id="starttime" name="starttime" type="text" class="jcalendar input-text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endtime\')}'});" value="${starttime }" style="width:175px"/> 
				&nbsp;至&nbsp;
				<input id="endtime" name="endtime" type="text" class="jcalendar input-text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'starttime\')}'});" value="${endtime }" style="width:175px"/>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn btn-primary" value="检索" style="margin-right:5px;"/>
				<input type="button" class="btn advsearch-cancel" value="返回" />
				<div class="line-dotted"></div>
			</form>
		</div>
		<h:grid></h:grid>
	</div>
</body>
</html>