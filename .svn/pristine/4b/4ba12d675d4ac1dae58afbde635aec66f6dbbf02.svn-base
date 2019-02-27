package com.hanweb.jmp.sys.controller.sites;

import java.io.File;
import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.annotation.Permission;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.FileResource;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.MultipartFileInfo;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.complat.constant.Settings;
import com.hanweb.complat.entity.BanList;
import com.hanweb.complat.entity.User;
import com.hanweb.complat.exception.LoginException;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.complat.service.BanListService;
import com.hanweb.complat.service.UserService;
import com.hanweb.jmp.constant.StaticValues;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.support.controller.CurrentUser;

@Controller
@Permission
@RequestMapping("manager/site")
public class OprSiteController {

	/**
	 * logger
	 */
	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * siteService
	 */
	@Autowired
	private SiteService siteService;

	/**
	 * userService
	 */
	@Autowired
	private UserService userService; 

	/**
	 * banListService
	 */
	@Autowired
	private BanListService banListService;

	/**
	 * 新增页
	 * @return    设定参数 .
	 */
	@RequestMapping("add_show")
	public ModelAndView showAdd() {
		ModelAndView modelAndView = new ModelAndView("jmp/sys/sites/site_opr");
		SiteFormBean siteFrom = new SiteFormBean();
		Site siteEn=new Site();
		siteEn.setIsSafeControl(1);
		siteFrom.setSite(siteEn);
		modelAndView.addObject("url", "add_submit.do");
		modelAndView.addObject("siteFrom", siteFrom);
		modelAndView.addObject("checkPasswordLevel", Settings.getSettings().getCheckLevel());
		return modelAndView;
	}

	/**
	 * 修改页
	 * @param iid iid
	 * @return    设定参数 .
	 */
	@RequestMapping("modify_show")
	public ModelAndView showModify(String iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/sys/sites/site_opr");
		int siteId = NumberUtil.getInt(iid);
		SiteFormBean siteFrom = new SiteFormBean();
		Site site = siteService.findByIid(siteId);
		siteFrom.setSite(site);
		if(NumberUtil.getInt(site.getUserId()) > 0){
			User user = userService.findByIid(site.getUserId());
			siteFrom.setUser(user);
		}
		modelAndView.addObject("url", "modify_submit.do");
		modelAndView.addObject("siteFrom", siteFrom);
		modelAndView.addObject("checkPasswordLevel", Settings.getSettings().getCheckLevel());
		return modelAndView;
	}

	/**
	 * 新增提交
	 * @param siteFrom siteFrom
	 * @return    设定参数 .
	 * @throws ParseException 
	 */
	@RequestMapping("add_submit")
	@ResponseBody
	public JsonResult submitAdd(SiteFormBean siteFrom) throws ParseException {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();  
		if(!currentUser.isSysAdmin()){
			return null;
		}
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			isSuccess = siteService.add(siteFrom);
			if (isSuccess) {
				jsonResult.set(ResultState.ADD_SUCCESS);
			} else {
				jsonResult.set(ResultState.ADD_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult; 
	}

	/**
	 * 修改提交
	 * @param siteFrom siteFrom
	 * @return    设定参数 .
	 */
	@RequestMapping("modify_submit")
	@ResponseBody
	public JsonResult submitModify(SiteFormBean siteFrom) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();  
		if(!currentUser.isSysAdmin()){
			return null;
		}
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = siteService.modify(siteFrom);
			if (isSuccess) {
				jsonResult.set(ResultState.MODIFY_SUCCESS);
			} else {
				jsonResult.set(ResultState.MODIFY_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult; 
	}

	/**
	 * 删除
	 * @param id id
	 * @return    设定参数 .
	 */
	@RequestMapping("remove")
	@ResponseBody
	public JsonResult remove(int id) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();  
		if(!currentUser.isSysAdmin()){
			return null;
		}
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = siteService.remove(id);
			if (isSuccess) {
				jsonResult.set(ResultState.REMOVE_SUCCESS);
			} else {
				jsonResult.set(ResultState.REMOVE_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult; 
	}

	/**
	 * 导出
	 * @param id id
	 * @param name name
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "export")
	public FileResource export(Integer id, String name) {
		File file = null;
		FileResource fileResouce = null;
		try {
			String filePath = siteService.eexport(id);
			file = new File(filePath);
			fileResouce = ControllerUtil.getFileResource(file, name + ".zip");
		} catch (Exception e) {
			logger.error("export group Error ", e);
			return null;
		} finally {
			if (file != null && file.exists()) {
				FileUtil.deleteFile(file); 
			}
		}
		return fileResouce;
	} 

	/**
	 * 导入页
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "import_show")
	@ResponseBody
	public ModelAndView showImport() {
		ModelAndView modelAndView = new ModelAndView("jmp/sys/sites/site_import");
		modelAndView.addObject("url", "import_submit.do");
		return modelAndView;
	}

	/**
	 * 导入提交
	 * @param file file
	 * @return    设定参数 .
	*/
	@RequestMapping(value = "import_submit")
	@ResponseBody
	public String submitImport(MultipartFile file) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();  
		if(!currentUser.isSysAdmin()){
			return null;
		}
		// 提示信息
		String message = "";
		// 错误信息
		String error = "";
		Script script = Script.getInstanceWithJsLib();
		if(file==null){
		    script.addAlert("导入失败！文件类型不正确！"); 
		}else{
		    if (file.isEmpty()) {
	            error = SpringUtil.getMessage("import.nofile");
	        } else {
	            try {
	                MultipartFileInfo info = MultipartFileInfo.getInstance(file);
	                if (ArrayUtils.contains(StaticValues.ZIP_TYPE, info.getFileType())) {
	                    File filePath = new File(Settings.getSettings().getFileTmp() 
	                            + StringUtil.getUUIDString() + "."
	                            + info.getFileType());
	                    ControllerUtil.writeMultipartFileToFile(filePath, file);
	                    message = siteService.importSite(filePath);
	                } else {
	                    throw new OperationException("文件类型不正确");
	                }
	            } catch (OperationException e) {
	                error += e.getMessage();
	            } catch (Exception e) {
	                error += e.getMessage();
	            }
	        }
		    if (!StringUtil.isEmpty(error)) {
	            error = "导入失败！" + error;
	            script.addAlert(error);
	        } else {
	            message = "导入成功！" + message;
	            script.addAlert(message);
	            script.addScript("parent.refreshParentWindow();parent.closeDialog();");
	        }
		}
		
		
		return script.getScript();
	}

	/**
	 * 管理员登录
	 * @param siteId siteId
	 * @param response response
	 * @param session session
	 * @return    设定参数 .
	*/
	@SuppressWarnings("deprecation")
    @RequestMapping("adminLogin")
	@ResponseBody
	public JsonResult adminLogin(Integer siteId, HttpServletResponse response, HttpSession session) {
		JsonResult jsonResult = JsonResult.getInstance();
		String ip = ControllerUtil.getIp();
		BanList banList = null;
		Site site = siteService.findByIid(siteId);
		CurrentUser user = null;
		User userEn = new User();
		if(NumberUtil.getInt(site.getUserId()) == 0){
			jsonResult.setMessage("前台未初始化管理员，请返回前台进行初始化");
			return jsonResult;
		}else{ 
			userEn = userService.findByIid(site.getUserId());
		}
		try {
			banList = banListService.checkLoginTimes(userEn.getLoginName(), ip);
			if (!banList.isCanLogin()) {
				throw new LoginException("login.error");
			}
			user = userService.checkSiteUserLogin(userEn.getLoginName(), userEn.getPwd(), ip);
			if (user != null) {
				UserSessionInfo.setCurrentUser(user);  
				ControllerUtil.addCookie(response, 
						com.hanweb.complat.constant.StaticValues.LOGIN_COOKIE, user
						.getName(), 60 * 60 * 24 * 7);
				banList.setLoginTimes(0);
				if (banList != null && banList.getIid() != null) {
					banListService.removeById(banList.getIid());
				}
				jsonResult.set(ResultState.OPR_SUCCESS);
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
					message = SpringUtil.getMessage("user.login.times", 
							Settings.getSettings().getBanTimes());
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

	/**
	 * 栏目字段设置
	 * @param siteId
	 * @return
	 */
	@RequestMapping("sitecolfield_show")
	public ModelAndView showColFieldLevel(Integer siteId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("fieldUrl", ControllerUtil.getAbsolutePath("/manager/site/colfield/list.do?siteId="+siteId));
		return modelAndView;
	}
	
	/**
	 * 判断自助打包是否初始化站点管理员
	 * @param siteId
	 * @return
	 */
	@RequestMapping("checkAdmin")
	@ResponseBody
	public JsonResult adminLogin(Integer siteId) {
		JsonResult jsonResult = JsonResult.getInstance();
		Site site = siteService.findByIid(siteId);
		if(NumberUtil.getInt(site.getUserId()) == 0){
			jsonResult.setMessage("前台未初始化管理员，请返回前台进行初始化");
			return jsonResult;
		}else{ 
			jsonResult.set(ResultState.OPR_SUCCESS);
			return jsonResult;
		}
	}
	
}