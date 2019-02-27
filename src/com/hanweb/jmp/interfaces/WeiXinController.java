package com.hanweb.jmp.interfaces;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.SpringUtil; 
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.complat.listener.OnlineUserListener;

import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.cms.service.infos.InfoService;
import com.hanweb.jmp.global.entity.jsonentity.InfoJSON;
import com.hanweb.jmp.util.StrUtil;

/**
 * 微信分享接口
 * @author duweibn
 *
 */
@Controller
@RequestMapping("interfaces")
public class WeiXinController {
	
	/**
	 * InfoService
	 */
	private InfoService infoService;  

	/**
	 * 返回信息具体内容
	 * @param json String
	 * @return String
	 */
	@RequestMapping("weixin")
	@ResponseBody
	public String findInfoEntityList(String json){
		IFileUtil fileUtil = (IFileUtil)SpringUtil.getBean("FileUtil"); 
		infoService = SpringUtil.getBean("jmp_InfoService", InfoService.class);
		int infoId=0;
		int siteId=0;
		JSONArray jsonArr =  JSONArray.fromObject(json);
		String reStr = "["; 
		List<InfoJSON> infoJsonList = new ArrayList<InfoJSON>();
		try{ 
			InfoJSON infoJson2 = null;
			Info info = null;
			String author = null;
			String title = null;
			String content = null;
			String abs = null;
			String infoPath="";
			for(int i=0; i<jsonArr.size(); i++){
				infoId = jsonArr.getJSONObject(i).getInt("artId");
				siteId = jsonArr.getJSONObject(i).getInt("webId");
				info = infoService.findByIid(infoId, siteId); 
				if(info==null){
					continue; 
				} 
				infoJson2 = new InfoJSON();
				//作者
			    author = info.getAuthor();
				author = author.replace("&quot;", "\"");
				author = author.replace("\\", "");
				infoJson2.setVc_author(author);
				//标题		
				title = info.getTitle();
				title = title.replace("&quot;", "\"");
				title = title.replace("\\", "");
				infoJson2.setVc_title(title);	
				infoPath=StringUtil.getString(info.getPath());
				if(infoPath.startsWith("/")){
					infoPath=infoPath.substring(1);
				}
				infoPath=fileUtil.getAbsolutePath(infoPath);
				//正文
				info.setContent(infoService.findContent(infoPath));
				content = info.getContent();
				content = content.replace("&quot;", "\"");
				content = content.replace("\\", "");
				infoJson2.setArtcontent(content);
				
				//摘要
				abs = info.getAbs();
				abs = abs.replace("&quot;", "\"");
				abs = abs.replace("\\", "");
				if(abs.length() > 120){
					abs = StrUtil.subStr(abs, 114);
					abs += "......";
				}
				infoJson2.setVc_describe(abs);
				
				//原文链接
				infoJson2.setVc_href(info.getUrl());
				
				//图片 
				infoJson2.setVc_pic(info.getFirstPicPath());
				infoJsonList.add(infoJson2);
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
	
		reStr = JsonUtil.objectToString(infoJsonList);
		return reStr;
	}
	 
	/**
	 * 检查sessionId合法性
	 * @param sessionId String
	 * @return String
	 */
	@RequestMapping("checksession")
	@ResponseBody
	public String checkSessionValid(String sessionId){ 
		int validInt = 1;
		String str = "{\"valid\":";
		try{
			for (int i = 0; i < OnlineUserListener.ONLINE_ENTITIES.size(); i++) {
				if (OnlineUserListener.ONLINE_ENTITIES.get(i).getSessionId().equals(sessionId)) {
					validInt = 0;
					break;
				}
			} 
			str += "\"" + validInt + "\"}";
			return str;
		
		}catch(Exception e){
			e.printStackTrace();
		   return str + "\"" + validInt + "\"" + "}";
		}
	}
	
}