package com.hanweb.jmp.cms.dao.matters.picture;

import java.util.Date;
import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.NumberUtil;

import com.hanweb.jmp.cms.entity.matters.picture.Picture;
import com.hanweb.jmp.constant.Tables;

public class PictureDAO extends BaseJdbcDAO<Integer,Picture>{
	
	/**
	 * 更新图片
	 * @param id Integer
	 * @return boolean
	 */
	public boolean updatePictureInfo(Integer iid) {
		UpdateSql sql = new UpdateSql(Tables.PICTURE);
		sql.setWhere("iid=:iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
	}
	
	/**
	 * 根据站点id取得该站点下图片
	 * @param siteid
	 * @return
	 */
	public List<Picture> findPicture(Integer siteid) {
		String sql = this.getEntitySql() + " WHERE siteid=:siteid ORDER BY  iid DESC";
		Query query = createQuery(sql);
		query.addParameter("siteid", NumberUtil.getInt(siteid));
		return this.queryForEntities(query);
	}
	
	/**
	 * 删除图片
	 * @param id
	 * @return
	 */
	public boolean deletePicture(Integer iid) {
		String sql = "DELETE FROM " + Tables.PICTURE + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		return delete(query);
	}
	
	/**
	 * 根据iid查找图片
	 * @param idsLsit
	 * @return
	 */
	public List<Picture> findByIds(List<Integer> idsList) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.iid, a.siteid, a.classid FROM " + Tables.PICTURE +" a," + Tables.PICTURECOL + " b")
		   .append(" WHERE a.iid IN (:idsLsit) AND b.iid = a.classid ORDER BY  a.iid DESC" );
		Query query = createQuery(sql.toString());
		query.addParameter("idsLsit", idsList);
		List<Picture> List = queryForEntities(query);
		return List;
	}
	
	/**
	 * 判断是否重复
	 * @param iid
	 * @param name
	 * @param siteId
	 * @param pid
	 * @return
	 */
	public int findNumOfSameName(Integer iid, String name, Integer siteId,Integer classId) {
		int num = 0;
		String sql = " SELECT COUNT(iid) FROM " + Tables.PICTURE + " WHERE name=:name ";
		if(NumberUtil.getInt(classId) > 0){
			sql += " AND classid=:classId ";
		}
		if (NumberUtil.getInt(iid) > 0) {
			sql += " AND iid NOT IN(:iid)";
		}
		if (NumberUtil.getInt(siteId) > 0) {
			sql += " AND siteid=:siteId";
		}
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		query.addParameter("name", name);
		query.addParameter("siteId", siteId);
		query.addParameter("classId", classId);
		num = this.queryForInteger(query);
		return num;
	}
	
	/**
	 * findByIid
	 * @param iid
	 * @return
	 */
	public Picture findByIid(int iid) {
		String sql = getEntitySql() + " WHERE iid=:iid ";
		Query query = createQuery(sql);
		query.addParameter("iid", iid); 
		Picture picture = this.queryForEntity(query); 
		return picture;
	}
	
	/**
	 * 改变删除状态
	 * @param isremove
	 * @param ids
	 * @return
	 */
	public boolean updateIsRemove(int isremove, List<Integer> ids) {
		String sql = " UPDATE " + Tables.PICTURE + " SET  removetime=:removetime, isremove=:isremove"
                   + " WHERE iid IN (:ids)";
		Query query = createQuery(sql);
		query.addParameter("ids", ids);
		query.addParameter("isremove", isremove);
		query.addParameter("removetime", new Date());
		return this.execute(query) > 0; 
	}
	
	/**
	 * 删除回收站的所有图片
	 * @param isremove
	 * @param siteId
	 * @return
	 */
	public boolean deleteAllByIsremove(int isremove, Integer siteId,String classId) {
		String sql = "DELETE FROM " + Tables.PICTURE + " WHERE siteid=:siteId AND classid=:classId AND isremove=:isremove";
		Query query = createQuery(sql);
		query.addParameter("siteId", siteId);
		query.addParameter("classId", classId);
		query.addParameter("isremove", isremove);
		return delete(query);
	}
	
	/**
	 * 根据网站ID，分类ID和是否删除查找图片的iid
	 * @param siteId
	 * @param classId
	 * @param isremove
	 * @return
	 */
	public String [] findBySiteId(Integer siteId,String classId,Integer isRemove) {
		String sql = "SELECT iid FROM " + Tables.PICTURE + " WHERE siteid=:siteId AND classid=:classId AND isremove=:isRemove ";
		Query query = createQuery(sql);
		query.addParameter("siteId", siteId);
		query.addParameter("classId", classId);
		query.addParameter("isRemove", isRemove);
		String [][] arr = this.queryForArrays(query);
		int a = arr.length;
		String [] str = new String [a];
		for(int i = 0;i<arr.length;i++){
			str[i] = arr[i][0];
		}
		return str;
	}

	/**
	 * 修改分类名称时修改实体表中pname
	 * @param siteId
	 * @param iid
	 * @param name
	 * @return
	 */
	public boolean modifyPName(Integer siteId, Integer iid, String name) {
		String sql = " UPDATE " + Tables.PICTURE + " SET pname=:name WHERE siteid=:siteId AND classid=:iid";
		Query query = createQuery(sql);
		query.addParameter("name", name);
		query.addParameter("siteId", siteId);
		query.addParameter("iid", iid);
		return this.execute(query) > 0; 
	}

}