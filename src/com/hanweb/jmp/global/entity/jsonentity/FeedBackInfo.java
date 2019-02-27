/*
 * @(#)BrokeInfo.java	
 *
 * Copyright 1997-2008  Hanweb CO.,LTD. All rights reserved.
 * HANWEB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.hanweb.jmp.global.entity.jsonentity;

import java.io.Serializable;  
 
public class FeedBackInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4077960442556495000L;

	/**
	 * 主键iid
	 */
	private String iid;
	
	/**
	 * 站点id
	 */ 
	private String siteid;
	
	/**
	 * 内容
	 */ 
	private String content;
	
	/**
	 * 联系方式
	 */ 
	private String contact;
	
	/**
	 * 创建时间
	 */
	private String createtime;
	
	/**
	 * 登录名
	 */
	private String loginname;

	public String getIid() {
		return iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	} 
	
}