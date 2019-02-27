package com.hanweb.jmp.sys.entity.role;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 角色栏目关联表
 * @author ZHUL
 *
 */
@Table(name = Tables.ROLECOL)
public class RoleCol implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7280254704663720920L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;

	/**
	 * 角色id
	 */
	@Column(type = ColumnType.INT)
	private Integer roleId;

	/**
	 * 栏目id
	 */
	@Column(type = ColumnType.INT)
	private Integer colId;
	
	/**
	 * 站点Id
	 */
	@Column(type = ColumnType.INT, length=11)
	private Integer siteId;

	/**
	 * 信息是否支持编辑
	 */
	@Column(type = ColumnType.INT, length=1)
	private Integer isedit=0;
	
	/**
	 * 信息是否支持审核
	 */
	@Column(type = ColumnType.INT, length=1)
	private Integer isaudit=0;
	
	/**
	 * 栏目是否支持管理
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isColManage = 0;
	
	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getColId() {
		return colId;
	}

	public void setColId(Integer colId) {
		this.colId = colId;
	}
	
	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getIsedit() {
		return isedit;
	}

	public void setIsedit(Integer isedit) {
		this.isedit = isedit;
	}

	public Integer getIsaudit() {
		return isaudit;
	}

	public void setIsaudit(Integer isaudit) {
		this.isaudit = isaudit;
	}

    public void setIsColManage(Integer isColManage) {
        this.isColManage = isColManage;
    }

    public Integer getIsColManage() {
        return isColManage;
    }
	
}