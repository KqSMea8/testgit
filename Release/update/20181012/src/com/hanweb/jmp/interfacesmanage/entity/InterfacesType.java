package com.hanweb.jmp.interfacesmanage.entity;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.OnlyQuery;
import com.hanweb.common.annotation.Table;
import com.hanweb.jmp.constant.Tables;


/**
 * 接口类型表
 * @author Administrator
 *
 */
@Table(name=Tables.INTERFACESTYPE)
public class InterfacesType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6494186582851603131L;

	
	/**
	 * 接口类型主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;

	/**
	 * 接口类型名称
	 */
	@Column(type = ColumnType.VARCHAR)
	private String name;

	/**
	 * 父类型id
	 */
	@Column(type = ColumnType.VARCHAR)
	private Integer pid;

	/**
	 * 父类型名称
	 */
	@OnlyQuery
	private String pname;

	/**
	 * 是否有下属类型
	 */
	private Boolean isParent = false;
	
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	
	
	
}
