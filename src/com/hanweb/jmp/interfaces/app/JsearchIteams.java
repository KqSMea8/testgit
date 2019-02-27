package com.hanweb.jmp.interfaces.app;

import com.hanweb.jmp.cms.entity.infos.Info;

public class JsearchIteams {
	
	/**
	 * docId
	 */
	private Integer docId = 0;
	
	/**
	 * score
	 */
	private double score = 0;
	
	/**
	 * data
	 */
	private Info data = null;
	
	/**
	 * cacheKey
	 */
	private String cacheKey = "";
	
	public Integer getDocId() {
		return docId;
	}
	
	public void setDocId(Integer docId) {
		this.docId = docId;
	}
	
	public double getScore() {
		return score;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
	
	public Info getData() {
		return data;
	}
	
	public void setData(Info data) {
		this.data = data;
	}
	
	public String getCacheKey() {
		return cacheKey;
	}
	
	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
	
}