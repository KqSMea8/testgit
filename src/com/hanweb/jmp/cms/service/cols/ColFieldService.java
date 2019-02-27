package com.hanweb.jmp.cms.service.cols;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.commons.collections.CollectionUtils;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;

import com.hanweb.jmp.cms.dao.cols.ColFieldDAO;
import com.hanweb.jmp.cms.entity.cols.ColField;
import com.hanweb.jmp.cms.controller.cols.ColFieldFormBean;

public class ColFieldService {
	
	/**
	 * colfieldDAO
	 */
	@Autowired
	private ColFieldDAO colfieldDAO; 
	
	/**
	 * 新建字段
	 * @param colfield colfield
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean add(ColFieldFormBean colfield) throws OperationException {
		if (colfield == null) {
			return false;
		} 
		int num=colfieldDAO.findNumOfSameFieldName(colfield.getIid(), colfield.getFieldName());
		int num1= colfieldDAO.findNumOfSameFieldKey(colfield.getIid(), colfield.getFieldKey(), 
				colfield.getFieldType());
		if(num > 0){
			throw new OperationException("数据库字段名称重复，请重新输入！");
		}
		if(num1 > 0){
			throw new OperationException("键值重复，请重新输入！");
		}
		int iid = colfieldDAO.insert(colfield);
		return iid > 0;
	}

	 
	/**
	 * 删除字段
	 * @param ids
	 *            字段ID串 如:1,2,3 
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean removeByIds(String ids) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		boolean isSuccess = false;
		isSuccess = colfieldDAO.deleteByIds(idList); 
		if (!isSuccess) {
			throw new OperationException("删除字段失败！");
		} 
		
		return isSuccess;
	}
	
	/**
	 * 修改字段
	 * @param colfield
	 *            字段实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean modify(ColFieldFormBean colfield) throws OperationException {
		if (colfield == null || NumberUtil.getInt(colfield.getIid()) == 0) {
			return false;
		}
		boolean isSuccess = false;
		int num=colfieldDAO.findNumOfSameFieldName(colfield.getIid(), colfield.getFieldName());
		int num1= colfieldDAO.findNumOfSameFieldKey(colfield.getIid(), colfield.getFieldKey(), 
				colfield.getFieldType());
		if(num > 0){
			throw new OperationException("数据库字段名称重复，请重新输入！");
		}
		if(num1 > 0){
			throw new OperationException("键值重复，请重新输入！");
		}
		isSuccess = colfieldDAO.update(colfield);
		if (!isSuccess) {
			throw new OperationException("更新操作失败！");
		} 
		return isSuccess;
	} 
	
	/**
	 * 根据iid获取实体
	 * @param iid iid
	 * @return ColField
	 */
	public ColField findByIid(Integer iid) {
		return colfieldDAO.queryForEntityById(iid);
	}
	 
	/**
	 * 通过类型查询
	 * @param type type
	 * @param siteId siteId
	 * @return    设定参数 .
	*/
	public Map<Integer, String> findByType(Integer type, Integer siteId){
		List<ColField> colField = colfieldDAO.findByType(type, siteId);
		Map<Integer, String> map = new HashMap<Integer, String>();
		for(ColField col : colField){
			if(NumberUtil.getInt(col.getShowList())==1){
				map.put(col.getFieldKey(), col.getFieldName());
			}
		}
		return map;
	}

}