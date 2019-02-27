package com.hanweb.jmp.cms.controller.infos.info; 

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.common.util.file.LocalFileUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.FileResource;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.complat.constant.Settings;
import com.hanweb.complat.entity.Group;
import com.hanweb.complat.entity.User;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.complat.service.GroupService;
import com.hanweb.complat.service.UserService;
import com.hanweb.jmp.apps.entity.survey.Survey;
import com.hanweb.jmp.apps.service.survey.SurveyService;
import com.hanweb.jmp.cms.entity.channels.Channel;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.cms.entity.sign.Sign;
import com.hanweb.jmp.cms.service.channels.ChannelService;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.cms.service.infos.InfoService;
import com.hanweb.jmp.cms.service.sign.SignRelService;
import com.hanweb.jmp.cms.service.sign.SignService;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.StaticValues;
import com.hanweb.jmp.constant.TypeConfig;
import com.hanweb.jmp.global.entity.PushThread;
import com.hanweb.jmp.global.service.PushThreadService;
import com.hanweb.jmp.newspush.news.service.NewsDetailService;
import com.hanweb.jmp.newspush.peoplelist.entity.PeoplelistRelation;
import com.hanweb.jmp.newspush.peoplelist.service.PeoplelistRelationService;
import com.hanweb.jmp.sys.service.field.FieldService;
import com.hanweb.jmp.task.PushTask;
import com.hanweb.jmp.util.CacheUtil;
import com.hanweb.jmp.util.CutStringUtil;
import com.hanweb.jmp.util.QRCodeUtil;
import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping("manager/info")
public class OprInfoController {
	
	/**
	 * infoservice 信息service
	 */
	@Autowired
	private InfoService infoService; 
	
	/**
	 * 栏目service
	 */
	@Autowired
	private ColService colService;
	
	/**
	 * 字段Service
	 */
	@Autowired
	private FieldService fieldService;
	
	/**
	 * channelService
	 */
	@Autowired
	private ChannelService channelService;
	
	/**
	 * typeConfig
	 */
	@Autowired
	private TypeConfig typeConfig;
	
	/**
	 * pushThreadService
	 */
	@Autowired
	private PushThreadService pushThreadService;
	
	/**
	 * signService
	 */
	@Autowired
	private SignService signService;
	
	/**
	 * signRelService
	 */
	@Autowired
	private SignRelService signRelService;
	
	/**
	 * surveyService
	 */
	@Autowired
	private SurveyService surveyService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private NewsDetailService infoDetailService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PeoplelistRelationService peoplelistRelationService;
	
	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;
	
	/**
	 * 添加界面
	 * @param localpage 当前页面
	 * @param colId 栏目id
	 * @return ModelAndView 信息新建页面
	 */
	@RequestMapping("add_show")
	public ModelAndView showadd(String localpage, String colId){ 
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		int siteId = currentUser.getSiteId();
		int colid = NumberUtil.getInt(colId);
		int type = 0;
		//栏目信息审核方式： 1：手动 2：立即 3：限时审核
		int auditType = 0; 
		int colType = 0;
		InfoFormBean info = new InfoFormBean();
		if(colid > 0){
			Col col = colService.findByIid(colid);
			if(col != null){
				type = col.getType();
				auditType = NumberUtil.getInt(col.getAuditType()); 
				colType = NumberUtil.getInt(col.getInfoContentType());
				info.setInfoListType(NumberUtil.getInt(col.getInfoListType()));
			}
		}  
		ModelAndView modelAndView = new ModelAndView("jmp/cms/infos/info_opr");   
		info.setColId(NumberUtil.getInt(colId));
		info.setInfoContentType(colType);
		Map<Integer, String> infoListType = typeConfig.getInfoListType(siteId);
		Map<Integer, String> infoContentType = typeConfig.getInfoContentType(siteId);
		modelAndView.addObject("info", info);  
		modelAndView.addObject("poitypename", ""); 
		//lbs类型栏目，信息坐标默认为空
		modelAndView.addObject("poilocationtmp", ""); 
		modelAndView.addObject("content", "");  
		modelAndView.addObject("type", type); 
		//发文时间默认为当前时间
		modelAndView.addObject("synshowtime", DateUtil.getCurrDate(DateUtil.YYYY_MM_DD_HH_MM_SS));
		modelAndView.addObject("auditType", auditType); 
		modelAndView.addObject("url", "add_submit.do");
		modelAndView.addObject("localpage", localpage); 
		modelAndView.addObject("operatename", "信息管理");
		modelAndView.addObject("operatetype", "新增");
		modelAndView.addObject("customFields", fieldService.findUnSysBySiteid(siteId));
		modelAndView.addObject("infoExpand", new HashMap<String, String>());
		modelAndView.addObject("PicFileType", Configs.getConfigs().getPicFileType());
		modelAndView.addObject("VideoFileType", Configs.getConfigs().getVideoFileType());
		modelAndView.addObject("AudioFileType", Configs.getConfigs().getAudioFileType());
		modelAndView.addObject("InfoListType", infoListType);
		modelAndView.addObject("InfoContentType", infoContentType);
		modelAndView.addObject("colType", colType); 
		modelAndView.addObject("bookTree", infoService.findAddTagTree(siteId));
		List<Sign> signList = signService.findBySiteId(siteId, 3);
		if(signList.size() > 0){
			StringBuffer names = new StringBuffer();
			StringBuffer ids = new StringBuffer();
			String tagNames = "";
			String tagIds = "";
			for(Sign sign : signList){
				names.append(",").append(sign.getDname());
				ids.append(",").append(sign.getIid());
			}
			tagNames = names.substring(1);
			tagIds = ids.substring(1);
			modelAndView.addObject("tagNames", tagNames);
			modelAndView.addObject("tagIds", tagIds);
		}	 
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}  
		modelAndView.addObject("jmpurl", jmpUrl);
		this.addOtherObject(modelAndView, siteId);
		return modelAndView;
	}
   
	/**
	 * addNode栏目、频道、调查
	 * @param modelAndView modelAndView
	 * @param siteId    设定参数 .
	 */
	private void addOtherObject(ModelAndView modelAndView, int siteId) {
		Tree tree = Tree.getInstance();
		tree.addNode(TreeNode.getInstance("col_0", "", "栏目", true).setClick("onTopClick()").addAttr("infoContentType", 8));
		tree.addNode(TreeNode.getInstance("channel_0", "", "频道", true).setClick("onTopClick()").addAttr("infoContentType", 9));
		tree.addNode(TreeNode.getInstance("survey_0", "", "调查", true).setClick("onTopClick()").addAttr("infoContentType", 10));
		List<Col> colList = colService.findBySiteIdAndType(siteId, 1);
		List<Channel> channelList = channelService.findAll(siteId);
		List<Survey> surveyList = surveyService.findAll(siteId);
		if(colList != null){
			for (Col col : colList) {
				int pid = NumberUtil.getInt(col.getPid());
				String pname = "col_"+pid; 
				if(NumberUtil.getInt(col.getType())==1){
					tree.addNode(TreeNode.getInstance(
							"col_"+StringUtil.getString(col.getIid()), pname, col.getName(),
							col.getIsParent()).addAttr("infoContentType", 8).setIsDisabled(true));
				} else {
					tree.addNode(TreeNode.getInstance(
							"col_"+StringUtil.getString(col.getIid()), pname, col.getName(),
							col.getIsParent()).addAttr("infoContentType", 8));
				}
			}
		}
		if(channelList != null){
			for (Channel channel : channelList) {
				tree.addNode(TreeNode.getInstance("channel_"+StringUtil.getString(
						channel.getIid()), "channel_0", channel.getName(),
						false).addAttr("infoContentType", 9));
			}
		}
		if(surveyList != null){
			for(Survey survey : surveyList){
				tree.addNode(TreeNode.getInstance("survey_"+StringUtil.getString(
						survey.getIid()), "survey_0", survey.getName(),
						false).addAttr("infoContentType", 10));
			}
		}
		modelAndView.addObject("ztMenu", tree.parse());
	}

	/**
	 * 新增提交
	 * @param infoFormBean infoFormBean
	 * @param localpage localpage
	 * @param auditType auditType
	 * @return    设定参数 .
	 */
	@RequestMapping("add_submit")
	@ResponseBody
	public String submitadd(InfoFormBean infoFormBean, String localpage, Integer auditType){
		// 若上传了首图，则使用上传的图片作为首图
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		int siteId = currentUser.getSiteId();
		boolean isSuccess = false;
		String message = ""; 
		Script script = Script.getInstanceWithJsLib();
		try {  
			infoFormBean.setCreateTime(new Date());
			if(infoFormBean.getSynTime()==null){
				infoFormBean.setSynTime(new Date()); 
			}
			infoFormBean.setTopId(0);
			//立即审核
			if(auditType == 2){
				infoFormBean.setStatus(1);
			}else{
				infoFormBean.setStatus(0);
			}
			infoFormBean.setSiteId(siteId);
			if(infoFormBean.getInfoContentType() == 10){
				infoFormBean.setUrl(Configs.getConfigs().getJmpUrl() + "/interfaces/survey/index.do");
			}
			isSuccess = infoService.add(infoFormBean);  
			if(isSuccess){
				//修改栏目变动标识位
				isSuccess = colService.modifyFlag(infoFormBean.getColId()+"");
			}
		} catch (OperationException e) {
			message = e.getMessage();
		} 
		if (isSuccess) {
			script.addScript("parent.success", localpage);  
		} else {
			 script.addAlert("新增失败！" + message).addScript(
	                    "location.href='" + localpage + "';"); 
		}   
		return script.getScript();
	}   
	
	/**
	 * 修改页面
	 * @param iid iid
	 * @param localpage localpage
	 * @param onlylook onlylook
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "modify_show")
	public ModelAndView showModify(String iid, String localpage, String onlylook) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		int siteId = currentUser.getSiteId();
		int infoId = NumberUtil.getInt(iid); 
		Info info = infoService.findByIid(infoId, siteId);
		int colid=NumberUtil.getInt(info.getColId());
		//栏目类型 1、虚拟栏目2、普通栏目
		int type = 0;
		//栏目信息审核方式： 1：手动 2：立即 3：限时审核
		int auditType = 0;
		String[] pointNames = {"类型", "地点", "路线", "街景"};
		String poitypename = "";
		String poilocationtmp = "";
		int colType=0;
		if(colid>0){
			Col col = colService.findByIid(colid);
			if(col != null){
				type = col.getType();
				auditType = col.getAuditType();
				colType=col.getInfoContentType();
			}
		}  
		//导航类型信息
		poitypename = pointNames[info.getPointType()];
		String strLocation = info.getPointLocation();
		if(StringUtil.isEmpty(strLocation)){
			strLocation = "坐标";
		}
		poilocationtmp = strLocation;
		ModelAndView modelAndView = new ModelAndView("jmp/cms/infos/info_opr");	
		String ztName = "";
		int infoContentType = NumberUtil.getInt(info.getInfoContentType()); 
		if(infoContentType == 8){
			Col col = colService.findByIid(info.getZtId());
			if(col != null){
				ztName = "(栏目)" + col.getName();
			}
		} else if (infoContentType == 9){ 
			if(NumberUtil.getInt(info.getZtId())>0){
				Channel channel = channelService.findByIid(info.getZtId()); 
				if(channel != null){
					ztName = "(频道)"+channel.getName();
				}
			}
		} else if (infoContentType == 10){
			if(NumberUtil.getInt(info.getZtId())>0){
				Survey survey = surveyService.findByIid(info.getZtId()); 
				if(survey != null){
					ztName = "(调查)"+survey.getName();
				}
			}
		}
		//将正文中的附件标记替换成路径
		info.setContent(infoService.findContent(BaseInfo.getRealPath()+info.getPath())); 
		Map<Integer, String> infoListType = typeConfig.getInfoListType(siteId);
		Map<Integer, String> infoContentType1 = typeConfig.getInfoContentType(siteId);
		modelAndView.addObject("ztName", ztName);
		modelAndView.addObject("localpage", localpage);
		modelAndView.addObject("poitypename", poitypename);
		modelAndView.addObject("poilocationtmp", poilocationtmp);
		modelAndView.addObject("synshowtime", DateUtil.dateToString(info.getSynTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		modelAndView.addObject("pushshowtime", DateUtil.dateToString(info.getPushTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		modelAndView.addObject("type", type); 
		modelAndView.addObject("auditType", auditType); 
		modelAndView.addObject("operatename", "信息管理");
		modelAndView.addObject("operatetype", "编辑");
		modelAndView.addObject("url", "modify_submit.do"); 
		modelAndView.addObject("customFields", fieldService.findUnSysBySiteid(siteId));
		modelAndView.addObject("infoExpand", info.getInfoExpand());
		modelAndView.addObject("PicFileType", Configs.getConfigs().getPicFileType());
		modelAndView.addObject("VideoFileType", Configs.getConfigs().getVideoFileType());
		modelAndView.addObject("AudioFileType", Configs.getConfigs().getAudioFileType());
		modelAndView.addObject("InfoListType", infoListType);
		modelAndView.addObject("InfoContentType", infoContentType1);
		modelAndView.addObject("colType", colType); 
		modelAndView.addObject("bookTree", infoService.findModifyTagTree(siteId, info));
		modelAndView.addObject("onlylook", NumberUtil.getInt(onlylook));
		Sign dimensionEn = signService.findByIid(NumberUtil.getInt(info.getTagid()));
		if(dimensionEn!=null){
			info.setTagname(dimensionEn.getDname());
		}	
		modelAndView.addObject("info", info);  
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}  
		modelAndView.addObject("jmpurl", jmpUrl);
		this.addOtherObject(modelAndView, siteId);
		return modelAndView;
	}

	/**
	 * 修改提交
	 * @param info info
	 * @param localpage localpage
	 * @param auditType auditType
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public String submitModify(InfoFormBean info, String localpage, Integer auditType){
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		int siteId = currentUser.getSiteId();
		boolean isSuccess = false;
		String message = "";
		try {   
			//立即审核
			if(auditType == 2){
				info.setStatus(1);
			}else{
				info.setStatus(0);
			}
			info.setSiteId(siteId);
			if(info.getInfoContentType() == 10){
				info.setUrl(Configs.getConfigs().getJmpUrl() + "/interfaces/survey/index.do");
			}
			isSuccess = infoService.modify(info); 
			if(isSuccess){
				isSuccess = colService.modifyFlag(info.getColId()+"");
			}
		} catch (OperationException e) {
			message = e.getMessage();
		}
		Script script = Script.getInstanceWithJsLib();
		if (isSuccess) {
			//清除缓存 
			String key = siteId + "_" + info.getIid(); 
			CacheUtil.removeKey(StaticValues.CACHE_REGION, 
					key);
			script.addScript("parent.success", localpage); 
		} else {
			script.addAlert("修改失败！" + message).addScript(
                    "location.href='" + localpage + "';"); 
		}  
		return script.getScript();
	}
	
	/**
	 * 审核信息
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
			isSuccess = infoService.modifyAudit(ids, siteId);
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
	 * 撤审
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
			isSuccess = infoService.modifyUnAudit(ids, siteId);
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
	
	/**
	 * 取消置顶
	 * @param ids ids
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "untop")
	@ResponseBody
	public JsonResult untopInfo(String ids, Integer siteId, Integer colId) {
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			isSuccess = infoService.modifyToptime("", ids, 0, siteId);
			if (isSuccess) {
				colService.modifyFlag(colId+"");
				jsonResult.set(ResultState.OPR_SUCCESS, "取消置顶成功");
			} else {
				jsonResult.set(ResultState.OPR_FAIL, "取消置顶失败");
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult;   
	}
		
	/** 
	 * 排序页面
	 * @param colId colId
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "sort_show")
	@ResponseBody
	public ModelAndView order(String colId) {
		ModelAndView modelAndView =new ModelAndView("jmp/cms/infos/info_sort");
		int siteId = UserSessionInfo.getCurrentUser().getSiteId(); 
		List<Info> infoList = infoService.findAllInfoByColid(NumberUtil.getInt(colId), siteId);
		modelAndView.addObject("url", "sort_submit.do");
		modelAndView.addObject("infoList", infoList);
		modelAndView.addObject("colid", colId);
		return modelAndView;
	}
	
	/**
	 * 按时间降序排序
	 * @param colid
	 * @return
	 */
	@RequestMapping(value = "infodesc")
	@ResponseBody
	public JsonResult descByTime(Integer colid){
		int siteId = UserSessionInfo.getCurrentUser().getSiteId(); 
		JsonResult jsonResult = JsonResult.getInstance(); 
		infoService.update(siteId,colid);
		return jsonResult;  
	}

	/**
	 * 排序提交
	 * @param ids ids
	 * @param orderids orderids
	 * @param colid colid
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "sort_submit")
	@ResponseBody
	public JsonResult submitSort(String ids, String orderids, String colid) {
		boolean isSuccess = false; 
		JsonResult jsonResult = JsonResult.getInstance();  
		try {
			isSuccess = infoService.modifyOrderIdById(ids, orderids, colid);
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
	 * 信息同步页面
	 * @param colId colId
	 * @return    设定参数 .
	 */
	@RequestMapping("addsyn_show")
	public ModelAndView showSynch(String colId){
		ModelAndView modelAndView = new ModelAndView("jmp/cms/infos/info_syn");
		InfoFormBean info = new InfoFormBean();   
		info.setColId(NumberUtil.getInt(colId)); 
		modelAndView.addObject("info", info);  
		modelAndView.addObject("url", "addsyn_submit.do"); 
		modelAndView.addObject("operatename", "信息管理");
		modelAndView.addObject("operatetype", "同步");
		return modelAndView;
	} 
	
	/**
	 * 文件上传
	 * @param picdata picdata
	 * @param picDesc picDesc
	 * @return String
	 * @throws Exception    设定参数 .
	 */
	@ResponseBody
	@RequestMapping("picfileupload")
	public String picfileupload(MultipartFile picdata, String picDesc) throws Exception {
		/*上传文件总大小*/  
		String syspath = Settings.getSettings().getFileTmp();  
		FileUtil.createDir(syspath); 
		String picName="";
		if (picdata == null || picdata.isEmpty()) {
			return  "{\"picName\":\""+picName+"\"," +
					"\"picDesc\":\""+picDesc+"\",\"state\":\"false\"}";
		} 
		String fileName = picdata.getOriginalFilename(); 
		String newName = StringUtil.getUUIDString();
		String fileType = null; 
		long  picSize = picdata.getSize();
		if (fileName.contains(".")) {
			int index = fileName.lastIndexOf(".") + 1;
			fileType = fileName.substring(index);
			newName = newName + "." + fileType.toLowerCase();
		} 
		ControllerUtil.writeMultipartFileToFile(new File(syspath + newName), picdata);  
		if(picDesc.equals(fileName)){
			picDesc = "图片说明为空";
		}
		return "{\"picName\":\"" + newName + "\",\"picDesc\":\"" +
			picDesc + "\",\"picSize\":\"" + picSize + "\",\"state\":\"success\"}";
	}
	
	/**
	 * 信息同步提交
	 * @param infoFormBean infoFormBean
	 * @param starttime starttime
	 * @param endtime endtime
	 * @return    设定参数 .
	 */
	@RequestMapping("addsyn_submit")
	@ResponseBody
	public JsonResult submitSynadd(InfoFormBean infoFormBean, String starttime, String endtime, String createtime){ 
		boolean isSuccess = false; 
		JsonResult jsonResult = JsonResult.getInstance();  
		try {
			Col col=colService.findByIid(infoFormBean.getColId()); 
			isSuccess = infoService.synInfo(col, starttime, endtime, 0, createtime);
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
	 * 信息置顶
	 * @param infoids infoids
	 * @param colId colId
	 * @return    设定参数 .
	 */
	@RequestMapping("addtop_show")
	public ModelAndView showTop(String infoids, Integer colId){	 
		ModelAndView modelAndView =new ModelAndView("jmp/cms/infos/info_top"); 
		modelAndView.addObject("colid", colId); 
		modelAndView.addObject("infoids", infoids);
		modelAndView.addObject("url", "addtop_submit.do"); 
		modelAndView.addObject("operatename", "信息管理");
		modelAndView.addObject("operatetype", "置顶");
		return modelAndView;
	}
   
	/**
	 * 信息置顶提交
	 * @param infoids infoids
	 * @param colid colid
	 * @param toptime toptime
	 * @return    设定参数 .
	 */
	@RequestMapping("addtop_submit")
	@ResponseBody
	public JsonResult submitTopadd(String infoids, Integer colid, String toptime){ 
		boolean isSuccess = false; 
		JsonResult jsonResult = JsonResult.getInstance();  
		try {
			isSuccess = infoService.modifyToptime(toptime, infoids, colid);
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
	 * 信息推送页面
	 * @param infoId infoId
	 * @param colId colId
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "push_show")
	public ModelAndView showPush(int infoId, int colId, int siteId){
		ModelAndView modelAndView = new ModelAndView("jmp/cms/infos/push_opr");
		modelAndView.addObject("infoId", infoId);
		modelAndView.addObject("colId", colId);
		modelAndView.addObject("siteId", siteId);
		modelAndView.addObject("url", "push_submit.do"); 
		this.addOtherObject(modelAndView);
		return modelAndView;
	}
	
	/**
	 * 信息推送提交
	 * @param colId colId
	 * @param infoId infoId
	 * @param siteId siteId
	 * @param delayTime delayTime
	 * @param offLine offLine
	 * @param pushtimetype pushtimetype
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "push_submit")
	@ResponseBody
	public JsonResult pushModify(Integer colId, Integer infoId, Integer siteId, String delayTime, Integer offLine, Integer pushtimetype, Integer sendType, Integer sendtypeid, String userIds, Integer ispublic){ 
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			Info info = infoService.findByIid(infoId, siteId);
			if(info == null || NumberUtil.getInt(info.getIid()) == 0){
				throw new OperationException("信息不存在！");
			}
			/*判断是否为即时推送 */
			if(NumberUtil.getInt(pushtimetype)==1 || StringUtil.isEmpty(delayTime)){
				info.setPushTime(new Date());
				info.setPushtimeshow(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			}else{
				info.setPushTime(DateUtil.stringtoDate(delayTime, "yyyy-MM-dd HH:mm:ss"));
				info.setPushtimeshow(DateUtil.dateToString(DateUtil.stringtoDate(delayTime, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
			}
			info.setPushOfl(offLine);
			
			isSuccess = infoService.updatePushState(info);
			PushThread pushThread = new PushThread();
			pushThread.setInfoId(info.getIid());
			pushThread.setSiteId(info.getSiteId());
			pushThread.setPushTime(info.getPushTime());
			pushThread.setSendtypeid(sendtypeid);
			pushThread.setUserIds(userIds);
			pushThread.setSendType(sendType);
			pushThread.setIspublic(ispublic);
			isSuccess = isSuccess && pushThreadService.add(pushThread);
			String users = "";
			if(sendType == 0){
				List<User> user = userService.findAllUser(siteId);
				for(User u:user){
					users += ","+StringUtil.getString(u.getIid());
				}
			}else if(sendType == 1){
				List<User> user = userService.findByGroupid(sendtypeid);
				for(User u:user){
					users += ","+StringUtil.getString(u.getIid());
				}
			}else if(sendType == 2){
				List<PeoplelistRelation> list = peoplelistRelationService.findBypeoplelistid(sendtypeid);
				for(PeoplelistRelation p:list){
					users += ","+StringUtil.getString(p.getUserId());
				}
			}else if(sendType == 3){
				users = userIds;
			}
			
			infoDetailService.add(info, users, ispublic);
			
			PushTask.state();
			if (isSuccess) {  
				jsonResult.set(ResultState.OPR_SUCCESS);
			} else {
				jsonResult.set(ResultState.OPR_FAIL);
			}
			throw new OperationException("");
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		}
		return jsonResult; 
	}
	
	/**
	 * 信息删除页面
	 * @param ids ids
	 * @param idlength idlength
	 * @return    设定参数 .
	 */
	@RequestMapping("remove_show")
	public ModelAndView showRemove(String ids, Integer idlength){ 
		ModelAndView modelAndView = new ModelAndView("jmp/cms/infos/removeinfo_opr"); 
		modelAndView.addObject("url", "remove.do");
		modelAndView.addObject("idlength", idlength); 
		modelAndView.addObject("ids", ids);  
		return modelAndView;
	}
	
	/**
	 * 信息清空页面 
	 * @param colId 栏目id
	 * @return ModelAndView 信息新建页面
	 */
	@RequestMapping("clean_show")
	public ModelAndView showClean(Integer colId){ 
		ModelAndView modelAndView = new ModelAndView("jmp/cms/infos/clearinfo_opr"); 
		modelAndView.addObject("url", "clean.do"); 
		modelAndView.addObject("colId", colId);  
		return modelAndView;
	}
	
	/**
	 * 信息清空
	 * @param colId colId
	 * @param showList showList
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "clean")
	@ResponseBody
	public JsonResult clean(int colId, Integer showList) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		if(!currentUser.getIsWebSiteAdmin()){
			return null;
		} 
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			Integer siteId = currentUser.getSiteId();
			//信息放入回收站
			if(NumberUtil.getInt(showList)==1){ 
				isSuccess = infoService.modifyIsRemoveByColId(1, colId, siteId);
			} else {
			//彻底删除
				isSuccess = infoService.removeByColId(colId, siteId, 0);
			}
			if (isSuccess) { 
				jsonResult.set(ResultState.OPR_SUCCESS, "清空成功");
			} else {
				jsonResult.set(ResultState.OPR_FAIL, "清空失败");
			}
		} catch (OperationException e) {
			jsonResult.setMessage(e.getMessage());
		} 
		return jsonResult;    
	}
	
	/**
	 * 信息删除提交
	 * @param ids ids
	 * @param showList showList
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids, Integer showList) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId=currentUser.getSiteId();
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			//信息删除放入回收站
			if(NumberUtil.getInt(showList)==1){
				isSuccess = infoService.modifyIsRemove(1, ids, siteId);
			}else{
				isSuccess = infoService.removeByIds(ids, siteId, 0);
			}
			if (isSuccess) {
				//删除信息对应的标签关联
				List<Integer> idList = StringUtil.toIntegerList(ids, ",");
				signRelService.removeByInfoIds(idList);
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
	 * 信息引用/复制 页面
	 * @param pid
	 * @return
	 */
	@RequestMapping("qc_show")
	public ModelAndView showQuote(String pid){
		ModelAndView modelAndView = new ModelAndView("jmp/cms/infos/info_qc");
		modelAndView.addObject("pid", pid);
	    modelAndView.addObject("orgType", "c");
		return modelAndView;
	}
	
	/**
	 * 信息模板二维码
	 * @param appId
	 * @return
	 */
	@RequestMapping("scanQRCode")
	public ModelAndView scanQRCode(String iid) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteid = currentUser.getSiteId();
		ModelAndView modelAndView = new ModelAndView("jmp/cms/infos/scanQRCode");
		String url = BaseInfo.getDomain()+"/webapp/qrcode/" + iid + ".png";
		String file = BaseInfo.getRealPath()+"/webapp/qrcode/" + iid + ".png";
		File htmlFile = new File(file);
		if(!htmlFile.exists()){
			Info info = infoService.findByInfoId(NumberUtil.getInt(iid), "jmp_info"+siteid);
			info.setContent(infoService.findContent(BaseInfo.getRealPath()+info.getPath())); 
			infoService.findshareInfo(info);
			FileUtil.isDirExsit(new File(BaseInfo.getRealPath() + "/webapp/qrcode/"), true);
			String imgPath = BaseInfo.getRealPath() + "/webapp/qrcode/" + info.getIid() + ".png";
			//生成二维码
			QRCodeUtil.createQRCode(BaseInfo.getDomain()+"/resources/jmp/html/"+info.getIid()+".html", imgPath, null);
		}
		modelAndView.addObject("url", url);
		modelAndView.addObject("code", "app");
		return modelAndView;
	}
	
	/**
     * 信息引用复制转移页-组织栏目树
     * @param pid
     * @return
     */
    @RequestMapping("infos_load")
    @ResponseBody
    public String loadColsData(String pid) {
        List<HashMap<String, Object>> colNodes = new ArrayList<HashMap<String, Object>>();
        CurrentUser currentUser = UserSessionInfo.getCurrentUser();
        HashMap<String, Object> colNode = null;
        int colId = NumberUtil.getInt(pid);
        Integer siteId = currentUser.getSiteId();
        List<Col> colList = colService.findColByColIdAndType(colId, siteId, 2);
        String text;
        if(CollectionUtils.isNotEmpty(colList)){
            for (Col col : colList) {
                colNode = new HashMap<String, Object>();
                colNode.put("id", col.getIid());
                text = "<span class='optionname'>" + col.getName() + "</span>";
                if (col.getName() != null && col.getName().length() > 0) {
                    text += " &lt;<span class='ic'>" + "栏目ID:" + col.getIid() + "</span>&gt;";
                }
                colNode.put("text", text);
                colNode.put("state", "");
                colNodes.add(colNode);
            }
        }
        String ids = "";
        for(HashMap<String, Object> map : colNodes){
            ids += map.get("id") + ",";
        }
        ids = ids.substring(0, ids.length()-1);
        colNode = new HashMap<String, Object>();
        colNode.put("id", ids);
        colNode.put("text", "<span class='optionname'>全部栏目</span>");
        colNode.put("children", colNodes);
        colNodes = new ArrayList<HashMap<String, Object>>();
        colNodes.add(colNode);
        return JsonUtil.objectToString(colNodes);
    }
	
	/**
	 * 信息引用/复制 提交
	 * @param ids
	 * @param colId
	 * @return
	 */
	@RequestMapping(value = "qc_submit")
	@ResponseBody
	public JsonResult quoteSubmit(String ids, String colIds, String flag) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		JsonResult jsonResult = JsonResult.getInstance();
		boolean success;
		try {
			success = infoService.addInfoToCol(ids, colIds, siteId, flag);
			if (success) {
				jsonResult.set(ResultState.OPR_SUCCESS);
			} else {
				jsonResult.set(ResultState.OPR_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage("操作失败！"+e.getMessage());
		} //信息引用
		return jsonResult; 
	}

	/**
	 * 信息转移页
	 * @return
	 */
	@RequestMapping("transfer_show")
	public ModelAndView showTransfer(int colId,String ids){
		ModelAndView modelAndView = new ModelAndView("jmp/cms/infos/info_refer");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		modelAndView.addObject("tree", infoService.findAddColTree(siteId,colId));
		modelAndView.addObject("url", "transfersubmit.do");
		modelAndView.addObject("ids", ids);
		return modelAndView;
	}
	
	/**
	 * 信息转移
	 * @param ids
	 * @param colId
	 * @return
	 */
	@RequestMapping(value = "transfersubmit")
	@ResponseBody
	public JsonResult transferSubmit(String ids, String colId) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		JsonResult jsonResult = JsonResult.getInstance();
		boolean success;
		try {
			success = infoService.addInfoToCol(ids, colId, siteId, "T");
			if (success) {
				jsonResult.set(ResultState.OPR_SUCCESS);
			} else {
				jsonResult.set(ResultState.OPR_FAIL);
			}
		} catch (OperationException e) {
			jsonResult.setMessage("信息转移失败！"+e.getMessage());
		} //信息引用
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
	 * 组织消息操作页面其它参数
	 * 
	 * @param modelAndView
	 *           
	 */
	private void addOtherObject(ModelAndView modelAndView) {
		// 组织树
		Tree groupTree = Tree.getInstance();
		Tree clusterTree = Tree.getInstance();
		User user = UserSessionInfo.getCurrentUser();
		Integer rangeId = 0;
		String rangeName = "";
		if(!user.getIsSysAdmin()){			//非系统管理员只能选择自己所在机构或所在机构的子孙机构
			rangeId = user.getGroupId();
			rangeName = user.getGroupName();
		}
		String nodeName = StringUtil.isEmpty(rangeName) ? "机构选择" : rangeName;
		groupTree.addNode(TreeNode.getInstance(rangeId + "", "groupmenu", nodeName
				, (StringUtil.isEmpty(rangeName) ? true : false)).setClick("onTopClick()"));
		
		List<Group> groupList = groupService.findChildGroupByIid(NumberUtil.getInt(rangeId),user.getSiteId());
		for (Group group : groupList) {
			groupTree.addNode(TreeNode.getInstance(group.getIid() + "", rangeId + "",
					group.getName(), null, group.getIsParent(), false));
		}

		clusterTree.addNode(TreeNode.getInstance("0", "clustermenu", "群组选择", true).setClick(
			"onTopClick1()"));
		
		modelAndView.addObject("groupMenu", groupTree.parse()); 
		modelAndView.addObject("clusterMenu", clusterTree.parse());
	}
	
	
}