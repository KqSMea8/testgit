package com.hanweb.support.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.BaseInfo;  
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.view.menu.Menu;
import com.hanweb.common.view.menu.MenuItem;
import com.hanweb.complat.constant.Settings;
import com.hanweb.complat.entity.BanList;
import com.hanweb.complat.entity.Right;
import com.hanweb.complat.entity.Role;
import com.hanweb.complat.exception.LoginException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.complat.service.BanListService;
import com.hanweb.complat.service.UserService;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.sys.entity.role.RoleCol;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.role.RoleColService;
import com.hanweb.jmp.sys.service.sites.SiteService;


@Controller
@RequestMapping(value = "manager")
public class MainFrameController {
	
	/**
	 *  siteService
	 */
	@Autowired
	private SiteService siteService;
	
	/**
	 *  banListService
	 */
	@Autowired
	private BanListService banListService;
	
	/** 
	 *  userService
	 */
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleColService roleColService;
	
	/**
	 *  logger
	 */
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 *  PAGE
	 */
	private static String PAGE = "/manager/info/list.do"; // 登录后第一个页面
	
	/**
	 * page
	 * @param str  str
	 */
	private static void page(String str){
		PAGE = str;
	}

	/**
	 * MENU
	 */
	private static String MENU = "";  //登录后第一个下拉菜单
	
	/**
	 * menu
	 * @param str  str
	 */
	private static void menu(String str){
		MENU = str;
	}
	
	/**
	 * CHANNEL
	 */
	private static String CHANNEL = "";
	
	/**
	 * channel
	 * @param str  str
	 */
	private static void channel(String str){
		CHANNEL = str;
	}
	
	/**
	 *  NO_MENU
	 */
	private static final Set<String> NO_MENU = 
		new HashSet<String>(Arrays.asList(new String[]{"channel", "feedback",
				"parameter", "role", "update", "comment", "lightapp", "read", "broke", "numsense"}));

	/**
	 * showMainPage
	 * @return  ModelAndView
	 */
	@RequestMapping("index")
	public ModelAndView showMainPage() {
		ModelAndView modelAndView = new ModelAndView("support/mainframe");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();   
		String backAdminName = "系统管理";
		CurrentUser adminUser = UserSessionInfo.getAdminUserInfo();
		if(adminUser != null && !currentUser.isSysAdmin()){
			backAdminName = "系统设置";
		}
		if(currentUser.isSysAdmin()){
			page("/manager/site/list.do");
			menu("");
			channel("sysconfig");
		}else if(currentUser.getIsWebSiteAdmin()){
			page("/manager/info/list.do");
			menu("/manager/menu/infomenu_show.do");
			channel("");
		}else{
			List<Right> rightList = currentUser.getRightList();
			if(rightList != null && rightList.size() > 0){
				Right right=rightList.get(0); 
				if("subscribecol".equals(right.getModuleId())){
					page("/manager/sign/subscribecol/list.do");
				}else if("parameter".equals(right.getModuleId())){
					page("manager/site/modifyDetail_show.do?iid="+currentUser.getSiteId());
				}else if("broke".equals(right.getModuleId())){
					page("/manager/lightapp/list.do");
				}else if("numsense".equals(right.getModuleId())){
					page("/manager/lightapp/list.do");
				}else if("read".equals(right.getModuleId())){
					page("/manager/lightapp/list.do");
				}else{
					page("/manager/"+right.getModuleId()+"/list.do");
				}
				
		        if(NO_MENU.contains(right.getModuleId())){
		        	menu("");
		        }else{
		        	if("subscribecol".equals(right.getModuleId())){
		        		menu("/manager/menu/subscribesign_show.do");
		        	}else{
		        		menu("/manager/menu/"+right.getModuleId()+"menu_show.do");
		        	}
		        	
		        }
		        channel("");
			}
			
			if(CollectionUtils.isNotEmpty(currentUser.getRoleList())){
			    for(Role role : currentUser.getRoleList()){
	                List<RoleCol> roleColList = roleColService.findByRoleId(role.getIid());
	                if(CollectionUtils.isNotEmpty(roleColList)){
	                    for(RoleCol roleCol : roleColList){
	                        Set<String> colRightids = currentUser.getColids();
	                        if(colRightids.contains("manage-" + roleCol.getColId())){
	                            page("/manager/col/list.do");
                                menu("/manager/menu/colmenu_show.do");
                            }
	                        if(colRightids.contains("edit-" + roleCol.getColId())){
                                menu("/manager/menu/infomenu_show.do");
                            }
	                        if(colRightids.contains("audit-" + roleCol.getColId())){
                                menu("/manager/menu/infomenu_show.do");
                            }
	                    }
	                }
	            }
			}
			
		}
		modelAndView.addObject("pageUrl", ControllerUtil.getAbsolutePath(PAGE));
		modelAndView.addObject("menuUrl", ControllerUtil.getAbsolutePath(MENU));
		modelAndView.addObject("channel", CHANNEL);
		
		Integer siteId = currentUser.getSiteId();
		Site site = siteService.findByIid(siteId);
		String siteName = "";
		if(site != null && StringUtil.isNotEmpty(site.getName())){
			siteName = site.getName();
		}else{
			siteName = "微门户管理平台";
		}

		Menu menu = Menu.getInstance();

		
		if(currentUser.isSysAdmin()){ 
			//站点管理：系统管理、封停管理、参数管理、统计分析
			menu.addMenuItem(MenuItem.getInstance("sys", null, backAdminName).setChannel("sysconfig")
					.setSeparator(true));
			
			menu.addMenuItem(MenuItem.getInstance("site", "sys", "站点管理",
					ControllerUtil.getAbsolutePath("/manager/site/list.do"))
					.setAllowed(true, false));
			
			menu.addMenuItem(MenuItem.getInstance("banlist", "sys", "封停管理",
					ControllerUtil.getAbsolutePath("/manager/banlist/list.do"))
					.setAllowed(true, false));

			menu.addMenuItem(MenuItem.getInstance("sysparam", "sys", "参数管理",
					ControllerUtil.getAbsolutePath("/manager/jmp/configuration/modify_show.do"))
					.setAllowed(true, false));
			 
			menu.addMenuItem(MenuItem.getInstance("count", "sys", "统计分析",
					ControllerUtil.getAbsolutePath("/manager/menu/countmenu_show.do"),
					ControllerUtil.getAbsolutePath("/manager/count/list.do"))
					.setAllowed(true, false));
			if(NumberUtil.getInt(Configs.getConfigs().getIsAutoCreateApp()) == 1 
					&& NumberUtil.getInt(Configs.getConfigs().getCreateAppType()) == 0){ 
				menu.addMenuItem(MenuItem.getInstance("app", "plugins", "一键打包",
						ControllerUtil.getAbsolutePath("/manager/menu/appmenu_show.do"),
						ControllerUtil.getAbsolutePath("/manager/app/list.do"))
						.setPermission("app", null).setAllowed(true, false));
			}
		}else{
			//信息管理：栏目分类 、 信息管理
			if(!currentUser.isSysAdmin() && (haveRight(currentUser, "info") ||haveRight(currentUser, "todo")
					|| haveRight(currentUser, "col") || haveRight(currentUser, "recycle"))){
				menu.addMenuItem(MenuItem.getInstance("infomanage", null, "信息管理")
						.setChannel("info").setSeparator(true));
			}
			
			if(haveRight(currentUser, "col")){
				menu.addMenuItem(MenuItem.getInstance("col", "infomanage", "栏目管理",
						ControllerUtil.getAbsolutePath("/manager/menu/colmenu_show.do"),
						ControllerUtil.getAbsolutePath("/manager/col/list.do"))
						.setPermission("col", null).setAllowed(false, true));
			}
			
			if(haveRight(currentUser, "info")){
				try {
					menu.addMenuItem(MenuItem.getInstance("info", "infomanage", "信息管理",
							ControllerUtil.getAbsolutePath("/manager/menu/infomenu_show.do"),
							ControllerUtil.getAbsolutePath("/manager/info/list.do?colId=0&colName=" 
									+ URLEncoder.encode("信息管理", "UTF-8")))
							.setPermission("info", null).setAllowed(false, true));
				} catch (UnsupportedEncodingException e) {
					logger.error("UTF-8加密失败", e);
				}
			}
			if(haveRight(currentUser, "todo")){
				menu.addMenuItem(MenuItem.getInstance("todo", "infomanage", "待审核信息",
						ControllerUtil.getAbsolutePath("/manager/menu/todomenu_show.do"),
						ControllerUtil.getAbsolutePath("/manager/todo/list.do"))
						.setAllowed(false, true));
			}
			
			if(haveRight(currentUser, "channel/list.do") 
					|| haveRight(currentUser, "/sign/subscribecol")
					|| haveRight(currentUser, "/sign")||haveRight(currentUser, "channel")){
				//功能管理：导航管理 、订阅管理、日志管理、角标定义
				menu.addMenuItem(MenuItem.getInstance("channelmanage", null, "功能管理")
						.setChannel("channel").setSeparator(true)); 
			}
			if(haveRight(currentUser, "channel")){
				menu.addMenuItem(MenuItem.getInstance("channel", "channelmanage", "导航管理",
						ControllerUtil.getAbsolutePath("/manager/channel/list.do")).
						setPermission("channel", null).setSeparator(true).setChannel("channel").setAllowed(false, true));
			}	
			if(haveRight(currentUser, "subscribecol")){
				menu.addMenuItem(MenuItem.getInstance("subscribecol", "channelmanage", "订阅管理",
						ControllerUtil.getAbsolutePath("/manager/menu/subscribesign_show.do"),
						ControllerUtil.getAbsolutePath("/manager/sign/list.do?mid=2"))
						.setPermission("subscribecol", null).setAllowed(false, true));
			}
			if(haveRight(currentUser, "comment")){
				menu.addMenuItem(MenuItem.getInstance("comment", "channelmanage", "评论管理",
						ControllerUtil.getAbsolutePath("/manager/comment/list.do"))
						.setPermission("comment", null).setAllowed(false, true));
			}
			if(haveRight(currentUser, "sign")){
				menu.addMenuItem(MenuItem.getInstance("sign", "channelmanage", "角标定义",
						ControllerUtil.getAbsolutePath("/manager/sign/list.do?mid=3"))
						.setPermission("sign", null).setAllowed(false, true));
			} 
			if(haveRight(currentUser, "log")){
				menu.addMenuItem(MenuItem.getInstance("log", "channelmanage", "日志管理",
						ControllerUtil.getAbsolutePath("/manager/menu/logmenu_show.do"),
						ControllerUtil.getAbsolutePath("/manager/log/chart.do"))
						.setPermission("log", null).setAllowed(true, true)); 
			}
			if(!currentUser.isSysAdmin() && haveRight(currentUser, "lightapp") || 
											haveRight(currentUser, "broke") || 
											haveRight(currentUser, "numsense") || 
											haveRight(currentUser, "read")){
				menu.addMenuItem(MenuItem.getInstance("lightapp", null, "应用管理")
						.setPermission("lightapp", null).setSeparator(true).setChannel("lightapp")
						.setAllowed(false, true));
				
				menu.addMenuItem(MenuItem.getInstance("lightapptype", "lightapp", "应用分类",
						ControllerUtil.getAbsolutePath("/manager/lightapptype/menu/typemenu_show.do?state=1"),
						ControllerUtil.getAbsolutePath("/manager/lightapptype/list.do"))
						.setPermission("lightapp", null).setChannel("lightapptype")
						.setAllowed(false, true));
				
				menu.addMenuItem(MenuItem.getInstance("lightappmanager", "lightapp", "应用列表", 
						ControllerUtil.getAbsolutePath("/manager/lightapptype/menu/typemenu_show.do?state=2"),
						ControllerUtil.getAbsolutePath("/manager/lightapp/list.do?lightTypeId=-1"))
						.setPermission("lightapp", null).setChannel("lightapptype")
						.setAllowed(false, true));
			}
			if(!currentUser.isSysAdmin() && haveRight(currentUser, "matter")){		
				menu.addMenuItem(MenuItem.getInstance("matter", null, "素材管理",
						ControllerUtil.getAbsolutePath("/manager/menu/mattermenu_show.do"),
						ControllerUtil.getAbsolutePath("/manager/matter/list.do")).
						setPermission("matter", null).setSeparator(true).setAllowed(false, true));
			}

			//系统管理：机构管理、用户管理、角色管理、参数设置、统计分析、评论管理
			menu.addMenuItem(MenuItem.getInstance("sys", null, backAdminName).setChannel("sysconfig")
					.setSeparator(true));
			
			if(haveRight(currentUser, "site")){
				menu.addMenuItem(MenuItem.getInstance("parameter", "sys", "参数设置",
						ControllerUtil.getAbsolutePath("/manager/site/modifyParameter_show.do?iid=" + siteId))
						.setPermission("parameter", null).setAllowed(false, true));
			}
			if(haveRight(currentUser, "group")){
				menu.addMenuItem(MenuItem.getInstance("group", "sys", "机构管理",
						ControllerUtil.getAbsolutePath("//manager/menu/groupmenu_show.do"),
						ControllerUtil.getAbsolutePath("/manager/group/list.do"))
						.setPermission("group", null).setAllowed(false, true));
			}
			if(haveRight(currentUser, "user")){
				menu.addMenuItem(MenuItem.getInstance("user", "sys", "用户管理",
						ControllerUtil.getAbsolutePath("/manager/menu/usermenu_show.do"),
						ControllerUtil.getAbsolutePath("/manager/user/list.do"))
						.setPermission("user", null).setAllowed(false, true));
			}
			if(haveRight(currentUser, "role")){
				menu.addMenuItem(MenuItem.getInstance("role", "sys", "角色管理",
						ControllerUtil.getAbsolutePath("/manager/role/list.do"))
						.setPermission("role", null).setAllowed(false, true));
			}
			if(haveRight(currentUser, "feedback")){
				menu.addMenuItem(MenuItem.getInstance("feedback", "sys", "意见反馈",
						ControllerUtil.getAbsolutePath("/manager/feedback/list.do"))
						.setPermission("feedback", null).setAllowed(false, true));
			}
			if(haveRight(currentUser, "version")){
				menu.addMenuItem(MenuItem.getInstance("version", "sys", "版本更新",
						ControllerUtil.getAbsolutePath("/manager/version/list.do"))
						.setPermission("version", null).setAllowed(false, true));
			}
			if(haveRight(currentUser, "ditch")){
				menu.addMenuItem(MenuItem.getInstance("ditch", "sys", "渠道管理",
						ControllerUtil.getAbsolutePath("/manager/ditch/list.do"))
						.setPermission("ditch", null).setAllowed(false, true));
			}
	    }
		
		menu.addMenuItem(MenuItem
				.getInstance("account", null, "<i class=\"iconfont nav-account-btn\">&#xf503f;</i>")
				.setStyle("width: 40px;").setSeparator(true));
		if(adminUser != null && !currentUser.isSysAdmin()){
			menu.addMenuItem(MenuItem.getInstance("backadmin", "account", "系统管理").setOnClick(
					"backAdminLogin('"+adminUser.getLoginName()+"'," +
						"'"+adminUser.getPwd()+"')"));
		} 
		 
		menu.addMenuItem(MenuItem.getInstance("modify", "account", "账户设置").setOnClick(
				"openDialog('"
						+ ControllerUtil.getAbsolutePath("/manager/user/modify_self_show.do")
						+ "',500,515,{title:'账户设置'})"));

		menu.addMenuItem(MenuItem.getInstance("online", "account", "在线用户").setOnClick(
				"openDialog('"
						+ ControllerUtil.getAbsolutePath("/manager/onlineuser/list.do")
						+ "',960,500,{title:'在线用户'})"));

		menu.addMenuItem(MenuItem.getInstance("logout", "account", "系统退出").setOnClick(
				"top.location.href='" + ControllerUtil.getAbsolutePath("/logout.do") + "'"));
		
		modelAndView.addObject("siteName", siteName);
		modelAndView.addObject("sso", BaseInfo.isSso());
		modelAndView.addObject("topMenuHtml", menu.parse());
		return modelAndView;
	}
	
	
	
	
	/**
	 * backAdminLogin
	 * @param name  name
	 * @param password  password
	 * @param response  response
	 * @param session   session
	 * @return  JsonResult
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("backAdminLogin")
	@ResponseBody
	public JsonResult backAdminLogin(String name, String password,
			HttpServletResponse response, HttpSession session) {  
		//判断系统管理员是否存在
		CurrentUser adminUser = UserSessionInfo.getAdminUserInfo();
		if(adminUser == null){
			return null;
		}
		JsonResult jsonResult = JsonResult.getInstance(); 
		String ip = ControllerUtil.getIp();
		BanList banList = null;
		try {
			if (StringUtil.isEmpty(name) || StringUtil.isEmpty(password)) {
				throw new LoginException("login.error");
			}
			banList = banListService.checkLoginTimes(name, ip);
			if (!banList.isCanLogin()) {
				throw new LoginException("login.error");
			}
			CurrentUser user = userService.checkSiteUserLogin(name, password, ip);
			if (user != null) {
				UserSessionInfo.setCurrentUser(user);
				if(user.isSysAdmin()){
					UserSessionInfo.setAdminUserInfo(user);
				} 
				ControllerUtil.addCookie(response, 
						com.hanweb.complat.constant.StaticValues.LOGIN_COOKIE, user.getName(),
						60 * 60 * 24 * 7);
				banList.setLoginTimes(0);
				if (banList != null && banList.getIid() != null) {
					banListService.removeById(banList.getIid());
				}
				jsonResult.setSuccess(true);
			} else {
				throw new LoginException("login.error");
			}
		} catch (LoginException e) {
			if (banList != null && "login.error".equals(e.getMessage())) {
				int last = Settings.getSettings().getLoginError() - banList.getLoginTimes() - 1;
				String message = null;
				if (last > 0) {
					message = SpringUtil.getMessage(e.getMessage());
					message += "，" + SpringUtil.getMessage("login.error.limit", last);
					banList.setLoginTimes(banList.getLoginTimes() + 1);
					banListService.modify(banList);
				} else {
					message = SpringUtil.getMessage("user.login.times", Settings.getSettings()
							.getBanTimes());
					banList.setLoginTimes(banList.getLoginTimes() + 1);
					banListService.modify(banList);
				}
				jsonResult.setMessage(message);
			} else {
				jsonResult.setMessage(e.getMessage());
			}
		}
		return jsonResult;
	}
	
	private boolean haveRight(CurrentUser currentUser, String url){
		if(currentUser.isSysAdmin() || currentUser.getIsWebSiteAdmin() || (currentUser.getPermissions()!=null 
						&& currentUser.getPermissions().contains(url))){
			return true;
		}
		return false;
	}
}
