package com.hanweb.weather.entity;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.weather.constant.Tables;

@Table(name = Tables.AREA)
public class Area implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3048117643024028550L;
	/**
	 * IId
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;
	
	/**
	 * 地区编码
	 */
	@Column(type = ColumnType.VARCHAR)
	private String areaCode;
	/**
	 * 城市英文名称
	 */
	@Column(type = ColumnType.VARCHAR)
	private String nameEn;
	/**
	 * 城市中文名称
	 */
	@Column(type = ColumnType.VARCHAR)
	private String nameCn;
	
	/**
	 * 首字母
	 */
	@Column(type = ColumnType.CHAR, length = 1)
	private String firstLetter;
	
	/**
	 * 首字母拼音缩略
	 */
	@Column(type = ColumnType.VARCHAR)
	private String acronym;
	/**
	 * 启用状态  1：启用  0：停用
	 */
	@Column(type = ColumnType.INT, length=1)
	private Integer state;
	
	/**
	 * 省份编码
	 */
	@Column(type = ColumnType.VARCHAR)
	private String parcode;
	
	/**
	 * 城市编码
	 */
	@Column(type = ColumnType.VARCHAR)
	private String citycode;
	
	public Integer getIid() {
		return iid;
	}
	public void setIid(Integer iid) {
		this.iid = iid;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public String getNameCn() {
		return nameCn;
	}
	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getFirstLetter() {
		return firstLetter;
	}
	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}
	public String getAcronym() {
		return acronym;
	}
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getParcode() {
		return parcode;
	}
	public void setParcode(String parcode) {
		this.parcode = parcode;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	} 
	
}
