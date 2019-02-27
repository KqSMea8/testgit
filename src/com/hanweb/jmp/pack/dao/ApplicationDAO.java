package com.hanweb.jmp.pack.dao;
   
import java.util.List; 

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;  
import com.hanweb.common.util.NumberUtil;

import com.hanweb.jmp.pack.entity.App;

public class ApplicationDAO  extends BaseJdbcDAO<Integer, App> {
	
	/**
	 * 根据iid查找实体
	 * @param iid iid
	 * @return    设定参数 .
	 */
	public App findByIid(Integer iid){
		if(NumberUtil.getInt(iid)<=0){
			return null;
		}
		String sql = this.getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		App app = this.queryForEntity(query);
		return app;
	}
	
	/**
	 * 通过用户ID获取应用实体集合
	 * @param userid
	 *            用户ID
	 * @return 应用实体集合
	 */
	public List<App> findByUserId(int userid) {
		if(userid<=0){
			return null;
		}
		String sql = this.getEntitySql() + " a WHERE a.userid=:userid";
		Query query = createQuery(sql);
		query.addParameter("userid", userid);
		List<App> appList = queryForEntities(query);
		return appList;
	}
	
	/**
	 * 根据网站id查找实体
	 * @param siteid siteid
	 * @return    设定参数 .
	 */
	public App findBySiteid(Integer siteid){
		if(NumberUtil.getInt(siteid)<=0){
			return null;
		}
		String sql = this.getEntitySql() + " WHERE siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteid);
		App app = this.queryForEntity(query);
		return app;
	}
	
}