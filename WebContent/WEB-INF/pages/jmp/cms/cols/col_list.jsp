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
<title>栏目列表页</title>
<h:head pagetype="page" grid="true" select="true" loadmask="true"></h:head>
<!--使用${contextPath}可以获得应用路径，并无/结尾-->
<script>
	var checkids = "";
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
				ajaxSubmit('remove.do?ids='+ids, {success:function(result){
					if(result.success){
						Tree.getInstance().removeNode(result.params.remove);
						location.reload();
					}else{
						alert(result.message);
					}
				}});
			}); 
			break;
			
		case 'add':
			openDialog("col/add_show.do?pid=${colId}", 700, 650, {
				title : '新增栏目'
			});
			break;
			
		case 'open':
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			}  
			ajaxSubmit('enable_modify.do', {
				data : "ids=" + ids + "&enable=1",
				success:function(result){
					if(result.success){
						window.location.reload();
					}else{
						alert(result.message);
					}
				}
			});  
			break;
			
		case 'close':
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			} 
			confirm('您确定要停用这' + ids.split(',').length + '条记录吗', function() {
				ajaxSubmit('enable_modify.do', {
					data : "ids=" + ids + "&enable=0",
					success:function(result){
						if(result.success){
							window.location.reload();
						}else{
							alert(result.message);
						}
					}
				});   
			});	
			break;
			
		case 'import':
			openDialog('col/import_show.do?pId=${colId}', 550, 200, {
				title : '栏目导入'
			});
			break;
			
		case 'export':
			var ids = getCheckedIds();
			window.open("export.do?ids=" + ids + "&siteId=${siteId}&pid=${colId}");
			break;
			
		case 'order': 
			openDialog('col/sort_show.do?colId=${colId}&siteId=${siteId}', 550, 560, {
				title : '栏目排序'
			});
			break;
			
		case 'copy': 
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			} 
			checkids = ids;
			openDialog(contextPath + '/manager/col/qc_show.do?orgType=c', 800, 500, {
				callback : addCopyMembers
			});
			break;
			
		case 'quote': 
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			} 
			checkids = ids;
			openDialog(contextPath + '/manager/col/qc_show.do?orgType=c', 800, 500, {
				callback : addQuoteMembers
			});
			break;
		}
	}
	
	/**
	 * 编辑
	 */
	function edit(iid) {
		openDialog("col/modify_show.do?iid=" + iid, 700, 650, {
			title : '栏目修改'
		});
	}
	/**
	 * 排序
	 */
			
	function sort(iid, newlightappid){
		openDialog('lightapp/sortforcol_show.do?lightAppIds='+newlightappid+'&colId='+iid, 470, 540, {
			title : '应用排序'
		});
	}
	
	/**
	 * 启用、停用
	 */ 
	function modifyEnable(iid, enable) {
		enable = enable == "1" ? "0" : "1";  //切换当前状态
		ajaxSubmit('enable_modify.do', {
			data : "ids=" + iid + "&enable=" + enable,
			success:function(result){
				if(result.success){
					Tree.getInstance().refreshNode("0");
					window.location.reload();
				}else{
					alert(result.message);
				}
			}
		});
	}
	
	/**
	 * 信息维护
	 */
	function goInfo(iid,name){ 
		var url = encodeURIComponent(location.href);
		var listUrl = "${contextPath}/manager/info/list.do?colId="+ iid 
					+ "&colName="+ encodeURI(name)+"&fromutl="+url;
        location.href=listUrl; 
	}

	/**
	* 设置过滤规则
	*/
	function filterRule(iid){
		openDialog('col/filter_show.do?colId=' + iid, 600, 400, {
			title: '设置过滤规则'
		});
	}

	function addCopyMembers(cols) {
		$('body').mask('处理中，请稍后...');
		var targetids = "";
		$.each(cols, function(id, col) {
			targetids += (id+",");
		});
		if(targetids.length>0){
			targetids = targetids.substring(0,targetids.length-1);
		}
		ajaxSubmit('qc_submit.do', {
			data : "ids=" + checkids + "&targetIds=" + targetids + "&flag=" + "C",
			success:function(result){
				if(result.success){
					$('body').unmask();
					Tree.getInstance().refreshNode("0");
					window.location.reload();
				}else{
					$('body').unmask();
					alert(result.message);
				}
			}
		});
	}

	function addQuoteMembers(cols) {
		$('body').mask('处理中，请稍后...');
		var targetids = "";
		$.each(cols, function(id, col) {
			targetids += (id+",");
		});
		if(targetids.length>0){
			targetids = targetids.substring(0,targetids.length-1);
		}
		ajaxSubmit('qc_submit.do', {
			data : "ids=" + checkids + "&targetIds=" + targetids + "&flag=" + "Q",
			success:function(result){
				if(result.success){
					$('body').unmask();
					Tree.getInstance().refreshNode("0");
					window.location.reload();
				}else{
					$('body').unmask();
					alert(result.message);
				}
			}
		});
	}

	function removeMembers() {
		$('.selected').animate({
			'margin-left' : '-=100',
			opacity : '0.1'
		}, 'fast', function() {
			$(this).css('visibility', 'hidden').slideUp('fast', function() {
				$(this).remove();
			});
		});
	}
</script>
</head>
<body>
    <div id="page-title">
		栏目管理 / 
		<c:if test="${colName!='栏目分类'}">
			<span id="page-location">${colName}</span>
		</c:if> 
	</div>
	 
	<div id="page-content">
		<div class="grid-advsearch">
			<form>
				栏目名称<input name="colText" type="text" class="input-text" value="${colText}" style="width:220px;margin:0 10px 0 10px;"/>
				状态<select name="colState" data-value="${colState}" style="width:120px">
						<option value="-1">请选择状态</option>
						<option value="0">停用</option>
						<option value="1">启用</option> 
					</select> 
			         类型<select name="colType" data-value="${colType}" style="width:120px">
						<option value="-1">请选择状态</option>
						<option value="1">类目</option>
						<option value="2">信息列表</option>
						<option value="3">互动栏目</option>
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