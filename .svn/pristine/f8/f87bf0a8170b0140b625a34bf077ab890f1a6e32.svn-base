package com.hanweb.jmp.sys.dao.role;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.util.NumberUtil;

import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.entity.role.RolePushType;

public class RolePushTypeDAO extends BaseJdbcDAO<Integer, RolePushType>{

	/**
	 * 获得角色的栏目集合
	 * @param roleId Integer
	 *            角色ID
	 * @return 栏目实体集合
	 */
	public List<RolePushType> findByRoleId(Integer roleId) {
		String sql = " SELECT iid, roleid, pushtypeid FROM " + Tables.ROLEPUSHTYPE 
		           + " WHERE roleid = :roleId "
				   + " ORDER BY iid ";
		Query query = createQuery(sql);
		query.addParameter("roleId", roleId);
		List<RolePushType> rolePushList = this.queryForEntities(query);
		return rolePushList;
	}
	
	/**
	 * 获取用户的推送分类集合
	 * @param userId 用户id
	 * @param groupId 机构id
	 * @return 推送分类集合
	 */
	public List<RolePushType> findByUserId(Integer userId, Integer groupId) {
		String sql = " SELECT c.iid, c.roleid, c.pushtypeid " 
			       + " FROM " + com.hanweb.complat.constant.Tables.ROLERELATION
			       + " a, " + com.hanweb.complat.constant.Tables.ROLE + " b," + Tables.ROLEPUSHTYPE + " c "
			       + " WHERE a.roleid = b.iid " + " AND b.iid = c.roleid"
			       + " AND( a.userid = :userId ";
		if (NumberUtil.getInt(groupId) > 0) {
			sql += " OR a.groupid = :groupId";
		}
		sql += ") ";
		Query query = createQuery(sql);
		query.addParameter("userId", userId);
		query.addParameter("groupId", groupId);
		List<RolePushType> pushList = this.queryForEntities(query);
		return pushList;
	}
	
	/**
	 * 通过角色ID串删除推送分类栏目对应关系
	 * @param roleIds
	 *            角色ID串 如:1,2,3
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean deleteByRoleIds(List<Integer> roleIds) {
		String sql = "DELETE FROM " + Tables.ROLEPUSHTYPE + " WHERE roleid IN (:roleIds)";
		Query query = createQuery(sql);
		query.addParameter("roleIds", roleIds);
		return this.delete(query);
	}
	
	/**
	 * 通过栏目ID串删除角色推送分类对应关系
	 * @param colIds
	 *            栏目ID串 如:1,2,3
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean deleteByColIds(List<Integer> colIds) {
		String sql = "DELETE FROM " + Tables.ROLEPUSHTYPE + " WHERE colid IN (:colIds)";
		Query query = createQuery(sql);
		query.addParameter("colIds", colIds);
		return this.delete(query);
	}
	
}