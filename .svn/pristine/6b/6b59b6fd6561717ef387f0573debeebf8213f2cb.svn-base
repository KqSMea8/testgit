package com.hanweb.jmp.sys.dao.log;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;

import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.entity.log.Log;

/**
 * 操作日志数据库处理类
 * @author WangFei
 */
public class LogDAO extends BaseJdbcDAO<Integer, Log> {
	
	/**
	 * 删除某一站点日志
	 * @param siteId Integer
	 * @return Integer
	 */
	public Integer clean(Integer siteId) {
		String sql = "DELETE FROM " + Tables.LOG + " WHERE siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		return this.execute(query);
	}

	/**
	 * 清空表数据
	 * @return Integer
	 */
	public Integer clean() {
		String sql = "TRUNCATE TABLE " + Tables.LOG + "";
		Query query = createQuery(sql);
		return this.execute(query);
	}
	
	/**
	 * 查询操作日志表中每个功能模块操作记录条数
	 * @param moduleId     功能模块ID
	 * @param siteId     int
	 * @return       操作记录条数
	 */
	public int findCountByModuleId(int moduleId, int siteId) {
		int count = 0;
		String sql = " SELECT COUNT(1) FROM " + Tables.LOG
				   + " WHERE moduleid = :moduleId AND siteid = :siteId";
		Query query = createQuery(sql);
		query.addParameter("moduleId", moduleId);
		query.addParameter("siteId", siteId);
		count = this.queryForInteger(query); 
		return count;
	}
	
	/**
	 * 查询操作日志表中每个功能模块操作记录条数
	 * @param funcId     int
	 * @param siteId    int
	 * @return       操作记录条数
	 */
	public int findCountByFuncID(int funcId, int siteId) {
		int count = 0;
		String sql = " SELECT COUNT(1) FROM " + Tables.LOG
				   + " WHERE funcid = :funcId AND siteid = :siteId";
		Query query = createQuery(sql);
		query.addParameter("funcId", funcId);
		query.addParameter("siteId", siteId);
		count = this.queryForInteger(query); 
		return count;
	}
	
}