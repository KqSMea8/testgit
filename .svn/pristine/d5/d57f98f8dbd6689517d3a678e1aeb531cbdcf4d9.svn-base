package com.hanweb.jmp.apps.dao.numbersense;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.NumberUtil;

import com.hanweb.jmp.apps.entity.numbersense.NumSensePhone;
import com.hanweb.jmp.constant.Tables;

public class NumSensePhoneDAO extends BaseJdbcDAO<Integer, NumSensePhone> {

	/**
	 * 判重处理控制器
	 * @param iid id
	 * @param name 名称
	 * @param siteId 网站ID
	 * @param colId 分类ID
	 * @return num
	 */
	public int findNumOfSameName(Integer iid, String name, Integer siteId, Integer colId) {
		int num = 0;
		String sql = " SELECT COUNT(iid) FROM " + Tables.NUMSENSEPHONE + " WHERE name=:name ";
		if (NumberUtil.getInt(iid) > 0) {
			sql += " AND iid NOT IN(:iid)";
		}
		if (NumberUtil.getInt(siteId) > 0) {
			sql += " AND siteid=:siteId";
		}
		if (NumberUtil.getInt(colId) > 0) {
			sql += " AND colid=:colId";
		}
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		query.addParameter("name", name);
		query.addParameter("siteId", siteId);
		query.addParameter("colId", colId);
		num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 获得orderid最大值
	 * @return int
	 */
	public int getMaxOrderId(){
		String sql = " SELECT MAX(orderid) FROM " + Tables.NUMSENSEPHONE;
		Query query = createQuery(sql);
		return this.queryForInteger(query);
	}
	
	/**
	 * 通过ID获取号码实体
	 * @param iid
	 *            号码ID
	 * @return  号码实体
	 */
	public NumSensePhone findByIid(int iid) {
		String sql = getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid); 
		NumSensePhone phone = this.queryForEntity(query); 
		return phone;
	}
	
	/**
	 * 获得所有的分类
	 * @param siteId 网站ID
	 * @return list
	 */
	public List<NumSensePhone> findAllCol(int siteId){
		String sql = getEntitySql() + " WHERE siteid=:siteid ORDER BY orderid DESC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
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
		UpdateSql sql = new UpdateSql(Tables.NUMSENSEPHONE);
		sql.addInt("orderid", orderid);
		sql.setWhere("iid = :iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
	}
	
	/**
	 * 根据分类ID查询最小的orderid
	 * @param colId 分类ID
	 * @return num
	 */
	public int getMinOrderId(int colId){
		String sql = " SELECT MIN(orderid) FROM " + Tables.NUMSENSEPHONE + " WHERE colid=:colid";
		Query query = createQuery(sql);
		query.addParameter("colid", colId);
		int minOrderId=NumberUtil.getInt(this.queryForInteger(query));
		return minOrderId;
	}
	
	/**
	 * 根据colid获得商品list
	 * @param colId 分类Id
	 * @return list
	 */
	public List<NumSensePhone> findByColId(int colId){
		String sql = getEntitySql() + " WHERE colid=:colid ORDER BY orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("colid", colId);
		return this.queryForEntities(query);
	}
	
	/**
	 * 分页查询数据
	 * @param siteid 网站ID
	 * @param colId 分类ID
	 * @param pageSize 页码
	 * @param pageNum 页数
	 * @return list
	 */
	public List<NumSensePhone> findByColIdLimit(int siteid, int colId, int pageSize, int pageNum){
		String sql = getEntitySql() + " WHERE siteid=:siteid";
		if(NumberUtil.getInt(colId) > 0){
			sql += " AND colid=:colid";
		}
		sql += " ORDER BY orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("colid", colId);
		query.addParameter("siteid", siteid);
		query.setPageSize(pageSize);
		query.setPageNo(pageNum);
		return this.queryForEntities(query);
	}
	
	/**
	 * 根据关键字搜索号码实体集
	 * @param siteid 网站ID
	 * @param pageSize 页码
	 * @param pageNum 页数
	 * @param keyWord 关键字
	 * @return 集合
	 */
	public List<NumSensePhone> findByKeyWord(int siteid, int pageSize, int pageNum, String keyWord, List<Integer> ids){
		String sql = getEntitySql() + " WHERE siteid=:siteid AND name LIKE :name AND colid IN (:idsList)";
		sql += " ORDER BY orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("name", keyWord, LikeType.LR);
		query.addParameter("siteid", siteid);
		query.addParameter("idsList", ids);
		query.setPageSize(pageSize);
		query.setPageNo(pageNum);
		return this.queryForEntities(query);
	}
	
	/**
	 * 根据ID串查询出所有号码实体集合
	 * @param idsList id串
	 * @return list
	 */
	public List<NumSensePhone> findByIds(List<Integer> idsList) {
		String sql = this.getEntitySql() + " WHERE  iid IN (:idsList) ";	 
		Query query = createQuery(sql);
		query.addParameter("idsList", idsList);
		List<NumSensePhone> phoneList = this.queryForEntities(query);
		return phoneList;
	}
	
}