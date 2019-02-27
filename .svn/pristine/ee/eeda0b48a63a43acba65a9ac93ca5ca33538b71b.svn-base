package com.hanweb.jmp.cms.service.cols;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanweb.jmp.cms.dao.cols.ColQuoteRelationDAO;
import com.hanweb.jmp.cms.entity.cols.ColQuoteRelation;

@Service
public class ColQuoteRelationService {

    /**
     * colQuoteRelDAO
     */
    @Autowired
    private ColQuoteRelationDAO colQuoteRelDAO;
    
    /**
     * 查找相关的引用栏目
     * @param colid
     * @param siteId
     * @return
     */
    public List<ColQuoteRelation> findByColId(int colid, int siteId) {
        if(colid<=0 || siteId<=0){
            return null;
        }
        return colQuoteRelDAO.findByColId(colid, siteId);
    }

}
