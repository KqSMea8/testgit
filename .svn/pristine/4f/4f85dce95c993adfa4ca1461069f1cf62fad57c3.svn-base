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
public class OprInfoCountController {
	
	/**
	 * infoservice 信息service
	 */
	@Autowired
	private InfoCountService infoCountService;
	 
	/**
	 * 点赞页
	 * @param infoId infoId
	 * @param type type
	 * @return    设定参数 .
	 */
	@RequestMapping("good_show")
	public ModelAndView showadd(Integer infoId, Integer type){ 
		ModelAndView modelAndView = new ModelAndView("jmp/cms/infos/info_good");
		Integer siteId = UserSessionInfo.getCurrentUser().getSiteId();
		InfoCount infoCount = infoCountService.findByInfoId(infoId, type, "", siteId);
		//type 评论和报料的点赞  暂不做
		modelAndView.addObject("infoCount", infoCount);
		modelAndView.addObject("url", "good_submit.do");
		return modelAndView;
	}

	/**
	 * 点赞功能 
	 * @param titleId titleId
	 * @param type type
	 * @param goodCount goodCount
	 * @return    设定参数 .
	*/
	@RequestMapping("good_submit")
	@ResponseBody
	public JsonResult submitadd(Integer titleId, Integer type, Integer goodCount){
		JsonResult jsonResult = JsonResult.getInstance();
		Integer siteId = UserSessionInfo.getCurrentUser().getSiteId();
		boolean isSuccess = infoCountService.modifyGoodCount(titleId, type, goodCount, siteId);
		if (isSuccess) {
			jsonResult.set(ResultState.OPR_SUCCESS);
		} else {
			jsonResult.set(ResultState.OPR_FAIL);
		}   
		return jsonResult;
	}   
	
}