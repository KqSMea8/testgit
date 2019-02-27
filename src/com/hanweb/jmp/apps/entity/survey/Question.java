package com.hanweb.jmp.apps.entity.survey;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.QUESTION)
public class Question implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7825991174921904869L;
	
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
	 * 标题
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String name;
	
	/**
	 * 摘要
	 */
	@Column(type = ColumnType.VARCHAR, length = 900)
	private String abs;
	
	/**
	 * 网站Id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer siteId;
	
	/**
	 * 是否展示 0、展示 1、不展示
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isShow=0;
	
	/**
	 * 是否必填：0、必填 1、不必填 
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isWrite=0;
	
	/**
	 * 调查问题类型：1、单选 2、多选  3、文本
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer type;
	
	/**
	 * 信息排序ID
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer orderId = 0;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getIsWrite() {
		return isWrite;
	}

	public void setIsWrite(Integer isWrite) {
		this.isWrite = isWrite;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

}