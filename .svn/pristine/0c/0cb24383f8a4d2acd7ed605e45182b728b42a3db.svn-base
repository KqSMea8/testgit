package com.hanweb.jmp.sys.controller.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.pack.service.AppService;
import com.hanweb.jmp.sys.entity.version.Version;
import com.hanweb.jmp.sys.service.version.VersionService;

@Controller
//@Permission(module = "version", allowedGroup = Allowed.YES)
@RequestMapping("manager/version")
public class OprVersionController {
	
	/**
	 * versionService
	 */
	@Autowired
	private VersionService versionService;
	
	/**
	 * appService
	 */
	@Autowired
	private AppService appService;

	/**
	 * 删除
	 * @param ids ids
	 * @return JsonResult
	 */
	@RequestMapping("remove")
	@ResponseBody
	public JsonResult remove(String ids) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = versionService.removeByIds(ids);
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
	 * 新增页面
	 * @return    设定参数 .
	 */
	@RequestMapping("add_show")
	@ResponseBody
	public ModelAndView showAdd() {
		if(NumberUtil.getInt(Configs.getConfigs().getCreateAppType()) == 0) {
			ModelAndView modelAndView = new ModelAndView("jmp/sys/version/versionfront_opr");
			modelAndView.addObject("url", "add_submit.do");
			return modelAndView;
		} else {
			ModelAndView modelAndView = new ModelAndView("jmp/sys/version/versionback_opr");
			VersionFormBean version=new VersionFormBean();
			version.setClientType(2);
			version.setApptype(0);
			modelAndView.addObject("version", version);
			modelAndView.addObject("url", "add_submit.do");
			return modelAndView;
		}
	}

	/**
	 * 新增提交
	 * @param version version
	 * @return JsonResult
	 */
	@RequestMapping(value = "add_submit")
	@ResponseBody
	public String submitAdd(VersionFormBean version) { 
		boolean isSuccess = false;
		String message = null;
		Script script = Script.getInstanceWithJsLib();  
		version.setCreateTime(DateUtil.stringtoDate(DateUtil.getCurrDateTime(),DateUtil.YYYY_MM_DD_HH_MM_SS));
		version.setSiteId(UserSessionInfo.getCurrentUser().getSiteId());
		try {
			isSuccess = versionService.add(version);
		} catch (OperationException e) {
			message = e.getMessage(); 
		} 
		if (isSuccess) {
			if(Configs.getConfigs().getCreateAppType() == 1) {
				appService.generateTplBack(version.getAppId(), version.getSiteId());
			}
			script.addScript("parent.refreshParentWindow();parent.closeDialog();");
		} else {
			script.addAlert("新增失败！" + message);
		} 
		return script.getScript(); 
	}
	
	/**
	 * 修改页面
	 * @param iid iid
	 * @return ModelAndView
	 */
	@RequestMapping("modify_show")
	public ModelAndView showModify(Integer iid) {
		if(NumberUtil.getInt(Configs.getConfigs().getCreateAppType()) == 0) {
			ModelAndView modelAndView = new ModelAndView("jmp/sys/version/versionfront_opr");
			Version version = versionService.findByIid(iid);
			modelAndView.addObject("url", "modify_submit.do");
			modelAndView.addObject("version", version);
			return modelAndView;
		} else {
			ModelAndView modelAndView = new ModelAndView("jmp/sys/version/versionback_opr");
			Version version = versionService.findByIid(iid);
			modelAndView.addObject("url", "modify_submit.do");
			modelAndView.addObject("version", version);
			if(version.getClientType() == 2 && StringUtil.isNotEmpty(version.getDownUrl())) {
				String ipa = version.getDownUrl();
				ipa = ipa.substring(ipa.lastIndexOf("/")+1).replace("plist", "ipa");
				modelAndView.addObject("ipa", version.getDownUrl().replace("plist", "ipa"));
			}
			if(version.getClientType() == 4 && StringUtil.isNotEmpty(version.getDownUrl())) {
				String ipa = version.getDownUrl();
				ipa = ipa.substring(ipa.lastIndexOf("/")+1).replace("plist", "ipa");
				modelAndView.addObject("ipa", version.getDownUrl().replace("plist", "ipa"));
			}
			return modelAndView;
		}
	}
	
	/**
	 * 修改
	 * @param version version
	 * @return JsonResult
	 */
	@RequestMapping("modify_submit")
	@ResponseBody
	public String submitModify(VersionFormBean version) {
		boolean isSuccess = false; 
		String message = null;
		Script script = Script.getInstanceWithJsLib();  
		try {
			isSuccess = versionService.modify(version);
		} catch (OperationException e) {
			message = e.getMessage();
		}
		if (isSuccess) {
			if(Configs.getConfigs().getCreateAppType() == 1) {
				appService.generateTplBack(version.getAppId(), version.getSiteId());
			}
			script.addScript("parent.refreshParentWindow();parent.closeDialog();");
		} else {
			script.addAlert("修改失败！" + message);
		} 
		return script.getScript(); 
	}
	
}