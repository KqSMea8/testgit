package com.hanweb.jmp.sys.service.parameter;

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
	 * 删除
	 * @param iid 站点详细表id
	 * @return  true 成功<b/>
	 * 		   	false 失败
	 */
	public boolean remove(Integer iid){
		return parameterDAO.deleteById(iid);
	}
	
	/**
	 * 修改
	 * @param parameter 
	 * @return  true 成功<b/>
	 * 		   	false 失败
	 */
	public boolean modify(Parameter parameter){
		Parameter curParameter = this.findBySiteId(parameter.getSiteId());
		if(curParameter != null){
			parameter.setIid(curParameter.getIid());
			return parameterDAO.update(parameter);
		}else{
			return this.add(parameter) > 0;
		}
	}
	
	/**
	 * 根据id查找站点详细
	 * @param siteId 站点id
	 * @return Parameter
	 */
	public Parameter findBySiteId(Integer siteId){
		return parameterDAO.findBySiteId(siteId); 
	}
	
	/**
	 * 修改模块权限
	 * @param name name
	 * @param state state
	 * @param siteId siteId
	 * @return boolean
	 */
	public boolean updateModuleState(String name, int state, int siteId){
		return parameterDAO.updateModuleState(name, state, siteId);
	}
	
}