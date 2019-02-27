package com.hanweb.jmp.cms.entity.matters.picture;

import java.io.Serializable;

import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.PICTURE)
public class Picture implements Serializable{

	private static final long serialVersionUID = 1860567787946626437L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;

	/**
	 * 网站id
	 */
	@Column(type = ColumnType.INT)
	private Integer siteId;
	
	/**
	 * 图片名称
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String name;
	
	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createTime;
	
	/**
	 * 删除时间
	 */
	@Column(type = ColumnType.DATETIME,update = false)
	private Date removeTime;

	/**
	 * 图片路径
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String picturePath;
	
	/**
	 * classid 分类id
	 */
	@Column(type = ColumnType.INT)
	private Integer classId;
	
	/**
	 * 分类名称
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String pname;
	
	/**
	 * 是否删除 0：未删除 1：删除
	 */
	@Column(type = ColumnType.INT)
	private Integer isremove;
	
	public Integer getIsremove() {
		return isremove;
	}
	
	public void setIsremove(Integer isremove) {
		this.isremove = isremove;
	}
	
	public Date getRemoveTime() {
		return removeTime;
	}
	
	public void setRemoveTime(Date removeTime) {
		this.removeTime = removeTime;
	}
	
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getPicturePath() {
		return picturePath;
	}
	
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	
	public Integer getClassId() {
		return classId;
	}
	
	public void setClassId(Integer classId) {
		this.classId = classId; 
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPname() {
		return pname;
	}
	
}