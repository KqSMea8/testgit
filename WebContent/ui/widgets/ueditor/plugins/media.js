UE.registerUI('insertmedia',function(editor,uiName){
    //注册按钮执行时的command命令，使用命令默认就会带有回退操作
    editor.registerCommand(uiName,{
        execCommand:function(){
		FileSelector.selectMedia(function(checkedPics) {
			var html = '';
			//解析返回的json，自己也可以是xml或者数组或者其他的，自己解析，组织要插入的html

			var pic = checkedPics.split(',');
			 for (i=0;i<pic.length ;i++ )   
			    {   
				 html += '<video width="100%" controls="controls" src="'+pic[i]+'"';
				 html += ' />';
			    }   
			//插入编辑器
			editor.execCommand( 'inserthtml', html);
		}, false);
        }
    });

    //创建一个button
    var btn = new UE.ui.Button({
        //按钮的名字
        name:uiName,
        //提示
        title:'视频',
        //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
        cssRules :'background-position: -320px -20px;',
        //点击时执行的命令
        onclick:function () {
            //这里可以不用执行命令,做你自己的操作也可
           editor.execCommand(uiName);
        }
    });

    //当点到编辑内容上时，按钮要做的状态反射
    editor.addListener('selectionchange', function () {
        var state = editor.queryCommandState(uiName);
        if (state == -1) {
            btn.setDisabled(true);
            btn.setChecked(false);
        } else {
            btn.setDisabled(false);
            btn.setChecked(state);
        }
    });

    //因为你是添加button,所以需要返回这个button
    return btn;
}/*index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮*/);

