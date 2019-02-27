//判断容器类型，浏览器还是APP
var container = containerT();
if(container == "web") {
	document.getElementById("header").style.display = "block";
} else {
	document.getElementById("header").style.display = "none";
	document.getElementById("paddingtop").style.paddingTop = 0;
}

(function($) {
	var callback = function(results) {
		var example1 = new Vue({
			el: '#parent',
			data: {
				items: []
			}
		})
		if(results.getCurrentNumPois() > 0) {
			var hasinfo = '';
			for(var i = 0; i < results.getCurrentNumPois(); i++) {
				if(results.getPoi(i).type == 1) {
					hasinfo = 'true';
					example1.items.push({
						station: results.getPoi(i).title,
						passlines: results.getPoi(i).address
					});
				}
				if(i == 0) {
					document.getElementById("parent").style.display = "block";
				}
			}
			if(hasinfo != 'true') {
				alert("查询无结果!");
			}
		}
	}
	GetCurrentLocation(callback);
})(mui);

function stationCellClicked(station, passlines) {
	var stationAndPasslines = station + "+" + passlines;

	var historyStation = getCookie("historyStation");
	var results = historyStation.split(",");
	if(results.indexOf(stationAndPasslines) < 0) {
		if(results.length > 4) {
			//只保存五条元素，因此添加元素时若超过五条要删除最后一条
			results.pop(); //删除数组最后一个元素
		}
		historyStation = results.join(",");
		historyStation = historyStation ? (stationAndPasslines + "," + historyStation) : stationAndPasslines;
		setCookie("historyStation", historyStation);
	}

	mui.openWindow({
		url: "busStationDetail.html?stationName=" + station + "&passlines=" + passlines,
		id: "info",
		show: {
			autoShow: false
		}
	});
}