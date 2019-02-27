package com.hanweb.jmp.cms.dao.infos;
  
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.dao.field.FieldDAO;
import com.hanweb.jmp.sys.entity.field.Field;

public class InfoDAO  extends BaseJdbcDAO<Integer, Info> {
	
	/**
	 * fieldDAO
	 */
	@Autowired
	private FieldDAO fieldDAO;
	
	/**
	 * 通用查询信息的sql语句
	 * @param siteId  siteId
	 * @return    设定参数 .
	 */
	private String getSql(Integer siteId) {
		StringBuffer sql = new StringBuffer(1024);
		List<String> fieldList = getSysField();
		for(String field : fieldList){
			sql.append(",a.").append(field);
		}
		fieldList = getCustomField(siteId);
		for(String field : fieldList){
			sql.append(",a.").append(field);
		}
		sql.append(" FROM ").append("jmp_info"+siteId + " a");
		return "SELECT " + sql.toString().substring(1);
	}
	
	/**
	 * 系统默认字段
	 * @return List<String>
	 */
	private List<String> getSysField(){
		String[] sysFields = {"iid", "colid", "siteid", "title", "subtitle",
		         "createtime", "jgetid", "syntime", "url", "path",
		         "abs", "status", "orignalpicpath", "firstpicpath",
		         "topid", "toptime", "orderid", "pointtype", "pointlocation",
		         "address", "recomtime", "recommend", "source", "author", "vedio",
		         "audio", "infolisttype", "infocontenttype", 
		         "ztid", "pushstate", "pushtime", "pushofl", "tagid", "audiotime","summary"};
		return Arrays.asList(sysFields);
	}
	
	/**
	 * 自定义字段
	 * @param siteId siteId
	 * @return List<String>
	 */
	public List<String> getCustomField(Integer siteId){
		List<Field> fieldList = fieldDAO.findUnSys(siteId);
		List<String> fields = new ArrayList<String>();
		if(fieldList != null){
			for(Field field : fieldList){
				fields.add(field.getFieldName());
			}
		}
		return fields;
	}
	
	/**
	 * 将MAP转换成信息实体
	 * @param mapList  map数组
	 * @param siteId   网站ID
	 * @return         信息数组
	 */
	private List<Info> mapForList(List<Map<String, Object>> mapList, Integer siteId){
		List<Info> infoList = new ArrayList<Info>();
		if(mapList != null){
			Info info;
			Map<String, Object> map;
			List<String> customFields = getCustomField(siteId);
			for(Map<String, Object> infoMap : mapList){
				info = new Info();
				map = new HashMap<String, Object>();
				info.setIid(NumberUtil.getInt(infoMap.get("iid")));
				info.setColId(NumberUtil.getInt(infoMap.get("colid")));
				info.setSiteId(NumberUtil.getInt(infoMap.get("siteid")));
				info.setTitle(StringUtil.getString(infoMap.get("title")));
				info.setSubTitle(StringUtil.getString(infoMap.get("subtitle")));
				info.setCreateTime((Date) infoMap.get("createtime"));
				info.setJgetId(StringUtil.getString(infoMap.get("jgetid")));
				info.setSynTime((Date) infoMap.get("syntime"));
				info.setUrl(StringUtil.getString(infoMap.get("url")));
				info.setPath(StringUtil.getString(infoMap.get("path")));
				info.setAbs(StringUtil.getString(infoMap.get("abs")));
				info.setSummary(StringUtil.getString(infoMap.get("summary")));
				info.setStatus(NumberUtil.getInt(infoMap.get("status")));
				info.setOrignalPicpath(StringUtil.getString(infoMap.get("orignalpicpath")));
				info.setFirstPicPath(StringUtil.getString(infoMap.get("firstpicpath")));
				info.setTopId(NumberUtil.getInt(infoMap.get("topid")));
				info.setTopTime((Date) infoMap.get("toptime"));
				info.setOrderid(NumberUtil.getInt(infoMap.get("orderid")));
				info.setPointType(NumberUtil.getInt(infoMap.get("pointtype")));
				info.setPointLocation(StringUtil.getString(infoMap.get("pointlocation")));
				info.setAddress(StringUtil.getString(infoMap.get("address")));
				info.setRecomtime((Date) infoMap.get("recomtime"));
				info.setRecommend(NumberUtil.getInt(infoMap.get("recommend")));
				info.setSource(StringUtil.getString(infoMap.get("source")));
				info.setAuthor(StringUtil.getString(infoMap.get("author")));
				info.setVedio(StringUtil.getString(infoMap.get("vedio")));
				info.setAudio(StringUtil.getString(infoMap.get("audio")));
				info.setInfoListType(NumberUtil.getInt(infoMap.get("infolisttype")));
				info.setInfoContentType(NumberUtil.getInt(infoMap.get("infocontenttype")));
				info.setZtId(NumberUtil.getInt(infoMap.get("ztid")));
				info.setPushState(NumberUtil.getInt(infoMap.get("pushstate")));
				info.setPushTime((Date) infoMap.get("pushtime"));
				info.setPushOfl(NumberUtil.getInt(infoMap.get("pushofl")));
				info.setTagid(NumberUtil.getInt(infoMap.get("tagid")));
				info.setAudioTime(StringUtil.getString(infoMap.get("audiotime")));
				info.setCommentcount(NumberUtil.getInt(infoMap.get("commentcount")));
				info.setTagname(StringUtil.getString(infoMap.get("tagname")));
				info.setTagcolor(StringUtil.getString(infoMap.get("tagcolor")));
				info.setImguuid(StringUtil.getString(infoMap.get("imguuid")));
				for(String field : customFields){
					map.put(field, StringUtil.getString(infoMap.get(field)));
				}
				info.setInfoExpand(map);
				infoList.add(info);
			}
		}
		return infoList;
	}
	
	/**
	 * 新增信息
	 * @param info  info
	 * @param tableName   tableName
	 * @return   Integer
	 */
	public Integer insert(Info info, String tableName){
		Integer iid = super.insert(info, tableName);
		if(NumberUtil.getInt(iid)>0){
			Map<String, Object> infoEx = info.getInfoExpand();
			infoEx.put("siteid", info.getSiteId());
			infoEx.put("colid", info.getColId());
			infoEx.put("infoid", info.getIid()); 
			updateExtend(infoEx);
		}
		return iid;
	}
	
	/**
	 * updateExtend
	 * @param contentMap contentMap
	 * @return    设定参数 .
	 */
	public boolean updateExtend(Map<String, Object> contentMap){
		int iid=NumberUtil.getInt(contentMap.get("infoid"));
		if(iid<=0){
			return false;
		}
		int siteid = NumberUtil.getInt(contentMap.get("siteid"));
		UpdateSql sql = new UpdateSql("jmp_info" + siteid); 
		sql.addInt("colid", NumberUtil.getInt(contentMap.get("colid")));
		List<Field> fieldList = fieldDAO.findUnSys(siteid);
		if(fieldList!=null){
			String fieldName = "";
			for(Field field : fieldList){
				fieldName = field.getFieldName();
				sql.addString(fieldName, StringUtil.getString(contentMap.get(fieldName)));
			}
		}
		sql.setWhere("iid=:iid");
		sql.addWhereParamInt("iid", iid);
		return super.update(sql);
	}
	
	/**
	 * 更新信息
	 * @param info info
	 * @param tableName tableName
	 * @return    设定参数 .
	*/
	public boolean update(Info info, String tableName){
		boolean isSuccess = super.update(info, tableName);
		if(isSuccess){
			Map<String, Object> infoEx = info.getInfoExpand();
			infoEx.put("siteid", info.getSiteId());
			infoEx.put("colid", info.getColId());
			infoEx.put("infoid", info.getIid()); 
			isSuccess = updateExtend(infoEx);
		}
		return isSuccess;
	}
	
	/**
	 * 更新信息
	 * @param info info
	 * @return    设定参数 .
	 */
	public boolean updateInfo(Info info){ 
		return super.update(info, "jmp_info" + info.getSiteId());
	}
	
	/**
	 * 按照时间降序排序
	 * @param siteId
	 * @param colid
	 * @return true 成功
	 */
	public boolean update1(int siteId,int colid){
		String sql = "update jmp_info" +siteId+" t "+ 
		" inner join "+ 
		" (SELECT @rownum := @rownum - 1 AS rank, t.orderid "+ 
		" FROM jmp_info"+siteId+" t, (SELECT @rownum := 0) r where colid="+colid+ 
		" order by syntime asc) c on c.orderid = t.orderid "+ 
		" set t.orderid = c.rank where colid="+colid;
		Query query = createQuery(sql);
		int row = super.execute(query); 
		return row > 0;
		}
	
	/**
	 * 通过id删除信息
	 * @param ids
	 *            信息ID字符串
	 * @param siteId
	 *            siteId
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean deleteByIds(List<Integer> ids, Integer siteId) {
		String sql = " DELETE FROM " + Tables.INFO + " WHERE iid IN (:ids)";
		sql = sql.replace("jmp_info", "jmp_info" + siteId);
		Query query = createQuery(sql);
		query.addParameter("ids", ids); 
		return delete(query);
	}
	
	/**
	 * 通过栏目id删除信息
	 * @param colId 栏目
	 * @param siteId siteId                      
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean deleteByColids(int colId, Integer siteId) {
		String sql = " DELETE FROM " + Tables.INFO + " WHERE colid = :colId";
		sql = sql.replace("jmp_info", "jmp_info" + siteId);
		Query query = createQuery(sql);
		query.addParameter("colId", colId); 
		return delete(query);
	}
	
	
	/**
     * 修改信息审核状态位
	 * @param ids 信息ID串 如:1,2,3
	 * @param status 审核状态位
	 * @param tableName tableName
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean updateStateByIds(List<Integer> ids, int status, String tableName) {
		String sql = " UPDATE " + Tables.INFO + " SET status = " + status
		           + " WHERE iid IN (:ids)"; 
		sql = sql.replace("jmp_info", tableName);
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		int row = super.execute(query); 
		return row > 0;
	}
	
	/**
     * 修改信息审核状态位
	 * @param ids 信息ID串 如:1,2,3
	 * @param status 审核状态位
	 * @param tableName tableName
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean updateTagid(List<Integer> tagids, int tagid, String tableName) {
		String sql = " UPDATE " + Tables.INFO + " SET tagid = " + tagid
		           + " WHERE tagid IN (:tagids)"; 
		sql = sql.replace("jmp_info", tableName);
		Query query = createQuery(sql);
		query.addParameter("tagids", tagids);
		int row = super.execute(query); 
		return row > 0;
	}
	
	/**
	 * 更新信息id的orderid
	 * @param orderid
	 *            排序id
	 * @param iid
	 *            信息id
	 * @param tableName
	 *            tableName
	 * @return true/false
	 */
	public boolean updateOrderIdById(Integer orderid, int iid, String tableName) {
		UpdateSql sql = new UpdateSql(tableName);
		sql.addInt("orderid", orderid);
		sql.setWhere("iid = :iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
	}
	 
	/**
	 * 更新置顶时间
	 * @param topid 置顶id
	 * @param toptime 置顶时间
	 * @param infoid 信息id
	 * @param tableName tableName
	 * @return  boolean
	 */
	public boolean updateTopime(int topid, String toptime, String infoids, String tableName) {
		if(StringUtil.isEmpty(infoids)){
			return false;
		}
		List<Integer> idList = StringUtil.toIntegerList(infoids, ",");
		UpdateSql sql = new UpdateSql(tableName);
		sql.addInt("topid", topid);
		sql.addDate("toptime", DateUtil.stringtoDate(
				toptime, DateUtil.YYYY_MM_DD_HH_MM_SS)); 
		sql.setWhere("iid IN (:iid)");
		sql.addWhereParam("iid", idList); 
		return super.update(sql);
	}
	 
	/**
	 * findNewInfo:获取已经审核的最新信息.
	 *
	 * @param siteId siteId
	 * @param colid colid
	 * @param ispic ispic
	 * @return    设定参数 .
	*/
	public Info findNewInfo(Integer siteId, Integer colid, boolean ispic) {
		if(colid<=0){
			return null;
		}
		Info info=null;
		String sql = this.getSql(siteId) + " WHERE a.colid=:colid AND a.status=1";
		if(ispic){
			 sql += " AND a.orignalpicpath IS NOT NULL AND a.orignalpicpath<>''";
		}
		sql += " ORDER BY a.orderid DESC";
		Query query = createQuery(sql);
		query.addParameter("colid", colid); 
		List<Map<String, Object>> list = super.queryForList(query);
		List<Info> infoList = mapForList(list, siteId);
		if(infoList != null && infoList.size()>0){
			info=infoList.get(0); 
		}
		return info;
	} 
	
	/**
	 * 通过信息ID获取信息实体
	 * @param iid
	 *            信息ID
	 * @param siteId
	 *            siteId         
	 * @return 信息实体
	 */
	public Info findByIid(int iid, int siteId) {
		String sql = this.getSql(siteId) + " WHERE a.iid=:iid AND a.isremove=0";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		Info info = null;
		List<Map<String, Object>> list = super.queryForList(query);
		List<Info> infoList = mapForList(list, siteId);
		if(infoList != null && infoList.size()>0){
			info=infoList.get(0); 
		}
		return info;
	}
	
	/**
	 * 通过网站ID获取信息实体
	 * 
	 * @param siteId
	 *            信息ID
	 * @return 信息实体集合
	 */
	public List<Info> findBySiteId(int siteId) {
		String sql = this.getSql(siteId) + " WHERE a.siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		List<Map<String, Object>> list = super.queryForList(query);
		List<Info> infoList = mapForList(list, siteId);
		return infoList;
	}
	
	/**
	 * findByJgetid:通过源系统id获取信息实体.
	 * @param jgetid jgetid
	 * @param siteId siteId
	 * @param colid colid
	 * @return    设定参数 .
	*/
	public Info findByJgetid(String jgetid, Integer siteId, Integer colid) {
		String sql = this.getSql(siteId) + " WHERE a.jgetid=:jgetid AND a.colid=:colid";
		Query query = createQuery(sql);
		query.addParameter("jgetid", jgetid);
		query.addParameter("colid", colid);
		Info info = null;
		List<Map<String, Object>> list = super.queryForList(query);
		List<Info> infoList = mapForList(list, siteId);
		if(infoList != null && infoList.size()>0){
			info=infoList.get(0); 
		}
		return info;
	}
	 
	/**
	 * findByIds:获得指定信息ID串的信息集合.
	 *
	 * @param idsList idsList
	 * @param siteId siteId
	 * @return    设定参数 .
	*/
	public List<Info> findByIds(List<Integer> idsList, Integer siteId) {
		String sql =this.getSql(siteId) + " WHERE  a.iid IN (:idsList) ";	 
		Query query = createQuery(sql);
		query.addParameter("idsList", idsList);
		List<Map<String, Object>> list = super.queryForList(query);
		List<Info> infoList = mapForList(list, siteId);
		return infoList;
	}
	   
	/**
	 * findInfoByColid:获得指定信息ID串的信息集合.
	 *
	 * @param pageSize pageSize
	 * @param pageNo pageSize
	 * @param isremove isremove
	 * @param colId colId
	 * @param tableName tableName
	 * @return    设定参数 .
	*/
	public List<Info> findInfoByColid(int pageSize, int pageNo, int isremove, int colId, String tableName) {
		String sql = " SELECT iid, path, colid, title, siteid, infolisttype," 
			       + " infocontenttype, firstpicpath, orignalpicpath " 
			       + " FROM " + Tables.INFO 
			       + " WHERE  colid = :colId AND isremove = :isremove ORDER BY orderid ASC";
		sql = sql.replace(Tables.INFO, tableName);
		Query query = createQuery(sql);
		query.addParameter("colId", colId);
		query.addParameter("isremove", isremove);
		query.setPageNo(pageNo);
		query.setPageSize(pageSize);
		List<Info> infoList = super.queryForEntities(query);
		return infoList;
	} 
	
	/**
	 * 通过栏目id和在线信息数获取信息实体
	 * @param colId colId
	 * @param siteId siteId
	 * @param offlineNum offlineNum
	 * @param tableName tableName
	 * @return    设定参数 .
	*/
	public List<Info> findInfoByColidAndOfflineNum(int colId, int siteId, int offlineNum, String tableName){
		String sql = this.getEntitySql() 
			       + " WHERE  colid = :colId AND siteid=:siteid AND infocontenttype!=4 " 
			       + " AND infocontenttype!=8 AND infocontenttype!=9 AND isremove!=1 ORDER BY orderid ASC LIMIT :offlinenum";
		sql = sql.replace(Tables.INFO, tableName);
		Query query = createQuery(sql);
		query.addParameter("colId", colId);
		query.addParameter("siteid", siteId);
		query.addParameter("offlinenum", offlineNum);
		List<Info> infoList = super.queryForEntities(query);
		return infoList;
	}
	 
	/**
	 * 根据TOPTIME查询信息
	 * @param topTime 置顶
	 * @param start 开始条数
	 * @param end 结束条数
	 * @param tableName tableName
	 * @return 信息集合
	 */
	public List<Info> findByTopTime(String topTime, int start, int end, String tableName) { 
		String sql = " SELECT a.iid, a.colid,a.siteid FROM "+Tables.INFO
			       + " a WHERE a.topid>0 AND a.toptime<:topTime"  
		           + " ORDER BY a.colid DESC ";
		sql = sql.replace(Tables.INFO, tableName);
		Query query = createQuery(sql); 
		query.addParameter("topTime", topTime);
		List<Info> infoList =null; 
		if(start > 0 || end > 0){
			query.setStart(start);
			query.setEnd(end); 
		} 
		infoList = super.queryForEntities(query); 
		return infoList;
	}
	 
	/**
	 * 根据栏目id获取信息
	 * @param colid 栏目id
	 * @param start 开始条数
	 * @param end 结束条数
	 * @param tableName tableName
	 * @return 信息集合
	 */
	public List<Info> findByColid(int colid, int start, int end, String tableName) {
		String sql = " SELECT a.iid, a.title, a.path, a.colid,a.siteid  FROM "+Tables.INFO
		           + " a WHERE a.colid=:colid"  
	               + " ORDER BY a.topid DESC,a.orderid ASC ";
		sql = sql.replace(Tables.INFO, tableName);
		Query query = createQuery(sql); 
		query.addParameter("colid", colid);
		List<Info> infoList =null;
		if(start > 0 || end > 0){
			query.setStart(start);
			query.setEnd(end); 
		} 
		infoList = super.queryForEntities(query); 
		return infoList;
	}
	
	/**
	 * 根据栏目id获取信息list（用于首页接口）
	 * @param colid     栏目id
	 * @param tableName 查询表名
	 * @return
	 */
	public List<Info> findInfoListByColid(int colid, String tableName) {
		String sql = this.getEntitySql()
		           + " a WHERE a.colid=:colid"  
	               + " ORDER BY a.topid DESC,a.orderid ASC ";
		sql = sql.replace(Tables.INFO, tableName);
		Query query = createQuery(sql); 
		query.addParameter("colid", colid);
		List<Info> infoList =null;
		infoList = super.queryForEntities(query); 
		return infoList;
	}
	
	/**
	 * 获取某一时间点之前的信息
	 * @param colid 栏目id
	 * @param endtime 结束时间
	 * @param start 开始条数
	 * @param end 结束条数
	 * @param tableName 表名
	 * @return 信息集合
	 */
	public List<Info> findByEndtime(int colid, String endtime, int start, int end, String tableName) {
		String sql = " SELECT a.iid, a.title, a.path, a.colid,a.status,a.siteid  FROM "+Tables.INFO
		           + " a WHERE a.colid=:colid AND a.createtime<:endtime"  
		           + " ORDER BY a.topid DESC,a.orderid ASC "; 
		sql = sql.replace(Tables.INFO, tableName);
		Query query = createQuery(sql); 
		query.addParameter("colid", colid);
		query.addParameter("endtime", endtime);
		List<Info> infoList =null;
		if(start > 0 || end > 0){
			query.setStart(start);
			query.setEnd(end); 
		} 
		infoList = super.queryForEntities(query); 
		return infoList;
	}
	
	/**
	 * 获取某一时间点之前的信息
	 * @param colid 栏目id
	 * @param endtime 结束时间  
	 * @param tableName tableName  
	 * @return 信息集合
	 */
	public int findByEndtimeCount(int colid, String endtime, String tableName) {
		String sql = " SELECT COUNT(1) FROM "+Tables.INFO
		           + " WHERE colid=:colid AND createtime<:endtime";
		sql = sql.replace(Tables.INFO, tableName);
		Query query = createQuery(sql); 
		query.addParameter("colid", colid);
		query.addParameter("endtime", endtime);
		 
		int count = this.queryForInteger(query); 
		return count;
	}
	
	/**
	 * 根据栏目ID查询最大ORDERID
	 * @param colId     栏目ID
	 * @param tableName     tableName
	 * @return           最大ORDERID
	 */
	public int findMaxOrderIdByCateID(int colId, String tableName) {
		int orderId = 0;
		String sql = " SELECT MAX(orderid) FROM " + Tables.INFO
				   + " WHERE colid = :colId";
		sql = sql.replace(Tables.INFO, tableName);
		Query query = createQuery(sql);
		query.addParameter("colId", colId);
		orderId = this.queryForInteger(query); 
		return orderId;
	}
	
	/**
	 * 根据栏目ID查询最小ORDERID
	 * @param colId     栏目ID
	 * @param tableName     tableName
	 * @return           最大ORDERID
	 */
	public int findMinOrderIdByCateID(int colId, String tableName) {
		int orderId = 0;
		String sql = " SELECT MIN(orderid) FROM " + Tables.INFO
				   + " WHERE colid = :colId";
		sql = sql.replace(Tables.INFO, tableName);
		Query query = createQuery(sql);
		query.addParameter("colId", colId);
		orderId = this.queryForInteger(query); 
		return orderId;
	}
	
	/**
	 * 查询栏目下面信息数量
	 * @param colId     栏目ID
	 * @param tableName     tableName
	 * @return       信息数量
	 */
	public int findCountByCateID(int colId, String tableName) {
		int count = 0;
		String sql = " SELECT COUNT(1) FROM " + Tables.INFO
				   + " WHERE colid = :colId";
		sql = sql.replace(Tables.INFO, tableName);
		Query query = createQuery(sql);
		query.addParameter("colId", colId);
		count = this.queryForInteger(query); 
		return count;
	}
	 
	/**
	 * 根据栏目id获取信息
	 * @param colid 栏目id
	 * @param tableName tableName
	 * @return List<Info>
	 */
	public List<Info> findAllChildInfoByColid(Integer colid, String tableName) { 
		String sql = " SELECT a.iid, a.title, a.colid, a.orderid,a.siteid FROM " 
			       + Tables.INFO + " a WHERE a.isremove=0 AND a.topid=0 ";
		if (NumberUtil.getInt(colid) > 0) {
			sql += " AND a.colid =:colid";
		} 
		sql += " ORDER BY a.topid DESC, a.orderid ASC ";
		sql = sql.replace(Tables.INFO, tableName);
		Query query = createQuery(sql); 
		query.addParameter("colid", colid);
		 
		List<Info> infoList = super.queryForEntities(query); 
		return infoList;
	}
	
	/**
	 * 根据栏目id获取搜索信息
	 * @param colId 栏目id
	 * @param num   需要多少条 
	 * @param page  第几页
	 * @param keyWord 关键字
	 * @param siteId siteId
	 * @return List<Info>
	 */
	public List<Info> findInfoByColid(int colId, int num, 
			int page, String keyWord, int siteId) { 
		String sql = getEntitySql() 
			       + " WHERE siteid=:siteid AND status=1 AND title LIKE :keyword AND isremove=0";
		if(colId > 0){
			sql += " AND colid=:colid ";
		}
		sql += " ORDER BY syntime DESC ";
		sql = sql.replace(Tables.INFO, "jmp_info"+siteId);
		Query query = createQuery(sql); 
		query.addParameter("siteid", siteId);
		query.addParameter("colid", colId);
		query.addParameter("keyword", keyWord, LikeType.LR);
		query.setPageNo(page);
		query.setPageSize(num);
		List<Info> infoList = super.queryForEntities(query); 
		return infoList;
	}
	
	/**
	 * 根据栏目id获取信息
	 * @param colId 栏目id
	 * @param siteId 网站id 
	 * @return List<Info>
	 */
	public List<Info> findInfoByColid(int colId, int siteId) {
		int status = 1;
		int isremove = 0;
		String sql = getEntitySql()
			       + " WHERE siteid=:siteid AND colid=:colid AND status = :status " 
			       + " AND isremove = :isremove ORDER BY topid DESC, orderid ASC";
		sql = sql.replace(Tables.INFO, "jmp_info"+siteId);
		Query query = createQuery(sql); 
		query.addParameter("siteid", siteId);
		query.addParameter("colid", colId);
		query.addParameter("status", status);
		query.addParameter("isremove", isremove);
		List<Info> infoList = super.queryForEntities(query); 
		return infoList;
	}
	
	/**
	 * 根据栏目ID查询最大TOPID
	 * @param colId     栏目ID
	 * @param tableName     tableName
	 * @return           最大TOPID
	 */
	public int findMaxTopIdByCateID(int colId, String tableName) {
		int orderId = 0;
		String sql = " SELECT MAX(topid) FROM " + Tables.INFO
				   + " WHERE colid = :colId";
		sql = sql.replace(Tables.INFO, tableName);
		Query query = createQuery(sql);
		query.addParameter("colId", colId);
		orderId = this.queryForInteger(query); 
		return orderId;
	}
	
	/**
	 * 查询重复信息数量
	 * @param info
	 *            信息实体 
	 * @return 0 - 不存在<br/>
	 *         n - 存在n个
	 */
	public int findNumOfSame(Info info) {
		int num = 0;
		String sql = " SELECT COUNT(iid) FROM " + Tables.INFO 
			       + " WHERE title=:title AND isremove=0";
		if (NumberUtil.getInt(info.getIid()) > 0){
			sql += " AND iid <>:iid";
		} 
		if (!StringUtil.isEmpty(info.getUrl())) {
			sql += " AND url=:url";
		}
		if (NumberUtil.getInt(info.getColId()) > 0){
			sql += " AND colid =:colid";
		}   
		if (!StringUtil.isEmpty(info.getJgetId())) {
			sql += " AND jgetid=:jgetid";
		}  
		sql = sql.replace(Tables.INFO, "jmp_info"+info.getSiteId());
		Query query = createQuery(sql);
		query.addParameter("title", info.getTitle());
		if (NumberUtil.getInt(info.getIid()) > 0){
			query.addParameter("iid", info.getIid());
		} 
		if (!StringUtil.isEmpty(info.getUrl())) {
			query.addParameter("url", info.getUrl());
		}
		if (NumberUtil.getInt(info.getColId()) > 0){
			query.addParameter("colid", info.getColId());
		}   
		if (!StringUtil.isEmpty(info.getJgetId())) {
			query.addParameter("jgetid", info.getJgetId());
		}   
		num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * 查询信息排序id
	 * @param type type
	 * @param colEn colEn
	 * @param orderId orderId
	 * @param topId topId
	 * @param start start
	 * @param end end
	 * @param tableName tableName
	 * @return    设定参数 .
	*/
	public List<Info> findGTOrderid(int type, Col colEn, Integer orderId, int topId, int start, int end, String tableName, Integer isbanner) { 
		String sql = getCommnSql(colEn.getSiteId())+" FROM " + Tables.INFO+ colEn.getSiteId()+ " i " 
				   + " WHERE i.colid=:colid AND i.status=1 AND i.isremove=0";  
		if(NumberUtil.getInt(isbanner)==1){
			sql += " AND  (i.firstPicPath !='' OR i.orignalPicpath !='')";
		}
		if(type == 0){
			if(topId != 0){
				sql += " AND i.topid >:topId";
			}
			if(orderId != null){ //为null的话代表第一次请求
				sql += " AND i.orderid > :orderId";
			}
		}else{
			if(topId != 0){
				sql += " AND i.topid <:topId";
			}
			if(orderId != null){
				sql += " AND i.orderid > :orderId";
			}
		}
		if(NumberUtil.getInt(colEn.getSortType()) == 1){
			sql += " ORDER BY i.syntime DESC";
		}else{
			sql += " ORDER BY i.topid DESC, i.orderid ASC";
		}  
		Query query = createQuery(sql);
		query.addParameter("colid", NumberUtil.getInt(colEn.getIid()));
		query.addParameter("orderId", orderId);
		if(topId != 0){
			query.addParameter("topId", topId);
		}
		query.setStart(start);
		query.setEnd(end);
		List<Map<String, Object>> list = super.queryForList(query);
		List<Info> infoList = mapForList(list, colEn.getSiteId());
		return infoList;
	}
	
	/**
	 * 查询信息时间
	 * @param type type
	 * @param colEn type
	 * @param time time
	 * @param topId topId
	 * @param start start
	 * @param end end
	 * @param tableName tableName
	 * @return    设定参数 .
	*/
	public List <Info> findGTTime(int type, Col colEn, Date time, Integer topId, int start, int end, String tableName, Integer isbanner) {
		String sql = getCommnSql(colEn.getSiteId())+" FROM " + Tables.INFO+ colEn.getSiteId()+ " i " 
				   + " WHERE i.colid=:colid AND i.status=1 AND i.isremove=0";  
		if(NumberUtil.getInt(isbanner)==1){
			sql += " AND (i.firstPicPath !='' OR i.orignalPicpath !='')";
		}
		if(type == 0){
			if(topId != 0){
				sql += " AND i.topid >:topId";
			}
			if(time != null){
				sql += " AND i.syntime > :syntime";
			}
		}else{
			if(topId != 0){
				sql += " AND i.topid <:topId";
			}
			if(time != null){
				sql += " AND i.syntime < :syntime";
			}
		}
		if(NumberUtil.getInt(colEn.getSortType()) == 1){
			sql += " ORDER BY i.syntime DESC";
		}else{
			sql += " ORDER BY topid DESC, syntime DESC";
		}	 
		if(NumberUtil.getInt(colEn.getInfoListType())!=10){
			sql = sql.replace(Tables.INFO, tableName);
		}
		Query query = createQuery(sql);
		query.addParameter("colid", NumberUtil.getInt(colEn.getIid()));
		if(time != null){
			query.addParameter("syntime", time);
		}
		query.addParameter("topId", topId);
		query.setStart(start);
		query.setEnd(end);
		List<Map<String, Object>> list = super.queryForList(query);
		List<Info> infoList = mapForList(list, colEn.getSiteId());
		return infoList;
	}
	
	/**
	 * 查询推送信息时间
	 * @param type type
	 * @param time time
	 * @param start start
	 * @param end end
	 * @param siteId siteId
	 * @return    设定参数 .
	*/
	public List<Info> findPushInfoGTtime(int type, Date time, int start, int end, int siteId) {
		String sql = getEntitySql() 
			       + " WHERE siteid=:siteid AND pushstate = 2 AND status=1 AND isremove=0";
		if(type == 0){
			sql += " AND pushtime > :pushtime ORDER BY pushtime DESC";
		}else{
			sql += " AND pushtime < :pushtime ORDER BY pushtime DESC";
		}
		sql = sql.replace(Tables.INFO, "jmp_info" + siteId);
		Query query = createQuery(sql);
		query.addParameter("pushtime", time);
		query.addParameter("siteid", siteId);
		query.setStart(start);
		query.setEnd(end);
		return queryForEntities(query);
	}
	
	/**
	 * 查找需要推送的信息
	 * @param now 当前时间
	 * @param tableName tableName
	 * @return 信息list
	 */
	public List<Info> findInfoToPush(Date now, String tableName){
		String sql = getEntitySql() 
			       + " WHERE pushstate = 1 AND pushtime < :pushtime AND isremove=0";
		sql = sql.replace(Tables.INFO, tableName);
		Query query = createQuery(sql);
		query.addParameter("pushtime", now);
		return queryForEntities(query);
	}
	
	/**
	 * 修改信息推送状态为已推送
	 * @param ids  ids
	 * @param tableName tableName 
	 * @return boolean
	 */
	public boolean updatePushState(List<Integer> ids, String tableName){
		UpdateSql sql = new UpdateSql(tableName);
		sql.addInt("pushstate", 2);
		sql.setWhere("iid IN (:ids)");
		sql.addWhereParamList("ids", ids);
		return this.update(sql);
	}
	
	/**
	 * 修改信息带待推送
	 * @param info info 
	 * @return boolean
	 */
	public boolean updatePushState(Info info){
		UpdateSql sql = new UpdateSql("jmp_info" + info.getSiteId());
		sql.addInt("pushstate", 1);
		sql.addDate("pushtime", info.getPushTime());
		sql.addInt("pushofl", info.getPushOfl());
		sql.addString("pushtimeshow", info.getPushtimeshow());
		sql.setWhere("iid = :id");
		sql.addWhereParamInt("id", info.getIid());
		return this.update(sql);
	}
	
	/**
	 * getSql
	 * @return    设定参数 .
	*/
	private String getSql(){
		String sql = " SELECT i.iid, i.colid, i.siteid, i.title," 
			       + " i.subtitle, i.createtime, i.syntime,"
			       + " i.jgetid, i.url, i.abs, i.status, i.firstpicpath, i.orignalpicpath, i.topid, " 
			       + " i.toptime, i.orderid, i.pointtype, i.pointlocation, i.imguuid, i.sourceid, i.infotype" 
		           + " i.address, i.recomtime, i.recommend, "
			       + " i.source, i.author, i.vedio, i.audio, i.path, i.infolisttype, i.infocontenttype, " 
			       + " i.ztid, i.pushstate, i.pushtime, i.pushofl ,i.summary ";
		return sql;
	}
	
	/**
	 * getSql
	 * @return    设定参数 .
	*/
	private String getCommnSql(int siteid){
		
		//获取自定义字段
		String unSysField = "";
		List<String> fieldList = getCustomField(siteid);
		if(CollectionUtils.isNotEmpty(fieldList)){
			for(String field : fieldList){
				unSysField += " i." + field + ",";
			}
		}
		
		String sql = " SELECT i.iid, i.colid, i.siteid, i.title," 
			       + " i.subtitle, i.createtime, i.syntime,"
			       + " i.jgetid, i.url, i.abs, i.status, i.firstpicpath, i.orignalpicpath, i.topid, " 
			       + " i.toptime, i.orderid, i.pointtype, i.pointlocation, i.imguuid, i.sourceid, i.infotype, " 
		           + " i.address, i.recomtime, i.recommend,i.summary, "
			       + " i.source, i.author, i.vedio, i.audio, i.path, i.infolisttype, i.infocontenttype, " 
			       + " i.ztid, i.pushstate, i.pushtime, i.pushofl,i.isremove, i.removetime,i.tagid,i.audiotime," 
			       + unSysField
			       + "(SELECT dname FROM " + Tables.DIMENSION + " d WHERE i.tagid = d.iid) tagname,"
			       + "(SELECT color FROM " + Tables.DIMENSION + " d1 WHERE i.tagid = d1.iid) tagcolor,"
			       + "(SELECT MAX(v.commentcount) FROM " 
			       + Tables.INFO_COUNT + siteid + " v WHERE v.titleid = i.iid AND v.type=1) commentcount ";

		return sql;
	}
	 
	/**
	 * 查找指定维度及栏目下的信息实体（栏目下卡片内信息展现）
	 * @param 
	 * @param colId 栏目id
	 * @param page page
	 * @param tableName tableName
	 * @return    设定参数 .
	*/
	public List<Info> findCardInfoByColIdAndSignId(int dimensionid, 
			int colId, int page, String tableName){
		String sql = getSql() + " FROM " + Tables.INFO + " i, " + Tables.DIMENSIONREL + " dr " 
			       + " WHERE i.iid=dr.attrid AND dr.dimensionid=:dimensionid " 
		           + " AND i.colid=:colid AND i.status=1 AND i.isremove=0"
			       + " ORDER BY dr.orderid ASC";
		//维度信息接口，orderid用的是自己的
		sql = sql.replace("i.orderid", "dr.orderid");
		sql = sql.replace(Tables.INFO, tableName);
		Query query = createQuery(sql);
		query.addParameter("dimensionid", dimensionid);
		query.addParameter("colid", colId);
		query.setStart(0);
		query.setEnd(page);
		return this.queryForEntities(query);
	}
	 
	/**
	 * 新增表
	 * @param tableName 表名
	 * @return    设定参数 .
	*/
	public boolean addTable(String tableName){
		return this.createTable(Info.class, tableName);
	}
	
	/**
	 * 删除信息表
	 * @param tableName 表名
	 * @return boolean
	 */
	public boolean deleteTable(String tableName){
		String sql = "DROP TABLE " + Tables.INFO;
		sql = sql.replace("jmp_info", tableName);
		Query query = createQuery(sql);
		return this.delete(query);	
	}
	
	/**
	 * 更新信息的展现样式
	 * @param infoListType infoListType
	 * @param infoContentType infoContentType
	 * @param colid colid
	 * @param tableName tableName
	 * @return boolean tableName
	 */
	public boolean updateColInfoType(int infoListType, int infoContentType, int colid, String tableName){
		String sql = " UPDATE " + Tables.INFO + " SET infolisttype=:infoListType " 
		           + " ,infocontenttype=:infoContentType WHERE colid=:colid AND infocontenttype!=4 " 
		           + " AND infocontenttype!=8 AND infocontenttype!=9";
		sql = sql.replace("jmp_info", tableName);
		Query query = createQuery(sql);
		query.addParameter("infoListType", infoListType);
		query.addParameter("infoContentType", infoContentType);
		query.addParameter("colid", colid);
		return this.execute(query) > 0;
	}
	
	/**
	 * 更新信息删除的状态
	 * @param state           状态
	 * @param ids             信息id串
	 * @param tableName       信息表名
	 * @return boolean
	 */
	public boolean updateIsRemove(int state, List<Integer> ids, String tableName){
		String sql = " UPDATE " + Tables.INFO + " SET isremove=:state, removetime=:removetime "
		           + " WHERE iid IN (:ids)";
		sql = sql.replace("jmp_info", tableName);
		Query query = createQuery(sql);
		query.addParameter("state", state);
		query.addParameter("ids", ids);
		query.addParameter("removetime", new Date());
		return this.execute(query) > 0;
	}
	
	/**
	 * 通过栏目Id更新信息删除状态
	 * @param state state
	 * @param colId colId
	 * @param tableName tableName
	 * @return boolean
	 */
	public boolean updateIsRemoveByColId(int state, int colId, String tableName){
		String sql = " UPDATE " + Tables.INFO + " SET isremove=:state, removetime=:removetime"
		           + " WHERE colid=:colId";
		sql = sql.replace("jmp_info", tableName);
		Query query = createQuery(sql);
		query.addParameter("state", state);
		query.addParameter("colId", colId);
		query.addParameter("removetime", new Date());
		return this.execute(query) > 0;            
	}
	
	/**
	 * 回收站清空所有信息
	 * @param tableName     表名
	 * @param colId         栏目id
	 * @return boolean
	 */
	public boolean removeAll(String tableName, int colId){
		String sql = "DELETE FROM " + Tables.INFO + " WHERE isremove=1 AND colid=:colId";
		sql = sql.replace("jmp_info", tableName);
		Query query = createQuery(sql);
		query.addParameter("colId", colId);
		return this.execute(query) > 0;
	}
	
	public static void main(String[] args) {
		 try
		    {
		      String url="jdbc:mysql://127.0.0.1/jmp315";
		      String user="root";
		      String pwd="123";
		      //加载驱动，这一句也可写为：Class.forName("com.mysql.jdbc.Driver");
		      Class.forName("com.mysql.jdbc.Driver").newInstance();
		      //建立到MySQL的连接
		       Connection conn = DriverManager.getConnection(url,user, pwd);
		      conn.close();
		    }catch (Exception ex){
		        ex.printStackTrace();
		      }
	}
	
	/**
	 * 根据时间降序
	 * @param colId
	 * @param siteId
	 * @return
	 */
	public List<Info> descByTime(int colId,int siteId) { 
		String sql = getSql(siteId) + "  where a.colid =:colId  ORDER BY a.syntime DESC ";		
		Query query = createQuery(sql); 
		query.addParameter("siteid", siteId);
		query.addParameter("colId", colId);	 
		List<Info> infoList = super.queryForEntities(query); 
		return infoList;
	}
	
	/**
	 * 根据orderid降序
	 * @param colId
	 * @param siteId
	 * @return
	 */
	public List<Info> descByOrderId(int colId,int siteId) { 
		String sql = getSql(siteId) + " where a.colid = :colId  ORDER BY a.orderid ASC ";
		Query query = createQuery(sql); 
		query.addParameter("siteid", siteId);
		query.addParameter("colId", colId);	 
		List<Info> infoList = super.queryForEntities(query); 
		return infoList;
	}

	/**
	 * 根据栏目id, 信息标题, 信息源文地址判重
	 * @param iid
	 * @param title
	 * @param url
	 * @return
	 */
    public int findNumByColIdAndTitleAndUrl(int iid, String title,
            String url, int siteid) {
        String tableName = "jmp_info" + siteid;
        String sql = "SELECT count(1) FROM " + Tables.INFO + " WHERE colid=:iid AND title=:title AND isremove=0";
        sql = sql.replace("jmp_info", tableName);
        if(url != null && url != ""){
            sql += " AND url=:url";
        }
        Query query = createQuery(sql);
        query.addParameter("iid", iid);
        query.addParameter("title", title);
        query.addParameter("url", url);
        return this.queryForInteger(query);
    }

    /**
     * 删除信息时查找该信息引用的相关信息
     * @param idList
     * @param tableName
     * @return
     */
    public List<Info> findByIdList(List<Integer> idList, String tableName) {
        String sql = "SELECT iid FROM " + Tables.INFO + " WHERE sourceid IN (:idList) AND infotype=:infotype";
        sql = sql.replace("jmp_info", tableName);
        Query query = createQuery(sql);
        query.addParameter("idList", idList);
        query.addParameter("infotype", "Q");
        return this.queryForEntities(query);
    }

    /**
     * 修改信息时查找该信息引用的相关信息
     * @param infoId
     * @param tableName
     * @return
     */
    public List<Info> findByInfoId(int infoId, String tableName) {
        String sql = "SELECT iid, colid, orderid, sourceid, infotype FROM " + Tables.INFO + " WHERE sourceid =:infoId AND infotype=:infotype";
        sql = sql.replace("jmp_info", tableName);
        Query query = createQuery(sql);
        query.addParameter("infoId", infoId);
        query.addParameter("infotype", "Q");
        return this.queryForEntities(query);
    }
	
    /**
     * 通过iid和表名查找信息实体
     * @param iid
     * @param tableName
     * @return
     */
    public Info findByInfoId(Integer iid, String tableName){
    	String sql = this.getEntitySql() + " WHERE iid=:iid";
    	sql = sql.replace(Tables.INFO, tableName);
    	Query query = createQuery(sql);
    	query.addParameter("iid", iid);
    	return this.queryForEntity(query);
    }
}