package com.hanweb.jmp.pack.service;

import java.io.File;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.cms.dao.infos.PicDAO;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.cms.entity.infos.Pic;

public class PicService {
	
	/**
	 * logger
	 */
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * picDAO 
	 */
	@Autowired
	private PicDAO picDAO;
	 
	/**
	 * 新增酷图
	 * @param info 信息
	 * @param picName 图片名称
	 * @param picDesc 图片描述
	 * @return boolean
	 * @throws OperationException    设定参数 .
	 */
	public boolean addPics(Info info, String picName, String picDesc) throws OperationException{  
		try{
			String[] picNames = StringUtil.split(picName, "%_&");
		    String[] picDescs = StringUtil.split(picDesc, "%_&"); 
		    Pic picEn = null;
		    int colid = info.getColId();
		    int infoid = info.getIid();
		    int siteid = info.getSiteId();
		    String temppath = BaseInfo.getRealPath() + "/resources/app/images/pic/" ; 
		    String strType = "";  
		    String imgname = "";
		    String picpath = "";
		    String imadesc = "";
		    for(int i=0; i<picNames.length; i++){
		    	imadesc = StringUtil.getString(picDescs[i]);
		    	imgname = StringUtil.getString(picNames[i]);
		    	strType = imgname.substring(imgname.lastIndexOf(".") + 1);   
		    	if("图片说明为空".equals(imadesc)){
		    		imadesc = "";
		    	} 
		    	picEn = new Pic();
		    	picEn.setColId(colid);
		    	picEn.setInfoId(infoid);
		    	picEn.setPicabstract(imadesc);
		    	picpath = "/web/site" + info.getSiteId() + "/pic/" + info.getColId() + "/"
				        + DateUtil.getCurrDate("yyyyMM") + "/"+infoid+"/"
				        + DateUtil.getCurrDate(DateUtil.YYYYMMDD_HHMMSS)+"_"+i+"."+strType;
		    	FileUtil.copyFile(new File(temppath+imgname), new File(BaseInfo.getRealPath()+picpath));
		    	picEn.setPicpath(picpath);
		    	picEn.setSiteId(siteid);
		    	picEn.setCreateTime(new Date());
		    	picEn.setSynTime(new Date());
		    	picDAO.insert(picEn);
		    }
		}catch(Exception e){
			logger.error("addPics error", e);
			return false;
		}
		return true;
	}

}