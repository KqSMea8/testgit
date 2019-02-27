package com.hanweb.jmp.newspush.peoplelist.entity;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.jmp.constant.Tables;


/**
 * 角色关系实体
 * 
 */
@Table(name = Tables.PEOPLELISTRELATION)
public class PeoplelistRelation implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7618392606713008215L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;

	/**
	 * 群组id
	 */
	@Column(type = ColumnType.INT)
	private Integer peoplelistId;

	/**
	 * 用户id
	 */
	@Column(type = ColumnType.INT)
	private Integer userId;

	/**
	 * 机构id
	 */
	@Column(type = ColumnType.INT)
	private Integer groupId;

	/**
	 * 站点id
	 */
	@Column(type = ColumnType.INT)
	private Integer siteId;
	
	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}


	public Integer getPeoplelistId() {
		return peoplelistId;
	}

	public void setPeoplelistId(Integer peoplelistId) {
		this.peoplelistId = peoplelistId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getSiteId() {
		return siteId;
	}

}
