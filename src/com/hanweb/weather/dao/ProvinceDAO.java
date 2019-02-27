package com.hanweb.weather.dao; 
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanweb.common.basedao.BaseJdbcDAO;   
import com.hanweb.common.basedao.Query;
import com.hanweb.weather.entity.Province;

@Repository
public class ProvinceDAO extends BaseJdbcDAO<Integer, Province> { 
	
	/**
	 * 查找全部数据
	 * @return List<Province>
	 */
	public List<Province> findAll(){
		String sql = this.getEntitySql(); 
		Query query = createQuery(sql); 
		return this.queryForEntities(query);
	}
}

