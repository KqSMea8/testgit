package com.hanweb.jmp.sys.controller.field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.annotation.Permission;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.cms.controller.cols.ColFieldFormBean;
import com.hanweb.jmp.cms.entity.cols.ColField;
import com.hanweb.jmp.cms.service.cols.ColFieldService;

@Controller
@Permission
@RequestMapping("manager/site")
public class OprColFieldController {
	
	/**
	 * colfieldService 
	 */
	@Autowired
	private ColFieldService colfieldService;
	
	
	/**
	 * 新增栏目字段页
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	@RequestMapping("colfield_show")
	public ModelAndView showColFieldAdd(Integer siteId){
		ModelAndView modelAndView = new ModelAndView("jmp/sys/field/colfield_opr"); 
		ColFieldFormBean colfieldFromBean = new ColFieldFormBean();
		colfieldFromBean.setSiteId(siteId);  
		modelAndView.addObject("url", "addcolfield_submit.do");
		modelAndView.addObject("colfield", colfieldFromBean); 
		modelAndView.addObject("siteId", siteId);
		return modelAndView;
	}
	
	/**
	 * 新增栏目字段提交
	 * @param colfieldFrom colfieldFrom
	 * @return    设定参数.
	 */
	@RequestMapping("addcolfield_submit")
	@ResponseBody
	public JsonResult submitColFieldAdd(ColFieldFormBean colfieldFrom) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			isSuccess = colfieldService.add(colfieldFrom); 
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
	 * 修改栏目字段页
	 * @param iid iid
	 * @return    设定参数 .
	 */
	@RequestMapping("modifycolfield_show")
	public ModelAndView showColFieldModify(String iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/sys/field/colfield_opr");
		int fieldId = NumberUtil.getInt(iid); 
		ColField colfieldEn = colfieldService.findByIid(fieldId);  
		modelAndView.addObject("url", "modifycolfield_submit.do");
		modelAndView.addObject("colfield", colfieldEn); 
		return modelAndView;
	}
	
	/**
	 * 修改提交
	 * @param colfield colfield
	 *            栏目对象
	 * @return 操作信息
	 */
	@RequestMapping(value = "modifycolfield_submit")
	@ResponseBody
	public JsonResult submitColModify(ColFieldFormBean colfield){ 
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = colfieldService.modify(colfield);
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
	 * 删除栏目字段
	 * @param ids 
	 *          字段id串
	 * @return  结果信息
	 */
	@RequestMapping(value = "colfield_remove")
	@ResponseBody
	public JsonResult removeColField(String ids) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = colfieldService.removeByIds(ids);
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