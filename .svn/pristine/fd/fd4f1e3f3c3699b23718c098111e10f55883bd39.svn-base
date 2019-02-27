package com.hanweb.jmp.sys.service.sites;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.reg.LicenceCheck;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.Md5Util;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.ZipUtil;
import com.hanweb.common.util.file.IFileUtil; 
import com.hanweb.common.util.file.LocalFileUtil;
import com.hanweb.common.util.mvc.MultipartFileInfo;
import com.hanweb.common.util.xml.XmlDocument;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.complat.constant.Settings;
import com.hanweb.complat.dao.RoleDAO;
import com.hanweb.complat.dao.UserDAO;
import com.hanweb.complat.entity.Right;
import com.hanweb.complat.entity.Role;
import com.hanweb.complat.entity.User;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.complat.service.RightService;
import com.hanweb.complat.service.RoleRelationService;
import com.hanweb.complat.service.RoleRightService;
import com.hanweb.complat.service.UserService;

import com.hanweb.jmp.apps.service.manage.LightInitService;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.cms.service.infos.InfoCountService;
import com.hanweb.jmp.cms.service.infos.InfoService;
import com.hanweb.jmp.cms.service.matters.doc.DocColService;
import com.hanweb.jmp.cms.service.matters.picture.PictureColService;
import com.hanweb.jmp.cms.service.matters.video.VideoColService;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.LogConfig;
import com.hanweb.jmp.constant.StaticValues;
import com.hanweb.jmp.pack.backstage.service.ApplicationService;
import com.hanweb.jmp.pack.entity.App;
import com.hanweb.jmp.pack.entity.Client;
import com.hanweb.jmp.pack.service.AppService;
import com.hanweb.jmp.pack.service.ClientService;
import com.hanweb.jmp.sys.controller.parameter.ParameterFormBean;
import com.hanweb.jmp.sys.controller.sites.SiteFormBean;
import com.hanweb.jmp.sys.dao.sites.SiteDAO;
import com.hanweb.jmp.sys.entity.parameter.Parameter;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.entity.sites.SiteSplash;
import com.hanweb.jmp.sys.service.field.FieldService;
import com.hanweb.jmp.sys.service.log.InterfaceLogService;
import com.hanweb.jmp.sys.service.log.LogService;
import com.hanweb.jmp.sys.service.parameter.ParameterService;
import com.hanweb.jmp.util.CacheUtil;
import com.hanweb.jmp.util.HadoopUtil;
import com.hanweb.jmp.util.QRCodeUtil; 
import com.hanweb.support.controller.CurrentUser;

public class SiteService {

	/**
	 * logger
	 */
	private final Log logger = LogFactory.getLog(getClass());

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
	 * userService
	 */
	@Autowired
	private UserService userService;

	/**
	 * userDAO
	 */
	@Autowired
	private UserDAO userDAO;

	/**
	 * roleDAO
	 */
	@Autowired
	private RoleDAO roleDAO;

	/**
	 * roleRelationService
	 */
	@Autowired
	private RoleRelationService roleRelationService;

	/**
	 * logService
	 */
	@Autowired
	private LogService logService;

	/**
	 * colService
	 */
	@Autowired
	private ColService colService;

	/**
	 * roleRightService
	 */
	@Autowired
	private RoleRightService roleRightService;

	/**
	 * rightService
	 */
	@Autowired
	private RightService rightService;
	
	/**
	 * infoService
	 */
	@Autowired
	private InfoService infoService;
	
	/**
	 * infoService
	 */
	@Autowired
	private InfoCountService infoCountService;
	
	/**
	 * interfaceLogService
	 */
	@Autowired
	private InterfaceLogService interfaceLogService; 
	
	/**
	 * appService
	 */
	@Autowired
	private AppService appService;
	
	/**
	 * clientService
	 */
	@Autowired
	private ClientService clientService;
	
	/**
	 * applicationService
	 */
	@Autowired
	private ApplicationService applicationService;
	
	/**
	 * fieldService
	 */
	@Autowired
	private FieldService fieldService;
	
	/**
	 * lightInitService
	 */
	@Autowired
	private LightInitService lightInitService;
	
	/**
	 * PictureColService
	 */
	@Autowired
	private PictureColService pictureColService;
	
	/**
     * DocColService
     */
    @Autowired
    private DocColService docColService;
    
    /**
     * VideoColService
     */
    @Autowired
    private VideoColService videoColService;
	
	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;
	
	/**
	 * 后台新建站点
	 * @param siteBean siteBean
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             操作异常
	 * @throws ParseException 
	 */
	public boolean add(SiteFormBean siteBean) throws OperationException, ParseException {
		if (siteBean == null) {
			return false;
		}
		Site site = siteBean.getSite();
		User user = siteBean.getUser();
		boolean isSuccess = true;
		String sitePath = "";

		// 1、检查licence是否超过站点最大数
//		int maxNum = NumberUtil.getInt(LicenceCheck.getLicence(BaseInfo.getRealPath(),
//				"jmportal.licence", BaseInfo.getAppName()).getWebCount());
//		int realNum = NumberUtil.getInt(siteDAO.findSiteCount());
//		if (realNum >= maxNum) {
//			throw new OperationException("站点数目已达到最大，请联系系统管理员！");
//		}
		
		// 2、检查站点、登录名是否重名,需回滚
		if(user != null) {
			int usernum = userDAO.findIidByLoginName(user.getIid(), user.getLoginName());
			if (usernum > 0) {
				throw new OperationException("登录名已存在,请重新设置！");
			}
		}
		int sitenum = siteDAO.findNumByName(0, site.getName());
		if (sitenum > 0) {
			throw new OperationException("站点名已存在，请重新设置！");
		}

		// 3、站点表入库,需回滚
		site.setAppFrom(0);
		site.setCreateTime(new Date());
		site.setUuid(StringUtil.getUUIDString());
		Integer siteId = siteDAO.insert(site);
		
		// 生成站点对应的信息表
		infoService.addTable(infoService.getTableName(siteId));
		
		// 生成站点对应的信息阅读数表
		infoCountService.addTable(infoCountService.getTableName(siteId));
		
		// 生成站点对应的接口日志表
		interfaceLogService.addTable(interfaceLogService.getTableName(siteId));
		
		// 生成信息字段表
		fieldService.addFieldsBySiteId(siteId);
		
		// 插入素材初始化分类
        pictureColService.addFieldsBySiteId(siteId);
		docColService.addFieldsBySiteId(siteId);
		videoColService.addFieldsBySiteId(siteId);
		
		// 初始化默认应用
		applicationService.addDefaultAppsBySiteId(siteId);
		
		// 是否支持用户注册和注册用户默认状态
		if(site.getRegState() == null){
			site.setRegState(0);
		}
		site.setIsSupportReg(site.getIsSupportReg());
		
		// 初始化轻应用
		lightInitService.lightInit(siteId);
		
		// 4、新增站点详细表,需回滚
		Parameter parameter = new Parameter();
		parameter.setSiteId(siteId);
		parameterService.add(parameter);

		// 5、新增站点引导页表,需回滚
		SiteSplash siteSplash = new SiteSplash();
		siteSplash.setSiteId(siteId);
		siteSplash.setAndroidVersion(0);
		siteSplash.setIphoneVersion(0);
		siteSplash.setIpadVersion(0);
		siteSplashService.add(siteSplash);

		// 6、用户表入库,需回滚
		user.setSiteId(siteId);
		user.setOpenId(StringUtil.getUUIDString());
		int userId = userService.add(user);

		// 7、角色表入库,需回滚
		Role role = new Role();
		role.setName("站点管理员");
		role.setPinYin("WZGLY");
		role.setSpec("管理自己站点的用户");
		role.setSiteId(siteId);
		role.setType(1);
		Integer roleId = roleDAO.insert(role);

		// 8、角色用户对应表入库,需回滚
		roleRelationService.addUser(roleId, userId, siteId);

		// 9、角色权限对应表入库
		List<Right> rightList = rightService.findAllRights();
		List<Integer> list = new ArrayList<Integer>();
		for (Right right : rightList) {
			if (!StringUtil.equals("site", right.getModuleId())) {
				list.add(right.getIid());
			}
		}
		roleRightService.modifyRoleRight(roleId, list, siteId);

		// 10、站点表更新,需回滚
		String url = "web/site" + siteId;
		if (Configs.getConfigs().getJmpUrl().endsWith("/")) {
			url = Configs.getConfigs().getJmpUrl() + url;
		} else {
			url = Configs.getConfigs().getJmpUrl() + "/" + url;
		}
		site.setIid(siteId);
		site.setUrl(url);
		site.setUserId(userId);
		isSuccess = siteDAO.updateUrlAndUserId(site);
		if (!isSuccess) {
			throw new OperationException("");
		}

		// 11、写站点配置文件
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
		HadoopUtil.fileUpload(file, "web/site"+siteId +"/config.xml");
		// 初始化应用数据（后台打包）
		if(Configs.getConfigs().getCreateAppType() == 1 && siteId > 0) {
			this.initSiteData(siteId, site.getName(), userId);
		}
		
		// 12、初始化站点介绍页
		if(fileUtil.getImplClazz() == LocalFileUtil.class) {
			String relative = "/web/site" + siteId + "/";
			File oldFile = new File(BaseInfo.getRealPath() + "/resources/jmp/module/");
			File newFile = new File(BaseInfo.getRealPath() + relative);
			FileUtil.copyDirectory(oldFile, newFile);
		}

		// 13、保存站点id到缓存,需回滚
		site.setParameter(parameter);
		site.setSiteSplash(siteSplash);
		//加入站点缓存
		CacheUtil.setValue(StaticValues.CACHE_SITE, "" + siteId, site);

		// 14、写日志,不需回滚
		logService.add(LogConfig.modsite, LogConfig.opradd, site.getName());
		
		return isSuccess;
	}

	/**
	 * 后台修改站点
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
		
		// 1、检查站点是否重名
		int num = siteDAO.findNumByName(site.getIid(), site.getName());
		if (num > 0) {
			throw new OperationException("站点名已存在，请重新设置！");
		}

		// 2、站点表更新
		isSuccess = siteDAO.update(site);
		if (num > 0) {
			throw new OperationException("站点表更新失败！");
		}

		// 3、用户表更新
		isSuccess = userService.modify(user);
		if (num > 0) {
			throw new OperationException("用户表更新失败！");
		}

		// 4、站点缓存更新
		site = siteDAO.findByIid(site.getIid());
		Site siteCache = (Site) CacheUtil.getValue(StaticValues.CACHE_SITE, "" + site.getIid());
		if (siteCache != null) {
			site.setParameter(siteCache.getParameter());
			site.setSiteSplash(siteCache.getSiteSplash());
		}
		CacheUtil.setValue(StaticValues.CACHE_SITE, "" + site.getIid(), site);

		// 5、写日志
		logService.add(LogConfig.modsite, LogConfig.oprmodify, site.getName());

		return isSuccess;
	}
	
	/**
	 * 站点管理员后台设置站点
	 * @param parameterBean		站点实体
	 * @param fileLogo			站点logo
	 * @param fileIphone1		iPhone4配图
	 * @param fileIphone2		iPhone5配图
	 * @param fileIphone3		iPhone6配图
	 * @param fileIphone4		iPhone6p配图
	 * @param fileAndroid1		Android1配图
	 * @param fileIpad1			Ipad1配图
	 * @return
	 * @throws OperationException
	 */
	public boolean modify(ParameterFormBean parameterBean, MultipartFile fileLogo, 
			MultipartFile fileIphone1, MultipartFile fileIphone2, MultipartFile fileIphone3, 
			MultipartFile fileIphone4, MultipartFile fileAndroid1, 
			MultipartFile fileIpad1) throws OperationException {
		if (parameterBean == null) {
			return false;
		}
		int siteid = parameterBean.getIid();
		Site site = parameterBean;
		Parameter parameter = site.getParameter();
		SiteSplash siteSplash = site.getSiteSplash();

		// 1、处理上传文件
		String[][] fileNames = {
				{ "site_logo.png", "site_iphone1.jpg", "site_iphone2.jpg",
					"site_iphone3.jpg", "site_iphone4.jpg", "site_android1.jpg","site_ipad1.jpg",
					"site_ipad2.jpg", "site_ipad3.jpg" },
			    { "站点图标", "iphone首页", "iphone中页", "iphone尾页","iphone4首页", "iphone4中页", "iphone4尾页", 
					"android首页", "android中页", "android尾页", "ipad首页",
					"ipad中页", "ipad尾页" } }; 
		List<MultipartFile> files = new ArrayList<MultipartFile>();
		files.add(fileLogo);
		files.add(fileIphone1);
		files.add(fileIphone2);
		files.add(fileIphone3);
		files.add(fileIphone4);
		files.add(fileAndroid1);
		files.add(fileIpad1);
		String path = fileUtil.getAbsolutePath("");
		if (!path.endsWith("/")) {
			path += "/";
		}
		path += "web/site" + siteid + "/site/";
		if(!fileUtil.exists(path)){
			fileUtil.createDir(path);
		}
		if(CollectionUtils.isNotEmpty(files)){
			for (int i = 0, len = files.size(); i < len; i++) {
				if(files.get(i) == null || files.get(i).isEmpty()){
					continue;
				}
				MultipartFileInfo info = MultipartFileInfo.getInstance(files.get(i));
				long size = info.getSize();
				if (size == 0) {
					continue;
				}
				// 上传大小限制
				if((long) Configs.getConfigs().getPicFileSize()*1024*1024 < size){
					throw new OperationException(fileNames[1][i] + "图片大于"
							+Configs.getConfigs().getPicFileSize()+"M，请重新上传！"); 
				}
				// 上传类型限制 
				if(Configs.getConfigs().getPicFileType().indexOf(
						info.getFileType().toLowerCase()) == -1){
					throw new OperationException(fileNames[1][i] + "图片类型不正确，请重新上传！");
				}  
				// 由于文件目前属于暂存状态，我们需要把文件拷贝到我们需要的位置
				// 使用ControllerUtil.writeMultipartFileToFile(目标位置,源文件)来拷贝 
				fileUtil.writeMultipartFileToFile(path + fileNames[0][i], files.get(i));
			}
		}

		// 2、检查站点是否重名
		int num = siteDAO.findNumByName(site.getIid(), site.getName());
		if (num > 0) {
			throw new OperationException("站点名已存在,请重新设置！");
		}

		// 3、更新3个数据库
		parameter.setSiteId(siteid);
		siteSplash.setSiteId(siteid);
		SiteSplash s = siteSplashService.findBySiteId(siteid);
		siteSplash.setIphoneVersion(NumberUtil.getInt(s.getIphoneVersion()) + 1);
		siteSplash.setIpadVersion(NumberUtil.getInt(s.getIpadVersion()) + 1);
		siteSplash.setAndroidVersion(NumberUtil.getInt(s.getAndroidVersion()) + 1);
		siteSplash.setImguuid(UUID.randomUUID().toString().replace("-", ""));
		parameterService.modify(parameter);
		siteSplashService.modify(siteSplash);
		siteDAO.updateNameAndColor(site);

		// 4、站点缓存更新
		site = siteDAO.findByIid(siteid);
		site.setParameter(parameterService.findBySiteId(site.getIid()));
		site.setSiteSplash(siteSplashService.findBySiteId(site.getIid()));
		CacheUtil.setValue(StaticValues.CACHE_SITE, "" + site.getIid(), site);

		// 5、增加日志
		logService.add(LogConfig.modsite, LogConfig.oprmodify, site.getName());

		return true;
	}

	/**
	 * 站点删除
	 * @param id
	 *            站点id
	 * @return boolean true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             操作异常
	 */
	public boolean remove(int id) throws OperationException {
		boolean isSuccess = true;
		if (id == 0) {
			return false;
		}
		String name = this.findByIid(id).getName();

		// 1、判断该该站点下是否有栏目，需回滚
		int num = colService.findColNumBySiteId(id);
		if (num > 0) {
			throw new OperationException("该站点下有栏目，不可删除！");
		}

		// 2、删除表记录，需回滚
		isSuccess = siteDAO.deleteAll(id);
		if (!isSuccess) {
			throw new OperationException("删除站点相关记录失败！");
		}

		// 3、删除站点目录
		String filePath="web/site" + id+"/";
		fileUtil.deleteDirectory(fileUtil.getAbsolutePath(filePath)); 

		// 4、删除站点缓存
		CacheUtil.removeKey(StaticValues.CACHE_SITE, ""+id);
		if(! CacheUtil.getAllElement(StaticValues.CACHE_REGION).isEmpty()){
			CacheUtil.removeKeyStartsWith(StaticValues.CACHE_REGION, id+"");
		}

		// 5、增加操作日志
		logService.add(LogConfig.modsite, LogConfig.oprremove, name);
		
		// 6、删除信息表
		infoService.deleteTable(infoService.getTableName(id));
		
		// 7、删除信息阅读数表
		infoCountService.deleteTable(infoCountService.getTableName(id));
		
		// 8、删除接口日志表
		interfaceLogService.deleteTable(interfaceLogService.getTableName(id));

		return isSuccess;
	}

	/**
	 * 根据站点id查找站点实体
	 * @param iid
	 *            站点id
	 * @return Site 站点实体
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
	 * 根据站点id串查找站点实体
	 * @param iids iids 
	 * @return List<Site>
	 */
	public List<Site> findByIids(String iids) {
		List<Site> list = siteDAO.findByIids(iids);
		if (list != null && list.size() > 0) {
			for (Site site : list) {
				Parameter parameter = parameterService.findBySiteId(site.getIid());
				SiteSplash siteSplash = siteSplashService.findBySiteId(site.getIid());
				if (parameter == null) {
					parameter = new Parameter();
				}
				if (siteSplash == null) {
					siteSplash = new SiteSplash();
				}
				site.setParameter(parameter);
				site.setSiteSplash(siteSplash);
			}
		}
		return list;
	}

	/**
	 * 站点导出
	 * @param siteId
	 *            站点id
	 * @return 临时文件路径
	 */
	public String eexport(Integer siteId) {
		Site site = this.findByIid(siteId);
		Parameter parameter = site.getParameter();
		SiteSplash siteSplash = site.getSiteSplash();
		User user = userService.findByIid(site.getUserId());
		String exportPath = "";
		XmlDocument siteXml = new XmlDocument("UTF-8"); // utf-8
		try {
			// 1、写站点信息
			siteXml.createRoot("root");
			generateXml(site, siteXml, "root/site");
			generateXml(parameter, siteXml, "root/site");
			generateXml(siteSplash, siteXml, "root/site");
			generateXml(user, siteXml, "root/user");
			/* 写入文件 */
			String sitePath = BaseInfo.getRealPath() 
				+ "/web/site" + NumberUtil.getInt(siteId) + "/";
			String siteXmlfileName = "siteinfomation.xml";
			String filePath = sitePath + siteXmlfileName;
			File siteXmlFile = new File(filePath);
			siteXml.saveAs(siteXmlFile);

			// 2、写栏目文件
			colService.writeAllColXml(siteId);

			// 3、组织需要导出的文件
			File colXmlFile = new File(sitePath + "cols.xml");
			File colFiles = new File(sitePath + "col");
			File siteFiles = new File(sitePath + "site");
			File[] files = { siteXmlFile, siteFiles, colXmlFile, colFiles };

			// 4、压缩文件 现在是把site下的所有文件都进行压缩
			String exportName = StringUtil.getUUIDString() + ".zip";
			exportPath = Settings.getSettings().getFileTmp() + exportName;
			ZipUtil.zip(files, new File(exportPath));

			// 5、增加日志
			logService.add(LogConfig.modsite, LogConfig.oprexport, site.getName());
			
		} catch (Exception e) {
			logger.error("export error", e);
		}
		return exportPath;
	}

	/**
	 * 站点导入
	 * @param file
	 *            站点id
	 * @return String 提示信息
	 * @throws OperationException
	 *             操作异常
	 * @throws ParseException 
	 */
	public String importSite(File file) throws OperationException, ParseException {
		String result = "";
		if (file == null) {
			throw new OperationException("找不到导入文件！");
		}

		// 1、检查licence是否超过站点最大数
		int maxNum = LicenceCheck.getLicence(BaseInfo.getRealPath(),
				"jmportal.licence", BaseInfo.getAppName())
				.getWebCount();
		int realNum = siteDAO.findSiteCount();
		if (realNum >= maxNum) {
			throw new OperationException("站点数目已达到最大，请联系系统管理员！");
		}

		// 2、解压zip
		String temFilePath = StringUtil.getUUIDString();
		String outPath = BaseInfo.getRealPath() + "/tempfile/web_" + temFilePath; // 解压路径
		ZipUtil.unzip(file, new File(outPath));
		File outFile = new File(outPath);

		// 3、读取sitexml
		String sitePath = outPath + "/siteinfomation.xml";
		Site site = new Site();
		Parameter parameter = new Parameter();
		SiteSplash siteSplash = new SiteSplash();
		User user = new User();
		XmlDocument xml = new XmlDocument();
		xml.read(new File(sitePath));
		readSiteXml(site, xml);

		// 4、站点数据校验、插站点表
		result = validateSite(site);
		site.setIsSafeControl(1);
		Integer siteId = siteDAO.insert(site);
		
		// 5、生成站点对应的信息表
		infoService.addTable(infoService.getTableName(siteId));
		
		// 6、生成站点对应的信息阅读数表
		infoCountService.addTable(infoCountService.getTableName(siteId));
		
		// 7插入素材初始化分类
        pictureColService.addFieldsBySiteId(siteId);
        docColService.addFieldsBySiteId(siteId);
        videoColService.addFieldsBySiteId(siteId);
		
		// 8、生成站点对应的接口日志表
		interfaceLogService.addTable(interfaceLogService.getTableName(siteId));
		
		// 9、生成信息字段表
		fieldService.addFieldsBySiteId(siteId);
		
		// 10、初始化默认应用
		applicationService.addDefaultAppsBySiteId(siteId);
		
		// 11、是否支持用户注册和注册用户默认状态
		if(site.getRegState() == null){
			site.setRegState(0);
		}
		site.setIsSupportReg(site.getIsSupportReg());
		
		// 12、初始化轻应用
		lightInitService.lightInit(siteId);
		
		// 13、读取站点详细表，入库
		readParameterXml(parameter, xml, siteId);
		parameterService.add(parameter);

		// 14、读取站点splash表 ，入库
		readSiteSplashXml(siteSplash, xml, siteId);
		siteSplashService.add(siteSplash);

		// 15、读取用户表，数据校验、入库
		readUserXml(user, xml, siteId);
		validateUser(user);
		Integer userId = userService.add(user);

		// 16、角色表入库,需回滚
		Role role = new Role();
		role.setName("站点管理员");
		role.setPinYin("WZGLY");
		role.setIid(siteId);
		role.setType(1);
		Integer roleId = roleDAO.insert(role);

		// 17、角色用户对应表入库,需回滚
		roleRelationService.addUser(roleId, userId, siteId);

		// 18、角色权限对应表入库
		List<Right> rightList = rightService.findAllRights();
		List<Integer> list = new ArrayList<Integer>();
		for (Right right : rightList) {
			if (!StringUtil.equals("site", right.getModuleId())) {
				list.add(right.getIid());
			}
		}
		roleRightService.modifyRoleRight(roleId, list, siteId);

		// 19、站点表更新,需回滚
		String url = "web/site" + siteId;
		if (Configs.getConfigs().getJmpUrl().endsWith("/")) {
			url = Configs.getConfigs().getJmpUrl() + url;
		} else {
			url = Configs.getConfigs().getJmpUrl() + "/" + url;
		}
		site.setIid(siteId);
		site.setUrl(url);
		site.setUserId(userId);
		boolean isSuccess = siteDAO.updateUrlAndUserId(site);
		if (!isSuccess) {
			throw new OperationException("");
		}

		// 20、修改config配置文件
		String filePath = outPath + "/config.xml";
		File configFile = new File(filePath);
		XmlDocument configxml = new XmlDocument();
		configxml.createRoot("root");
		configxml.addNode("root", "siteid", "" + siteId);
		configxml.saveAs(configFile);

		// 21、copy文件
		String realSitePath = BaseInfo.getRealPath() + "/web/site" + siteId;
		File realFile = new File(realSitePath);
		isSuccess = FileUtil.copyDirectory(outFile, realFile);
		if(! isSuccess){
			throw new OperationException("站点拷贝文件失败。");
		}
		
		// 22、初始化应用数据（后台打包）
		this.initSiteData(siteId, site.getName(), userId);
		
		// 23、栏目导入
		String colPath = realSitePath + "/cols.xml";
		colService.importCols(colPath, siteId, 0, StringUtil.getUUIDString());

		// 24、保存站点id到缓存,需回滚
		site.setParameter(parameter);
		site.setSiteSplash(siteSplash);
		// 25、加入站点缓存
		CacheUtil.setValue(StaticValues.CACHE_SITE, "" + siteId, site);

		// 26、增加日志
		logService.add(LogConfig.modsite, LogConfig.oprimport, site.getName());

		return result;
	}

	/**
	 * 检查站点属性合法性
	 * @param site
	 *            Site
	 * @return String 提示信息
	 * @throws OperationException
	 *             操作异常
	 */
	private String validateSite(Site site) throws OperationException {
		String message = "";
		if ("".equals(site.getName())) {
			throw new OperationException("站点名称不能为空");
		}
		if ("".equals(site.getColor())) {
			throw new OperationException("站点颜色不能为空");
		}
		if (site.getIsSearch() == 1 && "".equals(site.getSearchUrl())) {
			throw new OperationException("全文检索服务器地址不能为空");
		}
		int num = siteDAO.findNumByName(0, site.getName());
		if (num > 0) {
			throw new OperationException("站点名已存在");
		}
		return message;

	}

	/**
	 * 检查站点属性合法性
	 * @param user
	 *            User
	 * @throws OperationException
	 *             操作异常
	 */
	private void validateUser(User user) throws OperationException {
		if ("".equals(user.getLoginName())) {
			throw new OperationException("站点管理员登录名不能为空");
		}
		User temUser = userDAO.findByLoginName(user.getLoginName());
		if (temUser != null && temUser.getIid() > 0) {
			throw new OperationException("站点管理员登录名已存在");
		}
		if ("".equals(user.getName())) {
			throw new OperationException("站点管理员呢称不能为空");
		}
		if ("".equals(user.getPwd())) {
			throw new OperationException("站点管理员密码不能为空");
		}
	}

	/**
	 * 写xml--导入时使用
	 * @param object
	 *            实体
	 * @param xml
	 *            xml
	 * @param xmlPath
	 *            属性的路径
	 */
	@SuppressWarnings("deprecation")
    private void generateXml(Object object, XmlDocument xml, String xmlPath) {
		try {
			Field[] field = object.getClass().getDeclaredFields();
			for (Field siteField : field) {
				String fieldname = siteField.getName();
				if ("serialVersionUID".equals(fieldname) || "parameter".equals(fieldname)
						|| "siteSplash".equals(fieldname) || "iid".equals(fieldname) 
						|| "siteId".equals(fieldname)) {
					continue;
				}
				fieldname = fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
				Method get = object.getClass().getMethod("get" + fieldname);
				if ("Pwd".equals(fieldname)) {// 密码加密
					xml.addNodeCDATA(xmlPath, fieldname.toLowerCase(), 
							Md5Util.md5encode(StringUtil.getString(get
							.invoke(object))));
				} else {
					xml.addNodeCDATA(xmlPath, fieldname.toLowerCase(), 
							StringUtil.getString(get.invoke(object)));
				}
			}
		} catch (Exception e) {
			logger.error("generateXml error", e);
		}
	}

	/**
	 * 读取站点详细信息 --站点导入时使用
	 * @param parameter
	 *            站点详细实体
	 * @param xml
	 *            xml
	 * @param siteId
	 *            站点id
	 */
	private void readParameterXml(Parameter parameter, XmlDocument xml, Integer siteId) {
		String xmlPath = "root/site";
		String sitePath = "site" + siteId;
		parameter.setSiteId(siteId);
		parameter.setSitePic(xml.getXmlNode(xmlPath + "/sitepic").getContent().replace("siteid", sitePath));
		parameter.setSpec(xml.getXmlNode(xmlPath + "/spec").getContent());
		parameter.setDomain(xml.getXmlNode(xmlPath + "/domain").getContent());
		parameter.setAndroidUrl(xml.getXmlNode(xmlPath + "/androidurl").getContent());
		parameter.setIphoneUrl(xml.getXmlNode(xmlPath + "/iphoneurl").getContent());
		parameter.setIpadUrl(xml.getXmlNode(xmlPath + "/ipadurl").getContent());
		parameter.setIsPush(NumberUtil.getInt(xml.getXmlNode(xmlPath + "/ispush").getContent()));
		parameter.setAndroidPushAppKey(xml.getXmlNode(xmlPath + "/androidpushappkey").getContent());
		parameter.setAndroidPushAppSecret(xml.getXmlNode(xmlPath + "/androidpushappsecret").getContent());
		parameter.setIphonePushAppKey(xml.getXmlNode(xmlPath + "/iphonepushappkey").getContent());
		parameter.setIphonePushAppSecret(xml.getXmlNode(xmlPath + "/ipadpushappsecret").getContent());
		parameter.setIpadPushAppKey(xml.getXmlNode(xmlPath + "/ipadpushappkey").getContent());
		parameter.setIpadPushAppSecret(xml.getXmlNode(xmlPath + "/ipadpushappsecret").getContent());
		parameter.setPushSaveNum(NumberUtil.getInt(xml.getXmlNode(xmlPath + "/pushsavenum").getContent()));
		parameter.setAppsIcon(xml.getXmlNode(xmlPath + "/appsicon").getContent());
		parameter.setAppDesc(xml.getXmlNode(xmlPath + "/appdesc").getContent());
		parameter.setAppType(NumberUtil.getInt(xml.getXmlNode(xmlPath + "/apptype").getContent()));
	}

	/**
	 * 读取站点引导页信息--站点导入时使用
	 * @param siteSplash
	 *            站点引导页实体
	 * @param xml
	 *            xml
	 * @param siteId
	 *            站点id
	 */
	private void readSiteSplashXml(SiteSplash siteSplash, XmlDocument xml, Integer siteId) {
		String xmlPath = "root/site";
		String sitePath = "site" + siteId;
		siteSplash.setSiteId(siteId);
		siteSplash.setFirstAndroidPic(xml.getXmlNode(
				xmlPath + "/firstandroidpic").getContent().replace("siteid",sitePath));
		siteSplash.setMiddleAndroidPic(xml.getXmlNode(
				xmlPath + "/middleandroidpic").getContent().replace("siteid",sitePath));
		siteSplash.setLastAndroidPic(xml.getXmlNode(
				xmlPath + "/lastandroidpic").getContent().replace("siteid",sitePath));
		siteSplash.setFirstIpadPic(xml.getXmlNode(
				xmlPath + "/firstipadpic").getContent().replace("siteid", sitePath));
		siteSplash.setMiddleIpadPic(xml.getXmlNode(
				xmlPath + "/middleipadpic").getContent().replace("siteid", sitePath));
		siteSplash.setLastIpadPic(xml.getXmlNode(
				xmlPath + "/lastipadpic").getContent().replace("siteid", sitePath));
		siteSplash.setFirstIphonePic(xml.getXmlNode(
				xmlPath + "/firstiphonepic").getContent().replace("siteid",sitePath));
		siteSplash.setMiddleIphonePic(xml.getXmlNode(
				xmlPath + "/middleiphonepic").getContent().replace("siteid",sitePath));
		siteSplash.setLastIphonePic(xml.getXmlNode(
				xmlPath + "/lastiphonepic").getContent().replace("siteid", sitePath));
		siteSplash.setFirstTitle(xml.getXmlNode(
				xmlPath + "/firsttitle").getContent().replace("siteid", sitePath));
		siteSplash.setMiddleTitle(xml.getXmlNode(
				xmlPath + "/middletitle").getContent().replace("siteid", sitePath));
		siteSplash.setLastTitle(xml.getXmlNode(
				xmlPath + "/lasttitle").getContent().replace("siteid", sitePath));
		siteSplash.setFirstUrl(xml.getXmlNode(
				xmlPath + "/firsturl").getContent().replace("siteid", sitePath));
		siteSplash.setMiddleUrl(xml.getXmlNode(
				xmlPath + "/middleurl").getContent().replace("siteid", sitePath));
		siteSplash.setLastUrl(xml.getXmlNode(
				xmlPath + "/lasturl").getContent().replace("siteid", sitePath));
	}

	/**
	 * 读站点xml
	 * @param site
	 *            站点实体
	 * * @param xml
	 *            xml 
	 */
	private void readSiteXml(Site site, XmlDocument xml){
		String xmlPath = "root/site";
		site.setName(xml.getXmlNode(xmlPath + "/name").getContent());
		site.setColor(xml.getXmlNode(xmlPath + "/color").getContent());
		site.setPushLevel(NumberUtil.getInt(xml.getXmlNode(xmlPath + "/pushlevel").getContent()));
		site.setTaskId(xml.getXmlNode(xmlPath + "/taskid").getContent());
		site.setTaskName(xml.getXmlNode(xmlPath + "/taskname").getContent());
		site.setUrl(xml.getXmlNode(xmlPath + "/url").getContent());
		site.setIsSearch(NumberUtil.getInt(xml.getXmlNode(xmlPath + "/issearch").getContent()));
		site.setSearchUrl(xml.getXmlNode(xmlPath + "/searchurl").getContent());
		site.setCreateTime(new Date());
		site.setAppFrom(NumberUtil.getInt(xml.getXmlNode(xmlPath + "/appfrom").getContent()));
		site.setUuid(StringUtil.getUUIDString());
	}

	/**
	 * 读用户xml
	 * @param user
	 *            用户实体
	 * @param xml
	 *            xml
	 * @param siteId
	 *            站点id
	 */
	private void readUserXml(User user, XmlDocument xml, Integer siteId) {
		String xmlPath = "root/user";
		user.setOpenId(xml.getXmlNode(xmlPath + "/uuid").getContent());
		user.setLoginName(xml.getXmlNode(xmlPath + "/loginname").getContent());
		user.setPwd(xml.getXmlNode(xmlPath + "/pwd").getContent());
		user.setName(xml.getXmlNode(xmlPath + "/name").getContent());
		user.setGroupId(NumberUtil.getInt(xml.getXmlNode(xmlPath + "/groupid").getContent()));
		user.setAge(NumberUtil.getInt(xml.getXmlNode(xmlPath + "/age").getContent()));
		user.setSex(NumberUtil.getInt(xml.getXmlNode(xmlPath + "/sex").getContent()));
		user.setEnable(NumberUtil.getInt(xml.getXmlNode(xmlPath + "/enable").getContent()));
		user.setUsertype(NumberUtil.getInt(xml.getXmlNode(xmlPath + "/usertype").getContent()));
		user.setPwdquestion(xml.getXmlNode(xmlPath + "/pwdquestion").getContent());
		user.setPwdanswer(xml.getXmlNode(xmlPath + "/pwdanswer").getContent());
		user.setCreatetime(new Date());
		user.setIp(xml.getXmlNode(xmlPath + "/ip").getContent());
		user.setPinYin(xml.getXmlNode(xmlPath + "/pinyin").getContent());
		user.setMobile(xml.getXmlNode(xmlPath + "/mobile").getContent());
		user.setPhone(xml.getXmlNode(xmlPath + "/phone").getContent());
		user.setHeadship(xml.getXmlNode(xmlPath + "/headship").getContent());
		//user.setFax(xml.getXmlNode(xmlPath + "/fax").getContent());
		user.setEmail(xml.getXmlNode(xmlPath + "/email").getContent());
		//user.setQq(xml.getXmlNode(xmlPath + "/qq").getContent());
		//user.setMsn(xml.getXmlNode(xmlPath + "/msn").getContent());
		user.setContact(xml.getXmlNode(xmlPath + "/contact").getContent());
		user.setAddress(xml.getXmlNode(xmlPath + "/address").getContent());
		user.setOrderid(NumberUtil.getInt(xml.getXmlNode(xmlPath + "/orderid").getContent()));
		user.setNickname(xml.getXmlNode(xmlPath + "/nickname").getContent());
		user.setIdcard(xml.getXmlNode(xmlPath + "/idcard").getContent());
		user.setVerifycode(xml.getXmlNode(xmlPath + "/verifycode").getContent());
		user.setSiteId(siteId);
		user.setIsappuser(NumberUtil.getInt(xml.getXmlNode(xmlPath + "/isappuser").getContent()));
		user.setAppstatus(NumberUtil.getInt(xml.getXmlNode(xmlPath + "/appstatus").getContent()));
		user.setIsappcreate(NumberUtil.getInt(xml.getXmlNode(xmlPath + "/isappcreate").getContent()));
		user.setIsappupload(NumberUtil.getInt(xml.getXmlNode(xmlPath + "/isappupload").getContent()));
		user.setGroupName(xml.getXmlNode(xmlPath + "/groupname").getContent());
		user.setCodeId(xml.getXmlNode(xmlPath + "/codeid").getContent());
		user.setParGroupName(xml.getXmlNode(xmlPath + "/pargroupname").getContent());
		user.setIsSysAdmin("true".equals(xml.getXmlNode(xmlPath + "/issysadmin").getContent()));
		user.setIsGroupAdmin("true".equals(xml.getXmlNode(xmlPath + "/isgroupadmin").getContent()));
	}

	/**
	 * 查找所有的站点
	 * 
	 * @return List<Site> 站点list
	 */
	public List<Site> findAll() {
		return siteDAO.findAll();
	}
	
	/**
	 * 查找所有支持jsearch的站点
	 * @return List<Site> 站点list
	 */
	public List<Site> findJsearchSite() {
		return siteDAO.findJsearchSite();
	}
	
	/**
	 * 获取除了id的其他站点信息，组织成树
	 * @param iid
	 *            本站点id
	 * @param siteIds
	 *            选中的站点ids
	 * @return String 树的字符串
	 */
	public String findSubscribeTree(Integer iid, String siteIds) {
		try {
			siteIds = "," + siteIds + ",";
			// 创建一个树实例
			Tree tree = Tree.getInstance();
			tree.addNode(TreeNode.getInstance(
					"root", null, "<font color=red><b>订阅</b></font>", null, true));
			List<Site> siteList = this.findAll();
			TreeNode treeNode;
			for (Site site : siteList) {
				if (!iid.equals(site.getIid())) {
					// 判断是否是当前节点
					treeNode = TreeNode.getInstance(
							"S" + site.getIid(), "root", "<b>" + site.getName() + "</b>")
							.setIsParent(true);
					if (siteIds.indexOf("," + site.getIid() + ",") > -1) {
						// 判断是否选中
						treeNode.setChecked(true);
					}
					tree.addNode(treeNode);
				}
			}
			return tree.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
 
	/**
	 * 修改是否订阅其他站点.
	 * @param isSubscribeOther isSubscribeOther
	 * @param iid iid
	 * @throws OperationException    设定参数 .
	*/
	public void modifySubscribeOther(Integer isSubscribeOther, Integer iid) throws OperationException{
		siteDAO.updateSubscribeOther(isSubscribeOther, iid);
	}

	/**
	 * 分析客户端类型
	 * @param header header
	 * @param clientType clientType
	 * @return Integer
	 */
	public Integer findClientType(String header, Integer clientType) {
		if (StringUtil.isNotEmpty(header)) {
			if (header.toLowerCase().indexOf("windows") != -1) {
				clientType = 1;
			}
			if (header.toLowerCase().indexOf("iphone") != -1) {
				clientType = 2;
			}
			if (header.toLowerCase().indexOf("android") != -1) {
				clientType = 3;
			}
			if (header.toLowerCase().indexOf("ipad") != -1) {
				clientType = 4;
			}
		}
		return clientType;
	}
	
	/**
	 * 获得jsearch url
	 * @param siteid siteid
	 * @return String
	 */
	public String getJsearchUrl(Integer siteid){
	  Site siteEn = new Site();
      //获取URL
      siteEn.setIid(siteid);
      siteEn = getOneSite(siteEn);
      if(siteEn == null){
    	  return "";
      }else{
    	  return siteEn.getSearchUrl();
      }
	}
	
	 /**
     * 取出一条站点信息.
     * @param entity
     *            站点实体
     * @return 站点实体
     */
    public Site getOneSite(Site entity) {
        if (entity == null) {
            return null;
        }
        return siteDAO.findByIid(entity.getIid());
    }
    
    /**
     * 取出一条站点信息.
     * @param id
     *            id
     * @return 站点实体
     */
    public Site getOneSite(Integer id) {
        if (NumberUtil.getInt(id) == 0) {
            return null;
        }
        return siteDAO.findByIid(id);
    }
    
    /**
     * 修改站点支持jsearch状态
     * @param state state
     * @param siteId siteId
     * @return boolean
     */
    public boolean updateSearchState(int state, int siteId) {
    	return siteDAO.updateSearchState(state, siteId);
    }
    
    /**
     * 查询app来源
     * @param iid iid
     * @return Integer
     */
    public Integer findAppFrom(Integer iid) {
    	return siteDAO.findAppFrom(iid);
    }
    
    /**
     * 查询推送标记位
     * @param iid iid
     * @return    设定参数 .
    */
    public int findPushFlagById(Integer iid){
    	return NumberUtil.getInt(siteDAO.findPushFlagById(iid));
    }
    
    /**
     * 修改推送标记位
     * @param iid iid
     * @return    设定参数 .
    */
    public boolean modifyPushFlag(Integer iid){
    	Site site = (Site) CacheUtil.getValue("" + iid, "site");
    	if(site != null){
    		site.setPushFlag(NumberUtil.getInt(site.getPushFlag()) + 1);
    	}
    	CacheUtil.removeKey(StaticValues.CACHE_SITE, StringUtil.getString(iid)); 
    	return siteDAO.updatePushFlag(iid);
    }
    
	/**
	 * 站点下频道增删改排序启用停用标记位+1
	 * @param iid iid
	 * @return boolean
	 */
    public boolean modifyChanFlag(Integer iid){
    	Site site = (Site) CacheUtil.getValue("" + iid, "site");
    	if(site != null){
    		site.setChanFlag(NumberUtil.getInt(site.getChanFlag()) + 1);
    	}
    	CacheUtil.removeKey(StaticValues.CACHE_SITE, StringUtil.getString(iid)); 
    	return siteDAO.updateChanFlag(iid);
    }
    
	/**
	 * 站点下栏目增删改排序启用停用标记位+1
	 * @param iid iid
	 * @return boolean
	 */
    public boolean modifyColFlag(Integer iid){
    	Site site = (Site) CacheUtil.getValue("" + iid, "site");
    	if(site != null){
    		site.setColFlag(NumberUtil.getInt(site.getColFlag()) + 1);
    	}
    	modifySubscribeColFlag(iid);
    	return siteDAO.updateColFlag(iid);
    }
    
    /**
	 * 站点下订阅栏目分类增删改排序标记位+1
	 * @param iid iid
	 * @return boolean
	 */
	public boolean modifySubscribeColSignFlag(Integer iid){
		Site site = (Site) CacheUtil.getValue("" + iid, "site");
    	if(site != null){
    		site.setBookColDimensionFlag(NumberUtil.getInt(site.getBookColDimensionFlag()) + 1);
    	}
    	CacheUtil.removeKey(StaticValues.CACHE_SITE, StringUtil.getString(iid)); 
    	return siteDAO.updateSubscribeColSignFlag(iid);
	}
	
	/**
	 * 站点下订阅栏目操作标记位+1
	 * @param iid iid
	 * @return boolean
	 */
	public boolean modifySubscribeColFlag(Integer iid){
		Site site = (Site) CacheUtil.getValue("" + iid, "site");
    	if(site != null){
    		site.setBookColFlag(NumberUtil.getInt(site.getBookColFlag()) + 1);
    	}
    	CacheUtil.removeKey(StaticValues.CACHE_SITE, StringUtil.getString(iid)); 
    	return siteDAO.updateSubscribeColFlag(iid);
	}
	
	/**
	 * 站点下卡片维度操作标记位+1
	 * @param iid iid
	 * @return boolean
	 */
	public boolean modifyCardSignFlag(Integer iid){
		Site site = (Site) CacheUtil.getValue("" + iid, "site");
    	if(site != null){
    		site.setCardDimensionFlag(NumberUtil.getInt(site.getCardDimensionFlag()) + 1);
    	}
    	CacheUtil.removeKey(StaticValues.CACHE_SITE, StringUtil.getString(iid)); 
    	return siteDAO.updateCardSignFlag(iid);
	}
	
	/**
	 * 通过是否支持离线下载查找站点实体
	 * @return    设定参数 .
	 */
	public List<Site> findOfflineSite(){
		return siteDAO.findOfflineSite();
	}
	
	
	/**
	 * 初始化站点数据
	 * @params siteId 站点ID
	 * @params name 应用名称（站点名称）
	 * @params userId 用户ID
 	 */
	public void initSiteData(int siteId, String name, int userId) throws OperationException {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer appId = 0;
		App app = new App(); 
		app.setSiteId(siteId);
		app.setName(name);
		app.setUserId(userId);
		app.setCreater(currentUser.getLoginName());
		app.setCreateTime(new Date());
		appId = appService.addApp(app); 
		app.setLogoPath("/web/" + "site"+siteId + "/app/" + appId + "/images/logo.png");
		appService.updateApp(app);
		//生成二维码
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz() != LocalFileUtil.class) {
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}
		if(StringUtil.isNotEmpty(jmpUrl)){
			String contentPath = jmpUrl + "/web/"+ "site"+siteId +"/app/" + appId + "/index.html";
	        String imgPath = "/web/"+ "site"+siteId + "/app/" + appId + "/qcode.png";
	        FileUtil.isDirExsit(new File(BaseInfo.getRealPath() +"/web/"+ "site"+siteId + "/app/" + appId), true);
	        int result = QRCodeUtil.createQRCode(contentPath, BaseInfo.getRealPath() + imgPath, null);	
	        HadoopUtil.fileUpload(new File(BaseInfo.getRealPath()+ imgPath), imgPath);
			if(result == 0){
				app.setIid(appId);
				app.setQrcodePath(imgPath);
				appService.updateApp(app);
			}  
		}
		//初始化版本管理数据
		Client client = new Client();
		client.setAppName(name);
		client.setVersion("1.0");
		client.setClientType(2);
		client.setCreateTime(new Date());
		client.setAppId(appId);
		client.setStatus(1);
		client.setSiteId(siteId);
		client.setIphoneUrl("/web/" + "site"+siteId + "/app/" + appId + "/iphone.ipa");
		client.setPlist("/web/" + "site"+siteId + "/app/" + appId + "/iphone.plist");
		clientService.addClient(client);
		client.setAndroidUrl("/web/" + "site"+siteId + "/app/" + appId + "/android.apk");
		client.setClientType(3);
		clientService.addClient(client);
		client.setIpadUrl("/web/" + "site"+siteId + "/app/" + appId + "/ipad.ipa"); 
		client.setPlist("/web/" + "site"+siteId + "/app/" + appId + "/ipad.plist");
		client.setClientType(4);
		clientService.addClient(client); 
	}
	
	/**
	 * 替换js的指定字符串
	 * @param filePath
	 * @param newStr
	 * @param oldStr
	 * @return
	 */
	public boolean rewriteJs(String filePath, String newStr, String oldStr){
		String fileStr = "";
		FileReader fr;
		boolean isSuccess = false;
		if(StringUtil.isEmpty(oldStr)){
			return false;
		}
		try {
			fr = new FileReader(filePath);
			int ch = 0;  
	        while((ch = fr.read()) != -1){
	        	fileStr+=(char)ch;  
	        }
		    fr.close();  
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}  
		if(StringUtil.isNotEmpty(fileStr)){
			fileStr = fileStr.replace(oldStr, newStr);
			FileWriter writer = null;
			try {
				writer = new FileWriter(filePath);
				writer.write(fileStr);
				writer.flush();
				writer.close();
				isSuccess = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {  
	            if (writer != null)  
	                try {  
	                	writer.close();  
	                } catch (IOException e) {  
	                    throw new RuntimeException("关闭失败！");  
	                }  
	        }  
		}
		return isSuccess;
	}
	
}