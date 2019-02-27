package com.hanweb.jmp.cms.controller.cols;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.common.util.file.LocalFileUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.FileResource;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.MultipartFileInfo;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.apps.entity.manage.LightApp;
import com.hanweb.jmp.apps.service.manage.LightAppService;
import com.hanweb.jmp.appstype.entity.LightAppType;
import com.hanweb.jmp.appstype.service.LightAppTypeService;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.StaticValues;
import com.hanweb.jmp.constant.TypeConfig;
import com.hanweb.jmp.global.service.TaskService;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.jmp.util.CacheUtil;
import com.hanweb.jmp.util.CutStringUtil;
import com.hanweb.support.controller.CurrentUser;

/**
 * 栏目控制器
 * @author lgq
 */
@Controller
@RequestMapping("manager/col")
public class OprColController {

	/**
	 * 日志
	 */
	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * colService
	 */
	@Autowired
	private ColService colService;
	
	/**
	 * siteService
	 */
	@Autowired
	private SiteService siteService;

	/**
	 * taskService
	 */
	@Autowired
	private TaskService taskService;
	
	/**
	 * typeConfig
	 */
	@Autowired
	private TypeConfig typeConfig;
	
	@Autowired
	private LightAppService lightAppService;
	
	@Autowired
	private LightAppTypeService lightAppTypeService;
	
	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;
	

	/**
	 * 新增展现
	 * @param request  request
	 * @param pid  pid
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "add_show")
	public ModelAndView showAdd(HttpServletRequest request, String pid) {
		ModelAndView modelAndView = new ModelAndView("jmp/cms/cols/col_opr");
		Col col = new Col();
		col.setColListType(1);
		col.setHdType(1);
		Col tmpCol = colService.findByIid(NumberUtil.getInt(pid));
		if (tmpCol != null) {
			col.setPid(tmpCol.getIid());
			col.setPname(tmpCol.getName());
		}
		col.setIsJsearch(0);
		boolean isSupportReg = false;
		boolean isSupportSearch = false;
		boolean isSupportOfflineDownload = false;
		
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		Site siteEn = siteService.findByIid(siteId);
		//若网站支持手机注册，则需要显示栏目访问权限
		if(NumberUtil.getInt(siteEn.getIsSupportReg()) == 1){
			isSupportReg = true;
		} 
		//若网站支持全文检索
		if(NumberUtil.getInt(siteEn.getIsSearch()) == 1){
			isSupportSearch = true;
		}
		//若网站支持离线下载
		if(NumberUtil.getInt(siteEn.getIsOfflineZip()) == 1){
			isSupportOfflineDownload = true;
		}
		Integer userId = currentUser.getIid();
		Map<Integer, String> infoListType = typeConfig.getInfoListType(siteId);
		Map<Integer, String> infoContentType = typeConfig.getInfoContentType(siteId);
		Map<Integer, String> colHdType = typeConfig.getColHdType(siteId);
		Map<Integer, String> colCommonType = typeConfig.getColCommonType(siteId);
		Map<Integer, String> colListType = typeConfig.getColListType(siteId);
		modelAndView.addObject("currentTime", DateUtil.getCurrDate("yyyy-MM-dd"));
		modelAndView.addObject("userId", userId);
		modelAndView.addObject("siteId", siteId);
		modelAndView.addObject("iid", col.getIid());
		modelAndView.addObject("url", "add_submit.do");
		modelAndView.addObject("col", col);
		modelAndView.addObject("PicFileType", Configs.getConfigs().getPicFileType());
		modelAndView.addObject("isSupportReg", isSupportReg);
		modelAndView.addObject("isSupportSearch", isSupportSearch);
		modelAndView.addObject("isSupportOfflineDownload", isSupportOfflineDownload);
		modelAndView.addObject("InfoListType", infoListType);
		modelAndView.addObject("InfoContentType", infoContentType);
		modelAndView.addObject("ColHDType", colHdType);
		modelAndView.addObject("ColCommonType", colCommonType);
		modelAndView.addObject("ColListType", colListType);
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		} 
		modelAndView.addObject("jmpurl", jmpUrl);
		this.addOtherObject(modelAndView, request);
		return modelAndView;
	}

	/**
	 * 填参
	 * @param modelAndView  modelAndView
	 * @param request  request
	 */
	private void addOtherObject(ModelAndView modelAndView, HttpServletRequest request) {
		Integer adminRangeId = 0; // 管理员 管理的任务ID
		String adminRangeName = "栏目选择"; // 管理员 管理的任务名称
		// 组织树
		Tree tree = Tree.getInstance();
		tree.addNode(TreeNode.getInstance(adminRangeId + "", "menu", adminRangeName, true).setClick("onTopClick()"));
		modelAndView.addObject("colMenu", tree.parse());
		modelAndView.addObject("adminRangeId", adminRangeId);
		String taskTree = taskService.findColTaskTree();
		modelAndView.addObject("taskTree", taskTree);
//		String nodeTree = taskService.findNodeTaskTree();
//		modelAndView.addObject("nodeTree", nodeTree);
		Tree tree1 = Tree.getInstance();
		tree1.addNode(TreeNode.getInstance(adminRangeId + "", "menu", adminRangeName, true).setClick("onTopBannerClick()"));
		modelAndView.addObject("colBannerMenu", tree1.parse());
		// 组织树
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		Tree tree2 = Tree.getInstance();
		tree2.addNode(TreeNode.getInstance("root", null, "应用选择").setIsParent(true));
		List<LightAppType> lightAppTypes = lightAppTypeService.findBySiteId(siteId);
		if(lightAppTypes!=null && lightAppTypes.size()>0){
			for(LightAppType lightAppType : lightAppTypes){
				 tree2.addNode(TreeNode.getInstance( "type_" + lightAppType.getIid(), "root",
						 lightAppType.getName()).setIsParent(true).setOpen(false));  
			}
		}
		//增加默认分组
		tree2.addNode(TreeNode.getInstance( "type_0", "root","默认分组").setIsParent(true).setOpen(false));  
		
		
		List<LightApp> lightApps = lightAppService.findBySiteId(siteId, 1);
		
		for (LightApp lightApp : lightApps) {
			 if(lightApp.getDefaultType() == 8){
				 if(lightApp.getGroupName()!=null){
				 tree2.addNode(TreeNode.getInstance( "" + lightApp.getIid(), "root",
							lightApp.getName()+"("+lightApp.getGroupName()+")").setIsParent(true)); 
				 }else{
					 tree2.addNode(TreeNode.getInstance( "" + lightApp.getIid(), "root",
								lightApp.getName()).setIsParent(true)); 					 
				 }
			 } else {
				 if(lightApp.getGroupName()!=null){
				 tree2.addNode( TreeNode.getInstance( "" + lightApp.getIid(),"type_"+NumberUtil.getInt(lightApp.getLightType()),
							lightApp.getName()+"("+lightApp.getGroupName()+")").setIsParent(false));
				 }else{
					 tree2.addNode( TreeNode.getInstance( "" + lightApp.getIid(),"type_"+NumberUtil.getInt(lightApp.getLightType()),
								lightApp.getName()).setIsParent(false));
					 }
			 }
		} 
		modelAndView.addObject("lightAppMenu", tree2.parse());
	}

	/**
	 * 新增提交
	 * @param col  col
	 * @return  String
	 */
	@RequestMapping(value = "add_submit")
	@ResponseBody
	public String saveAdd(ColFormBean col) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		boolean isSuccess = false;
		String message = "";
		if(StringUtil.isNotEmpty(col.getBgColor())){
			col.setBgColor(StringUtil.getString(col.getBgColor().replace("#", "")));
		}
		try {
			col.setSpec(StringUtil.getSafeString(col.getSpec()));
			isSuccess = colService.add(col);
		} catch (OperationException e) {
			message = e.getMessage();
		}
		Script script = Script.getInstanceWithJsLib();
		if (isSuccess) {
			CacheUtil.removeKey(StaticValues.CACHE_REGION, "first_"+NumberUtil.getInt(currentUser.getSiteId()));
			script.refreshNode(NumberUtil.getInt(col.getPid()) + "");
			script.addScript("parent.refreshParentWindow();parent.closeDialog();");
		} else {
			script.addAlert("新增失败！" + message);
		}
		return script.getScript();
	}

	/**
	 * 修改页面
	 * @param request  request
	 * @param iid  
	 * 栏目ID
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "modify_show")
	public ModelAndView showModify(HttpServletRequest request, String iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/cms/cols/col_opr");
		Col col = colService.findByIid(Integer.parseInt(iid));
		if(StringUtil.isNotEmpty(col.getBgColor())) {
			modelAndView.addObject("bgColor", "#"+col.getBgColor());
		}
		modelAndView.addObject("col", col);
		modelAndView.addObject("iid", iid);
		// 开始时间需要转换
		modelAndView.addObject("starttime", DateUtil.dateToString(col.getStartTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		modelAndView.addObject("lastupdatetime", DateUtil.dateToString(col.getLastUpdateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		modelAndView.addObject("siteId", col.getSiteId());
		modelAndView.addObject("userId", col.getUserId());
		modelAndView.addObject("url", "modify_submit.do");
		boolean isSupportReg = false;
		boolean isSupportSearch = false;
		boolean isSupportOfflineDownload = false;
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		Site siteEn = siteService.findByIid(siteId);
		//若网站支持手机注册，则需要显示栏目访问权限
		if(NumberUtil.getInt(siteEn.getIsSupportReg()) == 1){
			isSupportReg = true;
		} 
		//若网站支持全文检索
		if(siteEn.getIsSearch() == 1){
			isSupportSearch = true;
		}
		//若网站支持离线下载
		if(NumberUtil.getInt(siteEn.getIsOfflineZip()) == 1){
			isSupportOfflineDownload = true;
		}
		Map<Integer, String> infoListType = typeConfig.getInfoListType(siteId);
		Map<Integer, String> infoContentType = typeConfig.getInfoContentType(siteId);
		Map<Integer, String> colHdType = typeConfig.getColHdType(siteId);
		Map<Integer, String> colCommonType = typeConfig.getColCommonType(siteId);
		Map<Integer, String> colListType = typeConfig.getColListType(siteId);
		modelAndView.addObject("isSupportReg", isSupportReg);
		modelAndView.addObject("isSupportSearch", isSupportSearch);
		modelAndView.addObject("isSupportOfflineDownload", isSupportOfflineDownload);
		modelAndView.addObject("PicFileType", Configs.getConfigs().getPicFileType());
		modelAndView.addObject("InfoListType", infoListType);
		modelAndView.addObject("InfoContentType", infoContentType);
		modelAndView.addObject("ColHDType", colHdType);
		modelAndView.addObject("ColCommonType", colCommonType);
		modelAndView.addObject("ColListType", colListType);
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}  
		modelAndView.addObject("jmpurl", jmpUrl);
		this.addOtherObject(modelAndView, request);
		return modelAndView;
	}

	/**
	 * 修改提交
	 * @param col
	 * 栏目对象
	 * @return 操作信息
	 */
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public String submitModify(ColFormBean col) {
		col.setSpec(StringUtil.getSafeString(col.getSpec()));
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		boolean isSuccess = false;
		String message = null;
		Script script = Script.getInstanceWithJsLib();
		if (col == null || StringUtil.isEmpty(col.getName())) {
			script.addAlert("栏目名称不能为空");
		} else {
			if(StringUtil.isNotEmpty(col.getBgColor())){
				col.setBgColor(StringUtil.getString(col.getBgColor().replace("#", "")));
			}
			try {
				isSuccess = colService.modify(col);
			} catch (OperationException e) {
				message = e.getMessage();
			}
			if (isSuccess) {
				// 清空信息缓存
				try {
					CacheUtil.removeAll(StaticValues.CACHE_REGION);
				} catch (IOException e) {
					message = e.getMessage();
				} 
				CacheUtil.removeKey(StaticValues.CACHE_SITE, StringUtil.getString(currentUser.getSiteId())); 
				if (NumberUtil.getInt(col.getPrevPid()) != NumberUtil.getInt(col.getPid())) { 
					script.removeNodes(NumberUtil.getInt(col.getIid())+"");
				}
				script.refreshNode(NumberUtil.getInt(col.getPid()) + "");
				script.addScript("parent.refreshParentWindow();parent.closeDialog();");
			} else {
				script.addAlert("修改失败！" + message);
			}
		}
		return script.getScript();
	}

	/**
	 * 删除栏目
	 * @param ids 
	 *          栏目id串
	 * @return  结果信息
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try {
			CurrentUser currentUser = UserSessionInfo.getCurrentUser();
			Integer siteId = currentUser.getSiteId();
			isSuccess = colService.removeByIds(ids, siteId);
			if (isSuccess) {
				CacheUtil.removeKey(StaticValues.CACHE_REGION, "first_"+NumberUtil.getInt(currentUser.getSiteId()));
				CacheUtil.removeKey(StaticValues.CACHE_SITE, StringUtil.getString(currentUser.getSiteId()));
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
	 * 启用、停用
	 * @param ids
	 *            栏目Id串
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
			CurrentUser currentUser = UserSessionInfo.getCurrentUser();
			isSuccess = colService.modifyEnable(ids, NumberUtils.toInt(enable));
			if (isSuccess) {
				CacheUtil.removeKey(StaticValues.CACHE_REGION, "first_"+NumberUtil.getInt(currentUser.getSiteId()));
				CacheUtil.removeKey(StaticValues.CACHE_SITE, StringUtil.getString(currentUser.getSiteId())); 
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

	/**
	 * 导出提交
	 * @param request   request
	 * @param ids
	 *           栏目ids
	 * @param siteId
	 *           网站ID
	 * @param pid
	 *           父Id
	 * @return FileResource
	 */
	@RequestMapping(value = "export")
	public FileResource export(HttpServletRequest request, String ids, String siteId, String pid) {
		File file = null;
		FileResource fileResouce = null;
		try {
			String filePath = colService.exportCol(ids, NumberUtil.getInt(siteId), NumberUtil.getInt(pid));
			file = new File(filePath);
			fileResouce = ControllerUtil.getFileResource(file, "栏目分类.zip");
		} catch (Exception e) {
			logger.error("export group Error ", e);
			return null;
		} finally {
			if (file != null && file.exists()) {
				FileUtil.deleteFile(file); 
			}
		}
		return fileResouce;
	}

	/**
	 * 导入页面
	 * @param pId
	 *            父栏目Id
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "import_show")
	@ResponseBody
	public ModelAndView showImport(String pId) {
		ModelAndView modelAndView = new ModelAndView("jmp/cms/cols/col_import");
		modelAndView.addObject("exporturl", "col.zip");
		modelAndView.addObject("url", "import_submit.do?pId=" + pId);
		return modelAndView;
	}

	/**
	 * 导入提交
	 * @param file  file
	 * @param   pId  pId
	 * @return String
	 */
	@RequestMapping(value = "import_submit")
	@ResponseBody
	public String submitImport(MultipartFile file, String pId) {
		String message = "";
		Script script = Script.getInstanceWithJsLib();
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		if(file==null){
		    script.addAlert("导入失败！文件类型不正确！"); 
		}else{
		    if (file.isEmpty()) {
	            message = SpringUtil.getMessage("import.nofile");
	        } else {
	            try {
	                MultipartFileInfo info = MultipartFileInfo.getInstance(file);
	                if (ArrayUtils.contains(StaticValues.ZIP_TYPE, info.getFileType())) {
	                    File filePath = new File(com.hanweb.complat.constant.Settings.getSettings().getFileTmp()
	                                  + StringUtil.getUUIDString() + "." + info.getFileType());
	                    ControllerUtil.writeMultipartFileToFile(filePath, file);
	                    Integer siteId = currentUser.getSiteId(); // 网站ID
	                    message = colService.importCol(filePath, siteId, NumberUtil.getInt(pId));
	                    if (StringUtil.isEmpty(message)) {
	                        CacheUtil.removeKey(StaticValues.CACHE_REGION, "first_"
	                                + NumberUtil.getInt(currentUser.getSiteId()));
	                        script.refreshNode("0");
	                        script.addScript("parent.refreshParentWindow();parent.closeDialog();");
	                    } else {
	                        script.addAlert(message);
	                        script.refreshNode("0");
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
	 * 下载文件
	 * @param request  request
	 * @return  FileResource
	 */
	@RequestMapping(value = "downloadimfile")
	@ResponseBody
	public FileResource downloadImFile(HttpServletRequest request) {
		File file = new File(BaseInfo.getRealPath() + "/WEB-INF/pages/jmp/cms/cols/col.zip");
		FileResource fileResource = ControllerUtil.getFileResource(file, "col.zip");
		return fileResource;
	}

	/**
	 * 排序
	 * @param siteId  siteId
	 * @param colId  colId
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "sort_show")
	@ResponseBody
	public ModelAndView order(String siteId, String colId) {
		ModelAndView modelAndView = new ModelAndView("jmp/cms/cols/col_sort");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Set<String> colRightids = currentUser.getColids();
		List<Integer> list = new ArrayList<Integer>();
		List<Col> colList = null;
		if(CollectionUtils.isNotEmpty(colRightids)){
		    for(String str : colRightids){
                if(str.contains("manage")){
                    list.add(NumberUtil.getInt(str.substring(7)));
                }
            }
		    colList = colService.findChildColByIidAndList(NumberUtil.getInt(colId), NumberUtil.getInt(siteId), list);
		} else {
		    colList = colService.findChildColByIid(NumberUtil.getInt(colId), NumberUtil.getInt(siteId));
		}
		modelAndView.addObject("siteId", siteId);
		modelAndView.addObject("colId", colId);
		modelAndView.addObject("url", "sort_submit.do");
		modelAndView.addObject("colList", colList);
		return modelAndView;
	}

	/**
	 * 排序提交
	 * @param ids  ids
	 * @param orderids  orderids
	 * @param colId  colId
	 * @param siteId  siteId
	 * @return  JsonResult
	 */
	@RequestMapping(value = "sort_submit")
	@ResponseBody
	public JsonResult submitSort(String ids, String orderids, String colId, String siteId) {
		boolean isSuccess = false; 
		JsonResult jsonResult = JsonResult.getInstance(); 
		if(StringUtil.isEmpty(colId)){
			colId="0";
		} 
		try {
			isSuccess = colService.modifyOrderIdById(ids, orderids);
			if (isSuccess) {
				jsonResult.set(ResultState.OPR_SUCCESS);
				jsonResult.addParam("refresh", colId + "");
			} else {
				jsonResult.set(ResultState.OPR_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;  
	}

	/**
	 * 文件下载
	 * @param filePath  filePath
	 * @return  FileResource
	 */
	@RequestMapping(value = "downloadfile")
	@ResponseBody
	public FileResource downloadFile(String filePath) {
		if(StringUtil.isEmpty(filePath)){
			return null;
		} 
		return CutStringUtil.getdownloadFile(filePath);
	}
	
	/**
	 * 刷新树
	 * @return  String
	 */
	@RequestMapping("refleshJgetTree")
	@ResponseBody
	public String refleshJgetTree() {
		return taskService.refleshColJgetTree();
	}
	
	/**
     * 选择机构，检索机构数据
     * 
     * @param keyword
     * @return
     */
    @RequestMapping("cols_search")
    @ResponseBody
    public String searchColDate(String keyword) {
        if (StringUtil.isEmpty(keyword)) {
            return "";
        }

        Map<String, Object> allNode = new HashMap<String, Object>();
        List<HashMap<String, Object>> groupNodes = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> groupNode = null;

        List<Col> colsList = colService.findSimilarByName(keyword);
        for (Col col : colsList) {
            groupNode = new HashMap<String, Object>();
            groupNode.put("id", col.getIid());
            groupNode.put("text", col.getName());
            groupNode.put("ic", col.getName());
            groupNodes.add(groupNode);
        }
        allNode.put("groups", groupNodes);
        return JsonUtil.objectToString(allNode);
    }
    
	/**
     * 栏目引用/复制 页面
     * @param orgType
     * @return
     */
    @RequestMapping("qc_show")
    public ModelAndView showQuotePage(String orgType) {
        ModelAndView modelAndView = new ModelAndView("jmp/cms/cols/col_qc");
        modelAndView.addObject("orgType", orgType);
        return modelAndView;
    }
    
    /**
     * 加载栏目树
     * @param id
     * @return
     */
    @RequestMapping("cols_load")
    @ResponseBody
    public String quoteColsData(String id) {
        List<HashMap<String, Object>> colNodes = new ArrayList<HashMap<String, Object>>();
        CurrentUser currentUser = UserSessionInfo.getCurrentUser();
        HashMap<String, Object> colNode = null;
        Integer colId = NumberUtil.getInt(id);
        Integer siteId = currentUser.getSiteId();
        List<Col> colList = colService.findColTreeByType(colId, siteId);
        String text;
        for (Col col : colList) {
            colNode = new HashMap<String, Object>();
            colNode.put("id", col.getIid());
            text = "<span class='optionname'>" + col.getName() + "</span>";
            if (col.getName() != null && col.getName().length() > 0) {
                text += " &lt;<span class='ic'>" + col.getName() + "</span>&gt;";
            }
            colNode.put("text", text);
            if(NumberUtil.getInt(col.getType()) == 1){
                colNode.put("state", "closed");
            } else {
                colNode.put("state", "");
            }
            colNodes.add(colNode);
        }
        if (colId == 0) {
            colNode = new HashMap<String, Object>();
            colNode.put("id", colId);
            colNode.put("text", "<span class='optionname'>全部栏目</span>");
            colNode.put("children", colNodes);

            colNodes = new ArrayList<HashMap<String, Object>>();
            colNodes.add(colNode);
        }

        return JsonUtil.objectToString(colNodes);
    }
    
    /**
     * 栏目引用/复制 提交
     * @param ids
     * @param targetIds
     * @param flag
     * @return
     */
    @RequestMapping(value = "qc_submit")
    @ResponseBody
    public JsonResult quoteSubmit(String ids, String targetIds, String flag) {
        CurrentUser currentUser = UserSessionInfo.getCurrentUser();
        Integer siteId = currentUser.getSiteId();
        JsonResult jsonResult = JsonResult.getInstance();
        boolean success;
        try {
            success = colService.copyCol(ids, targetIds, siteId, flag);
            if (success) {
                jsonResult.set(ResultState.OPR_SUCCESS);
            } else {
                jsonResult.set(ResultState.OPR_FAIL);
            }
        } catch (OperationException e) {
            jsonResult.setMessage("操作失败！"+e.getMessage());
        }
        return jsonResult; 
    }
    
    /**
     * 配置过滤规则页面
     * @return
     */
    @RequestMapping("filter_show")
    public ModelAndView filterShow(int colId){
        ModelAndView modelAndView = new ModelAndView("jmp/cms/cols/col_filter");
        List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
        Map<String, Object> result = null;
        String[] array = new String[]{"img", "链接", "换行", "段落", "span", "form", "表格", "script", "style", "div", "粗体", "strong", "font", "字体大小", "center", "nbsp", "iframe", "flash"};
        for(int i=0; i<array.length; i++){
            result = new HashMap<String, Object>();
            result.put("name", array[i]);
            mapList.add(result);
        }
        Col col = colService.findByIid(colId);
        String selectFilter = "";
        if(col!=null){
            selectFilter = col.getFilterReg();
        }
        modelAndView.addObject("colId", colId + "");
        modelAndView.addObject("mapList", mapList);
        modelAndView.addObject("selectFilter", selectFilter);
        modelAndView.addObject("url", "filter_submit.do");
        return modelAndView;
    }
    
    /**
     * 过滤规则提交
     * @param colId
     * @param names
     * @return
     */
    @RequestMapping("filter_submit")
    @ResponseBody
    public JsonResult filterSubmit(String colId, String names){
        JsonResult jsonResult = JsonResult.getInstance();
        if(StringUtil.isEmpty(colId)){
            return jsonResult.setMessage("参数有误");
        }
        boolean isSuccess = false;
        try {
            Col col = colService.findByIid(NumberUtil.getInt(colId));
            if(col == null){
                return jsonResult.setMessage("栏目不存在");
            }
            col.setFilterReg(names);
            isSuccess = colService.modify(col);
        } catch (Exception e) {
            jsonResult.setMessage(e.getMessage());
        }
        if (isSuccess) {
            jsonResult.set(ResultState.MODIFY_SUCCESS);
        } else {
            jsonResult.set(ResultState.MODIFY_FAIL);
        }
        return jsonResult;
    }
    
    /**
	 * 选择应用
	 * @param ids 应用id
	 * @return
	 */
	@RequestMapping("selectApp")
	public ModelAndView selectApp(String siteId, String iid){
		String newAppIds="";
		String appName = "";
		ModelAndView modelAndView = new ModelAndView("jmp/cms/cols/col_selectApp");
		if(!"undefined".equals(iid)){
		Col col = colService.findByIid(NumberUtil.getInt(iid));
		LightApp lightapp;
		newAppIds = col.getNewLightAppId();
		String[] appids = newAppIds.split(",");
		
		for(int i = 0; i<appids.length; i++){
			String appid = appids[i];
			lightapp = lightAppService.findById(NumberUtil.getInt(appid), NumberUtil.getInt(siteId));
			if(i>0){
				appName = appName + ","+lightapp.getName();
			}else{
				appName = lightapp.getName();
			}
		} 
		
		}
		modelAndView.addObject("siteId",siteId);
		modelAndView.addObject("appNames", appName);
		modelAndView.addObject("appids", newAppIds);
		return modelAndView;
	}
	
	/**
	 * 选择应用类型及应用，ajax加载应用类型及应用
	 * 
	 * @param id
	 *            
	 * @return
	 */
	@RequestMapping("app_load")
	@ResponseBody
	public String loadUserData(String id, Integer siteId) {
		Map<String, Object> allNode = new HashMap<String, Object>();
		
		List<HashMap<String, Object>> typeNodes = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> appNodes = new ArrayList<HashMap<String, Object>>();
		
		HashMap<String, Object> typeNode = null;
		HashMap<String, Object> appNode = null;
		
		Integer pid = 0;
		
		if(NumberUtil.getInt(id)!=0){
			pid = lightAppTypeService.findPidByIid(NumberUtil.getInt(id));
		}
		List<LightAppType> lightAppTypeList = lightAppTypeService.findChildByIid(NumberUtil.getInt(id), siteId);
		
		for (LightAppType lightAppType : lightAppTypeList) {
			typeNode = new HashMap<String, Object>();
			typeNode.put("id", lightAppType.getIid());
			typeNode.put("text", lightAppType.getName());
			typeNode.put("ic", lightAppType.getName());
			typeNodes.add(typeNode);
		}
		
		List<LightApp> lightAppList = lightAppService.findByPid(siteId, id);
		for(LightApp lightApp : lightAppList){
			appNode = new HashMap<String, Object>();
			appNode.put("id", lightApp.getIid());
			appNode.put("text", lightApp.getName());
			appNode.put("ic", lightApp.getName());
			appNodes.add(appNode);
		}
		
		allNode.put("id", id);
		allNode.put("pid", pid);
		allNode.put("groups", typeNodes);
		allNode.put("users", appNodes);


		return JsonUtil.objectToString(allNode);
	}
	
	/**
	 * 添加app
	 * @param users
	 * @return
	 */
	@RequestMapping("addApp")
	@ResponseBody
	public JsonResult addApp(String users){
		JSONObject jsonObject = JSONObject.fromObject(users);
		String key = "";
		String keys = "";
		JSONObject appInfo;
		String name = "";
		Iterator iterator = jsonObject.keys();
		while(iterator.hasNext()){
			key = (String) iterator.next();
			keys += key + ",";
			appInfo = jsonObject.getJSONObject(key);
			name += appInfo.getString("name") + ",";
		}
		JsonResult jsonResult = JsonResult.getInstance();
		jsonResult.setCode(keys.substring(0, keys.length()-1));
		jsonResult.setSuccess(true);
		jsonResult.setMessage(name.substring(0, name.length()-1));
		return jsonResult;
		
	}
	
}