package com.hanweb.jmp.interfacesmanage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.interfacesmanage.entity.Interfaces;
import com.hanweb.jmp.interfacesmanage.entity.InterfacesParam;
import com.hanweb.jmp.interfacesmanage.service.InterfaceService;
import com.hanweb.jmp.interfacesmanage.service.InterfacesTypeService;

@Controller
@RequestMapping("manager/interface")
public class OprInterfaceController {

	@Autowired
	private InterfaceService interfaceService;

	@Autowired
	private InterfacesTypeService interfaceTypeService;

	/**
	 * 删除接口
	 * 
	 * @param ids
	 *            用户ID串 如:1,2,3,4
	 * @return
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = interfaceService.removeByIds(ids);
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
	 * 显示新增页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "add_show")
	public ModelAndView showAdd(Integer typeId) {
		ModelAndView modelAndView = new ModelAndView(
				"jmp/interfacesmanage/interface_opr");
		modelAndView.addObject("url", "add_submit.do");
		Interfaces inter = new Interfaces();
		inter.setTypeid(typeId);
		String typeName = "";
		if (typeId != 0) {
			typeName = interfaceTypeService.findByIid(typeId).getName();
		}
		modelAndView.addObject("inter", inter);
		modelAndView.addObject("typeName", typeName);
		return modelAndView;
	}

	/**
	 * 新增
	 * 
	 * @param inter
	 * @return
	 */
	@RequestMapping(value = "add_submit")
	@ResponseBody
	public JsonResult submitAdd(String editorValue, Interfaces inter) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = interfaceService.add(inter);
			if (isSuccess) {
				jsonResult.set(ResultState.ADD_SUCCESS);
				jsonResult.addParam("refresh",
						NumberUtil.getInt(inter.getIid()) + "");
			} else {
				jsonResult.set(ResultState.ADD_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;
	}

	/**
	 * 显示修改页面
	 * 
	 * @param iid
	 * @return
	 */
	@RequestMapping(value = "modify_show")
	public ModelAndView showModify(Integer iid) {
		ModelAndView modelAndView = new ModelAndView(
				"jmp/interfacesmanage/interface_opr");
		Interfaces inter = interfaceService.findByIid(iid);
		List<InterfacesParam> paramList = interfaceService.findParamsByIid(iid,
				null);
		if (inter != null) {
			modelAndView.addObject("inter", inter);
		}
		modelAndView.addObject("url", "modify_submit.do");
		modelAndView.addObject("paramList", paramList);
		int typeId = inter.getTypeid();
		modelAndView.addObject("typeName",
				interfaceTypeService.findByIid(typeId).getName());
		return modelAndView;
	}

	/**
	 * 修改
	 * 
	 * @param inter
	 * @return
	 */
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public JsonResult submitModify(String editorValue, Interfaces inter) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {

			isSuccess = interfaceService.modify(inter);
			if (isSuccess) {
				jsonResult.set(ResultState.MODIFY_SUCCESS);
				jsonResult.addParam("refresh",
						NumberUtil.getInt(inter.getIid()) + "");
			} else {
				jsonResult.set(ResultState.MODIFY_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;
	}

	/**
	 * 显示配置页面
	 * 
	 * @param iid
	 *            用户iid
	 * @return
	 */
	@RequestMapping(value = "show_examine")
	public ModelAndView showConfigure(Integer iid) {
		
		ModelAndView modelAndView = new ModelAndView(
				"jmp/interfacesmanage/interfaces_configure");
		List<InterfacesParam> params = interfaceService.findParamByName(iid);
		Interfaces inter= interfaceService.findByIid(iid);
		modelAndView.addObject("params", params);
		modelAndView.addObject("inter", inter);
		modelAndView.addObject("url", "isexamine_modify.do");
		return modelAndView;
	}

}
