package com.hanweb.jmp.apps.service.survey;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
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
import com.hanweb.jmp.apps.controller.survey.AnswerFromBean;
import com.hanweb.jmp.apps.dao.survey.AnswerDAO;
import com.hanweb.jmp.apps.entity.survey.Answer;
import com.hanweb.jmp.constant.Configs;

public class AnswerService {
	
	/**
	 * answerDAO
	 */
	@Autowired
	AnswerDAO  answerDAO;
	
	public String [] colors = new String []{"#FF6666","#00CC99","#FFCC00","#00CCFF","#9966CC"};
	
	/**
	 * 调查答案新增
	 * 
	 * @param route boolean
	 * @return boolean
	 * @throws OperationException 设定参数.
	 */
	public boolean add(AnswerFromBean answer) throws OperationException{
		boolean isSuccess = false;
		if(answer == null){
			return false;
		}
		Integer iid = answerDAO.insert(answer);
		if(iid > 0){
			checkFileType(answer);
			String filePath = findFilePath(answer.getFirstPicFile(), answer);
			answer.setFirstPicPath(filePath);
		}
		isSuccess = answerDAO.update(answer);
		return isSuccess;
	}
	
	/**
	 * 通过ID串删除调查答案
	 * @param ids ids
	 * @return boolean
	 * @throws OperationException 设定参数.
	 */
	public boolean removeByIds(String ids, int siteId) throws OperationException{
		List<Integer> idsLsit = StringUtil.toIntegerList(ids, ",");
		boolean isSuccess = false;
		// 删除调查文件
		String filePath = ""; // 调查路径
		for (int i = 0, len = idsLsit.size(); i < len; i++) {
			Integer iid = idsLsit.get(i);
			Answer answer = this.findByIid(iid);
			filePath = BaseInfo.getRealPath() + "/web/site" + siteId + "/survey/" 
			         + answer.getSurveyId() + "/question/" + answer.getQuestionId() + "/answer/" + iid;
			File file = new File(filePath);
			if (file.isDirectory()) {
				FileUtil.deleteDirectory(file);
			}
		}
		//删除调查
		isSuccess = answerDAO.deleteByIds(idsLsit);
		if (!isSuccess) {
			throw new OperationException("删除答案失败!");
		}
		return isSuccess;
	}
	
	/**
	 * 根据网站id和调查答案名称查询调查实体
	 * @param siteId 网站id
	 * @param name 调查名称
	 * @return 调查答案实体
	 */
	public Answer findByName(Integer siteId, String name){
		return answerDAO.findByName(siteId, name); 
	}
	
	/**
	 * 通过调查答案ID获取调查实体
	 * @param iid iid
	 * @return  调查答案实体
	 */
	public Answer findByIid(int iid){
		return answerDAO.findByIid(iid);
	}
	
	/**
	 * 根据问题Id查找答案
	 * @param siteId         网站Id
	 * @param questionId     问题Id
	 * @return
	 */
	public List<Answer> findByQuestionId(Integer siteId, Integer questionId){
		return answerDAO.findByQuestionId(siteId, questionId);
	}
	
	/**
	 * 根据问题Id查找答案 
	 * @param siteId         网站Id
	 * @param questionId     问题Id
	 * @return
	 */
	public List<Answer> findBySurveyId(Integer siteId, Integer SurveyId){
		return answerDAO.findBySurveyId(siteId, SurveyId);
	}
	
	/**
	 * 获取所有调查答案
	 * @param siteId   网站Id
	 * @return  调查答案实体集合
	 */
	public List<Answer> findAll(Integer siteId){
		return answerDAO.findAll(siteId);
	}
	
	/**
	 * 修改调查答案
	 * @param route iid
	 * @return boolean
	 * @throws OperationException 设定参数.
	 */
	public boolean modify(AnswerFromBean answer) throws OperationException{
		boolean isSuccess = false;
		if(answer == null){
			return false;
		}
		if(! answer.getFirstPicFile().isEmpty()){
			checkFileType(answer);
			String filePath = findFilePath(answer.getFirstPicFile(), answer);
			answer.setFirstPicPath(filePath);
			
		}
		isSuccess = answerDAO.update(answer);
		return isSuccess;
	}
	
	/**
	 * 检查文件类型
	 * @param phone 号码实体
	 * @throws OperationException OperationException
	 */
	private void checkFileType(AnswerFromBean answer) throws OperationException {
		if(answer==null){
			return;
		} 
		//图片
		if (answer.getFirstPicFile()!=null && !answer.getFirstPicFile().isEmpty()) {
			MultipartFileInfo iconfile = MultipartFileInfo.getInstance(answer.getFirstPicFile()); 
			if(Configs.getConfigs().getPicFileType().indexOf(iconfile.getFileType().toLowerCase()) == -1){
				throw new OperationException("图片类型不正确，请重新上传！");
			}
			if(1*1024*1024 < iconfile.getSize()){
				throw new OperationException("图片大小不能超过1M！");
			}
		}
	}
	
	/**
	 * 文件存放
	 * @param file 文件
	 * @param phone 号码实体
	 * @return json 
	 * @throws OperationException OperationException
	 */
	private String findFilePath(MultipartFile file, AnswerFromBean answer) throws OperationException{
		MultipartFileInfo fileInfo = MultipartFileInfo.getInstance(file);
		if (fileInfo.getFileType() == null) {
			return "";
		}
		String uuid = StringUtil.getUUIDString();
		String tmpFilePath = Settings.getSettings().getFileTmp() + uuid + "." + fileInfo.getFileType();
		String path = BaseInfo.getRealPath() + "/web/site" + answer.getSiteId()
				    + "/survey/" + answer.getSurveyId() + "/question/" + answer.getQuestionId() 
				    + "/" + answer.getIid() + "/";
		String absPath = "/web/site" + answer.getSiteId()
					   + "/survey/" + answer.getSurveyId() + "/question/" + answer.getQuestionId() 
					   + "/" + answer.getIid() + "/";
		String newPath = "question_firstpic." + fileInfo.getFileType();
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
	
	/** 根据网站id 和 answerid串查找Answer集合
	 * @param siteId
	 * @param answerIds
	 * @return
	 */
	public List<Answer> findAnswersByIds(Integer siteId, String questionIds){
		return answerDAO.findByIds(siteId, questionIds);
	}
	

	/** 根据id串修改Answer的count字段
	 * @param idsList
	 * @return
	 */
	public boolean modifyAnswerCount(List<Integer> idsList){
		return answerDAO.modifyCount(idsList) ; 
	}
	
	/**随机获取颜色
	 * @param last
	 * @return
	 */
	public int getRandomColor(int last){
		if(last == -1){
			return (int)(Math.random()*100)/25;
		}else{
			int i = (int)(Math.random()*100)/25;
			if(i != last){
				return i;
			}else{
				if((i+1)<5){
					return i+1;
				}else{
					return 0;
				}
			}
		}
	}
	
	/**
	 * 组织列表版样式html内容
	 * @return
	 */
	public String organizeListHtml(int size, List<Answer> answerList, String domain, int quesType, int isend, int quesIndex){
		//列表版页面{body}替换
		//读取单个选项模板
		String listHtml = FileUtil.readFileToString(new File(BaseInfo.getRealPath()
				        + "/resources/jmp/survey/txt/list.txt"), "utf-8");
		String listAll = "";
		//循环追加
		for(int i=0 ;i<size; i++){
			String listHtml2 = listHtml;
			listHtml2 = listHtml2.replace("{picSrc}", domain+answerList.get(i).getFirstPicPath());
			if(quesType==1){
				listHtml2 = listHtml2.replace("{quesType}", "radio");
			}else{
				listHtml2 = listHtml2.replace("{quesType}", "checkbox");
			}
			listHtml2 = listHtml2.replace("{index}", quesIndex+"");
			listHtml2 = listHtml2.replace("{answerindex}", (i+1)+"");
			listHtml2 = listHtml2.replace("{abs}", StringUtil.getString(answerList.get(i).getAbs()));
			listHtml2 = listHtml2.replace("{value}", answerList.get(i).getIid()+"");
			listHtml2 = listHtml2.replace("{count}", answerList.get(i).getCount()+"");
			listHtml2 = listHtml2.replace("{link}", answerList.get(i).getUrl());
			listAll += listHtml2;
		}
		return listAll;
	}
	
	/**
	 * 组织九宫格html内容
	 * @return
	 */
	public String organizeSudokuHtml(int size, List<Answer> answerList, String domain, int quesType, int isend, int quesIndex){
		String trHtml = FileUtil.readFileToString(new File(BaseInfo.getRealPath()
				      + "/resources/jmp/survey/txt/tr.txt"), "utf-8");
		String tdHtml = FileUtil.readFileToString(new File(BaseInfo.getRealPath()
				      + "/resources/jmp/survey/txt/td.txt"), "utf-8");
		//九宫格行数
		int lineNum = (int)(size/3)+1;
		String trAll = "";
			for(int i=0;i<lineNum;i++){
				String trHtml2 = trHtml;
				String tdALL = "";
				for(int j = i*3;(j<i*3+3)&&j<size;j++){
					String tdHtml2 = tdHtml;
					tdHtml2 = tdHtml2.replace("{picSrc}", domain+answerList.get(j).getFirstPicPath());
					tdHtml2 = tdHtml2.replace("{title}", answerList.get(j).getName());
					if(quesType==1){
						tdHtml2 = tdHtml2.replace("{quesType}", "radio");
						tdHtml2 = tdHtml2.replace("{index}", quesIndex+"");
					}else{
						if(isend==1){
							tdHtml2 = tdHtml2.replace("{disabled}", "disabled");
						}
						tdHtml2 = tdHtml2.replace("{quesType}", "checkbox");
					}
					tdHtml2 = tdHtml2.replace("{value}", answerList.get(j).getIid()+"");
					tdHtml2 = tdHtml2.replace("{index}", quesIndex+"");
					tdHtml2 = tdHtml2.replace("{answerindex}", (j+1)+"");
					tdHtml2 = tdHtml2.replace("{count}", answerList.get(j).getCount()+"");
					tdHtml2 = tdHtml2.replace("{link}", answerList.get(j).getUrl());
					tdALL += tdHtml2;
				}
				trHtml2 = trHtml2.replace("{body}", tdALL);
				trAll +=trHtml2;
			}
	
		return trAll;
	}
	
	/**
	 * 组织调查结果html内容
	 * @return
	 */
	public String organizeResultHtml(List<Answer> answerList, String domain, int sum){
		String resultHtml = FileUtil.readFileToString(new File(BaseInfo.getRealPath()
				          + "/resources/jmp/survey/txt/resultdiv.txt"), "utf-8");
		String resultContent = FileUtil.readFileToString(new File(BaseInfo.getRealPath()
				             + "/resources/jmp/survey/txt/resultcontent.txt"), "utf-8");
		String resultContentAll = "";
		for(int i=0; i<answerList.size(); i++){
			String resultContent2 = resultContent;
			resultContent2 = resultContent2.replace("{title}",answerList.get(i).getName());
			double count = NumberUtil.getInt(answerList.get(i).getCount());
			String percent = "";
			if(NumberUtil.getInt(count)==0){
				percent = "0";
			}else{
				percent = ((count/sum)*1000)/10+"";
				if(percent.indexOf(".")+3<=(percent.length()-1)){
					percent = percent.substring(0,percent.indexOf(".")+3);
				}
			}
			double n = NumberUtil.getDouble(percent);
			String width = 0.5*n+"";
	        String margin = 0.5*n+20+"";
			resultContent2 = resultContent2.replace("{percent}",percent+"%");
			resultContent2 = resultContent2.replace("{width}", width+"%");
			resultContent2 = resultContent2.replace("{margin}", margin+"%");
			int color = -1;
			resultContent2 = resultContent2.replace("{color}", colors[color=getRandomColor(color)]);
			resultContentAll += resultContent2;
		}
		resultHtml = resultHtml.replace("{body}", resultContentAll);
		return resultHtml;
	}
	
}