package com.hanweb.jmp.cms.controller.matters.picture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.annotation.Permission;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;

import com.hanweb.jmp.cms.service.matters.picture.PictureService;
import com.hanweb.support.controller.CurrentUser;

@Controller
@Permission
@RequestMapping("manager/matter/picture/recycle")
public class OprPictureRecycleController {

	/**
	 * infoService
	 */
	@Autowired
	private PictureService pictureService;

	/** 
	 * 删除提交
	 * @param ids 主键id串
	 * @param siteId 网站id
	 * @return JsonResult
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(PictureFormController picture,String ids, Integer siteId) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		picture.setSiteId(currentUser.getSiteId());
		siteId = picture.getSiteId();
		try {
			isSuccess = pictureService.removeByIds(ids,siteId);
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
	 * 清空提交
	 * @param colId 栏目id
	 * @return JsonResult
	 * @throws OperationException  
	 */
	@RequestMapping(value = "clean")
	@ResponseBody
	public JsonResult clean(String [] ids,String classId,Integer siteId) throws OperationException {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		siteId = UserSessionInfo.getCurrentUser().getSiteId();
		ids = pictureService.findBySiteIds(siteId,classId,1);
		try {
			isSuccess = pictureService.removeAllByIsremove(1 ,classId,ids,siteId);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		if (isSuccess) {
			jsonResult.set(ResultState.OPR_SUCCESS, "清空成功");
		} else {
			jsonResult.set(ResultState.OPR_FAIL, "清空失败");
		}
		return jsonResult;    
	}
	
	/**
	 * 还原提交
	 * @param siteId    网站id
	 * @return JsonResult
	 * @throws OperationException 
	 */
	@RequestMapping(value = "restore")
	@ResponseBody
	public JsonResult restore( String ids, Integer siteId) throws OperationException {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		isSuccess = pictureService.modifyIsRemove(0, ids, siteId);
		if (isSuccess) {
			jsonResult.set(ResultState.OPR_SUCCESS, "还原成功");
		} else {
			jsonResult.set(ResultState.OPR_FAIL, "还原失败");
		}
		return jsonResult;
	}
	
}