package com.hanweb.interfaces.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.beans.factory.annotation.Qualifier;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil; 
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.common.util.file.LocalFileUtil;

import com.hanweb.jmp.cms.entity.channels.Channel;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.cms.service.channels.ChannelService;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.cms.service.infos.InfoService;
import com.hanweb.jmp.constant.Configs; 
import com.hanweb.jmp.constant.InterfaceLogConfig;

 
public class InfoInterfaceService {

	/**
	 * colService
	 */
	@Autowired
	ColService colService;
	
	/**
	 * channelService
	 */
	@Autowired
	ChannelService channelService;
	
	/**
	 * infoService
	 */
	@Autowired
	InfoService infoService;
	
	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;
	
	/**
	 * 组织列表页
	 * 3.1.12增加：当resourceid为多个时，返回多个栏目和信息。
	 * @param infoList 信息list
	 * @param page page
	 * @param col 当信息属于同一个栏目，栏目对象
	 * @param b 信息是否同属该栏目
	 * @param type 信息是否同属该栏目
	 * @return String
	 */
	public String listInfoToJson(Integer page, List<Col> c, boolean b, Integer number, Integer flag, Integer orderid,
			                     Long time,Integer ordertype,Integer topid,Integer isbanner,Integer type){
		//最终的json格式
		Map<String, Object> result = new HashMap<String, Object>(); 

		//结果集放入resourceList
		List<Map<String, Object>> resourceList = null;
		
		//取信息组成infoMapList
		List<Map<String, String>> infoMapList = null ;
		List<Info> infoList = null;
		resourceList = new ArrayList<Map<String, Object>>();
		int colFlag = 0 ;
		for(Col col:c){
			Map<String, Object> resource = new HashMap<String, Object>();
			if(col == null){
				return "";
			}
			if(type != 2 && flag != null && (NumberUtil.getInt(col.getFlag()) != NumberUtil.getInt(flag))){
				type = 1;
				orderid = null;
				time = null;
				topid = null;
			}
			Date date = null;
			if(time != null){
				date = new Date();
				date.setTime(NumberUtil.getLong(time));
			}
			if(ordertype == 1 && type == 1){//orderid 刷新
				infoList = infoService.findGTOrderid(col, orderid, topid, 0, page+1, col.getSiteId(),  isbanner);
			}else if(ordertype == 1 && type == 2){//orderid 更多
				infoList = infoService.findLTOrderid(col, orderid, topid, 0, page+1, col.getSiteId(),  isbanner);
			}else if(ordertype == 2 && type == 1){//时间排序 刷新
				infoList = infoService.findGTTime(col, date, topid, 0, page+1, col.getSiteId(),  isbanner);
			}else if(ordertype == 2 && type == 2){//时间排序 更多
				infoList = infoService.findLTTime(col, date, topid, 0, page+1, col.getSiteId(),  isbanner);
			}
			if(infoList!=null){
			    if(infoList.size() > page){
	                infoList.remove(infoList.size()-1);
	            }
			}else{
			    return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_INFOLIST, 
                        InterfaceLogConfig.ERROR_13); 
			}
			infoMapList = infoList(infoList, page, col, b, number);
			//resource加入的数据
			resource.put("columnstatus", StringUtil.getString(col.getEnable()));
			resource.put("infonum", StringUtil.getString(col.getInfoNum()));
			if(b){
				resource.put("resname", StringUtil.getString(col.getName()));
				resource.put("resourceid", StringUtil.getString(col.getIid()));
			}
			resource.put("createtime", col.getCreateTime().getTime());
			resource.put("resourcetitle", infoMapList);
			int num = infoService.findCountByCateID(col.getIid(), col.getSiteId());
			resource.put("totalinfo", StringUtil.getString(num));
			colFlag = colFlag + col.getFlag();
			result.put("flag", StringUtil.getString(colFlag));
			resourceList.add(resource);
			
			//结果集中加入的数据
			result.put("resource", resourceList);
		}

		return JsonUtil.objectToString(result);
	}
	
	/**
	 * 组织列表页
	 * @param infoList 信息list
	 * @param page page
	 * @param col 当信息属于同一个栏目，栏目对象
	 * @param b 信息是否同属该栏目
	 * @param type 信息是否同属该栏目
	 * @return String
	 */
	public String listToJson(List<Info> infoList, Integer page, Col col, boolean b, Integer type){
		Map<String, Object> result = new HashMap<String, Object>(); 
		if(infoList.size() > page){
			infoList.remove(infoList.size()-1);
		}
		Map<String, Object> resource = new HashMap<String, Object>();
		List<Map<String, Object>> resourceList = new ArrayList<Map<String, Object>>();
		List<Map<String, String>> infoMapList = infoList(infoList, page, col, b, type);
		resource.put("columnstatus", StringUtil.getString(col.getEnable()));
		resource.put("infonum", StringUtil.getString(col.getInfoNum()));
		if(b){
			resource.put("resname", StringUtil.getString(col.getName()));
			resource.put("resourceid", StringUtil.getString(col.getIid()));
		}
		if(col.getCreateTime()!=null){
			resource.put("createtime", col.getCreateTime().getTime());
		}
		resource.put("resourcetitle", infoMapList);
		resourceList.add(resource);
		if(col.getIid() != null && col.getSiteId()!=null){
		    int num = infoService.findCountByCateID(col.getIid(), col.getSiteId());
	        result.put("totalinfo", num);
		}
		result.put("flag", StringUtil.getString(col.getFlag()));
		result.put("resource", resourceList);
		return JsonUtil.objectToString(result);
	}
	
	/**
	 * 组织信息List
	 * @param infoList 信息list
	 * @param page page
	 * @param col 当信息属于同一个栏目，栏目对象
	 * @param b 信息是否同属该栏目
	 * @param type 0代表接口取数据  1代表离线下载
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> infoList(List<Info> infoList, Integer page, Col col, boolean b, Integer type){
		List<Map<String, String>> infoMapList = new ArrayList<Map<String, String>>();
		Map<String, String> infoMap = null;
		long sendtime = 0;
		String infotype = "";
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		} 
		for(Info info : infoList){
			infoMap = new HashMap<String, String>();
			StringBuilder imageUrl = new StringBuilder();
			imageUrl.append(StringUtil.getString(info.getFirstPicPath()));
			int infoListType = NumberUtil.getInt(info.getInfoListType());
			//6.三张图文混排 
			if(infoListType == 6){
				imageUrl = new StringBuilder();
				imageUrl.append(StringUtil.getString(info.getOrignalPicpath()));
			}else if(StringUtil.isEmpty(imageUrl.toString())){
				imageUrl.append(StringUtil.getString(info.getOrignalPicpath()));
			}
			 
			List<String> imgs = StringUtil.toStringList(imageUrl.toString());
		    //2.标题+时间+来源   4.左侧一张图片   7.一张大图+标题格式
			if(infoListType == 4 || infoListType == 7){
				if(imgs.size() < 1){
					infoListType = 2;
				}
			}
			//2.标题+时间+来源    6.三张图文混排   8.纯图左一+右二  9.纯图左二+右一
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
			if(NumberUtil.getInt(type)==0){
			    if(StringUtil.isNotEmpty(info.getImguuid())){
			        infoMap.put("imageurl", imageUrl.toString() + "?" + info.getImguuid());
			    } else {
			        infoMap.put("imageurl", imageUrl.toString());
			    }
				
			}else{
				imgs = StringUtil.toStringList(imageUrl.toString());
				imageUrl = new StringBuilder();
				if(imgs.size() > 0){
					String filename = "";
					for(String img : imgs){
						if(StringUtil.isNotEmpty(imageUrl.toString())){
							imageUrl.append(",");
						}
						filename = img.substring(img.lastIndexOf("/")+1);
						imageUrl.append("./info"+info.getIid()+"/"+ filename);
					}
				}
				if(StringUtil.isNotEmpty(info.getImguuid())){
                    infoMap.put("imageurl", imageUrl.toString() + "?" + info.getImguuid());
                } else {
                    infoMap.put("imageurl", imageUrl.toString());
                }
			}
			if(info.getCreateTime() != null){
				infoMap.put("createtime", StringUtil.getString(info.getCreateTime().getTime()));
			}
			infoMap.put("orderid", StringUtil.getString(info.getOrderid()));
			infoMap.put("topid", StringUtil.getString(info.getTopId()));
			infoMap.put("source", StringUtil.getString(info.getSource()));
			infoMap.put("subtitle", StringUtil.getString(info.getSubTitle()));
			if(info.getSynTime() == null){
				infoMap.put("time", StringUtil.getString(new Date().getTime()));
			}else{
				infoMap.put("time", StringUtil.getString(info.getSynTime().getTime()));
			}
			if(info.getPushTime() == null){
				sendtime = new Date().getTime();
			}else{
				sendtime = info.getPushTime().getTime();
			}
			infoMap.put("sendtime", StringUtil.getString(sendtime));
			infoMap.put("titleid", StringUtil.getString(info.getIid()));
			infoMap.put("audiotime", StringUtil.getString(info.getAudioTime()));
			infoMap.put("titlesubtext", StringUtil.getString(info.getAbs()).trim());
			infoMap.put("titletext", StringUtil.getString(info.getTitle()));
			//音频
			String audioUrl="";
			if(StringUtil.isNotEmpty(info.getAudio())){
				audioUrl = StringUtil.getString(jmpUrl + info.getAudio());
			}
			infoMap.put("audiourl", audioUrl);
			//视频
			if(NumberUtil.getInt(info.getInfoContentType()) == 5){
				infoMap.put("url", StringUtil.getString(info.getUrl()));
			}else if(NumberUtil.getInt(info.getInfoContentType()) == 6){
				String vedioUrl = info.getVedio();
				if(StringUtil.isNotEmpty(vedioUrl) && !vedioUrl.startsWith("http")){
					vedioUrl = jmpUrl + info.getVedio();
				}
				infoMap.put("url", StringUtil.getString(vedioUrl));
			}else{
				infoMap.put("url", "");
			}
			infoMap.put("poitype", StringUtil.getString(info.getPointType()));
			infoMap.put("address", StringUtil.getString(info.getAddress()));
			infoMap.put("poilocation", StringUtil.getString(info.getPointLocation()));
			infoMap.put("ztid", StringUtil.getString(info.getZtId()));
			if(b){ //true:信息所属栏目相同 false：每条信息来自不同栏目
				if(info.getInfoContentType() == null 
						|| NumberUtil.getInt(info.getInfoContentType()) <= 0){
					infotype = StringUtil.getString(col.getInfoContentType());
				}else{
					infotype = StringUtil.getString(info.getInfoContentType());
				}
				infoMap.put("infotype", infotype);
				if(infoListType <= 0){
					infotype = StringUtil.getString(col.getInfoListType());
				}else{
					infotype = StringUtil.getString(infoListType);
				}
				infoMap.put("listtype", infotype);
			}else{
				Col infoCol = colService.findByIid(info.getColId());
				if(info.getInfoContentType() == null 
						|| NumberUtil.getInt(info.getInfoContentType()) <= 0){
					infotype = StringUtil.getString(infoCol.getInfoContentType());
				}else{
					infotype = StringUtil.getString(info.getInfoContentType());
				}
				infoMap.put("infotype", infotype);
				if(infoListType <= 0){
					infotype = StringUtil.getString(infoCol.getInfoListType());
				}else{
					infotype = StringUtil.getString(infoListType);
				}
				infoMap.put("listtype", infotype);
				infoMap.put("resname", StringUtil.getString(infoCol.getName()));
				infoMap.put("resourceid", StringUtil.getString(infoCol.getIid()));
			}
			if(info.getZtId() > 0 && info.getInfoContentType() == 8){//专题指向栏目
				Col ztCol = colService.findByIid(info.getZtId());
				if(ztCol==null){
					continue;
				}
				infoMap.put("zname", StringUtil.getString(ztCol.getName()));
			}else if(info.getZtId() > 0 && info.getInfoContentType() == 9){//专题指向频道
				Channel channel = channelService.findByIid(info.getZtId());
				if(channel==null){
					continue;
				}
				infoMap.put("zname", StringUtil.getString(channel.getName()));
			}else{
				infoMap.put("zname", "");
			}
			infoMap.put("iscomment", StringUtil.getString(col.getIsComment()));
			infoMap.put("commentcount", NumberUtil.getInt(info.getCommentcount())+"");
			infoMap.put("tagname", StringUtil.getString(info.getTagname()));
			infoMap.put("tagcolor", StringUtil.getString(info.getTagcolor()));
			infoMap.put("summary", StringUtil.getString(info.getSummary()));
			//自定义字段
			List<String> customFields = infoService.getCustomField(info.getSiteId());
			if(CollectionUtils.isNotEmpty(customFields)){
				Map<String, Object> infoExpand = info.getInfoExpand();
				for(String field : customFields){
					infoMap.put(field, StringUtil.getString(infoExpand.get(field)));
				}
			}
			
			infoMapList.add(infoMap);	
		}
		return infoMapList;
	}
	
	/**
	 * 获取单个信息的数据
	 * @param info
	 * @param col
	 * @param type
	 * @return
	 */
	public Map<String, Object> getInfo(Info info, Col col, Integer type){
		Map<String, Object> infoMap  = new HashMap<String, Object>();
		long sendtime = 0;
		String infotype = "";
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
			//6.三张图文混排 
			if(infoListType == 6){
				imageUrl = new StringBuilder();
				imageUrl.append(StringUtil.getString(info.getOrignalPicpath()));
			}else if(StringUtil.isEmpty(imageUrl.toString())){
				imageUrl.append(StringUtil.getString(info.getOrignalPicpath()));
			}
			 
			List<String> imgs = StringUtil.toStringList(imageUrl.toString());
		    //2.标题+时间+来源   4.左侧一张图片   7.一张大图+标题格式
			if(infoListType == 4 || infoListType == 7){
				if(imgs.size() < 1){
					infoListType = 2;
				}
			}
			//2.标题+时间+来源    6.三张图文混排   8.纯图左一+右二  9.纯图左二+右一
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
			if(NumberUtil.getInt(type)==0){
			    if(StringUtil.isNotEmpty(info.getImguuid())){
			        infoMap.put("imageurl", imageUrl.toString() + "?" + info.getImguuid());
			    } else {
			        infoMap.put("imageurl", imageUrl.toString());
			    }
				
			}else{
				imgs = StringUtil.toStringList(imageUrl.toString());
				imageUrl = new StringBuilder();
				if(imgs.size() > 0){
					String filename = "";
					for(String img : imgs){
						if(StringUtil.isNotEmpty(imageUrl.toString())){
							imageUrl.append(",");
						}
						filename = img.substring(img.lastIndexOf("/")+1);
						imageUrl.append("./info"+info.getIid()+"/"+ filename);
					}
				}
				if(StringUtil.isNotEmpty(info.getImguuid())){
                    infoMap.put("imageurl", imageUrl.toString() + "?" + info.getImguuid());
                } else {
                    infoMap.put("imageurl", imageUrl.toString());
                }
			}
			if(info.getCreateTime() != null){
				infoMap.put("createtime", StringUtil.getString(info.getCreateTime().getTime()));
			}
			infoMap.put("orderid", StringUtil.getString(info.getOrderid()));
			infoMap.put("topid", StringUtil.getString(info.getTopId()));
			infoMap.put("source", StringUtil.getString(info.getSource()));
			infoMap.put("subtitle", StringUtil.getString(info.getSubTitle()));
			if(info.getSynTime() == null){
				infoMap.put("time", StringUtil.getString(new Date().getTime()));
			}else{
				infoMap.put("time", StringUtil.getString(info.getSynTime().getTime()));
			}
			if(info.getPushTime() == null){
				sendtime = new Date().getTime();
			}else{
				sendtime = info.getPushTime().getTime();
			}
			infoMap.put("sendtime", StringUtil.getString(sendtime));
			infoMap.put("titleid", StringUtil.getString(info.getIid()));
			infoMap.put("audiotime", StringUtil.getString(info.getAudioTime()));
			infoMap.put("titlesubtext", StringUtil.getString(info.getAbs()).trim());
			infoMap.put("titletext", StringUtil.getString(info.getTitle()));
			//音频
			String audioUrl="";
			if(StringUtil.isNotEmpty(info.getAudio())){
				audioUrl = StringUtil.getString(jmpUrl + info.getAudio());
			}
			infoMap.put("audiourl", audioUrl);
			//视频
			if(NumberUtil.getInt(info.getInfoContentType()) == 5){
				infoMap.put("url", StringUtil.getString(info.getUrl()));
			}else if(NumberUtil.getInt(info.getInfoContentType()) == 6){
				String vedioUrl = info.getVedio();
				if(StringUtil.isNotEmpty(vedioUrl) && !vedioUrl.startsWith("http")){
					vedioUrl = jmpUrl + info.getVedio();
				}
				infoMap.put("url", StringUtil.getString(vedioUrl));
			}else{
				infoMap.put("url", "");
			}
			infoMap.put("poitype", StringUtil.getString(info.getPointType()));
			infoMap.put("address", StringUtil.getString(info.getAddress()));
			infoMap.put("poilocation", StringUtil.getString(info.getPointLocation()));
			infoMap.put("ztid", StringUtil.getString(info.getZtId()));
			if(info.getInfoContentType() == null 
					|| NumberUtil.getInt(info.getInfoContentType()) <= 0){
				infotype = StringUtil.getString(col.getInfoContentType());
			}else{
				infotype = StringUtil.getString(info.getInfoContentType());
			}
			infoMap.put("infotype", infotype);
			if(infoListType <= 0){
				infotype = StringUtil.getString(col.getInfoListType());
			}else{
				infotype = StringUtil.getString(infoListType);
			}
			infoMap.put("listtype", infotype);
			if(info.getZtId() > 0 && info.getInfoContentType() == 8){//专题指向栏目
				Col ztCol = colService.findByIid(info.getZtId());
				if(ztCol!=null){
					infoMap.put("zname", StringUtil.getString(ztCol.getName()));
				}
			}else if(info.getZtId() > 0 && info.getInfoContentType() == 9){//专题指向频道
				Channel channel = channelService.findByIid(info.getZtId());
				if(channel!=null){
					infoMap.put("zname", StringUtil.getString(channel.getName()));
				}
			}else{
				infoMap.put("zname", "");
			}
			infoMap.put("iscomment", StringUtil.getString(col.getIsComment()));
			infoMap.put("commentcount", NumberUtil.getInt(info.getCommentcount())+"");
			infoMap.put("tagname", StringUtil.getString(info.getTagname()));
			infoMap.put("tagcolor", StringUtil.getString(info.getTagcolor()));
			infoMap.put("summary", StringUtil.getString(info.getSummary()));
			//自定义字段
			List<String> customFields = infoService.getCustomField(info.getSiteId());
			if(CollectionUtils.isNotEmpty(customFields)){
				Map<String, Object> infoExpand = info.getInfoExpand();
				for(String field : customFields){
					infoMap.put(field, StringUtil.getString(infoExpand.get(field)));
				}
			}
		
		return infoMap;
	} 
	
	/**
	 * 信息正文接口 1.8
	 * @param titleid titleid
	 * @param siteid siteid
	 * @param clienttype clienttype
	 * @return String
	 */ 
	public String findOffineContent(Integer titleid, Integer siteid, Integer clienttype){  
		String jsonString = infoService.findContentJson(titleid, siteid, clienttype, 1);
		return jsonString;
	}
	
}