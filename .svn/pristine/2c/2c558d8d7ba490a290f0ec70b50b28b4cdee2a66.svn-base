package com.hanweb.jmp.interfaces.app;

import java.util.ArrayList; 
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.jmp.annotation.InterfaceCache;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.InterfaceLogConfig;
import com.hanweb.jmp.util.HttpClientUtil;

@Controller
@RequestMapping("interfaces")
public class WeatherController {

	/**
	 * colService
	 */
	@Autowired
	ColService colService;
	
	/**
	 * 天气预报-天气数据.
	 * @param resourceid resourceid
	 * @param citycode citycode
	 * @param cityname cityname
	 * @return    设定参数 .
	 */
	@RequestMapping("weather")
	@ResponseBody
	@InterfaceCache
	public String weatherClient(Integer resourceid, String citycode, String cityname){ 
		Col col = colService.findByIid(NumberUtil.getInt(resourceid));
		if(col == null || col.getIid() <= 0 || col.getType() != 3 || col.getHdType() != 5){ 
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_WEATHER, 
					InterfaceLogConfig.ERROR_03); 
		}
        List<NameValuePair> list = new ArrayList<NameValuePair>();
		NameValuePair nameValuePair = new BasicNameValuePair("citycode", citycode);
		list.add(nameValuePair); 
		nameValuePair = new BasicNameValuePair("cityname", cityname);
		list.add(nameValuePair);
		String json = HttpClientUtil.postInfo(col.getActUrl() + "/interfaces/weather_s.do", list, "UTF-8");
		return json;
	}
	
	/**
	 * areaClient
	 * @param resourceid resourceid
	 * @param flag flag
	 * @param parcode parcode
	 * @param citycode citycode
	 * @return    设定参数 .
	 */
	@RequestMapping("area")
	@ResponseBody
	@InterfaceCache
	public String areaClient(Integer resourceid, String flag, String parcode, String citycode){  
		Col col = colService.findByIid(NumberUtil.getInt(resourceid));
		if(col == null || col.getIid() <= 0 || col.getType() != 3 || col.getHdType() != 5){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_WEATHER, 
					InterfaceLogConfig.ERROR_03); 
		}
        List<NameValuePair> list = new ArrayList<NameValuePair>();
		NameValuePair nameValuePair = new BasicNameValuePair("flag", flag);
		list.add(nameValuePair);  
		nameValuePair = new BasicNameValuePair("parcode", parcode);
		list.add(nameValuePair);  
		nameValuePair = new BasicNameValuePair("citycode", citycode);
		list.add(nameValuePair);  
		String json = HttpClientUtil.postInfo(col.getActUrl() + "/interfaces/area_s.do", list, "UTF-8");
		return json;
	}
	
	/**
	 * weathernewClient
	 * @param citycode citycode
	 * @param cityname cityname
	 * @return    设定参数 .
	 */
	@RequestMapping("weather_new")
	@ResponseBody
	@InterfaceCache
	public String weathernewClient(String citycode, String cityname){
		Configs configs = new Configs();
        List<NameValuePair> list = new ArrayList<NameValuePair>();
		NameValuePair nameValuePair = new BasicNameValuePair("citycode", citycode);
		list.add(nameValuePair); 
		nameValuePair = new BasicNameValuePair("cityname", cityname);
		list.add(nameValuePair);
		String json = HttpClientUtil.postInfo(configs.getWeatherServerUrl() + "/interfaces/weather_s.do", list, "UTF-8");
		return json;
	}
	
	/**
	 * areanewClient
	 * @param flag flag
	 * @param parcode parcode
	 * @param citycode citycode
	 * @return    设定参数 .
	 */
	@RequestMapping("area_new")
	@ResponseBody
	@InterfaceCache
	public String areanewClient(String flag, String parcode, String citycode){  
		Configs configs = new Configs();
        List<NameValuePair> list = new ArrayList<NameValuePair>();
		NameValuePair nameValuePair = new BasicNameValuePair("flag", flag);
		list.add(nameValuePair);  
		nameValuePair = new BasicNameValuePair("parcode", parcode);
		list.add(nameValuePair);  
		nameValuePair = new BasicNameValuePair("citycode", citycode);
		list.add(nameValuePair);  
		String json = HttpClientUtil.postInfo(configs.getWeatherServerUrl() + "/interfaces/area_s.do", list, "UTF-8");
		return json;
	}
	
}