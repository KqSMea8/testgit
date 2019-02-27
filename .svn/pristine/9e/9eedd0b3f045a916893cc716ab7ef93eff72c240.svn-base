//判断容器类型，浏览器还是APP
var container = containerT();
if(container == "web") {
	document.getElementById("header").style.display = "block";
} else {
	document.getElementById("header").style.display = "none";
	document.getElementById("sliderSegmentedControl").style.paddingTop = 0;
}

function GoToMap(type) {
	var ul = document.getElementById('parent');
	var ul_lis = ul.getElementsByTagName('li');
	var j;
	//使用遍历获取点击的li的index索引
	for(var i = 0; i < ul_lis.length; i++) {
		ul_lis[i].index = i;
		ul_lis[i].onclick = function() {
			j = this.index;
			GoToMapWithPoint(j, type);
		}
	}
	//当显示的地图为换乘步行方案时
	if(type == "步行" || type == "驾车") {
		var startName = document.getElementById("startTF").value;
		var endName = document.getElementById("endTF").value;
		if(!(new RegExp("\\S+")).test(startName)) { //判断字符串是否为空或都是空格
			mui.toast("请输入起点");
		} else if(!(new RegExp("\\S+")).test(endName)) {
			mui.toast("请输入终点");
		} else if(startName.replace(/(^\s*)|(\s*$)/g, "") === endName.replace(/(^\s*)|(\s*$)/g, "")) {
			mui.toast("起点和终点不能相同");
		} else {
			mui.openWindow({
				url: "busMap.html?startName=" + startName + "&endName=" + endName + "&type=" + type + "&city=" + city + "&station=" + "&pointLat=" + "&pointLng=",
				id: "info",
				show: {
					autoShow: false
				}
			});
		}
	}
}

//点击“站点”页面的查询按钮
function StationQueryBtnClicked() {

	var stationName = document.getElementById("stationTF").value;
	if(!(new RegExp("\\S+")).test(stationName)) { //判断字符串是否为空或都是空格
		mui.toast("请输入站点名");
	} else {
		mui.openWindow({
			url: "busStationQuery.html" + "?stationName=" + stationName,
			id: "info",
			extras: {
				name: '站点查询的页面'
			},
			show: {
				autoShow: false
			}
		});
	}

};

function setCookie(name, value, iDay) {
	var oDate = new Date();

	oDate.setDate(oDate.getDate() + iDay);

	document.cookie = name + '=' + encodeURIComponent(value) + ';expires=' + oDate;
}

function getCookie(name) {
	var arr = document.cookie.split('; ');
	var i = 0;
	for(i = 0; i < arr.length; i++) {
		//arr2->['username', 'abc']
		var arr2 = arr[i].split('=');

		if(arr2[0] == name) {
			var getC = decodeURIComponent(arr2[1]);
			return getC;
		}
	}

	return '';
}

function delCookie(name) {
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = getCookie(name);
	if(cval != null){
		document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
		var cval2 = getCookie(name);
		if(cval2 != null){
			document.cookie = name + "=" + cval2 + ";expires=" + exp.toGMTString();
		}
	}
}
//点击线路查询按钮
function LineQueryBtnClicked() {
	var lineName = document.getElementById("lineTF").value;
	if(lineName.length > 15) {

		mui.toast('输入线路名过长请重新输入');
		return;
	}
	if(!(new RegExp("\\S+")).test(lineName)) { //判断字符串是否为空或都是空格
		mui.toast("请输入线路名");
	} else {
		var historyLine = getCookie("historyLine");
		var results = historyLine.split(",");
		if(results.indexOf(lineName) < 0) {
			if(results.length > 4) {
				//只保存五条元素，因此添加元素时若超过五条要删除最后一条
				results.pop(); //删除数组最后一个元素
			}
			historyLine = results.join(",");
			historyLine = historyLine ? (lineName + "," + historyLine) : lineName;
			setCookie("historyLine", historyLine);
		}
		mui.openWindow({
			url: "busLineDetail.html" + "?lineName=" + lineName,
			id: "info",
			extras: {
				name: '站点查询的页面'
			},
			show: {
				autoShow: false
			}
		});
	}

};

//点击线路的历史记录
function lineHistoryClicked(lineName) {
	document.getElementById("lineTF").value = lineName;
	LineQueryBtnClicked();
}

//点击“换乘”的公交查询按钮
function BusChangeQueryBtnClicked() {
	var startName = document.getElementById("startTF").value;
	var endName = document.getElementById("endTF").value;
	if(!(new RegExp("\\S+")).test(startName)) { //判断字符串是否为空或都是空格
		mui.toast("请输入起点");
	} else if(!(new RegExp("\\S+")).test(endName)) {
		mui.toast("请输入终点");
	} else if(startName.replace(/(^\s*)|(\s*$)/g, "") === endName.replace(/(^\s*)|(\s*$)/g, "")) {
		mui.toast("起点和终点不能相同");
	} else {
		mui.openWindow({
			url: "busBusChange.html" + "?startName=" + startName + "&endName=" + endName,
			id: "info",
			show: {
				autoShow: false
			}
		});
	}
}

//点击起始点交换按钮
function changeStartAndEnd() {
	var startName = document.getElementById("startTF").value;
	var endName = document.getElementById("endTF").value;
	document.getElementById("startTF").value = endName;
	document.getElementById("endTF").value = startName;
}
//“换乘”的textField的值改變時發生的事件
function BusChangeTFChanged(tfId) {
	var example4 = new Vue({
		el: '#parent4',
		data: {
			items4: [{
				title: ''
			}]
		}
	})

	var keyword = document.getElementById(tfId).value;
	// 百度地图API功能
	var options = {
		onSearchComplete: function(results) {
			// 判断状态是否正确
			if(local.getStatus() == BMAP_STATUS_SUCCESS) {
				//				var s = [];
				var table = document.body.querySelector('.mui-table-view');
				var ul = document.getElementById('parent4');
				ul.innerHTML = "";
				//				$("ul").find("li").remove();
				for(var i = 0; i < results.getCurrentNumPois(); i++) {
					//					s.push(results.getPoi(i).title + ", " + results.getPoi(i).address);
					//					example4.items4.push({
					//						title: results.getPoi(i).title
					//					});
					var searchtitle = "<a onclick=\"KeywordClicked('{tfid}','{resultstitle}');return false;\"><img class=\"mui-pull-left\" id=\"leftImage\" src=\"../source/images/search.png\">" + "<div>{itemtitle}</div></a>";
					var title = results.getPoi(i).title;

					var li = document.createElement('li');
					var titlecontent;
					li.className = 'mui-table-view-cell  mui-scroll-wrapper muihistory';
					li.style = "margin-left:5%;width: 90%;border-bottom-width: 1px solid #f2f2f2;";
					titlecontent = searchtitle.replace(/{itemtitle}/, title).replace(/{tfid}/, tfId).replace(/{resultstitle}/, title);
					li.innerHTML = titlecontent;
					ul.appendChild(li);

					if(i == 0) {
						document.getElementById("parent4").style.display = "block";
					}
				}
			}
		}
	};

	example4.items4.pop();
	var local = new BMap.LocalSearch(city, options);
	local.search(keyword);
	//	alert("搜索");
}

window.onload = function GetData() {
	//	换乘起点默认为我的位置
	document.getElementById("startTF").value = "我的位置";
	GetNearByData();
	ShowHistory();
}

function KeywordClicked(tfId, title) {
	document.getElementById(tfId).value = title;
}

function GetNearByData() {
	var callback = function(results) {
		var example1 = new Vue({
			el: '#parent',
			data: {
				items: []
			}
		})
		if(results.length > 0) {
			for(var i = 0; i < results.length; i++) {
				example1.items.push({
					station: results[i].station,
					passlines: results[i].passlines,
					distance: results[i].distance
				});
				if(i == 0) {
					document.getElementById("parent").style.display = "block";
				}
			}
			//自适应屏幕大小
			var reducetopheight = document.getElementById('header').offsetHeight + document.getElementById('sliderSegmentedControl').offsetHeight;
			document.getElementById("item1mobile").style.minHeight = (document.body.scrollHeight - reducetopheight) + "px";
			document.getElementById("scroll1").style.minHeight = (document.body.scrollHeight - reducetopheight) + "px";
			document.getElementById("scroll2").style.minHeight = (document.body.scrollHeight - reducetopheight) + "px";
			document.getElementById("scroll3").style.minHeight = (document.body.scrollHeight - reducetopheight) + "px";
			document.getElementById("scroll4").style.minHeight = (document.body.scrollHeight - reducetopheight) + "px";

			document.getElementById("item2mobile").style.minHeight = (document.body.scrollHeight - reducetopheight) + "px";

			document.getElementById("item3mobile").style.minHeight = (document.body.scrollHeight - reducetopheight) + "px";

			document.getElementById("item4mobile").style.minHeight = (document.body.scrollHeight - reducetopheight) + "px";

			//			数据加载成功后下拉刷新结束
			mui('#scroll1').pullRefresh().endPulldownToRefresh();

		}
	}
	GetCurrentLocation(callback);
}

function ShowHistory() {
	var example2 = new Vue({
		el: '#parent2',
		data: {
			items2: []
		}
	})
	var historyLine = getCookie("historyLine");
	var results2 = [];
	if(historyLine != "") {
		results2 = historyLine.split(",");
	}

	if(results2.length > 0) {
		for(var i = 0; i < results2.length; i++) {
			example2.items2.push({
				history: results2[i].split("+")[0]
			});
			if(i == 0) {
				document.getElementById("parent2").style.display = "block";
			}
		}
	}

	var example3 = new Vue({
		el: '#parent3',
		data: {
			items3: []
		}
	})
	var historyStation = getCookie("historyStation");
	var result3 = [];
	if(historyStation != "") {
		result3 = historyStation.split(",");
	}
	if(result3.length > 0) {
		for(var i = 0; i < result3.length; i++) {
			//			var stationName = ;
			example3.items3.push({
				history: result3[i].split("+")[0],
				passlines: result3[i].split("+")[1]
			});
			if(i == 0) {
				document.getElementById("parent3").style.display = "block";
			}
		}
	}
}

function clearHistory(name) {
	delCookie(name);
	if(name == "historyStation") {
		document.getElementById("parent3").style.display = "none";
	}
	if(name == "historyLine") {
		document.getElementById("parent2").style.display = "none";
	}
//	mui.toast("删除cookie");
}

function pullToRefresh() {
	GetNearByData();
}

function dragToGetMore() {
	this.endPullupToRefresh(true);
	mui('#scroll1').pullRefresh().endPullupToRefresh();
}