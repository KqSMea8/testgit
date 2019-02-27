package com.hanweb.jmp.sys.dao.collect;


import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.jmp.sys.entity.collect.Collect;

/**
 * 收藏管理DAO
 * @author hzz
 *
 */
public class CollectDAO extends BaseJdbcDAO<Integer, Collect>{
	
	/**
	 * 查找收藏
	 * @param userId 用户id
	 * @param type   类型1应用 2信息
	 * @param collectId 收藏id
	 * @return
	 */
	public Collect findCollect(String userId, Integer type, Integer collectId, Integer siteId){
		String sql = this.getEntitySql() + " WHERE userid =:userid AND type =:type AND collectid =:collectid AND siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("userid", userId);
		query.addParameter("type", type);
		query.addParameter("collectid", collectId);
		query.addParameter("siteid", siteId);
		return this.queryForEntity(query);
	}

	/**
	 * 通过用户和类型查找收藏实体
	 * @param userId
	 * @param type
	 * @param siteId
	 * @return
	 */
	public List<Collect> findCollectByUseridAndType(String userId, Integer type, Integer siteId){
		String sql = this.getEntitySql() + " WHERE userid =:userid AND type =:type AND siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("userid", userId);
		query.addParameter("type", type);
		query.addParameter("siteid", siteId);
		return this.queryForEntities(query);
	}
	
	
	/**
	 * 查找收藏
	 * @param userId    用户id
	 * @param type      类型1应用 2信息
	 * @param siteId    站点
	 * @param collectId 收藏id
	 * @return
	 */
	public List<Collect> findRealCollect(String userId, Integer type, Integer siteId, String collectId){
		String sql = this.getEntitySql() + " WHERE userid =:userid AND type =:type AND siteid=:siteid AND collectid=:collectid";
		Query query = createQuery(sql);
		query.addParameter("userid", userId);
		query.addParameter("type", type);
		query.addParameter("siteid", siteId);
		query.addParameter("collectid", collectId);
		return this.queryForEntities(query);
	}

}