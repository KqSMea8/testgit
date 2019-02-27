package com.hanweb.jmp.sys.controller.version;

import org.springframework.web.multipart.MultipartFile;

import com.hanweb.jmp.sys.entity.version.Version;

public class VersionFormBean  extends Version implements java.io.Serializable{ 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1405457483136136074L;
	
	/**
	 * apk文件
	 */
	private MultipartFile apkFile;
	
	/**
	 * plist文件
	 */
	private MultipartFile plistFile;
	
	/**
	 * ipa文件
	 */
	private MultipartFile ipaFile;
	
	/**
	 * logo文件
	 */
	private MultipartFile logoFile;

	public MultipartFile getApkFile() {
		return apkFile;
	}

	public void setApkFile(MultipartFile apkFile) {
		this.apkFile = apkFile;
	}

	public MultipartFile getPlistFile() {
		return plistFile;
	}

	public void setPlistFile(MultipartFile plistFile) {
		this.plistFile = plistFile;
	}

	public MultipartFile getIpaFile() {
		return ipaFile;
	}

	public void setIpaFile(MultipartFile ipaFile) {
		this.ipaFile = ipaFile;
	}

	public MultipartFile getLogoFile() {
		return logoFile;
	}

	public void setLogoFile(MultipartFile logoFile) {
		this.logoFile = logoFile;
	} 
	
}