package com.hanweb.jmp.task;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;

import com.hanweb.common.task.BaseTask;
import com.hanweb.common.task.TaskScheduleBuilder;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.jmp.apps.controller.manage.LightAppFormBean;
import com.hanweb.jmp.apps.entity.manage.LightApp;
import com.hanweb.jmp.apps.service.manage.LightAppService;
import com.hanweb.jmp.appstype.entity.LightAppType;
import com.hanweb.jmp.appstype.service.LightAppTypeService;
import com.hanweb.jmp.sys.entity.sites.SiteSplash;
import com.hanweb.jmp.sys.service.sites.SiteSplashService;
import com.hanweb.jmp.util.HttpClientUtil;

/**
 * 应用同步线程
 */
public class AppSynchronizeTask extends BaseTask { 

	/**
	 * logger
	 */
	private final Log logger = LogFactory.getLog(getClass());

	@Override
	protected void config() {  
		setTaskId("syn_app");
		setTaskName("应用同步");
		TaskScheduleBuilder taskScheduleBuilder = TaskScheduleBuilder.getInstance();
		//每天凌晨1点执行
		taskScheduleBuilder.setHour("1");
		setTaskSchedule(taskScheduleBuilder.getSchedule());
	}


	@Override
	protected void doWork(JobDataMap dataMap) { 


		SiteSplashService siteSplashService = SpringUtil.getBean("jmp_SiteSplashService", SiteSplashService.class);
		LightAppService lightAppService = SpringUtil.getBean("jmp_LightAppService", LightAppService.class);	
		LightAppTypeService lightAppTypeService = SpringUtil.getBean("jmp_LightAppTypeService", LightAppTypeService.class);

		List<SiteSplash>  siteSplashs = siteSplashService.findSiteSplashs();
		for(SiteSplash siteSplash:siteSplashs){
			//同步应用系统域名
			String jopUrl = siteSplash.getJopUrl();
			Integer siteId = siteSplash.getSiteId();		


			List<LightApp> list = lightAppService.findAllLightApp(siteId);

			/*String udid = "safDADasd";
		Date now = new Date();
		Long nowLong = now.getTime();		
		String uniquecode = StringUtil.getString(nowLong);
		String tokenuuid = uniquecode+"318qwe"+udid;
		tokenuuid = Md5Util.encodePwd(tokenuuid);*/

			
			String result = HttpClientUtil.getInfo(jopUrl+"interfaces/getAppIssue.do?startTime=0","UTF-8");//+"&udid="+udid+"&uniquecode="+uniquecode+"&tokenuuid="+tokenuuid
			if(result!=null){
				String appinfojson = JsonUtil.StringToObject(result, net.sf.json.JSONObject.class).getString("appinfo");
				JSONArray appinfoArr = JSONArray.fromObject(appinfojson);     
				int sum = 0;
				for(int i=0; i<appinfoArr.size(); i++){
					String tName = appinfoArr.getJSONObject(i).getString("appTypeName");
					//应用分类
					LightAppType appType = new LightAppType();
					appType.setName(tName);
					appType.setSiteId(siteId);
					appType.setCreateTime(new Date());
					appType.setPid(0);
					boolean hasAppName = lightAppTypeService.findCountByAppTypeName(tName,siteId);
					if(hasAppName==false){
						lightAppTypeService.add(appType);
					}
					String infojson = appinfoArr.getJSONObject(i).getString("info");
					JSONArray infoArr = JSONArray.fromObject(infojson);
					
					for(int j=0; j<infoArr.size(); j++){
						sum++;
						//应用名称、url
						String name = infoArr.getJSONObject(j).getString("name");
						String appIssueUrl = infoArr.getJSONObject(j).getString("appIssueUrl");
						String iconurl = infoArr.getJSONObject(j).getString("iconUrl");
						//是否上架0、未上架 1、已上架 2、已下架
						String isIssue = infoArr.getJSONObject(j).getString("isIssue");
						//上下架表应用状态0.正常,1.已删除, 2.审核未通过
						String appIssueState = infoArr.getJSONObject(j).getString("appIssueState");
						String keyValue = infoArr.getJSONObject(j).getString("keyValue");
						//所属机构
						String groupName = infoArr.getJSONObject(j).getString("groupName");
						//if("1".equals(isIssue)&&"0".equals(appIssueState)){												

						int iid = lightAppTypeService.findIidByAppTypeName(tName, siteId);						
						//应用
						LightAppFormBean lightApp = new LightAppFormBean();
						lightApp.setName(name);
						lightApp.setUrl(appIssueUrl);
						lightApp.setSiteId(siteId);
						lightApp.setLightType(0);
						lightApp.setIsDefault(0);
						lightApp.setDefaultType(0);
						lightApp.setCreateTime(new Date());
						lightApp.setOrderId(-sum);
						lightApp.setLightType(iid);
						lightApp.setLightTypeName(tName);
						lightApp.setKeyValue(keyValue);
						lightApp.setGroupName(groupName);
						boolean flagk = lightAppService.findAppByKeyValue(keyValue, siteId);
						if(flagk==true){
							lightAppService.update(name, appIssueUrl, iid, tName, groupName, keyValue);
						}else{
							lightAppService.addApp(lightApp);	
						}
						//当前库keyValue和同步数据中keyValue比较
						if(list!=null){
							Iterator<LightApp> it = list.iterator();
							while(it.hasNext()){
								LightApp app = it.next();
								if(app.getKeyValue()!=null&&app.getKeyValue().equals(keyValue)){
									it.remove();
								}
							}
						}					
					}				
				}
			}
			for(int b=0;b<list.size();b++){
				if(list.get(b).getKeyValue()!=null&&!"".equals(list.get(b).getKeyValue())){
					lightAppService.deleteBykeyValue(list.get(b).getKeyValue()); 
				}
			}
		} 
	}
}