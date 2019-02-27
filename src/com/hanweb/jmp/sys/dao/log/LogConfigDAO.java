package com.hanweb.jmp.sys.dao.log;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;

import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.entity.log.LogConfig;

public class LogConfigDAO extends BaseJdbcDAO<Integer, LogConfig> {

	/**
	 * 检查日志写入权限
	 * @param moduleid Integer
	 * @param funcid Integer
	 * @return Integer
	 */
	public Integer checkIsLog(Integer moduleid, Integer funcid) {
		String sql = " SELECT islog FROM " + Tables.LOGCONFIG + " WHERE moduleid=:moduleid "
		           + " AND funcid=:funcid";
		Query query = createQuery(sql);
		query.addParameter("moduleid", moduleid);
		query.addParameter("funcid", funcid);
		return this.queryForInteger(query);
	}

	/**
	 * 查询模块集合
	 * @return List<LogConfig>
	 */
	public List<LogConfig> findModuleName() {
		String sql = getEntitySql() + " GROUP BY moduleid";
		Query query = createQuery(sql);
		return this.queryForEntities(query);
	}

	/**
	 * 查询功能集合
	 * @param  moduleId Integer
	 * @return List<LogConfig>
	 */
	public List<LogConfig> findFuncName(Integer moduleId) {
		String sql = getEntitySql() + " WHERE moduleid=:moduleid";
		Query query = createQuery(sql);
		query.addParameter("moduleid", moduleId);
		return this.queryForEntities(query);
	}

	/**
	 * 修改日志权限
	 * @param moduleId Integer
	 * @param funcId Integer
	 * @return  boolean
	 */
	public boolean updateIsLog(Integer moduleId, Integer funcId) {
		UpdateSql updateSql = new UpdateSql(Tables.LOGCONFIG);
		updateSql.addInt("islog", 1);
		updateSql.setWhere("moduleid=:moduleid AND funcid=:funcid");
		updateSql.addWhereParamInt("moduleid", moduleId);
		updateSql.addWhereParamInt("funcid", funcId);
		return this.update(updateSql);
	}

	/**
	 * 重置日志权限
	 * @return boolean
	 */
	public boolean updateIsLog() {
		UpdateSql updateSql = new UpdateSql(Tables.LOGCONFIG);
		updateSql.addInt("islog", 0);
		return this.update(updateSql);
	}
	
}