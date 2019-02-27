//定位
var city;

function GetCurrentLocation(fuc) {

	var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r) {
		if(this.getStatus() == BMAP_STATUS_SUCCESS) { //定位成功
			//定位成功后依据城市名和经纬度进行附近检索
			GetLineDetail(r.address.city, fuc);
			city = r.address.city;

		} else { //定位失败
			alert('failed' + this.getStatus());
		}
	}, {
		enableHighAccuracy: true
	});
}

function GetLineDetail(city, fuc) {
	var uid = 0;

	var result1;
	var result2;
	var time1;
	var time2;
	// 百度地图API功能
	var busline = new BMap.BusLineSearch(city, {
		onGetBusListComplete: function(result) {

			if(result) {
				console.log(result);
				if(result.NA.length ==0||result.NA.length ==1) {
					alert('查询无结果');
				} else {
					for(var i = 0; i < 2; i++) {
						var fstLine = result.getBusListItem(i); //获取某一个具体的公交列表对象 0上行 1下行
						if(i == 0) {
							if(fstLine == undefined) {
								alert('上行查询无结果');
							}
						} else {
							if(fstLine == undefined) {
								alert('下行查询无结果');
							}
						}
						busline.getBusLine(fstLine); //返回busLine对象
					}

					
				}

			}
		},
		onGetBusLineComplete: function(result) {
			uid++;
			if(result) {
				if(uid == 1) {
					result1 = result;
					time1 = result.startTime + "--" + result.endTime;
				} else if(uid == 2) {
					result2 = result;
					time2 = result.startTime + "--" + result.endTime;
				}
			}
			if(uid == 2) { //控制回调只走一次，否则第二部分无法塞值
				fuc(result1, result2, time1, time2, uid);
			}
		},

	});

	function busSearch() {
		busline.getBusList(lineName);
	}
	setTimeout(function() {
		busSearch();
	}, 1500);
}