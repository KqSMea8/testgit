package com.hanweb.jmp.apps.service.survey;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import com.hanweb.jmp.apps.controller.survey.SurveyFormBean;
import com.hanweb.jmp.apps.dao.survey.SurveyDAO;
import com.hanweb.jmp.apps.entity.survey.Question;
import com.hanweb.jmp.apps.entity.survey.Survey;
import com.hanweb.jmp.constant.Configs;

public class SurveyService {
	
	/**
	 * surveyDAO
	 */
	@Autowired
	SurveyDAO surveyDAO;
	
	/**
	 * questionService
	 */
	@Autowired
	QuestionService  questionService;
	
	/**
	 * 调查新增
	 * @param route boolean
	 * @return boolean
	 * @throws OperationException 设定参数.
	 */
	public boolean add(SurveyFormBean survey) throws OperationException{
		boolean isSuccess = false;
		if(survey == null){
			return false;
		}
		int orderId = surveyDAO.findMinOrderId(survey.getSiteId()) -1;
		survey.setOrderId(orderId);
		Integer iid = surveyDAO.insert(survey);
		if(iid > 0){
			checkFileType(survey);
			String filePath = findFilePath(survey.getFirstPicFile(), survey);
			survey.setFirstPicPath(filePath);
			
		}
		isSuccess = surveyDAO.update(survey);
		return isSuccess;
	}
	
	/**
	 * 通过ID串删除调查
	 * @param ids ids
	 * @return boolean
	 * @throws OperationException 设定参数.
	 */
	public boolean removeByIds(String ids, int siteId) throws OperationException{
		List<Integer> idsLsit = StringUtil.toIntegerList(ids, ",");
		boolean isSuccess = false;
		List<Question> questionList = new ArrayList<Question>();
		for (int i = 0, len = idsLsit.size(); i < len; i++) {
			Integer iid = idsLsit.get(i);
			questionList = questionService.findBySurveyId(siteId, iid);
		}
		if(questionList.size() > 0){
			throw new OperationException("请先删除调查下面的问题!");
		}
		//删除调查
		isSuccess = surveyDAO.deleteByIds(idsLsit);
		if (!isSuccess) {
			throw new OperationException("删除调查失败!");
		}
		// 删除调查文件
		String filePath = ""; // 调查路径
		for (int i = 0, len = idsLsit.size(); i < len; i++) {
			Integer iid = idsLsit.get(i);
			filePath = BaseInfo.getRealPath() + "/web/site" + siteId + "/survey/" + iid;
			File file = new File(filePath);
			if (file.isDirectory()) {
				FileUtil.deleteDirectory(file);
			}
		}
		return isSuccess;
	}
	
	/**
	 * 根据网站id和调查名称查询调查实体
	 * @param siteId 网站id
	 * @param name 调查名称
	 * @return 调查实体
	 */
	public Survey findByName(Integer siteId, String name){
		return surveyDAO.findByName(siteId, name); 
	}
	
	/**
	 * 通过调查ID获取调查实体
	 * @param iid iid
	 * @return  调查实体
	 */
	public Survey findByIid(int iid){
		return surveyDAO.findByIid(iid);
	} 
	
	/**
	 * 获取所有调查
	 * @param siteId   网站Id
	 * @return  调查实体集合
	 */
	public List<Survey> findAll(Integer siteId){
		return surveyDAO.findAll(siteId);
	}
	
	/**
	 * 修改调查
	 * @param route iid
	 * @return boolean
	 * @throws OperationException 设定参数.
	 */
	public boolean modify(SurveyFormBean survey) throws OperationException{
		boolean isSuccess = false;
		if(survey == null){
			return false;
		}
		if(!survey.getFirstPicFile().isEmpty()){
			checkFileType(survey);
			String filePath = findFilePath(survey.getFirstPicFile(), survey);
			survey.setFirstPicPath(filePath);
		}
		isSuccess = surveyDAO.update(survey);
		return isSuccess;
	}
	
	/**
	 * 修改调查
	 * @param survey
	 * @return
	 * @throws OperationException
	 */
	public boolean modifySurvey(Survey survey) throws OperationException{
		boolean isSuccess = false;
		if(survey == null){
			return false;
		}
		isSuccess = surveyDAO.update(survey);
		return isSuccess;
	}
	
	/**
	 * 文件存放
	 * @param file 文件
	 * @param phone 号码实体
	 * @return json 
	 * @throws OperationException OperationException
	 */
	private String findFilePath(MultipartFile file, SurveyFormBean survey) throws OperationException{
		MultipartFileInfo fileInfo = MultipartFileInfo.getInstance(file);
		if (fileInfo.getFileType() == null) {
			return "";
		}
		String uuid = StringUtil.getUUIDString();
		String tmpFilePath = Settings.getSettings().getFileTmp() + uuid + "." + fileInfo.getFileType();
		String path = BaseInfo.getRealPath() + "/web/site" + survey.getSiteId() + "/survey/" + survey.getIid() + "/";
		String absPath = "/web/site" + survey.getSiteId() + "/survey/" + survey.getIid() + "/";
		String newPath = "survey_firstpic." + fileInfo.getFileType();
		File tempFile = new File(tmpFilePath);
		ControllerUtil.writeMultipartFileToFile(tempFile, file);
		BufferedImage imgBuf = null;
		try {
			imgBuf = ImageIO.read(tempFile);
		} catch (IOException e1) {
			throw new OperationException("图片读取失败");
		}
		FileUtil.createDir(path); // 创建目标文件夹
		File desFile = new File(path + newPath);
		int nSrcWedth = 630;
		int nSrcHeight = 340;
		try {
			Thumbnails.of(tempFile).size(nSrcWedth, nSrcHeight).outputQuality(0.8f).toFile(desFile);
		} catch (IOException e) {
			throw new OperationException("图片压缩失败");
		}
		return absPath + newPath; // 返回文件路径以供录入数据库
	}
	
	/**
	 * 检查文件类型
	 * @param phone 号码实体
	 * @throws OperationException OperationException
	 */
	private void checkFileType(SurveyFormBean survey) throws OperationException {
		if(survey==null){
			return;
		} 
		//图片
		if (survey.getFirstPicFile()!=null && !survey.getFirstPicFile().isEmpty()) {
			MultipartFileInfo iconfile = MultipartFileInfo.getInstance(survey.getFirstPicFile()); 
			if(Configs.getConfigs().getPicFileType().indexOf(iconfile.getFileType().toLowerCase()) == -1){
				throw new OperationException("图片类型不正确，请重新上传！");
			}
			if(1*1024*1024 < iconfile.getSize()){
				throw new OperationException(
						"图片大小不能超过1M！");
			}
		}
	}
	
	/**
	 * 更新信息id的orderid
	 * @param ids ids
	 * @param orderids orderids
	 * @param colid colid
	 * @return boolean boolean
	 * @throws OperationException    设定参数 .
	 */
	public boolean modifyOrderIdById(String ids, String orderids) throws OperationException {
		if (ids == null || ids.length() == 0 || orderids == null || orderids.length() == 0) {
			return false;
		}
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		List<Integer> ordersList = StringUtil.toIntegerList(orderids, ",");
		boolean isSuccess = false;
		for (int i = 0, len = idsList.size(); i < len; i++) {
			isSuccess = surveyDAO.updateOrderIdById(ordersList.get(i), idsList.get(i));
		}
		return isSuccess;
	}
	
	/**
	 * 获得指定调查ID串的调查集合
	 * @param idsList idsList
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public List<Survey> findByIds(List<Integer> idsList, Integer siteId) {
		if (CollectionUtils.isEmpty(idsList) || NumberUtil.getInt(siteId) <= 0) {
			return null;
		}
		return surveyDAO.findByIds(idsList, siteId);
	}
	
}