package com.hanweb.jmp.global.entity.normalentity;

import java.util.List;

public class SynchInfo {
	
	/**
	 * 信息id
	 */
	private String id;		 
	
	/**
	 * 信息状态
	 */
	private String state;	     
	
	/**
	 * 日志惟一标识
	 */
	private String logidentity;	 
	
	/**
	 * 创建时间
	 */
	private String ctime;	  
	
	/**
	 * 字段名称串
	 */
	private String fileds;     
	
	/**
	 * 字段实体集合
	 */
	private List<SynchField> filed;		    
	
	/**
	 * 文件实体集合
	 */
	private List<SynchFile> files;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLogidentity() {
		return logidentity;
	}

	public void setLogidentity(String logidentity) {
		this.logidentity = logidentity;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getFileds() {
		return fileds;
	}

	public void setFileds(String fileds) {
		this.fileds = fileds;
	}

	public List<SynchField> getFiled() {
		return filed;
	}

	public void setFiled(List<SynchField> filed) {
		this.filed = filed;
	}

	public List<SynchFile> getFiles() {
		return files;
	}

	public void setFiles(List<SynchFile> files) {
		this.files = files;
	}	
	
}