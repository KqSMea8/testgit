package com.hanweb.jmp.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;

import com.hanweb.common.task.BaseTask;
import com.hanweb.common.task.TaskScheduleBuilder;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.cms.service.infos.InfoService;

public class TopInfoTask extends BaseTask { 
	
	/**
	 * logger
	 */
	private final Log logger = LogFactory.getLog(getClass());
	
	@Override
	protected void config() {  
		setTaskId("top_info");
		setTaskName("置顶信息下架");
		//每5分钟执行一次
		setTaskSchedule(TaskScheduleBuilder.getEveryMinuteSchedule(5));
	}

	@Override
	protected void doWork(JobDataMap colhm) {
		try{  
			String now = DateUtil.getCurrDate(DateUtil.YYYY_MM_DD_HH_MM_SS);
			int len = 1000;
			List<Info> infos = null;
			Info infoEn = null;
			int infoid=0;
			int colid=0;
			int siteid = 0;
			InfoService infoService = SpringUtil.getBean("jmp_InfoService", InfoService.class);
			ColService colService = SpringUtil.getBean("jmp_ColService", ColService.class);
			Map<Integer, Integer> colids=new HashMap<Integer, Integer>();
			for (int i = 0; len > 999; i += 1000) {
				infos = infoService.findByTopTime(now, i, i+1000);
				if (infos != null) { 
					len = infos.size(); 
					for (int j = 0, len1 = infos.size(); j < len1; j++) { 
						infoEn=infos.get(j);
						infoid=infoEn.getIid();
						colid=infoEn.getColId();
						siteid = infoEn.getSiteId();
						if(infoid<=0 || colid<=0){
							continue;
						}
						//修改置顶时间
						infoService.modifyToptime("", infoid+"", 0, siteid); 
						//记录栏目id
						if(!colids.containsKey(colid)){
							colids.put(colid, colid);
						} 
					}
				} else {
					len = 0;
					break;
				}
			}
			if(colids!=null && colids.size()>0){
				for (Integer key : colids.keySet()) {
					if(NumberUtil.getInt(key)<=0){
						continue;
					} 
					//修改栏目信息标识
					colService.modifyFlag(key+""); 
			    }
			}
			

		}catch(Exception e){
			logger.error("置顶信息下架线程报错,doWork:", e);
		}
        return;
		
	} 

}
