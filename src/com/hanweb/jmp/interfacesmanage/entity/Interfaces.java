package com.hanweb.jmp.interfacesmanage.entity;

import java.io.Serializable;
import java.util.List;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.INTERFACES)
public class Interfaces implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3449482112749510707L;

	/**
	 * 接口表主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;

	/**
	 * 接口名
	 */
	@Column(type = ColumnType.VARCHAR)
	private String name;

	/**
	 * 接口域名
	 */
	@Column(type = ColumnType.VARCHAR)
	private String domain;

	

	/**
	 * 请求方式
	 */
	@Column(type = ColumnType.INT)
	private Integer type;
	
	/**
	 * 接口类型id
	 */
	@Column(type = ColumnType.INT)
	private Integer typeid;
	
	private List<InterfacesParam> params;
	
	
	

	public List<InterfacesParam> getParams() {
		return params;
	}

	public void setParams(List<InterfacesParam> params) {
		this.params = params;
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

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
