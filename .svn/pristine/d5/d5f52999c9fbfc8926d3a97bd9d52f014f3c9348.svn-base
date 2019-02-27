package com.hanweb.jmp.interfaces.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.common.util.file.LocalFileUtil;
import com.hanweb.jmp.annotation.InterfaceCache;

import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.InterfaceLogConfig;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.entity.sites.SiteSplash;
import com.hanweb.jmp.sys.entity.version.Version;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.jmp.sys.service.sites.SiteSplashService;
import com.hanweb.jmp.sys.service.version.VersionService;

@Controller
@RequestMapping("interfaces")
public class SiteController {
	
	/**
	 * siteSplashService
	 */
	@Autowired
	SiteSplashService siteSplashService;
	
	/**
	 * versionService
	 */
	@Autowired
	VersionService versionService;
	
	/**
	 * siteService
	 */
	@Autowired
	private SiteService siteService;
	
	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;

	/**
	 * APP封面(接口1.1)
	 * @param flag splash变动标记位
	 * @param siteid 网站id
	 * @param clienttype 客户端类型
	 * @return String
	 */
	@RequestMapping("splash")
	@ResponseBody
	@InterfaceCache
	public String splash(Integer flag, Integer siteid, Integer clienttype){
		Map<String, Object> result = new HashMap<String, Object>();
		Site site = siteService.findByIid(NumberUtil.getInt(siteid)); 
		if(site == null){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_SPLASH, 
					InterfaceLogConfig.ERROR_07); 
		}
		SiteSplash siteSplash = site.getSiteSplash();
		if(siteSplash == null){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_SPLASH, 
					InterfaceLogConfig.ERROR_07); 
		}
		String firstPic = "";
		String middlePic = "";
		String lastPic = "";
		String firstTitle = "";
		String middleTitle = "";
		String lastTitle = "";
		String firstUrl = "";
		String middleUrl = "";
		String lastUrl = "";
		String strPic = "";
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}
		switch (clienttype) {
		case 2:
			result.put("flag", NumberUtil.getInt(siteSplash.getIphoneVersion()) + "");
			firstPic = siteSplash.getFirstIphonePic() ;
			middlePic = siteSplash.getMiddleIphonePic();
			lastPic = siteSplash.getLastIphonePic();
			firstTitle = siteSplash.getFirstTitle();
			middleTitle = siteSplash.getMiddleTitle();
			lastTitle = siteSplash.getLastTitle();
			firstUrl = siteSplash.getFirstUrl();
			middleUrl = siteSplash.getMiddleUrl();
			lastUrl = siteSplash.getLastUrl();
			break;
		case 3:
			result.put("flag", NumberUtil.getInt(siteSplash.getAndroidVersion()) + "");
			firstPic = siteSplash.getFirstAndroidPic();
			middlePic = siteSplash.getMiddleAndroidPic();
			lastPic = siteSplash.getLastAndroidPic();
			firstTitle = siteSplash.getFirstTitle();
			middleTitle = siteSplash.getMiddleTitle();
			lastTitle = siteSplash.getLastTitle();
			firstUrl = siteSplash.getFirstUrl();
			middleUrl = siteSplash.getMiddleUrl();
			lastUrl = siteSplash.getLastUrl();
			break;
		case 4:
			result.put("flag", NumberUtil.getInt(siteSplash.getIpadVersion()) + "");
			firstPic = siteSplash.getFirstIpadPic();
			middlePic = siteSplash.getMiddleIpadPic();
			lastPic = siteSplash.getLastIpadPic();
			firstTitle = siteSplash.getFirstTitle();
			middleTitle = siteSplash.getMiddleTitle();
			lastTitle = siteSplash.getLastTitle();
			firstUrl = siteSplash.getFirstUrl();
			middleUrl = siteSplash.getMiddleUrl();
			lastUrl = siteSplash.getLastUrl();
			break;
		}
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> picMap = null;
		
		picMap = new HashMap<String, String>();
		strPic = "";
		if(StringUtil.isNotEmpty(firstPic)){
			strPic = jmpUrl + firstPic;
		}
		if(StringUtil.isNotEmpty(siteSplash.getImguuid())){
		    picMap.put("pic", StringUtil.getString(strPic) + "?" + siteSplash.getImguuid());
		} else {
		    picMap.put("pic", StringUtil.getString(strPic));
		}
		picMap.put("text", StringUtil.getString(firstTitle));
		picMap.put("link", StringUtil.getString(firstUrl));
		list.add(picMap);
		
		picMap = new HashMap<String, String>();
		strPic = "";
		if(StringUtil.isNotEmpty(middlePic)){
			strPic = jmpUrl + middlePic;
		}
		if(StringUtil.isNotEmpty(siteSplash.getImguuid())){
            picMap.put("pic", StringUtil.getString(strPic) + "?" + siteSplash.getImguuid());
        } else {
            picMap.put("pic", StringUtil.getString(strPic));
        }
		picMap.put("text", StringUtil.getString(middleTitle));
		picMap.put("link", StringUtil.getString(middleUrl));
		list.add(picMap);
		
		picMap = new HashMap<String, String>();
		strPic = "";
		if(StringUtil.isNotEmpty(lastPic)){
			strPic = jmpUrl + lastPic;
		}
		lastTitle = StringUtil.getString(lastTitle); 
		if(StringUtil.isNotEmpty(siteSplash.getImguuid())){
            picMap.put("pic", StringUtil.getString(strPic) + "?" + siteSplash.getImguuid());
        } else {
            picMap.put("pic", StringUtil.getString(strPic));
        }
		picMap.put("text", StringUtil.getString(lastTitle));
		picMap.put("link", StringUtil.getString(lastUrl));
		list.add(picMap);
		result.put("pics", list);
		result.put("logintype", StringUtil.getString(site.getLoginType()));
		result.put("overall", StringUtil.getString(site.getOverall()));
		return JsonUtil.objectToString(result);
	}
	
	/**
	 * 版本更新接口(接口1.11)
	 * @param siteid 网站id
	 * @param clienttype 客户端类型
	 * @param version 客户端版本号
	 * @return String
	 */
	@RequestMapping("version")
	@ResponseBody
	@InterfaceCache
	public String version(Integer siteid, Integer clienttype, String version){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Version version1 = versionService.findNewVersionClient(NumberUtil.getInt(siteid)
					, NumberUtil.getInt(clienttype));
				version = StringUtil.getString(version);
			if(version!=null && version1!=null && !version.equals(StringUtil.getString(version1.getVersion())) 
					&& VersionService.isMaxVersion(version1.getVersion(), version)){
				result.put("hasnew", StringUtil.getString(NumberUtil.getInt(version1.getUpdateType())));
				result.put("downloadurl", version1.getDownUrl());
				result.put("html", version1.getMsg());
				HashMap<String, Object> versionmsg = new HashMap<String, Object>();
				versionmsg.put("newversion", version1.getVersion());
				versionmsg.put("prompt", StringUtil.escapeHTML(version1.getMsg()));
				result.put("updatemsg", versionmsg);
			}else{ //无新版本
				result.put("hasnew", "0");
				result.put("downloadurl", "");
				result.put("html", "");
				HashMap<String, Object> versionmsg = new HashMap<String, Object>();
				versionmsg.put("newversion", "");
				versionmsg.put("prompt", "");
				result.put("updatemsg", versionmsg);
			}
			return JsonUtil.objectToString(result);
			
		} catch (Exception e) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_VERSION, 
					InterfaceLogConfig.ERROR_08); 
		}
	}
	
}