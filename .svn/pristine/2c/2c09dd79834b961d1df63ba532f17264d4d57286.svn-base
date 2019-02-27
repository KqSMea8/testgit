package com.hanweb.jmp.sys.service.log;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;

import com.hanweb.jmp.sys.dao.log.PushInfoLogDAO;
import com.hanweb.jmp.sys.entity.log.PushInfoLog;

public class PushInfoLogService {
	
	/**
	 * pushInfoLogDAO
	 */
	@Autowired
	PushInfoLogDAO pushInfoLogDAO;
	
	/**
	 * 新增推送日志信息
	 * @param pushInfoLog pushInfoLog
	 * @return    设定参数 .
	 */
	public boolean add(PushInfoLog pushInfoLog){
		if(pushInfoLog == null){
			return false;
		}
		pushInfoLog.setCreateTime(new Date());
		pushInfoLog.setMonth(DateUtil.getToMonth());
		pushInfoLog.setHour(NumberUtil.getInt(DateUtil.getCurrDate("HH")));
		Integer iid = pushInfoLogDAO.insert(pushInfoLog);
		return NumberUtil.getInt(iid) > 0;
	}
	
	/**
	 * 删除推送日志
	 * @param ids ids
	 * @return    设定参数 .
	 */
	public boolean remove(String ids){
		if(StringUtil.isEmpty(ids)){
			return false;
		}
		return pushInfoLogDAO.deleteByIds(StringUtil.toIntegerList(ids));
	}
	
	/**
	 * 根据网站id清除日志信息
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public boolean clean(Integer siteId){
		if(NumberUtil.getInt(siteId) <= 0){
			return false;
		}
		return pushInfoLogDAO.clean(siteId);
	}
	
	/**
	 * 删除推送日志信息
	 * @return    设定参数 .
	 */
	public boolean clean(){
		return pushInfoLogDAO.clean();
	}
	
	/**
	 * 按年份和网站ID查找推送日志的条数
	 * @param year year
	 * @param iid iid
	 * @return    设定参数 .
	 */
	public Map<Integer, Integer> findCountBySiteIdAndYear(String year, int iid){
		return pushInfoLogDAO.findByYearAndSiteId(year, iid);
	}
	
	/**
	 * 按年份和网站ID查找新增注册用户
	 * @param year year
	 * @param iid iid
	 * @return    设定参数 .
	 */
	public Map<Integer, Integer> findOutUserBySiteIdAndYear(String year, int iid){
		return pushInfoLogDAO.findOutuserByYearAndSiteId(year, iid);
	}
	
	/**
	 * 统计各个时段信息统送的次数
	 * @param year year
	 * @param iid iid
	 * @return    设定参数 .
	 */
	public Map<Integer, Integer> findPushTimeBySiteIdAndYear(String year, int iid){
		return pushInfoLogDAO.findPushTimeByYearAndSiteId(year, iid);
	}
	
	/**
	 * 更新推送日志.
	 * @param androidstatus androidstatus
	 * @param iosstatus iosstatus
	 * @param infoId infoId
	 * @param ipadstatus ipadstatus
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public boolean modifyPushInfoLog(int androidstatus, int iosstatus, int ipadstatus, int infoId, int siteId){
		return pushInfoLogDAO.updatePushInfoLog(androidstatus, iosstatus, ipadstatus, infoId, siteId);
	}
	
}