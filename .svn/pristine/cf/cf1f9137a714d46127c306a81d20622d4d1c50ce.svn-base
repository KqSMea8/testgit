package com.hanweb.jmp.cms.controller.matters.doc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;

import com.hanweb.jmp.cms.entity.matters.doc.DocCol;
import com.hanweb.jmp.cms.service.matters.doc.DocColService;
import com.hanweb.jmp.cms.service.matters.doc.DocService;
import com.hanweb.support.controller.CurrentUser;

@Controller 
@RequestMapping("manager/matter/doccol")
public class OprDocColController {

	/**
	 * docColService
	 */
	@Autowired
	private DocColService docColService;
	
	/**
	 * DocService
	 */
	@Autowired
	private DocService docService;
	
	/**
	 * 新增页面 
	 * @param request   request
	 * @return   ModelAndView
	 */
	@RequestMapping(value = "add_show")
	public ModelAndView showAdd(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("jmp/cms/matters/doc/doccol_opr");
		DocCol docCol = new DocCol();
		modelAndView.addObject("url", "add_submit.do");
		modelAndView.addObject("docCol", docCol);
		return modelAndView;
	}
	/**
	 * 新增提交
	 * @param docCol
	 * @return
	 */
	@RequestMapping(value = "add_submit")
	@ResponseBody
	public JsonResult submitAdd(DocCol docCol) {
		docCol.setCreateTime(DateUtil.stringtoDate(DateUtil.getCurrDateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		docCol.setOrderId(docColService.findMaxOrderId() + 1);
		docCol.setPid(2);
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		docCol.setSiteId(currentUser.getSiteId());
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = docColService.add(docCol);
			if (isSuccess) {
				jsonResult.set(ResultState.ADD_SUCCESS);
				jsonResult.addParam("refresh", "doc");
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
	 * 删除
	 * @param ids  ids
	 * @return JsonResult
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids,Integer siteId,String isremove) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = docColService.removeByIds(ids,siteId,isremove);
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
	 * 修改页面 
	 * @param request   request
	 * @param iid       iid
	 * @return   ModelAndView
	 */
	@RequestMapping(value = "modify_show")
	public ModelAndView showModify(HttpServletRequest request, Integer iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/cms/matters/doc/doccol_opr");
		DocCol docCol = docColService.findByIid(iid);
		modelAndView.addObject("url", "modify_submit.do");
		modelAndView.addObject("docCol", docCol);
		return modelAndView;
	}
	
	/**
	 * 修改数据库操作
	 * @param pictureCol
	 * @return   JsonResult
	 */
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public JsonResult submitModify(DocCol docCol) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			isSuccess = docColService.modify(docCol);
			
			//修改分类名称时修改实体表中pname
			docService.modifyPName(docCol.getSiteId(), docCol.getIid(), docCol.getName());
			
			if (isSuccess) {
				jsonResult.set(ResultState.OPR_SUCCESS);
				jsonResult.addParam("refresh", "doc");
			} else {
				jsonResult.set(ResultState.OPR_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;
	}
	
	/**
	 * 排序页面
	 * @param 
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "sort_show")
	public ModelAndView showSort(String orderId) {
		ModelAndView modelAndView = new ModelAndView("jmp/cms/matters/doc/doccol_sort");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		List<DocCol> docColList = docColService.findBySiteId(currentUser.getSiteId());
		modelAndView.addObject("url", "sort_submit.do");
		modelAndView.addObject("docColList", docColList);
		return modelAndView;
	}
	
	/**
	 * 修改排序
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
			isSuccess = docColService.modifyOrderIdById(ids, orderids);
			if (isSuccess) {
				jsonResult.set(ResultState.OPR_SUCCESS);
				jsonResult.addParam("refresh", "doc");
			} else {
				jsonResult.set(ResultState.OPR_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;
	}
	
}