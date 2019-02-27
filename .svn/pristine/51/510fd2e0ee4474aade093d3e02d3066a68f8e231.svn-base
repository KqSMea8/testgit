package com.hanweb.jmp.cms.service.matters.picture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.MultipartFileInfo;
import com.hanweb.complat.constant.Settings;
import com.hanweb.complat.exception.OperationException;

import com.hanweb.jmp.cms.controller.matters.picture.PictureFormController;
import com.hanweb.jmp.cms.dao.matters.picture.PictureDAO;
import com.hanweb.jmp.cms.entity.matters.picture.Picture;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.util.HadoopUtil;

public class PictureService {

	/**
	 * pictureDAO
	 */
	@Autowired
	private PictureDAO pictureDAO;
	
	/**
	 * 新增图片
	 * @param picture
	 * @return
	 * @throws OperationException
	 */
	public boolean add(PictureFormController picture, File file) throws OperationException{
		if(picture == null){
			return false;
		}
		this.checkFileType(picture);
		int num = this.findNumOfSameName(0, picture.getName(), picture.getSiteId(),picture.getClassId());
		if (num > 0) {
			throw new OperationException("图片名称已存在,请重新设置！");
		}
		boolean isSuccess = true;
		picture.setCreateTime(new Date());
		int iid = pictureDAO.insert(picture);
		picture.setIid(iid);
		if(iid > 0){
			FileUtil.createDir(BaseInfo.getRealPath()+"/web/site"+picture.getSiteId()+"/picture/"+iid+"/");
			File desFile=new File(BaseInfo.getRealPath()+"/web/site"+picture.getSiteId()+"/picture/"+iid+"/");
			FileUtil.copyFileToDirectory(file, desFile);
			FileUtil.deleteFile(file);FileUtil.deleteFile(file);
			picture.setPicturePath("/web/site"+picture.getSiteId()+"/picture/"+iid+"/"+file.getName());
			isSuccess = pictureDAO.update(picture);
			return isSuccess;
		}else{
			return false;
		}
	}
	
	/**
	 * 图片存储路径
	 * @param picturefile
	 * @param picture
	 * @return
	 * @throws OperationException
	 */
	private String findFilePath(MultipartFile picturefile, PictureFormController picture) throws OperationException {
		MultipartFileInfo fileInfo = MultipartFileInfo.getInstance(picturefile);
		if(fileInfo.getFileType()==null){
			return "";
		}
		String uuid = StringUtil.getUUIDString();
		String tmpFilePath = Settings.getSettings().getFileTmp() + uuid  + "." + fileInfo.getFileType();
		String path = BaseInfo.getRealPath()+ "/web/site" + picture.getSiteId() 
					+ "/matter/picture/" + picture.getIid() + "/";
		String absPath = "/web/site" + picture.getSiteId() 
					   + "/matter/picture/" + picture.getIid() + "/";
		String newPath =  "picture_source."+ fileInfo.getFileType();
		File tempFile = new File(tmpFilePath);
		ControllerUtil.writeMultipartFileToFile(tempFile, picturefile);
		BufferedImage imgBuf = null;
		try {
			imgBuf = ImageIO.read(tempFile);
		} catch (IOException e1) {
			throw new OperationException("图片读取失败");
		}
		FileUtil.createDir(path);	// 创建目标文件夹
		File desFile = new File(path + newPath);
		int nSrcWedth = imgBuf.getWidth();
	    int nSrcHeight = imgBuf.getHeight(); 
		try {
			Thumbnails.of(tempFile).size(nSrcWedth, nSrcHeight).outputQuality(0.8f).toFile(desFile);
			HadoopUtil.fileUpload(desFile, absPath+newPath);
		} catch (IOException e) {
			throw new OperationException("图片压缩失败");
		}
		return absPath + newPath; //返回文件路径以供录入数据库
	}
	
	/**
	 * 检查图片类型
	 * @param picture
	 * @throws OperationException
	 */
	private void checkFileType(PictureFormController picture) throws OperationException {
		if(picture==null){
			return;
		} 
		//图片
		if (picture.getPicturefile()!=null && !picture.getPicturefile().isEmpty()) {
			MultipartFileInfo picturefile = MultipartFileInfo.getInstance(picture.getPicturefile());
			if(Configs.getConfigs().getPicFileType().indexOf(picturefile.getFileType().toLowerCase()) == -1){
				throw new OperationException("图片类型不正确，请重新上传！");
			}
			if((long) Configs.getConfigs().getPicFileSize()*1024*1024 < picturefile.getSize()){
				throw new OperationException("图片大小不能超过1M！");
			}
		}
	}
	
	/**
	 * 判重方法
	 * @param id id
	 * @param name 名称
	 * @param siteId 网站ID
	 * @return num
	 */
	public int findNumOfSameName(Integer id, String name, Integer siteId,Integer classId) {
		if (StringUtil.isEmpty(name)) {
			return 0;
		}
		int num = pictureDAO.findNumOfSameName(id, name, siteId,classId);
		return num;
	}
	
	/**
	 * 获取图片
	 * @param iid
	 * @return
	 */
	public Picture findByIid(Integer iid) {
		if(NumberUtil.getInt(iid) <= 0){
			return null;
		}
		return pictureDAO.findByIid(iid);
	}
	
	/**
	 * 删除图片
	 * @param ids
	 * @return
	 * @throws OperationException
	 */
	public boolean removeByIds(String ids,Integer siteId) throws OperationException{
		String[] stringArr = ids.split(",");
		for(String s:stringArr){
			File desFile = new File(BaseInfo.getRealPath()+"/web/site"+siteId+"/picture/"+s);
			FileUtil. deleteDirectory(desFile);
		}
		return pictureDAO.deleteByIds(StringUtil.toIntegerList(ids));
	}
	
	/**
	 * 修改图片
	 * @param pictureType
	 * @return
	 * @throws OperationException
	 */
	public boolean modify(PictureFormController picture) throws OperationException{
		if(picture == null || picture.getIid() < 0){
			return false;
		}
		this.checkFileType(picture);
		int num = this.findNumOfSameName(0, picture.getName(), picture.getSiteId(),picture.getClassId());
		if (num > 1) {
			throw new OperationException("图片名称已存在,请重新设置！");
		}
		if(picture.getPicturefile()!=null){
			String Path = this.findFilePath(picture.getPicturefile(), picture);
			picture.setPicturePath(Path);
			
		}
		boolean isSuccess  = pictureDAO.update(picture);
		return isSuccess;
	}

	/**
	 * 还原
	 * @param ids
	 * @param siteId
	 * @return
	 */
	public boolean modifyIsRemove(int isremove, String ids, Integer siteId) throws OperationException{
		boolean isSuccess = false;
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		isSuccess = pictureDAO.updateIsRemove(isremove,idList);
		return isSuccess;
	}

	/**
	 * 清空回收站的所有内容
	 * @param isremove
	 * @param ids
	 * @param siteId
	 * @return
	 * @throws OperationException
	 */
	public boolean removeAllByIsremove(int isremove,String classId,String [] ids, Integer siteId)throws OperationException {
		for(String s:ids){
			File desFile = new File(BaseInfo.getRealPath()+"/web/site"+siteId+"/picture/"+s);
			FileUtil. deleteDirectory(desFile);
		}
		return pictureDAO.deleteAllByIsremove(isremove,siteId,classId);
	}

	/**
	 * 通过网站id查找图片
	 * @param siteId
	 * @return
	 */
	public List<Picture> findBySiteId(Integer siteId) {
		List<Picture> list = null;
		if(NumberUtil.getInt(siteId) > 0){
			list = pictureDAO.findPicture(siteId);
		}
		return list;
	}

	/**
	 * 通过网站id,分类id,是否删除类别获取图片
	 * @param siteId
	 * @param classId
	 * @param isremove
	 * @return
	 */
	public String [] findBySiteIds(Integer siteId,String classId,Integer isremove) {
		return pictureDAO.findBySiteId(siteId,classId, isremove);
	}

	/**
	 * 修改分类名称时修改实体表中pname
	 * @param siteId
	 * @param iid
	 * @param name
	 * @return
	 */
	public boolean modifyPName(Integer siteId, Integer iid, String name) {
		if(siteId == 0 && iid == 0 && name == null){
			return false;
		}
		return pictureDAO.modifyPName(siteId, iid, name);
	}
	
}