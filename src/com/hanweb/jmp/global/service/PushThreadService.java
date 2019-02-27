package com.hanweb.jmp.global.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.jmp.global.dao.PushThreadDAO;
import com.hanweb.jmp.global.entity.PushThread;

public class PushThreadService {

	/**
	 * pushThreadDAO
	 */
	@Autowired
	PushThreadDAO pushThreadDAO;
	
	/**
	 * 查找需要推送的信息
	 * @param now 当前时间
	 * @return  List<PushThread>
	 */
	public List<PushThread> findAllByTime(Date now){
		return pushThreadDAO.findAllByTime(now);
	}
	
	/**
	 * 查询所有需要推送的信息
	 * @return int
	 */
	public int findAllPushTask(){
		return pushThreadDAO.findAllPushTask();
	}
	
	/**
	 * 根据iid删除记录
	 * @param iids iids
	 * @return boolean
	 */
	public boolean removeByIids(List<Integer> iids){
		if(iids == null || iids.size() == 0){
			return false;
		}
		return pushThreadDAO.deleteByIds(iids);
	}
	
	/**
	 * 新增
	 * @param pushThread pushThread
	 * @return boolean
	 */
	public boolean add(PushThread pushThread){
		return pushThreadDAO.insert(pushThread) > 0;
	}
	
}