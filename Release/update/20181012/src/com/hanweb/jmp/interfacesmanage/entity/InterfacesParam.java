package com.hanweb.jmp.interfacesmanage.entity;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.jmp.constant.Tables;

@Table(name=Tables.INTERFACESPARAM)
public class InterfacesParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 842828506560066795L;

	
	/**
	 * 参数表主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;

	/**
	 * 接口id
	 */
	@Column(type = ColumnType.INT)
	private Integer interfaceid;

	/**
	 * 参数名
	 */
	@Column(type = ColumnType.VARCHAR)
	private String name;

	/**
	 * 是否必须
	 */
	@Column(type = ColumnType.INT)
	private Integer isrequired;

	/**
	 * 参数类型
	 */
	@Column(type = ColumnType.INT)
	private Integer type;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getInterfaceid() {
		return interfaceid;
	}

	public void setInterfaceid(Integer interfaceid) {
		this.interfaceid = interfaceid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsrequired() {
		return isrequired;
	}

	public void setIsrequired(Integer isrequired) {
		this.isrequired = isrequired;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
