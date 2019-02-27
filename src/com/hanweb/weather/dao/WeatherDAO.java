package com.hanweb.weather.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.weather.constant.Tables;
import com.hanweb.weather.entity.Weather;

@Repository
public class WeatherDAO extends BaseJdbcDAO<Integer, Weather>{

	public Integer findId(Integer areaId, String time) {
		String sql = "SELECT iid FROM " + Tables.WEATHER 
		+ " WHERE areaid=:areaId  AND createtime=:time ORDER BY iid DESC";
		Query query = createQuery(sql);
		query.addParameter("areaId", areaId);
		query.addParameter("time", time);
		List<Map<String, Object>> list = queryForList(query);
		if(list.size() > 0){
			return NumberUtil.getInt(list.get(0).get("iid"));
		}
		return 0;
	}

	public Weather findByTime(Integer areaId, String time) {
		String sql = getEntitySql() + " WHERE areaid=:areaId  AND createtime=:time";
		Query query = createQuery(sql);
		query.addParameter("areaId", areaId);
		query.addParameter("time", time);
		return queryForEntity(query);
	}
	
	/**
	 * findGroup:(查询当前天气以及后三天的天气).
	 *
	 * @param areaId
	 * @return    设定参数 .
	*/
	public List<Weather> findGroup(Integer areaId) {
		String sql = getEntitySql() + " WHERE areaid=:areaId  AND " 
			+ "(createtime=:time OR createtime=:time1 OR createtime=:time2 OR createtime=:time3) " 
			+ " ORDER BY createtime ASC";
		Query query = createQuery(sql);
		query.addParameter("areaId", areaId);
		query.addParameter("time", DateUtil.getCurrDate(DateUtil.YYYY_MM_DD));
		query.addParameter("time1", 
				DateUtil.dateToString(DateUtil.nextDay(new Date(), 1), DateUtil.YYYY_MM_DD));
		query.addParameter("time2", 
				DateUtil.dateToString(DateUtil.nextDay(new Date(), 2), DateUtil.YYYY_MM_DD));
		query.addParameter("time3", 
				DateUtil.dateToString(DateUtil.nextDay(new Date(), 3), DateUtil.YYYY_MM_DD));
		return queryForEntities(query);
	}

	/**
	 * 删除天气数据
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
		
		String sql = "DELETE FROM " + Tables.WEATHER + " WHERE createtime <:time";	
		Query query = createQuery(sql);
		query.addParameter("time", time);
		boolean bl=this.delete(query); 
		return bl;
	}
	
}
