package com.hanweb.jmp.apps.controller.read;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.common.util.file.LocalFileUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.support.controller.CurrentUser;

import com.hanweb.jmp.apps.entity.read.Read;
import com.hanweb.jmp.apps.service.read.ReadService;
import com.hanweb.jmp.constant.Configs;

/**
 * 阅读操作控制器
 * @author ZDJ
 */
@Controller
@RequestMapping("manager/plugins/read")

public class OprReadController {
	
	/**
	 * readService
	 */
	@Autowired
	private ReadService readService;
	
	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;
	
	/**
	 * 新增展现
	 * @param siteId 网站ID
	 * @param colId 分类ID
	 * @return modelAndView
	 */
	@RequestMapping(value = "add_show")
	public ModelAndView showAdd(Integer siteId, Integer colId) {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/read/read_opr");
		ReadFormBean read = new ReadFormBean();
		read.setSiteId(siteId);
		read.setPid(NumberUtil.getInt(colId));
		modelAndView.addObject("read", read);
		modelAndView.addObject("url", "add_submit.do");
		return modelAndView;
	}
	
	/**
	 * 新增提交
	 * @param read 阅读实体
	 * @return json
	 */
	@RequestMapping(value = "add_submit")
	@ResponseBody
	public String saveAdd(ReadFormBean read) {
		boolean isSuccess = false;
		String message = "";
		try {
			isSuccess = readService.add(read);
		} catch (OperationException e) {
			message = e.getMessage();
		}
		Script script = Script.getInstanceWithJsLib();
		if (isSuccess) {
			if(NumberUtil.getInt(read.getType()) == 1){
				script.addScript("parent.getDialog().dialog('options').callback(1);parent.closeDialog(true);");
			}else{
				script.addScript("parent.getDialog().dialog('options').callback(0);parent.closeDialog(true);");
			}
		} else {
			script.addAlert("新增失败！" + message); 
		}  
		return script.getScript(); 
	}
	
	/**
	 * 修改展现
	 * @param iid 阅读ID
	 * @return modelAndView
	 */
	@RequestMapping(value = "modify_show")
	public ModelAndView showModify(Integer iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/read/read_opr");
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz() != LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}
		Read read = readService.findById(NumberUtil.getInt(iid));
		modelAndView.addObject("read", read);
		modelAndView.addObject("jmpUrl", jmpUrl);
		if(read.getChangeTime()!=null){
			modelAndView.addObject("changeTime", DateUtil.dateToString(read.getChangeTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		}
		modelAndView.addObject("pubTime", DateUtil.dateToString(read.getPubTime(), DateUtil.YYYY_MM_DD));
		modelAndView.addObject("url", "modify_submit.do");
		return modelAndView;
	}

	/**
	 * 修改提交
	 * @param read 阅读实体
	 * @return json
	 */
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public String submitModify(ReadFormBean read) {
		boolean isSuccess = false;
		String message = "";
		try {
			isSuccess = readService.modify(read);
		} catch (OperationException e) {
			message = e.getMessage();
		}
		Script script = Script.getInstanceWithJsLib();
		if (isSuccess) {
			if(NumberUtil.getInt(read.getType()) == 1){
				script.addScript("parent.getDialog().dialog('options').callback(1);parent.closeDialog(true);");
			}else{
				script.addScript("parent.getDialog().dialog('options').callback(0);parent.closeDialog(true);");
			}
		} else {
			script.addAlert("修改失败！" + message); 
		}  
		return script.getScript(); 
	}
	
	/**
	 * 删除阅读
	 * @param ids  id串
	 * @return jsonResult
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			isSuccess = readService.removeByIds(ids);
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
	 * 阅读排序
	 * @param colId 分类ID
	 * @return modelAndView
	 */
	@RequestMapping(value = "sort_show")
	@ResponseBody
	public ModelAndView order(String colId) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		ModelAndView modelAndView = new ModelAndView("jmp/apps/read/read_sort");
		List<Read> readList = readService.findOrder(NumberUtil.getInt(colId), siteId);
		modelAndView.addObject("url", "sort_submit.do");
		modelAndView.addObject("colList", readList);
		return modelAndView;
	}
	
	/**
	 * 阅读排序提交
	 * @param ids id串
	 * @param orderids 排序ID
	 * @return jsonResult
	 */
	@RequestMapping(value = "sort_submit")
	@ResponseBody
	public JsonResult submitSort(String ids, String orderids) {
 		boolean isSuccess = false; 
		JsonResult jsonResult = JsonResult.getInstance();  
		try {
			if(null == ids || ids.length() <= 0){
				isSuccess = true;
			}else{
				isSuccess = readService.modifyOrderIdById(ids, orderids);
			}
			if (isSuccess) {
				jsonResult.set(ResultState.OPR_SUCCESS); 
			} else {
				jsonResult.set(ResultState.OPR_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;   
	}
	
	/**
	 * 弹出层页面
	 * @return modelAndView
	 */
	@RequestMapping(value = "frame_show")
	public ModelAndView showFrame(){
		ModelAndView modelAndView = new ModelAndView("jmp/apps/read/readframe");
		return modelAndView;
	}
	
}