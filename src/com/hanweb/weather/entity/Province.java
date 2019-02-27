package com.hanweb.weather.entity;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.weather.constant.Tables;

@Table(name = Tables.PROVINCE)
public class Province implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8638475416664808015L;

	/**
	 * id
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;
	
	/**
	 * 省份编码
	 */
	@Column(type = ColumnType.VARCHAR)
	private String proCode;
	
	/**
	 * 省份英文名称
	 */
	@Column(type = ColumnType.VARCHAR)
	private String proven;
	
	/**
	 * 省份中文名称
	 */
	@Column(type = ColumnType.VARCHAR)
	private String provcn;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getProven() {
		return proven;
	}

	public void setProven(String proven) {
		this.proven = proven;
	}

	public String getProvcn() {
		return provcn;
	}

	public void setProvcn(String provcn) {
		this.provcn = provcn;
	} 
}

