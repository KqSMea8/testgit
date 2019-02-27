package com.hanweb.jmp.cms.service.matters.video;

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
import com.hanweb.jmp.cms.controller.matters.video.VideoFormBean;
import com.hanweb.jmp.cms.dao.matters.video.VideoDAO;
import com.hanweb.jmp.cms.entity.matters.video.Video;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.util.HadoopUtil;

public class VideoService {

	@Autowired
	private VideoDAO videoDAO;
	
	/**
	 * 新增video
	 * @param video
	 * @return
	 * @throws OperationException
	 */
	public boolean add(VideoFormBean video, File file) throws OperationException{;
		if(video == null){
			return false;
		}
		this.checkFileType(video);
		int num = this.findNumOfSameName(0, video.getName(), video.getSiteId(),video.getClassId());
		if (num > 0) {
			throw new OperationException("视频名称已存在,请重新设置！");
		}
		boolean isSuccess = true;
		video.setCreateTime(new Date()); 
		int iid = videoDAO.insert(video);
		video.setIid(iid);
		if(iid > 0){
			FileUtil.createDir(BaseInfo.getRealPath()+"/web/site"+video.getSiteId()+"/video/"+iid+"/");
			File desFile = new File(BaseInfo.getRealPath()+"/web/site"+video.getSiteId()+"/video/"+iid+"/");
			FileUtil.copyFileToDirectory(file, desFile);
			FileUtil.deleteFile(file);
			video.setVideoPath("/web/site"+video.getSiteId()+"/video/"+iid+"/"+file.getName());
			isSuccess = videoDAO.update(video);
			return isSuccess;
		}
		return isSuccess ;
	}
	
	/**
	 * 音视频存储路径
	 * @param videofile
	 * @param video
	 * @return
	 * @throws OperationException
	 */
	private String findFilePath(MultipartFile videofile, VideoFormBean video) throws OperationException {
		MultipartFileInfo fileInfo = MultipartFileInfo.getInstance(videofile);
		if(fileInfo.getFileType()==null){
			return "";
		}
		String uuid = StringUtil.getUUIDString();
		String tmpFilePath = Settings.getSettings().getFileTmp() + uuid  + "." + fileInfo.getFileType();
		String path = BaseInfo.getRealPath()+ "/web/site" + video.getSiteId() 
					+ "/matter/video/" + video.getIid() + "/";
		String absPath = "/web/site" + video.getSiteId() 
					   + "/matter/video/" + video.getIid() + "/";
		String newPath = "video_source."+ fileInfo.getFileType();
		File tempFile = new File(tmpFilePath);
		ControllerUtil.writeMultipartFileToFile(tempFile, videofile);
		BufferedImage imgBuf = null;
		try {
			imgBuf = ImageIO.read(tempFile);
		} catch (IOException e1) {
			throw new OperationException("读取失败");
		}
		FileUtil.createDir(path);	// 创建目标文件夹
		File desFile = new File(path + newPath);
		int nSrcWedth = imgBuf.getWidth();
	    int nSrcHeight = imgBuf.getHeight(); 
		try {
			Thumbnails.of(tempFile).size(nSrcWedth, nSrcHeight).outputQuality(0.8f).toFile(desFile);
			HadoopUtil.fileUpload(desFile, absPath+newPath);
		} catch (IOException e) {
			throw new OperationException("压缩失败");
		}
		return absPath + newPath; //返回文件路径以供录入数据库
	}
	
	/**
	 * 检查音视频类型
	 * @param video
	 * @throws OperationException
	 */
	private void checkFileType(VideoFormBean video) throws OperationException {
		if(video == null){
			return;
		} 
		//音视频
		if (video.getVideofile()!=null && !video.getVideofile().isEmpty()) {
			MultipartFileInfo videofile = MultipartFileInfo.getInstance(video.getVideofile()); 
			if(Configs.getConfigs().getVideoFileType().indexOf(videofile.getFileType().toLowerCase()) == -1){
				throw new OperationException("音视频类型不正确，请重新上传！");
			}
			if((long) Configs.getConfigs().getVideoFileSize()*1024*1024 < videofile.getSize()){
				throw new OperationException("音视频大小不能超过20M！");
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
		int num = videoDAO.findNumOfSameName(id, name, siteId,classId);
		return num;
	}
	
	/**
	 * 查询音视频
	 * @param iid
	 * @return
	 */
	public Video findByIid(Integer iid) {
		if(NumberUtil.getInt(iid) <= 0){
			return null;
		}
		return videoDAO.findByIid(iid);
	}
	
	/**
	 * 删除音视频
	 * @param ids
	 * @return
	 * @throws OperationException
	 */
	public boolean removeByIds(String ids,Integer siteId) throws OperationException{
		String[] stringArr = ids.split(",");
		for(String s:stringArr){
			File desFile = new File(BaseInfo.getRealPath()+"/web/site"+siteId+"/video/"+s);
			FileUtil. deleteDirectory(desFile);
		}
		return videoDAO.deleteByIds(StringUtil.toIntegerList(ids));
	}
	
	/**
	 * 修改音视频
	 * @param videoType
	 * @return
	 * @throws OperationException
	 */
	public boolean modify(VideoFormBean video) throws OperationException{
		if(video == null || video.getIid() < 0){
			return false;
		}
		this.checkFileType(video);
		int num = this.findNumOfSameName(0, video.getName(), video.getSiteId(),video.getClassId());
		if (num > 1) {
			throw new OperationException("音视频名称已存在,请重新设置！");
		}
		if(video.getVideofile()!=null){
			String Path = this.findFilePath(video.getVideofile(), video);
			video.setVideoPath(Path);
		}
		boolean isSuccess  = videoDAO.update(video);
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
		isSuccess = videoDAO.updateIsRemove(isremove,idList);
		return isSuccess;
	}
	
	/**
	 * 删除所有音视频
	 * @param isremove
	 * @param classId
	 * @param ids
	 * @param siteId
	 * @return
	 * @throws OperationException
	 */
	public boolean removeAllByIsremove(int isremove,String classId,String [] ids, Integer siteId)throws OperationException {
		for(String s:ids){
			File desFile = new File(BaseInfo.getRealPath() + "/web/site" + siteId + "/video/" + s);
			FileUtil. deleteDirectory(desFile);
		}
		return videoDAO.deleteAllByIsremove(isremove,siteId,classId);
	}
	
	/**
	 * 通过网站id,分类id,是否删除来查询音视频
	 * @param siteId
	 * @param classId
	 * @param isremove
	 * @return
	 */
	public String [] findBySiteIds(Integer siteId,String classId,Integer isremove) {
		return videoDAO.findBySiteId(siteId,classId, isremove);
	}

	/**
	 * 修改分类名称时修改实体表中pname
	 * @param siteId
	 * @param iid
	 * @param name
	 */
	public boolean modifyPName(Integer siteId, Integer iid, String name) {
		if(siteId == 0 && iid == 0 && name == null){
			return false;
		}
		return videoDAO.modifyPName(siteId, iid, name);
	}
	
}