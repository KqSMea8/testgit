package com.hanweb.jmp.pack.controller.application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.MultipartFileInfo;
import com.hanweb.complat.constant.Settings;
import com.hanweb.complat.entity.User;
import com.hanweb.complat.exception.OperationException;

import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.StaticValues;
import com.hanweb.jmp.pack.dao.ClientDAO;
import com.hanweb.jmp.pack.entity.App;
import com.hanweb.jmp.pack.entity.AppUser;
import com.hanweb.jmp.pack.entity.Client;
import com.hanweb.jmp.pack.listener.FrontUserSessionInfo;
import com.hanweb.jmp.pack.service.AppService;
import com.hanweb.jmp.pack.service.ClientService;
import com.hanweb.jmp.pack.service.SiteService;
import com.hanweb.jmp.pack.service.UserService;
import com.hanweb.jmp.pack.util.ImageUtil;
import com.hanweb.jmp.sys.controller.sites.SiteFormBean;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.entity.sites.SiteSplash;
import com.hanweb.jmp.sys.service.sites.SiteSplashService;
import com.hanweb.jmp.util.CacheUtil;
import com.hanweb.jmp.util.QRCodeUtil;

@Controller
@RequestMapping("createapp")
public class AppController {
	
	/**
	 * appService
	 */
	@Autowired
	private AppService appService;
	
	/**
	 * clientService
	 */
	@Autowired
	private ClientService clientService;
	
	/**
	 * userService
	 */
	@Autowired
	UserService userService;
	
	/**
	 * siteService
	 */
	@Autowired
	private SiteService siteService;
	
	/**
	 * clientDAO
	 */
	@Autowired
	private ClientDAO clientDAO;
	
	/**
	 * siteSplashService
	 */
	@Autowired
	private SiteSplashService siteSplashService;
	
	/**
	 * logger
	 */
	private final Log logger = LogFactory.getLog(getClass());
	 
	/**
	 * showFirst:打包第一步
	 * @param appId appId
	 * @param response response
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "tocreatefirst")
	public ModelAndView showFirst(Integer appId, HttpServletResponse response) {
		AppUser user = FrontUserSessionInfo.getCurrentUserInfo();
		ModelAndView modelAndView;
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
		String username = FrontUserSessionInfo.getCurrentUserInfo().getUsername();
		modelAndView = new ModelAndView("jmp/pack/application/create_first");
		int createAppType=Configs.getConfigs().getCreateAppType(); 
		if(createAppType==1){
			modelAndView = new ModelAndView("jmp/pack/application/create_firstback");
		}
		modelAndView.addObject("url", "tocreatesecond.do");
		modelAndView.addObject("jmpurl", Configs.getConfigs().getJmpUrl());
		modelAndView.addObject("username", user.getUsername());
		HttpSession session = SpringUtil.getRequest().getSession();
		if(session.getAttribute("ssp")!=null){
			String ssp = (String) session.getAttribute("ssp");
			modelAndView.addObject("ssp", ssp);
		}else{
			modelAndView.addObject("ssp", "");
		}
		if(session.getAttribute("iconurl")!=null){
			String iconurl = (String) session.getAttribute("iconurl");
			modelAndView.addObject("iconurl", iconurl);
		}else{
			modelAndView.addObject("iconurl", "");
		} 
		int siteid= NumberUtil.getInt(user.getSiteId());
		if(siteid>0 || NumberUtil.getInt(appId)>0){
			App app1 = null;
			if(siteid>0){
			   app1 = appService.findBySiteid(siteid);
			}else{
			   app1 = appService.findById(appId);
			}
			if(app1!=null && app1.getName()!=null){
				String spic = app1.getName().substring(0, 1);
				modelAndView.addObject("pic", spic);
			}
			session.setAttribute(username, app1);
			modelAndView.addObject("app", app1);
		}else if(session.getAttribute(username)!=null){
			App app = (App) session.getAttribute(username);
			String pic = (String) session.getAttribute("pic");
			modelAndView.addObject("app", app);
			modelAndView.addObject("pic", pic);
		}
		session.setAttribute("appid", appId);
		return modelAndView;
	}
	 
	/**
	 * showSecond:(打包第二步)
	 * @param app app
	 * @param ssp ssp
	 * @param pic pic
	 * @param iconurl iconurl
	 * @param response response
	 * @return    设定参数 .
	*/
	@RequestMapping(value = "tocreatesecond")
	public ModelAndView showSecond(App app, String ssp, String pic, String iconurl, HttpServletResponse response) {
		AppUser user = FrontUserSessionInfo.getCurrentUserInfo();
		ModelAndView modelAndView;
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
		String username = user.getUsername();
		modelAndView = new ModelAndView("jmp/pack/application/create_second");
		if(app.getName()!=null){
			session.setAttribute("pic", pic);
			session.setAttribute("ssp", ssp);
			session.setAttribute("iconurl", iconurl);
			session.setAttribute(username, app);
		}
		modelAndView.addObject("username", user.getUsername());
		modelAndView.addObject("jmpurl", Configs.getConfigs().getJmpUrl());
		if(session.getAttribute(username)!=null){
			App app1 = (App) session.getAttribute(username);
			modelAndView.addObject("app", app1);
		}
		modelAndView.addObject("url", "tocreatethird.do");
		modelAndView.addObject("siteid", NumberUtil.getInt(user.getSiteId()));
		int ismodify=0;
		int iid=NumberUtil.getInt(app.getIid());
		if(iid>0){
			App appEn=appService.findById(iid);
			if(StringUtil.isNotEmpty(appEn.getLogoIcon())){
				ismodify=1;
			}
		}
		modelAndView.addObject("ismodify", StringUtil.getString(ismodify));
		return modelAndView;
	}
	 
	/**
	 * showThird:打包第三步
	 * @param app app
	 * @param response response
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "tocreatethird")
	public ModelAndView showThird(App app, HttpServletResponse response) {
		AppUser user = FrontUserSessionInfo.getCurrentUserInfo();
		ModelAndView modelAndView;
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
		String username = user.getUsername();
		HttpSession session = SpringUtil.getRequest().getSession();
		if(session.getAttribute(username)!=null){
			App app1 = (App) session.getAttribute(username);
			app.setName(app1.getName());
			app.setSpec(app1.getSpec());
			app.setLogoPath(app1.getLogoPath());
			app.setSplashIcon(app1.getSplashIcon());
		}
		session.setAttribute(username, app);
		modelAndView = new ModelAndView("jmp/pack/application/create_third");
		modelAndView.addObject("url", "appdetail.do");
		modelAndView.addObject("app", app);
		modelAndView.addObject("username", user.getUsername());
		modelAndView.addObject("jmpurl", Configs.getConfigs().getJmpUrl());
		return modelAndView;
	}
	
	/**
	 * createSite
	 * @param app app
	 * @param response response
	 * @return String
	 * @throws OperationException    设定参数 .
	 */
	@RequestMapping(value = "createsite")
	@ResponseBody
	public String createSite(App app, HttpServletResponse response) throws OperationException{
		String msg = "0";
		AppUser user = FrontUserSessionInfo.getCurrentUserInfo();
		HttpSession session = SpringUtil.getRequest().getSession();
		session.removeAttribute("ssp");
		session.removeAttribute("iconurl");
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
		String username = user.getUsername();
		//初始化网站
		boolean success = true;
		Integer siteId = 0;
		Integer appid = app.getIid();
		if(Configs.getConfigs().getCreateAppType() == 0) {
			siteId = appService.addComponentFront(app, user, session);
			if(NumberUtil.getInt(siteId)>0){
				Integer appId = 0;
				if(NumberUtil.getInt(app.getIid())<=0){
					app.setSiteId(siteId);
					app.setUserId(user.getIid());
					app.setCreater(user.getUsername());
					app.setCreateTime(new Date());
					appId = appService.addApp(app);
				}
				//拷贝应用图标
				if(NumberUtil.getInt(appid)>0){
					appId = appid;
				}
				if(NumberUtil.getInt(app.getLogoPath())<=0){//自动上传图标
					String plogoPath="";
					if(NumberUtil.getInt(appid)>0){
						plogoPath = appService.findById(appid).getLogoPath();
					}
					if(!plogoPath.equals(app.getLogoPath())){
						String des = BaseInfo.getRealPath() + "/web/" 
						+ "site"+siteId + "/app/" + appId + "/images/logo.png";
						File logoFile = new File(app.getLogoPath());
						File desFile = new File(des);
						FileUtil.copyFile(logoFile, desFile); 
						app.setLogoPath("/web/" +"site"+ siteId + "/app/" + appId + "/images/logo.png");
					}
				}else if(NumberUtil.getInt(app.getLogoPath())>0){//默认图标
					ImageUtil img = new ImageUtil();
					String targetImg = BaseInfo.getRealPath()
						             + "/resources/app/images/stepone/"
						             + NumberUtil.getInt(app.getLogoPath())+".png";
					String sourceImg = Settings.getSettings().getFileTmp();
					String result = "";
					String pic = (String) session.getAttribute("pic");
					if (pic!=null){
						result = img.pressText(targetImg, sourceImg, pic, 1);
					}else if(app.getName()!=null){
						result = img.pressText(targetImg, sourceImg, app.getName(), 1);
					}else{
						result = img.pressText(targetImg, sourceImg, "汉", 1);
					}
					session.removeAttribute("pic");
					String des = BaseInfo.getRealPath() + "/web/" 
						       + "site"+siteId + "/app/" + appId + "/images/logo.png";
					File logoFile = new File(sourceImg + result);
					File desFile = new File(des);
					FileUtil.copyFile(logoFile, desFile);
					app.setLogoPath("/web/" +"site"+ siteId + "/app/" + appId + "/images/logo.png");
				}
				//拷贝logo图片
				if(StringUtil.isNotEmpty(app.getLogoIcon()) 
						&& app.getLogoIcon().indexOf("tempfile")>-1){
					String sourceicon = BaseInfo.getRealPath()+app.getLogoIcon();
					String des1 = BaseInfo.getRealPath() 
					            + "/web/" + "site"+siteId + "/app/" + appId + "/images/logoIcon.png";
					FileUtil.copyFile(sourceicon, des1);
					app.setLogoIcon("/web/" + "site"+siteId + "/app/" + appId + "/images/logoIcon.png");
				}
				//拷贝开机图标
				if(NumberUtil.getInt(app.getSplashIcon())>0){
					String splashicon = BaseInfo.getRealPath() + "/resources/app/images/stepone/"
						              + NumberUtil.getInt(app.getSplashIcon())+".png";
					String des1 = BaseInfo.getRealPath() + "/web/" + "site"+siteId + "/app/" + appId + "/images/splashIcon.png";
					File splashIconFile = new File(splashicon);
					File desFile1 = new File(des1);
					FileUtil.copyFile(splashIconFile, desFile1);
					app.setSplashIcon("/web/" + "site"+siteId + "/app/" + appId + "/images/splashIcon.png");	
				}else if(NumberUtil.getInt(app.getSplashIcon())<=0){
					String psplashIcon = "";
					if(NumberUtil.getInt(appid)>0){
						psplashIcon = appService.findById(appid).getSplashIcon();
					}
					if(!psplashIcon.equals(app.getSplashIcon())){
						String des1 = BaseInfo.getRealPath() + "/web/" + "site"+siteId + "/app/" + appId + "/images/splashIcon.png";
						File splashIconFile = new File(app.getSplashIcon());
						File desFile1 = new File(des1);
						FileUtil.copyFile(splashIconFile, desFile1);
						app.setSplashIcon("/web/" + "site"+siteId + "/app/" + appId + "/images/splashIcon.png");
					}
				}
				//生成应用二维码
				if(NumberUtil.getInt(appid)<=0||app.getQrcodePath()==null){
					String jmpUrl=Configs.getConfigs().getJmpUrl();
					if(StringUtil.isNotEmpty(jmpUrl)){
						String contentPath = jmpUrl + "/web/"+ "site"+siteId +"/app/" + appId + "/index.html";
				        String imgPath = "/web/"+ "site"+siteId + "/app/" + appId + "/qcode.png";
				        FileUtil.isDirExsit(new File(BaseInfo.getRealPath() +"/web/"+ "site"+siteId + "/app/" + appId), true);
				        int result = QRCodeUtil.createQRCode(contentPath, BaseInfo.getRealPath() + imgPath, null);	
						if(result == 0){
							app.setQrcodePath(imgPath);
						} 
						contentPath = BaseInfo.getRealPath() + "/web/"+ "site"+siteId +"/app/" 
						            + appId + "/index.html";
					}
				}
				//更新应用属性
				if(NumberUtil.getInt(appid)>0){
					App app1 = appService.findById(appid);
					app1.setName(app.getName());
					app1.setSpec(app.getSpec());
					app1.setLogoPath(app.getLogoPath());
					app1.setSplashIcon(app.getSplashIcon());
					app1.setModelType(app.getModelType());
					app1.setLogoIcon(app.getLogoIcon());
					app = app1;
					Site site = siteService.findByIid(app1.getSiteId());
					site.setName(app.getName());
					SiteFormBean siteBean = new SiteFormBean();
					siteBean.setSite(site);
					User user1 = userService.findByIid(user.getIid());
					siteBean.setUser(user1);
					siteService.modify(siteBean);
					appService.updateApp(app1);
					siteId = app1.getSiteId();
					session.setAttribute(username, app1);
				}else{
					app.setUuid(StringUtil.getUUIDString());
					app.setIid(appId); 
					appService.updateApp(app);
					siteId = app.getSiteId();
					session.setAttribute(username, app);
				}
				List<Client> clientList = clientDAO.findByIds(app.getIid());
				int andcLiiD = 0 ;
				int iphoneCliId = 0 ;
				int ipadCliId = 0;
				Client client = new Client();
				//新增的时候初始化
				if(CollectionUtils.isEmpty(clientList)){
					client.setAppName(app.getName());
					client.setVersion("1.0");
					client.setClientType(2);
					client.setCreateTime(new Date());
					client.setAppId(app.getIid());
					client.setStatus(1);
					client.setSiteId(siteId);
					iphoneCliId = clientDAO.insert(client);
					if(iphoneCliId > 0){
						client.setClientType(3);
						andcLiiD = clientDAO.insert(client); 
					}else{
						return msg;
					}
					if(andcLiiD > 0){
						client.setClientType(4);
						ipadCliId = clientDAO.insert(client); 
					}else{
						return msg;
					}
					
					if(ipadCliId<=0){
						return msg;
					}
					appService.generateApp(app, andcLiiD, iphoneCliId, ipadCliId, client.getVersion());
				}else{//修改应用
					String maxVersion = appService.findNewVersion(clientList);
					String version = StringUtil.getString(NumberUtil.getDouble(maxVersion)+1);
					client.setAppName(app.getName());
					client.setVersion(version);
					client.setClientType(2);
					client.setCreateTime(new Date());
					client.setAppId(app.getIid());
					client.setStatus(1);
					client.setSiteId(siteId);
					iphoneCliId = clientDAO.insert(client);
					if(iphoneCliId > 0){
						client.setClientType(3);
						andcLiiD = clientDAO.insert(client);
					}else{
						return msg;
					}
					if(andcLiiD > 0){
						client.setClientType(4);
						ipadCliId = clientDAO.insert(client); 
					}else{
						return msg;
					}
					
					if(ipadCliId<=0){
						return msg;
					}
					appService.generateApp(app, andcLiiD, iphoneCliId, 
							ipadCliId, client.getVersion());
				}
				//初始化成功
				msg = "1";
				return msg;
			}else{
				return msg;
			}
		} else { // 后台打包
			int iid = app.getIid();
			App appPre = appService.findById(NumberUtil.getInt(iid));
			siteId = appPre.getSiteId();
			if(StringUtil.isEmpty(appPre.getLogoIcon())) {
				success = appService.addComponentBack(app, user, session);
			} else {
				File splashIconFile =null;
				//拷贝开机图标
				if(NumberUtil.getInt(app.getSplashIcon())>0){
					String splashicon = BaseInfo.getRealPath()
						              + "/resources/app/images/stepone/"+NumberUtil.getInt(app.getSplashIcon())+".png";
					String des1 = BaseInfo.getRealPath() 
					            + "/web/" + "site"+siteId + "/app/" + appid + "/images/splashIcon.png";
					splashIconFile = new File(splashicon);
					File desFile1 = new File(des1);
					FileUtil.copyFile(splashIconFile, desFile1);
					app.setSplashIcon("/web/" + "site"+siteId + "/app/" + appid + "/images/splashIcon.png");
					
				}else if(NumberUtil.getInt(app.getSplashIcon())<=0){
					String psplashIcon="";
					if(NumberUtil.getInt(appid)>0){
						psplashIcon = appService.findById(appid).getSplashIcon();
					}
					if(!psplashIcon.equals(app.getSplashIcon())){
						String des1 = BaseInfo.getRealPath() + "/web/" 
							        + "site"+siteId + "/app/" + appid + "/images/splashIcon.png";
						splashIconFile = new File(app.getSplashIcon());
						File desFile1 = new File(des1);
						FileUtil.copyFile(splashIconFile, desFile1);
						app.setSplashIcon("/web/" + "site"+siteId + "/app/" + appid + "/images/splashIcon.png");
					}
				}
				String des1 ="";
				//splash图复制
				if(splashIconFile!=null && splashIconFile.exists()){
					SiteSplash sitePlash = siteSplashService.findBySiteId(siteId);
					des1 = BaseInfo.getRealPath() + "/web/site" + siteId + "/site/site_iphone1.jpg";
					File desFile1 = new File(des1);
					FileUtil.copyFile(splashIconFile, desFile1);
					des1 = BaseInfo.getRealPath() + "/web/site" + siteId + "/site/site_android1.jpg";
					desFile1 = new File(des1);
					FileUtil.copyFile(splashIconFile, desFile1);
					des1 = BaseInfo.getRealPath() + "/web/site" + siteId + "/site/site_ipad1.jpg";
					desFile1 = new File(des1);
					FileUtil.copyFile(splashIconFile, desFile1);
					sitePlash.setFirstAndroidPic("/web/site" + siteId + "/site/site_android1.jpg");
					sitePlash.setFirstIpadPic("/web/site" + siteId + "/site/site_ipad1.jpg");
					sitePlash.setFirstIphonePic("/web/site" + siteId + "/site/site_iphone1.jpg");
					siteSplashService.modify(sitePlash);
					CacheUtil.removeKey(StaticValues.CACHE_SITE, StringUtil.getString(user.getSiteId())); 
				}
				//拷贝logo图片
				if(StringUtil.isNotEmpty(app.getLogoIcon()) && app.getLogoIcon().indexOf("tempfile")>-1){
					String sourceicon = BaseInfo.getRealPath()+app.getLogoIcon();
				    des1 = BaseInfo.getRealPath() + "/web/" + "site"+siteId + "/app/" + appid + "/images/logoIcon.png";
					FileUtil.copyFile(sourceicon, des1);
					appPre.setLogoIcon("/web/" + "site"+siteId + "/app/" + appid + "/images/logoIcon.png");
				} 
				if(appPre.getModelType() != app.getModelType()) {
					CacheUtil.removeKey(StaticValues.CACHE_REGION, "first_" + NumberUtil.getInt(siteId));
				}
				appPre.setModelType(app.getModelType());
				appPre.setSpec(app.getSpec());
				success=appService.updateApp(appPre); 
				session.setAttribute(username, appPre);
			}
			if(success){ 
				msg = "1";  
			} 
			return msg; 
		}
	}
	 
	/**
	 * showDetail:应用明细页
	 * @param app app
	 * @param response response
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "appdetail")
	public ModelAndView  showDetail(App app, HttpServletResponse response){
		AppUser user = FrontUserSessionInfo.getCurrentUserInfo();
		ModelAndView modelAndView;
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
		if(app!=null && NumberUtil.getInt(app.getIid())>0){
			app=appService.findById(app.getIid());
		}else if(!session.getAttribute(user.getUsername()).equals("")){
			app = (App) session.getAttribute(user.getUsername());
		}		
		Integer iphoneStatus = 3;
		Integer andriodStatus =3;
		Integer ipadStatus =3;
		List<Client> iphoneList = clientService.findByAppIdAndClientType(app.getIid(), 2);
		List<Client> andList = clientService.findByAppIdAndClientType(app.getIid(), 3);
		List<Client> ipadList =  clientService.findByAppIdAndClientType(app.getIid(), 4);
		String iphoneVersion = "";
		String andriodVersion = "";
		String ipadVersion = "";
		String andriodUrl = "";
		String iphoneUrl = "";
		String ipadUrl = "";
		if(CollectionUtils.isEmpty(iphoneList)){
			iphoneStatus = 3;
		}else{
			Client iphoneClient = iphoneList.get(0);
			if(iphoneClient == null){
				iphoneStatus = 1;
			}else{
				iphoneStatus = iphoneClient.getStatus();
				iphoneVersion = iphoneClient.getVersion();
				iphoneUrl = iphoneClient.getIphoneUrl();
			}
		}
		if(CollectionUtils.isEmpty(andList)){
			andriodStatus = 3;
		}else{
			Client androidClient = andList.get(0);
			if(androidClient == null){
				andriodStatus = 1;
			}else{
				andriodStatus = androidClient.getStatus();
				andriodVersion = androidClient.getVersion();
				andriodUrl = androidClient.getAndroidUrl();
			}
		}
		if(CollectionUtils.isEmpty(ipadList)){
			ipadStatus = 3;
		}else{
			Client ipadClient = ipadList.get(0);
			if(ipadClient == null){
				ipadStatus = 1;
			}else{
				ipadStatus = ipadClient.getStatus();
				ipadVersion = ipadClient.getVersion();
				ipadUrl = ipadClient.getIpadUrl();
			}
		}
		modelAndView = new ModelAndView("jmp/pack/application/appdetail");
		if(andriodStatus==1 || iphoneStatus==1 || ipadStatus==1){
			modelAndView.addObject("flag", "1");
			modelAndView.addObject("qrcode", "1");
			modelAndView.addObject("dabaostate", "正在打包中....");
		}else if(andriodStatus==2 && iphoneStatus==2 && ipadStatus==2){
			modelAndView.addObject("dabaostate", "打包成功");
			modelAndView.addObject("qrcode", "2");
			Site site = siteService.findSiteByIid(NumberUtil.getInt(app.getSiteId()));
			if(NumberUtil.getInt(site.getUserId())>0){
				modelAndView.addObject("userid", site.getUserId());
			}
		}else{
			modelAndView.addObject("qrcode", "3");
			modelAndView.addObject("dabaostate", "打包失败");
			Site site = siteService.findSiteByIid(NumberUtil.getInt(app.getSiteId()));
			if(NumberUtil.getInt(site.getUserId())>0){
				modelAndView.addObject("userid", site.getUserId());
			}
		}
		modelAndView.addObject("iphoneStatus", iphoneStatus);
		modelAndView.addObject("andriodStatus", andriodStatus);
		modelAndView.addObject("ipadStatus", ipadStatus);
		//地址后面带上uuid，防止出现缓存
		app.setQrcodePath(app.getQrcodePath());
		String logoPath = StringUtil.getString(app.getLogoPath());
		if(StringUtil.isNotEmpty(logoPath)){
			logoPath = logoPath+"?"+StringUtil.getUUIDString();
		}
		app.setLogoPath(logoPath);
		if(Configs.getConfigs().getCreateAppType()==0){
			modelAndView.addObject("message", "正在打包过程中，打包过程大概需要五分钟，请您过段时间手动刷新本页面，谢谢配合！");
		} else {
			modelAndView.addObject("message", "二维码不能正常扫描，请联系管理员！");
		}
		modelAndView.addObject("jmpurl", Configs.getConfigs().getJmpUrl());
		modelAndView.addObject("app", app);
		modelAndView.addObject("andriodVersion", andriodVersion);
		modelAndView.addObject("iphoneVersion", iphoneVersion);
		modelAndView.addObject("ipadVersion", ipadVersion);
		modelAndView.addObject("andriodUrl", Configs.getConfigs().getJmpUrl()+andriodUrl);
		modelAndView.addObject("iphoneIpa", Configs.getConfigs().getJmpUrl()+iphoneUrl);
		modelAndView.addObject("ipadIpa", Configs.getConfigs().getJmpUrl()+ipadUrl);
		return modelAndView;
	}
	
	/**
	 * showManage
	 * @param siteId siteId
	 * @param response response
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "appmanage")
	public ModelAndView showManage(Integer siteId, HttpServletResponse response){
		ModelAndView modelAndView;
		AppUser user1 = FrontUserSessionInfo.getCurrentUserInfo();
		if(user1 == null){
			//用户未登录直接跳转到登录页面
			response.setCharacterEncoding("UTF-8");
			try {
				response.sendRedirect(Configs.getConfigs().getJmpUrl()+"/app/index.do");
			} catch (IOException e) {
				logger.error("sendRe error", e);
			}    
			return null; 
		}
		Site site = siteService.findSiteByIid(NumberUtil.getInt(siteId));
		if(site.getUserId()!=null){
			User user = userService.findByIid(site.getUserId());
			try {
				response.sendRedirect(Configs.getConfigs().getJmpUrl()+"/ssologin.do?username="
						+URLEncoder.encode(user.getLoginName(), "UTF-8")
						+"&password="+user.getPwd());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}else{
			modelAndView = new ModelAndView("jmp/pack/manage/manageregist");
			modelAndView.addObject("username", user1.getUsername());
		}
		modelAndView.addObject("siteId", siteId);
		modelAndView.addObject("jmpurl", Configs.getConfigs().getJmpUrl());
		return modelAndView;
	}
	
	/**
	 * 后台管理员注册   返回1 注册成功   2用户名已存在  3 验证码错误   4注册失败
	 * @param user user
	 * @param session session
	 * @return String
	 */
	@RequestMapping(value = "registmanage")
	@ResponseBody
	public String registMana(User user, HttpSession session){
		User user1 = userService.findByLoginName(user.getLoginName());
		String msg = "2";
		if(user1 == null){
			boolean success = appService.addUser(user);
			if(success){
				msg = "1";
				return msg;
			}else{
				msg = "4";
				return msg;
			}
		}else{
			return msg;
		}
	}
	
	/**
	 * toRepwd
	 * @param userid userid
	 * @param siteId siteId
	 * @param response response
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "torepwd")
	public ModelAndView toRepwd(Integer userid, 
			Integer siteId, HttpServletResponse response){
		AppUser user1 = FrontUserSessionInfo.getCurrentUserInfo();
		if(user1 == null){
			//用户未登录直接跳转到登录页面
			response.setCharacterEncoding("UTF-8");
			try {
				response.sendRedirect(Configs.getConfigs().getJmpUrl()+"/app/index.do");
			} catch (IOException e) {
				logger.error("sendRe error", e);
			}    
			return null; 
		}
		ModelAndView modelAndView = new ModelAndView("jmp/pack/manage/repwd");
		User user = userService.findByIid(userid);
		modelAndView.addObject("username", user.getLoginName());
		modelAndView.addObject("url", "repwdsubmit.do");
		modelAndView.addObject("siteId", siteId);
		modelAndView.addObject("jmpurl", Configs.getConfigs().getJmpUrl());
		modelAndView.addObject("username", user1.getUsername());
		return modelAndView;
		
	}
	
	/**
	 * toManageLogin
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "tomanagelogin")
	public ModelAndView toManageLogin(Integer siteId){
		ModelAndView modelAndView = new ModelAndView("jmp/pack/manage/managelogin");
		modelAndView.addObject("siteId", siteId);
		modelAndView.addObject("url", "checkmanageuser.do");
		return modelAndView;
		
	}
	/**
	 * Repwd
	 * @param user user
	 * @param rpwd rpwd
	 * @param siteId siteId
	 * @param session session
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "repwd")
	@ResponseBody
	public String repwd(User user, String rpwd, Integer siteId, HttpSession session){
		String msg = "2";
		Site site = siteService.findSiteByIid(siteId);
		User user1 = userService.findByIid(site.getUserId());
		String password = user.getPwd();
		if(StringUtils.equals(password, user1.getPwd()) && StringUtils.equals(user1.getLoginName(), user.getLoginName())){
			user1.setPwd(rpwd);
			try {
				userService.modify(user1);
				 msg = "1";
				 return msg;
			} catch (OperationException e) {
				e.printStackTrace();
				msg="4";
				return msg;
			}
		}
		return msg;
	}
	 
	/**
	 * 后台管理员登陆 返回1 登陆成功 2 登陆失败 3验证码有误
	 * @param user user
	 * @param session session
	 * @param randCode randCode
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "checkmanageuser")
	@ResponseBody
	public String checkManageUser(User user, HttpSession session,
			@RequestParam(value = "randcode", required = false) String randCode){
		String rand = (String) session.getAttribute("managelogin_rand");
		String msg = "2";
		if (StringUtil.isEmpty(randCode) || StringUtil.isEmpty(rand) 
				|| !StringUtil.equals(rand.toLowerCase(), randCode.toLowerCase())) {
			session.setAttribute("managelogin_rand", null);
			msg = "3";
			return msg;
		}
		Site site = siteService.findSiteByIid(user.getSiteId());
		User user1 = userService.findByIid(site.getUserId());
		String password = user.getPwd();
		if(StringUtils.equals(password, user1.getPwd())
				&&StringUtils.equals(user1.getLoginName(), user.getLoginName())){
			 msg = "1";
			 return msg;
		}
		return msg;
	}
	
	/**
	 * addAppPic
	 * @param siteid siteid
	 * @param iconUpload iconUpload
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "appadd")
	@ResponseBody
	public String addAppPic(Integer siteid, MultipartFile iconUpload) { 
		Map<String, String> hm = new HashMap<String, String>(); 
		String error = "";
		MultipartFileInfo info = MultipartFileInfo.getInstance(iconUpload); 
		String uuid = StringUtil.getUUIDString();
		File filePath = new File(Settings.getSettings().getFileTmp()+uuid+"."+info.getFileType());
		ControllerUtil.writeMultipartFileToFile(filePath, iconUpload);
		String filePath1 = Settings.getSettings().getFileTmp()+uuid+"."+info.getFileType();
		String filePath2 = Configs.getConfigs().getJmpUrl()
		                 + "/tempfile/"+uuid+"."+info.getFileType();
		BufferedImage imgBuf = null;
		try {
			imgBuf = ImageIO.read(filePath);
			if(imgBuf.getWidth()==114&&imgBuf.getHeight()==114){
				 hm.put("info", filePath1);
				 hm.put("pimg", filePath2);
				 hm.put("error", error);
			}else{
				hm.put("info", "");
				hm.put("pimg", "");
				hm.put("error", "图片格式不正确，请上传114*114的图片！");
			}
		} catch (IOException e) {
			hm.put("info", "");
			hm.put("pimg", "");
			hm.put("error", "图片格式不正确，请上传114*114的图片！");
			e.printStackTrace();
		}
		 //ImageUtil.scaleMiniature(filePath, distinctPath
	       //  		, 100, 100);
		return JsonUtil.objectToString(hm);
	}
	
	/**
	 * addAppSplash 
	 * @param siteid siteid
	 * @param splashUpload splashUpload
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "appadd1")
	@ResponseBody
	public String addAppSplash(Integer siteid, MultipartFile splashUpload) { 
		Map<String, String> hm = new HashMap<String, String>(); 
		String error = "";
		MultipartFileInfo info = MultipartFileInfo.getInstance(splashUpload); 
		String uuid = StringUtil.getUUIDString();
		File filePath = new File(Settings.getSettings().getFileTmp()+ uuid +"."+info.getFileType());
		ControllerUtil.writeMultipartFileToFile(filePath, splashUpload);
		String filePath1 = Settings.getSettings().getFileTmp()+uuid+"."+info.getFileType();
		String filePath2 = Configs.getConfigs().getJmpUrl()
		                 + "/tempfile/"+uuid+"."+info.getFileType();
		if(!"jpg".equals(StringUtil.getString(info.getFileType()))){
			hm.put("info", "");
			hm.put("splash", "");
			hm.put("error", "图片格式不正确，只能上传jpg格式的图片！");
			return JsonUtil.objectToString(hm);
		}
		BufferedImage imgBuf = null;
		try {
			imgBuf = ImageIO.read(filePath);
			if(imgBuf.getWidth()==640 && imgBuf.getHeight()==1136){
				 hm.put("info", filePath1);
				 hm.put("splash", filePath2);
				 hm.put("error", error);
			}else{
				hm.put("info", "");
				hm.put("splash", "");
				hm.put("error", "图片大小不正确，请上传640*1136的图片！");
			}
		} catch (IOException e) {
			hm.put("info", "");
			hm.put("splash", "");
			hm.put("error", "图片大小不正确，请上传640*1136的图片！");
			e.printStackTrace();
		}
		 //ImageUtil.scaleMiniature(filePath, distinctPath
	       //  		, 100, 100);
		return JsonUtil.objectToString(hm);
	}
	
	/**
	 * addAppSplash
	 * @param siteid siteid
	 * @param logoUpload logoUpload
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "appadd2")
	@ResponseBody
	public String addLogoSplash(Integer siteid, MultipartFile logoUpload) { 
		Map<String, String> hm = new HashMap<String, String>(); 
		String error = "";
		MultipartFileInfo info = MultipartFileInfo.getInstance(logoUpload); 
		String uuid = StringUtil.getUUIDString();
		File filePath = new File(Settings.getSettings().getFileTmp()+ uuid +"."+info.getFileType());
		if(!"png".equals(StringUtil.getString(info.getFileType()))){
			hm.put("info", "");
			hm.put("splash", "");
			hm.put("error", "图片格式不正确，只能上传png格式的图片！");
			return JsonUtil.objectToString(hm);
		}
		ControllerUtil.writeMultipartFileToFile(filePath, logoUpload); 
		BufferedImage imgBuf = null;
		try {
			imgBuf = ImageIO.read(filePath);
			if(imgBuf.getWidth()==522 && imgBuf.getHeight()==80){
				imgBuf = ImageIO.read(filePath); 
				hm.put("info", "/tempfile/"+uuid+"."+info.getFileType());
				hm.put("splash", "");
				hm.put("error", error);
			}else{
				hm.put("info", "");
				hm.put("splash", "");
				hm.put("error", "图片大小不正确，请上传522*80的图片！");
			}  
		} catch (IOException e) {
			hm.put("info", "");
			hm.put("splash", "");
			hm.put("error", "图片大小不正确，请上传522*80的图片！");
			e.printStackTrace();
		}
		 //ImageUtil.scaleMiniature(filePath, distinctPath
	       //  		, 100, 100);
		return JsonUtil.objectToString(hm);
	}
	
}