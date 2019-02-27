package com.hanweb.jmp.cms.controller.infos.info; 

import org.springframework.web.multipart.MultipartFile;

import com.hanweb.jmp.cms.entity.infos.Info;

public class InfoFormBean  extends Info implements java.io.Serializable {

	private static final long serialVersionUID = 129205068690677477L;
	
	/**
	 * 首图文件
	 */
	private MultipartFile firstPicFile;
	
	/**
	 * 视频文件
	 */
	private MultipartFile vedioFile;
	
	/**
	 * 音频文件
	 */
	private MultipartFile audioFile;
	
	/**
	 * IPAD图片文件
	 */
	private MultipartFile ipadImgFile1;
	
	/**
	 * IPAD图片文件
	 */
	private MultipartFile ipadImgFile2;
	
	
	/**
	 * IPHONE图片文件
	 */
	private MultipartFile iphoneImgFile;
	
	/**
	 * ANDROID图片文件
	 */
	private MultipartFile androidImgFile;

	public MultipartFile getFirstPicFile() {
		return firstPicFile;
	}

	public void setFirstPicFile(MultipartFile firstPicFile) {
		this.firstPicFile = firstPicFile;
	}

	public MultipartFile getVedioFile() {
		return vedioFile;
	}

	public void setVedioFile(MultipartFile vedioFile) {
		this.vedioFile = vedioFile;
	}

	public MultipartFile getAudioFile() {
		return audioFile;
	}

	public void setAudioFile(MultipartFile audioFile) {
		this.audioFile = audioFile;
	}

	public MultipartFile getIpadImgFile1() {
		return ipadImgFile1;
	}

	public void setIpadImgFile1(MultipartFile ipadImgFile1) {
		this.ipadImgFile1 = ipadImgFile1;
	}

	public MultipartFile getIpadImgFile2() {
		return ipadImgFile2;
	}

	public void setIpadImgFile2(MultipartFile ipadImgFile2) {
		this.ipadImgFile2 = ipadImgFile2;
	}

	public MultipartFile getIphoneImgFile() {
		return iphoneImgFile;
	}

	public void setIphoneImgFile(MultipartFile iphoneImgFile) {
		this.iphoneImgFile = iphoneImgFile;
	}

	public MultipartFile getAndroidImgFile() {
		return androidImgFile;
	}

	public void setAndroidImgFile(MultipartFile androidImgFile) {
		this.androidImgFile = androidImgFile;
	}
	
}