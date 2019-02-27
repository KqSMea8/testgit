package com.hanweb.jmp.global.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.jmp.global.dao.GoodRecordDAO;
import com.hanweb.jmp.global.entity.GoodRecord;
public class GoodRecordService {

	/**
	 * goodRecordDAO
	 */
	@Autowired
	private GoodRecordDAO goodRecordDAO;
	
	/**
	 * 新增
	 * @param goodRecord goodRecord
	 * @return    设定参数 .
	*/
	public boolean add(GoodRecord goodRecord){
		if(goodRecord == null){
			return false;
		}
		int iid = goodRecordDAO.insert(goodRecord); 
		return iid > 0;
	}
	
}
