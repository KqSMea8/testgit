package com.hanweb.jmp.apps.service.broke;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.ExcelUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.file.IFileUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.complat.constant.Settings;
import com.hanweb.complat.exception.OperationException;

import com.hanweb.jmp.util.ImageUtil;
import com.hanweb.jmp.util.StrUtil;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.LogConfig;
import com.hanweb.jmp.apps.dao.broke.BrokeDAO;
import com.hanweb.jmp.apps.entity.broke.Broke;
import com.hanweb.jmp.sys.service.log.LogService;
import com.hanweb.jmp.cms.dao.comment.CommentDAO;
import com.hanweb.jmp.cms.dao.infos.InfoCountDAO;
import com.hanweb.jmp.cms.entity.infos.InfoCount;
import com.hanweb.jmp.cms.service.infos.InfoCountService;
import com.hanweb.jmp.apps.controller.broke.interfaces.BrokeFormBean;

public class BrokeService {
	
	/**
	 * logger
	 */
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * brokeDAO
	 */
	@Autowired
	private BrokeDAO brokeDAO;
	
	/**
	 * commentDAO
	 */
	@Autowired
	private CommentDAO commentDAO;

	/**
	 * brokeTypeService
	 */
	@Autowired
	private BrokeTypeService brokeTypeService;
	
	/**
	 * logService
	 */
	@Autowired
	private LogService logService;

	/**
	 * infoCountDAO
	 */
	@Autowired
	private InfoCountDAO infoCountDAO;

	/**
	 * infoCountService
	 */
	@Autowired
	private InfoCountService infoCountService;
	
	/**
	 * fileUtil
	 */
	@Autowired
	@Qualifier("FileUtil")
	private IFileUtil fileUtil;
	 
	/**
	 * add:新增报料,返回是否成功.
	 * @param broke broke
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean add(BrokeFormBean broke) throws OperationException{
		if (brokeTypeService.isAudit(broke.getClassId())) {
			// 自动审核
			broke.setIsAudit(1);
		} else {
			// 手动审核
			broke.setIsAudit(0);
		}
		
		// 过滤emoji表情
		StrUtil strUtil = new StrUtil();
		String content = broke.getContent();
		content = strUtil.filterEmoji(content);
		
		//获取报料参数，入库
		String title = broke.getTitle();
		if (StringUtil.isNotEmpty(content) && StringUtil.isEmpty(title)) {
			if (content.length() > 100) {
				title = content.substring(0, 100) + "...";
			} else {
				title = content;
			}
		}
		broke.setTitle(title);
		broke.setContent(content);
		broke.setCreateTime(new Date());
		//接口请求，需上传附件
		if(broke.getIp()!= null && broke.getIp() != "" || broke.getClientType()!=null){
			processMultipartFile(broke); 
		}
	
		if(StringUtil.isEmpty(broke.getUuid())){
			broke.setUuid(broke.getLoginId());
		}
		int iid=brokeDAO.insert(broke);
		
		//入infocount表
		if(iid > 0){
			InfoCount infoCount = new InfoCount();
			infoCount.setTitleId(iid);
			infoCount.setType(2);
			infoCountDAO.insert(infoCount);
		}
		
		//新增日志
		logService.setSiteid(broke.getSiteId());
		logService.add(LogConfig.modbroke, LogConfig.opradd, broke.getTitle());
		return iid>0;
	} 
	/**
	 * 根据标题查报料内容
	 * @param title
	 * @return
	 */
	public List<Map<String, Object>> findBrokeByTitle(String title){
		return brokeDAO.findBrokeByTitle(title);
	}
	/**
	 * 文件上传
	 * @param brokeInfo brokeInfo
	 * @throws OperationException    设定参数 .
	*/
	private void processMultipartFile(BrokeFormBean brokeInfo) throws OperationException{
		String path = "";
		String fileName = "";
		String[] fileArray = new String[]{brokeInfo.getPicfile(),brokeInfo.getPicfile1(), 
			    									brokeInfo.getPicfile2(), brokeInfo.getPicfile3(),
			    									brokeInfo.getAudiofile(), brokeInfo.getVideofile()};
		for (int i = 0; i < fileArray.length; i++) {
			if(fileArray[i] != null && fileArray[i] != "" ){
			    if(fileArray[i].length() > 0){
    				//文件名(.以及后面的部分)
    	            String fileLastName = fileArray[i].substring(fileArray[i].lastIndexOf("."), fileArray[i].length());
    	            //文件名(.以及前面的部分)
    	            String fileFirstname = DateUtil.getCurrDate("yyyyMMddHHmmss") + new Random().nextInt(100);
    				//根据网络地址获取字节流
    				byte[] btImg = getImageFromNetByUrl(fileArray[i]);
    				//若获取成功，则保存至本地服务器
    				if(null != btImg && btImg.length > 0){ 
    					//文件后缀全名(例如:23528.jpg)
    					fileName = fileFirstname + fileLastName;
    		            //将文件保存到本地
    		            writeImageToDisk(btImg, brokeInfo, fileName);
    		        }else{  
    		            System.out.println("没有从该连接获得内容");  
    		        }  
    				// 获取附件存放路径
    				String filepath = getStaticFilePath(brokeInfo);
    			    if (StringUtil.isEmpty(filepath)) {
    			    	throw new OperationException("文件路径错误！");
    				}
    				// 文件名字,采用当前时间+随机数
    				String filefullpath = filepath + fileFirstname + fileLastName;
    				if(i==0 || i==1 || i==2 || i==3){
    					//ControllerUtil.writeMultipartFileToFile(new File(filefullpath), multipartFile);
    					ImageUtil.zoomBrokeImg(filefullpath, fileFirstname);
    				}else{
    					//fileUtil.writeMultipartFileToFile(filefullpath, multipartFile);
    				}
    				//存入数据库的相对路径
    				path = filefullpath.replace((BaseInfo.getRealPath()).replaceAll("\\\\", "/"), "");
    				//入库
    				switch (i) {
    				case 0:
    					brokeInfo.setPicPath(path);
    					break;
    				case 1:
    					brokeInfo.setPicPath1(path);
    					break;
    				case 2:
    					brokeInfo.setPicPath2(path);
    					break;
    				case 3:
    					brokeInfo.setPicPath3(path);
    					break;
    				case 4:
    					brokeInfo.setAudioPath(path);			
    					break;
    				case 5:
    					brokeInfo.setVideoPath(path);
    					break;
    				default:
    					break;
    				}			
    			}
			}
		}
	}
	
	 /** 
     * 将图片写入到磁盘 
     * @param img 图片数据流 
     * @param fileName 文件保存时的名称 
     */  
    public static void writeImageToDisk(byte[] img, Broke broke, String fileName){  
        try {
        	String filepath = "/web/site" + broke.getSiteId()
							+ "/broke/"+broke.getClassId() + "/" + DateUtil.getCurrDate("yyyyMM") + "/";
        	filepath = BaseInfo.getRealPath() + filepath + fileName;
            File file = new File(filepath);  
            FileOutputStream fops = new FileOutputStream(file);  
            fops.write(img);  
            fops.flush();  
            fops.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    } 
	
	/**
	 * 根据url地址从网络获得文件的字节流
	 * 
	 * @param strUrl
	 *            网络连接地址
	 * @return
	 */
	public byte[] getImageFromNetByUrl(String strUrl) {
		byte[] btImg = null;
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(10 * 5000);
			InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据
			btImg = readInputStream(inStream);// 得到图片的二进制数据
		} catch (Exception e) {
			e.printStackTrace();
		}
		return btImg;
	}
	
	/**
	 * 根据url地址从网络获得文件的字节流
	 * 
	 * @param inStream
	 *            输入流
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	/**
	 * 查找我的报料列表
	 * @param siteid siteid
	 * @param pagesize pagesize
	 * @param maxid maxid
	 * @param lgname lgname
	 * @return    设定参数 .
	*/
	public List<Broke> findMyList(Integer siteid, Integer pagesize, Integer maxid, String lgname){
		if(NumberUtil.getInt(pagesize) <= 0){
			pagesize = 15;
		}
		List<Broke> list = brokeDAO.findMyList(siteid, pagesize, maxid, lgname); 
		return list;
	}
	
	/**
	 * 查找信息列表
	 * @param siteid siteid
	 * @param pagesize pagesize
	 * @param maxid maxid
	 * @return    设定参数 .
	*/
	public List<Broke> findInfoList(Integer siteid, Integer pagesize, Integer maxid){
		if(NumberUtil.getInt(pagesize) <= 0){
			pagesize = 15;
		}
		List<Broke> list = brokeDAO.findInfoList(siteid, pagesize, maxid);
		return list;
	}
	
	/**
	 * 根据网站id获取报料信息 
	 * @param siteId 网站id 
	 * @return List<Broke>
	 */
	public List<Broke> findInfoBySiteid(int siteId) {
		if(siteId <= 0){
			return null;
		}
		List<Broke> brokeList = new ArrayList<Broke>();
		int count = brokeDAO.findCountBySiteID(siteId);
		int times = count / 1000 + 1;
		int pageSize = 1000;
		for (int i = 1; i < times + 1; i++) {
			brokeList.addAll(brokeDAO.findInfoBySiteid(pageSize, i, siteId));
		} 
		return brokeList;
	}
	
	
	/**
	 * 获取文件路径
	 * @param broke broke
	 * @return    设定参数 .
	*/
	private String getStaticFilePath(Broke broke) {
		String filepath = "/web/site" + broke.getSiteId()
						+ "/broke/"+broke.getClassId() + "/" + DateUtil.getCurrDate("yyyyMM");
		filepath = BaseInfo.getRealPath() + filepath;
		File file = new File(filepath);
		boolean flag = true;
		if (!file.exists() || !file.isDirectory()) {
			flag = file.mkdirs();
		}
		if (!flag) {
			return "";
		}
		return (filepath + File.separator).replaceAll("\\\\", "/");
	}

	/**
	 * 附件上传格式检查
	 * @param filetype filetype
	 * @return boolean
	 */
	public boolean checkFileType(String filetype) {
		if (StringUtil.isEmpty(filetype)) {
			return false;
		}
		filetype = filetype.toLowerCase();
		String[] fileTypes = Configs.getConfigs().getPicFileType().split(",");
		for (String file : fileTypes) {
			if (file.equals(filetype)) {
				return true;
			}
		}
		fileTypes = Configs.getConfigs().getAudioFileType().split(",");
		for (String file : fileTypes) {
			if (file.equals(filetype)) {
				return true;
			}
		}
		fileTypes = Configs.getConfigs().getVideoFileType().split(",");
		for (String file : fileTypes) {
			if (file.equals(filetype)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 删除报料
	 * @param ids ids
	 * @param siteId siteId
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean removeByIds(String ids, Integer siteId) throws OperationException{
		List<Integer> intIds = StringUtil.toIntegerList(ids, ","); 
		if(CollectionUtils.isEmpty(intIds)){
			return false;
		}
		//删除报料的评论
		boolean bl = commentDAO.deleteByInfoIds(intIds, 2);
		if(bl){
			bl = brokeDAO.deleteByIds(intIds);
			//删除信息数据库表
			infoCountService.removeByIds(intIds, 2, siteId);
		}
		List<Broke> list = this.findByIds(ids);
		StringBuffer name = new StringBuffer();
		for(Broke broke : list){
			name.append(broke.getTitle());
		}
		logService.add(LogConfig.modbroke, LogConfig.oprremove, name.toString());
		return bl;
	}
 
	/**
	 * 删除我的报料
	 * @param deviceCode deviceCode
	 * @param loginName loginName
	 * @param id id
	 * @param siteId siteId
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean removeMyBroke(String deviceCode, String loginName, Integer id, Integer siteId) throws OperationException{
		List<Integer> infoIdList = StringUtil.toIntegerList(StringUtil.getString(id));
		if(CollectionUtils.isEmpty(infoIdList) || NumberUtil.getInt(infoIdList.size()) == 0){
			return false;
		}
		//删除报料的评论
		boolean bl = commentDAO.deleteByInfoIds(infoIdList, 2);
		if(bl){
			bl = brokeDAO.deleteMyBroke(deviceCode, loginName, id);
			//删除信息数据库表
			infoCountService.removeByIds(infoIdList, 2, siteId);
		}
		return bl;
	}

	/**
	 * 修改报料信息
	 * @param ids ids
	 * @param isAudit isAudit
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean modifyBrokeInfo(String ids, int isAudit) throws OperationException{
		return brokeDAO.updateBrokeInfo(ids, isAudit);
	}

	/**
	 * 修改是否公开状态
	 * @param iid iid
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean modifyIsOpen(Integer iid) throws OperationException{
		if(NumberUtil.getInt(iid) <= 0){
			return false;
		}
		return brokeDAO.updateIsOpen(iid);
	}

	/**
	 * 查找iid
	 * @param iid iid
	 * @return    设定参数 .
	 */
	public Broke findByIid(Integer iid){
		if(NumberUtil.getInt(iid) <= 0){
			return null;
		}
		return brokeDAO.queryForEntityById(iid);
	}


	/**
	 * 修改报料
	 * @param broke broke
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean modify(Broke broke) throws OperationException{
		if(broke == null){
			return false;
		}
		return brokeDAO.update(broke);
	}
	 
	/**
	 * 以Excel形式导出报料信息.
	 * @param ids 报料ID串 如:1,2,3
	 * @param siteId 网站id
	 * @return 导出文件的绝对路径
	 * @throws OperationException    设定参数 .
	*/
	public String exportBroke(String ids, Integer siteId) throws OperationException{
		String filePath = "";
		List<List<String>> rows = new ArrayList<List<String>>();
		//表头
		List<String> headList = new ArrayList<String>();
		//报料数据列 
		List<String> valueList = null;  
		List<Broke> brokeList = null;
		headList.add("报料ID");
		headList.add("报料名称");
		headList.add("提交人");
		headList.add("报料内容");
		headList.add("所属报料分类ID");
		headList.add("所属网站ID");
		rows.add(headList);
		//得到所要导出的报料
		brokeList = this.findByIds(ids); 
		if (StringUtil.isEmpty(ids)) {
			throw new OperationException("未选择记录！");
		}
		for (Broke broke : brokeList) {
			valueList = new ArrayList<String>();
			valueList.add(StringUtil.getString(broke.getIid()));
			valueList.add(broke.getTitle());
			valueList.add(StringUtil.getString(broke.getLoginId()));
			valueList.add(broke.getContent());
			valueList.add(StringUtil.getString(broke.getClassId()));
			valueList.add(StringUtil.getString(broke.getSiteId()));
			rows.add(valueList);
		}
		/* 写入文件 */
		String fileName = StringUtil.getUUIDString() + ".xls";
		filePath = Settings.getSettings().getFileTmp() + fileName;
		ExcelUtil.writeExcel(filePath, rows);
		return filePath;
	}
	
	/**
	 * 通过报料ID串获取机构 
	 * @param ids
	 *            报料ID串 如:1,2,3
	 * @return 报料实体集合
	 */
	public List<Broke> findByIds(String ids) {
		List<Integer> idsLsit = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idsLsit)) {
			return null;
		}
		List<Broke> brokeList = brokeDAO.findByIds(idsLsit);
		return brokeList;
	}
	
	/**
	 * 从Excel导入报料信息
	 * @param file
	 *            导入Excel表单的绝对路径
	 * @return 提示信息
	 * @throws OperationException
	 *             错误信息
	 */
	public String importBroke(File file) throws OperationException {
		if (file == null) {
			throw new OperationException("无法找到上传的文件！");
		}
		List<Map<String, String>> rows = ExcelUtil.readExcel(file);
		if (CollectionUtils.isEmpty(rows)) {
			throw new OperationException(SpringUtil.getMessage("import.filetype.error"));
		}
        //读出数据
		List<Broke> brokeList = this.findBrokeListByRows(rows);
		String retMessage = "";
		try {
			//循环插入报料信息
			retMessage = this.importBroke(brokeList);
			if (!retMessage.equals("")) {
				retMessage = "<div style='height:150px;overflow:auto'>导入完毕，存在以下问题：<br/>" + retMessage + "</div>";
			}
			return retMessage;
		} catch (Exception e) {
			logger.error("import group error", e);
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
	 * 获得报料实体List
	 * @param rows
	 * 报料集合 每个Map为一个报料实体<br/>
	 * key为表单头 如:"报料名称"<br/>
	 * vaule为表单值 如:"大汉网络"
	 * @return 机构实体List
	 */
	private List<Broke> findBrokeListByRows(List<Map<String, String>> rows) {
		List<Broke> brokeList = new ArrayList<Broke>(); // 报料集合
		Map<String, String> cell = null;
		Broke broke = null;
		/* excel记录转换成用户集合 */
		Iterator<Map<String, String>> iterator = rows.iterator();
		while (iterator.hasNext()) {
			cell = iterator.next();
			broke = new Broke();
			broke.setIid(NumberUtil.getInt(cell.get("报料ID")));
			broke.setTitle(StringUtil.getString(cell.get("报料名称")));
			broke.setLoginId(StringUtil.getString(cell.get("提交人")));
			broke.setContent(StringUtil.getString(cell.get("报料内容")));
			broke.setClassId(NumberUtil.getInt(cell.get("所属报料分类ID")));
		    broke.setSiteId(NumberUtil.getInt(cell.get("所属网站ID")));
			brokeList.add(broke);
		}
		return brokeList;
	}

	/**
	 * 循环插入报料信息
	 * @param brokeList
	 *            报料实体List
	 * @return 提示信息
	 * @throws OperationException
	 *             界面异常
	 */
	private String importBroke(List<Broke> brokeList) throws OperationException {
		if (brokeList == null) {
			return "";
		}
		Broke broke = null; // Excel表中的取出的报料
		BrokeFormBean validBroke = null; // 待验证并插入的报料对象
		StringBuilder result = new StringBuilder();
		Integer iid = 0;
		Integer classId = 0;
		Integer siteId = 0;
		String title = "";
		String loginId = "";
		String content = "";
		String message = ""; // 错误提示信息
        boolean isSccuess = false;
		for (int i = 0; i < brokeList.size(); i++) {
			broke = brokeList.get(i);
			if (broke == null || StringUtil.isEmpty(broke.getTitle())) {
				message = "该行报料标题为空";
				this.getReturnMessage(result, i, message);
				continue;
			}
			validBroke = new BrokeFormBean();
			iid = NumberUtil.getInt(broke.getIid());
			title = broke.getTitle();
			classId = broke.getClassId();
			siteId = broke.getSiteId();
			loginId = broke.getLoginId();
			content = broke.getContent();
			if (message != null && message.length() > 0) {
				this.getReturnMessage(result, i, message);
				continue;
			}				
			validBroke.setIid(iid);
			validBroke.setTitle(title);
			validBroke.setLoginId(loginId);
			validBroke.setClassId(classId);
			validBroke.setContent(content);
			validBroke.setSiteId(siteId);
			isSccuess = this.add(validBroke);
			logService.add(LogConfig.modbroke, LogConfig.oprimport, title);
			if (!isSccuess) {
				message = "导入报料'" + title + "'出现异常";
				this.getReturnMessage(result, i, message);
				continue;
		   }
		}
		return result.toString();
	}
	
	/**
	 * 组织导入的错误提示
	 * @param result
	 *            错误提示信息集
	 * @param i
	 *            信息在List中的序号
	 * @param message
	 *            错误信息
	 */
	private void getReturnMessage(StringBuilder result, int i, String message) {
		result.append("<li>");
		result.append("[" + SpringUtil.getMessage("import.error", i + 2) + "]" + message);
		result.append("</li>");
	}
	
	/**
	 * 查找图片列表
	 * @param siteid siteid
	 * @param pagesize pagesize
	 * @return    设定参数 .
	*/
	public List<Broke> findPicList(Integer siteid, Integer pagesize){ 
		List<Broke> list = brokeDAO.findPicInfoList(siteid, pagesize);
		return list;
	}

}