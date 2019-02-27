package com.hanweb.jmp.apps.dao.manage;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query; 
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.jmp.apps.entity.manage.LightApp;
import com.hanweb.jmp.constant.Tables;

public class LightAppDAO extends BaseJdbcDAO<Integer, LightApp>{
	
	/**
	 * 根据主键id查找实体
	 * @param iid 主键id
	 * @return LightApp
	 */
	public LightApp findByIid(int iid){
		String sql = getEntitySql() + " WHERE iid=:iid";
		Query query = createQuery(sql);
		query.addParameter("iid", iid);
		return this.queryForEntity(query);
	}
	
	/**
	 * 根据网站id查找实体
	 * @param siteId
	 * @param isOpen
	 * @return
	 */
	public List<LightApp> findBySiteId(int siteId, int isOpen){
		String sql = getEntitySql() + " WHERE siteid=:siteid ";
        if(isOpen>-1){
        	sql += " AND isopen=:isOpen";
        }
        sql += " ORDER BY orderid DESC";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		if(isOpen>-1){
			query.addParameter("isOpen", isOpen);
		} 
		return this.queryForEntities(query);
	} 
	
	/**
	 * 根据name查找数量
	 * @param iid
	 * @param name
	 * @param siteId
	 * @return
	 */
	public int findNumByName(int iid, String name, int siteId, int lightType) {
		String sql = "SELECT COUNT(1) FROM " + Tables.LIGHTAPP + " WHERE "
			+ " siteid=:siteid AND name=:name AND lighttype=:lighttype";
		if(iid>0){
			sql += " AND iid <> :iid";
		}
		Query query = createQuery(sql);
		query.addParameter("name", name);
		if(iid>0){
			query.addParameter("iid", iid);
		}
		query.addParameter("siteid", siteId);
		query.addParameter("lighttype", lightType);
		Integer num = this.queryForInteger(query);
		return NumberUtil.getInt(num);
	}
	
	/**
	 * 根据网站id查找最小排序id
	 * @param siteId
	 * @return
	 */
	public int findMinOrderIdBySiteId(int siteId){
		String sql = " SELECT MIN(orderid) FROM " + Tables.LIGHTAPP
			       + " WHERE siteid=:siteid ";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		return this.queryForInteger(query);
	}
	
	/**
	 * 根据iid修改orderid
	 * @param iid int
	 * @param orderId int
	 * @return boolean
	 */
	public boolean updateOrderIdById(int iid, int orderId){
		UpdateSql sql = new UpdateSql(Tables.LIGHTAPP);
		sql.addInt("orderid", orderId);
		sql.setWhere("iid = :iid");
		sql.addWhereParamInt("iid", iid);
		return this.update(sql);
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
		UpdateSql sql = new UpdateSql(Tables.LIGHTAPP);
		sql.addInt("isopen", enable);
		sql.setWhere("iid IN (:ids)");
		sql.addWhereParamList("ids", ids);
		return super.update(sql);
	}

	/**
	 * 根据name查找实体
	 * @param name
	 * @param siteid
	 * @return
	 */
	public LightApp findByName(String name,Integer siteid) {
		String sql = getEntitySql() + " WHERE name=:name AND siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("name", name);
		query.addParameter("siteid", siteid);
		return this.queryForEntity(query);
	}
	/**
	 * 根据id查找实体
	 * @param id
	 * @param siteid
	 * @return
	 */
	public LightApp findById(Integer id,Integer siteid) {
		String sql = getEntitySql() + " WHERE iid=:id AND siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("id", id);
		query.addParameter("siteid", siteid);
		return this.queryForEntity(query);
	}

	/**
	 * 查找分类下应用数量
	 * @param ids
	 * @return
	 */
    public int findCountByTypeId(List<Integer> ids) {
        String sql = "SELECT COUNT(iid) FROM " + Tables.LIGHTAPP + " WHERE lighttype IN (:ids)";
        Query query = createQuery(sql);
        query.addParameter("ids", ids);
        int num = this.queryForInteger(query);
        return num;
    }

    /**
     * 根据站点id和pid查找实体
     * @param siteId
     * @param pid
     * @return
     */
    public List<LightApp> findByPid(int siteId, String pid) {
        String sql = getEntitySql() + " WHERE siteid=:siteid AND lighttype=:pid ORDER BY orderid ASC";
        Query query = createQuery(sql);
        query.addParameter("siteid", siteId);
        query.addParameter("pid", pid);
        return this.queryForEntities(query);
    }
    /**
	 * 获得所有轻应用
	 */
	public List<LightApp> findAllLightApp(int siteId){
		String sql = getEntitySql() + " WHERE siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("siteid", siteId);
		List<LightApp> list =  this.queryForEntities(query);
		return list;		
	}
	
	/**
	 * 根据lightType查找应用
	 * 
	 */
	public boolean findAppByLightType(int lightType){
		String sql = "SELECT COUNT(iid) FROM " + Tables.LIGHTAPP + " WHERE lighttype=:lighttype";
		Query query = createQuery(sql);
		//query.addParameter("name", name);
		query.addParameter("lighttype", lightType);
		return this.queryForInteger(query)>0;
		
	}
	
	/**
	 * 根据name查找应用
	 * 
	 */
	public boolean findAppByName(String name){
		String sql = "SELECT COUNT(1) FROM " + Tables.LIGHTAPP + " WHERE name=:name";
		Query query = createQuery(sql);
		query.addParameter("name", name);
		return this.queryForInteger(query)>0;
		
	}
	
	/**
	 * 根据groupName查找应用
	 */
	public boolean findAppByGroupName(String groupName){
		String sql = "SELECT COUNT(1) FROM " + Tables.LIGHTAPP + " WHERE groupname=:groupname";
		Query query = createQuery(sql);		
		query.addParameter("groupname", groupName);
		return this.queryForInteger(query)>0;
		
	}
	
	/**
	 * 根据keyValue查找应用
	 * 
	 */
	public boolean findAppByKeyValue(String keyValue, int siteId){
		String sql = "SELECT COUNT(1) FROM " + Tables.LIGHTAPP + " WHERE keyvalue=:keyvalue AND siteid=:siteid";
		Query query = createQuery(sql);
		query.addParameter("keyvalue", keyValue);
		query.addParameter("siteid", siteId);
		return this.queryForInteger(query)>0;
		
	}
	
	public boolean updateApp(String name, String appIssueUrl, Integer lightType, String lightTypeName, String groupName, String keyValue) {
		UpdateSql sql = new UpdateSql(Tables.LIGHTAPP);
		
		sql.addString("name", name);
		sql.addString("url", appIssueUrl);
		sql.addInt("lighttype", lightType);
		sql.addString("lighttypename", lightTypeName);
		sql.addString("groupname", groupName);
		sql.setWhere("keyvalue = :keyvalue");
		sql.addWhereParamString("keyvalue", keyValue);
		return super.update(sql);
	}
	
	/**
	 * 根据name获取iid
	 * @param name
	 * @return
	 */
	public int getIidByName(String name){
		String sql = "SELECT iid FROM " + Tables.LIGHTAPP + " WHERE "
				+ " name=:name";
		Query query = createQuery(sql);
		query.addParameter("name", name);
		return this.queryForInteger(query);
	}
	
	/**
	 * 通过keyValue删除应用
	 */
	public boolean deleteBykeyValue(String keyValue) {
		String sql = "DELETE FROM " + Tables.LIGHTAPP + " WHERE keyvalue=:keyvalue";
		Query query = createQuery(sql);
		query.addParameter("keyvalue", keyValue);
		return this.delete(query);
	}

	/**
	 * 查找站点下默认轻应用
	 * @param siteId
	 * @return
	 */
    public List<LightApp> findDefaultAppBySiteId(int siteId) {
        String sql = getEntitySql() + " WHERE siteid=:siteId AND isdefault=1";
        Query query = createQuery(sql);
        query.addParameter("siteId", siteId);
        return this.queryForEntities(query);
    }
    
    /**
     * 通过ids查找LightApp实体并且按照in内容排序
     * @param idList
     * @return
     */
    public List<LightApp> findByAppIds(String lightAppIds){
    	int dbType = BaseInfo.getDbType();
    	String sql = "";
    	if(dbType == 1){//oracle数据库
			if(StringUtil.isNotEmpty(lightAppIds)){
				List<Integer> idList = StringUtil.toIntegerList(lightAppIds, ",");
				String[] Ids = lightAppIds.split(",");
				StringBuffer sb = new StringBuffer();
				for(int i=1;i<=Ids.length;i++){
					sb.append(Ids[i-1]);
					sb.append(",");
					sb.append(i+",");
				}
				String ss = sb.substring(0, sb.length()-1);
				List<Integer> idsList = StringUtil.toIntegerList(ss, ",");
				sql = this.getEntitySql() + " WHERE iid IN (:idList) ORDER  BY DECODE(iid,:idsList)";
				Query query = createQuery(sql);
				query.addParameter("idList", idList);
				query.addParameter("idsList", idsList);
				return this.queryForEntities(query);
			}
		}else{//FIELD目前只是在mysql中测试过，如后期增加适配数据库，需自行添加方法
			if(StringUtil.isNotEmpty(lightAppIds)){
				List<Integer> idList = StringUtil.toIntegerList(lightAppIds, ",");
				sql = this.getEntitySql() + " WHERE iid IN (:idList) ORDER  BY FIELD(iid,:idList)";
				Query query = createQuery(sql);
				query.addParameter("idList", idList);
				return this.queryForEntities(query);
			}
		}
    	return null;
    }
    
    /**
     * 通过ids查找应用
     * @param ids
     * @return
     */
    public List<LightApp> findByIdList(List<Integer> ids){
    	String sql = this.getEntitySql() + " WHERE iid IN (:ids)";
    	Query query = createQuery(sql);
    	query.addParameter("ids", ids);
    	return this.queryForEntities(query);
    }
    
    
    /**
     * 生成二维码修改
     * @param qrCodeAddress
     * @param iid
     * @return
     */
    public  boolean updataAppQrCodeAddress(String qrCodeAddress,Integer iid){
    	UpdateSql sql = new UpdateSql(Tables.LIGHTAPP);
    	sql.addString("qrCodeAddress", qrCodeAddress);
    	sql.setWhere("iid=:iid");
    	sql.addWhereParam("iid", iid);
    	return super.update(sql);
    }
    
}