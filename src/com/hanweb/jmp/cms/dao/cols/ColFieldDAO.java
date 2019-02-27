package com.hanweb.jmp.cms.dao.cols;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.util.NumberUtil;

import com.hanweb.jmp.cms.entity.cols.ColField;
import com.hanweb.jmp.constant.Tables;

public class ColFieldDAO extends BaseJdbcDAO<Integer, ColField>{
	
	/**
	 * 通过ID获取实体
	 * 
	 * @param iid
	 *            字段ID
	 * @return 字段实体
	 */
	public ColField findByIid(Integer iid) {
		String sql = getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		ColField colfieldEn = this.queryForEntity(query);
		return colfieldEn;
	}
	
	/**
	 * 通过名称获得同名的个数
	 *  
	 * @param iid 
	 * @param fieldname
	 *            字段名称  
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findNumOfSameFieldName(Integer iid, String fieldname) {
		int num = 0; 
		String sql = " SELECT COUNT(1) FROM " + Tables.COLFIELD + " WHERE fieldname=:fieldname ";
		if (NumberUtil.getInt(iid) > 0) {
			sql += " AND iid NOT IN(:iid)";
		}
		Query query = createQuery(sql);
		query.addParameter("iid", iid); 
		query.addParameter("fieldname", fieldname); 
		num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 相同类型相同键值的查重
	 * 
	 * @param iid Integer
	 * @param fieldKey
	 *                键值
	 * @param fieldType
	 *                字段类型
	 * @return int
	 */
	public int findNumOfSameFieldKey(Integer iid, Integer fieldKey, Integer fieldType) {
		int num = 0; 
		String sql = " SELECT COUNT(1) FROM " + Tables.COLFIELD + " WHERE fieldkey=:fieldKey "
		+" AND fieldtype =:fieldType";
		if (NumberUtil.getInt(iid) > 0) {
			sql += " AND iid NOT IN(:iid)";
		}
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		query.addParameter("fieldKey", fieldKey);
		query.addParameter("fieldType", fieldType);
		num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 
	 * 通过ID获取实体
	 * 
	 * @param type Integer
	 * @param siteId
	 *            字段ID
	 * @return 字段实体
	 */
	public List<ColField> findByType(Integer type, Integer siteId) {
		siteId = NumberUtil.getInt(siteId);
		int fieldType = NumberUtil.getInt(type);
		String sql = getEntitySql() + " WHERE fieldtype=:fieldType AND siteid=:siteId";
		Query query = createQuery(sql);
		query.addParameter("fieldType", fieldType);
		query.addParameter("siteId", siteId);
		List<ColField> colfieldEn = this.queryForEntities(query);
		return colfieldEn;
	}

}