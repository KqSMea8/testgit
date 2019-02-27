package com.hanweb.jmp.sys.controller.feedback;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.annotation.Permission;
import com.hanweb.common.permission.Allowed;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.sys.entity.feedback.FeedBack;
import com.hanweb.jmp.sys.service.feedback.FeedBackService;

@Controller
@Permission(module = "feedback", allowedAdmin = Allowed.YES)
@RequestMapping("manager/feedback")
public class OprFeedBackInfoController {
	
	/**
	 * feedBackService
	 */
	@Autowired
	private FeedBackService feedBackService;

	/**
	 * 删除
	 * @param ids  ids
	 * @return  JsonResult
	 */
	@RequestMapping("remove")
	@ResponseBody
	public JsonResult remove(String ids) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			isSuccess = feedBackService.removeByIds(ids);
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
	 * @param request  request
	 * @param iid  iid
	 * @return  ModelAndView
	 * @throws OperationException 
	 * @throws OperationException 
	 */
	@RequestMapping("modify_show")
	public ModelAndView showModify(HttpServletRequest request, Integer iid) throws OperationException {
		ModelAndView modelAndView = new ModelAndView("jmp/sys/feedback/feedback_opr");
		FeedBack feedBack = feedBackService.findByIid(iid);
		boolean isSuccess = feedBackService.modify(iid);
		String time = DateUtil.dateToString(feedBack.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
		modelAndView.addObject("url", "modify_submit.do");
		modelAndView.addObject("feedBack", feedBack);
		modelAndView.addObject("strTime", time);
		modelAndView.addObject("isSuccess", isSuccess);
		return modelAndView;
	}
	
}