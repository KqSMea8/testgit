package com.hanweb.complat.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.interfacesmanage.entity.Interfaces;


public class InterDAO extends BaseJdbcDAO<Integer, Interfaces> {

	/**
	 * 查询所有接口地址
	 * @return
	 */
	public List<Interfaces> findInterfacesDomain(){
		String sql="SELECT iid,domain FROM "+Tables.INTERFACES;
		Query query=createQuery(sql);
		return this.queryForEntities(query);
	}
	
	public boolean updateInerfacesDomain(String domain,Integer iid){
		UpdateSql sql = new UpdateSql(Tables.INTERFACES);
		sql.addString("domain", domain);
		sql.setWhere("iid=:iid");
		sql.addWhereParam("iid", iid);
		return super.update(sql);
		
	}
	
	
	
}
