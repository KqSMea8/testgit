package com.hanweb.jmp.cms.dao.cols;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.DateUtil; 
import com.hanweb.common.util.NumberUtil; 
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.constant.Tables; 

public class ColDAO extends BaseJdbcDAO<Integer, Col> { 

	/**
	 * 通过栏目ID、栏目名称及父栏目ID获得同名栏目名称的个数
	 * @param iid
	 *            栏目ID
	 * @param name
	 *            栏目名称
	 * @param pid
	 *            上级栏目ID
	 * @param siteId
	 *            网站id
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findNumOfSameName(Integer iid, String name, Integer pid, Integer siteId) {
		int num = 0;
		String sql = " SELECT COUNT(iid) FROM " + Tables.COL + " WHERE name=:name ";
		if (NumberUtil.getInt(iid) > 0) {
			sql += " AND iid NOT IN(:iid)";
		}
		if (NumberUtil.getInt(siteId) > 0) {
			sql += " AND siteid=:siteId";
		}
		if (pid != null) {
			sql += " AND pid = :pid";
		} else {
			sql += " AND pid IS NULL";
		}
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		query.addParameter("name", name);
		query.addParameter("siteId", siteId);
		query.addParameter("pid", pid);
		num = this.queryForInteger(query);
		return num;
	}

	/**
	 * 获得使用轻应用的栏目数
	 * @param ids
	 *            轻应用ID串 如:1,2,3
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findCountByAppid(List<Integer> ids) {
		String sql = "SELECT COUNT(iid) FROM " + Tables.COL + " WHERE lightappid IN (:ids)";
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		int num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 获得子栏目数
	 * @param ids
	 *            栏目ID串 如:1,2,3
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findCountSubCol(List<Integer> ids) {
		String sql = "SELECT COUNT(iid) FROM " + Tables.COL + " WHERE pid IN (:ids)";
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		int num = this.queryForInteger(query);
		return num;
	}

	/**
	 * 获得信息数
	 * @param ids
	 *            栏目ID串 如:1,2,3
	 * @param siteId          
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findCountSubInfo(List<Integer> ids, Integer siteId) {
		String sql = "SELECT COUNT(iid) FROM " + Tables.INFO + " WHERE colid IN (:ids) AND isremove=0";
		sql = sql.replace(Tables.INFO, "jmp_info" + siteId);
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		int num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 获得信息数
	 * @param ids
	 *            栏目ID串 如:1,2,3
	 * @param siteId          
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findCountRecycleInfo(List<Integer> ids, Integer siteId) {
		String sql = "SELECT COUNT(iid) FROM " + Tables.INFO + " WHERE colid IN (:ids) AND isremove=1";
		sql = sql.replace(Tables.INFO, "jmp_info" + siteId);
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		int num = this.queryForInteger(query);
		return num;
	}

	/**
	 * 更新附件路径
	 * @param col
	 *            栏目对象
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean updatePath(Col col) {
		UpdateSql sql = new UpdateSql(Tables.COL);
		sql.addString("iconpath", col.getIconPath()); 
		sql.setWhere("iid = :iid");
		sql.addWhereParamInt("iid", col.getIid());
		return super.update(sql);
	}

	/**
	 * 更新时间
	 * @param colid
	 *            栏目id
	 * @param startTime
	 *            栏目开始时间
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean updateTime(int colid, String startTime) {
		if (colid <= 0) {
			return false;
		}
		UpdateSql sql = new UpdateSql(Tables.COL);
		sql.addDate("starttime", DateUtil.stringtoDate(startTime, DateUtil.YYYY_MM_DD_HH_MM_SS));
		sql.setWhere("iid = :iid");
		sql.addWhereParamInt("iid", colid);
		return super.update(sql);
	}

	/**
	 * 查询子栏目数量
	 * @param colid
	 *            栏目id
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findSubCol(int colid) {
		int num = 0;
		String sql = " SELECT COUNT(iid) FROM " + Tables.COL;
		if (NumberUtil.getInt(colid) > 0) {
			sql += " WHERE pid =:colid";
		}
		Query query = createQuery(sql);
		query.addParameter("colid", colid);
		num = this.queryForInteger(query);
		return num;
	}

	/**
	 * 根据网站Id查询栏目数量
	 * @param siteId
	 *            网站id
	 * @return 栏目个数
	 */
	public int findColCount(Integer siteId) {
		int num = 0;
		String sql = " SELECT COUNT(iid) FROM " + Tables.COL;
		if (NumberUtil.getInt(siteId) > 0) {
			sql += " WHERE siteid =:siteId";
		}
		Query query = createQuery(sql);
		query.addParameter("siteId", siteId);
		num = this.queryForInteger(query);
		return num;
	}

	/**
	 * 栏目启用停用
	 * @param ids
	 *            栏目ID串
	 * @param enable
	 *            是否有效<br/>
	 *            1 - 有效<br/>
	 *            0 - 无效
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean updateEnable(List<Integer> ids, int enable) {
		UpdateSql sql = new UpdateSql(Tables.COL);
		sql.addInt("enable", enable);
		sql.setWhere("iid IN (:ids)");
		sql.addWhereParamList("ids", ids);
		return super.update(sql);
	}

	/**
	 * 通过ID获取栏目实体并获取isparent属性 用于频道的树调用
	 * @author denganming
	 * @param iid
	 *            栏目ID
	 * @param type
	 *            栏目类型
	 * @return 栏目实体
	 */
	public Col findIsParentByIid(Integer iid, Integer type) {
		String sql = "SELECT a.iid, a.name, a.pid,a.siteid, CASE WHEN EXISTS(SELECT 1 FROM "
			    + Tables.COL
				+ " b WHERE b.pid = a.iid) THEN 1 ELSE 0 END isparent,type,orderid " + " FROM "
				+ Tables.COL + " a WHERE a.iid=:iid";
		if (NumberUtil.getInt(type) != 1) {
			sql += " AND type=:type";
		}
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		query.addParameter("type", type);
		return super.queryForEntity(query);
	}
	
	/**
	 * 通过ID获取栏目实体并获取isparent属性 用于频道的树调用
	 * 
	 * @author denganming
	 * @param iid
	 *            栏目ID
	 * @param types
	 *            栏目类型串
	 * @return 栏目实体
	 */
	public Col findIsParentByIid(Integer iid, String types) {
		String sql = "SELECT a.iid, a.name, a.pid,a.siteid, CASE WHEN EXISTS(SELECT 1 FROM " 
			    + Tables.COL
				+ " b WHERE b.pid = a.iid) THEN 1 ELSE 0 END isparent,type,orderid " + " FROM "
				+ Tables.COL + " a WHERE a.iid=:iid";
		if (types.length() > 0) {
			sql += " AND type IN (:types)";
		}
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		query.addParameter("types", types);
		return super.queryForEntity(query);
	}

	/**
	 * 通过ID获取栏目实体
	 * 
	 * @param iid
	 *            栏目ID
	 * @return 栏目实体
	 */
	public Col findByIid(int iid) {
		String sql = this.getSql() + " WHERE a.iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid); 
		Col col = this.queryForEntity(query); 
		return col;
	}

	/**
	 * 通过栏目名称获取栏目
	 * @param name
	 *            栏目名称
	 * @return 栏目实体集合
	 */
	public List<Col> findByName(String name) {
		String sql = this.getSql() + " WHERE name=:name";
		Query query = createQuery(sql);
		query.addParameter("name", name);
		List<Col> colList = queryForEntities(query);
		return colList;
	}
	
	/**
	 * 通过栏目名称获取相似栏目
	 * @param name
	 *            栏目名称
	 * @return 栏目实体集合
	 */
	public List<Col> findSimilarByName(String name) {
		String sql = this.getSql() + " WHERE name LIKE:name";
		Query query = createQuery(sql);
		query.addParameter("name", name, LikeType.LR);
		List<Col> colList = queryForEntities(query);
		return colList;
	}

	/**
	 * 获取所有栏目
	 * @return 栏目实体列表
	 */
	public List<Col> findAllCol() {
		String sql = getSql();
		Query query = createQuery(sql);
		return super.queryForEntities(query);
	}

	/**
	 * 通过栏目ID获得子栏目集合（树）
	 * @param iid
	 *            栏目父id
	 * @param siteId
	 *            网站id
	 * @return 栏目集合
	 */
	public List<Col> findChildColByIid(Integer iid, Integer siteId) {
		String sql = "SELECT a.iid, a.name, a.pid,a.siteid,a.newlightappid, CASE WHEN EXISTS(SELECT 1 FROM " 
			    + Tables.COL
			    + " b WHERE b.pid = a.iid) THEN 1 ELSE 0 END isparent,"
			    + " a.type,a.orderid,a.enable,a.createtime,a.coltype " 
				+ " FROM "+ Tables.COL + " a ";
		if (NumberUtil.getInt(iid) > 0) {
			sql += "WHERE a.pid=:iid";
		} else {
			sql += "WHERE a.pid IS NULL";
		}
		if (NumberUtil.getInt(siteId) > 0) {
			sql += " AND a.siteid=:siteId";
		}
		sql += " ORDER BY a.orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		query.addParameter("siteId", siteId);
		return super.queryForEntities(query);
	}
	
	/**
	 * 查找所有类目
	 * @param iid
	 * @param siteId
	 * @return
	 */
    public List<Col> findColTreeByType(Integer iid, Integer siteId) {
        String sql = "SELECT a.iid, a.name, a.pid,a.siteid, CASE WHEN EXISTS(SELECT 1 FROM " 
                + Tables.COL
                + " b WHERE b.pid = a.iid) THEN 1 ELSE 0 END isparent,"
                + " a.type,a.orderid,a.enable,a.createtime,a.coltype " 
                + " FROM "+ Tables.COL + " a ";
        if (NumberUtil.getInt(iid) > 0) {
            sql += "WHERE a.pid=:iid";
        } else {
            sql += "WHERE a.pid IS NULL";
        }
        if (NumberUtil.getInt(siteId) > 0) {
            sql += " AND a.siteid=:siteId AND type=1";
        }
        sql += " ORDER BY a.orderid ASC";
        Query query = createQuery(sql);
        query.addParameter("iid", iid);
        query.addParameter("siteId", siteId);
        return super.queryForEntities(query);
    }

	/**
	 * 通过栏目ID获得子栏目集合（树）
	 * @param iid
	 *            栏目ID
	 * @param siteId           
	 * @param typeIds
	 *            栏目类型
	 * @return List<Map> 每个Map包含一个栏目实体
	 */
	public List<Col> findChildColByIidAndType(Integer iid, Integer siteId, List<Integer> typeIds) {
		String sql = "SELECT a.iid, a.name, a.pid,a.siteid, CASE WHEN EXISTS(SELECT 1 FROM " 
			    + Tables.COL+ " b WHERE b.pid = a.iid) THEN 1 ELSE 0 END isparent,a.type,a.orderid" 
			    + " FROM "+ Tables.COL + " a ";
		sql += " WHERE a.type IN(:typeIds)";
		if (NumberUtil.getInt(iid) > 0) {
			sql += "AND a.pid=:iid";
		} 
		sql += " AND a.siteId =:siteId";
		sql += " ORDER BY a.orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		query.addParameter("siteId", siteId);
		query.addParameter("typeIds", typeIds);
		return super.queryForEntities(query);
	}

	/**
	 * 通过是否启用状态位查询栏目
	 * @param enable
	 *            0 未启用 1 启用
	 * @param pageSize Integer
	 * @param pageNo Integer          
	 * @return 栏目集合
	 */
	public List<Col> findColByEnable(Integer pageSize, Integer pageNo, Integer enable) {
		String sql = " SELECT a.iid, a.name, a.type, a.pid,a.audittype,a.lastupdatetime, "
				+ "a.infolisttype,a.infocontenttype,a.collisttype,a.siteid,a.synperiod,a.starttime," 
				+ "a.sourceurl,a.sourcetype,a.sourcename,a.sourcepwd,a.ditchid, "
				+ "(SELECT taskid FROM " + Tables.COLRELATION + " WHERE colid = a.iid) taskid, "
				+ "a.infonum "
				+ " FROM  " + Tables.COL
				+ " a WHERE a.enable=:enable";
		sql += " ORDER BY a.iid ASC";
		Query query = createQuery(sql);
		query.addParameter("enable", enable);
		query.setPageNo(pageNo);
		query.setPageSize(pageSize);
		return super.queryForEntities(query);
	}

	/**
	 * 查找数目
	 * @param enable Integer
	 * @return int
	 */
	public int findEnableCount(Integer enable){
		String sql = "SELECT COUNT(1) FROM " + Tables.COL + " WHERE enable=:enable";
		Query query = createQuery(sql);
		query.addParameter("enable", enable);
		return this.queryForInteger(query);
	}
	
	/**
	 * 查询某一状态的栏目
	 * @param enable
	 *            启用状态位
	 * @param ids
	 *            栏目id
	 * @return 栏目集合
	 */
	public List<Col> findColByEnable(Integer enable, List<Integer> ids) {
		String sql = " SELECT a.iid, a.name, a.type, a.pid,a.audittype, "
				+ "a.infolisttype,a.infocontenttype,a.collisttype,a.siteid," 
				+ "a.synperiod,a.starttime," + "(SELECT taskid FROM "
				+ Tables.COLRELATION + " WHERE colid = a.iid) taskid " + " FROM  " + Tables.COL
				+ " a WHERE a.enable=:enable";
		sql += " AND a.iid IN(:ids)";
		sql += " ORDER BY a.iid ASC";
		Query query = createQuery(sql);
		query.addParameter("enable", enable);
		query.addParameter("ids", ids); 
		return super.queryForEntities(query);

	}

	/**
	 * 查询栏目,包括任务id
	 * @param iid
	 *            主键id
	 * @return 栏目实体
	 */
	public Col findTaskColByIid(int iid) {
		String sql = " SELECT a.iid, a.name, a.type, a.pid,a.audittype, "
				+ " a.infolisttype,a.infocontenttype,a.collisttype,a.siteid,"
				+ " a.synperiod,a.starttime,a.sourceurl,a.sourcename,a.sourcepwd,a.sourcetype," + "(SELECT taskid FROM "
				+ Tables.COLRELATION + " WHERE colid = a.iid) taskid " + " FROM  " + Tables.COL
				+ " a WHERE a.iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		return super.queryForEntity(query);
	}

	/**
	 * 获得指定栏目ID串的栏目集合
	 * @param idsList
	 *            栏目ID串 如:1,2,3
	 * @return 栏目集合
	 */
	public List<Col> findByIds(List<Integer> idsList) {
		String sql = getSql();
		sql += " WHERE iid IN (:idsList) ORDER BY  orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("idsList", idsList);
		List<Col> colList = super.queryForEntities(query);
		return colList;
	}

	/**
	 * 查询指定id串并且启用的栏目集合
	 * @param ids String
	 * @param state boolean
	 * @return List<Col>
	 */
	public List<Col> findByIdsAndState(String ids, boolean state) {
		String sql = getEntitySql() + " WHERE iid IN(:ids)";
		if (state) {
			sql += " AND enable=1";
		}
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		return super.queryForEntities(query);
	}
 
	/**
	 * findBySiteIds:获得指定网站ID串下的所有栏目集合.
	 * @param pageSize pageSize
	 * @param pageNo pageNo
	 * @param siteidList siteidList
	 * @return    栏目集合 .
	 */
	public List<Col> findBySiteIds(Integer pageSize, Integer pageNo, List<Integer> siteidList) {
		String sql = getSql();
		sql += " WHERE a.siteid IN (:siteidList) ORDER BY  a.orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteidList", siteidList);
		query.setPageNo(pageNo);
		query.setPageSize(pageSize);
		List<Col> colList = super.queryForEntities(query);
		return colList;
	}

	/**
	 * 通过网站id查找该网站下栏目数目
	 * @param siteidList
	 *            siteidList
	 * @return 栏目数目
	 */
	public int findColNumBySiteId(List<Integer> siteidList) {
		String sql = "SELECT COUNT(1) FROM " + Tables.COL + " WHERE siteid IN (:siteidList)";
		Query query = this.createQuery(sql);
		query.addParameter("siteidList", siteidList);
		int num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 获得指定父栏目下的所有栏目集合
	 * @param pId
	 *            父栏目
	 * @param siteId
	 *            网站Id
	 * @return 栏目集合
	 */
	public List<Col> findByPidAndSiteId(Integer pId, Integer siteId) {
		String sql = getSql();
		sql += " WHERE a.siteid =:siteId AND a.enable = 1";
		if (NumberUtil.getInt(pId) > 0) {
			sql += " AND a.pid =:pId";
		} else {
			sql += " AND a.pid IS NULL";
		}
		sql += " ORDER BY  a.orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("pId", pId);
		query.addParameter("siteId", siteId);
		List<Col> colList = super.queryForEntities(query);
		return colList;
	}
	
	/**
	 * 获得离线下载栏目集合
	 * @param siteId
	 *            网站Id
	 * @return 栏目集合
	 */
	public List<Col> findByofflineCol(Integer siteId) {
		String sql = getSql();
		sql += " WHERE a.siteid =:siteId AND a.enable = 1 AND offlinenum>0";
		sql += " ORDER BY  a.orderid ASC";
		Query query = createQuery(sql); 
		query.addParameter("siteId", siteId);
		List<Col> colList = super.queryForEntities(query);
		return colList;
	}
	

	/**
	 * 根据网站ID和栏目类型获取该网站下的栏目
	 * @param siteId
	 *            网站id
	 * @param type
	 *            栏目类型
	 * @return List<Col>
	 */
	public List<Col> findBySiteIdAndType(Integer siteId, Integer type) {
		String sql = "SELECT a.iid, a.name, a.pid,a.siteid, CASE WHEN EXISTS(SELECT 1 FROM " 
			    + Tables.COL
				+ " b WHERE b.pid = a.iid) THEN 1 ELSE 0 END isparent,type,orderid " + " FROM "
				+ Tables.COL + " a " + " WHERE siteid=:siteid ";
		if (NumberUtil.getInt(type) != 1) {
			sql += " AND type=:type";
		}
		sql += " ORDER BY orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("type", type);
		List<Col> colList = super.queryForEntities(query);
		return colList;
	}
	 
	/**
	 * findBySiteIdAndType:根据网站ID和栏目类型获取该网站下的栏目.
	 * @param pageSize pageSize
	 * @param pageNo pageNo
	 * @param siteId siteId
	 * @return    设定参数 .
	*/
	public List<Col> findBySiteIdAndType(Integer pageSize, Integer pageNo, Integer siteId) {
		String sql = "SELECT a.iid, a.name, a.pid,a.siteid, CASE WHEN EXISTS(SELECT 1 FROM " 
			    + Tables.COL
				+ " b WHERE b.pid = a.iid) THEN 1 ELSE 0 END isparent,type,orderid " + " FROM "
				+ Tables.COL + " a " + " WHERE siteid=:siteid ";
		sql += " ORDER BY orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		query.setPageNo(pageNo);
		query.setPageSize(pageSize);
		List<Col> colList = super.queryForEntities(query);
		return colList;
	}

	/**
	 * 查找栏目集合
	 * 
	 * @param enable
	 *            启用状态
	 * @param start
	 *            开始条数
	 * @param end
	 *            结束条数
	 * @return 栏目集合
	 */
	public List<Col> findByEnable(int enable, int start, int end) {
		String sql = "SELECT a.iid, a.name, a.type, a.pid,a.audittype, a.limittime, a.siteid FROM "
				+ Tables.COL + " a  WHERE a.enable=:enable";
		Query query = createQuery(sql);
		query.addParameter("enable", enable);
		List<Col> colList = null;
		if (start > 0 || end > 0) {
			query.setStart(start);
			query.setEnd(end);
		}
		colList = super.queryForEntities(query);
		return colList;
	}

	/**
	 * 取得表中相应字段最大的值 ＋1 (用于取得主键)
	 * 
	 * @param tableName
	 *            表名
	 * @param field
	 *            字段名
	 * @return 最大值
	 */
	public String getStrMaxKey(String tableName, String field) {
		try {
			/* 取得最大值 */
			String sql = "SELECT MAX(orderid) FROM " + Tables.COL;
			Query query = createQuery(sql);
			int max = 0;
			max = this.queryForInteger(query);
			if (max + "" == null) {
				return "0";
			}
			/* 最大值＋1 */
			max++;
			return max + "";
		} catch (Exception e) {
			return "0";
		}
	}

	/**
	 * 当栏目下信息有变动时flag+1
	 * 
	 * @param ids
	 *            栏目id集合
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean updateFlag(List<Integer> ids) {
		String sql = "UPDATE " + Tables.COL + " SET flag=flag+1 WHERE iid IN (:ids)";
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		int row = super.execute(query);
		return row > 0;
	}
	
	/**
	 * 当栏目下信息有变动时flag+1
	 * 
	 * @param ids
	 *            栏目id集合
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean updateInfonum(List<Integer> ids, int infonum) {  
		String sql = "UPDATE " + Tables.COL 
			+ " SET infonum=infonum+"+infonum+" WHERE iid IN (:ids)";
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		int row = super.execute(query);
		return row > 0;
	}

	/**
	 * 通用查询栏目的sql语句
	 * @return sql
	 */
	private String getSql() {
		String sql = "SELECT a.iid, a.name, a.userid,a.type, a.firstpicshow,a.pid,"
				+ "(SELECT name FROM " + Tables.COL + "  WHERE iid = a.pid) pname,"
				+ "(SELECT name FROM " + Tables.COL + "  WHERE iid = a.bannerid) bannername,"
				+ "(SELECT lightAppName FROM " + Tables.COL + "  WHERE iid = a.iid) lightAppName,"
				+ "(SELECT url FROM " + Tables.LIGHTAPP + "  WHERE iid = a.lightappid) lightAppUrl,"
				+ "(SELECT taskid FROM " + Tables.COLRELATION + " WHERE colid = a.iid) taskid,"
				+ "(SELECT taskname FROM " + Tables.COLRELATION + " WHERE colid = a.iid) taskname,"
				+ "a.audittype,a.limittime,a.infolisttype,a.infocontenttype,a.collisttype,a.domain,"
				+ "a.iconpath,a.enable,a.spec,a.hdtype,a.bgcolor," 
				+ "a.blogtype,a.nickname,a.acturl,a.siteid,a.orderid,a.bookorderid,a.flag,"
				+ "a.synperiod,a.starttime,a.isjsearch,a.isvisit,a.lastupdatetime,a.iscomment,a.createtime," 
				+ "a.offlinenum,a.listtype,a.collevel,a.commontype,a.bannerid,a.sorttype,a.imguuid,"
				+ "a.issearch, a.infonum,a.lightappid, a.showtype, a.sourceurl, " 
				+ "a.sourcename, a.sourcepwd, a.sourcetype, a.coltype, a.filterreg, a.ditchid, a.newlightappid, a.applayout "
				+" FROM "+ Tables.COL + " a";
		return sql;
	}

	/**
	 * 更新任务id的orderid
	 * @param orderid
	 *            排序id
	 * @param iid
	 *            任务id
	 * @return true/false
	 */
	public boolean updateOrderIdById(Integer orderid, int iid) {
		UpdateSql sql = new UpdateSql(Tables.COL);
		sql.addInt("orderid", orderid);
		sql.setWhere("iid = :iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
	}

	/**
	 * 更新任务id的subscribeorderid
	 * @param subscribeorderid
	 *            订阅排序id
	 * @param iid
	 *            id
	 * @return true/false
	 */
	public boolean updateSubscribeOrderIdById(Integer subscribeorderid, int iid) {
		UpdateSql sql = new UpdateSql(Tables.COL);
		sql.addInt("subscribeorderid", subscribeorderid);
		sql.setWhere("iid = :iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
	}

	/**
	 * 获取网站下面最小的栏目id
	 * 
	 * @param siteid
	 *            网站id
	 * @return 栏目id
	 */
	public int findMinId(int siteid) {
		int iid = 0;
		String sql = "SELECT MIN(iid) FROM " + Tables.COL + " WHERE siteid = :siteid";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteid);
		iid = this.queryForInteger(query);
		return iid;
	}

	/**
	 * 通过网站id查找该网站下栏目数目
	 * 
	 * @param siteId
	 *            网站id
	 * @return 栏目数目
	 */
	public int findColNumBySiteId(int siteId) {
		String sql = "SELECT COUNT(1) FROM " + Tables.COL + " WHERE siteid = :siteid";
		Query query = this.createQuery(sql);
		query.addParameter("siteid", siteId);
		int num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 通过栏目id和导航id获取栏目
	 * @param channelId Integer
	 * @param flagNew boolean
	 * @return List<Col>
	 */
	public List<Col> complete(Integer channelId, boolean flagNew) {
		String sql = "SELECT b.iid,b.name,b.siteid,a.orderid,b.infolisttype,b.infocontenttype,"
				+ " b.collisttype,b.type,b.acturl,b.showtype,b.acttype,b.enable,b.visit,b.issearch "
				+ " FROM "+ Tables.CHANNEL_COLUMN+ " a,"+ Tables.COL+ " b "
				+ " WHERE a.colid=b.iid AND a.channelid=:channelid";
		if (!flagNew) {
			sql += " AND b.type<>3 AND b.type<>4";
		}
		sql += " ORDER BY a.i_orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("channelid", channelId);
		return this.queryForEntities(query);
	}
	
	/**
	 * 修改启用状态
	 * @param col Col
	 * @param enable Integer
	 * @return boolean
	 */
	public boolean updateEnable(Col col, Integer enable) {
		UpdateSql updateSql = new UpdateSql(Tables.COL);
		updateSql.addInt("enable", col.getEnable());
		String where = " siteid=:siteid AND type=:type AND enable=:enab "; 
		updateSql.setWhere(where);
		updateSql.addWhereParamInt("siteid", col.getSiteId());
		updateSql.addWhereParamInt("type", col.getType());
		updateSql.addWhereParamInt("enab", enable);
		return super.update(updateSql);
	}
	 
	/**
	 * 修改栏目的首图路径
	 * @param colEn 栏目实体
	 * @return boolean
	 */
	public boolean updateFirstPath(Col colEn) { 
		if(colEn==null || colEn.getIid()<=0){
			return false;
		} 
		UpdateSql sql = new UpdateSql(Tables.COL);
		sql.addString("infotitle", colEn.getInfoTitle()+"");
		sql.setWhere("iid=:iid");
		sql.addWhereParamInt("iid", colEn.getIid()); 
		return this.update(sql); 
	}
	
	/**
	 * 通过网站id查找该网站下栏目数目
	 * @param siteId
	 *            网站id
	 * @param type
	 *            栏目类型  
	 * @return 数目
	 */
	public int findColNumBySiteId(int siteId , Integer type) {
		String sql = "SELECT COUNT(1) FROM " + Tables.COL + " WHERE siteid = :siteid";
		if(NumberUtil.getInt(type)<=3){
			sql = sql + " AND type = :type";
		}
		Query query = this.createQuery(sql);
		query.addParameter("siteid", siteId);
		query.addParameter("type", NumberUtil.getInt(type));
		int num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 通过网站id获取该网站下离线信息条数
	 * @param siteId int
	 * @return List<Col>
	 */
	public List<Col> findAllOfflineBySiteId(int siteId){
		String sql = getSql() + " WHERE a.siteid=:siteid AND a.offlinenum > 0";
		Query query = this.createQuery(sql);
		query.addParameter("siteid", siteId);
		return queryForEntities(query);
	}
	
	/**
	 * 通过网站id获取栏目
	 * @param siteId int
	 * @return List<Col>
	 */
	public List<Col> findColBySiteId(int siteId){
		String sql = getSql() + " WHERE a.siteid=:siteid";
		Query query = this.createQuery(sql);
		query.addParameter("siteid", siteId);
		return queryForEntities(query);
	}
	
	/**
	 * 获得频道下栏目list
	 * @param chanid 频道id
	 * @return List<Col>
	 */
	public List<Col> findColByChanId(int chanid){
		String sql = getSql() + " , " + Tables.CHANNEL_COLUMN 
			+ " b WHERE a.iid=b.colid AND b.channelid=:chanid AND a.enable=1";
		sql = sql.replace("a.orderid", "b.orderid");
		Query query = this.createQuery(sql);
		query.addParameter("chanid", chanid);
		return queryForEntities(query);
	}
	
	/**
	 * 获得该网站下所有支持订阅的栏目
	 * @param siteId 网站id
	 * @return List<Col>
	 */
	public List<Col> findSubscribeColBySiteId(int siteId){
		String sql = "SELECT a.iid, a.name, a.userid,a.type, a.firstpicshow,a.pid,"
			+ "(SELECT name FROM " + Tables.COL + "  WHERE iid = a.pid) pname,"
			+ "(SELECT name FROM " + Tables.COL + "  WHERE iid = a.bannerid) bannername,"
			+ "b.dimensionid AS bookdimensionid,"
			+ "a.audittype,a.limittime,a.infolisttype,a.infocontenttype,a.collisttype,"
			+ "a.iconpath,a.enable,a.spec,a.hdtype," 
			+ "a.blogtype,a.nickname,a.acturl,a.siteid,b.orderid,a.bookorderid,a.flag,"
			+ "a.synperiod,a.starttime,a.isjsearch,a.isvisit,a.showtype," 
			+ "a.offlinenum,a.listtype,a.collevel,a.commontype,a.bannerid,a.iscomment,a.issearch "
			+ "  FROM "+Tables.COL + " a, " + Tables.DIMENSIONREL 
			+ " b WHERE a.iid=b.attrid AND b.moduleid=2 AND b.siteid=:siteid AND a.enable=1";
		Query query = this.createQuery(sql);
		query.addParameter("siteid", siteId);
		return this.queryForEntities(query);
	}

	public List<Col> findByPidAndSiteId(Integer pId, Integer siteId,
			Integer orderId) {
		String sql = getSql();
		sql += " WHERE a.siteid =:siteId AND a.enable = 1";
		if (NumberUtil.getInt(pId) > 0) {
			sql += " AND a.pid =:pId";
		} else {
			sql += " AND a.pid IS NULL";
		}
		if(orderId != null){
			sql += " AND a.orderid > :orderId";
		}
		sql += " ORDER BY  a.orderid ASC";
		Query query = createQuery(sql);
		query.addParameter("pId", pId);
		query.addParameter("orderId", orderId);
		query.addParameter("siteId", siteId);
		List<Col> colList = super.queryForEntities(query);

		return colList;
	}

	/**
	 * 根据应用iid查找所有关联栏目
	 * @param iid
	 * @param siteId
	 * @return
	 */
	public List<Col> findByLightAppId(Integer iid,Integer siteId) {
		String sql = getSql();
		sql += " WHERE a.siteid =:siteId ";
		if (NumberUtil.getInt(iid) > 0) {
			sql += " AND a.lightappid =:iid";
		} 
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		query.addParameter("siteId", siteId);
		List<Col> colList = super.queryForEntities(query);
		return colList;
	}

	/**
	 * 修改栏目中应用名
	 * @param iid
	 * @param name
	 * @return
	 */
	public boolean modifyLightAppName(Integer iid, String name) {
		String sql = "UPDATE " + Tables.COL + " SET lightappname=:name, name=:name" + " WHERE lightappid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		query.addParameter("name", name);
		int row = super.execute(query);
		return row > 0;
	}

	/**
	 * 根据url查找是否存在栏目调用
	 * @param url
	 * @return
	 */
    public int findCountByUrl(String url, int siteId) {
        String sql = "SELECT COUNT(*) FROM " + Tables.COL + " WHERE sourceurl=:url AND siteid=:siteId";
        Query query = createQuery(sql);
        query.addParameter("url", url);
        query.addParameter("siteId", siteId);
        return this.queryForInteger(query);
    }

    /**
     * 根据type类型查找子栏目
     * @param colId
     * @param siteId
     * @param type
     * @return
     */
    public List<Col> findChildColByIidAndType(Integer colId, Integer siteId,
            int type) {
        String sql = "SELECT a.iid, a.name, a.pid,a.siteid, CASE WHEN EXISTS(SELECT 1 FROM " 
            + Tables.COL+ " b WHERE b.pid = a.iid) THEN 1 ELSE 0 END isparent,a.type,a.orderid" 
            + " FROM "+ Tables.COL + " a ";
        sql += " WHERE a.type =:type ";
        if (NumberUtil.getInt(colId) > 0) {
            sql += "AND a.pid=:iid";
        } else {
            sql += "AND a.pid IS NULL";
        }
        sql += " AND a.siteId =:siteId";
        sql += " ORDER BY a.orderid ASC";
        Query query = createQuery(sql);
        query.addParameter("iid", colId);
        query.addParameter("siteId", siteId);
        query.addParameter("type", type);
        return super.queryForEntities(query);
    }

    /**
     * 查出非父栏目的其他信息列表类型栏目
     * @param colId
     * @param siteId
     * @param type
     * @return
     */
    public List<Col> findColByColIdAndType(int colId, Integer siteId,
            int type) {
        String sql = "SELECT a.iid, a.name, a.pid,a.siteid, CASE WHEN EXISTS(SELECT 1 FROM " 
            + Tables.COL+ " b WHERE b.pid = a.iid) THEN 1 ELSE 0 END isparent,a.type,a.orderid" 
            + " FROM "+ Tables.COL + " a ";
        sql += " WHERE a.type =:type ";
        if (NumberUtil.getInt(colId) > 0) {
            sql += "AND a.iid!=:iid";
        } else {
            sql += "AND a.pid IS NOT NULL";
        }
        sql += " AND a.siteId =:siteId";
        sql += " ORDER BY a.orderid ASC";
        Query query = createQuery(sql);
        query.addParameter("iid", colId);
        query.addParameter("siteId", siteId);
        query.addParameter("type", type);
        return super.queryForEntities(query);
    }
    
    public boolean findCountByAppTypeName(String name){
    	String sql = "SELECT COUNT(*) FROM " + Tables.COL + " WHERE name=:name";    	
    	Query query = createQuery(sql);
        query.addParameter("name", name);        
    	return this.queryForInteger(query)>0;
    }
    
    /**
     * 根据类型获得iid
     */
    public int findIidByAppTypeName(String name){
    	String sql = "SELECT iid FROM " + Tables.COL + " WHERE name=:name and pid is null";    	
    	Query query = createQuery(sql);
        query.addParameter("name", name);        
    	return this.queryForInteger(query);
    }
    
    public boolean findCountByPidAndKeyValue(int pid, String keyValue){
    	String sql = "SELECT COUNT(*) FROM " + Tables.COL + " WHERE pid=:pid AND keyvalue=:keyvalue";
    	Query query = createQuery(sql);
        query.addParameter("pid", pid);
        query.addParameter("keyvalue", keyValue);
        return this.queryForInteger(query)>0;
    }
    
    public boolean updateCol(String name,String keyValue) {
		UpdateSql sql = new UpdateSql(Tables.COL);		
		sql.addString("name", name);
		sql.addString("lightappname", name);
		sql.setWhere("keyvalue = :keyvalue");
		sql.addWhereParamString("keyvalue", keyValue);
		return super.update(sql);
	}
    
    /**
   	 * 通过keyValue删除栏目
   	 */
   	public boolean deleteBykeyValue(String keyValue) {
   		String sql = "DELETE FROM " + Tables.COL + " WHERE keyvalue=:keyvalue";
   		Query query = createQuery(sql);
   		query.addParameter("keyvalue", keyValue);
   		return this.delete(query);
   	}

   	/**
   	 * 根据渠道url查找栏目实体
   	 * @param url
   	 * @param siteId
   	 * @return
   	 */
    public List<Col> findBySourceUrl(String url, int siteId) {
        String sql = getEntitySql() + " WHERE sourceurl=:url AND siteid=:siteId";
        Query query = createQuery(sql);
        query.addParameter("url", url);
        query.addParameter("siteId", siteId);
        return this.queryForEntities(query);
    }

    /**
     * 查询该权限下的子栏目
     * @param iid
     * @param siteId
     * @param list
     * @return
     */
    public List<Col> findChildColByIidAndList(int iid, int siteId, List<Integer> list) {
        String sql = "SELECT a.iid, a.name, a.pid, a.siteid, CASE WHEN EXISTS(SELECT 1 FROM " 
                + Tables.COL
                + " b WHERE b.pid = a.iid) THEN 1 ELSE 0 END isparent,"
                + " a.type,a.orderid,a.enable,a.createtime,a.coltype " 
                + " FROM "+ Tables.COL + " a ";
            if (NumberUtil.getInt(iid) > 0) {
                sql += "WHERE a.pid=:iid";
            } else {
                sql += "WHERE a.pid IS NULL";
            }
            if (NumberUtil.getInt(siteId) > 0) {
                sql += " AND a.siteid=:siteId";
            }
            
            sql += " AND a.iid IN (:list) ORDER BY a.orderid ASC";
            Query query = createQuery(sql);
            query.addParameter("iid", iid);
            query.addParameter("list", list);
            query.addParameter("siteId", siteId);
            return super.queryForEntities(query);
    }

}