package com.hanweb.jmp.global.dao;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.global.entity.CaheData;

/**
 * 报料
 * @author duweibn
 */
public class CacheDataDAO extends BaseJdbcDAO<Integer, CaheData> {
	
	/**
	 * 根据name查找数量
	 * @param iid int
	 * @return ChannelCol
	 */
	public int findByKey(String cachekey){
		String sql = "SELECT COUNT(1) FROM " + Tables.CACHEDATA + " WHERE name =:name";
		Query query = createQuery(sql);
		query.addParameter("name", cachekey);
		Integer num = this.queryForInteger(query);
		return NumberUtil.getInt(num);
	}
	
	/**
	 * 清空所有缓存
	 * @return
	 */
	public boolean removeAll(){
		String sql = "DELETE FROM " + Tables.CACHEDATA;
		Query query = createQuery(sql);
		return this.execute(query) > 0;
	}
 
}