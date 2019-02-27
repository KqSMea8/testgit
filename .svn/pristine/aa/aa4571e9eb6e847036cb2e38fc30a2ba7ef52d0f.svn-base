package com.hanweb.jmp.appstype.controller;

import java.util.Date;
import java.util.List;

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
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.appstype.entity.LightAppType;
import com.hanweb.jmp.appstype.service.LightAppTypeService;
import com.hanweb.support.controller.CurrentUser;

/**
 * 应用分类操作控制器
 * @author ZCC
 */
@Controller
@RequestMapping("manager/lightapptype")
public class OprLightAppTypeController {
	
	@Autowired
	private LightAppTypeService lightAppTypeService;
	
	/**
	 * 新增页面
	 * @return
	 */
	@RequestMapping("add_show")
	public ModelAndView showAdd(String lightTypeId) {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/lightapptype/lightapptype_opr");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		LightAppType lightapptype = new LightAppType();
		lightapptype.setSiteId(siteId);
		int pid = NumberUtil.getInt(lightTypeId);
		if(pid > 0){
		    lightapptype.setPid(pid);
		    LightAppType lightapptype1 = lightAppTypeService.findByIid(pid);
		    if(lightapptype1 != null){
		        lightapptype.setPname(lightapptype1.getName());
		    }
		} else {
		    lightapptype.setPid(0);
		}
		String createTime = DateUtil.dateToString(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS);
		modelAndView.addObject("createTime", createTime);
		modelAndView.addObject("url", "add_submit.do");
		modelAndView.addObject("lightapptype", lightapptype);
		modelAndView.addObject("rangeId", currentUser.getRangeId());
		modelAndView.addObject("rangeName", currentUser.getRangeName());
		return modelAndView;
	}
	
	/**
	 * 新增提交
	 * @param lightapptype
	 * @return
	 */
	@RequestMapping("add_submit")
	@ResponseBody
	public JsonResult submitAdd(LightAppType lightapptype) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			lightapptype.setCreateTime(new Date());
			isSuccess = lightAppTypeService.add(lightapptype);
			if (isSuccess) {
				jsonResult.set(ResultState.ADD_SUCCESS);
				jsonResult.addParam("refresh", NumberUtil.getInt(lightapptype.getPid()) + "");
			} else {
				jsonResult.set(ResultState.ADD_FAIL);
			}
		} catch (OperationException  e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult; 
	}
	
	/**
	 * 显示编辑分类页面
	 * 
	 * @param iid
	 *            机构ID
	 * @return
	 */
	@RequestMapping(value = "modify_show")
	public ModelAndView showModify(int iid) {
		LightAppType lightapptype = lightAppTypeService.findByIid(iid);
		ModelAndView modelAndView = new ModelAndView("jmp/apps/lightapptype/lightapptype_opr");
		modelAndView.addObject("url", "modify_submit.do");
		modelAndView.addObject("lightapptype", lightapptype);
		return modelAndView;
	}
	
	/**
	 * 编辑机构
	 * 
	 * @param group
	 *            机构实体
	 * @return
	 */
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public JsonResult submitModify(LightAppType lightapptype) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = lightAppTypeService.modify(lightapptype);
			if (isSuccess) {
				if (NumberUtil.getInt(lightapptype.getPrevPid()) != NumberUtil.getInt(lightapptype.getPid())) {
					jsonResult.addParam("remove", NumberUtil.getInt(lightapptype.getIid()) + "");
				}
				jsonResult.addParam("refresh", NumberUtil.getInt(lightapptype.getPid()) + "");
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
            isSuccess = lightAppTypeService.removeByIds(ids);
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
     * 排序页
     * @param pid
     * @return
     */
    @RequestMapping("sort_show")
    @ResponseBody
    public ModelAndView showSort(String pid) {
        CurrentUser currentUser = UserSessionInfo.getCurrentUser();
        Integer siteId = currentUser.getSiteId();
        ModelAndView modelAndView = new ModelAndView("jmp/apps/lightapptype/lightapptype_sort");
        List<LightAppType> lightApptypeList = lightAppTypeService.findOrderByPid(siteId, pid);
        modelAndView.addObject("url", "sort_submit.do");
        modelAndView.addObject("pid", pid);
        modelAndView.addObject("siteId", StringUtil.getString(siteId));
        modelAndView.addObject("lightApptypeList", lightApptypeList);
        return modelAndView;
    }
    
    /**
     * 排序提交
     * @param ids
     * @param orderids
     * @param colId
     * @param siteId
     * @return
     */
    @RequestMapping(value = "sort_submit")
    @ResponseBody
    public JsonResult submitSort(String ids, String orderids, String pid, String siteId) {
        boolean isSuccess = false; 
        JsonResult jsonResult = JsonResult.getInstance(); 
        if(StringUtil.isEmpty(pid)){
            pid = "0";
        } 
        try {
            isSuccess = lightAppTypeService.modifyOrderIdById(ids, orderids, siteId);
            if (isSuccess) {
                jsonResult.set(ResultState.OPR_SUCCESS);
                jsonResult.addParam("refresh", pid + "");
            } else {
                jsonResult.set(ResultState.OPR_FAIL);
            }
        } catch (OperationException e) {
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;  
    }
	
}