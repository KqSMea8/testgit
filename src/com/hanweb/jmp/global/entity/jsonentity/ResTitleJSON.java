package com.hanweb.jmp.global.entity.jsonentity;

import java.io.Serializable;

/**
 * 首图JSON实体
 * @author WangFei
 */
public class ResTitleJSON implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4361859926252436161L;

	/**
	 * 
	 */
	private Integer titleId;

	/**
	 * 
	 */
	private String titleText = "";

	/**
	 * 
	 */
	private String time = "";

	/**
	 * 
	 */
	private String imageUrl = "";

	/**
	 * 
	 */
	private String origImgUrl = "";

	/**
	 * 
	 */
	private Integer visit;

	/**
	 * 
	 */
	private Integer haveNew;

	public Integer getTitleId() {
		return titleId;
	}

	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
	}

	public String getTitleText() {
		return titleText;
	}

	public void setTitleText(String titleText) {
		this.titleText = titleText;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getOrigImgUrl() {
		return origImgUrl;
	}

	public void setOrigImgUrl(String origImgUrl) {
		this.origImgUrl = origImgUrl;
	}

	public Integer getVisit() {
		return visit;
	}

	public void setVisit(Integer visit) {
		this.visit = visit;
	}

	public Integer getHaveNew() {
		return haveNew;
	}

	public void setHaveNew(Integer haveNew) {
		this.haveNew = haveNew;
	}

}