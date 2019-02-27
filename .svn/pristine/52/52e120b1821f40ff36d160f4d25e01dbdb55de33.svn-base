package com.hanweb.weather.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.weather.constant.Tables;
import com.hanweb.weather.entity.WeatherIndex;

@Repository
public class WeatherIndexDAO extends BaseJdbcDAO<Integer, WeatherIndex>{

	public Integer findId(Integer areaId, String title, String time) {
		String sql = "SELECT iid FROM " + Tables.WEATHERINDEX 
			+ " WHERE areaid=:areaId AND title=:title AND createtime=:time ORDER BY iid DESC";
		Query query = createQuery(sql);
		query.addParameter("areaId", areaId);
		query.addParameter("title", title);
		query.addParameter("time", time);
		List<Map<String, Object>> list = queryForList(query);
		if(list.size() > 0){
			return NumberUtil.getInt(list.get(0).get("iid"));
		}
		return 0;
	}

	public List<WeatherIndex> findByTime(Integer areaId, String time) {
		String sql = getEntitySql() + " WHERE areaid=:areaId  AND createtime=:time";
		Query query = createQuery(sql);
		query.addParameter("areaId", areaId);
		query.addParameter("time", time);
		return queryForEntities(query);
	}


	/**
	 * 删除直说数据
	 * 
	 * @param time 时间
	 *            
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean deleteByTime(String time) {
		if(StringUtil.isEmpty(time)){
			return false;
		} 
		String sql = "DELETE FROM " + Tables.WEATHERINDEX + " WHERE createtime <:time";	
		Query query = createQuery(sql);
		query.addParameter("time", time);
		boolean bl=this.delete(query); 
		return bl;
	}
}
