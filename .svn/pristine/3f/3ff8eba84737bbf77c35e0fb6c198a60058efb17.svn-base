package com.hanweb.jmp.sys.dao.ditch;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.entity.ditch.Ditch;

public class DitchDAO extends BaseJdbcDAO<Integer, Ditch>{

	/**
	 * 新增提交前判重
	 * @param int1
	 * @param url
	 * @param int2
	 * @return
	 */
	public int findNumByUrl(int iid, String url, int siteid) {
		String sql = "SELECT COUNT(1) FROM " + Tables.DITCH + " WHERE "
			+ " siteid=:siteid AND url=:url";
		if(iid>0){
			sql += " AND iid <> :iid";
		}
		Query query = createQuery(sql);
		if(iid>0){
			query.addParameter("iid", iid);
		}
		query.addParameter("siteid", siteid);
		query.addParameter("url", url);
		Integer num = this.queryForInteger(query);
		return NumberUtil.getInt(num);
	}

	/**
	 * 通过iid查找实体
	 * @param iid
	 * @return
	 */
	public Ditch findByIid(Integer iid) {
		String sql = getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		return this.queryForEntity(query);
	}

	/**
	 * 查找当前站点下的所有渠道
	 * @param siteId
	 * @return
	 */
	public List<Ditch> findBySiteId(int siteId) {
		String sql = getEntitySql() + " WHERE siteid=:siteid AND enable=1";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		return this.queryForEntities(query);
	}

	/**
	 * 根据url查找主键id
	 * @param sourceurl
	 * @param siteId
	 * @return
	 */
    public Ditch findByUrl(String sourceurl, Integer siteId) {
        String sql = "SELECT iid FROM " + Tables.DITCH + " WHERE url=:sourceurl AND siteid=:siteId";
        Query query = createQuery(sql);
        query.addParameter("sourceurl", sourceurl);
        query.addParameter("siteId", siteId);
        return this.queryForEntity(query);
    }

    /**
     * 修改启用状态
     * @param iid
     * @param enable
     * @return
     */
    public boolean modifyEnable(Integer iid, Integer enable) {
        String sql = "UPDATE " + Tables.DITCH + " SET enable=:enable WHERE iid=:iid" ;
        Query query = createQuery(sql);
        query.addParameter("iid", iid);
        query.addParameter("enable", enable);
        return this.execute(query) > 0;
    }

}