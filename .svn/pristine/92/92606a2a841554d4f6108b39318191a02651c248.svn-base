/*
 * @(#)DataController.java	
 *
 * Copyright 1997-2008  Hanweb CO.,LTD. All rights reserved.
 * HANWEB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.hanweb.jmp.interfaces;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.xml.XmlDocument;
import com.hanweb.common.util.xml.XmlNode; 

import com.hanweb.jmp.util.StrUtil;
 

@Controller
@RequestMapping("interfaces")
public class DataController {

	/**
	 * 数据库状态接口 
	 * @return String
	 */
	@RequestMapping("database")
	@ResponseBody 
	public String findContent(){  
		String strBody = FileUtil.readFileToString(new File(BaseInfo.getRealPath()+"/resources/database/html/dbbase.html"));
	    StrUtil strUtil = new StrUtil();
		//图片滚动效果和幻灯片效果  
	    String everyCont = strUtil.getContentByTag("<!--data start-->", "<!--data end-->", strBody);
		String xmlPath = BaseInfo.getRealPath()+"/WEB-INF/config/database.xml";
		XmlDocument xmlDoc = new XmlDocument("UTF-8"); // utf-8
		xmlDoc.read(new File(xmlPath));
		List<XmlNode> ipList = xmlDoc.getXmlNodes("root/database/ip");
		List<XmlNode> portList = xmlDoc.getXmlNodes("root/database/port");
		List<XmlNode> nameList = xmlDoc.getXmlNodes("root/database/dataname");
		List<XmlNode> usernameList = xmlDoc.getXmlNodes("root/database/username");
		List<XmlNode> pwdList = xmlDoc.getXmlNodes("root/database/password");
		XmlNode xmlNode = null;
		String ip = "";
		String port = "";
		String dataname = "";
		String userName = "";
		String pwd = "";
		String replaceCont = "";
		for(int i=0; i<ipList.size(); i++){
			//状态
			String serverstate = "异常";
			String datastate = "异常";
			String writestate = "关";
			String readstate = "关";
			String cont = everyCont;
			xmlNode = ipList.get(i);
			ip = xmlNode.getContent(); 
			xmlNode = portList.get(i);
			port = xmlNode.getContent(); 
			xmlNode = nameList.get(i);
			dataname = xmlNode.getContent(); 
			xmlNode = usernameList.get(i);
			userName = xmlNode.getContent(); 
			xmlNode = pwdList.get(i);
			pwd = xmlNode.getContent();  
			Map<String, Boolean> map = isDataConnect(ip, port, dataname, userName, pwd); 
			boolean isDataConnect = map.get("isDataConnect");
			boolean  writeRight = map.get("writeRight");
			boolean  readRight = map.get("readRight");
		
			if(isConnect(ip)){
				serverstate = "正常";
			}
			if(isDataConnect){
				datastate = "正常";
			}
			if(writeRight){
				 writestate = "开";
			}
			if(readRight){
				 readstate = "开";
			}
			cont=cont.replace("{ip}", ip);
			cont=cont.replace("{dataname}", dataname);
			cont=cont.replace("{serverstate}", serverstate);
			cont=cont.replace("{datastate}", datastate);
			cont=cont.replace("{readstate}", readstate);
			cont=cont.replace("{writestate}", writestate);
			replaceCont +=cont;
		}
		strBody = strBody.replace(everyCont, replaceCont);
		return strBody;
	}
	
    /**
     * 判断网络状态  
     * @param ip String ip地址
     * @return boolean
     */
    public static boolean isConnect(String ip) { 
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String line = null; 
        boolean bl = false; 
        try { 
            process = runtime.exec("ping " + ip); 
            is = process.getInputStream(); 
            isr = new InputStreamReader(is); 
            br = new BufferedReader(isr); 
            while ((line = br.readLine()) != null) { 
                if(line.contains("ttl")||line.contains("TTL")){
                	bl = true; 
                	break;
                }
            } 
            is.close(); 
            isr.close(); 
            br.close(); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return bl;
    } 

    /**
     * 判断数据库连接,以及读写权限
     * @param ip String ip地址
     * @param port String 端口号
     * @param dataname String 数据库名
     * @param userName String 用户名
     * @param pwd String 密码
     * @return Map<String, Boolean>
     */
    public static Map<String, Boolean> isDataConnect(String ip, String port, String dataname, String userName, String pwd) { 
    	 boolean writeRight = false;
    	 Map<String, Boolean>  map = new HashMap<String, Boolean>();
    	 Connection conn = null;
    	 try  
    	   { 
    	   Class.forName("com.mysql.jdbc.Driver");  
    	   conn = DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/"+dataname
    		    + "?characterEncoding=utf8&autoreconnect=true", userName, pwd);
    	   boolean isReadOnly = conn.isReadOnly();
    	   writeRight = !isReadOnly;
    	   } catch(Exception ex){  		
    		   	ex.printStackTrace();
    		   	map.put("isDataConnect", false);
    		   	map.put("writeRight", false);
    			map.put("readRight", false);
    		    return map;
    	   }
		   map.put("writeRight", writeRight);
		   map.put("readRight", true);
		   map.put("isDataConnect", true);
		   return map;
    } 
  
}