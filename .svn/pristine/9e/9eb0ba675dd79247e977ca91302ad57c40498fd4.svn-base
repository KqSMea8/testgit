//判断容器类型，浏览器还是APP
var container = containerT();
if (container == "web") {
	document.getElementById("header").style.display = "block";
}else{
	document.getElementById("header").style.display = "none";
	document.getElementById("paddingtop").style.paddingTop = 0;
}
function GetBusChangeDetail(changeLine, changeDetail, changeDescription, index) { //index为li的索引
	mui.openWindow({
		url: "busBusChangeDetail.html?myPointLng=" + myPoint.lng + "&myPointLat=" + myPoint.lat + "&city=" + city + "&index=" + index + "&startName=" + startName + "&endName=" + endName + "&changeLine=" + changeLine + "&changeDetail=" + changeDetail + "&changeDescription=" + changeDescription,
		id: "info",
		show: {
			autoShow: false
		}
	});
};

(function($) {
	var callback = function(results) {
		var example1 = new Vue({
			el: '#parent',
			data: {
				items: []
			}
		})
		if (results.length > 0) {
			for (var i = 0; i < results.length; i++) {
				example1.items.push({
					changeLine: results[i].changeLine,
					changeDetail: results[i].changeDetail,
					changeDescription: results[i].changeDescription
				});

				if (i == 0) {
					document.getElementById("parent").style.display = "block";
				}
			}
		}
	}
	GetCurrentLocation(callback);
})(mui);