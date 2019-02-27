package com.hanweb.weather.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.hanweb.common.util.NumberUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.weather.dao.AirQualityDAO;
import com.hanweb.weather.entity.AirQuality;

@Service
public class AirQualityService {
	
	@Autowired
	private AirQualityDAO airQualityDAO;
	
	public boolean addModify(AirQuality airQuality) throws OperationException{
		boolean bl = false;
		if(airQuality == null){
			return bl;
		}
		AirQuality oldAirQuality = airQualityDAO.findByAreaCode(airQuality.getAreaCode());
		if(oldAirQuality == null){
			bl = add(airQuality);
		}else{
			airQuality.setIid(oldAirQuality.getIid());
			bl = modify(airQuality);
		}
		return bl;
	}
	
	public boolean addModifyList(List<AirQuality> airlist) throws OperationException{
		boolean bl = false;
		if(CollectionUtils.isEmpty(airlist)){
			return bl;
		} 
		AirQuality oldAirQuality = null;
		for(AirQuality airQuality : airlist){
			if(airQuality == null){
				continue;
			}
			oldAirQuality = airQualityDAO.findByAreaCode(airQuality.getAreaCode());
			if(oldAirQuality == null){
				bl = add(airQuality);
			}else{
				airQuality.setIid(oldAirQuality.getIid());
				bl = modify(airQuality);
			}
		} 
		return bl;
	}
	
	
	public AirQuality findByAreaCode(String areaCode){
		return airQualityDAO.findByAreaCode(areaCode);
	}
	
	private boolean add(AirQuality airQuality) throws OperationException{
		if(airQuality == null){
			return false;
		}
		Integer iid = airQualityDAO.insert(airQuality);
		return NumberUtil.getInt(iid) > 0;
	}
	
	private boolean modify(AirQuality airQuality) throws OperationException{
		if(airQuality == null){
			return false;
		}
		return airQualityDAO.update(airQuality);
	}
	
}
