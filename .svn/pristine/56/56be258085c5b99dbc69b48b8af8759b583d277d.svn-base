package com.hanweb.jmp.cms.dao.cols;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanweb.common.basedao.BaseJdbcDAO;
import com.hanweb.common.basedao.Query;
import com.hanweb.jmp.cms.entity.cols.ColQuoteRelation;
import com.hanweb.jmp.constant.Tables;

@Repository
public class ColQuoteRelationDAO extends BaseJdbcDAO<Integer, ColQuoteRelation>{

    /**
     * 查找相关的引用栏目
     * @param colid
     * @param siteId
     * @return
     */
    public List<ColQuoteRelation> findByColId(int colid, int siteId) {
        String sql = this.getEntitySql() + " WHERE sourceid=:colid AND siteid=:siteId";
        Query query = createQuery(sql);
        query.addParameter("colid", colid);
        query.addParameter("siteId", siteId);
        return this.queryForEntities(query);
    }

    /**
     * 查找和所删除栏目相关的引用栏目
     * @param idList
     * @param siteId
     * @return
     */
    public List<ColQuoteRelation> findByColIds(List<Integer> idList, int siteId) {
        String sql = this.getEntitySql() + " WHERE sourceid IN (:idList) AND siteid=:siteId";
        Query query = createQuery(sql);
        query.addParameter("idList", idList);
        query.addParameter("siteId", siteId);
        return this.queryForEntities(query);
    }

    /**
     * 删除栏目时，同时删除关联表中数据
     * @param afterId
     * @param siteId
     */
    public void deleteByAfterId(int afterId, int siteId) {
        String sql = " DELETE FROM " + Tables.COLQUOTERELATION + " WHERE afterid=:afterId AND siteid=:siteId";
        Query query = createQuery(sql);
        query.addParameter("afterId", afterId);
        query.addParameter("siteId", siteId);
        this.delete(query);
    }
    
}
