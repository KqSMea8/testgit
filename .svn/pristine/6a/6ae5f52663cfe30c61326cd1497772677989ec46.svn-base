package com.hanweb.jmp.sys.dao.field;  

import java.util.List;

import org.apache.commons.collections.CollectionUtils; 

import com.hanweb.common.BaseInfo;
import com.hanweb.common.basedao.BaseJdbcDAO; 
import com.hanweb.common.basedao.Query; 
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.NumberUtil;  
import com.hanweb.common.util.StringUtil; 

import com.hanweb.jmp.constant.Tables;  
import com.hanweb.jmp.sys.entity.field.Field;

public class FieldDAO extends BaseJdbcDAO<Integer, Field> { 

	/**
	 * 通过ID获取实体
	 * @param iid
	 *            字段ID
	 * @return 字段实体
	 */
	public Field findByIid(Integer iid) {
		String sql = getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		Field fieldEn = this.queryForEntity(query);
		return fieldEn;
	}
	
	/**
	 * 获得指定ID串的字段集合
	 * @param ids
	 *            信息字段ID串 如:1,2,3
	 * @return 信息字段集合
	 */
	public List<Field> findByIds(String ids) {
		List<Integer> idList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idList)) {
			return null;
		}
		String sql =getEntitySql()  + " WHERE  iid IN (:idsList) ";	 
		Query query = createQuery(sql);
		query.addParameter("idsList", idList);
		List<Field> fieldList = this.queryForEntities(query);
		return fieldList;
	}
	
	/**
	 * 通过网站ID串获取字段实体
	 * @param siteid 网站id
	 * @return List<Field>
	 */
	public List<Field> findBySiteid(Integer siteid) {
		String sql = getEntitySql() + " WHERE siteid =:siteid ORDER BY orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteid);
		return this.queryForEntities(query);
	}
	
	/**
	 *  获取同步
	 * @param siteid 网站id
	 * @return List<Field>
	 */
	public List<Field> findSysn(Integer siteid) {
		String sql = getEntitySql() + " WHERE siteid =:siteid ORDER BY iid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteid);
		return this.queryForEntities(query);
	}
	
	/**
	 *  获取自定义字段
	 * @param siteid 网站id
	 * @return List<Field>
	 */
	public List<Field> findUnSys(Integer siteid) {
		String sql = getEntitySql() + " WHERE (siteid =:siteid AND fieldtype=0) ORDER BY iid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteid);
		return this.queryForEntities(query);
	}
	
	/**
	 * 更新字段id的orderid
	 * @param orderid
	 *            排序id
	 * @param iid
	 *            字段id
	 * @return true/false
	 */
	public boolean updateOrderIdById(Integer orderid, int iid) {
		UpdateSql sql = new UpdateSql(Tables.FIELD);
		sql.addInt("orderid", orderid);
		sql.setWhere("iid = :iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
	}
	
	/**
	 * 通过名称获得同名的个数
	 * @param iid
	 *            字段ID
	 * @param name
	 *            栏目名称 
	 * @param siteId
	 *            网站id
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findNumOfSameName(Integer iid, String name,  Integer siteId) {
		int num = 0;
		String sql = " SELECT COUNT(1) FROM " + Tables.FIELD + " WHERE name=:name ";
		if (NumberUtil.getInt(iid) > 0) {
			sql += " AND iid NOT IN(:iid)";
		}
		if (NumberUtil.getInt(siteId) > 0) {
			sql += " AND siteid=:siteId";
		} 
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		query.addParameter("name", name);
		query.addParameter("siteId", siteId);
		num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 通过名称获得同名的个数
	 * @param iid
	 *            字段ID
	 * @param jgetName
	 *            同步名称 
	 * @param siteId
	 *            网站id
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findNumOfSameJgetName(Integer iid, String jgetName,  Integer siteId) {
		int num = 0;
		String sql = " SELECT COUNT(1) FROM " + Tables.FIELD + " WHERE jgetname=:jgetname ";
		if (NumberUtil.getInt(iid) > 0) {
			sql += " AND iid NOT IN(:iid)";
		}
		if (NumberUtil.getInt(siteId) > 0) {
			sql += " AND siteid=:siteId";
		} 
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		query.addParameter("jgetname", jgetName);
		query.addParameter("siteId", siteId);  
		num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 通过名称获得同名的个数
	 * @param fieldname
	 *            字段名称  
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findNumOfSameFieldName(String fieldname) {
		int num = 0; 
		String sql = " SELECT COUNT(1) FROM " + Tables.FIELD + " WHERE fieldname=:fieldname ";
		Query query = createQuery(sql);
		query.addParameter("fieldname", fieldname); 
		num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 根据网站ID查询最大ORDERID
	 * @param siteId     网站ID
	 * @return int          最大ORDERID
	 */
	public int findMaxOrderIdBySiteID(Integer siteId) {
		int orderId = 0;
		String sql = " SELECT MAX(orderid) FROM " + Tables.FIELD
				   + " WHERE siteid = :siteId";
		Query query = createQuery(sql);
		query.addParameter("siteId", siteId);
		orderId = this.queryForInteger(query); 
		return orderId;
	}
	
	/**
	 * 查询最大ID 
	 * @return           最大ID
	 */
	public int findMaxID() {
		int orderId = 0;
		String sql = "SELECT MAX(iid) FROM " + Tables.FIELD;
		Query query = createQuery(sql); 
		orderId = this.queryForInteger(query); 
		return orderId;
	}
	
	/**
	 * 给机构用户表添加字段
	 * @param fieldEn     字段实体
	 * @param siteId      Integer
	 */
	public void addTableField(Field fieldEn, Integer siteId) {
		if (fieldEn == null) {
			return;
		} 
		String fieldname=fieldEn.getFieldName();
		int length=fieldEn.getFieldLength();
		String sql = ""; 
		switch (BaseInfo.getDbType()) {
		//ORACLE
		case 1:
			sql = "ALTER TABLE " + Tables.INFO_EXPAND + " ADD "
			+ fieldname + " varchar2("+length+") ";
			break;
		//MSSQL
		case 2:
			sql = "ALTER TABLE " + Tables.INFO_EXPAND + " ADD "
			+ fieldname + " varchar("+length+") ";
			break;
		//MYSQL
		case 5:
			sql = "ALTER TABLE " + Tables.INFO_EXPAND + " ADD "
			+ fieldname + " varchar("+length+") ";
			break;	 
		} 
		sql = sql.replace(Tables.INFO_EXPAND, Tables.INFO + siteId);
		this.executeForDDL(sql);
	}
	
	/**
	 * 删除信息表字段
	 * @param ids     字段IDS
	 * @param siteId Integer
	 */
	public void removeTableFields(String ids, Integer siteId){ 
		List<Field> list = findByIds(ids); 
		String sql = ""; 
		for(Field fieldEn : list){
			switch (BaseInfo.getDbType()) {
			//ORACLE
			case 1:
				sql = "ALTER TABLE " + Tables.INFO_EXPAND + " DROP  COLUMN "
				+ fieldEn.getFieldName();
				break;
			//MSSQL
			case 2:
				sql = "ALTER TABLE " + Tables.INFO_EXPAND  + " DROP  COLUMN "
				+ fieldEn.getFieldName();
				break;
			//MYSQL
			case 5:
				sql = "ALTER TABLE " + Tables.INFO_EXPAND  + " DROP  "
				+ fieldEn.getFieldName();
				break;	 
			}  
			sql = sql.replace(Tables.INFO_EXPAND, Tables.INFO + siteId);
			this.executeForDDL(sql);
		} 
	}

}