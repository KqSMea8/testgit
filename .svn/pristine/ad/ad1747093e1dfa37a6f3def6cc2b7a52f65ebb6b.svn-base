package com.hanweb.jmp.sys.service.ditch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.sys.dao.ditch.SynFieldDAO;
import com.hanweb.jmp.sys.entity.ditch.Ditch;
import com.hanweb.jmp.sys.entity.ditch.SynField;
import com.hanweb.support.controller.CurrentUser;

public class SynFieldService {
    
    /**
     * SynFieldDAO
     */
    @Autowired
    private SynFieldDAO synFieldDAO;
    
    /**
     * DitchService
     */
    @Autowired
    private DitchService ditchService;
    
    /**
     * 创建同步字段表
     * @param tableName
     */
    public boolean createSynFieldTable(String tableName) {
        return synFieldDAO.createSynFieldTable(tableName);
    }

    /**
     * 删除同步字段表
     * @param tableName
     */
    public boolean dropTableByTableName(String tableName) {
        return synFieldDAO.dropTableByTableName(tableName);
    }

    /**
     * 新增字段
     * @param synField
     * @return
     */
    public boolean add(SynField synField, int ditchId) {
        //获取当前用户的siteId
        CurrentUser currentUser = UserSessionInfo.getCurrentUser();
        int siteId = currentUser.getSiteId();
        if(synField==null){
            return false;
        }
        //查找当前最大排序id
        int orderId = synFieldDAO.findMaxOrderId(siteId, ditchId);
        orderId = orderId + 1; 
        synField.setSiteId(siteId);
        synField.setDitchId(ditchId);
        synField.setOrderId(orderId);
        synField.setFieldType(1);
        int num = synFieldDAO.findRepeatNum(NumberUtil.getInt(synField.getIid()), synField.getName(), synField.getFieldName(), siteId, ditchId);
        if(num > 0){
            throw new OperationException("字段名称或数据库字段已存在,请重新设置！");
        }
        int iid = synFieldDAO.insert(synField, "jmp_synfield"+ "_" +siteId + "_" + ditchId);
        return iid > 0;
    }

    /**
     * 删除字段
     * @param ids
     * @return
     */
    public boolean removeByIds(String ids, int ditchId, int siteId) {
        List<Integer> idList = StringUtil.toIntegerList(ids, ",");
        if (CollectionUtils.isEmpty(idList)) {
            return false;
        }
        if(ditchId == 0 || siteId == 0){
            return false;
        }
        String tableName = "jmp_synfield" + "_" + siteId + "_" + ditchId;
        return synFieldDAO.deleteByIds(idList, tableName);
    }

    /**
     * 根据iid查找实体
     * @param iid
     * @return
     */
    public SynField findByIid(int iid, int ditchId) {
        CurrentUser currentUser = UserSessionInfo.getCurrentUser();
        Integer siteId = currentUser.getSiteId();
        if(iid<1 || ditchId<1 || siteId<1){
            return null;
        }
        return synFieldDAO.findByIid(iid, ditchId, siteId);
    }

    /**
     * 修改同步字段
     * @param synField
     * @return
     */
    public boolean modify(SynField synField) {
        if(synField == null){
            return false;
        }
        int num = synFieldDAO.findRepeatNum(NumberUtil.getInt(synField.getIid()), synField.getName(), 
                synField.getFieldName(), synField.getSiteId(), synField.getDitchId());
        if(num > 0){
            throw new OperationException("字段名称或数据库字段已存在,请重新设置！");
        }
        String tableName = "jmp_synfield" + "_" + synField.getSiteId() + "_" + synField.getDitchId();
        return synFieldDAO.update(synField, tableName);
    }

    /**
     * 字段排序页面按照orderId排
     * @param siteId
     * @param ditchId
     * @return
     */
    public List<SynField> findSynFieldByOrderId(int siteId, int ditchId) {
        return synFieldDAO.findSynFieldByOrderId(siteId, ditchId);
    }

    /**
     * 修改排序id
     * @param ids
     * @param orderids
     * @return
     */
    public boolean modifyOrderId(String ids, String orderids, int ditchId) {
        CurrentUser currentUser = UserSessionInfo.getCurrentUser();
        Integer siteId = currentUser.getSiteId();
        if(StringUtil.isEmpty(ids) || StringUtil.isEmpty(orderids) || ditchId<1){
            return false;
        }
        List<Integer> idList = StringUtil.toIntegerList(ids);
        List<Integer> orderList = StringUtil.toIntegerList(orderids);
        if(idList.size() != orderList.size()){
            return false;
        }
        for(int i=0; i<idList.size(); i++){
            int iid = NumberUtil.getInt(idList.get(i));
            int orderId = NumberUtil.getInt(orderList.get(i));
            synFieldDAO.updateOrderId(iid, orderId, siteId, ditchId);
        }
        return true;
    }

    /**
     * 检查是否存在数据库字段
     * @param fieldName
     * @param siteId
     * @return
     */
    public boolean checkSynField(String fieldName, Integer siteId) {
        //查找所有渠道id
        List<Ditch> ditchList = ditchService.findBySiteId(siteId);
        if(CollectionUtils.isNotEmpty(ditchList)){
            for(Ditch ditch : ditchList){
               int n = synFieldDAO.checkSynField(fieldName, siteId, ditch.getIid());
               if(n > 0){
                   throw new OperationException("在渠道管理中存在相同字段,请先删除该字段！");
               }
            }
        }
        return true;
    }

    /**
     * 将字段名称和数据库字段放入map集合中
     * @param siteId
     * @param ditchId
     * @return
     */
    public Map<String, String> findSynField(Integer siteId, Integer ditchId) {
        Map<String, String> map = new HashMap<String, String>();
        List<SynField> synFieldlist = synFieldDAO.findSysn(siteId, ditchId);
        if(CollectionUtils.isNotEmpty(synFieldlist)){
            for(SynField synField : synFieldlist){
                map.put(synField.getName(), synField.getFieldName());
            }
        } 
        return map;
    }

}