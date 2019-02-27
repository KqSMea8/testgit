package com.hanweb.jmp.sys.entity.role;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 角色推送分类关联表
 * @author ZHUL
 */
@Table(name = Tables.ROLEPUSHTYPE)
public class RolePushType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5883852102601570292L;

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
	 * 推送分类id
	 */
	@Column(type = ColumnType.INT)
	private Integer pushTypeId;

	/**
	 * 站点Id
	 */
	@Column(type = ColumnType.INT, length=11)
	private Integer siteId;
	
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

	public Integer getPushTypeId() {
		return pushTypeId;
	}

	public void setPushTypeId(Integer pushTypeId) {
		this.pushTypeId = pushTypeId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	} 
	
}