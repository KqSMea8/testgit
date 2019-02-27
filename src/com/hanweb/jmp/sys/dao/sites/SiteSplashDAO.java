package com.hanweb.jmp.sys.dao.sites;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.entity.sites.SiteSplash;

public class SiteSplashDAO extends BaseJdbcDAO<Integer, SiteSplash>{

	
	/**
	 * 通用查询信息的sql语句
	 * @return String
	 */
	private String getSql() {
		String sql = " SELECT iid, siteid, Firstandroidpic, Middleandroidpic, Lastandroidpic, "
				   + " firstipadpic, middleipadpic, lastipadpic, Firstiphonepic, Middleiphonepic, "
				   + " lastiphonepic, Firstiphone4pic, Middleiphone4pic, lastiphone4pic, firsttitle, "
				   + " middletitle, lasttitle, firsturl, middleurl, "
				   + " lasturl, ipadversion, iphoneversion, androidversion, imguuid, jopurl "
				   + " FROM " + Tables.SITE_SPLASH;
		return sql;
	}
	
	/**
	 * 通过信息ID获取网站实体
	 * @param iid
	 *            信息ID
	 * @return 信息实体
	 */
	public SiteSplash findByIid(int iid) {
		String sql = this.getSql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		SiteSplash siteSplash = this.queryForEntity(query);
		return siteSplash;
	}
	
	/**
	 * 通过信息ID获取网站实体
	 * @param siteId int
	 *            信息ID
	 * @return 信息实体
	 */
	public SiteSplash findBySiteId(int siteId) {
		String sql = this.getSql() + " WHERE siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		SiteSplash siteSplash = this.queryForEntity(query);
		return siteSplash;
	}
	
	public List<SiteSplash> findSiteSplashs() {
		String sql = this.getSql();
		Query query = createQuery(sql);
		List<SiteSplash> siteSplashs = this.queryForEntities(query);
		return siteSplashs;
	}
	
}