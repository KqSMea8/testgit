/**
 * 栏目类型切换
 */

function changeType(type){
	if(type == 2){ 
	    $("#a").show(); 
	    $("#synPeriod_tr").show(); 
	    $("#taskn").show();
	    $("#taskn2").show();
	    if($("#taskName").val()!=""){ 
	    	$("#logomust").show(); 
	    	$("#periodmust").show(); 
	    }else{
	    	$("#logomust").hide();
	    	$("#periodmust").hide(); 
	    }
	    $("#startTime_tr").show();
	    $("#b").show();
		$("#c").show(); 
		$("#d").show(); 
		$("#e").hide(); 
		$("#f").show(); 
	    $("#g").hide();
		$("#h").hide(); 
		$("#i").show(); 
		$("#j").hide(); 
		$("#k").hide(); 
	    $("#l").hide();
		$("#m").hide(); 
		$("#n").hide(); 
		$("#o").hide(); 
		$("#p").hide();   
		
		$("#r").show(); 
		$("#s").show(); 
		$("#z").show();
		$("#t").show(); 
		$("#u").show(); 
		$("#v").show(); 
		$("#x").show();
		$("#lxxz").show();
		if($("#x").val() == 2){
			$("#y").show();
		}else{
			$("#y").hide();
		}
		$("#w").hide();  
		$("#a2").hide();
		$("#a3").hide();
		$("#a4").hide();
		$("#a5").hide();
		$("#a6").hide();
		$("#apptr").hide();
		$("#apptricon").hide();
		$("#appLayOutTr").hide();
		if($("#x").val() == 6){
			$("#cardDisplay").show(); 
		}else{
			$("#cardDisplay").hide(); 
		}
		
	}else if(type == 1){
		$("#a").hide(); 
		$("#taskn").hide();
		$("#taskn2").hide();
		$("#synPeriod_tr").hide(); 
	    $("#startTime_tr").hide();
	    $("#b").show();
		$("#c").show(); 
		$("#d").show(); 
		$("#e").hide(); 
		$("#f").show(); 
	    $("#g").hide();
		$("#h").hide(); 
		$("#i").hide(); 
		$("#j").hide(); 
		$("#k").hide(); 
	    $("#l").hide();
		$("#m").hide(); 
		$("#n").hide(); 
		$("#o").hide(); 
		$("#p").hide(); 
		 
		$("#r").hide(); 
		$("#s").hide(); 
		$("#z").hide();
		$("#t").hide(); 
		$("#u").hide(); 
		$("#v").hide(); 
		$("#x").hide(); 
		$("#y").hide();
		$("#w").hide();  
		$("#a2").hide();
		$("#a3").hide();
		$("#a4").show();
		$("#a5").hide();
		$("#a6").hide();
		$("#apptr").hide();
		$("#apptricon").hide();
		$("#appLayOutTr").hide();
		$("#cardDisplay").hide();
		$("#lxxz").hide();
	}else if(type == 4){
		$("#a").hide(); 
		$("#synPeriod_tr").hide(); 
	    $("#startTime_tr").hide();
	    $("#taskn").show();
		$("#taskn2").show();
	    $("#b").show();
	    $("#a").hide(); 
		$("#c").show(); 
		$("#d").show(); 
		$("#e").hide(); 
		$("#f").show(); 
	    $("#g").hide();
		$("#h").hide(); 
		$("#i").hide(); 
		$("#j").hide(); 
		$("#k").hide(); 
	    $("#l").hide();
		$("#m").hide(); 
		$("#n").hide(); 
		$("#o").hide(); 
		$("#p").hide(); 
		 
		$("#r").hide(); 
		$("#s").hide(); 
		$("#z").hide(); 
		$("#t").hide(); 
		$("#u").hide(); 
		$("#v").hide(); 
		$("#x").hide(); 
		$("#y").hide();
		$("#w").show();  
		$("#a2").hide();
		$("#a4").hide();
		$("#a3").show();
		$("#a5").show();
		$("#a6").show();
		$("#apptr").hide();
		$("#apptricon").hide();
		$("#appLayOutTr").hide();
		$("#cardDisplay").hide();  
	}else if(type == 3){
		$("#a").hide(); 
		$("#synPeriod_tr").hide(); 
	    $("#startTime_tr").hide();
	    $("#taskn").hide();
		$("#taskn2").hide();
	    $("#b").show();
	    $("#a").hide(); 
		$("#c").show(); 
		$("#d").show(); 
		$("#e").hide(); 
		$("#f").show(); 
	    $("#g").hide();
		$("#h").hide(); 
		$("#i").hide(); 
		$("#j").hide(); 
		$("#k").hide(); 
	    $("#l").hide();
		$("#m").hide(); 
		$("#n").hide(); 
		$("#o").hide(); 
		$("#p").hide(); 
		 
		$("#r").hide(); 
		$("#s").hide(); 
		$("#z").hide(); 
		$("#t").hide(); 
		$("#u").hide(); 
		$("#v").hide(); 
		$("#x").hide(); 
		$("#y").hide();
		$("#w").hide();  
		$("#a2").hide();
		$("#a4").hide();
		$("#a3").hide();
		$("#a5").hide();
		$("#a6").hide();
		$("#apptr").show();
		$("#apptricon").show();
		$("#appLayOutTr").show();
		$("#cardDisplay").hide();
		$("#lxxz").hide();
	}
} 

function changeHDType(type, colType){
	if(colType == -1){
		colType = $("#type").val();
	}
	if(type == 1 || type == '' || type == 2 || type == 3){
		var s = $("#a3").eq(0).html();
		s = s.replace("接口地址", "系统ID");
		$("#a3").eq(0).html(s);
	}else{
		var s = $("#a3").eq(0).html();
		s = s.replace("系统ID", "接口地址");
		$("#a3").eq(0).html(s);
	}
	if((type == 1 || type == 2 || type == 3) && colType == 4){
		$("#a5").show();
		$("#a6").show();
	}else{
		$("#a5").hide();
		$("#a6").hide();
	}
	if((type == 4 || type == 1 || type == 2 || type == 3 || type == 5) && colType == 4){
		$("#a3").show(); 
		$("#a2").hide();
		$("#taskn").show();
		$("#taskn2").show();
	}else if(type == 7 && colType == 4){ 
		$("#a2").show();
		$("#a3").show();
		$("#taskn").show();
		$("#taskn2").show();
	}else if(colType == 2){ 
		$("#a2").hide();
		$("#a3").hide();
		$("#taskn").show();
		$("#taskn2").show();
	}else{ 
		$("#a2").hide();
		$("#a3").hide();
		$("#taskn").hide();
		$("#taskn2").hide();
	}
}

function changeCommonType(commType1){
	if(commType1 == 3) {
		$("#z").hide();
	}
	if(commType1 == 2){
		$("#y").show();
	}else{
		$("#y").hide();
	}
	if(commType1 == 6){
		$("#cardDisplay").show(); 
	}else{
		$("#cardDisplay").hide();  
	}
}

/**
 * 删除文件 
 */
function deleteFile(type){
	if(type == 1){//图标 
		$('#down_icon').hide();
		$('#up_icon').show();
		$('#iconPath').val('');
	}else if(type == 2){//背景图 
		$('#down_back').hide();
		$('#up_back').show();
		$('#backPicPath').val('');
	}else if(type == 3){//首图 
		$("#down_first").hide();
		$("#up_first").show();
		$('#firstPicPath').val('');
	} 
}

/**
 * 下载图片
 */
function downloadFile(fileType){
	if(fileType == 1){ //图标下载 
		window.open("downloadfile.do?filePath="+$('#iconPath').val());
	}else if(fileType == 2){ //背景图 
		window.open("downloadfile.do?filePath="+$('#backPicPath').val());
	}else if(fileType == 3){ //首图
		window.open("downloadfile.do?filePath="+$('#firstPicPath').val());
	}else if(fileType == 4){ // 互动手机背景图
		window.open("downloadfile.do?filePath="+$('#actMobilePic').val());
	}else if(fileType == 5){ //互动IPAD背景图 
		window.open("downloadfile.do?filePath="+$('#actIpadPic').val());
	}else if(fileType == 6){ //互动 xml
		window.open("downloadfile.do?filePath="+$('#actXmlPath').val());
	}		
}
/**
 * 切换审核方式
 */
function changeAudit(auditType){
	if(auditType==1){//手动
		$("#autotime").hide();
	}else if(auditType==2){//自动
		$("#autotime").hide();
	}else if(auditType==3){ //智能
			$("#autotime").show();
	}
}