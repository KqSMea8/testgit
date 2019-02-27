// JavaScript Document

/* 文章区域自适应 */
function autofitArticle(width) {
		if($("body").width() <= width){
			$("#title").css("margin-top","20px");
			$("#from").css("margin-left","0px");
			$("#pubdate").css("margin-left","0px");
			$("#text").css("margin","0px");
			$("#text img").height("auto");
			$("#text iframe").width($("body").width()*0.9-20);
			$("#text iframe").height("240");
			$("[id$='.mp4_wrapper']").width($("body").width()*0.9-20);
			$("[id$='.mp4_wrapper'] object").attr("width",$("body").width()*0.9-20);
			$("#article").css("margin","5px auto");
			$("#kit").hide(); 
		} else {
			$("#title").css("margin-top","60px");
			$("#from").css("margin-left","10px");
			$("#pubdate").css("margin-left","10px");
			$("#text").css("margin","10px");
			$("#text img").height("auto");
			$("#text iframe").width("750");
			$("#text iframe").height("500");
			$("#article").css("margin","10px auto");
			$("#kit").show(); 
		}
}

/* 来源时间自适应 */
function autofitST() {
	var liutpWidth=$("#from").width()+$("#pubdate").width()+50;
	$("#liutp").width($("#content").width());
}

/* 视频部分自适应 */
function autofitVideo() {
	if( $("#content").width() < 480 ) {
		$("video").width("98%");
	} else {
		$("video").width("auto");
	}
}  
  
/* 页面加载完成 */
$(document).ready(function(){
	autofitArticle(710);
	autofitST();
	autofitVideo(); 
});

/* 窗口大小变化 */
$(window).resize(function() {
	autofitArticle(710);
	autofitST();
	autofitVideo(); 
});