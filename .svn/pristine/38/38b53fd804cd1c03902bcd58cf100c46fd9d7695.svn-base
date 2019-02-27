package com.hanweb.jmp.apps.controller.numbersense.numsensecol;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.support.controller.CurrentUser;

import com.hanweb.jmp.apps.entity.numbersense.NumSenseCol;
import com.hanweb.jmp.apps.service.numbersense.NumSenseColService;

/**
 * 号码分类操作控制器
 * @author ZDJ
 */
@Controller
@RequestMapping("manager/plugins/numsense/col")
public class OprNumSenseColController {
	
	/**
	 * numSenseColService
	 */
	@Autowired
	private NumSenseColService numSenseColService;
	
	/**
	 * 新增展现
	 * @param siteId 网站ID
	 * @param colId 分类ID
	 * @return modelAndView
	 */
	@RequestMapping(value = "add_show")
	public ModelAndView showAdd(Integer siteId, Integer colId) {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/numbersense/col_opr");
		NumSenseCol col = new NumSenseCol();
		col.setSiteId(siteId);
		col.setPid(colId);
		modelAndView.addObject("PicFileType", "jpg,png");
		modelAndView.addObject("col", col);
		modelAndView.addObject("url", "add_submit.do");
		return modelAndView;
	}
	
	/**
	 * 新增提交
	 * @param col 号码分类
	 * @return json
	 */
	@RequestMapping(value = "add_submit")
	@ResponseBody
	public String saveAdd(NumSenseColFormBean col) {
		String message = "";
		boolean isSuccess = false;
		try {
			isSuccess = numSenseColService.add(col);
		} catch (OperationException e) {
			message = e.getMessage();
		}
		Script script = Script.getInstanceWithJsLib();
		if (isSuccess) {
			script.addScript("parent.oprSuccess('c" + NumberUtil.getInt(col.getPid()) + "')");  
		} else {
			script.addAlert("新增失败！" + message); 
		}  
		return script.getScript(); 
	}
	
	/**
	 * 修改展现
	 * @param iid 分类ID
	 * @return modelAndView
	 */
	@RequestMapping(value = "modify_show")
	public ModelAndView showModify(Integer iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/numbersense/col_opr");
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(jmpUrl.endsWith("/")){
			jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
		} 
		NumSenseCol col = numSenseColService.findById(NumberUtil.getInt(iid));
		modelAndView.addObject("PicFileType", "jpg,png");
		modelAndView.addObject("col", col);
		modelAndView.addObject("jmpUrl", jmpUrl);
		modelAndView.addObject("url", "modify_submit.do");
		return modelAndView;
	}

	/**
	 * 修改提交
	 * @param col
	 *            栏目对象
	 * @return 操作信息
	 */
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public String submitModify(NumSenseColFormBean col) {
		boolean isSuccess = false;
		String message = "";
		try {
			isSuccess = numSenseColService.modify(col);
		} catch (OperationException e) {
			message = e.getMessage();
		}
		Script script = Script.getInstanceWithJsLib();
		if (isSuccess) {
			script.addScript("parent.oprSuccess('c" + NumberUtil.getInt(col.getPid()) + "')");  
			script.addScript("parent.oprSuccess('p" + NumberUtil.getInt(col.getPid()) + "')");  
//			script.refreshNode("c"+NumberUtil.getInt(col.getPid()));
//			script.refreshNode("p"+NumberUtil.getInt(col.getPid()));
//			script.addScript("parent.refreshParentWindow();parent.closeDialog();");  
		} else {
			script.addAlert("修改失败！" + message); 
		}  
		return script.getScript(); 
	}
	
	/**
	 * 删除分类
	 * @param ids 
	 *          分类id串
	 * @return  结果信息
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		String idJson = "";
		for(Integer idEn : idList) {
			idJson += ",c" + idEn + ",p" + idEn;
		}
		try {
			isSuccess = numSenseColService.removeByIds(ids);
			if (isSuccess) {
				jsonResult.set(ResultState.REMOVE_SUCCESS);
				jsonResult.addParam("remove", idJson.substring(1));
			} else {
				jsonResult.set(ResultState.REMOVE_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult; 
	}
	
	/**
	 * 排序展现
	 * @param colId 分类ID
	 * @return modelAndView
	 */
	@RequestMapping(value = "sort_show")
	@ResponseBody
	public ModelAndView order(String colId) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		ModelAndView modelAndView = new ModelAndView("jmp/apps/numbersense/col_sort");
		List<NumSenseCol> colList = numSenseColService.findOrderCol(NumberUtil.getInt(colId), siteId);
		modelAndView.addObject("url", "sort_submit.do");
		modelAndView.addObject("colList", colList);
		return modelAndView;
	}
	
	/**
	 * 分类排序提交
	 * @param ids id串
	 * @param orderids 订单id串
	 * @return json
	 */
	@RequestMapping(value = "sort_submit")
	@ResponseBody
	public JsonResult submitSort(String ids, String orderids) {
 		boolean isSuccess = false; 
		JsonResult jsonResult = JsonResult.getInstance(); 
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		String idJson = "";
		for(Integer idEn : idList) {
			idJson += ",c" + idEn + ",p" + idEn;
		}
		try {
			if(null == ids || ids.length() <= 0){
				isSuccess = true;
			}else{
				isSuccess = numSenseColService.modifyOrderIdById(ids, orderids);
			}
			if (isSuccess) {
				jsonResult.set(ResultState.OPR_SUCCESS);
				jsonResult.addParam("refresh", "c0,p0");
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
		ModelAndView modelAndView = new ModelAndView("jmp/apps/numbersense/colframe");
		return modelAndView;
	}
	
}