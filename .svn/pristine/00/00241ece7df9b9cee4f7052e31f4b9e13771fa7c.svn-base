package com.hanweb.jmp.cms.service.cols; 

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.sf.ehcache.CacheException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.reg.LicenceCheck;
import com.hanweb.common.task.TaskManager;
import com.hanweb.common.task.TaskScheduleBuilder;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.ZipUtil;
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.common.util.file.LocalFileUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.MultipartFileInfo;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.complat.dao.RoleRelationDAO;
import com.hanweb.complat.entity.RoleRelation;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.cms.controller.cols.ColFormBean;
import com.hanweb.jmp.cms.dao.channels.ChannelColDAO;
import com.hanweb.jmp.cms.dao.channels.ChannelDAO;
import com.hanweb.jmp.cms.dao.cols.ColDAO;
import com.hanweb.jmp.cms.dao.cols.ColQuoteRelationDAO;
import com.hanweb.jmp.cms.dao.cols.ColRelationDAO;
import com.hanweb.jmp.cms.entity.channels.ChannelCol;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.entity.cols.ColQuoteRelation;
import com.hanweb.jmp.cms.entity.cols.ColRelation;
import com.hanweb.jmp.cms.service.infos.InfoService;
import com.hanweb.jmp.cms.service.sign.SignRelService;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.LogConfig;
import com.hanweb.jmp.constant.StaticValues;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.dao.role.RoleColDAO;
import com.hanweb.jmp.sys.entity.role.RoleCol;
import com.hanweb.jmp.sys.service.log.LogService;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.jmp.task.SynchInfoTask;
import com.hanweb.jmp.util.CacheUtil;
import com.hanweb.jmp.util.HadoopUtil;
import com.hanweb.jmp.util.XmlUtil;
import com.hanweb.support.controller.CurrentUser;

public class ColService {

	/**
	 * 日志
	 */
	private final Log logger = LogFactory.getLog(getClass());  
	
	/**
	 * colDAO
	 */
	@Autowired
	private ColDAO colDAO;

	/**
	 * colreDAO
	 */
	@Autowired
	private ColRelationDAO colreDAO;
	
	/**
	 * colQuoteRelDAO
	 */
	@Autowired
	private ColQuoteRelationDAO colQuoteRelDAO;

	/**
	 * channelColDAO
	 */
	@Autowired
	private ChannelColDAO channelColDAO;

	/**
	 * logService
	 */
	@Autowired
	private LogService logService;

	/**
	 * channelDAO
	 */
	@Autowired
	private ChannelDAO channelDAO;

	/**
	 * roleColDAO
	 */
	@Autowired
	private RoleColDAO roleColDAO;

	/**
	 * siteService
	 */
	@Autowired
	private SiteService siteService;
	
	/**
	 * signRelService
	 */
	@Autowired
	private SignRelService signRelService;
	
	/**
	 * infoService
	 */
	@Autowired
	private InfoService infoService;
	
	/**
	 * colQuoteRelService
	 */
	@Autowired
	private ColQuoteRelationService colQuoteRelService;
	
	/**
	 * 文件集合
	 */
	private ArrayList<File> fileArr = null;

	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;
	
	@Autowired
	private RoleRelationDAO roleRelationDAO;
	
	/**
	 * 通过栏目ID获得子栏目集合（树）
	 * @param iid
	 *            栏目ID
	 * @param siteId
	 *            网站Id
	 * @return List<Col>
	 */
	public List<Col> findChildColByIid(Integer iid, Integer siteId) {
		return colDAO.findChildColByIid(iid, siteId);
	}
	
	/**
	 * 查找所有类目
	 * @param iid
	 * @param siteId
	 * @return
	 */
	public List<Col> findColTreeByType(Integer iid, Integer siteId) {
        return colDAO.findColTreeByType(iid, siteId);
    }
 
	/**
	 * 通过栏目ID和栏目类别获得子栏目集合（树）.
	 * @param iid 栏目ID
	 * @param siteId 网站id
	 * @param typeIds 栏目类型
	 * @return    设定参数 .
	*/
	public List<Col> findChildColByIidAndType(Integer iid, Integer siteId, String typeIds) {
		List<Integer> typeList = StringUtil.toIntegerList(typeIds, ",");
		if (CollectionUtils.isEmpty(typeList)) {
			return null;
		}
		return colDAO.findChildColByIidAndType(iid, siteId, typeList);
	}

	/**
	 * 新增栏目
	 * @param col
	 *            栏目实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean add(ColFormBean col) throws OperationException {
		
		if (col == null) {
			return false;
		}
		//检查上传文件类型
		checkFileType(col);
		int num = this.findNumOfSameName(0, col.getName(), col.getPid(), col.getSiteId());
		if (num > 0) {
			throw new OperationException("栏目名称已存在,请重新设置！");
		}
		// 注册文件栏目最大数判断
//		boolean checkLicence = this.checkLicenceCol(col.getSiteId());
//		if (checkLicence) {
//			throw new OperationException("栏目数已超最大限制！");
//		}
		//栏目层级
		int collevel=1; 
		if(NumberUtil.getInt(col.getPid()) > 0){
			Col parcol = findByIid(col.getPid());
			if(parcol != null && parcol.getIid() > 0){
				collevel = parcol.getCollevel() + 1;
			} 
		}
		col.setCollevel(collevel);
		//上传背景图以及栏目图标及首图
		col = excuteDefaultPic(col);
		col.setEnable(1);
		col.setCreateTime(new Date());
		String orderId = colDAO.getStrMaxKey(Tables.COL, "orderid");
		col.setOrderId(NumberUtil.getInt(orderId));
		col.setBookorderId(NumberUtil.getInt(orderId));
		//栏目不为互动栏目，将互动类型重置
		if(col.getType() != 3){
			col.setHdType(0);
		}
		//栏目类型不为类目，将栏目平铺方式重置
		if(col.getType() != 1){
			col.setColListType(0);
		}
		int iid = colDAO.insert(col);
		siteService.modifyColFlag(col.getSiteId());
		boolean isSuccess = false;
		if (iid > 0) {
			// 更新附件路径
			col.setIid(iid);
			String oldPath = BaseInfo.getRealPath() + "/web/site" + col.getSiteId() + "/col/temp" + col.getUserId();
			String newPath = oldPath.replace("temp" + col.getUserId(), iid + "");
			File oldFile = new File(oldPath);
			if (oldFile.exists()) {
				isSuccess = oldFile.renameTo(new File(newPath));
			}
			isSuccess = this.modifyPath(col);
			if (isSuccess) {
				if (NumberUtil.getInt(col.getTaskId()) != 0) {
					// 插入栏目任务关联表
					ColRelation colRe = new ColRelation();
					colRe.setColId(iid);
					colRe.setColName(col.getName());
					colRe.setTaskId(col.getTaskId());
					colRe.setTaskName(col.getTaskName());
					Integer reIid = colreDAO.insert(colRe);
					if (reIid > 0) {
						isSuccess = true;
					}
				} 
			}
			
			// 栏目管理权限用户创建栏目时,所创建的栏目也应具有管理权限
			CurrentUser currentUser  = UserSessionInfo.getCurrentUser();
			Set<String> colRightids = currentUser.getColids();
			if(CollectionUtils.isNotEmpty(colRightids)){
			    List<RoleRelation> roleRelationList = roleRelationDAO.findByUserId(currentUser.getIid());
			    if(CollectionUtils.isNotEmpty(roleRelationList)){
			        for(RoleRelation roleRelation : roleRelationList){
			            RoleCol roleCole = new RoleCol();
			            roleCole.setRoleId(roleRelation.getRoleId());
			            roleCole.setColId(iid);
			            roleCole.setSiteId(currentUser.getSiteId());
			            roleCole.setIsedit(0);
			            roleCole.setIsaudit(0);
			            roleCole.setIsColManage(1);
			            roleColDAO.insert(roleCole);
			            colRightids.add("manage-" + iid);
			            colRightids.add(StringUtil.getString(iid));
			        }
			    }
			}
			
		}
		// 若栏目是启用状态，需要启用任务
		if (NumberUtil.getInt(col.getEnable()) == 1) {
			Col colTask = colDAO.findTaskColByIid(col.getIid());
			startTask(colTask);
		}
		// 写日志
		logService.add(LogConfig.modcol, LogConfig.opradd, col.getName());
		return isSuccess;
	}

    /**
	 * 导入新增栏目.
	 *
	 * @param col 实体
	 * @return    设定参数 .
	*/
	public int addCol(Col col) {
		if (col == null) {
			return 0;
		}
		// 注册文件栏目最大数判断
		boolean checkLicence = this.checkLicenceCol(col.getSiteId());
		if (checkLicence) {
			return 0;
		}
		int num = this.findNumOfSameName(0, col.getName(), col.getPid(), col.getSiteId());
		if (num > 0) {// 如果栏目重名则重新命名
			col.setName(col.getName() + "【重" + StringUtil.getUUIDString() + "】");
		}
		col.setEnable(1);
		col.setCreateTime(new Date());
		String orderId = colDAO.getStrMaxKey(Tables.COL, "orderid");
		col.setOrderId(NumberUtil.getInt(orderId));
		col.setBookorderId(NumberUtil.getInt(orderId));
		int iid = colDAO.insert(col);
		if (iid > 0) {
			col.setIid(iid);
			boolean isSuccess = this.modifyPath(col);
			if (isSuccess) {
				return iid;
			}
		}
		// 写日志
		logService.add(LogConfig.modcol, LogConfig.opradd, col.getName());
		return iid;
	}

	/**
	 * 根据文件获取文件名称
	 * @param file
	 *            文件
	 * @param fileName
	 *            文件名称
	 * @return 文件路径
	 */
	private String findFilePath(MultipartFile file, String fileName) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		Integer userId = currentUser.getIid();
		String path = BaseInfo.getRealPath() + "/web/site" + siteId + "/col/temp" + userId + "/";
		// 文件
		MultipartFileInfo fileInfo = MultipartFileInfo.getInstance(file);
		// 由于文件目前属于暂存状态，我们需要把文件拷贝到我们需要的位置
		String newFileName = fileName + "." + fileInfo.getFileType();
		File desFile = new File(path + newFileName);
		// 开始拷贝
		ControllerUtil.writeMultipartFileToFile(desFile, file);
		return desFile.getAbsolutePath();
	}

	/**
	 * 更新路径方法
	 * @param col
	 *            栏目实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean modifyPath(Col col) {
		if (col == null || col.getIid() <= 0) {
			return false;
		}
		int colid = col.getIid();
		String newPath = "/web/site" + col.getSiteId() + "/col/" + colid + "/"; 
		String imgformat = "";
		String appimgformat = "";
		//订阅图标
		if (!StringUtil.isEmpty(col.getIconPath())) {
			imgformat = col.getIconPath().substring(col.getIconPath().lastIndexOf(".")+1);
			appimgformat = col.getAppIconPath().substring(col.getAppIconPath().lastIndexOf(".")+1);
			String iconPath = newPath + StaticValues.ICONPIC_NAME + "." + imgformat;
			String appIconPath = newPath + StaticValues.APPICONPIC_NAME + "." + appimgformat;
			//分布式部署需要将本地文件剪切到云环境
			if(new File(BaseInfo.getRealPath() + iconPath).exists()) {
				File file = new File(BaseInfo.getRealPath() + iconPath);
				HadoopUtil.fileUpload(file, iconPath);
			}
			if(new File(BaseInfo.getRealPath() + appIconPath).exists()) {
                File file = new File(BaseInfo.getRealPath() + appIconPath);
                HadoopUtil.fileUpload(file, appIconPath);
            }
			col.setIconPath(iconPath);
			col.setAppIconPath(appIconPath);
		}
		// xml
		boolean bl = colDAO.updatePath(col);
		// 保存栏目到缓存
		if (bl) {
			CacheUtil.removeKey(StaticValues.CACHE_COL, "" + colid);
		}
		return bl;
	}

	/**
	 * 通过栏目ID、栏目名称及父栏目ID获得同名任务名称的个数
	 * @param id
	 *            栏目ID
	 * @param name
	 *            栏目名称
	 * @param pId
	 *            父栏目ID
	 * @param siteId
	 *            网站id
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findNumOfSameName(Integer id, String name, Integer pId, Integer siteId) {
		if (StringUtil.isEmpty(name)) {
			return 0;
		}
		int num = colDAO.findNumOfSameName(id, name, pId, siteId);
		return num;
	}

	/**
	 * 获取子栏目数
	 * @param colid
	 *            栏目id
	 * @return 子栏目数量
	 */
	public int findSubCol(int colid) {
		if (NumberUtil.getInt(colid) <= 0) {
			return 0;
		}
		int num = colDAO.findSubCol(colid);
		return num;
	}

	/**
	 * 修改栏目
	 * @param col
	 *            栏目实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean modify(ColFormBean col) throws OperationException {
		if (col == null || NumberUtil.getInt(col.getIid()) == 0) {
			return false;
		}
		//检查上传文件类型
		checkFileType(col);
		//栏目层级
		int collevel = 1; 
		if(NumberUtil.getInt(col.getPid())>0){
			Col parcol = findByIid(col.getPid());
			if(parcol != null && parcol.getIid() > 0){
				collevel = parcol.getCollevel() + 1;
			} 
		}
		col.setCollevel(collevel);
		//上传背景图以及栏目图标及首图
		col = excuteDefaultPic(col);  
		Integer iid = col.getIid();
		boolean isSuccess = false;
		int num = this.findNumOfSameName(iid, col.getName(), col.getPid(), col.getSiteId());
		if (num > 0) {
			throw new OperationException("栏目名称已存在,请重新设置！");
		}
		if(NumberUtil.getInt(col.getCommonType()) != 2){
			col.setBannerId(null);
		}
		if(col.getTaskId()==null || "".equals(col.getTaskId())){
		    col.setSourcename("");
		    col.setSourceurl("");
		    col.setSourcepwd("");
		    col.setSourcetype(null);
		}
		isSuccess = colDAO.update(col);
		
		// 查找相关的引用栏目
		List<ColQuoteRelation> colQuoteRelList = colQuoteRelService.findByColId(NumberUtil.getInt(col.getIid()), NumberUtil.getInt(col.getSiteId()));
		// 查出和该栏目存在引用关系的栏目
		if(CollectionUtils.isNotEmpty(colQuoteRelList)){
		    for(ColQuoteRelation colQuotoRel : colQuoteRelList){
		        // 获取栏目实体
		        Col quoteCol = colDAO.findByIid(NumberUtil.getInt(colQuotoRel.getAfterId()));
		        if(quoteCol!=null){
		            // 先记录原实体应保留的参数值
		            int quoteIid = quoteCol.getIid();
		            int quotePid = quoteCol.getPid();
		            int quoteOrderId = quoteCol.getOrderId();
		            int quoteBookOrderId = quoteCol.getBookorderId();
		            String quotePName = quoteCol.getPname();
		            String quoteColType = quoteCol.getColType();
	                
	                String oldPath = BaseInfo.getRealPath() + col.getIconPath();
	                String filename = "";
	                String newPath = "";
	                File oldFile = new File(oldPath);
	                if (oldFile.exists()) {
	                    filename = quoteCol.getIconPath().substring(quoteCol.getIconPath().lastIndexOf("/")+1);
	                    newPath = "/web/site" + quoteCol.getSiteId() + "/col/" + quoteIid + "/" + filename;
	                    String filePath = BaseInfo.getRealPath()+newPath;
	                    FileUtil.copyFile(oldPath, filePath);
	                }
	                
	                // 复制对象
	                quoteCol = (Col)col.clone();
	                
	                // 塞原始值
	                quoteCol.setIid(quoteIid);
	                quoteCol.setPid(quotePid);
	                quoteCol.setOrderId(quoteOrderId);
	                quoteCol.setBookorderId(quoteBookOrderId);
	                quoteCol.setPname(quotePName);
	                quoteCol.setColType(quoteColType);
	                quoteCol.setIconPath(newPath);
	                
	                // 更新引用的实体
	                colDAO.update(quoteCol);
	                
//	                if (NumberUtil.getInt(col.getTaskId()) != 0) {
//	                    // 插入栏目任务关联表
//	                    ColRelation colRe = new ColRelation();
//	                    colRe.setColId(iid);
//	                    colRe.setColName(col.getName());
//	                    colRe.setTaskId(col.getTaskId());
//	                    colRe.setTaskName(col.getTaskName());
//	                    Integer reIid = colreDAO.insert(colRe);
//	                    if (reIid > 0) {
//	                        success = true;
//	                    }
//	                }
	                
		        }
		    }
		}
		// 修改导航标识位
		siteService.modifyChanFlag(col.getSiteId());
		// 修改栏目标识位
		siteService.modifyColFlag(col.getSiteId());
		colDAO.updateFlag(StringUtil.toIntegerList(StringUtil.getString(col.getIid())));
		if (isSuccess) {
			//更新附件路径
			isSuccess = this.modifyPath(col);
			if(NumberUtil.getInt(col.getTaskId()) > 0){
				// 插入栏目任务关联表
				ColRelation colRe = new ColRelation(); 
				colRe.setColId(col.getIid());
				colRe.setColName(col.getName());
				colRe.setTaskId(col.getTaskId());
				colRe.setTaskName(col.getTaskName());
				if (NumberUtil.getInt(col.getColRelationIid())> 0) {
					colRe.setIid(col.getColRelationIid());
					isSuccess = colreDAO.update(colRe);
				}else{
					colreDAO.insert(colRe);
				}
			}else{
				colreDAO.deleteByColIds(
						StringUtil.toIntegerList(StringUtil.getString(col.getIid())));
			}
		}
		//如果栏目修改了信息展现样式或信息内容展现样式，栏目下信息的展现样式或内容展现样式也要跟着修改
		if(NumberUtil.getInt(col.getIid())>0 && NumberUtil.getInt(col.getSiteId())>0){
			infoService.updateColInfoType(col.getInfoListType(), 
					col.getInfoContentType(), 
		             col.getIid(), col.getSiteId());
		} 
		if (!isSuccess) {
			throw new OperationException("更新操作失败！");
		}
		logService.add(LogConfig.modcol, LogConfig.oprmodify, col.getName());
		// 若栏目是启用状态，需要启用任务
		if (NumberUtil.getInt(col.getEnable()) == 1) {
			TaskManager.removeTask("synInfo_" + col.getIid());
			Col colTask = colDAO.findTaskColByIid(col.getIid());
			startTask(colTask);
		} else {
			// 删除停用的任务
			TaskManager.removeTask("synInfo_" + col.getIid());
		}
		//查询频道栏目信息
        List<ChannelCol> channelColList = channelColDAO.findByColid(col.getSiteId(), col.getIid());
        if(CollectionUtils.isNotEmpty(channelColList)){
            for(ChannelCol channelCol : channelColList){
                channelDAO.updateFlag(channelCol.getChannelId());
            }
        }
		// 保存栏目到缓存
		if (iid > 0) {
			CacheUtil.removeKey(StaticValues.CACHE_COL, "" + iid);
		}
		return isSuccess;
	}

	/**
	 * 根据文件相关信息获取路径
	 * 
	 * @param file
	 *            文件
	 * @param fileName
	 *            文件名 
	 * @param col
	 *            栏目对象
	 * @return 文件路径
	 */
	private String findFilePath(MultipartFile file, String fileName, Col col) {
		String path = BaseInfo.getRealPath() + "/web/site" + col.getSiteId() + "/col/" + col.getIid() + "/"; 
		String absPath = "/web/site" + col.getSiteId() + "/col/" + col.getIid() + "/";
		MultipartFileInfo fileInfo = MultipartFileInfo.getInstance(file);
		// 由于文件目前属于暂存状态，我们需要把文件拷贝到我们需要的位置
		String newiconFileName = fileName + "." + fileInfo.getFileType();
		File desFile = new File(path + newiconFileName);
		if (desFile.exists()) {
			FileUtil.deleteFile(desFile);
		}
		// 开始拷贝
		ControllerUtil.writeMultipartFileToFile(desFile, file); 
		return absPath + newiconFileName; 
	}

	/**
	 * 根据Iid获取栏目详细信息
	 * @param iid
	 *            主键ID
	 * @return 栏目实体
	 */
	public Col findByIid(int iid) {
		if (NumberUtil.getInt(iid) == 0) {
			return null;
		}
		Col col = (Col) CacheUtil.getValue(StaticValues.CACHE_COL, "" + iid);
		if(col == null){
			col = colDAO.findByIid(iid);
			ColRelation colreEn = colreDAO.findByColid(iid);
			if (colreEn != null) {
				col.setColRelationIid(NumberUtil.getInt(colreEn.getIid()));
				col.setTaskId(NumberUtil.getInt(colreEn.getTaskId()));
				col.setTaskName(colreEn.getTaskName());
			}
			CacheUtil.setValue(StaticValues.CACHE_COL, "" + iid, col);
		}
		return col;
	}
	
	/**
	 * 获取栏目 带上任务属性
	 * @param iid
	 *            栏目id
	 * @return 栏目实体
	 */
	public Col findSynByIid(Integer iid) {
		if (iid <= 0) {
			return null;
		}
		iid = 2;
		Col col = colDAO.findByIid(iid);
		ColRelation colreEn = colreDAO.findByColid(iid);
		col.setTaskId(colreEn.getTaskId());
		col.setTaskName(colreEn.getTaskName());
		return col;
	}

	/**
	 * 获取栏目实体集合
	 * @return 栏目实体集合
	 */
	public List<Col> findAllCol() {
		List<Col> colList = colDAO.findAllCol();
		return colList;
	}

	/**
	 * 获取栏目集合
	 * @param enable
	 *            是否启用状态
	 * @return 栏目集合
	 */
	public List<Col> findColByEnable(int enable) {
		List<Col> list = new ArrayList<Col>();
		int count = colDAO.findEnableCount(enable);
		int times = count / 1000 + 1;
		int pageSize = 1000;
		for (int i = 1; i < times + 1; i++) {
			list.addAll(colDAO.findColByEnable(pageSize, i, enable));
		}
		return list;  
	}

	/**
	 * 通过机构名称获取机构
	 * @param name
	 *            机构名称
	 * @return 机构实体列表
	 */
	public List<Col> findByName(String name) {
		if (name == null || name.length() == 0) {
			return null;
		}
		List<Col> colList = colDAO.findByName(name);
		return colList;
	}
	
	/**
	 * 通过机构名称获取类似机构
	 * @param name
	 *            机构名称
	 * @return 机构实体列表
	 */
	public List<Col> findSimilarByName(String name) {
		if (name == null || name.length() == 0) {
			return null;
		}
		List<Col> colList = colDAO.findSimilarByName(name);
		return colList;
	}
	
	/**
	 * 删除栏目
	 * @param ids
	 *            栏目ID串 如:1,2,3
	 * @param siteId
	 *            网站id
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean removeByIds(String ids, Integer siteId) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList) || NumberUtil.getInt(siteId) == 0) {
			return false;
		}
		List<Col> colList = colDAO.findByIds(idList);
		boolean hasSubCol = this.checkSubCol(ids);
		if (hasSubCol) {
			throw new OperationException("所选栏目存在子栏目,请先删除子栏目!");
		}
		boolean hasSubInfo = this.checkSubInfo(ids, siteId);
		if (hasSubInfo) {
			throw new OperationException("所选栏目存在信息,请先删除信息!");
		}
		boolean hasRecycleInfo = this.checkRecycleInfo(ids, siteId);
		if (hasRecycleInfo) {
			throw new OperationException("所选栏目回收站存在信息,请先删除信息!");
		}
		
		// 查找和所删除栏目相关的引用栏目
		List<ColQuoteRelation> colQuoteRelList = colQuoteRelDAO.findByColIds(idList, NumberUtil.getInt(siteId));
        if(CollectionUtils.isNotEmpty(colQuoteRelList)){
            for(ColQuoteRelation colQuoteRel : colQuoteRelList){
                hasSubInfo = this.checkSubInfo(StringUtil.getString(colQuoteRel.getAfterId()), siteId);
                if (hasSubInfo) {
                    throw new OperationException("所选栏目的引用栏目存在信息,请先删除该信息!");
                }
                hasRecycleInfo = this.checkRecycleInfo(StringUtil.getString(colQuoteRel.getAfterId()), siteId);
                if (hasRecycleInfo) {
                    throw new OperationException("所选栏目的引用栏目的回收站存在信息,请先删除该信息!");
                }
                idList.add(NumberUtil.getInt(colQuoteRel.getAfterId()));
            }
        }
        
		boolean isSuccess = false;
		isSuccess = colDAO.deleteByIds(idList); // 删栏目
		
		//
		if(isSuccess && CollectionUtils.isNotEmpty(colQuoteRelList)){
		    for(ColQuoteRelation colQuoteRel : colQuoteRelList){
		        colQuoteRelDAO.deleteByAfterId(NumberUtil.getInt(colQuoteRel.getAfterId()), NumberUtil.getInt(colQuoteRel.getSiteId()));
		    }
		}
		
		if (!isSuccess) {
			throw new OperationException("删除栏目失败！");
		}
		isSuccess = signRelService.removeByInfoIds(idList);
		if (!isSuccess) {
			throw new OperationException("删除订阅栏目失败！");
		}
		siteService.modifyColFlag(siteId);
		siteService.modifyChanFlag(siteId);
		isSuccess = colreDAO.deleteByColIds(idList); // 删任务
		if (!isSuccess) {
			throw new OperationException("删除栏目任务失败！");
		}
		
		//查询频道栏目信息
		List<ChannelCol> channelCols=channelColDAO.findByColids(siteId, idList);
		Map<Integer, Integer> channelIds=new HashMap<Integer, Integer>();
		
		if(!CollectionUtils.isEmpty(channelCols)){
			ChannelCol channelEn=null;
			int channelId = 0;
			for(int i=0; i<channelCols.size(); i++){
				channelEn = channelCols.get(i);
				if(channelEn==null || NumberUtil.getInt(channelEn.getChannelId())==0){
					continue;
				}
				channelId = NumberUtil.getInt(channelEn.getChannelId());
				//去除重复
				if(!channelIds.containsKey(channelId)){
					channelIds.put(channelId, channelId);
					channelDAO.updateFlag(channelId);
				}
			}
		}
		isSuccess = channelDAO.deleteByColIds(idList, siteId); // 删除频道
		if (!isSuccess) {
			throw new OperationException("删除频道失败！");
		}
		isSuccess = channelColDAO.deleteByColIds(idList); // 删除频道栏目
		if (!isSuccess) {
			throw new OperationException("删除频道栏目关联失败！");
		}
		isSuccess = roleColDAO.deleteByColIds(idList); // 删除角色栏目
		if (!isSuccess) {
			throw new OperationException("删除角色栏目关联失败！");
		}
		// 删除栏目文件
		String filePath = ""; // 栏目路径
		
		for (int i = 0, len = idList.size(); i < len; i++) {
			Integer colIid = idList.get(i);
			if(fileUtil.getImplClazz()!=LocalFileUtil.class){
				filePath = "web/site" + siteId + "/col/" + colIid+"/";
				fileUtil.deleteDirectory(fileUtil.getAbsolutePath(filePath));
			}else{
				filePath = BaseInfo.getRealPath() + "/web/site" + siteId + "/col/" + colIid;
				File file = new File(filePath);
				if (file.isDirectory()) {
					FileUtil.deleteDirectory(file);
				}
			}
			// 删除栏目缓存
			CacheUtil.removeKey(StaticValues.CACHE_COL, "" + colIid);
			// 删除停用的任务
			TaskManager.removeTask("synInfo_" + colIid);
		}
		
		// 栏目名称(记录日志)
        StringBuffer names = new StringBuffer(100);
        for(Col col : colList){
            names.append(",").append(col.getName());
        }
        String strnames = "";
        if(names != null && names.length() > 0){
            strnames = names.substring(1);
        }
        if(names != null && names.length() > 100){
            strnames = names.substring(0, 100);
        }
		logService.add(LogConfig.modcol, LogConfig.oprremove, strnames);
		
		return isSuccess;
	}

	/**
	 * 检查是否有子栏目
	 * @param ids
	 *            栏目ID串 如:1,2,3
	 * @return true - 有子栏目<br/>
	 *         false - 无子栏目
	 */
	public boolean checkSubCol(String ids) {
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idsList)) {
			return false;
		}
		int num = colDAO.findCountSubCol(idsList);
		return num > 0;
	} 
	
	/**
	 * 检查是否有信息.
	 * @param ids 栏目ID串 如:1,2,3
	 * @param siteId 网站id
	 * @return    设定参数 .
	 */
	public boolean checkSubInfo(String ids, Integer siteId) {
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idsList)) {
			return false;
		}
		int num = colDAO.findCountSubInfo(idsList, siteId);
		return num > 0;
	}
	
	/**
	 * 检查回收站是否有信息
	 * @param ids 栏目ID串 如:1,2,3
	 * @param siteId 网站id
	 * @return    设定参数 .
	*/
	public boolean checkRecycleInfo(String ids, Integer siteId) {
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idsList)) {
			return false;
		}
		int num = colDAO.findCountRecycleInfo(idsList, siteId);
		return num > 0;
	}

	/**
	 * 更新栏目的有效性
	 * @param ids
	 *            栏目ID
	 * @param enable
	 *            是否有效<br/>
	 *            1 - 有效<br/>
	 *            0 - 无效
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean modifyEnable(String ids, int enable) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		boolean isSuccess = false;
		List<Col> tasklist = null;
		Col col = null;
		int preenable = 0;
		if (enable == 0) {// 停用
			preenable = 1;
		} else {
			preenable = 0;
		}
		// 获取需要停用或者启用的栏目
		tasklist = colDAO.findColByEnable(preenable, idList);
		if (CollectionUtils.isEmpty(tasklist)) {
			return true;
		}
		String colids = "";
		StringBuffer colidsStr=new StringBuffer(100);
		for (int i = 0; i < tasklist.size(); i++) {
			col = tasklist.get(i);
			colidsStr.append("," + col.getIid()); 
		}
		if(colidsStr!=null && colidsStr.length()>0){
			colids = colidsStr.toString().substring(1);
		} 
		idList = StringUtil.toIntegerList(colids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return true;
		}
		// 记录日志的操作id
		int funcId = 0;
		if (enable == 0) {// 停用
			funcId = LogConfig.oprclose;
			isSuccess = colDAO.updateEnable(idList, enable);
			if (!isSuccess) {
				throw new OperationException("停用栏目失败！");
			}
			isSuccess = channelColDAO.deleteByColIds(idList); // 删除频道
			if (!isSuccess) {
				throw new OperationException("删除频道失败！");
			}
		} else {// 启用
			funcId = LogConfig.opropen;
			isSuccess = colDAO.updateEnable(idList, enable);
			if (!isSuccess) {
				throw new OperationException("启用栏目失败！");
			}
		}
		for (int i = 0; i < idList.size(); i++) {
			col = findByIid(idList.get(i));
			if(i == 0){
				siteService.modifyColFlag(col.getSiteId());
			}
			col.setEnable(enable);
			CacheUtil.removeKey(StaticValues.CACHE_COL, "" + col.getIid());
			logService.add(LogConfig.modcol, funcId, col.getName());
			if (enable == 0) {// 停用
				// 删除启用的任务
				TaskManager.removeTask("synInfo_" + col.getIid());
			} else {
				// 启动任务
				col = colDAO.findTaskColByIid(col.getIid());
				startTask(col);
			}
		}
		return isSuccess;
	}

	/**
	 * 修改栏目的flag值
	 * @param ids
	 *            栏目id集合
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean modifyFlag(String ids) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		List<Col> colList = findByIds(ids);
		if(colList == null || colList.size() == 0){
			return false;
		} 
		for(Col col : colList){
			//修改推送信息的flag
			siteService.modifyPushFlag(col.getSiteId());
			//清缓存
	    	CacheUtil.removeKey(StaticValues.CACHE_COL, "" + col.getIid());
		}
		boolean isSuccess = colDAO.updateFlag(idList);
		if (!isSuccess) {
			throw new OperationException("修改flag值失败！");
		}
		return isSuccess;
	}

    /**
	 * 修改栏目的infonum
	 * @param ids 栏目id串
	 * @param infonum 信息变动数
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException    设定参数 .
	 */
	public boolean modifyInfonum(String ids, Integer infonum) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		boolean isSuccess = colDAO.updateInfonum(idList, infonum);
		if (!isSuccess) {
			throw new OperationException("修改infonum值失败！");
		}
		return isSuccess;
	}
	
	/**
	 * 修改开始时间
	 * @param colid
	 *            栏目id
	 * @param startTime
	 *            开始时间
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean modifyStarttime(int colid, String startTime) throws OperationException {
		boolean isSuccess = colDAO.updateTime(colid, startTime);
		if (!isSuccess) {
			throw new OperationException("修改开始时间startTime失败！");
		}
		Col col = findByIid(colid);
		if (col != null) {
			col.setStartTime(DateUtil.stringtoDate(startTime, DateUtil.YYYY_MM_DD_HH_MM_SS));
			CacheUtil.setValue(StaticValues.CACHE_COL, "" + col.getIid(), col);
		}
		return isSuccess;
	}

	/**
	 * 根据网站ID，写XML到网站文件夹下（用于网站导出）
	 * @param siteId
	 *            网站Id
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean writeAllColXml(Integer siteId) {
		if (NumberUtil.getInt(siteId) == 0) {
			return false;
		}
		List<Col> colList = this.findByPidAndSiteId(0, siteId); /* 获得当前网站下的所有栏目 */
		if (colList == null || colList.size() == 0) {
			return false;
		}
		StringBuffer sbColumn = new StringBuffer(512);
		sbColumn.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sbColumn.append("<cols>\n");
		Col col = null;
		for (int i = 0, len = colList.size(); i < len; i++) {
			sbColumn.append("<col");
			col = colList.get(i); 
				Field[] nodeName = col.getClass().getDeclaredFields();
				for (Field colField : nodeName) {
					String fieldname = colField.getName();
					if ("serialVersionUID".equals(fieldname) || "siteId".equals(fieldname)
							|| "taskId".equals(fieldname) || "taskName".equals(fieldname)
							|| "createTime".equals(fieldname) || "orderId".equals(fieldname)) {
						continue;
					}
					fieldname = fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
					Method get=null;
					try {
						get = col.getClass().getMethod("get" + fieldname);
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
					sbColumn.append(' ');
					sbColumn.append(fieldname.toLowerCase());
					sbColumn.append("=\"");
					String value="";
					try {
					    if(get!=null){
					        if (get.invoke(col) != null){
	                            value = StringUtil.getString(get.invoke(col));
	                        }
					    }
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch(NullPointerException e){
						e.printStackTrace();
					}
					if (value.equals("null")) {
						value = "";
					}
					sbColumn.append(value);
					sbColumn.append("\"");

				}
				sbColumn.append(">");
				boolean isHave = this.checkSubCol(col.getIid() + "");
				if (isHave) {
					sbColumn.append("\n");
					sbColumn.append(this.findColXml(siteId, col.getIid(), null));
				}
				sbColumn.append("</col>\n");
		}
		sbColumn.append("</cols>");
		String basePath = BaseInfo.getRealPath() + "/web/site" + siteId;
		/* 写入文件 */
		String fileName = "/cols.xml";
		String filePath = basePath + fileName;
		File file = new File(filePath);
		return FileUtil.writeStringToFile(file, sbColumn.toString());
	}

	/**
	 * 获得指定栏目ID串的栏目集合
	 * @param ids
	 *            栏目ID串 如:1,2,3
	 * @return List<Col>
	 */
	public List<Col> findByIds(String ids) {
		if (StringUtil.isEmpty(ids)) {
			return null;
		}
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		List<Col> colList = colDAO.findByIds(idsList);
		return colList;
	}

	/**
	 * 获得指定网站ID串下的所有用户集合
	 * @param siteIds
	 *            栏目ID串 如:1,2,3
	 * @return List<Col>
	 */
	public List<Col> findBySiteIds(String siteIds) {
		if (StringUtil.isEmpty(siteIds)) {
			return null;
		}
		List<Integer> siteidList = StringUtil.toIntegerList(siteIds, ",");
		List<Col> list = new ArrayList<Col>();
		int count = colDAO.findColNumBySiteId(siteidList);
		int times = count / 1000 + 1;
		int pageSize = 1000;
		for (int i = 1; i < times + 1; i++) {
			list.addAll(colDAO.findBySiteIds(pageSize, i, siteidList));
		}
		return list; 
	}

	/**
	 * 获得栏目
	 * @param siteId
	 *            网站id
	 * @param type
	 *            栏目类型
	 * @return List<Col>
	 */
	public List<Col> findBySiteIdAndType(Integer siteId, Integer type) {
		List<Col> colList = colDAO.findBySiteIdAndType(siteId, type);
		return colList;
	}
	
	/**
	 * 获得栏目
	 * @param siteId
	 *            网站id 
	 * @return List<Col>
	 */
	public List<Col> findBySiteIdAndType(Integer siteId) {
		List<Col> list = new ArrayList<Col>();
		int count = colDAO.findColNumBySiteId(siteId);
		int times = count / 1000 + 1;
		int pageSize = 1000;
		for (int i = 1; i < times + 1; i++) {
			list.addAll(colDAO.findBySiteIdAndType(pageSize, i, siteId));
		}
		return list;   
	}

	/**
	 * 更新栏目id的orderid
	 * @param ids
	 *            栏目id串
	 * @param orderids
	 *            排序id
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             操作
	 */
	public boolean modifyOrderIdById(String ids, String orderids) throws OperationException {
		if (ids == null || ids.length() == 0 || orderids == null || orderids.length() == 0) {
			return false;
		}
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		List<Integer> ordersList = StringUtil.toIntegerList(orderids, ",");
		boolean isSuccess = false;
		for (int i = 0, len = idsList.size(); i < len; i++) {
			isSuccess = colDAO.updateOrderIdById(ordersList.get(i), idsList.get(i));
			if(i == 0){
				int siteId = findByIid(idsList.get(i)).getSiteId();
				siteService.modifyColFlag(siteId);
			}
		}
		if (isSuccess) {
			try {
				CacheUtil.removeAll(StaticValues.CACHE_COL);
			} catch (CacheException e) {
				logger.error("清除缓存出错：", e);
			} catch (IOException e) {
				logger.error("清除缓存出错：", e);
			}
		}
		return isSuccess;
	}

	/**
	 * 更新栏目id的orderid
	 * @param ids
	 *            栏目id串
	 * @param orderids
	 *            排序id
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             操作
	 */
	public boolean modifySubscribeOrderIdById(String ids, String orderids) throws OperationException {
		if (ids == null || ids.length() == 0 || orderids == null || orderids.length() == 0) {
			return false;
		}
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		List<Integer> ordersList = StringUtil.toIntegerList(orderids, ",");
		boolean isSuccess = false;
		for (int i = 0, len = idsList.size(); i < len; i++) {
			isSuccess = colDAO.updateSubscribeOrderIdById(ordersList.get(i), idsList.get(i));
		}
		if (isSuccess) {
			try {
				CacheUtil.removeAll(StaticValues.CACHE_COL);
			} catch (CacheException e) {
				logger.error("清除缓存出错：", e);
			} catch (IOException e) {
				logger.error("清除缓存出错：", e);
			}
		}
		return isSuccess;
	}

	/**
	 * 获得指定目录下所有的文件列表
	 * @param outPath
	 *            路径
	 * @return ArrayList<File>
	 */
	public ArrayList<File> getAllfileName(String outPath) {
		File file = new File(outPath);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files == null || files.length < 0) {
				return null;
			}
			File childFile = null;
			for (int i = 0; i < files.length; i++) {
				childFile = files[i];
				if (childFile.isDirectory()) {
					getAllfileName(childFile.getPath());
				} else {
					fileArr.add(childFile);
				}
			}
		}
		return fileArr;
	}

	/**
	 * 通过ID获取栏目实体并获取isparent属性 用于频道的树调用
	 * @param iid
	 *            栏目ID
	 * @param type
	 *            栏目类型
	 * @return 栏目实体
	 */
	public Col findIsParentByIid(Integer iid, Integer type) {
		return colDAO.findIsParentByIid(iid, type);
	}
	
	/**
	 * 通过ID获取栏目实体并获取isparent属性 用于频道的树调用
	 * @param iid
	 *            栏目ID
	 * @param types
	 *            栏目类型串
	 * @return 栏目实体
	 */
	public Col findIsParentByIid(Integer iid, String types) {
		return colDAO.findIsParentByIid(iid, types);
	}

	/**
	 * 查找栏目集合
	 * @param enable
	 *            启用状态
	 * @param start
	 *            开始条数
	 * @param end
	 *            结束条数
	 * @return 栏目集合
	 */
	public List<Col> findByEnable(int enable, int start, int end) {
		return colDAO.findByEnable(enable, start, end);
	}

	/**
	 * 查找栏目集合
	 * @param enable
	 *            启用状态
	 * @return 栏目集合
	 */
	public List<Col> findAllByEnable(int enable) {
		int num = 0;
		int start = 0;
		int end = 0;
		int temp = 1000;
		int lists = 0;
		List<Col> allcol = new ArrayList<Col>();
		List<Col> collist = null;
		do {
			start = num * temp;
			end = temp * (num + 1);
			collist = colDAO.findByEnable(1, start, end);
			num++;
			allcol.addAll(collist);
			lists = collist.size();
		} while (lists == temp);
		return allcol;
	}

	/**
	 * 获取网站下面最小的栏目id
	 * @param siteid
	 *            网站id
	 * @return 栏目id
	 */
	public int findMinId(int siteid) {
		if (siteid <= 0) {
			return 0;
		}
		return colDAO.findMinId(siteid);
	}

	/**
	 * 导出栏目方法
	 * @param ids
	 *            栏目ID串
	 * @param siteId
	 *            网站Id
	 * @param pid
	 *            父栏目Id
	 * @return 导出文件路径
	 */
	public String exportCol(String ids, Integer siteId, Integer pid) {
		String colXml = findColXml(siteId, pid, ids); /* 调用取栏目XML字符串方法 */
		if (colXml == null || colXml.trim().length() == 0) {
			return "";
		}
		String zipFileName = StringUtil.getUUIDString();
		StringBuffer sbContent = new StringBuffer(1024);
		sbContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sbContent.append("<cols>\n");
		sbContent.append(colXml);
		sbContent.append("</cols>");
		String basePath = BaseInfo.getRealPath() + "/tempfile/" + zipFileName;
		String xmlPath = BaseInfo.getRealPath() + "/tempfile/" + zipFileName + "/cols.xml";
		File file = new File(xmlPath);
		boolean isSuccess = FileUtil.writeStringToFile(file, sbContent.toString());
		if (isSuccess) {
			String desPath = ""; // 目标路径
			String srcPath = ""; // 原路径
			if (StringUtil.isNotEmpty(ids)) {
				List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
				for (int i = 0; i < idsList.size(); i++) {
					desPath = basePath + "/col/" + idsList.get(i);
					srcPath = BaseInfo.getRealPath() 
						    + "/web/site" + siteId + "/col/" + idsList.get(i);
					File srcFile = new File(srcPath);
					if (srcFile.exists()) {
						File desFile = new File(desPath);
						// 拷贝文件
						FileUtil.copyDirectory(srcFile, desFile);
					}
				}
			} else {
				desPath = basePath + "/col";
				srcPath = BaseInfo.getRealPath() + "/web/site" + siteId + "/col";
				File srcFile = new File(srcPath);
				if (srcFile.exists()) {
					File desFile = new File(desPath);
					// 拷贝文件
					FileUtil.copyDirectory(srcFile, desFile);
				}
			}
		}
		// 压缩目标文件
		String zipPath = BaseInfo.getRealPath() 
			           + "/tempfile/" + StringUtil.getUUIDString() + ".zip"; 
		if (!StringUtil.isEmpty(basePath)) {
			File srcFile = new File(basePath);
			File desFile = new File(zipPath);
			ZipUtil.zip(srcFile, desFile);
			return zipPath;
		}
		// 写日志
		List<Col> list = this.findByIds(ids);
		StringBuffer name = new StringBuffer();
		for(Col col : list){
			 name.append(col.getName()).append(","); 
		}
		logService.add(LogConfig.modcol, LogConfig.oprexport, name.substring(1).toString());
		return "";

	}

	/**
	 * 根据栏目父ID和栏目ID集合获取栏目XML
	 * @param siteId
	 *            网站ID
	 * @param pId
	 *            父栏目ID
	 * @param ids
	 *            栏目ID串
	 * @return XML字符串
	 */
	private String findColXml(Integer siteId, Integer pId, String ids) {
		if (NumberUtil.getInt(pId) < 0) {
			return "";
		}
		List<Col> colList = null;
		if (StringUtil.isNotEmpty(ids) && NumberUtil.getInt(pId) == 0) {
			colList = this.findByIds(ids);
		} else {
			colList = this.findByPidAndSiteId(pId, siteId);
		}
		if (colList == null || colList.size() == 0) {
			return "";
		}
		StringBuffer sbColumn = new StringBuffer(1024);
		Col col = null;
		for (int i = 0, len = colList.size(); i < len; i++) {
			sbColumn.append("<col");
			col = colList.get(i);
				Field[] nodeName = col.getClass().getDeclaredFields();
				for (Field colField : nodeName) {
					String fieldname = colField.getName();
					if ("serialVersionUID".equals(fieldname) || "siteId".equals(fieldname)
//							|| "taskId".equals(fieldname) || "taskName".equals(fieldname)
							|| "createTime".equals(fieldname) || "orderId".equals(fieldname)) {
						continue;
					}
					fieldname = fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
					Method get = null;
					try {
						get = col.getClass().getMethod("get" + fieldname);
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
					sbColumn.append(' ');
					sbColumn.append(fieldname.toLowerCase());
					sbColumn.append("=\"");
					String value = "";
					if(get!=null){
					    try {
	                        value = StringUtil.getString(get.invoke(col));
	                    } catch (IllegalArgumentException e) {
	                        e.printStackTrace();
	                    } catch (IllegalAccessException e) {
	                        e.printStackTrace();
	                    } catch (InvocationTargetException e) {
	                        e.printStackTrace();
	                    }
					}
					if (value.equals("null")) {
						value = "";
					}
					sbColumn.append(value);
					sbColumn.append("\"");
				}
				sbColumn.append(">");
				boolean isHave = this.checkSubCol(col.getIid() + "");
				if (isHave) {
					sbColumn.append("\n");
					sbColumn.append(this.findColXml(siteId, col.getIid(), null));
				}
				sbColumn.append("</col>\n");
		}
		return sbColumn.toString();
	}

	/**
	 * 根据父栏目ID和网站Id获取栏目
	 * @param pId
	 *            父栏目ID
	 * @param siteId
	 *            网站Id
	 * @return List<Col>
	 */
	public List<Col> findByPidAndSiteId(Integer pId, Integer siteId) {
		List<Col> colList = colDAO.findByPidAndSiteId(pId, siteId);
		return colList;
	}
	
	/**
	 * 根据父栏目ID和网站Id一次性获取栏目
	 * @param pId
	 *            父栏目ID
	 * @param siteId
	 *            网站Id
	 * @param ret
	 *            ret
	 * @return List<Col>
	 */
	public List<Col> findAllByPidAndSiteId(Integer pId, Integer siteId, List<Col> ret){
		List<Col> colList = colDAO.findByPidAndSiteId(pId, siteId);
		if(colList != null && colList.size() > 0){
			ret.addAll(colList);
			for(Col col : colList){
				findAllByPidAndSiteId(col.getIid(), siteId, ret);
			}
		}
		return ret;
	}
	
	/**
	 * 根据父栏目ID和网站Id获取下一级子栏目
	 * @param pId
	 * @param siteId
	 * @param ret
	 * @return
	 */
	public List<Col> findNextLevelCatesByPidAndSiteId(Integer pId, Integer siteId, List<Col> ret){
        List<Col> colList = colDAO.findByPidAndSiteId(pId, siteId);
        if(colList != null && colList.size() > 0){
            ret.addAll(colList);
        }
        return ret;
    }
	
	/**
	 * 获得离线下载栏目集合
	 * @param siteId
	 *            网站Id
	 * @return 栏目集合
	 */
	public List<Col> findByofflineCol(Integer siteId) {
		return colDAO.findAllOfflineBySiteId(siteId);
	}

	/**
	 * 导入栏目
	 * @param file
	 *            导入文件
	 * @param siteId
	 *            网站Id
	 * @param pId
	 *            父栏目Id
	 * @return 操作信息
	 * @throws OperationException
	 *             操作提示
	 */
	public String importCol(File file, Integer siteId, Integer pId) throws OperationException {
		if (file == null) {
			throw new OperationException("找不到导入文件！");
		} else if (NumberUtil.getInt(siteId) == 0) {
			throw new OperationException("网站ID为空！");
		}
		try {
			String retMessage = "";
			String unzipName = StringUtil.getUUIDString();
			String outPath = BaseInfo.getRealPath() + "/tempfile/" + unzipName; // 解压路径
			ZipUtil.unzip(file, new File(outPath));
			File outFile = new File(outPath);
			if (outFile.exists()) {
				fileArr = new ArrayList<File>();
				ArrayList<File> fileList = this.getAllfileName(outPath); /* 获取所有文件 */
				for (int i = 0; fileList != null && i < fileList.size(); i++) {
					File subFile = fileList.get(i);
					String fileName = subFile.getName();
					String filePath = subFile.getAbsolutePath();
					if (fileName.indexOf("cols") >= 0) {
						/* 循环插入栏目信息 */
						retMessage = this.importCols(filePath, siteId, pId, unzipName);
						if (!retMessage.equals("")) {
							retMessage = "<div style='height:150px;overflow:auto'>导入完毕，" 
									+ "存在以下问题：<br/>" + retMessage
									+ "</div>";
						}
					}
				}
			}
			logService.add(LogConfig.modcol, LogConfig.oprimport, "栏目");
			return retMessage;
		} catch (Exception e) {
			logger.error("import users error", e);
			return "导入失败";
		} finally {
			try {
				if (file.exists()) {
					file.delete();
				}
			} catch (Exception e) {
				logger.error("delete file error", e);
			}
		}
	}

	/**
	 * 导入栏目
	 * @param filePath
	 *            文件路径
	 * @param siteId
	 *            网站Id
	 * @param pId
	 *            父栏目Id
	 * @param unZipName
	 *            解压临时文件
	 * @return 操作信息
	 * @throws OperationException
	 *             666
	 */
	@SuppressWarnings("unchecked")
	public String importCols(String filePath, Integer siteId, Integer pId, String unZipName) throws OperationException {
		CurrentUser user = UserSessionInfo.getCurrentUser();
		if (filePath == null || filePath.equals("")) {
			return null;
		}
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		try {
			String strXml = FileUtil.readFileToString(file);
			XmlUtil xml = new XmlUtil(strXml);
			List<Element> nodeList = xml.getNodeFromName("/cols");
			String message = "";
			if (nodeList != null && nodeList.size() > 0) {
				Col col = null;
				for (Iterator<Element> it = nodeList.iterator(); it.hasNext(); ){
					col = new Col();
					Element aa = (Element) it.next();
					if (!aa.getName().equals("col")) {
						continue;
					}
					col.setIid(NumberUtil.getInt(aa.attributeValue("iid")));
					col.setSiteId(siteId);
					col.setName(aa.attributeValue("name"));
					col.setType(NumberUtil.getInt(aa.attributeValue("type")));
					col.setFirstPicShow(NumberUtil.getInt(aa.attributeValue("firstpicshow")));
					if (pId > 0) {
						col.setPid(pId);
					}
					col.setPname(aa.attributeValue("pname"));
					col.setAuditType(NumberUtil.getInt(aa.attributeValue("audittype")));
					col.setLimitTime(NumberUtil.getInt(aa.attributeValue("limittime")));
					col.setInfoListType(NumberUtil.getInt(aa.attributeValue("infolisttype")));
					col.setInfoTitle(aa.attributeValue("infotitle"));
					col.setIconPath(aa.attributeValue("iconpath"));
					col.setTaskId(NumberUtil.getInt(aa.attributeValue("taskid")));
					col.setTaskName(aa.attributeValue("taskname"));
					col.setSpec(aa.attributeValue("spec"));  
					col.setBlogType(NumberUtil.getInt(aa.attributeValue("blogtype")));
					col.setCollevel(NumberUtil.getInt(aa.attributeValue("collevel")));
					col.setCommonType(NumberUtil.getInt(aa.attributeValue("commontype")));
					col.setNickName(aa.attributeValue("nickname"));
					col.setFlag(NumberUtil.getInt(aa.attributeValue("flag")));
					if(NumberUtil.getInt(aa.attributeValue("taskid"))>0 && 
					        StringUtil.isEmpty(aa.attributeValue("starttime"))) {
						col.setStartTime(DateUtil.stringtoDate("2014-01-01 00:00:00'", 
						        DateUtil.YYYY_MM_DD_HH_MM_SS));
					} else {
						col.setStartTime(DateUtil.stringtoDate(StringUtil.getString(
						        aa.attributeValue("starttime")), DateUtil.YYYY_MM_DD_HH_MM_SS));
					}
					col.setSynPeriod(NumberUtil.getInt(aa.attributeValue("synperiod")));
					col.setInfoContentType(NumberUtil.getInt(aa.attributeValue("infocontenttype")));
					col.setIssearch(NumberUtil.getInt(aa.attributeValue("issearch")));
					if(user != null) {
						col.setUserId(user.getIid());
//						col.setIssearch(0);
					}
					col.setImgUuid(aa.attributeValue("imguuid"));
					int iid = this.addCol(col);
					if (iid > 0) {
						if (NumberUtil.getInt(col.getTaskId()) != 0) {
							// 插入栏目任务关联表
							ColRelation colRe = new ColRelation();
							colRe.setColId(iid);
							colRe.setColName(col.getName());
							colRe.setTaskId(col.getTaskId());
							colRe.setTaskName(col.getTaskName());
							colreDAO.insert(colRe);
						} 
						this.addCol(aa, siteId, iid, unZipName);
						// 拷贝文件
						String srcPath = filePath.replace("cols.xml", "col/" + aa.attributeValue("iid"));
						File srcFile = new File(srcPath);
						String desPath = BaseInfo.getRealPath() 
							           + "/web/site" + siteId + "/col/" + iid;
						File desFile = new File(desPath);
						boolean isCopy = false;
						if (srcFile.isDirectory()) { // 栏目文件存在
							isCopy = FileUtil.copyDirectory(srcFile, desFile);
						} else {
							continue;
						}
						if (!isCopy) {
							message += "拷贝栏目【" + col.getName() + "】相关文件失败";
							continue;
						}
					} else {
						message += "栏目【" + col.getName() + "】新增失败";
						continue;
					}
				}
			}
			return message;
		} catch (Exception e) {
			logger.error("importCol error", e);
			return null;
		}
	}

	/**
	 * 栏目导入新增方法
	 * @param ele
	 *            Element
	 * @param siteId
	 *            网站Id
	 * @param pId
	 *            父栏目Id
	 * @param unZipName
	 *            解压临时文件
	 * @return 操作信息
	 * @throws OperationException
	 *             页面
	 */
	@SuppressWarnings("unchecked")
	public String addCol(Element ele, Integer siteId, Integer pId, String unZipName) throws OperationException {
		ColFormBean col = new ColFormBean();
		CurrentUser user = UserSessionInfo.getCurrentUser();
		for (Iterator it = ele.elementIterator(); it.hasNext(); ){
			// 获取list中的值
			Element aa = (Element) it.next();
			col.setIid(NumberUtil.getInt(aa.attributeValue("iid")));
			col.setName(aa.attributeValue("name"));
			col.setSiteId(siteId);
			col.setType(NumberUtil.getInt(aa.attributeValue("type")));
			col.setFirstPicShow(NumberUtil.getInt(aa.attributeValue("firstpicshow")));
			if (pId > 0) {
				col.setPid(pId);
			}
			col.setPname(aa.attributeValue("pname"));
			col.setAuditType(NumberUtil.getInt(aa.attributeValue("audittype")));
			col.setLimitTime(NumberUtil.getInt(aa.attributeValue("limittime")));
			col.setInfoListType(NumberUtil.getInt(aa.attributeValue("infotype")));
			col.setInfoListType(NumberUtil.getInt(aa.attributeValue("infolisttype")));
			col.setInfoTitle(aa.attributeValue("infotitle"));
			col.setTaskId(NumberUtil.getInt(aa.attributeValue("taskid")));
			col.setTaskName(aa.attributeValue("taskname"));
			col.setBlogType(NumberUtil.getInt(aa.attributeValue("blogtype")));
			col.setCollevel(NumberUtil.getInt(aa.attributeValue("collevel")));
			col.setIconPath(aa.attributeValue("iconpath"));
			col.setSpec(aa.attributeValue("spec"));   
			col.setBlogType(NumberUtil.getInt(aa.attributeValue("blogtype")));
			col.setNickName(aa.attributeValue("nickname"));
			col.setFlag(NumberUtil.getInt(aa.attributeValue("flag")));
			col.setInfoContentType(NumberUtil.getInt(aa.attributeValue("infocontenttype")));
			col.setCommonType(NumberUtil.getInt(aa.attributeValue("commontype")));
			col.setStartTime(DateUtil.stringtoDate("2014-01-01 00:00:00'", DateUtil.YYYY_MM_DD_HH_MM_SS));
			col.setSynPeriod(NumberUtil.getInt(aa.attributeValue("synperiod")));
			col.setImgUuid(aa.attributeValue("imguuid"));
			if(user != null) {
				col.setUserId(user.getIid());
				col.setIssearch(0);
			}
			col.setImgUuid(aa.attributeValue("imguuid"));
			int iid = this.addCol(col);
			if (iid > 0) {
				if (NumberUtil.getInt(col.getTaskId()) != 0) {
					// 插入栏目任务关联表
					ColRelation colRe = new ColRelation();
					colRe.setColId(iid);
					colRe.setColName(col.getName());
					colRe.setTaskId(col.getTaskId());
					colRe.setTaskName(col.getTaskName());
					colreDAO.insert(colRe);
				} 
				addCol(aa, siteId, iid, unZipName);
				// 拷贝文件
				String srcPath = BaseInfo.getRealPath() 
					           + "/tempfile/" + unZipName + "/col/" + aa.attributeValue("iid");
				File srcFile = new File(srcPath);
				String desPath = BaseInfo.getRealPath() + "/web/site" + siteId + "/col/" + iid;
				File desFile = new File(desPath);
				FileUtil.copyDirectory(srcFile, desFile);
			} else {
				return "";
			}
		}
		return "";
	}

	/**
	 * 启用栏目
	 * @param col
	 *            栏目任务
	 */
	private void startTask(Col col) {
		if (col == null) {
			return;
		}
		int iid = NumberUtil.getInt(col.getIid());
		int taskid = NumberUtil.getInt(col.getTaskId());
		int synperiod = NumberUtil.getInt(col.getSynPeriod());
		if (iid <= 0 || taskid <= 0 || synperiod <= 0) {
			return;
		}
		SynchInfoTask synTask = new SynchInfoTask();
		synTask.addParam("col", col);
		// 任务周期
		synTask.setTaskSchedule(TaskScheduleBuilder.getEveryMinuteSchedule(synperiod));
		// 任务id
		synTask.setTaskId("synInfo_" + iid);
		// 任务名称
		synTask.setTaskName(col.getName() + "信息同步");
		// 任务可以暂停
		synTask.setCanPause(true);
		// 任务可以删除
		synTask.setCanRemove(true);
		// 信息同步线程启用
		TaskManager.addTask(synTask);
	}

	/**
	 * 判断栏目数是否已超注册文件栏目最大数
	 * @param siteId siteId
	 * @return boolean
	 */
	private boolean checkLicenceCol(Integer siteId) {
		boolean isSuccess = false;
		int maxNum = LicenceCheck.getLicence(BaseInfo.getRealPath(),"jmportal.licence", BaseInfo.getAppName()).getColCount();
		int realNum = colDAO.findColCount(siteId);
		if (realNum >= maxNum) {
			isSuccess = true;
		}
		return isSuccess;
	}

	/**
	 * 通过网站id查找该网站下栏目数目
	 * @param siteId
	 *            网站id
	 * @return int 栏目数目
	 */
	public int findColNumBySiteId(int siteId) {
		int num = colDAO.findColNumBySiteId(siteId);
		return num;
	} 
	
	/**
     * 修改启用状态
     * @param col col
     * @param enable enable
     * @return boolean
     */
    public boolean modifyEnable(Col col, Integer enable) {
    	//清缓存
    	CacheUtil.removeKey(StaticValues.CACHE_COL, "" + col.getIid());
    	return colDAO.updateEnable(col, enable);
    }
    
    /**
     * 根据Id查找类型
     * @param cateId cateId
     * @return  类型
     */
    public int getCateTypeById(int cateId){
    	Col cateEn = colDAO.findByIid(cateId);
    	if(cateEn != null){
    		return cateEn.getType();
    	} else {
    		return 0;
    	}
    }
    
    /**
	 * 通过网站id查找该网站下栏目数目
	 * @param siteId
	 *            网站id
	 * @param type
	 *            栏目类型            
	 * @return int 数目
	 */
	public int findColNumBySiteId(int siteId , Integer type) {
		int num = colDAO.findColNumBySiteId(siteId , NumberUtil.getInt(type));
		return num;
	}
	  
	/**
	 * 处理默认图片
	 * @param col col
	 * @return  ColFormBean
	 * @throws OperationException    设定参数 .
	*/
	private ColFormBean excuteDefaultPic(ColFormBean col) throws OperationException {
		if(col == null){
			return col;
		} 
		String path = "";
		if(NumberUtil.getInt(col.getIid()) > 0){
			path = BaseInfo.getRealPath() + "/web/site" + col.getSiteId() 
				 + "/col/" + col.getIid() + "/";
		}else{
			path = BaseInfo.getRealPath() + "/web/site" + col.getSiteId() 
				 + "/col/temp" + col.getUserId() + "/";
		}  
		String demopic = "";
		String distinctpic = "";
		int randompic = (int) (Math.random()*8+1);
		// 图标上传
		if (col.getIconFile() != null && !col.getIconFile().isEmpty()) {
			String iconPath = ""; 
			if(NumberUtil.getInt(col.getIid()) > 0){
				iconPath = this.findFilePath(col.getIconFile(), StaticValues.ICONPIC_NAME, col); 
			}else{
				iconPath = this.findFilePath(col.getIconFile(), StaticValues.ICONPIC_NAME); 
			}
			col.setImgUuid(UUID.randomUUID().toString().replace("-", ""));
			col.setIconPath(iconPath);
		//栏目Icon图标未上传，则使用自带的图标作为订阅图标
		} else if (NumberUtil.getInt(col.getIid()) <= 0 || (NumberUtil.getInt(col.getIid()) > 0 
				   && StringUtil.isEmpty(col.getIconPath()))){  
			demopic = BaseInfo.getRealPath() + "/resources/jmp/col/images/icon/" + randompic+".png";
			distinctpic = path + StaticValues.ICONPIC_NAME + ".png";
			File demoFile = new File(demopic);
			File desFile = new File(distinctpic);
			boolean isCopy = FileUtil.copyFile(demoFile, desFile);
			if (isCopy) { 
				col.setIconPath(distinctpic);
			}
		}
		//应用图标上传
		if(col.getAppIconFile() !=null && !col.getAppIconFile().isEmpty()){
		    String appIconPath = "";
		    if(NumberUtil.getInt(col.getIid()) > 0){
		        appIconPath = this.findFilePath(col.getAppIconFile(), StaticValues.APPICONPIC_NAME, col); 
            }else{
                appIconPath = this.findFilePath(col.getAppIconFile(), StaticValues.APPICONPIC_NAME); 
            }
            col.setImgUuid(UUID.randomUUID().toString().replace("-", ""));
            col.setAppIconPath(appIconPath);
		}
		return col;
	}
	
	/**
	 * 检查附件类型
	 * @param col
	 *            栏目实体
	 * @return
	 * @throws OperationException
	 *             界面异常
	 */
	private void checkFileType(ColFormBean col) throws OperationException {
		if(col == null){
			return;
		} 
		//首图
		if (col.getFirstPicFile()!=null && !col.getFirstPicFile().isEmpty()) {
			MultipartFileInfo firstfile = MultipartFileInfo.getInstance(col.getFirstPicFile()); 
			if(Configs.getConfigs().getPicFileType().indexOf(firstfile.getFileType().toLowerCase()) == -1){
				throw new OperationException("首图类型不正确，请重新上传！");
			}
			if((long) Configs.getConfigs().getPicFileSize()*1024*1024 < firstfile.getSize()){
				throw new OperationException("首图大小不能超过" + Configs.getConfigs().getPicFileSize() + "M！");
			}
		}
		//背景图
		if (col.getBackFile()!=null && !col.getBackFile().isEmpty()) {
			MultipartFileInfo backfile = MultipartFileInfo.getInstance(col.getBackFile());  
			if(Configs.getConfigs().getPicFileType().indexOf(backfile.getFileType().toLowerCase()) == -1){
				throw new OperationException("背景图类型不正确，请重新上传！");
			}
			if((long) Configs.getConfigs().getPicFileSize()*1024*1024<backfile.getSize()){
				throw new OperationException("背景图大小不能超过" + Configs.getConfigs().getPicFileSize()+"M！");
			}
		}
		//订阅图标
		if (col.getIconFile()!=null && !col.getIconFile().isEmpty()) {
			MultipartFileInfo iconfile = MultipartFileInfo.getInstance(col.getIconFile()); 
			long size = iconfile.getSize();
			if(Configs.getConfigs().getPicFileType().indexOf(iconfile.getFileType().toLowerCase()) == -1){
				throw new OperationException("订阅图标类型不正确，请重新上传！");
			}
			if((long) Configs.getConfigs().getPicFileSize()*1024*1024 < size){
				throw new OperationException("订阅图标大小不能超过" + Configs.getConfigs().getPicFileSize()+"M！");
			}
		} 
	}
    
	/**
	 * 查询offLine
	 * @param siteId
	 * @return
	 */
	public List<Col> findAllOfflineBySiteId(Integer siteId){
		int id = NumberUtil.getInt(siteId);
		if(id <= 0){
			return null;
		}
		return colDAO.findAllOfflineBySiteId(id);
	}
	
	/**
	 * 通过网站id查找栏目
	 * @param siteId siteId
	 * @return    设定参数 .
	*/
	public List<Col> findColBySiteId(int siteId){
		if(siteId <= 0){
			return null;
		}
		return colDAO.findColBySiteId(siteId);
	}
	
	/**
	 * 获得频道下栏目list
	 * @param chanid 频道id
	 * @return List<Col>
	 */
	public List<Col> findColByChanId(int chanid){
		return colDAO.findColByChanId(chanid);
	}
	
	/**
	 * 获得该网站下所有支持订阅的栏目
	 * @param siteId 网站id
	 * @return List<Col>
	 */
	public List<Col> findSubscribeColBySiteId(int siteId){
		if(siteId <= 0){
			return null;
		}
		return colDAO.findSubscribeColBySiteId(siteId);
	}
	
	/**
	 * 获取最新的信息变动时间
	 * @param colId
	 *             栏目ID
	 */
	public void modifyUpdateTime(int colId){
		Col col = colDAO.findByIid(colId);
		col.setLastUpdateTime(new Date());
		colDAO.update(col);
		CacheUtil.removeKey(StaticValues.CACHE_COL, colId+"");
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
	 * 查询父id和网站id
	 * @param cateid
	 * @param siteid
	 * @param orderid
	 * @return
	 */
	public List<Col> findByPidAndSiteId(Integer cateid, Integer siteid, Integer orderid) {
		List<Col> colList = colDAO.findByPidAndSiteId(cateid, siteid , orderid);
		return colList;
	}

	/**
	 * 根据应用id查找所有栏目
	 * @param iid
	 * @return
	 */
	public List<Col> findByLightAppId(Integer iid, Integer siteid) {
		List<Col> colList = colDAO.findByLightAppId(iid,siteid);
		return colList;
	}
	
	/**
	 * 修改栏目信息
	 * @param col
	 * @return
	 */
	public boolean modify(Col col) {
		boolean isSuccess = colDAO.update(col);
		return isSuccess;
	}

	/**
	 * 修改栏目中应用名
	 * @param iid
	 * @param name
	 */
	public boolean modifyLightAppName(Integer iid, String name) {
		return colDAO.modifyLightAppName(iid, name);
		
	}

	/**
	 * 组织频道新增树
	 * @param siteId 网站id
	 * @return String 栏目树的信息
	 */
	public String findAddColTree(Integer siteId, Integer colId){
		List<Col> colList;
		colList = this.findBySiteIdAndType(siteId);
		//组织树
		Tree tree = Tree.getInstance();
		tree.addNode(TreeNode.getInstance("0",  null, "选择栏目", false).setNocheck(true));
		for (Col col : colList) {
			if(NumberUtil.getInt(col.getIid()) != NumberUtil.getInt(colId)){
				if(col.getType() != 3){
					tree.addNode(TreeNode.getInstance("" + col.getIid(), 
							""+NumberUtil.getInt(col.getPid()), col.getName())
							.setOpen(false));
				}
			}
		}
		return tree.parse();
	}

	/**
	 * 栏目复制/引用
	 * @param ids
	 * @param colId
	 * @param siteId
	 * @param flag 是引用还是复制
	 * @return
	 */
	public boolean copyCol(String ids, String colIds, Integer siteId, String flag) {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		
		List<Integer> colIdList = StringUtil.toIntegerList(colIds, ",");
		if (CollectionUtils.isEmpty(colIdList)) {
			return false;
		}
		boolean success = false ;
		List<Col> colList = colDAO.findByIds(idList);
		
		// 已经是引用或复制的栏目，不应该再作为引用或复制栏目
		for(Col col : colList){
		    if("Q".equals(col.getColType())){
		        throw new OperationException("所选栏目中包含已经被引用或被复制的栏目");
		    }
		    if(col.getType().equals(1)){
		        throw new OperationException("栏目类型不支持类目");
		    }
		}
		
		List<Col> targetList = colDAO.findByIds(colIdList);
		int iid = 0 ;
		
		// 判重处理
		for(Col targetCol : targetList){
			for(Col col : colList){
				int num = this.findNumOfSameName(0, col.getName(), targetCol.getIid(), col.getSiteId());
				if (num > 0) {
					throw new OperationException("【"+targetCol.getName()+"】栏目下的【"+col.getName()+"】栏目名已存在,请重新设置！");
				}
			}
		}
		
		// 插入数据
		String filename = "";
		for(Col targetCol : targetList){
			for(Col col : colList){
			    Col newcol = (Col)col.clone();
			    newcol.setIid(null);
			    newcol.setPid(NumberUtil.getInt(targetCol.getIid()));
			    newcol.setPname(StringUtil.getString(targetCol.getName()));
				String orderId = colDAO.getStrMaxKey(Tables.COL, "orderid");
				newcol.setOrderId(NumberUtil.getInt(orderId));
				newcol.setBookorderId(NumberUtil.getInt(orderId));
				if("Q".equals(flag)){
				    newcol.setColType("Q");
				} else if ("C".equals(flag)){
				    newcol.setColType("C");
				}
				iid = colDAO.insert(newcol);
				success = iid > 0;
				String oldPath = BaseInfo.getRealPath() + col.getIconPath();
				
				File oldFile = new File(oldPath);
				if (oldFile.exists()) {
					filename = col.getIconPath().substring(col.getIconPath().lastIndexOf("/")+1);
					String newPath = "/web/site" + col.getSiteId() + "/col/"+iid+"/"+filename;
					String filePath = BaseInfo.getRealPath()+newPath;
					FileUtil.copyFile(oldPath, filePath);
					newcol.setIconPath(newPath);
					colDAO.update(newcol);
				}
				if (NumberUtil.getInt(col.getTaskId()) != 0) {
					// 插入栏目任务关联表
					ColRelation colRe = new ColRelation();
					colRe.setColId(iid);
					colRe.setColName(col.getName());
					colRe.setTaskId(col.getTaskId());
					colRe.setTaskName(col.getTaskName());
					Integer reIid = colreDAO.insert(colRe);
					if (reIid > 0) {
						success = true;
					}
				}
				// 若为栏目引用,还需记录下引用关系
				if("Q".equals(flag)){
				    ColQuoteRelation colQuoteRel = new ColQuoteRelation();
				    colQuoteRel.setSourceId(NumberUtil.getInt(col.getIid()));
				    colQuoteRel.setAfterId(iid);
				    colQuoteRel.setSiteId(NumberUtil.getInt(col.getSiteId()));
				    colQuoteRelDAO.insert(colQuoteRel);
				}
				// 若栏目是启用状态，需要启用任务
				if (NumberUtil.getInt(col.getEnable()) == 1) {
					Col colTask = colDAO.findTaskColByIid(col.getIid());
					startTask(colTask);
				}
				// 写日志
				logService.add(LogConfig.modcol, LogConfig.opradd, col.getName());
			}
		}
		return success;
	}
	
	/**
	 * 根据type类型查找子栏目
	 * @param colId
	 * @param siteId
	 * @param type
	 * @return
	 */
    public List<Col> findChildColByIidAndType(Integer colId, Integer siteId,
            int type) {
        return colDAO.findChildColByIidAndType(colId, siteId, type);
    }
    
    /**
     * 查出非父栏目的其他信息列表类型栏目
     * @param colId
     * @param siteId
     * @param type
     * @return
     */
    public List<Col> findColByColIdAndType(int colId, Integer siteId, int type) {
        return colDAO.findColByColIdAndType(colId, siteId, type);
    }
    
    public boolean findCountByAppTypeName(String name){
    	
    	return colDAO.findCountByAppTypeName(name);
    }
    
    /**
     * 根据类型获得iid
     */
    public int findIidByAppTypeName(String name){
    	return colDAO.findIidByAppTypeName(name);
    }
    
    public boolean findCountByPidAndKeyValue(int pid, String keyValue){
    	return colDAO.findCountByPidAndKeyValue(pid, keyValue);
    }
    
    public boolean updateCol(String name,String keyValue) {
    	return colDAO.updateCol(name,keyValue);
    }
    
    /**
   	 * 通过keyValue删除栏目
   	 */
   	public boolean deleteBykeyValue(String keyValue) {
   		return colDAO.deleteBykeyValue(keyValue);
   	}
   	
   	/**
   	 * 新增栏目
   	 * @param col
   	 * @return
   	 */
   	public boolean addColl(ColFormBean col){
   		return colDAO.insert(col)>0;
   	}

   	/**
   	 * 根据渠道url查找栏目实体
   	 * @param url
   	 * @param siteId
   	 * @return
   	 */
    public List<Col> findBySourceUrl(String url, int siteId) {
        return colDAO.findBySourceUrl(url, siteId);
    }

    /**
     * 查询该权限下的子栏目
     * @param iid
     * @param siteId
     * @param list
     * @return
     */
    public List<Col> findChildColByIidAndList(int iid, int siteId, List<Integer> list) {
        return colDAO.findChildColByIidAndList(iid, siteId, list);
    }
    
    /**
     * 修改栏目参数
     * @param col
     * @return
     */
    public boolean modifyLightAppIds(Col col){
    	if(col == null){
    		return false;
    	}
    	return colDAO.update(col);
    }
	
}