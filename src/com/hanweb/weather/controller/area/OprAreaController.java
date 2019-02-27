package com.hanweb.weather.controller.area;

import java.io.File;
import java.util.ArrayList;
import java.util.List; 

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.annotation.Permission;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.permission.Allowed; 
import com.hanweb.jmp.util.CacheUtil;
import com.hanweb.common.util.FileUtil; 
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState; 
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.util.HttpClientUtil;
import com.hanweb.setup.dao.DataInitDAO;
import com.hanweb.weather.entity.Area;
import com.hanweb.weather.service.AreaService;
import com.hanweb.weather.service.WeatherService;

/**
 * 机构操作控制器
 * 
 * @author ZhangC
 * 
 */
@Controller
@Permission(module = "area", allowedGroup = Allowed.YES)
@RequestMapping("manager/area")
public class OprAreaController {

	@Autowired
	private AreaService areaService; 
	
	@Autowired
	private WeatherService weatherService;
	
	@Autowired
	private DataInitDAO dataInitDAO;
	
	
	/**
	 * 审核
	 * 
	 * @param ids
	 *            ID串 如:1,2,3,4
	 * @return
	 */
	@RequestMapping(value = "start")
	@ResponseBody
	public JsonResult audit(String ids) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = areaService.modifyState(ids, 1);
		if (isSuccess) {
			jsonResult.set(ResultState.OPR_SUCCESS);
		} else {
			jsonResult.set(ResultState.OPR_FAIL);
		}
		return jsonResult;
	}
	
	/**
	 * 撤审
	 * 
	 * @param ids
	 *            ID串 如:1,2,3,4
	 * @return
	 */
	@RequestMapping(value = "stop")
	@ResponseBody
	public JsonResult unaudit(String ids) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = areaService.modifyState(ids, 0);
		if (isSuccess) {
			jsonResult.set(ResultState.OPR_SUCCESS);
		} else {
			jsonResult.set(ResultState.OPR_FAIL);
		}
		return jsonResult;
	}
	
	@RequestMapping("initarea")
	@ResponseBody
	public JsonResult initArea(){
		JsonResult jsonResult = JsonResult.getInstance();
		List<String> sqls = FileUtil.readLines(new File(BaseInfo.getRealPath()
				+ "/WEB-INF/sql/init/area.sql"), "utf-8");

		if (sqls != null) {
			for (String sql : sqls) {
				if (StringUtils.isBlank(sql)) {
					continue;
				}
				if (sql.endsWith(";")) {
					sql = sql.substring(0, sql.length() - 1);
				}
				Query query = dataInitDAO.createQuery(sql);
				dataInitDAO.execute(query);
			}
		}
		jsonResult.set(ResultState.OPR_SUCCESS);
		return jsonResult;
	}
	 
	@RequestMapping("baidu_show")
	@ResponseBody 
	public String baiduShow(Integer iid){
		String chartSet = "UTF-8";
		Area areaEn=areaService.findById(iid);
		if(areaEn==null || NumberUtil.getInt(areaEn.getIid())<=0){
			return "地域数据为空";
		}
		String weatherurl=StringUtil.getString(Configs.getConfigs().getWeatherurl()); 
		String areaCn  = areaEn.getNameCn();  
		if(StringUtil.isEmpty(areaCn)){
			return "地域数据为空";
		} 
		
		if(StringUtil.isEmpty(weatherurl)){
			return "weatherurl天气预报接口地址为空";
		} 
         
		weatherurl=weatherurl.replace("{area}", areaCn);  
		String jsonStr = HttpClientUtil.getInfo(weatherurl, chartSet); 
		if(StringUtil.isEmpty(jsonStr) || jsonStr.indexOf("\"error\":-3") > 0){
			return "城市："+areaCn+"的jsonStr数据为空或格式不正确"; 
		}
		return jsonStr;
	}
	
	@RequestMapping("weather_show")
	@ResponseBody 
	public String weatherShow(Integer iid){
		Area areaEn=areaService.findById(iid);
		String jsonStr=weatherService.findWeaterJson(areaEn);
		return jsonStr;
	}

	@RequestMapping("modifyweater_submit")
	@ResponseBody
	public JsonResult submitWeaterModify(Integer iid){
		JsonResult jsonResult = JsonResult.getInstance();
		Area areaEn=areaService.findById(iid);
		if(areaEn==null || StringUtil.isEmpty(areaEn.getNameCn())){
			String errorMessage="城市数据有误";
			jsonResult.set(ResultState.OPR_FAIL, errorMessage);
			return jsonResult;
		}
		List<Area>  areas=new ArrayList<Area>();
		areas.add(areaEn);
		//更新天气预报数据
		weatherService.syninfoWeather(areas);
		//清除该城市的天气缓存
		CacheUtil.removeKey("weather", areaEn.getAreaCode());
		jsonResult.set(ResultState.OPR_SUCCESS);
		return jsonResult;
	}
	
}