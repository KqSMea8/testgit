package com.hanweb.jmp.pack.backstage.controller.appuser;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.hanweb.common.util.Md5Util;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.pack.entity.AppUser;
import com.hanweb.jmp.pack.service.AppUserService;
import com.hanweb.support.controller.CurrentUser;

@Controller 
@RequestMapping("manager/appuser")
public class OprAppUserController { 
	
	/**
	 * appService
	 */
	@Autowired
	private AppUserService appUserService;
	
	
	/**
	 * 添加
	 * @return  ModelAndView
	 */
	@RequestMapping("add_show")
	public ModelAndView showAdd(){
		ModelAndView modelAndView = new ModelAndView("jmp/pack/backstage/appuser/appuser_opr");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		AppUser appuser=new  AppUser();
		modelAndView.addObject("url", "add_submit.do");
		modelAndView.addObject("appuser", appuser); 
		modelAndView.addObject("siteId", currentUser.getSiteId()); 
		return modelAndView;
	}
	
	 
	/**
	 * submitAdd
	 * @param appuser   appuser  外网用户
	 * @return  JsonResult
	 */
	@RequestMapping("add_submit")
	@ResponseBody
	public JsonResult submitAdd(AppUser appuser) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try { 
			CurrentUser currentUser = UserSessionInfo.getCurrentUser();
			appuser.setSiteId(currentUser.getSiteId());
			appuser.setCreateTime(new Date());
			appuser.setPwd(Md5Util.md5encode(appuser.getPwd()));
		    isSuccess=appUserService.add(appuser);
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
	 * 删除打包用户
	 * 
	 * @param ids 
	 *          用户id串
	 * @return  结果信息
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try { 
			isSuccess = appUserService.removeByIds(ids);
			if (isSuccess) {
				jsonResult.set(ResultState.REMOVE_SUCCESS);
				jsonResult.addParam("remove", ids);
			} else {
				jsonResult.set(ResultState.REMOVE_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult; 
	}
	/**
	 * 显示编辑外网用户页面
	 * 
	 * @param iid
	 *            外网用户ID
	 * @return ModelAndView
	 */ 
	@RequestMapping(value = "modify_show")
	public ModelAndView showModify(int iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/pack/backstage/appuser/appuser_opr");
		AppUser appUser=appUserService.findByid(iid);

		modelAndView.addObject("url", "modify_submit.do");
		modelAndView.addObject("appuser", appUser);
		return modelAndView;
	}

	/**
	 * 编辑外网用户
	 * 
	 * @param outsideuser
	 *            外网用户实体
	 * @return String
	 */ 
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public JsonResult submitModify(AppUser appUser) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		String message = "";
		try {
			if(StringUtil.isNotEmpty(appUser.getPwd())){
				appUser.setPwd(Md5Util.md5encode(appUser.getPwd()));
			}
			isSuccess = appUserService.modify(appUser);
		} catch (OperationException e) {
			message = e.getMessage();
		}

		if (isSuccess) {
			jsonResult.set(ResultState.MODIFY_SUCCESS); 
		} else {
			jsonResult.set(ResultState.MODIFY_FAIL,message); 
		}

		return jsonResult;
	}
}
