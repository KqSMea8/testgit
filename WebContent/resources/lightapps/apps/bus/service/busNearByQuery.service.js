//定位
var city;

function GetCurrentLocation(fuc) {
	var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r) {
		if (this.getStatus() == BMAP_STATUS_SUCCESS) { //定位成功
			city = r.address.city;
			//定位成功后依据城市名和经纬度进行附近检索
			GetNearByBus(r.address.city, r.point, fuc);
		} else { //定位失败
			alert('failed' + this.getStatus());
		}
	}, {
		enableHighAccuracy: true
	});
}

var infoList = new Array();
//附近搜索
function GetNearByBus(city, mPoint, fuc) {

	// 百度地图API功能
	var map = new BMap.Map("allmap");
	var options = {
		onSearchComplete: function(results) {
			// 判断状态是否正确
			if (local.getStatus() == BMAP_STATUS_SUCCESS) {
				for (var i = 0; i < results.getCurrentNumPois(); i++) {
					//计算当前位置到站台的距离
					var distance = (map.getDistance(mPoint, results.getPoi(i).point)).toFixed(2); //获取两点距离,保留
					//封装对象
					var info = new Info(results.getPoi(i).title, results.getPoi(i).address, distance, results.getPoi(i).point);
					infoList[i] = info;

				}
				fuc(infoList);
			}
		}
	};
	var local = new BMap.LocalSearch(city, options);
	local.searchNearby('公交车站', mPoint, 1000, options);
}

function GoToMapWithPoint(i, type) {
	var station = infoList[i].station;
	var pointLat = infoList[i].point.lat;
	var pointLng = infoList[i].point.lng;
	mui.openWindow({
		url: "busMap.html?startName=&endName=&type=" + type + "&city=" + city + "&station=" + station + "&pointLat=" + pointLat + "&pointLng=" + pointLng,
		id: "info",
		show: {
			autoShow: false
		}
	});
}