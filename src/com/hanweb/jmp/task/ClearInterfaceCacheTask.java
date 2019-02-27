package com.hanweb.jmp.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;

import com.hanweb.common.task.BaseTask;
import com.hanweb.common.task.TaskScheduleBuilder;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.jmp.global.service.CacheDataService;

public class ClearInterfaceCacheTask extends BaseTask{
    
    /**
     * logger
     */
    private final Log logger = LogFactory.getLog(getClass());
    
    @Override
    protected void config() {  
        setTaskId("clear_interface_cache");
        setTaskName("接口缓存自动清理");
        setTaskSchedule(TaskScheduleBuilder.getEveryMinuteSchedule(5));
    }
    
    @Override
    protected void doWork(JobDataMap arg0) {
        try {
            CacheDataService cacheService = SpringUtil.getBean("jmp_CacheDataService", CacheDataService.class);
            cacheService.removeAll();
        } catch (Exception e) {
            logger.error("接口缓存自动清理报错", e);
        }
        
    }

}
