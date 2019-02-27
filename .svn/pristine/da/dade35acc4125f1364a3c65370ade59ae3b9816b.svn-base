package com.hanweb.jmp.sys.entity.ditch;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.SYNFIELD)
public class SynField implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键
     */
    @Id
    @Column(type = ColumnType.INT, length = 11)
    private Integer iid;
    
    /**
     * 同步字段名
     */
    @Column(type = ColumnType.VARCHAR, length = 255)
    private String name;
    
    /**
     * 数据库字段
     */
    @Column(type = ColumnType.VARCHAR, length = 255)
    private String fieldName;
    
    /**
     * 渠道id
     */
    @Column(type = ColumnType.INT, length = 11)
    private Integer ditchId;
    
    /**
     * 站点id
     * @return
     */
    @Column(type = ColumnType.INT, length = 11)
    private Integer siteId;
    
    /**
     * 排序id
     * @return
     */
    @Column(type = ColumnType.INT, length = 11)
    private Integer orderId;
    
    /**
     * 字段类型   0：原生的   1：新增的
     * @return
     */
    @Column(type = ColumnType.INT, length = 11)
    private Integer fieldType;

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getDitchId() {
        return ditchId;
    }

    public void setDitchId(Integer ditchId) {
        this.ditchId = ditchId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getFieldType() {
        return fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }
    
}