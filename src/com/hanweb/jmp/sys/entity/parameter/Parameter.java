package com.hanweb.jmp.sys.entity.parameter;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 网站高级参数
 * @author hq
 */
@Table(name = Tables.SITE_DETAIL)
public class Parameter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4926725063150306426L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;

	/**
	 * 站点id，与网站表IID做关联
	 */
	@Column(type = ColumnType.INT)
	private Integer siteId;

	/**
	 * 站点图标存放路径
	 */
	@Column(type = ColumnType.VARCHAR)
	private String sitePic;

	/**
	 * 关于本站
	 */
	@Column(type = ColumnType.VARCHAR, length = 600)
	private String spec;

	/**
	 * 网站域名
	 */
	@Column(type = ColumnType.VARCHAR)
	private String domain;

	/**
	 * iphone客户端下载地址
	 */
	@Column(type = ColumnType.VARCHAR)
	private String iphoneUrl;
	
	/**
	 * android客户端下载地址
	 */
	@Column(type = ColumnType.VARCHAR)
	private String androidUrl;

	/**
	 * ipad客户端下载地址
	 */
	@Column(type = ColumnType.VARCHAR)
	private String ipadUrl;

	/**
	 * 是否支持推送
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isPush = 0;

	/**
	 * 极光推送标签
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String tag = "";

	/**
	 * Android推送appkey
	 */
	@Column(type = ColumnType.VARCHAR, length = 100)
	private String androidPushAppKey;

	/**
	 * Android推送appsecret
	 */
	@Column(type = ColumnType.VARCHAR, length = 100)
	private String androidPushAppSecret;

	/**
	 * iphone推送appkey
	 */
	@Column(type = ColumnType.VARCHAR, length = 100)
	private String iphonePushAppKey;

	/**
	 * iphone推送appsecret
	 */
	@Column(type = ColumnType.VARCHAR, length = 100)
	private String iphonePushAppSecret;

	/**
	 * ipad推送appkey
	 */
	@Column(type = ColumnType.VARCHAR, length = 100)
	private String ipadPushAppKey;

	/**
	 * ipad推送appsecret
	 */
	@Column(type = ColumnType.VARCHAR, length = 100)
	private String ipadPushAppSecret;

	/**
	 * 信息保存条数
	 */
	@Column(type = ColumnType.INT)
	private Integer pushSaveNum;

	/**
	 * 应用市场的大图标
	 */
	@Column(type = ColumnType.VARCHAR)
	private String appsIcon;

	/**
	 * 应用市场的描述
	 */
	@Column(type = ColumnType.VARCHAR, length = 600)
	private String appDesc;

	/**
	 * App工厂应用类型1：列表版2：九宫格
	 */
	@Column(type = ColumnType.INT, update = false)
	private Integer appType;
	
	/**
	 * 评论是否自动审核   1：是  0：否
	 */
	@Column(type = ColumnType.INT)
	private Integer commentAutoAudit = 0;

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

	public String getSitePic() {
		return sitePic;
	}

	public void setSitePic(String sitePic) {
		this.sitePic = sitePic;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
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

	public Integer getIsPush() {
		return isPush;
	}

	public void setIsPush(Integer isPush) {
		this.isPush = isPush;
	}

	public String getAndroidPushAppKey() {
		return androidPushAppKey;
	}

	public void setAndroidPushAppKey(String androidPushAppKey) {
		this.androidPushAppKey = androidPushAppKey;
	}

	public String getAndroidPushAppSecret() {
		return androidPushAppSecret;
	}

	public void setAndroidPushAppSecret(String androidPushAppSecret) {
		this.androidPushAppSecret = androidPushAppSecret;
	}

	public String getIphonePushAppKey() {
		return iphonePushAppKey;
	}

	public void setIphonePushAppKey(String iphonePushAppKey) {
		this.iphonePushAppKey = iphonePushAppKey;
	}

	public String getIphonePushAppSecret() {
		return iphonePushAppSecret;
	}

	public void setIphonePushAppSecret(String iphonePushAppSecret) {
		this.iphonePushAppSecret = iphonePushAppSecret;
	}

	public String getIpadPushAppKey() {
		return ipadPushAppKey;
	}

	public void setIpadPushAppKey(String ipadPushAppKey) {
		this.ipadPushAppKey = ipadPushAppKey;
	}

	public String getIpadPushAppSecret() {
		return ipadPushAppSecret;
	}

	public void setIpadPushAppSecret(String ipadPushAppSecret) {
		this.ipadPushAppSecret = ipadPushAppSecret;
	}

	public Integer getPushSaveNum() {
		return pushSaveNum;
	}

	public void setPushSaveNum(Integer pushSaveNum) {
		this.pushSaveNum = pushSaveNum;
	}

	public String getAppsIcon() {
		return appsIcon;
	}

	public void setAppsIcon(String appsIcon) {
		this.appsIcon = appsIcon;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public Integer getAppType() {
		return appType;
	}

	public void setAppType(Integer appType) {
		this.appType = appType;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Integer getCommentAutoAudit() {
		return commentAutoAudit;
	}

	public void setCommentAutoAudit(Integer commentAutoAudit) {
		this.commentAutoAudit = commentAutoAudit;
	}
	
}
