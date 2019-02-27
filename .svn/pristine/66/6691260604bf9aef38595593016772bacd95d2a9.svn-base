package com.hanweb.jmp.sys.controller.parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.annotation.Permission;
import com.hanweb.common.permission.Allowed;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.common.util.file.LocalFileUtil;
import com.hanweb.common.util.mvc.FileResource;
import com.hanweb.common.util.mvc.Script; 
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.entity.sites.SiteSplash;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.jmp.util.CutStringUtil;
import com.hanweb.support.controller.CurrentUser;

@Controller
@Permission(module = "parameter", allowedGroup = Allowed.YES)
@RequestMapping("manager/site")
public class OprParameterController {

	/**
	 * siteService
	 */
	@Autowired
	SiteService siteService;

	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;

	/**
	 * 修改网站参数页
	 * @param iid iid
	 * @return    设定参数 .
	 */
	@RequestMapping("modifyParameter_show")
	public ModelAndView showModifyParameter(int iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/sys/parameter/parameter_opr");
		Site site = siteService.findByIid(iid);
		modelAndView.addObject("url", "modifyParameter_submit.do"); 
		modelAndView.addObject("site", site);
		String jmpUrl=Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl=fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl=jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}  
		modelAndView.addObject("jmpurl", jmpUrl);
		return modelAndView;
	}

	/**
	 * 修改提交
	 * @param site site
	 * @return    设定参数 .
	 */
	@RequestMapping("modifyParameter_submit")
	@ResponseBody
	public String submitModifyParameter(ParameterFormBean site, MultipartFile file_logo, MultipartFile file_iphone1,
			MultipartFile file_iphone2, MultipartFile file_iphone3, MultipartFile file_iphone4, MultipartFile file_android1,
			MultipartFile file_ipad1) {
		//设置siteid
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		String jopUrl = site.getSiteSplash().getJopUrl();
		SiteSplash siteSplash = site.getSiteSplash();
		if(jopUrl !=null && !"".equals(jopUrl)){
			String str=jopUrl.substring(jopUrl.length()-1,jopUrl.length());
			if(!"/".equals(str)){
				jopUrl = jopUrl+"/";
			}
		}
		siteSplash.setJopUrl(jopUrl);
		int siteid = currentUser.getSiteId();	
		site.setIid(siteid);
		site.setSiteSplash(siteSplash);
		boolean isSuccess = false;
		String message = "";
		try { 
			isSuccess = siteService.modify(site, file_logo, file_iphone1, file_iphone2, 
					file_iphone3, file_iphone4, file_android1, file_ipad1);   
		} catch (OperationException e) { 
			message = e.getMessage();
		} catch (Exception e) {
			message = e.getMessage();
		}
		Script script = Script.getInstanceWithJsLib();
		if (isSuccess) {
			script.addScript("parent.success", "更新成功！");
		} else {
			script.addScript("parent.fail", "更新失败！" + message);
		}
		return script.getScript();
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

}