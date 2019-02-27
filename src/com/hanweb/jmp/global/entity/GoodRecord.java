package com.hanweb.jmp.global.entity;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.GOOD_RECORD)
public class GoodRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	/**
	 * iid:主键id.
	 * @since zx Ver 1.0 .
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;
	
	/**
	 * 信息id（报料id，评论id）
	 */
	@Column(type = ColumnType.INT)
	private Integer titleId;
	
	/**
	 * 网站id
	 */
	@Column(type = ColumnType.INT)
	private Integer siteId;
	
	/**
	 * 类型  1：信息   2：报料   3：评论
	 */
	@Column(type = ColumnType.INT)
	private Integer type;
	
	/**
	 * 手机的uuid
	 */
	@Column(type = ColumnType.VARCHAR)
	private String uuid;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getTitleId() {
		return titleId;
	}

	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}