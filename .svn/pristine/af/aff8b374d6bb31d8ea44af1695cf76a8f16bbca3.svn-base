package com.hanweb.jmp.newspush.peoplelist.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.newspush.peoplelist.entity.PeoplelistRelation;

/**
 * 角色成员关系
 * 
 * @author ZhangC
 * 
 */
public class PeoplelistRelationDAO extends BaseJdbcDAO<Integer, PeoplelistRelation> {

	/**
	 * 取得机构关联的角色id集合
	 * 
	 * @param groupId
	 *            机构ID
	 * @return
	 */
	public List<String> findRoleIdsByGroupId(Integer groupId) {
		List<String> roleIdList = new ArrayList<String>();
		String sql = "SELECT roleid FROM " + com.hanweb.complat.constant.Tables.ROLERELATION + " WHERE groupid = :groupId";

		Query query = createQuery(sql);

		query.addParameter("groupId", groupId);

		List<Map<String, Object>> resultList = this.queryForList(query);

		for (Map<String, Object> result : resultList) {
			roleIdList.add(NumberUtil.getInt(result.get("roleid")) + "");
		}
		return roleIdList;
	}

	/**
	 * 根据机构ID串，删除其对应的角色
	 * 
	 * @param groupIdsList
	 *            机构ID 集合
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean deleteByGroupIds(List<Integer> groupIdsList) {
		return deleteGroups(null, groupIdsList);
	}

	/**
	 * 删除机构对应的角色成员关系
	 * 
	 * @param roleId
	 *            角色ID
	 * @param groupIdsList
	 *            机构ID 集合
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean deleteGroups(Integer peoplelistid, List<Integer> groupIdsList) {
		String sql = "DELETE FROM " + Tables.PEOPLELISTRELATION + " WHERE groupid IN(:groupIds)";
		if (NumberUtil.getInt(peoplelistid) > 0) {
			sql += " AND peoplelistid = :peoplelistid";
		}
		Query query = createQuery(sql);
		query.addParameter("peoplelistid", peoplelistid);
		query.addParameter("groupIds", groupIdsList);

		return this.delete(query);
	}

	/**
	 * 删除用户对应的角色成员关系
	 * 
	 * @param roleId
	 *            角色ID
	 * @param userIdsList
	 *            用户ID 集合
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean deleteUsers(Integer peoplelistId, List<Integer> userIdsList) {
		String sql = "DELETE FROM " + Tables.PEOPLELISTRELATION + " WHERE userid IN(:userIdsList)";
		if (NumberUtil.getInt(peoplelistId) > 0) {
			sql += " AND peoplelistid = :peoplelistid";
		}
		Query query = createQuery(sql);
		query.addParameter("peoplelistid", peoplelistId);
		query.addParameter("userIdsList", userIdsList);

		return this.delete(query);
	}

	/**
	 * 清除角色相关的机构用户关系
	 * 
	 * @param roleIdList
	 *            角色ID集
	 * @return
	 */
	public boolean deleteByroleId(List<Integer> peoplelistIdList) {
		String sql = "DELETE FROM " + Tables.PEOPLELISTRELATION + " WHERE peoplelistid IN(:peoplelistIdList)";
		Query query = createQuery(sql);
		query.addParameter("peoplelistIdList", peoplelistIdList);

		return this.delete(query);
	}

	/**
	 * 获得机构型成员的数量
	 * 
	 * @param roleId
	 * @param groupId
	 * @return
	 */
	public int findGroupMemberSize(Integer peoplelistId, Integer groupId) {
		String sql = "SELECT COUNT(*) FROM " + Tables.PEOPLELISTRELATION
				+ " WHERE peoplelistid = :peoplelistid AND groupid = :groupId";

		Query query = createQuery(sql);

		query.addParameter("peoplelistid", peoplelistId);
		query.addParameter("groupId", groupId);

		Integer size = this.queryForInteger(query);
		return size;
	}

	/**
	 * 获得用户型成员的数量
	 * 
	 * @param roleId
	 * @param userId
	 * @return
	 */
	public int findUserMemberSize(Integer peoplelistId, Integer userId) {
		String sql = "SELECT COUNT(*) FROM " + Tables.PEOPLELISTRELATION
				+ " WHERE peoplelistid = :peoplelistid AND userid = :userId";

		Query query = createQuery(sql);

		query.addParameter("peoplelistid", peoplelistId);
		query.addParameter("userId", userId);

		Integer size = this.queryForInteger(query);
		return size;
	}

	/**
	 * 根据群组id查询关系实体
	 * @param peoplelistid
	 * @return
	 */
    public List<PeoplelistRelation> findBypeoplelistid(Integer peoplelistid) {
        String sql = this.getEntitySql() + " WHERE peoplelistid =:peoplelistid";
        Query query = createQuery(sql);
        query.addParameter("peoplelistid", peoplelistid);
        return this.queryForEntities(query);
    }
}
