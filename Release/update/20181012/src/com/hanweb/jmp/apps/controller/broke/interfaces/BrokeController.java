package com.hanweb.jmp.apps.controller.broke.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.common.util.file.LocalFileUtil;
import com.hanweb.common.util.mvc.ControllerUtil;

import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.apps.entity.broke.Broke;
import com.hanweb.jmp.apps.entity.broke.BrokeType;
import com.hanweb.jmp.apps.service.broke.BrokeService;
import com.hanweb.jmp.apps.service.broke.BrokeTypeService;
import com.hanweb.jmp.constant.InterfaceLogConfig;

@Controller
@RequestMapping("interfaces/broke")
public class BrokeController {
	
	/**
	 * brokeService
	 */
	@Autowired
	private BrokeService brokeService;

	/**
	 * brokeTypeService
	 */
	@Autowired
	private BrokeTypeService brokeTypeService;  
	
	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;
	
	/**
	 * 2.6.1 大家的报料
	 * @param siteid 网站id
	 * @param clienttype 客户端类型
	 * @param uuid 客户端uuid
	 * @param version 版本号
	 * @param maxid 最后一天id
	 * @param pagesize 每页多少条
	 * @return String
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public String getAllList(Integer siteid, Integer clienttype, String uuid, String version, 
			                 Integer maxid, Integer pagesize) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		if (NumberUtil.getInt(siteid) <= 0) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_BROKE, 
					InterfaceLogConfig.ERROR_03);
		}
		maxid = NumberUtil.getInt(maxid);
		pagesize = NumberUtil.getInt(pagesize);
		List<Broke> brokeList = brokeService.findInfoList(siteid, pagesize, maxid);
		String picPath = "";
		String picPath1 = "";
		String picPath2 = "";
		String picPath3 = "";
		String audioPath = "";
		String videoPath = "";
		String jmpUrl=Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}
		if(list != null){
			for (Broke broke : brokeList) {
				map = new HashMap<String, String>();
				map.put("id", StringUtil.getString(broke.getIid()));
				map.put("title", StringUtil.getString(broke.getTitle()));
				map.put("classify", StringUtil.getString(broke.getClassify()));
				map.put("time", DateUtil.dateToString(broke.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
				map.put("clienttype", StringUtil.getString(broke.getClientType()));
				if(StringUtil.isNotEmpty(broke.getPicPath())){
					picPath = broke.getPicPath().replace(".", "_middle.");
					picPath = jmpUrl + picPath;
				}else{
					picPath = "";
				}
				if(StringUtil.isNotEmpty(broke.getPicPath1())){
					picPath1 = broke.getPicPath1().replace(".", "_middle.");
					picPath1 = jmpUrl + picPath1;
				}else{
					picPath1 = "";
				}
				if(StringUtil.isNotEmpty(broke.getPicPath2())){
					picPath2 = broke.getPicPath2().replace(".", "_middle.");
					picPath2 = jmpUrl + picPath2;
				}else{
					picPath2 = "";
				}
				if(StringUtil.isNotEmpty(broke.getPicPath3())){
					picPath3 = broke.getPicPath3().replace(".", "_middle.");
					picPath3 = jmpUrl + picPath3;
				}else{
					picPath3 = "";
				}
				if(StringUtil.isNotEmpty(broke.getAudioPath())){
					audioPath = jmpUrl + StringUtil.getString(broke.getAudioPath());
				}else{
					audioPath = "";
				}
				if(StringUtil.isNotEmpty(broke.getVideoPath())){
					videoPath = jmpUrl + StringUtil.getString(broke.getVideoPath());
				}else{
					videoPath = "";
				}
				map.put("picpath", picPath);
				map.put("picpath1", picPath1);
				map.put("picpath2", picPath2);
				map.put("picpath3", picPath3);
				map.put("audiopath", audioPath);
				map.put("videopath", videoPath);
				if(StringUtil.isEmpty(broke.getHeadurl())){
				    map.put("headurl", StringUtil.getString(broke.getHeadurl()));
                } else {
                    map.put("headurl", StringUtil.getString(BaseInfo.getDomain() + broke.getHeadurl()));
                }
				map.put("name", StringUtil.getString(broke.getName()));
				if(StringUtil.isNotEmpty(broke.getReply())){
					broke.setIsReply(true);
				}
				map.put("isreply", StringUtil.getString(broke.getIsReply()));
				if(StringUtil.isEmpty(broke.getName())){
					if(StringUtil.equals(broke.getUuid(), broke.getLoginId())){
						map.put("name", "");
					}else{
						map.put("name", StringUtil.getString(broke.getLoginId()));
					}
				}
				list.add(map);
			}
		}
		result.put("infolist", list);
		return JsonUtil.objectToString(result);
	}
	
	/**
	 * 我的报料
	 * @param siteid 网站id
	 * @param pagesize 每页条数
	 * @param maxid 最后条信息id
	 * @param loginid 登录名
	 * @return String
	 */
	@RequestMapping(value = "mylist")
	@ResponseBody
	public String getMyList(Integer siteid,  Integer pagesize, Integer maxid, String loginid) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		if (NumberUtil.getInt(siteid) == 0 || StringUtil.isEmpty(loginid)) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_BROKE, 
					InterfaceLogConfig.ERROR_03);
		}
		List<Broke> brokeList = brokeService.findMyList(siteid, pagesize, maxid, loginid);
		String picPath = "";
		String picPath1 = "";
		String picPath2 = "";
		String picPath3 = "";
		String audioPath = "";
		String videoPath = "";
		String jmpUrl=Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl=fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl=jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}
		if(list != null){
			for (Broke broke : brokeList) {
				map = new HashMap<String, String>();
				map.put("id", StringUtil.getString(broke.getIid()));
				map.put("title", StringUtil.getString(broke.getTitle()));
				map.put("classify", StringUtil.getString(broke.getClassify()));
				map.put("time", DateUtil.dateToString(broke.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
				map.put("clienttype", StringUtil.getString(broke.getClientType()));
				if(StringUtil.isNotEmpty(broke.getPicPath())){
					picPath = broke.getPicPath().replace(".", "_middle.");
					picPath = jmpUrl + picPath;
				}else{
					picPath = "";
				}
				if(StringUtil.isNotEmpty(broke.getPicPath1())){
					picPath1 = broke.getPicPath1().replace(".", "_middle.");
					picPath1 = jmpUrl + picPath1;
				}else{
					picPath1 = "";
				}
				if(StringUtil.isNotEmpty(broke.getPicPath2())){
					picPath2 = broke.getPicPath2().replace(".", "_middle.");
					picPath2 = jmpUrl + picPath2;
				}else{
					picPath2 = "";
				}
				if(StringUtil.isNotEmpty(broke.getPicPath3())){
					picPath3 = broke.getPicPath3().replace(".", "_middle.");
					picPath3 = jmpUrl + picPath3;
				}else{
					picPath3 = "";
				}
				if(StringUtil.isNotEmpty(broke.getAudioPath())){
					audioPath = jmpUrl + StringUtil.getString(broke.getAudioPath());
				}else{
					audioPath = "";
				}
				if(StringUtil.isNotEmpty(broke.getVideoPath())){
					videoPath = jmpUrl + StringUtil.getString(broke.getVideoPath());
				}else{
					videoPath = "";
				}
				map.put("picpath", picPath);
				map.put("picpath1", picPath1);
				map.put("picpath2", picPath2);
				map.put("picpath3", picPath3);
				map.put("audiopath", audioPath);
				map.put("videopath", videoPath);
				map.put("headurl", StringUtil.getString(broke.getHeadurl()));
				map.put("name", StringUtil.getString(broke.getName()));
				if(StringUtil.isNotEmpty(broke.getReply())){
					broke.setIsReply(true);
				}
				map.put("isreply", StringUtil.getString(broke.getIsReply()));
				if(StringUtil.isEmpty(broke.getName())){
					if(StringUtil.equals(broke.getUuid(), broke.getLoginId())){
						map.put("name", "");
					} else {
						map.put("name", StringUtil.getString(broke.getLoginId()));
					}
				}
				list.add(map);
			}
		}
		result.put("infolist", list);
		return JsonUtil.objectToString(result);
	}
	
	/**
	 * 报料信息分类接口
	 * @param siteid siteid
	 * @return String
	 */
	@RequestMapping(value = "group")
	@ResponseBody
	public String getBrokeInfoGroup(String siteid) {
		if (NumberUtil.getInt(siteid) <= 0) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_BROKE, InterfaceLogConfig.ERROR_03);
		}
		List<BrokeType> list = brokeTypeService.findBySiteId(NumberUtil.getInt(siteid));
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		Map<String, String> resultMap = null;
		if(list != null){
			for(BrokeType group: list){
				resultMap = new HashMap<String, String>();
				resultMap.put("classid", ""+NumberUtil.getInt(group.getIid()));
				resultMap.put("classname", group.getName());
				resultList.add(resultMap);
			}
		}
		return JsonUtil.objectToString(resultList);
	}
	
	/**
	 * 2.6.4 提交报料接口
	 * @param siteid 网站id
	 * @param clienttype 客户端类型
	 * @param uuid uuid
	 * @param loginid 登录名
	 * @param title 报料标题（可不填，自动截取内容）
	 * @param content 报料内容
	 * @param classid 分类id
	 * @param isopen 是否公开（选填 默认是公开）
	 * @param picfile 图片文件流数组
	 * @param audiofile 音频文件流数组
	 * @param videofile 视频文件流数组
	 * @param contact 联系方式
	 * @param version 版本号
	 * @return  String
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "brokeadd")
	@ResponseBody
	public String addBroke(Integer siteid, Integer clienttype, String uuid, String loginid, String title, 
			               String content, Integer classid, String isopen, String picfile, String picfile1, 
			               String picfile2, String picfile3, String audiofile, String videofile, String contact, 
			               String version) {
		BrokeType brokeType =  brokeTypeService.findByIid(classid);
		Map<String, String> result = new HashMap<String, String>();
		/*if(NumberUtil.getInt(siteid) == 0 || NumberUtil.getInt(classid) == 0 || brokeType == null 
			|| StringUtil.isEmpty(loginid) || StringUtil.isEmpty(uuid) || StringUtil.isEmpty(content)){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_BROKE, InterfaceLogConfig.ERROR_03);
		}*/
		if(NumberUtil.getInt(siteid) == 0 || NumberUtil.getInt(classid) == 0 
				|| StringUtil.isEmpty(loginid) || StringUtil.isEmpty(uuid) || StringUtil.isEmpty(content)){
				return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_BROKE, InterfaceLogConfig.ERROR_03);
		}
		if(brokeType == null){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_BROKE, InterfaceLogConfig.ERROR_13);
		}
		boolean autoaudit = false;
		if(NumberUtil.getInt(brokeType.getAuditType()) == 1){
			autoaudit = true;
		}
		BrokeFormBean broke = new BrokeFormBean();
		boolean isSuccess = true;
		try{		
			broke.setSiteId(siteid);
			broke.setUuid(uuid);
			broke.setLoginId(loginid);
			broke.setTitle(title);
			broke.setContent(content);
			broke.setClassId(classid);
			broke.setIsOpen(NumberUtil.getInt(isopen, 1));
			broke.setPicfile(picfile);
			broke.setPicfile1(picfile1);
			broke.setPicfile2(picfile2);
			broke.setPicfile3(picfile3);
			broke.setAudiofile(audiofile);
			broke.setVideofile(videofile);
			broke.setContact(contact);
			broke.setClientType(clienttype);
			broke.setIp(ControllerUtil.getIp());
			isSuccess = brokeService.add(broke);
			result.put("result", "" + isSuccess);
			result.put("autoaudit", "" + autoaudit);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "false");
			result.put("errorCode", "020508");
			result.put("errorMsg", "服务器错误！"+e.getMessage());
		}
		return JsonUtil.objectToString(result);
	}

	/**
	 * detail
	 * @param infoid infoid
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "detail")
	@ResponseBody
	public String detail(Integer infoid) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			Broke info = brokeService.findByIid(infoid);
			if(info == null){
				return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_BROKE, InterfaceLogConfig.ERROR_03);
			}
			String jmpUrl = Configs.getConfigs().getJmpUrl();
			if(fileUtil.getImplClazz()!=LocalFileUtil.class){
				jmpUrl = fileUtil.getURL("");
				if(jmpUrl.endsWith("/")){
					jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
				} 
			}
			String content = StringUtil.getString(info.getContent());
			String pic = info.getPicPath();
			String video = info.getVideoPath();
			String audio = info.getAudioPath();
			if(StringUtil.isNotEmpty(audio)){
				content = "<audio onclick='window.js1.method2()' width='100%' src='" 
					    + jmpUrl + audio
					    + "' controls='controls' preload='preload'></audio><br/>"
					    + content; 
			}
			if(StringUtil.isNotEmpty(video)){
				content = "<video onclick='window.js1.method1()' width='100%' src='"
					    + jmpUrl + video
					    + "' controls='controls' " 
					    + " preload='preload' poster='./submit_video_defualt.png' " 
					    + " <source src='/i/movie.mp4' type='video/mp4'>" 
					    + " <source src='/i/movie.ogg' type='video/ogg'>></video>"
					    + content;  
			}
			if(StringUtil.isNotEmpty(pic)){
				content = "<img width='100%' src='"+jmpUrl+pic+"'/><br/>"
					    + content;
			}
			result.put("title", info.getTitle());
			result.put("createtime", info.getCreateTime());
			result.put("content", content);
			result.put("replytime", info.getReplyTime());
			result.put("replycontent", info.getReply());
		}catch (Exception e){
			e.printStackTrace();
		}
		return JsonUtil.objectToString(result);
	}
	
	/**
	 * 查询报料列表接口
	 * 
	 * @param req
	 * @param response
	 * @param title
	 * @return
	 */
	@RequestMapping(value = "showdetail")
	@ResponseBody
	public String showDetail(String title) {

		List<Map<String, Object>> broke = brokeService.findBrokeByTitle(title);

		Map<String, Object> result = new HashMap<String, Object>();
		if (broke == null) {
			return InterfaceLogConfig.interfaceResult(false,
					InterfaceLogConfig.MOD_BROKE, InterfaceLogConfig.ERROR_03);
		}
		result.put("broke", broke);
		return JsonUtil.objectToString(result);
	}
	
}