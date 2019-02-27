package com.hanweb.jmp.apps.entity.numbersense;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.NUMSENSEPHONE)
public class NumSensePhone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2963989861508361870L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;
	
	/**
	 * 名称
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String name;
	
	/**
	 * 固定电话
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String phone;
	
	/**
	 * 移动电话
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String tel;
	
	/**
	 * 固定电话
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String email;

	/**
	 * 网址
	 */
	@Column(type = ColumnType.VARCHAR, length = 600)
	private String url;

	/**
	 * 简介
	 */
	@Column(type = ColumnType.VARCHAR, length = 2100)
	private String spec;

	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createTime;
	
	/**
	 * 网站ID
	 */
	@Column(type = ColumnType.INT)
	private Integer siteId;
	
	/**
	 * 分类ID
	 */
	@Column(type = ColumnType.INT)
	private Integer colId;
	
	/**
	 * 图片路径
	 */
	@Column(type = ColumnType.VARCHAR)
	private String iconPath;

	/**
	 * 坐标
	 */
	@Column(type = ColumnType.VARCHAR)
	private String address;
	
	/**
	 * 排序ID
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer orderId;
	
	@Column(type = ColumnType.VARCHAR)
	private String bgColor;

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
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

	public Integer getColId() {
		return colId;
	}

	public void setColId(Integer colId) {
		this.colId = colId;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}