package com.hanweb.jmp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List; 

import javax.activation.DataHandler;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.encoding.ser.JAFDataHandlerDeserializerFactory;
import org.apache.axis.encoding.ser.JAFDataHandlerSerializerFactory;
import org.apache.axis.encoding.ser.MapDeserializerFactory;
import org.apache.axis.encoding.ser.MapSerializerFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;     
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;  
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.util.ImageUtil;
import com.hanweb.jmp.util.ImgChange;
import com.hanweb.jmp.util.PatternUtil; 

public class SynInfoUtil {

	/**
	 * logger
	 */
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
     * getinfoNum:获取信息数量
     * @param strLoginId        用户名
     * @param strPwd            密码
     * @param strKey            密钥
     * @param strStartCTime     开始时间
     * @param strEndCTime       结束时间 
     * @param itaskid           任务号 
     * @param urladdress        webservice接口地址
     * @param col               栏目实体
     * @return    设定参数 .
    */
    @SuppressWarnings("unchecked")
	public int getinfoNum(String strLoginId, String strPwd, String strKey,
            String strStartCTime, String strEndCTime, int itaskid, String urladdress, Col col, int flag){
        String ret = "";
        int num = 0; 
            try {
				Service service = new Service();
				Call call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(urladdress));
				//指定方法名称
				call.setOperationName("getInfoNum");
				//指定参数
				call.addParameter("strLoginId", XMLType.XSD_STRING, ParameterMode.IN);
				call.addParameter("strPwd", XMLType.XSD_STRING, ParameterMode.IN);
				call.addParameter("strKey", XMLType.XSD_STRING, ParameterMode.IN);
				call.addParameter("strStartCTime", XMLType.XSD_STRING, ParameterMode.IN);
				call.addParameter("strEndCTime", XMLType.XSD_STRING, ParameterMode.IN); 
				if(flag == 0){
					call.addParameter("iTaskId", XMLType.XSD_INT, ParameterMode.IN);
				} else {
					call.addParameter("cataId", XMLType.XSD_INT, ParameterMode.IN);
				}
				call.setReturnType(XMLType.XSD_STRING);
				//返回处理结果
				ret = StringUtil.getString(call.invoke(new Object[] {strLoginId, strPwd,
				                strKey, strStartCTime, strEndCTime, itaskid}));
				if(StringUtil.isNotEmpty(findError(ret, col, "getinfoNum()"))){
					Document doc = DocumentHelper.parseText(ret);
					List numlist = doc.selectNodes("/root/infonum");
					Iterator it = numlist.iterator();  
					while (it.hasNext()) { 
					    Element numElement = (Element) it.next();
					    num = NumberUtil.getInt(numElement.getText()); 
					}
				} 
			} catch (Exception e) {
				logger.error("getinfoNum 获取信息数量error：", e); 
			}
			logger.debug("getinfoNum:"+"-"+itaskid+":"+num);
        return num;
    }
    
    /**
     * 获取附件接口
     * @param strLoginId    用户名
     * @param strPwd        密码
     * @param strKey        密钥
     * @param strFileName   附件路径（全路径）
     * @param state         1分包   2取文件  3删除分包文件
     * @param urladdress    webservice接口地址
     * @return String
     */
    @SuppressWarnings("unchecked")
	public HashMap<String, DataHandler> getfile(String strLoginId, String strPwd, 
            String strKey, String strFileName, int state, String urladdress){
        
        HashMap ret =null;
        try{
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new java.net.URL(urladdress));
            
          //指定方法名称
            call.setOperationName("getfile");
          //指定参数
            call.addParameter("strLoginId", XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter("strPwd", XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter("strKey", XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter("strFileName", XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter("state", XMLType.XSD_INT, ParameterMode.IN);    
            
          //注册复杂对象，返回类型中的所有对象均要注册（序列化/反序列化）
            QName qRet = new QName("http://fileTransfer.com/ ", "map"); 
            call.registerTypeMapping(HashMap.class, qRet,
                        new MapSerializerFactory(HashMap.class, qRet),
                        new MapDeserializerFactory(HashMap.class, qRet)); 

            QName qRet2 = new QName("http://fileTransfer.sample", "DataHandler"); 
            call.registerTypeMapping(DataHandler.class, qRet2,
                        new JAFDataHandlerSerializerFactory(DataHandler.class, qRet2),
                        new JAFDataHandlerDeserializerFactory(DataHandler.class, qRet2)); 
            
            // 指定返回类型
            call.setReturnType(qRet);
            
          //返回处理结果
            ret = (HashMap) call.invoke(
                    new Object[] {strLoginId, strPwd,
                            strKey, strFileName, state});
        }catch(Exception e){
        	logger.error("getfile Error:", e); 
            return null;
        }
        return ret;
    }
    
    /**
     * getinfos:获得信息xml字符串.
     *
     * @param strLoginId        用户名
     * @param strPwd            密码
     * @param strKey            密钥
     * @param strStartCTime     开始时间
     * @param strEndCTime       结束时间
     * @param iStart            开始条数
     * @param iEnd              结束条数
     * @param itaskid           任务号 
     * @param urladdress        webservice接口地址 
     * @param col               栏目实体
     * @return String
    */
    public String getinfos(String strLoginId, String strPwd, String strKey,
            String strStartCTime, String strEndCTime,
            int iStart, int iEnd, int itaskid, String urladdress, Col col, int flag){
        String ret ="";
        try{
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new java.net.URL(urladdress));
            
            //指定方法名称
            call.setOperationName("getInfo");//新版本jget
            //call.setOperationName("getJmpotalInfo");老版本jget
            //指定参数
            call.addParameter("strLoginId", XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter("strPwd", XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter("strKey", XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter("strStartCTime", XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter("strEndCTime", XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter("iStart", XMLType.XSD_INT, ParameterMode.IN);
            call.addParameter("iEnd", XMLType.XSD_INT, ParameterMode.IN);
            if(NumberUtil.getInt(flag)==0){
            	call.addParameter("iTaskId", XMLType.XSD_INT, ParameterMode.IN);
            }else{
            	call.addParameter("cataId", XMLType.XSD_INT, ParameterMode.IN);
            }
            
            call.setReturnType(XMLType.XSD_STRING);
            //返回处理结果
            ret = StringUtil.getString(call.invoke(
                    new Object[] {strLoginId, strPwd,
                            strKey, strStartCTime, strEndCTime, iStart, iEnd, itaskid})+"");
            
            logger.debug("source xml----"+itaskid+":"+ret);
            if(StringUtil.isEmpty(findError(ret, col, "getinfos()"))){
            	ret = "";
            }
        }catch(Exception e){
        	logger.error("getinfo Error:", e);
            return "";
        }
        return ret;
    } 
    
    
    
    /**
     * 验证从jget获取到的信息xml格式
     * @param retXml 信息xml
     * @param col 栏目实体
     * @param method 方法名称
     * @return 验证结果
     */
    public String findError(String retXml, Col col, String method){
    	if(StringUtil.isEmpty(retXml)){
    		logger.warn("栏目："+col.getName()+",方法："+method+"获取的xml为空"); 
    		return null;
    	}
    	if(retXml.indexOf("<errorcode>A01</errorcode>") > -1){ 
    		logger.warn("栏目："+col.getName()+",方法："+method+"jget接口用户名不存在");  
            return null;
        }else if(retXml.indexOf("<errorcode>A02</errorcode>") > -1){
        	logger.warn("栏目："+col.getName()+",方法："+method+"jget接口密码错误");  
            return null;  
        }else if(retXml.indexOf("<errorcode>A03</errorcode>") > -1){ 
            logger.warn("栏目："+col.getName()+",方法："+method+"指定参数错误或不存在");  
            return null; 
        }else if(retXml.indexOf("<errorcode>A04</errorcode>") > -1){ 
            logger.warn("栏目："+col.getName()+",方法："+method+"栏目下未同步到新信息");  
            return null; 
        }else if(retXml.indexOf("<errorcode>A05</errorcode>") > -1){ 
            logger.warn("栏目："+col.getName()+",方法："+method+"连接服务异常");  
            return null;
        }else if(retXml.indexOf("<errorcode>A06</errorcode>") > -1){ 
            logger.warn("栏目："+col.getName()+",方法："+method+"接收的信息格式错误");  
            return null;
        }else if(retXml.indexOf("<errorcode>A07</errorcode>") > -1){ 
            logger.warn("栏目："+col.getName()+",方法："+method+"获取的附件不存在");  
            return null;
        }else if(retXml.indexOf("<errorcode>A00</errorcode>") > -1){ 
            logger.warn("栏目："+col.getName()+",方法："+method+"其它错误");  
            return null;
        }
    	return "success"; 
    }
    
    
    /**
     * 过滤信息内容
     * @param content 信息内容
     * @return 过滤后的内容
     */
    public String filterContent(String content){
    	ImageUtil imgutil = new ImageUtil();
    	content = imgutil.replaceHtmlTag(content,
                "a", "href", "<a href=\"", "\">", "1", "");
    	PatternUtil patterUtil=new PatternUtil(); 

    	if(!PatternUtil.regCheck("<\\s*?img[^>]*?>", content)) { 
    		content = StrUtil.removeHTMLTag(patterUtil.html2Text(content));
    		content = content.replaceAll("&nbsp;", "").trim();
    	}
    	/**
    	 * 解决的问题:在正文中如果在图片路径中存在'/r'或者'/n'的时候，图片显示会出问题，所以在img中
    	 * 出现的‘/r’或者‘/n’不予与替换，只替换除了img标签外的‘/r’或者‘/n’
    	 */
        //用于存储img标签中含有/r字符的标签索引号
    	ArrayList<Integer> indexList = new ArrayList<Integer>();
        //得到所有<img>标签的集合
        ArrayList imgList = ImgChange.getAllContent(content, "<img", ">", true, false);
        if(imgList!=null && imgList.size()>0) {
            for(int j=0; j<imgList.size(); j++) {
            	if(((String) imgList.get(j)).indexOf("/r") != -1 
            			|| ((String) imgList.get(j)).indexOf("/n") != -1) {
            		indexList.add(j);
            	}
            }
        }
        content=content.replace("\n", "").replace("\r", "").replace("\f\n\r\t", ""); 
        if(indexList!=null && indexList.size()>0) {
        	//得到替换/n和/r之后内容中所有<img>标签的集合
            ArrayList imgChangeList = ImgChange.getAllContent(content,
            		"<img", ">", true, false);
        	for(int k=0; k<indexList.size(); k++) {
        		int index = (Integer) indexList.get(k); 
        		content = content.replace(
        				(String) imgChangeList.get(index), (String) imgList.get(index));
        	}
        }  //图片‘/n’或者‘/r’问题处理结束
    	
    	//正文图片处理
        content = imgutil.replaceHtmlTag(content,
                "img", "src", "<img border=0 hspace=0 src=\"", "\"/><br>", "1", ""); 
        if(content.indexOf("webp&wxfrom")>-1 && content.indexOf("http://mmbiz.qpic.cn")>-1){
        	content=content.replace("?tp=webp&wxfrom=5", "");
        }
        return content;
    }
    
    /**
     * saveFile:将取文保存至指定路径下.
     *
     * @param hm 图片文件
     * @param strFileName 目标文件名称
     * @param strFilePath 目标文件路径
     * @return    设定参数 .
    */
    public String saveFile(HashMap hm, String strFileName, String strFilePath) {
        File dir = new File(strFilePath);
        if (!dir.exists()) {
            try {
                dir.mkdirs();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        String newFile = strFilePath + strFileName;

        InputStream is = null;
        FileOutputStream os = null; // 写入目标文件的数据流
        
        try { 
            DataHandler handler = (DataHandler) hm.get("handler");
            if(handler != null){
            	is = handler.getInputStream();      // 获得输入流
            	os = new FileOutputStream(newFile); // 创建输出流
            }else{
            	return "";
            }
            	
            // 获取不到文件
            if (is == null){
                return "";
            }
            byte[] buffer = new byte[4096];
            int bytesread; // 缓存中的字节数目

            // 将一段字节流读到缓存中，然后将它们写出来，循环直到文件末尾停止
            while ((bytesread = is.read(buffer)) != -1) {
                // 读取流直到文件末尾
                os.write(buffer, 0, bytesread); // 写入
            }
        } catch (Exception e) {
        	logger.error("getFile Error:", e);   
            return "";
        } finally {
            // 关闭流
            if (is != null) {
                try {
                    is.close();
                }catch (IOException e){
                     e.printStackTrace();   
                }
            }
            if (os != null) {
                try {
                    os.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return newFile;
    }
     
    /**
     * 判断能否连接到接口。超过15秒为不通
     * @param strInterAddress 接口地址
      * @return true - 成功<br/>
	 *         false - 失败
     */
    public boolean isConnection(String strInterAddress){    	
        if(strInterAddress == null || strInterAddress.length()<=0){
            return false;
        }
        
        URL url = null;
        HttpURLConnection conn = null;
        int  state = -1;
        try {
            
            url = new URL(strInterAddress);
            conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(15000);
			state = conn.getResponseCode();
            
            return state==200;
        }catch(Exception e){
        	logger.warn("jget接口连接异常:", e);    
            return false;
        }
    }
}
