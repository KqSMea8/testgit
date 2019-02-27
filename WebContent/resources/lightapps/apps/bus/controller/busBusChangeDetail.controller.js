

//判断容器类型，浏览器还是APP
var container = containerT();
if (container == "web") {
	document.getElementById("header").style.display = "block";
}else{
	document.getElementById("header").style.display = "none";
	document.getElementById("paddingtop").style.paddingTop = 0;
}
function GoToMap(type) {
	mui.openWindow({
		url: "busMap.html?startName=" + startName + "&endName=" + endName + "&type=" + type + "&city=" + city + "&station=" + index + "&pointLat=" + pointLat + "&pointLng=" + pointLng, //此处的statio为index
		id: "info",
		show: {
			autoShow: false
		}
	});
}