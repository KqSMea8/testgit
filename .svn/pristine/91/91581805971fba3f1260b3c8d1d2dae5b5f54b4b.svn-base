package com.hanweb.jmp.cms.dao.infos;

import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.jmp.cms.entity.infos.InfoCount;
import com.hanweb.jmp.constant.Tables;

public class InfoCountDAO extends BaseJdbcDAO<Integer, InfoCount>{

	/**
	 * getSql
	 * @param siteId Integer
	 * @return String
	 */
	public String getSql(Integer siteId){
		String sql = " SELECT i.iid,i.titleid,i.visitcount,i.commentcount,i.goodcount,i.type,"
			       + " (SELECT COUNT(1) FROM "+Tables.GOOD_RECORD
			       + " g WHERE g.type=:type "
				   + " AND g.titleid=:titleid AND g.uuid=:uuid) isgood"
				   + " FROM " + Tables.INFO_COUNT 
				   + " i";
		sql = sql.replace(Tables.INFO_COUNT, "jmp_info_count" + siteId);
		return sql;
	}
	
	/**
	 * 更新信息
	 * @param titleId Integer
	 * @param type Integer
	 * @param countName String
	 * @param siteId Integer
	 */
	public void updateCountAdd(Integer titleId, Integer type, String countName, Integer siteId){
		String sql = " UPDATE " + Tables.INFO_COUNT 
			       + " SET " + countName + "=" + countName + "+1"
			       + " WHERE titleid=:titleid AND type=:type";
		sql = sql.replace(Tables.INFO_COUNT, "jmp_info_count" + siteId);
		Query query = this.createQuery(sql);
		query.addParameter("titleid", titleId);
		query.addParameter("type", type);
		this.execute(query);
		
	}
	
	/**
	 * 删除已审核信息时更新信息数
	 * @param titleId Integer
	 * @param type Integer
	 * @param countName String
	 * @param siteId Intege
	 */
	public void updateCountDes(Integer titleId, Integer type, String countName, Integer siteId){
		String sql = "UPDATE " + Tables.INFO_COUNT 
			       + " SET " + countName + "=" + countName + "-1"
			       + " WHERE titleid=:titleid AND type=:type";
		sql = sql.replace(Tables.INFO_COUNT, "jmp_info_count" + siteId);
		Query query = this.createQuery(sql);
		query.addParameter("titleid", titleId);
		query.addParameter("type", type);
		this.execute(query);
		
	}
	
	/**
	 * 删除未审核信息时更新信息数
	 * @param titleId Integer
	 * @param type Integer
	 * @param countName String
	 * @param siteId Intege
	 */
	public void updateCountDes1(Integer titleId, Integer type, String countName, Integer siteId){
		String sql = "UPDATE " + Tables.INFO_COUNT 
			       + " SET " + countName + "=" + countName
			       + " WHERE titleid=:titleid AND type=:type";
		sql = sql.replace(Tables.INFO_COUNT, "jmp_info_count" + siteId);
		Query query = this.createQuery(sql);
		query.addParameter("titleid", titleId);
		query.addParameter("type", type);
		this.execute(query);
		
	}
	
	/**
	 * updateCount
	 * @param titleId Integer
	 * @param type Integer
	 * @param countName String
	 * @param count Integer
	 * @param siteId Integer
	 * @return boolean
	 */
	public boolean updateCount(Integer titleId, Integer type, String countName, Integer count, Integer siteId){
		UpdateSql updateSql = new UpdateSql("jmp_info_count" + siteId);
		updateSql.addInt(countName, count);
		updateSql.setWhere(" titleid=:titleid AND type=:type");
		updateSql.addWhereParam("titleid", titleId);
		updateSql.addWhereParam("type", type);
		return this.update(updateSql);
	}
	
	/**
	 * 根据titleid和type来获取信息数
	 * @param titleId Integer
	 * @param type Integer
	 * @param uuid String
	 * @param siteId Integer
	 * @return InfoCount
	 */
	public InfoCount findByTitleIdAndType(Integer titleId, Integer type, String uuid, Integer siteId){
		String sql = this.getSql(siteId) + " WHERE i.titleid=:titleid AND i.type=:type";
		Query query = this.createQuery(sql);
		query.addParameter("titleid", titleId);
		query.addParameter("type", type);
		query.addParameter("uuid", uuid);
		List<InfoCount> infolist=this.queryForEntities(query);
		InfoCount infoEn=null;
		if(!CollectionUtils.isEmpty(infolist)){
			infoEn=infolist.get(0);
		}
		return infoEn;
	}
	
	/**
	 * 查询信息数量
	 * @param titleId     信息ID
	 * @param type        类型
	 * @param siteId Integer
	 * @return int     数量
	 */
	public int findCountByTitleid(Integer titleId, Integer type, Integer siteId) {
		if(titleId==0){
			return 0;
		}
		int count = 0;
		String sql = " SELECT COUNT(1) FROM " + Tables.INFO_COUNT
				   + " WHERE titleid=:titleid AND type=:type";
		sql = sql.replace(Tables.INFO_COUNT, "jmp_info_count" + siteId);
		Query query = createQuery(sql);
		query.addParameter("titleid", titleId);
		query.addParameter("type", type);
		count = this.queryForInteger(query); 
		return count;
	}
	
	/**
	 * 删除信息/栏目时，信息数表也要删除
	 * @param ids List<Integer>
	 * @param type Integer
	 * @param siteId Integer
	 * @return boolean
	 */
	public boolean deleteByIds(List<Integer> ids, Integer type, Integer siteId){
		String sql = " DELETE FROM " + Tables.INFO_COUNT 
		           + " WHERE titleid IN(:idList) AND type=:type";
		sql = sql.replace(Tables.INFO_COUNT, "jmp_info_count" + siteId);
		Query query = createQuery(sql);
		query.addParameter("idList", ids);
		query.addParameter("type", type);
		this.execute(query);
		return true;
	}
	
	/**
	 * 增加信息阅读数表
	 * @param tableName 表名
	 * @return boolean
	 */
	public boolean addTable(String tableName){
		return this.createTable(InfoCount.class, tableName);
	}
	
	/**
	 * 删除信息阅读数表
	 * 
	 * @param tableName 表名
	 * @return boolean
	 */
	public boolean deleteTable(String tableName){
		String sql = "DROP TABLE " + Tables.INFO_COUNT;
		sql = sql.replace("jmp_info_count", tableName);
		Query query = createQuery(sql);
		return this.delete(query);		
	}
	
}