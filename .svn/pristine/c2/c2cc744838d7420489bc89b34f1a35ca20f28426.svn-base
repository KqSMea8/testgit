package com.hanweb.jmp.cms.entity.sign;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 角标关系
 * @author qzq
 */
@Table(name = Tables.DIMENSIONREL)
public class SignRel implements Serializable {

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
	 * 角标id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer dimensionid;
	
	/**
	 * 角标下属性id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer attrid;
	
	/**
	 * 功能模块唯一标识
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer moduleid;
	
	/**
	 * 角标下属性orderid（订阅用）
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer orderid;
	
	/**
	 * 订阅栏目名称
	 */
	private String colName;
	
	/**
	 * 卡片下信息名称
	 */
	private String infoName;

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

	public Integer getAttrid() {
		return attrid;
	}

	public void setAttrid(Integer attrid) {
		this.attrid = attrid;
	}

	public Integer getModuleid() {
		return moduleid;
	}

	public void setModuleid(Integer moduleid) {
		this.moduleid = moduleid;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getInfoName() {
		return infoName;
	}

	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}

	public Integer getDimensionid() {
		return dimensionid;
	}

	public void setDimensionid(Integer dimensionid) {
		this.dimensionid = dimensionid;
	}

}