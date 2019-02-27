package com.hanweb.jmp.cms.service.infos;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.common.util.file.LocalFileUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.MultipartFileInfo;
import com.hanweb.common.util.xml.XmlDocument;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.cms.controller.infos.info.InfoFormBean;
import com.hanweb.jmp.cms.dao.cols.ColDAO;
import com.hanweb.jmp.cms.dao.cols.ColRelationDAO;
import com.hanweb.jmp.cms.dao.infos.InfoCountDAO;
import com.hanweb.jmp.cms.dao.infos.InfoDAO;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.entity.cols.ColRelation;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.cms.entity.infos.InfoCount;
import com.hanweb.jmp.cms.entity.sign.Sign;
import com.hanweb.jmp.cms.entity.sign.SignRel;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.cms.service.comment.CommentService;
import com.hanweb.jmp.cms.service.sign.SignRelService;
import com.hanweb.jmp.cms.service.sign.SignService;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.InterfaceLogConfig;
import com.hanweb.jmp.constant.LogConfig;
import com.hanweb.jmp.constant.StaticValues;
import com.hanweb.jmp.global.entity.normalentity.SynchField;
import com.hanweb.jmp.global.entity.normalentity.SynchFile;
import com.hanweb.jmp.global.entity.normalentity.SynchInfo;
import com.hanweb.jmp.sys.entity.ditch.Ditch;
import com.hanweb.jmp.sys.entity.log.PushInfoLog;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.ditch.DitchService;
import com.hanweb.jmp.sys.service.ditch.SynFieldService;
import com.hanweb.jmp.sys.service.log.LogService;
import com.hanweb.jmp.sys.service.log.PushInfoLogService;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.jmp.util.CacheUtil;
import com.hanweb.jmp.util.GeneratorABS;
import com.hanweb.jmp.util.HadoopUtil;
import com.hanweb.jmp.util.HttpClientUtil;
import com.hanweb.jmp.util.ImageUtil;
import com.hanweb.jmp.util.MusicUtil;
import com.hanweb.jmp.util.PatternUtil;
import com.hanweb.jmp.util.QRCodeUtil;
import com.hanweb.jmp.util.StrUtil;
import com.hanweb.jmp.util.SynInfoUtil;
import com.hanweb.jmp.util.TimeUtil;
import com.hanweb.jmp.util.XmlFormat;
import com.hanweb.jmp.util.XmlUtil;
import com.hanweb.support.controller.CurrentUser;

public class InfoService {
	
	/**
	 * infoDAO
	 */
	@Autowired
	private InfoDAO infoDAO;
	
	/**
     * colDAO
     */
    @Autowired
    private ColDAO colDAO;

	/**
	 * colReDAO
	 */
	@Autowired
	private ColRelationDAO colReDAO; 

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
	 * infoOperService
	 */
	@Autowired
	private InfoOperService infoOperService;
	
	/**
	 * infoCountService
	 */
	@Autowired
	private InfoCountService infoCountService;
	
	/**
	 * pushInfoLogService
	 */
	@Autowired
	private PushInfoLogService pushInfoLogService;
	
	/**
	 * picService
	 */
	@Autowired
	private PicService picService;
	
	/**
	 * commentService
	 */
	@Autowired
	private CommentService commentService;
	
	/**
	 * signRelService
	 */
	@Autowired
	private SignRelService signRelService;
	
	/**
	 * infoCountDAO
	 */
	@Autowired
	private InfoCountDAO infoCountDAO; 
	
	/**
	 * siteService
	 */
	@Autowired
	private SiteService siteService;
	
	/**
     * ditchService
     */
    @Autowired
    private DitchService ditchService; 
	
	/**
	 * signService
	 */
	@Autowired
	private SignService signService;
	
	/**
	 * SynFieldService
	 */
	@Autowired
	private SynFieldService synFieldService;
	
	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;
	
	PatternUtil patutil = new PatternUtil(); 
	
    /**
     * logger
     */
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * 查询iid
	 * @param iid
	 * @param siteId
	 * @return
	 */
	public Info findByIid(Integer iid, Integer siteId) {
		if (NumberUtil.getInt(iid) == 0) {
			return null;
		}
		Info info = infoDAO.findByIid(iid, siteId);
		return info;
	}
	 
	/**
	 * 通过网站ID获取信息实体
	 * @param siteId
	 *            网站id
	 * @return 信息实体
	 */
	public List<Info> findBySiteId(Integer siteId) {
		if (NumberUtil.getInt(siteId) == 0) {
			return null;
		}
		return infoDAO.findBySiteId(siteId);
	}

	/**
	 * 存在重复信息个数
	 * @param info
	 *            信息实体
	 * @return 已存在重复信息的个数
	 */
	public int findNumOfSameInfo(Info info) {
		int nReturn = 0;
		if (info == null) {
			return nReturn;
		}
		nReturn = infoDAO.findNumOfSame(info);
		return nReturn;
	}

	/**
	 * 新增信息
	 * @param info
	 *            信息实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean add(InfoFormBean info) throws OperationException {
		boolean isSuccess = false;
		if (info == null) {
			return isSuccess;
		}
		String title = info.getTitle();
		title = title.replace("\n", "").replace("\r", "");
		info.setTitle(title);
		int samenum = this.findNumOfSameInfo(info);
		if (samenum > 0) {
			throw new OperationException("信息标题或者url已存在,请重新设置！");
		}
		// 排序id
		int orderid = infoDAO.findMinOrderIdByCateID(info.getColId(),this.getTableName(info.getSiteId()));
		orderid = orderid - 1;
		info.setOrderid(orderid);
		// 保存新增记录
		colService.modifyUpdateTime(info.getColId());
		int iid = infoDAO.insert(info, this.getTableName(info.getSiteId()));
		if (iid > 0) {
			// 信息存放相对路径
			String relativePath = "web/site" + info.getSiteId() + "/info/" + info.getColId() + "/"
					            + DateUtil.getCurrDate("yyyyMM") + "/" + iid + "/";
			fileUtil.createDir(fileUtil.getAbsolutePath(relativePath));
			info = excuteFile(info);
			String content = info.getContent();
			// 摘要
			String abs = GeneratorABS.generatorAbs(content);
			info.setAbs(abs);

			// 将临时目录替换成正式目录
			content = picChangeByCateid(content, info.getSiteId(), info.getColId(), iid, "");
			info.setContent(content);
			
			String xmlName = "1.xml"; 
			info.setPath("/"+relativePath + xmlName);
			info.setIid(iid);
			// 获取在高级编辑器中手动上传的图片
			ArrayList<String> alfile = findJmpFile(info.getContent());
			
			String firstPath = "";
			// 若没有上传首图，则使用信息中第一张图片作为首图
			if (StringUtil.isEmpty(info.getFirstPicPath()) 
					|| NumberUtil.getInt(info.getInfoListType())==6) {
				firstPath = excutefirstfiles(alfile, info.getSiteId(), true, info.getTitle());
				// 原首图地址
				info.setOrignalPicpath(firstPath);
			} else {
				firstPath = excutefirstfiles(info.getFirstPicPath(), info.getSiteId());
				info.setOrignalPicpath(firstPath); 
				// 将高级编辑器图片上传到远程服务器
				for(String filePath : alfile){
		    		if(filePath == null || filePath.trim().length() == 0){
		    			continue;
		    		}
		   			filePath = filePath.replace(Configs.getConfigs().getJmpUrl(), "");
		    		filePath = BaseInfo.getRealPath() + filePath;
		    		
		    		relativePath=filePath.replace(BaseInfo.getRealPath(), "");
			    	if(relativePath.startsWith("/")){
			    		relativePath=relativePath.substring(1);
			    	} 
			    	HadoopUtil.fileUpload(new File(filePath), relativePath);
				}
			}
			Col col = colService.findByIid(info.getColId());
			if(col == null){
				return false;
			}
			if(NumberUtil.getInt(info.getInfoListType()) == 0){
				info.setInfoListType(col.getInfoListType());
			}
			if(NumberUtil.getInt(info.getInfoContentType()) == 0){
				info.setInfoContentType(col.getInfoContentType());
			}
			// 修改信息
			isSuccess = infoDAO.updateInfo(info);
			if (isSuccess) {
				// 信息保存至xml
				isSuccess = writeXml(info, alfile); 
				// 酷图
				if(NumberUtil.getInt(info.getInfoContentType())==4){
					picService.addPics(info, info.getPicname(), info.getPicdesc());
				} 
			} 
			// 新增信息阅读记录
			InfoCount infoCount = new InfoCount();
			infoCount.setTitleId(iid);
			infoCount.setType(1);
			infoCountDAO.insert(infoCount, "jmp_info_count" + info.getSiteId());
			// 信息标签关联表入库
			List<Integer> tagIds = StringUtil.toIntegerList(StringUtil.getString(info.getTagid()));
			for(int i = 0; i < tagIds.size(); i++){
				SignRel signRel = new SignRel();
				signRel.setAttrid(iid);
				signRel.setSiteId(info.getSiteId());
				signRel.setDimensionid(NumberUtil.getInt(tagIds.get(i)));
				signRel.setModuleid(3);
				signRelService.add(signRel);
			}
			// 写日志,不需回滚
			logService.add(LogConfig.modinfo, LogConfig.opradd, info.getTitle());
			// 清除缓存
			CacheUtil.removeKey(StaticValues.CACHE_REGION, this.findKey(info.getSiteId(), iid));

		} 
		// jsearch
		Col col = colService.findByIid(info.getColId());
		if(NumberUtil.getInt(col.getIsJsearch()) == 1){
			infoOperService.add(info.getSiteId(), info.getColId(), info.getIid(), 1
					, info.getTitle(), info.getUrl());
		}
		if(isSuccess){
			isSuccess = colService.modifyInfonum(info.getColId()+"", 1);
		}
		CacheUtil.removeKey(StaticValues.CACHE_COL, info.getColId()+"");
		findshareInfo(info);
		
		FileUtil.isDirExsit(new File(BaseInfo.getRealPath() + "/webapp/qrcode/"), true);
		String imgPath = BaseInfo.getRealPath() + "/webapp/qrcode/" + info.getIid() + ".png";
		//String url = String.valueOf(info.getIid()) ;
		//String QRCodePath = BaseInfo.getDomain()+"/webapp/qrcode/" + info.getIid() + ".png";
		//生成二维码
		QRCodeUtil.createQRCode(BaseInfo.getDomain()+"/resources/jmp/html/"+info.getIid()+".html", imgPath, null);
		
		return isSuccess;
	}
 
	/**
	 * 对上传的附件进行处理
	 * @param info  信息实体
	 * @return InfoFormBean
	 * @throws OperationException    设定参数 .
	 */
	private InfoFormBean excuteFile(InfoFormBean info) throws OperationException {
		if (info == null) {
			return null;
		}
		int iid = info.getIid();
		if (iid <= 0) {
			return null;
		}
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		int siteId = currentUser.getSiteId();
		// 信息存放相对路径
		String relativePath = "/web/site" + siteId + "/info/" + info.getColId() + "/"
				            + DateUtil.getCurrDate("yyyyMM") + "/" + iid + "/";
		String orignalPicpath = "";
		MultipartFile firstPicFile = info.getFirstPicFile();
		MultipartFile vedioFile = info.getVedioFile();
		MultipartFile audioFile = info.getAudioFile();
		orignalPicpath = relativePath + "info_source";
		orignalPicpath = excuteFile(firstPicFile, orignalPicpath, 
				Configs.getConfigs().getPicFileSize(), Configs.getConfigs().getPicFileType());
		if(!StringUtil.isEmpty(orignalPicpath)){
			info.setFirstPicPath(orignalPicpath);
		}
		// 视频
		orignalPicpath = relativePath + "info_video_" + iid;
		orignalPicpath = excuteFile(vedioFile, orignalPicpath, 
				Configs.getConfigs().getVideoFileSize(),
				Configs.getConfigs().getVideoFileType());
		if(!StringUtil.isEmpty(orignalPicpath)){
			info.setVedio(orignalPicpath);
		}else if(StringUtil.isNotEmpty(info.getVedioPath()) 
				&& info.getVedioPath().startsWith("http")){
			info.setVedio(info.getVedioPath());
		}
		// 音频
		orignalPicpath = relativePath + "info_audio_" + iid;
		orignalPicpath = excuteFile(audioFile, orignalPicpath, 
				Configs.getConfigs().getAudioFileSize(),
				Configs.getConfigs().getAudioFileType());
		if(!StringUtil.isEmpty(orignalPicpath)){
			MusicUtil musicUtil=new MusicUtil();
			String audioTime=musicUtil.findTime(BaseInfo.getRealPath()+"/"+orignalPicpath);
			info.setAudioTime(audioTime);
			info.setAudio(orignalPicpath);
		}
		return info;
	}
	
	/**
	 * 对上传的附件进行处理
	 * @param file
	 * @param path
	 * @param maxSize
	 * @param types
	 * @return
	 * @throws OperationException
	 */
	private String excuteFile(MultipartFile file, String path, Integer maxSize, String types) throws OperationException{
		MultipartFileInfo infofile = MultipartFileInfo.getInstance(file);
		long size = infofile.getSize();
		if (size == 0) {
			return null;
		}
		if(size > (long) maxSize*1024*1024){
			throw new OperationException(infofile.getFileName() + "大于" + maxSize +"M，请重新上传！");
		}
		path += "." + infofile.getFileType();
		types=","+types+",";
		if(types.indexOf(","+infofile.getFileType()+",")<=-1){
			throw new OperationException(infofile.getFileName() + "类型不正确，请重新上传！");
		} 
		if("png".equals(infofile.getFileType()) || "jpg".equals(infofile.getFileType()) 
		        || "jpeg".equals(infofile.getFileType())){ 
			File desFile = new File(BaseInfo.getRealPath() + path);
			// 由于文件目前属于暂存状态，我们需要把文件拷贝到我们需要的位置
			ControllerUtil.writeMultipartFileToFile(desFile, file);
		}else{  
			String distinctPath=fileUtil.getAbsolutePath(path); 
			// 由于文件目前属于暂存状态，我们需要把文件拷贝到我们需要的位置
			fileUtil.writeMultipartFileToFile(distinctPath, file); 
		}
		return path;
	}
	
	/**
	 * 查找用户自己上传的附件
	 * @param content
	 *            信息内容
	 * @return 附件地址集合
	 */
	private ArrayList<String> findJmpFile(String content) {
		String jmpurl = Configs.getConfigs().getJmpUrl();
		PatternUtil patutil = new PatternUtil();
		ArrayList<String> aljmpfile = new ArrayList<String>();
		ArrayList<String> alfile = new ArrayList<String>();
		// 获取内容中的文件附件集合
		alfile = patutil.extracLinksForStr(content, alfile);
		// 去除掉网络图片的文件，只取服务器中存在的文件
		if (alfile != null && alfile.size() > 0) {
			for (int i = 0; i < alfile.size(); i++) {
				if (((String) alfile.get(i)).indexOf(jmpurl) > -1) {
					aljmpfile.add(StringUtil.getString(alfile.get(i)));
				}
			}
		}
		return aljmpfile;
	}

	/**
	 * 更新信息
	 * @param info
	 *            信息实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean modify(InfoFormBean info) throws OperationException {
		if (info == null || info.getIid() <= 0) {
			return false;
		}
		int samenum = this.findNumOfSameInfo(info);
		if (samenum > 0) {
			throw new OperationException("信息标题或者url已存在,请重新设置！");
		}
		colService.modifyUpdateTime(info.getColId());
		boolean isSuccess = infoDAO.update(info, this.getTableName(info.getSiteId()));
		if (!isSuccess) {
			throw new OperationException("信息编辑失败！");
		}
		info = excuteFile(info);
		String content = info.getContent();
		// 将临时目录替换成正式目录
		content = picChangeByCateid(content, info.getSiteId(), info.getColId(), info.getIid(), "");
		info.setContent(content); 
		// 摘要
		String abs = GeneratorABS.generatorAbs(content);
		info.setAbs(abs);
		// 获取在高级编辑器中手动上传的图片
		ArrayList<String> alfile = findJmpFile(info.getContent());
		String firstPath = "";
		// 若没有上传首图，则使用信息中第一张图片作为首图
		if (StringUtil.isEmpty(info.getFirstPicPath())) {
			firstPath = excutefirstfiles(alfile, info.getSiteId(), true, info.getTitle());
			// 原首图地址
			info.setOrignalPicpath(firstPath);
		} else {
			firstPath = excutefirstfiles(info.getFirstPicPath(), info.getSiteId());
			info.setImguuid(UUID.randomUUID().toString().replace("-", ""));
			info.setOrignalPicpath(firstPath);
			// 将高级编辑器图片上传到远程服务器
			String relativePath = "";
			for(String filePath : alfile){
	    		if(filePath == null || filePath.trim().length() == 0){
	    			continue;
	    		}
	   			filePath = filePath.replace(Configs.getConfigs().getJmpUrl(), "");
	    		filePath = BaseInfo.getRealPath() + filePath;
	    		relativePath = filePath.replace(BaseInfo.getRealPath(), "");
		    	if(relativePath.startsWith("/")){
		    		relativePath = relativePath.substring(1);
		    	} 
		    	HadoopUtil.fileUpload(new File(filePath), relativePath);
			}
		}
		
		// 修改信息
		isSuccess = infoDAO.update(info, this.getTableName(info.getSiteId()));
		
		if (isSuccess) {
			// 信息保存至xml
			isSuccess = writeXml(info, alfile);
			
			// 修改信息同时也要修改相关的引用信息
			List<Info> list = infoDAO.findByInfoId(NumberUtil.getInt(info.getIid()), this.getTableName(info.getSiteId()));
	        if(CollectionUtils.isNotEmpty(list)){
	            for(Info quoteInfo : list){
	                // 获取原对象的值
	                int iid = NumberUtil.getInt(quoteInfo.getIid());
	                int colId = NumberUtil.getInt(quoteInfo.getColId());
	                int orderId = NumberUtil.getInt(quoteInfo.getOrderid());
	                int sourceId = NumberUtil.getInt(quoteInfo.getSourceid());
	                String infoType = quoteInfo.getInfoType();
	                // 复制实体对象
	                quoteInfo = (Info)info.clone();
	                // 保留原对象的值
	                quoteInfo.setIid(iid);
	                quoteInfo.setColId(colId);
	                quoteInfo.setOrderid(orderId);
	                quoteInfo.setSourceid(sourceId);
	                quoteInfo.setInfoType(infoType);
	                // 更新相关引用信息实体
	                infoDAO.update(quoteInfo, this.getTableName(quoteInfo.getSiteId()));
	                // 修改对应栏目的信息变动数
	                colService.modifyInfonum(quoteInfo.getColId() + "", 1);
	                // 将数据放入信息操作表
	                Col col = colService.findByIid(quoteInfo.getColId());
	                if(NumberUtil.getInt(col.getIsJsearch()) == 1){
	                    infoOperService.add(quoteInfo.getSiteId(), quoteInfo.getColId(), quoteInfo.getIid(), 3, 
	                            quoteInfo.getTitle(), quoteInfo.getUrl());
	                }
	                // 信息标签关联更新
	                signRelService.deleteByInfoidAndSiteId(quoteInfo.getIid(), quoteInfo.getSiteId(), 3);
	                List<Integer> tagIds = StringUtil.toIntegerList(StringUtil.getString(quoteInfo.getTagid()));
	                for(int i = 0; i < tagIds.size(); i++){
	                    if(NumberUtil.getInt(tagIds.get(i))<=0){
	                        continue;
	                    }
	                    SignRel signRel = new SignRel();
	                    signRel.setAttrid(quoteInfo.getIid());
	                    signRel.setSiteId(quoteInfo.getSiteId());
	                    signRel.setDimensionid(NumberUtil.getInt(tagIds.get(i)));
	                    signRel.setModuleid(3);
	                    signRelService.add(signRel);
	                }
	            }
	        }
		}
		// 信息标签关联更新
		signRelService.deleteByInfoidAndSiteId(info.getIid(), info.getSiteId(), 3);
		List<Integer> tagIds = StringUtil.toIntegerList(StringUtil.getString(info.getTagid()));
		for(int i = 0; i < tagIds.size(); i++){
			if(NumberUtil.getInt(tagIds.get(i))<=0){
				continue;
			}
			SignRel signRel = new SignRel();
			signRel.setAttrid(info.getIid());
			signRel.setSiteId(info.getSiteId());
			signRel.setDimensionid(NumberUtil.getInt(tagIds.get(i)));
			signRel.setModuleid(3);
			signRelService.add(signRel);
		}
		// 写日志,不需回滚
		logService.add(LogConfig.modinfo, LogConfig.oprmodify, info.getTitle());
		// 将数据放入信息操作表
		Col col = colService.findByIid(info.getColId());
		if(NumberUtil.getInt(col.getIsJsearch()) == 1){
			infoOperService.add(info.getSiteId(), info.getColId(), info.getIid(), 3, 
			        info.getTitle(), info.getUrl());
		}
		if(isSuccess){
			isSuccess = colService.modifyInfonum(info.getColId()+"", 1);
		}
		CacheUtil.removeKey(StaticValues.CACHE_COL, info.getColId()+"");
		
		findshareInfo(info);
		
		FileUtil.isDirExsit(new File(BaseInfo.getRealPath() + "/webapp/qrcode/"), true);
		String imgPath = BaseInfo.getRealPath() + "/webapp/qrcode/" + info.getIid() + ".png";
		File imgFile = new File(imgPath);
		if(imgFile.exists()){
			imgFile.delete();
		}
		//String url = String.valueOf(info.getIid()) ;
		//String QRCodePath = BaseInfo.getDomain()+"/webapp/qrcode/" + info.getIid() + ".png";
		//生成二维码
		QRCodeUtil.createQRCode(BaseInfo.getDomain()+"/resources/jmp/html/"+info.getIid()+".html", imgPath, null);
		
		return isSuccess;
	}

	/**
	 * 删除信息
	 * @param ids
	 *            信息ID串 如:1,2,3
	 * @param siteId
	 *            网站id
	 * @param isThread
	 *            isThread
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean removeByIds(String ids, Integer siteId, int isThread) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		List<Info> infoList = infoDAO.findByIds(idList, siteId);
		for(int i = 0; i < infoList.size(); i ++){	
			colService.modifyUpdateTime(infoList.get(i).getColId());
		}
		boolean isSuccess = removeByList(infoList, isThread);
		for(int j=0;j<idList.size();j++){
			String imgPath = BaseInfo.getRealPath() + "/webapp/qrcode/" + idList.get(j) + ".png";
			File imgFile = new File(imgPath);
			if(imgFile.exists()){
				imgFile.delete();
			}
			String fileame = BaseInfo.getRealPath()+"/resources/jmp/html/"+idList.get(j)+".html";
			File file = new File(fileame);
			if(file.exists()){
				file.delete();
			}
		}
		if (!isSuccess) {
			throw new OperationException("删除信息失败！");
		} 
		return isSuccess;
	} 

	/**
	 * 删除信息
	 * @param colId
	 *            栏目id
	 * @param siteId
	 *            网站id
	 * @param isremove isremove          
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean removeByColId(int colId, Integer siteId, Integer isremove) throws OperationException {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		if(!currentUser.getIsWebSiteAdmin()){
			return false;
		} 
		if (colId <= 0) {
			return false;
		} 
		List<Info> infoList = new ArrayList<Info>();
		int count = infoDAO.findCountByCateID(colId, this.getTableName(siteId));
		int times = count / 1000 + 1;
		int pageSize = 1000;
		for (int i = 1; i < times + 1; i++) {
			infoList.addAll(infoDAO.findInfoByColid(pageSize, i, isremove,
					colId, this.getTableName(siteId)));
		} 
		if(CollectionUtils.isEmpty(infoList)){
			return true;	
		}
		colService.modifyUpdateTime(colId);
		boolean isSuccess = true;
		//删除缓存
		if(!CacheUtil.getAllElement(StaticValues.CACHE_REGION).isEmpty()){
			CacheUtil.removeKeyStartsWith(StaticValues.CACHE_REGION, siteId + "_");
		} 
	    isSuccess = removeByList(infoList, 0);
		if (!isSuccess) {
			throw new OperationException("删除信息失败！");
		}
		String name = colService.findByIid(colId).getName();
		logService.add(LogConfig.modinfo, LogConfig.oprclean, name);
		return isSuccess;
	}
	
	/**
	 * 删除信息
	 * @param infoList
	 *            信息集合
	 * @param isThread
	 *            isThread
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean removeByList(List<Info> infoList, int isThread) throws OperationException {
		if (infoList==null || infoList.size()<=0) {
			return false;
		}
		StringBuffer idssb = new StringBuffer(100);
		int i = 0;
		String ids = "";
		boolean isSuccess =false;
		for (Info infoEn : infoList) {
			i = i+1;
			if (infoEn == null || infoEn.getIid() <= 0) {
				continue;
			} 
			idssb.append(",").append(infoEn.getIid()); 
			if(i%100==0 || i==infoList.size()){
				if (StringUtil.isNotEmpty(idssb + "") && idssb.length() > 0) {
					ids = idssb.substring(1);
					List<Integer> idList = StringUtil.toIntegerList(ids, ",");
					if (CollectionUtils.isEmpty(idList)) {
						return false;
					}
					
					//删除信息时查找该信息引用的相关信息
					List<Info> List = infoDAO.findByIdList(idList, this.getTableName(infoEn.getSiteId()));
					if(CollectionUtils.isNotEmpty(List)){
					    for(Info info : List){
					        idList.add(info.getIid());
					    }
					}
					colService.modifyUpdateTime(infoEn.getColId());
					isSuccess = infoDAO.deleteByIds(idList, this.getTableName(infoEn.getSiteId()));
					
					for(int j=0;j<idList.size();j++){
						String imgPath = BaseInfo.getRealPath() + "/webapp/qrcode/" + idList.get(j) + ".png";
						File imgFile = new File(imgPath);
						if(imgFile.exists()){
							imgFile.delete();
						}
						String fileame = BaseInfo.getRealPath()+"/resources/jmp/html/"+idList.get(j)+".html";
						File file = new File(fileame);
						if(file.exists()){
							file.delete();
						}
					}
					
					if (!isSuccess) {
						throw new OperationException("删除信息失败！");
					}
					isSuccess = signRelService.removeByInfoIds(idList);
					if (!isSuccess) {
						throw new OperationException("删除卡片维度下信息失败！");
					}		
					isSuccess = infoOperService.removeByInfoIds(idList);
					if (!isSuccess) {
						throw new OperationException("删除全文检索线程信息失败！");
					}
					commentService.removeByInfoIds(ids, 1, infoEn.getSiteId());
					// 删除信息数据库表
					infoCountService.removeByIds(idList, 1, infoEn.getSiteId());
				}
				idssb = new StringBuffer(100);
			}
		}
		String path = "";
		Info infoEn = null;
		// 信息名称
		StringBuffer names = new StringBuffer(100);
		for (Info info : infoList) {
			if (info == null) {
				continue;
			}
			if (infoEn == null) {
				infoEn = info;
			}
			names.append(",").append(info.getTitle());
			path = info.getPath();
			if (path == null || "".equals(path)) {
				continue;
			}
			path = path.substring(0, path.lastIndexOf("/"));
			if(fileUtil.getImplClazz()!=LocalFileUtil.class){
				if(path.startsWith("/")){
					path = path.substring(1);
				}
				if(!path.endsWith("/")){
					path = path+"/";
				}
				fileUtil.deleteDirectory(fileUtil.getAbsolutePath(path));
			} else {
				FileUtil.deleteDirectory(new File(BaseInfo.getRealPath() + "/" + path));
			}
			// 将数据放入信息操作表
			Col col = colService.findByIid(info.getColId());
			if(NumberUtil.getInt(col.getIsJsearch()) == 1){
				infoOperService.add(info.getSiteId(), info.getColId(), info.getIid()
						, 2, info.getTitle(), info.getUrl());
			}
			// 清除缓存
			String key = this.findKey(info.getSiteId(), info.getIid());
			CacheUtil.removeKey(StaticValues.CACHE_REGION, key);
		}
		// 修改栏目的flag即信息变动标识位
		if (infoEn != null && infoEn.getColId() > 0) {
			isSuccess = colService.modifyFlag(infoEn.getColId() + "");
		}
		String strnames = "";
		if (names != null && names.length() > 0) {
			strnames = names.substring(1);
		}
		if(strnames!=null && strnames.length()>600){
			strnames=strnames.substring(0, 600)+"...";
		}
		// 线程自动清理不需要记录日志
		if(isThread != 1){
			// 写日志,不需回滚
			logService.add(LogConfig.modinfo, LogConfig.oprremove, strnames);
		}
		return isSuccess;
	}

	/**
	 * 审核信息
	 * @param ids
	 *            信息ID串 如:1,2,3
	 * @param siteId
	 *            siteId
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean modifyAudit(String ids, Integer siteId) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		} 
		boolean isSuccess = infoDAO.updateStateByIds(idList, 1, this.getTableName(siteId));
		if (!isSuccess) {
			throw new OperationException("审核信息失败！");
		}
		// 信息名称
		StringBuffer names = new StringBuffer(100);
		if (isSuccess) {
			List<Info> infoList = infoDAO.findByIds(idList, siteId);
			if (infoList == null) {
				return isSuccess;
			}
			Map<Integer, Integer> colids = new HashMap<Integer, Integer>();
			for (Info info : infoList) {
				if (info == null) {
					continue;
				}
				// 将数据放入信息操作表
				Col col = colService.findByIid(info.getColId());
				if(NumberUtil.getInt(col.getIsJsearch()) == 1){
					infoOperService.add(info.getSiteId(), info.getColId(), info.getIid(), 4
							, info.getTitle(), info.getUrl());
				}
				names.append(",").append(info.getTitle()); 
				if (colids.containsKey(info.getColId())) {
					continue;
				}
				if(NumberUtil.getInt(info.getColId())<=0){
					continue;
				}
				colService.modifyUpdateTime(info.getColId());
				// 修改栏目的flag
				colService.modifyFlag(info.getColId()+"");
				colids.put(info.getColId(), info.getColId());
			}
		}
		String strnames = "";
		if (names != null && names.length() > 0) {
			strnames = names.substring(1);
		}
		// 写日志,不需回滚
		logService.add(LogConfig.modinfo, LogConfig.opraudit, strnames);
		return isSuccess;
	}

	/**
	 * 撤审信息
	 * @param ids
	 *            信息ID串 如:1,2,3
	 * @param siteId
	 *            siteId
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean modifyUnAudit(String ids, Integer siteId) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		for(int i = 0; i < idList.size(); i++){		
			colService.modifyUpdateTime(infoDAO.findByIid(idList.get(i), siteId).getColId());
		}
		boolean isSuccess = infoDAO.updateStateByIds(idList, 2, this.getTableName(siteId));
		if (!isSuccess) {
			throw new OperationException("撤审信息失败！");
		}
		// 信息名称
		StringBuffer names = new StringBuffer(100);
		if (isSuccess) {
			List<Info> infoList = infoDAO.findByIds(idList, siteId);
			if (infoList == null) {
				return isSuccess;
			}
			Map<Integer, Integer> colids = new HashMap<Integer, Integer>();
			for (Info info : infoList) {
				if (info == null) {
					continue;
				}
				// 将数据放入信息操作表
				Col col = colService.findByIid(info.getColId());
				if(NumberUtil.getInt(col.getIsJsearch()) == 1){
					infoOperService.add(info.getSiteId(), info.getColId(), info.getIid()
							, 5, info.getTitle(), info.getUrl());
				}
				names.append(",").append(info.getTitle()); 
				if (colids.containsKey(info.getColId())) {
					continue;
				}
				// 修改栏目的flag
				colService.modifyFlag(info.getColId()+"");
				colids.put(info.getColId(), info.getColId());
			}
		}
		String strnames = "";
		if (names != null && names.length() > 0) {
			strnames = names.substring(1);
		}
		// 写日志,不需回滚
		logService.add(LogConfig.modinfo, LogConfig.oprunaudit, strnames);
		return isSuccess;
	} 

	/**
	 * 替换正文中图片地址，并将图片从临时文件夹转移至站点文件夹 
	 * @param content
	 *            信息内容
	 * @param siteid
	 *            网站id
	 * @param cateid
	 *            栏目id
	 * @param infoid
	 *            信息id
	 * @param picPath
	 *            存放路径
	 * @return String
	 */
	private String picChangeByCateid(String content, int siteid, int cateid, int infoid, String picPath) {
		File oldfile;
		File file;
		String jmpurl = Configs.getConfigs().getJmpUrl();
		// 临时图片在服务器中的地址
		String oldimgdirectory = "";
		// 存储到服务器中的地址
		String imgdirectory = "";
		// 系统的物理路径
		String syspath = BaseInfo.getRealPath();
		String oldpath = "/tempfile/";
		// 图片要存储的站点文件夹
		String path = "";
		if ("".equals(picPath)) {
			path = "/web/site" + siteid + "/info/" + cateid + "/" + DateUtil.getCurrDate("yyyyMM")
				 + "/" + infoid + "/";
		} else {
			try {
				path = picPath.substring(0, picPath.lastIndexOf("/") + 1);
			} catch (Exception e) {
				path = "/web/site" + siteid + "/info/" + cateid + "/"
					 + DateUtil.getCurrDate("yyyyMM") + "/" + infoid + "/";
			}
		}
		/**
		 * 将诸如http://192.168.89.151:8080/jmportal/jmportal/info/tmp/这样的路径修改成
		 * http://192.168.89.151:8080/jmportal/web/site9/infopic/930/
		 */
		Pattern p = Pattern.compile("<\\s*(img)[^>]+((src)|(href))\\s*=['\"\\s]*([^\"'\\s>]*)"
				, Pattern.MULTILINE + Pattern.DOTALL + Pattern.CASE_INSENSITIVE); 
		Matcher ma = p.matcher(content); //开始编译 
		while(ma.find()){
			String url = ma.group(5);
			if(url.indexOf("/web/site"+siteid+"/info/" + cateid + "/") != -1){
				continue;
			}	
			int m = url.lastIndexOf("/");
			String filename = url.substring(m + 1);
			int k = filename.lastIndexOf(".");
			if(k>-1){
				String newfilename = filename.substring(0, k) + "_source" + filename.substring(k);
				oldimgdirectory = syspath + oldpath + filename;
				imgdirectory = syspath + path + newfilename;
				if(!url.startsWith(jmpurl) && url.startsWith("http")){
		        	newfilename = downImg(url, syspath+path);
		        	if(newfilename != null && !newfilename.equals(url)){
		        		content = content.replace(url, jmpurl + path + newfilename);
		        	}
			    } else if (url.indexOf(oldpath) != -1){
			        oldfile = new File(oldimgdirectory);
			        file = new File(imgdirectory);
			        FileUtil.copyFile(oldfile, file);
			        content = content.replace(jmpurl + oldpath + filename, jmpurl + path + newfilename);
			    }
			}
		}
		return content;
	}

	/**
	 * 将正文中的附件标记替换成路径
	 * @param infohm
	 *            信息集合
	 * @return 替换后的内容
	 */
	public String analyzeContent(Map<String, String> infohm) {
		String jmpUrl = Configs.getConfigs().getJmpUrl();
		if(fileUtil.getImplClazz()!=LocalFileUtil.class){
			jmpUrl = fileUtil.getURL("");
			if(jmpUrl.endsWith("/")){
				jmpUrl = jmpUrl.substring(0, jmpUrl.length()-1);
			} 
		}
		String content = "";
		if (infohm != null) {
			PatternUtil patutil = new PatternUtil();
			content = infohm.get("content");  
			content = content.replaceAll("\n", "");  //去回车符
			content = content.replaceAll("\t", "");  //去制表符
			content = content.replaceAll("\r", "");  //去换行符
			content = content.replaceAll("<table", "<table style=\'border:1px solid black;border-collapse:collapse;\'");
            content = content.replaceAll("<tr", "<tr style=\'border:1px solid black;\'");
            content = content.replaceAll("<td", "<td style=\'border:1px solid black;\'");
			int filesize = NumberUtil.getInt(infohm.get("filesize"));
			if (filesize > 0) {
				for (int i = 0; i < filesize; i++) {
					String pathValue = (String) infohm.get("file" + (i + 1));
					pathValue = StringUtil.replace(pathValue, "\\", "\\\\");
					content = patutil.replaceStr(content, "\\{file" + (i + 1) + "\\}", jmpUrl
							+ pathValue);
				}
			}
		}
		return content;
	}
	
	/**
	 * 将正文中的附件标记替换成路径(用于离线下载)
	 * @param infohm
	 *            信息集合
	 * @return 替换后的内容
	 */
	public String analyzeOfflineContent(Map<String, String> infohm) {
		String content = "";
		if (infohm != null) { 
			PatternUtil patutil = new PatternUtil();
			content = infohm.get("content");
			int filesize = NumberUtil.getInt(infohm.get("filesize"));
			String fileName="";
			if (filesize > 0) {
				for (int i = 0; i < filesize; i++) {
					String pathValue = (String) infohm.get("file" + (i + 1));
					pathValue = StringUtil.replace(pathValue, "\\", "\\\\");
					fileName=pathValue.substring(pathValue.lastIndexOf("/"));
					if(fileName!=null && fileName.startsWith("/")){
						fileName=fileName.substring(1);
					}
					content = patutil.replaceStr(content, "\\{file" + (i + 1) + "\\}", "./"+fileName);
				}
			}
		}
		return content;
	}
  
	/**
	 * 根据栏目ID获取该栏目下信息的最大i_orderid cateid 栏目id.
	 * @param cateid  cateid
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public int findMaxOrderid(int cateid, int siteId) {
		return infoDAO.findMaxOrderIdByCateID(cateid, this.getTableName(siteId));
	}

	/**
	 * 更新信息id的orderid.
	 * @param ids ids
	 * @param orderids orderids
	 * @param colid colid
	 * @return boolean boolean
	 * @throws OperationException    设定参数 .
	 */
	public boolean modifyOrderIdById(String ids, String orderids, String colid) throws OperationException {
		if (ids == null || ids.length() == 0 || orderids == null || orderids.length() == 0) {
			return false;
		}
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		List<Integer> ordersList = StringUtil.toIntegerList(orderids, ",");
		boolean isSuccess = false;
		colService.modifyUpdateTime(NumberUtil.getInt(colid));
		Col col = colService.findByIid(NumberUtil.getInt(colid));
		for (int i = 0, len = idsList.size(); i < len; i++) {
			isSuccess = infoDAO.updateOrderIdById(ordersList.get(i), idsList.get(i), 
					                                      this.getTableName(col.getSiteId()));
		}
		if(col != null){
			isSuccess = isSuccess && colService.modifyFlag(colid);
		}
		// 记录日志
		if(col != null){
			logService.add(LogConfig.modinfo, LogConfig.oprorder, col.getName());
		}
		return isSuccess;
	}

	/**
	 * 根据栏目id获取信息集合
	 * @param colid
	 *            栏目id
	 * @param siteId
	 *            siteId
	 * @return List<Info>
	 */
	public List<Info> findAllInfoByColid(Integer colid, Integer siteId) {
		return infoDAO.findAllChildInfoByColid(colid, this.getTableName(siteId));
	}

	/**
	 * 根据栏目id获取搜索信息
	 * @param colId 栏目id
	 * @param num   需要多少条 
	 * @param page  第几页
	 * @param keyWord 关键字
	 * @param siteId siteId
	 * @return List<Info>
	 */
	public List<Info> findInfoByColid(Integer colId, int num, int page, String keyWord, int siteId) { 
		List<Info> list = new ArrayList<Info>();
		if(siteId <= 0 || StringUtil.isEmpty(keyWord)){
			return list;
		}
		return infoDAO.findInfoByColid(colId, num, page, keyWord, siteId);
	}
	
	/**
	 * 根据栏目id获取信息
	 * @param colId 栏目id
	 * @param siteId 网站id 
	 * @return List<Info>
	 */
	public List<Info> findInfoByColid(int colId, int siteId) {
		if(siteId <= 0 || colId <= 0){
			return null;
		}
		return infoDAO.findInfoByColid(colId, siteId);
	}
	
	/**
	 * 根据栏目id获取信息
	 * @param colId 栏目id
	 * @param siteId 网站id 
	 * @param isremove isremove 
	 * @return List<Info>
	 */
	public List<Info> findAllOfInfoByColid(int colId, Integer siteId, Integer isremove){
		if(colId <= 0){
			return null;
		}
		List<Info> infoList = new ArrayList<Info>();
		int count = infoDAO.findCountByCateID(colId, this.getTableName(siteId));
		int times = count / 1000 + 1;
		int pageSize = 1000;
		for (int i = 1; i < times + 1; i++) {
			infoList.addAll(infoDAO.findInfoByColid(pageSize, i, isremove, colId, this.getTableName(siteId)));
		} 
		return infoList;
	}
	
	/**
	 * 根据栏目id获取信息
	 * @param colId 栏目id
	 * @param siteId 网站id 
	 * @param isremove isremove 
	 * @param count 信息数量
	 * @return List<Info>
	 */
	public List<Info> findAllOfInfoByColid(int colId, Integer siteId, Integer isremove, int count){
		if(colId <= 0){
			return null;
		}
		List<Info> infoList = infoDAO.findInfoByColid(count, 1, isremove, colId, this.getTableName(siteId)); 
		return infoList;
	}
	
	/**
	 * 根据栏目id获取信息
	 * @param colId 栏目id
	 * @param siteId 网站id 
	 * @param offlineNum offlineNum 
	 * @return List<Info>
	 */
	public List<Info> findInfoByColid(int colId, int siteId, int offlineNum) {
		if(siteId <= 0 || colId <= 0 || offlineNum<=0){
			return null;
		}
		return infoDAO.findInfoByColidAndOfflineNum(colId, siteId, offlineNum, this.getTableName(siteId));
	}
	
	/**
	 * 根据TOPTIME查询信息
	 * @param topTime
	 *            置顶
	 * @param start
	 *            开始条数
	 * @param end
	 *            结束条数
	 * @return 信息集合
	 */
	public List<Info> findByTopTime(String topTime, int start, int end) {
		List<Site> siteList = siteService.findAll();
		List<Info> infoList = new ArrayList<Info>();
		for(Site site : siteList){
			infoList.addAll(infoDAO.findByTopTime(topTime, start, end, this.getTableName(site.getIid())));
		}
		return infoList;
	}

	/**
	 * 获取已经审核的最新信息
	 * @param colid
	 *            栏目id
	 * @return 信息实体
	 */
	public Info findNewInfo(int colid) {
		if (colid <= 0) {
			return null;
		}
		Col col = colService.findByIid(colid);
		if(col == null){
			return null;
		}
		// 查询有图的最新信息，如果信息都没图，返回最新信息
		Info info = infoDAO.findNewInfo(col.getSiteId(), colid, true);
		if (info == null || info.getIid() <= 0) {
			info = infoDAO.findNewInfo(col.getSiteId(), colid, false);
		}
		return info;
	} 
	 
	/**
	 * 信息同步
	 * @param col
	 *            栏目实体
	 * @param starttime
	 *            开始时间
	 * @param endtime
	 *            结束时间
	 * @param infoNum
	 *            信息数量
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException OperationException
	 */
	public boolean synInfo(Col col, String starttime, String endtime, int infoNum, String createtime) throws OperationException {
	    boolean isSuccess = false;
	    if(col!=null){
    		int taskId = NumberUtil.getInt(col.getTaskId());
    		if ((taskId <= 0)) {
    			ColRelation colReEn = colReDAO.findByColid(col.getIid());
    			if (colReEn == null || colReEn.getIid() == 0) {
    				throw new OperationException("栏目下面未绑定同步任务！ "); 
    			}
    			taskId = colReEn.getTaskId();
    		}
    		int successNum=0;
    		SynInfoUtil synDao = new SynInfoUtil();
    		// 栏目信息审核方式： 1：手动 2：立即 3：限时审核
    		int auditType = col.getAuditType();
    		if (infoNum <= 0) {
    			// 根据信息来源类型分别获取信息数量
    			if(col.getSourcetype().equals(0)){
    				infoNum = synDao.getinfoNum(col.getSourcename(), col.getSourcepwd(), "", starttime, endtime, 
    						  taskId, col.getSourceurl(), col, 0); 
    			}else if(col.getSourcetype().equals(1)){
    				infoNum = synDao.getinfoNum(col.getSourcename(), col.getSourcepwd(), "", starttime, endtime, 
    						  taskId, col.getSourceurl(), col, 1);
    			}
    		}
    		if (infoNum <= 0) {
    			throw new OperationException("栏目下获取到的信息数量为0！"); 
    		}
    		// 每次获取50条
    		int synchcount = 50;
    		// 整除为0，有余为1
    		int moreNum = 0;
    		if(infoNum % synchcount > 0){
    			moreNum = 1;
    		}
    		// 信息获取次数
    		int frequency = infoNum / synchcount + moreNum;
    		// 一次同步的开始条数
    		int startPos = 0;
    		// 一次同步的结束条数
    		int endPos = 0;
    		// xml字符串
    		String infoXml = "";
    		XmlFormat xmlUtil = new XmlFormat();
    		List<SynchInfo> infoList = null;
    		List<Map<String, String>> infomaps = null;
    		// 栏目下的信息总数
    		int infoTotalNum = 0;
    		// 循环获取信息xml，信息入库
    		for (int j = 0; j < frequency; j++) {
    			startPos = j * synchcount + 1;
    			endPos = (j + 1) * synchcount;
    			if (endPos > infoNum) {
    				endPos = infoNum;
    			}
    			// 根据不同的来源调用不同的接口，获取xml字符串
    			if(col.getSourcetype().equals(0)){
    				infoXml = synDao.getinfos(col.getSourcename(), col.getSourcepwd(), "", starttime, endtime, 
    						startPos, endPos, taskId, col.getSourceurl(), col, 0);
    			}else if(col.getSourcetype().equals(1)){
    				infoXml = synDao.getinfos(col.getSourcename(), col.getSourcepwd(), "", starttime, endtime, 
    						startPos, endPos, taskId, col.getSourceurl(), col, 1);
    			}
    			// 若获取的infoXml为空，则跳出本次循环
    			if (StringUtil.isEmpty(infoXml)) {
    				continue;
    			}
    			// 解析xml得到信息实体集合
    			infoList = xmlUtil.xmlToInfo(infoXml);
    			if (infoList == null || infoList.size() <= 0) {
    				continue;
    			}
    			// 信息格式转换
    			infomaps = modifyInfo(infoList, col.getSiteId(), col.getIid(), col.getDitchId());
    			if (infomaps == null) {
    				continue;
    			}
    			for (Map<String, String> infohm : infomaps) {
    				infohm.put("siteid", col.getSiteId() + "");
    				infohm.put("colid", col.getIid() + "");
    				infohm.put("coltype", col.getType() + "");
    				infohm.put("colinfotype", col.getInfoListType() + "");
    				// 立即审核
    				if (auditType == 2) {
    					infohm.put("status", "1");
    				} else {
    					infohm.put("status", "0");
    				} 
    				// 保存信息
    				try {
						successNum = addSyninfo(infohm, col, createtime);
					} catch (Exception e) {
						e.printStackTrace();
					} 
    				if(successNum>0){
    					infoTotalNum = infoTotalNum+1;
    				}
    			}
    		} 
    		//栏目信息同步成功后,需要更新栏目的flag字段和网站的pushflag字段
    		if(successNum>=0){
    		    isSuccess = colService.modifyFlag(StringUtil.getString(col.getIid()));   
    		    if(infoTotalNum>0){
    		    	isSuccess = colService.modifyInfonum(StringUtil.getString(col.getIid()), infoTotalNum);  
    		    } 
    		}
    		CacheUtil.removeKey(StaticValues.CACHE_COL, StringUtil.getString(col.getIid()));
	    }
		return isSuccess;
	}
	
	/**
	 * 新增信息
	 * @param infomap
	 *           infomap
	 * @param col
	 *            col
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public int addSyninfo(Map<String, String> infomap, Col col, String createtime) throws Exception {
		boolean isSuccess = false;
		if (infomap == null) {
			return -1;
		}
		//jgetz中的信息id
		String jgetid = infomap.get("jgetid");
		//网站id
		int siteid = NumberUtil.getInt(infomap.get("siteid"));
		//栏目id
		int colid = NumberUtil.getInt(infomap.get("colid"));
		//栏目类型
		int coltype = NumberUtil.getInt(infomap.get("coltype"));
		//栏目信息展现方式
		int colinfotype = NumberUtil.getInt(infomap.get("colinfotype"));
		//信息标题
		String title = StringUtil.getString(infomap.get("title"));
		//同步时间
		String gettime = null;
		String time = null;
		//如果信息源不传，获取当前系统时间；如果传的不是"yyyy-MM-dd HH:mm:ss"这种格式，则认为是yyyy-MM-dd格式，用来源时间+当前系统的HH:mm:ss组成；
		//如果传的是"yyyy-MM-dd HH:mm:ss"这种格式，则用信息源时间；
		if(StringUtil.isEmpty(infomap.get("syntime"))){
			gettime = DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss");
		}else{
			if(StringUtil.getString(infomap.get("syntime")).length() != 19){
				time = StringUtil.getString(infomap.get("syntime")).replace("	", "") + DateUtil.getCurrDate(" HH:mm:ss");
				gettime = TimeUtil.convertTime(time.replaceAll(" ", " "));
			}else{
				gettime = TimeUtil.convertTime(infomap.get("syntime"));
			}
		}
		// 信息状态 A表示新增 U表示更新 D表示删除
		String state = infomap.get("state");
		String url = StringUtil.getString(infomap.get("url"));
		String vediopath = StringUtil.getString(infomap.get("vediopath"));
		String video=StringUtil.getString(infomap.get("video"));
		String content = StringUtil.getString(infomap.get("content"));
		String subtitle = StringUtil.getString(infomap.get("subtitle"));
        String author = StringUtil.getString(infomap.get("author"));
        int filesize = NumberUtil.getInt(infomap.get("filesize"));
        int status = NumberUtil.getInt(infomap.get("status"));
        String source = StringUtil.getString(infomap.get("source"));
        String createDate = StringUtil.getString(infomap.get("c_createdate"));
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if(StringUtil.isNotEmpty(createDate) && StringUtil.isNotEmpty(createtime)){
        	 Date createTime = sdf.parse(createDate);
             Date startDate = sdf.parse(createtime);
             if(createTime.before(startDate)){
             	return 0;
             }
        }
       
		if(StringUtil.getString(source).length() > 80){
			source = source.substring(0, 80);
		}
		source = source.replace("	", "");
		String path = "";
		Info infoEn = new Info();
		// 信息删除
		if ("D".equals(state)) {
			infoEn = infoDAO.findByJgetid(jgetid, siteid, colid);
			if (infoEn != null && infoEn.getIid() > 0) {
				removeByIds(infoEn.getIid() + "", siteid, 1);
				if(NumberUtil.getInt(col.getIsJsearch()) == 1){
					infoOperService.add(infoEn.getSiteId(), infoEn.getColId(), infoEn.getIid(), 2
							, infoEn.getTitle(), infoEn.getUrl());
				}
				return 1;
			} 
			return 0;
		}
		// 标题不能为空
		if (StringUtil.isEmpty(title)) {
			return 0;
		}  
		// 内容和连接不能都为空
		if (StringUtil.isEmpty(content) && StringUtil.isEmpty(url)) {
			return 0;
		}
		//SynInfoUtil synchDao = new SynInfoUtil();
		infoEn.setJgetId(jgetid);
		infoEn.setColId(colid);
		infoEn.setSiteId(siteid);
		// 信息标题
		title = title.replace("/n", "").replace("/r", "");
		title = StringUtil.removeHTML(title);
		// 若标题长度超过85，则标题截取85
		if (title.length() > 85) {
			title = title.substring(0, 85);
		}
		infomap.put("title", title);
		// 信息内容过滤 
		/*content=content.replace("	", "");
		content = synchDao.filterContent(content); 
		// 正文格式处理
		content = repUtil.replaceContent(content);  
		*/
		
		// 信息正文内容过滤
		content = filterContent(content, col.getFilterReg());
		infomap.put("content", content);
		// 摘要
		String abs = GeneratorABS.generatorAbs(content);
		infomap.put("abs", abs);
		infoEn.setAbs(abs);
		// 若信息原文URL长度超过600，则url截取600
		if (url.length() > 600) {
			url = url.substring(0, 600);
		}
		infomap.put("url", url);
		infoEn.setTitle(title);
		infoEn.setUrl(url);
		// 重复信息数
		int samenum = 0;
		// 新增时遇到重复信息直接跳出循环
		if("A".equals(state)){
			samenum = this.findNumOfSameInfo(infoEn);
			if (samenum > 0) {
			   return 0;
		    }
		}
		infoEn.setVedioPath(vediopath);
		infoEn.setVedio(video);
		if(DateUtil.stringtoDate(gettime, DateUtil.YYYY_MM_DD_HH_MM_SS) != null){
			infoEn.setSynTime(DateUtil.stringtoDate(gettime, DateUtil.YYYY_MM_DD_HH_MM_SS));
		}else{
			infoEn.setSynTime(DateUtil.stringtoDate(DateUtil.getCurrDate(DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.YYYY_MM_DD_HH_MM_SS));
		}
		infoEn.setCreateTime(new Date());
		infomap.put("createtime", DateUtil.getCurrDate(DateUtil.YYYY_MM_DD_HH_MM_SS));
		infoEn.setSource(source);
		infoEn.setSubTitle(subtitle);
		infoEn.setAuthor(author);
		infoEn.setStatus(status);
		infoEn.setInfoListType(col.getInfoListType());
		infoEn.setInfoContentType(col.getInfoContentType());
		int orderid = NumberUtil.getInt(infomap.get("orderid"));
		int topid = 0;
		topid = NumberUtil.getInt(infomap.get("topid"));
		if(NumberUtil.getInt(Configs.getConfigs().getJmpcolId())==NumberUtil.getInt(col.getIid())) {
			topid = NumberUtil.getInt(infomap.get("topid"));
			if(topid>0){
				infoEn.setTopId(topid);
			}
			if (orderid == 0 && !"U".equals(state)) {
				orderid = infoDAO.findMinOrderIdByCateID(colid, this.getTableName(siteid)) - 1;
			} 
		} else {
			// 若是新增信息，并且源系统未传orderid，则系统自动生成排序id
			if (orderid == 0 && !"U".equals(state)) {
				orderid = infoDAO.findMinOrderIdByCateID(colid, this.getTableName(siteid)) - 1;
			} 
		}
		infoEn.setOrderid(orderid);
		int iid = 0;
		// 存放扩展字段属性
		Map<String, Object> infoEx = new HashMap<String, Object>();
		for(Map.Entry<String, String> entry : infomap.entrySet()){
			infoEx.put(entry.getKey(), infomap.get(entry.getKey()));
		}
		infoEn.setInfoExpand(infoEx);
		if("U".equals(state)){
		    // 获取信息实体
			Info tempInfo = infoDAO.findByJgetid(jgetid, siteid, colid);
			if(tempInfo != null){
				iid = tempInfo.getIid();
				if(orderid == 0){
					// 修改时，源系统未传orderid，则系统获取之前的排序id
					orderid = NumberUtil.getInt(tempInfo.getOrderid());
					// 若之前的排序id是0，则重新生成排序id
					if(orderid == 0){
						orderid = infoDAO.findMinOrderIdByCateID(colid, this.getTableName(siteid)) - 1;
					}
				}
				if(StringUtil.isNotEmpty(tempInfo.getFirstPicPath())){
					infoEn.setFirstPicPath(tempInfo.getFirstPicPath());
				}
				infoEn.setOrderid(orderid);
				infoEn.setTopId(topid);
			} else {
			    if(orderid == 0){
			        orderid = infoDAO.findMinOrderIdByCateID(colid, this.getTableName(siteid)) - 1;
			    }
			    infoEn.setOrderid(orderid);
			    infoEn.setTopId(topid);
				iid = infoDAO.insert(infoEn, this.getTableName(siteid));
				if(iid > 0){
					InfoCount infoCount = new InfoCount();
					infoCount.setTitleId(iid);
					infoCount.setType(1);
					infoCountDAO.insert(infoCount, "jmp_info_count" + siteid);
				}
			}
		} else {
		    infoEn.setTopId(topid);
			iid = infoDAO.insert(infoEn, this.getTableName(siteid));
			if(iid > 0){
				InfoCount infoCount = new InfoCount();
				infoCount.setTitleId(iid);
				infoCount.setType(1);
				infoCountDAO.insert(infoCount, "jmp_info_count" + siteid);
			}
		}
		if (iid <= 0) {
			return 0;
		}
		infoEn.setIid(iid);
		infomap.put("iid", iid + "");
		infomap.put("infoid", iid + "");
		if (filesize > 0) {
			// 处理附件
			if(col.getSourcetype().equals(0) && NumberUtil.getInt(Configs.getConfigs().getJmpcolId())!= colid){
				infomap = excutefiles(infomap);
			}else{
				infomap = excuteJcmsfiles(infomap);
			}
		}
		// 原图
		String origpicpath = StringUtil.getString(infomap.get("origpicpath"));
		// 信息缩略图
		if (!"".equals(origpicpath)) {
			infoEn.setOrignalPicpath(origpicpath);
		}
		path = "/web/site"+siteid+"/info/" + colid + "/" + DateUtil.getCurrDate("yyyyMM") + "/" + iid + "/1.xml";
		// 保存数据库
		infoEn.setPath(path);
		if (coltype == 2 && colinfotype == 1) {
			infoEn.setVedioPath("");
			infomap.put("vedio", ""); // 上面放的是video，普通信息的，和这个没关系
			infomap.put("vedio1", infomap.get("video"));
			infomap.put("audio", "");
		}
		// 保存至信息xml文件
		if (coltype == 1 && colinfotype == 1) {
			infomap.put("infotype", "2");
		} else {
			infomap.put("infotype", "1");
		}
		infomap.put("path", path);
		// 更新信息
		isSuccess = infoDAO.updateInfo(infoEn);
		// 保存xml文件至本地
		if (isSuccess) {
			isSuccess = writeInfoXML(infomap, siteid);
		}
		// 将数据放入信息操作表
		if(NumberUtil.getInt(col.getIsJsearch()) == 1){
			if(StringUtil.equals("A", state)){
				infoOperService.add(infoEn.getSiteId(), infoEn.getColId(), iid, 1, infoEn.getTitle(), infoEn.getUrl());
			}else if(StringUtil.equals("U", state)){
				infoOperService.add(infoEn.getSiteId(), infoEn.getColId(), iid, 3, infoEn.getTitle(), infoEn.getUrl());
			}
		}
		
		findshareInfo(infoEn);
		
		FileUtil.isDirExsit(new File(BaseInfo.getRealPath() + "/webapp/qrcode/"), true);
		String imgPath = BaseInfo.getRealPath() + "/webapp/qrcode/" + infoEn.getIid() + ".png";
		//生成二维码
		QRCodeUtil.createQRCode(BaseInfo.getDomain()+"/resources/jmp/html/"+infoEn.getIid()+".html", imgPath, null);
		
		return 1;
	}
	
	/**
	 * 生成模板
	 */
	public void findshareInfo(Info info){ 
		String htmlname = "";
		htmlname = "shareinfo.html";
		String	realpath = BaseInfo.getRealPath()+"/resources/jmp/html/"+htmlname;
		File htmlFile = new File(realpath);
		String modelhtml = FileUtil.readFileToString(htmlFile);
		String showsource = "";
		if(StringUtil.isNotEmpty(info.getUrl())){
			showsource = "block";
		}else{
			showsource = "none"; 
		}
		//模板解析
		modelhtml = modelhtml.replace("{title}", StringUtil.getString(info.getTitle()))
		                     .replace("{content}", StringUtil.getString(info.getContent()))
			                 .replace("{jmpurl}", BaseInfo.getDomain())
			                 .replace("{colname}", StringUtil.getString(info.getSource()))
			                 .replace("{showsource}", StringUtil.getString(showsource))
			                 .replace("{url}", StringUtil.getString(info.getUrl()))
			                 .replace("{synshowtime}", DateUtil.dateToString(new Date(), DateUtil.YYYY_MM_DD)) ;
		String fileame = info.getIid() + ".html";
		fileame = BaseInfo.getRealPath()+"/resources/jmp/html/"+fileame;// 生成的html文件保存路径。
		
		
		
		File htmlFile1 = new File(fileame);
		if(htmlFile1.exists()){
			htmlFile1.delete();
		}
		 try {
			FileOutputStream fileoutputstream = new FileOutputStream(fileame);// 建立文件输出流
			byte tag_bytes[] = modelhtml.getBytes();
	          fileoutputstream.write(tag_bytes);
	          fileoutputstream.close();
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
  
	// 根据栏目设置过滤规则过滤信息正文
	private String filterContent(String content, String filterReg) {
        if(StringUtil.isEmpty(filterReg)){
            return content;
        }
        // 若只有一个，也拼上","
        filterReg = filterReg + ",";
        
        // 按","分割成数组
        String[] regArray = filterReg.split(",");
        
        // 全部过滤
        if(regArray.length == 18){
            // 过滤所有html标签
            content = patutil.replaceStr(content,  "<script[^<]*</script>",  ""); 
            content = patutil.replaceStr(content,  "<style[^<]*</style>",  ""); 
            content = patutil.replaceStr(content,  "<[^>]+>",  ""); 
            content = patutil.replaceStr(content,  "&nbsp;",  ""); 
            content = content.replaceAll(" ","");/*.replaceAll(" " , "").replaceAll("  ","")*//*去重特殊空格*/
            // 过滤图片
            content = patutil.replaceStr(content,  "<img.*>",  "");         
            content = patutil.replaceStr(content,  "</img>",  "");
            
        // 部分过滤
        } else {
            String[] tabarr = {"table", "tr", "td", "thead", "tbody", "th", "tfoot", "caption", "col", "colgroup"}; //表格元素
            for(int i=0; i<regArray.length; i++){
                if(regArray[i].equals("链接")){
                    content = patutil.replaceStr(content, "<(?i)"+ "a" +".*?>",  ""); 
                    content = patutil.replaceStr(content, "</(?i)"+ "a" +".*?>",  "");
                } else if(regArray[i].equals("换行")){
                    content = patutil.replaceStr(content, "<(?i)"+ "br" +".*?>",  ""); 
                    content = patutil.replaceStr(content, "</(?i)"+ "br" +".*?>",  "");
                } else if(regArray[i].equals("段落")){
                    content = patutil.replaceStr(content, "<(?i)"+ "p" +".*?>",  ""); 
                    content = patutil.replaceStr(content, "</(?i)"+ "p" +".*?>",  "");
                } else if(regArray[i].equals("字体大小")){
                    content = patutil.replaceStr(content, "<(?i)"+ "h" +".*?>",  ""); 
                    content = patutil.replaceStr(content, "</(?i)"+ "h" +".*?>",  "");
                } else if(regArray[i].equals("粗体")){
                    content = patutil.replaceStr(content,  "<b\\s.*?>",  ""); 
                    content = patutil.replaceStr(content,  "<b>",  ""); 
                    content = patutil.replaceStr(content,  "</b>",  ""); 
                } else if(regArray[i].equals("nbsp")){
                    content = patutil.replaceStr(content,  "&nbsp;",  ""); 
                    content = content.replaceAll(" ","");  /*.replaceAll(" " , "").replaceAll("  ","")*//*过滤特殊空格*/
                } else if(regArray[i].equals("script")){
                    content = patutil.replaceStr(content, "<script[^>]*?>[\\s\\S]*?<\\/script>",  "");
                } else if(regArray[i].equals("style")){
                    content = patutil.replaceStr(content, "<style[^<]*</style>",  ""); 
                    content = patutil.replaceStr(content, "style=(\"(.*?)\"|'(.*?)'|([^>]*?)\\s|(.*?)([^>]*)?)", "");
                    content = patutil.replaceStr(content, "class=(\"(.*?)\"|'(.*?)'|([^>]*?)\\s|(.*?)([^>]*)?)", "");
                } else if(regArray[i].equals("表格")){
                    for(int j=0; j<tabarr.length; j++){
                        content = patutil.replaceStr(content, "<(?i)"+ tabarr[j] +".*?>",  ""); 
                        content = patutil.replaceStr(content, "</(?i)"+ tabarr[j] +".*?>",  ""); 
                    }
                } else {
                    content = patutil.replaceStr(content, "<(?i)"+ regArray[i] +".*?>",  ""); 
                    content = patutil.replaceStr(content, "</(?i)"+ regArray[i] +".*?>",  "");
                }
            }
        }
        return content;
    }
	
    /**
	 * 更新置顶时间
	 * @param toptime 置顶时间
	 * @param infoids 信息id
	 * @param colid 栏目id
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean modifyToptime(String toptime, String infoids, int colid) throws OperationException {
		boolean isSuccess = false;
		if (colid == 0 || StringUtil.isEmpty(infoids)) {
			return isSuccess;
		}
		Col col = colService.findByIid(colid);
		String[] ids = StringUtil.split(infoids, ",");
		int maxTopId = 0;
		if(col != null){
		    maxTopId = infoDAO.findMaxTopIdByCateID(colid, this.getTableName(col.getSiteId()));
		}
		int topid = 0;
		int infoid = 0;
		// 对信息批次置顶，新信息在老信息上面
		for (int i = ids.length - 1; i >= 0; i--) {
			topid = ++maxTopId;
			infoid = NumberUtil.getInt(ids[i]);
			isSuccess = infoDAO.updateTopime(topid, toptime, infoid+"", this.getTableName(col.getSiteId()));
		}
		if(col != null){
			isSuccess = isSuccess && colService.modifyFlag(colid + "");
		}
		return isSuccess;
	}
  
	/**
	 * 更新置顶时间
	 * @param toptime  置顶时间
	 * @param infoid 信息id
	 * @param topid topid
	 * @param siteId siteId
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean modifyToptime(String toptime, String infoids, int topid, Integer siteId) throws OperationException {
		boolean issuccess = false;
		if (StringUtil.isEmpty(infoids)) {
			return issuccess;
		}
		issuccess = infoDAO.updateTopime(topid, toptime, infoids, this.getTableName(siteId));
		return issuccess;
	}

	/**
	 * 处理附件文件.
	 * @param infohm
	 *            infohm
	 * @return Map<String, String>.
	 */
	private Map<String, String> excutefiles(Map<String, String> infohm) {
		StrUtil strutil = new StrUtil();
		SynInfoUtil synDao = new SynInfoUtil();
		int siteid = NumberUtil.getInt(infohm.get("siteid"));
		int colid = NumberUtil.getInt(infohm.get("colid"));
		int iid = NumberUtil.getInt(infohm.get("iid"));
		Col col = colService.findByIid(colid);
		// 图片存放相对路径
		String imgrelatepath = "/web/site" + siteid + "/info/" + colid + "/"
				             + DateUtil.getCurrDate("yyyyMM") + "/" + iid + "/";
		String infodirectory = BaseInfo.getRealPath() + "/" + imgrelatepath;
		int filesize = NumberUtil.getInt((infohm.get("filesize") + ""));
		String imgformat = ""; // 图片后缀名
		String filepath = ""; // 图片路径
		String serialnub = ""; // 图片序号
		List<String> fileList = new ArrayList<String>();
		if(col!=null){
    		for (int i = 1; i <= filesize; i++) {
    			filepath = StringUtil.getString(infohm.get("file" + i));
    			if ("".equals(filepath)) {
    				continue;
    			}
    			// 图片时间，格式：yyyyMMddHHmmssSSS
    			String imgtime = DateUtil.getCurrDate("yyyyMMddHHmmssSSS");
    			if (filepath.indexOf(".") > 0) {
    				imgformat = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());
    			}
    			// 图片序号
    			serialnub = strutil.getNumber(i + "", 4);
    			// 原图地址
    			String sorceimgname = imgtime + "_" + serialnub + "_source." + imgformat;
    			// 取附件，返回值类型为DataHandler
    			HashMap<String, DataHandler> filesmap = synDao.getfile(col.getSourcename(), col.getSourcepwd(), "", filepath, 2, col.getSourceurl());
    			if (filesmap != null && filesmap.size() > 0) {
    				// 保存附件,处理前的图片名称为temp加规定图片名称
    				synDao.saveFile(filesmap, sorceimgname, infodirectory);
    				infohm.put("file" + i, imgrelatepath + sorceimgname);
    				fileList.add(imgrelatepath + sorceimgname);
    			}
    		}
		}
		String firstPath = excutefirstfiles(fileList, siteid, true, StringUtil.getString(infohm.get("title")));
		infohm.put("origpicpath", firstPath);
		return infohm;
	}

	/**
	 * 处理附件文件
	 * @param infohm
	 *            信息集合
	 * @return 设定参数 .
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> excuteJcmsfiles(Map infohm) {
		StrUtil strutil = new StrUtil();
		SynInfoUtil synDao = new SynInfoUtil();
		int siteid = NumberUtil.getInt(infohm.get("siteid"));
		int colid = NumberUtil.getInt(infohm.get("colid"));
		int iid = NumberUtil.getInt(infohm.get("iid"));
		Col col = colService.findByIid(colid);
		// String path;
		// 图片存放相对路径
		String imgrelatepath = "/web/site" + siteid + "/info/" + colid + "/"
				             + DateUtil.getCurrDate("yyyyMM") + "/" + iid + "/";
		String infodirectory = BaseInfo.getRealPath() + "/" + imgrelatepath;
		int filesize = NumberUtil.getInt((infohm.get("filesize") + ""));
		String imgformat = ""; // 图片后缀名
		String filepath = ""; // 图片路径
		String serialnub = ""; // 图片序号
		List<String> fileList = new ArrayList<String>();
		String content = StringUtil.getString(infohm.get("content"));
		if(filesize>0){
			List<SynchFile> synfiles=(List<SynchFile>) infohm.get("filelist");
			for(int i=0; i<synfiles.size(); i++){
				SynchFile sychEn = synfiles.get(i);
				filepath = sychEn.getPath();
				if ("".equals(filepath)) {
					continue;
				}
				// 图片时间，格式：yyyyMMddHHmmssSSS
				String imgtime = DateUtil.getCurrDate("yyyyMMddHHmmssSSS");
				if (filepath.indexOf(".") > 0) {
					imgformat = filepath.substring(
							filepath.lastIndexOf(".") + 1, filepath.length());
				}
				// 图片序号
				serialnub = strutil.getNumber(i + "", 4);
				// 原图地址
				String sorceimgname = imgtime + "_" + serialnub + "_source." + imgformat;
				if(col!=null){
    				// 取附件，返回值类型为DataHandler
    				HashMap<String, DataHandler> filesmap = synDao.getfile(col.getSourcename(), 
    				        col.getSourcepwd(), "", filepath, 2, col.getSourceurl());
    				if (filesmap != null && filesmap.size() > 0) {
    					// 保存附件,处理前的图片名称为temp加规定图片名称
    					synDao.saveFile(filesmap, sorceimgname, infodirectory);
    					content = content.replace("{"+sychEn.getName()+"}", "{"+"file" + (i+1)+"}");
    					infohm.put("file" + (i+1), imgrelatepath + sorceimgname);
    					infohm.put("content", content);
    					fileList.add(imgrelatepath + sorceimgname);
    				}
				}
			}
		} 
		String firstPath = excutefirstfiles(fileList, siteid, true, StringUtil.getString(infohm.get("title")));
		infohm.put("origpicpath", firstPath);
		return infohm;
	}

	/**
	 * 查询栏目下面信息数量
	 * @param colId
	 *            栏目ID
	 * @param siteId  siteId          
	 * @return 信息数量
	 */
	public int findCountByCateID(int colId, Integer siteId) {
		return infoDAO.findCountByCateID(colId, this.getTableName(siteId));
	}

	/**
	 * 根据栏目id获取信息
	 * @param colid
	 *            栏目id
	 * @param start
	 *            开始条数
	 * @param end
	 *            结束条数
	 * @param siteId 
	 * @return 信息集合
	 */
	public List<Info> findByColid(int colid, int start, int end, int siteId) {
		List<Info> allInfo = new ArrayList<Info>();
		//若信息条数间隔大于1000，则分段获取
		if(end-start>1000){
			int num = 0;
			int startInfo = 0;
			int endInfo = 0;
			int temp = 500;
			int lists = 0;
			List<Info> infolist = null;
			do {
				startInfo = num * temp+start;
				endInfo = temp * (num + 1)+start;
				infolist = infoDAO.findByColid(colid, startInfo, endInfo, this.getTableName(siteId));
				num++;
				allInfo.addAll(infolist);
				lists = infolist.size();
			} while (lists == temp);  
		} else {
			allInfo = infoDAO.findByColid(colid, start, end, this.getTableName(siteId));
		}
		return allInfo;
	}

	/**
	 * 根据栏目id获取信息list（用于首页接口）
	 * @param colid     栏目id
	 * @param tableName 查询表名
	 * @return
	 */
	public List<Info> findInfoListByColid(int colId, int siteId){
		List<Info> InfoList = new ArrayList<Info>();
		InfoList = infoDAO.findInfoListByColid(colId, this.getTableName(siteId));
		return InfoList;
	}
	
	/**
	 * 获取某一时间点之前的信息
	 * @param colid   栏目id
	 * @param endtime   结束时间
	 * @param siteId    网站id
	 * @return 信息集合
	 */
	public List<Info> findByEndtime(int colid, String endtime, Integer siteId) {
		List<Info> allInfo = new ArrayList<Info>();
		int allCount = infoDAO.findByEndtimeCount(colid, endtime, this.getTableName(siteId));
		//若信息条数间隔大于1000，则分段获取
		if(allCount>1000){
			int num = 0;
			int startInfo = 0;
			int endInfo = 0;
			int temp = 500;
			int lists = 0;
			List<Info> infolist = null;
			do {
				startInfo = num * temp;
				endInfo = temp * (num + 1);
				infolist = infoDAO.findByEndtime(colid, endtime, startInfo, endInfo, this.getTableName(siteId));
				num++;
				allInfo.addAll(infolist);
				lists = infolist.size();
			} while (lists == temp);  
		} else {
			allInfo = infoDAO.findByEndtime(colid, endtime, 0, 0, this.getTableName(siteId));
		}
		return allInfo;
	} 
	
	/**
	 * 下载图片
	 * @param url url
	 * @param path path
	 * @return    设定参数 .
	 */
	private String downImg(String url, String path){
		if(!path.endsWith("/")){
			path = path + "/";
		}
		File dir = new File(path);
		boolean isSuccess = true;
		if(dir!=null && !dir.exists()){
			isSuccess = dir.mkdirs();
		}
		if(!isSuccess){
			return null;
		}
		//有带参数的情况需将？号后的参数去掉
		String sufix = HttpClientUtil.getSufix(url);
		if(sufix == null || sufix.trim().length() == 0){
			return url;
		}
		if(StaticValues.IMG_TYPE.indexOf(sufix) == -1){
			return url;
		}
		String newFileName =  DateUtil.getCurrDate("yyyyMMddHHmmssSSS") + "_source." + sufix;
		boolean bl = HttpClientUtil.downloadFile(url, path + newFileName);
		if(bl){
			return newFileName;
		}
		return null;
	}
	
    /**
     * 信息字段名称转换，将jget的字段名称修改为微门户的字段名称
     * @param syninfos 信息集合
     * @param siteId   网站id
     * @return 转换以后的信息集合
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, String>> modifyInfo(List<SynchInfo> syninfos, Integer siteId, Integer colid, Integer ditchId){
        if(syninfos == null){
    		return null;
    	}
        // 获取渠道实体
    	Ditch ditch = ditchService.findByIid(ditchId);
    	if(ditch == null){
    	    return null;
    	}
    	List<Map<String, String>> infos = new ArrayList<Map<String, String>>();
    	Map infoMap = null;
    	List<SynchField> fields = null;
    	List<SynchFile> synfiles = null;
    	// 获取同步字段集合
    	Map<String, String> bindFields = synFieldService.findSynField(siteId, ditch.getIid());
    	if(bindFields == null){ 
    		XmlFormat xmlUtil = new XmlFormat(); 
    		bindFields = xmlUtil.findBindField();
    		StaticValues.setBindFields(bindFields);
    	}
    	String fieldName = ""; 
    	String[] fieldnames = null;
    	SynchField synfield = null;
    	SynchFile synfile = null;
    	// 循环遍历信息
    	for(SynchInfo syninfoEn : syninfos){
    		if(syninfoEn == null){
    			continue;
    		}
    		infoMap = new HashMap<String, String>();
    		infoMap.put("jgetid", syninfoEn.getId());
    		infoMap.put("state", syninfoEn.getState()); 
    		if("D".equals(syninfoEn.getState())){
    			infos.add(infoMap);
    			continue;
    		}
    		fields = syninfoEn.getFiled();
    		if(fields == null || fields.size() == 0){
    			continue;
    		} 
    		if(fieldnames == null){
    			fieldnames = StringUtil.split(syninfoEn.getFileds(), ",");	
    		}
    		if(fieldnames.length!=fields.size()){
    			continue;
    		}
    		for(int i=0; i<fields.size(); i++){
    			synfield = fields.get(i); 
    			if(synfield == null){
    				continue;
    			}
    			fieldName = StringUtil.getString(synfield.getName());
    			if(NumberUtil.getInt(Configs.getConfigs().getJmpcolId()) == colid){
    				if("信息标题".equals(fieldName)){
    					infoMap.put("title", StringUtil.getString(synfield.getContent()));
    				}
    				if("信息内容".equals(fieldName)) {
    					infoMap.put("content", StringUtil.getString(synfield.getContent()));
    				}
    				if("信息创建时间".equals(fieldName)) {
    					infoMap.put("syntime", StringUtil.getString(synfield.getContent()));
    				}
    			}
    			if(fieldName.equals("排序id")){
    				infoMap.put("orderid", StringUtil.getString(synfield.getContent()));
    			} else if (fieldName.equals("置顶")){
    				infoMap.put("topid", StringUtil.getString(synfield.getContent()));
    			} else {
	    			//对比某一渠道对应的同步字段
	    			if(bindFields.containsKey(fieldnames[i])){
	    				fieldName = StringUtil.getString(bindFields.get(fieldnames[i]));
	    			}  
	    			infoMap.put(fieldName, StringUtil.getString(synfield.getContent()));
    			}
    		}
    		synfiles = syninfoEn.getFiles();
    		if(synfiles == null || synfiles.size()==0){
    			infoMap.put("filesize", "0");
    			infos.add(infoMap);
    			continue;
    		}
    		infoMap.put("filesize", synfiles.size()+"");
    		//循环遍历附件
    		for(int i=0; i<synfiles.size(); i++){
    			synfile = synfiles.get(i); 
    			if(synfile == null){
    				continue;
    			} 
    			infoMap.put(synfile.getName(), synfile.getPath());
    		}
    		Col col = colService.findByIid(colid);
    		if(NumberUtil.getInt(col.getSourcetype()) == 1 || NumberUtil.getInt(Configs.getConfigs().getJmpcolId()) == colid){
    			infoMap.put("filelist", synfiles);
    		}
    		infos.add(infoMap);
    	}
    	return infos;
    }
    
    /**
     * 查询排序id
     * @param colEn colEn
     * @param orderId orderId
     * @param topId orderId
     * @param start start
     * @param end end
     * @param siteId siteId
     * @return    设定参数 .
     */
	public List<Info> findGTOrderid(Col colEn, Integer orderId, Integer topId, int start, int end, Integer siteId, Integer isbanner) {
		topId = NumberUtil.getInt(topId);
		return infoDAO.findGTOrderid(0, colEn, orderId, topId, start, end, this.getTableName(siteId),  isbanner);
	}
    
    /**
     * 查询排序id
     * @param colEn colEn
     * @param orderId orderId
     * @param topId topId
     * @param start start
     * @param end end
     * @param siteId siteId
     * @return    设定参数 .
     */
	public List<Info> findLTOrderid(Col colEn, Integer orderId, Integer topId, int start, int end, Integer siteId, Integer isbanner) {
		return infoDAO.findGTOrderid(1, colEn, orderId, topId, start, end, this.getTableName(siteId), isbanner);
	}
    
	/**
	 * 获取信息时间
	 * @param colEn colEn
	 * @param time time
	 * @param topId topId
	 * @param start start
	 * @param end end
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public List<Info> findGTTime(Col colEn, Date time, Integer topId, int start, int end, Integer siteId, Integer isbanner) {
		return infoDAO.findGTTime(0, colEn, time, topId, start, end, this.getTableName(siteId),  isbanner);
	}
	
	/**
	 * 获取时间
	 * @param colEn colEn
	 * @param time time
	 * @param topId topId
	 * @param start start
	 * @param end end
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public List<Info> findLTTime(Col colEn, Date time, Integer topId, int start, int end, Integer siteId, Integer isbanner) {
		return infoDAO.findGTTime(1, colEn, time, topId, start, end, this.getTableName(siteId), isbanner);
	}
    
	/**
	 * 获取信息推送时间
	 * @param time time
	 * @param start start
	 * @param end end
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public List<Info> findPushInfoGTtime(Date time, int start, int end, int siteId) {
		return infoDAO.findPushInfoGTtime(0, time, start, end, siteId);
	}

	/**
	 * 获取信息推送时间
	 * @param time time
	 * @param start time
	 * @param end endend
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public List<Info> findPushInfoLTtime(Date time, int start, int end, int siteId){
		return infoDAO.findPushInfoGTtime(1, time, start, end, siteId);
	}
    
    /**
     * 写入xml
     * @param info  信息实体
     * @param alfile   集合
     * @return boolean
     */
    public boolean writeXml(Info info, ArrayList<String> alfile){
		int filesize = 0;
		if(alfile != null){
			filesize = alfile.size();
		}
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("infoid", info.getIid() + "");
		hm.put("filesize", filesize+"");
		String content = info.getContent();
		String str = "";
		//把内容中的图片替换成{file1}这种格式
		if (alfile != null && alfile.size() > 0) {
			for (int i = 0; i < alfile.size(); i++) {
				str = (String) alfile.get(i);
				content = content.replace(str, "{file"
				+ (i + 1) + "}");
				hm.put("file" + (i + 1), str.replace(Configs.getConfigs().getJmpUrl(), ""));
			}
		}
		hm.put("content", content);
		hm.put("path", info.getPath());
	    return writeInfoXML(hm, info.getSiteId()); 
	} 
    
    /**
     * 保存信息文件   
     * @param hm   map
     * @param siteId   网站id
     * @return  boolean
     */
    public boolean writeInfoXML(Map<String, String> hm, int siteId){
        try{
            if(hm==null){ 
                return false;
            }
            XmlUtil xmlutil = new XmlUtil("");
            /** 建立document对象 */
            Document document = DocumentHelper.createDocument();
            /** 建立XML文档的根节点 */
            Element root = document.addElement("root");
            xmlutil.setChildNode(hm, root, "infoid", false); //设置信息ID
            xmlutil.setChildNode(hm, root, "content", true); //设置内容
            int fileSize = NumberUtil.getInt(hm.get("filesize")+""); //设置图片附件
            Element files = root.addElement("files");      
            files.addAttribute("size",  fileSize+"");
            String pathValue="";
            for(int i=0; i<fileSize; i++){
                pathValue = StringUtil.getString(hm.get("file"+(i+1)));       //文件路径
                xmlutil.setAttrNode(files, "file"+(i+1), "", "path", pathValue, true);
            }
            String filePath = StringUtil.getString(hm.get("path"));
            if(!"".equals(filePath)){
            	filePath = BaseInfo.getRealPath() + filePath.substring(0, filePath.lastIndexOf("/"))+"/";
            	File file = new File(filePath);
        	    if (!file.exists()){
        	    	FileUtil.deleteFile(file); 
                } 
            } 
            filePath = hm.get("path");
            xmlutil.createXmlFile(document, BaseInfo.getRealPath()+"/"+filePath); 
            HadoopUtil.fileUpload(new File(BaseInfo.getRealPath()+"/"+filePath), filePath);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
	/**
	 * 首图处理方法
	 * @param firstpath 首图地址（相对路径）
	 * @param stieId    网站ID
	 * @return          缩略图地址
	 */
	public String excutefirstfiles(String firstpath, int stieId){ 
		List<String> picList = new ArrayList<String>();
		picList.add(firstpath);
		return excutefirstfiles(picList, stieId, false, "");
	}
	
	/**
	 * 首图处理方法
	 * @param fileList  附件数组
	 * @param stieId    网站ID
	 * @param ope       false:手动上传的图不做处理 true:从信息正文取图要处理
	 * @param title     标题
	 * @return      String    缩略图地址
	 */
    public String excutefirstfiles(List<String> fileList, int stieId, boolean ope, String title){
    	String imgformat = "";
    	StringBuffer firstPath = new StringBuffer(); 
    	boolean fileiserror = false;
    	for(String filePath : fileList){
    		if(filePath == null || filePath.trim().length() == 0){
    			continue;
    		}
   			filePath = filePath.replace(Configs.getConfigs().getJmpUrl(), "");
    		filePath = BaseInfo.getRealPath() + filePath;
            if(filePath.indexOf("?") != -1){
            	imgformat = filePath.substring(0, filePath.lastIndexOf("?"));
            }
            imgformat = imgformat.substring(imgformat.lastIndexOf(".") + 1);
            if(StaticValues.IMG_TYPE.indexOf(imgformat.toLowerCase()) == -1){
            	continue;
            } 
        	//对原图进行压缩
        	ImageUtil.scaleFirstMiniature(stieId, filePath, filePath);
            if(ope && !ImageUtil.isWidthForNetwork(filePath)){
            	fileiserror=true;
            	continue;
            }
            if(StringUtil.isNotEmpty(firstPath.toString())){
            	firstPath.append(",");
            }
            firstPath.append(filePath);
    	}
    	if(StringUtil.isNotEmpty(title) && fileiserror){
    		logger.debug("信息"+title+"的图片附件不符合要求");
    	} 
        return firstPath.toString().replaceAll(BaseInfo.getRealPath(), "");
    }

    /**
     * 取信息XML详情
     * @param filepath   信息xml文件路径
     * @return .
     */
    public Map<String, String> findByPath(String filepath){   
        Map<String, String> hm = new HashMap<String, String>(); 
        try{
            if(filepath==null || filepath.equals("")){
                return null;
            }
            File file = new File(filepath);
            if(fileUtil.getImplClazz()!=LocalFileUtil.class){
            	if(filepath.indexOf(BaseInfo.getRealPath())>=0){
            		filepath = filepath.replace(BaseInfo.getRealPath(), "");
                }
            	if(filepath.startsWith("/")){
            		filepath = filepath.substring(1);
            	}
            	file = new File(BaseInfo.getRealPath()+"/"+filepath);
            	fileUtil.copyFile(fileUtil.getAbsolutePath(filepath), file);
            } 
            if(!file.exists()){
                return null;
            }
            if(fileUtil.getImplClazz()!=LocalFileUtil.class){
            	filepath = BaseInfo.getRealPath()+"/"+filepath;
            }
            XmlDocument xml = new XmlDocument(); 
            xml.read(new File(filepath));  
            hm.put("infoid", xml.getXmlNode("root/infoid").getContent()); 
            hm.put("content", xml.getXmlNode("root/content").getContent()); 
            /* 取得所有图片附件 */
            int fileSize = NumberUtil.getInt(xml.getXmlNode("root/files").getAttr("size"));
            hm.put("filesize", fileSize+"");
            if(fileSize > 0){
            	String filePath ="";
            	for(int i=0; i<fileSize; i++){
                    filePath = xml.getXmlNode("/root/files/file"+(i+1)).getAttr("path");
                    //文件路径
                    hm.put("file"+(i+1), filePath);
                }
            }
            if(fileUtil.getImplClazz()!=LocalFileUtil.class){
            	FileUtil.deleteFile(file);
            }
            return hm;
        }catch(Exception e){
        	e.printStackTrace();
            return null;
        }
    }

	/**
	 * 查找内容
	 * @param path 路径
	 * @return   String
	 */
	public String findContent(String path) {
		Map<String, String> hm = findByPath(path);
		String content = analyzeContent(hm);  
		return content;
	}
	
	/**
	 * 查找内容(用于离线下载)
	 * @param path 路径
	 * @return   String
	 */
	public String findOffineContent(String path) {
		Map<String, String> hm = findByPath(path);
		String content = analyzeOfflineContent(hm);  
		return content;
	}
	
	/**
	 * 查找需要推送的信息
	 * @param now 当前时间
	 * @return 信息list
	 */
	public List<Info> findInfoToPush(Date now){
		int siteId = UserSessionInfo.getCurrentUser().getSiteId();
		return infoDAO.findInfoToPush(now, this.getTableName(siteId));
	}
	
	/**
	 * 修改信息推送状态为已推送
	 * @param ids       信息id串
	 * @param siteid    网站id
	 * @return boolean boolean
	 */
	public boolean modifyPushState(List<Integer> ids, Integer siteid){
		boolean bl = true;
        if(NumberUtil.getInt(siteid)>0){
        	bl = infoDAO.updatePushState(ids, this.getTableName(siteid));
        }
		return bl;
	}
	
	/**
	 * 修改信息待推送
	 * @param info  信息实体
	 * @return  boolean boolean
	 */
	public boolean updatePushState(Info info){
		PushInfoLog pushInfoLog = new PushInfoLog();
		pushInfoLog.setInfoId(info.getIid());
		pushInfoLog.setSiteId(info.getSiteId());
		pushInfoLog.setUserId(UserSessionInfo.getCurrentUser().getIid());
		pushInfoLog.setAndroidStatus(1);
		pushInfoLog.setIosStatus(1);
		pushInfoLog.setIpadStatus(1);
		pushInfoLogService.add(pushInfoLog);
		//记录日志
		logService.add(LogConfig.modpush, LogConfig.oprpush, info.getTitle());
		return infoDAO.updatePushState(info);
	}
	
	/**
	 * 查找指定维度及栏目下的信息实体（栏目下卡片内信息展现）
	 * @param
	 * @param colId 栏目id
	 * @param page    页数
	 * @param siteId  网站id
	 * @return   List<Info>
	 */
	public List<Info> findCardInfoByColIdAndSignId(int dimensionid, int colId, int page, int siteId){
		if(dimensionid <= 0 || colId <= 0){
			return null;
		}
		return infoDAO.findCardInfoByColIdAndSignId(dimensionid, 
				colId, page, this.getTableName(siteId));
	}
	
	/**
	 * 增加信息表
	 * @param tableName 表名
	 * @return boolean
	 */
	public boolean addTable(String tableName){
		return infoDAO.addTable(tableName);
	}
	
	/**
	 * 删除信息表
	 * @param tableName 表名
	 * @return  boolean
	 */
	public boolean deleteTable(String tableName){
		return infoDAO.deleteTable(tableName);
	}
	
	/**
	 * 获得不同网站的信息表名
	 * @param iid 网站Id
	 * @return  String
	 */
	public String getTableName(Integer iid){
		String tableName = "jmp_info" + iid;
		return tableName;
	}
	
	/**
	 * 获取缓存的key
	 * @param iid      信息id
	 * @param siteId   网站id
	 * @return  String
	 */
	public String findKey(int siteId, int iid){
		return  siteId + "_" + iid ;
 	}
	
	/**
	 * 更新信息实体
	 * @param info   信息实体
	 * @return  boolean
	 */
	public boolean updateInfo(Info info){
		return infoDAO.updateInfo(info);
	}
	
	/**
	 * 按照时间降序排序
	 * @param siteId
	 * @param colid
	 * @return
	 */
	public boolean update(int siteId,int colid){
		return infoDAO.update1(siteId,colid);
	}
	
	/**
	 * 更新信息展现样式
	 * @param infoListType     信息列表展现样式
	 * @param infoContentType  信息内容展现样式
	 * @param infoId           信息id
	 * @param siteId           网站id
	 * @return  boolean boolean
	 */
	public boolean updateColInfoType(int infoListType, int infoContentType, int colid, int siteId){ 
		return infoDAO.updateColInfoType(infoListType, infoContentType, colid, this.getTableName(siteId));
	}
	
	/**
	 * 更新信息删除状态.
	 * @param state 信息删除状态
	 * @param ids 信息id串
	 * @param siteId 网站id
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean modifyIsRemove(int state, String ids, int siteId)throws OperationException{
		boolean isSuccess = false;
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		List<Info> infoList = infoDAO.findByIds(idList, siteId);
		isSuccess = infoDAO.updateIsRemove(state, idList, this.getTableName(siteId));
		if (!isSuccess) {
			throw new OperationException("更新信息删除状态失败！");
		}
		if(state == 1){
			for(int i = 0; i < infoList.size(); i ++){	
				colService.modifyUpdateTime(infoList.get(i).getColId());
				// 修改栏目的flag即信息变动标识位
				if (infoList.get(i) != null && infoList.get(i).getColId() > 0) {
					isSuccess = colService.modifyFlag(infoList.get(i).getColId() + "");
				}
				// 写日志,不需回滚
				logService.add(LogConfig.modinfo, LogConfig.oprremove, "信息");
				//清除缓存
				String key = this.findKey(infoList.get(i).getSiteId(), infoList.get(i).getIid());
				CacheUtil.removeKey(StaticValues.CACHE_REGION, key);
			}
			//删除全文检索线程信息
			isSuccess = infoOperService.removeByInfoIds(idList);
			if (!isSuccess) {
				throw new OperationException("删除全文检索线程信息失败！");
			}
		}else{
			for(Info info : infoList){
				isSuccess = colService.modifyFlag(info.getColId()+"");
			}		
		}		
		return isSuccess;
	}
	
	/**
	 * 根据栏目Id更新信息删除状态
	 * @param state     信息删除状态
	 * @param colId     栏目id
	 * @param siteId    网站id
	 * @return  boolean
	 */
	public boolean modifyIsRemoveByColId(int state, int colId, int siteId){
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		if(!currentUser.getIsWebSiteAdmin()){
			return false;
		} 
		boolean isSuccess = false;
		isSuccess = infoDAO.updateIsRemoveByColId(state, colId, this.getTableName(siteId));
		if(state == 1){
			//删除缓存
			if(!CacheUtil.getAllElement(StaticValues.CACHE_REGION).isEmpty()){
				CacheUtil.removeKeyStartsWith(StaticValues.CACHE_REGION, siteId + "_");
			}
			//修改栏目flag
			try {
			    isSuccess = colService.modifyFlag(StringUtil.getString(colId));
			} catch (OperationException e) {
				e.printStackTrace();
			}
			String name = colService.findByIid(colId).getName();
			logService.add(LogConfig.modinfo, LogConfig.oprclean, name);
		}	
		return isSuccess;
	}
	
	/**
	 * 获取信息的json
	 * 
	 * @param titleid       信息id
	 * @param siteid        网站 id
	 * @param clienttype    客户端类型
	 * @return  String
	 */	
	public String findContentJson(Integer titleid, Integer siteid, Integer clienttype, Integer type){
		Info info = this.findByIid(titleid, siteid);
		Map<String, Object> result = new HashMap<String, Object>();
		if(info == null){ 
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_INFOCONTENT, 
					InterfaceLogConfig.ERROR_07); 
		}
		Col col = colService.findByIid(info.getColId());
		if(col!=null){
			result.put("iscomment", StringUtil.getString(col.getIsComment()));
		}else{
			result.put("iscomment", "0");
		}
		result.put("titleid", StringUtil.getString(info.getIid()));
		result.put("titletext", info.getTitle());
		result.put("titletext", info.getTitle());
		result.put("titlesubtext", info.getAbs());
		result.put("summary", info.getSummary());
		if(info.getSynTime()==null){
			result.put("time", StringUtil.getString(new Date().getTime()));
		}else{
			result.put("time", StringUtil.getString(info.getSynTime().getTime()));
		}
		result.put("source", info.getSource());
		result.put("author", StringUtil.getString(info.getAuthor()));
		String infoPath=StringUtil.getString(info.getPath());
		if(infoPath.startsWith("/")){
			infoPath=infoPath.substring(1);
		}
		infoPath=fileUtil.getAbsolutePath(infoPath);
		//手机接口
		if(NumberUtil.getInt(type)==0){
			result.put("titlecontent", this.findContent(infoPath));
		//离线下载
		}else{
			result.put("titlecontent", this.findOffineContent(infoPath));
		}
		result.put("url", info.getUrl());
		result.put("subtitle", info.getSubTitle());
		result.put("topid", StringUtil.getString(info.getTopId()));
		result.put("orderid", StringUtil.getString(info.getOrderid()));
		result.put("downurl", Configs.getConfigs().getJmpUrl()+"/client/"+siteid+"_"+titleid);
		result.put("poitype", StringUtil.getString(info.getPointType()));
		result.put("poilocation", info.getPointLocation());
		result.put("address", info.getAddress());
		result.put("audiotime", StringUtil.getString(info.getAudioTime()));
		result.put("audiourl", StringUtil.getString(info.getAudio()));
		//自定义字段
		Map<String, Object> infoExpand=info.getInfoExpand();
		if(infoExpand != null){
			for(Map.Entry<String, Object> entry:infoExpand.entrySet()){
				result.put(entry.getKey(), StringUtil.getString(infoExpand.get(entry.getKey())));
			}
		}
		return JsonUtil.objectToString(result);
	}
	
	/**
	 * 组织频道新增树
	 * @param siteId 网站id
	 * @return String 栏目树的信息
	 */
	public String findAddTagTree(int siteId){
		List<Sign> signList;
		signList = signService.findBySiteId(siteId, 3);
		//组织树
		Tree tree = Tree.getInstance(); 
		for (Sign dimension : signList) {
			tree.addNode(TreeNode.getInstance("" + dimension.getIid(), "0"
					, dimension.getDname())
					.setOpen(false));
		}
		return tree.parse();
	}
	
	/**
	 * 组织频道修改树
	 * @param siteId 网站id
	 * @param infoId infoId
	 * @return String 栏目树信息
	 */
	public String findModifyTagTree(Integer siteId, Info info){
		List<Sign> signList;
		String checkedTagIds = "," + info.getTagid() + ",";
		signList = signService.findBySiteId(siteId, 3);
		//组织树
		Tree tree = Tree.getInstance(); 
		TreeNode treeNode;
		for (Sign dimension : signList) {
			treeNode = TreeNode.getInstance("" + dimension.getIid(), "root", dimension.getDname()).setOpen(false);
			if(NumberUtil.getInt(info.getTagid())>0 && checkedTagIds.indexOf("," + dimension.getIid() + ",") > -1){
				treeNode.setChecked(true);
			}
			tree.addNode(treeNode);
		}
		return tree.parse();
	}
	
	/**
	 * 组织频道新增树
	 * @param siteId 网站id
	 * @return String 栏目树的信息
	 */
	public String findAddColTree(Integer siteId, Integer colId){
		List<Col> colList;
		colList = colService.findBySiteIdAndType(siteId);
		//组织树
		Tree tree = Tree.getInstance();
		tree.addNode(TreeNode.getInstance("0",  null, "选择栏目", false).setNocheck(true));
		for (Col col : colList) {
			if(NumberUtil.getInt(col.getIid()) != NumberUtil.getInt(colId)){
				if(col.getType() != 3){
					if(col.getType() == 1){
						tree.addNode(TreeNode.getInstance("" + col.getIid(), 
								""+NumberUtil.getInt(col.getPid()), col.getName())
								.setOpen(true).setIsDisabled(true));
					}else{
						tree.addNode(TreeNode.getInstance("" + col.getIid(), 
								""+NumberUtil.getInt(col.getPid()), col.getName())
								.setOpen(false));
					}
				}
			}
		}
		return tree.parse();
	}
	
	/**
	 * 信息引用转移方法
	 * @param ids 信息id串
	 * @param colId 栏目id
	 * @param siteId 网站id
	 * @param flag 引用转移标识位  0:引用      1:转移
	 * @return true/false
	 * @throws OperationException 
	 */
	public boolean addInfoToCol(String ids, String colIds, Integer siteId, String flag) throws OperationException{
	    List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		List<Integer> colList = StringUtil.toIntegerList(colIds, ",");
		if (CollectionUtils.isEmpty(colList)) {
            return false;
        }
		List<Col> targetList = colDAO.findByIds(colList);
		boolean success = false ;
		String relativePath = "";
		List<Info> infoList = infoDAO.findByIds(idList, siteId);
		int iid = 0 ;
		if(CollectionUtils.isNotEmpty(targetList)){
		    // 判重处理
		    for(Col col : targetList){
		        for(Info info : infoList){
	                int num = infoDAO.findNumByColIdAndTitleAndUrl(NumberUtil.getInt(col.getIid()), info.getTitle(), info.getUrl(), NumberUtil.getInt(info.getSiteId()));
	                if (num > 0) {
	                    throw new OperationException("信息标题为【"+info.getTitle()+"】的标题名或者url已存在,请重新设置！");
	                }
	            }
		    }
            // 插入数据
            int orderId = 0;
            for(Col col : targetList){
                for(Info info : infoList){
                    Info newInfo = (Info)info.clone();
                    int oldId = info.getIid();
                    newInfo.setColId(NumberUtil.getInt(col.getIid()));
                    newInfo.setSourceid(oldId);
                    if("C".equals(flag)){
                        newInfo.setInfoType("C");
                    } else if("Q".equals(flag)){
                        newInfo.setInfoType("Q");
                    }
                    orderId = NumberUtil.getInt(infoDAO.findMinOrderIdByCateID(NumberUtil.getInt(col.getIid()), this.getTableName(siteId)));
                    newInfo.setOrderid(orderId - 1);
                    iid = infoDAO.insert(newInfo, this.getTableName(siteId));
                    success = iid > 0;
                    newInfo.setIid(iid);
//                    relativePath = "/web/site" + siteId + "/info/" + col.getIid() + "/"
//                                 + DateUtil.getCurrDate("yyyyMM") + "/" + iid + "/";
//                    File oldFile = new File(BaseInfo.getRealPath() + info.getPath().replace("1.xml", ""));
//                    File newFile = new File(BaseInfo.getRealPath() + relativePath);
//                    FileUtil.copyDirectory(oldFile, newFile);
//                    // 替换xml文件中的信息id
//                    File xmlFile = new File(BaseInfo.getRealPath()+relativePath + "1.xml");
//                    String xml = FileUtil.readFileToString(xmlFile)
//                                 .replace("<infoid>" + oldId + "</infoid>", "<infoid>" + iid + "</infoid>")
//                                 .replace(info.getPath().replace("1.xml", ""), relativePath);
//                    FileUtil.writeStringToFile(xmlFile, xml);
//                    if(StringUtil.isNotEmpty(info.getPath())) {
//                        newInfo.setPath(relativePath + "1.xml");
//                    }
//                    if(StringUtil.isNotEmpty(info.getVedio())) {
//                        newInfo.setVedio(relativePath + info.getVedio().substring(info.getVedio().lastIndexOf("/")+1));
//                    }
//                    if(StringUtil.isNotEmpty(info.getFirstPicPath())){
//                        newInfo.setFirstPicPath(relativePath + info.getFirstPicPath().substring(info.getFirstPicPath().lastIndexOf("/")+1));
//                        newInfo.setOrignalPicpath(relativePath + info.getFirstPicPath().substring(info.getFirstPicPath().lastIndexOf("/")+1));
//                    } else {
//                        String orignalPicpath = StringUtil.getString(info.getOrignalPicpath());
//                        if(StringUtil.isNotEmpty(orignalPicpath)) {
//                            String[] paths = StringUtil.split(orignalPicpath, ",");
//                            String picPath = "";
//                            String newPath = "";
//                            for(int j=0; j<paths.length; j++){
//                                picPath = StringUtil.getString(paths[j]);
//                                newPath += ("," + relativePath + picPath.substring(picPath.lastIndexOf("/") + 1, picPath.length()));
//                            }
//                            newPath = newPath.substring(1);
//                            newInfo.setOrignalPicpath(newPath);
//                        }
//                    }
                    
                    // 更新信息
                    success = infoDAO.update(newInfo, this.getTableName(siteId));
                    
                    // 新增信息阅读记录
                    InfoCount infoCount = new InfoCount();
                    infoCount.setTitleId(iid);
                    infoCount.setType(1);
                    infoCountDAO.insert(infoCount, "jmp_info_count" + siteId);
                    
                    // 信息标签关联表入库
                    List<Integer> tagIds = StringUtil.toIntegerList(StringUtil.getString(info.getTagid()));
                    for(int i = 0; i < tagIds.size(); i++){
                        SignRel signRel = new SignRel();
                        signRel.setAttrid(iid);
                        signRel.setSiteId(siteId);
                        signRel.setDimensionid(NumberUtil.getInt(tagIds.get(i)));
                        signRel.setModuleid(3);
                        signRelService.add(signRel);
                    }
                }
            }
            // 若为信息转移,则删除原栏目下信息  
            if("T".equals(flag)) {
                infoList = infoDAO.findByIds(idList, siteId);
                for(int i = 0; i < infoList.size(); i ++){  
                    StringUtil.removeHTML(ids);
                    colService.modifyUpdateTime(infoList.get(i).getColId());
                }
                try {
                    success = removeByList(infoList, 0);
                } catch (OperationException e) {
                    return false;
                }
            }
		}
		return success;
	}
	
	/**
	 * 根据时间倒序来排序
	 * @param colId
	 * @param siteId
	 * @return
	 */
	public List<Info> descByTime(int colId, int siteId){
		return infoDAO.descByTime(colId,siteId);
	}
	
	/**
	 * 根据排序id来倒排
	 * @param colId
	 * @param siteId
	 * @return
	 */
	public List<Info> descByOrderId(int colId, int siteId){
		return infoDAO.descByOrderId(colId,siteId);
	}

	/**
	 * 获取自定义字段
	 * 
	 * @param siteId	站点id
	 * @return
	 */
	public List<String> getCustomField(Integer siteId) {
		return infoDAO.getCustomField(siteId);
	}
	
	public Info findByInfoId(Integer iid, String tableName){
		if(iid == null || tableName == null){
			return null;
		}
		return infoDAO.findByInfoId(iid, tableName);
	}
}