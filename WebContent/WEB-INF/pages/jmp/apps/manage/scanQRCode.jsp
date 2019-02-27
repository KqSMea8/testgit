<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="h" uri="/WEB-INF/tag/hanweb-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组件编辑</title>
<h:head dialog="true" message="true" iconfont="true" pagetype="dialog" validity="true" 
		checkpwd="true" placeholder="true"></h:head>
<script>
	function refreshQRCode(appid){

		$.ajax({
	  		type : "POST",
	  		async: false,
			url: "../apps/manage/refreshQRCode.do?iid=" + iid,
			success:function(result){
				if(result!=null && result!=""){
					var random = parseInt(Math.random()*1000);
					$('#qrcode').attr('src',(result + '?random=' + random));
				}
			}
	   });
	   document.location.reload();
	}
	
</script>
</head>
<body>
	<div style="width: 100%; height: 100%;font-size: 16px; color: #306ca2;" align="center">
		<div style="clear:both"><img id="qrcode" alt="" src="${url }" style="width: 250px;" onclick='refreshQRCode(${iid})'></div>
		<div style="float:center;text-align:center;padding:5 0 10 0">请使用预览助手扫描二维码进行兼容性检测</div>
		<div style="float:center;padding-left:10px;padding-top:5px"><input type='button' class='btn btn-success btn-small' onclick='refreshQRCode(${iid})' value='刷新' /></div>
	</div>
</body>
</html>