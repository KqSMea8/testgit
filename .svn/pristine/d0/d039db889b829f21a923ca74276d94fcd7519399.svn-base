package com.hanweb.jmp.sys.entity.version;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.jmp.constant.Tables;

/**
 * 客户端升级实体
 * 
 * @author WangFei
 * 
 */
@Table(name = Tables.UPDATE)
public class Version implements Serializable {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = 2755559066554644488L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer iid;

	/**
	 * 服务器版本号
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String version;

	/**
	 * 更新说明
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String msg;

	/**
	 * 网站id
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer siteId;
	
	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createTime;

	/**
	 * 客户端类型2:iphone 3:android 4:ipad
	 */
	@Column(type = ColumnType.VARCHAR, length = 2)
	private Integer clientType;

	/**
	 * 更新说明
	 */
	@Column(type = ColumnType.VARCHAR, length = 500)
	private String downUrl;
	
	/**
	 * 更新类型1:建议类型 2:强制更新
	 */
	@Column(type = ColumnType.INT, length = 2)
	private Integer updateType=1;

	/**
	 * 0:生产包   1：企业包
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer apptype;
	 
	/**
	 * 应用 ID
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer appId;
	
	/**
	 * 更新说明
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String logoPath;
	
	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public Integer getUpdateType() {
		return updateType;
	}

	public void setUpdateType(Integer updateType) {
		this.updateType = updateType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Integer getApptype() {
		return apptype;
	}

	public void setApptype(Integer apptype) {
		this.apptype = apptype;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}  
	
}