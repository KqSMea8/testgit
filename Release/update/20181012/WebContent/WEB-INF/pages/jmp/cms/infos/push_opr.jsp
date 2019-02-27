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
<title>分类管理</title>
<h:head pagetype="dialog" tree="true"  validity="true"  calendar="true" select="true"></h:head>
<script type="text/javascript">
	$(function() {  
		$("#oprform").validity(
			function() {
				if($("#ispublic").val() == 0){
					$('#publicvalue').require('请选择推送类型');
				}else if($("#ispublic").val() == 2 && $("#sendType").val() == 4){
						$('#sendtypevalue').require('请选择推送用户');
				}else if($("#ispublic").val() == 2 && $("#sendType").val() == 1 && $("#pname2").val() == ''){
					$('#sendtypevalue').require('机构不能为空');
				}else if($("#ispublic").val() == 2 && $("#sendType").val() == 2 && $("#pname").val() == ''){
					$('#sendtypevalue').require('群组不能为空');
				}else if($("#ispublic").val() == 2 && $("#sendType").val() == 3 && $("#pname111").val() == ''){
					$('#sendtypevalue').require('用户不能为空');
				}
		    },{
				success:function(result){
				if(result.success){ 
					closeDialog(true);
				}else{
					alert(result.message);
				}
			}
		});
		
		$('#pname2').menu({
			tree : 'groupmenu',
			init : function(menu, obj, treeJq) {
				var setting = {
					async : {
						enable : true,
						url : '${contextPath}/manager/menu/menuforgroup_search.do',
						autoParam : [ "id=groupId", "isDisabled" ],
						otherParam : [ "currentId", $('#iid').val() ] //当前操作机构id
					},
					callback : {
						beforeClick : beforeClick,
						onClick : onClick1,
						onDblClick : onDblClick1
					}
				};

				var rootNode = ${groupMenu };
				treeJq.tree(setting, rootNode);
				//treeJq.tree().refreshNode('0');
			}
		});
		
		$('#pname').menu({
			tree : 'clustermenu',
			init : function(menu, obj, treeJq) {
				var setting = {
					async : {
						enable : true,
						url : '${contextPath}/manager/menu/menuforcluster_search.do',
						autoParam : [ "isDisabled" ]
					},
					callback : {
						beforeClick : beforeClick,
						onClick : onClick2,
						onDblClick : onDblClick2
					}
				};

				var rootNode = ${clusterMenu};
				treeJq.tree(setting, rootNode);
				treeJq.tree().refreshNode('0');
			}
		});
		
	});
	
	function beforeClick(treeId, treeNode, clickFlag) {
		if (treeNode.isDisabled)
			return false;
		return (treeNode.id != 0);
	}

	/**
	 *	机构选择节点点击回调
	 */
	function onClick1(event, treeId, treeNode) {
		if (treeNode.isDisabled)
			return;
		$('#sendtypeid').val(treeNode.id);
		$('#pname2').val(treeNode.name);
	}

	function onDblClick1(event, treeId, treeNode) {
		if(treeNode == null){
			return;
		}
		if (treeNode.isDisabled)
			return;
		if (treeNode.id == '0'){
			onTopClick1();
		} else {
			$('#sendtypeid').val(treeNode.id);
			$('#pname2').val(treeNode.name);
		}
		$('#pname_menu').fadeOut(50);
	}
	
	/**
	 *	群组选择节点点击回调
	 */
	function onClick2(event, treeId, treeNode) {
		if (treeNode.isDisabled)
			return;
		$('#sendtypeid').val(treeNode.id);
		$('#pname').val(treeNode.name);
		
	}

	function onTopClick1() {
		$('#sendtypeid').val('');
		$('#pname2').val("");
	}
	
	function onDblClick2(event, treeId, treeNode) {
		if(treeNode == null){
			return;
		}
		if (treeNode.isDisabled)
			return;
		if (treeNode.id == '0'){
			onTopClick2();
		} else {
			$('#sendtypeid').val(treeNode.id);
			$('#pname').val(treeNode.name);
		}
		$('#pname_menu1').fadeOut(50);
	}
	
	function onTopClick2() {
		$('#sendtypeid').val('');
		$('#pname').val("");
	}
	
	function randomChar(num) {
		var jsSeed = "0123456789qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
		var jsTmp = "";
		if (num == '' || num <= 0) {
			num = 12;
		}
		for ( var i = 0; i < num; i++) {
			jsTmp += jsSeed.charAt(Math.ceil(Math.random() * 1000000)
					% jsSeed.length);
		}
		return jsTmp;
	}

	function getRandomCode(num) {
		$('#codeid').val(randomChar(num));
	}

	/**
	 *	机构选择节点点击前回调
	 */
	function beforeClick(treeId, treeNode, clickFlag) {
		if (treeNode.isDisabled)
			return false;
		return (treeNode.id != 0);
	}

	/**
	 *	机构选择节点点击回调
	 */
	function onClick(event, treeId, treeNode) {
		if (treeNode.isDisabled)
			return;
		$('#typeId').val(treeNode.id);
		$('#pushTypeName').val(treeNode.name);
	}

	function onDblClick(event, treeId, treeNode) {
		if(treeNode == null){
			return;
		}
		if (treeNode.isDisabled)
			return;
		if (treeNode.id == '0'){
			onTopClick();
		} else {
			$('#typeId').val(treeNode.id);
			$('#pushTypeName').val(treeNode.name);
		}
		$('#pname_menu').fadeOut(50);
	}

	/**
	 *	机构选择 根节点 点击回调
	 */
	function onTopClick() {
		$('#typeId').val('');
		$('#pushTypeName').val("推送分类");
	}

	function changePushTime(val){
		if(val==1){
			$("#pushTime").css("display","none"); 
		}else{
			$("#pushTime").css("display",""); 
		}
	}

	function changeCustom(va){
		if($("#offLine").val()==-1){
			$("#custom").css("display","block");
		}else{
			$("#custom").css("display","none");
		}
	}	
	
	function f() {
		openDialog('${contextPath}/manager/userextra/users/list.do?infoId=${info.iid}&userIds='+$('#userIds').val()+'&pageSize=10', 600, 570, {
			title : '用户选择（最多选择200个用户）'
		});
	}
	
	function f5(userNames, userIds) {
		$('#pname111').val(userNames);
		$('#userIds').val(userIds);
	}
	
	function f2(id){   
		if(id == 1){
			$("#jgxz").show();
			$("#yhxz").hide();
			$("#qzxz").hide();
		}
		if(id == 3){
			$("#yhxz").show();
			$("#jgxz").hide();
			$("#qzxz").hide();
		}
		if(id == 2){
			$("#qzxz").show();
			$("#jgxz").hide();
			$("#yhxz").hide();
		}
		if(id == 0){
			$("#qzxz").hide();
			$("#jgxz").hide();
			$("#yhxz").hide();
		}
		
	}
	
	function f1(id){   
		if(id == 1){
			$("#yh").hide();
			$("#jgxz").hide();
			$("#yhxz").hide();
			$("#qzxz").hide();
		}
		if(id == 2){
			$("#yh").show();
		}
		
		
	}
		
	$(function() {
		if('${url}'== 'add_submit.do'){	//新增时自动生成机构标识
			getRandomCode(12);
		}
		$('#pushTypeName').menu({
			tree : 'pushtypemenu',
			init : function(menu, obj, treeJq) {
				var setting = {
					async : {
						enable : true,
						url : '${contextPath}/manager/menu/menuforinfopush_search.do',
						autoParam : [ "id=iid", "isDisabled" ],
						otherParam : [ "currentId", $("#iid").val() ] //当前操作机构id
					},
					callback : {
						beforeClick : beforeClick,
						onClick : onClick,
						onDblClick : onDblClick
					}
				};
				var rootNode = [{"id":"t0","url":null, "isParent":true,"click":"onTopClick()", "isDisabled":false, "checked":false,"iconClose":null,"iconOpen":null,"iconSkin":null,"nocheck":false,"open":true,"pId":"0","chkDisabled":false,"halfCheck":false,"name":"选择分类","font":{},"target":"page","icon":null},
							    {"id":"g0","url":null, "isParent":true,"click":"onTopClick()", "isDisabled":false, "checked":false,"iconClose":null,"iconOpen":null,"iconSkin":null,"nocheck":false,"open":true,"pId":"0","chkDisabled":false,"halfCheck":false,"name":"选择群组","font":{},"target":"page","icon":null}];
				treeJq.tree(setting, rootNode);
				treeJq.tree().refreshNode('0');
			}
		});
	});
</script>
</head>
<body> 
	<form action="${url}"  method="post" id="oprform">
		<input type="hidden" name="infoId" id="infoId" value="${infoId}" />
		<input type="hidden" name="colId" id="colId" value="${colId}" /> 
		<input type="hidden" name="typeOrGroupId" id="typeId" value="${typeId}" /> 
		<input type="hidden" name="siteId" id="siteId" value="${siteId}" />
		<input type="hidden" name="userIds" id="userIds" value="${userIds}" />
		<input type="hidden" name="sendtypeid" id="sendtypeid" value="${sendtypeid}" />
		<input type="hidden" name="publicvalue" id="publicvalue" />
		<input type="hidden" name="sendtypevalue" id="sendtypevalue" />
		<div id="dialog-content">
			<table border="0" align="left"  cellpadding="10" cellspacing="0" class="table">
				<tr>
					<td align="right" class="label">推送方式</td>
					<td class="required">*</td>
					<td align="left">
						<input type="radio" class="radio" name="pushtimetype" id="nowPush" value="1" onclick="changePushTime(this.value);" checked/>即时&nbsp;&nbsp;
						<input type="radio" class="radio" name="pushtimetype" id="delayPush" value="2" onclick="changePushTime(this.value);"/>定时	
					</td>
				</tr>
				
				<tr id="pushTime" style="display:none;">
					<td align="right" class="label">推送时间</td>
					<td class="required"> </td>
					<td align="left">
						<input id="delayTime" name="delayTime" class="jcalendar input-text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" value="${currentDate  }" readOnly="readonly" type="text">
					</td>	
				</tr>
				
				<tr> 
					<td align="right" class="label">推送类型</td>
					<td class="required">*</td>
					<td align="left"  >
						<select name="ispublic" id="ispublic" data-value="${ispublic}" style="width: 110px; margin: 0 30px 0 0px;" onchange="f1(this.value)">
							<option value="0">--请选择--</option>
							<option value="1">公有</option>
							<option value="2">私有</option>
						</select>
					</td>
				</tr>
				
				<tr id="yh" style="display:none;"> 
					<td align="right" class="label">推送用户</td>
					<td class="required">*</td>
					<td align="left"  >
						<select name="sendType" id="sendType" data-value="${sendType}" style="width: 110px; margin: 0 30px 0 0px;" onchange="f2(this.value)">
							<option value="4">--请选择--</option>
							<option value="0">广播（所有人）</option>
							<option value="1">指定机构</option>
							<option value="2">指定群组</option>
							<option value="3">指定用户</option>
						</select>
					</td>
				</tr>
				
				
				<tr id="jgxz" style="display:none;">
					<td align="right" class="label">机构选择</td>
					<td class="required">*</td>
					<td align="left">
						<input  style="width: 212px" type="text" id="pname2" name="pname2" maxlength="33" class="input-text" value="${info.pname2}" />
					</td>	
				</tr>
				<tr id="yhxz" style="display:none;">
					<td align="right" class="label">用户选择</td>
					<td class="required">*</td>
					<td align="left">
						<input  style="width: 212px" type="text" id="pname111" name="pname111" maxlength="33" class="input-text" value="${info.pname111}" onclick="f()"/>
					</td>	
				</tr>
				<tr id="qzxz" style="display:none;">
					<td align="right" class="label">群组选择</td>
					<td class="required">*</td>
					<td align="left">
						<input  style="width: 212px" type="text" id="pname" name="pname" maxlength="33" class="input-text" value="${info.pname}" />
					</td>	
				</tr>
				
				
				<tr> 
					<td align="right" class="label">离线消息保留时间</td>
					<td class="required">*</td>
					<td align="left"  >
						<select name="liveTime" id="offLine" onchange="changeCustom(this);">
							<option value="86400" data-value="">1天</option>
							<option value="60" data-value="">1分钟</option>
							<option value="600" data-value="">10分钟</option>
							<option value="3600" data-value="">1小时</option>
							<option value="10800" data-value="">3小时</option>
							<option value="21600" data-value="">6小时</option>
							<option value="43200" data-value="">12小时</option>
							<option value="259200" data-value="">3天</option>
						</select>
					</td>
				</tr>
			</table>
		</div>
		<div id="dialog-toolbar">
			<div id="dialog-toolbar-panel">
				<input type="submit" class="btn btn-primary" value="推送" /> 
				<input type="button" class="btn" value="关闭" onclick="closeDialog();" />
			</div>
		</div>
	</form>
</body>
</html>