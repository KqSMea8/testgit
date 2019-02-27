package com.hanweb.jmp.interfaces.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.StringUtil;

import com.hanweb.interfaces.service.InfoInterfaceService;
import com.hanweb.jmp.apps.entity.manage.LightApp;
import com.hanweb.jmp.apps.service.manage.LightAppService;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.cms.service.infos.InfoService;
import com.hanweb.jmp.sys.entity.collect.Collect;
import com.hanweb.jmp.sys.service.collect.CollectService;

@Controller
@RequestMapping("interfaces")
public class CollectController {
	
	/**
	 * channelService
	 */
	@Autowired
	CollectService collectService;
	
	@Autowired
	LightAppService lightAppService;
	
	@Autowired
	InfoService infoService;
	
	@Autowired
	InfoInterfaceService infoInterfaceService;
	
	ColService colService;
	/**
	 * 收藏接口
	 * @param userName  用户名
	 * @param userId    用户id
	 * @param type      类型 1 应用 2信息
	 * @param collectId 收藏id
	 * @param siteId    站点id
	 * @return
	 */
	@RequestMapping("collect")
	@ResponseBody 
	public String collect(String userName, String userId, Integer type, Integer collectId, Integer siteId, String infoName){
		HashMap<String, Object> ret = new HashMap<String, Object>();
		boolean isSuccess = false;
		String message = "";
		
		if(userName == null || userId == null || type == null || collectId == null || siteId == null){
			message = "非法参数";
		}
		Collect collect1 = collectService.findCollect(userId, type, collectId, siteId);
		if(collect1 == null){
			Collect collect = new Collect();
			collect.setUserName(userName);
			collect.setUserId(userId);
			collect.setType(type);
			collect.setCollectId(collectId);
			collect.setCreateTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
			collect.setSiteId(siteId);
			collect.setInfoName(infoName);
			isSuccess = collectService.add(collect);
		}else {
			if(type == 1){
				message = "该应用已收藏";
			}else{
				message = "该信息已收藏";
			}
		}
	
		ret.put("result", isSuccess);
		ret.put("message", message);
		return JsonUtil.objectToString(ret);
	}
	
	/**
	 * 取消收藏接口
	 * @param userId    用户 id
	 * @param type      类型1应用  2信息
	 * @param collectId 收藏id
	 * @param siteId    站点id
	 * @return
	 */
	@RequestMapping("cancelCollect")
	@ResponseBody 
	public String cancelCollect(String userId, Integer type, Integer collectId, Integer siteId){
		HashMap<String, Object> ret = new HashMap<String, Object>();
		boolean isSuccess = false;
		String message = "";
		
		if(userId == null || type == null || collectId == null){
			message = "非法参数";
		}
		Collect collect = collectService.findCollect(userId, type, collectId, siteId);
		if(collect == null){
			if(type == 1){
				message = "该应用未收藏";
			}else{
				message = "该信息未收藏";
			}
		}else{
			isSuccess = collectService.delete(collect.getIid());
		}
		
		ret.put("result", isSuccess);
		ret.put("message", message);
		return JsonUtil.objectToString(ret);
	}
	
	/**
	 *	查询收藏
	 * @param userId 
	 * @param type
	 * @param siteId 
	 * @return
	 */
	@RequestMapping("inquireCollect")
	@ResponseBody 
	public String inquireCollect(String userId, Integer type, Integer siteId){
		List<Map<String, Object>> resources = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> ret = new HashMap<String, Object>();
		HashMap<String, Object> resource = new HashMap<String, Object>();
		boolean isSuccess = true;
		String message = "";
		
		if(userId == null || type == null){
			isSuccess = false;
			message = "非法参数";
		}
		
		List<Collect> collectList = collectService.findCollectByUseridAndType(userId, type, siteId);
		if(collectList == null){
			isSuccess = false;
			if(type == 1){
				message = "暂无收藏应用";
			}else{
				message = "暂无收藏信息";
			}
		}else{
			//应用
			if(type == 1){
				for(Collect collect : collectList){
					LightApp lightApp = lightAppService.findById(collect.getCollectId(), siteId);
					resource = new HashMap<String, Object>();
					resource.put("appname", lightApp.getName());
					resource.put("appid", lightApp.getIid());
					resource.put("hudongtype", StringUtil.getString(lightApp.getAppType()));
					resource.put("isshowtopview", StringUtil.getString(lightApp.getIsShowTopView()));
					resource.put("lightapptype", StringUtil.getString(lightApp.getAppType()));
					resource.put("url", lightApp.getUrl());
					resource.put("isopen", lightApp.getIsOpen()+"");
					resource.put("iconpath", BaseInfo.getDomain()+"/lightapp/icon/"+lightApp.getIid()+"/" + lightApp.getIconNewName());
					resources.add(resource);
				}
			}else{//信息
				for(Collect collect : collectList){
					String tableName = infoService.getTableName(siteId);
					Info info = infoService.findByInfoId(collect.getCollectId(), tableName);
					Col col = colService.findByIid(info.getColId());
					if(info != null){
						Map<String, Object> infoMap = infoInterfaceService.getInfo(info, col, 0);
						resources.add(infoMap);
					}
				}
			}
		}
		
		ret.put("result", isSuccess);
		ret.put("message", message);
		ret.put("resources", resources);
		return JsonUtil.objectToString(ret);
	}
	
	/**
	 * 查找收藏
	 * @param userId    用户id
	 * @param type      类型1应用 2信息
	 * @param siteId    站点
	 * @param collectId 收藏id
	 * @return
	 */
	@RequestMapping("realCollect")
	@ResponseBody 
	public String isCollected(String userId, Integer type, Integer siteId, String collectId){
		HashMap<String, Object> ret = new HashMap<String, Object>();
		List<Collect> collectList = collectService.findRealCollect(userId, type, siteId, collectId);
		if(collectList == null || collectList.size() == 0 || collectList.size()>1){
			ret.put("result", "false");
		}
		if(collectList.size() == 1){
			ret.put("result", "true");
			}
		return JsonUtil.objectToString(ret);
	}
	
}