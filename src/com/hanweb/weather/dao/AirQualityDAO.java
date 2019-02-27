package com.hanweb.weather.dao;

import org.springframework.stereotype.Repository;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.weather.entity.AirQuality;

@Repository
public class AirQualityDAO extends BaseJdbcDAO<Integer, AirQuality>{

	public AirQuality findByAreaCode(String areaCode) {
		String sql = this.getEntitySql() + " WHERE areacode=:areacode";
		Query query = createQuery(sql);
		query.addParameter("areacode", areaCode);
		return queryForEntity(query);
	}

}
