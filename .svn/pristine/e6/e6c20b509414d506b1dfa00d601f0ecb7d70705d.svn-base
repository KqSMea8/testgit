package com.hanweb.jmp.apps.entity.survey;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.ANWSER)
public class Answer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3856016601331106834L;
	
	/**
	 * id
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;
	
	/**
	 * 调查Id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer surveyId;
	
	/**
	 * 调查问题Id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer questionId;
	
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String name;
	
	/**
	 * 投票数
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer count=0;
	
	/**
	 * 网站Id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer siteId;
	
	/**
	 * 首图路径
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String firstPicPath="";
	
	/**
	 * 信息排序ID
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer orderId = 0;
	
	/**
	 * 外链地址
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String url;
	
	/**
	 * 外链地址
	 */
	@Column(type = ColumnType.VARCHAR, length = 600)
	private String abs;
	
	/**
	 * 选项所占百分比
	 */
	@Column(type = ColumnType.DOUBLE)
	private Float percent;

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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstPicPath() {
		return firstPicPath;
	}

	public void setFirstPicPath(String firstPicPath) {
		this.firstPicPath = firstPicPath;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	public Float getPercent() {
		return percent;
	}

	public void setPercent(Float percent) {
		this.percent = percent;
	}

}