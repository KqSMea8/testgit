package com.hanweb.jmp.cms.dao.sign;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;

import com.hanweb.jmp.cms.entity.sign.SignRel;
import com.hanweb.jmp.constant.Tables;

public class SignRelDAO extends BaseJdbcDAO<Integer, SignRel> {

	/**
	 * 获得信息数
	 * @param ids
	 *            角标ID串 如:1,2,3
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findCountSubInfo(List<Integer> ids) {
		String sql = " SELECT COUNT(iid) FROM " + Tables.DIMENSIONREL
		           + " WHERE dimensionid IN (:ids)";
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		int num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 根据信息id及模块id获得关系实体
	 * @param infoId
	 * @param mid
	 * @param siteid
	 * @return
	 */
	public SignRel findRelByInfoId(int infoId, int mid , int siteid){
		String sql = getEntitySql() + " WHERE attrid=:attrid AND moduleid=:moduleid AND siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("attrid", infoId);
		query.addParameter("moduleid", mid);
		query.addParameter("siteid", siteid);
		return this.queryForEntity(query);
	}
	
	/**
	 * 根据信息id及模块id获得关系实体
	 * @param infoId infoId infoId 
	 * @return List
	 */
	public List<SignRel> findRelByInfoId(int infoId){
		String sql = getEntitySql() + " WHERE attrid=:attrid AND moduleid=3";
		Query query = createQuery(sql);
		query.addParameter("attrid", infoId);
		return this.queryForEntities(query);
	}
	
	/**
	 * 根据角标id及模块id获得实体
	 * @param did 角标id
	 * @param mid 模块id
	 * @param siteId 网站id
	 * @return List
	 */
	public List<SignRel> findRelBySignId(int did, int mid, int siteId){
		String sql = getEntitySql() 
			       + " WHERE dimensionid=:dimensionid AND moduleid=:moduleid AND siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("dimensionid", did);
		query.addParameter("moduleid", mid);
		query.addParameter("siteid", siteId);
		return this.queryForEntities(query);
	}
	
	/**
	 * 根据角标id及模块id获得实体
	 * @param did 角标id
	 * @param mid 模块id
	 * @param siteId 网站id
	 * @return List
	 */
	public List<SignRel> findRelBySignIdAndModuleId(int did, int mid, int siteId){
		String sql = "SELECT a.iid, a.siteid, a.dimensionid, a.moduleid, a.attrid, a.orderid, " 
			       + " (SELECT name FROM " + Tables.COL + " WHERE iid=a.attrid) colname, "
			       + " (SELECT title FROM " + Tables.INFO + siteId + " WHERE iid=a.attrid) infoname"
			       + " FROM " + Tables.DIMENSIONREL + " a"
		           + " WHERE a.dimensionid=:dimensionid AND a.moduleid=:moduleid AND a.siteid=:siteid " 
			       + " ORDER BY a.orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("dimensionid", did);
		query.addParameter("moduleid", mid);
		query.addParameter("siteid", siteId);
		return this.queryForEntities(query);
	}
	
	/**
	 * 根据模块id获得实体
	 * @param mid 模块id
	 * @param siteId 网站id
	 * @return List
	 */
	public List<SignRel> findRelByModuleId(int mid, int siteId){
		String sql = getEntitySql() + " WHERE moduleid=:moduleid AND siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("moduleid", mid);
		query.addParameter("siteid", siteId);
		return this.queryForEntities(query);
	}
	
	/**
	 * 删除信息/栏目时，关系表也要删除
	 * @param infoIds List
	 * @return boolean
	 */
	public boolean deleteByInfoIds(List<Integer> infoIds){
		String sql = "DELETE FROM " + Tables.DIMENSIONREL + " WHERE attrid IN(:idList)";
		Query query = createQuery(sql);
		query.addParameter("idList", infoIds);
		this.execute(query);
		return true;
	}
	
	/**
	 * 根据角标id和模块id删除记录
	 * @param
	 * @param moduleId
	 * @param siteId
	 * @return
	 */
	public boolean deleteBySignIdAndModuleId(int dimensionid, int moduleId, int siteId){
		String sql = " DELETE FROM " + Tables.DIMENSIONREL
			       + " WHERE dimensionid=:dimensionid AND moduleid=:moduleid AND siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("dimensionid", dimensionid);
		query.addParameter("moduleid", moduleId);
		query.addParameter("siteid", siteId);
		this.execute(query);
		return true;
	}
	
	/**
	 * 根据角标Id和模块id查找最小的orderid
	 * @param 
	 * @param moduleId
	 * @param siteId
	 * @return
	 */
	public int findMinOrderIdBySignIdAndModuleId(int dimensionid, int moduleId, int siteId){
		int orderId = 0;
		String sql = " SELECT MIN(orderid) FROM " + Tables.DIMENSIONREL
			       + " WHERE dimensionid=:dimensionid AND moduleid=:moduleid AND siteid=:siteid ";
		Query query = createQuery(sql);
		query.addParameter("dimensionid", dimensionid);
		query.addParameter("moduleid", moduleId);
		query.addParameter("siteid", siteId);
		orderId = this.queryForInteger(query); 
		return orderId;
	}
	
	/**
	 * 根据iid修改orderid
	 * @param iid int
	 * @param orderId int
	 * @return boolean
	 */
	public boolean updateOrderIdById(int iid, int orderId){
		UpdateSql sql = new UpdateSql(Tables.DIMENSIONREL);
		sql.addInt("orderid", orderId);
		sql.setWhere("iid = :iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
	}
	
	/**
	 * 根据Iid获得实体
	 * @param idList List
	 * @return List
	 */
	public List<SignRel> findByIid(List<Integer> idList){
		String sql = getEntitySql() + " WHERE iid IN(:idList)"; 
		Query query = createQuery(sql);
		query.addParameter("idList", idList);
		return this.queryForEntities(query);
	}
	
	/**
	 * 根据网站id和频道id获取该频道选择过的栏目id
	 * @param siteId 网站id
	 * @param iid 频道id
	 * @return List 网站栏目对应关系list
	 */
	public List<SignRel> findCheckedSiteIds(Integer siteId, Integer iid){
		String sql = "SELECT DISTINCT dimensionid FROM " 
			       + Tables.DIMENSIONREL 
			       + " WHERE siteid=:siteid AND attrid=:iid";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("iid", iid);
		return this.queryForEntities(query);
	}
	
	/**
	 * 根据信息id和网站id删除
	 * @param infoId    信息id	
	 * @param siteId	网站id
	 * @return  boolean
	 */
	public boolean deleteByInfoidAndSiteId(Integer infoId, Integer siteId, Integer moduleid){
		String sql = " DELETE FROM " + Tables.DIMENSIONREL 
		           + " WHERE attrid = :infoid AND siteid = :siteid AND moduleid = :moduleid";
		Query query = createQuery(sql);
		query.addParameter("infoid", infoId);
		query.addParameter("siteid", siteId);
		query.addParameter("moduleid", moduleid);
		return this.delete(query);
	}
	
}