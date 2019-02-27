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
import com.hanweb.weibo.com.tencent.weibo.oauthv2.OAuthV2;
import com.hanweb.weibo.com.tencent.weibo.oauthv2.OAuthV2Client;
import com.hanweb.weibo.weibo4j.util.WeiboConfig;

@Controller
@RequestMapping("qq")
public class TecentOauthController {

	private static OAuthV2 oAuth = new OAuthV2();
	
	/**
	 * 日志
	 */
	private final Log logger = LogFactory.getLog(getClass());
	
	@RequestMapping("index")
	public void tencentOauth(HttpServletResponse response){
		oAuth.setClientId(WeiboConfig.getValue("qq_client_ID").trim());
        oAuth.setClientSecret(WeiboConfig.getValue("qq_client_SERCRET").trim());
        oAuth.setRedirectUri(WeiboConfig.getValue("qq_redirect_URI").trim());
        
        String authorizationUrl = OAuthV2Client.generateAuthorizationURL(oAuth);
        try {
			response.sendRedirect(authorizationUrl);
		} catch (IOException e) {
			logger.error("腾讯微博授权打开页面失败！");
		}
	}
	
	@RequestMapping("getToken")
	public void getToken(HttpServletResponse response, String code, String openid, String openkey
			, String expires_in){
		if(StringUtil.isNotEmpty(code)){
			//换取access token
			oAuth.setAuthorizeCode(code);
			oAuth.setOpenid(openid);
			oAuth.setOpenkey(openkey);
			oAuth.setGrantType("authorize_code");
			try {
				OAuthV2Client.accessToken(oAuth);
				if(StringUtil.isEmpty(oAuth.getAccessToken())){
					logger.error("腾讯微博授权获取token失败！");
				}else{
					logger.info("腾讯微博授权成功！有效期："
							+ NumberUtil.getInt(expires_in)/24/60/60 + "天");
					Properties properties = new Properties(BaseInfo.getRealPath()
							+ "/WEB-INF/classes/config.properties");
					properties
						.setProperty("qqAccessToken", Md5Util.md5encode(oAuth.getAccessToken()));
					properties
						.setProperty("openid", Md5Util.md5encode(openid));
					properties
						.setProperty("openkey", Md5Util.md5encode(openkey));
					properties.save();
					response.sendRedirect(Configs.getConfigs().getJmpUrl());
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				logger.error("腾讯微博授权获取token失败！");
		    }
		}
	}
}
