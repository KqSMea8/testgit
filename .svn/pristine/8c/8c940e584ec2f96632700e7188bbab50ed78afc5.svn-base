package com.hanweb.jmp.cms.dao.infos;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.InsertSql;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;

import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.dao.field.FieldDAO;
import com.hanweb.jmp.sys.entity.field.Field;

public class InfoExpandDAO extends BaseJdbcDAO<Integer, Object>{
	
	/**
	 * FieldDAO
	 */
	@Autowired
	private FieldDAO fieldDAO;
	
	/**
	 * 删除扩展字段数据
	 * @param ids  信息IDS
	 * @param siteId Integer
	 * @return   boolean  成功？
	 */
	public boolean deleteByInfoIds(List<Integer> ids, Integer siteId){
		String sql = "DELETE FROM " + Tables.INFO + " WHERE infoid IN (:ids)";
		sql = sql.replace(Tables.INFO, "jmp_info" + siteId);
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		return delete(query);
	}
	
	/**
	 * 删除信息
	 * @param colId 栏目
	 * @param siteId Integer      
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean deleteByColids(int colId, Integer siteId) {
		String sql = "DELETE FROM " + Tables.INFO + " WHERE colid = :colId";
		sql = sql.replace(Tables.INFO, "jmp_info" + siteId);
		Query query = createQuery(sql);
		query.addParameter("colId", colId);
		return delete(query);
	}
	
	/**
	 * insert
	 * @param contentMap Map<String, Object>
	 * @return Integer
	 */
	public Integer insert(Map<String, Object> contentMap){
		int siteid = NumberUtil.getInt(contentMap.get("siteid"));
		InsertSql sql = new InsertSql("jmp_info" + siteid);
		sql.addInt("siteid", siteid);
		sql.addInt("colid", NumberUtil.getInt(contentMap.get("colid")));
		List<Field> fieldList = fieldDAO.findUnSys(siteid);
		if(fieldList!=null){
			String fieldName = "";
			for(Field field : fieldList){
				fieldName = field.getFieldName();
				sql.addString(fieldName, StringUtil.getString(contentMap.get(fieldName)));
			}
		}
		return NumberUtil.getInt(super.insert(sql));
	}
	
}