package com.hanweb.jmp.newspush.peoplelist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.annotation.Permission;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.newspush.peoplelist.entity.PeopleList;
import com.hanweb.jmp.newspush.peoplelist.service.PeoplelistService;
import com.hanweb.support.controller.CurrentUser;

/**
 * 角色操作控制器
 * 
 * @author ZhangC
 * 
 */
@Controller
@Permission(module = "role")
@RequestMapping("manager/peoplelist")
public class OprPeoplelistController {

	@Autowired
	private PeoplelistService peoplelistService;

	/**
	 * 打开新增页面
	 * 
	 * @return
	 */
	@Permission(function = "add_show")
	@RequestMapping(value = "add_show")
	public ModelAndView showAdd() {
		ModelAndView modelAndView = new ModelAndView("jmp/newspush/peoplelist/peoplelist_opr");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		PeopleList peoplelist = new PeopleList();
		peoplelist.setSiteId(currentUser.getSiteId());
		modelAndView.addObject("peoplelist", peoplelist);
		modelAndView.addObject("url", "add_submit.do");
		return modelAndView;
	}

	/**
	 * 打开编辑页面
	 * 
	 * @param iid
	 *            角色ID
	 * @return
	 */
	@Permission(function = "modify_show")
	@RequestMapping(value = "modify_show")
	public ModelAndView showModify(int iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/newspush/peoplelist/peoplelist_opr");

		PeopleList peoplelist = peoplelistService.findByIid(iid);

		modelAndView.addObject("url", "modify_submit.do");
		modelAndView.addObject("peoplelist", peoplelist);
		return modelAndView;
	}

	/**
	 * 提交新增
	 * 
	 * @param roleFormBean
	 *            角色FormBean
	 * @return
	 */
	@Permission(function = "add_submit")
	@RequestMapping(value = "add_submit")
	@ResponseBody
	public JsonResult submitAdd(PeopleList peoplelist) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		int siteId = currentUser.getSiteId();
		try {
			peoplelist.setSiteId(siteId);
			isSuccess = peoplelistService.add(peoplelist);
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
	 * 提交修改
	 * 
	 * @param roleFormBean
	 *            角色FormBean
	 * @return
	 */
	@Permission(function = "modify_submit")
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public JsonResult submitModify(PeopleList peoplelist) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		int siteId = currentUser.getSiteId();
		try {
			peoplelist.setSiteId(siteId);
			isSuccess = peoplelistService.modify(peoplelist);
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
	 * 角色删除
	 * 
	 * @param ids
	 *            角色ID串 如:1,2,3,4
	 * @return
	 */
	@Permission(function = "remove")
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = peoplelistService.removeByIds(ids);
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

	
}
