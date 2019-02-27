package com.hanweb.jmp.cms.entity.sign;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 角标实体
 * @author qzq
 */
@Table(name = Tables.DIMENSION)
public class Sign implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2476085732796873228L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11)
	private Integer iid;
	
	/**
	 * 网站id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer siteId;
	
	/**
	 * 角标名称（例如：新闻、资讯、娱乐等）
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String dname;
	
	/**
	 * 排序id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer orderId;
	
	/**
	 * 功能模块唯一标识
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer mid;
	
	/**
	 * 功能模块名称（例如：微信卡片、报料、推送等）
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String mname;
	
	/**
	 * 订阅角标颜色
	 */
	@Column(type = ColumnType.VARCHAR, length = 30)
	private String color = "#008fd5";
	
	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createTime;
	
	/**
	 * 卡片式角标展现信息条数    0=块显示4条及以下 1= 块显示5条 2= 块显示6条及以上
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer cardType = 0;
	
	/**
	 * 卡片角标所属栏目ID
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer colId;
	
	/**
	 * 卡片式角标展现样式		0= 名称+时间	  1=时间		2=名称		3=无
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer showType = 0;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public Integer getColId() {
		return colId;
	}

	public void setColId(Integer colId) {
		this.colId = colId;
	}

	public Integer getShowType() {
		return showType;
	}

	public void setShowType(Integer showType) {
		this.showType = showType;
	}
	
}