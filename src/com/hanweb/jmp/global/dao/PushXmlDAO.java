package com.hanweb.jmp.global.dao;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hanweb.common.BaseInfo; 
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.xml.XmlDocument;

import com.hanweb.jmp.util.XmlUtil;


public class PushXmlDAO {

	/**
	 * log
	 */
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
     * 保存信息文件   
     * @param hm    .
     * @param type   .
     * @return .
     */
    public boolean writeInfoXML(Map<String, String> hm, int type){
    	boolean b = false;
        try{
            if(hm==null){ 
                return false;
            }
            XmlUtil xmlutil = new XmlUtil("");
            /** 建立document对象 */
            Document document = DocumentHelper.createDocument();
            /** 建立XML文档的根节点 */
            Element root = document.addElement("root");
            xmlutil.setChildNode(hm, root, "infoid", false); //设置信息ID
            xmlutil.setChildNode(hm, root, "cateid", false); //设置栏目ID
            xmlutil.setChildNode(hm, root, "title", false); //设置标题
            xmlutil.setChildNode(hm, root, "source", false); //设置来源
            xmlutil.setChildNode(hm, root, "author", false); //设置作者
            xmlutil.setChildNode(hm, root, "gettime", true); //设置发布时间
            xmlutil.setChildNode(hm, root, "content", true); //设置内容
            xmlutil.setChildNode(hm, root, "url", true); //设置原文地址
            xmlutil.setChildNode(hm, root, "subtitle", true); //设置副标题 
            int fileSize = NumberUtil.getInt(hm.get("filesize")+""); //设置图片附件
            Element files = root.addElement("files");      
            files.addAttribute("size",  fileSize+"");
            for(int i=0; i<fileSize; i++){
                String pathValue = (String) hm.get("file"+(i+1));       //文件路径
                if(type==1){
                    xmlutil.setAttrNode(files, "file"+(i+1), "", "path", pathValue, true);
                }else if(type==2){
                    String fileBase = (String) hm.get("filebase"+(i+1));        //文件Base64Encoder
                    xmlutil.setAttrNode(files, "file"+(i+1), fileBase, "path", pathValue, true);
                }
            }
            String filePath=StringUtil.getString(hm.get("path"));
            if(!"".equals(filePath)){
            	filePath=BaseInfo.getRealPath()
            		+filePath.substring(0, filePath.lastIndexOf("/"))+"/";
            	File file = new File(filePath);
        	    if (!file.exists()){ 
        	    	b = file.mkdirs();
                } 
            } 
            b = xmlutil.createXmlFile(document, BaseInfo.getRealPath()+"/"+hm.get("path")); 
        }catch(Exception e){
        	logger.error("writeInfoXML error", e); 	   
        	b = false;
        }
        return b;
    }
    
    /**
     * 取信息XML详情
     * @param filepath   信息xml文件路径
     * @return .
     */
    public Map<String, String> findInfoXml(String filepath){   
        Map<String, String> hm = new HashMap<String, String>(); 
        try{
            if(filepath==null || filepath.equals("")){
                return null;
            }
            File file = new File(filepath);
            if(!file.exists()){
                return null;
            }
            XmlDocument xml = new XmlDocument(); 
            xml.read(new File(filepath));  
            hm.put("iid", xml.getXmlNode("root/iid").getContent()); 
            hm.put("title", xml.getXmlNode("root/title").getContent()); 
            hm.put("subtitle", xml.getXmlNode("root/subtitle").getContent()); 
            hm.put("createtime", xml.getXmlNode("root/createtime").getContent()); 
            hm.put("abs", xml.getXmlNode("root/abs").getContent()); 
            hm.put("typeid", xml.getXmlNode("root/typeid").getContent()); 
            hm.put("content", xml.getXmlNode("root/content").getContent()); 
            hm.put("json", xml.getXmlNode("root/json").getContent());
             
            /* 取得所有图片附件 */
            int fileSize = NumberUtil.getInt(xml.getXmlNode("root/files").getAttr("size"));
            hm.put("filesize", fileSize+"");
            if(fileSize > 0){
            	String filePath ="";
            	for(int i=0; i<fileSize; i++){
                    filePath = xml.getXmlNode("/root/files/file"+(i+1)).getAttr("path");
                    //文件路径
                    hm.put("file"+(i+1), filePath);
                }
            }
            return hm;
        }catch(Exception e){
        	logger.error("findPushXml error", e);     
            return null;
        }
    }
    
    /**
     * 组织推送信息的xml文档
     * @param map 信息map
     * @return true 成功 false 失败
     */
    public boolean writePushInfoXml(HashMap<String, String> map){
    	boolean b = false;
    	try{
            if(map == null){
            	logger.error("writeInfoXml Error: HashMap is null"); 
                return false;
            }
            XmlUtil xmlutil = new XmlUtil("");
            /** 建立document对象 */
            Document document = DocumentHelper.createDocument();
            /** 建立XML文档的根节点 */
            Element root = document.addElement("root");
            xmlutil.setChildNode(map, root, "iid", false); //设置消息ID
            xmlutil.setChildNode(map, root, "title", false);
            xmlutil.setChildNode(map, root, "subtitle", false);
            xmlutil.setChildNode(map, root, "createtime", false);
            xmlutil.setChildNode(map, root, "abs", false);
            xmlutil.setChildNode(map, root, "typeid", false);
            xmlutil.setChildNode(map, root, "content", true);
            xmlutil.setChildNode(map, root, "json", true);
            int fileSize = NumberUtil.getInt(map.get("filesize")+""); //设置图片附件
            Element files = root.addElement("files");      
            files.addAttribute("size",  fileSize+"");
            for(int i=0; i<fileSize; i++){
                String pathValue = (String) map.get("file"+(i+1));       //文件路径
                xmlutil.setAttrNode(files, "file"+(i+1), "", "path", pathValue, true);
            }
            String filePath=StringUtil.getString(map.get("path"));
            if(!"".equals(filePath)){
            	filePath=BaseInfo.getRealPath()
            		+filePath.substring(0, filePath.lastIndexOf("/"))+"/";
            	File file = new File(filePath);
        	    if (!file.exists()){
        	    	b = file.mkdirs();
                } 
            } 
            b = xmlutil.createXmlFile(document, filePath+"1.xml");
        }catch(Exception e){
        	logger.error("writeInfoXML  Error:", e); 
            return false;
        }
    	return b;
    }
    
}