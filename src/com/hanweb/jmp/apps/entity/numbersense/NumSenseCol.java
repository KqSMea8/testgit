package com.hanweb.jmp.apps.entity.numbersense;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.OnlyQuery;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.NUMSENSECOL)
public class NumSenseCol implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8727915561282603806L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;
	
	/**
	 * 分类名称
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String name;

	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createTime;
	
	/**
	 * 网站ID
	 */
	@Column(type = ColumnType.INT)
	private Integer siteId;
	
	/**
	 * 类型 1:虚拟类型   2：普通类型
	 */
	@Column(type = ColumnType.INT)
	private Integer type;
	
	/**
	 * 父ID
	 */
	@Column(type = ColumnType.INT)
	private Integer pid;
	
	/**
	 * 排序ID
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer orderId;
	
	/**
	 * 分类图标路径
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String iconPath;
	
	/**
	 * 是否支持检索 0:支持   1:不支持
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer issearch;

	/**
	 * 是否为父节点
	 */
	@OnlyQuery
	private boolean isparent;
	
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public boolean isIsparent() {
		return isparent;
	}

	public void setIsparent(boolean isparent) {
		this.isparent = isparent;
	}

	public Integer getIssearch() {
		return issearch;
	}

	public void setIssearch(Integer issearch) {
		this.issearch = issearch;
	}
	
}