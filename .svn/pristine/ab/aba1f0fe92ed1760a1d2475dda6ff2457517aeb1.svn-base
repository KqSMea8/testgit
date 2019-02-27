package com.hanweb.jmp.cms.dao.function;

import java.util.List;
import com.hanweb.common.BaseInfo;
import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;

import com.hanweb.jmp.cms.entity.function.Layout;
import com.hanweb.jmp.constant.Tables;

public class LayoutDAO extends BaseJdbcDAO<Integer, Layout> {

	/**
	 * 查找导航排序id
	 * @param channelid Integer
	 * @return Integer
	 */
	public Integer findChannelOrderId(Integer channelid) {
		String sql = " SELECT orderid FROM " + Tables.LAYOUT
				   + " WHERE type <> 'R' AND channelid=:channelid";
		Query query = createQuery(sql);
		query.addParameter("channelid", channelid);
		return this.queryForInteger(query);
	}

	/**
	 * 查找导航类型
	 * @param channelid Integer
	 * @return Integer
	 */
	public Integer checkChannelIsMyDesk(Integer channelid) {
		String sql = " SELECT type FROM " + Tables.LAYOUT
				   + " WHERE channelid=:channelid AND colid IS NULL";
		Query query = createQuery(sql);
		query.addParameter("channelid", channelid);
		return this.queryForInteger(query);
	}

	/**
	 * 查找栏目排序id
	 * @param channelid Integer
	 * @param colIds String
	 * @return List<Layout>
	 */
	public List<Layout> findColOrderId(Integer channelid, String colIds) {
		String sql = "";
		Query query = createQuery("");
		if (BaseInfo.getDbType() == 1) {// oracle数据库
			sql = " SELECT orderid FROM "+ Tables.LAYOUT
			    + " WHERE type='R' AND channelid=:channelid AND colid IN(:colids) "
			    + " ORDER BY DECODE(colid,:ids)";
			String[] colId = colIds.split(",");
			StringBuffer strids=new StringBuffer(100);
			for (int i = 0; i < colId.length; i++) {
				strids.append(colId[i] + "," + (i+1) + ","); 
			}
			query.addParameter("ids", strids.toString());
		} else {
			sql = " SELECT orderid FROM " + Tables.LAYOUT + " WHERE type='R' " 
			    + " AND channelid=:channelid AND colid IN(:colids) ORDER BY FIELD(colid, :colids)";
		}
		query.addParameter("channelid", channelid);
		query.addParameter("colids", colIds);
		query.setSql(sql);
		return this.queryForEntities(query);
	}

}