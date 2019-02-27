package com.hanweb.jmp.apps.dao.numbersense;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.NumberUtil;

import com.hanweb.jmp.apps.entity.numbersense.NumSenseCol;
import com.hanweb.jmp.constant.Tables;

public class NumSenseColDAO extends BaseJdbcDAO<Integer, NumSenseCol> {

	/**
	 * 判重处理控制器
	 * @param iid ID
	 * @param name 名称
	 * @param siteId 网站ID
	 * @param pid 父ID
	 * @return num
	 */
	public int findNumOfSameName(Integer iid, String name, Integer siteId, Integer pid) {
		int num = 0;
		String sql = " SELECT COUNT(iid) FROM " + Tables.NUMSENSECOL + " WHERE name=:name ";
		if (NumberUtil.getInt(iid) > 0) {
			sql += " AND iid NOT IN(:iid)";
		}
		if (NumberUtil.getInt(siteId) > 0) {
			sql += " AND siteid=:siteId";
		}
		if(NumberUtil.getInt(pid) >= 0) {
			sql +=" AND pid=:pid";
		}
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		query.addParameter("name", name);
		query.addParameter("siteId", siteId);
		query.addParameter("pid", pid);
		num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 根据父ID查询出最小的orderid
	 * @param pid 父ID
	 * @return num
	 */
	public int getMinOrderId(Integer pid){
		String sql = " SELECT MIN(orderid) FROM " + Tables.NUMSENSECOL+ " WHERE pid=:pid ";
		Query query = createQuery(sql);
		query.addParameter("pid", pid);
		return this.queryForInteger(query);
	}
	
	/**
	 * 通过ID获取分类实体
	 * @param iid
	 *            分类ID
	 * @return 分类实体
	 */
	public NumSenseCol findByIid(int iid) {
		String sql = getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid); 
		NumSenseCol col = this.queryForEntity(query); 
		return col;
	}
	
	/**
	 * 获得所有的分类
	 * @param siteId 网站ID
	 * @return list
	 */
	public List<NumSenseCol> findAllCol(int siteId){
		String sql = " SELECT a.iid, a.name,a.pid,a.type, CASE WHEN EXISTS(SELECT 1 FROM "
			       + Tables.NUMSENSECOL + " b WHERE b.pid = a.iid) THEN 1 ELSE 0 END isparent " + " FROM "
			       + Tables.NUMSENSECOL + " a  ORDER BY a.orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		return this.queryForEntities(query);
	}
	
	/**
	 * 查询子集
	 * @param iid id
	 * @param siteId 网站ID
	 * @return list
	 */
	public List<NumSenseCol> findChildByPid(int iid, int siteId) {
		String sql = " SELECT a.iid, a.name, a.pid,a.type, CASE WHEN EXISTS(SELECT 1 FROM " 
		           + Tables.NUMSENSECOL
		           + " b WHERE b.pid = a.iid) THEN 1 ELSE 0 END isparent"
			       + " FROM "+ Tables.NUMSENSECOL + " a  WHERE a.pid=:iid";
		if (NumberUtil.getInt(siteId) > 0) {
			sql += " AND a.siteid=:siteId";
		}
		sql += " ORDER BY a.orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		query.addParameter("siteId", siteId);
		return super.queryForEntities(query);
	}
	
	/**
	 * 根据父ID查询子分类
	 * @param iid iid
	 * @param siteId 网站ID
	 * @return list
	 */
	public List<NumSenseCol> findByPid(int iid, int siteId) {
		String sql = this.getEntitySql() + " WHERE pid=:iid AND siteid=:siteid";
		sql += " ORDER BY orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		query.addParameter("siteid", siteId);
		return super.queryForEntities(query);
	}
	
	/**
	 * 获取排序所需的分类
	 * @param colId 分类ID
	 * @param siteId 网站ID
	 * @return list
	 */
	public List<NumSenseCol> findOrderCol(int colId, int siteId){
		String sql = getEntitySql() + " WHERE siteid=:siteid AND pid=:colid ORDER BY orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("colid", colId);
		return this.queryForEntities(query);
	}
	
	/**
	 * 更新信息id的orderid
	 * @param orderid
	 *            排序id
	 * @param iid
	 *            信息id
	 * @return true/false
	 */
	public boolean updateOrderIdById(Integer orderid, int iid) {
		UpdateSql sql = new UpdateSql(Tables.NUMSENSECOL);
		sql.addInt("orderid", orderid);
		sql.setWhere("iid = :iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
	}
	
	/**
	 * 获得信息数
	 * @param ids
	 *            栏目ID串 如:1,2,3
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findCountSubCol(List<Integer> ids) {
		String sql = " SELECT COUNT(iid) FROM " + Tables.NUMSENSECOL
		           + " WHERE pid IN (:ids)";
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		int num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 获得信息数
	 * @param ids
	 *            栏目ID串 如:1,2,3
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findCountSubPhone(List<Integer> ids) {
		String sql = " SELECT COUNT(iid) FROM " + Tables.NUMSENSEPHONE
		           + " WHERE colid IN (:ids)";
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		int num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 根据ID串查询所有分类集合
	 * @param idsList id串
	 * @return list
	 */
	public List<NumSenseCol> findByIds(List<Integer> idsList) {
		String sql =this.getEntitySql() + " WHERE  iid IN (:idsList) ";	 
		Query query = createQuery(sql);
		query.addParameter("idsList", idsList);
		List<NumSenseCol> colList = this.queryForEntities(query);
		return colList;
	}
	
	/**
	 * 根据网站ID查询出所有的支持检索的分类
	 * @param siteId 网站ID
	 * @return 集合
	 */
	public List<NumSenseCol> findidsByIssearch(int siteId){
		String sql = this.getEntitySql() +  " WHERE issearch = 1";
		if(NumberUtil.getInt(siteId) > 0) {
			sql += " AND siteid=:siteid";
		}
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		return this.queryForEntities(query);
		
	}

	/**
	 * 根据网站id查询号码分类
	 */
	public List<NumSenseCol> findNumSenseCol(Integer siteid) {
		String sql = this.getEntitySql() + " WHERE siteid=:siteid ORDER BY orderid ASC, iid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", NumberUtil.getInt(siteid));
		return this.queryForEntities(query);
	}
	
}