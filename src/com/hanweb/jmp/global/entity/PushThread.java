package com.hanweb.jmp.global.entity;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 推送线程信息实体
 * @author qzq
 *
 */
@Table(name = Tables.PUSHTHREAD)
public class PushThread implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6627498368195440580L;
	
	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11)
	private Integer iid;
	
	/**
	 * 信息id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer infoId;
	
	/**
	 * 所属网站id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer siteId;
	
	/**
	 * 信息推送时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date pushTime;
	
	/**
	 * 0、广播（所有人）1、指定机构2、指定群组3、指定用户
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer sendType;
	
	@Column(type = ColumnType.INT, length = 11)
	private Integer sendtypeid;
	
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String userIds;
	/**
	 * 1、公有；2、私有
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer ispublic;
	

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	public Integer getSendtypeid() {
		return sendtypeid;
	}

	public void setSendtypeid(Integer sendtypeid) {
		this.sendtypeid = sendtypeid;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public Integer getIspublic() {
		return ispublic;
	}

	public void setIspublic(Integer ispublic) {
		this.ispublic = ispublic;
	}
	
	
	
}