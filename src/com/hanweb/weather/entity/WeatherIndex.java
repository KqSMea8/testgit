package com.hanweb.weather.entity;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Index;
import com.hanweb.common.annotation.Indexes;
import com.hanweb.common.annotation.Table;
import com.hanweb.weather.constant.Tables;

@Table(name = Tables.WEATHERINDEX)
@Indexes(value={
		@Index(indexName="weaindex_idx_001", fieldNames={"areaid"}),
		@Index(indexName="weaindex_idx_002", fieldNames={"createtime"})
	})
public class WeatherIndex implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2269685316286985604L;
	/**
	 * IID
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;
	/**
	 * 地区ID
	 */
	@Column(type = ColumnType.INT)
	private Integer areaId;
	/**
	 * 指数名称
	 */
	@Column(type = ColumnType.VARCHAR)
	private String title;
	/**
	 * 指数
	 */
	@Column(type = ColumnType.VARCHAR)
	private String zs;
	/**
	 * 提示
	 */
	@Column(type = ColumnType.VARCHAR)
	private String tipt;
	/**
	 * 描述
	 */
	@Column(type = ColumnType.VARCHAR)
	private String des;
	/**
	 * 时间
	 */
	@Column(type = ColumnType.VARCHAR)
	private String createTime;
	
	/**
	 * 更新
	 */
	@Column(type = ColumnType.VARCHAR)
	private String updateTime;
	
	public Integer getIid() {
		return iid;
	}
	public void setIid(Integer iid) {
		this.iid = iid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getZs() {
		return zs;
	}
	public void setZs(String zs) {
		this.zs = zs;
	}
	public String getTipt() {
		return tipt;
	}
	public void setTipt(String tipt) {
		this.tipt = tipt;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
 
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	} 
}
