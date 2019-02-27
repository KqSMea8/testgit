package com.hanweb.jmp.apps.entity.manage;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.LIGHTAPP)
public class LightApp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;

	/**
	 * 站点id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer siteId;

	/**
	 * 应用名称
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String name;

	/**
	 * 应用分类
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer lightType = 0;

	/**
	 * 应用分类名称
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String lightTypeName;

	/**
	 * 应用类型 2:Native 1:H5
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer appType = 1;

	/**
	 * h5外链地址
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String url;

	/**
	 * 是否开启:1.开启 2.关闭
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer isOpen = 1;

	/**
	 * 排序id
	 */
	@Column(type = ColumnType.INT)
	private Integer orderId;

	/**
	 * 系统默认 0是后来新增的轻应用 1表示系统默认轻应用
	 */
	@Column(type = ColumnType.INT)
	private Integer isDefault;

	/**
	 * 默认后台管理类型
	 */
	@Column(type = ColumnType.INT)
	private Integer defaultType;

	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createTime;

	/**
	 * 是否显示标题区域：0.显示，1.不显示
	 */
	@Column(type = ColumnType.INT)
	private Integer isShowTopView;

	/**
	 * 同步唯一标识
	 */
	@Column(type = ColumnType.VARCHAR)
	private String keyValue;

	/**
	 * 应用所属机构
	 */
	@Column(type = ColumnType.VARCHAR)
	private String groupName;

	/**
	 * 应用图标原名陈
	 */
	@Column(type = ColumnType.VARCHAR)
	private String iconOldName;

	/**
	 * 应用图标新名称
	 */
	@Column(type = ColumnType.VARCHAR)
	private String iconNewName;

	/**
	 * 二维码地址
	 */
	@Column(type = ColumnType.VARCHAR)
	private String qrCodeAddress;

	public String getQrCodeAddress() {
		return qrCodeAddress;
	}

	public void setQrCodeAddress(String qrCodeAddress) {
		this.qrCodeAddress = qrCodeAddress;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDefaultType() {
		return defaultType;
	}

	public void setDefaultType(Integer defaultType) {
		this.defaultType = defaultType;
	}

	public void setAppType(Integer appType) {
		this.appType = appType;
	}

	public Integer getAppType() {
		return appType;
	}

	public Integer getIsShowTopView() {
		return isShowTopView;
	}

	public void setIsShowTopView(Integer isShowTopView) {
		this.isShowTopView = isShowTopView;
	}

	public Integer getLightType() {
		return lightType;
	}

	public void setLightType(Integer lightType) {
		this.lightType = lightType;
	}

	public String getLightTypeName() {
		return lightTypeName;
	}

	public void setLightTypeName(String lightTypeName) {
		this.lightTypeName = lightTypeName;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getIconOldName() {
		return iconOldName;
	}

	public void setIconOldName(String iconOldName) {
		this.iconOldName = iconOldName;
	}

	public String getIconNewName() {
		return iconNewName;
	}

	public void setIconNewName(String iconNewName) {
		this.iconNewName = iconNewName;
	}

}