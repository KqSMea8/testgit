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
<title>酷图列表</title>
<h:head pagetype="page" grid="true" select="true" calendar="true"></h:head>
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
				ajaxSubmit("remove.do?ids=" + ids + "&siteId=${siteId}",{success:function(result){
					if(result.success){
						location.reload();
					}else{
						alert(result.message);
					}
				}});
			}); 
			break;
			
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
			
		case 'add': 
			openDialog("pic/add_show.do?infoId=${infoId}&siteId=${siteId}&colId=${colId}", 750, 450, {
				title : '新增图片'
			});
			break;

		case 'order': 
			openDialog('pic/sort_show.do?infoId=${infoId}&siteId=${siteId}&colId=${colId}', 470, 540, {
				title : '组图排序'
			});
			break; 
				 
		case 'clean': 
			confirm("您确定要清除该栏目下面的记录吗", function() {
				ajaxSubmit("clean.do?colId=${colId}",{success:function(result){
					if(result.success){
						location.reload();
					}else{
						alert(result.message);
					}
				}});
			});   
			break; 
			
		case 'back': 
            location.href="${fromutl}";
			break; 
		} 
	}
	
	function edit(iid) {
		openDialog('pic/modify_show.do?iid=' + iid, 550, 400, {
			title : '组图编辑'
		});
	}
	
	function pushToTask(iid){
		openDialog('info/push_show.do?infoId=' + iid + "&colId=${colId}&siteId=${siteId}", 550, 300, {
			title : '信息推送'
		});
	}

	$.fn.preview = function(id){ 
		var xOffset = 10;
		var yOffset = 20;
		var w = $(window).width();
		var h = $(window).height();
		if(id == ''){
			return;
		} 
		var imgurl = "${contextPath}/"+$("#"+id).val();   
		$(this).hover(function(e){
			var myDate=new Date();
			var img = imgurl + "?" + myDate.getTime(); 
			$("#preview").remove();
			$("body").append("<div id='preview'><div><img id='img_' src='"+img+"' onload='$(this).resize();'/></div></div>");
			$("#preview").css({
				position:"absolute",
				padding:"4px",
				border:"1px solid #f3f3f3",
				backgroundColor:"#eeeeee",
				top:(e.pageY - yOffset) + "px",
				zIndex:999
			});
			$("#preview > div").css({
				padding:"5px",
				backgroundColor:"white",
				border:"1px solid #cccccc",
				textAlign:"center"
			});
			$("#preview > div > p").css({
				textAlign:"center",
				fontSize:"12px",
				padding:"8px 0 3px",
				margin:"0"
			});
			$("#preview").css({
				left: e.pageX + xOffset + "px",
				right: "auto"
			}).fadeIn("fast");
			if(e.pageY>h/2){
				$("#preview").css({
					top: e.pageY - yOffset -$("#img").height()+ "px"
				}).fadeIn("fast");
			}else{
				$("#preview").css({
					top: e.pageY + yOffset + "px"
				}).fadeIn("fast");
			}
		},function(){
			$("#preview").remove();
		}).mouseout(function(){
			$("#preview").remove();
		}).mousemove(function(e){
			if(e.pageY>h/2){
				$("#preview").css({
					top: e.pageY - yOffset -$("#img").height()+ "px"
				}).fadeIn("fast");
			}
			if(e.pageY>w/2){
				$("#preview").css({
					top: e.pageX - xOffset -$("#img").width()+ "px"
				}).fadeIn("fast");
			}
			$("#preview").css("left",(e.pageX + yOffset) + "px").css("right","auto");
		});						  
	};
	
	$.fn.resize=function(){
		$img = $("#img_");
		imgw = $img.width();
		imgh = $img.height();
		if(imgw*1>200){
			imgh = imgh/imgw*200;
			imgw=200;
		}
		if(imgh*1>160){
			imgw = imgw/imgh*160;
			imgh=160;
		}
		$img.css("width",imgw+"px");
		$img.css("height",imgh+"px");
	};
	
	$(function(){ 
	       //信息内容展现方式
			$("[id^='button_']").each(function(){ 
				$("#preview").remove(); 
				var id=$(this).attr("id");
				id=id.split("_")[1]; 
				$(this).preview("filetype_"+id);
			});

	});
</script>
</head>
<body>
    <div id="page-title">
		信息管理 / <span id="page-location">${infoName}</span>
	</div> 
	<div id="page-content">    
		<h:grid></h:grid>
	</div>
</body>
</html>