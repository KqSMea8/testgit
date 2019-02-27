package com.hanweb.jmp.sys.entity.log;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 离线信息记录表
 * @author wanghh
 */
@Table(name = Tables.OFFLINEZIP)
public class OfflineZip implements Serializable{

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
	 * 网站id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer siteId;
	
	/**
	 * 栏目id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer colId;
	
	/**
	 * 打包时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date zipTime;
	
	/**
	 * 信息变动时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date modifyTime;
	
	/**
	 * 是否打包成功： 0为失败，1为成功
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isZip;

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

	public Integer getColId() {
		return colId;
	}

	public void setColId(Integer colId) {
		this.colId = colId;
	}

	public Date getZipTime() {
		return zipTime;
	}

	public void setZipTime(Date zipTime) {
		this.zipTime = zipTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getIsZip() {
		return isZip;
	}

	public void setIsZip(Integer isZip) {
		this.isZip = isZip;
	}
	
}