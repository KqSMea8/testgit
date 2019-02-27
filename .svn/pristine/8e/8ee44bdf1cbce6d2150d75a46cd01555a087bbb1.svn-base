package com.hanweb.jmp.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; 

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.file.IFileUtil;
 
public class StrUtil {

	/**
	 * logger
	 */
	private final Log logger = LogFactory.getLog(getClass());
    /**
     * 判断是否是一个中文汉字
     * 
     * @param c
     *            字符
     * @return true表示是中文汉字，false表示是英文字母
     * @throws UnsupportedEncodingException
     *             使用了JAVA不支持的编码格式
     */
    private static boolean isChineseChar(char c)
            throws UnsupportedEncodingException {
        // 如果字节数大于1，是汉字
        // 以这种方式区别英文字母和中文汉字并不是十分严谨，但在这个题目中，这样判断已经足够了
        return String.valueOf(c).getBytes("GBK").length > 1;
    }

    /**
     * 按字节截取字符串
     * 
     * @param orignal
     *            原始字符串
     * @param count
     *            截取位数
     * @return 截取后的字符串
     * @throws UnsupportedEncodingException
     *             使用了JAVA不支持的编码格式
     */
    public static String substring(String orignal, int count)
            throws UnsupportedEncodingException {
        // 原始字符不为null，也不是空字符串
        if (orignal != null && !"".equals(orignal)) {
            // 将原始字符串转换为GBK编码格式
            // orignal = new String(orignal.getBytes(), "GBK");//
            // System.out.println(new String(orignal.getBytes("GBK"),"UTF-8"));
            // 要截取的字节数大于0，且小于原始字符串的字节数
            if (count > 0 && count < orignal.getBytes("GBK").length) {
                StringBuffer buff = new StringBuffer();
                char c;
                for (int i = 0; i < count; i++) {
                    c = orignal.charAt(i);
                    buff.append(c);
                    if (isChineseChar(c)) {
                        // 遇到中文汉字，截取字节总数减1
                        --count;
                    }
                }
                // String(buff.toString().getBytes("GBK"),"UTF-8"));
                return new String(buff.toString().getBytes(), "UTF-8");
            }
        }
        return orignal;
    }
    
    /**
     * 替换"<"和">"
     * @param strData 
     * @return String 
     */
    public  String replaceHtmlTag(String strData){
        try{
            if(strData==null || strData.equals("")){
                return "";
            }
            strData = strData.replaceAll("&", "&amp;"); //顺序 需要排在前面 
            strData = strData.replaceAll("<", "&lt;");
            strData = strData.replaceAll(">", "&gt;");
            
            
            return strData;
        }catch(Exception e){
        	logger.error("replaceStr Error:", e); 
            return "";
        }
    }

    /**
     * 返回第二个ArrayList与第一个ArrayList不同的元素(只是第二个中的元素)
     * @param al  al
     * @param ala ala  
     * @return ArrayList 
     */
    public  ArrayList<String> getDiffValue(ArrayList<String> al, ArrayList<String> ala){
        ArrayList<String> result = new ArrayList<String>();
        int flag = 0;
        try{
            if(al==null || ala==null){
                return null;
            }
            if(al.size()==0){
                return ala;
            }
            for(int i=0; i<ala.size(); i++){
                String valuev = (String) ala.get(i);
                for(int j=0; j<al.size(); j++){
                    String value = (String) al.get(j);
                    if(value.equals(valuev)){
                        flag = 0;
                        break;
                    }else{
                        flag = 1;
                    }
                }
                if(flag==1){
                    result.add(valuev);
                }
            }
            return result;
        }catch(Exception e){
        	logger.error("getDiffValue Error:", e);  
            return null;
        }
    }
    
    /**
     * 位数不足补0
     * @param num       需要补0的字符
     * @param formart   位数
     * @return 补足 后的结果
     */
    public String getNumber(String num, int formart){
        String result = ""; 
        int numlength = String.valueOf(num).length(); 
        
        if(numlength >= formart){
            return num; 
        }else{
            result = "0"+num; 
            num = getNumber(result, formart); 
        }
        return num; 
    }
    
    /**
     * getMainURL:取URL中的主域名.
     *
     * @param url 链接地址
     * @return    设定参数 .
    */
    public  String getMainURL(String url){
        if (url == null || url.length() == 0){
            return "";
        }
        try {
            Pattern p = Pattern.compile("[^//]*?\\.(com\\.cn|com|cn|net|org|biz|info|cc|tv|xml)",
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = p.matcher(url);
            matcher.find();
            url = matcher.group();
        } catch (Exception e) {
        	logger.error("取当前URL根目录  Error:", e);   
        }
        return url;
    }

    /**
     * getCurURL:取URL中的取到当前目录.
     *
     * @param url 链接地址
     * @return    设定参数 .
    */
    public  String getCurURL(String url){
        if (url == null || url.length() == 0){
            return "";
        }
        try {
            Pattern p = Pattern.compile("(http://|https://){1}[\\w\\.\\-/:]+",
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = p.matcher(url);
            matcher.find();
            url = matcher.group();

            if (url != null && !url.endsWith("/")) {
                int p1 = url.lastIndexOf("/");
                url = url.substring(0, p1 + 1);
            }
        } catch (Exception e) {
        	logger.error("取当前URL根目录：error", e);
            return "";
        }
        return url;
    }
    
    /**
     * getDifferStringValue:比较新旧串 不同的元素（只是旧串中的）
     *
     * @param strOld 原字符串 
     * @param strNew 新字符串
     * @param strSplit 分割符
     * @return    设定参数 .
    */
    public static String getDifferStringValue(String strOld, String strNew, String strSplit){
        if(strSplit==null || "".equals(strSplit)){
            return "";
        }
        if(strOld==null || strNew==null){
            return "";
        }
        if("".equals(strNew)){
            return strOld;
        }
        StringBuffer sbdeffer = new StringBuffer();
        String[] arrOld = strOld.split(strSplit);
        String[] arrNew = strNew.split(strSplit);
        int flag = 0;
        for(int i=0; arrOld != null && i<arrOld.length; i++){
            for(int j=0; arrNew != null && j<arrNew.length; j++){
                if(arrNew[j].equals(arrOld[i])){
                    flag = 0;
                    break;
                }else{
                    flag = 1;
                }
            }
            if(flag==1){
            	sbdeffer.append(strSplit+arrOld[i]);
            }
        }
        return sbdeffer.substring(strSplit.length());
    }

    /**
     * subStr:(这里用一句话描述这个方法的作用).
     *
     * @param str str
     * @param len len
     * @return    设定参数 .
    */
    public static String subStr(String str, int len){
        str = StringUtil.getString(str);
        if(str.length() > len){
        	str=str.substring(0, len);
        }
        return str;
    }
    
    /**
	 * 按字节长度截取字符串
	 * @param str 将要截取的字符串参数
	 * @param toCount 截取的字节长度
	 * @param charset 字符串编码
	 * @return 返回截取后的字符串
	 */
	public String limitLen(String str, int toCount, String charset) {
		int reInt = 0;
		StringBuilder reStr = new StringBuilder();
		if (str != null && str.length() > 0) {
			if (charset == null || charset.length() == 0){
				charset = "UTF-8";
			}
			try {
				int nLen = 0;
				nLen = str.getBytes(charset).length;
				if (nLen <= toCount) {
					reStr = new StringBuilder().append(str);
				} else {
					char[] tempChar = str.toCharArray();
					for (int kk = 0; kk < tempChar.length; kk++) {
						String s1 = String.valueOf(tempChar[kk]);
						byte[] b = s1.getBytes("UTF-8");
						reInt += b.length;
						if (reInt >= toCount){
							break;
						}
						reStr.append(tempChar[kk]);
					}
				}
			} catch (Exception ex) {
				logger.error("截取字符串长度时发生异常!");
			}
		}
		return reStr.toString();
	}
	
	/**
	 * 判断字符串是否超过指定长度,超过长度将进行截取,截取后增加后缀字符
	 * @param str 要截取的字符串
	 * @param toCount  长度
	 * @param charset  编码
	 * @param strTag  后缀字母
	 * @return String
	 */
	public String limitLen(String str, int toCount, String charset, String strTag) {
		strTag = StringUtil.getString(strTag);		
		int reInt = 0;
		StringBuilder reStr = new StringBuilder();
		if (str != null && str.length() > 0) {
			if (charset == null || charset.length() == 0){
				charset = "UTF-8";
			}
			try {
				int nLen = 0;
				nLen = str.getBytes(charset).length;
				if (nLen <= toCount) {
					reStr = new StringBuilder().append(str);
				} else {
					char[] tempChar = str.toCharArray();
					for (int kk = 0; kk < tempChar.length; kk++) {
						String s1 = String.valueOf(tempChar[kk]);
						byte[] b = s1.getBytes("UTF-8");
						reInt += b.length;
						if (reInt >= toCount){
							break;
						}
						reStr.append(tempChar[kk]);
					}
					reStr.append(strTag);
				}
			} catch (Exception ex) {
				logger.error("截取字符串长度时发生异常!");
			}
		}
		return reStr.toString();
	}
    
	/**
    * getContentByTag:获得标签之间的字符串.
    *
    * @param startTag 开始标签
    * @param endTag 结束标签
    * @param content 字符串内容 
    * @return    设定参数 .
    */
	public String getContentByTag(String startTag, String endTag,
           String content){
       if (startTag == null || endTag == null || content==null){
           return null;
       }
       if (startTag.length() == 0 || endTag.length() == 0 || content.length()==0){
           return null;
       }
       int nPos1 = content.indexOf(startTag)+startTag.length();
       int nPos2 = content.indexOf(endTag); 
       
       if (nPos1 != -1 && nPos2 != -1 && nPos2 > nPos1) {
           return content.substring(nPos1, nPos2);
       }
       return null;
	}
    
   /**
    * replaceContByTag:生成替换之后的字符串.
    *
    * @param startTag 开始标签
    * @param endTag 结束标签
    * @param replaceContent 要替换的内容
    * @param content 内容
    * @return    设定参数 .
   */
   public String replaceContByTag(String startTag, String endTag,
           String replaceContent, String content) {
       if (startTag == null || endTag == null){
           return null;
       }
       if (startTag.length() == 0 || endTag.length() == 0){
           return null;
       }
       String startHtml = "";
       String endHtml = "";
       int nPos1 = content.indexOf(startTag);
       int nPos2 = content.indexOf(endTag, nPos1);
       if (nPos1 != -1 && nPos2 != -1 && nPos2 > nPos1) {
           startHtml = content.substring(0, nPos1 + startTag.length());
           endHtml = content.substring(nPos2);
           content = startHtml + replaceContent + endHtml;
           return content;
       }
       return null;
   } 
   
   /**
 * removeHTMLTag:(这里用一句话描述这个方法的作用).
 *
 * @param tmp tmp
 * @return    设定参数 .
*/
public static String removeHTMLTag(String tmp){ 
	   String regEx = "<!--.*?-->|</?[^>aApP(br)(table)/].*?>|[\f\n\r\t]";
       Pattern p = Pattern.compile(regEx);
       Matcher m = p.matcher(tmp);
       return m.replaceAll("");
   }
   
   /**
	 * 过滤emoji表情
	 * 
	 * @param contents contents
	 * @return String
	 */
	public String filterEmoji(String contents) {
		try {
			String finalStr = contents;
			contents = URLEncoder.encode(contents, "utf-16");
			finalStr = finalStr.replaceAll("[\\pP|~|$|^|<|>|\\||\\+|=]*", "");
			finalStr = finalStr.replaceAll("[A-Za-z0-9\\d]", "");
			finalStr = URLEncoder.encode(finalStr, "utf-16");
			finalStr = finalStr.replace("%fe%ff", "");
			finalStr = finalStr.replace("%FE%FF", "");
			int splitLen = 6;
			int pos = 0;
			int len = (finalStr.length() / splitLen);
			String[] splitStr = new String[len];
			for (int i = 0; i < len; i++) {
				splitStr[i] = finalStr.substring(pos, pos + splitLen);
				pos += splitLen;
				if (splitStr[i].substring(1, 2).equals("d") 
						|| splitStr[i].substring(1, 2).equals("D")) {
					contents = contents.replace(splitStr[i], "");
				}
			}
			contents = URLDecoder.decode(contents, "utf-16");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return contents;
	}
	/**
	 * 文件夹遍历上传方法
	 * @author ZDJ
	 * @param path 本地文件夹路径
	 */
	public static void moveFile(String path){ 
		IFileUtil fileUtil = (IFileUtil)SpringUtil.getBean("FileUtil");
	    File root = new File(path);
   	    File[] files = root.listFiles();
	    for(File file:files){   
	      if(file.isDirectory()){
	    	 moveFile(file.getAbsolutePath());
	      }else{
	    	 fileUtil.moveFile(new File(file.getAbsolutePath()), 
	    	 fileUtil.getAbsolutePath(file.getAbsolutePath().replace(BaseInfo.getRealPath(), "")
	    							.substring(1)));
	      }     
	  }
	}

    /**
     * main:.
     *
     * @param args    设定参数 .
    */
    public static void main(String[] args) {
    	String html="werew<div>123<span><br><table>11</table>" 
    			+ "<a href='11'><img>qwee</img></a></span></div>";
    	System.out.println(removeHTMLTag(html));
    }
}
