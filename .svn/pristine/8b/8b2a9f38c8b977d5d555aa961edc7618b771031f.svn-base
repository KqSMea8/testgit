package com.hanweb.jmp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.StringUtil;
 

public class XmlUtil {
	
	/**
	 * logger
	 */
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * doc
	 */
	private Document doc = null;
	
	/**
	 * 将字符串读取问doc对象
	 * @param strContent
	 * @param type
	 */
	public XmlUtil(String strContent) throws Exception {
		try {
			if(strContent!=null && !"".equals(strContent)){
				doc = DocumentHelper.parseText(strContent);
			} 
		} catch (Exception e) {
			throw new Exception("XML format error");
		}
	} 
	
	/**
	 * 根据节点名称取子节点列表
	 * @param member	子节点名称
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List  getNodeFromName(String member){
		try {
			Element bm = (Element) doc.selectSingleNode(getNodeName(member));
			if (bm == null){
				return null;
			}
			List list = bm.elements();
			return list;
		} catch (Exception e) {
			logger.error("getNodeFromName Error:", e);
			return null;
		}
	}
	
	/**
	 * getNodeName:(这里用一句话描述这个方法的作用).
	 *
	 * @param name name
	 * @return    设定参数 .
	*/
	private String getNodeName(String name) {
		try {
			String node = "";
			if(name!=null && !name.startsWith("/")){
				Element root = doc.getRootElement();
				String rootname = root.getName();
				/** 取得xml中对应的节点 */
				String[] array = parsePropertyName(name);
				node = "/" + rootname;
	
				for (int i = 0; i < array.length; i++) {
					node += "/" + array[i];
				}
			}else{
				node = name;
			}

			return node;
		} catch (Exception e) {
			logger.error("getNodeName Error:", e);
			return "";
		}
	}
	
	/**
	 * Returns an array representation of the given Jive property. Jive
	 * properties are always in the format "prop.name.is.this" which would be
	 * represented as an array of four Strings.
	 * 
	 * @param name
	 *            the name of the Jive property.
	 * @return an array representation of the given Jive property.
	 */
	private String[] parsePropertyName(String name) {
		// Figure out the number of parts of the name (this becomes the size
		// of the resulting array).
		int size = 1;
		for (int i = 0; i < name.length(); i++) {
			if (name.charAt(i) == '.') {
				size++;
			}
		}
		String[] propName = new String[size];
		// Use a StringTokenizer to tokenize the property name.
		StringTokenizer tokenizer = new StringTokenizer(name, ".");
		int i = 0;
		while (tokenizer.hasMoreTokens()) {
			propName[i] = tokenizer.nextToken();
			i++;
		}
		return propName;
	}
	
	
	/**
	 * 添加子节点
	 * @param element		父节点
	 * @param nodeName		节点名称
	 * @param nodeValue		节点值
	 * @param bNeedCDATA	是否CDATA语法
	 */
	public void setChildNode(Element element, String nodeName
			, String nodeValue, boolean bNeedCDATA){
		try{
			if(element==null || nodeName==null || nodeValue==null){
				return;
			}
			Element child = element.addElement(nodeName);
			if(!bNeedCDATA){
				child.setText(StringUtil.getString(nodeValue));
			}else{
				child.addCDATA(StringUtil.getString(nodeValue));
			}
		}catch(Exception e){
			logger.error("setChildNode error", e); 
		}
	}
	
	/**
	 * 在指定节点下添加子节点
	 * @param hm  hm
	 * @param element		父节点
	 * @param key	 key		
	 * @param bNeedCDATA	是否CDATA语法
	 */
	public void setChildNode(Map<String, String> hm, Element element,
			String key, boolean bNeedCDATA){
		try{
			if(element==null || key==null){
				return;
			}
			Element child = element.addElement(key);
			if(!bNeedCDATA){
				child.setText(StringUtil.getString((String) hm.get(key)));
			}else{
				child.addCDATA(StringUtil.getString((String) hm.get(key)));
			}
		}catch(Exception e){
			logger.error("setChildNode error", e);  
		}
	}
	
	/**
	 * 在指定节点下添加带属性的子节点
	 * @param element		父节点
	 * @param nodeName		节点名称
	 * @param nodeVale		节点值
	 * @param attName		属性名称	
	 * @param attVale		属性值
	 * @param bNeedCDATA	是否CDATA语法
	 */
	public void setAttrNode(Element element, String nodeName, String nodeVale, 
							String attName, String attVale, boolean bNeedCDATA){
		try{
			if(element==null || nodeName==null  
				|| nodeVale==null || attName==null 
				|| attVale==null){
				return;
			}
			Element child = element.addElement(nodeName);
			if(!bNeedCDATA){
				child.setText(nodeVale);
			}else{
				child.addCDATA(nodeVale);
			}
			child.addAttribute(attName, attVale);
		}catch(Exception e){
			logger.error("setAttrNode error", e);   
		}
	}
	
	/**
	 * 在指定节点下添加带内容的子节点
	 * @param fatherElement		节点的父节点
	 * @param sonNodeName		节点名称
	 * @param sonAttrName		节点属性名
	 * @param sonAttrValue		节点属性值
	 * @param sonsNodeName		节点的子节点名称
	 * @param sonsNodeValue		节点的子节点的值
	 * @param bNeedCDATA	是否CDATA语法
	 */
	public void setNodeWithAtrAndNode(Element fatherElement, String sonNodeName, String sonAttrName,
			String sonAttrValue, String sonsNodeName, String sonsNodeValue, boolean bNeedCDATA){
		try{
			if (fatherElement == null || sonNodeName == null  
				|| sonAttrName == null || sonAttrValue == null
				|| sonsNodeName == null|| sonsNodeValue == null){
				return;
			}
			Element son = fatherElement.addElement(sonNodeName);
			son.addAttribute(sonAttrName, sonAttrValue);
			Element sonChild = son.addElement(sonsNodeName);
			if(!bNeedCDATA){
				sonChild.setText(sonsNodeValue);
			}else{
				sonChild.addCDATA(sonsNodeValue);
			}
		}catch(Exception e){
			logger.error("setNodeWithValue error", e);    
		}
	}
	
	/**
	 * 修改节点值
	 * @param element		要修改的节点
	 * @param newValue		修改后的值
	 * @param bNeedCDATA	是否CDATA语法
	 */
	public void setNodeText(Element element, String newValue, boolean bNeedCDATA) {
		try{
			if(element == null || newValue==null){
				return;
			}
			if(!bNeedCDATA){
				element.setText(newValue);
			}else{
				element.addCDATA(newValue);
			}
		}catch(Exception e){
			logger.error("setNodeText error", e);   
		}

	}
	
	/**
	 * 生成物理文件
	 * @param document 
	 * @param filepath  boolean 
	 * @return boolean 
	 */
	public boolean createXmlFile(Document document, String filepath){
		XMLWriter output = null;
		try{
			//生成文件
			/** 格式化输出,类型IE浏览一样 */
			OutputFormat format = OutputFormat.createPrettyPrint();
			/** 指定XML字符集编码 */
			format.setEncoding("UTF-8");
			//output = new XMLWriter(new FileWriter(new File(filepath)),format);
			FileUtil.createDir(new File(filepath).getParent().toString());
			output = new XMLWriter(new FileOutputStream(new File(filepath)), format);
			output.write(document);
				
			return true;
		}catch(Exception e){
			logger.error("createXmlFile error", e);    
			return false;
		}finally{
			try{
				if(output!=null){
					output.close();
				}
			}catch(Exception e){
				logger.error("createXmlFile error", e);     
			}
		}
	}
	
    /**
     * createDirectory:(这里用一句话描述这个方法的作用).
     *
     * @param strPath    设定参数 .
    */
    public static void createDirectory(String strPath){
	    File path = new File(strPath);
	    if (!path.exists()){
	      boolean isSuccess = path.mkdirs();
	      if(! isSuccess){
	    	  System.out.println("创建文件目录失败");
	      }
	    }
    }
}
