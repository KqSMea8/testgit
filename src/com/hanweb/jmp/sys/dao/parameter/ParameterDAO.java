package com.hanweb.jmp.sys.dao.parameter;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;

import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.entity.parameter.Parameter;

public class ParameterDAO extends BaseJdbcDAO<Integer, Parameter>{
	
	/**
	 * 通过网站id获取网站实体
	 * @param siteId Integer
	 *            信息ID
	 * @return 信息实体
	 */
	public Parameter findBySiteId(Integer siteId) {
		String sql = this.getEntitySql() + " WHERE siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		Parameter siteParameter = this.queryForEntity(query);
		return siteParameter;
	}
	
	/**
	 * 通过网站id删除
	 * @param siteid Integer
	 * @return boolean
	 */
	public boolean deleteBySiteId(Integer siteid){
		String sql = "DELETE " + Tables.SITE_DETAIL + " WHERE siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteid);
		return this.delete(query);
	}
	
	/**
	 * 修改模块权限
	 * @param name String
	 * @param state int
	 * @param siteId int
	 * @return boolean
	 */
	public boolean updateModuleState(String name, int state, int siteId) {
		UpdateSql updateSql = new UpdateSql(Tables.SITE_DETAIL);
		updateSql.addInt(name, state);
		updateSql.setWhere("siteid=:siteid");
		updateSql.addWhereParamInt("siteid", siteId);
		return super.update(updateSql);
	}
	
}