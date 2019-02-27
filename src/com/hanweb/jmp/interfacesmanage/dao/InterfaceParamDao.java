package com.hanweb.jmp.interfacesmanage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.interfacesmanage.entity.InterfacesParam;

@Repository
public class InterfaceParamDao extends BaseJdbcDAO<Integer, InterfacesParam> {

	/**
	 * 根据接口id查找接口参数
	 * 
	 * @param iid
	 * @return
	 */
	public List<InterfacesParam> findParamsByIid(Integer iid, Integer isRequired) {
		String sql = this.getEntitySql() + Tables.INTERFACESPARAM
				+ " WHERE interfaceid = :iid ";
		if (isRequired != null) {
			sql = sql + " AND isrequired = :isrequired ";
		}
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		if (isRequired != null) {
			query.addParameter("isrequired", isRequired);
		}
		return super.queryForEntities(query);
	}

	/**
	 * 根据接口ids查找接口参数
	 * 
	 * @param ids
	 * @return
	 */
	public List<InterfacesParam> findParamsByIids(List<Integer> idsLsit) {
		String sql = this.getEntitySql() + Tables.INTERFACESPARAM
				+ " WHERE interfaceid IN (:idsLsit) ";
		Query query = createQuery(sql);
		query.addParameter("idsLsit", idsLsit);
		return super.queryForEntities(query);
	}

	/**
	 * 根据接口id查询参数
	 * @param interfaceid
	 * @return
	 */
	public List<InterfacesParam> findParamByName(Integer interfaceid) {

		String sql = "SELECT p.iid,p.name,p.type,p.isrequired FROM "
				+ Tables.INTERFACESPARAM + " p WHERE interfaceid=:interfaceid";
		Query query=createQuery(sql);
		query.addParameter("interfaceid", interfaceid);
		return super.queryForEntities(query);
	}

}
