<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="h" uri="/WEB-INF/tag/hanweb-tags.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>图片新增</title>
<h:head pagetype="dialog" multifileupload="true" toggle="true" select="true"></h:head>
<h:import type="js" path="/resources/jmp/picture/script/add.js"></h:import>
<h:import type="js" path="/ui/widgets/json/json2.js"></h:import>
<h:import type="js" path="/ui/widgets/hanweb/multifileupload/multifileupload.js"></h:import>
<script type="text/javascript">
	$(function() {
		$('.upload_btn').multifileupload({
			dialogUrl : '${uploadUrl}',
			filters : [ {
				title : '图片文件（jpg,gif,png）',
				extensions : 'jpg,gif,png'
			} ]
		});
	});
	
	function addSubmit(){
		$("#addsubmit").attr("disabled",true).css("background-color", "#BBB").css("border-color", "#BBB");
		save();
	}
</script>
<style>
.tip {
	list-style-position: inside;
	list-style-type: disc;
	margin: 5px;
}

.tip li {
	line-height: 2em;
	word-break: break-all;
}

.upload-filelist {
	margin: 0;
	padding: 0 5px;
}

.file-caption-big {
	padding: 0 5px;
	height: 30px;
	line-height: 30px;
	border-bottom: 1px solid #CCC;
	background-color: #EFEFEF;
}

.file-list-big {
	position:absolute;
	width: 300px;
	border-right: 1px solid #CCC;
	top:31px;
	bottom:0;
	overflow: auto;
	white-space: nowrap;
}

.file-tip-big {
	position:absolute;
	right:0;
	width: 620px;
}
	</style>
</head>
<body>
	<form action="${url }" method="post" id="oprform" name="oprform" target="hiddenSubmit">
		<input type="hidden" name="filesJson" id="filesJson" value=""/>
		<input type="hidden" name="classId" id="classId" value="${classId}"/>
		<div id="dialog-content" style="padding:0;border: 1px solid #CCC;">
			<div class="file-caption-big">
				文件名
			</div>
			<div class="file-list-big">
				<ul class="upload-filelist"></ul>
			</div>
			<div class="file-tip-big">
				<ul class="tip" style="margin-bottom:20px;">
					<li>支持格式：${pictype}</li>
					<li>请限制单个文件大小在${picsize }M以内</li>
					<li>一次可上传10个文件</li>
				</ul>
			</div>
		</div>

		<div id="dialog-toolbar">
			<div id="dialog-toolbar-panel">
			<div style="float: left;">
				<a type="button" class="btn btn-success upload_btn" style="margin-left: 0"> <i
						class="iconfont" style="font-size: 14px;">&#xf002d;</i>添加文件
				</a>
			</div>
				<input type="submit"  class="btn btn-primary" value="保存"
					onclick="addSubmit();" style="margin-right: 10px;" /> <input
					type="button" class="btn" value="取消" onclick="closeDialog();" />
			</div>
		</div>
	</form>
</body>
</html>