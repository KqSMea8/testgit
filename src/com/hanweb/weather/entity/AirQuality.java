package com.hanweb.weather.entity;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.weather.constant.Tables;

@Table(name = Tables.AIRQUALITY)
public class AirQuality implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id
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
	 * 地区名称
	 */
	@Column(type = ColumnType.VARCHAR)
	private String name;
	
	/**
	 * AQI
	 */
	@Column(type = ColumnType.VARCHAR)
	private String aqi;
	
	/**
	 * 空气质量描述
	 */
	@Column(type = ColumnType.VARCHAR)
	private String advice;
	
	/**
	 * 细颗粒物
	 */
	@Column(type = ColumnType.VARCHAR)
	private String pm25;
	
	/**
	 * 可吸入颗粒物
	 */
	@Column(type = ColumnType.VARCHAR)
	private String pm10;
	
	/**
	 * 一氧化碳
	 */
	@Column(type = ColumnType.VARCHAR)
	private String co;
	
	/**
	 * 二氧化氮
	 */
	@Column(type = ColumnType.VARCHAR)
	private String no2;
	
	/**
	 * 臭氧1小时平均
	 */
	@Column(type = ColumnType.VARCHAR)
	private String o3;
	
	/**
	 * 二氧化硫
	 */
	@Column(type = ColumnType.VARCHAR)
	private String so2;
	
	/**
	 * 来源
	 */
	@Column(type = ColumnType.VARCHAR)
	private String dataFrom;
	
	/**
	 * 更新时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date updateTime;

	public String getDataFrom() {
		return dataFrom;
	}

	public void setDataFrom(String dataFrom) {
		this.dataFrom = dataFrom;
	}

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	} 

	public String getAqi() {
		return aqi;
	}

	public void setAqi(String aqi) {
		this.aqi = aqi;
	}

	public String getPm25() {
		return pm25;
	}

	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}

	public String getPm10() {
		return pm10;
	}

	public void setPm10(String pm10) {
		this.pm10 = pm10;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getNo2() {
		return no2;
	}

	public void setNo2(String no2) {
		this.no2 = no2;
	}

	public String getO3() {
		return o3;
	}

	public void setO3(String o3) {
		this.o3 = o3;
	}

	public String getSo2() {
		return so2;
	}

	public void setSo2(String so2) {
		this.so2 = so2;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
