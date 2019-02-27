package com.hanweb.jmp.sys.controller.field;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.annotation.Permission;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.sys.entity.field.Field;
import com.hanweb.jmp.sys.service.field.FieldService;

@Controller
@Permission
@RequestMapping("manager/site")
public class OprFieldController {
	
	/**
	 * fieldService
	 */
	@Autowired
	private FieldService fieldService;
	
	/**
	 * 信息字段展示
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	@RequestMapping("field_show")
	public ModelAndView showFieldAdd(String siteId){
		ModelAndView modelAndView = new ModelAndView("jmp/sys/field/field_opr"); 
		FieldFormBean fieldFromBean = new FieldFormBean();
		fieldFromBean.setSiteId(NumberUtil.getInt(siteId));
		modelAndView.addObject("url", "addfield_submit.do");
		modelAndView.addObject("field", fieldFromBean); 
		modelAndView.addObject("siteId", siteId); 
		return modelAndView;
	}
	
	/**
	 * 新增字段提交
	 * @param fieldFrom fieldFrom
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	@RequestMapping("addfield_submit")	
	@ResponseBody
	public JsonResult submitFieldAdd(FieldFormBean fieldFrom, Integer siteId) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			isSuccess = fieldService.add(fieldFrom, siteId);
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
	 * 修改字段页
	 * @param iid iid
	 * @return    设定参数 .
	 */
	@RequestMapping("modifyfield_show")
	public ModelAndView showFieldModify(String iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/sys/field/field_opr");
		int fieldId = NumberUtil.getInt(iid); 
		Field fieldEn = fieldService.findByIid(fieldId);  
		modelAndView.addObject("url", "modifyfield_submit.do");
		modelAndView.addObject("field", fieldEn); 
		return modelAndView;
	}
	
	/**
	 * 信息修改提交
	 * @param field
	 *            栏目对象
	 * @return 操作信息
	 */
	@RequestMapping(value = "modifyfield_submit")
	@ResponseBody
	public JsonResult submitModify(FieldFormBean field) { 
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = fieldService.modify(field);
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
	 * 信息字段设置
	 * @param siteId siteId
	 * @return ModelAndView
	 */
	@RequestMapping("sitefield_show")
	public ModelAndView showFieldLevel(Integer siteId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("jmp/site/choosefield_opr");
		modelAndView.addObject("fieldUrl", ControllerUtil.getAbsolutePath("/manager/site/field/list.do?siteId="+siteId));
		return modelAndView;
	}
	
	/**
	 * 删除信息字段
	 * @param ids ids
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "field_remove")
	@ResponseBody
	public JsonResult removeField(String ids, Integer siteId) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = fieldService.removeByIds(ids, siteId);
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
	 * 字段排序页
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "sortfield_show")
	@ResponseBody
	public ModelAndView order(String siteId) {
		ModelAndView modelAndView =new ModelAndView("jmp/sys/field/field_sort"); 
		List<Field> fieldList = fieldService.findAllBySiteid(NumberUtil.getInt(siteId));
		modelAndView.addObject("url", "sortfield_submit.do");
		modelAndView.addObject("fieldList", fieldList);
		modelAndView.addObject("siteid", siteId);
		return modelAndView;
	}
	
	/**
	 * 字段排序提交
	 * @param ids ids
	 * @param orderids orderids
	 * @param colid colid
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "sortfield_submit")
	@ResponseBody
	public String submitSort(String ids, String orderids, String colid) {
		boolean isSuccess = false;
		String message = "";
		try {
			isSuccess = fieldService.modifyOrderIdById(ids, orderids);
		} catch (OperationException e) {
			message = e.getMessage();
		}
		Script script = Script.getInstance();
		if (isSuccess) {
			script.closeDialogAndReload();
		} else {
			script.addAlert("排序失败！" + message);
		} 
		return script.getScript();
	}

}