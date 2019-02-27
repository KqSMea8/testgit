package com.hanweb.weather.entity;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Index;
import com.hanweb.common.annotation.Indexes;
import com.hanweb.common.annotation.Table;
import com.hanweb.weather.constant.Tables;

@Table(name = Tables.WEATHER)
@Indexes(value={
		@Index(indexName="weather_idx_001", fieldNames={"areaid"}),
		@Index(indexName="weather_idx_002", fieldNames={"createtime"})
	})
public class Weather implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3672678695348148606L;
	/**
	 * IID
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;
	/**
	 * 地区
	 */
	@Column(type = ColumnType.INT)
	private Integer areaId;
	/**
	 * 时间 只用于接收网络数据
	 */
	private String date;
	/**
	 * 时间
	 */
	@Column(type = ColumnType.VARCHAR)
	private String createTime;
	/**
	 * 白天图片地址
	 */
	@Column(type = ColumnType.VARCHAR)
	private String dayPictureUrl;
	/**
	 * 夜晚图片地址
	 */
	@Column(type = ColumnType.VARCHAR)
	private String nightPictureUrl;
	/**
	 * 天气
	 */
	@Column(type = ColumnType.VARCHAR)
	private String weather;
	/**
	 * 风力
	 */
	@Column(type = ColumnType.VARCHAR)
	private String wind;
	/**
	 * 温度
	 */
	@Column(type = ColumnType.VARCHAR)
	private String temperature;
	/**
	 * PM2.5
	 */
	@Column(type = ColumnType.VARCHAR)
	private String pm25 = "";
	/**
	 * 实时温度
	 */
	@Column(type = ColumnType.VARCHAR)
	private String currentTemperature;
	
	/**
	 * 更新
	 */
	@Column(type = ColumnType.VARCHAR)
	private String updateTime;
	
	/**
	 * 周
	 */
	@Column(type = ColumnType.VARCHAR)
	private String weekday;
	
	public Integer getIid() {
		return iid;
	}
	
	public void setIid(Integer iid) {
		this.iid = iid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDayPictureUrl() {
		return dayPictureUrl;
	}
	public void setDayPictureUrl(String dayPictureUrl) {
		this.dayPictureUrl = dayPictureUrl;
	}
	public String getNightPictureUrl() {
		return nightPictureUrl;
	}
	public void setNightPictureUrl(String nightPictureUrl) {
		this.nightPictureUrl = nightPictureUrl;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	} 

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPm25() {
		return pm25;
	}

	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}

	public String getCurrentTemperature() {
		return currentTemperature;
	}

	public void setCurrentTemperature(String currentTemperature) {
		this.currentTemperature = currentTemperature;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getWeekday() {
		return weekday;
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	} 
}
