package com.hanweb.jmp.cms.controller.matters.video;

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

import com.hanweb.jmp.cms.entity.matters.video.VideoCol;
import com.hanweb.jmp.cms.service.matters.video.VideoColService;
import com.hanweb.jmp.cms.service.matters.video.VideoService;
import com.hanweb.support.controller.CurrentUser;

@Controller 
@RequestMapping("manager/matter/videocol")
public class OprVideoColController {

	@Autowired
	private VideoColService videoColService;
	
	@Autowired
	private VideoService videoService;
	
	/**
	 * 新增页面 
	 * @param request   request
	 * @return   ModelAndView
	 */
	@RequestMapping(value = "add_show")
	public ModelAndView showAdd(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("jmp/cms/matters/video/videocol_opr");
		VideoCol videoCol = new VideoCol();
		modelAndView.addObject("url", "add_submit.do");
		modelAndView.addObject("videoCol", videoCol);
		return modelAndView;
	}
	
	/**
	 * 新增提交
	 * @param pictureCol
	 * @return
	 */
	@RequestMapping(value = "add_submit")
	@ResponseBody
	public JsonResult submitAdd(VideoCol videoCol) {
		videoCol.setCreateTime(DateUtil.stringtoDate(DateUtil.getCurrDateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		videoCol.setOrderId(videoColService.findMaxOrderId() + 1);
		videoCol.setPid(1);
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		videoCol.setSiteId(currentUser.getSiteId());
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = videoColService.add(videoCol);
			if (isSuccess) {
				jsonResult.set(ResultState.ADD_SUCCESS);
				jsonResult.addParam("refresh", "video");
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
			isSuccess = videoColService.removeByIds(ids,siteId,isremove);
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
		ModelAndView modelAndView = new ModelAndView("jmp/cms/matters/video/videocol_opr");
		VideoCol videoCol = videoColService.findByIid(iid);
		modelAndView.addObject("url", "modify_submit.do");
		modelAndView.addObject("videoCol", videoCol);
		return modelAndView;
	}
	
	/**
	 * 修改数据库操作 
	 * @param videoCol   
	 * @return   JsonResult
	 */
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public JsonResult submitModify(VideoCol videoCol) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			isSuccess = videoColService.modify(videoCol);
			
			//修改分类名称时修改实体表中pname
			videoService.modifyPName(videoCol.getSiteId(), videoCol.getIid(), videoCol.getName());
			
			if (isSuccess) {
				jsonResult.set(ResultState.OPR_SUCCESS);
				jsonResult.addParam("refresh", "video");
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
		ModelAndView modelAndView = new ModelAndView("jmp/cms/matters/video/videocol_sort");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		List<VideoCol> videoColList = videoColService.findBySiteId(currentUser.getSiteId());
		modelAndView.addObject("url", "sort_submit.do");
		modelAndView.addObject("videoColList", videoColList);
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
			isSuccess = videoColService.modifyOrderIdById(ids, orderids);
			if (isSuccess) {
				jsonResult.set(ResultState.OPR_SUCCESS);
				jsonResult.addParam("refresh", "video");
			} else {
				jsonResult.set(ResultState.OPR_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;
	}
	
}