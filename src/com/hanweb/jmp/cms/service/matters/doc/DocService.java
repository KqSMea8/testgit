package com.hanweb.jmp.cms.service.matters.doc;

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

import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.util.HadoopUtil;
import com.hanweb.jmp.cms.dao.matters.doc.DocDAO;
import com.hanweb.jmp.cms.entity.matters.doc.Doc;
import com.hanweb.jmp.cms.controller.matters.doc.DocFormBean;

public class DocService {

	@Autowired
	private DocDAO docDAO;
	
	/**
	 * 新增附件
	 * @param doc
	 * @return
	 * @throws OperationException
	 */
	public boolean add(DocFormBean doc,File file) throws OperationException{
		if(doc == null){
			return false;
		}
		this.checkFileType(doc);
		int num = this.findNumOfSameName(0, doc.getName(), doc.getSiteId(),doc.getClassId());
		if (num > 0) {
			throw new OperationException("附件名称已存在,请重新设置！");
		}
		boolean isSuccess = true;
		doc.setCreateTime(new Date()); 
		int iid = docDAO.insert(doc);
		doc.setIid(iid);
		if(iid > 0){
			FileUtil.createDir(BaseInfo.getRealPath()+"/web/site"+doc.getSiteId()+"/doc/"+iid+"/");
			File desFile = new File(BaseInfo.getRealPath()+"/web/site"+doc.getSiteId()+"/doc/"+iid+"/");
			FileUtil.copyFileToDirectory(file, desFile);
			FileUtil.deleteFile(file);
			doc.setDocPath("/web/site"+doc.getSiteId()+"/doc/"+iid+"/"+file.getName());
			isSuccess = docDAO.update(doc);
			return isSuccess;
		}
		return isSuccess ;
	}
	
	/**
	 * 附件存储路径
	 * @param docfile
	 * @param doc
	 * @return
	 * @throws OperationException
	 */
	private String findFilePath(MultipartFile docfile, DocFormBean doc) throws OperationException {
		MultipartFileInfo fileInfo = MultipartFileInfo.getInstance(docfile);
		if(fileInfo.getFileType()==null){
			return "";
		}
		String uuid = StringUtil.getUUIDString();
		String tmpFilePath = Settings.getSettings().getFileTmp() + uuid  + "." + fileInfo.getFileType();
		String path = BaseInfo.getRealPath()+ "/web/site" + doc.getSiteId() 
					+ "/matter/doc/" + doc.getIid() + "/";
		String absPath = "/web/site" + doc.getSiteId() 
					   + "/matter/doc/" + doc.getIid() + "/";
		String newPath =  "doc_source."+ fileInfo.getFileType();
		File tempFile = new File(tmpFilePath);
		ControllerUtil.writeMultipartFileToFile(tempFile, docfile);
		BufferedImage imgBuf = null;
		try {
			imgBuf = ImageIO.read(tempFile);
		} catch (IOException e1) {
			throw new OperationException("附件读取失败");
		}
		FileUtil.createDir(path);	// 创建目标文件夹
		File desFile = new File(path + newPath);
		int nSrcWedth = imgBuf.getWidth();
	    int nSrcHeight= imgBuf.getHeight(); 
		try {
			Thumbnails.of(tempFile).size(nSrcWedth, nSrcHeight).outputQuality(0.8f).toFile(desFile);
			HadoopUtil.fileUpload(desFile, absPath+newPath);
		} catch (IOException e) {
			throw new OperationException("附件压缩失败");
		}
		return absPath + newPath; //返回文件路径以供录入数据库
	}
	
	/**
	 * 检查附件类型
	 * @param doc
	 * @throws OperationException
	 */
	private void checkFileType(DocFormBean doc) throws OperationException {
		if(doc==null){
			return;
		} 
		//图片
		if (doc.getDocfile()!=null && !doc.getDocfile().isEmpty()) {
			MultipartFileInfo docfile = MultipartFileInfo.getInstance(doc.getDocfile()); 
			if(Configs.getConfigs().getDocFileType().indexOf(docfile.getFileType().toLowerCase()) == -1){
				throw new OperationException("附件类型不正确，请重新上传！");
			}
			if((long) Configs.getConfigs().getDocFileSize()*1024*1024 < docfile.getSize()){
				throw new OperationException(
						"附件大小不能超过10M！");
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
		int num = docDAO.findNumOfSameName(id, name, siteId,classId);
		return num;
	}
	
	/**
	 * 查询iid
	 * @param iid
	 * @return
	 */
	public Doc findByIid(Integer iid) {
		if(NumberUtil.getInt(iid) <= 0){
			return null;
		}
		return docDAO.findByIid(iid);
	}
	
	/**
	 * 删除附件
	 * @param ids
	 * @return
	 * @throws OperationException
	 */
	public boolean removeByIds(String ids,Integer siteId) throws OperationException{
		String[] stringArr = ids.split(",");
		for(String s:stringArr){
			File desFile=new File(BaseInfo.getRealPath()+"/web/site"+siteId+"/doc/"+s);
			FileUtil.deleteDirectory(desFile);
		}
		return docDAO.deleteByIds(StringUtil.toIntegerList(ids));
	}
	
	/**
	 * 修改附件
	 * @param docType
	 * @return
	 * @throws OperationException
	 */
	public boolean modify(DocFormBean doc) throws OperationException{
		if(doc == null || doc.getIid() < 0){
			return false;
		}
		this.checkFileType(doc);
		int num = this.findNumOfSameName(0, doc.getName(), doc.getSiteId(),doc.getClassId());
		if (num > 1) {
			throw new OperationException("附件名称已存在,请重新设置！");
		}
		if(doc.getDocfile()!=null){
			String path = this.findFilePath(doc.getDocfile(), doc);
			doc.setDocPath(path);
		}
		boolean isSuccess  = docDAO.update(doc);
		return isSuccess;
	}
	
	/**
	 * 还原附件
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
		isSuccess = docDAO.updateIsRemove(isremove,idList);
		return isSuccess;
	}
	
	/**
	 * 全部清空
	 * @param isremove
	 * @param classId
	 * @param ids
	 * @param siteId
	 * @return
	 * @throws OperationException
	 */
	public boolean removeAllByIsremove(int isremove,String classId,String [] ids, Integer siteId)throws OperationException {
		for(String s:ids){
			File desFile = new File(BaseInfo.getRealPath()+"/web/site"+siteId+"/doc/"+s);
			FileUtil. deleteDirectory(desFile);
		}
		return docDAO.deleteAllByIsremove(isremove,siteId,classId);
	}
	
	/**
	 * 根据siteId，classId，isremove查找附件
	 * @param siteId
	 * @param classId
	 * @param isremove
	 * @return
	 */
	public String [] findBySiteId(Integer siteId, String classId, Integer isremove) {
		return docDAO.findBySiteId(siteId,classId, isremove);
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
		return docDAO.modifyPName(siteId, iid, name);
	}
	
}