package com.hanweb.jmp.newspush.peoplelist.dao;


import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.newspush.peoplelist.entity.PeopleList;


/**
 * 群组
 * 
 * @author Wangjw
 * 
 */
public class PeoplelistDAO extends BaseJdbcDAO<Integer, PeopleList> {
	
	public List<PeopleList> findByName(String name){
		String sql = this.getEntitySql()+" where name=:name";
		Query query = createQuery(sql);
		query.addParameter("name", name);
		return this.queryForEntities(query);
	}
	
	public PeopleList findByIid(Integer iid){
		String sql = this.getEntitySql()+" where iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		return this.queryForEntity(query);
	}
	
	
	public boolean deleteBypeoplelistId(List<Integer> peoplelistIdList) {
		String sql = "DELETE FROM " + Tables.PEOPLELISTRELATION + " WHERE peoplelistid IN(:peoplelistIdList)";
		Query query = createQuery(sql);
		query.addParameter("peoplelistIdList", peoplelistIdList);
		return this.delete(query);
	}
	
	public List<PeopleList> find(){
		String sql = this.getEntitySql()+" ORDER BY iid ASC";
		Query query = createQuery(sql);
		return this.queryForEntities(query);
	}
}
