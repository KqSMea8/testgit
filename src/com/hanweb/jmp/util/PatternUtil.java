package com.hanweb.jmp.util;

import java.util.ArrayList; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Attribute;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser; 
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter; 
import org.htmlparser.tags.FrameTag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;   
 
public class PatternUtil { 
	/**
	 * logger
	 */
	private final Log logger = LogFactory.getLog(getClass());

    /**
     * 获取链接和图片链接
     * @param content   要过滤的内容
     * @param al .
     * @return ArrayList 
     */
    public ArrayList<String> extracLinksForStr(String content, ArrayList<String> al) {
        String path = "";
        try {
            //创建Parser对象根据传给字符串和指定的编码
            Parser parser = Parser.createParser(content, "gb2312");
            
            // OrFilter 来设置过滤 <a> 标签，<img> 标签和 <frame> 标签，三个标签是 or 的关系
            NodeFilter[] ondefilter = {new NodeClassFilter(LinkTag.class),
                                    new NodeClassFilter(ImageTag.class),
                                    new NodeClassFilter(FrameTag.class)};
            OrFilter linkFilter =  new OrFilter(ondefilter);

            // 得到所有经过过滤的标签
            NodeList list = parser.extractAllNodesThatMatch(linkFilter);
            for (int i = 0; i < list.size(); i++) {
                Node tag = list.elementAt(i);
                if (tag instanceof LinkTag){// <a> 标签
                    LinkTag link = (LinkTag) tag;
                    path = link.getLink();
                    
                } else if (tag instanceof ImageTag){// <img> 标签
                    ImageTag image = (ImageTag) list.elementAt(i);
                    path = image.getImageURL();
                    
                } else{// <frame> 标签
                    // 提取 frame 里 src 属性的链接如 <frame src="test.html"/>
                    FrameTag frame = (FrameTag) list.elementAt(i);
                    Attribute att = frame.getAttributeEx("src");
                    path = att.getValue();
                }
                if(!compareSufix(path)){
                    continue;
                }
                if(!al.contains(path)){   //去掉重复地址
                    al.add(path);  
                }
            }
            return al;
        } catch (Exception e) {
        	logger.error("extracLinksForStr error", e); 
            return null;
        }
    } 
    
    /**
     * 判断是否为图片或附件格式
     * @param fileName 
     * @return  boolean   true 是   false 不是
     */
    public boolean compareSufix(String fileName){
        boolean b = false;
        String filestyle = "jpg,gif,doc,xls,txt,png,bmp,jpeg";
        
        if(filestyle ==""){
            return false;
        }
        String[] sufix = filestyle.split(",");
        try{
            String fileSufix = "";
            //去掉url中的参数，如：<img src="/portal/wps/wcm/connect/7d79ed8046ebd23a8f39af8760d110d0/1/5.jpg
            //?MOD=AJPERES&amp;CACHEID=7d79ed8046ebd23a8f39af8760d110d0/1" border="0">
            if(fileName.indexOf("?")!=-1){
                fileName = fileName.substring(0, fileName.lastIndexOf("?"));
            }
            if(fileName.indexOf(".")!=-1){
                fileSufix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            }
            for(int i=0; i<sufix.length; i++){
                if(fileSufix.equalsIgnoreCase("."+sufix[i])){
                    //与设定的附件格式相同
                    b = true;
                }
            }
            return b;
        } catch (Exception e) {
        	logger.error("compareSufix error", e); 
            return false;
        }
    }
    
    /**
     * 根据旧值匹配数据，并替换为新值
     * @param html      要解析的html文档内容
     * @param oldvalue  旧值
     * @param newvalue  新值
     * @return String 
     */
    public String replaceStr(String html, String oldvalue, String newvalue){
        try{
            if(oldvalue==null || oldvalue.equals("")){
                return null;
            }
            Pattern p = null;
            p = Pattern.compile(oldvalue
                    , Pattern.MULTILINE + Pattern.DOTALL + Pattern.CASE_INSENSITIVE); 
            Matcher m = p.matcher(html); //开始编译 
            
            if (m.find()) { 
                html = m.replaceAll(newvalue);
            } 
            return html;
        }catch(Exception e){
        	logger.error("replaceStr error", e);  
            return null;
        }
    }
     
	/**
	 * regCheck:(这里用一句话描述这个方法的作用).
	 *
	 * @param reg logger
	 * @param string string
	 * @return    设定参数 .
	*/
	public static boolean regCheck(String reg, String string) {
		boolean tem=false; 
		Pattern pattern = Pattern.compile(reg); 
		Matcher matcher=pattern.matcher(string); 
		tem=matcher.find(); 
		return tem; 
	}
	
	/**
     * html2Text:过滤特殊字符.
     *
     * @param inputString 字符串
     * @return    设定参数 .
    */
    public String html2Text(String inputString) {
        String textStr = "";
        //针对jget抓到的特殊样式过滤
        String htmlStr = inputString.replaceAll("(width|WIDTH|HEIGHT|height) *(>|<) *", "");

        try {
            // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regExscript = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regExstyle = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; 
            String regExhtml = "</?font[^>]*?>"; // 定义HTML标签的正则表达式
            String regExStrong = "</?strong[^>]*>"; // 定义HTML标签的正则表达式
            String regExem = "</?em[^>]*>"; // 定义HTML标签的正则表达式
            //定义HTML标签的正则表达式
            String regExxml = "<[\\s]*?xml[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?xml[\\s]*?>";

            Pattern pstr = Pattern.compile(regExscript, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pstr.matcher(htmlStr);
            htmlStr = matcher.replaceAll(""); // 过滤script标签

            pstr = Pattern.compile(regExstyle, Pattern.CASE_INSENSITIVE);
            matcher = pstr.matcher(htmlStr);
            htmlStr = matcher.replaceAll(""); // 过滤style标签

            pstr = Pattern.compile(regExhtml, Pattern.CASE_INSENSITIVE);
            matcher = pstr.matcher(htmlStr);
            htmlStr = matcher.replaceAll(""); // 过滤html标签

            pstr = Pattern.compile(regExStrong, Pattern.CASE_INSENSITIVE);
            matcher = pstr.matcher(htmlStr);
            htmlStr = matcher.replaceAll(""); // 过滤html标签

            pstr = Pattern.compile(regExem, Pattern.CASE_INSENSITIVE);
            matcher = pstr.matcher(htmlStr);
            htmlStr = matcher.replaceAll(""); // 过滤html标签
            
            pstr = Pattern.compile(regExxml, Pattern.CASE_INSENSITIVE); 
            matcher = pstr.matcher(htmlStr); 
            htmlStr = matcher.replaceAll(""); //过滤xml标签 

            textStr = htmlStr;

        } catch (Exception e) {
            logger.error("replaceStr error", e);
        }
        // 返回文本字符串
        return textStr;
    }
	
}
