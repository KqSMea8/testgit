//判断容器类型，浏览器还是APP
var container = containerT();
if (container == "web") {
	document.getElementById("header").style.display = "block";
}else{
	document.getElementById("header").style.display = "none";
	document.getElementById("topinde").style.paddingTop = 0;
}
function GoToMap() {
	mui.openWindow({
		//此处为查询线路的站点详情地图，startName为线路的名称，endName为线路的上下行
		url: "busMap.html?startName=" + lineName + "&endName=" + sliderIndex + "&type=" + "线路" + "&city=" + city + "&station=" + "&pointLat=" + "&pointLng=",
		id: "info",
		show: {
			autoShow: false
		}
	});
}

(function($) {

	var callback = function(results1, results2, time1, time2, uid) {
		//		callback里面的方法只能走一次才能把上下行的数据全部替换，走第二次的时候替换是不成功的（非常重要）
		var example1 = new Vue({
			el: '#parent',
			data: {
				items: []
			}
		})
		var example2 = new Vue({
			el: '#parent2',
			data: {
				itemsx: []
			}
		})

		if (results1.getNumBusStations() > 0) { //替换上下数据
			document.getElementById("time").innerHTML = "运营时间:&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + time1;
			for (var i = 0; i < results1.getNumBusStations(); i++) {
				example1.items.push({
					station: results1.getBusStation(i).name
				});
				if (i == 0) {
					document.getElementById("parent").style.display = "block";
				}
			}
			//自适应屏幕大小
			document.getElementById("item1mobile").style.minHeight = (document.body.scrollHeight - document.getElementById('header').offsetHeight - document.getElementById('topinde').offsetHeight) + "px";
		}

		if (results2.getNumBusStations() > 0) { //替换下行数据
			document.getElementById("time2").innerHTML = "运营时间:&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + time2;
			for (var i = 0; i < results2.getNumBusStations(); i++) {
				example2.itemsx.push({
					station: results2.getBusStation(i).name
				});
				if (i == 0) {
					document.getElementById("parent2").style.display = "block";
				}
			}
			//自适应屏幕大小
			document.getElementById("item2mobile").style.minHeight = (document.body.scrollHeight - document.getElementById('header').offsetHeight - document.getElementById('topinde').offsetHeight) + "px";
		}
	}
	GetCurrentLocation(callback);
})(mui);