package com.hanweb.jmp.task;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobDataMap;
import com.hanweb.common.BaseInfo;
import com.hanweb.common.task.BaseTask;
import com.hanweb.common.task.TaskScheduleBuilder;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;

import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.cms.entity.infos.InfoOperate;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.cms.service.infos.InfoOperService;
import com.hanweb.jmp.cms.service.infos.InfoService;
import com.hanweb.jmp.global.service.JsearchService;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.sites.SiteService;

public class JSearchTask extends BaseTask{
	
	/**
	 * colService
	 */
	private ColService colService = SpringUtil.getBean(ColService.class);
	
	/**
	 * siteService
	 */
	private SiteService siteService = SpringUtil.getBean(SiteService.class);
	
	/**
	 * infoService
	 */
	private InfoService infoService = SpringUtil.getBean(InfoService.class);
	
	/**
	 * infoOperService
	 */
	private InfoOperService infoOperService = SpringUtil.getBean(InfoOperService.class);
	
	/**
	 * jsearchService
	 */
	private JsearchService jsearchService = SpringUtil.getBean(JsearchService.class);
	
	@Override
	protected void config() {
		setPriority(5);
		setTaskId("jsearch");
		setTaskName("jsearch同步线程");
		setTaskSchedule(TaskScheduleBuilder.getEveryMinuteSchedule(10));
	}

	@Override
	protected void doWork(JobDataMap arg0) {
		List<Site> siteList = siteService.findJsearchSite();
		List<Col> colList = null;
		List<InfoOperate> infoOperList = null;
		int siteId = 0;
		int colId = 0;
		int infoId = 0;
		Info info = null;
		if(CollectionUtils.isEmpty(siteList)){
			return;
		}
		for(Site site : siteList){
			siteId = NumberUtil.getInt(site.getIid());
			colList = colService.findBySiteIds(StringUtil.getString(siteId));
			if(CollectionUtils.isEmpty(colList)){
				continue;
			}
			for(Col col : colList){
				colId = NumberUtil.getInt(col.getIid());
				if(NumberUtil.getInt(col.getIsJsearch()) == 0){
					continue;
				}
				infoOperList = infoOperService.findJsearchByCateId(siteId, colId);
				if(infoOperList == null){
					continue;
				}
				StringBuffer sb = new StringBuffer();
				for(InfoOperate infoOperate : infoOperList){
					infoId = NumberUtil.getInt(infoOperate.getInfoId());
					//操作类型   1新增    2删除  3修改  4审核 5 撤审
					if(infoOperate.getOprType() != 2 && infoOperate.getOprType() != 5){
						info = infoService.findByIid(infoId, siteId);
						if(info == null || info.getIid() <= 0){
							continue;
						}
						String content
							= infoService.findContent(BaseInfo.getRealPath() + info.getPath());
						info.setContent(content);
					}else{
						info = new Info();
						info.setIid(infoId);
						info.setSiteId(siteId);
						info.setColId(colId);
						info.setTitle(infoOperate.getTitle());
						info.setUrl(infoOperate.getUrl());
					}
					String opr = getOpr(infoOperate.getOprType());
					if(jsearchService.sendToRobot(site, col, info, opr)){
						sb.append(infoOperate.getIid() + ",");
					}
				}
				String s = sb.toString();
				if(s.length() > 0){
					s = s.substring(0, s.length() - 1);
					infoOperService.modifyIsJsearchByMaxId(siteId, colId, s);
				}
			}
		}
		
	} 
	
	/**
	 * 
	 * getOpr:(这里用一句话描述这个方法的作用).
	 *
	 * @param i i
	 * @return    设定参数 .
	 */
	private String getOpr(int i){
		//操作类型   1新增    2删除  3修改  4审核 5 撤审
		String opr = "";
		switch (i) {
		case 1:
			opr = "A";
			break;
		case 2:
			opr = "D";
			break;
		case 3:
			opr = "U";
			break;
		case 4:
			opr = "A";
			break;
		case 5:
			opr = "D";
			break;
		default:
			opr = "A";
			break;
		}
		return opr;
	}

}
