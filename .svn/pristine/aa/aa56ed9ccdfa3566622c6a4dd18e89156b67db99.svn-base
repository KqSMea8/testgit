var myPoint;
var city;
//定位
function GetCurrentLocation(fuc) {
	var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r) {
		if (this.getStatus() == BMAP_STATUS_SUCCESS) { //定位成功
			myPoint = r.point;
			city = r.address.city;
		//定位成功后依据城市名进行起始点转码
			GetBusChange(r.address.city,fuc);
//			GeocoderLocationByName(r.address.city);
		} else { //定位失败
			alert('failed' + this.getStatus());
		}
	}, {
		enableHighAccuracy: true
	});
}
//function GeocoderLocationByName(city) {
//	// 创建地址解析器实例
//	var myGeo = new BMap.Geocoder();
//	// 将地址解析
//	myGeo.getPoint(startName, function(startPoint){
//		if (startPoint) {//起始点解析成功继续对终点解析
//			
//		}else{
//			alert("您选择地址没有解析到结果!");
//		}
//	}, city);
//}

function GetBusChange(city,fuc) {
	var infoList = new Array();
	var transit = new BMap.TransitRoute(city, {onSearchComplete: function(result){   
		if (transit.getStatus() == BMAP_STATUS_SUCCESS){
			for (var i = 0; i < result.getNumPlans(); i++) {
				var walkDistance = 0;
				var busLine = "";
				var changeDetail;
				var plan = result.getPlan(i);
				var time = plan.getDuration(true);
				var description = plan.getDescription(true);
//				非常重要，将description转为HTMLString类型的才能在函数中作为参数传递
				document.getElementById("changeDescription").innerHTML = description;
				description =  document.getElementById("changeDescription").innerHTML;
				var distance = plan.getDistance(false);//单位为米
				for (var j = 0; j < plan.getNumRoutes(); j++) {//步行路段
					walkDistance += plan.getRoute(j).getDistance(false);
				}
				for (var k = 0; k < plan.getNumLines(); k++) {//公交
					var line = plan.getLine(k).title.split("(")[0];
					busLine = busLine == ""? line : (busLine + "-" + line);
				}
				changeDetail = time+"|"+"全程"+distance+"|"+"步行"+walkDistance+"米";
				var info = new Info(busLine,changeDetail,description);
				
				
				infoList[i] = info;
			}
			fuc(infoList);
		}
	}
	});
	transit.search(startName,endName);
	if (startName.replace(/(^\s*)|(\s*$)/g, "")==="我的位置") {//去除字符串左右两端的空格
		transit.search(myPoint,endName);
	} else if (endName.replace(/(^\s*)|(\s*$)/g, "")==="我的位置") {
		transit.search(startName,myPoint);
	}
}