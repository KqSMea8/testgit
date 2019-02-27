package com.hanweb.jmp.newspush.peoplelist.entity;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.jmp.constant.Tables;


/**
 * 群组实体
 * 
 * @author Wangjw
 * 
 */
@Table(name = Tables.PEOPLELIST)
public class PeopleList implements Serializable {
	

	

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;

	/**
	 * 群组名称
	 */
	@Column(type = ColumnType.VARCHAR)
	private String name;

	/**
	 * 群组备注
	 */
	@Column(type = ColumnType.VARCHAR)
	private String spec;
	
	/**
	 * 群组类型
	 */
	@Column(type = ColumnType.INT)
	private Integer type;
	
	/**
	 * 网站ID，与网站表iid做关联
	 */
	@Column(type = ColumnType.INT)
	private Integer siteId;

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

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}


	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	

}
