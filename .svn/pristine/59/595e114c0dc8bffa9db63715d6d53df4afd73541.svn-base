package com.hanweb.jmp.global.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.jmp.global.dao.CacheDataDAO;
import com.hanweb.jmp.global.entity.CaheData;


public class CacheDataService {

	/**
	 * cacheDataDAO
	 */
	@Autowired
	private CacheDataDAO cacheDataDAO;
	
	/**
	 * 
	 * @param cachekey
	 * @return
	 */
	public int findByCacheKey(String cachekey) {
		return cacheDataDAO.findByKey(cachekey);
		
	}
	
	/**
	 * 新增
	 * @param caheData
	 * @return
	 */
	public int add(CaheData caheData) {
		return cacheDataDAO.insert(caheData);
	}
	
	/**
	 * 删除所有
	 * @return
	 */
	public boolean removeAll(){
		return cacheDataDAO.removeAll();
	}
	
}