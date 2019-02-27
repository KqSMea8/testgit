package com.hanweb.jmp.sys.entity.feedback;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 用户意见反馈表
 * @author hq
 */
@Table(name = Tables.FEEDBACK)
public class FeedBack implements Serializable {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -3333545074968578404L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11)
	private Integer iid;

	/**
	 * 用户id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer userId;

	/**
	 * 站点id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer siteId;

	/**
	 * 客户端类型 1:uc 2:iphone 3:android 4:ipad
	 */
	@Column(type = ColumnType.INT, length = 2)
	private Integer clientType;

	/**
	 * 设备码
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String deviceCode;

	/**
	 * 用户登录名
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String loginName;

	/**
	 * 反馈内容
	 */
	@Column(type = ColumnType.VARCHAR, length = 2000)
	private String content;

	/**
	 * 是否已读
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isRead;

	/**
	 * 联系方式
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String contact;

	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date createTime;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}