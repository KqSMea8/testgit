package com.hanweb.jmp.sys.dao.ditch;

import java.util.List;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.common.basedao.UpdateSql;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.entity.ditch.SynField;

public class SynFieldDAO extends BaseJdbcDAO<Integer, SynField>{

    /**
     * 创建同步字段表
     * @param tableName
     * @return
     */
    public boolean createSynFieldTable(String tableName) {
        return this.createTable(SynField.class, tableName);
    }

    /**
     * 删除同步字段表
     * @param tableName
     * @return
     */
    public boolean dropTableByTableName(String tableName) {
        return this.dropTable(tableName);
    }

    /**
     * 根据iid查找实体
     * @param iid
     * @return
     */
    public SynField findByIid(int iid, int ditchId, int siteId) {
        String tableName = "jmp_synfield" + "_" + siteId + "_" + ditchId;
        String sql = "SELECT * FROM " + Tables.SYNFIELD.replace("jmp_synfield", tableName) + " WHERE iid=:iid";
        Query query = createQuery(sql);
        query.addParameter("iid", iid);
        return this.queryForEntity(query);
    }

    /**
     * 字段排序页面按照orderId排
     * @param siteId
     * @param ditchId
     * @return
     */
    public List<SynField> findSynFieldByOrderId(int siteId, int ditchId) {
        String tableName = "jmp_synfield" + "_" + siteId + "_" + ditchId;
        String sql = "SELECT * FROM " + Tables.SYNFIELD.replace("jmp_synfield", tableName) + " ORDER BY orderid ASC ";
        Query query = createQuery(sql);
        return this.queryForEntities(query);
    }

    /**
     * 查找最大排序id
     * @param siteId
     * @param ditchId
     * @return
     */
    public int findMaxOrderId(int siteId, int ditchId) {
        String tableName = "jmp_synfield" + "_" + siteId + "_" + ditchId;
        String sql = " SELECT MAX(orderid) FROM " + Tables.SYNFIELD.replace("jmp_synfield", tableName);
        Query query = createQuery(sql);
        return this.queryForInteger(query);
    }

    /**
     * 修改排序id
     * @param iid
     * @param orderId
     * @param siteId
     * @param ditchId
     * @return 
     */
    public boolean updateOrderId(int iid, int orderId, Integer siteId, int ditchId) {
        String tableName = "jmp_synfield" + "_" + siteId + "_" + ditchId;
        UpdateSql sql = new UpdateSql(Tables.SYNFIELD.replace("jmp_synfield", tableName));
        sql.addInt("orderid", orderId);
        sql.setWhere("iid=:iid");
        sql.addWhereParamInt("iid", iid);
        return this.update(sql);
    }

    /**
     * 新增、修改判重
     * @param iid
     * @param name
     * @param fieldName
     * @param siteId
     * @param ditchId
     * @return
     */
    public int findRepeatNum(int iid, String name, String fieldName,
            int siteId, int ditchId) {
        String tableName = "jmp_synfield" + "_" + siteId + "_" + ditchId;
        String sql = "SELECT COUNT(1) FROM " + Tables.SYNFIELD.replace("jmp_synfield", tableName) 
        + " WHERE (name=:name OR fieldname=:fieldName)";
        if(iid>0){
            sql += " AND iid <> :iid";
        }
        Query query = createQuery(sql);
        if(iid>0){
            query.addParameter("iid", iid);
        }
        query.addParameter("name", name);
        query.addParameter("fieldName", fieldName);
        return this.queryForInteger(query);
    }

    /**
     * 检查是否存在数据库字段
     * @param fieldName
     * @param siteId
     * @param iid
     * @return
     */
    public int checkSynField(String fieldName, Integer siteId, Integer ditchId) {
        String tableName = "jmp_synfield" + "_" + siteId + "_" + ditchId;
        String sql = "SELECT COUNT(1) FROM " + Tables.SYNFIELD.replace("jmp_synfield", tableName) 
        + " WHERE fieldname=:fieldName"; ;
        Query query = createQuery(sql);
        query.addParameter("fieldName", fieldName);
        return this.queryForInteger(query);
    }

    /**
     * 查找表中所有数据库字段和同步字段名称
     * @param siteId
     * @param ditchId
     * @return
     */
    public List<SynField> findSysn(Integer siteId, Integer ditchId) {
        String tableName = "jmp_synfield" + "_" + siteId + "_" + ditchId;
        String sql = getEntitySql().replace("jmp_synfield", tableName) + " WHERE siteid =:siteid ORDER BY iid ASC";
        Query query = createQuery(sql);
        query.addParameter("siteid", siteId);
        return this.queryForEntities(query);
    }


}