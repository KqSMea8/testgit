package com.hanweb.jmp.sys.entity.collect;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.COLLECT)
public class Collect implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11)
	private Integer iid;
	
	/**
	 * 用户id
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String userId;
	
	/**
	 * 用户名
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String userName;
	
	/**
	 * 收藏类型
	 * 1 信息
	 * 2 应用
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer type;
	
	/**
	 * 收藏id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer collectId;
	
	/**
	 * 收藏时间
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String createTime;
	
	/**
	 * 信息名称
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String infoName;
	
	/**
	 * 网站id
	 */
	@Column(type = ColumnType.VARCHAR, length = 11)
	private Integer siteId;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCollectId() {
		return collectId;
	}

	public void setCollectId(Integer collectId) {
		this.collectId = collectId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getInfoName() {
		return infoName;
	}

	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}
	
}