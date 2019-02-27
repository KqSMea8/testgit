package com.hanweb.jmp.apps.entity.survey;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.SURVEYUSERVOTECOUNT)
public class UserVoteCount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1153619785883914176L;
	
	/**
	 * id
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;
	
	/**
	 * 网站Id
	 */
	@Column(type = ColumnType.INT)
	private Integer siteId;
	
	/**
	 * 调查Id
	 */
	@Column(type = ColumnType.INT)
	private Integer surveyId;
	
	/**
	 * 某一调查投票次数
	 */
	@Column(type = ColumnType.INT)
	private Integer count;
	
	/**
	 * 用户id
	 */
	@Column(type = ColumnType.INT)
	private Integer userId;
	
	/**
	 * uuid
	 */
	@Column(type = ColumnType.VARCHAR)
	private String uuid;
	
	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}