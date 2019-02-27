package com.hanweb.jmp.pack.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.cms.dao.sign.SignRelDAO;
import com.hanweb.jmp.cms.entity.sign.SignRel;

public class SignRelService {

	/**
	 * signRelDAO
	 */
	@Autowired
	SignRelDAO signRelDAO;
	
	/**
	 * 新增
	 * @param signRel signRel
	 * @return boolean
	 * @throws OperationException    设定参数 .
	 */
	public boolean addRel(SignRel signRel){
		if(signRel == null){
			return false;
		}
		return signRelDAO.insert(signRel) > 0;
	}
	
}