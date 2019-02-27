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
<title>地区管理</title>
<h:head pagetype="page" grid="true" loadmask="true" select="true"></h:head>
<script type="text/javascript"> 
	function toolbarAction(action) {
		switch (action) {
		case 'start':
			var ids = getCheckedIds();
			if (ids == "") { 
				alert("未选中任何记录");
				return;
			} 
			confirm("您确定要启用这" + ids.split(",").length + "条记录吗", function() {
				ajaxSubmit("start.do?ids=" + ids,{success:function(result){
					if(result.success){
						location.reload();
					}else{
						alert(result.message);
					}
				}});
			});  
			break;
			
		case 'stop':
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			} 
			confirm("您确定要停用这" + ids.split(",").length + "条记录吗", function() {
				ajaxSubmit("stop.do?ids=" + ids,{success:function(result){
					if(result.success){
						location.reload();
					}else{
						alert(result.message);
					}
				}});
			});   
			break;
			
		case 'init':
			confirm("您确定要初始化数据吗", function() {
				$('html').mask('初始化数据中，请稍后...');
				ajaxSubmit("initarea.do",{success:function(result){
					$('html').unmask();
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

	function areaLook(iid){
		var formData = "./weather_show.do?iid="+iid;
		var features = '';
		var newwin=window.open(formData,"preview",features);
		newwin.moveTo(0,0);
		newwin.resizeTo(screen.width,screen.height-30);
		newwin.focus();
	}

	function baiduLook(iid){
		var formData = "./baidu_show.do?iid="+iid;
		var features = '';
		var newwin=window.open(formData,"preview",features);
		newwin.moveTo(0,0);
		newwin.resizeTo(screen.width,screen.height-30);
		newwin.focus();
	}
	
	function syninfoWeather(id, name){  
		confirm("您确定要更新"+name+"的天气数据吗", function() {
			$('html').mask('天气预报数据更新中，请稍后...');
			ajaxSubmit("modifyweater_submit.do?iid="+id,{success:function(result){
				$('html').unmask();
				if(result.success){
					location.reload();
				}else{
					alert(result.message);
				}
			}});
		});    
	}
</script>
</head>
<body>
    <div id="page-title">
		地区管理 / 
		<c:if test="${provcn!='地区管理'}">
			<span id="page-location">${provcn}</span>
		</c:if>
	</div>
	<div id="page-content">
		<div class="grid-advsearch">
			<form>
				地区名称<input name="areaname" type="text" class="input-text" value="${areaname}" style="width:220px;margin:0 30px 0 10px;"/>
				地区状态<select name="areastate" data-value="${areastate}" style="width:120px">
						<option value="-1">请选择状态</option>
						<option value="0">未启用</option>
						<option value="1">已启用</option>
					 </select>  
				<input type="button" class="btn btn-primary" value="检索" style="margin-right:5px;" />
				<input type="button" class="btn advsearch-cancel" value="返回" />
				<div class="line-dotted"></div>
			</form>
		</div>
		<h:grid></h:grid>
	</div>
</body>
</html>