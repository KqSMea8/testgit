package com.hanweb.jmp.interfaces.app;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody; 

import com.hanweb.common.BaseInfo; 
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.common.util.file.LocalFileUtil;

import com.hanweb.jmp.annotation.InterfaceCache;
import com.hanweb.jmp.cms.entity.channels.Channel;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.cms.entity.infos.InfoCount;
import com.hanweb.jmp.cms.entity.infos.Pic;
import com.hanweb.jmp.cms.service.channels.ChannelService;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.cms.service.infos.InfoCountService;
import com.hanweb.jmp.cms.service.infos.InfoService;
import com.hanweb.jmp.cms.service.infos.PicService;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.InterfaceLogConfig;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.entity.version.Version;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.jmp.sys.service.version.VersionService;
import com.hanweb.jmp.util.StrUtil; 
import com.hanweb.jmp.util.CacheUtil;
import com.hanweb.jmp.constant.StaticValues;

@Controller
@RequestMapping("interfaces")
public class InfoContentController {
	
	/**
	 * infoService
	 */
	@Autowired
	InfoService infoService;
	
	/**
	 * siteService
	 */
	@Autowired
	SiteService siteService;
	
	/**
	 * picService
	 */
	@Autowired
	PicService picService;
	 
	/**
	 * colService
	 */
	@Autowired
	private ColService colService;
	
	/**
	 * infoCountService 
	 */
	@Autowired
	private InfoCountService infoCountService;
	
	/**
	 * channelService
	 */
	@Autowired
	private ChannelService channelService;
	
	/**
	 * versionService
	 */
	@Autowired
	VersionService versionService; 
	
	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;
	
	/**
	 * 信息正文接口 1.8
	 * @param titleid titleid
	 * @param siteid siteid
	 * @param clienttype clienttype
	 * @param resourceid resourceid
	 * @return String
	 */
	@RequestMapping("infocontent")
	@ResponseBody
	@InterfaceCache
	public String findContent(Integer titleid, Integer siteid, Integer clienttype, Integer resourceid){
		String jsonString="";
		//若信息id不传，则默认获取栏目下面的第一个信息，用于单信息栏目
		if(NumberUtil.getInt(titleid)==0){
			List<Info> infoList=infoService.findInfoByColid(resourceid, siteid); 
			if(!CollectionUtils.isEmpty(infoList)){
				titleid=infoList.get(0).getIid();
			}
		}
		String key = infoService.findKey(siteid, titleid);
		jsonString = StringUtil.getString(CacheUtil.getValue(StaticValues.CACHE_REGION, key));
		if(StringUtil.isNotEmpty(jsonString)){
			return jsonString;
		}else{		
			jsonString = infoService.findContentJson(titleid, siteid, clienttype, 0);
			//数据正确才放入缓存
			if(jsonString!=null && jsonString.indexOf("titleid")>-1){
				CacheUtil.setValue(StaticValues.CACHE_REGION, key, jsonString);
			}
		}
		return jsonString;
	}
	
	/**
	 * 信息详细属性接口
	 * @param titleid 推送信息id
	 * @param siteid 网站id
	 * @param resourceid 单信息栏目的栏目id
	 * @return String
	 */
	@RequestMapping("infodetail")
	@ResponseBody
	@InterfaceCache
	public String findInfoDetail(Integer titleid, Integer siteid, Integer resourceid){
		Map<String, String> result = new HashMap<String, String>();
		Info info = infoService.findByIid(NumberUtil.getInt(titleid), NumberUtil.getInt(siteid));
		Col col = colService.findByIid(NumberUtil.getInt(resourceid));
		if(info != null && info.getIid() > 0){
			Col c = colService.findByIid(info.getColId());
			getJSON(info, c, result);
		} else if (col != null && col.getIid() > 0){
			List<Info> infos = infoService.findAllOfInfoByColid(resourceid, siteid, 0);
			if(infos != null && infos.size() > 0){
				Info i = infos.get(0);
				getJSON(i, col, result);
			}
		}
		return JsonUtil.objectToString(result);
	}
	
    /**
     * 获取JSON对象
     * @param info info
     * @param c c
     * @param result    设定参数 .
    */
    private void getJSON(Info info, Col c, Map<String, String> result){
    	String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}
		StringBuilder imageUrl = new StringBuilder();
		imageUrl.append(StringUtil.getString(info.getFirstPicPath()));
		int infoListType = NumberUtil.getInt(info.getInfoListType());
		if(StringUtil.isEmpty(imageUrl.toString())){
			imageUrl.append(StringUtil.getString(info.getOrignalPicpath()));
		}
		List<String> imgs = StringUtil.toStringList(imageUrl.toString());
		if(infoListType == 4 || infoListType == 7){
			if(imgs.size() < 1){
				infoListType = 2;
			}
		}
		if(infoListType == 6 || infoListType == 8 || infoListType ==9){
			if(imgs.size() < 3){
				infoListType = 2;
			}
		}
		if(imgs.size() > 0){
			imageUrl = new StringBuilder();
			for(String img : imgs){
				if(StringUtil.isNotEmpty(imageUrl.toString())){
					imageUrl.append(",");
				}
				imageUrl.append(jmpUrl + img);
			}
		}
		result.put("titleid", StringUtil.getString(info.getIid()));
		result.put("titletext", StringUtil.getString(info.getTitle()));
		result.put("subtitle", StringUtil.getString(info.getSubTitle()));
		if(info.getSynTime()==null){
			result.put("time", StringUtil.getString(new Date().getTime()));
		}else{
			result.put("time", StringUtil.getString(info.getSynTime().getTime()));
		}
		if(c!=null){
		    result.put("resname", StringUtil.getString(c.getName()));
	        result.put("resourceid", StringUtil.getString(c.getIid()));
			result.put("iscomment", StringUtil.getString(c.getIsComment()));
		}else{
			result.put("iscomment", "0");
		}
		String infotype="";
		result.put("source", StringUtil.getString(info.getSource()));
		result.put("orderid", StringUtil.getString(info.getOrderid()));
		result.put("topid", StringUtil.getString(info.getTopId()));
		if(info.getInfoContentType() == null || info.getInfoContentType() <= 0){
			infotype = StringUtil.getString(c.getInfoContentType());
		}else{
			infotype = StringUtil.getString(info.getInfoContentType());
		}
		result.put("infotype", infotype);
		if(NumberUtil.getInt(info.getInfoContentType()) == 5){
			result.put("url", StringUtil.getString(info.getUrl()));
		}else if(NumberUtil.getInt(info.getInfoContentType()) == 6){
			String vedioUrl = info.getVedio();
			if(StringUtil.isNotEmpty(vedioUrl) && !vedioUrl.startsWith("http")){
				vedioUrl = jmpUrl + info.getVedio();
			}
			result.put("url", StringUtil.getString(vedioUrl));
		}else{
			result.put("url", "");
		}
		result.put("poitype", StringUtil.getString(info.getPointType()));
		result.put("address", StringUtil.getString(info.getAddress()));
		result.put("poilocation", StringUtil.getString(info.getPointLocation()));
		result.put("ztid", StringUtil.getString(info.getZtId()));
		if(info.getZtId() > 0 && info.getInfoContentType() == 8){//专题指向栏目
			Col ztCol = colService.findByIid(info.getZtId());
			result.put("zname", StringUtil.getString(ztCol.getName()));
		}else if(info.getZtId() > 0 && info.getInfoContentType() == 9){//专题指向频道
			Channel channel = channelService.findByIid(info.getZtId());
			result.put("zname", StringUtil.getString(channel.getName()));
		}else{
			result.put("zname", "");
		}
		if(infoListType <= 0){
			infotype = StringUtil.getString(c.getInfoListType());
		}else{
			infotype = StringUtil.getString(infoListType);
		}
		result.put("listtype", infotype);
		result.put("titlesubtext", StringUtil.getString(info.getAbs()));
		result.put("imageurl", imageUrl.toString());
		if(infoListType==10){
			InfoCount infoCount=infoCountService.findByInfoId(info.getIid(), 1, "", c.getSiteId());
			if(infoCount!=null && NumberUtil.getInt(infoCount.getCommentCount())>0){
				result.put("commentcount", StringUtil.getString(NumberUtil.getInt(infoCount.getCommentCount())));
			}else{
				result.put("commentcount", "0");
			}
		}
		//自定义字段
		Map<String, Object> infoExpand=info.getInfoExpand();
		if(infoExpand != null){
			for(Map.Entry<String, Object> entry:infoExpand.entrySet()){
				result.put(entry.getKey(), StringUtil.getString(infoExpand.get(entry.getKey())));
			}
		}
	}
	
	/**
	 * 信息分享
	 * @param request  request
	 * @param titleid titleid
	 * @param siteid siteid
	 * @param uuid uuid
	 * @return    字符 .
	*/
	@RequestMapping("shareinfo")
	@ResponseBody
	@InterfaceCache
	public String shareInfo(HttpServletRequest request, Integer titleid, Integer siteid, String uuid){
		if(NumberUtil.getInt(titleid)<=0 || NumberUtil.getInt(siteid)<=0){ 
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_INFOCONTENT, 
					InterfaceLogConfig.ERROR_03); 
		}
		//阅读数加1
		infoCountService.mVisitCount(titleid, 1, siteid);
		Info info = infoService.findByIid(titleid, siteid);
		int infoContentType = NumberUtil.getInt(info.getInfoContentType());
		String modelhtml = "";
		//酷图
		if(infoContentType==4){
			modelhtml = findsharePic(titleid, siteid);
		//文章
		}else{
			modelhtml = findshareInfo(request, titleid, siteid, uuid);
		}
		return modelhtml;
	}
	
	/**
	 * 信息分享页面组织
	 * @param request  request
	 * @param titleid titleid
	 * @param siteid siteid
	 * @param uuid uuid
	 * @return    字符 .
	 */ 
	private String findshareInfo(HttpServletRequest request, Integer titleid, Integer siteid, String uuid){ 
		String htmlname = "";
		String downurl = "";
		Site site = siteService.findByIid(siteid);
		Version version = null;
		Integer clientType = 0;
		//iphone
		if(request.getHeader("user-agent")!=null && (request.getHeader("user-agent").toLowerCase().indexOf("iphone")!=-1)) { 
			clientType = 2;
		//ipad
		}else if(request.getHeader("user-agent")!=null && (request.getHeader("user-agent").toLowerCase().indexOf("ipad")!=-1)) { 
			clientType = 4; 
		//android
		}else if(request.getHeader("user-agent")!=null && (request.getHeader("user-agent").toLowerCase().indexOf("android")!=-1)) { 
			clientType = 3; 
		} 
		if(NumberUtil.getInt(clientType)>0){
			version = versionService.findNewVersionClient(NumberUtil.getInt(siteid), clientType);
			if(version != null){
				downurl = version.getDownUrl();
			}
		}
		htmlname = "shareinfo.html";
		//先读取网站下面的模板
		String realpath = BaseInfo.getRealPath()+"/resources/jmp/interface/html/"
			            + NumberUtil.getInt(siteid)+"/"+htmlname;
		File htmlFile = new File(realpath);
		//网站模板不存在，则使用公共模板
		if(!htmlFile.exists()){
			realpath = BaseInfo.getRealPath()+"/resources/jmp/interface/html/"+htmlname;
			htmlFile = new File(realpath);
		}
		String modelhtml = FileUtil.readFileToString(htmlFile);
		Info info = infoService.findByIid(titleid, siteid);
		Col col = colService.findByIid(info.getColId());
		String content = infoService.findContent(BaseInfo.getRealPath() + info.getPath());
		info.setContent(content);
		
        //是否显示查看原文和下载客户端
		String showdownurl = "";
		String showsource = "";
		if(StringUtil.isNotEmpty(downurl)){
			showdownurl = "block"; 
		}else{
			showdownurl = "none";  
		} 
		if(StringUtil.isNotEmpty(info.getUrl())){
			showsource = "block";
		}else{
			showsource = "none"; 
		}
		InfoCount infoCount = infoCountService.findByInfoId(titleid, 1, uuid, siteid);
		//模板解析
		modelhtml = modelhtml.replace("{title}", StringUtil.getString(info.getTitle()))
		                     .replace("{webname}", StringUtil.getString(site.getName()))
		                     .replace("{content}", StringUtil.getString(info.getContent()))
			                 .replace("{jmpurl}", Configs.getConfigs().getJmpUrl())
			                 .replace("{downurl}", StringUtil.getString(downurl))
			                 .replace("{source}", StringUtil.getString(info.getSource()))
			                 .replace("{subtitle}", StringUtil.getString(info.getSubTitle()))
			                 .replace("{colname}", StringUtil.getString(col.getName()))
			                 .replace("{synshowtime}", DateUtil.dateToString(info.getSynTime(), DateUtil.YYYY_MM_DD)) 
			           		 .replace("{url}", StringUtil.getString(info.getUrl()))
			           		 .replace("{showdownurl}", StringUtil.getString(showdownurl))
			           		 .replace("{showsource}", StringUtil.getString(showsource))
			           		 .replace("{visitcount}", StringUtil.getString(infoCount.getVisitCount())) 
			           		 .replace("{goodcount}", StringUtil.getString(infoCount.getGoodCount()));
		return modelhtml;
	}
	
	/**
	 * 组图分享页面组织
	 * @param titleid titleid  
	 * @param siteid siteid 
	 * @return    String设定参数 .
	 */ 
	private String  findsharePic(Integer titleid, Integer siteid){ 
		Info info = infoService.findByIid(titleid, siteid);  
	    String htmlname="sharepic.html";
		//先读取网站下面的模板
		String realpath = BaseInfo.getRealPath()+"/resources/jmp/interface/html/"
			            + NumberUtil.getInt(siteid)+"/"+htmlname;
		File htmlFile=new File(realpath);
		//网站模板不存在，则使用公共模板
		if(!htmlFile.exists()){
			realpath = BaseInfo.getRealPath()+"/resources/jmp/interface/html/"+htmlname;
			htmlFile = new File(realpath);
		}
		String strBody=FileUtil.readFileToString(htmlFile);
	    StrUtil strUtil = new StrUtil();
		//图片滚动效果和幻灯片效果  
	    String everyCont=strUtil.getContentByTag("<!--pic start-->", "<!--pic end-->", strBody);
		String replaceCont="";
        List<Pic> pics = picService.findByInfoid(titleid, siteid);   
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}
	    for(Pic picEn : pics){
	    	if(picEn==null){
	    		continue;
	    	} 
	    	replaceCont+=everyCont.replace("{picpath}", jmpUrl + picEn.getPicpath())
	    			              .replace("{picnote}", picEn.getPicabstract())
	    			              .replace("{title}", info.getTitle())
	    			              .replace("{picsource}", info.getSource());
	    } 
	    strBody = strUtil.replaceContByTag("<!--pic start-->", "<!--pic end-->", replaceCont, strBody); 
		strBody = strBody.replace("{jmpurl}", Configs.getConfigs().getJmpUrl())
					     .replace("{title}", info.getTitle());
		return strBody;
	}
	
	/**
	 * 查询图片
	 * @param titleid titleid
	 * @param siteid siteid
	 * @param clienttype clienttype
	 * @return    设定参数 .
	*/
	@RequestMapping("pic")
	@ResponseBody
	@InterfaceCache
	public String findPic(Integer titleid, Integer siteid, Integer clienttype){
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}
		Map<String, Object> infoMap = new HashMap<String, Object>();
		if(NumberUtil.getInt(titleid)<=0 || NumberUtil.getInt(siteid)<=0){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_PIC, 
					InterfaceLogConfig.ERROR_03); 
		}
		Info info = infoService.findByIid(titleid, siteid);
		List<Pic> pics = picService.findByInfoid(titleid, siteid); 
		if(info == null){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_PIC, 
					InterfaceLogConfig.ERROR_03); 
		}
		infoMap.put("titleid", StringUtil.getString(info.getIid()));
		infoMap.put("titletext", info.getTitle());
		infoMap.put("titlesubtext", info.getAbs());
		infoMap.put("time", StringUtil.getString(info.getSynTime().getTime()));
		infoMap.put("source", info.getSource()); 
		List<Map<String, String>> picMapList = new ArrayList<Map<String, String>>();
		Map<String, String> picMap = null;
		for(Pic picEn: pics){
			if(picEn==null){
				continue;
			}
			picMap = new HashMap<String, String>(); 
			picMap.put("picurl", jmpUrl+picEn.getPicpath());
			picMap.put("titlesubtext", StringUtil.getString(picEn.getPicabstract())); 
			picMap.put("time", StringUtil.getString(picEn.getCreateTime().getTime()));
			picMap.put("titleid", StringUtil.getString(picEn.getIid()));
			picMap.put("titletext", StringUtil.getString(info.getTitle()));
			picMap.put("source", StringUtil.getString(info.getSource()));
			picMapList.add(picMap);
		} 
		infoMap.put("pics", picMapList);
		infoMap.put("downurl", Configs.getConfigs().getJmpUrl()+"/client/"+siteid+"_"+titleid);
		return JsonUtil.objectToString(infoMap);
	}
	
}