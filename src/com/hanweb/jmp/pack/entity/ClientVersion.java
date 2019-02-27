package com.hanweb.jmp.pack.entity;

/**
 * ftl版本
 * @author Zhangx
 */
public class ClientVersion {
	
	/**
	 * pad包地址
	 */
	private  String padUrl="";
	
	/**
	 * iphone包地址
	 */
	private String iphoneUrl="";
	
	/**
	 * android包地址
	 */
	private String androidUrl="";
	
	/**
	 * androidhtml地址
	 */
	private String androidHtml="";
	
	/**
	 * androidhtml地址
	 */
	private String iphoneHtml="";
	
	/**
	 * 应用名称
	 */
	private String appName="";
	
	/**
	 * androidhtml地址
	 */
	private String ipadHtml="";
	
	/**
	 * 系统路径
	 */
	private String sysUrl="";
	
	/**
	 * 应用图标
	 */
	private String appLogo="";
	
	public String getPadUrl() {
		return padUrl;
	}

	public void setPadUrl(String padUrl) {
		this.padUrl = padUrl;
	}

	public String getIphoneUrl() {
		return iphoneUrl;
	}

	public void setIphoneUrl(String iphoneUrl) {
		this.iphoneUrl = iphoneUrl;
	}

	public String getAndroidUrl() {
		return androidUrl;
	}

	public void setAndroidUrl(String androidUrl) {
		this.androidUrl = androidUrl;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getSysUrl() {
		return sysUrl;
	}

	public void setSysUrl(String sysUrl) {
		this.sysUrl = sysUrl;
	}

	public String getAndroidHtml() {
		return androidHtml;
	}

	public void setAndroidHtml(String androidHtml) {
		this.androidHtml = androidHtml;
	}

	public String getAppLogo() {
		return appLogo;
	}

	public void setAppLogo(String appLogo) {
		this.appLogo = appLogo;
	}

	public String getIphoneHtml() {
		return iphoneHtml;
	}

	public void setIphoneHtml(String iphoneHtml) {
		this.iphoneHtml = iphoneHtml;
	}

	public String getIpadHtml() {
		return ipadHtml;
	}

	public void setIpadHtml(String ipadHtml) {
		this.ipadHtml = ipadHtml;
	}
	
}