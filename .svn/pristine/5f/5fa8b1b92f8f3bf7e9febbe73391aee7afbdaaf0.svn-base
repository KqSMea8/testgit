package com.hanweb.complat.controller.interfaces;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.service.AccessLogService;
import com.hanweb.complat.service.BanListService;
import com.hanweb.complat.service.LdapService;
import com.hanweb.complat.service.UserService;
import com.hanweb.sso.ldap.util.MD5;

/**
 * SSO接口
 * 
 * @author wt
 * 
 */
@Controller
@RequestMapping(value = "interface/ldap")
public class LdapController {

	@Autowired
	private LdapService ldapService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BanListService banListService;
	
	@Autowired
	private AccessLogService accessLogService;

	protected Log logger = LogFactory.getLog(getClass());

	/**
	 * 单点登录接收接口
	 * 
	 * @param formBean
	 * @return
	 */
	@RequestMapping(value = "receive")
	public ModelAndView receive(
			@RequestParam(value = "parameters", required = false) String parameters) {
		if (!BaseInfo.isSso()) {
			logger.info("系统未开启SSO，不能被接入");
			return null;
		}
		if (StringUtil.isEmpty(parameters)) {
			logger.error("参数为空");
			return null;
		}
		String xmlPath = ldapService.getXmlFile("0");
		String xmlContent = FileUtil.readFileToString(xmlPath);
		Document document = Jsoup.parse(xmlContent, "", Parser.xmlParser());
		String enckey = document.select("ldap>enckey").text();
		if (enckey == null || "".equals(enckey)) {
			logger.error("秘钥为空");
			return null;
		}
		String encrypttype = document.select("ldap>encrypttype").text();
		MD5 md5 = new MD5();
		parameters = md5.decrypt(parameters, enckey);
		logger.debug(parameters);
		Map<String,String>  jsonObject = JsonUtil.StringToObject(parameters, Map.class);

		ReceiveFormBean formBean = new ReceiveFormBean();
		String state = jsonObject.get("state"); // 操作状态位
		// 判断状态位是否正确
		if ((!state.equals("C")) && (!state.equals("D"))) {
			logger.error("状态位不正确");
			return null;
		} else {
			formBean.setState(state);
		}
		if (jsonObject.containsKey("email")) {
			formBean.setEmail(jsonObject.get("email"));
		}
		if (jsonObject.containsKey("mobile")) {
			formBean.setMobile(jsonObject.get("mobile"));
		}
		String t_name = "";// 实名
		if (jsonObject.containsKey("t_name")) {// 实名
			t_name = jsonObject.get("t_name");
		}
		// 解密姓名
		if (encrypttype.equals("1")) {
			t_name = md5.decrypt(t_name, enckey);
		} else if (encrypttype.equals("2")) {
			t_name = md5.decryptMB(t_name, enckey);
		}
		formBean.setT_name(t_name);
		if (jsonObject.containsKey("address")) {
			formBean.setAddress(jsonObject.get("address"));
		}
		if (jsonObject.containsKey("sex")) {
			formBean.setSex(jsonObject.get("sex"));
		}
		if (jsonObject.containsKey("result")) {
			formBean.setResult(jsonObject.get("result"));
		}
		if (jsonObject.containsKey("loginuser")) {
			formBean.setLoginuser(jsonObject.get("loginuser"));
		}
		if (jsonObject.containsKey("loginpass")) {
			formBean.setLoginpass(jsonObject.get("loginpass"));
		}
		if (jsonObject.containsKey("domainname")) {
			formBean.setDomainname(jsonObject.get("domainname"));
		}
		if (jsonObject.containsKey("appid")) {
			formBean.setAppid(jsonObject.get("appid"));
		}
		String web = ""; // 内网 0 or 外网 1
		if (jsonObject.containsKey("web")) {
			web = jsonObject.get("web");
		}
		if (!"1".equals(web)) { // 不是内网 则是外网
			web = "0";
		}
		formBean.setWeb(web);
		if (jsonObject.containsKey("url")) {
			formBean.setUrl(jsonObject.get("url"));
		}
		String modifyuser = ""; // 是否需要修改本地用户，默认 T（T是 F否 U只支持UPDATE操作）
		if (jsonObject.containsKey("modifyuser")) {
			modifyuser = jsonObject.get("modifyuser");
		}
		if ("".equals(modifyuser) || modifyuser == null) {
			modifyuser = "T";
		}
		formBean.setModifyuser(modifyuser);
		if (jsonObject.containsKey("ssourl")) {// sso 调用地址
			formBean.setSsourl(jsonObject.get("ssourl"));
		}

		ModelAndView modelAndView = new ModelAndView("complat/interfaces/ldap/receive");
		String result = formBean.getResult();
		boolean isSuccess = false;
		modelAndView.addObject("state", state);
		// if(state.equalsIgnoreCase("S")){ //注册
		// isSuccess = ldapService.register(formBean);
		// modelAndView.addObject("msg", "应用系统注册" + (isSuccess ? "成功" : "失败") + "！");
		// } else
		if (state.equalsIgnoreCase("C") && result.equalsIgnoreCase("T")) { // 单点登录
			formBean = ldapService.readXML(formBean);
			formBean = decryptFormBean(formBean);

			isSuccess = ldapService.checkValidate(formBean);

			modelAndView.addObject("formBean", formBean);
		} else if (state.equalsIgnoreCase("D")) { // 删除用户
			isSuccess = ldapService.removeUser(formBean);
			modelAndView.addObject("msg", "用户删除" + (isSuccess ? "成功" : "失败") + "！");
		}
		modelAndView.addObject("isSuccess", isSuccess);
		return modelAndView;
	}

	/**
	 * 请求sso菜单
	 * 
	 * @return
	
	@RequestMapping(value = "sso")
	@ResponseBody
	public String sso() {
		if (!BaseInfo.isSso()) {
			logger.info("系统未开启SSO，不能被接入");
			return null;
		}
		// ModelAndView modelAndView = new ModelAndView("complat/interfacesldap>sso");
		ReceiveFormBean formBean = new ReceiveFormBean();
		formBean.setWeb("0");
		formBean = ldapService.readXML(formBean);

		String isOk = "";
		try {
			URL url = new URL(formBean.getSsourl());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			isOk = conn.getResponseMessage();
		} catch (IOException e) {
			logger.warn("无法连接到SSO地址" + formBean.getSsourl() + "  连接失败！请检测SSO是否启动！");
		}
		
		String username = ""; // 加密登录用户名
		String password = ""; // 加密密码
		List<String> ssoList = null;
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		if (currentUser != null && "OK".equals(isOk)) {
			MD5 md5 = new MD5();
			String key = formBean.getEnckey();
			String encrypttype = formBean.getEncrypttype();
			password = Md5Util.md5decode(currentUser.getPwd());
			String name = currentUser.getName();
			if ("1".equals(encrypttype)) {
				username = md5.encrypt(currentUser.getLoginName(), key);
				password = md5.encrypt(password, key);
				name = md5.encrypt(name, key);
			} else if ("2".equals(encrypttype)) {
				username = md5.encryptMB(currentUser.getLoginName(), key);
				password = md5.encryptMB(password, key);
				name = md5.encryptMB(name, key);
			} else if ("0".equals(encrypttype)) {
				username = currentUser.getLoginName();
			}
			// user.setUserDetail(ldapService.findDetailById(user.getIid()));

			// if("5".equals(type)){ //返回xml解析
			//
			// }
			ssoList = xmlParse(currentUser, formBean, username, password, name);
		}
		// modelAndView.addObject("user", user);
		// modelAndView.addObject("isOk", isOk);
		// modelAndView.addObject("formBean", formBean);
		// modelAndView.addObject("username", username);
		// modelAndView.addObject("password", password);
		// modelAndView.addObject("type", 5); //SSO显示的样式 1下拉框 2多行 3单行
		return JsonUtil.objectToString(ssoList);
	}
	@RequestMapping(value = "ticketlogin")
	public ModelAndView ticketLogin(@RequestParam(value = "ticket", required = false) String ticket, HttpServletResponse response){
		ModelAndView errorModelAndView = new ModelAndView("support/login");
		ModelAndView successModelAndView = new ModelAndView();
		String ip = ControllerUtil.getIp();
		if(StringUtil.isEmpty(ticket)){
			
			return errorModelAndView;
		}
		String token = ldapService.getToken(ticket);
		if(StringUtil.isEmpty(token)){
			return errorModelAndView;
		}
		String loginname = ldapService.getUserInfo(token);
		if(StringUtil.isEmpty(loginname)){
			return errorModelAndView;
		}
		//User user = userService.findByLoginName(loginname);
		try {
			BanList banList = banListService.checkLoginTimes(loginname, ip);
			if (!banList.isCanLogin()) {
				throw new LoginException("login.time.error");
			}
			CurrentUser user = userService.checkUserLogin(loginname, ip);
			if (user != null) {
				UserSessionInfo.setCurrentUser(user);
				
				banList.setLoginTimes(0);
				if (banList != null && banList.getIid() != null) {
					banListService.removeById(banList.getIid());
				}
				AccessLog accessLog = new AccessLog();
				accessLog.setIp(ip);
				accessLog.setLoginName(loginname);
				accessLog.setAccesstime(user.getAccessTime());
				accessLogService.record(accessLog);
				RedirectView redirectView = new RedirectView("../../manager/index.do");
				successModelAndView.setView(redirectView);
				return successModelAndView;
			} else {
				throw new LoginException("login.error");
			}
		} catch (LoginException e) {
			System.out.println(e.getMessage());
			RedirectView redirectView = new RedirectView("../../login.do");
			errorModelAndView.setView(redirectView);
			return errorModelAndView;
		}
		
	}
	 * 解析XML
	 * 
	 * @param currentUser
	 *            当前登录用户
	 * @param formBean
	 * @param username
	 *            加密后的用户名
	 * @param password
	 *            加密后的密码
	 * @return
	
	private List<String> xmlParse(CurrentUser currentUser, ReceiveFormBean formBean,
			String username, String password, String name) {
		HttpBLF httpBLF = new HttpBLF();
		Map<String, String> params = new HashMap<String, String>();
		params.put("user", username);
		params.put("pwd", password);
		params.put("app", formBean.getAppname());
		params.put("type", "5");
		params.put("t_name", name);
		params.put("email", StringUtil.getString(currentUser.getEmail()));
		params.put("mobile", StringUtil.getString(currentUser.getMobile()));
		params.put("sex", currentUser.getSex() + "");
		params.put("address", StringUtil.getString(currentUser.getAddress()));
		params.put("encrypttype", formBean.getEncrypttype());
		params.put("enckey", formBean.getEnckey());
		params.put("domainname", "");
		logger.debug(params);
		logger.debug(formBean.getSsourl());
		String xmlContent = httpBLF.sendPost(formBean.getSsourl(), params);//FileUtil.readFileToString(BaseInfo.getRealPath() + "/WEB-INF/logs/xx.txt");//
		if (xmlContent == null || "".equals(xmlContent)) {
			logger.warn("SSO返回异常");
			return null;
		}
		logger.debug("receive from sso xmlContent:" + xmlContent);
		Document document = Jsoup.parse(xmlContent, "", Parser.htmlParser());
//		SSOXMLParser parser = new SSOXMLParser(new StringReader(xmlContent));
		String xmlState = document.select("sso>xmlstate").text();
		String result = document.select("sso>result").text();
		String state = document.select("sso>state").text();
		String groupName = document.select("sso>groupname").text();
		List<String> ssoList = null;
		if (xmlState != null && "OK".equalsIgnoreCase(xmlState)) {
			ssoList = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			MD5 md5 = new MD5();
			Elements apps = document.select("sso>app");
			for (Element app : apps) {
				// <a href="#" class="active">JCMS内容管理系统</a>
				String target = app.select("cuttype").text().equals("2") ? "_blank" : "_self";
				Map<String, String> parameters = new HashMap<String, String>();
				parameters.put("loginuser", app.select("loginuser").text());
				parameters.put("loginpass", app.select("loginpass").text());
				parameters.put("result", result);
				parameters.put("state", state);
				parameters.put("modifyuser", app.select("modifyuser").text());
				String encrypttype = app.select("encrypttype").text();
				String key = app.select("key").text();
				if("1".equals(encrypttype)){
					parameters.put("t_name", md5.encrypt(currentUser.getName(), key));
				}else if("2".equals(encrypttype)){
					parameters.put("t_name", md5.encryptMB(currentUser.getName(), key));
				}else{
					parameters.put("t_name", currentUser.getName());
				}
				parameters.put("domainname", groupName);
				logger.debug("paramters=" + JsonUtil.objectToString(parameters));
				String jsonParams = StringUtil.encoder(md5.encrypt(JSONObject.toJSONString(parameters), key));
				sb.append("<a href=\"");
				// sb.append(url[i]).append("?loginuser=").append(loginUser[i]).append("&loginpass=")
				// .append(loginPass[i]).append("&result=").append(result[0])
				// .append("&state=").append(state[0]).append("&modifyuser=")
				// .append(modifyUser[i]).append("&t_name=").append(name)
				// .append("&domainname=").append(groupName).append("\" ");
				sb.append(app.select("url").text()).append("?parameters=").append(jsonParams).append("\" ");
				sb.append(" target=\"").append(target).append("\">").append(app.select("appname").text())
						.append("</a>");
				logger.debug("11111===" + JsonUtil.objectToString(sb.toString()));
				ssoList.add(sb.toString());
				StringUtil.cleanStringBuffer(sb);
			}
		}
		return ssoList;
	}*/
	/**
	 * 解密用户信息
	 * 
	 * @param formBean
	 * @return
	 */
	private ReceiveFormBean decryptFormBean(ReceiveFormBean formBean) {
		String loginId = formBean.getLoginuser();
		String password = formBean.getLoginpass();
		String encrypttype = formBean.getEncrypttype();
		String enckey = formBean.getEnckey();
		MD5 md5 = new MD5();
		if ("1".equals(encrypttype)) {
			loginId = md5.decrypt(loginId, enckey);
			password = md5.decrypt(password, enckey);
		} else if ("2".equals(encrypttype)) {
			loginId = md5.decryptMB(loginId, enckey);
			password = md5.decryptMB(password, enckey);
		}
		formBean.setLoginuser(loginId);
		formBean.setLoginpass(password);
//		try {
//			formBean.setT_name(URLDecoder.decode(formBean.getT_name(), "UTF-8"));
//		} catch (Exception e) {
//		}
		return formBean;
	}
}
