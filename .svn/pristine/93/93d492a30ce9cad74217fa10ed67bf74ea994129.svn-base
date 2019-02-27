package com.hanweb.jmp.cms.service.infos; 

import java.io.File; 
import java.util.Date; 
import java.util.List;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
 
import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.FileUtil; 
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.constant.Settings; 
import com.hanweb.complat.exception.OperationException;  
import com.hanweb.jmp.cms.controller.infos.pic.PicFormBean;
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
	 * 新增,返回主键id
	 * 
	 * @param pic pic
	 * @return picDAO
	 */
	public Integer add(Pic pic) {
		return picDAO.insert(pic);
	} 
	 
	/**
	 * addPics:新增酷图.
	 *
	 * @param info 信息
	 * @param picName 图片名称
	 * @param picDesc 图片描述
	 * @return 图片描述
	 * @throws OperationException    设定参数 .
	*/
	public boolean addPics(Info info, String picName, String picDesc) 
		throws OperationException{  
		try{
			String[] picNames=StringUtil.split(picName, "%_&");
		    String[] picDescs=StringUtil.split(picDesc, "%_&"); 
		    Pic picEn=null;
		    int colid=info.getColId();
		    int infoid=info.getIid();
		    int siteid=info.getSiteId();
		    String temppath = Settings.getSettings().getFileTmp(); 
		    String strType="";  
		    String imgname="";
		    String picpath="";
		    String imadesc="";
		    for(int i=0; i<picNames.length; i++){
		    	imadesc=StringUtil.getString(picDescs[i]);
		    	imgname=StringUtil.getString(picNames[i]);
		    	strType=imgname.substring(imgname.lastIndexOf(".") + 1);   
		    	if("图片说明为空".equals(imadesc)){
		    		imadesc="";
		    	} 
		    	picEn=new Pic();
		    	picEn.setColId(colid);
		    	picEn.setInfoId(infoid);
		    	picEn.setOrderId(picDAO.getMinOrderId()-1);
		    	picEn.setPicabstract(imadesc);
		    	picpath="/web/site" + info.getSiteId() + "/pic/" + info.getColId() + "/"
				+ DateUtil.getCurrDate("yyyyMM") + "/"+infoid+"/"
				+DateUtil.getCurrDate(DateUtil.YYYYMMDD_HHMMSS)+"_"+i+"."+strType;
		    	FileUtil.copyFile(new File(temppath+imgname), 
		    			new File(BaseInfo.getRealPath()+picpath));
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
	
  
	/**
	 * 删除
	 * 
	 * @param ids ids
	 * @return boolean
	 * @throws OperationException 设定参数.
	 */
	public boolean removeByIds(String ids) throws OperationException {
		if(StringUtil.isEmpty(ids)){
			return false;
		}
		List<Pic> pics=picDAO.findByIds(StringUtil.toIntegerList(ids));
		String picPath="";
		for(Pic picEn : pics){
			picPath=BaseInfo.getRealPath()+"/"+picEn.getPicpath();
			FileUtil.deleteFile(new File(picPath));
		}
		return picDAO.deleteByIds(StringUtil.toIntegerList(ids));
	}
 
	/**
	 * modify:(修改).
	 *
	 * @param pic pic
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean modify(PicFormBean pic) throws OperationException {
		if (pic == null || pic.getIid() <= 0) {
			return false;
		}
		 
		boolean isSuccess = picDAO.update(pic);
 
		if (!isSuccess) {
			throw new OperationException("更新组图失败！");
		} 
		return isSuccess;
	}
	
	/**
	 * 获取信息实体
	 * 
	 * @param iid iid
	 * @return Pic
	 */
	public Pic findByIid(Integer iid) {
		return picDAO.findByIid(iid);
	} 
	 
	/**
	 * findByInfoid:获取图片集合.
	 *
	 * @param infoid 信息id 
	 * @param siteid 网站id 
	 * @return    设定参数 .
	*/
	public List<Pic> findByInfoid(int infoid, int siteid) {
		return picDAO.findByInfoid(infoid, siteid);
	} 
	
	public List<Pic> findByInfoid1(int infoid, int siteid) {
		return picDAO.findByInfoid1(infoid, siteid);
	} 
	
	public List<Pic> findByInfoid2(int infoid, int siteid) {
		return picDAO.findByInfoid2(infoid, siteid);
	}
	
	/**
	 * 更新排序ID
	 * @param ids id
	 * @param orderids  订单
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
			isSuccess = picDAO.updateOrderIdById(ordersList.get(i), idsList.get(i));
		}
		return isSuccess;
	}
	
}
