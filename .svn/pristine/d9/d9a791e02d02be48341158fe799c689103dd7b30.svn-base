package com.hanweb.jmp.global.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.xml.XmlDocument;

import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.sys.entity.sites.Site;

public class JsearchService {
	
	/**
	 * logger
	 */
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * sendToRobot
	 * @param site site
	 * @param col col
	 * @param info info
	 * @param opr opr
	 * @return    设定参数 .
	*/
	@SuppressWarnings("unchecked")
	public boolean sendToRobot(Site site, Col col, Info info, String opr) {
		int colId = NumberUtil.getInt(col.getIid());
		String colName = StringUtil.getString(col.getName());
		int siteId = NumberUtil.getInt(site.getIid());
		String siteName = StringUtil.getString(site.getName());
		String url="";
		//组织xml
		XmlDocument xmlDoc = new XmlDocument("UTF-8");
		String rootPath= "dahan/index";
		xmlDoc.createPath(rootPath+"/fields");
		xmlDoc.addNodeCDATA(rootPath, "sign", StringUtil.getString(site.getSearchUrl()));
		xmlDoc.addNodeCDATA(rootPath, "group", "1");
		xmlDoc.addNodeCDATA(rootPath, "web", StringUtil.getString(siteId));
		xmlDoc.addNodeCDATA(rootPath, "webname", StringUtil.getString(siteName));
		xmlDoc.addNodeCDATA(rootPath, "column", StringUtil.getString(colId));
		xmlDoc.addNodeCDATA(rootPath, "module", StringUtil.getString(colName));
		xmlDoc.addNodeCDATA(rootPath, "subject", StringUtil.getString(info.getTitle()));
		xmlDoc.addNodeCDATA(rootPath, "author", StringUtil.getString(info.getAuthor()));
		xmlDoc.addNodeCDATA(rootPath, "date", DateUtil.dateToString(info.getSynTime(), DateUtil.YYYY_MM_DD_HH_MM_SS)); 
		xmlDoc.addNodeCDATA(rootPath, "opr", opr);
		url=info.getUrl();
		if(StringUtil.isEmpty(info.getUrl())){
			url=info.getSiteId() + "-" + info.getColId() + "-" + info.getIid();
		}
		xmlDoc.addNodeCDATA(rootPath, "url", url);
		xmlDoc.addNodeCDATA(rootPath, "columnurl", "");
		xmlDoc.addNodeCDATA(rootPath, "type", "2");
		xmlDoc.addNodeCDATA(rootPath, "typename", StringUtil.getString(siteName));
		
		Map<String, String> attr = null;
		attr = new HashMap<String, String>();
		attr.put("name", "ID");
		xmlDoc.addNode(rootPath+"/fields", "iid", StringUtil.getString(info.getIid()), attr, true);
		attr = new HashMap<String, String>();
		attr.put("name", "栏目ID");
		xmlDoc.addNode(rootPath+"/fields", "colId",  StringUtil.getString(info.getColId()), attr, true);
		attr = new HashMap<String, String>();
		attr.put("name", "网站ID");
		xmlDoc.addNode(rootPath+"/fields", "siteId", StringUtil.getString(info.getSiteId()), attr, true);
		attr = new HashMap<String, String>();
		attr.put("name", "标题");
		xmlDoc.addNode(rootPath+"/fields", "title", StringUtil.getString(info.getTitle()), attr, true);
		attr = new HashMap<String, String>();
		attr.put("name", "副标题");
		xmlDoc.addNode(rootPath+"/fields", "subTitle", StringUtil.getString(info.getSubTitle()), attr, true);
		attr = new HashMap<String, String>();
		attr.put("name", "同步时间");
		xmlDoc.addNode(rootPath+"/fields", "synTime", DateUtil.dateToString(info.getSynTime(), DateUtil.YYYY_MM_DD_HH_MM_SS), attr, true);
		attr = new HashMap<String, String>();
		attr.put("name", "摘要");
		xmlDoc.addNode(rootPath+"/fields", "abs", StringUtil.getString(info.getAbs()), attr, true);
		attr = new HashMap<String, String>();
		attr.put("name", "首图");
		xmlDoc.addNode(rootPath+"/fields", "firstPicPath", StringUtil.getString(info.getFirstPicPath()), attr, true);
		attr = new HashMap<String, String>();
		attr.put("name", "原图");
		xmlDoc.addNode(rootPath+"/fields", "orignalPicpath", StringUtil.getString(info.getOrignalPicpath()), attr, true);
		attr = new HashMap<String, String>();
		attr.put("name", "视频");
		xmlDoc.addNode(rootPath+"/fields", "vedio", StringUtil.getString(info.getVedio()), attr, true);
		attr = new HashMap<String, String>();
		attr.put("name", "音频");
		xmlDoc.addNode(rootPath+"/fields", "audio", StringUtil.getString(info.getAudio()), attr, true);
		attr = new HashMap<String, String>();
		attr.put("name", "坐标类型");
		xmlDoc.addNode(rootPath+"/fields", "pointType", StringUtil.getString(info.getPointType()), attr, true);
		attr = new HashMap<String, String>();
		attr.put("name", "坐标");
		xmlDoc.addNode(rootPath+"/fields", "pointLocation", StringUtil.getString(info.getPointLocation()), attr, true);
		attr = new HashMap<String, String>();
		attr.put("name", "地址");
		xmlDoc.addNode(rootPath+"/fields", "address", StringUtil.getString(info.getAddress()), attr, true);
		attr = new HashMap<String, String>();
		attr.put("name", "来源");
		xmlDoc.addNode(rootPath+"/fields", "source", StringUtil.getString(info.getSource()), attr, true);
		attr.put("name", "作者");
		xmlDoc.addNode(rootPath+"/fields", "author", StringUtil.getString(info.getAuthor()), attr, true);
		attr.put("name", "信息展现方式");
		xmlDoc.addNode(rootPath+"/fields", "infoListType", StringUtil.getString(info.getInfoListType()), attr, true);
		attr.put("name", "信息内容展现方式");
		xmlDoc.addNode(rootPath+"/fields", "infoContentType", StringUtil.getString(info.getInfoContentType()), attr, true);
		attr.put("name", "信息内容");
		attr.put("text", "1");
		xmlDoc.addNode(rootPath+"/fields", "content", StringUtil.getString(info.getContent()), attr, true);
		attr.remove("text");
		attr.put("name", "专题ID");
		xmlDoc.addNode(rootPath+"/fields", "ztId", StringUtil.getString(info.getZtId()), attr, true);

		//发送
		String filePath = BaseInfo.getRealPath() + "/jsearch/" + siteId +"/" + colId 
			            + "/" + DateUtil.getCurrDate("yyyyMMddHHmmssSSS")+"_"+info.getIid() + ".xml";
		xmlDoc.saveAs(new File(filePath));
		
//		String f = BaseInfo.getRealPath() + "/jsearch1/" + siteId +"/" + colId 
//			+ "/" + DateUtil.getCurrDate("yyyyMMddHHmmssSSS")+"_"+info.getIid() + ".xml";
//		xmlDoc.saveAs(new File(f));
		
		url = Configs.getConfigs().getJsearchUrl()+"/interface/receive.do";
		boolean bl = false;
		if(url.toLowerCase().startsWith("http")){
			HttpClient httpClient = new DefaultHttpClient();
			try{
				HttpPost post = new HttpPost(url);
				FileBody fileBody = new FileBody(new File(filePath));
				MultipartEntity entity = new MultipartEntity();  
				entity.addPart("xml", fileBody);  
				post.setEntity(entity);
				HttpResponse response = httpClient.execute(post);  
				if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
					HttpEntity entitys = response.getEntity();
					if (entity != null) {
						String json = EntityUtils.toString(entitys);
						Map<String, String> result = JsonUtil.StringToObject(json, Map.class);
						if(result != null && !StringUtil.equals("200", result.get("state"))){
							logger.error(StringUtil.getString(result.get("message")));
						}else{
							bl = true;
						}
					}
				}
			}catch(Exception e){
				logger.error("发送jsearch失败！");
				return false;
			}finally{
				httpClient.getConnectionManager().shutdown();
			}
		}else{
			if(!url.endsWith("/")){
				url += "/";
			}
			return FileUtil.copyFileToDirectory(new File(filePath), new File(url));
		}
		return FileUtil.deleteFile(new File(filePath)) && bl;
	}
	
}