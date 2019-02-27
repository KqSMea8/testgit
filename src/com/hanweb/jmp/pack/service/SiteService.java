package com.hanweb.jmp.pack.service;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.xml.XmlDocument;
import com.hanweb.complat.entity.User;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.cms.service.infos.InfoCountService;
import com.hanweb.jmp.cms.service.infos.InfoService;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.StaticValues;
import com.hanweb.jmp.sys.controller.sites.SiteFormBean;
import com.hanweb.jmp.sys.dao.sites.SiteDAO;
import com.hanweb.jmp.sys.entity.parameter.Parameter;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.entity.sites.SiteSplash;
import com.hanweb.jmp.sys.service.log.InterfaceLogService;
import com.hanweb.jmp.sys.service.sites.SiteSplashService;
import com.hanweb.jmp.util.CacheUtil;

public class SiteService {

	/**
	 * siteDAO
	 */
	@Autowired
	private SiteDAO siteDAO;

	/**
	 * parameterService
	 */
	@Autowired
	private ParameterService parameterService;

	/**
	 * siteSplashService
	 */
	@Autowired
	private SiteSplashService siteSplashService;
	
	/**
	 * infoService
	 */
	@Autowired
	private InfoService infoService;
	
	/**
	 * infoCountService
	 */
	@Autowired
	private InfoCountService infoCountService;
	
	/**
	 * interfaceLogService
	 */
	@Autowired
	private InterfaceLogService interfaceLogService; 
	
	/**
	 * userService
	 */
	@Autowired
	private UserService userService;   
	
	/**
	 * 后台新建网站
	 * @param siteBean siteBean
	 * @return Integer
	 * @throws OperationException    设定参数 .
	 */
	public Integer addSite(SiteFormBean siteBean) throws OperationException {
		if (siteBean == null) {
			return 0;
		}
		Site site = siteBean.getSite();
		boolean isSuccess = true;
		String sitePath = "";

		// 3、网站表入库,需回滚
		site.setAppFrom(1);
		site.setCreateTime(new Date());
		site.setUuid(StringUtil.getUUIDString());
		Integer siteId = siteDAO.insert(site);
		
		//生成网站对应的信息表
		infoService.addTable(infoService.getTableName(siteId));
		
		//生成网站对应的信息阅读数表
		infoCountService.addTable(infoCountService.getTableName(siteId));
		
		//生成网站对应的接口日志表
		interfaceLogService.addTable(interfaceLogService.getTableName(siteId));
		
		//是否支持用户注册和注册用户默认状态
		site.setRegState(0);
		site.setIsSupportReg(0);
		
		// 4、新增网站详细表,需回滚
		Parameter parameter = new Parameter();
		parameter.setSiteId(siteId);
		parameterService.add(parameter);

		// 5、新增网站引导页表,需回滚
		SiteSplash siteSplash = new SiteSplash();
		siteSplash.setSiteId(siteId);
		siteSplash.setAndroidVersion(0);
		siteSplash.setIphoneVersion(0);
		siteSplash.setIpadVersion(0);
		siteSplashService.add(siteSplash);
		
		// 6、网站表更新,需回滚
		String url = "web/site" + siteId;
		if (Configs.getConfigs().getJmpUrl().endsWith("/")) {
			url = Configs.getConfigs().getJmpUrl() + url;
		} else {
			url = Configs.getConfigs().getJmpUrl() + "/" + url;
		}
		site.setIid(siteId);
		site.setUrl(url); 
		isSuccess = siteDAO.updateUrlAndUserId(site);
		if (!isSuccess) {
			throw new OperationException("");
		}

		// 7、写网站配置文件
		sitePath = BaseInfo.getRealPath() + "/web/site" + siteId + "";
		String filePath = sitePath + "/config.xml";
		File file = new File(sitePath);
		if (!file.exists()) {
			isSuccess = file.mkdirs();
		}
		file = new File(filePath);
		XmlDocument xml = new XmlDocument(); // utf-8
		xml.createRoot("root");	
		xml.addNode("root", "siteid", "" + siteId);
		xml.saveAs(file);

		// 8、保存网站id到缓存,需回滚
		site.setParameter(parameter);
		site.setSiteSplash(siteSplash);
		CacheUtil.setValue(StaticValues.CACHE_SITE, "" + siteId, site);

		if(!isSuccess){
			return 0;
		}
		return siteId;
	}
	
	/**
	 * 根据网站id查找网站实体
	 * @param iid
	 *            网站id
	 * @return Site 网站实体
	 */
	public Site findSiteByIid(Integer iid) {
		Site site = (Site) CacheUtil.getValue(StaticValues.CACHE_SITE, StringUtil.getString(iid));
		if (site == null) {
			site = siteDAO.findByIid(iid);
			if (site == null) {
				return null;
			}
			Parameter parameter = parameterService.findBySiteId(iid);
			SiteSplash siteSplash = siteSplashService.findBySiteId(iid);
			if (parameter == null) {
				parameter = new Parameter();
			}
			if (siteSplash == null) {
				siteSplash = new SiteSplash();
			}
			site.setParameter(parameter);
			site.setSiteSplash(siteSplash);
			CacheUtil.setValue(StaticValues.CACHE_SITE, ""+iid, site);
		}
		return site;
	}
	
	/**
	 * 根据网站id查找网站实体
	 * @param iid
	 *            网站id
	 * @return Site 网站实体
	 */
	public Site findByIid(Integer iid) {
		Site site = (Site) CacheUtil.getValue(StaticValues.CACHE_SITE, StringUtil.getString(iid));
		if (site == null) {
			site = siteDAO.findByIid(iid);
			if (site == null) {
				return null;
			}
			Parameter parameter = parameterService.findBySiteId(iid);
			SiteSplash siteSplash = siteSplashService.findBySiteId(iid);
			if (parameter == null) {
				parameter = new Parameter();
			}
			if (siteSplash == null) {
				siteSplash = new SiteSplash();
			}
			site.setParameter(parameter);
			site.setSiteSplash(siteSplash);
			CacheUtil.setValue(StaticValues.CACHE_SITE, ""+iid, site);
		}
		return site;
	}
	/**
	 * 后台修改网站
	 * @param siteBean
	 *            siteFromBean
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             操作异常
	 */
	public boolean modify(SiteFormBean siteBean) throws OperationException {
		if (siteBean == null) {
			return false;
		}
		Site site = siteBean.getSite();
		User user = siteBean.getUser();
		boolean isSuccess = true;
		isSuccess = siteDAO.update(site);
		// 3、用户表更新
		isSuccess = userService.modify(user);

		// 4、网站缓存更新
		site = siteDAO.findByIid(site.getIid());
		Site siteCache = (Site) CacheUtil.getValue(StaticValues.CACHE_SITE, ""+site.getIid());
		if (siteCache != null) {
			site.setParameter(siteCache.getParameter());
			site.setSiteSplash(siteCache.getSiteSplash());
		}
		CacheUtil.setValue(StaticValues.CACHE_SITE, ""+site.getIid(), site);
		return isSuccess;
	}
	
	/**
	 * 后台修改是否支持商城
	 * @param siteBean
	 *            siteFromBean
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             操作异常
	 */
	public boolean modify(Integer isDiccount, Integer overall, Integer iid){
		return siteDAO.updateDiscount(isDiccount, overall, iid);
	}

}