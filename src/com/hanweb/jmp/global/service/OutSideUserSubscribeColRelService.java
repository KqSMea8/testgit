package com.hanweb.jmp.global.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.StringUtil;
import com.hanweb.jmp.global.dao.OutSideUserSubscribeColRelDAO;
import com.hanweb.jmp.global.entity.OutSideUserSubscribeColRel;

public class OutSideUserSubscribeColRelService {
	/**
	 * outSideUserSubscribeColRelDAO
	 */
	@Autowired
	OutSideUserSubscribeColRelDAO outSideUserSubscribeColRelDAO;

	/**
	 * 外网用户新增订阅
	 * @param outSideUserBookColRel outSideUserBookColRel
	 * @return boolean
	 */
	public boolean add(OutSideUserSubscribeColRel outSideUserSubscribeColRel){
		if(outSideUserSubscribeColRel == null){
			return false;
		}
		int iid = outSideUserSubscribeColRelDAO.insert(outSideUserSubscribeColRel);
		if(iid > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 删除外网用户订阅的指定栏目
	 * @param colId 栏目id
	 * @param siteId 网站id
	 * @param loginName 外网用户登录名
	 * @return boolean
	 */
	public boolean removeColIdAndLoginName(int colId, int siteId, String loginName){
		if(colId <= 0 || siteId <= 0 || StringUtil.isEmpty(loginName)){
			return false;
		}
		return outSideUserSubscribeColRelDAO.deleteByColIdAndLoginName(colId, siteId, loginName);
	}
	
	/**
	 * 根据用户名查找用户订阅的栏目
	 * @param loginName 用户名
	 * @param siteId 网站id
	 * @return List<OutSideUserBookColRel>
	 */
	public List<OutSideUserSubscribeColRel> findByloginName(String loginName, int siteId){
		if(siteId <= 0 || StringUtil.isEmpty(loginName)){
			return null;
		}
		return outSideUserSubscribeColRelDAO.findByloginName(loginName, siteId);
	}
	
}