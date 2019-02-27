package com.hanweb.jmp.cms.entity.function;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 首页布局实体
 * @author hq
 *
 */
@Table(name = Tables.LAYOUT)
public class Layout implements Serializable {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 204643708744575252L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11)
	private Integer iid;

	/**
	 * 排序id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer orderId;

	/**
	 * 频道id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer channelId;

	/**
	 * 类型C:频道(channel),R:普通栏目(category),M:我的桌面(mydesk)
	 */
	@Column(type = ColumnType.INT, length = 2)
	private Integer type;

	/**
	 * 栏目id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer colId;

	/**
	 * 网站id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer siteId;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getColId() {
		return colId;
	}

	public void setColId(Integer colId) {
		this.colId = colId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

}