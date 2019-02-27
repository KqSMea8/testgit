package com.hanweb.jmp.global.entity.jsonentity;

/**
 * 全文检索信息实体
 * @author hq
 */
public class JsearchInfo {
	
	/**
	 * 
	 */
	private String pubDate = "";
	
	/**
	 * 
	 */
	private String link = "";
	
	/**
	 * 
	 */
	private String title = "";
	
	/**
	 * 
	 */
	private String subtitle = "";
	
	/**
	 * 
	 */
	private Integer iid = 0;
	
	/**
	 * 
	 */
	private String source = "";
	
	/**
	 * 
	 */
	private String description = "";

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	} 

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}