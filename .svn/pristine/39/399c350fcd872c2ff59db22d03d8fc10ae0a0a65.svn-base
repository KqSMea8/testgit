package com.hanweb.jmp.cms.dao.infos; 
import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query; 
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.jmp.constant.Tables;  
import com.hanweb.jmp.cms.entity.infos.Pic;


public class PicDAO extends BaseJdbcDAO<Integer, Pic> {

	/**
	 * 获取爆料信息实体
	 * 
	 * @param iid Integer
	 * @return Pic
	 */
	public Pic findByIid(Integer iid) {
		return super.queryForEntityById(iid);
	}

	  
	/**
	 * 删除图片
	 * 
	 * @param ids
	 *            主键ID字符串
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean deleteByIds(List<Integer> ids) {
		String sql = "DELETE FROM " + Tables.PIC + " WHERE iid IN (:ids)";
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		return this.delete(query);
	}
	
	/**
	 * 删除图片
	 * 
	 * @param ids
	 *            信息ID字符串
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean deleteByInfoids(List<Integer> ids) {
		String sql = "DELETE FROM " + Tables.PIC + " WHERE infoid IN (:ids)";
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		return this.delete(query);
	}
	
	/**
	 * findByInfoid:(获取组图信息).
	 *
	 * @param infoid 信息id 
	 * @param siteid int
	 * @return    设定参数 .
	*/
	public List<Pic> findByInfoid(int infoid, int siteid) {
		if(infoid==0){
			return null;
		}
		String sql = getEntitySql() + " WHERE infoid=:infoid AND siteid=:siteid";
		 
		sql += " ORDER BY iid DESC";
		Query query = createQuery(sql);
		query.addParameter("infoid", infoid);
		query.addParameter("siteid", siteid);
		return queryForEntities(query);
	}
	
	/**
	 * 获得指定ID串的集合
	 * 
	 * @param idsList
	 *            ID
	 * @return 集合
	 */
	public List<Pic> findByIds(List<Integer> idsList) {
		String sql =this.getEntitySql() + " WHERE  iid IN (:idsList) ";	 
		Query query = createQuery(sql);
		query.addParameter("idsList", idsList);
		List<Pic> picList = super.queryForEntities(query);
		return picList;
	}
	
	public List<Pic> findByInfoid1(int infoid, int siteid) {
		if(infoid==0){
			return null;
		}
		String sql = getEntitySql() + " WHERE infoid=:infoid AND siteid=:siteid";
		 
		sql += " ORDER BY iid ASC";
		Query query = createQuery(sql);
		query.addParameter("infoid", infoid);
		query.addParameter("siteid", siteid);
		return queryForEntities(query);
	}
	
	public List<Pic> findByInfoid2(int infoid, int siteid) {
		if(infoid==0){
			return null;
		}
		String sql = getEntitySql() + " WHERE infoid=:infoid AND siteid=:siteid";
		 
		sql += " ORDER BY orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("infoid", infoid);
		query.addParameter("siteid", siteid);
		return queryForEntities(query);
	}
	
	/**
	 * 更新组图的orderid
	 * 
	 * @param orderid
	 *            排序id
	 * @param iid
	 *            图片id
	 * @return true/false
	 */
	public boolean updateOrderIdById(Integer orderid, int iid) {
		UpdateSql sql = new UpdateSql(Tables.PIC);
		sql.addInt("orderid", orderid);
		sql.setWhere("iid = :iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
	}
	
	/**
	 * 获得orderid最小值
	 * @return int
	 */
	public int getMinOrderId(){
		String sql = " SELECT MIN(orderid) FROM " +  Tables.PIC ;
		Query query = createQuery(sql);
		int minOrderId=NumberUtil.getInt(this.queryForInteger(query));
		return minOrderId;
	}
}
