package com.hanweb.jmp.apps.dao.read;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.NumberUtil;

import com.hanweb.jmp.apps.entity.read.Read;
import com.hanweb.jmp.constant.Tables;

public class ReadDAO extends BaseJdbcDAO<Integer, Read> {

	/**
	 *  判重处理
	 * @param iid ID
	 * @param name 名称
	 * @param siteId 网站ID
	 * @param type 类型
	 * @param pid 父ID
	 * @return num
	 */
	public int findNumOfSameName(Integer iid, String name, Integer siteId, Integer type, Integer pid) {
		int num = 0;
		String sql = " SELECT COUNT(iid) FROM " + Tables.EBOOK + " WHERE name=:name";
		if (NumberUtil.getInt(iid) > 0) {
			sql += " AND iid NOT IN(:iid)";
		}
		if (NumberUtil.getInt(siteId) > 0) {
			sql += " AND siteid=:siteId";
		}
		if (NumberUtil.getInt(type) > 0) {
			sql += " AND type=:type";
		}
		if (NumberUtil.getInt(pid) >= 0) {
			sql += " AND pid=:pid";
		}
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		query.addParameter("name", name);
		query.addParameter("siteId", siteId);
		query.addParameter("type", type);
		query.addParameter("pid", pid);
		num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 查找最小的orderid
	 * @param pid 父ID
	 * @return num
	 */
	public int getMinOrderId(int pid) {
		String sql = " SELECT MIN(orderid) FROM " + Tables.EBOOK + " WHERE pid=:pid";
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
	public Read findByIid(int iid) {
		String sql = getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid); 
		Read read = this.queryForEntity(query); 
		return read;
	}
	
	/**
	 * 根据网站ID和id查找实体
	 * @param iid id 
	 * @param siteid 网站ID 
	 * @return 实体
	 */
	public Read findByIidAndSiteId(int iid, int siteid) {
		String sql = getEntitySql() + " WHERE iid=:iid AND siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid); 
		query.addParameter("siteid", siteid);
		Read read = this.queryForEntity(query); 
		return read;
	}
	
	/**
	 * 查询所有的分类
	 * @param iid ID
	 * @param siteId 网站ID
	 * @return list
	 */
	public List<Read> findAllCol(int iid, int siteId){
		String sql = " SELECT a.iid, a.name,a.pid,a.type, CASE WHEN EXISTS(SELECT 1 FROM "
			       + Tables.EBOOK + " b WHERE b.pid = a.iid AND b.type=0) THEN 1 ELSE 0 END isparent "
			       + " FROM " + Tables.EBOOK + " a WHERE a.pid=:iid AND a.type=0";
		if (NumberUtil.getInt(siteId) > 0) {
			sql += " AND a.siteid=:siteid";
		}
		sql += " ORDER BY a.orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("iid", iid);
		return this.queryForEntities(query);
	}
	
	/**
	 * 查询所有子集
	 * @param pid 父ID
	 * @param siteid 网站ID
	 * @return list
	 */
	public List<Read> findByPid(int pid, int siteid) {
		String sql = " SELECT a.iid, a.name, a.picpath, a.orderid, a.type, a.pid, a.flag, a.changetime,"
			       + "(SELECT name FROM " + Tables.EBOOK + "  WHERE iid = a.pid) pname"
		 	       + " FROM "+ Tables.EBOOK + " a WHERE a.siteid=:siteid";
		if(pid!=0){
			sql += " AND a.pid=:pid";
		}
		sql += " ORDER BY a.orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteid);
		query.addParameter("pid", pid);
		return this.queryForEntities(query);
	}
	
	/**
	 * 查询出需要排序的实体
	 * @param iid ID
	 * @param siteId 网站ID
	 * @return list
	 */
	public List<Read> findOrder(int iid, int siteId){
		String sql = " SELECT a.iid, a.name,a.pid,a.type,a.orderid, CASE WHEN EXISTS(SELECT 1 FROM "
			       + Tables.EBOOK + " b WHERE b.pid = a.iid) THEN 1 ELSE 0 END isparent " + " FROM "
			       + Tables.EBOOK + " a WHERE a.pid=:iid AND a.siteid=:siteid ORDER BY a.orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("iid", iid);
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
		UpdateSql sql = new UpdateSql(Tables.EBOOK);
		sql.addInt("orderid", orderid);
		sql.setWhere("iid = :iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
	}
	
	/**
	 * 查找最大的flag
	 * @param siteid 网站ID
	 * @return 最大数
	 */
	public int findMaxFlag(int siteid) {
		String sql = " SELECT MAX(flag) FROM " + Tables.EBOOK + " WHERE siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteid);
		int flag = NumberUtil.getInt(this.queryForInteger(query));
		flag = flag+NumberUtil.getInt(Math.random()*100);
		return flag;
 	}
	
	/**
	 * 变动flag标识位
	 * @param flag 标识位
	 * @param siteid 网站ID
	 * @return true/false
	 */
	public boolean updateFlag(Integer flag, int siteid) {
		UpdateSql sql = new UpdateSql(Tables.EBOOK);
		sql.addInt("flag", NumberUtil.getInt(flag));
		sql.setWhere("siteid=:siteid");
		sql.addWhereParamInt("siteid", siteid);
		return this.update(sql);
	}
	
	/**
	 * 获得信息数 
	 * @param ids
	 *            分类ID串 如:1,2,3
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findCountSubInfo(List<Integer> ids) {
		String sql = "SELECT COUNT(iid) FROM " + Tables.EBOOK + " WHERE pid IN (:ids)";
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		int num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 根据ID串查询电子书集合
	 * @param idsList id串
	 * @return list
	 */
	public List<Read> findByIds(List<Integer> idsList) {
		String sql =this.getEntitySql() + " WHERE iid IN (:idsList) ";	 
		Query query = createQuery(sql);
		query.addParameter("idsList", idsList);
		List<Read> readList = this.queryForEntities(query);
		return readList;
	}
	
}