package com.hanweb.jmp.apps.entity.survey;

import java.io.Serializable;
import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.SURVEYANSWER)
public class SurveyAnswer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6426656622650490909L;

	/**
	 * id
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;
	
	/**
	 * 调查Id
	 */
	@Column(type = ColumnType.INT)
	private Integer surveyId;
	
	/**
	 * 问题Id
	 */
	@Column(type = ColumnType.INT)
	private Integer questionId;
	
	/**
	 * 网站Id
	 */
	@Column(type = ColumnType.INT)
	private Integer siteId;
	
	/**
	 * 问答题答案
	 */
	@Column(type = ColumnType.VARCHAR, length=900)
	private String content;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}