package com.hanweb.jmp.cms.controller.channels;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.common.util.file.LocalFileUtil;
import com.hanweb.common.util.mvc.FileResource;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.cms.entity.channels.Channel;
import com.hanweb.jmp.cms.service.channels.ChannelColService;
import com.hanweb.jmp.cms.service.channels.ChannelService;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.StaticValues;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.jmp.util.CacheUtil;
import com.hanweb.jmp.util.CutStringUtil;
import com.hanweb.support.controller.CurrentUser;

@Controller 
//@Permission(module = "/channel", allowedAdmin = Allowed.YES)
@RequestMapping("manager/channel")
public class OprChannelController {
	
	/**
	 *  siteService
	 */
	@Autowired
	private SiteService siteService;
	
	/**
	 *   channelService
	 */
	@Autowired
	ChannelService channelService;
	
	/**
	 * channelColService
	 */
	@Autowired
	ChannelColService channelColService;
	
	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;
	
	/**
	 * 添加
	 * @return  ModelAndView
	 */
	@RequestMapping("add_show")
	public ModelAndView showAdd(){
		ModelAndView modelAndView = new ModelAndView("jmp/cms/channels/channel_opr");
		CurrentUser user = UserSessionInfo.getCurrentUser();
		ChannelFormBean channelFromBean = new ChannelFormBean();
		channelFromBean.setSiteId(user.getSiteId());
		channelFromBean.setType(1);
		channelFromBean.setChanneltype(1);
        boolean isSupportReg = false;  
		Integer siteId = user.getSiteId();
		Site siteEn = siteService.findByIid(siteId);
		//若网站支持手机注册，则需要显示栏目访问权限
		if(siteEn.getIsSupportReg()==1){
			isSupportReg = true;
		} 
		modelAndView.addObject("isSupportReg", isSupportReg);
		modelAndView.addObject("asyncurl", "addBookCol_show.do");
		modelAndView.addObject("url", "add_submit.do");
		modelAndView.addObject("channel", channelFromBean);
		modelAndView.addObject("bookTree", channelService.findAddColTree(user.getSiteId()));
		modelAndView.addObject("siteId", user.getSiteId());
		modelAndView.addObject("PicFileType", Configs.getConfigs().getPicFileType());
		String jmpUrl=Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl=fileUtil.getURL("");
		}  
		if(jmpUrl.endsWith("/")){
			jmpUrl=jmpUrl.substring(0, jmpUrl.length()-1);
		} 
		modelAndView.addObject("jmpurl", jmpUrl);
		return modelAndView;
	}
	
	/**
	 * 修改
	 * @param iid  iid
	 * @return  ModelAndView
	 */
	@RequestMapping("modify_show")
	public ModelAndView showModify(String iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/cms/channels/channel_opr");
		CurrentUser user = UserSessionInfo.getCurrentUser();
		int channelId = NumberUtil.getInt(iid);
		Channel channel = channelService.findByIid(channelId);
		boolean isSupportReg = false; 
		Integer siteId = user.getSiteId();
		Site siteEn=siteService.findByIid(siteId);
		//若网站支持手机注册，则需要显示栏目访问权限
		if(siteEn.getIsSupportReg()==1){
			isSupportReg=true;
		} 
		modelAndView.addObject("isSupportReg", isSupportReg);
		modelAndView.addObject("asyncurl", "modifyBookCol_show.do");
		modelAndView.addObject("bookTree", channelService.findModifyColTree(user.getSiteId(), channelId));
		modelAndView.addObject("url", "modify_submit.do");
		modelAndView.addObject("channel", channel);
		modelAndView.addObject("PicFileType", Configs.getConfigs().getPicFileType());
		String jmpUrl=Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl=fileUtil.getURL("");
		}  
		if(jmpUrl.endsWith("/")){
			jmpUrl=jmpUrl.substring(0, jmpUrl.length()-1);
		} 
		modelAndView.addObject("jmpurl", jmpUrl);
		return modelAndView;
	}
	
	/**
	 * 新增提交
	 * @param channel  channel
	 * @return  String
	 */
	@RequestMapping("add_submit")
	@ResponseBody
	public String submitAdd(ChannelFormBean channel){
		CurrentUser user = UserSessionInfo.getCurrentUser();
		channel.setCreateTime(new Date());
		channel.setSiteId(user.getSiteId());
		boolean isSuccess = false;
		String message = "";
		try {
			isSuccess = channelService.add(channel);
		} catch (OperationException e) {
			message = e.getMessage();
		}
		Script script = Script.getInstanceWithJsLib();
		if (isSuccess) {
			CacheUtil.removeKey(StaticValues.CACHE_REGION, 
					             "first_"+NumberUtil.getInt(user.getSiteId()));
			script.addScript("parent.success", "新增成功！");
		} else {
			script.addScript("parent.fail", "新增失败！" + message);
		}
		return script.getScript();
	}
	
	/**
	 * 修改提交
	 * @param channel  channel
	 * @return  String
	 */
	@RequestMapping("modify_submit")
	@ResponseBody
	public String submitModify(ChannelFormBean channel) {
		CurrentUser user = UserSessionInfo.getCurrentUser();
		channel.setSiteId(user.getSiteId());
		boolean isSuccess = false;
		String message = "";
		try {
			isSuccess = channelService.modify(channel);   
		} catch (OperationException e) {
			message = e.getMessage();
		}
		Script script = Script.getInstanceWithJsLib();
		if (isSuccess) {
			CacheUtil.removeKey(StaticValues.CACHE_REGION, "first_"+NumberUtil.getInt(user.getSiteId()));
			script.addScript("parent.success", "更新成功！");
		} else {
			script.addScript("parent.fail", "更新失败！" + message);
		}

		return script.getScript();
	}

	/**
	 * 删除
	 * @param ids    ids
	 * @param names  names
	 * @return JsonResult
	 */
	@RequestMapping("remove")
	@ResponseBody
	public JsonResult remove(String ids, String names) {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		List<String> nameList = StringUtil.toStringList(names, ",");
		CurrentUser user = UserSessionInfo.getCurrentUser();
		boolean isSuccess = false;
		JsonResult jsonResult = JsonResult.getInstance();
		try {
			List<Channel>  channels=channelService.findByIids(ids);
			isSuccess = channelService.removeByIdsAndSiteId(idList, nameList, user.getSiteId(), true, channels);  
			if (isSuccess) {
				CacheUtil.removeKey(StaticValues.CACHE_REGION, "first_"+NumberUtil.getInt(user.getSiteId()));
				CacheUtil.removeKey(StaticValues.CACHE_SITE, StringUtil.getString(user.getSiteId())); 
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
	 * 排序
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "sort_show")
	@ResponseBody
	public ModelAndView srot() {
		ModelAndView modelAndView =new ModelAndView("jmp/cms/channels/channel_sort");
		CurrentUser user = UserSessionInfo.getCurrentUser();
		List<Channel> channelList = channelService.findAll(user.getSiteId());
		modelAndView.addObject("url", "sort_submit.do");
		modelAndView.addObject("channelList", channelList);
		return modelAndView;
	}
	
	/**
	 * 栏目排序
	 * @param channelId     channelId
	 * @return   ModelAndView
	 */ 
	@RequestMapping(value = "sortCol_show")
	@ResponseBody
	public ModelAndView sortCol(Integer channelId) {
		ModelAndView modelAndView = new ModelAndView("jmp/cms/channels/channelcol_sort");
		CurrentUser user = UserSessionInfo.getCurrentUser();
		List<ChannelColFormBean> channelColList = channelColService.findColByIid(user.getSiteId(), channelId);
		modelAndView.addObject("url", "sortCol_submit.do");
		modelAndView.addObject("channelColList", channelColList);
		return modelAndView;	
	}

	/**
	 * 排序提交
	 * @param ids       ids
	 * @param orderids  orderids
	 * @return  JsonResult
	 */
	@RequestMapping(value = "sort_submit")
	@ResponseBody
	public JsonResult submitSort(String ids, String orderids) {
		boolean isSuccess = false; 
		CurrentUser user = UserSessionInfo.getCurrentUser();
		JsonResult jsonResult = JsonResult.getInstance();  
		try {
			isSuccess = channelService.modifyOrderIdById(ids, orderids);
			if (isSuccess) {
				CacheUtil.removeKey(StaticValues.CACHE_REGION, "first_"+NumberUtil.getInt(user.getSiteId()));
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
	 * 提交
	 * @param ids      ids
	 * @param orderids orderids
	 * @return   JsonResult
	 */
	@RequestMapping(value = "sortCol_submit")
	@ResponseBody
	public JsonResult submitSortCol(String ids, String orderids) {
		CurrentUser user = UserSessionInfo.getCurrentUser();
		boolean isSuccess = false; 
		JsonResult jsonResult = JsonResult.getInstance();  
		try {
			isSuccess = channelColService.modifyOrderIdById(ids, orderids);
			if (isSuccess) {
				CacheUtil.removeKey(StaticValues.CACHE_REGION, "first_"+NumberUtil.getInt(user.getSiteId()));
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
	 * 文件下载
	 * @param filePath   filePath
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
		CurrentUser user = UserSessionInfo.getCurrentUser();
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = false;
		try { 
			isSuccess = channelService.modifyState(ids, NumberUtils.toInt(enable));
			if (isSuccess) {
				CacheUtil.removeKey(StaticValues.CACHE_REGION, "first_"+NumberUtil.getInt(user.getSiteId()));
				CacheUtil.removeKey(StaticValues.CACHE_SITE, StringUtil.getString(user.getSiteId())); 
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
	
}