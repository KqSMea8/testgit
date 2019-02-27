package com.hanweb.jmp.cms.service.sign;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;

import com.hanweb.jmp.cms.dao.sign.SignRelDAO;
import com.hanweb.jmp.cms.entity.sign.SignRel;
import com.hanweb.jmp.global.dao.OutSideUserSubscribeColRelDAO;

public class SignRelService {
	
	/**
	 * signRelDAO
	 */
	@Autowired
	SignRelDAO signRelDAO;
	
	@Autowired
	OutSideUserSubscribeColRelDAO outSideUserSubscribeColRelDAO;
	

	/**
	 * 检查是否有信息
	 * @param ids
	 *            角标ID串 如:1,2,3
	 * @return true - 有<br/>
	 *         false - 无
	 */
	public boolean checkSubInfo(String ids) {
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idsList)) {
			return false;
		}
		int num = signRelDAO.findCountSubInfo(idsList);
		return num > 0;
	}
	 
	/**
	 * 新增.
	 * @param signRel signRel
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean add(SignRel signRel) throws OperationException{
		if(signRel == null){
			return false;
		}
		return signRelDAO.insert(signRel) > 0;
	}
	
	/**
	 * 修改
	 * @param signRel signRel
	 * @return boolean
	 */
	public boolean modify(SignRel signRel){
		if(signRel == null){
			return false;
		}
		return signRelDAO.update(signRel);
	}
	
	/**
	 * 删除
	 * @param signRel signRel
	 * @return boolean
	 */
	public boolean remove(SignRel signRel){
		if(signRel == null || signRel.getIid()<= 0){
			return false;
		}
		return signRelDAO.deleteById(signRel.getIid());
	}
	 
	/**
	 * 删除角标信息
	 * @param ids ids
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean removeByIds(String ids) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		List<SignRel> signRelList = this.findByIid(idList);
		for(SignRel signRel : signRelList) {
			signRel.getAttrid();
			outSideUserSubscribeColRelDAO.deleteByColIdAndLoginName(NumberUtil.getInt(signRel.getAttrid()), 
					signRel.getSiteId());
		}
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		return signRelDAO.deleteByIds(idList);
	}
	
	/**
	 * 删除信息/栏目时，关系表也要删除
	 * @param infoIds infoIds
	 * @return boolean
	 * @throws OperationException OperationException
	 */
	public boolean removeByInfoIds(List<Integer> infoIds) throws OperationException {
		if (CollectionUtils.isEmpty(infoIds)) {
			return false;
		}
		return signRelDAO.deleteByInfoIds(infoIds);
	}
	
	/**
	 * 根据角标id和模块id删除记录
	 * @param 
	 * @param moduleId 模块id
	 * @param siteId 网站id
	 * @return boolean
	 */
	public boolean removeBySignIdAndModuleId(int dimensionid, int moduleId, int siteId){
		if(dimensionid <= 0 || moduleId <= 0 || siteId <= 0){
			return false;
		}
		return signRelDAO.deleteBySignIdAndModuleId(dimensionid, moduleId, siteId);
	}
	
	/**
	 * 根据信息id及模块id获得关系实体
	 * @param infoId infoId
	 * @param mid mid
	 * @return signRel
	 */
	public SignRel findRelByInfoId(int infoId, int mid, int siteid){
		if(infoId <= 0){
			return null;
		}
		return signRelDAO.findRelByInfoId(infoId, mid, siteid);
	}
	
	/**
	 * 根据信息id及模块id获得关系实体集合
	 * @param infoId infoId 
	 * @return signRel
	 */
	public List<SignRel> findRelByInfoId(int infoId){
		if(infoId <= 0){
			return null;
		}
		return signRelDAO.findRelByInfoId(infoId);
	}
	
	/**
	 * 根据角标id及模块id获得实体
	 * @param did 角标id
	 * @param mid 模块id
	 * @param siteId 网站id
	 * @return List<signRel>
	 */
	public List<SignRel> findRelBySignId(int did, int mid, int siteId){
		if(did <= 0 || mid <=0 || siteId <= 0){
			return null;
		}
		return signRelDAO.findRelBySignIdAndModuleId(did, mid, siteId);
	}
	
	/**
	 * 根据模块id获得实体
	 * @param mid 模块id
	 * @param siteId 网站id
	 * @return List<signRel>
	 */
	public List<SignRel> findRelByModuleId(int mid, int siteId){
		if(mid <=0 || siteId <= 0){
			return null;
		}
		return signRelDAO.findRelByModuleId(mid, siteId);
	}
		
	/**
	 * 根据角标Id和模块id查找最小的orderid
	 * @param 
	 * @param moduleId 模块Id
	 * @param siteId 网站id
	 * @return int
	 */
	public int findMinOrderIdBySignIdAndModuleId(int dimensionid, int moduleId, int siteId){
		if(dimensionid <= 0 || moduleId <= 0 || siteId <= 0){
			return 0;
		}
		return signRelDAO.findMinOrderIdBySignIdAndModuleId(dimensionid, moduleId, siteId);
	} 
	
	/**
	 * 更新角标下订阅栏目的orderid
	 * @param ids ids
	 * @param orderids orderids
	 * @return boolean
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
			isSuccess = signRelDAO.updateOrderIdById(idsList.get(i), ordersList.get(i));
		}
		return isSuccess;
	}
	
	/**
	 * 根据角标id及模块id获得实体
	 * @param did 角标id
	 * @param mid 模块id
	 * @param siteId 网站id
	 * @return List<signRel>
	 */
	public List<SignRel> findRelBySignIdAndModuleId(int did, int mid, int siteId){
		if(did <= 0 || mid <=0 || siteId <= 0){
			return null;
		}
		return signRelDAO.findRelBySignIdAndModuleId(did, mid, siteId);
	}
	
	/**
	 * 根据Iid获得实体
	 * @param idList idList
	 * @return List<signRel>
	 */
	public List<SignRel> findByIid(List<Integer> idList){
		if(idList == null || idList.size() <= 0){
			return null;
		}
		return signRelDAO.findByIid(idList);
	}
	
	/**
	 * 根据网站id和频道id获取该频道选择过的栏目
	 * @param siteId 网站id
	 * @param iid 频道id
	 * @return String 网站ids 如 1,2,3
	 */
	public String findCheckedInfoIds(Integer siteId, Integer iid){
		List<SignRel> list = signRelDAO.findCheckedSiteIds(siteId, iid);
		StringBuffer buffer = new StringBuffer(128);
		String siteIds = "";
		for (SignRel signRel : list) {
			buffer.append(",").append(signRel.getDimensionid());
		}
		if(buffer.length() > 0){
			siteIds = buffer.substring(1);
		}
		return siteIds;
	}
	
	/**
	 * 根据信息id和网站id删除
	 * @param infoId    信息id	
	 * @param siteId	网站id
	 * @return  boolean
	 */
	public boolean deleteByInfoidAndSiteId(Integer infoId, Integer siteId, Integer mid){
		return signRelDAO.deleteByInfoidAndSiteId(infoId, siteId, mid);
	}
	
}