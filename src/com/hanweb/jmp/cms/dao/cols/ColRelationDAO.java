package com.hanweb.jmp.cms.dao.cols;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO; 
import com.hanweb.common.basedao.Query; 

import com.hanweb.jmp.cms.entity.cols.ColRelation;
import com.hanweb.jmp.constant.Tables;

public class ColRelationDAO extends BaseJdbcDAO<Integer, ColRelation> {

	/**
	 * 获取栏目绑定的任务
	 * 
	 * @param colid Integer
	 *            栏目ID
	 * @return List<ColRelation> 
	 */
	public ColRelation findByColid(Integer colid) {
		if(colid<=0){
			return null;
		} 
		String sql = "SELECT a.iid, a.colid, a.colname,a.taskid, a.taskname FROM "
				   + Tables.COLRELATION + " a WHERE a.colid=:colid";
		Query query = createQuery(sql);
		query.addParameter("colid", colid);
		return this.queryForEntity(query);
	}
	
	/**
	 * 通过栏目ID串删除频道栏目对应关系
	 * 
	 * @param colIds
	 *            栏目ID串 如:1,2,3
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean deleteByColIds(List<Integer> colIds) {
		String sql = "DELETE FROM " + Tables.COLRELATION + " WHERE colid IN (:colIds)";
		Query query = createQuery(sql);
		query.addParameter("colIds", colIds);
		return this.delete(query);
	}

}