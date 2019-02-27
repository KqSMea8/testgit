package com.hanweb.jmp.global.entity.jsonentity;

/**
 * 推送历史即消息中心信息实体
 * @author hq
 */
public class PushLogEntity {
	
	/**
	 * 信息id
	 */
	private String infoId;
	
	/**
	 * 信息标题
	 */
	private String infoTitle;
	
	/**
	 * 原文连接
	 */
	private String infoUrl;
	
	/**
	 * 音、视频和图片链接地址json
	 */
	private String attachment;
	
	/**
	 * 信息类型，文本、视频、音频、图片
	 */
	private String infoType;
	
	/**
	 * 摘要
	 */
	private String subtext;
	
	/**
	 * 分类名称
	 */
	private String source;
	
	/**
	 * z信息正文
	 */
	private String content;
	
	/**
	 * 发送时间
	 */
	private String infoTime;

	/**
	 * 第三方调用的跳转连接
	 */
	private String url;
	
	/**
	 * 信息推送分类id
	 */
	private String classficationId;
	
	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getInfoTitle() {
		return infoTitle;
	}

	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}

	public String getInfoUrl() {
		return infoUrl;
	}

	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getSubtext() {
		return subtext;
	}

	public void setSubtext(String subtext) {
		this.subtext = subtext;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getInfoTime() {
		return infoTime;
	}

	public void setInfoTime(String infoTime) {
		this.infoTime = infoTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getClassficationId() {
		return classficationId;
	}

	public void setClassficationId(String classficationId) {
		this.classficationId = classficationId;
	}
	
}