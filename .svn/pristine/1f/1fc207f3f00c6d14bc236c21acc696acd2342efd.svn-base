package com.hanweb.weibo.com.hanweb.jmp.controller.blog;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.Md5Util;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.Properties;
import com.hanweb.common.util.StringUtil;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.weibo.weibo4j.Oauth;
import com.hanweb.weibo.weibo4j.http.AccessToken;
import com.hanweb.weibo.weibo4j.model.WeiboException;


@Controller
@RequestMapping("sina")
public class SinaOauthController {

	/**
	 * 日志
	 */
	private final Log logger = LogFactory.getLog(getClass());
	
	@RequestMapping("index")
	public void sinaOauth(HttpServletResponse response){
		Oauth oauth = new Oauth();
		try {
			String ret = oauth.authorize("");
			response.sendRedirect(ret);
		} catch (WeiboException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("getToken")
	public void getToken(HttpServletResponse response, String code, String expires_in){
		if(StringUtil.isNotEmpty(code)){
			Oauth oauth = new Oauth();
			try {
				AccessToken accessToken = oauth.getAccessTokenByCode(code);
				if(StringUtil.isNotEmpty(accessToken.getAccessToken())){
					logger.info("新浪微博授权成功！有效期："
							+ NumberUtil.getInt(expires_in)/24/60/60 + "天");
					Properties properties = new Properties(BaseInfo.getRealPath()
							+ "/WEB-INF/classes/config.properties");
					properties
						.setProperty("sinaAccessToken"
							, Md5Util.md5encode(accessToken.getAccessToken()));
					properties.save();
					response.sendRedirect(Configs.getConfigs().getJmpUrl());
				}else{
					logger.error("新浪微博授权获取token失败！");
				}
			} catch (WeiboException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
