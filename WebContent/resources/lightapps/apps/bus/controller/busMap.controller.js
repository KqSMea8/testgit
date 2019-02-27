//判断容器类型，浏览器还是APP
var container = containerT();
if (container == "web") {
	document.getElementById("header").style.display = "block";
}else{
	document.getElementById("header").style.display = "none";
	document.getElementById("allmap").style.paddingTop = 0;
}
(function($) {
	if (type == "地图") { //点击地图
		// 百度地图API功能
		var map = new BMap.Map("allmap"); // 创建Map实例
		var point = new BMap.Point(pointLng, pointLat);
		map.centerAndZoom(point, 20); // 初始化地图,设置中心点坐标和地图级别
		map.addControl(new BMap.MapTypeControl()); //添加地图类型控件
		map.setCurrentCity(city); // 设置地图显示的城市 此项是必须设置的
		map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
		var marker = new BMap.Marker(point);
		map.addOverlay(marker);
		var label = new BMap.Label(station, {
			offset: new BMap.Size(20, -10)
		});
		marker.setLabel(label);
		// 添加带有定位的导航控件
		var navigationControl = new BMap.NavigationControl({
			// 靠左上角位置
			anchor: BMAP_ANCHOR_TOP_LEFT,
			// LARGE类型
			type: BMAP_NAVIGATION_CONTROL_LARGE,
			// 启用显示定位
			enableGeolocation: true
		});
		map.addControl(navigationControl);
		// 添加定位控件
		var geolocationControl = new BMap.GeolocationControl();
		geolocationControl.addEventListener("locationSuccess", function(e) {
			// 定位成功事件
		});
		geolocationControl.addEventListener("locationError", function(e) {
			// 定位失败事件
			mui.toast(e.message);
		});
		map.addControl(geolocationControl);
	} else if (type == "街景") { //点击街景
		var map = new BMap.Map('allmap');
		var point = new BMap.Point(pointLng, pointLat);
		map.centerAndZoom(point, 12); // 初始化地图,设置中心点坐标和地图级别
		map.addTileLayer(new BMap.PanoramaCoverageLayer());
		var panorama = new BMap.Panorama('allmap');
		panorama.setPov({
			heading: -40,
			pitch: 6
		}); 
		panorama.setPosition(new BMap.Point(pointLng, pointLat)); //根据经纬度坐标展示全景图		
	} else if (type == "步行") {
		// 百度地图API功能
		var map = new BMap.Map("allmap");
		map.centerAndZoom(city, 11);
		var walking = new BMap.WalkingRoute(city, {
			renderOptions: {
				map: map,
				autoViewport: true
			}
		});
		walking.search(startName, endName);
	} else if (type == "驾车") {
		// 百度地图API功能
		var map = new BMap.Map("allmap");
		map.centerAndZoom(city, 12);
		var driving = new BMap.DrivingRoute(city, {
			renderOptions: {
				map: map,
				autoViewport: true
			}
		});
		driving.search(startName, endName);
	} else if (type == "线路") {
		// 百度地图API功能
		var map = new BMap.Map("allmap"); // 创建Map实例
		map.centerAndZoom(city, 12);
		var busline = new BMap.BusLineSearch(map, {
			renderOptions: {
				map: map
			},
			onGetBusListComplete: function(result) {
				if (result) {
					var fstLine = result.getBusListItem(endName); //获取第一个公交列表显示到map上
					busline.getBusLine(fstLine);
				}
			}
		});

		function busSearch() {
			busline.getBusList(startName);
		}
		setTimeout(function() {
			busSearch();
		}, 1500);
	} else if (type == "换乘") {
		var map = new BMap.Map("allmap");
		map.centerAndZoom(city, 12);
		map.enableScrollWheelZoom();

		var transit = new BMap.TransitRoute(map, {
			renderOptions: {
				map: map
			},
			onSearchComplete: function(result) {
				if (transit.getStatus() == BMAP_STATUS_SUCCESS) {

					var firstPlan = result.getPlan(station);
					//绘制步行线路
					for (var i = 0; i < firstPlan.getNumRoutes(); i++) {
						var walk = firstPlan.getRoute(i);
						if (walk.getDistance(false) > 0) {
							// 步行线路有可能为0
							map.addOverlay(new BMap.Polyline(walk.getPath(), {
								lineColor: "green"
							}));
						}
					}
					// 绘制公交线路
					for (i = 0; i < firstPlan.getNumLines(); i++) {
						var line = firstPlan.getLine(i);
						map.addOverlay(new BMap.Polyline(line.getPath()));
						// map.addOverlay(line.getPolyline());
					}
				}
			}
		});
		//		transit.setPolicy(3);
		transit.search(startName, endName);
	}
	//自适应屏幕大小
	document.getElementById("allmap").style.minHeight = (document.body.scrollHeight - 44) + "px";
})(mui);