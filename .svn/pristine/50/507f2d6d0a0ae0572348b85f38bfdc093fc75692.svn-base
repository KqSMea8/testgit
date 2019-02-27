package com.hanweb.jmp.global.dao;

import java.util.List;
import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.global.entity.OutSideUserSubscribeColRel;

public class OutSideUserSubscribeColRelDAO extends BaseJdbcDAO<Integer, OutSideUserSubscribeColRel> {

	/**
	 * 删除外网用户订阅的指定栏目
	 * @param colId 栏目id
	 * @param siteId 网站id
	 * @param loginName 外网用户登录名
	 * @return boolean
	 */
	public boolean deleteByColIdAndLoginName(int colId, int siteId, String loginName){
		String sql = " DELETE FROM " + Tables.OUTSIDEUSERBCR 
			       + " WHERE colid=:colid AND siteid=:siteid AND loginname=:loginname";
		Query query = createQuery(sql);
		query.addParameter("colid", colId);
		query.addParameter("siteid", siteId);
		query.addParameter("loginname", loginName);
		this.execute(query);
		return true;
	}
	
	/**
	 * 删除外网用户订阅的指定栏目
	 * @param colId 栏目id
	 * @param siteId 网站id
	 * @param loginName 外网用户登录名
	 * @return boolean
	 */
	public boolean deleteByColIdAndLoginName(int colId, int siteId){
		String sql = " DELETE FROM " + Tables.OUTSIDEUSERBCR 
			       + " WHERE colid=:colid AND siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("colid", colId);
		query.addParameter("siteid", siteId);
		this.execute(query);
		return true;
	}
	
	/**
	 * 根据用户名查找用户订阅的栏目
	 * @param loginName 用户名
	 * @param siteId 网站id
	 * @return List<OutSideUserBookColRel>
	 */
	public List<OutSideUserSubscribeColRel> findByloginName(String loginName, int siteId){
		String sql = getEntitySql() + " WHERE siteid=:siteid AND loginname=:loginname";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("loginname", loginName);
		return this.queryForEntities(query);
	}
	
}