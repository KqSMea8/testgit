package com.hanweb.jmp.pack.entity;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 *一键打包版本实体
 *
 */
@Table(name = Tables.CLIENT)
public class Client implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8020206996027596982L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11)
	private Integer iid;

	/**
	 * 应用 ID
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer appId;

	/**
	 * 网站ID,与用户表iid做关联
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer siteId;
	
	/**
	 * 版本号
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String version;
	
	/**
	 * 版本号
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String appName;
	
	/**
	 * 版本描述
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String spec;
	
	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createTime;
	
	/**
	 * 应用创建人
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String creater;
	
	/**
	 * 客户端类型
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer clientType;
	
	/**
	 * 打包状态   1: 打包进行    2：打包成功    3：打包失败
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer status;
	
	/**
	 * iphone下载地址
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String iphoneUrl;
	
	/**
	 * 安卓下载地址
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String androidUrl;
	
	/**
	 * ipad下载地址
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String ipadUrl;
	
	/**
	 * plist文件
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String plist;
	
	/**
	 * 服务器端打包发送参数
	 */
	@Column(type = ColumnType.VARCHAR, length = 600)
	private String sendJson;
	
	/**
	 * 客户端打包返回参数
	 */
	@Column(type = ColumnType.VARCHAR, length = 600)
	private String backJson;
	
	/**
	 * 客户端打包回调参数
	 */
	@Column(type = ColumnType.VARCHAR, length = 600)
	private String callbackJson;
	
	/**
	 * 审核状态
	 */
	@Column(type = ColumnType.INT, length = 2)
	private Integer isAudit=0;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAndroidUrl() {
		return androidUrl;
	}

	public void setAndroidUrl(String androidUrl) {
		this.androidUrl = androidUrl;
	}

	public String getIphoneUrl() {
		return iphoneUrl;
	}

	public void setIphoneUrl(String iphoneUrl) {
		this.iphoneUrl = iphoneUrl;
	}

	public String getIpadUrl() {
		return ipadUrl;
	}

	public void setIpadUrl(String ipadUrl) {
		this.ipadUrl = ipadUrl;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getPlist() {
		return plist;
	}

	public void setPlist(String plist) {
		this.plist = plist;
	}

	public String getSendJson() {
		return sendJson;
	}

	public void setSendJson(String sendJson) {
		this.sendJson = sendJson;
	}

	public String getBackJson() {
		return backJson;
	}

	public void setBackJson(String backJson) {
		this.backJson = backJson;
	}

	public String getCallbackJson() {
		return callbackJson;
	}

	public void setCallbackJson(String callbackJson) {
		this.callbackJson = callbackJson;
	}

	public Integer getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}
	
}