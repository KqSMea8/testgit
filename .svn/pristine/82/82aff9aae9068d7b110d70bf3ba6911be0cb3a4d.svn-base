package com.hanweb.weather.service;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;  

import com.hanweb.weather.dao.ProvinceDAO;
import com.hanweb.weather.entity.Province;

@Service
public class ProvinceService { 

	@Autowired
	private ProvinceDAO provinceDao;
	
	public List<Province> findAll(){ 
		return provinceDao.findAll();
	}
}

