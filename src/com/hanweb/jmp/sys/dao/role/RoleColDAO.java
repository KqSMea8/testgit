package com.hanweb.jmp.sys.dao.role;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.util.NumberUtil; 

import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.entity.role.RoleCol;

public class RoleColDAO extends BaseJdbcDAO<Integer, RoleCol>{

	/**
	 * 获得角色的栏目集合
	 * @param roleId Integer
	 *            角色ID
	 * @return 栏目实体集合
	 */
	public List<RoleCol> findByRoleId(Integer roleId) {
		String sql = " SELECT iid, roleid, colid,isedit,isaudit,iscolmanage FROM " + Tables.ROLECOL 
			       + " WHERE roleid = :roleId "
				   + " ORDER BY iid ";
		Query query = createQuery(sql);
		query.addParameter("roleId", roleId);
		List<RoleCol> roleColList = this.queryForEntities(query);
		return roleColList;
	}
	
	/**
	 * 获取用户的栏目权限集合
	 * @param userId 用户id
	 * @param groupId 机构id
	 * @return 栏目权限集合
	 */
	public List<RoleCol> findByUserId(Integer userId, Integer groupId) {
		String sql = " SELECT c.iid, c.roleid, c.colid, c.isedit, c.isaudit, c.iscolmanage " 
			       + " FROM " + com.hanweb.complat.constant.Tables.ROLERELATION
				   + " a, " + com.hanweb.complat.constant.Tables.ROLE + " b," + Tables.ROLECOL + " c "
				   + " WHERE a.roleid = b.iid " + " AND b.iid = c.roleid"
				   + " AND( a.userid = :userId ";
		if (NumberUtil.getInt(groupId) > 0) {
			sql += " OR a.groupid = :groupId";
		}
		sql += ") ";
		sql+=" ORDER BY c.colid ASC";
		Query query = createQuery(sql);
		query.addParameter("userId", userId);
		query.addParameter("groupId", groupId);
		List<RoleCol> colList = this.queryForEntities(query);
		return colList;
	}
	
	/**
	 * 通过角色ID串删除角色栏目对应关系
	 * @param roleIds
	 *            角色ID串 如:1,2,3
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean deleteByRoleIds(List<Integer> roleIds) {
		String sql = "DELETE FROM " + Tables.ROLECOL + " WHERE roleid IN (:roleIds)";
		Query query = createQuery(sql);
		query.addParameter("roleIds", roleIds);
		return this.delete(query);
	}
	
	/**
	 * 通过栏目ID串删除角色栏目对应关系
	 * @param colIds
	 *            栏目ID串 如:1,2,3
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean deleteByColIds(List<Integer> colIds) {
		String sql = "DELETE FROM " + Tables.ROLECOL + " WHERE colid IN (:colIds)";
		Query query = createQuery(sql);
		query.addParameter("colIds", colIds);
		return this.delete(query);
	}
	
}