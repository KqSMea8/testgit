package com.hanweb.jmp.apps.controller.numbersense.numsensephone;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.complat.exception.OperationException;

import com.hanweb.jmp.apps.entity.numbersense.NumSensePhone;
import com.hanweb.jmp.apps.service.numbersense.NumSensePhoneService;

/**
 * 号码分类操作控制器
 * @author ZDJ
 */
@Controller
@RequestMapping("manager/plugins/numsense/phone")
public class OprNumSensePhone {
	
	/**
	 * shopColService
	 */
	@Autowired
	private NumSensePhoneService numSensePhoneService;
	
	/**
	 * 号码新增
	 * @param siteId 网站ID
	 * @param colId 分类ID
	 * @return modelAndView
	 */
	@RequestMapping(value = "add_show")
	public ModelAndView showAdd(Integer siteId, Integer colId) {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/numbersense/phone_opr");
		NumSensePhone numSensePhone = new NumSensePhone();
		numSensePhone.setSiteId(siteId);
		numSensePhone.setColId(NumberUtil.getInt(colId));
		modelAndView.addObject("numsense", numSensePhone);
		modelAndView.addObject("url", "add_submit.do");
		return modelAndView;
	}
	
	/**
	 * 新增提交
	 * @param phone 号码实体
	 * @return json
	 */
	@RequestMapping(value = "add_submit")
	@ResponseBody
	public String saveAdd(NumSensePhoneFormBean phone, String flag) {
		String message = "";
		boolean isSuccess = false;
		if(StringUtil.isNotEmpty(phone.getBgColor())){
			phone.setBgColor(StringUtil.getString(phone.getBgColor().replace("#", "")));
		}
		try {
			isSuccess = numSensePhoneService.add(phone, flag);
		} catch (OperationException e) {
			message = e.getMessage();
		}
		Script script = Script.getInstanceWithJsLib();
		if (isSuccess) {
			script.addScript("parent.refreshParentWindow();parent.closeDialog();");
		} else {
			script.addAlert("新增失败！" + message); 
		}
		return script.getScript(); 
	}
	
	/**
	 * 修改页面
	 * @param request
	 * @param iid  
	 *             分类ID
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "modify_show")
	public ModelAndView showModify(Integer iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/numbersense/phone_opr");
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(jmpUrl.endsWith("/")){
			jmpUrl=jmpUrl.substring(0, jmpUrl.length()-1);
		} 
		NumSensePhone phone = numSensePhoneService.findById(iid);
		if(StringUtil.isNotEmpty(phone.getBgColor())) {
			modelAndView.addObject("bgColor", "#"+phone.getBgColor());
		}
		modelAndView.addObject("jmpUrl", jmpUrl);
		modelAndView.addObject("numsense", phone);
		modelAndView.addObject("url", "modify_submit.do");
		return modelAndView;
	}
	
	/**
	 * 修改提交
	 * @param phone 号码实体
	 * @return json
	 */
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public String submitModify(NumSensePhoneFormBean phone, String flag) {
		String message = "";
		boolean isSuccess = false;
		if(StringUtil.isNotEmpty(phone.getBgColor())){
			phone.setBgColor(StringUtil.getString(phone.getBgColor().replace("#", "")));
		}
		try {
			isSuccess = numSensePhoneService.modify(phone, flag);
		} catch (OperationException e) {
			message = e.getMessage();
		}
		Script script = Script.getInstanceWithJsLib();
		if (isSuccess) {
			script.addScript("parent.refreshParentWindow();parent.closeDialog();");  
		} else {
			script.addAlert("修改失败！" + message); 
		}
		return script.getScript(); 
	}
	
	/**
	 * 删除分类
	 * @param ids 
	 *          分类id串
	 * @return  结果信息
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			isSuccess = numSensePhoneService.removeByIds(ids);
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
	 * 商品排序展现
	 * @param colId 商品所属分类ID
	 * @return ModelAndView
	 */
	@RequestMapping(value = "sort_show")
	@ResponseBody
	public ModelAndView order(Integer colId) {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/numbersense/phone_sort");
		List<NumSensePhone> phones = numSensePhoneService.findByColId(NumberUtil.getInt(colId));
		modelAndView.addObject("url", "sort_submit.do");
		modelAndView.addObject("phones", phones);
		return modelAndView;
	}
	
	/**
	 * 商品排序提交
	 * @param ids 商品id
	 * @param orderids 排序id
	 * @return json
	 */
	@RequestMapping(value = "sort_submit")
	@ResponseBody
	public JsonResult submitSort(String ids, String orderids) {
		boolean isSuccess = false; 
		JsonResult jsonResult = JsonResult.getInstance();  
		try {
			if(null == ids || ids.length() <= 0){
				isSuccess = true;
			}else{
				isSuccess = numSensePhoneService.modifyOrderIdById(ids, orderids);
			}
			if (isSuccess) {
				jsonResult.set(ResultState.OPR_SUCCESS); 
			} else {
				jsonResult.set(ResultState.OPR_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;   
	}
		
}