package com.hanweb.jmp.pack.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.jmp.sys.dao.parameter.ParameterDAO;
import com.hanweb.jmp.sys.entity.parameter.Parameter;

public class ParameterService {

	/**
	 * parameterDAO
	 */
	@Autowired
	ParameterDAO parameterDAO;
	
	/**
	 * 新增
	 * @param parameter 
	 * @return 新增之后的id
	 */
	public Integer add(Parameter parameter){
		return parameterDAO.insert(parameter);
	}
	
	/**
	 * 根据id查找网站详细
	 * @param siteId 网站id
	 * @return Parameter
	 */
	public Parameter findBySiteId(Integer siteId){
		return parameterDAO.findBySiteId(siteId); 
	}
	
}