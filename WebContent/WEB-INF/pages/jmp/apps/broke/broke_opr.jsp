<%@ page language="java" contentType="text/html; charset=UTF-8"
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
<title>报料信息</title>
<h:head pagetype="dialog" multiselect="true" tree="true" validity="true"></h:head>
<script type="text/javascript">
	$(function() { 
		$("#oprform").validity(function() {
			if($.trim($("#content").val()).length > 0){
				$("#content").maxLength(660,"内容不能超过660个字");
			}
			if($.trim($("#contact").val()).length > 0){
				$("#contact").maxLength(24,"联系方式不能超过24个字"); 
			}
			if($.trim($("#reply").val()).length > 0){
				$("#reply").maxLength(660,"回复不能超过660个字"); 
			}
		},{
			success:function(result){
			if(result.success){
				var treeObj = Tree.getInstance({domPath:getParentWindow().parent.menu});
				treeObj.refreshNode(result.params.refresh);
				treeObj.removeNode(result.params.remove);
				closeDialog(true);
			}else{
				alert(result.message);
			}
		}
		});
	});
</script>
<style type="text/css">
	.input-textarea {
		resize: none;
	}
</style>
</head>
<body>
	<form action="${url }" method="post" id="oprform" enctype="multipart/form-data">
		<input type="hidden" id="siteId" name="siteId" value="${broke.siteId }">
		<input type="hidden" id="iid" name="iid" value="${broke.iid }">
		<input type="hidden" id="classId" name="classId" value="${broke.classId}"> 
		<input type="hidden" id="title" name="title" value="${broke.title }">
		<input type="hidden" id="isAudit" name="isAudit" value="${broke.isAudit}"> 
		<input type="hidden" id="ip" name="ip" value="${broke.ip}"> 
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0" class="table">
			<c:choose>
			    <c:when test="${url=='add_submit.do'}">
				<tr>
					<td align="right" class="label">提交人</td>
					<td class="required">&nbsp;</td>
					<td>
						<input type="text" name="loginId" maxlength="33" class="input-text" value="${broke.loginId }" />
					</td> 
				</tr>
				
				<tr>
					<td align="right" class="label">联系方式</td>
					<td>&nbsp;</td>
					<td><input  id="contact" name="contact" class="input-text" value="${broke.contact }">
					</td>
				</tr>
				
				<tr>
					<td align="right" class="label">内容</td>
					<td>&nbsp;</td>
					<td><textarea id="content" name="content" class="input-textarea">${broke.content }</textarea>
					</td>
				</tr>
				</c:when>
				
		        <c:otherwise>
				<tr>
					<td align="right" class="label">图片1地址</td>
					<td class="required">&nbsp;</td>
					<td><input disabled="disabled" type="text" id="picPath" name="picPath" maxlength="33"
						class="input-text" value="${broke.picPath }" />
						<c:if test="${broke.picPath!=''}">
						 <a href="${broke.picPath }" target="_blank">
						  <span class="btn btn-primary">查看</span>
						 </a>
						</c:if>
					</td>
				</tr>
				
				<tr>
					<td align="right" class="label">图片2地址</td>
					<td class="required">&nbsp;</td>
					<td><input disabled="disabled" type="text" id="picPath1" name="picPath1" maxlength="33"
						class="input-text" value="${broke.picPath1 }" />
						<c:if test="${broke.picPath1!=''}">
						 <a href="${broke.picPath1 }" target="_blank">
						  <span class="btn btn-primary">查看</span>
						 </a>
						</c:if>
					</td>
				</tr>
				
				<tr>
					<td align="right" class="label">图片3地址</td>
					<td class="required">&nbsp;</td>
					<td><input disabled="disabled" type="text" id="picPath2" name="picPath2" maxlength="33"
						class="input-text" value="${broke.picPath2 }" />
						<c:if test="${broke.picPath2!=''}">
						 <a href="${broke.picPath2 }" target="_blank">
						  <span class="btn btn-primary">查看</span>
						 </a>
						</c:if>
					</td>
				</tr>
				
				<tr>
					<td align="right" class="label">图片4地址</td>
					<td class="required">&nbsp;</td>
					<td><input disabled="disabled" type="text" id="picPath3" name="picPath3" maxlength="33"
						class="input-text" value="${broke.picPath3 }" />
						<c:if test="${broke.picPath3!=''}">
						 <a href="${broke.picPath3 }" target="_blank">
						  <span class="btn btn-primary">查看</span>
						 </a>
						</c:if>
					</td>
				</tr>
				
				<tr>
					<td align="right" class="label">音频地址</td>
					<td class="required">&nbsp;</td>
					<td><input disabled="disabled" type="text" id="audioPath" name="audioPath" maxlength="33"
						class="input-text" value="${broke.audioPath }" />
						<c:if test="${broke.audioPath!=''}">
						<a href="${broke.audioPath}" target="_blank"><span class="btn btn-primary">下载</span></a>
						</c:if>
					</td>
				</tr>
				
				<tr>
					<td align="right" class="label">视频地址</td>
					<td class="required">&nbsp;</td>
					<td><input disabled="disabled" type="text" id="videoPath" name="videoPath" maxlength="33"
						class="input-text" value="${broke.videoPath }" />
						<c:if test="${broke.videoPath!=''}">
						<a href="${broke.videoPath }" target="_blank"><span class="btn btn-primary">下载</span></a>
						</c:if>
					</td>
				</tr>
				
				<tr>
					<td align="right" class="label">创建时间</td>
					<td class="required">&nbsp;</td>
					<td><input disabled="disabled" type="text" id="createTime" name="createTime" maxlength="33"
						class="input-text" value="${broke.createTime }" /></td>
				</tr>
				
				<tr>
					<td align="right" class="label">提交人</td>
					<td class="required">&nbsp;</td>
					<td><input disabled="disabled" type="text" name="loginId" maxlength="33"
						class="input-text" value="${broke.loginId }" /></td> 
				</tr>
				
				<tr>
					<td align="right" class="label">联系方式</td>
					<td>&nbsp;</td>
					<td><input disabled="disabled"  id="content" name="contact" 
						class="input-text" value="${broke.contact }">
					</td>
				</tr>
				
				<tr>
					<td align="right" class="label">内容</td>
					<td>&nbsp;</td>
					<td><textarea id="content" name="content" class="input-textarea">${broke.content }</textarea>
					</td>
				</tr>
				
				<tr>
					<td align="right" class="label">回复</td>
					<td>&nbsp;</td>
					<td><textarea id="reply" name="reply" class="input-textarea">${broke.reply }</textarea>
					</td>
				</tr>
				</c:otherwise>
			</c:choose>
				<tr>
					<td align="right" class="label">是否公开</td>
					<td>&nbsp;</td>
					<td><input type="checkbox" value="1" data-value="${broke.isOpen }" name="isOpen">
					</td>
				</tr>			
			</table>
		</div>
		<div id="dialog-toolbar">
			<div id="dialog-toolbar-panel">
				<input type="submit" class="btn btn-primary" value="保存并审核" /> 
				<input type="button" class="btn" value="取消" onclick="closeDialog();" />
			</div>
		</div>
	</form>
</body>
</html>