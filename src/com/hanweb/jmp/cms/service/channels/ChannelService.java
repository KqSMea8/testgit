package com.hanweb.jmp.cms.service.channels;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.common.util.file.LocalFileUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.MultipartFileInfo;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.apps.entity.manage.LightApp;
import com.hanweb.jmp.apps.service.manage.LightAppService;
import com.hanweb.jmp.cms.controller.channels.ChannelFormBean;
import com.hanweb.jmp.cms.dao.channels.ChannelDAO;
import com.hanweb.jmp.cms.entity.channels.Channel;
import com.hanweb.jmp.cms.entity.channels.ChannelCol;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.InterfaceLogConfig;
import com.hanweb.jmp.constant.LogConfig;
import com.hanweb.jmp.constant.StaticValues;
import com.hanweb.jmp.pack.entity.App;
import com.hanweb.jmp.pack.service.AppService;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.log.LogService;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.jmp.util.CacheUtil;
import com.hanweb.jmp.util.HadoopUtil;

public class ChannelService {

	/**
	 * channelDAO
	 */
	@Autowired
	private ChannelDAO channelDAO;

	/**
	 * channelColService
	 */
	@Autowired
	private ChannelColService channelColService;

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
	 * logService
	 */
	@Autowired
	private LogService logService; 

	/**
	 * appService
	 */
	@Autowired
	private AppService appService;

	/**
	 * lightAppService
	 */
	@Autowired
	LightAppService lightAppService;

	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;

	/**
	 * 新增
	 * @param channel
	 * @return
	 * @throws OperationException
	 */
	public boolean add(ChannelFormBean channel) throws OperationException{
		checkFileType(channel);
		boolean isSuccess;
		int type = NumberUtil.getInt(channel.getType());
		String filePath=fileUtil.getAbsolutePath("/web/site" + channel.getSiteId() 
				+ "/channel");  
		if(!fileUtil.exists(filePath)){
			fileUtil.createDir(filePath); 
		}
		//分布式文件目录
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			fileUtil.createDir(fileUtil.getAbsolutePath("web/site" + channel.getSiteId() 
					+ "/channel/")); 
		} 
		switch (type) {
		case 1://单栏目类  需要增加多条导航记录
			isSuccess = addChannels(channel);
			break;
		default://普通类
			isSuccess = addChannel(channel);
			break;
		}
		isSuccess = isSuccess && siteService.modifyChanFlag(channel.getSiteId()); 
		CacheUtil.removeKey(StaticValues.CACHE_SITE, StringUtil.getString(channel.getSiteId())); 
		return isSuccess;
	}

	/**
	 * 导航新增
	 * @param channel
	 * @return
	 * @throws OperationException
	 */
	private boolean addChannels(ChannelFormBean channel) throws OperationException{
		List<Integer> colIds = StringUtil.toIntegerList(channel.getColIds());
		List<String> colNames = StringUtil.toStringList(channel.getColNames());
		int orderId = channelDAO.findMaxOrderId();
		ChannelCol channelCol;
		int colOrderId = 1;
		MultipartFileInfo firstfile = MultipartFileInfo.getInstance(channel.getFirstPicFile()); 
		String firsturl = "";
		firsturl = "/web/site" + channel.getSiteId() 
				+ "/channel/channel_first_update" + "."+firstfile.getFileType();
		File updatefile = new File(BaseInfo.getRealPath() + firsturl);
		ControllerUtil.writeMultipartFileToFile(updatefile, channel.getFirstPicFile());
		for (int i = 0, len = colIds.size(); i < len; i++) {
			//1、检查导航是否重名,需回滚
			int num = channelDAO.findNumByName(0, colNames.get(i), channel.getSiteId()); 
			if (num > 0) {
				throw new OperationException("导航名称已存在,请重新填写！");
			}
			//2、导航表入库
			if(NumberUtil.getInt(channel.getOrderId()) <= 0 || i>=1){
				channel.setOrderId(orderId);
				orderId++;
			}
			//单栏目类导航名称为栏目名称
			channel.setName(colNames.get(i));
			int iid = channelDAO.insert(channel);
			if(NumberUtil.getInt(channel.getOrderId()) > 0&&channel.getOrderId()>=orderId){
				orderId = channelDAO.findMaxOrderId();
			}
			//3、导航首图和配图处理
			if(channel.getFirstPicFile()!=null){
				if (!channel.getFirstPicFile().isEmpty()) {
					if(!channel.getFirstPicFile().isEmpty()){
						firsturl = "/web/site" + channel.getSiteId() 
								+ "/channel/channel_first_" + iid + "."+firstfile.getFileType();
						File file = new File(BaseInfo.getRealPath() + firsturl);
						FileUtil.copyFile(updatefile, file);
						//分布式部署需要将本地文件剪切到云环境
						if(file.exists()) {
							HadoopUtil.fileUpload(file, firsturl);
						}
						//4、更新导航首图图片地址
						channel.setIid(iid);
						channel.setFirstPic(firsturl);  
					}
					channelDAO.updatePic(channel); 
				}
			}
			//5、导航栏目关系表入库
			channelCol = new ChannelCol();
			channelCol.setSiteId(channel.getSiteId());
			channelCol.setChannelId(iid);
			channelCol.setColId(NumberUtil.getInt(colIds.get(i)));
			channelCol.setOrderId(colOrderId++);
			channelColService.add(channelCol);
			//6、写日志
			logService.add(LogConfig.modchannel, LogConfig.opradd, colNames.get(i));
		}
		FileUtil.deleteFile(updatefile);
		return true;
	}

	/**
	 * 导航新增 ：信息聚合和微博群聚合类
	 * @param channel 
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException 操作异常
	 */
	private boolean addChannel(ChannelFormBean channel) throws OperationException{
		boolean isSuccess = true;
		if(channel == null){
			return false;
		} else {
			//1、检查导航是否重名,需回滚
			int num = channelDAO.findNumByName(0, channel.getName(), channel.getSiteId()); 
			if (num > 0) {
				throw new OperationException("导航名称已存在,请重新填写！");
			}
			//2、导航表入库
			int orderId = channelDAO.findMaxOrderId();
			channel.setOrderId(orderId);
			int iid = channelDAO.insert(channel);
			//3、导航首图和配图处理
			if(channel!=null && channel.getFirstPicFile()!=null){
				if (!channel.getFirstPicFile().isEmpty()) {
					String firsturl="";
					if(!channel.getFirstPicFile().isEmpty()){
						MultipartFileInfo firstfile = MultipartFileInfo.getInstance(
								channel.getFirstPicFile()); 
						firsturl = "/web/site" + channel.getSiteId() 
								+ "/channel/channel_first_" + iid
								+ "."+firstfile.getFileType(); 
						fileUtil.writeMultipartFileToFile(fileUtil.getAbsolutePath(firsturl), 
								channel.getFirstPicFile()); 
						//4、更新导航首图图片地址
						channel.setIid(iid);
						channel.setFirstPic(firsturl);  
					}
					channelDAO.updatePic(channel); 
				}
			}
			//5、导航栏目关系表入库
			ChannelCol channelCol;
			String colId = channel.getColIds();
			String[] colids = StringUtil.split(colId, ",");
			int colOrderId = 1;
			for (String colid : colids) {
				channelCol = new ChannelCol();
				channelCol.setSiteId(channel.getSiteId());
				channelCol.setChannelId(iid);
				channelCol.setColId(NumberUtil.getInt(colid));
				channelCol.setOrderId(colOrderId++);
				channelColService.add(channelCol);
			}
			//5、写日志
			logService.add(LogConfig.modchannel, LogConfig.opradd, channel.getName());
			return isSuccess; 
		}

	}

	/**
	 * 导航修改
	 * @param channel 
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException 操作异常
	 */
	public boolean modify(ChannelFormBean channel) throws OperationException{
		checkFileType(channel);
		boolean isSuccess;
		int type = NumberUtil.getInt(channel.getType());
		switch (type) {
		case 1://单栏目类  需要增加多条导航记录
			isSuccess = modifyChannels(channel);
			break;
		default://信息聚合类和微博聚合类
			isSuccess = modifyChannel(channel);
			break;
		}
		isSuccess = isSuccess && modifyFlag(channel.getIid());
		isSuccess = isSuccess && siteService.modifyChanFlag(channel.getSiteId());
		CacheUtil.removeKey(StaticValues.CACHE_SITE, StringUtil.getString(channel.getSiteId())); 
		return isSuccess;
	}

	/**
	 * 信息聚合类和微博聚合类的修改
	 * @param channel 
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException 操作异常
	 */
	public boolean modifyChannel(ChannelFormBean channel) throws OperationException{
		if(channel == null){
			return false;
		} else {
			//1、检查导航是否重名,需回滚
			int num = channelDAO.findNumByName(channel.getIid(), channel.getName(), channel.getSiteId()); 
			if (num > 0) {
				throw new OperationException("导航名称已存在,请重新填写！");
			}
			//2、首图处理
			Integer iid = channel.getIid();
			//3、导航首图和配图处理
			if(channel!=null && channel.getFirstPicFile()!=null){
				if (!channel.getFirstPicFile().isEmpty()) {
					String firsturl="";
					if(!channel.getFirstPicFile().isEmpty()){
						MultipartFileInfo firstfile = MultipartFileInfo.getInstance(channel.getFirstPicFile()); 
						firsturl = "/web/site" + channel.getSiteId() 
								+ "/channel/channel_first_" + iid +"_"+StringUtil.getUUIDString()
								+ "."+firstfile.getFileType(); 
						fileUtil.writeMultipartFileToFile(fileUtil.getAbsolutePath(firsturl), channel.getFirstPicFile()); 
						//4、更新导航首图图片地址
						channel.setIid(iid);
						channel.setFirstPic(firsturl); 
						//this.modifyPath(channel);
					}  
					channelDAO.updatePic(channel); 
				}
			}
			//4、导航栏目对应表更新 注意结合使用siteid来操作 以免恶意攻击
			channelColService.deleteByIidAndSiteId(iid, channel.getSiteId());
			ChannelCol channelCol;
			String colId = channel.getColIds();
			String[] colids = StringUtil.split(colId, ",");
			int colOrderId = 1;
			for (String colid : colids) {
				channelCol = new ChannelCol();
				channelCol.setSiteId(channel.getSiteId());
				channelCol.setChannelId(iid);
				channelCol.setColId(NumberUtil.getInt(colid));
				channelCol.setOrderId(colOrderId++);
				channelColService.add(channelCol);
			}
			//5、写日志
			logService.add(LogConfig.modchannel, LogConfig.oprmodify, channel.getName());
			return channelDAO.update(channel); 
		}
	}

	/**
	 * 单栏目类的导航修改
	 * @param channel 
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException 操作异常
	 */
	public boolean modifyChannels(ChannelFormBean channel) throws OperationException{
		List<Integer> idList = new ArrayList<Integer>();
		List<String> nameList = new ArrayList<String>();
		List<Channel> channels = new ArrayList<Channel>();
		idList.add(channel.getIid());
		nameList.add(channel.getName());
		channels.add(channel);
		removeByIdsAndSiteId(idList, nameList, channel.getSiteId() , false, channels);
		channel.setCreateTime(new Date());
		return addChannels(channel);
	}

	/**
	 * 导航删除
	 * @param ids 多个导航id 
	 * @param names 多个导航名 如 1,2,3
	 * @param siteId 网站id
	 * @param isdelete isdelete
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException 操作异常
	 */
	public boolean removeByIdsAndSiteId(List<Integer> ids,
			List<String> names, Integer siteId, boolean isdelete, List<Channel> channels) 
					throws OperationException{
		boolean isSuccess = true; 
		//1、删除channel表
		isSuccess = channelDAO.deleteByIidsAndSiteId(ids, siteId);
		if(!isSuccess){
			throw new OperationException("删除导航失败！");
		}

		//2、删除channel_column 关系表
		isSuccess =	channelColService.deleteByIidsAndSiteId(ids, siteId);
		if(!isSuccess){
			throw new OperationException("删除导航栏目关系失败！");
		}

		//3、删除首图
		if(isdelete){
			this.deletePicByIidsAndSiteId(channels, siteId);
		}
		//4、修改网站下导航变动标记位
		siteService.modifyChanFlag(siteId);

		//4、写日志
		for (String name : names) {
			logService.add(LogConfig.modchannel, LogConfig.oprremove, name);
		}
		return true;
	}

	/**
	 * 根据导航id查实体
	 * @param iid 导航id
	 * @return Channel 导航实体 
	 */
	public Channel findByIid(Integer iid){
		//1、查channel表 
		Channel channel = channelDAO.findByIid(iid);
		if(channel==null){
			return channel;
		}
		//2、查channel_column关联表,组织colIds和colNames
		StringBuffer colidsStr=new StringBuffer(100);
		StringBuffer colNamessStr=new StringBuffer(100);
		String colIds = "";
		String colNames = "";
		List<ChannelCol> channelCols = channelColService.findByChannelId(iid);
		for (ChannelCol channelCol : channelCols) {
			colidsStr.append("," + channelCol.getColId());
			colNamessStr.append("," + colService.findByIid(channelCol.getColId()).getName());
		}
		if(colidsStr!=null && colidsStr.length()>1){
			colIds = colidsStr.toString().substring(1);
		}
		if(colNamessStr!=null && colNamessStr.length()>1){
			colNames = colNamessStr.toString().substring(1);
		}
		channel.setColIds(colIds);
		channel.setColNames(colNames);
		return channel; 
	}

	/**
	 * 组织导航新增树
	 * @param siteId 网站id
	 * @return String 栏目树的信息
	 */
	public String findAddColTree(Integer siteId){
		List<Col> colList;
		colList = colService.findBySiteIdAndType(siteId);
		//组织树4
		Tree tree = Tree.getInstance();
		tree.addNode(TreeNode.getInstance("0", "-1", /*"<a style='color:red'>选择栏目</a>" 
				+ " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
		        + " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
				+ " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
				+ " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
				+ " <a style='color:#3498DB' onclick='clearValue();'>清空</a>"*/"选择栏目", null, true)
				.setNocheck(true));
		for (Col col : colList) {
			tree.addNode(TreeNode.getInstance("" + col.getIid(), 
					""+NumberUtil.getInt(col.getPid()), col.getName()).setOpen(false));
		}
		return tree.parse();
	}

	/**
	 * 组织导航修改树
	 * @param siteId 网站id
	 * @param channelId 导航id
	 * @return String 栏目树信息
	 */
	public String findModifyColTree(Integer siteId, Integer channelId){
		List<Col> colList;
		String checkedColIds = "," + channelColService.findCheckedColIds(siteId, channelId) + ",";	
		colList = colService.findBySiteIdAndType(siteId);
		//组织树
		Tree tree = Tree.getInstance();
		tree.addNode(TreeNode.getInstance("root", null, /*"<a style='color:red'>选择栏目</a>" 
				+ " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
				+ " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
				+ " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
				+ " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
				+ " <a style='color:#3498DB' onclick='clearValue();'>清空</a>"*/"选择栏目", null, true)	
				.setNocheck(true));
		TreeNode treeNode;
		for (Col col : colList) {
			treeNode = TreeNode.getInstance("" + col.getIid(), 
					""+NumberUtil.getInt(col.getPid()), col.getName()).setOpen(false);
			if(checkedColIds.indexOf("," + col.getIid() + ",") > -1){
				treeNode.setChecked(true);
			}
			tree.addNode(treeNode);
		}
		return tree.parse();
	}

	/**
	 * 查找所有导航
	 * @param siteId 网站id
	 * @return List<Channel> 导航list
	 */
	public List<Channel> findAll(Integer siteId){
		return channelDAO.findAll(siteId);
	}

	/**
	 * 更新信息id的orderid
	 * @param ids 信息id
	 * @param orderids 排序id
	 * @return true 成功<b/>
	 * 			false 失败
	 * @throws OperationException 操作异常
	 */
	public boolean modifyOrderIdById(String ids, String orderids)
			throws OperationException {
		if (ids == null || ids.length() == 0 || orderids == null
				|| orderids.length() == 0) {
			return false;
		}
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		List<Integer> ordersList = StringUtil.toIntegerList(orderids, ",");
		boolean isSuccess = false;
		for (int i = 0, len = idsList.size(); i < len; i++) {
			isSuccess =  channelDAO.updateOrderIdById(ordersList.get(i),
					idsList.get(i));
			if(i == len-1){
				isSuccess = isSuccess 
						&& siteService.modifyChanFlag(findByIid(idsList.get(i)).getSiteId());
			}
		}
		return isSuccess;
	}

	/**
	 * 根据iid查询导航
	 * @param channelIds channelIds
	 * @return    设定参数 .
	 */
	public List<Channel> findByIids(String channelIds) {
		return channelDAO.findByIids(channelIds);
	} 

	/**
	 * 通过网站id查找该网站下导航数目
	 * @param siteId
	 *            网站id
	 * @return int 数目
	 */
	public int findChannelNumBySiteId(int siteId) {
		int num = channelDAO.findChannelNumBySiteId(siteId);
		return num;
	} 

	/**
	 * 通过iid和网站id删除首图
	 * @param ids ids
	 * @param siteId    设定参数 .
	 */
	public void deletePicByIidsAndSiteId(List<Channel> channels, Integer siteId){
		if(CollectionUtils.isEmpty(channels)){
			return;
		}
		String firstPicPath ="";
		String compoundPath="";
		for(Channel channelEn : channels){
			firstPicPath=channelEn.getFirstPic();
			compoundPath=channelEn.getCompoundPic();
			if(StringUtil.isNotEmpty(firstPicPath)){
				if(firstPicPath.startsWith("/")){
					firstPicPath=firstPicPath.substring(1);
					fileUtil.deleteFile(fileUtil.getAbsolutePath(firstPicPath));
				}
			}
			if(StringUtil.isNotEmpty(compoundPath)){
				if(compoundPath.startsWith("/")){
					compoundPath=compoundPath.substring(1);
					fileUtil.deleteFile(fileUtil.getAbsolutePath(compoundPath));
				}
			}
		}  
	}

	/**
	 * 检查附件类型
	 * @param channel
	 *            导航实体
	 * @return

	 * @throws OperationException
	 *             界面异常
	 */
	private void checkFileType(ChannelFormBean channel) throws OperationException {
		if(channel==null){
			return;
		} 
		if(channel.getFirstPicFile()!=null){


			//首图
			if (!channel.getFirstPicFile().isEmpty()) {
				//判断首图尺寸大小
				MultipartFile file = channel.getFirstPicFile();
				int imgWidth = 0;
				int imgHeight = 0;		
				try {
					InputStream ins = file.getInputStream();
					File f = new File(file.getOriginalFilename());
					OutputStream os = new FileOutputStream(f);
					int bytesRead = 0;
					byte[] buffer = new byte[8192];
					while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
						os.write(buffer, 0, bytesRead);
					}
					os.close();
					ins.close();
					FileInputStream fis = new FileInputStream(f);
					BufferedImage bufferedImg = ImageIO.read(fis);
					imgWidth = bufferedImg.getWidth();
					imgHeight = bufferedImg.getHeight();
					fis.close();
					File del = new File(f.toURI());
					del.delete();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if(imgWidth!=128 && imgHeight!=128){
					throw new OperationException("首图尺寸为128*128");
				}

				MultipartFileInfo firstfile = MultipartFileInfo.getInstance(channel.getFirstPicFile()); 
				if(StaticValues.IMG_TYPE.indexOf(firstfile.getFileType().toLowerCase()) == -1){
					throw new OperationException("首图类型不正确，请重新上传！");
				}
			} 
		}
	}

	/**
	 * 更新标志位
	 * @param id id
	 * @return    设定参数 .
	 */
	public boolean modifyFlag(int id){
		if(id <= 0){
			return false;
		}
		return channelDAO.updateFlag(id);
	}

	/**
	 * 导航启用停用
	 * @param ids ids
	 * @param state state
	 * @return boolean
	 * @throws OperationException    设定参数 .
	 */
	public boolean modifyState(String ids, int state) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		boolean isSuccess = false;
		// 记录日志的操作id
		int funcId = 0;
		if (state == 0) {// 停用
			funcId = LogConfig.oprclose;
			isSuccess = channelDAO.updateState(idList, state);
			if (!isSuccess) {
				throw new OperationException("停用栏目失败！");
			}
		}else {// 启用
			funcId = LogConfig.opropen;
			isSuccess = channelDAO.updateState(idList, state);
			if (!isSuccess) {
				throw new OperationException("启用栏目失败！");
			}
		}
		for (int i = 0; i < idList.size(); i++) {
			Channel chan = findByIid(idList.get(i));
			logService.add(LogConfig.modchannel, funcId, chan.getName());
			if(i == 0){
				siteService.modifyChanFlag(chan.getSiteId());
			}
		}
		return isSuccess;
	}

	/**
	 * 查询首页信息
	 * @param siteid siteid
	 * 3.1.12中将领导信箱，网上调查等取消
	 * 原来的混合类型的id改为2，自定义类型为3
	 * 新增一种自定义类型
	 * @return String
	 */
	public String findCatesJson(Integer siteid){  
		String jmpUrl=Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl=fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl=jmpUrl.substring(0, jmpUrl.length()-1);
			}
		}
		HashMap<String, Object> ret = new HashMap<String, Object>();
		List<HashMap<String, Object>> channels = new ArrayList<HashMap<String, Object>>();
		try {
			Site site = siteService.findByIid(NumberUtil.getInt(siteid));
			if(site!=null){
				ret.put("flag", StringUtil.getString(site.getChanFlag() + site.getColFlag())); 
			}
			ret.put("channels", channels);
			App appEn=appService.findBySiteid(siteid);
			if(appEn!=null && appEn.getIid()>0){
				int modeltype=NumberUtil.getInt(appEn.getModelType(), 1);
				String[]  bgColors = {"ee333e", "00a6e8", "f5a500" };
				ret.put("bgcolor", bgColors[modeltype-1]);
				ret.put("logoicon", jmpUrl + StringUtil.getString(appEn.getLogoIcon()));
			}else{ 
				ret.put("bgcolor", "");
				ret.put("logoicon", "");
			}
			String firstpic="";
			List<Channel> cs = this.findAll(NumberUtil.getInt(siteid)); 
			String hudongurl = ""; 
			for(Channel c : cs){ 
				//混合类型
				if(c.getType() == 2){
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("id", StringUtil.getString(c.getIid()));
					map.put("name", StringUtil.getString(c.getName()));
					map.put("type", "0");
					map.put("channeltype",  StringUtil.getString(c.getChanneltype()));
					map.put("coltype", "");
					map.put("hudongtype", "");
					map.put("hudongurl", "");
					firstpic = StringUtil.getString(c.getFirstPic());
					if(StringUtil.isNotEmpty(firstpic)){
						firstpic = jmpUrl + c.getFirstPic();
					}
					map.put("firstpic", firstpic);
					map.put("islogin", StringUtil.getString(c.getIsVisit()));
					map.put("orderid", StringUtil.getString(c.getOrderId()));
					map.put("havenew", "0");
					map.put("islogin", StringUtil.getString(c.getIsVisit()));
					channels.add(map);
					//单栏目类型
				}else if(c.getType() == 1){ 
					List<Col> cols = colService.findColByChanId(c.getIid());
					for(Col col : cols){
						hudongurl=StringUtil.getString(col.getActUrl());
						HashMap<String, Object> map = new HashMap<String, Object>();
						//						if(colType==3){
						//							//领导信箱
						//							if(hdType==1){
						//								hudongurl=Configs.getConfigs().getJmpUrl()
						//								+"interfaces/lightapps/search.do?color="
						//								+col.getBgColor()+"&sysid="+col.getActUrl()
						//								+"&resourceid="+col.getIid();
						//							//
						//							}else if(hdType==2){
						//								hudongurl=Configs.getConfigs().getJmpUrl()
						//								+"interfaces/solicitopinion/index.do?barcolor="
						//								+col.getBgColor()+"&resourceid="+col.getIid();
						//								
						//							//网上调查
						//							}else if(hdType==3){
						//								hudongurl=Configs.getConfigs().getJmpUrl()
						//									+"interfaces/onlinesurvey/index.do?barcolor="
						//									+col.getBgColor()+"&resourceid="+col.getIid();
						//							}
						//						} 
						map.put("id", StringUtil.getString(col.getIid()));
						map.put("name", StringUtil.getString(col.getName()));
						map.put("type", "1");
						map.put("channeltype",  "0");
						map.put("coltype", StringUtil.getString(col.getType()));
						map.put("hudongname", StringUtil.getString(col.getName()));
						map.put("hudongtype", StringUtil.getString(col.getHdType()));
						map.put("hudongurl", hudongurl);
						map.put("cateimgurl", StringUtil.getString(col.getIconPath()) + "?" + col.getImgUuid());
						firstpic = StringUtil.getString(c.getFirstPic());
						if(StringUtil.isNotEmpty(firstpic)){
							firstpic = jmpUrl + c.getFirstPic();
						}
						map.put("firstpic", firstpic);
						map.put("islogin", StringUtil.getString(col.getIsVisit()));
						map.put("orderid", StringUtil.getString(c.getOrderId()));
						map.put("havenew", "0");
						map.put("weibotype", StringUtil.getString(col.getBlogType()));
						map.put("islogin", StringUtil.getString(c.getIsVisit()));
						map.put("commontype", StringUtil.getString(col.getCommonType()));
						map.put("bannerid", StringUtil.getString(col.getBannerId()));
						map.put("inventtype", StringUtil.getString(NumberUtil.getInt(col.getColListType())));
						map.put("remark", StringUtil.getString(col.getSpec()));
						map.put("issearch", NumberUtil.getInt(col.getIssearch())+"");
						map.put("lightappurl", StringUtil.getString(col.getLightAppUrl()));
						map.put("lightappid", StringUtil.getString(col.getLightAppId()));
						channels.add(map);
					}
					//用户中心
				}else if(c.getType() == 3){
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("id", StringUtil.getString(c.getIid()));
					map.put("name", StringUtil.getString(c.getName()));
					map.put("orderid", StringUtil.getString(c.getOrderId()));
					map.put("type", "2");
					firstpic = StringUtil.getString(c.getFirstPic());
					if(StringUtil.isNotEmpty(firstpic)){
						firstpic = jmpUrl + c.getFirstPic();
					}
					map.put("firstpic", firstpic);
					channels.add(map);

					//综合首页
				}else if(c.getType() == 4){
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("id", StringUtil.getString(c.getIid()));
					map.put("name", StringUtil.getString(c.getName()));
					map.put("orderid", StringUtil.getString(c.getOrderId()));
					map.put("type", "3");
					firstpic = StringUtil.getString(c.getFirstPic());
					if(StringUtil.isNotEmpty(firstpic)){
						firstpic = jmpUrl + c.getFirstPic();
					}
					map.put("firstpic", firstpic);
					channels.add(map);

					//自定义类型
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_FIRST, 
					InterfaceLogConfig.ERROR_08); 
		}

		return JsonUtil.objectToString(ret);
	}

}