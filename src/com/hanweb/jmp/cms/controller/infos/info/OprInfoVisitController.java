package com.hanweb.jmp.cms.controller.infos.info;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.cms.entity.infos.InfoCount;
import com.hanweb.jmp.cms.service.infos.InfoCountService;

@Controller
//@Permission(module = "/info", allowedAdmin = Allowed.YES)
@RequestMapping("manager/info")
public class OprInfoVisitController {
	
	/**
	 * infoservice 信息service
	 */
	@Autowired
	private InfoCountService infoCountService;
	 
	/**
	 * 信息展示
	 * @param infoId 信息id
	 * @param type type
	 * @return    设定参数 .
	*/
	@RequestMapping("visit_show")
	public ModelAndView showadd(Integer infoId, Integer type){ 
		ModelAndView modelAndView = new ModelAndView("jmp/cms/infos/info_visit");
		Integer siteId = UserSessionInfo.getCurrentUser().getSiteId();
		InfoCount infoCount = infoCountService.findByInfoId(infoId, type, "", siteId);
		modelAndView.addObject("infoCount", infoCount); 
		modelAndView.addObject("url", "visit_submit.do");
		return modelAndView;
	}
   
	/**
	 * 展示
	 * @param titleId titleId
	 * @param type type
	 * @param visitCount visitCount
	 * @return    设定参数 .
	*/
	@RequestMapping("visit_submit")
	@ResponseBody
	public JsonResult submitadd(Integer titleId, Integer type, Integer visitCount){
		JsonResult jsonResult = JsonResult.getInstance();
		Integer siteId = UserSessionInfo.getCurrentUser().getSiteId();
		boolean isSuccess = infoCountService.modifyVisitCount(titleId, type, visitCount, siteId);
		if (isSuccess) {
			jsonResult.set(ResultState.OPR_SUCCESS);
		} else {
			jsonResult.set(ResultState.OPR_FAIL);
		}   
		return jsonResult;
	}   
	
}