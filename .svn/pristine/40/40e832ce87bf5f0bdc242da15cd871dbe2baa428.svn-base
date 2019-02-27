package com.hanweb.jmp.pack.entity;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 *一键打包用户实体表
 */
@Table(name = Tables.APPUSER)
public class AppUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3173732372905268328L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11)
	private Integer iid;

	/**
	 * 用户名
	 */
	@Column(type = ColumnType.VARCHAR, length = 255, update = false)
	private String username;
	
	/**
	 * uuid
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String uuid;

	/**
	 * 密码
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String pwd;
	
	/**
	 * 用户类型
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer userType;
	
	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createTime;
	
	/**
	 * 网站ID
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer siteId;
	
	/**
	 * 头像
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String headUrl; 
	
	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}  
	
}