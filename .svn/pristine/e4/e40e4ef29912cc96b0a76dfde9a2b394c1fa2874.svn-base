package com.hanweb.jmp.newspush.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.annotation.Permission;
import com.hanweb.common.permission.Allowed;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.newspush.news.entity.NewsDetail;
import com.hanweb.jmp.newspush.news.service.NewsDetailService;



/**
 * 消息回执操作控制器
 * 
 * @author Qianzq
 * 
 */
@Controller
@Permission(allowedGroup = Allowed.YES)
@RequestMapping("manager/infodetail")
public class OprNewsDetailController {
	
	@Autowired
	private NewsDetailService newsDetailService;
	
	/**
	 * 打开回执页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "receipt")
	public ModelAndView show(int iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/newspush/news/receipt_opr");

		NewsDetail infoDetail = newsDetailService.findByIid(iid);
		
		modelAndView.addObject("infodetail", infoDetail);
		return modelAndView;
	}
	
	/**
	 *  日志删除
	 * 
	 * @param ids
	 *            消息ID串 如:1,2,3,4
	 * @return
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids) {
		boolean isSuccess = true;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = newsDetailService.removeByIds(ids);
			if (isSuccess) {
				jsonResult.set(ResultState.REMOVE_SUCCESS);
			} else {
				jsonResult.set(ResultState.REMOVE_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.set(ResultState.REMOVE_FAIL);
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;
	}
	
	/**
	 *  日志清空
	 * @param infoId 信息id
	 * @return JsonResult 
	 */
	@RequestMapping(value = "clean")
	@ResponseBody
	public JsonResult clean(Integer infoId) {
		boolean isSuccess = true;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = newsDetailService.clean(infoId);
			if (isSuccess) {
				jsonResult.set(ResultState.REMOVE_SUCCESS);
			} else {
				jsonResult.set(ResultState.REMOVE_FAIL);
			}
		} catch (OperationException e) {
			e.printStackTrace();
			jsonResult.set(ResultState.REMOVE_FAIL);
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;
	}
}
