/*
 * @(#)CutString.java	
 *
 * Copyright 1997-2008  Hanweb CO.,LTD. All rights reserved.
 * HANWEB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.hanweb.jmp.util;

import java.io.File;
import java.io.UnsupportedEncodingException; 

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.FileResource;
import com.hanweb.jmp.constant.StaticValues;

public class CutStringUtil {
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
     * 按字节截取字符串长度
     * 
     * @param orignal
     *            原始字符串
     * @return 字符串长度
     * @throws UnsupportedEncodingException 
     * @throws UnsupportedEncodingException
     *             使用了JAVA不支持的编码格式
     */
    public static int getLength(String orignal) throws UnsupportedEncodingException{
        // 原始字符不为null，也不是空字符串
        if (orignal != null && !"".equals(orignal)) {
            StringBuffer buff = new StringBuffer();
            char c;
            int count = 0;
            for (int i = 0; i < orignal.length(); i++) {
                c = orignal.charAt(i);
                buff.append(c);
                if (isChineseChar(c)) {
                    count += 2;
                }else{
                    count += 1;
                }
            }
            return count;
        }
        return 0;
    }
    
    /**
     * 用最后出现的“。”截取满足指定长度的字符串
     * 
     * @param str
     *            原始字符串
     * @param maxlen
     *            截取位数
     * @param minlen
     *            截取位数
     * @return 截取后的字符串
     */
	public static String getDescForBlog(String str, int maxlen, int minlen){
		if(str == null || "".equals(str)){
			return "";
		}
		if(maxlen <= minlen || maxlen<= 0){
			if(str.length()>minlen && minlen>0){
				return str.substring(0, minlen);
			}else{
				return str;
			}
		}
		if(str.length()<=maxlen){
			return str;
		}
		int index = str.indexOf("。" , minlen);
		int temp = 0;
		while(index>=minlen && index <=maxlen){
			temp = index;
			index = str.indexOf("。" , index+1);
		}
		if(temp >=minlen && temp <=maxlen){
			return str.substring(0, temp+1);
		}else{
			return str.substring(0, maxlen-3)+"...";
		}
	}
	
	 
	/**
	 * 获取下载的文件
	 * @param filePath 文件路径
	 * @return FileResource
	 */
	public static FileResource getdownloadFile(String filePath) {
		if(StringUtil.isEmpty(filePath)){
			return null;
		}
		filePath = filePath.replaceAll("\\\\", "/");
		filePath = filePath.replaceAll("\\.\\./", "");
		String imgformat = filePath.substring(filePath.lastIndexOf(".")+1).toLowerCase();  
		//下载类型控制
		if(!StaticValues.ALL_FILE_TYPE.contains(imgformat)){
			return null;
		}
		File file = new File(BaseInfo.getRealPath() + filePath);
		String fileName = "";
		if (StringUtil.isNotEmpty(filePath)) {
			int index = filePath.lastIndexOf('/');
			fileName = filePath.substring(index + 1);
		}
		FileResource fileResource = ControllerUtil.getFileResource(file, fileName);

		return fileResource;
	}
}
