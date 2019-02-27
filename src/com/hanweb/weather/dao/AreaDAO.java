package com.hanweb.weather.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query; 
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.StringUtil;
import com.hanweb.weather.constant.Tables;
import com.hanweb.weather.entity.Area;

@Repository
public class AreaDAO extends BaseJdbcDAO<Integer, Area> { 
	/**
	 * 分页查找
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public List<Area> findByState(Integer pageSize, Integer pageNo, Integer state, String parcode, 
			                       String citycode){
		String sql = this.getEntitySql()+ " WHERE state=:state";
		if(StringUtil.isNotEmpty(parcode)){
			sql += " AND parcode =:parcode";
		}
		if(StringUtil.isNotEmpty(citycode)){
			sql += " AND citycode =:citycode";
		}
		Query query = createQuery(sql);
		query.addParameter("state", state);
		query.addParameter("parcode", parcode);
		query.addParameter("citycode", citycode);
		query.setPageNo(pageNo);
		query.setPageSize(pageSize);
		return this.queryForEntities(query); 
	}
	
	/**
	 * 查找数目
	 * @return
	 */
	public int findEnableCount(Integer state, String parcode, String citycode){
		String sql = "SELECT COUNT(1) FROM " + Tables.AREA + " WHERE state=:state";
		if(StringUtil.isNotEmpty(parcode)){
			sql += " AND parcode =:parcode";
		}
		if(StringUtil.isNotEmpty(citycode)){
			sql += " AND citycode =:citycode";
		}
		Query query = createQuery(sql);
		query.addParameter("state", state);
		query.addParameter("parcode", parcode);
		query.addParameter("citycode", citycode);
		return this.queryForInteger(query);
	}
	
	
	/**
	 * 通过地区编码获取地区
	 * 
	 * @param areacode
	 *            地区编码
	 * @return 地区实体
	 */
	public Area findByAreaCode(String areacode) {
		String sql = getEntitySql() + " WHERE areacode=:areacode";
		Query query = createQuery(sql);
		query.addParameter("areacode", areacode);
		Area areaEn = this.queryForEntity(query);
		return areaEn;
	}
	
	/**
	 * 更新地区状态
	 * @param id id
	 * @param state 状态
 	 * @return 是否成功
	 */
	public boolean updateState(List<Integer> idList, Integer state){
		UpdateSql updateSql = new UpdateSql(Tables.AREA);
		updateSql.addInt("state", state);
		updateSql.setWhere(" iid IN (:ids)");
		updateSql.addWhereParam("ids", idList);
		return this.update(updateSql);
	}
	
	/**
	 * 查找数目
	 * @return
	 */
	public int findCount(){
		String sql = "SELECT COUNT(*) FROM " + Tables.AREA;
		Query query = createQuery(sql);
		return this.queryForInteger(query);
	}
	
	/**
	 * 分页查找
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public List<Area> findAll(Integer pageSize, Integer pageNo){
		String sql = this.getEntitySql();// + " ORDER BY acronym ASC";
		Query query = createQuery(sql);
		query.setPageNo(pageNo);
		query.setPageSize(pageSize);
		return this.queryForEntities(query);
	}
}
