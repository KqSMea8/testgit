package com.hanweb.jmp.cms.controller.infos.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.cms.service.infos.InfoService;
 
@Controller
//@Permission(module = "/info", allowedAdmin = Allowed.YES)
@RequestMapping("manager/recycle")
public class OprRecycleController {
	
	/**
	 * infoService
	 */
	@Autowired
	private InfoService infoService;

	/** 
	 * 删除
	 * @param ids 主键id串
	 * @param siteId 网站id
	 * @return JsonResult
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids, Integer siteId) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = infoService.removeByIds(ids, siteId, 0);
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
	 * 信息清空
	 * @param colId 栏目id
	 * @return JsonResult
	 * @throws OperationException  
	 */
	@RequestMapping(value = "clean")
	@ResponseBody
	public JsonResult clean(int colId) throws OperationException {
		boolean isSuccess = false;
		Integer siteId = UserSessionInfo.getCurrentUser().getSiteId();
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = infoService.removeByColId(colId, siteId, 1);
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
	 * 还原信息
	 * @param ids       信息id串
	 * @param siteId    网站id
	 * @return JsonResult
	 */
	@RequestMapping(value = "restore")
	@ResponseBody
	public JsonResult restore(String ids, Integer siteId) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		//将信息删除状态修改为0
		try{
			isSuccess = infoService.modifyIsRemove(0, ids, siteId);
			if (isSuccess) {
				jsonResult.set(ResultState.OPR_SUCCESS, "还原成功");
			} else {
				jsonResult.set(ResultState.OPR_FAIL, "还原失败");
			}
	    } catch (OperationException e) {
		    jsonResult.setMessage(e.getMessage());
	    }
		return jsonResult;
	}
	
}