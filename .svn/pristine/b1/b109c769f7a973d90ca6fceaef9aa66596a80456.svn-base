package com.hanweb.jmp.apps.service.read;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.MultipartFileInfo;
import com.hanweb.complat.constant.Settings;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.support.controller.CurrentUser;

import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.util.HadoopUtil;
import com.hanweb.jmp.apps.dao.read.ReadDAO;
import com.hanweb.jmp.apps.entity.read.Read;
import com.hanweb.jmp.apps.controller.read.ReadFormBean;

public class ReadService {
	
	/**
	 * readDao
	 */
	@Autowired
	private ReadDAO readDAO;
	
	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;
	 
	/**
	 * 新增阅读
	 * @param read 阅读实体
	 * @return true/false
	 */
	public boolean add(ReadFormBean read) throws OperationException{
		if(read == null){
			return false;
		}
		int oprType = 1;
		this.checkFileType(read); //判断图片上传类型
		boolean isSuccess = true;
		
		read.setOrderId(readDAO.getMinOrderId(read.getPid()) - 1);
		read.setCreateTime(new Date());
		if(read.getType() ==0) {
			int num = this.findNumOfSameName(0, read.getName(), read.getSiteId(), 0, read.getPid());
			if (num > 0) {
				throw new OperationException("分类名称已存在,请重新设置！");
			}
			int iid = readDAO.insert(read);
			read.setIid(iid);
			if(iid > 0){
				if(!read.getPicFile().isEmpty()){
					String picPath = this.findFilePath(read.getPicFile(), read, "smallpic_source", oprType);
					read.setPicPath(picPath);
					isSuccess = readDAO.update(read);
					return isSuccess;
				}
			} else{
				return false;
			}
		}else if(read.getType()==1){
			int num = this.findNumOfSameName(0, read.getName(), read.getSiteId(), 1, read.getPid());
			if (num > 0) {
				throw new OperationException("书籍名称已存在,请重新设置！");
			}
			int iid = readDAO.insert(read);
			read.setIid(iid);
			if(iid > 0){
				if(!read.getPicFile().isEmpty()){
					String picPath = this.findFilePath(read.getPicFile(), read, "smallpic_source", oprType);
					read.setPicPath(picPath);
				}
				if(!read.getBigFile().isEmpty()){
					String bigPath = this.findFilePath(read.getBigFile(), read, "bigpic_source", oprType);
					read.setBigPath(bigPath);
				}
				if(!read.getBookFile().isEmpty()){
					double size = read.getBookFile().getSize();
					size = size/(1024*1024);
					String filePath = this.findFilePath(read.getBookFile(), read, "book_source", oprType);
					read.setPicsize(StringUtil.getString(size));
					read.setFilePath(filePath);
					read.setChangeTime(new Date());
				}
				isSuccess = readDAO.update(read);
				if(isSuccess) {
					int flag = readDAO.findMaxFlag(NumberUtil.getInt(read.getSiteId()));
					readDAO.updateFlag(flag, NumberUtil.getInt(read.getSiteId()));
				}
				return isSuccess;
			} else {
				return false;
			}
		}
		
		
		return isSuccess;
	}
	
	/**
	 * 判重处理
	 * @param id id
	 * @param name 名称
	 * @param siteId 网站ID
	 * @param type 类型
	 * @param pid 父ID
	 * @return num
	 */
	public int findNumOfSameName(Integer id, String name, Integer siteId, Integer type, Integer pid) {
		if (StringUtil.isEmpty(name)) {
			return 0;
		}
		int num = readDAO.findNumOfSameName(id, name, siteId, type, pid);
		return num;
	}
	
	/**
	 * 根据ID查询阅读实体
	 * @param iid id
	 * @return read
	 */
	public Read findById(int iid) {
		return readDAO.findByIid(iid);
	}
	
	/**
	 * 根据网站ID 和 id查找实体
	 * @param iid id
	 * @param siteid  网站ID
	 * @return 实体
	 */
	public Read findByIdAndSiteId(int iid, int siteid) {
		return readDAO.findByIidAndSiteId(iid, siteid);
	}
	
	/**
	 * 根据父id查询子集
	 * @param pid 父id
	 * @param siteid 网站ID
	 * @return list
	 */
	public List<Read> findByPid(int pid, int siteid){
		return readDAO.findByPid(pid, siteid);
	}
	
	/**
	 * 修改阅读
	 * @param read 阅读实体
	 * @return true/false
	 * @throws OperationException OperationException
	 */
	public boolean modify(ReadFormBean read) throws OperationException{
		if(read == null || read.getIid() <= 0){
			return false;
		}
		int oprType =2;
		this.checkFileType(read);
		if(read.getType() == 0){
			int num = this.findNumOfSameName(read.getIid(), read.getName(), read.getSiteId(), 0, read.getPid());
			if (num > 0) {
				throw new OperationException("分类名称已存在,请重新设置！");
			}
			if(read.getPicFile()!=null && read!=null){
				if(!read.getPicFile().isEmpty()){
					String picPath = this.findFilePath(read.getPicFile(), read, "smallpic_source", oprType);
					read.setPicPath(picPath);
				}
			}
		} else if (read.getType() == 1){
			int num = this.findNumOfSameName(read.getIid(), read.getName(), read.getSiteId(), 1, read.getPid());
			if (num > 0) {
				throw new OperationException("书籍名称已存在,请重新设置！");
			}
			if(read.getPicFile() != null && !read.getPicFile().isEmpty()){
				String picPath = this.findFilePath(read.getPicFile(), read, "smallpic_source", oprType);
				read.setPicPath(picPath);
			}
			if(read.getBigFile() != null && !read.getBigFile().isEmpty()){
				String bigPath = this.findFilePath(read.getBigFile(), read, "bigpic_source", oprType);
				read.setBigPath(bigPath);
			}
			if(read.getBookFile() != null && !read.getBookFile().isEmpty()){
				double size = read.getBookFile().getSize();
				size = size/(1024*1024);
				String filePath = this.findFilePath(read.getBookFile(), read, "book_source", oprType);
				read.setPicsize(StringUtil.getString(size));
				read.setFilePath(filePath);
				read.setChangeTime(new Date());
			}
		}
				
		boolean isSuccess  = readDAO.update(read);
		if(isSuccess) {
			int flag = readDAO.findMaxFlag(NumberUtil.getInt(read.getSiteId()));
			readDAO.updateFlag(flag, NumberUtil.getInt(read.getSiteId()));
		}
		return isSuccess;
	}
	
	/**
	 * 删除分类
	 * @param ids
	 *            分类ID串 如:1,2,3
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean removeByIds(String ids) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		
		boolean hasSubInfo = false;
		hasSubInfo = this.checkSubInfo(ids);
		List<Read> readList = readDAO.findByIds(idList);
		if (hasSubInfo) {
			throw new OperationException("所选分类下存在分类或书刊,请先删除!");
		}
		
		boolean isSuccess = false;
		isSuccess = readDAO.deleteByIds(idList); // 删分类
		if(isSuccess) {
			CurrentUser currentUser = UserSessionInfo.getCurrentUser();
			Integer siteId = currentUser.getSiteId();
			int flag = readDAO.findMaxFlag(NumberUtil.getInt(siteId));
			readDAO.updateFlag(flag, NumberUtil.getInt(siteId));
		}
		for(Read readEn : readList){
			String path = readEn.getPicPath();  // 删除文件
			path = path.substring(0, path.lastIndexOf("/"));
			fileUtil.deleteDirectory(fileUtil.getAbsolutePath(path));
//			FileUtil.deleteDirectory(new File(BaseInfo.getRealPath() + "/" + path));
		}
		return isSuccess;
	}
	
	/**
	 * 查询所有的阅读集合
	 * @param iid id
	 * @param siteId 网站ID
	 * @return list
	 */
	public List<Read> findAllCol(Integer iid, int siteId){
		return readDAO.findAllCol(iid, siteId);
	}
	
	/**
	 * 查找阅读实体集
	 * @param iid id
	 * @param siteId 网站ID
	 * @return list
	 */
	public List<Read> findOrder(Integer iid, int siteId){
		return readDAO.findOrder(iid, siteId);
	}
	
	/**
	 * 更新排序ID
	 * @param ids id串
	 * @param orderids 阅读ID串
	 * @return true/false
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean modifyOrderIdById(String ids, String orderids) throws OperationException{
		if (ids == null || ids.length() == 0 || orderids == null || orderids.length() == 0) {
			return false;
		}
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		List<Integer> ordersList = StringUtil.toIntegerList(orderids, ",");
		boolean isSuccess = false;
		for (int i = 0, len = idsList.size(); i < len; i++) {
			isSuccess = readDAO.updateOrderIdById(ordersList.get(i), idsList.get(i));
		}
		if(isSuccess) {
			CurrentUser currentUser = UserSessionInfo.getCurrentUser();
			Integer siteId = currentUser.getSiteId();
			int flag = readDAO.findMaxFlag(NumberUtil.getInt(siteId));
			readDAO.updateFlag(flag, NumberUtil.getInt(siteId));
		}
		return isSuccess;
	}
	
	/**
	 * 检查是否有子栏目
	 * @param ids
	 *            栏目ID串 如:1,2,3
	 * @return true - 有子栏目<br/>
	 *         false - 无子栏目
	 */
	public boolean checkSubInfo(String ids) {
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idsList)) {
			return false;
		}
		int num = readDAO.findCountSubInfo(idsList);
		return num > 0;
	} 
	
	/**
	 * 文件存放
	 * @param file 文件
	 * @param book 阅读实体
	 * @param fileName 文件名
	 * @param oprType 	操作类型
	 * @return json
	 * @throws OperationException OperationException
	 */
	private String findFilePath(MultipartFile file, ReadFormBean book, String fileName, int oprType) throws OperationException{
		MultipartFileInfo fileInfo = MultipartFileInfo.getInstance(file);
		if(fileInfo.getFileType() == null){
			return "";
		}
		String uuid = StringUtil.getUUIDString();
		String tmpFilePath = Settings.getSettings().getFileTmp() + uuid  + "." + fileInfo.getFileType();
		String path = BaseInfo.getRealPath() + "/web/site" + book.getSiteId()
					+ "/read/" + book.getIid() + "/";
		String absPath = "/web/site" + book.getSiteId() +"/read/"+book.getIid()+"/";
		String newPath = StringUtil.getString(fileName)
					   + "."+ fileInfo.getFileType();
		File tempFile = new File(tmpFilePath);
		ControllerUtil.writeMultipartFileToFile(tempFile, file);
		FileUtil.createDir(path);	// 创建目标文件夹
		BufferedImage imgBuf = null;
		try {
			imgBuf = ImageIO.read(tempFile);
		} catch (IOException e) {
			FileUtil.deleteDirectory(new File(path));	//抛出异常前删除文件夹
			oprReadEntity(book, oprType);
			throw new OperationException("图片读取失败");
		}
		if("smallpic_source".equals(fileName)){
			if(imgBuf.getWidth()!=160 || imgBuf.getHeight()!=220){
				oprReadEntity(book, oprType);
				throw new OperationException("封面图格式不正确，请上传160*220的图片！");
			}else{
				File desFile = new File(path + newPath);
				int nSrcWedth = imgBuf.getWidth();
			    int nSrcHeight = imgBuf.getHeight(); 
				try {
					Thumbnails.of(tempFile).size(nSrcWedth, nSrcHeight).outputQuality(0.8f).toFile(desFile);
				} catch (IOException e) {
					oprReadEntity(book, oprType);
					throw new OperationException("封面图压缩失败");
				}
			}
		} else if("bigpic_source".equals(fileName) && NumberUtil.getInt(book.getType())== 1){
			if(imgBuf.getWidth()!= 640 || imgBuf.getHeight()!=360){
				oprReadEntity(book, oprType);
				throw new OperationException("海报图格式不正确，请上传640*360的图片！");
			} else {
				File desFile = new File(path + newPath);
				int nSrcWedth = imgBuf.getWidth();
			    int nSrcHeight= imgBuf.getHeight(); 
				try {
					Thumbnails.of(tempFile).size(nSrcWedth, nSrcHeight).outputQuality(0.8f).toFile(desFile);
				} catch (IOException e) {
					oprReadEntity(book, oprType);
					throw new OperationException("海报图压缩失败");
				}
				
			}
		}else if("book_source".equals(fileName) && NumberUtil.getInt(book.getType())== 1){
			File desFile = new File(path + newPath);
			FileUtil.copyFile(tempFile, desFile);
		}
		HadoopUtil.fileUpload(new File(path + newPath), absPath + newPath);
	    return absPath + newPath; //返回文件路径以供录入数据库
	}
	
	/**
	 * 当新增时，若附件处理失败， 则删除新增的阅读实体
	 * 
	 * @param book		阅读实体
	 * @param oprType	操作类型
	 */
	private void oprReadEntity(ReadFormBean book, int oprType) {
		if(book != null && oprType == 1){
			readDAO.deleteById(book.getIid());
		}
		
	}

	/**
	 * 检查所上传文件的类型
	 * @param read 阅读实体 
	 * @throws OperationException OperationException
	 */
	private void checkFileType(ReadFormBean read) throws OperationException {
		if(read==null){
			return;
		} 
		//封面图
		if (read.getPicFile()!=null && !read.getPicFile().isEmpty()) {
			MultipartFileInfo iconfile = MultipartFileInfo.getInstance(read.getPicFile()); 
			if(Configs.getConfigs().getPicFileType().indexOf(iconfile.getFileType().toLowerCase()) == -1){
				throw new OperationException("封面图类型不正确，请重新上传！");
			}
			if(1*1024*1024 < iconfile.getSize()){
				throw new OperationException("封面图大小不能超过1M！");
			}
		}
		//海报图
		if (read.getBigFile()!=null && !read.getBigFile().isEmpty() && NumberUtil.getInt(read.getType())== 1) {
			MultipartFileInfo iconfile = MultipartFileInfo.getInstance(read.getBigFile()); 
			if(Configs.getConfigs().getPicFileType().indexOf(iconfile.getFileType().toLowerCase()) == -1){
				throw new OperationException("海报图类型不正确，请重新上传！");
			}
			if(1*1024*1024 < iconfile.getSize()){
				throw new OperationException("海报图大小不能超过1M！");
			}
		}
		//文件类型
		if (read.getBookFile()!=null && !read.getBookFile().isEmpty() && NumberUtil.getInt(read.getType())== 1) {
			MultipartFileInfo iconfile = MultipartFileInfo.getInstance(read.getBookFile()); 
			if("pdf".indexOf(iconfile.getFileType().toLowerCase()) == -1){
				throw new OperationException("文件类型不正确，请上传pdf格式文件！");
			}
			if(40*1024*1024 < iconfile.getSize()){
				throw new OperationException("文件大小不能超过40M！");
			}
		}
	}
	
}