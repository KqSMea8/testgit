package com.hanweb.jmp.global.entity.jsonentity;

public class InfoJSON {

	/**
	 * 信息标题
	 */
	private String vc_title = "";
	
	/**
	 * 作者
	 */
	private String vc_author = "";
	
	/**
	 * 摘要
	 */
	private String vc_describe = "";
	
	/**
	 * 相关图片文件
	 */
	private String vc_pic = "";      
	
	/**
	 * 链接属性
	 */
	private String vc_href = ""; 
	
	/**
	 * 对应文章正文的内容, 非数据库字段
	 */
	private String artcontent = "";

	public String getVc_title() {
		return vc_title;
	}

	public void setVc_title(String vc_title) {
		this.vc_title = vc_title;
	}

	public String getVc_author() {
		return vc_author;
	}

	public void setVc_author(String vc_author) {
		this.vc_author = vc_author;
	}

	public String getVc_describe() {
		return vc_describe;
	}

	public void setVc_describe(String vc_describe) {
		this.vc_describe = vc_describe;
	}

	public String getVc_pic() {
		return vc_pic;
	}

	public void setVc_pic(String vc_pic) {
		this.vc_pic = vc_pic;
	}

	public String getVc_href() {
		return vc_href;
	}

	public void setVc_href(String vc_href) {
		this.vc_href = vc_href;
	}

	public String getArtcontent() {
		return artcontent;
	}

	public void setArtcontent(String artcontent) {
		this.artcontent = artcontent;
	}          

}