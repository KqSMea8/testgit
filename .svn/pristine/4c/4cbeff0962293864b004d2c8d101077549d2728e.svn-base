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
<title>日志设置</title>
<h:head pagetype="dialog" multiselect="true" tree="true" validity="true"></h:head>
<script type="text/javascript">
	$(function() { 
		$("#oprform").validity(function() {

		},{
			success:function(result){
			if(result.success){
				closeDialog(true);
			} else {
				alert(result.message);
			}
		}
       });
		var len = $("[id^=md]").length;
		for(var i=0;i<len;i++){
			var mod = $("[id^=md]")[i].id; 
			var funid = mod.replace("md", "fd");
			var c = $("[id^="+funid+"-]:checked");
			if(c.length > 0){
				$("#"+mod).attr("checked","checked");
			}
		}
	});
	
	function choseall(){
		var c=$('#checkall').attr("checked");
		if(c=="checked"){
			$("#oprform :checkbox").attr("checked","checked");
		}else{
			$("#oprform :checkbox").removeAttr("checked");
		}
	}

	function chose(obj){
		if(obj.id.indexOf("-") == -1){
			var id = obj.id.replace("md", "fd");
			$("input[id^='"+id+"-']").attr("checked",obj.checked);
		} else {
			var mod = obj.id.substring(0,obj.id.indexOf("-"));
			if(obj.checked){
				$("#"+mod.replace("fd", "md")).attr("checked",true);
			}else{
				var len = $("input[id^='"+mod+"-']").length;
				for(var i=0;i<len;i++){
					var c = $("input[id^='"+mod+"-"+i+"']").attr("checked");
					if(c=="checked"){
						return;
					}
				}
				$("#"+mod.replace("fd", "md")).removeAttr("checked");
			}
		}
	}
</script>
</head>
<body>
	<form action="${url }" method="post" id="oprform">
		<div id="dialog-content">
			<table border="0" align="center" cellpadding="5" cellspacing="0" class="table" width="550px">
				<c:forEach items="${beanList }" var="LogConfigFormBean" varStatus="module">
				<tr>
					<td align="left" class="label" width="120px" style="padding-bottom: 5px;">
						<font style="font-size: 14px;font-weight: bold;font-family: 黑体;color: #302D2D;">
						<input type="checkbox" id="md${LogConfigFormBean.moduleId }" onclick="chose(this)">	
						${LogConfigFormBean.moduleName }
						</font>
					</td>
					
					<td style="padding-bottom: 5px;text-align: left;">
						<table style="width: 420px;text-align: left;padding-bottom: 0px;">
							<tr align="left">
							<td style="padding-bottom: 0px;">
							<c:forEach items="${LogConfigFormBean.logConfigList }" var="LogConfig" varStatus="state">
							   <div style="font-size: 14px;width: 80px;display: inline;margin-right: 10px;">
								<c:choose>
									<c:when test="${LogConfig.isLog == 1 }">
									<input type="checkbox" checked id="fd${LogConfigFormBean.moduleId }-${LogConfig.funcId }" name="modAndFuncId" onclick="chose(this)" value="${LogConfigFormBean.moduleId }-${LogConfig.funcId }">
									</c:when>
									<c:otherwise>
									<input type="checkbox" id="fd${LogConfigFormBean.moduleId }-${LogConfig.funcId }" name="modAndFuncId" onclick="chose(this)" value="${LogConfigFormBean.moduleId }-${LogConfig.funcId }">
									</c:otherwise>
								</c:choose>
								${LogConfig.funcName }</div>
							</c:forEach>
							</td>
							</tr>
						</table>
					</td>
				</tr>
				</c:forEach>
				<tr><td style="color: red;"><input type="checkbox" id="checkall" onclick="choseall();"/>全选</td></tr>
			</table>
		</div>
		<div id="dialog-toolbar">
			<div id="dialog-toolbar-panel">
				<input type="submit" class="btn btn-primary" value="保存" /> 
				<input type="button" class="btn" value="取消" onclick="closeDialog();" />
			</div>
		</div>
	</form>
</body>
</html>