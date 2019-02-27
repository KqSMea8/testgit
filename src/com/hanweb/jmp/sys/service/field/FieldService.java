package com.hanweb.jmp.sys.service.field;
  
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;

import com.hanweb.jmp.sys.controller.field.FieldFormBean;
import com.hanweb.jmp.sys.dao.field.FieldDAO;
import com.hanweb.jmp.sys.entity.field.Field;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.ditch.SynFieldService;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.setup.dao.DataInitDAO;

public class FieldService { 
	
	/**
	 * fieldDAO
	 */
	@Autowired
	private FieldDAO fieldDAO; 
	
	@Autowired
	private DataInitDAO dataInitDAO;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private SynFieldService synFieldService;
	
	/**
	 * 后台新建字段
	 * @param field field
	 * @param siteId siteId
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             操作异常
	 */
	public boolean add(FieldFormBean field, Integer siteId) throws OperationException {
		if (field == null) {
			return false;
		} 
		int orderid = fieldDAO.findMaxOrderIdBySiteID(field.getSiteId())+1;
		field.setOrderId(orderid);
		if(StringUtil.isEmpty(field.getFieldName())){
			int maxid = fieldDAO.findMaxID()+1;
			String fieldname = "field_"+maxid;
			field.setFieldName(fieldname);
		} 
		int num = fieldDAO.findNumOfSameFieldName(field.getFieldName());
		if(num>0){
			throw new OperationException("数据库字段名称重复！");
		}
		int fieldType = field.getFieldType();
		//全局自定义字段
		if(fieldType!=0){
			field.setSiteId(0);
		}
		int iid = fieldDAO.insert(field);
        if(iid<=0){
        	throw new OperationException("新增信息字段失败！");
        } 
        fieldDAO.addTableField(field, siteId);
		return iid > 0;
	}

	/**
	 * 删除字段
	 * @param ids
	 *            字段ID串 如:1,2,3 
	 * @param siteId
	 *            网站id
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean removeByIds(String ids, Integer siteId) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		fieldDAO.removeTableFields(ids, siteId);
		for(Integer iid : idList){
		    Field field = fieldDAO.findByIid(iid);
		    if(field!=null){
		        synFieldService.checkSynField(field.getFieldName(), siteId);
		    }
		}
		boolean isSuccess = false;
		isSuccess = fieldDAO.deleteByIds(idList); 
		if (!isSuccess) {
			throw new OperationException("删除字段失败！");
		} 
		return isSuccess;
	}
	
	/**
	 * 根据网站id获取字段集合集合
	 * @param siteid
	 *            网站id
	 * @return List<Field>
	 */
	public List<Field> findAllBySiteid(Integer siteid) {
		return fieldDAO.findBySiteid(siteid);
	}

	/**
	 * 通过网站id获取字段
	 * @param siteid siteid
	 * @return    设定参数 .
	*/
	public Map<String, String> findBySiteId(Integer siteid){
		List<Field> list = fieldDAO.findUnSys(siteid);
		Map<String, String> map = new HashMap<String, String>();
		if(list != null){
			for(Field field : list){
				map.put(field.getJgetName(), field.getFieldName());
			}
		}
		return map;
	}
	
	/**
	 * 通过网站id获取同步字段
	 * @param siteid siteid
	 * @return    设定参数 .
	*/
	public Map<String, String> findSynBySiteId(Integer siteid){
		Map<String, String> map = new HashMap<String, String>();
		List<Field> list = fieldDAO.findSysn(siteid);
		if(list != null){
			for(Field field : list){
				map.put(field.getJgetName(), field.getFieldName());
			}
		} 
		return map;
	}
	
	/**
	 * 根据网站id获取自定义字段集合
	 * @param siteid
	 *            网站id
	 * @return List<Field>
	 */
	public List<Field> findUnSysBySiteid(Integer siteid) {
		return fieldDAO.findUnSys(siteid);
	}

	/**
	 * 根据iid获取实体
	 * @param iid iid
	 * @return Field
	 */
	public Field findByIid(Integer iid) {
		return fieldDAO.queryForEntityById(iid);
	}
	
	/**
	 * 新增字段
	 * @param siteid
	 * @return
	 */
	public boolean addFieldsBySiteId(Integer siteid) {
		List<String> sqls = FileUtil.readLines(new File(BaseInfo.getRealPath()
				+ "/WEB-INF/sql/init/field.sql"), "utf-8");
		Site site = siteService.findByIid(siteid);
		if (sqls != null) {
			for (String sql : sqls) {
				if (StringUtils.isBlank(sql)) {
					continue;
				}
				if (sql.endsWith(";")) {
					sql = sql.substring(0, sql.length() - 1);
				}
				if(NumberUtil.getInt(site.getSyntype())==0 
						|| NumberUtil.getInt(site.getSyntype())==2){
					sql = sql.replace("{title}", "'标题'");
					sql = sql.replace("{content}", "'内容'");
					sql = sql.replace("{siteid}", ""+siteid);
				} else {
					sql = sql.replace("{title}", "'信息标题'");
					sql = sql.replace("{content}", "'信息内容'");
					sql = sql.replace("{siteid}", ""+siteid);
				}
				Query query = dataInitDAO.createQuery(sql);
				dataInitDAO.execute(query);
			}
		}
		return true;
	}
	
	/**
	 * 更新字段的排序id
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
			isSuccess = fieldDAO.updateOrderIdById(ordersList.get(i), idsList.get(i));
		}
		return isSuccess;
	}
	
	/**
	 * 修改字段
	 * @param field
	 *            字段实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean modify(FieldFormBean field) throws OperationException {
		if (field == null || NumberUtil.getInt(field.getIid()) == 0) {
			return false;
		}
		Integer iid = field.getIid();
		boolean isSuccess = false;
		int num = fieldDAO.findNumOfSameName(iid, field.getName(), field.getSiteId());
		if (num > 0) {
			throw new OperationException("名称已存在,请重新设置！");
		}
		if(StringUtil.isNotEmpty(field.getJgetName())){
			num = fieldDAO.findNumOfSameJgetName(iid, field.getJgetName(), field.getSiteId());
			if (num > 0) {
				throw new OperationException("同步名称已存在,请重新设置！");
			} 
		}
		int fieldType=field.getFieldType();
		//全局自定义字段
		if(fieldType!=0){
			field.setSiteId(0);
		}
		isSuccess = fieldDAO.update(field);
		if (!isSuccess) {
			throw new OperationException("更新操作失败！");
		} 
		return isSuccess;
	}

}