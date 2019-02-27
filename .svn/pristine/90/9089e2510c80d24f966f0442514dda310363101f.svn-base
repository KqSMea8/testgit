package com.hanweb.weather.task;

import java.io.IOException; 
import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.CacheException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;

import com.hanweb.common.task.BaseTask;
import com.hanweb.common.task.TaskManager;
import com.hanweb.common.task.TaskScheduleBuilder;
import com.hanweb.common.task.TaskState;
import com.hanweb.jmp.util.CacheUtil;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;  
import com.hanweb.jmp.constant.Configs;
import com.hanweb.weather.entity.Area;
import com.hanweb.weather.service.AreaService;
import com.hanweb.weather.service.WeatherIndexService;
import com.hanweb.weather.service.WeatherService;


public class WeatherTask extends BaseTask{
	
	private final Log logger = LogFactory.getLog(getClass());
	@Override
	protected void config() {  
		setTaskId("weather_task");
		setTaskName("天气预报"); 
		//设定线程时间
		List<Object> list = new ArrayList<Object>(); 
		String weathertime=Configs.getConfigs().getWeathertime();
		if(StringUtil.isEmpty(weathertime)){
			weathertime="1,7,10,14,17";
		} 
		String[] times=weathertime.split(",");
		for(int i=0; i<times.length; i++){
			list.add(TaskScheduleBuilder.getInstance()
					.setHour(times[i])); 
		} 
		setTaskSchedule(list);
	} 
	
	@Override
	protected void doWork(JobDataMap map) { 
		TaskState state = TaskManager.getTaskState("weather_task_start");  
		if(TaskState.RUNNING.equals(state) && !"weather_task_start".equals(map.getString("taskId"))){ 
			return;
		}  
		WeatherService weatherService = SpringUtil.getBean(WeatherService.class);
		WeatherIndexService indexService = SpringUtil.getBean(WeatherIndexService.class);
		AreaService areaService = SpringUtil.getBean(AreaService.class); 
		try{
			List<Area>  areas=areaService.findByState(1, null, null);
			if(areas==null || areas.size()<=0){ 
				return;
			}  
		    weatherService.removeBytime(DateUtil.getCurrDate(DateUtil.YYYY_MM_DD));
		    indexService.removeBytime(DateUtil.getCurrDate(DateUtil.YYYY_MM_DD)); 
			//同步更新天气预报
			weatherService.syninfoWeather(areas);
			if(! CacheUtil.getAllElement("weather").isEmpty()){
				CacheUtil.removeAll("weather");  
			}
		}catch (OperationException e) { 
			logger.error("doWork error:", e);
		}catch (CacheException e) { 
			logger.error("doWork error:", e); 
		} catch (IOException e) { 
			logger.error("doWork error:", e);  
		}   
	}
	public static void main(String[] args) {
		String area = "10101";
	}

}