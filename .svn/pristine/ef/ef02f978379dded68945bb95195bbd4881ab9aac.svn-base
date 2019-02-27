/**
 * 文件管理
 */
UE.plugins['filemanager'] = function() {
	var me = this;
	
	me.commands['insertimg'] = {
		execCommand : function() {
			FileSelector.selectPic(function(checkedPics) {
				var html = '';
				alert(checkedPics);
				//解析返回的json，自己也可以是xml或者数组或者其他的，自己解析，组织要插入的html

//				$.each(checkedPics, function(index, pic) {
//					html += '<img title="'+pic.name+'" src="'+pic.filepath+'"';
//					if (typeof picWidth != 'undefined') {
//						html += ' width="'+picWidth+'"';
//					}else if(typeof pic.fileWidth != 'undefined'){
//						html += ' width="'+pic.fileWidth+'"';
//					}
//					if (typeof picHeight != 'undefined') {
//						html += ' height="'+picHeight+'"';
//					}else if(typeof pic.fileHeight != 'undefined'){
//						html += ' height="'+pic.fileHeight+'"';
//					}
//					html += ' />';
//				});


				//插入编辑器
				me.execCommand( 'inserthtml', html);
			}, false);
		}
	};
	
	me.commands['insertattach'] = {
		execCommand : function() {
			FileSelector.selectAttach(function(checkedAttachs) {
				var html = '';
				$.each(checkedAttachs, function(index, attach) {
					html += '<a href="'+attach.filepath+'">'+attach.name+'</a>';
				});
				me.execCommand( 'inserthtml', html);
			}, false);
		}
	};
	
	me.commands['insertmedia'] = {
		execCommand : function() {
			FileSelector.selectMedia(function(checkedMedia, mediaWidth, mediaHeight) {
				var html = '';
				$.each(checkedMedia, function(index, media) {
					html += '<embed ' +
					'name="media_placeholder" id="mediaContent' + index + '" '+
					'src="'+contextPath+'/ui/widgets/ckplayer/ckplayer.swf" '+
					'flashvars="f='+media.filepath+
					'&p=0" quality="autohigh" width="200" height="200" align="middle" wmode="transparent"'+
					' type="application/x-shockwave-flash" allowScriptAccess="always" allowFullscreen="true"';
					if (mediaWidth) {
						html += ' width="'+mediaWidth+'"';
					}
					if (mediaHeight) {
						html += ' height="'+mediaHeight+'"';
					}
					html += '></embed>';
				});
				me.execCommand( 'inserthtml', html);
			}, false);
		}
	};
};