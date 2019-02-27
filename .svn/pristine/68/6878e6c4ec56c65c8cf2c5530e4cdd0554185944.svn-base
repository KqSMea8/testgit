package com.hanweb.jmp.apps.controller.read.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.common.util.file.LocalFileUtil;
import com.hanweb.jmp.annotation.InterfaceCache;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.InterfaceLogConfig;

import com.hanweb.jmp.apps.entity.read.Read;
import com.hanweb.jmp.apps.service.read.ReadService;

@Controller
@RequestMapping("interfaces")
public class ReadInterController {
	
	/**
	 * readService
	 */
	@Autowired
	private ReadService readService;
	
	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;
	
	/**
	 * 阅读分类接口
	 * @param siteid 网站id
	 * @param clienttype 客户端类型
	 * @param uuid uuid
	 * @param version 版本号
	 * @param parid 父ID
	 * @return json 
	 */
	@RequestMapping("readcates")
	@ResponseBody
	@InterfaceCache
	public String readCol(Integer siteid, Integer clienttype, String uuid, String version, Integer parid){
		HashMap<String, Object> ret = new HashMap<String, Object>();
		if(siteid == null || NumberUtil.getInt(parid) < 0) {
			return InterfaceLogConfig.interfaceResult(false, 
					InterfaceLogConfig.MOD_EBOOK, InterfaceLogConfig.ERROR_07); 
		}
		List<Read> readList = readService.findByPid(NumberUtil.getInt(parid), NumberUtil.getInt(siteid));
		List<HashMap<String, String>> resource = new ArrayList<HashMap<String, String>>();
		if(readList.size()==0){
			ret.put("resource", resource);
			return JsonUtil.objectToString(ret);
		}
		ret.put("flag", StringUtil.getString(readList.get(0).getFlag()));
		String changeTime = "";
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz() != LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			}
		}
		for(Read read : readList){
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("parname", StringUtil.getString(read.getPname()));
			hm.put("resourceid", StringUtil.getString(read.getIid()));
			hm.put("resourcename", StringUtil.getString(read.getName()));
			hm.put("cateimgurl", jmpUrl + StringUtil.getString(read.getPicPath()));
			if( read.getChangeTime() != null) {
				changeTime = StringUtil.getString(read.getChangeTime().getTime());
				hm.put("changetime", changeTime);
			}else{
				hm.put("changetime", "");
			}
			hm.put("changetime", changeTime);
			hm.put("orderid", StringUtil.getString(read.getOrderId()));
			hm.put("parid", StringUtil.getString(read.getPid()));
			hm.put("type", StringUtil.getString(read.getType()));
			resource.add(hm);
		}
		ret.put("resource", resource);
		return JsonUtil.objectToString(ret);
	}
	
	/**
	 * 阅读书籍接口
	 * @param siteid 网站ID
	 * @param clienttype 客户端类型
	 * @param uuid uuid
	 * @param version 版本号
	 * @param resourceid 阅读id
	 * @return json
	 */
	@RequestMapping("readdetail")
	@ResponseBody
	@InterfaceCache
	public String readDetail(Integer siteid, Integer clienttype, String uuid, String version, Integer resourceid){
		HashMap<String, Object> ret = new HashMap<String, Object>();
		if(siteid == null || resourceid == null) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_EBOOK, InterfaceLogConfig.ERROR_07); 
		}
		Read read = readService.findByIdAndSiteId(resourceid, siteid);
		String time = "";
		String changeTime = "";
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz() != LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}
		if(read == null) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_EBOOK, InterfaceLogConfig.ERROR_13); 
		}
		ret.put("titleid", StringUtil.getString(read.getIid()));
		ret.put("titletext", StringUtil.getString(read.getName()));
		ret.put("titlesubtext", StringUtil.getString(read.getSpec()));
		if(read.getPubTime() != null) {
			time = StringUtil.getString(read.getPubTime().getTime());
		}
		ret.put("time", time);
		if(read.getChangeTime() != null) {
			changeTime = StringUtil.getString(read.getChangeTime().getTime());
		}
		ret.put("changetime", changeTime);
		ret.put("source", StringUtil.getString(read.getAuthor()));
		int index = StringUtil.getString(read.getPicsize()).indexOf(".");
		int length = StringUtil.getString(read.getPicsize()).length();
		if(index > 0 && index + 3 < length){
			index = StringUtil.getString(read.getPicsize()).indexOf(".");
			String size = StringUtil.getString(read.getPicsize()).substring(0, index + 3);
			ret.put("size", size + "M");
		} else {
			ret.put("size", StringUtil.getString(read.getPicsize())+"M");
		}
		ret.put("url", jmpUrl + StringUtil.getString(read.getBigPath()));
		ret.put("imgurl", jmpUrl + StringUtil.getString(read.getPicPath()));
		ret.put("downloadurl", jmpUrl + StringUtil.getString(read.getFilePath()));	
		return JsonUtil.objectToString(ret);
	}
	
}