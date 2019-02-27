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
import com.hanweb.jmp.apps.dao.numbersense.NumSenseColDAO;
import com.hanweb.jmp.apps.entity.numbersense.NumSenseCol;
import com.hanweb.jmp.apps.controller.numbersense.numsensecol.NumSenseColFormBean;

public class NumSenseColService {
	
	/**
	 * numSenseColDAO
	 */
	@Autowired
	private NumSenseColDAO numSenseColDAO;
	
	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;
	 
	/**
	 * 新增方法
	 * @param col 分类
	 * @return true/false
	 * @throws OperationException OperationException
	 */
	public boolean add(NumSenseColFormBean col){
		if(col == null){
			return false;
		}
		boolean isOk = checkFileType(col);
		if(!isOk){
			throw new OperationException("图片不符合规格");
		}
		this.checkFileType(col); // 判断图片格式及大小
		int num = this.findNumOfSameName(0, col.getName(), col.getSiteId(), NumberUtil.getInt(col.getPid()));
		if (num > 0) {
			throw new OperationException("分类名称已存在,请重新设置！");
		}
		boolean isSuccess = true;
		col.setOrderId(numSenseColDAO.getMinOrderId(NumberUtil.getInt(col.getPid())) - 1);
		col.setCreateTime(new Date());
		int iid = numSenseColDAO.insert(col);
		col.setIid(iid);
		if(iid > 0){
			if(!col.getIconFile().isEmpty()){
				String iconPath = this.findFilePath(col.getIconFile(), col);
				col.setIconPath(iconPath);
				isSuccess = numSenseColDAO.update(col);
				return isSuccess;
			}
		} else {
			return false;
		}
		return isSuccess ;
	}
	
	/**
	 * 判重方法
	 * @param id id
	 * @param name 名称
	 * @param siteId 网站ID
	 * @param pid 父ID
	 * @return num
	 */
	public int findNumOfSameName(Integer id, String name, Integer siteId, Integer pid) {
		if (StringUtil.isEmpty(name)) {
			return 0;
		}
		int num = numSenseColDAO.findNumOfSameName(id, name, siteId, pid);
		return num;
	}
	
	/**
	 * 获得指定分类
	 * @param iid
	 *            分类ID
	 * @return 分类实体
	 */
	public NumSenseCol findById(int iid) {
		return numSenseColDAO.findByIid(iid);
	}
	
	/**
	 * 修改分类
	 * @param col 分类实体
	 * @return true/false
	 * @throws OperationException 
	 */
	public boolean modify(NumSenseColFormBean col) throws OperationException{
		if(col == null || col.getIid() <= 0){
			return false;
		}
		this.checkFileType(col);
		int num = this.findNumOfSameName(col.getIid(), col.getName(), col.getSiteId(), NumberUtil.getInt(col.getPid()));
		if (num > 0) {
			throw new OperationException("分类名称已存在,请重新设置！");
		}
		if(col.getIconFile()!=null){
			String iconPath = this.findFilePath(col.getIconFile(), col);
			col.setIconPath(iconPath);
		}
		boolean isSuccess  = numSenseColDAO.update(col);
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
		if (hasSubInfo) {
			throw new OperationException("所选分类下存在子分类或号码,请先删除子分类或号码!");
		}
		boolean isSuccess = false;
		List<NumSenseCol> colList = numSenseColDAO.findByIds(idList);
		isSuccess = numSenseColDAO.deleteByIds(idList); // 删分类
		String path = "";
		for(NumSenseCol col : colList){ // 删除分类后将分类下的图片文件全部删除
			if(col != null){
				path = col.getIconPath();
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
	 * 获得所有分类
	 * @param siteId 网站ID
	 * @return list
	 */
	public List<NumSenseCol> findAllCol(int siteId){
		return numSenseColDAO.findAllCol(siteId);
	}
	
	/**
	 * 根据父ID查询子集
	 * @param colId 分类ID
	 * @param siteId 网站ID
	 * @return list
	 */
	public List<NumSenseCol> findChildByPid(int colId, int siteId) {
		return numSenseColDAO.findChildByPid(colId, siteId);
	}
	
	/**
	 * 根据父id查询子分类
	 * @param pid 父ID
	 * @param siteId 网站ID
	 * @return list
	 */
	public List<NumSenseCol> findByPid(int pid, int siteId) {
		return numSenseColDAO.findByPid(pid, siteId);
	}
	
	/**
	 * 查询排序的分类集合
	 * @param colId 分类ID
	 * @param siteId 网站ID
	 * @return list
	 */
	public List<NumSenseCol> findOrderCol(int colId, int siteId) {
		return numSenseColDAO.findOrderCol(colId, siteId);
	}
	
	/**
	 * 更新排序ID
	 * @param ids id串
	 * @param orderids 订单ID串
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
			isSuccess = numSenseColDAO.updateOrderIdById(ordersList.get(i), idsList.get(i));
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
		int num = numSenseColDAO.findCountSubCol(idsList);
		int num1 = numSenseColDAO.findCountSubPhone(idsList);
		if(num > 0 || num1 > 0) {
			return true;
		}else{
			return false;
		}
	} 
	
	/**
	 * 文件路径
	 * @param file 文件
	 * @param col 分类实体
	 * @return json
	 * @throws OperationException  OperationException
	 */
	private String findFilePath(MultipartFile file, NumSenseColFormBean col) throws OperationException{
		MultipartFileInfo fileInfo = MultipartFileInfo.getInstance(file);
		if(fileInfo.getFileType()==null){
			return "";
		}
		String uuid = StringUtil.getUUIDString();
		String tmpFilePath = Settings.getSettings().getFileTmp() + uuid  + "." + fileInfo.getFileType();
		String path = BaseInfo.getRealPath()+ "/web/site" + col.getSiteId() 
					+ "/numsense/col/" + col.getIid() + "/";
		String absPath = "/web/site" + col.getSiteId() 
					   + "/numsense/col/" + col.getIid() + "/";
		String newPath = "col_source."+ fileInfo.getFileType();
		File tempFile = new File(tmpFilePath);
		ControllerUtil.writeMultipartFileToFile(tempFile, file);
		BufferedImage imgBuf = null;
		try {
			imgBuf = ImageIO.read(tempFile);
		} catch (IOException e1) {
			numSenseColDAO.deleteById(col.getIid());
			throw new OperationException("图片读取失败");
		}
		if(imgBuf.getWidth()!=100 || imgBuf.getHeight()!=100){
			numSenseColDAO.deleteById(col.getIid());
			throw new OperationException("图片格式不正确，请上传100*100的图片！");
		}else{
			FileUtil.createDir(path);	// 创建目标文件夹
			File desFile = new File(path + newPath);
			int nSrcWedth = imgBuf.getWidth();
		    int nSrcHeight= imgBuf.getHeight(); 
			try {
				Thumbnails.of(tempFile).size(nSrcWedth, nSrcHeight).outputQuality(0.8f).toFile(desFile);
				HadoopUtil.fileUpload(desFile, absPath+newPath);
			} catch (IOException e) {
				numSenseColDAO.deleteById(col.getIid());
				throw new OperationException("图片压缩失败");
			}
		}
		return absPath + newPath; //返回文件路径以供录入数据库
	}
	
	/**
	 * 检查附件类型
	 * @param col
	 *            栏目实体
	 * @return
	 * @throws OperationException
	 *             界面异常
	 */
	private boolean checkFileType(NumSenseColFormBean col){
		if(col==null){
			throw new OperationException("请上传图片");
		} 
		//图片
		if (col.getIconFile()!=null && !col.getIconFile().isEmpty()) {
			MultipartFileInfo iconfile = MultipartFileInfo.getInstance(col.getIconFile()); 
			if(Configs.getConfigs().getPicFileType().indexOf(iconfile.getFileType().toLowerCase()) == -1){
				throw new OperationException("图片类型不正确，请重新上传！");
			}
			if(1*1024*1024 < iconfile.getSize()){
				throw new OperationException(
						"图片大小不能超过1M！");
			}
		}
		return true;
	}

	/**
	 * 根据网站id查找号码分离
	 * @param siteId
	 * @return
	 */
	public List<NumSenseCol> findBySiteId(Integer siteId) {
		List<NumSenseCol> list = null;
		if(NumberUtil.getInt(siteId) > 0){
			list = numSenseColDAO.findNumSenseCol(siteId);
		}
		if(list == null){
			list = new ArrayList<NumSenseCol>();
		}
		return list;
	}
	
}