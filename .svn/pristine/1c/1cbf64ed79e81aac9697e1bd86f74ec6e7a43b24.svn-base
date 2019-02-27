package com.hanweb.jmp.cms.entity.channels;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 频道栏目对应表
 * @author hq
 */
@Table(name = Tables.CHANNEL_COLUMN)
public class ChannelCol implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3022410020172365157L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;

	/**
	 * 频道id
	 */
	@Column(type = ColumnType.INT)
	private Integer channelId;
	
	/**
	 * 栏目id
	 */
	@Column(type = ColumnType.INT)
	private Integer colId;
	
	/**
	 * 排序id
	 */
	@Column(type = ColumnType.INT)
	private Integer orderId;
	
	/**
	 * 网站id
	 */
	@Column(type = ColumnType.INT)
	private Integer siteId;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getColId() {
		return colId;
	}

	public void setColId(Integer colId) {
		this.colId = colId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	
}