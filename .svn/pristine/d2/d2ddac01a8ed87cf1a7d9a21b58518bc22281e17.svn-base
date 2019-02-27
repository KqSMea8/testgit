package com.hanweb.jmp.pack.backstage.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.pack.entity.Client;
import com.hanweb.jmp.pack.service.AppService;
import com.hanweb.jmp.pack.service.ClientService;



@Controller 
@RequestMapping("manager/app")
public class OprAppController {
	/**
	 * clientService
	 */
	@Autowired
	private ClientService clientService;
	
	/**
	 * appService
	 */
	@Autowired
	private AppService appService;
	
	/**
	 * 显示发送json
	 * 
	 * @param iid iid
	 * @return ModelAndView
	 */
	@RequestMapping(value = "send")
	public ModelAndView showSendJson(Integer iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/pack/backstage/app/sendJson");
		Client client = clientService.findByIid(iid);
		modelAndView.addObject("client", client);
		return modelAndView;
	}
	
	/**
	 * 显示返回json
	 * 
	 * @param iid iid
	 * @return ModelAndView
	 */
	@RequestMapping(value = "back")
	public ModelAndView showBackJson(Integer iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/pack/backstage/app/backJson");
		Client client = clientService.findByIid(iid);
		modelAndView.addObject("client", client);
		return modelAndView;
	}
	
	/**
	 * 显示回调json
	 * 
	 * @param iid iid
	 * @return ModelAndView
	 */
	@RequestMapping(value = "callback")
	public ModelAndView showCallBackJson(Integer iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/pack/backstage/app/callbackJson");
		Client client = clientService.findByIid(iid);
		modelAndView.addObject("client", client);
		return modelAndView;
	}
	
	/**
	 * 重新打包
	 * 
	 * @param iid iid
	 * @return JsonResult
	 */
	@RequestMapping(value = "restart")
	@ResponseBody
	public JsonResult restart(Integer iid){
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		Client client = clientService.findByIid(iid);
		isSuccess = appService.obtGenerate(client.getSendJson(), client.getVersion(), 
				client.getSiteId(), client.getClientType());
		if (isSuccess) {
			client.setStatus(1);
			clientService.update(client);
			jsonResult.setMessage("打包参数发送成功，正在打包请稍后。");
				
		} else {
			jsonResult.setMessage("重新打包失败！");
		}
		
		return jsonResult;
	}
	
	/**
	 * 
	 * auditInfo:(这里用一句话描述这个方法的作用).
	 *
	 * @param ids ids
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "audit")
	@ResponseBody
	public JsonResult auditInfo(String ids, Integer siteId) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = clientService.modifyAudit(ids, siteId);
			if (isSuccess) {
				jsonResult.set(ResultState.OPR_SUCCESS, "审核成功");
			} else {
				jsonResult.set(ResultState.OPR_FAIL, "审核失败");
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;  
	}
	
	/**
	 * 
	 * unauditInfo:(这里用一句话描述这个方法的作用).
	 *
	 * @param ids ids
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "unaudit")
	@ResponseBody
	public JsonResult unauditInfo(String ids, Integer siteId) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = clientService.modifyUnAudit(ids, siteId);
			if (isSuccess) {
				jsonResult.set(ResultState.OPR_SUCCESS, "撤审成功");
			} else {
				jsonResult.set(ResultState.OPR_FAIL, "撤审失败");
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;   
	}

}
