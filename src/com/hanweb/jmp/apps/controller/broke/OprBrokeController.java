package com.hanweb.jmp.apps.controller.broke;

import java.io.File;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.BaseInfo; 
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.FileResource;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.MultipartFileInfo;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.complat.constant.Settings;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.support.controller.CurrentUser;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.LogConfig;

import com.hanweb.jmp.apps.entity.broke.Broke;
import com.hanweb.jmp.apps.service.broke.BrokeService;
import com.hanweb.jmp.sys.service.log.LogService;
import com.hanweb.jmp.apps.controller.broke.interfaces.BrokeFormBean;

@Controller 
@RequestMapping("manager/broke")
public class OprBrokeController {
	
	/**
	 * 日志
	 */
	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * brokeService
	 */
	@Autowired
	private BrokeService brokeService;
	
	/**
	 * logService
	 */
	@Autowired
	private LogService logService;
	
	/**
	 * 新增报料
	 * @param request   request
	 * @param typeId    typeId
	 * @return   ModelAndView
	 */
	@RequestMapping(value = "add_show")
	public ModelAndView showAdd(HttpServletRequest request, int typeId) {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/broke/broke_opr");
		Broke broke = new Broke();
		broke.setClassId(typeId);
		modelAndView.addObject("url", "add_submit.do");
		modelAndView.addObject("broke", broke);
		modelAndView.addObject("typeId", typeId);
		return modelAndView;
	}
	
	/**
	 * 新增提交
	 * @param broke  broke
	 * @return  JsonResult
	 */
	@RequestMapping(value = "add_submit")
	@ResponseBody
	public JsonResult saveAdd(BrokeFormBean broke) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			CurrentUser currentUser = UserSessionInfo.getCurrentUser();
			broke.setSiteId(currentUser.getSiteId());
			broke.setIsBack(1);
			isSuccess = brokeService.add(broke);
			if (isSuccess) {
				jsonResult.set(ResultState.ADD_SUCCESS);
				jsonResult.addParam("refresh", "broke");
			} else {
				jsonResult.set(ResultState.ADD_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;
		
	}

	/**
	 * 删除
	 * @param ids  ids
	 * @return  JsonResult
	 */
	@RequestMapping("remove")
	@ResponseBody
	public JsonResult remove(String ids) {
		JsonResult jsonResult = JsonResult.getInstance();
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		boolean isSuccess = false;
		try {
			isSuccess = brokeService.removeByIds(ids, siteId);
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
	 * 审核
	 * @param ids  ids
	 * @return  JsonResult
	 */
	@RequestMapping("audit")
	@ResponseBody
	public JsonResult audit(String ids) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			isSuccess = brokeService.modifyBrokeInfo(ids, 1);
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
	 * 撤审
	 * @param ids  ids
	 * @return  JsonResult
	 */
	@RequestMapping("unaudit")
	@ResponseBody
	public JsonResult unaudit(String ids) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			isSuccess = brokeService.modifyBrokeInfo(ids, 0);
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
	 * 关闭公开
	 * @param iid  iid
	 * @return  JsonResult
	 */ 
	@RequestMapping("isopen")
	@ResponseBody
	public JsonResult isopen(Integer iid) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			isSuccess = brokeService.modifyIsOpen(iid);
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
	 * 修改页面
	 * @param request   request
	 * @param iid       iid
	 * @return  ModelAndView
	 */
	@RequestMapping("modify_show")
	public ModelAndView showModify(HttpServletRequest request, Integer iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/broke/broke_opr");
		Broke broke = brokeService.findByIid(iid);
		if(StringUtil.isNotEmpty(broke.getPicPath())){
			broke.setPicPath(Configs.getConfigs().getJmpUrl()+broke.getPicPath());
		}else{
			broke.setPicPath("");
		}
		if(StringUtil.isNotEmpty(broke.getPicPath1())){
			broke.setPicPath1(Configs.getConfigs().getJmpUrl()+broke.getPicPath1());
		}else{
			broke.setPicPath1("");
		}
		
		if(StringUtil.isNotEmpty(broke.getPicPath2())){
			broke.setPicPath2(Configs.getConfigs().getJmpUrl()+broke.getPicPath2());
		}else{
			broke.setPicPath2("");
		}
		
		if(StringUtil.isNotEmpty(broke.getPicPath3())){
			broke.setPicPath3(Configs.getConfigs().getJmpUrl()+broke.getPicPath3());
		}else{
			broke.setPicPath3("");
		}
		if(StringUtil.isNotEmpty(broke.getAudioPath())){
			broke.setAudioPath(Configs.getConfigs().getJmpUrl()+broke.getAudioPath());
		}else{
			broke.setAudioPath("");
		}
		
		if(StringUtil.isNotEmpty(broke.getVideoPath())){
			broke.setVideoPath(Configs.getConfigs().getJmpUrl()+broke.getVideoPath());
		}else{
			broke.setVideoPath("");
		}
		modelAndView.addObject("url", "modify_submit.do");
		modelAndView.addObject("broke", broke);
		return modelAndView;
	}

	/**
	 * 修改提交
	 * @param broke   broke
	 * @return  JsonResult
	 */
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public JsonResult submitModify(Broke broke) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		if (StringUtil.isNotEmpty(broke.getReply())) {
			broke.setReplyTime(DateUtil.stringtoDate(DateUtil.getCurrDateTime(), 
					DateUtil.YYYY_MM_DD_HH_MM_SS));
		}
		broke.setIsAudit(1); // 保存并审核
		try {
			isSuccess = brokeService.modify(broke);
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
	 * 报料导出
	 * @param ids
	 *        报料ID串 如:1,2,3,4
	 * @return   FileResource
	 */
	@RequestMapping(value = "export")
	public FileResource export(String ids) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		File file = null;
		FileResource fileResouce = null;
		try {
			String filePath = brokeService.exportBroke(ids, NumberUtil.getInt(siteId));
			file = new File(filePath);
			fileResouce = ControllerUtil.getFileResource(file, "报料列表.xls");
		} catch (Exception e) {
			logger.error("export broke Error ", e);
			return null;
		} finally {
			if (file.exists()) {
				FileUtil.deleteFile(file); 
			}
		}
		logService.add(LogConfig.modbroke, LogConfig.oprimport, "报料");
		return fileResouce;
	}

	/**
	 * 显示报料导入页面
	 * @return   ModelAndView
	 */
	@RequestMapping(value = "import_show")
	@ResponseBody
	public ModelAndView showImport() {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/broke/broke_import");
		modelAndView.addObject("url", "import_submit.do");
		return modelAndView;
	}

	/**
	 * 报料导入
	 * @param file
	 *            上传的文件
	 * @return  String
	 */
	@RequestMapping(value = "import_submit")
	@ResponseBody
	public String submitImport(MultipartFile file) {
		Script script = Script.getInstanceWithJsLib();
		String message = "";
		if(file==null){
		    script.addAlert("导入失败！文件类型不正确！"); 
		}else{
		    if (file.isEmpty()) {
	            message = SpringUtil.getMessage("import.nofile");
	        } else {
	            try {
	                MultipartFileInfo info = MultipartFileInfo.getInstance(file);
	                if (ArrayUtils.contains(FileUtil.EXCEL_FILE, info.getFileType())) {
	                    File filePath = new File(Settings.getSettings().getFileTmp()
	                            + StringUtil.getUUIDString() + "." + info.getFileType());
	                    ControllerUtil.writeMultipartFileToFile(filePath, file);
	                    message = brokeService.importBroke(filePath);
	                    if (StringUtil.isEmpty(message)) {
	                        script.addScript("parent.refreshParentWindow();parent.closeDialog();");
	                    } else {
	                        script.addAlert(message);
	                        script.addScript("parent.refreshParentWindow();");
	                    }
	                } else {
	                    script.addAlert("导入失败！文件类型不正确！");
	                    throw new OperationException("文件类型不正确");
	                }
	            } catch (OperationException e) {
	                message = e.getMessage();
	            }
	        }
		}
		return script.getScript();
	}

	/**
	 * 报料xls文件下载
	 * @return   FileResource
	 */ 
	@RequestMapping(value = "downloadfile")
	@ResponseBody
	public FileResource downloadFile() {
		File file = new File(BaseInfo.getRealPath() + "/WEB-INF/pages/jmp/apps/broke/broke.xls");
		FileResource fileResource = ControllerUtil.getFileResource(file, "nroke.xls");
		return fileResource;
	}
	
}