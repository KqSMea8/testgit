//定位
function GetCurrentLocation(fuc) {
	var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r) {
		if (this.getStatus() == BMAP_STATUS_SUCCESS) { //定位成功
			//定位成功后依据城市名进行公交车站检索
			GetStation(r.address.city, fuc);
		} else { //定位失败
			alert('failed' + this.getStatus());
		}
	}, {
		enableHighAccuracy: true
	});
}

function GetStation(city, fuc) {
	var options = {
		onSearchComplete: function(results) {
			// 判断状态是否正确
			if (local.getStatus() == BMAP_STATUS_SUCCESS) {
				fuc(results);
			}
		}
	};
	var local = new BMap.LocalSearch(city, options);
	local.search(stationName);
}