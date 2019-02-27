package com.hanweb.jmp.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element; 

import com.hanweb.common.BaseInfo; 
import com.hanweb.common.util.FileUtil; 
import com.hanweb.common.util.StringUtil; 
import com.hanweb.jmp.global.entity.normalentity.SynchField;
import com.hanweb.jmp.global.entity.normalentity.SynchFile;
import com.hanweb.jmp.global.entity.normalentity.SynchInfo;

/**
 * jiep中xml与对象的转换
 * @author 
 *
 */
public class XmlFormat {

	/**
	 * logger
	 */
	private final Log logger = LogFactory.getLog(getClass());
	  
	
	 
	/**
	 * xmlToInfo:将xml转换成信息对象
	 * @param strXml 信息xml
	 * @return    设定参数 .
	*/
	@SuppressWarnings("unchecked")
	public List<SynchInfo> xmlToInfo(String strXml){
		List<SynchInfo> alinfo = new ArrayList<SynchInfo>();
		SynchInfo info = null;
		try{
			XmlUtil xml = new XmlUtil(strXml);
			List list = xml.getNodeFromName("/root");
			if(list==null){
				return null;
			}
			
			String fields = "";
			for (Iterator it = list.iterator(); it.hasNext(); ){
				Element ele = (Element) it.next();
				if(ele.getName().equals("fields")){
					fields=ele.getText();
					continue;
				}
				if(!ele.getName().equals("info")){
					continue;
				}
				info = new SynchInfo();
				info.setFileds(fields);
				for(Iterator it1 = ele.elementIterator(); it1.hasNext(); ) {
					Element e = (Element) it1.next();
					String nodeName = e.getName();
					if(nodeName.equals("id")){
						info.setId(StringUtil.getString(e.getText()));
					}else if(nodeName.equals("state")){
						info.setState(StringUtil.getString(e.getText()));
					}else if(nodeName.equals("ctime")){
						info.setCtime(StringUtil.getString(e.getText()));
					}else if(nodeName.equals("item")){
						info.setFiled(xmlToField(e));
					}else if(nodeName.equals("files")){
						info.setFiles(xmlToFiles(e));
					}
				}
				alinfo.add(info);
			}
			return alinfo;
		}catch(Exception e){
			logger.error("xmlToInfo error", e);      
			return null;
		}
	}
	  
	/**
	 * findBindField:(这里用一句话描述这个方法的作用).
	 *
	 * @return    设定参数 .
	*/
	@SuppressWarnings({ "unchecked", "unused" })
	public Map<String, String> findBindField(){
		Map<String, String> fields = new HashMap<String, String>();
		try{
			String strXml=FileUtil.readFileToString(new File(
					BaseInfo.getRealPath()+"/WEB-INF/config/infoconvert.xml")); 
			XmlUtil xml = new XmlUtil(strXml);
			List list = xml.getNodeFromName("/root");
			if(list==null){
				return null;
			}
			for (Iterator it = list.iterator(); it.hasNext(); ){
				Element ele = (Element) it.next();
				if(!ele.getName().equals("field")){
					continue;
				} 
			    fields.put(ele.attributeValue("jgetfield"), ele.attributeValue("jmpfield"));  
				for(Iterator itfield = ele.elementIterator(); it.hasNext(); ){
					Element e = (Element) it.next();
 				    fields.put(e.attributeValue("jgetfield"), e.attributeValue("jmpfield"));  
				} 
			}
			return fields;
		}catch(Exception e){
			logger.error("findBindField error", e);      
			return null;
		}
	}          
	/**
	 * 转换字段
	 * @param ele 字段集合
	 * @return 字段实体集合.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<SynchField> xmlToField(Element ele){
		ArrayList<SynchField> alfield = new ArrayList<SynchField>();
		SynchField field = null;
		for(Iterator it = ele.elementIterator(); it.hasNext(); ){
			field = new SynchField();
			Element e = (Element) it.next();
			field.setName(StringUtil.getString(e.attributeValue("name")));
			field.setContent(StringUtil.getString(e.getText()));
			alfield.add(field);
		}
		return alfield;
	}
	
	/**
	 * 转换文件
	 * @param ele 装换文件集合
	 * @return 文件集合.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<SynchFile> xmlToFiles(Element ele){
		ArrayList<SynchFile> alfiles = new ArrayList<SynchFile>();
		SynchFile files = null;
		for(Iterator it = ele.elementIterator(); it.hasNext(); ){
			files = new SynchFile();
			Element e = (Element) it.next();
			files.setName(StringUtil.getString(e.attributeValue("name")));
			files.setPath(StringUtil.getString(e.attributeValue("path"))); 
			alfiles.add(files);
		}
		return alfiles;
	} 
	
}