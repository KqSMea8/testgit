package com.hanweb.jmp.interfaces.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.cache.CacheHandle;
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.common.util.file.LocalFileUtil;
import com.hanweb.complat.entity.OutsideUser;
import com.hanweb.complat.service.OutsideUserService;

import com.hanweb.interfaces.service.InfoInterfaceService;
import com.hanweb.jmp.annotation.InterfaceCache;
import com.hanweb.jmp.apps.entity.manage.LightApp;
import com.hanweb.jmp.apps.service.manage.LightAppService;
import com.hanweb.jmp.cms.entity.channels.Channel;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.cms.entity.sign.Sign;
import com.hanweb.jmp.cms.service.channels.ChannelService;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.cms.service.infos.InfoService;
import com.hanweb.jmp.cms.service.sign.SignService;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.InterfaceLogConfig;
import com.hanweb.jmp.constant.StaticValues;
import com.hanweb.jmp.global.entity.OutSideUserSubscribeColRel;
import com.hanweb.jmp.global.service.OutSideUserSubscribeColRelService;
import com.hanweb.jmp.sys.entity.log.OfflineZip;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.log.OfflineZipService;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.jmp.util.CacheUtil;

/**
 * 栏目相关接口
 * @author lgq
 *
 */
@Controller
@RequestMapping("interfaces")
public class CateController {
	
	/**
	 * colService
	 */
	@Autowired
	ColService colService;
	
	/**
	 * siteService
	 */
	@Autowired
	SiteService siteService;
	
	/**
	 * channelService
	 */
	@Autowired
	ChannelService channelService;
	
	/**
	 * signService
	 */
	@Autowired
	SignService signService;
	
	/**
	 *outsideUserService 
	 */
	@Autowired
	OutsideUserService outsideUserService;
	
	/**
	 * outSideUserSubscribeColRelService
	 */
	@Autowired
	OutSideUserSubscribeColRelService outSideUserSubscribeColRelService;
	
	/**
	 * outSideUserSubscribeColRelService
	 */
	@Autowired
	OfflineZipService offlineZipService;
	
	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;
	
	@Autowired
	LightAppService lightAppService;
	
	@Autowired
	InfoService infoService;
	
	@Autowired
	InfoInterfaceService infoInterfaceService;

	/**
	 * 栏目下子栏目接口一次性全给
	 * @param cateid 栏目id
	 * @param siteid 站点id
	 * @param type 类型 1:刷新 2：加载更多
	 * @param orderid 排序id 
	 * @return json
	 */
	@RequestMapping("cates")
	@ResponseBody
	@InterfaceCache
	public String cates(Integer cateid, Integer siteid, Integer type ,Integer orderid){
		HashMap<String, Object> ret = new HashMap<String, Object>();
		
		// 判断缓存空间是否存在,不存在则创建一个缓存空间
		if(!CacheHandle.isExist(StaticValues.CACHE_COL)) {
            CacheHandle.createCache(StaticValues.CACHE_COL, 0, false, false, 86400, 86400);
        }
		
		// 取缓存，并判断是否有值
		String key = "cates_" + NumberUtil.getInt(siteid) + "_" + NumberUtil.getInt(cateid);
		String cacheValue = StringUtil.getString(CacheUtil.getValue(key, StaticValues.CACHE_COL));
		if(cacheValue != "" && cacheValue.length() > 0){
		    return cacheValue;
		} else {
            try {
                List<Col> cols = new ArrayList<Col>();
                //获取栏目下所有子栏目
                colService.findAllByPidAndSiteId(cateid, siteid, cols);
                Col c = colService.findByIid(cateid);
                Integer colNumber = 10;
                if(c!=null){
                    if(NumberUtil.getInt(c.getColListType()) == 3 && type == 1){
                        if(cols.size() > colNumber ){
                            int i = 0;
                            for(i = (cols.size()-colNumber);i>0;i--){
                                cols.remove(cols.size()-1);
                            }
                        }
                    }else if(NumberUtil.getInt(c.getColListType()) == 3 && type == 2){
                        cols = colService.findByPidAndSiteId(cateid,siteid,orderid);
                        if(cols.size() > colNumber ){
                            int i = 0;
                            for(i = (cols.size()-colNumber);i>0;i--){
                                cols.remove(cols.size()-1);
                            }
                        }
                    }
                }
                ret.put("flag", StringUtil.getString(siteService.findByIid(
                        NumberUtil.getInt(siteid)).getColFlag()));
                ret.put("resource", listToJson(cols, 1));
                
                // 给缓存塞值
                if(JsonUtil.objectToString(ret) != null){
                    CacheUtil.setValue(key, JsonUtil.objectToString(ret), StaticValues.CACHE_COL);
                } 
            } catch (Exception e) {
                e.printStackTrace();
                return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_CATES, 
                        InterfaceLogConfig.ERROR_08);
            }
		}
		return JsonUtil.objectToString(ret);
	}
	
	/**
	 * 查询栏目下一级子栏目
	 * @param cateid 栏目id
	 * @param siteid 站点id
	 * @param type 1:刷新 2：加载更多
	 * @param orderid 排序id 
	 * @return json 
	 */
    @RequestMapping("nextlevelcates")
    @ResponseBody
    @InterfaceCache
    public String nextLevelCates(String cateid, Integer siteid, Integer type ,Integer orderid){
        HashMap<String, Object> ret = new HashMap<String, Object>();
        try {
            List<Col> cols = new ArrayList<Col>();
            if(cateid.contains(",")){
                String[] chrstr = cateid.split(",");
                for(int j=0; j<chrstr.length; j++){
                  //获取栏目下一级子栏目
                    cols = colService.findNextLevelCatesByPidAndSiteId(NumberUtil.getInt(chrstr[j]), siteid, cols);
                    Col c = colService.findByIid(NumberUtil.getInt(chrstr[j]));
                    Integer colNumber = 10;
                    if(c!=null){
                      //如果是卡片式栏目
                        if(NumberUtil.getInt(c.getColListType()) == 3 && type == 1){
                            if(cols.size() > colNumber ){ //取前10个
                                int i = 0;
                                for(i = (cols.size()-colNumber);i>0;i--){
                                    cols.remove(cols.size()-1);
                                }
                            }
                        }else if(NumberUtil.getInt(c.getColListType()) == 3 && type == 2){
                            cols = colService.findByPidAndSiteId(NumberUtil.getInt(chrstr[j]), siteid, orderid);
                            if(cols.size() > colNumber ){  //取前10个
                                int i = 0;
                                for(i = (cols.size()-colNumber);i>0;i--){
                                    cols.remove(cols.size()-1);
                                }
                            }
                        }
                    }
                    ret.put("flag", StringUtil.getString(siteService.findByIid(
                            NumberUtil.getInt(siteid)).getColFlag()));
                    ret.put("resource", listToJson(cols, 1));  //将list组织成json
                }
            } else {
                //获取栏目下一级子栏目
                cols = colService.findNextLevelCatesByPidAndSiteId(NumberUtil.getInt(cateid), siteid, cols);
                Col c = colService.findByIid(NumberUtil.getInt(cateid));
                Integer colNumber = 10;
                if(c!=null){
                  //如果是卡片式栏目
                    if(NumberUtil.getInt(c.getColListType()) == 3 && type == 1){
                        if(cols.size() > colNumber ){ //取前10个
                            int i = 0;
                            for(i = (cols.size()-colNumber);i>0;i--){
                                cols.remove(cols.size()-1);
                            }
                        }
                    }else if(NumberUtil.getInt(c.getColListType()) == 3 && type == 2){
                        cols = colService.findByPidAndSiteId(NumberUtil.getInt(cateid), siteid, orderid);
                        if(cols.size() > colNumber ){  //取前10个
                            int i = 0;
                            for(i = (cols.size()-colNumber);i>0;i--){
                                cols.remove(cols.size()-1);
                            }
                        }
                    }
                }
                ret.put("flag", StringUtil.getString(siteService.findByIid(
                        NumberUtil.getInt(siteid)).getColFlag()));
                ret.put("resource", listToJson(cols, 1));  //将list组织成json
            }
        } catch (Exception e) {
            e.printStackTrace();
            return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_CATES, 
                    InterfaceLogConfig.ERROR_08);
        }
        return JsonUtil.objectToString(ret);
    }
	
	/**
	 * 离线下载栏目接口 
	 * @param siteid 网站ID
	 * @return json
	 */
	@RequestMapping("offlinelist")
	@ResponseBody
	@InterfaceCache
	public String cates(Integer siteid){
		HashMap<String, Object> ret = new HashMap<String, Object>();
		try {
			List<Col> cols = colService.findAllOfflineBySiteId(siteid); 
			ret.put("flag", StringUtil.getString(siteService.findByIid(
					NumberUtil.getInt(siteid)).getColFlag()));
			ret.put("resource", listToJson(cols, 1));
		} catch (Exception e) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_CATES, 
					InterfaceLogConfig.ERROR_08);
		}
		return JsonUtil.objectToString(ret);
	}
	
	/**
	 * 导航下
	 * 栏目接口
	 * @param channelid 栏目ID
	 * @param siteid 网站ID
	 * @return String
	 */
	@RequestMapping("chancates")
	@ResponseBody
	@InterfaceCache
	public String chanCol(Integer channelid, Integer siteid){
		HashMap<String, Object> ret = new HashMap<String, Object>();
		try {
			List<Col> cols = colService.findColByChanId(NumberUtil.getInt(channelid));
			Channel channel = channelService.findByIid(NumberUtil.getInt(channelid));
			int flag = 0;
			if(cols != null && cols.size() > 0){
				for(Col col : cols){
					flag += NumberUtil.getInt(col.getFlag());
				}
			}
			ret.put("flag", StringUtil.getString(NumberUtil.getInt(channel.getFlag())));
			ret.put("resource", listToJson(cols, 1));
		} catch (Exception e) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_CHANCATES, 
					InterfaceLogConfig.ERROR_08);
		}
		return JsonUtil.objectToString(ret);
	}
	
	/**
	 * 2.1.2	栏目订阅分类接口
	 * @param siteid 网站id
	 * @param flag 订阅维度变动标记位
	 * @return String
	 */
	@RequestMapping("bookcatesdimension")
	@ResponseBody
	@InterfaceCache
	public String bookColSign(Integer siteid, Integer flag){
		HashMap<String, Object> ret = new HashMap<String, Object>();
		ArrayList<HashMap<String, String>> classes = new ArrayList<HashMap<String, String>>();
		try {
			Site site = siteService.findByIid(NumberUtil.getInt(siteid));
			ret.put("flag", "" + NumberUtil.getInt(site.getBookColDimensionFlag()));
			ret.put("classes", classes);
			List<Sign> list = signService.findByMid(2, NumberUtil.getInt(siteid), 0);
			if(list != null && list.size() > 0){
				for(Sign d : list){
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("classid", NumberUtil.getInt(d.getIid()) + "");
					map.put("classname", StringUtil.getString(d.getDname()));
					map.put("orderid", NumberUtil.getInt(d.getOrderId()) + "");
					map.put("classbgcolor", StringUtil.getString(d.getColor()));
					classes.add(map);
				}
			}
		} catch (Exception e) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_MYBOOK, 
					InterfaceLogConfig.ERROR_08);
		}
		return JsonUtil.objectToString(ret);
	}
	
	/**
	 * 2.1.3	栏目订阅列表接口
	 * @param siteid 网站id
	 * @param flag 变动标记位
	 * @return String
	 */
	@RequestMapping("bookcateslist")
	@ResponseBody
	@InterfaceCache
	public String bookColList(Integer siteid, Integer flag){
		HashMap<String, Object> ret = new HashMap<String, Object>();
		List<HashMap<String, String>> resource;
		try {
			Site site = siteService.findByIid(NumberUtil.getInt(siteid));
			ret.put("flag", "" + NumberUtil.getInt(site.getBookColFlag()));
			List<Col> cols = colService.findSubscribeColBySiteId(NumberUtil.getInt(siteid));
			resource = listToJsonSubscribe(cols, 0);
			ret.put("resource", resource);
		} catch (Exception e) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_MYBOOK, 
					InterfaceLogConfig.ERROR_08);
		}
		return JsonUtil.objectToString(ret);
	}
	
	/**
	 * 2.1.5	我的栏目订阅/退订接口
	 * @param siteid 网站id
	 * @param type 1=订阅 2=退订
	 * @param resourceid 栏目Id
	 * @param loginid 登录名
	 * @param uuid 手机唯一码
	 * @return String
	 */
	@RequestMapping("mybookcates")
	@ResponseBody 
	public String bookColList(Integer siteid, Integer type, Integer resourceid, String loginid, String uuid){
		HashMap<String, Object> ret = new HashMap<String, Object>();
		try {
			OutsideUser outsideUser = outsideUserService
				.findByLoginName(StringUtil.getString(loginid), NumberUtil.getInt(siteid));
			if(!StringUtil.getString(uuid).equals(StringUtil.getString(outsideUser.getUuid()))){ 
				return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_MYBOOK, 
						InterfaceLogConfig.ERROR_12);
			}else{
				boolean isSuccess = false;
				if(NumberUtil.getInt(type) == 1){
					OutSideUserSubscribeColRel outSideUserSubscribeColRel = new OutSideUserSubscribeColRel();
					outSideUserSubscribeColRel.setSiteId(siteid);
					outSideUserSubscribeColRel.setCreateTime(new Date());
					outSideUserSubscribeColRel.setColId(NumberUtil.getInt(resourceid));
					outSideUserSubscribeColRel.setLoginName(StringUtil.getString(loginid));
					isSuccess = outSideUserSubscribeColRelService.add(outSideUserSubscribeColRel);
				}else{
					isSuccess = outSideUserSubscribeColRelService
						.removeColIdAndLoginName(resourceid, siteid, loginid);
				}
				if(isSuccess){
					ret.put("result", "success");
				}else{
					return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_MYBOOK, 
							InterfaceLogConfig.ERROR_11);
				}
			}
		} catch (Exception e) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_MYBOOK, 
					InterfaceLogConfig.ERROR_08);
		}
		return JsonUtil.objectToString(ret);
	}
	
	/**
	 * 2.1.4	我的订阅列表接口
	 * @param siteid 网站id
	 * @param loginid 登录名
	 * @param uuid 手机唯一码
	 * @return String
	 */
	@RequestMapping("mybookcateslist")
	@ResponseBody 
	public String bookColList(Integer siteid, String loginid, String uuid){
		HashMap<String, Object> ret = new HashMap<String, Object>();
		try {
			OutsideUser outsideUser = outsideUserService.findByLoginName(StringUtil.getString(loginid), NumberUtil.getInt(siteid));
			if(!StringUtil.getString(uuid).equals(StringUtil.getString(outsideUser.getUuid()))){
				return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_MYBOOK, 
						InterfaceLogConfig.ERROR_12);
			}else{
				ret.put("result", "success");
				ArrayList<HashMap<String, String>> resource = new ArrayList<HashMap<String, String>>();
				ret.put("resource", resource);
				List<OutSideUserSubscribeColRel> list = outSideUserSubscribeColRelService.findByloginName(loginid, siteid);
				if(CollectionUtils.isNotEmpty(list)){
					String oprtime="";
					for(OutSideUserSubscribeColRel o : list){
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("resourceid", NumberUtil.getInt(o.getColId()) + "");
						if(o.getCreateTime() == null){
							oprtime="";
						}else{
							oprtime=StringUtil.getString(o.getCreateTime().getTime());
						}
						map.put("oprtime", oprtime);
						resource.add(map);
					}
				}
			}
		} catch (Exception e) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_MYBOOK, 
					InterfaceLogConfig.ERROR_08);
		}
		return JsonUtil.objectToString(ret);
	}
	
	/**
	 * 组织栏目的json
	 * @param cols
	 * @param type
	 * @return
	 */
	private List<HashMap<String, String>> listToJson(List<Col> cols, int type){
		List<HashMap<String, String>> resource = new ArrayList<HashMap<String, String>>();
		if(cols == null){
			return resource;
		}
		String commontype="";
		String hudongurl="";
		OfflineZip offlinezip = null;
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}
		LightApp lightApp = null;
		int sortType=0;
		for(Col col : cols){
			HashMap<String, String> hm = new HashMap<String, String>();
			offlinezip = offlineZipService.findByColId(col.getIid(), col.getSiteId());
			if(offlinezip==null){
			  hm.put("ziptime", "0");	
			}else{
			  hm.put("ziptime", offlinezip.getZipTime().getTime()+"");
			}
			hm.put("resourceid", StringUtil.getString(col.getIid()));
			hm.put("resourcename", StringUtil.getString(col.getName()));
			hm.put("resourcetype", StringUtil.getString(col.getType()));
			hm.put("spec", StringUtil.getString(col.getSpec()));
			
			if(col.getType() == 3 && col.getLightAppName() != null){
//				lightApp = lightAppService.findById(col.getLightAppId(),col.getSiteId());
//				if(lightApp!=null){
//				    hm.put("hudongtype", StringUtil.getString(lightApp.getAppType()));
//	                hm.put("isshowtopview", StringUtil.getString(lightApp.getIsShowTopView()));
//	                hm.put("lightapptype", StringUtil.getString(lightApp.getAppType()));
//				}
//				hm.put("lightappid", StringUtil.getString(col.getLightAppId()));
//				//hm.put("lightappname", StringUtil.getString(col.getLightAppName()));
//				hm.put("lightappurl", StringUtil.getString(col.getLightAppUrl()));
				hm.put("applayout", col.getAppLayOut());
			}
			//频道下栏目接口、栏目下子栏目接口、离线下载栏目接口
			
			//订阅栏目接口 cateimgurl：3.1.12中将iconpath改为cateimgurl
			hm.put("cateimgurl", StringUtil.getString(jmpUrl+ col.getIconPath() + "?" + col.getImgUuid()));
			hm.put("islogin", StringUtil.getString(col.getIsVisit()));
			hm.put("orderid", StringUtil.getString(col.getOrderId()));
			hm.put("parid", StringUtil.getString(col.getPid()));
//			hm.put("weibotype", StringUtil.getString(col.getBlogType()));//3.1.12中注释
			if(NumberUtil.getInt(col.getCommonType())<=0){
				commontype="1";
			}else{
				commontype=StringUtil.getString(col.getCommonType());
			}
			if(col.getCommonType() == 6){
				hm.put("showtype",NumberUtil.getInt(col.getShowType())+"" );
			}
			hm.put("commontype", commontype);
			hm.put("bannerid", StringUtil.getString(col.getBannerId()));
			hm.put("classid", NumberUtil.getInt(col.getBookDimensionId()) + "");
			hudongurl=StringUtil.getString(col.getActUrl());
			hm.put("hudongurl", hudongurl);
			hm.put("inventtype", StringUtil.getString(NumberUtil.getInt(col.getColListType())));
			hm.put("iscomment", StringUtil.getString(col.getIsComment()));
			hm.put("offlinenum", StringUtil.getString(col.getOfflineNum()));
			hm.put("issearch", NumberUtil.getInt(col.getIssearch())+"");
			hm.put("infonum", StringUtil.getString(col.getInfoNum()));
			hm.put("time", StringUtil.getString(col.getCreateTime().getTime()));
			sortType=NumberUtil.getInt(col.getSortType());
			//栏目下信息的排序方式： 0自定义排序    1按照发布时间降序
			if(sortType==0){
				hm.put("ordertype", "1");
			}else{
				hm.put("ordertype", "2");
			}
			resource.add(hm);
		}
		return resource;
	} 
	
	/**
	 * listToJson
	 * @param cols cols
	 * @return    设定参数 .
	 */
	private List<HashMap<String, String>> listToJsonSubscribe(List<Col> cols, int type){
		List<HashMap<String, String>> resource = new ArrayList<HashMap<String, String>>();
		if(cols == null){
			return resource;
		}
		String commontype = "";
		//int hdType = 0;
		//int colType = 0;
		String hudongurl = "";
		OfflineZip offlinezip = null;
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}
		int sortType=0;
		for(Col col : cols){
			HashMap<String, String> hm = new HashMap<String, String>();
			offlinezip = offlineZipService.findByColId(
					col.getIid(), col.getSiteId());
			if(offlinezip==null){
			  hm.put("ziptime", "0");	
			}else{
			  hm.put("ziptime", offlinezip.getZipTime().getTime()+"");
			}
			hm.put("resourceid", StringUtil.getString(col.getIid()));
			hm.put("resourcename", StringUtil.getString(col.getName()));
			hm.put("resourcetype", StringUtil.getString(col.getType()));
			hm.put("hudongtype", StringUtil.getString(col.getHdType()));
			//hdType = NumberUtil.getInt(col.getHdType());
			//colType = NumberUtil.getInt(col.getType());
			//频道下栏目接口、栏目下子栏目接口、离线下载栏目接口
			if(type==1){
				hm.put("cateimgurl", StringUtil.getString(jmpUrl + col.getActUrl() + "?" + col.getFirstPicUuid()));
			}else{
				//订阅栏目接口
				hm.put("cateimgurl", StringUtil.getString(jmpUrl + col.getIconPath() + "?" + col.getImgUuid()));
			}
			hm.put("islogin", StringUtil.getString(0));
			hm.put("orderid", StringUtil.getString(col.getOrderId()));
			hm.put("parid", StringUtil.getString(col.getPid()));
			hm.put("weibotype", StringUtil.getString(col.getBlogType()));
			hm.put("islogin", StringUtil.getString(col.getIsVisit()));
			if(NumberUtil.getInt(col.getCommonType())<=0){
				commontype="1";
			}else{
				commontype=StringUtil.getString(col.getCommonType());
			}
			hm.put("commontype", commontype);
			hm.put("bannerid", StringUtil.getString(col.getBannerId()));
			hm.put("classid", NumberUtil.getInt(col.getBookDimensionId()) + "");
			hudongurl = StringUtil.getString(col.getActUrl());
			hm.put("hudongurl", hudongurl);
			hm.put("inventtype", StringUtil.getString(NumberUtil.getInt(col.getColListType())));
			hm.put("iscomment", StringUtil.getString(col.getIsComment()));
			hm.put("offlinenum", StringUtil.getString(col.getOfflineNum()));
			hm.put("issearch", NumberUtil.getInt(col.getIssearch())+"");
			hm.put("infonum", StringUtil.getString(col.getInfoNum()));
			hm.put("lightappurl", StringUtil.getString(col.getLightAppUrl()));
			sortType = NumberUtil.getInt(col.getSortType());
			//栏目下信息的排序方式： 0自定义排序    1按照发布时间降序
			if(sortType==0){
				hm.put("ordertype", "1");
			}else{
				hm.put("ordertype", "2");
			}
			resource.add(hm);
		}
		return resource;
	} 
	
	/**
	 * 离线下载
	 * @param siteid siteid
	 * @param cateid cateid
	 * @return    设定参数 .
	 */
	@RequestMapping("offlinedownload")
	@ResponseBody
	public ModelAndView offline(Integer siteid, Integer resourceid){
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}
		jmpUrl += "/web/site" + siteid + "/zip/" + resourceid + ".zip";
		return new ModelAndView(new RedirectView(jmpUrl));
	}
	
	/**
	 * zip更新
	 * @param siteid siteid
	 * @param cateid cateid
	 * @return    设定参数 .
	 */
	@RequestMapping("offlineupdate.do")
	@ResponseBody
	public String isZipUpdate(String resid, String ziptime){
		if(StringUtil.isEmpty(resid) || StringUtil.isEmpty(ziptime)){ 
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_ZIPUPDATE, 
					InterfaceLogConfig.ERROR_07); 
		}
		String[] colIds = StringUtil.split(resid, ",");
		String[] ziptimes = StringUtil.split(ziptime, ",");
		long newZipTime = 0 ;
		long oldZipTime = 0;
		OfflineZip offlinezip = null;
		Col col = null;
		HashMap<String, Object> ret = new HashMap<String, Object>();
		List<HashMap<String, String>> resource = new ArrayList<HashMap<String, String>>();	
			for(int i=0; i<colIds.length; i++){
				col = colService.findByIid(NumberUtil.getInt(colIds[i]));
				if(col==null){
					HashMap<String,String> hm = new HashMap<String,String>();
					hm.put("resourceid", colIds[i]);
					hm.put("ziptime", ziptimes[i]);
					hm.put("isupdate", "0");
					resource.add(hm);
				}else{
					offlinezip = offlineZipService.findByColId(
						col.getIid(), col.getSiteId());
					if(offlinezip==null){
						newZipTime = 0;
					}else{
						newZipTime = offlinezip.getZipTime().getTime();
					}
					oldZipTime = NumberUtil.getLong(ziptimes[i]);
					HashMap<String,String> hm = new HashMap<String,String>();
					hm.put("resourceid", colIds[i]);
					hm.put("ziptime", newZipTime+"");
			
					if(newZipTime==oldZipTime){
						hm.put("isupdate", "0");
					}else{
					hm.put("isupdate", "1");
					}
					resource.add(hm);
				}
			}
			ret.put("resource", resource);
			return JsonUtil.objectToString(ret);
	}
	
	/**
	 * 根据栏目id获取应用
	 * @param siteid siteid
	 * @param cateid cateid
	 * @return    设定参数 .
	 */
	@RequestMapping("getapplist")
	@ResponseBody
	public String getAppList(Integer siteid, Integer colId){
		Map<Object,Object> result = new HashMap<Object, Object>();
		Map<Object,Object> app = new HashMap<Object, Object>();
		List<Map<Object,Object>> apps = new ArrayList<Map<Object,Object>>();
		if(siteid == null || colId == null){
			result.put("result", "false");
			result.put("message", "非法参数");
			return JsonUtil.objectToString(result);
		}
		
		Col col = colService.findByIid(colId);
		
		if(col == null){
			result.put("result", "false");
			result.put("message", "未找到栏目");
			return JsonUtil.objectToString(result);
		}
		
		Integer type = col.getType();
		if(type != 3){
			result.put("result", "false");
			result.put("message", "栏目类型不符合规范");
			return JsonUtil.objectToString(result);
		}
		String ids = col.getNewLightAppId();
		List<LightApp> appsList = lightAppService.findByAppIds(ids);
		if(appsList == null){
			result.put("result", "false");
			result.put("message", "未找到应用");
			return JsonUtil.objectToString(result);
		}
		
		for(LightApp applist : appsList){
			app = new HashMap<Object, Object>();
			app.put("appname", applist.getName());
			app.put("appid", applist.getIid());
			app.put("hudongtype", StringUtil.getString(applist.getAppType()));
            app.put("isshowtopview", StringUtil.getString(applist.getIsShowTopView()));
            app.put("lightapptype", StringUtil.getString(applist.getAppType()));
            app.put("url", applist.getUrl());
            app.put("isopen", applist.getIsOpen()+"");
            app.put("iconpath", BaseInfo.getDomain()+"/lightapp/icon/"+applist.getIid()+"/" + applist.getIconNewName());
            apps.add(app);
		}
		result.put("result", "true");
		result.put("applayout", col.getAppLayOut());
		result.put("apps", apps);
		return JsonUtil.objectToString(result);
		
	}
	
	/**
	 * 
	 * @param siteid
	 * @param iid
	 * @return
	 */
	@RequestMapping("getcomppage")
	@ResponseBody
	public String getAppList1(Integer channelid){
		HashMap<String, Object> ret = new HashMap<String, Object>();
		try {
			List<Col> cols = colService.findColByChanId(NumberUtil.getInt(channelid));
			Channel channel = channelService.findByIid(NumberUtil.getInt(channelid));
			ret.put("flag", StringUtil.getString(NumberUtil.getInt(channel.getFlag())));
			ret.put("resource", colToChannel(cols));
		} catch (Exception e) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_CHANCATES, 
					InterfaceLogConfig.ERROR_08);
		}
		return JsonUtil.objectToString(ret);
		
	}
	
	/**
	 * 将栏目下的应用和信息组织成json
	 * @param cols
	 * @return
	 */
	private List<HashMap<String, Object>> colToChannel(List<Col> cols){
		List<HashMap<String, Object>> resource = new ArrayList<HashMap<String, Object>>();
		List<Map<Object,Object>> apps = new ArrayList<Map<Object,Object>>();
		Map<Object,Object> app = new HashMap<Object, Object>();
		List<Map<String, String>> info ;
		if(cols == null){
			return resource;
		}
		String commontype="";
		String hudongurl="";
		OfflineZip offlinezip = null;
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}
		int sortType=0;
		for(Col col : cols){
			HashMap<String, Object> hm = new HashMap<String, Object>();
			offlinezip = offlineZipService.findByColId(col.getIid(), col.getSiteId());
			if(offlinezip==null){
			  hm.put("ziptime", "0");	
			}else{
			  hm.put("ziptime", offlinezip.getZipTime().getTime()+"");
			}
			hm.put("resourceid", StringUtil.getString(col.getIid()));
			hm.put("resourcename", StringUtil.getString(col.getName()));
			hm.put("resourcetype", StringUtil.getString(col.getType()));
			hm.put("spec", StringUtil.getString(col.getSpec()));
			
			//应用栏目
			if(col.getType() == 3 && col.getNewLightAppId() != null){
				hm.put("applayout", col.getAppLayOut());
				apps = new ArrayList<Map<Object,Object>>();
				List<LightApp> appsList = lightAppService.findByAppIds(col.getNewLightAppId());
				for(LightApp lightApp : appsList){
					app = new HashMap<Object, Object>();
					app.put("appid", lightApp.getIid());
					app.put("appname", lightApp.getName());
					app.put("hudongtype", StringUtil.getString(lightApp.getAppType()));
		            app.put("isshowtopview", StringUtil.getString(lightApp.getIsShowTopView()));
		            app.put("lightapptype", StringUtil.getString(lightApp.getAppType()));
		            app.put("url", lightApp.getUrl());
		            app.put("isopen", lightApp.getIsOpen()+"");
		            app.put("iconpath", BaseInfo.getDomain()+"/lightapp/icon/"+lightApp.getIid()+"/" + lightApp.getIconNewName());
		            apps.add(app);
				}
				hm.put("apps", apps);
			}
			
			//信息列表栏目
			if(col.getType() == 2){
				List<Info> infoList = infoService.findInfoListByColid(col.getIid(), col.getSiteId());
				info = new ArrayList<Map<String, String>>();
				info = infoInterfaceService.infoList(infoList, 1, col, true, 0);
				hm.put("infos", info);
			}
			
			
			//订阅栏目接口 cateimgurl：3.1.12中将iconpath改为cateimgurl
			hm.put("cateimgurl", StringUtil.getString(jmpUrl+ col.getIconPath() + "?" + col.getImgUuid()));
			hm.put("islogin", StringUtil.getString(col.getIsVisit()));
			hm.put("orderid", StringUtil.getString(col.getOrderId()));
			hm.put("parid", StringUtil.getString(col.getPid()));
//			hm.put("weibotype", StringUtil.getString(col.getBlogType()));//3.1.12中注释
			if(NumberUtil.getInt(col.getCommonType())<=0){
				commontype="1";
			}else{
				commontype=StringUtil.getString(col.getCommonType());
			}
			if(col.getCommonType() == 6){
				hm.put("showtype",NumberUtil.getInt(col.getShowType())+"");
			}
			hm.put("commontype", commontype);
			hm.put("bannerid", StringUtil.getString(col.getBannerId()));
			hm.put("classid", NumberUtil.getInt(col.getBookDimensionId()) + "");
			hudongurl=StringUtil.getString(col.getActUrl());
			hm.put("hudongurl", hudongurl);
			hm.put("inventtype", StringUtil.getString(NumberUtil.getInt(col.getColListType())));
			hm.put("iscomment", StringUtil.getString(col.getIsComment()));
			hm.put("offlinenum", StringUtil.getString(col.getOfflineNum()));
			hm.put("issearch", NumberUtil.getInt(col.getIssearch())+"");
			hm.put("infonum", StringUtil.getString(col.getInfoNum()));
			hm.put("time", StringUtil.getString(col.getCreateTime().getTime()));
			sortType=NumberUtil.getInt(col.getSortType());
			//栏目下信息的排序方式： 0自定义排序    1按照发布时间降序
			if(sortType==0){
				hm.put("ordertype", "1");
			}else{
				hm.put("ordertype", "2");
			}
			resource.add(hm);
		}
		return resource;
	} 
	
}