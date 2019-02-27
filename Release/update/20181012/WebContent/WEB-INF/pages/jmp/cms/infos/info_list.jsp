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
<title>信息列表</title>
<h:head pagetype="page" grid="true" select="true" calendar="true" loadmask="true"></h:head> 
<script type="text/javascript">
	var checkids = "";
	function toolbarAction(action) {
		switch (action) {
		case 'remove':
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			} 

			openDialog("info/remove_show.do?ids=" + ids+"&idlength="+ids.split(",").length, 420, 290, {
				title : '信息删除'
			});
			
			break;
		
		case 'add': 
			localpage = encodeURIComponent(location.href);  
			page="add_show.do?" + "localpage="+localpage+"&colId=${colId}";
			location.href=page;
			break;
			 
		case 'clean': 
			openDialog("info/clean_show.do?colId=${colId}", 420, 290, {
				title : '信息清空'
			});
			break;
			
		case 'order': 
			openDialog('info/sort_show.do?colId=${colId}', 470, 540, {
				title : '信息排序'
			});
			break; 
			
		case 'syninfo': 
			openDialog('info/addsyn_show.do?colId=${colId}', 650, 260, {
				title : '信息同步'
			});
			break; 
			
		case 'settop':
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			} 
			confirm("您确定要置顶这" + ids.split(",").length + "条记录吗", function() {
				openDialog('info/addtop_show.do?infoids='+ids+"&colId=${colId}", 650, 260, {
					title : '信息置顶'
				});
			}); 
			break;
			
		case 'untop':
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			} 
			confirm("您确定要取消置顶这" + ids.split(",").length + "条记录吗", function() {
				ajaxSubmit("untop.do?ids=" + ids + "&siteId=${siteId}&colId=${colId}",{success:function(result){
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
			 
		case 'recycle':
			url = encodeURIComponent(location.href);  
			page="${contextPath}/manager/recycle/list.do?colId=${colId}"+"&fromurl="+url;
			location.href=page;
			break;
			
		//信息引用	
		case 'quote': 
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			}
			checkids = ids;
			openDialog('info/qc_show.do?pid=${colId}&ids='+ids, 800, 500, {
				callback : addQuoteMembers
			});
			break;
			
		//信息复制
		case 'copy': 
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			} 
			checkids = ids;
			openDialog('info/qc_show.do?pid=${colId}&ids='+ids, 800, 500, {
				callback : addCopyMembers
			});
			break;
			
		//信息转移	
		case 'transfer': 
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			}
			checkids = ids;
			openDialog('info/transfer_show.do?colId=${colId}&ids='+ids, 400, 380, {
				title : '信息转移'
			});
			break;
			
		case 'weixin':
			var weixinSupport = '${wxSupport}';
			if(weixinSupport==0){
				alert("平台不支持微信、微博分享");
				return;
			}
			var ids = getCheckedIds();
			var gird=Grid.getInstance();
			//var statuslist=getCheckedAttrText("status"); 
			if (ids == "") {
				alert("未选中任何记录");
				return;
			}
			//else if(statuslist.indexOf("撤审")>-1 || statuslist.indexOf("未审")>-1){
				//alert("您选中的信息中包含已撤审或者未审核信息，您只能分享已审核信息");
				//return;
			//}
			else{
				var str = new Array();
				str = ids.split(",");
				var json = "[";
				for(var i=0; i<str.length; i++){
				json +='{"webId":"'+${siteId}+'","colId":"'+${colId}+'","articleId":"'+str[i]+'"},';
				}
				json = json.substring(0, json.length-1);
				json += "]";
				
			} 
			var loginName = '${loginName}';
			var serviceCode = '${serviceCode}';
			var sessionId = '${sessionId}';
			if(loginName == null){
				loginName = "";
			}
			if(serviceCode == null){
				serviceCode = "";
			}
			if(sessionId == null){
				sessionId  = "";
			}
			var url = "${weixinUrl}?loginName=" + loginName
			 + "&serviceCode=${serviceCode}&sessionId=" + sessionId + "&shareType=1&dataJson="+json;
			confirm("您确定要分享这"+ids.split(",").length+"条信息到微信吗",function(){
					openDialog(url,1000,700,{
						title: '微信分享'
					});
			});
			break;
			
		case 'weibo':
			var weixinSupport = '${wxSupport}';
			if(weixinSupport==0){
				alert("平台不支持微信、微博分享");
				return;
			}
			var ids = getCheckedIds();
			var gird=Grid.getInstance();
			var statuslist=getCheckedAttrText("status"); 
			if (ids == "") {
				alert("未选中任何记录");
				return;
			}else if(statuslist.indexOf("撤审")>-1 || statuslist.indexOf("未审")>-1){
				alert("您选中的信息中包含已撤审或者未审核信息，您只能分享已审核信息");
				return;
			}else{
				var str = new Array();
				str = ids.split(",");
				var json = "[";
				for(var i=0; i<str.length; i++){
				json +='{"webId":"'+${siteId}+'","colId":"'+${colId}+'","articleId":"'+str[i]+'"},';
				}
				json = json.substring(0, json.length-1);
				json += "]";
			} 
			var loginName = '${loginName}';
			var serviceCode = '${serviceCode}';
			var sessionId = '${sessionId}';
			if(loginName == null){
				loginName = "";
			}
			if(serviceCode == null){
				serviceCode = "";
			}
			if(sessionId == null){
				sessionId  = "";
			}
			
			var url = "${weixinUrl}?loginName=" + loginName
			 + "&serviceCode=${serviceCode}&sessionId=" + sessionId + "&shareType=2&dataJson="+json;
			
			confirm("您确定要分享这"+ids.split(",").length+"条信息到微博吗",function(){
					openDialog(url,1000,700,{
						title: '微博分享'
					});
			});
		
		} 
	}
	
	/**
	 * 查看详情
	 */
	function lookDetails(iid) {
		openDialog('infodetail/list.do?ustate=-1&state=-1&infoId=' + iid, 1000,540, {
			title : '查看回执'
		});
	}

	//信息引用提交回滚
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
			data : "ids=" + checkids + "&colIds=" + targetids + "&flag=" + "Q",
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

	//信息复制提交回滚
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
			data : "ids=" + checkids + "&colIds=" + targetids + "&flag=" + "C",
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
	
	function edit(id, onlylook) {
		var localpage = encodeURIComponent(location.href);  
	    page="modify_show.do?iid=" + id + "&onlylook=" + onlylook + "&localpage=" + localpage; 
		location.href=page; 
	} 
	
	function pushToTask(iid, colid){ 
		openDialog('info/push_show.do?infoId=' + iid + "&colId="+colid+"&siteId=${siteId}", 550, 400, {
			title : '信息推送'
		});
	} 

	function preview(iid){
		openDialog('info/scanQRCode.do?iid=' + iid, 300, 300, {
			title : '信息预览'
		});
	}
	
	/**
	 * 已审核、未审核
	 */ 
	function modifyStatus(iid, status) {
		status = status == 1? 1 : 2;  //切换当前状态

		if(status == 2){
			ajaxSubmit("audit.do?ids=" + iid + "&siteId=${siteId}",{success:function(result){
				if(result.success){
					location.reload();
				}else{
					alert(result.message); 
				}
			}});
		}else if(status == 1){
			ajaxSubmit("unaudit.do?ids=" + iid + "&siteId=${siteId}",{success:function(result){
				if(result.success){
					location.reload();
				}else{
					alert(result.message);
				}
			}});
		}
		
	}
	
	/**
	*	是否置顶
	*/
	function modifyTop(iid, topid) {
		if(topid == 0){
			openDialog('info/addtop_show.do?infoids='+iid+"&colId=${colId}", 650, 260, {
				title : '信息置顶'
			});
		}else{
			ajaxSubmit("untop.do?ids=" + iid + "&siteId=${siteId}&colId=${colId}",{success:function(result){
				if(result.success){
					location.reload();
				}else{
					alert(result.message);
				}
			}});
		}
	}
	
	/**
	 * 信息维护
	 */
	function goPic(iid,name, siteId, colId){ 
		var url = encodeURIComponent(location.href);
		var listUrl = "${contextPath}/manager/pic/list.do?infoId="+ iid 
					+ "&infoName="+ encodeURI(name)+"&fromurl="+url+"&siteId="+siteId+"&colId="+colId;
	    location.href=listUrl; 
	}
 
	function comment(iid){
		openDialog('comment/list.do?infoId=' + iid + "&type=1&siteId=${siteId}", 1020, 580, {
			title : '信息评论'
		});
	}
	
	function good(iid){
		openDialog('info/good_show.do?infoId=' + iid + "&type=1&siteId=${siteId}", 450, 200, {
			title : '信息评论'
		}); 
	} 
	
	function visitcount(iid){
		openDialog('info/visit_show.do?infoId=' + iid + "&type=1&siteId=${siteId}", 450, 200, {
			title : '信息阅读数'
		}); 
	}
	
	function tag(iid){
		openDialog('sign/cardsignmenu_show.do?infoId=' + iid , 380, 380, {
			title : '信息所属卡片'
		});
	}
</script>
</head>
<body>
    <div id="page-title">
		信息管理 / <span id="page-location">${colName}</span>
	</div>
	<div id="page-content">
		<div class="grid-advsearch">
			<form>
				标题<input name="infoTitle" type="text" class="input-text" value="${infoTitle}" style="width:220px;margin:0 30px 0 10px;"/>
				状态<select name="infoState" data-value="${infoState}" style="width:120px">
						<option value="-1">请选择状态</option>
						<option value="0">未审核</option>
						<option value="1">已审核</option>
						<option value="2">已撤审</option>
					</select> 
			          创建时间从<input id="starttime" name="starttime" type="text" class="jcalendar input-text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endtime\')}'});" value="${starttime }" style="width:120px"/> 
						至<input id="endtime" name="endtime" type="text" class="jcalendar input-text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'starttime\')}'});" value="${endtime }" style="width:120px"/>
				<input type="button" class="btn btn-primary" value="检索" style="margin-right:5px;" />
				<input type="button" class="btn advsearch-cancel" value="返回" />
				<div class="line-dotted"></div>
			</form>
		</div>
		<h:grid></h:grid>
	</div>
</body>
</html>