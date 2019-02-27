package com.hanweb.jmp.cms.service.sign;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;

import com.hanweb.jmp.cms.dao.infos.InfoDAO;
import com.hanweb.jmp.cms.dao.sign.SignDAO;
import com.hanweb.jmp.cms.entity.sign.Sign;

public class SignService {

	/**
	 * signDAO
	 */
	@Autowired
	SignDAO signDAO;
	
	/**
	 * signRelService
	 */
	@Autowired
	SignRelService signRelService;
	
	/**
	 * infoDAO
	 */
	@Autowired
	InfoDAO infoDAO;
	
	/**
	 * 根据主键iid获得实体
	 * @param iid 主键id
	 * @return sign
	 */
	public Sign findByIid(int iid){
		if(iid <= 0){
			return null;
		}
		return signDAO.findByIid(iid);
	}
	
	/**
	 * 根据网站id和模块id来查找角标
	 * @param mid 模块id
	 * @param siteId 网站id
	 * @param colId colId
	 * @return list
	 */
	public List<Sign> findByMid(Integer mid, Integer siteId, Integer colId){
		if(NumberUtil.getInt(mid) <= 0 || NumberUtil.getInt(siteId) <= 0){
			return null;
		}
		return signDAO.findByMid(mid, siteId, NumberUtil.getInt(colId));
	}
	
	/**
	 * 新增
	 * @param sign 实体
	 * @return boolean
	 * @throws OperationException OperationException
	 */
	public boolean add(Sign sign) throws OperationException{
		if(sign == null){
			return false;
		} 
		if(sign.getMid()==3){
			int num = signDAO.findNumByName(NumberUtil.getInt(sign.getIid()), 
					  sign.getDname(), sign.getMid(), sign.getSiteId());
			if(num > 0){
				throw new OperationException("标签名称已存在,请重新设置！");
			}
		}
		sign.setOrderId(findMinOrderIdBySiteIdAndMid(sign.getMid(), sign.getSiteId()
				, sign.getColId()) - 1);
		return signDAO.insert(sign) > 0;
	}
	
	/**
	 * 修改
	 * @param sign 实体
	 * @return boolean
	 * @throws OperationException OperationException
	 */
	public boolean modify(Sign sign) throws OperationException {
		if(sign == null){
			return false;
		}
		return signDAO.update(sign);
	}
	
	/**
	 * 根据网站id和模块id查找orderid最小的
	 * @param mid 模块id
	 * @param siteId 网站id
	 * @param colId colId
	 * @return int
	 */
	public int findMinOrderIdBySiteIdAndMid(Integer mid, Integer siteId, Integer colId){
		if(NumberUtil.getInt(mid) <= 0 || NumberUtil.getInt(siteId) <= 0){
			return 0;
		}
		return signDAO.findMinOrderIdBySiteIdAndMid(mid, siteId, NumberUtil.getInt(colId));
	}
	 
	/**
	 * 删除角标
	 * @param ids ids
	 * @param siteId siteId
	 * @param mid mid
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean removeByIds(String ids, int siteId, int mid) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		if(NumberUtil.getInt(mid)!= 3){
			if (signRelService.checkSubInfo(ids)) {
				throw new OperationException("所选角标下存在信息,请先删除信息!");
			}
		}
		//标签
		if(NumberUtil.getInt(mid) == 3){
			infoDAO.updateTagid(idList, 0, "jmp_info" + siteId);
		}
		for(int id : idList){
			signRelService.removeBySignIdAndModuleId(id, 3, siteId);
		}
		return signDAO.deleteByIds(idList);
	}
	
	/**
	 * 更新角标的orderid
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
			isSuccess = signDAO.updateOrderIdById(idsList.get(i), ordersList.get(i));
		}
		return isSuccess;
	}
	
	/**
	 * 卡片式获得栏目下信息列表的卡片角标
	 * @param colId 栏目id
	 * @param moduleId 模块id
	 * @param siteId 网站id
	 * @param orderid 排序id
	 * @param type 1=刷新 2=更多
	 * @param flag 变动标记位 true变动 false无变动
	 * @return List<sign>
	 */
	public List<Sign> findByColId(int colId, int moduleId, int siteId, Integer orderid, int type, boolean flag){
		if(colId <= 0 || moduleId <= 0 || siteId <=0){
			return null;
		}
		return signDAO.findByColId(colId, moduleId, siteId, orderid, type, flag);
	}
	
	/**
	 * 通过网站Id和模块Id查找角标实体
	 * @param siteId     网站Id 
	 * @param moduleId   模块Id
	 * @return List<sign>
	 */
	public List<Sign> findBySiteId(int siteId, int moduleId){
		if(moduleId <= 0 || siteId <=0){
			return null;
		}
		return signDAO.findBySiteId(siteId, moduleId);
	}
	
}