package com.hanweb.jmp.sys.entity.log;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.PUSH_INFO_LOG)
public class PushInfoLog implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2734802823458098159L;
	
	/**
	 * 主键id
	 */
	@Id
	@Column(type = ColumnType.INT)
	public Integer iid;
	
	/**
	 * 信息ID
	 */
	@Column(type = ColumnType.INT)
	public Integer infoId;
	
	/**
	 * 用户ID
	 */
	@Column(type = ColumnType.INT)
	public Integer userId;
	
	/**
	 * 网站ID
	 */
	@Column(type = ColumnType.INT)
	public Integer siteId;
	
	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update=false)
	public Date createTime;
	
	/**
	 * 创建月份
	 */
	@Column(type = ColumnType.INT, update=false)
	public Integer month;
	
	/**
	 * 创建时段（小时）
	 */
	@Column(type = ColumnType.INT, update=false)
	public Integer hour;
	
	/**
	 * 安卓推送 状态
	 */
	@Column(type = ColumnType.INT)
	public Integer androidStatus;
	
	/**
	 * iphone推送状态
	 */
	@Column(type = ColumnType.INT)
	public Integer iosStatus;
	
	/**
	 * ipad推送状态
	 */
	@Column(type = ColumnType.INT)
	public Integer ipadStatus;
	
	public Integer getIid() {
		return iid;
	}
	
	public void setIid(Integer iid) {
		this.iid = iid;
	}
	
	public Integer getInfoId() {
		return infoId;
	}
	
	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Integer getSiteId() {
		return siteId;
	}
	
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	
	public Integer getMonth() {
		return month;
	}
	
	public void setMonth(Integer month) {
		this.month = month;
	}
	
	public Integer getHour() {
		return hour;
	}
	
	public void setHour(Integer hour) {
		this.hour = hour;
	}
	
	public Integer getAndroidStatus() {
		return androidStatus;
	}
	
	public void setAndroidStatus(Integer androidStatus) {
		this.androidStatus = androidStatus;
	}
	
	public Integer getIosStatus() {
		return iosStatus;
	}
	
	public void setIosStatus(Integer iosStatus) {
		this.iosStatus = iosStatus;
	}
	
	public Integer getIpadStatus() {
		return ipadStatus;
	}
	
	public void setIpadStatus(Integer ipadStatus) {
		this.ipadStatus = ipadStatus;
	}
	
}