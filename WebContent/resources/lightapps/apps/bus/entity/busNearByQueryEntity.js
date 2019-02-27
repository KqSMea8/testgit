//信息实体
function Info(station, passlines, distance, point){
	//站台名称
	this.station = station;       
	//站台线路
	this.passlines = passlines;
	//当前位置到站台的距离
	this.distance = distance;
	//当前站台的经纬度坐标
	this.point = point;
}