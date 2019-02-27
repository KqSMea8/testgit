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

import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.cms.service.infos.InfoService;
import com.hanweb.jmp.util.TimeUtil;

public class AuditInfoTask  extends BaseTask { 
	
	/**
	 * logger
	 */
	private final Log logger = LogFactory.getLog(getClass());

	@Override
	protected void config() {
		setTaskId("audit_info");
		setTaskName("审核信息线程");
		setTaskSchedule(TaskScheduleBuilder.getEveryMinuteSchedule(4));
	}

	@Override
	protected void doWork(JobDataMap arg0) {
		 InfoService infoService = SpringUtil.getBean("jmp_InfoService", InfoService.class);
		 ColService colService = SpringUtil.getBean("jmp_ColService", ColService.class);
		 //获取所有已经启用的栏目
         List<Col> allcol = colService.findAllByEnable(1);
         if(CollectionUtils.isEmpty(allcol)){
        	return;
         }
         StringBuffer buinfos = null;
         String infoids =""; 
         int colsize = allcol.size(); 
         //限时审核时间
         int limittime = 0;
         String befortime = "";
         List<Info> allinfo = null;
         Info info = null;
         Col col = null;
         //循环遍历栏目
         for(int i=0; i<colsize; i++){
            buinfos = new StringBuffer(100);
            col = (Col) allcol.get(i);
            if(col == null || col.getIid() == 0 || col.getType() == 3){
                continue;
            } 
            if(NumberUtil.getInt(col.getAuditType()) != 3){
            	continue;
            } 
            //审核间隔时间
            limittime = col.getLimitTime();
            if(limittime <= 0){
            	continue;
            } 
            befortime = TimeUtil.befoMinute(limittime); 
            allinfo = infoService.findByEndtime(col.getIid(), befortime, col.getSiteId());
            if(CollectionUtils.isEmpty(allinfo)){
            	continue;
            }
            for(int j=0; j<allinfo.size(); j++){
                info = (Info) allinfo.get(j);
                if(info!=null && info.getIid()>0 && info.getStatus()!=1){
                    buinfos.append(","+info.getIid());
                }
            } 
            if(buinfos!=null && !"".equals(buinfos.toString())){
                infoids = buinfos.substring(1); 
                // 更新状态
                try {
					infoService.modifyAudit(infoids, col.getSiteId());
				} catch (OperationException e) {
					logger.error("信息审核线程出错：", e);
				} 
            }    
	    }
	}
	
}