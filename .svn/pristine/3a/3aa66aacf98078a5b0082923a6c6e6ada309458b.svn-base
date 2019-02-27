package com.hanweb.jmp.appstype.entity;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.jmp.appstype.constant.Tables;

/**
 * 轻应用分组管理
 * @author ZCC
 *
 */
@Table(name = Tables.LIGHTAPPTYPE)
public class LightAppType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7322461226272147323L;
	
	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;
	
	/**
	 * 站点id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer siteId;
	
	/**
	 * 应用分类名称
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String name;
	
	/**
	 * 应用分类备注
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String remark;
	
	/**
	 * 排序id
	 */
	@Column(type = ColumnType.INT)
	private Integer orderId;
	
	/**
	 * 父分类id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer pid;

	/**
	 * 父分类名称
	 */
	private String pname;
	
	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createTime;

	/**
	 * 是否有下属分类
	 */
	private Boolean isParent = false;
	
	/**
	 * 原始父id
	 */
	private Integer prevPid;
	
	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public Integer getPrevPid() {
		return prevPid;
	}

	public void setPrevPid(Integer prevPid) {
		this.prevPid = prevPid;
	}
	
}