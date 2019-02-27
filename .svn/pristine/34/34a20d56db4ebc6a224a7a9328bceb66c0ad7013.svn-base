package com.hanweb.jmp.constant;

import com.hanweb.common.util.SpringUtil;

public class ClientVersion {
	/**
	 * uc
	 */
	private String uc = "";
	
	/**
	 * iphone
	 */
	private String iphone = "";
	
	/**
	 * android
	 */
	private String android = "";
	
	/**
	 * ipad
	 */
	private String ipad = "";

	public String getUc() {
		return uc;
	}

	public void setUc(String uc) {
		this.uc = uc;
	}

	public String getIphone() {
		return iphone;
	}

	public void setIphone(String iphone) {
		this.iphone = iphone;
	}

	public String getAndroid() {
		return android;
	}

	public void setAndroid(String android) {
		this.android = android;
	}

	public String getIpad() {
		return ipad;
	}

	public void setIpad(String ipad) {
		this.ipad = ipad;
	}
	
	public static ClientVersion getClientVersions() {
		return SpringUtil.getBean("jmp_clientversions", ClientVersion.class);
	}

}
