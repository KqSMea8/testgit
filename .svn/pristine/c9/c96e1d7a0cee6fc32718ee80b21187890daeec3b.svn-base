package com.hanweb.jmp.apps.dao.broke;

import java.util.List;
import java.util.Map;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.jmp.apps.entity.broke.Broke;
import com.hanweb.jmp.constant.Tables;

public class BrokeDAO extends BaseJdbcDAO<Integer, Broke> {

    /**
     * 获取sql
     * @return String
     */
    private String getSql() {
        String sql = " SELECT a.iid, a.siteid, a.classid, a.isaudit,"
			       + " a.title, a.content, a.createtime, a.picpath, a.picpath1, a.picpath2, a.picpath3, a.audiopath, a.videopath,"
			       + " a.reply, a.replytime, a.loginid, a.uuid, a.isopen, a.clienttype, a.contact, "
			       + " (SELECT b.name FROM jmp_broketype b WHERE b.iid = a.classid) classify,"
			       + " (SELECT ou.name FROM complat_outsideuser ou WHERE ou.loginname = a.loginid AND "
			       + " ou.siteid=:siteid) name,"
			       + " (SELECT ou.headurl FROM complat_outsideuser ou WHERE ou.loginname = a.loginid AND "
			       + " ou.siteid=:siteid) headurl"
			   	   + " from jmp_broke a";
		return sql;
	}

	/**
	 * 获取报料信息实体
	 * @param  id Integer
	 * @return Broke
	 */
	public Broke findBrokeInfo(Integer id) {
		return super.queryForEntityById(id);
	}
	
	/**
	 * 根据标题查报料内容
	 * @param title
	 * @return
	 */
	public  List<Map<String, Object>> findBrokeByTitle(String title) {
		String sql = "SELECT iid,title,content,createtime,picpath,picpath1,picpath2,picpath3 FROM "
				+ Tables.BROKE + "  WHERE title LIKE :title";
		Query query=createQuery(sql);
		query.addParameter("title", title,LikeType.LR);
		return super.queryForList(query);
	}

	/**
	 * 获取某一站点下报料信息列表
	 * @param siteid Integer
	 * @param pageSize Integer
	 * @param maxid Integer
	 * @return List<Broke>
	 */
	public List<Broke> findInfoList(Integer siteid, Integer pageSize, Integer maxid) {
		Query query = createQuery("");
		StringBuffer sql = new StringBuffer(300); 
		sql.append(getSql());
		sql.append(" WHERE a.siteid=:siteid  AND a.isaudit=1 AND a.isopen=1");
		if(NumberUtil.getInt(maxid) > 0){
			  sql.append(" AND a.iid < :iid");
		}
		if(NumberUtil.getInt(pageSize) <= 0){
			pageSize = 15;
		}
		sql.append(" ORDER BY a.iid DESC");
		query.setPageNo(1);
		query.setPageSize(pageSize);
		query.addParameter("iid", maxid);
		query.addParameter("siteid", siteid);
		query.setSql(sql.toString());
		return queryForEntities(query);
	}

	/**
	 * 获取我的报料
     * @param siteid Integer
	 * @param loginid Integer
	 * @param maxid Integer
	 * @param pagesize Integer
	 * @return List<Broke>
	 */
	public List<Broke> findMyList(Integer siteid, Integer pagesize, Integer maxid, String loginid) {
		StringBuffer sql = new StringBuffer(300);
		Query query = createQuery("");
		sql.append(getSql());
		sql.append(" WHERE a.siteid=:siteid AND loginid=:loginid"); 
		if(NumberUtil.getInt(maxid) > 0){ 
			sql.append(" AND a.iid < :iid");  
		} 
		sql.append(" ORDER BY a.iid DESC");
		if(NumberUtil.getInt(pagesize)<=0){
			pagesize=15;
		}
		query.setPageNo(1);
		query.setPageSize(pagesize);
		query.addParameter("siteid", siteid);
		query.addParameter("loginid", loginid);
		query.addParameter("iid", maxid);
		query.setSql(sql.toString());
		return queryForEntities(query);
	}

	/**
	 * 附件上传,更新附件路径
	 * @param column String
	 * @param filepath String
	 * @param id Integer
	 * @return boolean
	 */
	public boolean updateBrokeInfo(String column, String filepath, Integer id) {
		UpdateSql sql = new UpdateSql(Tables.BROKE);
		sql.addString(column, filepath);
		sql.setWhere("iid=:id");
		sql.addWhereParamInt("id", id);
		return this.update(sql);
	}

	/**
	 * 审核和撤审
	 * @param ids String
	 * @param state Integer
	 * @return boolean
	 */
	public boolean updateBrokeInfo(String ids, Integer state) {
		UpdateSql updateSql = new UpdateSql(Tables.BROKE);
		updateSql.addInt("isaudit", state);
		updateSql.setWhere("iid IN (:ids)");
		List<Integer> intIds = StringUtil.toIntegerList(ids, ",");
		updateSql.addWhereParamList("ids", intIds);
		return this.update(updateSql);
	}

	/**
	 * 删除我的报料
	 * @param deviceCode String
	 * @param loginName String
	 * @param id Integer
	 * @return deleteMyBroke
	 */
	public boolean deleteMyBroke(String deviceCode, String loginName, Integer id) {
		String sql = "DELETE FROM " + Tables.BROKE + " WHERE iid=:id AND isaudit=0";
		if (StringUtil.isNotEmpty(deviceCode)) {
			sql += " AND devicecode=:devicecode";
		} else if (StringUtil.isNotEmpty(loginName)) {
			sql += " AND loginname=:loginName";
		}
		Query query = createQuery(sql);
		query.addParameter("id", id);
		query.addParameter("devicecode", deviceCode);
		query.addParameter("loginName", loginName);
		return delete(query);
	}

	/**
	 * 改变是否公开状态
	 * @param iid Integer
	 * @return boolean
	 */
	public boolean updateIsOpen(Integer iid) {
		UpdateSql updateSql = new UpdateSql(Tables.BROKE);
		updateSql.addInt("isopen", 0);
		updateSql.setWhere("iid =:iid");
		updateSql.addWhereParamInt("iid", iid);
		return this.update(updateSql);
	} 
	
	/**
	 * 通过报料ID串获取报料
	 * @param idsLsit
	 *            报料ID串 如:1,2,3
	 * @return 报料实体集合
	 */
	public List<Broke> findByIds(List<Integer> idsLsit) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT iid, siteid, title, content, classid, loginid FROM " + Tables.BROKE)
		   .append(" WHERE iid IN (:idsLsit) ");
		Query query = createQuery(sql.toString());
		query.addParameter("idsLsit", idsLsit);
		List<Broke> brokeList = queryForEntities(query);
		return brokeList;
	}

	/**
	 * 获得报料
	 * @param pageSize int
	 * @param pageNo int
	 * @param siteid int
	 * @return List<Broke>
	 */
	public List<Broke> findInfoBySiteid(int pageSize, int pageNo, int siteid) {
		String sql = " SELECT iid, siteid, title " + " FROM " + Tables.BROKE 
		           + " WHERE  siteid = :siteid";	 
		Query query = createQuery(sql);
		query.addParameter("siteid", siteid);
		query.setPageNo(pageNo);
		query.setPageSize(pageSize);
		List<Broke> brokeList = super.queryForEntities(query);
		return brokeList;
	}
	
	/**
	 * 获取某一站点下报料信息列表 
	 * @param siteid  siteid
	 * @param pageSize pageSize
	 * @return List<Broke>
	 */
	public List<Broke> findPicInfoList(Integer siteid, Integer pageSize) {
		Query query = createQuery("");
		StringBuffer sql = new StringBuffer(300); 
		sql.append(getSql());
		sql.append(" WHERE a.siteid=:siteid  AND a.isaudit=1 AND a.isopen=1 AND picpath!=''");
		if(NumberUtil.getInt(pageSize)<=0){
			pageSize=400;
		}
		sql.append(" ORDER BY a.iid DESC");
		query.setPageNo(1);
		query.setPageSize(pageSize); 
		query.addParameter("siteid", siteid); 
		query.setSql(sql.toString());
		return queryForEntities(query);
	}
	
	/**
	 * 查询网站下面的报料数量
	 * @param siteid  网站ID
	 * @return  报料数量
	 */
	public int findCountBySiteID(int siteid) {
		int count = 0;
		String sql = "SELECT COUNT(1) FROM " + Tables.BROKE + " WHERE siteid = :siteid";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteid);
		count = this.queryForInteger(query);
		return count;
	}
	
}