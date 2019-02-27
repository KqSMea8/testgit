package com.hanweb.jmp.appstype.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.jmp.appstype.constant.Tables;
import com.hanweb.jmp.appstype.entity.LightAppType;

/**
 * 应用分类数据链路层
 * @author ZCC
 *
 */
@Repository
public class LightAppTypeDAO extends BaseJdbcDAO<Integer, LightAppType>{
	
	/**
	 * 根据name查找数量
	 * @param iid
	 * @param name
	 * @param siteId
	 * @return
	 */
	public int findNumByName(int iid, String name, int siteId) {
		String sql = "SELECT COUNT(1) FROM " + Tables.LIGHTAPPTYPE + " WHERE "
			+ " siteid=:siteid AND name=:name";
		if(iid>0){
			sql += " AND iid <> :iid";
		}
		Query query = createQuery(sql);
		query.addParameter("name", name);
		if(iid>0){
			query.addParameter("iid", iid);
		}
		query.addParameter("siteid", siteId);
		Integer num = this.queryForInteger(query);
		return NumberUtil.getInt(num);
	}
	
	/**
	 * 根据网站id查找最小排序id
	 * @param siteId
	 * @return
	 */
	public int findMinOrderIdBySiteId(int siteId){
		String sql = " SELECT MIN(orderid) FROM " + Tables.LIGHTAPPTYPE
			       + " WHERE siteid=:siteid ";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		return this.queryForInteger(query);
	}

	/**
	 * 通过机构ID获得下属分类集合（树）
	 * @param appTypeId
	 * @param siteId
	 * @return
	 */
	public List<LightAppType> findChildByIid(Integer pId, int siteId) {
		String sql = "SELECT a.iid, a.name, a.pid, CASE WHEN EXISTS(SELECT 1 FROM "
			+ Tables.LIGHTAPPTYPE + " b WHERE b.pid = a.iid) THEN 1 ELSE 0 END isparent " + " FROM "
			+ Tables.LIGHTAPPTYPE + " a ";
		if (NumberUtil.getInt(pId) > 0) {
			sql += "WHERE a.pid=:iid";
		} else {
			sql += "WHERE (a.pid IS NULL OR pid=0)";
		}
		sql += " AND siteid=:siteid ORDER BY a.orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("iid", pId);
		query.addParameter("siteid", siteId);
		return super.queryForEntities(query);
	}

	/**
	 * 通过iid查找分类实体
	 * @param iid
	 * @return
	 */
	public LightAppType findByIid(int iid) {
		String sql = this.getSql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		LightAppType type = this.queryForEntity(query);
		return type;
	}
	
	/**
	 * 通用查询机构的sql语句
	 * 
	 * @return
	 */
	private String getSql() {
		String sql = "SELECT a.iid, a.name, a.remark, a.pid, a.siteid, " + " (SELECT name FROM "
				+ Tables.LIGHTAPPTYPE + " WHERE iid = a.pid) pname, orderid FROM " + Tables.LIGHTAPPTYPE + " a";
		return sql;
	}

	/**
	 * 根据siteid获取应用分类
	 * @param int1
	 * @return
	 */
	public List<LightAppType> findBySiteId(int siteId) {
		String sql = getEntitySql() + " WHERE siteid=:siteid ";
        sql += " ORDER BY orderid DESC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		return this.queryForEntities(query);
	}

	/**
	 * 判断是否存在子分类
	 * @param ids
	 * @return
	 */
    public int findCountByTypeId(List<Integer> ids) {
        String sql = "SELECT COUNT(iid) FROM " + Tables.LIGHTAPPTYPE + " WHERE pid IN (:ids)";
        Query query = createQuery(sql);
        query.addParameter("ids", ids);
        int num = this.queryForInteger(query);
        return num;
    }

    /**
     * 按顺序查找子分类
     * @param siteId
     * @param pid
     * @return
     */
    public List<LightAppType> findOrderByPid(Integer siteId, String pid) {
        String sql = "SELECT iid, name, orderid, remark, pid FROM " + Tables.LIGHTAPPTYPE + " WHERE siteid=:siteid AND pid=:pid ORDER BY orderid ASC";
        Query query = createQuery(sql);
        query.addParameter("siteid", siteId);
        query.addParameter("pid", pid);
        return this.queryForEntities(query);
    }

    /**
     * 更新排序id
     * @param orderid
     * @param iid
     * @return
     */
    public boolean updateOrderIdById(int orderid , int iid) {
        UpdateSql sql = new UpdateSql(Tables.LIGHTAPPTYPE);
        sql.addInt("orderid", orderid);
        sql.setWhere("iid=:iid");
        sql.addWhereParamInt("iid", iid);
        return this.update(sql);
    }
    
    public boolean findCountByAppTypeName(String name, int siteId){
    	String sql = "SELECT COUNT(*) FROM " + Tables.LIGHTAPPTYPE + " WHERE name=:name AND siteid=:siteid";    	
    	Query query = createQuery(sql);
        query.addParameter("name", name);   
        query.addParameter("siteid", siteId);   
    	return this.queryForInteger(query)>0;
    }
    
    public int findIidByAppTypeName(String name, int siteId){
    	String sql = "SELECT iid FROM " + Tables.LIGHTAPPTYPE + " WHERE name=:name AND siteid=:siteid";
    	Query query = createQuery(sql);
        query.addParameter("name", name); 
        query.addParameter("siteid", siteId);   
        return this.queryForInteger(query);
    }
    
    /**
     * 通过iid查找pid
     * @param id
     * @return
     */
    public Integer findPidByIid(Integer id){
    	String sql = "SELECT pid FROM "+ Tables.LIGHTAPPTYPE + " WHERE iid = :iid";
    	Query query = createQuery(sql);
    	query.addParameter("iid", id);
    	return this.queryForInteger(query);
    }
    
}