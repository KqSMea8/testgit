package com.hanweb.jmp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

public class ReplaceUtil {
	
    /**
     * replaceContent:(这里用一句话描述这个方法的作用).
     *
     * @param content  content
     * @return    设定参数 .
    */
    public String replaceContent(String content){
    	content = doFilterHtml(content);
    	content = balanceTag(content);
    	//content = addAlign(content);
        return content;
    }

    /**
     * doFilterHtml:(这里用一句话描述这个方法的作用).
     *
     * @param html html
     * @return    设定参数 .
    */
    private static String doFilterHtml(String html){
    	html = replaceStr(html, "<\\s*wbr\\s*>", "");
    	html = replaceStr(html, "&#160;", "");
    	// 去除style
    	html = replaceStr(html, "style\\s*=\\s*(\"|')[^(\"|')]*(\"|')", "");
    	// 去除width
    	html = replaceStr(html, "(width|height|align) *= *('|\")[0-9]*[A-z]*('|\")", "");
    	html = replaceStr(html, "<\\s*font[^>]*?>", "");
    	html = replaceStr(html, "<\\s*/\\s*font\\s*>", "");
    	html = replaceStr(html, "<\\s*strong[^>]*?>", "");
    	html = replaceStr(html, "<\\s*/\\s*strong\\s*>", "");
    	html = replaceStr(html, "<\\s*em[^>]*?>", "");
    	html = replaceStr(html, "<\\s*/\\s*em\\s*>", "");
    	// 移除div标签中样式及开头空格
    	html = replaceStr(html, "<\\s*div[^>]*?>", "<div>");
    	// 移除span标签中样式及开头空格
    	html = replaceStr(html, "<\\s*span[^>]*?>", "<span>");
    	// 换行后空格去掉
    	html = replaceStr(html, "(<\\s*br[^>]*?>\\s*)+", "<br/>");
    	// 中英文全角空格去掉
    	html = replaceStr(html, "(　|　)*", "");
    	// 补齐html标签
    	html = balanceTag(html);
    	// 移除空的p标签
    	html = replaceStr(html, 
    			"<\\s*p[^>]*?>[(<\\s*(wbr|br)\\s*/?\\s*>)(&nbsp;)\\s]+<\\s*/\\s*p\\s*>", "");
    	//去除原缩进
    	html = replaceStr(html, "<\\s*p[^>]*?>[\\s]*", "<p>");
    	//去除原缩进
    	html = replaceStr(html, "<\\s*p[^>]*?>(&nbsp;)*", "<p>");
    	// 去除script
        html = replaceStr(html, "<\\s*script[^<]*?<\\s*/\\s*script\\s*>", "");
        // 去除style
        html = replaceStr(html, "<\\s*style[^<]*?<\\s*/\\s*style\\s*>", "");
        // 去除XML
        html = replaceStr(html, "<\\s*xml[^<]*?<\\s*/\\s*xml\\s*>", "");
        
        //去除img中间的a标签
        if(PatternUtil.regCheck("<\\s*?img[^>]*?>", html)) { 
        	html = replaceStr(html, "<a[^>]*>", "");
            html = replaceStr(html, "</a>", "");
        }
        
    	return html;
    }

    /**
     * 补齐HTML代码的方法
     * 
     * @param inputHtml
     *            传入截取后的HTML代码
     * @return 补齐后的HTML代码
     */
    private static String balanceTag(String inputHtml) {
        StringBuffer fragmentHtml = new StringBuffer();
        // 新建一个标签工场
        PrototypicalNodeFactory factory = new PrototypicalNodeFactory();
        //factory.registerTag(new StrongTag()); // 增加对 <strong> 标签的补齐支持
        // 新建一个转换器
        Parser parser = new Parser();
        try {
        	// 注册这个新工厂到转换器
            parser.setNodeFactory(factory);
            // 把html代码放入转换器
            parser.setInputHTML(inputHtml);
            // 设置字符集
            parser.setEncoding("utf-8");
            for (NodeIterator e = parser.elements(); e.hasMoreNodes(); ){
                Node node = e.nextNode();
                // 每块节点存入fragmentHtml
                fragmentHtml.append(node.toHtml());
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
        String mystr = fragmentHtml.toString();
        fragmentHtml = null;
        return mystr;
    }
     
    /**
     * addAlign:首行缩进 图片居中.
     *
     * @param html html
     * @return    设定参数 .
    */
    private static String addAlign(String html){
    	html = replaceStr(html, "<\\s*p[^>]*?>", "<p style='TEXT-ALIGN: left; TEXT-INDENT: 2em'>");
    	html = replaceStr(html, "<\\s*img[^>]*?>", "<p style='TEXT-ALIGN: center;'>$0</p>");
    	return html;
    }
    
    /**
     * 字符串替换工具类
     * @param html        内容
     * @param oldvalue    旧值支持正则
     * @param newvalue    新值
     * @return String
     */
    private static String replaceStr(String html, String oldvalue, String newvalue){
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
}

