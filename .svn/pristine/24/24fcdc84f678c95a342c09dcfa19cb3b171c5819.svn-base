package com.hanweb.jmp.apps.service.manage;
 
import java.io.File;
import java.io.IOException;
import java.util.ArrayList; 
import java.util.List; 

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;   
import org.springframework.web.multipart.MultipartFile;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.NumberUtil; 
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.MultipartFileInfo;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.util.CacheUtil;
import com.hanweb.jmp.cms.dao.cols.ColDAO;
import com.hanweb.jmp.constant.StaticValues;
import com.hanweb.jmp.apps.dao.manage.LightAppDAO;
import com.hanweb.jmp.apps.entity.manage.LightApp;
import com.hanweb.jmp.apps.controller.manage.LightAppFormBean;

public class LightAppService {
	
	@Autowired
	private LightAppDAO lightAppDAO; 
	
	@Autowired
	private ColDAO colDAO; 
	
	/**
	 * 增加轻应用
	 * @param lightApp
	 * @return
	 * @throws OperationException
	 */
	public boolean add(LightAppFormBean lightApp, MultipartFile iconFile) throws OperationException{
		String iconNewName = "";
		String iconOldName = "";
		
		if(lightApp ==  null){
			return false;
		}
		//去重查询
		int num = lightAppDAO.findNumByName(NumberUtil.getInt(lightApp.getIid()), 
				lightApp.getName(), NumberUtil.getInt(lightApp.getSiteId()), NumberUtil.getInt(lightApp.getLightType()));
		if(num > 0){
			throw new OperationException("轻应用名已存在,请重新设置！");
		}
		lightApp.setOrderId(findMinOrderIdBySiteId(lightApp.getSiteId())-1);
		
		//数据入库 先入库保存图片地址和应用iid有关
		int iid = lightAppDAO.insert(lightApp);
		
		if(iid > 0){
			//保存图片
			MultipartFileInfo info = MultipartFileInfo.getInstance(iconFile);
			iconOldName = info.getFileName() + "." + info.getFileType();
			iconNewName = this.saveIconFile(iconFile, lightApp.getIid());
			
			lightApp.setIconNewName(iconNewName);
			lightApp.setIconOldName(iconOldName);
		}
		
		//数据入库
		boolean success = this.modify(lightApp);
		return success;
	    
	}
	
	/**
	 * 增加接口
	 * @param lightApp
	 * @return
	 */
	public boolean addInterface(LightApp lightApp){ 
		if(lightApp ==  null){
			return false;
		}
		//去重查询
		int num = lightAppDAO.findNumByName(NumberUtil.getInt(lightApp.getIid()), 
				lightApp.getName(), NumberUtil.getInt(lightApp.getSiteId()), NumberUtil.getInt(lightApp.getLightType()));
		if(num > 0){
			return false;
		} 
		//数据入库
		int iid = lightAppDAO.insert(lightApp);
		return iid > 0;
	}
	
	/**
	 * 通过iid查找实体
	 * @param iid
	 * @return
	 */
	public LightApp findByIid(int iid){
		if(iid <= 0){
			return null;
		}
		return lightAppDAO.findByIid(iid);
	}
	
	/**
	 * 通过网站id查找实体
	 * @param siteId
	 * @param isOpen
	 * @return
	 */
	public List<LightApp> findBySiteId(Integer siteId, Integer isOpen){
		List<LightApp> applist = new ArrayList<LightApp>();
		applist =  lightAppDAO.findBySiteId(NumberUtil.getInt(siteId), NumberUtil.getInt(isOpen));
 		return applist;
	} 
	
	/**
	 * 通过id删除实体
	 * @param ids
	 * @return
	 * @throws OperationException
	 */
	public boolean removeByIds(String ids) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		int colNum = colDAO.findCountByAppid(idList);
		if (colNum>0) {
			throw new OperationException("存在被栏目调用的轻应用,请先修改相关栏目!");
		}
		return lightAppDAO.deleteByIds(idList);
	}
	
	/**
	 * 修改轻应用
	 * @param lightApp
	 * @return
	 * @throws OperationException
	 */
	public boolean modify(LightAppFormBean lightApp, MultipartFile iconFile) throws OperationException {
		String iconOldName = "";
		String iconNewName = "";
		if(lightApp == null){
			return false;
		} 
		//判重
		int num = lightAppDAO.findNumByName(NumberUtil.getInt(lightApp.getIid()), 
				lightApp.getName(), NumberUtil.getInt(lightApp.getSiteId()), NumberUtil.getInt(lightApp.getLightType()));
		if(num > 0){
			throw new OperationException("轻应用名已存在,请重新设置！");
		}
		
		//保存图片
		if(iconFile != null){
			MultipartFileInfo info = MultipartFileInfo.getInstance(iconFile);
			iconOldName = info.getFileName() + "." + info.getFileType();
			iconNewName = this.saveIconFile(iconFile, lightApp.getIid());
			
			lightApp.setIconNewName(iconNewName);
			lightApp.setIconOldName(iconOldName);
		}else{
			LightApp app = this.findByIid(lightApp.getIid());
			lightApp.setIconNewName(app.getIconNewName());
			lightApp.setIconOldName(app.getIconOldName());
		}
		
		boolean isSuccess =  lightAppDAO.update(lightApp);
		if(isSuccess){
			try {
				CacheUtil.removeAll(StaticValues.CACHE_COL);
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		return isSuccess;
	}
	
	/**
	 * 修改
	 * @param lightApp
	 * @return
	 */
	public boolean modify(LightApp lightApp){
		return lightAppDAO.update(lightApp);
	}
	
	/**
	 * 修改轻应用时修改轻应用栏目
	 * @param lightApp
	 * @return
	 * @throws OperationException
	 */
	public boolean modifyCol(LightAppFormBean lightApp) throws OperationException {
		if(lightApp == null){
			return false;
		} 
		int num = lightAppDAO.findNumByName(NumberUtil.getInt(lightApp.getIid()), 
				lightApp.getName(), NumberUtil.getInt(lightApp.getSiteId()), NumberUtil.getInt(lightApp.getLightType()));
		if(num > 0){
			throw new OperationException("轻应用名已存在,请重新设置！");
		}
		boolean isSuccess =  lightAppDAO.update(lightApp);
		if(isSuccess){
			try {
				CacheUtil.removeAll(StaticValues.CACHE_COL);
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		return isSuccess;
	}
	/**
	 * 通过网站id查找最小排序id
	 * @param siteId
	 * @return
	 */
	public int findMinOrderIdBySiteId(Integer siteId){
		if(NumberUtil.getInt(siteId) <= 0){
			return 0;
		}
		int minOrderid=lightAppDAO.findMinOrderIdBySiteId(siteId);
		return minOrderid;
	}
	
	/**
	 * 更新轻应用的orderid
	 * @param ids
	 *            ids
	 * @param orderids
	 *            orderids
	 * @return boolean
	 * @throws OperationException OperationException
	 */
	public boolean modifyOrderIdById(String ids, String orderids) throws OperationException{
		if (ids == null || ids.length() == 0 || orderids == null || orderids.length() == 0) {
			return false;
		}
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		List<Integer> ordersList = StringUtil.toIntegerList(orderids, ",");
		boolean isSuccess = false;
		for (int i = 0, len = idsList.size(); i < len; i++) {
			isSuccess = lightAppDAO.updateOrderIdById(idsList.get(i), ordersList.get(i));
		}
		return isSuccess;
	}
	
	/**
	 * 更新有效性
	 * @param ids
	 *            ID
	 * @param enable
	 *            是否有效<br/>
	 *            1 - 有效<br/>
	 *            0 - 无效
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean modifyEnable(String ids, int enable) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		boolean issuccess=lightAppDAO.updateEnable(idList, enable);
		return issuccess;
	}
	
	/**
	 * 通过应用名称查找实体
	 * @param lightAppName
	 * @param siteid
	 * @return
	 */
	public LightApp findByName(String lightAppName,Integer siteid) {
		if(lightAppName == null){
			return null;
		}
		return lightAppDAO.findByName(lightAppName,siteid);
	}
	/**
	 * 通过应用名称查找实体
	 * @param lightAppName
	 * @param siteid
	 * @return
	 */
	public LightApp findById(Integer id,Integer siteid) {
		if(NumberUtil.getInt(id) == 0){
			return null;
		}
		return lightAppDAO.findById(id,siteid);
	}

	/**
	 * 根据站点id和pid查找实体
	 * @param siteId
	 * @param pid
	 * @return
	 */
    public List<LightApp> findByPid(Integer siteId, String pid) {
        List<LightApp> applist = new ArrayList<LightApp>();
        applist =  lightAppDAO.findByPid(NumberUtil.getInt(siteId), pid);
        return applist;
    }
    
    /**
	 * 获得当前库里的keyvalue
	 */
	public List<LightApp> findAllLightApp(int siteId){
		return lightAppDAO.findAllLightApp(siteId);
	}
	
	/**
	 * 根据lightType查找应用
	 * 
	 */
	public boolean findAppByLightType(int lightType){
		return lightAppDAO.findAppByLightType(lightType);
	}
	
	/**
	 * 根据groupName查找应用
	 */
	public boolean findAppByGroupName(String groupName){
		return lightAppDAO.findAppByGroupName(groupName);
	}
	
	/**
	 * 根据name查找应用
	 * 
	 */
	public boolean findAppByName(String name){
		return lightAppDAO.findAppByName(name);
	}
	
	/**
	 * 根据keyValue查找应用
	 * 
	 */
	public boolean findAppByKeyValue(String keyValue, int siteId){
		return lightAppDAO.findAppByKeyValue(keyValue, siteId);
	}
	
	/**
	 * 修改轻应用无比较
	 * @param lightApp
	 * @return
	 * @throws OperationException
	 */
	public boolean update(String name, String appIssueUrl, Integer lightType, String lightTypeName, String groupName, String keyValue){

		boolean isSuccess =  lightAppDAO.updateApp(name, appIssueUrl, lightType, lightTypeName, groupName, keyValue);
		return isSuccess;
	}
	
	public boolean addApp(LightAppFormBean lightApp){
		return lightAppDAO.insert(lightApp)>0;
	}
	
	/**
	 * 根据name获取iid
	 * @param name
	 * @return
	 */
	public int getIidByName(String name){

		return lightAppDAO.getIidByName(name);		
	}
	
	/**
	 * 通过keyValue删除应用
	 */
	public boolean deleteBykeyValue(String keyValue) {
		return lightAppDAO.deleteBykeyValue(keyValue);
	}
	
	/**
	 * 查找站点下默认轻应用
	 * @param siteId
	 * @return
	 */
	public List<LightApp> findDefaultAppBySiteId(int siteId){
	    return lightAppDAO.findDefaultAppBySiteId(siteId);
	}
	

	/**
	 * 通过ids查找LightApp实体
	 * @param idList
	 * @return
	 */
	public List<LightApp> findByAppIds(String lightAppIds){
		if(lightAppIds == null){
			return null;
		}
		return lightAppDAO.findByAppIds(lightAppIds);
	}
	
	/**
	 * 通过ids查找实体
	 * @param lightAppIds
	 * @return
	 */
	public List<LightApp> findByIdList(List<Integer> ids){
		if(CollectionUtils.isEmpty(ids)){
			return null;
		}
		return lightAppDAO.findByIdList(ids);
	}
	
	/**
	 * 保存图标图片
	 * @param iconfile
	 * @return
	 */
	public String saveIconFile(MultipartFile iconfile, Integer iid) {
		MultipartFileInfo info = MultipartFileInfo.getInstance(iconfile);
		String type = info.getFileType();
		if(!StringUtil.equals(type.toLowerCase(), "png")){
			throw new OperationException("只允许上传png格式的图片");
		}
		String iconNewName = StringUtil.getUUIDString()+ "." + info.getFileType();
		String iconPath = "/lightapp/icon/" + iid + "/" + iconNewName;
		File newFile = new File(BaseInfo.getRealPath() + iconPath);
		System.out.println(BaseInfo.getRealPath() + iconPath);
		ControllerUtil.writeMultipartFileToFile(newFile, iconfile);
		
		return iconNewName;
	}
	
	/**
     * 生成二维码修改
     * @param qrCodeAddress
     * @param iid
     * @return
     */
	public boolean updataAppQrCodeAddress(String qrCodeAddress,Integer iid){
		return  lightAppDAO.updataAppQrCodeAddress(qrCodeAddress, iid);
	}


}