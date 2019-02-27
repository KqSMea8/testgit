package com.hanweb.jmp.apps.controller.broke;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody; 

import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.support.controller.CurrentUser;

import com.hanweb.jmp.apps.entity.broke.BrokeType;
import com.hanweb.jmp.apps.service.broke.BrokeTypeService;

@Controller 
@RequestMapping("manager/broketype")
public class OprBrokeTypeController {
	
	/**
	 *  brokeTypeService
	 */
	@Autowired
	BrokeTypeService brokeTypeService;

	/**
	 * 新增报料分类页面
	 * @param request   request
	 * @return   ModelAndView
	 */
	@RequestMapping(value = "add_show")
	public ModelAndView showAdd(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/broke/broketype_opr");
		BrokeType brokeType = new BrokeType();
		modelAndView.addObject("url", "add_submit.do");
		modelAndView.addObject("brokeType", brokeType);
		return modelAndView;
	}

	/**
	 * 新增提交
	 * @param brokeType    brokeType
	 * @return   JsonResult
	 */
	@RequestMapping(value = "add_submit")
	@ResponseBody
	public JsonResult submitAdd(BrokeType brokeType) {
		brokeType.setCreateTime(DateUtil.stringtoDate(DateUtil.getCurrDateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		brokeType.setOrderId(brokeTypeService.findMaxOrderId() + 1);
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		brokeType.setSiteId(currentUser.getSiteId());
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = brokeTypeService.add(brokeType);
			if (isSuccess) {
				jsonResult.set(ResultState.ADD_SUCCESS);
				jsonResult.addParam("refresh", "broke");
			} else {
				jsonResult.set(ResultState.ADD_FAIL);
			}
		} catch (OperationException e) {
			e.printStackTrace();
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;
	}

	/**
	 * 删除报料分类
	 * @param ids  ids
	 * @param colname  colname
	 * @return JsonResult
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids, String colname) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = brokeTypeService.removeByIds(ids);
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
	 * 排序页面
	 * @param typeid    typeid
	 * @param taskid   taskid
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "sort_show")
	public ModelAndView showSort(String typeid, String taskid) {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/broke/broketype_sort");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		List<BrokeType> brokeTypeList = brokeTypeService.findBySiteId(currentUser.getSiteId());
		modelAndView.addObject("url", "sort_submit.do");
		modelAndView.addObject("brokeTypeList", brokeTypeList);
		return modelAndView;
	}

	/**
	 * 修改排序提交
	 * @param ids      ids
	 * @param orderids orderids
	 * @return  JsonResult
	 */
	@RequestMapping(value = "sort_submit")
	@ResponseBody
	public JsonResult submitSort(String ids, String orderids) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			isSuccess = brokeTypeService.modifyOrderIdById(ids, orderids);
			if (isSuccess) {
				jsonResult.set(ResultState.OPR_SUCCESS);
				jsonResult.addParam("refresh", "broke");
			} else {
				jsonResult.set(ResultState.OPR_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;
	}

	/**
	 * 修改报料分类页面
	 * @param request   request
	 * @param iid       iid
	 * @return   ModelAndView
	 */
	@RequestMapping(value = "modify_show")
	public ModelAndView showModify(HttpServletRequest request, Integer iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/broke/broketype_opr");
		BrokeType brokeType = brokeTypeService.findByIid(iid);
		modelAndView.addObject("url", "modify_submit.do");
		modelAndView.addObject("brokeType", brokeType);
		return modelAndView;
	}

	/**
	 * 修改提交
	 * @param brokeType   brokeType
	 * @return   JsonResult
	 */
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public JsonResult submitModify(BrokeType brokeType) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			isSuccess = brokeTypeService.modify(brokeType);
			if (isSuccess) {
				jsonResult.set(ResultState.OPR_SUCCESS);
				jsonResult.addParam("refresh", "plugins");
			} else {
				jsonResult.set(ResultState.OPR_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;
	}
	
	/**
	 * 弹出层页面
	 * @return modelAndView
	 */
	@RequestMapping(value = "frame_show")
	public ModelAndView showFrame(){
		ModelAndView modelAndView = new ModelAndView("jmp/apps/broke/brokeframe");
		return modelAndView;
	}
	
}