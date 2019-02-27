package com.hanweb.jmp.task; 

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;

import com.hanweb.common.task.BaseTask; 
import com.hanweb.common.util.DateUtil; 
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.cms.service.infos.InfoService;

import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.jmp.util.SynInfoUtil;

public class SynchInfoTask extends BaseTask { 
	
	/**
	 * logger
	 */
	private final Log logger = LogFactory.getLog(getClass());
	
	@Override
	protected void config() {  
		
	}

	@Override
	protected void doWork(JobDataMap colhm) {
		try{ 
			Col col = (Col) colhm.get("col");
			int colid = col.getIid();
			int taskId = NumberUtil.getInt(col.getTaskId()); 
			String statrtime = DateUtil.dateToString(col.getStartTime(), 
					DateUtil.YYYY_MM_DD_HH_MM_SS);
	        if(StringUtil.isEmpty(statrtime)){
	        	return;
			} 
			String endtime = DateUtil.getCurrDate(DateUtil.YYYY_MM_DD_HH_MM_SS);
			SynInfoUtil synDao = new SynInfoUtil();  
			
			int infoNum = 0;
			int siteId = NumberUtil.getInt(col.getSiteId());
			SiteService siteService = SpringUtil.getBean("jmp_SiteService", SiteService.class);
			Site site = siteService.findByIid(siteId);
			
			if(site != null){
				if(col.getSourcetype() == 0){
					infoNum = synDao.getinfoNum(col.getSourcename(), col.getSourcepwd(), "", statrtime, endtime, 
							taskId, col.getSourceurl(), col, 0);
				} else {
					infoNum = synDao.getinfoNum(col.getSourcename(), col.getSourcepwd(), "", statrtime, endtime, 
							taskId, col.getSourceurl(), col, 1); 
				}
				
			}
			//获取信息数量，信息时间范围为上次同步时间到当前时间的所有信息
			if(infoNum > 0){
				InfoService infoService = SpringUtil.getBean("jmp_InfoService", InfoService.class);
				ColService colService = SpringUtil.getBean("jmp_ColService", ColService.class);
				
				//同步栏目下信息
				infoService.synInfo(col, statrtime, endtime, infoNum, null);
				
				//修改同步时间
				if(endtime.equals(statrtime)){//如果相同就加1秒，防止同秒重复
         		   Date date1 = DateUtil.stringtoDate(endtime, DateUtil.YYYY_MM_DD_HH_MM_SS);
         		   endtime = DateUtil
         		   		.dateToString(new Date(date1.getTime()+1000), DateUtil.YYYY_MM_DD_HH_MM_SS);
         	    }
				colService.modifyStarttime(colid, endtime);
			}  
			//本次同步的结束时间作为下次同步的开始时间
			statrtime = endtime;
			col.setStartTime(DateUtil.stringtoDate(statrtime, DateUtil.YYYY_MM_DD_HH_MM_SS));
			colhm.put("col", col);
		}catch(Exception e){
			logger.error("信息同步线程报错,doWork:", e);
		}
        return;
		
	} 

}