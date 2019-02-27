package com.hanweb.jmp.apps.controller.manage;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.DateUtil; 
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.NumberUtil; 
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.support.controller.CurrentUser;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.jmp.util.QRCodeUtil;
import com.hanweb.jmp.apps.entity.manage.LightApp;
import com.hanweb.jmp.apps.service.manage.LightAppService; 
import com.hanweb.jmp.appstype.entity.LightAppType;
import com.hanweb.jmp.appstype.service.LightAppTypeService;

@Controller
@RequestMapping("manager/lightapp")
public class OprLightAppController {
	
	/**
	 * LightAppService
	 */
	@Autowired
	private LightAppService lightAppService;
	
	/**
	 * SiteService
	 */
	@Autowired
	private SiteService siteService;
	
	/**
	 * ColService
	 */
	@Autowired
	private ColService colService;
	
	@Autowired
    private LightAppTypeService lightAppTypeService;
	
	/**
	 * 新增页面
	 * @return
	 */
	@RequestMapping("add_show")
	public ModelAndView showAdd(String lightTypeId) {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/manage/lightapp_opr");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		LightApp lightapp = new LightApp();
		lightapp.setSiteId(siteId);
		lightapp.setIsDefault(0);
		lightapp.setDefaultType(0);
		LightAppType lightapptype = lightAppTypeService.findByIid(NumberUtil.getInt(lightTypeId));
		if(lightapptype != null){
		    lightapp.setLightType(NumberUtil.getInt(lightTypeId));
		    lightapp.setLightTypeName(lightapptype.getName());
		}
		String createTime = DateUtil.dateToString(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS);
		modelAndView.addObject("createTime", createTime);
		modelAndView.addObject("url", "add_submit.do");
		modelAndView.addObject("lightapp", lightapp);
		return modelAndView;
	}
	
	/**
	 * 新增提交
	 * @param lightapp
	 * @return
	 */
	@RequestMapping("add_submit")
	@ResponseBody
	public String submitAdd(LightAppFormBean lightapp, MultipartFile iconFile) {
		Script script = Script.getInstanceWithJsLib();
		String message = "";
		boolean isSuccess = false;
		try {
			if(iconFile == null){
				message = "应用图标不能为空";
			}else{
				lightapp.setCreateTime(new Date());
				isSuccess = lightAppService.add(lightapp, iconFile);
			}
		} catch (OperationException  e) {
			message = e.getMessage();
		}
		if (isSuccess) {
			script.addScript("parent.refreshParentWindow();parent.closeDialog();");
		} else {
			script.addAlert("新增失败！" + message);
		}
		return script.getScript(); 
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
		String newLightAppId = "";
		try {
			List<Col> colList = colService.findAllCol();
			if(colList.size() > 0){
				for(Col col : colList){
					if(col.getType() == 3 && col.getNewLightAppId() != null){
						newLightAppId = col.getNewLightAppId();
						if(newLightAppId.split(",").length > 1){
							for(int i = 0; i<newLightAppId.split(",").length; i++){
								if(ids.equals(newLightAppId.split(",")[i])){
									jsonResult.setSuccess(false);
									jsonResult.setMessage("已有栏目绑定该应用，请于栏目管理解除绑定，再删除应用");
									return jsonResult;
								}
							}
						}else{
							if(ids.equals(newLightAppId)){
								jsonResult.setSuccess(false);
								jsonResult.setMessage("已有栏目绑定该应用，请于栏目管理解除绑定，再删除应用");
								return jsonResult;
							}
						}
					}else{
						continue;
					}
				}
			}
			isSuccess = lightAppService.removeByIds(ids);
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
	 * 修改页面
	 * @param iid
	 * @return
	 */
	@RequestMapping("modify_show")
	public ModelAndView showModify(Integer iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/manage/lightapp_opr");
		LightApp lightApp = lightAppService.findByIid(iid);
		modelAndView.addObject("url", "modify_submit.do");
		modelAndView.addObject("lightapp", lightApp);
		return modelAndView;
	
	}
	
	/**
	 * 修改提交
	 * @param application
	 * @return
	 */
	@RequestMapping("modify_submit")
	@ResponseBody
	public String modifySubmit(LightAppFormBean lightapp, MultipartFile iconFile) {
		boolean isSuccess = false;
		String message = "";
		Script script = Script.getInstanceWithJsLib();
		try {
			isSuccess = lightAppService.modify(lightapp, iconFile);
		} catch (OperationException e) {
			message = e.getMessage();
		}
		if (isSuccess) {
			script.addScript("parent.refreshParentWindow();parent.closeDialog();");
		} else {
			script.addAlert("新增失败！" + message);
		}
		return script.getScript(); 
	
	}
	
	/**
	 * 排序页面
	 * @param mid   mid
	 * @param colId colId
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "sort_show")
	@ResponseBody
	public ModelAndView order(String pid) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		ModelAndView modelAndView = new ModelAndView("jmp/apps/manage/lightapp_sort");
		List<LightApp> appList = lightAppService.findByPid(siteId, pid);
		modelAndView.addObject("url", "sort_submit.do");
		modelAndView.addObject("appList", appList);
		modelAndView.addObject("pid", pid);
		return modelAndView;
	}
	
	/**
	 * 排序提交
	 * @param ids       ids
	 * @param orderids  orderids
	 * @param mid  mid
	 * @return  JsonResult
	 */
	@RequestMapping(value = "sort_submit")
	@ResponseBody
	public JsonResult submitSort(String ids, String orderids, String pid) {
		boolean isSuccess = false; 
		JsonResult jsonResult = JsonResult.getInstance();  
		try {
			isSuccess = lightAppService.modifyOrderIdById(ids, orderids);
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
	 * 栏目应用排序
	 * @param lightAppIds
	 * @param colId
	 * @return
	 */
	@RequestMapping(value = "sortforcol_show")
	@ResponseBody
	public ModelAndView orderForApp(String lightAppIds, String colId) {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/manage/lightappforapp_sort");
		List<LightApp> appList = lightAppService.findByAppIds(lightAppIds);
		modelAndView.addObject("url", "sortforapp_submit.do");
		modelAndView.addObject("appList", appList);
		modelAndView.addObject("colId", colId);
		return modelAndView;
	}
	
	/**
	 * 确认排序
	 * @param ids
	 * @param colId
	 * @return
	 */
	@RequestMapping(value = "sortforapp_submit")
	@ResponseBody
	public JsonResult submitSortForApp(String ids, String colId) {
		boolean isSuccess = false; 
		JsonResult jsonResult = JsonResult.getInstance();  
		try {
			Col col = colService.findByIid(NumberUtil.getInt(colId));
			Integer pid = col.getPid();
			if(pid == null){
				pid = 0;
			}
			col.setNewLightAppId(ids);
			isSuccess = colService.modifyLightAppIds(col);
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
	
	
	/**
	 * 启用、停用
	 * @param ids
	 *            Id串
	 * @param enable
	 *            状态值
	 * @return    操作信息 
	 */
	@RequestMapping(value = "enable_modify")
	@ResponseBody
	public JsonResult modifyEnable(String ids, String enable) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {  
			isSuccess = lightAppService.modifyEnable(ids, NumberUtil.getInt(enable));
			if (isSuccess) { 
				jsonResult.set(ResultState.OPR_SUCCESS);
			} else {
				jsonResult.set(ResultState.OPR_FAIL);
			}
			jsonResult.setSuccess(isSuccess);
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;  
	}
 
	
	@RequestMapping(value = "scanQRCode")
	@ResponseBody
	public ModelAndView scanQRCode(String url, Integer iid) {
		FileUtil.isDirExsit(new File(BaseInfo.getRealPath() + "/webapp/qrcode/"
				+ iid), true);
		String imgPath = BaseInfo.getRealPath() + "/webapp/qrcode/" + iid
				+ "/qcode.png";
		url = String.valueOf(url);
		String QRCodePath = BaseInfo.getDomain() + "/webapp/qrcode/" + iid
				+ "/qcode.png";
		// 生成二维码
		QRCodeUtil.createQRCode(url, imgPath, null);

		lightAppService.updataAppQrCodeAddress(QRCodePath, iid);

		ModelAndView modelAndView = new ModelAndView(
				"jmp/apps/manage/scanQRCode");
		url = BaseInfo.getDomain() + "/webapp/qrcode/" + iid + "/qcode.png";
		modelAndView.addObject("url", url);
		modelAndView.addObject("code", "app");
		return modelAndView;

	}

	@RequestMapping("refreshQRCode")
	@ResponseBody
	public String refreshQRCode(Integer iid) {
		File file = new File(BaseInfo.getRealPath() + "/webapp/qrcode/" + iid);
		if (!file.exists()) {
			file.mkdirs();
		}
		String imgPath = BaseInfo.getRealPath() + "/webapp/qrcode/" + iid
				+ "/qcode.png";
		LightApp app = lightAppService.findByIid(iid);
		int num = QRCodeUtil
				.createQRCode(app.getQrCodeAddress(), imgPath, null);
		String url = BaseInfo.getDomain() + "/webapp/qrcode/" + iid
				+ "/qcode.png";
		if (num == 0) {
			return url;
		}
		return null;
	}
}