package com.hanweb.jmp.global.entity.jsonentity;

import java.io.Serializable;

/**
 * 信息实体
 * @author hq
 */
public class CateMoreInfos implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5456994860989490499L;
	
	/**
	 * titleId
	 */
	private Integer titleId;
	
	/**
	 * titleText
	 */
	private String titleText = "";
	
	/**
	 * titleSubtext
	 */
	private String titleSubtext = "";
	
	/**
	 * time
	 */
	private String time = "";
	
	/**
	 * imageUrl
	 */
	private String imageUrl = "";
	
	/**
	 * origImgUrl
	 */
	private String origImgUrl = "";
	
	/**
	 * source
	 */
	private String source = "";
	
	/**
	 * titleContent
	 */
	private String titleContent = "";
	
	/**
	 * url
	 */
	private String url = "";
	
	/**
	 * subtitle
	 */
	private String subtitle = "";
	
	/**
	 * topid
	 */
	private Integer topid;
	
	/**
	 * orderid
	 */
	private Integer orderid;
	
	/**
	 * downurl
	 */
	private String downurl = "";
	
	/**
	 * ipoitype
	 */
	private Integer ipoitype = 0;  
	
	/**
	 * ipoitype
	 */
	private String vcpoiLocation = ""; 
	
	/**
	 * vc_address
	 */
	private String vcaddress = "";  

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

	public String getTitleSubtext() {
		return titleSubtext;
	}

	public void setTitleSubtext(String titleSubtext) {
		this.titleSubtext = titleSubtext;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTitleContent() {
		return titleContent;
	}

	public void setTitleContent(String titleContent) {
		this.titleContent = titleContent;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public Integer getTopid() {
		return topid;
	}

	public void setTopid(Integer topid) {
		this.topid = topid;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public String getDownurl() {
		return downurl;
	}

	public void setDownurl(String downurl) {
		this.downurl = downurl;
	}

	public Integer getIpoitype() {
		return ipoitype;
	}

	public void setIpoitype(Integer ipoitype) {
		this.ipoitype = ipoitype;
	}

	public String getVcpoiLocation() {
		return vcpoiLocation;
	}

	public void setVcpoiLocation(String vcpoiLocation) {
		this.vcpoiLocation = vcpoiLocation;
	}

	public String getVcaddress() {
		return vcaddress;
	}

	public void setVcaddress(String vcaddress) {
		this.vcaddress = vcaddress;
	} 
	
}