/*
 * @(#)CreateReturnEntity.java	
 *
 * Copyright 1997-2008  Hanweb CO.,LTD. All rights reserved.
 * HANWEB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.hanweb.jmp.pack.entity;

/**
 * 打包接口返回参数
 * @author zx
 */
public class CreateReturnEntity {

	/**
	 * 接口调用结果
	 */
	private String result = "";
	
	/**
	 * 网站id
	 */
	private String siteid;
	
	/**
	 * 客户端类型  2：iphone    3：android    4：ipad
	 */
	private String clienttype;
	
	/**
	 * 版本号
	 */
	private String version;
	
	/**
	 * 包地址
	 */
	private String url; 
	
	/**
	 * plist文件地址
	 */
	private String plisturl;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public String getClienttype() {
		return clienttype;
	}

	public void setClienttype(String clienttype) {
		this.clienttype = clienttype;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	} 
	 
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPlisturl() {
		return plisturl;
	}

	public void setPlisturl(String plisturl) {
		this.plisturl = plisturl;
	}

}