/**
 * 删除文件 
 */
function deleteFile(type){
	if(type == 1){//图标 
		$('#down_icon').hide();
		$('#up_icon').show();
		$('#iconPath').val('');
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
	}else if(fileType == 3){ //首图
		window.open("downloadfile.do?filePath="+$('#firstPicPath').val());
	}	
}