package com.hanweb.jmp.apps.entity.broke;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.BROKETYPE)
public class BrokeType implements Serializable {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -3426650929746958975L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;

	/**
	 * 站点id
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer siteId = 0;

	/**
	 * 父节点id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer pid = 0;

	/**
	 * 排序id
	 */
	@Column(type = ColumnType.INT, length = 3)
	private Integer orderId = 0;

	/**
	 * 分类名字
	 */
	@Column(type = ColumnType.VARCHAR, length = 50)
	private String name = "";

	/**
	 * 审核状态0：手动 1：自动
	 */
	@Column(type = ColumnType.INT, length = 2)
	private Integer auditType = 1;

	/**
	 * 描述
	 */
	@Column(type = ColumnType.VARCHAR)
	private String spec = "";
	 
	/**
	 * createTime:创建时间.
	 * @since zx Ver 1.0 .
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createTime;
	
	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getAuditType() {
		return auditType;
	}

	public void setAuditType(Integer auditType) {
		this.auditType = auditType;
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
	
	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}
	
}