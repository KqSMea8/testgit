package com.hanweb.complat.dao;


import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.complat.constant.Tables;
import com.hanweb.complat.entity.IpBanList;

/**
 * 封停ip
 * 
 * @author 王建卫
 * 
 */
public class IpBanListDAO extends BaseJdbcDAO<Integer, IpBanList> {
	
	public IpBanList findByIp(String ip){
		String sql = this.getEntitySql()+"where ipaddr=:ipaddr";
		Query query = createQuery(sql);
		query.addParameter("ipaddr", ip);
		return this.queryForEntity(query);
	}
	
	public boolean deleteByIp(String ip){
		String sql = "DELETE FROM " + Tables.IPBANLIST + " WHERE ipaddr = :ipaddr";
		Query query = createQuery(sql);
		query.addParameter("ipaddr", ip);
		return delete(query);
	}
	
}
