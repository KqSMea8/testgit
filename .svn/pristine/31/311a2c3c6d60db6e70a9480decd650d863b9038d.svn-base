package com.hanweb.jmp.pack.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.FreemarkerUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.MathUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.dao.RoleDAO;
import com.hanweb.complat.entity.Right;
import com.hanweb.complat.entity.Role;
import com.hanweb.complat.entity.User;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.service.RightService;
import com.hanweb.complat.service.RoleRelationService;
import com.hanweb.complat.service.RoleRightService;

import com.hanweb.jmp.util.CacheUtil;
import com.hanweb.jmp.util.HttpClientUtil;
import com.hanweb.jmp.apps.dao.broke.BrokeDAO;
import com.hanweb.jmp.apps.dao.broke.BrokeTypeDAO;
import com.hanweb.jmp.apps.entity.broke.Broke;
import com.hanweb.jmp.apps.entity.broke.BrokeType;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.StaticValues;
import com.hanweb.jmp.pack.dao.ApplicationDAO;
import com.hanweb.jmp.pack.dao.ClientDAO;
import com.hanweb.jmp.pack.entity.App;
import com.hanweb.jmp.pack.entity.AppUser;
import com.hanweb.jmp.pack.entity.Client;
import com.hanweb.jmp.pack.entity.ClientVersion;
import com.hanweb.jmp.pack.entity.CreateReturnEntity;
import com.hanweb.jmp.cms.controller.channels.ChannelFormBean;
import com.hanweb.jmp.cms.controller.cols.ColFormBean;
import com.hanweb.jmp.cms.controller.infos.info.InfoFormBean;
import com.hanweb.jmp.cms.entity.sign.Sign;
import com.hanweb.jmp.cms.entity.sign.SignRel;
import com.hanweb.jmp.sys.controller.sites.SiteFormBean;
import com.hanweb.jmp.sys.dao.sites.SiteDAO;
import com.hanweb.jmp.sys.dao.version.VersionDAO;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.entity.sites.SiteSplash;
import com.hanweb.jmp.sys.entity.version.Version;
import com.hanweb.jmp.sys.service.sites.SiteSplashService;

public class AppService {
	
	/**
	 * appDAO
	 */
	@Autowired
	private ApplicationDAO appDAO;  
	
	/**
	 * siteService
	 */
	@Autowired
	private SiteService siteService;
	
	/**
	 * colService
	 */
	@Autowired
	private ColService colService;
	
	/**
	 * userService
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * roleDAO
	 */
	@Autowired
	private RoleDAO roleDAO;
	
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
	 * roleRelationService
	 */
	@Autowired
	private RoleRelationService roleRelationService;
	
	/**
	 * channelService
	 */
	@Autowired
	private ChannelService channelService;
	
	/**
	 * infoService
	 */
	@Autowired
	private InfoService infoService;
	
	/**
	 * siteDAO
	 */
	@Autowired
	private SiteDAO siteDAO;
	
	/**
	 * brokeTypeDAO
	 */
	@Autowired
	private BrokeTypeDAO brokeTypeDAO;
	
	/**
	 * brokeDAO
	 */
	@Autowired
	private BrokeDAO brokeDAO;
	
	/**
	 * clientDAO
	 */
	@Autowired
	private ClientDAO clientDAO; 
	
	/**
	 * versionDAO
	 */
	@Autowired
	private VersionDAO versionDAO; 
	
	/**
	 * picService
	 */
	@Autowired
	private PicService picService;
	
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
	 * readService
	 */
	@Autowired
	private ReadService readService;
	
	/**
	 * numColService
	 */
	@Autowired
	private NumSenseColService numColService;
	
	/**
	 * siteSplashService
	 */
	@Autowired
	private SiteSplashService siteSplashService;
	
	/**
	 * domain
	 */
	String domain = Configs.getConfigs().getJmpUrl();
	
	/**
	 * 新增应用
	 * @param app app
	 * @return Integer
	 */
	public Integer addApp(App app){
		int appId = appDAO.insert(app);
		return appId;
	}
	
	/**
	 * 更新应用
	 * @param app app
	 * @return boolean
	 */
	public boolean updateApp(App app){
		boolean success = appDAO.update(app);
		return success;
	}
	
	/**
	 * addUser:(这里用一句话描述这个方法的作用).
	 *
	 * @param user user
	 * @return    设定参数 .
	*/
	public boolean addUser(User user){
		user.setOpenId(StringUtil.getUUIDString());
		try {
			int userId = userService.add(user);
			Site site = siteService.findSiteByIid(user.getSiteId());
			// 7、角色表入库,需回滚
			Role role = new Role();
			role.setName("网站管理员");
			role.setPinYin("WZGLY");
			role.setSpec("管理自己网站的用户");
			role.setIid(user.getSiteId());
			role.setType(1);
			Integer roleId = roleDAO.insert(role);
	
			// 8、角色用户对应表入库,需回滚
			roleRelationService.addUser(roleId, userId, user.getSiteId());
	
			// 9、角色权限对应表入库
			List<Right> rightList = rightService.findAllRights();
			List<Integer> list = new ArrayList<Integer>();
			for (Right right : rightList) {
				if (!StringUtil.equals("site", right.getModuleId())) {
					list.add(right.getIid());
				}
			}
			roleRightService.modifyRoleRight(roleId, list, user.getSiteId());
			site.setUserId(userId);
			site.setIid(site.getIid());
			site.setUrl(site.getUrl());
			siteDAO.updateUrlAndUserId(site);
			if(userId>0){
				CacheUtil.removeKey("site", "" + site.getIid()); 
				return true;
			} else {
				return false;
			}
		} catch (OperationException e) {
			return false;
		}
	}
	
	/**
	 * 新增频道
	 * @param channel 频道form
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException 操作异常
	 */
	public boolean addChann(ChannelFormBean channel) throws OperationException{
		boolean isSuccess;
		int type = NumberUtil.getInt(channel.getType());
		File chanDir = new File(BaseInfo.getRealPath() + "/web/site" + channel.getSiteId() + "/channel");
		if(!chanDir.exists()){
			isSuccess = chanDir.mkdir();
		}
		switch (type) {
		case 1://单栏目类  需要增加多条频道记录
			isSuccess = channelService.addChannels(channel);
			break;
		default://普通类
			isSuccess = channelService.addChannel(channel);
			break;
		}
		return isSuccess;
	}
	
	/**
	 * 初始化网站及首页
	 * @param app app
	 * @return Integer
	 */
	public Integer addSiteAndInfo(App app){
		Integer siteId = 0;
		SiteFormBean siteForm = new SiteFormBean();
		Site site = new Site();
		site.setName(app.getName());
		site.setColor("#008fd5");
		site.setIsSearch(0);
		site.setIsOfflineZip(0);
		site.setIsSupportReg(0);
		siteForm.setSite(site);
		try {
			siteId = siteService.addSite(siteForm);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		//初始化banner栏目
		Integer bannerId = 0;
		ColFormBean col = new ColFormBean();
		col.setType(2);
		col.setName("banner 栏目");
		col.setAuditType(2);
		col.setColListType(1);
		col.setIssearch(0);
		col.setCommonType(2);
		col.setInfoListType(1);
		col.setInfoContentType(1);
		col.setSiteId(siteId);
		try {
			bannerId = colService.addCol(col); 
		} catch (OperationException e) {
			e.printStackTrace();
		}
		Integer firstPageId = 0;
		int col2Id = 0;
		int col3Id = 0;
		ColFormBean col1 = new ColFormBean();
		//首页栏目
		col1.setType(2);
		col1.setName("首页");
		col1.setAuditType(2);
		col1.setInfoListType(4);
		col1.setBannerId(bannerId);
		col1.setColListType(1);
		col1.setIssearch(0);
		col1.setCommonType(2);
		col1.setInfoContentType(1);
		col1.setSiteId(siteId);
		col1.setBannerName("banner 栏目");
		//栏目一
		ColFormBean col2 = new ColFormBean();
		col2.setType(2);
		col2.setName("栏目一");
		col2.setAuditType(2);
		col2.setInfoListType(1);
		col2.setColListType(1);
		col2.setIssearch(0);
		col2.setCommonType(1);
		col2.setInfoContentType(1);
		col2.setSiteId(siteId);
		//栏目二
		ColFormBean col3 = new ColFormBean();
		col3.setType(2);
		col3.setName("栏目二");
		col3.setAuditType(2);
		col3.setInfoListType(5);
		col3.setColListType(1);
		col3.setCommonType(1);
		col3.setInfoContentType(1);
		col3.setIssearch(0);
		col3.setSiteId(siteId);
		try {
			firstPageId = colService.addCol(col1);
			col2Id = colService.addCol(col2);
			col3Id = colService.addCol(col3);
			
		} catch (OperationException e) {
			e.printStackTrace();
		}
		InfoFormBean info = new InfoFormBean();
		info.setColId(bannerId);
		info.setTitle("大汉科技-微门户");
		info.setCreateTime(new Date());
		info.setSynTime(new Date());
		info.setStatus(1);
		info.setSiteId(siteId);
		File bannerInfoContent = new File(BaseInfo.getRealPath() 
				               + "/resources/app/module/bannerInfoContent.txt");
		info.setContent(FileUtil.readFileToString(bannerInfoContent, "utf-8"));
		info.setInfoListType(1);
		info.setInfoContentType(1);
		info.setFirstPicPath("/resources/app/images/init/first.png");
		try {
			infoService.addInfo(info);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		InfoFormBean info1 = new InfoFormBean();
		info1.setColId(firstPageId);
		info1.setTitle("大汉网络");
		info1.setCreateTime(new Date());
		info1.setSynTime(new Date());
		info1.setSiteId(siteId);
		info1.setStatus(1);
		info1.setSiteId(siteId);
		File firstInfoContent = new File(BaseInfo.getRealPath() 
				              + "/resources/app/module/firstInfoContent.txt"); 
		String content=FileUtil.readFileToString(firstInfoContent, "utf-8");
		info1.setContent(content);
		info1.setInfoListType(4);
		info1.setInfoContentType(1);
		info1.setFirstPicPath("/resources/app/images/init/hanweb.png");
		
		InfoFormBean info11 = new InfoFormBean();
		info11.setColId(firstPageId);
		info11.setTitle("云端移动APP自助生成平台");
		info11.setCreateTime(new Date());
		info11.setSynTime(new Date());
		info11.setSiteId(siteId);
		info11.setStatus(1);
		info11.setSiteId(siteId);
		File firstInfoContent1 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent1.txt"); 
		String content1=FileUtil.readFileToString(firstInfoContent1, "utf-8");
		info11.setContent(content1);
		info11.setInfoListType(4);
		info11.setInfoContentType(1);
		info11.setFirstPicPath("/resources/app/images/init/2.png");
		//栏目一下信息
		InfoFormBean info2 = new InfoFormBean();
		info2.setColId(col2Id);
		info2.setTitle("网上办事大厅APP解决方案");
		info2.setCreateTime(new Date());
		info2.setSynTime(new Date());
		info2.setSiteId(siteId);
		info2.setStatus(1);
		info2.setSiteId(siteId);
		File firstInfoContent2 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent2.txt"); 
		String content2 = FileUtil.readFileToString(firstInfoContent2, "utf-8");
		info2.setContent(content2);
		info2.setInfoListType(2);
		info2.setInfoContentType(1);
		info2.setFirstPicPath("/resources/app/images/init/4.png");
		
		InfoFormBean info21 = new InfoFormBean();
		info21.setColId(col2Id);
		info21.setTitle("信息公开移动APP解决方案");
		info21.setCreateTime(new Date());
		info21.setSynTime(new Date());
		info21.setSiteId(siteId);
		info21.setStatus(1);
		info21.setSiteId(siteId);
		File firstInfoContent21 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent21.txt"); 
		String content21 = FileUtil.readFileToString(firstInfoContent21, "utf-8");
		info21.setContent(content21);
		info21.setInfoListType(2);
		info21.setInfoContentType(1);
		info21.setFirstPicPath("/resources/app/images/init/1.png");
		//栏目二下信息
		InfoFormBean info3 = new InfoFormBean();
		info3.setColId(col3Id);
		info3.setTitle("门户站群平台解决方案");
		info3.setCreateTime(new Date());
		info3.setSynTime(new Date());
		info3.setSiteId(siteId);
		info3.setStatus(1);
		info3.setSiteId(siteId);
		File firstInfoContent3 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent3.txt"); 
		String content3 = FileUtil.readFileToString(firstInfoContent3, "utf-8");
		info3.setContent(content3);
		info3.setInfoListType(7);
		info3.setInfoContentType(1);
		info3.setFirstPicPath("/resources/app/images/init/9.png");
		
		InfoFormBean info31 = new InfoFormBean();
		info31.setColId(col3Id);
		info31.setTitle("互动服务平台解决方案");
		info31.setCreateTime(new Date());
		info31.setSynTime(new Date());
		info31.setSiteId(siteId);
		info31.setStatus(1);
		info31.setSiteId(siteId);
		File firstInfoContent31 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent31.txt"); 
		String content31 = FileUtil.readFileToString(firstInfoContent31, "utf-8");
		info31.setContent(content31);
		info31.setInfoListType(7);
		info31.setInfoContentType(1);
		info31.setFirstPicPath("/resources/app/images/init/5.png");
		try {
			infoService.addInfo(info1);
			infoService.addInfo(info2);
			infoService.addInfo(info21);
			infoService.addInfo(info3);
			infoService.addInfo(info31);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		ChannelFormBean chan = new ChannelFormBean();
		chan.setColIds(StringUtil.getString(firstPageId));
		chan.setType(7);
		chan.setName("首页");
		chan.setColNames("首页");
		chan.setCreateTime(new Date());
		chan.setSiteId(siteId);
		chan.setState(1);
		chan.setFirstPic("/resources/app/images/col/first.png");
		chan.setColIds("" + firstPageId +"," + col2Id + "," + col3Id);
		try {
			this.addChann(chan);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		return siteId;
	} 
	
	/**
	 * 初始化天气预报组件
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public boolean addWeather(Integer siteId){
		ColFormBean col = new ColFormBean();
		col.setType(3);
		col.setName("天气预报");
		col.setActUrl("http://www.pertool.com/jmportalwb/");
		col.setAuditType(2);
		col.setColListType(1);
		col.setCommonType(1);
		col.setHdType(5);
		col.setIssearch(0);
		col.setInfoContentType(1);
		col.setInfoListType(4);
		col.setSiteId(NumberUtil.getInt(siteId));
		Integer colId = 0;
		try {
			colId = colService.addCol(col);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		ChannelFormBean chan = new ChannelFormBean();
		chan.setColIds(StringUtil.getString(colId));
		chan.setType(1);
		chan.setName("天气预报");
		chan.setColNames("天气预报");
		chan.setSiteId(siteId);
		chan.setState(1);
		chan.setCreateTime(new Date());
		chan.setFirstPic("/resources/app/images/channel/2.png");
		boolean success = false;
		try {
			success = this.addChann(chan);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	 * 初始微信卡片式组件
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public boolean addCard(Integer siteId){ 
		ColFormBean col = new ColFormBean();
		col.setType(2);
		col.setName("微信卡片式"); 
		col.setAuditType(2);
		col.setColListType(4);
		col.setCommonType(3); 
		col.setIssearch(0);
		col.setInfoContentType(1);
		col.setInfoListType(4);
		col.setSiteId(NumberUtil.getInt(siteId));
		int colId = 0;
		try {
			colId = colService.addCol(col); 
		} catch (OperationException e) {
			e.printStackTrace();
		}
		
		//新建卡片分类
		Sign signEn=new Sign();
		signEn.setCardType(0);
		signEn.setColId(colId);
		signEn.setCreateTime(new Date());
		signEn.setDname("卡片一");
		signEn.setMid(1);
		signEn.setMname("卡片维度");
		signEn.setOrderId(-1);
		signEn.setShowType(0);
		signEn.setSiteId(siteId);
		int cardid=signService.addSign(signEn); 
		signEn=new Sign();
		signEn.setCardType(0);
		signEn.setColId(colId);
		signEn.setCreateTime(new Date());
		signEn.setDname("卡片二");
		signEn.setMid(1);
		signEn.setMname("卡片维度");
		signEn.setOrderId(-2);
		signEn.setShowType(0);
		signEn.setSiteId(siteId);
		int cardid2=signService.addSign(signEn); 
		
		//新增卡片式信息
		int infoid1=0;
		int infoid2=0;
		int infoid3=0;
		int infoid4=0; 
		int infoid5=0;
		int infoid6=0;
		int infoid7=0;
		int infoid8=0;
		
		InfoFormBean info1 = new InfoFormBean();
		info1.setColId(colId);
		info1.setTitle("大汉网络");
		info1.setCreateTime(new Date());
		info1.setSynTime(new Date());
		info1.setSiteId(siteId);
		info1.setStatus(1);
		info1.setSiteId(siteId);
		File firstInfoContent = new File(BaseInfo.getRealPath() 
				+ "/resources/app/module/firstInfoContent.txt"); 
		String content=FileUtil.readFileToString(firstInfoContent, "utf-8");
		info1.setContent(content);
		info1.setInfoListType(4);
		info1.setInfoContentType(1);
		info1.setFirstPicPath("/resources/app/images/init/hanweb.png");
		
		  
		InfoFormBean info2 = new InfoFormBean();
		info2.setColId(colId);
		info2.setTitle("网上办事大厅APP解决方案");
		info2.setCreateTime(new Date());
		info2.setSynTime(new Date());
		info2.setSiteId(siteId);
		info2.setStatus(1);
		info2.setSiteId(siteId);
		File firstInfoContent2 = new File(
				BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent2.txt"); 
		String content2=FileUtil.readFileToString(firstInfoContent2, "utf-8");
		info2.setContent(content2);
		info2.setInfoListType(2);
		info2.setInfoContentType(1);
		info2.setFirstPicPath("/resources/app/images/init/4.png");
		
		InfoFormBean info3 = new InfoFormBean();
		info3.setColId(colId);
		info3.setTitle("信息公开移动APP解决方案");
		info3.setCreateTime(new Date());
		info3.setSynTime(new Date());
		info3.setSiteId(siteId);
		info3.setStatus(1);
		info3.setSiteId(siteId);
		File firstInfoContent21 = new File(
				BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent21.txt"); 
		String content21=FileUtil.readFileToString(firstInfoContent21, "utf-8");
		info3.setContent(content21);
		info3.setInfoListType(2);
		info3.setInfoContentType(1);
		info3.setFirstPicPath("/resources/app/images/init/1.png");
		//栏目二下信息
		InfoFormBean info4 = new InfoFormBean();
		info4.setColId(colId);
		info4.setTitle("门户站群平台解决方案");
		info4.setCreateTime(new Date());
		info4.setSynTime(new Date());
		info4.setSiteId(siteId);
		info4.setStatus(1);
		info4.setSiteId(siteId);
		File firstInfoContent3 = new File(
				BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent3.txt"); 
		String content3=FileUtil.readFileToString(firstInfoContent3, "utf-8");
		info4.setContent(content3);
		info4.setInfoListType(7);
		info4.setInfoContentType(1);
		info4.setFirstPicPath("/resources/app/images/init/9.png"); 
		
		
		InfoFormBean info15 = new InfoFormBean();
		info15.setColId(colId);
		info15.setTitle("云端移动APP自助生成平台");
		info15.setCreateTime(new Date());
		info15.setSynTime(new Date());
		info15.setSiteId(siteId);
		info15.setStatus(1);
		info15.setSiteId(siteId);
		File firstInfoContent1 = new File(
				BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent1.txt"); 
		String content1=FileUtil.readFileToString(firstInfoContent1, "utf-8");
		info15.setContent(content1);
		info15.setInfoListType(4);
		info15.setInfoContentType(1);
		info15.setFirstPicPath("/resources/app/images/init/2.png");
		
		InfoFormBean info16 = new InfoFormBean();
		info16.setColId(colId);
		info16.setTitle("网信通消息推送综合平台");
		info16.setCreateTime(new Date());
		info16.setSynTime(new Date());
		info16.setSiteId(siteId);
		info16.setStatus(1);
		info16.setSiteId(siteId);
		File firstInfoContent6 = new File(
				BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent4.txt"); 
		String content4=FileUtil.readFileToString(firstInfoContent6, "utf-8");
		info16.setContent(content4);
		info16.setInfoListType(4);
		info16.setInfoContentType(1);
		info16.setFirstPicPath("/resources/app/images/init/7.png");
		
		InfoFormBean info17 = new InfoFormBean();
		info17.setColId(colId);
		info17.setTitle("移动会议平台");
		info17.setCreateTime(new Date());
		info17.setSynTime(new Date());
		info17.setSiteId(siteId);
		info17.setStatus(1);
		info17.setSiteId(siteId);
		File firstInfoContent7 = new File(
				BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent5.txt"); 
		String content7=FileUtil.readFileToString(firstInfoContent7, "utf-8");
		info17.setContent(content7);
		info17.setInfoListType(4);
		info17.setInfoContentType(1);
		info17.setFirstPicPath("/resources/app/images/init/3.png");
		
		InfoFormBean info18 = new InfoFormBean();
		info18.setColId(colId);
		info18.setTitle("微信公众管理服务平台");
		info18.setCreateTime(new Date());
		info18.setSynTime(new Date());
		info18.setSiteId(siteId);
		info18.setStatus(1);
		info18.setSiteId(siteId);
		File firstInfoContent8 = new File(
				BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent6.txt"); 
		String content8=FileUtil.readFileToString(firstInfoContent8, "utf-8");
		info18.setContent(content8);
		info18.setInfoListType(4);
		info18.setInfoContentType(1);
		info18.setFirstPicPath("/resources/app/images/init/8.png");
		
		try {
			infoid1=infoService.addInfo(info1); 
			infoid2=infoService.addInfo(info2);
			infoid3=infoService.addInfo(info3);
			infoid4=infoService.addInfo(info4); 
			infoid5=infoService.addInfo(info15); 
			infoid6=infoService.addInfo(info16); 
			infoid7=infoService.addInfo(info17); 
			infoid8=infoService.addInfo(info18); 
		} catch (OperationException e) {
			e.printStackTrace();
		}
		
		//新建维度信息关联关系
		SignRel signEn1 = new SignRel();
		signEn1.setAttrid(infoid1);
		signEn1.setDimensionid(cardid);
		signEn1.setModuleid(1);
		signEn1.setOrderid(-1);
		signEn1.setSiteId(siteId);
		signRelService.addRel(signEn1);
		
		signEn1 = new SignRel();
		signEn1.setAttrid(infoid2);
		signEn1.setDimensionid(cardid);
		signEn1.setModuleid(1);
		signEn1.setOrderid(-2);
		signEn1.setSiteId(siteId);
		signRelService.addRel(signEn1);
		
		signEn1 = new SignRel();
		signEn1.setAttrid(infoid3);
		signEn1.setDimensionid(cardid);
		signEn1.setModuleid(1);
		signEn1.setOrderid(-3);
		signEn1.setSiteId(siteId);
		signRelService.addRel(signEn1);
		
		signEn1 = new SignRel();
		signEn1.setAttrid(infoid4);
		signEn1.setDimensionid(cardid);
		signEn1.setModuleid(1);
		signEn1.setOrderid(-4);
		signEn1.setSiteId(siteId);
		signRelService.addRel(signEn1);
		
		signEn1 = new SignRel();
		signEn1.setAttrid(infoid5);
		signEn1.setDimensionid(cardid2);
		signEn1.setModuleid(1);
		signEn1.setOrderid(-5);
		signEn1.setSiteId(siteId);
		signRelService.addRel(signEn1);
		
		signEn1 = new SignRel();
		signEn1.setAttrid(infoid6);
		signEn1.setDimensionid(cardid2);
		signEn1.setModuleid(1);
		signEn1.setOrderid(-6);
		signEn1.setSiteId(siteId);
		signRelService.addRel(signEn1);
		
		signEn1 = new SignRel();
		signEn1.setAttrid(infoid7);
		signEn1.setDimensionid(cardid2);
		signEn1.setModuleid(1);
		signEn1.setOrderid(-7);
		signEn1.setSiteId(siteId);
		signRelService.addRel(signEn1);
		
		signEn1 = new SignRel();
		signEn1.setAttrid(infoid8);
		signEn1.setDimensionid(cardid2);
		signEn1.setModuleid(1);
		signEn1.setOrderid(-8);
		signEn1.setSiteId(siteId);
		signRelService.addRel(signEn1);
		
		ChannelFormBean chan = new ChannelFormBean();
		chan.setColIds(StringUtil.getString(colId));
		chan.setType(1);
		chan.setName("微信卡片式");
		chan.setColNames("微信卡片式");
		chan.setCreateTime(new Date());
		chan.setSiteId(siteId);
		chan.setState(1);
		chan.setFirstPic("/resources/app/images/channel/2.png");
		boolean success = false;
		try {
			success = this.addChann(chan); 
		} catch (OperationException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	 * 初始化左侧一张图栏目
	 * @param siteid siteid
	 * @return Integer
	 */
	public boolean addLeftIo(int siteid){  
		//初始化banner栏目
		Integer colId = 0;
		ColFormBean col = new ColFormBean();
		col.setType(2);
		col.setName("左侧一张图");
		col.setAuditType(2);
		col.setColListType(1);
		col.setIssearch(0);
		col.setCommonType(1);
		col.setInfoListType(4);
		col.setInfoContentType(1);
		col.setSiteId(siteid);
		try {
			colId = colService.addCol(col); 
		} catch (OperationException e) {
			e.printStackTrace();
		}
		 
		InfoFormBean info = new InfoFormBean();
		info.setColId(colId);
		info.setTitle("大汉科技-微门户");
		info.setCreateTime(new Date());
		info.setSynTime(new Date());
		info.setStatus(1);
		info.setSiteId(siteid);
		File bannerInfoContent = new File(BaseInfo.getRealPath() + "/resources/app/module/bannerInfoContent.txt");
		info.setContent(FileUtil.readFileToString(bannerInfoContent, "utf-8"));
		info.setInfoListType(4);
		info.setInfoContentType(1);
		info.setFirstPicPath("/resources/app/images/init/first.png");
		try {
			infoService.addInfo(info);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		ChannelFormBean chan = new ChannelFormBean();
		chan.setColIds(StringUtil.getString(colId));
		chan.setType(1);
		chan.setName("左侧一张图");
		chan.setColNames("左侧一张图");
		chan.setCreateTime(new Date());
		chan.setSiteId(siteid);
		chan.setState(1);
		chan.setFirstPic("/resources/app/images/col/first.png"); 
		boolean bl=false;
		try {
			bl=this.addChann(chan);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		return bl;
	} 
	
	/**
	 * 初始化右侧一张图栏目
	 * @param siteid siteid
	 * @return Integer
	 */
	public boolean addRightIo(int siteid){  
		//初始化banner栏目
		Integer colId = 0;
		ColFormBean col = new ColFormBean();
		col.setType(2);
		col.setName("右侧一张图");
		col.setAuditType(2);
		col.setColListType(1);
		col.setIssearch(0);
		col.setCommonType(1);
		col.setInfoListType(5);
		col.setInfoContentType(1);
		col.setSiteId(siteid);
		try {
			colId = colService.addCol(col); 
		} catch (OperationException e) {
			e.printStackTrace();
		}
		InfoFormBean info = new InfoFormBean();
		info.setColId(colId);
		info.setTitle("大汉科技-微门户");
		info.setCreateTime(new Date());
		info.setSynTime(new Date());
		info.setStatus(1);
		info.setSiteId(siteid);
		File bannerInfoContent = new File(BaseInfo.getRealPath() + "/resources/app/module/bannerInfoContent.txt");
		info.setContent(FileUtil.readFileToString(bannerInfoContent, "utf-8"));
		info.setInfoListType(5);
		info.setInfoContentType(1);
		info.setFirstPicPath("/resources/app/images/init/first.png");
		try {
			infoService.addInfo(info);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		ChannelFormBean chan = new ChannelFormBean();
		chan.setColIds(StringUtil.getString(colId));
		chan.setType(1);
		chan.setName("右侧一张图");
		chan.setColNames("右侧一张图");
		chan.setCreateTime(new Date());
		chan.setSiteId(siteid);
		chan.setState(1);
		chan.setFirstPic("/resources/app/images/col/first.png"); 
		boolean bl=false;
		try {
			bl=this.addChann(chan);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		return bl;
	} 
	 
	/**
	 * 初始化图片新闻栏目
	 * @param siteid
	 * @return Integer
	 */
	public boolean addPicInfo(int siteid){  
		//初始化banner栏目
		Integer colId = 0;
		ColFormBean col = new ColFormBean();
		col.setType(2);
		col.setName("图片新闻");
		col.setAuditType(2);
		col.setColListType(4);
		col.setIssearch(0);
		col.setCommonType(6);
		col.setInfoListType(4);
		col.setInfoContentType(1);
		col.setSiteId(siteid);
		try {
			colId = colService.addCol(col); 
		} catch (OperationException e) {
			e.printStackTrace();
		}
		 
		InfoFormBean info1 = new InfoFormBean();
		info1.setColId(colId);
		info1.setTitle("云端移动APP自助生成平台");
		info1.setCreateTime(new Date());
		info1.setSynTime(new Date());
		info1.setSiteId(siteid);
		info1.setStatus(1); 
		File firstInfoContent1 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent1.txt"); 
		String content1=FileUtil.readFileToString(firstInfoContent1, "utf-8");
		info1.setContent(content1);
		info1.setInfoListType(4);
		info1.setInfoContentType(1);
		info1.setFirstPicPath("/resources/app/images/init/2.png");
		
		//栏目一下信息
		InfoFormBean info2 = new InfoFormBean();
		info2.setColId(colId);
		info2.setTitle("网上办事大厅APP解决方案");
		info2.setCreateTime(new Date());
		info2.setSynTime(new Date());
		info2.setSiteId(siteid);
		info2.setStatus(1); 
		File firstInfoContent2 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent2.txt"); 
		String content2=FileUtil.readFileToString(firstInfoContent2, "utf-8");
		info2.setContent(content2);
		info2.setInfoListType(2);
		info2.setInfoContentType(1);
		info2.setFirstPicPath("/resources/app/images/init/4.png");
		
		InfoFormBean info3 = new InfoFormBean();
		info3.setColId(colId);
		info3.setTitle("信息公开移动APP解决方案");
		info3.setCreateTime(new Date());
		info3.setSynTime(new Date()); 
		info3.setStatus(1);
		info3.setSiteId(siteid);
		File firstInfoContent21 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent21.txt"); 
		String content21=FileUtil.readFileToString(firstInfoContent21, "utf-8");
		info3.setContent(content21);
		info3.setInfoListType(2);
		info3.setInfoContentType(1);
		info3.setFirstPicPath("/resources/app/images/init/1.png");
		
		//栏目二下信息
		InfoFormBean info4 = new InfoFormBean();
		info4.setColId(colId);
		info4.setTitle("门户站群平台解决方案");
		info4.setCreateTime(new Date());
		info4.setSynTime(new Date());
		info4.setSiteId(siteid);
		info4.setStatus(1); 
		File firstInfoContent3 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent3.txt"); 
		String content3=FileUtil.readFileToString(firstInfoContent3, "utf-8");
		info4.setContent(content3);
		info4.setInfoListType(7);
		info4.setInfoContentType(1);
		info4.setFirstPicPath("/resources/app/images/init/9.png");
		try { 
			infoService.addInfo(info1); 
			infoService.addInfo(info2); 
			infoService.addInfo(info3); 
			infoService.addInfo(info4);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		ChannelFormBean chan = new ChannelFormBean();
		chan.setColIds(StringUtil.getString(colId));
		chan.setType(1);
		chan.setName("图片新闻");
		chan.setColNames("图片新闻");
		chan.setCreateTime(new Date());
		chan.setSiteId(siteid);
		chan.setState(1);
		chan.setFirstPic("/resources/app/images/col/first.png"); 
		boolean bl=false;
		try {
			bl=this.addChann(chan);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		return bl;
	} 
	
	/**
	 * 初始化瀑布流栏目
	 * @param siteid
	 * @return Integer
	 */
	public boolean addFlowInfo(int siteId){   
		int colId = 0;
		ColFormBean col = new ColFormBean();
		col.setType(2);
		col.setName("瀑布流");
		col.setAuditType(2);
		col.setColListType(4);
		col.setIssearch(0);
		col.setCommonType(5);
		col.setInfoListType(4);
		col.setInfoContentType(1);
		col.setSiteId(siteId);
		try {
			colId = colService.addCol(col); 
		} catch (OperationException e) {
			e.printStackTrace();
		}
		InfoFormBean info1 = new InfoFormBean();
		info1.setColId(colId);
		info1.setTitle("云端移动APP自助生成平台");
		info1.setCreateTime(new Date());
		info1.setSynTime(new Date());
		info1.setSiteId(siteId);
		info1.setStatus(1);
		info1.setSiteId(siteId);
		File firstInfoContent1 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent1.txt"); 
		String content1=FileUtil.readFileToString(firstInfoContent1, "utf-8");
		info1.setContent(content1);
		info1.setInfoListType(4);
		info1.setInfoContentType(1);
		info1.setFirstPicPath("/resources/app/images/init/2.png");
		//栏目一下信息
		InfoFormBean info2 = new InfoFormBean();
		info2.setColId(colId);
		info2.setTitle("网上办事大厅APP解决方案");
		info2.setCreateTime(new Date());
		info2.setSynTime(new Date());
		info2.setSiteId(siteId);
		info2.setStatus(1);
		info2.setSiteId(siteId);
		File firstInfoContent2 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent2.txt"); 
		String content2=FileUtil.readFileToString(firstInfoContent2, "utf-8");
		info2.setContent(content2);
		info2.setInfoListType(2);
		info2.setInfoContentType(1);
		info2.setFirstPicPath("/resources/app/images/init/4.png");
		
		InfoFormBean info3 = new InfoFormBean();
		info3.setColId(colId);
		info3.setTitle("信息公开移动APP解决方案");
		info3.setCreateTime(new Date());
		info3.setSynTime(new Date());
		info3.setSiteId(siteId);
		info3.setStatus(1);
		info3.setSiteId(siteId);
		File firstInfoContent21 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent21.txt"); 
		String content21=FileUtil.readFileToString(firstInfoContent21, "utf-8");
		info3.setContent(content21);
		info3.setInfoListType(2);
		info3.setInfoContentType(1);
		info3.setFirstPicPath("/resources/app/images/init/1.png");
		
		//栏目二下信息
		InfoFormBean info4 = new InfoFormBean();
		info4.setColId(colId);
		info4.setTitle("门户站群平台解决方案");
		info4.setCreateTime(new Date());
		info4.setSynTime(new Date());
		info4.setSiteId(siteId);
		info4.setStatus(1);
		info4.setSiteId(siteId);
		File firstInfoContent3 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent3.txt"); 
		String content3=FileUtil.readFileToString(firstInfoContent3, "utf-8");
		info4.setContent(content3);
		info4.setInfoListType(7);
		info4.setInfoContentType(1);
		info4.setFirstPicPath("/resources/app/images/init/9.png");
		
		//栏目二下信息
		InfoFormBean info5 = new InfoFormBean();
		info5.setColId(colId);
		info5.setTitle("本周六，邀你荧光夜跑+抽取iphone6+摇滚音乐+烟花秀");
		info5.setCreateTime(new Date());
		info5.setSynTime(new Date());
		info5.setSiteId(siteId);
		info5.setStatus(1);
		info5.setSiteId(siteId);
		File firstInfoContent4 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent7.txt"); 
		String content4=FileUtil.readFileToString(firstInfoContent4, "utf-8");
		info5.setContent(content4);
		info5.setInfoListType(7);
		info5.setInfoContentType(1);
		info5.setFirstPicPath("/resources/app/images/init/10.png");
		try { 
			infoService.addInfo(info1); 
			infoService.addInfo(info2); 
			infoService.addInfo(info3); 
			infoService.addInfo(info4);
			infoService.addInfo(info5);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		 
		ChannelFormBean chan = new ChannelFormBean();
		chan.setColIds(StringUtil.getString(colId));
		chan.setType(1);
		chan.setName("瀑布流");
		chan.setColNames("瀑布流");
		chan.setCreateTime(new Date());
		chan.setSiteId(siteId);
		chan.setState(1);
		chan.setFirstPic("/resources/app/images/col/first.png"); 
		boolean bl=false;
		try {
			bl=this.addChann(chan);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		return bl;
	} 
	
	/**
	 * 初始化场景式栏目
	 * @param siteid
	 * @return Integer
	 */
	public boolean addSceneInfo(int siteId){   
		int colId = 0;
		ColFormBean col = new ColFormBean();
		col.setType(1);
		col.setName("场景式");
		col.setAuditType(2);
		col.setColListType(1);
		col.setIssearch(0); 
		col.setInfoListType(4);
		col.setInfoContentType(1);
		col.setSiteId(siteId);
		try {
			colId = colService.addCol(col); 
		} catch (OperationException e) {
			e.printStackTrace();
		}
		int colId1 = 0;
		col = new ColFormBean();
		col.setType(2);
		col.setName("产品概览");
		col.setAuditType(2);
		col.setColListType(4);
		col.setIssearch(0);
		col.setCommonType(1);
		col.setInfoListType(4);
		col.setInfoContentType(1);
		col.setSiteId(siteId);
		col.setPid(colId);
		try {
			colId1= colService.addCol(col); 
		} catch (OperationException e) {
			e.printStackTrace();
		}
		int colId2 = 0;
		col = new ColFormBean();
		col.setType(2);
		col.setName("产品要闻");
		col.setAuditType(2);
		col.setColListType(4);
		col.setIssearch(0);
		col.setCommonType(1);
		col.setInfoListType(4);
		col.setInfoContentType(1);
		col.setSiteId(siteId);
		col.setPid(colId);
		try {
			colId2 = colService.addCol(col); 
		} catch (OperationException e) {
			e.printStackTrace();
		}
		InfoFormBean info1 = new InfoFormBean();
		info1.setColId(colId1);
		info1.setTitle("云端移动APP自助生成平台");
		info1.setCreateTime(new Date());
		info1.setSynTime(new Date());
		info1.setSiteId(siteId);
		info1.setStatus(1);
		info1.setSiteId(siteId);
		File firstInfoContent1 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent1.txt"); 
		String content1=FileUtil.readFileToString(firstInfoContent1, "utf-8");
		info1.setContent(content1);
		info1.setInfoListType(4);
		info1.setInfoContentType(1);
		info1.setFirstPicPath("/resources/app/images/init/2.png");
		
		//栏目一下信息
		InfoFormBean info2 = new InfoFormBean();
		info2.setColId(colId1);
		info2.setTitle("网上办事大厅APP解决方案");
		info2.setCreateTime(new Date());
		info2.setSynTime(new Date());
		info2.setSiteId(siteId);
		info2.setStatus(1);
		info2.setSiteId(siteId);
		File firstInfoContent2 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent2.txt"); 
		String content2=FileUtil.readFileToString(firstInfoContent2, "utf-8");
		info2.setContent(content2);
		info2.setInfoListType(2);
		info2.setInfoContentType(1);
		info2.setFirstPicPath("/resources/app/images/init/4.png");
		
		InfoFormBean info3 = new InfoFormBean();
		info3.setColId(colId2);
		info3.setTitle("信息公开移动APP解决方案");
		info3.setCreateTime(new Date());
		info3.setSynTime(new Date());
		info3.setSiteId(siteId);
		info3.setStatus(1);
		info3.setSiteId(siteId);
		File firstInfoContent21 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent21.txt"); 
		String content21=FileUtil.readFileToString(firstInfoContent21, "utf-8");
		info3.setContent(content21);
		info3.setInfoListType(2);
		info3.setInfoContentType(1);
		info3.setFirstPicPath("/resources/app/images/init/1.png");
		
		//栏目二下信息
		InfoFormBean info4 = new InfoFormBean();
		info4.setColId(colId2);
		info4.setTitle("门户站群平台解决方案");
		info4.setCreateTime(new Date());
		info4.setSynTime(new Date());
		info4.setSiteId(siteId);
		info4.setStatus(1);
		info4.setSiteId(siteId);
		File firstInfoContent3 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent3.txt"); 
		String content3=FileUtil.readFileToString(firstInfoContent3, "utf-8");
		info4.setContent(content3);
		info4.setInfoListType(7);
		info4.setInfoContentType(1);
		info4.setFirstPicPath("/resources/app/images/init/9.png");
		try {
			infoService.addInfo(info1);
			infoService.addInfo(info2);
			infoService.addInfo(info3);
			infoService.addInfo(info4);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		ChannelFormBean chan = new ChannelFormBean();
		chan.setColIds(StringUtil.getString(colId));
		chan.setType(1);
		chan.setName("感知中国");
		chan.setColNames("感知中国");
		chan.setCreateTime(new Date());
		chan.setSiteId(siteId);
		chan.setState(1);
		chan.setFirstPic("/resources/app/images/col/first.png"); 
		boolean bl=false;
		try {
			bl=this.addChann(chan);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		return bl;
	} 
	
	/**
	 * 初始标题时间来源组件
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public boolean addTitlemix(Integer siteId){
		ColFormBean col = new ColFormBean();
		col.setType(2);
		col.setName("标题时间来源"); 
		col.setAuditType(2);
		col.setColListType(2);
		col.setCommonType(1); 
		col.setIssearch(0);
		col.setInfoContentType(1);
		col.setInfoListType(2);
		col.setSiteId(NumberUtil.getInt(siteId));
		int colId = 0;
		try {
			colId = colService.addCol(col);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		 
		//新增信息 
		int infolisttype=2; 
		InfoFormBean info1 = new InfoFormBean();
		info1.setColId(colId);
		info1.setTitle("大汉网络");
		info1.setCreateTime(new Date());
		info1.setSynTime(new Date());
		info1.setSiteId(siteId);
		info1.setStatus(1);
		info1.setSiteId(siteId);
		File firstInfoContent = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent.txt"); 
		String content=FileUtil.readFileToString(firstInfoContent, "utf-8");
		info1.setContent(content);
		info1.setInfoListType(infolisttype);
		info1.setInfoContentType(1);
		info1.setFirstPicPath("/resources/app/images/init/hanweb.png");
		
		InfoFormBean info2 = new InfoFormBean();
		info2.setColId(colId);
		info2.setTitle("网上办事大厅APP解决方案");
		info2.setCreateTime(new Date());
		info2.setSynTime(new Date());
		info2.setSiteId(siteId);
		info2.setStatus(1);
		info2.setSiteId(siteId);
		File firstInfoContent2 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent2.txt"); 
		String content2=FileUtil.readFileToString(firstInfoContent2, "utf-8");
		info2.setContent(content2);
		info2.setInfoListType(infolisttype);
		info2.setInfoContentType(1);
		info2.setFirstPicPath("/resources/app/images/init/4.png");
		
		InfoFormBean info3 = new InfoFormBean();
		info3.setColId(colId);
		info3.setTitle("信息公开移动APP解决方案");
		info3.setCreateTime(new Date());
		info3.setSynTime(new Date());
		info3.setSiteId(siteId);
		info3.setStatus(1);
		info3.setSiteId(siteId);
		File firstInfoContent21 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent21.txt"); 
		String content21=FileUtil.readFileToString(firstInfoContent21, "utf-8");
		info3.setContent(content21);
		info3.setInfoListType(infolisttype);
		info3.setInfoContentType(1);
		info3.setFirstPicPath("/resources/app/images/init/1.png");
		
		//栏目二下信息
		InfoFormBean info4 = new InfoFormBean();
		info4.setColId(colId);
		info4.setTitle("门户站群平台解决方案");
		info4.setCreateTime(new Date());
		info4.setSynTime(new Date());
		info4.setSiteId(siteId);
		info4.setStatus(1);
		info4.setSiteId(siteId);
		File firstInfoContent3 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent3.txt"); 
		String content3=FileUtil.readFileToString(firstInfoContent3, "utf-8");
		info4.setContent(content3);
		info4.setInfoListType(infolisttype);
		info4.setInfoContentType(1);
		info4.setFirstPicPath("/resources/app/images/init/9.png"); 
		try {
			infoService.addInfo(info1);
			infoService.addInfo(info2);
			infoService.addInfo(info3);
			infoService.addInfo(info4); 
		} catch (OperationException e) {
			e.printStackTrace();
		}
		ChannelFormBean chan = new ChannelFormBean();
		chan.setColIds(StringUtil.getString(colId));
		chan.setType(1);
		chan.setName("标题时间来源");
		chan.setColNames("标题时间来源");
		chan.setCreateTime(new Date());
		chan.setSiteId(siteId);
		chan.setState(1);
		chan.setFirstPic("/resources/app/images/channel/2.png");
		boolean success = false;
		try {
			success = this.addChann(chan);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	 * 初始标题时间摘要组件
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public boolean addTitlemix1(Integer siteId){
		int infolisttype=3;
		ColFormBean col = new ColFormBean();
		col.setType(2);
		col.setName("标题时间摘要"); 
		col.setAuditType(2);
		col.setColListType(infolisttype);
		col.setCommonType(1); 
		col.setIssearch(0); 
		col.setInfoContentType(1);
		col.setInfoListType(infolisttype);
		col.setSiteId(NumberUtil.getInt(siteId));
		int colId = 0;
		try {
			colId = colService.addCol(col);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		 
		//新增信息 
		InfoFormBean info1 = new InfoFormBean();
		info1.setColId(colId);
		info1.setTitle("大汉网络");
		info1.setCreateTime(new Date());
		info1.setSynTime(new Date());
		info1.setSiteId(siteId);
		info1.setStatus(1);
		info1.setSiteId(siteId);
		File firstInfoContent = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent.txt"); 
		String content=FileUtil.readFileToString(firstInfoContent, "utf-8");
		info1.setContent(content);
		info1.setInfoListType(infolisttype);
		info1.setInfoContentType(1);
		info1.setFirstPicPath("/resources/app/images/init/hanweb.png");
		
		InfoFormBean info2 = new InfoFormBean();
		info2.setColId(colId);
		info2.setTitle("网上办事大厅APP解决方案");
		info2.setCreateTime(new Date());
		info2.setSynTime(new Date());
		info2.setSiteId(siteId);
		info2.setStatus(1);
		info2.setSiteId(siteId);
		File firstInfoContent2 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent2.txt"); 
		String content2=FileUtil.readFileToString(firstInfoContent2, "utf-8");
		info2.setContent(content2);
		info2.setInfoListType(infolisttype);
		info2.setInfoContentType(1);
		info2.setFirstPicPath("/resources/app/images/init/4.png");
		
		InfoFormBean info3 = new InfoFormBean();
		info3.setColId(colId);
		info3.setTitle("信息公开移动APP解决方案");
		info3.setCreateTime(new Date());
		info3.setSynTime(new Date());
		info3.setSiteId(siteId);
		info3.setStatus(1);
		info3.setSiteId(siteId);
		File firstInfoContent21 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent21.txt"); 
		String content21=FileUtil.readFileToString(firstInfoContent21, "utf-8");
		info3.setContent(content21);
		info3.setInfoListType(infolisttype);
		info3.setInfoContentType(1);
		info3.setFirstPicPath("/resources/app/images/init/1.png");
		
		//栏目二下信息
		InfoFormBean info4 = new InfoFormBean();
		info4.setColId(colId);
		info4.setTitle("门户站群平台解决方案");
		info4.setCreateTime(new Date());
		info4.setSynTime(new Date());
		info4.setSiteId(siteId);
		info4.setStatus(1);
		info4.setSiteId(siteId);
		File firstInfoContent3 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent3.txt"); 
		String content3=FileUtil.readFileToString(firstInfoContent3, "utf-8");
		info4.setContent(content3);
		info4.setInfoListType(infolisttype);
		info4.setInfoContentType(1);
		info4.setFirstPicPath("/resources/app/images/init/9.png"); 
		try {
			infoService.addInfo(info1);
			infoService.addInfo(info2);
			infoService.addInfo(info3);
			infoService.addInfo(info4); 
		} catch (OperationException e) {
			e.printStackTrace();
		} 
		ChannelFormBean chan = new ChannelFormBean();
		chan.setColIds(StringUtil.getString(colId));
		chan.setType(1);
		chan.setName("标题时间摘要");
		chan.setColNames("标题时间摘要");
		chan.setSiteId(siteId);
		chan.setCreateTime(new Date());
		chan.setState(1);
		chan.setFirstPic("/resources/app/images/channel/2.png");
		boolean success = false;
		try {
			success = this.addChann(chan);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	 * 初始标题左侧图评论数组件
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public boolean addTitlemix2(Integer siteId){
		int infolisttype=10;
		ColFormBean col = new ColFormBean();
		col.setType(2);
		col.setName("标题左侧图评论数"); 
		col.setAuditType(2);
		col.setColListType(infolisttype);
		col.setCommonType(1); 
		col.setIssearch(0); 
		col.setInfoContentType(1);
		col.setInfoListType(infolisttype);
		col.setSiteId(NumberUtil.getInt(siteId));
		col.setIsComment(1);
		int colId = 0;
		try {
			colId = colService.addCol(col);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		 
		//新增信息 
		InfoFormBean info1 = new InfoFormBean();
		info1.setColId(colId);
		info1.setTitle("大汉网络");
		info1.setCreateTime(new Date());
		info1.setSynTime(new Date());
		info1.setSiteId(siteId);
		info1.setStatus(1);
		info1.setSiteId(siteId);
		File firstInfoContent = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent.txt"); 
		String content=FileUtil.readFileToString(firstInfoContent, "utf-8");
		info1.setContent(content);
		info1.setInfoListType(infolisttype);
		info1.setInfoContentType(1);
		info1.setFirstPicPath("/resources/app/images/init/hanweb.png");
		
		InfoFormBean info2 = new InfoFormBean();
		info2.setColId(colId);
		info2.setTitle("网上办事大厅APP解决方案");
		info2.setCreateTime(new Date());
		info2.setSynTime(new Date());
		info2.setSiteId(siteId);
		info2.setStatus(1);
		info2.setSiteId(siteId);
		File firstInfoContent2 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent2.txt"); 
		String content2=FileUtil.readFileToString(firstInfoContent2, "utf-8");
		info2.setContent(content2);
		info2.setInfoListType(infolisttype);
		info2.setInfoContentType(1);
		info2.setFirstPicPath("/resources/app/images/init/4.png");
		
		InfoFormBean info3 = new InfoFormBean();
		info3.setColId(colId);
		info3.setTitle("信息公开移动APP解决方案");
		info3.setCreateTime(new Date());
		info3.setSynTime(new Date());
		info3.setSiteId(siteId);
		info3.setStatus(1);
		info3.setSiteId(siteId);
		File firstInfoContent21 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent21.txt"); 
		String content21=FileUtil.readFileToString(firstInfoContent21, "utf-8");
		info3.setContent(content21);
		info3.setInfoListType(infolisttype);
		info3.setInfoContentType(1);
		info3.setFirstPicPath("/resources/app/images/init/1.png");
		//栏目二下信息
		InfoFormBean info4 = new InfoFormBean();
		info4.setColId(colId);
		info4.setTitle("门户站群平台解决方案");
		info4.setCreateTime(new Date());
		info4.setSynTime(new Date());
		info4.setSiteId(siteId);
		info4.setStatus(1);
		info4.setSiteId(siteId);
		File firstInfoContent3 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent3.txt"); 
		String content3=FileUtil.readFileToString(firstInfoContent3, "utf-8");
		info4.setContent(content3);
		info4.setInfoListType(infolisttype);
		info4.setInfoContentType(1);
		info4.setFirstPicPath("/resources/app/images/init/9.png"); 
		try {
			infoService.addInfo(info1);
			infoService.addInfo(info2);
			infoService.addInfo(info3);
			infoService.addInfo(info4); 
		} catch (OperationException e) {
			e.printStackTrace();
		} 
		ChannelFormBean chan = new ChannelFormBean();
		chan.setColIds(StringUtil.getString(colId));
		chan.setType(1);
		chan.setName("标题左侧图评论数");
		chan.setColNames("标题左侧图评论数");
		chan.setSiteId(siteId);
		chan.setCreateTime(new Date());
		chan.setState(1);
		chan.setFirstPic("/resources/app/images/channel/2.png");
		boolean success = false;
		try {
			success = this.addChann(chan);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	 * 初始化智能公交组件
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public boolean addBus(Integer siteId){
		ColFormBean col = new ColFormBean();
		col.setType(3);
		col.setName("智能公交");
		col.setAuditType(2);
		col.setColListType(1);
		col.setCommonType(1);
		col.setHdType(8);
		col.setInfoContentType(1);
		col.setInfoListType(1);
		col.setSiteId(NumberUtil.getInt(siteId));
		col.setIssearch(0);
		Integer colId = 0;
		try {
			colId = colService.addCol(col);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		ChannelFormBean chan = new ChannelFormBean();
		chan.setColIds(StringUtil.getString(colId));
		chan.setType(1);
		chan.setName("智能公交");
		chan.setColNames("智能公交");
		chan.setSiteId(siteId);
		chan.setState(1);
		chan.setCreateTime(new Date());
		chan.setFirstPic("/resources/app/images/channel/3.png"); 
		boolean success = false;
		try {
			success = this.addChann(chan);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	 * 初始化报料组件
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public boolean addBaoLiao(Integer siteId){
		ColFormBean col = new ColFormBean();
		col.setType(3);
		col.setName("报料");
		col.setAuditType(2);
		col.setColListType(1);
		col.setCommonType(1);
		col.setHdType(6);
		col.setInfoContentType(1);
		col.setInfoListType(1);
		col.setSiteId(NumberUtil.getInt(siteId));
		col.setIssearch(0);
		Integer colId = 0;
		try {
			colId = colService.addCol(col);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		ChannelFormBean chan = new ChannelFormBean();
		chan.setColIds(StringUtil.getString(colId));
		chan.setType(1);
		chan.setName("报料");
		chan.setColNames("报料");
		chan.setSiteId(siteId);
		chan.setCreateTime(new Date());
		chan.setState(1);
		chan.setFirstPic("/resources/app/images/channel/3.png");
		BrokeType broketype = new BrokeType();
		broketype.setName("大家的报料");
		broketype.setSiteId(siteId);
		broketype.setOrderId(1);
		broketype.setCreateTime(new Date());
		broketype.setAuditType(1);
		int broketypeId = brokeTypeDAO.insert(broketype);
		Broke broke = new Broke();
		broke.setSiteId(siteId);
		broke.setClassId(broketypeId);
		broke.setCreateTime(new Date());
		broke.setContent("大家好，这里是大汉网络");
		broke.setLoginId("大汉网络");
		broke.setUuid("大汉网络");
		broke.setTitle("大汉网络");
		broke.setIsAudit(1);
		broke.setIsOpen(1);
		brokeDAO.insert(broke);
		boolean success = false;
		try {
			success = this.addChann(chan);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		return success;
	}
	 
	/**
	 * 初始化阅读组件
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public boolean addRead(Integer siteId){
		ColFormBean col = new ColFormBean();
		col.setType(3);
		col.setName("阅读");
		col.setAuditType(2);
		col.setColListType(1);
		col.setCommonType(1);
		col.setHdType(10);
		col.setInfoContentType(1);
		col.setInfoListType(1);
		col.setSiteId(NumberUtil.getInt(siteId));
		col.setIssearch(0);
		Integer colId = 0;
		try {
			colId = colService.addCol(col);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		ChannelFormBean chan = new ChannelFormBean();
		chan.setColIds(StringUtil.getString(colId));
		chan.setType(1);
		chan.setName("阅读");
		chan.setColNames("阅读");
		chan.setSiteId(siteId);
		chan.setCreateTime(new Date());
		chan.setState(1);
		chan.setFirstPic("/resources/app/images/channel/3.png");
		boolean success = false;
		try {
			success = this.addChann(chan);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		success=readService.addEbook(siteId);
		return success;
	}
	
	/**
	 * 初始化阅读组件
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
//	public boolean addShop(Integer siteId){
//		ColFormBean col = new ColFormBean();
//		col.setType(3);
//		col.setName("打折商城");
//		col.setAuditType(2);
//		col.setColListType(1);
//		col.setCommonType(1);
//		col.setHdType(9);
//		col.setInfoContentType(1);
//		col.setInfoListType(1);
//		col.setSiteId(NumberUtil.getInt(siteId));
//		col.setIssearch(0);
//		Integer colId = 0;
//		try {
//			colId = colService.addCol(col);
//		} catch (OperationException e) {
//			e.printStackTrace();
//		}
//		ChannelFormBean chan = new ChannelFormBean();
//		chan.setColIds(StringUtil.getString(colId));
//		chan.setType(1);
//		chan.setName("打折商城");
//		chan.setColNames("打折商城");
//		chan.setSiteId(siteId);
//		chan.setCreateTime(new Date());
//		chan.setState(1);
//		chan.setFirstPic("/resources/app/images/channel/3.png");
//		 
//		boolean success = false;
//		try {
//			success = this.addChann(chan);
//		} catch (OperationException e) {
//			e.printStackTrace();
//		}
//		success = ecoomService.addShop(siteId);
//		return success;
//	}
	
	/**
	 * 初始化通讯录组件
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public boolean addPhone(Integer siteId){
		ColFormBean col = new ColFormBean();
		col.setType(3);
		col.setName("通讯录");
		col.setAuditType(2);
		col.setColListType(1);
		col.setCommonType(1);
		col.setHdType(11);
		col.setInfoContentType(1);
		col.setInfoListType(1);
		col.setSiteId(NumberUtil.getInt(siteId));
		col.setIssearch(0);
		Integer colId = 0;
		try {
			colId = colService.addCol(col);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		ChannelFormBean chan = new ChannelFormBean();
		chan.setColIds(StringUtil.getString(colId));
		chan.setType(1);
		chan.setName("通讯录");
		chan.setColNames("通讯录");
		chan.setSiteId(siteId);
		chan.setCreateTime(new Date());
		chan.setState(1);
		chan.setFirstPic("/resources/app/images/channel/3.png");
		boolean success = false;
		try {
			success = this.addChann(chan);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		success=numColService.addColData(siteId);
		return success;
	}
	
	/**
	 * 初始化视频组件
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public boolean addVideo(Integer siteId){
		ColFormBean col = new ColFormBean();
		col.setType(2);
		col.setName("视频");
		col.setAuditType(2);
		col.setColListType(1);
		col.setCommonType(1);
		col.setInfoListType(1);
		col.setInfoContentType(1);
		col.setIssearch(0);
		col.setSiteId(siteId);
		Integer colId = 0;
		try {
			colId = colService.addCol(col);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		InfoFormBean info = new InfoFormBean();
		info.setColId(colId);
		info.setTitle("视频");
		info.setCreateTime(new Date());
		info.setSynTime(new Date());
		info.setSiteId(siteId);
		info.setStatus(1);
		info.setFirstPicPath("/resources/app/images/init/video.png");
		info.setVedio(domain+"/resources/app/module/test.mp4");
		info.setSiteId(siteId);
		info.setAbs("视频信息");
		info.setInfoListType(4);
		info.setInfoContentType(6);
		int infoId = 0;
		boolean success = false;
		try {
			infoId = infoService.addInfo(info);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		if(NumberUtil.getInt(infoId) > 0){
			ChannelFormBean chan = new ChannelFormBean();
			chan.setColIds(StringUtil.getString(colId));
			chan.setType(1);
			chan.setName("视频");
			chan.setColNames("视频");
			chan.setCreateTime(new Date());
			chan.setSiteId(siteId);
			chan.setState(1);
			try {
				success = this.addChann(chan);
			} catch (OperationException e) {
				e.printStackTrace();
			}
		}
		return success;
	}
	 
	/**
	 * addMap:初始化地图组件
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public boolean addMap(Integer siteId){
		ColFormBean col = new ColFormBean();
		col.setType(2);
		col.setName("地图");
		col.setAuditType(2);
		col.setColListType(1);
		col.setCommonType(1);
		col.setInfoListType(1);
		col.setIssearch(0);
		col.setInfoContentType(1);
		col.setSiteId(siteId);
		Integer colId = 0;
		try {
			colId = colService.addCol(col);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		InfoFormBean info = new InfoFormBean();
		info.setColId(colId);
		info.setTitle("地图");
		info.setCreateTime(new Date());
		info.setSynTime(new Date());
		info.setSiteId(siteId);
		info.setStatus(1);
		info.setSiteId(siteId);
		info.setAbs("地图");
		info.setPointType(1);
		info.setPointLocation("31.9750210000,118.8043410000");
		info.setAddress("南京市");
		info.setInfoListType(2);
		info.setInfoContentType(7);
		info.setFirstPicPath("/resources/app/images/init/map.png");
		boolean success = false;
		int infoId = 0;
		try {
			infoId = infoService.addInfo(info);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		if(NumberUtil.getInt(infoId) > 0){
			ChannelFormBean chan = new ChannelFormBean();
			chan.setColIds(StringUtil.getString(colId));
			chan.setType(1);
			chan.setName("地图");
			chan.setColNames("地图");
			chan.setSiteId(siteId);
			chan.setCreateTime(new Date());
			chan.setState(1);
			try {
				success = this.addChann(chan);
			} catch (OperationException e) {
				e.printStackTrace();
			}
		}
		return success;
	}
	 
	/**
	 * 初始化街景组件
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public boolean addStreetView(Integer siteId){
		ColFormBean col = new ColFormBean();
		col.setType(2);
		col.setName("街景");
		col.setAuditType(2);
		col.setColListType(1);
		col.setCommonType(1);
		col.setIssearch(0);
		col.setInfoListType(1);
		col.setInfoContentType(1);
		col.setSiteId(siteId);
		Integer colId = 0;
		try {
			colId = colService.addCol(col);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		InfoFormBean info = new InfoFormBean();
		info.setColId(colId);
		info.setTitle("街景");
		info.setCreateTime(new Date());
		info.setSynTime(new Date());
		info.setSiteId(siteId);
		info.setStatus(1);
		info.setSiteId(siteId);
		info.setFirstPicPath("/resources/app/images/init/vista.png");
		info.setAbs("街景");
		info.setPointType(3);
		info.setPointLocation("31.9750210000,118.8043410000");
		info.setAddress("南京市");
		info.setInfoListType(4);
		info.setInfoContentType(7);
		boolean success = false;
		int infoId = 0;
		try {
			infoId = infoService.addInfo(info);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		if(NumberUtil.getInt(infoId) > 0){
			ChannelFormBean chan = new ChannelFormBean();
			chan.setColIds(StringUtil.getString(colId));
			chan.setType(1);
			chan.setName("街景");
			chan.setColNames("街景");
			chan.setCreateTime(new Date());
			chan.setSiteId(siteId);
			chan.setState(1);
			try {
				success = this.addChann(chan);
			} catch (OperationException e) {
				e.printStackTrace();
			}
		}
		return success;
	}
	 
	/**
	 * 查询数据库中最新的版本号
	 * @param clientList clientList
	 * @return    设定参数 .
	 */
	public String findNewVersion(List<Client> clientList){
		Client client = new Client();
		for(Client clientEn : clientList){
			if(NumberUtil.getFloat(clientEn.getVersion()) > NumberUtil.getFloat(client.getVersion())){
				client = clientEn;  
				}
			}
		return client.getVersion();
    } 
	
	/**
	 * 初始化酷图组件
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public boolean addKuTu(Integer siteId){
		ColFormBean col = new ColFormBean();
		col.setType(2);
		col.setName("酷图");
		col.setAuditType(2);
		col.setColListType(1);
		col.setCommonType(1);
		col.setIssearch(0);
		col.setInfoListType(1);
		col.setInfoContentType(1);
		col.setSiteId(siteId);
		Integer colId = 0;
		try {
			colId = colService.addCol(col);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		InfoFormBean info = new InfoFormBean();
		info.setColId(colId);
		info.setTitle("酷图");
		info.setCreateTime(new Date());
		info.setSynTime(new Date());
		info.setSiteId(siteId);
		info.setStatus(1);
		info.setSiteId(siteId);
		info.setFirstPicPath("/resources/app/images/pic/1.jpg");
		info.setAbs("酷图");
		info.setInfoListType(7);
		info.setInfoContentType(4);
		boolean success = false;
		int infoId = 0;
		try {
			infoId = infoService.addInfo(info);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		if(NumberUtil.getInt(infoId) > 0){
			ChannelFormBean chan = new ChannelFormBean();
			chan.setColIds(StringUtil.getString(colId));
			chan.setType(1);
			chan.setName("酷图");
			chan.setColNames("酷图");
			chan.setSiteId(siteId);
			chan.setCreateTime(new Date());
			chan.setState(1);
			chan.setFirstPic("/resources/app/images/channel/2.png");
			String picName = "1.jpg%_&2.jpg%_&3.jpg";
			String picDesc = "图片三%_&图片二%_&图片一";		
			try {
				success = picService.addPics(info, picName, picDesc);
				success = this.addChann(chan);
			} catch (OperationException e) {
				e.printStackTrace();
			}
		}
		return success;
	}
	
	/**
	 * 通过用户ID获取应用实体集合
	 * @param userid
	 *            用户ID
	 * @return 应用实体集合
	 */
	public List<App> findByUserId(int userid) {
		return appDAO.findByUserId(userid);
	}
	
	/**
	 * 通过ID获取应用实体 
	 * @param iid
	 *            ID
	 * @return 应用实体
	 */
	public App findById(int iid) {
		return appDAO.findByIid(iid);
	}
	
	/**
	 * 打包
	 * @param app app
	 * @param andcLiiD andcLiiD
	 * @param iphoneCliId iphoneCliId
	 * @param ipadCliId ipadCliId
	 * @param version version
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public  boolean generateApp(App app, int andcLiiD, int iphoneCliId, int ipadCliId, String version) throws OperationException{
		Map<String, String> map = new HashMap<String, String>();
		//组织打包参数 
		String url = Configs.getConfigs().getJmpUrl();
    	map.put("appname", app.getName());
    	map.put("siteid", StringUtil.getString(app.getSiteId()));
    	map.put("colortype", StringUtil.getString(app.getModelType()));
    	map.put("icon", Configs.getConfigs().getJmpUrl() + app.getLogoPath()+"?"+MathUtil.randomNumeric(6));
    	map.put("splash", Configs.getConfigs().getJmpUrl() + app.getSplashIcon()+"?"+MathUtil.randomNumeric(6));
    	map.put("appdesc", app.getSpec());
    	map.put("serverurl", url+"/interfaces/resultcreateapp.do");
    	map.put("version", version);
    	map.put("logo", Configs.getConfigs().getJmpUrl() + app.getLogoIcon()+"?"+MathUtil.randomNumeric(6));
    	map.put("domain", url);
    	boolean bl = true;
    	String json = "";
    	map.put("clienttype", StringUtil.getString(2));
		json = JsonUtil.objectToString(map);
		bl = obtGenerate(json, version, app.getSiteId(), 2);
		map.put("clienttype", StringUtil.getString(3));
		json = JsonUtil.objectToString(map);
		bl = obtGenerate(json, version, app.getSiteId(), 3);
		map.put("clienttype", StringUtil.getString(4));
		json = JsonUtil.objectToString(map);
		bl = obtGenerate(json, version, app.getSiteId(),4);
		return bl;
}
	
	/**
	 * 生成包
	 * @param json     json
	 * @param version  version 
	 * @param siteid   siteid
	 * @param clienttype   clienttype
	 * @return boolean
	 */
	public boolean obtGenerate(String json, String version, Integer siteid, Integer clienttype){ 
		String clientReceive = ""; 
		//打包发送地址
		String sendUrl = Configs.getConfigs().getIosUrl();
		if(clienttype == 3){
			sendUrl = Configs.getConfigs().getApkUrl(); 
		} 
		clientReceive = sendAppSer(json, sendUrl);
		if(clientReceive!=null && clientReceive.indexOf("success")==-1){  
			Client client = clientDAO.findByVersionAndClientType(version, clienttype, siteid);
			client.setStatus(3);
			client.setSendJson(json);
			client.setBackJson(clientReceive);
			clientDAO.update(client);
			return false;
		}
		CreateReturnEntity createReturnEntity = JsonUtil.StringToObject(clientReceive, CreateReturnEntity.class); 
		boolean bl = true;
		if(createReturnEntity == null){
			bl = false;
			return bl; 
		} else {
			Client client = clientDAO.findByVersionAndClientType(createReturnEntity.getVersion(), 
					        NumberUtil.getInt(createReturnEntity.getClienttype()), 
					        NumberUtil.getInt(createReturnEntity.getSiteid()));
			if(client!=null){
				client.setStatus(1);
				client.setSendJson(json);
				client.setBackJson(clientReceive);
				clientDAO.update(client);
			}
		}
		return bl; 
	}
	
	/**
	 * 发送打包参数
	 * @param json 打包参数 
	 * @param url 打包地址 
	 * @return boolean
	 */
	public String sendAppSer(String json, String url){
		try {  
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("strJSON", json));
			String clientReturn = HttpClientUtil.postInfo(url, params, "UTF-8");
			return clientReturn;
		} catch (Exception e) {
			return "error";
		}
	}
	 
	/**
	 * 解析文件
	 * @param appid appid
	 * @param siteId    siteId .
	 */
	public void generateTpl(Integer appid, Integer siteId){ 
		Map<String, ClientVersion> root = new HashMap<String, ClientVersion>();
		ClientVersion clientVersion = new ClientVersion();
		List<Client> clientList = null;
		Client clientEn = null;
		String sysUrl = Configs.getConfigs().getJmpUrl(); // 获取系统路径
		
		//查询最新的版本3对象
		clientList = clientDAO.findByAppIdAndClientType(appid, 3);
		if(!CollectionUtils.isEmpty(clientList)){
			clientEn = clientList.get(0);
			clientVersion.setAndroidHtml(Configs.getConfigs().getJmpUrl()+"/web/" 
					+ "site" + siteId + "/app/" + appid + "/android.html");
			clientVersion.setAndroidUrl(clientEn.getAndroidUrl());
			
		}
		//查询最新的版本2对象
		clientList = clientDAO.findByAppIdAndClientType(appid, 2);
		if(!CollectionUtils.isEmpty(clientList)){
			clientEn = clientList.get(0);
			clientVersion.setIphoneHtml(Configs.getConfigs().getJmpUrl()+"/web/" 
					+ "site" + siteId + "/app/" + appid + "/iphone.html");
			clientVersion.setIphoneUrl(clientEn.getPlist());
		}
		//查询最新的版本4对象
		clientList = clientDAO.findByAppIdAndClientType(appid,4);
		if(!CollectionUtils.isEmpty(clientList)){
			clientEn = clientList.get(0);
			clientVersion.setIpadHtml(Configs.getConfigs().getJmpUrl()+"/web/" 
					+ "site" + siteId + "/app/" + appid + "/ipad.html");
			clientVersion.setPadUrl(sysUrl+clientEn.getIpadUrl());
		}
	
		clientVersion.setSysUrl(sysUrl);
		App app = appDAO.findByIid(appid);
		String appName = app.getName();
		clientVersion.setAppName(appName); 
		root.put("list1", clientVersion);
		String str = FreemarkerUtil.processFTL("index.ftl", root);
		FileUtil.writeStringToFile(new File(BaseInfo.getRealPath()+"/web/" + "site"+siteId
				+ "/app/" + appid + "/index.html"), str); // 将文件写入目标文件
		
		str = FreemarkerUtil.processFTL("android.ftl", root);
		FileUtil.writeStringToFile(new File(BaseInfo.getRealPath()+"/web/" + "site"+siteId 
				+ "/app/" + appid + "/android.html"), str); // 将文件写入目标文件
		
		str = FreemarkerUtil.processFTL("iphone.ftl", root);
		FileUtil.writeStringToFile(new File(BaseInfo.getRealPath()+"/web/" + "site"+siteId 
				+ "/app/" + appid + "/iphone.html"), str); // 将文件写入目标文件
		
		str = FreemarkerUtil.processFTL("ipad.ftl", root);
		FileUtil.writeStringToFile(new File(BaseInfo.getRealPath()+"/web/" + "site"+siteId 
				+ "/app/" + appid + "/ipad.html"), str); // 将文件写入目标文件
	}
	
	
	/**
	 * 解析文件
	 * @param appid appid
	 * @param siteId    siteId .
	 */
	public void generateTplBack(Integer appid, Integer siteId){ 
		Map<String, ClientVersion> root = new HashMap<String, ClientVersion>();
		ClientVersion clientVersion=new ClientVersion();
		List<Version> updateList = null;
		Version versionEn = null;
		String sysUrl=Configs.getConfigs().getJmpUrl(); // 获取系统路径
		
		//查询最新的安卓对象 
		updateList = versionDAO.findByAppIdAndClientType(appid, 3);
		if(!CollectionUtils.isEmpty(updateList)){
			versionEn = updateList.get(0);
			clientVersion.setAndroidHtml(Configs.getConfigs().getJmpUrl()+"/web/" 
					+ "site" + siteId + "/app/" + appid + "/android.html"); 
			if(NumberUtil.getInt(versionEn.getApptype())==1){
				clientVersion.setAndroidUrl(Configs.getConfigs().getJmpUrl()+versionEn.getDownUrl());
			}else{
				clientVersion.setAndroidUrl(versionEn.getDownUrl());
			} 
		}
		String url = Configs.getConfigs().getJmpUrl().replace("http", "https");
		//查询最新的iphone对象
		updateList = versionDAO.findByAppIdAndClientType(appid, 2);
		if(!CollectionUtils.isEmpty(updateList)){
			versionEn = updateList.get(0);
			clientVersion.setIphoneHtml(Configs.getConfigs().getJmpUrl()+"/web/" 
					+ "site" + siteId + "/app/" + appid + "/iphone.html"); 
			if(NumberUtil.getInt(versionEn.getApptype())==1){
				clientVersion.setIphoneUrl(url+versionEn.getDownUrl());
			}else{
				clientVersion.setIphoneUrl(versionEn.getDownUrl());
			} 
		}
		//查询最新的ipad对象
		updateList = versionDAO.findByAppIdAndClientType(appid,4);
		if(!CollectionUtils.isEmpty(updateList)){
			versionEn = updateList.get(0);
			clientVersion.setIpadHtml(Configs.getConfigs().getJmpUrl()+"/web/" 
					+ "site" + siteId + "/app/" + appid + "/ipad.html"); 
			if(NumberUtil.getInt(versionEn.getApptype())==1){
				clientVersion.setPadUrl(url+versionEn.getDownUrl());
			} else {
				clientVersion.setPadUrl(versionEn.getDownUrl());
			} 
		}
	
		clientVersion.setSysUrl(sysUrl); 
		App app = appDAO.findByIid(appid);
		String appName = app.getName();
		clientVersion.setAppName(appName);  
		root.put("list1", clientVersion);
		String str = FreemarkerUtil.processFTL("index.ftl", root); 
		FileUtil.writeStringToFile(new File(BaseInfo.getRealPath()+"/web/" + "site"+siteId + 
				"/app/" + appid + "/index.html"), str); // 将文件写入目标文件
		
		str = FreemarkerUtil.processFTL("android.ftl", root);
		FileUtil.writeStringToFile(new File(BaseInfo.getRealPath()+"/web/" + "site"+siteId 
				+ "/app/" + appid + "/android.html"), str); // 将文件写入目标文件
		
		str = FreemarkerUtil.processFTL("iphone.ftl", root);
		FileUtil.writeStringToFile(new File(BaseInfo.getRealPath()+"/web/" + "site"+siteId 
				+ "/app/" + appid + "/iphone.html"), str); // 将文件写入目标文件
		
		str = FreemarkerUtil.processFTL("ipad.ftl", root);
		FileUtil.writeStringToFile(new File(BaseInfo.getRealPath()+"/web/" + "site"+siteId 
				+ "/app/" + appid + "/ipad.html"), str); // 将文件写入目标文件
		
	}
	
	/**
	 * 通过ID获取应用实体  
	 * @param iid
	 *            ID
	 * @return 应用实体
	 */
	public App findBySiteid(int siteid) {
		return appDAO.findBySiteid(siteid);
	}
	
	/**
	 * 初始化网站及首页
	 * @param app app
	 * @return Integer
	 */
	public Integer addSiteAndInfoBack(App app){
		Integer siteId = NumberUtil.getInt(app.getSiteId());
		 
		//初始化banner栏目
		Integer bannerId = 0;
		ColFormBean col = new ColFormBean();
		col.setType(2);
		col.setName("banner 栏目");
		col.setAuditType(2);
		col.setColListType(1);
		col.setIssearch(0);
		col.setCommonType(1);
		col.setInfoListType(1);
		col.setInfoContentType(1);
		col.setSiteId(siteId);
		try {
			bannerId = colService.addCol(col); 
		} catch (OperationException e) {
			e.printStackTrace();
		}
		Integer firstPageId = 0;
		int col2Id = 0;
		int col3Id = 0;
		ColFormBean col1 = new ColFormBean();
		
		//首页栏目
		col1.setType(2);
		col1.setName("首页");
		col1.setAuditType(2);
		col1.setInfoListType(4);
		col1.setBannerId(bannerId);
		col1.setColListType(1);
		col1.setIssearch(0);
		col1.setCommonType(2);
		col1.setInfoContentType(1);
		col1.setSiteId(siteId);
		col1.setBannerName("banner 栏目");
		
		//栏目一
		ColFormBean col2 = new ColFormBean();
		col2.setType(2);
		col2.setName("栏目一");
		col2.setAuditType(2);
		col2.setInfoListType(1);
		col2.setColListType(1);
		col2.setIssearch(0);
		col2.setCommonType(1);
		col2.setInfoContentType(1);
		col2.setSiteId(siteId);
		
		//栏目二
		ColFormBean col3 = new ColFormBean();
		col3.setType(2);
		col3.setName("栏目二");
		col3.setAuditType(2);
		col3.setInfoListType(5);
		col3.setColListType(1);
		col3.setCommonType(1);
		col3.setInfoContentType(1);
		col3.setIssearch(0);
		col3.setSiteId(siteId);
		try {
			firstPageId = colService.addCol(col1);
			col2Id = colService.addCol(col2);
			col3Id = colService.addCol(col3);
			
		} catch (OperationException e) {
			e.printStackTrace();
		}
		
		InfoFormBean info = new InfoFormBean();
		info.setColId(bannerId);
		info.setTitle("大汉科技-微门户");
		info.setCreateTime(new Date());
		info.setSynTime(new Date());
		info.setStatus(1);
		info.setSiteId(siteId);
		File bannerInfoContent = new File(BaseInfo.getRealPath() + "/resources/app/module/bannerInfoContent.txt");
		info.setContent(FileUtil.readFileToString(bannerInfoContent, "utf-8"));
		info.setInfoListType(1);
		info.setInfoContentType(1);
		info.setFirstPicPath("/resources/app/images/init/first.png");
		try {
			infoService.addInfo(info);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		
		InfoFormBean info1 = new InfoFormBean();
		info1.setColId(firstPageId);
		info1.setTitle("大汉网络");
		info1.setCreateTime(new Date());
		info1.setSynTime(new Date());
		info1.setSiteId(siteId);
		info1.setStatus(1);
		info1.setSiteId(siteId);
		File firstInfoContent = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent.txt"); 
		String content = FileUtil.readFileToString(firstInfoContent, "utf-8");
		info1.setContent(content);
		info1.setInfoListType(4);
		info1.setInfoContentType(1);
		info1.setFirstPicPath("/resources/app/images/init/hanweb.png");
		
		InfoFormBean info11 = new InfoFormBean();
		info11.setColId(firstPageId);
		info11.setTitle("云端移动APP自助生成平台");
		info11.setCreateTime(new Date());
		info11.setSynTime(new Date());
		info11.setSiteId(siteId);
		info11.setStatus(1);
		info11.setSiteId(siteId);
		File firstInfoContent1 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent1.txt"); 
		String content1 = FileUtil.readFileToString(firstInfoContent1, "utf-8");
		info11.setContent(content1);
		info11.setInfoListType(4);
		info11.setInfoContentType(1);
		info11.setFirstPicPath("/resources/app/images/init/2.png");
		
		//栏目一下信息
		InfoFormBean info2 = new InfoFormBean();
		info2.setColId(col2Id);
		info2.setTitle("网上办事大厅APP解决方案");
		info2.setCreateTime(new Date());
		info2.setSynTime(new Date());
		info2.setSiteId(siteId);
		info2.setStatus(1);
		info2.setSiteId(siteId);
		File firstInfoContent2 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent2.txt"); 
		String content2 = FileUtil.readFileToString(firstInfoContent2, "utf-8");
		info2.setContent(content2);
		info2.setInfoListType(2);
		info2.setInfoContentType(1);
		info2.setFirstPicPath("/resources/app/images/init/4.png");
		
		InfoFormBean info21 = new InfoFormBean();
		info21.setColId(col2Id);
		info21.setTitle("信息公开移动APP解决方案");
		info21.setCreateTime(new Date());
		info21.setSynTime(new Date());
		info21.setSiteId(siteId);
		info21.setStatus(1);
		info21.setSiteId(siteId);
		File firstInfoContent21 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent21.txt"); 
		String content21=FileUtil.readFileToString(firstInfoContent21, "utf-8");
		info21.setContent(content21);
		info21.setInfoListType(2);
		info21.setInfoContentType(1);
		info21.setFirstPicPath("/resources/app/images/init/1.png");
		
		//栏目二下信息
		InfoFormBean info3 = new InfoFormBean();
		info3.setColId(col3Id);
		info3.setTitle("门户站群平台解决方案");
		info3.setCreateTime(new Date());
		info3.setSynTime(new Date());
		info3.setSiteId(siteId);
		info3.setStatus(1);
		info3.setSiteId(siteId);
		File firstInfoContent3 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent3.txt"); 
		String content3=FileUtil.readFileToString(firstInfoContent3, "utf-8");
		info3.setContent(content3);
		info3.setInfoListType(7);
		info3.setInfoContentType(1);
		info3.setFirstPicPath("/resources/app/images/init/9.png");
		
		InfoFormBean info31 = new InfoFormBean();
		info31.setColId(col3Id);
		info31.setTitle("互动服务平台解决方案");
		info31.setCreateTime(new Date());
		info31.setSynTime(new Date());
		info31.setSiteId(siteId);
		info31.setStatus(1);
		info31.setSiteId(siteId);
		File firstInfoContent31 = new File(BaseInfo.getRealPath() + "/resources/app/module/firstInfoContent31.txt"); 
		String content31=FileUtil.readFileToString(firstInfoContent31, "utf-8");
		info31.setContent(content31);
		info31.setInfoListType(7);
		info31.setInfoContentType(1);
		info31.setFirstPicPath("/resources/app/images/init/5.png");
		try {
			infoService.addInfo(info1);
			infoService.addInfo(info2);
			infoService.addInfo(info21);
			infoService.addInfo(info3);
			infoService.addInfo(info31);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		ChannelFormBean chan = new ChannelFormBean();
		chan.setColIds(StringUtil.getString(firstPageId));
		chan.setType(7);
		chan.setName("首页");
		chan.setColNames("首页");
		chan.setCreateTime(new Date());
		chan.setSiteId(siteId);
		chan.setState(1);
		chan.setFirstPic("/resources/app/images/col/first.png");
		chan.setColIds("" + firstPageId +"," + col2Id + "," + col3Id);
		try {
			this.addChann(chan);
		} catch (OperationException e) {
			e.printStackTrace();
		}
		return siteId;
	} 
	
	/**
	 * addComponentFront
	 * @param app
	 * @param user
	 * @param session
	 * @return
	 */
	public int addComponentFront(App app, AppUser user, HttpSession session) {
		boolean success = true;
		Integer siteId = app.getSiteId();
		if(NumberUtil.getInt(app.getIid())<=0){
			siteId = addSiteAndInfo(app);
			int isDiccount=0;
			//初始化微信卡片式
			if(success && NumberUtil.getInt(app.getCard())== 3){
				success = addCard(siteId);
			}
			
			//初始化左侧图组件
			if(success && NumberUtil.getInt(app.getLeftpic())==4){
				success = addLeftIo(siteId);
			}
			
			//初始化场景式组件
			if(success && NumberUtil.getInt(app.getScene())== 19){
				success = addSceneInfo(siteId);
			}  
			
			//初始化图片新闻组件
			if(success && NumberUtil.getInt(app.getPicmix())==5){
				success = addPicInfo(siteId);
			}
			
			//初始化瀑布流组件
			if(success && NumberUtil.getInt(app.getWaterflow())== 18){
				success = addFlowInfo(siteId);
			} 
			
			//初始化酷图组件
			if(success && NumberUtil.getInt(app.getKuTu())== 10){
				success=addKuTu(siteId);
			}
			
			//初始化视频组件
			if(success && NumberUtil.getInt(app.getVideo())== 11){
				success = addVideo(siteId);
			}
			
			//初始化标题+时间+来源
			if(success && NumberUtil.getInt(app.getTitlemix())==6){
				success = addTitlemix(siteId);
			}
			
			//初始化标题+时间+摘要组件
			if(success && NumberUtil.getInt(app.getTitlemix1())==7){
				success = addTitlemix1(siteId);
			}
			
			//初始化标题+时间+评论数组件
			if(success && NumberUtil.getInt(app.getTitlemix2())==8){
				success = addTitlemix2(siteId);
			}
			
			//初始智能公交组件
			if(success && NumberUtil.getInt(app.getIntelbus())== 12){
				success = addBus(siteId);
			} 
			
			//初始化天气组件
			if(success && NumberUtil.getInt(app.getWeather())== 14){
				success = addWeather(siteId);
			}
			
			//初始化地图组件
			if(success && NumberUtil.getInt(app.getMap())== 9){
				success = addMap(siteId);
			}
			
			//初始化报料组件
			if(success && NumberUtil.getInt(app.getBaoliao())== 13){
				success = addBaoLiao(siteId);
			}
			
			//初始化街景组件
			if(success && NumberUtil.getInt(app.getStreetView())== 6){
				success = addStreetView(siteId);
			}
			
			//初始化通讯录组件
			if(success && NumberUtil.getInt(app.getNumsense())== 15){
				success = addPhone(siteId);
			}
			
			//初始化阅读组件
			if(success && NumberUtil.getInt(app.getEbook())== 16){
				success = addRead(siteId);
			}  
			
			//初始化打折商城组件
//			if(success && NumberUtil.getInt(app.getEcommerce())== 20){
//				success = addShop(siteId);
//				isDiccount=1;
//			}  
			siteService.modify(isDiccount, NumberUtil.getInt(app.getOverall(), 1), siteId);
		}else{
			siteId = findById(NumberUtil.getInt(app.getIid())).getSiteId();
		}
		return siteId;
	}
	
	/**
	 * 初始化街景组件
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public boolean addComponentBack(App app, AppUser user, HttpSession session){
		String username = user.getUsername();
		//初始化网站
		boolean success = true;
		Integer siteId = app.getSiteId();
		Integer appid = app.getIid(); 
		App appPre= findById(appid);
		
		siteId= addSiteAndInfoBack(appPre);
		int isDiccount=0;
		
		//初始化微信卡片式
		if(success && NumberUtil.getInt(app.getCard())== 3){
			success = addCard(siteId);
		}
		
		//初始左侧图组件
		if(success && NumberUtil.getInt(app.getLeftpic())==4){
			success = addLeftIo(siteId);
		}
		
		//初始化场景式组件
		if(success && NumberUtil.getInt(app.getScene())== 19){
			success = addSceneInfo(siteId);
		}  
		
		//初始化图片新闻组件
		if(success && NumberUtil.getInt(app.getPicmix())==5){
			success = addPicInfo(siteId);
		}
		
		//初始化瀑布流组件
		if(success && NumberUtil.getInt(app.getWaterflow())== 18){
			success = addFlowInfo(siteId);
		} 
		
		//初始化酷图组件
		if(success && NumberUtil.getInt(app.getKuTu())== 10){
			success=addKuTu(siteId);
		}
		
		//初始化视频组件
		if(success && NumberUtil.getInt(app.getVideo())== 11){
			success = addVideo(siteId);
		}
		
		//初始化标题+时间+来源
		if(success && NumberUtil.getInt(app.getTitlemix())==6){
			success = addTitlemix(siteId);
		}
		
		//初始化标题+时间+摘要组件
		if(success && NumberUtil.getInt(app.getTitlemix1())==7){
			success = addTitlemix1(siteId);
		}
		
		//初始化标题+时间+评论数组件
		if(success && NumberUtil.getInt(app.getTitlemix2())==8){
			success = addTitlemix2(siteId);
		}
		
		//初始智能公交组件
		if(success && NumberUtil.getInt(app.getIntelbus())== 12){
			success = addBus(siteId);
		} 
		
		//初始化天气组件
		if(success && NumberUtil.getInt(app.getWeather())== 14){
			success = addWeather(siteId);
		}
		
		//初始化地图组件
		if(success && NumberUtil.getInt(app.getMap())== 9){
			success = addMap(siteId);
		}
		
		//初始化报料组件
		if(success && NumberUtil.getInt(app.getBaoliao())== 13){
			success = addBaoLiao(siteId);
		}
		
		//初始化街景组件
		if(success && NumberUtil.getInt(app.getStreetView())== 6){
			success = addStreetView(siteId);
		}
		
		//初始化通讯录组件
		if(success && NumberUtil.getInt(app.getNumsense())== 15){
			success = addPhone(siteId);
		}
		
		//初始化阅读组件
		if(success && NumberUtil.getInt(app.getEbook())== 16){
			success = addRead(siteId);
		}  
		
		//初始化打折商城组件
//		if(success && NumberUtil.getInt(app.getEcommerce())== 20){
//			success = addShop(siteId);
//			isDiccount=1;
//			 
//		}
		
		siteService.modify(isDiccount, NumberUtil.getInt(app.getOverall(), 1), siteId);
		if(success){ 
			//拷贝logo图片
			app.setLogoPath("/web/" + "site"+siteId + "/app/" + appid + "/images/logo.png");
			if(StringUtil.isNotEmpty(app.getLogoIcon()) && app.getLogoIcon().indexOf("tempfile")>-1){
				String sourceicon = BaseInfo.getRealPath()+app.getLogoIcon();
				String des1 = BaseInfo.getRealPath() + "/web/" + "site"+siteId + "/app/" + appid + "/images/logoIcon.png";
				FileUtil.copyFile(sourceicon, des1);
				app.setLogoIcon("/web/" + "site"+siteId + "/app/" + appid + "/images/logoIcon.png");
			}
			File splashIconFile =null;
			
			//拷贝开机图标
			if(NumberUtil.getInt(app.getSplashIcon())>0){
				String splashicon = BaseInfo.getRealPath() +"/resources/app/images/stepone/"+NumberUtil.getInt(app.getSplashIcon())+".png";
				String des1 = BaseInfo.getRealPath() + "/web/" + "site"+siteId + "/app/" + appid + "/images/splashIcon.png";
				splashIconFile = new File(splashicon);
				File desFile1 = new File(des1);
				FileUtil.copyFile(splashIconFile, desFile1);
				app.setSplashIcon("/web/" + "site"+siteId + "/app/" + appid + "/images/splashIcon.png");
			}else if(NumberUtil.getInt(app.getSplashIcon())<=0){
				String psplashIcon="";
				if(NumberUtil.getInt(appid)>0){
					psplashIcon = findById(appid).getSplashIcon();
				}
				if(!psplashIcon.equals(app.getSplashIcon())){
					String des1 = BaseInfo.getRealPath() + "/web/" + "site"+siteId + "/app/" + appid + "/images/splashIcon.png";
					splashIconFile = new File(app.getSplashIcon());
					File desFile1 = new File(des1);
					FileUtil.copyFile(splashIconFile, desFile1);
					app.setSplashIcon("/web/" + "site"+siteId + "/app/" + appid + "/images/splashIcon.png");
				}
			} 
			//splash图复制
			SiteSplash sitePlash=siteSplashService.findBySiteId(siteId);
			String des1 = BaseInfo.getRealPath() + "/web/site" + siteId + "/site/site_iphone1.jpg";
			File desFile1 = new File(des1);
			FileUtil.copyFile(splashIconFile, desFile1);
			
			des1 = BaseInfo.getRealPath() + "/web/site" + siteId + "/site/site_android1.jpg";
			desFile1 = new File(des1);
			FileUtil.copyFile(splashIconFile, desFile1);
			
			des1 = BaseInfo.getRealPath() + "/web/site" + siteId + "/site/site_ipad1.jpg";
			desFile1 = new File(des1);
			FileUtil.copyFile(splashIconFile, desFile1);
			sitePlash.setFirstAndroidPic("/web/site" + siteId + "/site/site_android1.jpg");
			sitePlash.setFirstIpadPic("/web/site" + siteId + "/site/site_ipad1.jpg");
			sitePlash.setFirstIphonePic("/web/site" + siteId + "/site/site_iphone1.jpg");
			siteSplashService.modify(sitePlash);
			CacheUtil.removeKey(StaticValues.CACHE_SITE, StringUtil.getString(user.getSiteId())); 
			//绑定user
			app.setUuid(StringUtil.getUUIDString());
			app.setIid(appid);  
			app.setCreater(user.getUsername());
			app.setUserId(user.getIid());
			app.setQrcodePath(appPre.getQrcodePath());
			updateApp(app); 
			session.setAttribute(username, app);
			CacheUtil.removeKey(StaticValues.CACHE_SITE, StringUtil.getString(siteId));  
		}
		return success;
	}
	
}