package com.hanweb.jmp.sys.dao.log;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;

import com.hanweb.jmp.sys.entity.log.OfflineZip;

public class OfflineZipDAO extends BaseJdbcDAO<Integer, OfflineZip>{
	
	/**
	 * 根据栏目id和网站id获取离线线程扫描记录
	 * @param colId 栏目id
	 * @param siteId 网站id 
	 * @return OfflineZip
	 */
	public OfflineZip findOfflineZipByColid(Integer colId, Integer siteId) {
		String sql = this.getEntitySql() + " WHERE colid=:colid AND siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("colid", colId);
		query.addParameter("siteid", siteId);
		OfflineZip offlineZip = this.queryForEntity(query);
		return offlineZip;
	}
	
}
