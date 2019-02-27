package com.hanweb.jmp.cms.controller.matters.picture;

import org.springframework.web.multipart.MultipartFile;

import com.hanweb.jmp.cms.entity.matters.picture.Picture;

public class PictureFormController extends Picture implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6584386659441836402L;
	
	/**
	 * 图片路径
	 */
	private MultipartFile picturefile;

	public MultipartFile getPicturefile() {
		return picturefile;
	}

	public void setPicturefile(MultipartFile picturefile) {
		this.picturefile = picturefile;
	}	
}