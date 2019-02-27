package com.hanweb.jmp.pack.backstage.entity;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.APPLICATIONS)
public class Application implements Serializable{

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
	 * h5外链地址
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String url;
	
	/**
	 * 图标路径
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String logoPath;

	/**
	 * 应用性质:0.统一用户   1.单点登录  2.不用校验
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer type = 1;
	
	/**
	 * 类型:1.爆料 2.vipchat m.其他系统应用
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String kind = "m";
	
	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createTime;
	
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
	 * 系统域名
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String domain;
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

}