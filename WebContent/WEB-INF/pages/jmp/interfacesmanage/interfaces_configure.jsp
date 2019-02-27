<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="h" uri="/hanweb-tags"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="${pageContext.request.contextPath }/ui/js/md5.js"></script>
<title>参数配置</title>
<h:head tabs="true" dialog="true" message="true" iconfont="true" pagetype="dialog" validity="true" 
		checkpwd="true" placeholder="true" upload="true" select="true" tree="true" ></h:head>

<script type="text/javascript">
$("#oprform").validity(function() {
	$("#name").require("分类不能为空");
}, {
	success : function(result) {
		if (result.success) {
			closeDialog(true);
		} else {
			alert(result.message);
		}
	}
});
</script>
<body>
	
    
	<form action="${url }" method="post" id="oprform" name="oprform">
		<div id="dialog-content">
			<input id="domain" type="hidden"  value="${inter.domain}" />
			 <input id="type" type="hidden" value="${inter.type}" />
				<div id="tabs" class="easyui-tabs" > 

    <div title="参数设置" style="padding:20px;">
			<table id="param" style="border:1px;">
			
				<c:forEach items="${params}" var="p" varStatus="state">
					<input type="hidden" name="iid" id="iid" value="${p.iid}" />

					<tr>
						<td><input class="paramname"
							style="color: black; width: 80px; background-color: #FFFFFF; border: 0;"
							type="text" disabled="disabled" value="${p.name}" /> 
							<span id="isrequired" style="color: red;">
							 	<c:if test="${p.isrequired==0 }"></c:if> 
							 	<c:if test="${p.isrequired==1 }">*</c:if>
							</span>
						
						
						</td>
						<td><input class="name input-text" type="text"/></td>
					</tr>
					<tr>
					<td>
					<div style="height: 10px;"></div>
					</td>
					</tr>

				</c:forEach>
			</table>
			</div>
			<div title="返回数据" style="padding:20px; ">
		
		
		<textarea id="div"  class="input-textarea" style="width: 100%;height: 200px;">
		</textarea>
		 
		<div id="divtest">
		</div>
		
    </div> 
  
</div> 
		</div>

		<div id="dialog-toolbar">
			<div id="dialog-toolbar-panel">
				<input type="button" id="visit" class="btn btn-primary" value="请求" /> <input
					type="button" class="btn" value="取消" onclick="closeDialog();" />
			</div>
		</div>
	</form>

     

    
	
			
</body>

<script type="text/javascript">
	$("#visit").click(function() {
		var domain = $("#domain").val();

		

		var name = document.getElementsByClassName("name");

		var paramname = document.getElementsByClassName("paramname");
		var iid = document.getElementById("iid").value;
		var span="";
		
		var str = "";
if(iid == 70){
	for (var i = 0; i < paramname.length; i++) {
		 span = document.getElementById("isrequired").innerText;
		if (name[i].value != "") {
			
			paramnames = paramname[i].value;
			if(paramnames == "password"){
				names = hex_md5(name[i].value);
			}else{
				names = name[i].value;
			}
			str += "&" + paramname[i].value + "=" +names;
		} else {
			names = "";
		}
	}
	
}else{
		for (var i = 0; i < paramname.length; i++) {
			 span = document.getElementById("isrequired").innerText;
			if (name[i].value != "") {
				str += "&" + paramname[i].value + "=" + name[i].value
				paramnames = paramname[i].value;
				names = name[i].value;
			} else {
				names = "";
			}

		}
	}
		//if (span == "*" && names == "") {
			//alert("请输入必要参数");
			//return false;
			
		//}
		str = str.substring(1);
		
		
		
		
		var url = domain + "?" + str;

		var type = document.getElementById("type").value;
		
		if (type == 1) {
			
			 $.ajax({
				url : url,
				data : {
					paramnames : names
				}, //以键/值对的形式  
				async : false,
				dataType : "json",
				success : function(data) {
					var datas = JSON.stringify(data);
					$("#div").html(datas); 


				},
				error : function(data) {
					//console.log("error");

				},
				
			}); 

		} else if (type == 0) {
			$.ajax({
				url : url,
				type : "post",
				data : {
					paramnames : names
				}, //以键/值对的形式  
				async : false,
				dataType : "json",
				success : function(data) {
					var datas = JSON.stringify(data);
					$("#div").val(datas);

				},
				error : function(data) {
					
				}
			});
		} else if(type == 2) {
			$.ajax({
				url : url,
				data : {
					paramnames : names
				}, //以键/值对的形式  
				async : false,
				dataType : "html",
				success : function(data) {
					//console.log("data:"+data);
					//var datas = JSON.stringify(data);
					//var jso = JSON.parse(data);
					//console.log("jso:"+jso);
					//console.log("data:"+data);
					$("#div").hide();
					$("#divtest").html(data); 

				},
				error : function(data) {
					console.log("error");
					
				}
			});
		}

	});
</script>


</html>