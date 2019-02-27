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
<title>应用列表</title>
<h:head pagetype="page" grid="true" dialog="true" validity="true" tree="true" loadmask="true"></h:head> 
<script type="text/javascript">
	function toolbarAction(action) {
		switch (action) {
		case 'add':
			openDialog('lightapp/add_show.do?lightTypeId=${lightTypeId}', 580, 450, {  
				title : '应用新增'
			});
			break;
			
		case 'remove':
			var ids = getCheckedIds();
			if (ids == '') {
				alert('未选中任何记录');
				return;
			}
			if (ids.split(',').length > 1) {
				alert('每次只能删除一个应用');
				return;
			}
			confirm('您确定要删除这' + ids.split(',').length + '条记录吗', function() {
				ajaxSubmit('remove.do?ids='+ids, {success:function(result){
					if(result.success){
						location.reload();
					}else{
						alert(result.message);
					}
				}});
			}); 
			break;
			
		case 'order': 
			openDialog('lightapp/sort_show.do?pid=${lightTypeId}', 470, 540, {
				title : '应用排序'
			});
			break; 
			
		case 'syn':
			confirm('您确定要同步应用吗', function() {
				$('body').mask('同步中，请稍后...');
				ajaxSubmit('putAppIssue.do', {success:function(result){			
					 if(result.success){
					 	Tree.getInstance().refreshNode(result.params.refresh);
						location.reload();
						alert('同步成功');
					}else{
						alert(result.message);  
					} 
				}});
				location.reload();
			}); 
			break;
		}
	}
	
	
	
	/**
	 * 编辑
	 */
	function edit(iid) {
		openDialog("lightapp/modify_show.do?iid=" + iid, 580, 450, {
			title : '应用修改'
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
					window.location.reload();
				}else{
					alert(result.message);
				}
			}
		});
	}

	function getBrowser() {
	    var ua = window.navigator.userAgent;
	    var isSafari = ua.indexOf("Safari") != -1 && ua.indexOf("Version") != -1;
	    if(isSafari){
		return "Safari";
	    }
    }
	
	function isIE() {
		if (!!window.ActiveXObject || "ActiveXObject" in window)
		return true;
		else
		return false;
	}
	function copyUrl(url,iid){
		if(url!=null&&url!=""){
		if(isIE()){
			window.clipboardData.setData("Text",url);
		    alert("复制成功");
		}else if(getBrowser()=="Safari"){
			openDialog('app/appUrl.do?iid=' + iid, 330, 100, {
				title : '应用地址：'
			});
		}else{
			var ele = document.getElementById(iid);
	        ele.select();
	        document.execCommand("Copy");
	        alert("复制成功");
		}
		}else{
			document.getElementById(iid).value="";
			var ele = document.getElementById(iid);
	        ele.select();
	        document.execCommand("Copy");
			alert("应用地址不存在");
		}
	}

	function previewModel(url,iid){  
		openDialog('lightapp/scanQRCode.do?url=' + url+"&iid="+iid, 300, 300, {
			title : '二维码'
		});
	}

	/**
	 * 信息维护
	 */
	function managerInfo(iid, type){ 
		var url = encodeURIComponent(location.href); 
		//通讯录
		if(type==5){
			openDialog('plugins/numsense/col/frame_show.do', 1250, 600, {
				title : '通讯录管理',
				onClose:function(){
				$(this).remove();
			}
			});
		//报料管理
		}else if (type==2){
			openDialog('broketype/frame_show.do', 1250, 600, {
				title : '报料管理',
				onClose:function(){
				$(this).remove();
			}
			});
	    //阅读
		}else if (type==6){
			openDialog('plugins/read/frame_show.do', 1250, 600, {
				title : '阅读管理',
				onClose:function(){
				$(this).remove();
			}
			});
		} else if (type==7){
			openDialog('plugins/read/frame_show.do', 1250, 600, {
				title : '阅读管理',
				onClose:function(){
				$(this).remove();
			}
			});
		}
	}
</script>
</head>
<body>
	<div id="page-title">
		 <span id="page-location">应用管理</span>
	</div>
	<div id="page-content">    
		<h:grid></h:grid>
	</div>
</body>
</html>