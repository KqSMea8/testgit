package com.hanweb.weather.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.Properties;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.weather.dao.AreaDAO;
import com.hanweb.weather.entity.Area;

@Service
public class AreaService {
	@Autowired
	private AreaDAO areaDao;
	
	public boolean add(Area area) throws OperationException{
		if(area == null){
			return false;
		}
		Integer iid = areaDao.insert(area);
		return NumberUtil.getInt(iid) > 0;
	}
	
	private void modifyFlag(){
		int flag = Configs.getConfigs().getAreaFlag() + 1;
		Configs.getConfigs().setAreaFlag(flag);
		Properties  jmpProperties = new Properties(BaseInfo.getRealPath() + "/WEB-INF/config/jmportal.properties");
		jmpProperties.setProperty("areaFlag", flag);
		jmpProperties.save();
	}
	
	/**
	 * 更新地区状态
	 * @param id id
	 * @param state 状态
 	 * @return 是否成功
	 */
	public boolean modifyState(String ids, Integer state){
		List<Integer> idList = StringUtil.toIntegerList(ids);
		if(CollectionUtils.isEmpty(idList)){
			return false;
		}
		modifyFlag();
		return areaDao.updateState(idList, state);
	} 
	public boolean remove(String ids) throws OperationException{
		if(StringUtil.isEmpty(ids)){
			return false;
		}
		return areaDao.deleteByIds(StringUtil.toIntegerList(ids));
	}
	
	
	public Area findById(Integer iid){
		if(NumberUtil.getInt(iid) <= 0){
			return null;
		}
		return areaDao.queryForEntityById(iid);
	}
	 
	/**
	 * 通过地区编码获取地区
	 * 
	 * @param areacode
	 *            地区编码
	 * @return 地区实体
	 */
	public Area findByAreaCode(String areacode) {
		if(StringUtil.isEmpty(areacode)){
			return null;
		}
		return areaDao.findByAreaCode(areacode);
	}
	
	public List<Area> findByState(Integer state, String parcode, String citycode){
		List<Area> list = new ArrayList<Area>();
		int count = areaDao.findEnableCount(state, parcode, citycode);
		int times = count / 1000 + 1;
		int pageSize = 1000;
		List<Area> areas=null;  
		for (int i = 1; i < times + 1; i++) {
			areas=areaDao.findByState(pageSize, i, state, parcode, citycode);
			if(CollectionUtils.isEmpty(areas)){
				continue;
			}
			list.addAll(areas);
		} 
		return list; 
	}

	/**
	 * 查找所有地区
	 * @return 地区list
	 */
	public List<Area> findAll(){
		List<Area> list = new ArrayList<Area>();
		int count = areaDao.findCount();
		int times = count / 1000 + 1;
		int pageSize = 1000;
		for (int i = 1; i < times + 1; i++) {
			list.addAll(areaDao.findAll(pageSize, i));
		}
		return list;
	}
	
	/**
	 * 判断是否初始化
	 * @return 
	 */
	public boolean isInitData(){
		int count = areaDao.findCount();
		if(count > 0){
			return true;
		}else{
			return false;
		}
	}
}
