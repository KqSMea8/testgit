package com.hanweb.jmp.pack.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.jmp.cms.dao.sign.SignDAO;
import com.hanweb.jmp.cms.entity.sign.Sign;

public class SignService {

	/**
	 * signDAO
	 */
	@Autowired
	private SignDAO signDAO;
	
	/**
	 * 频道新增 ：单栏目类
	 * @param dimenEn 
	 * @return num
	 */
	public int addSign(Sign signEn){
		return signDAO.insert(signEn);
	}
	
}
