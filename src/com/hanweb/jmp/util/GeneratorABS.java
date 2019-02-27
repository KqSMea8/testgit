package com.hanweb.jmp.util;

import com.hanweb.common.util.StringUtil;
 

public class GeneratorABS {
	 /**
	 * generatorAbs:(这里用一句话描述这个方法的作用).
	 *
	 * @param content content
	 * @return    设定参数 .
	*/
	public static String generatorAbs(String content){
	    	content = HtmlDecoder.decode(content);
			String abs = StringUtil.getSafeString(content, true);
			abs = StringUtil.replace(abs, "&#160;", "");
			abs = abs.trim().replaceAll("\"", "").replaceAll("'", "");
			if(checkChinese(abs)){//判断中英文，英文不需要过滤空格 
				abs = abs.replaceAll(" ", "");
				abs = StringUtil.replace(abs, "&nbsp;", "");
				abs = abs.replaceAll("&[a-zA-Z]{2,6};", "");
			}
			abs = abs.replaceAll("[△▽○◇□☆▲▼●◆■★]", "");
			abs = CutStringUtil.getDescForBlog(abs, 180, 30);
	    	return abs;
	    }
	    
	    /**
	     * 检查是否有中文
	     * @param str str
	     * @return boolean
	     */
	    public static boolean checkChinese(String str){
	    	if("".equals(str) || str == null){
	    		return false;
	    	}
	    	boolean b = false;
			for(int i=0; i<str.length(); i++){
				if(String.valueOf(str.charAt(i)).matches("[\\u4E00-\\u9FA5]+")){
					b = true;
					break;
				}
			}
			return b;
	    }
}
