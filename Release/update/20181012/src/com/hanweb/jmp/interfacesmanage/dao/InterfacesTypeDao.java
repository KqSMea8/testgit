package com.hanweb.jmp.interfacesmanage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.interfacesmanage.entity.InterfacesType;

/**
 * 管理员对接口类型增删改
 * 
 * @author Administrator
 * 
 */
@Repository
public class InterfacesTypeDao extends BaseJdbcDAO<Integer, InterfacesType> {

	/**
	 * 根据接口类型id 获取接口信息
	 * 
	 * @param iid
	 * @return
	 */
	public InterfacesType findByIid(Integer iid) {

		String sql = "SELECT iid,name,pid FROM " + Tables.INTERFACESTYPE
				+ " WHERE  iid=:iid";

		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		InterfacesType interfaceType = this.queryForEntity(query);
		return interfaceType;
	}

	/**
	 * 根据接口类型id和接口类型名称获取接口类型的个数
	 * 
	 * @param iid
	 * @param name
	 * @return
	 */
	public int findNumBySameName(Integer iid, String name, Integer pid) {
		String sql = "SELECT COUNT(iid) FROM " + Tables.INTERFACESTYPE
				+ " WHERE `name`=:name AND pid=:pid";
		Query query = createQuery(sql);
		query.addParameter("name", name);
		query.addParameter("pid", pid);
		if (NumberUtil.getInt(iid) > 0) {
			sql += " AND iid NOT IN(:iid)";

		}
		query.setSql(sql);
		query.addParameter("iid", iid);
		query.addParameter("name", name);
		return this.queryForInteger(query);
	}

	/**
	 * 查询所有接口类型集合
	 * 
	 * @return
	 */
	public List<InterfacesType> findAllInterfaceType() {
		String sql = "SELECT iid ,name,pid FROM " + Tables.INTERFACESTYPE;
		Query query = createQuery(sql);
		return super.queryForEntities(query);
	}

	/**
	 * 查询typeid个数
	 * 
	 * @param iid
	 * @return
	 */
	public int findInterfaceTypeId(List<Integer> typeid) {

		String sql = "SELECT COUNT(iid) FROM " + Tables.INTERFACES
				+ " WHERE typeid = :typeid";
		Query query = createQuery(sql);
		query.addParameter("typeid", typeid);
		return this.queryForInteger(query);
	}

	/**
	 * 查询所有类型名称
	 */
	public List<InterfacesType> findTypeByName() {
		String sql = "SELECT name FROM " + Tables.INTERFACESTYPE;
		Query query = createQuery(sql);
		return this.queryForEntities(query);
	}

	/**
	 * 查询类型父id 判断删除
	 */
	public int findTypeByPid(List<Integer> typeid) {
		String sql = "SELECT COUNT(pid) FROM " + Tables.INTERFACESTYPE
				+ " WHERE pid = :typeid";
		Query query = createQuery(sql);
		query.addParameter("typeid", typeid);
		return this.queryForInteger(query);
	}

	/**
	 * 根据类型id获得下属类型树
	 */
	public List<InterfacesType> findChildTypeByIid(Integer iid) {
		String sql = "SELECT a.iid,a.name,a.pid,"
				+ "CASE WHEN EXISTS(SELECT 1 FROM " + Tables.INTERFACESTYPE
				+ " b WHERE b.pid=a.iid) THEN 1 ELSE 0 END isparent " + "FROM "
				+ Tables.INTERFACESTYPE + " a WHERE a.pid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		return super.queryForEntities(query);
	}

}
