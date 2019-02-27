var FileSelector = {
	selectPic: function(callback) {
	    //素材管理-图片
		var url = contextPath + '/manager/matter/picture/showlist.do';
		openDialog(url, 1100, 550, {
			title : '选择图片',
			callback: callback
		});
	},
	selectAttach: function(callback) {
		var url = contextPath + '/manager/matter/doc/showlist.do';
		openDialog(url, 1100, 550, {
			title : '选择附件',
			callback: callback
		});
	},
	selectMedia: function(callback) {
		var url = contextPath + '/manager/matter/video/showlist.do';
		openDialog(url, 1100, 550, {
			title : '选择视频',
			callback: callback
		});
	}
}