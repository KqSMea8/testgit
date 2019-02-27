package com.hanweb.weather.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.CacheException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanweb.common.task.TaskManager;
import com.hanweb.common.task.TaskScheduleBuilder;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.InterfaceLogConfig;
import com.hanweb.jmp.global.entity.jsonentity.IndexInfo;
import com.hanweb.jmp.util.HttpClientUtil;
import com.hanweb.jmp.util.PatternUtil;
import com.hanweb.weather.dao.WeatherDAO;
import com.hanweb.weather.entity.AirQuality;
import com.hanweb.weather.entity.Area;
import com.hanweb.weather.entity.Weather;
import com.hanweb.weather.entity.WeatherIndex;
import com.hanweb.weather.task.Json;
import com.hanweb.weather.task.Result;
import com.hanweb.weather.task.WeatherTask;


@Service
public class WeatherService {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private WeatherDAO weatherDao; 
	
	@Autowired
	private AirQualityService airQualityService; 
	
	@Autowired
	private WeatherIndexService indexService;
	
	private static final String[] WEEK = {"周日","周一","周二","周三","周四","周五","周六"};
	
	static{
		init();
	}
	
	/**
	 * 初始化组件任务
	 * 放在每个组件service的里面实现
	 */
	private static void init(){
		String weatherurl=StringUtil.getString(Configs.getConfigs().getWeatherurl());
		if(StringUtil.isNotEmpty(weatherurl)){
			//启用天气同步的任务
			startWeatherTask();  
			/// 天气数据正常采集线程加入任务队列
			TaskManager.addTask(new WeatherTask()); 
		}
	}
	
	/**
	 * 启用天气同步的任务
	 */
	private static void startWeatherTask() {
		WeatherTask weaTask = new WeatherTask(); 
		// 任务周期
		weaTask.setTaskSchedule(TaskScheduleBuilder.getOnceSchedule());
			// 任务id
		weaTask.setTaskId("weather_task_start");
			// 任务名称
		weaTask.setTaskName("天气预报默认启动"); 
		
		weaTask.addParam("taskId", "weather_task_start");
		// 信息同步线程启用
		TaskManager.addTask(weaTask);
	}
	
	public boolean addModify(Weather weather) throws OperationException{
		boolean bl = false;
		if(weather == null){
			return bl;
		}
		String time = getTime(weather.getDate());
		weather.setCreateTime(time);
		if(time.equals(DateUtil.getCurrDate(DateUtil.YYYY_MM_DD))){
			String date = weather.getDate();
			int start = date.lastIndexOf("：");
			int end = date.lastIndexOf(")");
			if(start >0 && end >0 && end > start){
				weather.setCurrentTemperature(date.substring(start+1, end));
			}
		}else{
			weather.setPm25("");
		}
		weather.setWeekday(weather.getDate().substring(1,2));
		int iid = findId(weather); 
		if(iid <= 0){
			bl=add(weather);
		}else{
			weather.setIid(iid);
			bl = modify(weather);
		}
		return bl;
	}
	
	public boolean addModifyList(List<Weather> weatherlist) throws OperationException{
		boolean bl = false;
		if(CollectionUtils.isEmpty(weatherlist)){
			return bl;
		}
		String time ="";
		int iid = 0;
		String weatherDate=""; 
		String temperature="";
		for(Weather weather : weatherlist){
			if(weather == null){
				continue;
			}
			weatherDate=StringUtil.getString(weather.getDate()); 
		    time = getTime(weatherDate);
			weather.setCreateTime(time); 
			if(weatherDate.length()>2  ||  time.equals(DateUtil.getCurrDate(DateUtil.YYYY_MM_DD))){ 
				if(weatherDate.indexOf("实时")>-1){ 
					weather.setCurrentTemperature(weatherDate.replaceAll(".*实时：(.*?)℃.*", "$1")); 
				}else{ 
				    //若实时温度没有，则取最低温度
					temperature=weather.getTemperature();  
					temperature=StringUtil.getStringTrim(
							temperature.substring(
								temperature.indexOf("~")+1, temperature.indexOf("℃"))); 
					weather.setCurrentTemperature(
							StringUtil.getString(NumberUtil.getInt(temperature)));  
				} 
			}else{
				weather.setPm25("");
			}
			weather.setWeekday(weather.getDate().substring(1,2));
			iid = findId(weather); 
			if(iid <= 0){
				bl=add(weather);
			}else{
				weather.setIid(iid);
				bl = modify(weather);
			}
		} 
		return bl;
	}
	
	private String getTime(String time) {
		if(time == null || time.length()<2){
			return null;
		}
		Calendar c = Calendar.getInstance();
		int nowOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int dayOfWeek = 0;
		time = time.substring(0, 2);
		for(int i=0; i<WEEK.length; i++){
			if(StringUtil.equals(WEEK[i], time)){
				dayOfWeek = i+1;
				break;
			}
		}
		if((nowOfWeek - dayOfWeek) >0){
			dayOfWeek += 7;
		}
		Date date = DateUtil.nextDay(new Date(), Math.abs(nowOfWeek - dayOfWeek));
		return DateUtil.dateToString(date, DateUtil.YYYY_MM_DD);
	}

	private int findId(Weather weather) {
		Integer iid =  weatherDao.findId(weather.getAreaId(), weather.getCreateTime());
		return NumberUtil.getInt(iid);
	}

	private boolean add(Weather weather) throws OperationException{
		if(weather == null){
			return false;
		}
		Integer iid = weatherDao.insert(weather);
		return NumberUtil.getInt(iid) > 0;
	}
	
	private boolean modify(Weather weather) throws OperationException{
		if(weather == null){
			return false;
		}
		return weatherDao.update(weather);
	}
	
	public boolean remove(String ids) throws OperationException{
		if(StringUtil.isEmpty(ids)){
			return false;
		}
		return weatherDao.deleteByIds(StringUtil.toIntegerList(ids));
	}
	
	public boolean removeBytime(String time) throws OperationException{
		if(StringUtil.isEmpty(time)){
			return false;
		}
		return weatherDao.deleteByTime(time);
	}
	
	public Weather findByTime(Integer areaId, String time){
		if(NumberUtil.getInt(areaId) <= 0 || StringUtil.isEmpty(time)){
			return null;
		}
		return weatherDao.findByTime(areaId, time);
	}
	
	/**
	 * findGroup:(查询当前天气以及后三天的天气).
	 *
	 * @param areaId
	 * @return    设定参数 .
	*/
	public List<Weather> findGroup(Integer areaId) {
		if(NumberUtil.getInt(areaId) <= 0){
			return null;
		}
		return weatherDao.findGroup(areaId);
	}
	
	public String findWeaterJson(Area areaEn){
		String jsonString="";
		Map<String, Object> josnMap = new HashMap<String, Object>(); 
		try {  
			if(areaEn==null || NumberUtil.getInt(areaEn.getIid())<=0){ 
				return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_WEATHER, 
						"参数错误，未找到城市信息");
			}
			String cityname=areaEn.getNameCn(); 
			String citycode=areaEn.getAreaCode();
			AirQuality airQuality = airQualityService.findByAreaCode(citycode);
			if(airQuality == null){
				airQuality = new AirQuality();
			}
			//查询当前天气以及后三天的天气
			List<Weather> weatherList=findGroup(areaEn.getIid());
			if(CollectionUtils.isEmpty(weatherList)){  
				return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_WEATHER, 
						"数据查询错误，未找到天气信息");
			} 
			josnMap.put("resultcode", "200");
			josnMap.put("reason", "查询成功!");
			Weather weatherEn=weatherList.get(0);
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			//当前天气
			Map<String, String> skMap = new HashMap<String, String>();
			skMap.put("city", cityname);
			skMap.put("date", DateUtil.getCurrDate("yyyy年MM月dd日"));
			skMap.put("week", "星期"+weatherEn.getWeekday());
			skMap.put("temp", weatherEn.getCurrentTemperature());
			skMap.put("wind", weatherEn.getWind());
			skMap.put("weather", weatherEn.getWeather());
			skMap.put("time", weatherEn.getUpdateTime());
			skMap.put("pm25", weatherEn.getPm25());
			skMap.put("daypicurl", weatherEn.getDayPictureUrl().replaceAll(".*/(.*)", "$1"));
			skMap.put("nightpicurl", weatherEn.getNightPictureUrl().replaceAll(".*/(.*)", "$1"));
			resultMap.put("sk", skMap);
			
			//当前空气质量
			Map<String, String> airindexMap = new HashMap<String, String>();
			airindexMap.put("publishtime", DateUtil.getCurrDate("yyyyMMdd"));
			airindexMap.put("index", airQuality.getAqi());
			airindexMap.put("advice", airQuality.getAdvice());
			airindexMap.put("datafrom", airQuality.getDataFrom());
			airindexMap.put("PM2.5", weatherEn.getPm25());
			airindexMap.put("PM10", airQuality.getPm10());
			airindexMap.put("NO2", airQuality.getNo2());
			airindexMap.put("SO2", airQuality.getSo2());
			airindexMap.put("CO", airQuality.getCo());
			airindexMap.put("O3", airQuality.getO3());
			resultMap.put("airindex", airindexMap);
			
			//当前指数天气
			List<WeatherIndex> indexList=indexService.findByTime(areaEn.getIid(), 
					DateUtil.getCurrDate(DateUtil.YYYY_MM_DD)); 
			List<IndexInfo> indexJsonList=new ArrayList<IndexInfo>();
			IndexInfo indexEn=null;
			for(WeatherIndex indexEntity:indexList){
				indexEn=new IndexInfo();
				indexEn.setTitle(indexEntity.getTitle());
				indexEn.setZs(indexEntity.getZs());
				indexEn.setTipt(indexEntity.getTipt());
				indexEn.setDes(indexEntity.getDes());
				indexJsonList.add(indexEn);
			}
			resultMap.put("index", indexJsonList);
			 
			//今天和未来3天天气
			List<Object> futureList = new ArrayList<Object>();
			Map<String, String> futureMap = null;
			Weather futureWeather = null;
			for (int i = 0; i < weatherList.size(); i++) {
				futureWeather = weatherList.get(i);
				futureMap = new HashMap<String, String>();
				futureMap.put("temperature", futureWeather.getTemperature());
				futureMap.put("daypicurl", futureWeather.getDayPictureUrl().
						replaceAll(".*/(.*)", "$1"));
				futureMap.put("nightpicurl", futureWeather.getNightPictureUrl().
						replaceAll(".*/(.*)", "$1"));
				futureMap.put("weather", futureWeather.getWeather());
				futureMap.put("wind", futureWeather.getWind());
				futureMap.put("week", "周"+futureWeather.getWeekday());
				futureMap.put("date", DateUtil.befoDay(-i, "yyyyMMdd"));
				futureList.add(futureMap);
			}
			resultMap.put("future", futureList);
			josnMap.put("result", resultMap);
			josnMap.put("error_code", "0");
		} catch (Exception e) {
			logger.error("findWeatherList error:", e);
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_WEATHER, 
					InterfaceLogConfig.ERROR_08);
		}
		return JsonUtil.objectToString(josnMap);
	}
	 
	
	public void syninfoWeather(List<Area>  areas){
		Map<String, Map<String, String>> areasMap = getPM();
	    String weatherurl=StringUtil.getString(Configs.getConfigs().getWeatherurl()); 
	    List<WeatherIndex> indexDataList = new ArrayList<WeatherIndex>();
		List<Weather> weatherDataList = new ArrayList<Weather>();
		List<AirQuality> airDataList = new ArrayList<AirQuality>();
		String area  = "";
		String url = "";
		String chartSet = "UTF-8";
		String jsonStr = "";  
		int i=0;
		try{
			for(Area areaEn : areas){
				if(areaEn==null){
					continue;
				}
				area  = areaEn.getNameCn();  
				if(StringUtil.isEmpty(area)){
					continue;
				} 
	             
			    url=weatherurl.replace("{area}", area);  
				jsonStr = HttpClientUtil.getInfo(url, chartSet); 
				if(StringUtil.isEmpty(jsonStr) || jsonStr.indexOf("\"error\":-3") > 0){
					logger.error("城市："+area+"的jsonStr数据为空或格式不正确");
					continue;
				}
				Json json = JsonUtil.StringToObject(jsonStr, Json.class);
				if(json==null || json.getResults()==null){
					logger.error("城市："+area+"的json.getResults()数据为空");
					continue;
				}
				Result result = json.getResults().get(0);
				List<WeatherIndex> indexList = result.getIndex();
				List<Weather> weatherList = result.getWeather_data(); 
				//解析指数
				if(indexList != null){
					for(WeatherIndex index : indexList){
						index.setCreateTime(DateUtil.getCurrDate(DateUtil.YYYY_MM_DD));
						index.setAreaId(areaEn.getIid());
						index.setUpdateTime(DateUtil.getCurrDate("HH:mm"));
						indexDataList.add(index);
						if(i>0 && i%100==0){
							indexService.addModifyList(indexDataList);
							indexDataList=new ArrayList<WeatherIndex>(); 
						}
						
					}
				}
				//解析天气
				if(weatherList != null){
					for(Weather weather : weatherList){
						weather.setAreaId(areaEn.getIid());
						weather.setPm25(result.getPm25());
						weather.setUpdateTime(DateUtil.getCurrDate("HH:mm"));
						weatherDataList.add(weather);
						if(i>0 && i%100==0){
							addModifyList(weatherDataList);
							weatherDataList=new ArrayList<Weather>(); 
						} 
					}
				}
				//解析空气质量
				Map<String, String> airMap = areasMap.get(areaEn.getNameCn());
				if(airMap != null){
					AirQuality airQuality = new AirQuality();
					airQuality.setAreaCode(areaEn.getAreaCode());
					airQuality.setName(areaEn.getNameCn());
					airQuality.setAqi(airMap.get("AQI"));
					airQuality.setAdvice(airMap.get("advice"));
					airQuality.setPm25(airMap.get("PM2.5"));
					airQuality.setPm10(airMap.get("PM10"));
					airQuality.setCo(airMap.get("CO"));
					airQuality.setNo2(airMap.get("NO2"));
					airQuality.setO3(airMap.get("O3"));
					airQuality.setSo2(airMap.get("SO2"));
					airQuality.setDataFrom(airMap.get("datafrom"));
					airQuality.setUpdateTime(DateUtil.stringtoDate(airMap.get("updatetime"), 
							DateUtil.YYYY_MM_DD_HH_MM_SS) ); 
					airDataList.add(airQuality);
					if(i>0 && i%100==0){
						airQualityService.addModifyList(airDataList);
						airDataList=new ArrayList<AirQuality>(); 
					}  
				}
				i=i+1;
			}
			if(!CollectionUtils.isEmpty(indexDataList)){
				indexService.addModifyList(indexDataList);
			}
			if(!CollectionUtils.isEmpty(weatherDataList)){
				addModifyList(weatherDataList);
			}
			if(!CollectionUtils.isEmpty(airDataList)){
				airQualityService.addModifyList(airDataList);
			}
		}catch (OperationException e) { 
			logger.error("syninfoWeather error:", e);
		}catch (CacheException e) { 
			logger.error("syninfoWeather error:", e); 
		}  
	}
	
	public Map<String, Map<String, String>> getPM(){
		Map<String, Map<String, String>> areasMap = new HashMap<String, Map<String,String>>();
		String result = HttpClientUtil.getInfo("http://www.pm25.in/rank", "utf-8");
		if("connect error".equals(result)){
			return areasMap;
		}
		result = result.replaceAll("\n", "");
		result = result.replace("<br>", "");
		String updateTime = result.replaceAll(".*<p>数据更新时间：(.*?)</p>.*", "$1");
		
		result = new PatternUtil().replaceStr(result, ".*<tbody>(.*?)</tbody>.*", "$1"); 
		String regex = ".*?href.*?>(.*?)<.*?<td>(.*?)</td>.*?<td>(.*?)</td>.*?<td>(.*?)" +
				"</td>.*?<td>(.*?)</td>.*?<td>(.*?)</td>.*?<td>(.*?)</td>.*?<td>(.*?)</td>.*?" +
				"<td>(.*?)</td>.*?<td.*?>(.*?)</td>.*?<td>(.*?)</td>.*?</tr>";
		result = new PatternUtil().replaceStr(result, regex, "$1,$2,$3,$4,$5,$6,$7,$8,$9,$10,$11;");
		String[] areas = StringUtil.split(result, ";");
		Map<String, String> areaMap = null;
		if(areas.length > 0){
			for (String area : areas) {
				if(StringUtil.isEmpty(area)){
					continue;
				}
				areaMap = new HashMap<String, String>(); 
				String[] datas = area.split(",");
				areaMap.put("name", datas[0]);
				areaMap.put("AQI", datas[1]);
				areaMap.put("advice", datas[2]);
				areaMap.put("datafrom", "PM25.in");
				areaMap.put("PM2.5", datas[4]);
				areaMap.put("PM10", datas[5]);
				areaMap.put("CO", datas[6]);
				areaMap.put("NO2", datas[7]);
				areaMap.put("O3", datas[8]);
				areaMap.put("SO2", datas[10]);
				areaMap.put("updatetime", updateTime);
				areasMap.put(datas[0], areaMap);
			}
		}
		return areasMap;
	}
}
