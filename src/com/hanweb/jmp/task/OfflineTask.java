package com.hanweb.jmp.task;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobDataMap;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.task.BaseTask;
import com.hanweb.common.task.TaskScheduleBuilder;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.ZipUtil; 
import com.hanweb.common.util.file.IFileUtil;

import com.hanweb.interfaces.service.InfoInterfaceService;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.cms.service.infos.InfoService;
import com.hanweb.jmp.sys.entity.log.OfflineZip;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.log.OfflineZipService;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.jmp.util.HadoopUtil;

public class OfflineTask extends BaseTask {
	
	/**
	 * colService
	 */
	private ColService colService =  SpringUtil.getBean(ColService.class);
	
	/**
	 * siteService
	 */
	private SiteService siteService = SpringUtil.getBean(SiteService.class);
	
	/**
	 * infoService
	 */
	private InfoService infoService = SpringUtil.getBean(InfoService.class);
	
	/**
	 * infoService
	 */
	private InfoInterfaceService infoInterfaceService = SpringUtil.getBean(InfoInterfaceService.class);
	
	/**
	 * offlineZipService
	 */
	private OfflineZipService offlineZipService = SpringUtil.getBean(
			OfflineZipService.class);
	
	/**
	 * state
	 */
	public static int state = 1;
	
	@Override
	protected void config() {
		setTaskId("offline");
		setTaskName("离线下载"); 
		setTaskSchedule(TaskScheduleBuilder.getEveryMinuteSchedule(10));
	}

	@Override
	protected void doWork(JobDataMap arg0) {
		try {
			List<Site> siteList = siteService.findOfflineSite();
			List<Col> colList = null;
			List<Info> infoList = null;
			if(CollectionUtils.isEmpty(siteList)){ 
				return;
			}
			for(Site site : siteList){
				colList = colService.findAllOfflineBySiteId(site.getIid());
				if(CollectionUtils.isEmpty(colList)){  
					continue;
				}
				for(Col col: colList){
					if(col == null){
						return;
					}
					if(col.getLastUpdateTime() != null){
						long lastUpdateTime = col.getLastUpdateTime().getTime(); 
						OfflineZip offlinezip = offlineZipService.findByColId(
								col.getIid(), col.getSiteId());	 
						if(offlinezip == null){
							offlinezip = new OfflineZip();
							offlinezip.setColId(col.getIid()); 
							offlinezip.setSiteId(col.getSiteId());
							offlinezip.setModifyTime(col.getLastUpdateTime());
							infoList = infoService.findInfoByColid(
									col.getIid(), col.getSiteId(), col.getOfflineNum());
							boolean isSuccess = generatorInfo(
									infoList, col.getSiteId(), col.getIid());
							if(isSuccess){
								offlinezip.setZipTime(new Date());
								offlinezip.setIsZip(1);
							}
							offlineZipService.add(offlinezip);
						}else{
							long modifyTime = offlinezip.getModifyTime().getTime();
							if(lastUpdateTime > modifyTime  && site.getIsOfflineZip() ==1){
								infoList = infoService.findInfoByColid(
										col.getIid(), col.getSiteId(), col.getOfflineNum());
								boolean isSuccess =generatorInfo(
										infoList, col.getSiteId(), col.getIid());
								if(isSuccess){
									offlinezip.setModifyTime(col.getLastUpdateTime());
									offlinezip.setZipTime(new Date());
									offlinezip.setIsZip(1);
								}
								offlineZipService.modify(offlinezip);
							}
						}
					
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	/**
	 * 组织数据
	 * @param infoList infoList
	 * @param siteId siteId
	 * @param colId colId
	 * @return    设定参数 .
	*/
	private boolean generatorInfo(List<Info> infoList, int siteId, int colId) {
		String zipPath = BaseInfo.getRealPath() + "/web/site" + siteId + "/zip/" + colId;
		 
		FileUtil.deleteDirectory(new File(zipPath));  
		//正文页面json写入txt文本
		int infoSize = 10;
		String json = "";
		if(CollectionUtils.isNotEmpty(infoList)){
		    processContent(infoList, zipPath, siteId, colId);
			infoSize = infoList.size();
			json = StringUtil.getString(infoInterfaceService.listToJson(infoList, infoSize
	                , colService.findByIid(colId), true, 1));
	        for(Info info : infoList){
	            if(StringUtil.isEmpty(info.getFirstPicPath())){
	                continue;
	            } 
	            json = json.replaceAll(info.getFirstPicPath(), "./info_source.jpeg");
	        }
		}
		//列表页面json写入txt文本
		boolean isSuccess = writeJsonToTxt(json, siteId, colId);
		if (isSuccess) {
			// 压缩zip目录打包
			String disPath="web/site" + siteId + "/zip/" + colId+ ".zip";
			ZipUtil.zip(new File(zipPath), new File(zipPath + ".zip"));
			HadoopUtil.fileUpload(new File(zipPath + ".zip"), disPath);
		}
		return isSuccess;
	}
 
	/**
	 * 正文处理及图片拷贝.
	 * @param infoList infoList
	 * @param zipPath zipPath
	 * @param siteId siteId
	 * @param colId    设定参数 .
	*/
	private void processContent(List<Info> infoList, String zipPath, int siteId, int colId) { 
		IFileUtil fileUtil = (IFileUtil)SpringUtil.getBean("FileUtil"); 
		String firstPicpath="";
		for (Info info : infoList) {
			// 获取正文
			String content = infoInterfaceService.findOffineContent(info.getIid(), siteId, 1);
			// 正文写入html
			if (!"".equals(content)) {	 
				String strFilePath = BaseInfo.getRealPath() 
				+ "/web/site" + siteId + "/zip/" + colId 
				                     + "/info" + info.getIid()+ "/";
				FileUtil.createDir(strFilePath);  
				if(StringUtil.isNotEmpty(info.getFirstPicPath())){
					//拷贝原图
					firstPicpath=info.getFirstPicPath();
					if(firstPicpath.startsWith("/")){
						firstPicpath=firstPicpath.substring(1);
					}
					String picPath=fileUtil.getAbsolutePath(firstPicpath); 
					String filename = picPath.substring(picPath.lastIndexOf("/")+1); 
					fileUtil.copyFile(picPath, new File(strFilePath+filename)); 
					
					//拷贝中图
					String picEnd = info.getFirstPicPath().substring(info.getFirstPicPath().lastIndexOf(".")+1); 
					String midPath=StringUtil.replace(info.getFirstPicPath()
							, "info_source."+picEnd, "info_middle."+picEnd);
					if(midPath.startsWith("/")){
						midPath=midPath.substring(1);
					}
					midPath=fileUtil.getAbsolutePath(midPath); 
					filename = midPath.substring(midPath.lastIndexOf("/")+1); 
					fileUtil.copyFile(midPath, new File(strFilePath+filename));  
					
					//拷贝小图
					String minPath = StringUtil.replace(info.getFirstPicPath()
							, "info_source."+picEnd, "info_mini."+picEnd);
					if(minPath.startsWith("/")){
						minPath=minPath.substring(1);
					}
					minPath=fileUtil.getAbsolutePath(minPath); 
					filename = minPath.substring(minPath.lastIndexOf("/")+1); 
					fileUtil.copyFile(minPath, new File(strFilePath+filename));   
				} 
				 FileUtil.writeStringToFile(new File(strFilePath + "json.txt"), content, "UTF-8");
			} 
			
			// 拷贝图片
			copyImg(infoService.findByPath(BaseInfo.getRealPath()+ info.getPath())
					, zipPath + "/info" + info.getIid() + "/");
		}
	}


	/**
	 * 写json.txt
	 * @param json json
	 * @param siteId siteId
	 * @param colId colId
	 * @return boolean
	 */
	private boolean writeJsonToTxt(String json, int siteId, int colId) {
		boolean isSuccess = false;
		if ("".equals(json)) {
			isSuccess = false;
			return isSuccess;
		}
		String strFilePath = BaseInfo.getRealPath() + "/web/site" + siteId + "/zip/" + colId + "/";
		File file = new File(strFilePath);
		if (!file.exists() || !file.isDirectory()) {
			isSuccess = file.mkdirs();
		}
		isSuccess = FileUtil.writeStringToFile(new File(strFilePath + "json.txt"), json, "UTF-8");
		return isSuccess;
	}

	/**
	 * 拷贝图片
	 * @param hm hm
	 * @param imgPath imgPath
	 * @return boolean
	 */
	private boolean copyImg(Map<String, String> hm, String imgPath) {
		IFileUtil fileUtil = (IFileUtil )SpringUtil.getBean("FileUtil"); 
		if (hm!=null && NumberUtil.getInt(hm.get("filesize")) > 0) {
			int filessize=NumberUtil.getInt(hm.get("filesize"));
			String filepath = "";
			String middlePath="";
			String minPath="";
			String filename = ""; 
			for (int m = 1; m <= filessize; m++) {
				filepath = hm.get("file" + m);
				filename = filepath.substring(filepath.lastIndexOf("/")+1);
				if(filepath.startsWith("/")){
					filepath=filepath.substring(1);
				}
				filepath=fileUtil.getAbsolutePath(filepath); 
				//原图复制
				if (fileUtil.exists(filepath)) {
					fileUtil.copyFile(filepath, new File(imgPath + filename)); 
				} 
				//中图复制
				middlePath = filepath.replace("_source", "_middle");
				if (fileUtil.exists(middlePath)) {
					fileUtil.copyFile(middlePath, 
							new File(imgPath + filename.replace("_source", "_middle")));  
				}
				//小图复制
				minPath = filepath.replace("_source", "_mini");
				if (fileUtil.exists(minPath)) {
					fileUtil.copyFile(minPath, 
							new File(imgPath + filename.replace("_source", "_mini")));  
				}  
			}
		}
		return true;
	}
	
}