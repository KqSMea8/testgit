<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="h" uri="/hanweb-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>开放接口</title>
<h:head dialog="true" message="true" iconfont="true" pagetype="dialog"
	validity="true" checkpwd="true" placeholder="true" upload="true"
	select="true" tree="true"></h:head>
<style type="text/css">
#p {
	border-radius:5px;
	border: 1px solid #BBBBBB;
	background: #FCFCFC;
}
</style>
<script type="text/javascript" charset="utf-8"
	src="${contextPath}/ui/widgets/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${contextPath}/ui/widgets/ueditor/ueditor.all.js"></script>
<script type="text/javascript">
	$(function(){
		$('#pname').menu({
			width: 310,
			height: 250,
			tree : 'typemenu',
			init : function(menu, obj, treeJq) {
				var setting = {
					async : {
						enable : true,
						url : '${contextPath}/manager/interfacemenu/interfacemenu_edit.do',
						autoParam : [ "id=typeId" ]
					},
					callback : {
						beforeClick : beforeClick,
						onClick : onClick,
						onDblClick : onDbClick
					}
				};

//				var rootNode = ${groupMenu};
			//treeJq.tree(setting, rootNode);
			treeJq.tree(setting);
			//treeJq.tree().refreshNode('${rangeId}');
		}
	});
		
		$("#oprform").validity(function() {
			$("#name").require("接口名称不能为空");
			$("#domain").require("接口地址不能为空");
			$("#pname").require("接口分类不能为空");
			$("#description").require("接口描述不能为空");
			
		}, {
			success : function(result) {
				if (result.success) {
					closeDialog(true);
				} else {
					alert(result.message);
				}
			}
		});
		
		 $(".clean").click(function(){
			 $('#pid').val(0);
			 $('#pname').val('');
				 
		 });
	});

	/**
	 *	对textarea进行字数控制
	 */
	function checklength(obj) {
	    var max = $(obj).attr('maxlength');
	    if(max == null || max == "" || max == undefined) {
	        return;
	    }
	    if(obj.value.length > max) {
	        obj.value=obj.value.substring(0,(max-1));
	        return;
	    }
	}
	
	var i = ${fn:length(paramList)};
	
	function addrow(){

		var tables = $('#param');
		var addtr = $("<tr>"+
		"<td><input type='text' class='input-text' style='width:80px;' name='params["+i+"].name'/></td>"+
		"<td><select class='select input-text' style='width:80px;' name='params["+i+"].isrequired'><option value='0'>否</option><option value='1'>是</option></select></td>"+
		"<td><select class='select input-text' style='width:80px;' name='params["+i+"].type'><option value='0'>Integer</option><option value='1'>String</option></select></td>"+
		"<td><button class='btn' onclick='deleteTrRow(this);'>&nbsp;删除</button></td>"+
		"</tr>");
		addtr.appendTo(tables); 
		i++;
	}

	function deleteTrRow(str){

		$(str).parent().parent().remove();

	}
	
	function onClick(event, treeId, treeNode) {
		document.getElementById('pid').value=treeNode.id;
		document.getElementById('pname').value=treeNode.name;
	}

	function onDbClick(event, treeId, treeNode) {
		if (treeNode == null) {
			return;
		}
		if (treeNode.isDisabled || treeNode.id == '${formBean.rangeId }')//根节点及失效节点双击无效
			return;
		document.getElementById('pid').value=treeNode.id;
		document.getElementById('pname').value=treeNode.name;
		$('#groupname_menu').fadeOut(50);
	}
	
	function beforeClick(treeId, treeNode, clickFlag) {
		if (treeNode.isDisabled)
			return false;
		return (treeNode.id != 0);
	}
	

</script>
</head>
<body>
	<form action="${url }" method="post" name="oprform" id="oprform">
		<input type="hidden" name="iid" id="iid" value="${inter.iid }" /> 
		<input type="hidden" name="typeid" id="pid" value="${inter.typeid }"/>
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="10" cellspacing="0"
				class="table">
				<tr>
					<td align="right" class="label">接口名称</td>
					<td class="required">*</td>
					<td><input type="text" id="name" name="name"
						class="input-text" maxlength="33" value="${inter.name }" />&nbsp;</td>
				</tr>
				
				<tr>
					<td  align="right" class="label">接口地址</td>
					<td class="required">*</td>
					<td><input style="width: 500px" type="text" id="domain" name="domain"
						class="input-text" maxlength="100" value="${inter.domain }" />&nbsp;</td>
				</tr>
			
				
				<tr>
					

				</tr>
				<tr>
					<td align="right" class="label">接口分类</td>
					<td class="required">*</td>
					<td>
						<span class="input-append">
							<input type="text" name="pname" id="pname"
								readonly="readonly" style="width:276px;" class="input-text" value="${typeName }"/><i class="iconfont add-on inner button clean" style="color:#CCC;" title="清除">&#xf3015;</i>
						</span>
					</td>

				</tr>
				
			    <tr>
				<td align="right" class="label">请求方式</td>
					<td class="required">*</td>
					<td><select id="type" name="type">
							<option
								<c:if test="${inter.type==0 }"> selected="selected"</c:if>
								value="0">POST</option>
							<option
								<c:if test="${inter.type==1 }"> selected="selected"</c:if>
								value="1">GET</option>
								
								<option
								<c:if test="${inter.type==2 }"> selected="selected"</c:if>
								value="2">WebService</option>
					</select></td>
				</tr>
				
				<tr>
				<td align="right" class="label" >接口参数</td>
					<td>&nbsp;</td>
					<td colspan="4">
					<div  id="p" style="width: 510px;">
						<table id="param" class="table">
							<tr class="label">
								<th style='width: 110px;'>名称</th>
								<th style='width: 110px;'>必须</th>
								<th style='width: 110px;'>类型</th>
								<th><input class="btn" type="button" onclick="addrow();"
									value="添加参数" /></th>
							</tr>
							<c:forEach items="${paramList}" var="p">
								<c:set var="index" value="${index+1}" />
								<tr>
									<td><input type='text' class='input-text'
										style='width: 80px;' name="params[${index-1 }].name"
										value="${p.name}" /></td>
									<td><select class='select input-text'
										style='width: 80px;' name="params[${index-1 }].isrequired">
											<option
												<c:if test="${p.isrequired==0 }"> selected="selected"</c:if>
												value='0'>否</option>
											<option
												<c:if test="${p.isrequired==1 }"> selected="selected"</c:if>
												value='1'>是</option>
									</select></td>
									<td><select class='select input-text'
										style='width: 80px;' name="params[${index-1 }].type">
											<option
												<c:if test="${p.type==1 }"> selected="selected"</c:if>
												value='0'>Integer</option>
											<option
												<c:if test="${p.type==1 }"> selected="selected"</c:if>
												value='1'>String</option>
									</select></td>
									<td><button class="btn" onclick='deleteTrRow(this);'>&nbsp;删除</button></td>
								</tr>
							</c:forEach>
						</table>
						</div>
					</td>
				</tr>
				
				
				

				

			</table>
		</div>
		<div id="dialog-toolbar">
			<div id="dialog-toolbar-panel">
				<input type="submit" class="btn btn-primary" value="保存" /> <input
					type="button" class="btn" value="取消" onclick="closeDialog();" />
			</div>
		</div>
	</form>
</body>
</html>