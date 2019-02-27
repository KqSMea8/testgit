package com.hanweb.jmp.util;

import gui.ava.html.image.generator.HtmlImageGenerator;

import java.awt.Dimension; 
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
  
import com.hanweb.common.util.StringUtil;

public class ImgChange {	

	/**
	 * 根据前后标识符提取数据 如：提取"<title>XXXX< /title>"中的文字XXXX
	 * @param html  要解析的html文档内容
	 * @param startregex startregex
	 * @param endregex endregex
	 * @param includeregex	是否包含前后标识符
	 * @param casesensitive	是否区分大小写
	 * @return 解析结果，可以多次匹配，每次匹配的结果按文档中出现的先后顺序添加进结果List
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getContent(String html, String startregex
			, String endregex, boolean includeregex, boolean casesensitive) {
		try{
			if(startregex==null || startregex.equals("") 
					|| endregex ==null || endregex.equals("")) {
				return null;
			}
			//对特殊字符进行处理
			startregex = convertOther(startregex);
			endregex = convertOther(endregex);

			ArrayList resultList = new ArrayList(); 
			//Pattern p = Pattern.compile(""+startregex+"([^"+endregex+"]*)");
			//匹配<title>开头，</title>结尾的文档 

			Pattern p = null;
			//是否需要包含前后标识符
			if(includeregex){
				if(casesensitive){	//区分
					p = Pattern.compile("("+startregex+".*?"+endregex+")"
							, Pattern.MULTILINE + Pattern.DOTALL); 
				}else{			//不区分
					p = Pattern.compile("("+startregex+".*?"+endregex+")"
							, Pattern.MULTILINE + Pattern.DOTALL + Pattern.CASE_INSENSITIVE); 
				}
			}else{
				if(casesensitive){	//区分
					p = Pattern.compile(""+startregex+"(.*?)"+endregex+""
							, Pattern.MULTILINE + Pattern.DOTALL);
				}else{			//不区分
					p = Pattern.compile(""+startregex+"(.*?)"+endregex+""
							, Pattern.MULTILINE + Pattern.DOTALL + Pattern.CASE_INSENSITIVE); 
				}
			}
			Matcher m = p.matcher(html); //开始编译 
			while (m.find()) { 
				resultList.add(m.group(0)); //获取被匹配的部分
			} 
			return resultList; 
		}catch(Exception e){
			return null;
		}
	} 


	/**
	 * 转换特殊字符
	 * @param str 
	 * @return String 
	 */
	public static String convertOther(String str){
		try {
			str = StringUtil.replace(str, "\\", "\\\\");
			str = StringUtil.replace(str, "$", "\\$");
			str = StringUtil.replace(str, "(", "\\(");
			str = StringUtil.replace(str, ")", "\\)");
			str = StringUtil.replace(str, "*", "\\*");
			str = StringUtil.replace(str, "+", "\\+");
			str = StringUtil.replace(str, ".", "\\.");
			str = StringUtil.replace(str, "[", "\\[");
			str = StringUtil.replace(str, "]", "\\]");
			str = StringUtil.replace(str, "?", "\\?");
			str = StringUtil.replace(str, "^", "\\^");
			str = StringUtil.replace(str, "{", "\\{");
			str = StringUtil.replace(str, "}", "\\}");
			str = StringUtil.replace(str, "|", "\\|");
			return str;
		} catch (Exception e) {
			return str;
		}
	}
	/**
	 * 根据旧值匹配数据，并替换为新值
	 * @param html		要解析的html文档内容
	 * @param oldvalue	旧值
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
			return null;
		}
	}

	/**
	 * imgConversion:(这里用一句话描述这个方法的作用).
	 *
	 * @param htmlstr htmlstr
	 * @param toPath toPath
	 * @return    设定参数 .
	*/
	public boolean imgConversion(String htmlstr, String toPath) {
		int i = htmlstr.indexOf("<table");
		int j = htmlstr.lastIndexOf("</table>"); 
		String content = "";
		if(i > 0&&j > 0&& i<j){
			content = htmlstr.substring(i, j);
		}else{
			return false;
		}
		if(content == null||"".equals(content)){
			return false;
		}
		//		if(list!=null && list.size()>0) {
		//			content = (String)list.get(0);
		//		} else {
		//			return false;
		//		}
		content = replaceStr(content,  "<table.*?>",  
				"<table border='0' cellpadding='5' cellspacing='1' bgcolor='#dfdfdf'>");
		content = replaceStr(content,  "<tbody.*?>",  "<tbody>");
		content = replaceStr(content,  "<tr.*?>",  "<tr>"); 
		content = replaceStr(content,  "<td.*?>",  "<td bgcolor='#cccccc'>");
		//		byte[] data = content.getBytes();//这里获取到的字节数组不存在编码格式的问题吧？
		//		String str2 =null;
		//		try {
		//			str2 = new String(data,"UTF-8");
		//		} catch (UnsupportedEncodingException e) {
		//			e.printStackTrace();
		//		}//str2的字符编码是GBK的格式？
		//		//那么如何获取str2的编码格式呢？
		HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
		imageGenerator.loadHtml(content);
		imageGenerator.getBufferedImage();
		imageGenerator.setSize(new Dimension(100, 100));
		imageGenerator.saveAsImage(toPath);
		return true;
	} 



	/**
	 * 根据前后标识符提取数据 如：提取"<title>XXXX< /title>"中的文字XXXX
	 * @param html  要解析的html文档内容
	 * @param startregex startregex
	 * @param endregex endregex
	 * @param includeregex	是
	 * @param casesensitive	是否区分大小写
	 * @return 解析结果，可以多次匹配，每次匹配的结果按文档中出现的先后顺序添加进结果List
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getAllContent(String html, String startregex
			, String endregex, boolean includeregex, boolean casesensitive) {
		try{
			if(startregex==null || startregex.equals("") 
					|| endregex ==null || endregex.equals("")) {
				return null;
			}
			//对特殊字符进行处理
			startregex = convertOther(startregex);
			endregex = convertOther(endregex);

			ArrayList resultList = new ArrayList(); 
			//Pattern p = Pattern.compile(""+startregex+"([^"+endregex+"]*)");
			//匹配<title>开头，</title>结尾的文档 

			Pattern p = null;
			//是否需要包含前后标识符
			if(includeregex){
				if(casesensitive){	//区分
					p = Pattern.compile("("+startregex+".*?"+endregex+")"
							, Pattern.MULTILINE + Pattern.DOTALL); 
				}else{			//不区分
					p = Pattern.compile("("+startregex+".*?"+endregex+")"
							, Pattern.MULTILINE + Pattern.DOTALL + Pattern.CASE_INSENSITIVE); 
				}
			}else{
				if(casesensitive){	//区分
					p = Pattern.compile(""+startregex+"(.*?)"+endregex+""
							, Pattern.MULTILINE + Pattern.DOTALL);
				}else{			//不区分
					p = Pattern.compile(""+startregex+"(.*?)"+endregex+""
							, Pattern.MULTILINE + Pattern.DOTALL + Pattern.CASE_INSENSITIVE); 
				}
			}
			Matcher m = p.matcher(html); //开始编译 
			while (m.find()) { 
				resultList.add(m.group()); //获取被匹配的部分
			} 
			return resultList; 
		}catch(Exception e){
			return null;
		}
	} 
}