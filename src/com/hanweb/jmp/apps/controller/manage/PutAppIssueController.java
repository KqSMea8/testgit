package com.hanweb.jmp.apps.controller.manage;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.apps.controller.manage.LightAppFormBean;
import com.hanweb.jmp.apps.entity.manage.LightApp;
import com.hanweb.jmp.apps.service.manage.LightAppService;
import com.hanweb.jmp.appstype.entity.LightAppType;
import com.hanweb.jmp.appstype.service.LightAppTypeService;
import com.hanweb.jmp.sys.entity.sites.SiteSplash;
import com.hanweb.jmp.sys.service.sites.SiteSplashService;
import com.hanweb.jmp.util.HttpClientUtil;
import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping("manager/lightapp")
public class PutAppIssueController {

	@Autowired
	private LightAppTypeService lightAppTypeService;

	@Autowired
	private LightAppService lightAppService;

	@Autowired
	private SiteSplashService siteSplashService;

	@ResponseBody
	@RequestMapping("putAppIssue")
	public JsonResult putAppIssue(){

		//获得当前站点id
		CurrentUser user = UserSessionInfo.getCurrentUser();
		Integer siteId = user.getSiteId();
		SiteSplash siteSplash = siteSplashService.findBySiteId(siteId);

		//同步应用系统域名
		String jopUrl = siteSplash.getJopUrl();
		JsonResult jsonResult = JsonResult.getInstance(); 

		if(jopUrl == null || "".equals(jopUrl)){
			jsonResult.setMessage("同步失败，请在参数设置中添加同步系统域名");
			jsonResult.setSuccess(false);
		}else{
		
		List<LightApp> list = lightAppService.findAllLightApp(siteId);
		boolean isSuccess = true;
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
					if("1".equals(isIssue)&&"0".equals(appIssueState)){												

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
					lightApp.setOrderId(sum);
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
			CurrentUser currentUser = UserSessionInfo.getCurrentUser();
			jsonResult.setSuccess(isSuccess);
			jsonResult.addParam("refresh", currentUser.getRangeId() + "");
		}else{
			jsonResult.setSuccess(isSuccess);
		}
		for(int b=0;b<list.size();b++){
			if(list.get(b).getKeyValue()!=null&&!"".equals(list.get(b).getKeyValue())){
				lightAppService.deleteBykeyValue(list.get(b).getKeyValue()); 
			}
		}
		}
		return jsonResult;
	}

}
