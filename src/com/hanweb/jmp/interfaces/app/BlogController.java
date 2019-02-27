/**
 * 3.1.12 待完善
 */
package com.hanweb.jmp.interfaces.app;

import java.util.ArrayList; 
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;

import com.hanweb.jmp.annotation.InterfaceCache;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.constant.InterfaceLogConfig;
import com.hanweb.jmp.util.HttpClientUtil;

@Controller
@RequestMapping("interfaces")
public class BlogController {
	
	/**
	 * colService
	 */
	@Autowired
	ColService colService;

	/**
	 * 微博列表接口（给客户端调用）
	 * @param resourceid 栏目id
	 * @param pageflag 腾讯分页标识（0：第一页，1：向下翻页，2：向上翻页）
	 * @param pagetime 腾讯本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，
	 * 				        向下翻页：填上一次请求返回的最后一条记录时间）
	 * @param reqnum   腾讯每次请求记录的条数（1-70条）
	 * @param lastid   腾讯和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，
	 *                 向下翻页：填上一次请求返回的最后一条记录id）
	 * @param count 新浪单页返回的记录条数，默认为20
	 * @param page 新浪返回结果的页码，默认为1。 
	 * @return String
	 */
	@RequestMapping("blog_c")
	@ResponseBody
	@InterfaceCache
	public String blogClient(Integer resourceid, String pageflag, String pagetime, String reqnum, 
			                 String lastid, String count, String page){ 
		Col col = colService.findByIid(NumberUtil.getInt(resourceid));
		if(col == null || col.getIid() <= 0 || col.getType() != 3 || col.getHdType() != 7){ 
			return InterfaceLogConfig.interfaceResult(false, 
					InterfaceLogConfig.MOD_BLOG, InterfaceLogConfig.ERROR_03);
		}
		List<NameValuePair> list = setList(col, pageflag, pagetime, reqnum, lastid
				, col.getNickName(), col.getNickName(), count, page);
		String json
			= HttpClientUtil.postInfo(col.getActUrl() + "/interfaces/blog_s.do", list, "UTF-8");
		
		return json;
	}
	
	/**
	 * setList
	 * @param col col
	 * @param pageflag pageflag
	 * @param pagetime pageflag
	 * @param reqnum pageflag
	 * @param lastid lastid
	 * @param names names
	 * @param screenNames screen_names
	 * @param count count
	 * @param page page
	 * @return    设定参数 .
	*/
	private List<NameValuePair> setList(Col col, String pageflag, String pagetime, String reqnum, String lastid, 
			                            String names, String screenNames, String count, String page){
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		
		NameValuePair nameValuePair = new BasicNameValuePair("pageflag", pageflag);
		list.add(nameValuePair);
		
		nameValuePair = new BasicNameValuePair("pagetime", pagetime);
		list.add(nameValuePair);
		
		nameValuePair = new BasicNameValuePair("reqnum", reqnum);
		list.add(nameValuePair);
		
		nameValuePair = new BasicNameValuePair("lastid", lastid);
		list.add(nameValuePair);
		
		nameValuePair = new BasicNameValuePair("names", names);
		list.add(nameValuePair);
		
		nameValuePair = new BasicNameValuePair("screen_names", screenNames);
		list.add(nameValuePair);
		
		nameValuePair = new BasicNameValuePair("count", count);
		list.add(nameValuePair);
		
		nameValuePair = new BasicNameValuePair("page", page);
		list.add(nameValuePair);
		
		nameValuePair = new BasicNameValuePair("blogType", StringUtil.getString(col.getBlogType()));
		list.add(nameValuePair);
		
		return list;
	}
	
}