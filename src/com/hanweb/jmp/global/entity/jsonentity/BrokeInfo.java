/*
 * @(#)BrokeInfo.java	
 *
 * Copyright 1997-2008  Hanweb CO.,LTD. All rights reserved.
 * HANWEB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.hanweb.jmp.global.entity.jsonentity;

import java.io.Serializable; 
 
public class BrokeInfo implements Serializable{

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）.
	 * @since zx Ver 1.0 .
	 */
	
	private static final long serialVersionUID = 334584121581941480L;

	/**
	 * 主键
	 */ 
	private String id;

	/**
	 * 站点id
	 */ 
	private String siteid;

	/**
	 * 信息分类
	 */ 
	private String classid; 

	/**
	 * 报料地址
	 */ 
	private String address;

	/**
	 * 信息标题
	 */ 
	private String title;
	 
  
	/**
	 * 信息入库时间
	 */ 
	private String time;

	/**
	 * 图片路径
	 */ 
	private String picpath;

	/**
	 * 音频路径
	 */ 
	private String audiopath;

	/**
	 * 视频路径
	 */ 
	private String videopath; 

	/**
	 * 回复反馈内容
	 */ 
	private String reply;
	
	/**
	 * 回复反馈时间
	 */ 
	private String replytime; 
	
	/**
	 * 用户头像
	 */ 
	private String nameimage="";
	
	/**
	 * 用户名称
	 */ 
	private String name;
	
	/**
	 * 分类名称
	 */ 
	private String classify="";
	
	/**
	 * 后台管理员是否已回复
	 */
	private Boolean isreply = false;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPicpath() {
		return picpath;
	}

	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}

	public String getAudiopath() {
		return audiopath;
	}

	public void setAudiopath(String audiopath) {
		this.audiopath = audiopath;
	}

	public String getVideopath() {
		return videopath;
	}

	public void setVideopath(String videopath) {
		this.videopath = videopath;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getReplytime() {
		return replytime;
	}

	public void setReplytime(String replytime) {
		this.replytime = replytime;
	}

	public String getNameimage() {
		return nameimage;
	}

	public void setNameimage(String nameimage) {
		this.nameimage = nameimage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public Boolean getIsreply() {
		return isreply;
	}

	public void setIsreply(Boolean isreply) {
		this.isreply = isreply;
	}
	
}