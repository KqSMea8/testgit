package com.hanweb.jmp.apps.entity.survey;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.SURVEY)
public class Survey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3300518363571988212L;
	
	/**
	 * id
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;
	
	/**
	 * 标题
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String name;
	
	/**
	 * 摘要
	 */
	@Column(type = ColumnType.VARCHAR, length = 900)
	private String abs;
	
	/**
	 * 网站Id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer siteId;
	
	/**
	 * 是否公开
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isOpen;
	
	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date createTime;
	
	/**
	 * 截止时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date endTime;
	
	/**
	 * 首图路径
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String firstPicPath="";
	
	/**
	 * 信息排序ID
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer orderId = 0;
	
	/**
	 * 信息排序ID
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer infoId = 0;
	
	/**
	 * 前台展示类型 1、九宫格  2、列表版
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer showType;
	
	/**
	 * 用户投票次数限制
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer limitCount=1;

	public Integer getShowType() {
		return showType;
	}

	public void setShowType(Integer showType) {
		this.showType = showType;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getFirstPicPath() {
		return firstPicPath;
	}

	public void setFirstPicPath(String firstPicPath) {
		this.firstPicPath = firstPicPath;
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

	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public Integer getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(Integer limitCount) {
		this.limitCount = limitCount;
	}
	
}