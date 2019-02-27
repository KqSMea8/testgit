package com.hanweb.jmp.interfacesmanage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.interfacesmanage.entity.Interfaces;

@Repository
public class InterfaceDao extends BaseJdbcDAO<Integer, Interfaces> {

	/**
	 * 通过接口信息ID、接口名获得同名接口的个数
	 * 
	 * @param iid
	 * @param name
	 * @return
	 */
	public int findNumOfSameName(Integer iid, String name) {
		int num = 0;

		String sql = " SELECT COUNT(iid) FROM " + Tables.INTERFACES
				+ " WHERE name=:name";
		if (NumberUtil.getInt(iid) > 0) {
			sql += " AND iid NOT IN(:iid)";
		}

		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		query.addParameter("name", name);

		num = this.queryForInteger(query);
		return num;
	}

	/**
	 * 更新接口实体
	 * 
	 * @param inter
	 * @return
	 */
	public boolean updateInterface(Interfaces inter) {
		return super.update(inter);
	}

	/**
	 * 通过接口信息ID获取接口信息
	 * 
	 * @param iid
	 * @return
	 */
	public Interfaces findByIid(Integer iid) {
		String sql = "SELECT iid, name, domain, type, typeid FROM "
				+ Tables.INTERFACES + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		Interfaces inter = this.queryForEntity(query);
		return inter;
	}

	/**
	 * 查找接口实体集合
	 * 
	 * @param idList
	 * @return
	 */
	public List<Interfaces> findByIds(List<Integer> idList) {
		String sql = this.getEntitySql() + Tables.INTERFACES
				+ " WHERE iid IN (:idList)";
		Query query = createQuery(sql);
		query.addParameter("idList", idList);
		return super.queryForEntities(query);
	}

	

	
}
