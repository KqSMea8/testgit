package com.hanweb.jmp.sys.controller.ditch;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.annotation.Permission;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.sys.entity.ditch.Ditch;
import com.hanweb.jmp.sys.service.ditch.DitchService;
import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping("manager/ditch")
public class OprDitchController {
	
	/**
	 * DitchService
	 */
	@Autowired
	private DitchService ditchService;
	
	/**
	 * 新增展示页
	 * @return
	 */
	@RequestMapping("add_show")
	public ModelAndView showAdd(){
		ModelAndView modelAndView = new ModelAndView("jmp/sys/ditch/ditch_opr");
		Ditch ditch = new Ditch();
		modelAndView.addObject("url", "add_submit.do");
		modelAndView.addObject("ditch", ditch);
		return modelAndView;
	}

	/**
	 * 新增提交页
	 * @param ditch
	 * @return
	 */
	@RequestMapping("add_submit")
	@ResponseBody
	public JsonResult add(Ditch ditch){
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		//获取当前用户的siteId
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		
		try {
			ditch.setCreateTime(new Date());
			ditch.setSiteId(siteId);
			ditch.setEnable(1);
			isSuccess = ditchService.add(ditch);
			if (isSuccess) {
				jsonResult.set(ResultState.ADD_SUCCESS);
			} else {
				jsonResult.set(ResultState.ADD_FAIL);
			}
		} catch (OperationException  e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult; 
		
	}
	
	/**
	 * URL地址验证
	 * @param url  url
	 * @return  JsonResult
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("testurl")
	@ResponseBody
	public JsonResult testUrl(String url) {
		JsonResult jsonResult = JsonResult.getInstance();
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpGet httpGet = new HttpGet(url);
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);
			HttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) { 
				jsonResult.setSuccess(false);
				jsonResult.set(ResultState.OPR_FAIL);
			} else {
				jsonResult.setSuccess(true);
				jsonResult.set(ResultState.OPR_SUCCESS);
			}
			return jsonResult;
		} catch (ClientProtocolException e) {
			jsonResult.setSuccess(false);
			jsonResult.set(ResultState.OPR_FAIL);
			return jsonResult;
		} catch (IOException e) {
			jsonResult.setSuccess(false);
			jsonResult.set(ResultState.OPR_FAIL);
			return jsonResult;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	/**
	 * 修改页面
	 * @param iid
	 * @return
	 */
	@RequestMapping("modify_show")
	public ModelAndView showModify(Integer iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/sys/ditch/ditch_opr");
		Ditch ditch = ditchService.findByIid(iid);
		modelAndView.addObject("url", "modify_submit.do");
		modelAndView.addObject("ditch", ditch);
		return modelAndView;
	}
	
	/**
	 * 修改提交
	 * @param application
	 * @return
	 */
	@RequestMapping("modify_submit")
	@ResponseBody
	public JsonResult submitModify(Ditch ditch) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = ditchService.modify(ditch);
			if (isSuccess) {
				jsonResult.set(ResultState.MODIFY_SUCCESS);
			} else {
				jsonResult.set(ResultState.MODIFY_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult; 
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("remove")
	@ResponseBody
	public JsonResult remove(String ids) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			isSuccess = ditchService.removeByIds(ids);
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
	 * 修改接口启用状态
	 * @param iid
	 * @param enable
	 * @return
	 */
    @RequestMapping("modifyenable_submit")
    @ResponseBody
    public JsonResult submitModifyEnable(Integer iid, Integer enable) {
        JsonResult jsonResult = JsonResult.getInstance();
        boolean isSuccess = ditchService.modifyEnable(iid, enable);
        jsonResult.setSuccess(isSuccess);
        if (isSuccess) {
            jsonResult.setMessage("opr.success");
        } else {
            jsonResult.setMessage("opr.fail");
        }
        return jsonResult;
    }
	
}