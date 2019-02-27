package com.hanweb.jmp.pack.controller.appuser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.Md5Util;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.VerifyCode;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.pack.entity.App;
import com.hanweb.jmp.pack.entity.AppUser;
import com.hanweb.jmp.pack.listener.FrontUserSessionInfo;
import com.hanweb.jmp.pack.service.AppService;
import com.hanweb.jmp.pack.service.AppUserService;

@Controller
@RequestMapping("app")
public class AppUserController {
	
	/**
	 * logger
	 */
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * qqKey
	 */
	private String qqKey = "801421312";
	
	/**
	 * qqSecret
	 */
    private String qqSecret = "3859d2f29fee7390e631912dfbce46a7";
    
    /**
	 * sinaKey
	 */
    private String sinaKey = "2935514224";
    
    /**
	 * qqDomain
	 */
    private String qqDomain = Configs.getConfigs().getJmpUrl()+"/app/callbackqq.do";
    
    /**
	 * sinaDomain
	 */
    private String sinaDomain = Configs.getConfigs().getJmpUrl()+"/app/callbacksina.do";
	
    /**
	 * appuserService
	 */
	@Autowired
	AppUserService appuserService;
	
	/**
	 * appService
	 */
	@Autowired
	private AppService appService;
	
	
	/**
	 * showUserLogin
	 * @param uuid uuid
	 * @param response response
	 * @param clienttype clienttype
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "index")
	@ResponseBody
	public ModelAndView showUserLogin(String uuid, HttpServletResponse response, String clienttype) {
		HttpSession session = SpringUtil.getRequest().getSession();
		AppUser user = FrontUserSessionInfo.getCurrentUserInfo();
		if(user != null){
			session.removeAttribute(user.getUsername());
			session.removeAttribute("appid");
			session.removeAttribute("pic");
			session.removeAttribute("ssp");
			session.removeAttribute("iconurl");
		}
		FrontUserSessionInfo.removeCurrentUserInfo();
		if(StringUtil.isEmpty(clienttype)){
			clienttype = "2";
		}
		if(StringUtil.isEmpty(uuid)){
			uuid = "uuid" + UUID.randomUUID();
		}
		if(Configs.getConfigs().getIsAutoCreateApp()!=1){
			try {
				response.sendRedirect(Configs.getConfigs().getJmpUrl()+"/login.do");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		ModelAndView modelAndView = null ;
		if(Configs.getConfigs().getCreateAppType() == 0) {
			modelAndView = new ModelAndView("app/appuser/index");
		} else {
			modelAndView = new ModelAndView("app/appuser/indexback");
		}
		qqDomain = Configs.getConfigs().getJmpUrl()+"/app/callbackqq.do";
		sinaDomain = Configs.getConfigs().getJmpUrl()+"/app/callbacksina.do";
		modelAndView.addObject("url", "checkuser.do");
		String domain = Configs.getConfigs().getJmpUrl();
		modelAndView.addObject("sysUrl", domain);
		String qqUrl = "https://open.t.qq.com/cgi-bin/oauth2/authorize?"
					 + "client_id=" + qqKey
					 + "&redirect_uri=" + qqDomain + "?a="+uuid+"_"+clienttype
					 + "&response_type=code";
		String sinaUrl = "https://api.weibo.com/oauth2/authorize?"
					   + "client_id=" + sinaKey
					   + "&redirect_uri=" + sinaDomain
					   + "&response_type=code&display =popub&state="+uuid+"_"+clienttype;
		modelAndView.addObject("qqUrl", qqUrl);
		modelAndView.addObject("sinaUrl", sinaUrl);
		modelAndView.addObject("jmpurl", Configs.getConfigs().getJmpUrl());
		return modelAndView;
	}
	
	/**
	 * showVerifyCode
	 * @param session session
	 * @param response response
	 * @return    设定参数 .
	 */
	@RequestMapping("verifyCode")
	@ResponseBody
	public String showVerifyCode(HttpSession session, HttpServletResponse response) {
		return VerifyCode.generate(response, "app_rand");
	}
	
	/**
	 * showVerifyCode
	 * @param session session
	 * @param response response
	 * @return    设定参数 .
	 */
	@RequestMapping("registverifyCode")
	@ResponseBody
	public String showRegisterVerifyCode(HttpSession session, HttpServletResponse response) {
		return VerifyCode.generate(response, "appregist_rand");
	}
	
	/**
	 * showUserRegist
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "regist")
	@ResponseBody
	public ModelAndView showUserRegist() {
		ModelAndView modelAndView = new ModelAndView("app/appuser/register");
		modelAndView.addObject("url", "registUser.do");
		modelAndView.addObject("jmpurl", Configs.getConfigs().getJmpUrl());
		return modelAndView;
	}
	
	/**
	 * showUserLogin
	 * @param response response
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "login")
	@ResponseBody
	public ModelAndView showUserLogin(HttpServletResponse response) { 
		AppUser user = FrontUserSessionInfo.getCurrentUserInfo();
		ModelAndView modelAndView=null;
		if(user == null){
			//用户未登录直接跳转到登录页面
			response.setCharacterEncoding("UTF-8");
			try {
				response.sendRedirect(Configs.getConfigs().getJmpUrl()+"/app/index.do");
			} catch (IOException e) {
				logger.error("sendRe error", e);
			}    
			return null; 
		}
		HttpSession session = SpringUtil.getRequest().getSession();
		session.removeAttribute(user.getUsername());
		session.removeAttribute("appid");
		session.removeAttribute("pic");
		session.removeAttribute("ssp");
		session.removeAttribute("iconurl");
		Integer divWidth=600;
		Integer divHeight = 130;
		List<App> applist = new ArrayList<App>(); 
		if(Configs.getConfigs().getCreateAppType() == 0) {
			applist = appService.findByUserId(user.getIid());
			if(CollectionUtils.isEmpty(applist)){
				modelAndView=new ModelAndView("app/appuser/firstlogin");
			}else{
				int max = Configs.getConfigs().getCreateAppNum();
				boolean isCreate = (applist.size() < max);
				modelAndView=new ModelAndView("app/appuser/userinfo");
				modelAndView.addObject("applist", applist);
				modelAndView.addObject("isCreate", isCreate);
				int num = applist.size()/4;
				int s = applist.size()%4;
				if(s>0){
					num = num+1;
				}
				divHeight = num*130+200;
				//设立应用列表页面的div宽度
				if(applist.size()<=4){
					divWidth=applist.size()*150;
				} 
			}
		} else {
			App appEn = appService.findBySiteid(user.getSiteId());
			if(appEn==null || NumberUtil.getInt(appEn.getIid())==0 || NumberUtil.getInt(appEn.getModelType())==0){
				modelAndView=new ModelAndView("app/appuser/firstlogin");
			}else{ 
				String logoPath = StringUtil.getString("/web/" + "site"+appEn.getSiteId() + "/app/" 
						        + appEn.getIid() + "/iphone.png");
				if(StringUtil.isNotEmpty(logoPath)){
					logoPath = logoPath+"?"+StringUtil.getUUIDString();
				}
				appEn.setLogoPath(logoPath);
				applist.add(appEn); 
				int max = Configs.getConfigs().getCreateAppNum();
				boolean isCreate = (applist.size() < max);
				modelAndView = new ModelAndView("app/appuser/userinfo");
				modelAndView.addObject("applist", applist);
				modelAndView.addObject("isCreate", isCreate);
				int num = applist.size()/4;
				int s = applist.size()%4;
				if(s>0){
					num = num+1;
				}
				divHeight = num*130+200;
				//设立应用列表页面的div宽度
				if(applist.size()<=4){
					divWidth=applist.size()*150;
				} 
			}
		}
		modelAndView.addObject("jmpurl", Configs.getConfigs().getJmpUrl());
		modelAndView.addObject("divWidth", divWidth);
		modelAndView.addObject("divHeight", divHeight);
		modelAndView.addObject("username", user.getUsername());
		modelAndView.addObject("headurl", user.getHeadUrl());
		return modelAndView;
	}
	
	/**
	 * checkUserLogin
	 * @param user user
	 * @param session session
	 * @param randCode randCode
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "checkuser")
	@ResponseBody
	public String checkUserLogin(AppUser user, HttpSession session,
			@RequestParam(value = "randcode", required = false) String randCode) {
		Map<String, String> hm = new HashMap<String, String>(); 
		String rand = (String) session.getAttribute("app_rand");
		String error = "";
		String msg = "2";
		if (StringUtil.isEmpty(randCode) || StringUtil.isEmpty(rand) 
				|| !StringUtil.equals(rand.toLowerCase(), randCode.toLowerCase())) {
			session.setAttribute("app_rand", null);
			msg = "3";
			hm.put("msg", msg);
			hm.put("error", error);
			return JsonUtil.objectToString(hm);
		}
		AppUser user1 = appuserService.checkUserLogin(user);
		if(user1!=null){
			FrontUserSessionInfo .setCurrentUserInfo(user1);
			 msg = "1";
		}
		hm.put("msg", msg);
		hm.put("error", error);
		return JsonUtil.objectToString(hm);
	}
	
	/**
	 * registUser
	 * @param user user
	 * @param session session
	 * @param randCode randCode
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "registuser")
	@ResponseBody
	public String  registUser(AppUser user, HttpSession session,
			@RequestParam(value = "randcode", required = false) String randCode){
		AppUser user1 = appuserService.findByName(user.getUsername());
		String rand = (String) session.getAttribute("appregist_rand");
		String msg = "2";
		if (StringUtil.isEmpty(randCode) || StringUtil.isEmpty(rand) 
				|| !StringUtil.equals(rand.toLowerCase(), randCode.toLowerCase())) {
			session.setAttribute("appregist_rand", null);
			msg = "3";
			return msg;
		}
		if(user1 == null){
			user.setPwd(Md5Util.md5encode(user.getPwd()));
			Integer userId = appuserService.addUser(user);
			if(NumberUtil.getInt(userId)>0){
				msg = "1";
				return msg;
			}
			return msg;
		}else{
			return msg;
		}
	}
	 
	/**
	 * qq授权
	 * @param code code
	 * @param openid openid
	 * @param openkey openkey
	 * @param request request
	 * @param accessTokenqq accessTokenqq
	 * @param a a
	 * @return    设定参数 .
	 */
//	@RequestMapping("callbackqq")
//	public ModelAndView callBackQQ(String code, String openid, String openkey,
//			 HttpServletRequest request, String accessTokenqq, String a){ 
//		ModelAndView modelAndView = null; 
//		try {
//			if(!StringUtil.isEmpty(code)){
//				  OAuthV2 oauthQQ = new OAuthV2();
//				  oauthQQ.setAuthorizeCode(code);
//				  oauthQQ.setClientId(qqKey);
//				  oauthQQ.setClientSecret(qqSecret);
//				  oauthQQ.setRedirectUri(qqDomain+"?a="+a);
//				  oauthQQ.setOpenid(openid);
//				  oauthQQ.setOpenkey(openkey);
//				  OAuthV2Client.accessToken(oauthQQ);
//				  accessTokenqq = oauthQQ.getAccessToken(); 
//			}  
//			if(StringUtil.isEmpty(accessTokenqq)){
//				modelAndView = new ModelAndView(new RedirectView(
//						Configs.getConfigs().getJmpUrl()+"/appuser/index"));
//				return modelAndView;
//			}else{
//				UserAPI sapi = new UserAPI("2.a"); 
//		        OAuthV2 oauth = new OAuthV2();
//		        oauth.setClientId(qqKey);
//		        oauth.setClientSecret(qqSecret);
//		        oauth.setAccessToken(accessTokenqq);
//		        oauth.setOpenid(openid);
//		        String json = sapi.info(oauth, "json");
//		        
//		        JSONObject dataJson;
//				dataJson = new JSONObject(json);
//				JSONObject obj = dataJson.getJSONObject("data");
//				
//		        String head = obj.getString("head");
//		        if(!head.isEmpty()){
//		        	head = head + "/100";
//		        }
//		        String nick = obj.getString("nick");
//		        String [] a1 = a.split("_");
//		        if(!StringUtil.isEmpty(head)){
//					head = head.substring(0, head.lastIndexOf("/")) + "/180";
//				}   
//		        
//		        AppUser user = new AppUser();
//				user.setUsername(nick);
//				user.setUuid(openid);
//				user.setUserType(2);
//				if(StringUtil.isEmpty(head)){
//					head = Configs.getConfigs().getJmpUrl() + "/resources/app/images/head.png";
//				}
//				user.setHeadUrl(head);
//				String resulturl="../app/login.do?uuid="+openid+"&clienttype="+a1[1];
//				AppUser user1 = appuserService.findByUuid(openid);
//				if(user1 == null){
//					Integer userid = appuserService.addUser(user);
//					user.setIid(userid);
//					FrontUserSessionInfo .setCurrentUserInfo(user);
//				}else{
//					FrontUserSessionInfo .setCurrentUserInfo(user1);
//				}
//		    	modelAndView = new ModelAndView(new RedirectView(resulturl));
//			} 
//		} catch (Exception e) {
//			logger.error("callBackQQ error:", e);
//		}
//		return modelAndView;
//	}
//	 
//	
//	/**
//	 * callBackSina:sina授权.
//	 *
//	 * @param code code
//	 * @param request request
//	 * @param state state
//	 * @return    设定参数 .
//	*/
//	@RequestMapping("callbacksina")
//	public ModelAndView callBackSina(String code, HttpServletRequest request, String state){
//		ModelAndView modelAndView = null;  
//		try {
//			Oauth oauth = new Oauth();
//			String id = "";
//			String nick = "";
//			String head = "";
//			String accessTokensina = null;
//			if(!StringUtil.isEmpty(code)){
//				AccessToken accesstoken=oauth.getAccessTokenByCode(code);
//				accessTokensina=accesstoken.getAccessToken();
//				//accessTokensina = oauth.getAccessTokenByCode(code,
//				//		sinaKey, sinaSecret, sinaDomain).getAccessToken();
//			} 
//			if(StringUtil.isEmpty(accessTokensina)){
//				modelAndView = new ModelAndView(new RedirectView(
//						Configs.getConfigs().getJmpUrl()+"/appuser/index"));
//				return modelAndView;
//			}else{
//				Account  account=new Account(accessTokensina);
//				id = StringUtil.getString(account.getUid().get("uid"));
//				if(id != null){
//					Users users = new Users(accessTokensina);
//					nick = users.showUserById(id).getScreenName();
//					head = users.showUserById(id).getProfileImageUrl();
//					if(!StringUtil.isEmpty(head)){
//						head = head.replace("/50/", "/180/");
//					} 
//				    AppUser user = new AppUser();
//				    user.setUsername(nick);
//				    user.setUuid(id);
//				    user.setUserType(3);
//					if(StringUtil.isEmpty(head)){
//						head = Configs.getConfigs().getJmpUrl() + "/resources/app/images/head.png";
//					}
//					user.setHeadUrl(head); 
//					String resulturl="../app/login.do?uuid="+id;
//					AppUser user1 = appuserService.findByUuid(id);
//					if(user1 == null){
//						Integer userid = appuserService.addUser(user);
//						user.setIid(userid);
//						FrontUserSessionInfo .setCurrentUserInfo(user);
//					}else{
//						FrontUserSessionInfo .setCurrentUserInfo(user1);
//					}
//			    	modelAndView = new ModelAndView(new RedirectView(resulturl));
//					
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("callBackSina error:", e); 
//		}
//		
//		return modelAndView;
//	}
	
}