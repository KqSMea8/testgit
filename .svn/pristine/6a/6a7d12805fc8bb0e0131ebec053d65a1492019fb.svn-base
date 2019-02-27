package com.hanweb.jmp.cms.entity.matters.video;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.VIDEOCOL)
public class VideoCol implements Serializable{

	private static final long serialVersionUID = -2733965944232594341L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;
	
	/**
	 * 分类名称
	 */
	@Column(type = ColumnType.VARCHAR, length = 50)
	private String name;
	
	/**
	 * 父ID
	 */
	@Column(type = ColumnType.INT)
	private Integer pid;
	
	/**
	 * 网站id
	 */
	@Column(type = ColumnType.INT)
	private Integer siteId;
	
	/**
	 * 简介
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String spec;
	
	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createTime;

	/**
	 * 排序ID
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer orderId;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
}