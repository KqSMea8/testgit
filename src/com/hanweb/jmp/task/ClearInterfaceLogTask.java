package com.hanweb.jmp.task;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;
import com.hanweb.common.task.BaseTask;
import com.hanweb.common.task.TaskScheduleBuilder;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.sys.entity.log.InterfaceLog;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.log.InterfaceLogService;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.jmp.util.TimeUtil;

public class ClearInterfaceLogTask extends BaseTask{
	
	/**
	 * logger
	 */
	private final Log logger = LogFactory.getLog(getClass());
		
	@Override
	protected void config() {  
		setTaskId("clear_interface_log");
		setTaskName("接口日志自动清理");
		TaskScheduleBuilder taskScheduleBuilder = TaskScheduleBuilder.getInstance();
		//每天凌晨3点执行
		taskScheduleBuilder.setHour("3");
		setTaskSchedule(taskScheduleBuilder.getSchedule());
	}
		
		 
	@Override
	protected void doWork(JobDataMap dataMap) { 
		SiteService siteService = SpringUtil.getBean("jmp_SiteService", SiteService.class);
		InterfaceLogService interfaceLogService = SpringUtil.getBean(
				"jmp_InterfaceLogService", InterfaceLogService.class);
		
		//获取所有网站
		List<Site> siteList = siteService.findAll();
		List<InterfaceLog> interfaceLogList = null;
		for(Site site : siteList){
			int holdTime = NumberUtil.getInt(site.getHoldTime());
			//获取之前某一天的日期
	        String someDate= "";
	        if(holdTime>0 && site.getIsRecord() == 1){
	        	someDate= TimeUtil.befoDay(holdTime);
	        	interfaceLogList = interfaceLogService.findByEndTime(someDate, site.getIid());
	        }
	        if(CollectionUtils.isEmpty(interfaceLogList)){
            	continue;
            }
	        try { 
            	//删除接口日志
				interfaceLogService.removeByList(interfaceLogList, 1);
				interfaceLogList = null;
			} catch (OperationException e) {
				logger.error("自动清理线程删除信息报错：", e); 
			} 
		}
	}    	  
	   
}