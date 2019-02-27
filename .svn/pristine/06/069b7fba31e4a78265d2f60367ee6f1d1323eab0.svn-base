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
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.util.TimeUtil;
 
/**
 * 信息清除线程
 */
public class ClearInfoTask extends BaseTask { 
	
	/**
	 * logger
	 */
    private final Log logger = LogFactory.getLog(getClass());
	
	@Override
	protected void config() {  
		setTaskId("clear_info");
		setTaskName("信息自动清理");
		TaskScheduleBuilder taskScheduleBuilder = TaskScheduleBuilder.getInstance();
		//每天凌晨2点执行
		//taskScheduleBuilder.setHour("2");
		taskScheduleBuilder.setHour("2");
		setTaskSchedule(taskScheduleBuilder.getSchedule());
	}
	
	 
	@Override
	protected void doWork(JobDataMap dataMap) { 
	    //信息保留方式  0按信息数保留       1按时间保留
	    int infosavetype = Configs.getConfigs().getInfoSaveType(); 
	    //信息保留条数
	    int infosavecounts = Configs.getConfigs().getInfoSaveCounts(); 
	    //信息保留时间
	    int infosavedays = Configs.getConfigs().getInfoSaveDays(); 
	
	    //输入0则无需清理
        if((infosavetype==0 && infosavecounts==0) || (infosavetype==1 && infosavedays==0)){ 
	    	return;
	    } 
	    InfoService infoService = SpringUtil.getBean("jmp_InfoService", InfoService.class);
	    ColService colService = SpringUtil.getBean("jmp_ColService", ColService.class);
	    int infocount = 0;
	    
	    List<Info> allInfo = null;
	    
	    //获取所有已经启用的栏目
        List<Col> allcol = colService.findAllByEnable(1);
        if(CollectionUtils.isEmpty(allcol)){
        	return;
        } 
        int collen=allcol.size();
        Col colEn = null;  
        //获取之前某一天的日期
        String someDate= "";
        if(infosavetype==1 && infosavedays>0){
        	someDate= TimeUtil.befoDay(infosavedays);
        } 
        int colid=0;
        Integer siteId = 0;
        //循环遍历栏目
        for(int i=0; i<collen; i++){
        	colEn=(Col) allcol.get(i);
            if(colEn==null || colEn.getIid()<=0){
                continue;
            } 
            colid=colEn.getIid();
            siteId = colEn.getSiteId();
            //按信息数保留
    	    if(infosavetype==0 && infosavecounts>0){  
    	    	//栏目信息总数
                infocount = infoService.findCountByCateID(colid, siteId); 
                if(infocount > infosavecounts){ 
                    //取得单个栏目下要删除的信息
                    allInfo = infoService.findByColid(
                    		colid, infosavecounts, infocount, NumberUtil.getInt(siteId));  
                }
            //按照时间保留
    	    }else if(infosavedays>0){  
    	    	//获得信息，按信息创建时间计算
                allInfo = infoService.findByEndtime(colid, someDate, siteId); 
    	    }
    	    if(CollectionUtils.isEmpty(allInfo)){
            	continue;
            }
    	    try { 
            	//删除信息
				infoService.removeByList(allInfo, 1);
				allInfo=null;
			} catch (OperationException e) {
				logger.error("自动清理线程删除信息报错：", e); 
			} 
        }      
	} 
	
}