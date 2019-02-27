package com.hanweb.weather.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.weather.dao.WeatherIndexDAO;
import com.hanweb.weather.entity.WeatherIndex;

@Service
public class WeatherIndexService {
	@Autowired
	private WeatherIndexDAO indexDao;
	public boolean addModify(WeatherIndex index) throws OperationException{
		boolean bl = false;
		if(index == null){
			return bl;
		}
		int iid = findId(index);
		if(iid <= 0){
			bl = add(index);
		}else{
			index.setIid(iid);
			bl = modify(index);
		}
		return bl;
	}
	
	public boolean addModifyList(List<WeatherIndex> indexlist) throws OperationException{
		boolean bl = false;
		if(CollectionUtils.isEmpty(indexlist)){
			return bl;
		}
		int iid=0;
		for(WeatherIndex index : indexlist){
			if(index == null){
				continue;
			}
			iid = findId(index);
			if(iid <= 0){
				bl = add(index);
			}else{
				index.setIid(iid);
				bl = modify(index);
			}
		} 
		return bl;
	}
	
	private int findId(WeatherIndex index){
		Integer num =  indexDao.findId(index.getAreaId(), index.getTitle(), index.getCreateTime());
		return NumberUtil.getInt(num);
	}
	private boolean add(WeatherIndex index) throws OperationException{
		if(index == null){
			return false;
		}
		Integer iid = indexDao.insert(index);
		return NumberUtil.getInt(iid) > 0;
	}
	
	private boolean modify(WeatherIndex index) throws OperationException{
		if(index == null){
			return false;
		}
		return indexDao.update(index);
	}
	
	public boolean remove(String ids) throws OperationException{
		if(StringUtil.isEmpty(ids)){
			return false;
		}
		return indexDao.deleteByIds(StringUtil.toIntegerList(ids));
	}

	public boolean removeBytime(String time) throws OperationException{
		if(StringUtil.isEmpty(time)){
			return false;
		}
		return indexDao.deleteByTime(time);
	}
	
	public List<WeatherIndex> findByTime(Integer areaId, String time){
		if(NumberUtil.getInt(areaId) <= 0 || StringUtil.isEmpty(time)){
			return null;
		}
		return indexDao.findByTime(areaId, time);
	}
}
