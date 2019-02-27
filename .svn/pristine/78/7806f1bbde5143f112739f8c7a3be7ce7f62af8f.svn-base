package com.hanweb.weibo.com.hanweb.jmp.controller.interfaces;

import java.util.HashMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.Md5Util;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil; 
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.jmp.annotation.InterfaceCache; 
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.InterfaceLogConfig;
import com.hanweb.weibo.com.tencent.weibo.api.StatusesAPI;
import com.hanweb.weibo.com.tencent.weibo.oauthv2.OAuthV2;
import com.hanweb.weibo.weibo4j.Timeline;
import com.hanweb.weibo.weibo4j.model.WeiboException;
import com.hanweb.weibo.weibo4j.util.WeiboConfig;

@Controller
@RequestMapping("interfaces")
public class BlogControllers {
	

	/**
	 * 运维平台提供给各个服务器的微博数据接口
	 * @param blogType 微博类型 1：新浪 2：腾讯
	 * @param pageflag 腾讯分页标识（0：第一页，1：向下翻页，2：向上翻页）
	 * @param pagetime 腾讯本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，
	 * 				        向下翻页：填上一次请求返回的最后一条记录时间）
	 * @param reqnum   腾讯每次请求记录的条数（1-70条）
	 * @param lastid   腾讯和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，
	 *                 向下翻页：填上一次请求返回的最后一条记录id）
	 * @param names    你需要读取的用户的用户名（可选），目前只支持1个
	 * @param screen_names 新浪昵称，用半角逗号分隔，一次最多20个。 
	 * @param count 新浪单页返回的记录条数，默认为20
	 * @param page 新浪返回结果的页码，默认为1。 
	 * @return
	 */
	@RequestMapping("blog_s")
	@ResponseBody
	@InterfaceCache
	public String blogService(Integer blogType, String pageflag, String pagetime, String reqnum
			, String lastid, String names, String screen_names, String count, String page){
		if(Configs.getConfigs().getAllowIp().indexOf(ControllerUtil.getIp()) == -1){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_BLOG, 
					ControllerUtil.getIp()+InterfaceLogConfig.ERROR_16); 
		}
		HashMap<String, Object> ret = new HashMap<String, Object>(); 
		try {
			if(NumberUtil.getInt(blogType) == 1){
				String accessToken
					= "2.003LK34CibELPDe5c0acaee2ZaBH9C";
				if(StringUtil.isEmpty(accessToken)){
					ret.put("result", "false");
					ret.put("errorcode", "020108");
					ret.put("errormsg", "服务器错误accessToken is null！");
					return JsonUtil.objectToString(ret);
				}
				HashMap<String, String> par = new HashMap<String, String>();
				par.put("screen_names", screen_names);
				par.put("count", StringUtil.isEmpty(count) ? "20" : count);
				par.put("page", StringUtil.isEmpty(page) ? "1" : page);
				Timeline timeline = new Timeline(accessToken);
				try {
					String json = timeline.getTimelineBatch(par).toString();
					if(json!=null && json.length()<150){
						    par = new HashMap<String, String>();
							par.put("uids", screen_names);
							par.put("count", StringUtil.isEmpty(count) ? "20" : count);
							par.put("page", StringUtil.isEmpty(page) ? "1" : page);
							timeline = new Timeline(accessToken);
							json = timeline.getTimelineBatch(par).toString();
					}
					
					return json;
				} catch (WeiboException e) {
					e.printStackTrace();
				}
				
			}else if(NumberUtil.getInt(blogType) == 2){
				String accessToken
					= Md5Util.md5decode(WeiboConfig.getValue("qqAccessToken").trim());
				if(StringUtil.isEmpty(accessToken)){
					ret.put("result", "false");
					ret.put("errorcode", "020108");
					ret.put("errormsg", "服务器错误accessToken is null！");
					return JsonUtil.objectToString(ret);
				}
				OAuthV2 oAuthV2 = new OAuthV2(WeiboConfig.getValue("qq_client_ID").trim()
						, WeiboConfig.getValue("qq_client_SERCRET").trim()
						, WeiboConfig.getValue("qq_redirect_URI").trim());
				oAuthV2.setAccessToken(accessToken);
				oAuthV2.setOpenid(Md5Util.md5decode(WeiboConfig.getValue("openid").trim()));
				StatusesAPI statusesAPI = new StatusesAPI("2.a");
				String json = statusesAPI.userTimeline(oAuthV2, "json"
						, StringUtil.getString(pageflag), StringUtil.getString(pagetime)
						, StringUtil.getString(reqnum), StringUtil.getString(lastid)
						, StringUtil.getString(names), "", "1", "0");
				return json;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ret.put("result", "false");
		ret.put("errorcode", "020108");
		ret.put("errormsg", "服务器错误！");
		return JsonUtil.objectToString(ret);
	}
	
}
