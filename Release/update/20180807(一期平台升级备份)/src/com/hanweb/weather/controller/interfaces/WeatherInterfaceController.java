package com.hanweb.weather.controller.interfaces; 

import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
 
import com.hanweb.jmp.util.CacheUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.ip.IpUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.jmp.annotation.InterfaceCache;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.InterfaceLogConfig; 
import com.hanweb.weather.entity.Area;
import com.hanweb.weather.service.AreaService;
import com.hanweb.weather.service.WeatherService;

@Controller
@RequestMapping("interfaces")
public class WeatherInterfaceController {
	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private WeatherService weatherService; 
	
	@RequestMapping("weather_s")
	@ResponseBody
	@InterfaceCache
	public String weatherServer(String citycode, String cityname){ 
		if(Configs.getConfigs().getAllowIp().indexOf(IpUtil.getIp()) == -1){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_WEATHER, 
					IpUtil.getIp()+InterfaceLogConfig.ERROR_16); 
		}
		if(StringUtil.isEmpty(citycode)){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_WEATHER, 
					InterfaceLogConfig.ERROR_03); 
		}
		String key = citycode;
		String jsonString = StringUtil.getString(CacheUtil.getValue("weather", key));
		if(StringUtil.isNotEmpty(jsonString)){ 
			return jsonString;
		}  
		Area areaEn=areaService.findByAreaCode(citycode); 
		jsonString = weatherService.findWeaterJson(areaEn);
		CacheUtil.setValue("weather", key, jsonString);
		return jsonString;
	}
	
	@RequestMapping("area_s")
	@ResponseBody
	@InterfaceCache
	public String areaServer(String flag, String parcode, String citycode){
		Map<String, Object> josnMap = new HashMap<String, Object>(); 
		if(Configs.getConfigs().getAllowIp().indexOf(IpUtil.getIp()) == -1){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_WEATHER, 
					IpUtil.getIp()+InterfaceLogConfig.ERROR_16); 
		}
		int serviceFlag = Configs.getConfigs().getAreaFlag();
		int clientFlag = NumberUtil.getInt(flag); 
		josnMap.put("flag", StringUtil.getString(Configs.getConfigs().getAreaFlag()));
		josnMap.put("parcode", parcode);
		josnMap.put("citycode", citycode);
		List<Map<String, String>> citysList = new ArrayList<Map<String,String>>();
		if(serviceFlag != clientFlag){
			try { 
				List<Area> areaList = areaService.findByState(1, parcode, citycode);
				Map<String, String> cityMap = null;
				if(areaList != null){
					for (Area area : areaList) {
						cityMap = new HashMap<String, String>();
						cityMap.put("cityname", area.getNameCn());
						cityMap.put("cityid", area.getAreaCode());
						cityMap.put("firstletter", area.getFirstLetter());
						cityMap.put("acronym", area.getAcronym());
						citysList.add(cityMap);
					}
				}
			} catch (Exception e) {
				logger.error("findWeatherList error:", e);
				return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_WEATHER, 
						InterfaceLogConfig.ERROR_08);
			}
			
		}
		josnMap.put("citys", citysList);
		return JsonUtil.objectToString(josnMap);
	}
	
}
