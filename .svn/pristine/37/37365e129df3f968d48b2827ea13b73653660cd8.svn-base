package com.hanweb.jmp.apps.service.broke;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;

import com.hanweb.jmp.apps.dao.broke.BrokeTypeDAO;
import com.hanweb.jmp.apps.entity.broke.BrokeType;

public class BrokeTypeService {
	
	/**
	 * brokeTypeDAO
	 */
	@Autowired
	private BrokeTypeDAO brokeTypeDAO;
	
	/**
	 * 通过网站id查找实体
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public List<BrokeType> findBySiteId(Integer siteId) {
		List<BrokeType> list = null;
		if(NumberUtil.getInt(siteId) > 0){
			list = brokeTypeDAO.findBrokeType(siteId);
		}
		if(list == null){
			list = new ArrayList<BrokeType>();
		}
		return list;
	}
	
	/**
	 * 是否审核
	 * @param iid iid
	 * @return    设定参数 .
	*/
	public boolean isAudit(Integer iid) {
		Integer audit = brokeTypeDAO.findAudit(iid);
		return NumberUtil.getInt(audit) != 0;
	}
	
	/**
	 * 修改报料分类
	 * @param brokeType brokeType
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean modify(BrokeType brokeType) throws OperationException{
		if(brokeType == null){
			return false;
		}
		return brokeTypeDAO.update(brokeType);
	}
	
	/**
	 * 通过iid查找实体
	 * @param iid iid
	 * @return    设定参数 .
	*/
	public BrokeType findByIid(Integer iid) {
		if(NumberUtil.getInt(iid) <= 0){
			return null;
		}
		return brokeTypeDAO.queryForEntityById(iid);
	}
	
	/**
	 * 修改排序id
	 * @param ids ids
	 * @param orderids orderids
	 * @return boolean
	 * @throws OperationException    设定参数 .
	 */
	public boolean modifyOrderIdById(String ids, String orderids) throws OperationException{
		if(StringUtil.isEmpty(ids) || StringUtil.isEmpty(orderids)){
			return false;
		}
		List<Integer> idList = StringUtil.toIntegerList(ids);
		List<Integer> orderList = StringUtil.toIntegerList(orderids);
		if(idList.size() != orderList.size()){
			return false;
		}
		for(int i=0; i< idList.size(); i++){
			int id = NumberUtil.getInt(idList.get(i));
			int order = NumberUtil.getInt(orderList.get(i));
			brokeTypeDAO.updateOrder(id, order);
		}
		return true;
	}
	
	/**
	 * 删除报料分类
	 * @param ids ids
	 * @return boolean
	 * @throws OperationException    设定参数 .
	 */
	public boolean removeByIds(String ids) throws OperationException{
		boolean hasSubInfo = this.checkHaveInfo(ids);
		if (hasSubInfo) {
			throw new OperationException("所选分类存在信息,请先删除信息!");
		}
		return brokeTypeDAO.deleteByIds(StringUtil.toIntegerList(ids));
	}
	
	/**
	 * 新增报料分类
	 * @param brokeType brokeType
	 * @return  boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean add(BrokeType brokeType) throws OperationException{
		if(brokeType == null){
			return false;
		}
		Integer iid = brokeTypeDAO.insert(brokeType);
		return NumberUtil.getInt(iid) > 0;
	}
	
	/**
	 * 查询最大排序id
	 * @return    设定参数 .
	 */
	public int findMaxOrderId() {
		return NumberUtil.getInt(brokeTypeDAO.findMaxOrderId());
	}
	
	/**
	 * 通过网站id查找实体
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	public List<BrokeType> findBrokeTypeBySiteId(Integer siteId) {
		if(NumberUtil.getInt(siteId) <= 0){
			return null;
		}
		return brokeTypeDAO.findBrokeType(siteId);
	}
	
	/**
	 * 检查是否有信息 
	 * @param ids
	 *            报料分类ID串 如:1,2,3
	 * @return true - 有信息<br/>
	 *         false - 无信息
	 */
	public boolean checkHaveInfo(String ids) {
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idsList)) {
			return false;
		}
		int num = brokeTypeDAO.findCountSubInfo(idsList);
		return num > 0;
	}
	
}