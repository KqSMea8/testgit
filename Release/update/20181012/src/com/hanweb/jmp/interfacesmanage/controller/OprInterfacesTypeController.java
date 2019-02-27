package com.hanweb.jmp.interfacesmanage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.interfacesmanage.entity.InterfacesType;
import com.hanweb.jmp.interfacesmanage.service.InterfacesTypeService;

/**
 * 接口类型控制器
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("manager/interfaceType")
public class OprInterfacesTypeController {

	@Autowired
	private InterfacesTypeService interfaceTypeService;

	/**
	 * 删除接口类型
	 * 
	 * @param iid
	 * @return
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids, InterfacesType interfaceType) {

		boolean isSuccess = false;

		JsonResult jsonResult = JsonResult.getInstance();

		try {
			isSuccess = interfaceTypeService.removeByids(ids);
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
	 * 显示新增接口类型页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "add_show")
	public ModelAndView showAdd(Integer pid) {
		InterfacesType interfaceType = new InterfacesType();
		interfaceType.setPid(pid);
		String pname = "";
		if (pid != 0) {
			pname = interfaceTypeService.findByIid(pid).getName();
		}
		interfaceType.setPname(pname);
		ModelAndView modelAndView = new ModelAndView(
				"jmp/interfacesmanage/interfaceType_opr");
		modelAndView.addObject("url", "add_submit.do");
		modelAndView.addObject("interfaceType", interfaceType);

		return modelAndView;
	}

	/**
	 * 新增接口类型
	 * 
	 * @param interfaceType
	 * @return
	 */
	@RequestMapping(value = "add_submit")
	@ResponseBody
	public JsonResult submitAdd(InterfacesType interfaceType) {
		boolean isSuccess = false;

		JsonResult jsonResult = JsonResult.getInstance();

		if (interfaceType.getPid() == null) {
			interfaceType.setPid(0);
		}

		try {
			isSuccess = interfaceTypeService.add(interfaceType);
			if (isSuccess) {
				jsonResult.set(ResultState.ADD_SUCCESS);
				jsonResult.addParam("refresh",
						NumberUtil.getInt(interfaceType.getPid()) + "");
			} else {
				jsonResult.set(ResultState.ADD_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}

		return jsonResult;
	}

	/**
	 * 显示修改接口类型页面
	 * 
	 * @param iid
	 * @return
	 */
	@RequestMapping(value = "modify_show")
	public ModelAndView showModify(Integer iid) {
		ModelAndView modelAndView = new ModelAndView(
				"jmp/interfacesmanage/interfaceType_opr");
		InterfacesType interfaceType = interfaceTypeService.findByIid(iid);
		String pname = "";
		if (interfaceType != null) {
			int pid = interfaceType.getPid();
			if (pid != 0) {
				pname = interfaceTypeService.findByIid(pid).getName();
			}
			interfaceType.setPname(pname);
			modelAndView.addObject("interfaceType", interfaceType);
		}
		modelAndView.addObject("url", "modify_submit.do");
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
	public JsonResult submitModify(String ids, InterfacesType interfaceType) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {

			isSuccess = interfaceTypeService.modify(interfaceType);
			if (isSuccess) {
				jsonResult.set(ResultState.MODIFY_SUCCESS);
				jsonResult.addParam("refresh",
						NumberUtil.getInt(interfaceType.getPid()) + "");
				jsonResult.addParam("remove",
						NumberUtil.getInt(interfaceType.getIid()) + "");
			} else {
				jsonResult.set(ResultState.MODIFY_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;
	}

}
