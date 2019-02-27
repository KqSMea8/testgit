package com.hanweb.jmp.cms.dao.sign;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.NumberUtil;

import com.hanweb.jmp.cms.entity.sign.Sign;
import com.hanweb.jmp.constant.Tables;

public class SignDAO extends BaseJdbcDAO<Integer, Sign> {

	/**
	 * 根据主键id查找实体
	 * @param iid
	 * @return
	 */
	public Sign findByIid(int iid){
		String sql = getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		return this.queryForEntity(query);
	}
	
	/**
	 * 根据网站id和模块id来查找角标
	 * @param mid 模块id
	 * @param siteId 网站id
	 * @param colId int
	 * @return List<Sign>
	 */
	public List<Sign> findByMid(int mid, int siteId, int colId){
		String sql = getEntitySql() + " WHERE siteid=:siteid AND mid=:mid ";
		if(colId > 0){
			sql += " AND colid=:colid";
		}
		sql += " ORDER BY orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("mid", mid);
		query.addParameter("colid", colId);
		return this.queryForEntities(query);
	}
	
	/**
	 * 根据网站id和模块id查找orderid最小的
	 * @param mid 模块id
	 * @param siteId 网站id
	 * @param colId int
	 * @return int
	 */
	public int findMinOrderIdBySiteIdAndMid(int mid, int siteId, int colId){
		String sql = " SELECT MIN(orderid) FROM " + Tables.DIMENSION
			       + " WHERE siteid=:siteid AND mid=:mid";
		if(colId > 0){
			sql += " AND colid=:colid";
		}
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("mid", mid);
		query.addParameter("colid", colId);
		return this.queryForInteger(query);
	}
	
	/**
	 * 通过角标名称查找角标数
	 * @param iid int
	 *            网站id
	 * @param dname
	 *            网站名称
	 * @param mid int
	 * @param siteId int          
	 * @return int
	 */
	public int findNumByName(int iid, String dname, int mid, int siteId) {
		String sql = " SELECT COUNT(1) FROM " + Tables.DIMENSION + " WHERE mid=:mid AND"
			       + " siteid=:siteid AND dname=:dname AND iid <> :iid";
		Query query = createQuery(sql);
		query.addParameter("dname", dname);
		query.addParameter("iid", iid);
		query.addParameter("siteid", siteId);
		query.addParameter("mid", mid);
		Integer num = this.queryForInteger(query);
		return NumberUtil.getInt(num);
	}
	
	/**
	 * 根据iid修改orderid
	 * @param iid int
	 * @param orderId int
	 * @return boolean
	 */
	public boolean updateOrderIdById(int iid, int orderId){
		UpdateSql sql = new UpdateSql(Tables.DIMENSION);
		sql.addInt("orderid", orderId);
		sql.setWhere("iid = :iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
	}
	
	/**
	 * 卡片式获得栏目下信息列表的卡片角标
	 * @param colId 栏目id
	 * @param moduleId 模块id
	 * @param siteId 网站id
	 * @param orderid 排序id
	 * @param type 1=刷新 2=更多
	 * @param flag 变动标记位 true变动 false无变动
	 * @return List
	 */
	public List<Sign> findByColId(int colId, int moduleId, int siteId, 
			Integer orderid, int type, boolean flag){
		String sql = " SELECT d.iid, d.dname, d.orderid, d.createtime, d.cardtype, d.showtype FROM "
			       + Tables.DIMENSION + " d, " + Tables.DIMENSIONREL + " dr, " + Tables.INFO + siteId
			       + " i WHERE d.iid=dr.dimensionid AND dr.attrid=i.iid"
			       + " AND i.colid=:colid AND d.mid=:mid AND d.siteid=:siteid ";
		if(!flag && type == 1 && orderid != null){ //下拉刷新，flag无变动，取比orderid小的,orderid为null代表第一次请求
			sql += " AND d.orderid<:orderid";
		}else if(type == 2){
			sql += " AND d.orderid>:orderid";
		}
		sql += " GROUP BY d.iid ORDER BY d.orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("mid", moduleId);
		query.addParameter("colid", colId);
		query.addParameter("orderid", orderid);
		query.setStart(0);
		query.setEnd(2);
		return this.queryForEntities(query);
	}
	
	/**
	 * 通过网站Id和模块Id查找角标实体
	 * @param siteId     网站Id 
	 * @param moduleId   模块Id
	 * @return  List
	 */
	public List<Sign> findBySiteId(int siteId, int moduleId){
		String sql = this.getEntitySql() + " WHERE siteid=:siteid AND mid=:mid ";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("mid", moduleId);
		return this.queryForEntities(query);
	}
	
}