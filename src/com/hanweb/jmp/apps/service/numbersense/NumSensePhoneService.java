package com.hanweb.jmp.apps.service.numbersense;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.util.HadoopUtil;
import com.hanweb.jmp.apps.entity.numbersense.NumSenseCol;
import com.hanweb.jmp.apps.entity.numbersense.NumSensePhone;
import com.hanweb.jmp.apps.dao.numbersense.NumSenseColDAO;
import com.hanweb.jmp.apps.dao.numbersense.NumSensePhoneDAO;
import com.hanweb.jmp.apps.controller.numbersense.numsensephone.NumSensePhoneFormBean;

public class NumSensePhoneService {
	
	/**
	 * numSensePhoneDAO
	 */
	@Autowired
	private NumSensePhoneDAO numSensePhoneDAO;
	
	/**
	 * numSenseColDAO
	 */
	@Autowired
	NumSenseColDAO numSenseColDAO;
	
	/**
	 * fileUtil
	 */
	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;
	
	/**
	 * 新增方法
	 * @param phone 号码实体
	 * @return true/false
	 * @throws OperationException OperationException
	 */
	public boolean add(NumSensePhoneFormBean phone, String flag) throws OperationException {
		if(phone == null) {
			return false;
		}
		if(StringUtil.isEmpty(phone.getPhone()) && StringUtil.isEmpty(phone.getTel())){
			throw new OperationException("固定电话、移动电话至少选填一项");
		}
		if(phone.getLogoFile()!=null && flag!=null){
			if(!phone.getLogoFile().isEmpty() && StringUtil.isNotEmpty(flag)){
				this.checkFileType(phone);
			}
		}
		int num = this.findNumOfSameName(0, phone.getName(), phone.getSiteId(), phone.getColId());
		if (num > 0) {
			throw new OperationException("该分类下名称已存在，请重新设置！");
		}
		phone.setOrderId(numSensePhoneDAO.getMinOrderId(NumberUtil.getInt(phone.getColId())) - 1);
		phone.setCreateTime(new Date());
		int iid =  numSensePhoneDAO.insert(phone);
		phone.setIid(iid);
		boolean isSuccess = false;
		if(iid > 0){
			if(phone.getLogoFile()!=null && flag!=null){
				if(!phone.getLogoFile().isEmpty() && StringUtil.isNotEmpty(flag)) {
					String iconPath = this.findFilePath(phone.getLogoFile(), phone);
					phone.setIconPath(iconPath);
					isSuccess = numSensePhoneDAO.update(phone);
					return isSuccess;
				} else {
					return true;
				}
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * 获得指定号码实体
	 * @param iid
	 *            号码ID
	 * @return 号码实体
	 */
	public NumSensePhone findById(int iid) {
		return numSensePhoneDAO.findByIid(iid);
	}
	
	/**
	 * 根据关键字查找号码集合
	 * @param siteId 网站ID
	 * @param pageSize 页码
	 * @param pageNum 页数
	 * @param keyWord 关键字
	 * @return 集合
	 */
	public List<NumSensePhone> findByKeyWord(int siteId, int pageSize, int pageNum, String keyWord){
		List<NumSenseCol> colList = numSenseColDAO.findidsByIssearch(siteId);
		List<Integer> ids = new ArrayList<Integer>();
		if(CollectionUtils.isNotEmpty(colList)) {
			for(NumSenseCol col : colList) {
				ids.add(NumberUtil.getInt(col.getIid()));
			}
			return numSensePhoneDAO.findByKeyWord(siteId, pageSize, pageNum, keyWord, ids);
		} else {
			return null;
		}
	}
	
	/**
	 * 修改号码
	 * @param phone 号码实体
	 * @return true/false
	 * @throws OperationException OperationException
	 */
	public boolean modify(NumSensePhoneFormBean phone, String flag) throws OperationException{
		if(phone == null || phone.getIid() <= 0){
			return false;
		}
		if(phone.getLogoFile()!=null){
			if(!phone.getLogoFile().isEmpty() && StringUtil.isNotEmpty(flag)){
				this.checkFileType(phone);
			}
		}
		int num = this.findNumOfSameName(phone.getIid(), phone.getName(),
						phone.getSiteId(), phone.getColId());
		if (num > 0) {
			throw new OperationException("该分类下名称已存在，请重新设置！");
		}
		if(phone.getLogoFile()!=null){
			if(!phone.getLogoFile().isEmpty() && StringUtil.isNotEmpty(flag)) {
				String iconPath = this.findFilePath(phone.getLogoFile(), phone);
				phone.setIconPath(iconPath);
			}
		}
		boolean isSuccess  = numSensePhoneDAO.update(phone);
		return isSuccess;
	}
	
	/**
	 * 删除号码
	 * @param ids
	 *            号码ID串 如:1,2,3
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
		List<NumSensePhone> phoneList = numSensePhoneDAO.findByIds(idList);
		boolean isSuccess = false;
		isSuccess = numSensePhoneDAO.deleteByIds(idList); // 删号码实体
		String path = "";
		for(NumSensePhone phoneEn : phoneList) {	//删除号码实体过后，有关文件删除
			if(phoneEn != null){
				path = phoneEn.getIconPath();
				if (StringUtil.isNotEmpty(path)) {
					path = path.substring(0, path.lastIndexOf("/"));
					fileUtil.deleteDirectory(fileUtil.getAbsolutePath(path));
//					FileUtil.deleteDirectory(new File(BaseInfo.getRealPath() + "/" + path));
				}
			}
		}
		return isSuccess;
	}
	
	/**
	 * 根据colId获得号码
	 * @param colId 分类Id
	 * @return list
	 * 
	 */
	public List<NumSensePhone> findByColId(int colId){
		if(colId <= 0){
			return null;
		}
		return numSensePhoneDAO.findByColId(colId);
	}
	
	/**
	 * 分页查询
	 * @param siteId 网站ID
	 * @param colId 分类ID
	 * @param pageSize 页码
	 * @param pageNum 页数
	 * @return list
	 */
	public List<NumSensePhone> findByColIdLimit(int siteId, int colId, int pageSize, int pageNum){
		return numSensePhoneDAO.findByColIdLimit(siteId, colId, pageSize, pageNum);
	}
	
	/**
	 * 更新排序ID
	 * @param ids id
	 * @param orderids  排序id串
	 * @return  true/false
	 *@throws OperationException 异常处理
	 */
	public boolean modifyOrderIdById(String ids, String orderids) throws OperationException{
		if (ids == null || ids.length() == 0 || orderids == null || orderids.length() == 0) {
			return false;
		}
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		List<Integer> ordersList = StringUtil.toIntegerList(orderids, ",");
		boolean isSuccess = false;
		for (int i = 0, len = idsList.size(); i < len; i++) {
			isSuccess = numSensePhoneDAO.updateOrderIdById(ordersList.get(i), idsList.get(i));
		}
		return isSuccess;
	}
	
	/**
	 * 判重处理
	 * @param id id
	 * @param name 名称
	 * @param siteId 网站ID
	 * @param colId 分类ID
	 * @return num
	 */
	public int findNumOfSameName(Integer id, String name, Integer siteId, Integer colId) {
		if (StringUtil.isEmpty(name)) {
			return 0;
		}
		int num = numSensePhoneDAO.findNumOfSameName(id, name, siteId, colId);
		return num;
	}
	
	/**
	 * 文件存放
	 * @param file 文件
	 * @param phone 号码实体
	 * @return json 
	 * @throws OperationException OperationException
	 */
	private String findFilePath(MultipartFile file, NumSensePhoneFormBean phone) throws OperationException{
		MultipartFileInfo fileInfo = MultipartFileInfo.getInstance(file);
		if(fileInfo.getFileType()==null){
			return "";
		}
		String uuid = StringUtil.getUUIDString();
		String tmpFilePath = Settings.getSettings().getFileTmp() + uuid  + "." + fileInfo.getFileType();
		String path = BaseInfo.getRealPath()+ "/web/site" + phone.getSiteId() 
					+ "/numsense/phone/" + phone.getIid() + "/";
		String absPath = "/web/site" + phone.getSiteId() 
					   + "/numsense/phone/" + phone.getIid() + "/";
		String newPath = "phone_source."+ fileInfo.getFileType();
		File tempFile = new File(tmpFilePath);
		ControllerUtil.writeMultipartFileToFile(tempFile, file);
		BufferedImage imgBuf = null;
		try {
			imgBuf = ImageIO.read(tempFile);
		} catch (IOException e1) {
			throw new OperationException("图片读取失败");
		}
		if(imgBuf.getWidth()!=100 || imgBuf.getHeight()!=100){
			throw new OperationException("图片格式不正确，请上传100*100的图片！");
		}else{
			FileUtil.createDir(path);	// 创建目标文件夹
			File desFile = new File(path + newPath);
			int nSrcWedth = imgBuf.getWidth();
		    int nSrcHeight= imgBuf.getHeight(); 
			try {
				Thumbnails.of(tempFile).size(nSrcWedth, nSrcHeight).
					outputQuality(0.8f).toFile(desFile);
				HadoopUtil.fileUpload(desFile, absPath+newPath);
			} catch (IOException e) {
				throw new OperationException("图片压缩失败");
			}
		}
		return absPath + newPath; //返回文件路径以供录入数据库
	}
	
	/**
	 * 检查文件类型
	 * @param phone 号码实体
	 * @throws OperationException OperationException
	 */
	private void checkFileType(NumSensePhoneFormBean phone) throws OperationException {
		if(phone == null){
			return;
		} 
		//图片
		if (phone.getLogoFile() != null && !phone.getLogoFile().isEmpty()) {
			MultipartFileInfo iconfile = MultipartFileInfo.getInstance(phone.getLogoFile()); 
			if(Configs.getConfigs().getPicFileType().indexOf(iconfile.getFileType().toLowerCase()) == -1){
				throw new OperationException("图片类型不正确，请重新上传！");
			}
			if(1*1024*1024 < iconfile.getSize()){
				throw new OperationException(
						"图片大小不能超过1M！");
			}
			
		}
	}
	
}