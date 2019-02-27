package com.hanweb.jmp.pack.backstage.controller.application;

import org.springframework.web.multipart.MultipartFile;

import com.hanweb.jmp.pack.backstage.entity.Application;

public class ApplicationFormBean extends Application implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3116366547674243680L;
	
	
	/**
	 * 图标文件
	 */
	private MultipartFile iconFile;


	public MultipartFile getIconFile() {
		return iconFile;
	}


	public void setIconFile(MultipartFile iconFile) {
		this.iconFile = iconFile;
	}
	
}
