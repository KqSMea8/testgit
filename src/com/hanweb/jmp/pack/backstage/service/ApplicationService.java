package com.hanweb.jmp.pack.backstage.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.MultipartFileInfo;
import com.hanweb.complat.exception.OperationException;

import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.pack.backstage.controller.application.ApplicationFormBean;
import com.hanweb.jmp.pack.backstage.dao.ApplicationDAO;
import com.hanweb.jmp.pack.backstage.entity.Application;
import com.hanweb.setup.dao.DataInitDAO;

public class ApplicationService {
	
	@Autowired
	private ApplicationDAO applicationDAO;
	
	@Autowired
	private DataInitDAO dataInitDAO;
	
	public boolean add(ApplicationFormBean application) throws OperationException{
		if(application ==  null){
			return false;
		}
		
		//检查上传文件类型
		checkFileType(application);
		int num = applicationDAO.findNumByName(NumberUtil.getInt(application.getIid()), 
				application.getName(), NumberUtil.getInt(application.getSiteId()));
		if(num > 0){
			throw new OperationException("应用名已存在,请重新设置！");
		}
		application.setOrderId(findMinOrderIdBySiteId(application.getSiteId())-1);
		int iid = applicationDAO.insert(application);
		if(iid > 0){
			application.setIid(iid);
			String iconPath = null;
			MultipartFile iconFile = application.getIconFile();
			if(!iconFile.isEmpty()){
				iconPath = this.findFilePath(application.getIconFile(), "logo", application);
				application.setLogoPath(iconPath);
			}
				
			boolean isSuccess = applicationDAO.update(application);
			return isSuccess;
		}else{
			return false;
		}
	    
	}
	
	/**
	 * 通过id删除实体
	 * @param ids
	 * @return
	 * @throws OperationException
	 */
	public boolean removeByIds(String ids) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		
		return applicationDAO.deleteByIds(idList);
	}
	
	
	/**
	 * 检查附件类型
	 * @param col
	 *            栏目实体
	 * @return
	 * @throws OperationException
	 *             界面异常
	 */
	private void checkFileType(ApplicationFormBean application) throws OperationException {
		if(application==null){
			return;
		} 
		//应用图标
		if (application.getIconFile()!=null && !application.getIconFile().isEmpty()) {
			MultipartFileInfo iconfile = MultipartFileInfo.getInstance(application.getIconFile()); 
			long size = iconfile.getSize();
			if(Configs.getConfigs().getPicFileType().indexOf(
					iconfile.getFileType().toLowerCase()) == -1){
				throw new OperationException("应用图标类型不正确，请重新上传！");
			}
			if((long) Configs.getConfigs().getPicFileSize()*1024*1024 < size){
				throw new OperationException("应用图标大小不能超过"+
						Configs.getConfigs().getPicFileSize()+"M！");
			}
		} 
	}
	
	/**
	 * 获取文件路径
	 * @param file
	 * @param fileName
	 * @param application
	 * @return
	 */
	private String findFilePath(MultipartFile file, String fileName, Application application) {
		String path = BaseInfo.getRealPath() 
			        + "/web/site"+application.getSiteId()+"/application/"+application.getIid()+"/" ;
		String absPath = "/web/site"+application.getSiteId()+"/application/"+application.getIid()+"/" ;
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
	 * 根据iid查找实体
	 * @param iid
	 * @return
	 */
	public Application findByIid(int iid){
		if(iid <= 0){
			return null;
		}
		return applicationDAO.findByIid(iid);
	}
	
	/**
	 * 修改
	 * @param application
	 * @return
	 * @throws OperationException
	 */
	public boolean modify(ApplicationFormBean application) throws OperationException {
		if(application == null){
			return false;
		}
		checkFileType(application);
		int num = applicationDAO.findNumByName(NumberUtil.getInt(application.getIid()), 
				application.getName(), NumberUtil.getInt(application.getSiteId()));
		if(num > 0){
			throw new OperationException("应用名已存在,请重新设置！");
		}
		boolean isSuccess =  applicationDAO.update(application);
		if(isSuccess){
			String iconPath = null;
			MultipartFile iconFile = application.getIconFile();
			if(!iconFile.isEmpty()) {
				iconPath = this.findFilePath(application.getIconFile(), "logo", application);
				application.setLogoPath(iconPath);
			}
		    isSuccess = applicationDAO.update(application);
			return isSuccess;
		}else{
			return false;
		}
	}
	
	/**
	 * 根据网站id查找实体
	 * @param siteId
	 * @return
	 */
	public List<Application> findBySiteId(Integer siteId){
		List<Application> applist = new ArrayList<Application>();
		applist =  applicationDAO.findBySiteId(NumberUtil.getInt(siteId));
 		return applist;
	}
	
	/**
	 * 根据网站id新增应用
	 * @param siteid
	 * @return
	 */
	public boolean addDefaultAppsBySiteId(Integer siteid) { 
		List<String> sqls = FileUtil.readLines(new File(BaseInfo.getRealPath()
				          + "/WEB-INF/sql/init/application.sql"), "utf-8");
		if (sqls != null) {
			for (String sql : sqls) {
				if (StringUtils.isBlank(sql)) {
					continue;
				}
				if (sql.endsWith(";")) {
					sql = sql.substring(0, sql.length() - 1);
				}
				String abPath = "/web/site" + siteid 
				+ "/application/temp/logo.png";
				String path = BaseInfo.getRealPath() + abPath;
				String demopic = BaseInfo.getRealPath() + "/resources/jmp/application/images/icon/7.png";
				String distinctpic = path ;
				File demoFile = new File(demopic);
				File desFile = new File(distinctpic);
				boolean isCopy = FileUtil.copyFile(demoFile, desFile);
			
				if (isCopy) { 
					sql = sql.replace("{logopath}", "'"+abPath+"'");
				}
				String createtime = DateUtil.dateToString(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS);
					sql = sql.replace("{siteid}", ""+siteid);
					sql = sql.replace("{createtime}", "'"+createtime+"'");
				Query query = dataInitDAO.createQuery(sql);
				dataInitDAO.execute(query);
			}
		}
		return true;
	}
	
	/**
	 * 通过网站id查找最小排序id
	 * @param siteId
	 * @return
	 */
	public int findMinOrderIdBySiteId(Integer siteId){
		if(NumberUtil.getInt(siteId) <= 0){
			return 0;
		}
		return applicationDAO.findMinOrderIdBySiteId(siteId);
	}
	
	/**
	 * 更新应用的orderid
	 * @param ids
	 *            ids
	 * @param orderids
	 *            orderids
	 * @return boolean
	 * @throws OperationException OperationException
	 */
	public boolean modifyOrderIdById(String ids, String orderids) throws OperationException{
		if (ids == null || ids.length() == 0 || orderids == null || orderids.length() == 0) {
			return false;
		}
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		List<Integer> ordersList = StringUtil.toIntegerList(orderids, ",");
		boolean isSuccess = false;
		for (int i = 0, len = idsList.size(); i < len; i++) {
			isSuccess = applicationDAO.updateOrderIdById(idsList.get(i), ordersList.get(i));
		}
		return isSuccess;
	}
	
}