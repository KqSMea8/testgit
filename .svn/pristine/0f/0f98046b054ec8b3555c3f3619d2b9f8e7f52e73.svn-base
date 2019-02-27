package com.hanweb.jmp.cms.entity.cols;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 栏目实体
 * @author lgq
 */
@Table(name = Tables.COLQUOTERELATION)
public class ColQuoteRelation implements Serializable {

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
	 * 原始栏目id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer sourceId;

	/**
	 * 引用之后生成的栏目id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer afterId;
	
	/**
     * 站点id
     */
    @Column(type = ColumnType.INT, length = 11)
    private Integer siteId;

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getAfterId() {
        return afterId;
    }

    public void setAfterId(Integer afterId) {
        this.afterId = afterId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getSiteId() {
        return siteId;
    }

}