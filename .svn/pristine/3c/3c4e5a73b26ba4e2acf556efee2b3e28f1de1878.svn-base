package com.hanweb.jmp.cms.entity.infos;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 信息实体
 * @author zx
 */
@Table(name = Tables.PIC)

public class Pic implements Serializable {
	
	private static final long serialVersionUID = -3497020310212311268L;
	
	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11)
    private int iid;
	
	/**
	 * 信息id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer infoId;
	
	/**
	 * 栏目id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer colId;

	/**
	 * 图片排序ID
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer orderId = 0;
	
	/**
	 * 网站id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer siteId;
	
	/**
	 * 图片摘要
	 */
	@Column(type = ColumnType.VARCHAR, length = 2000)
	private String picabstract;
  
	/**
	 * 图片地址
	 */
	@Column(type = ColumnType.VARCHAR, length = 600)
    private String picpath = "";
	
	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createTime;

	/**
	 * 同步时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date synTime;

	/**
	 * 图片名称
	 */
	private String picname="";
	
	/**
	 * 图片描述
	 */
	private String picdesc=""; 
	
	/**
	 * 图片标题
	 */
	private String pictitle="";
	
	public int getIid() {
		return iid;
	}

	public void setIid(int iid) {
		this.iid = iid;
	}

	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public Integer getColId() {
		return colId;
	}

	public void setColId(Integer colId) {
		this.colId = colId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getPicabstract() {
		return picabstract;
	}

	public void setPicabstract(String picabstract) {
		this.picabstract = picabstract;
	}

	public String getPicpath() {
		return picpath;
	}

	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getSynTime() {
		return synTime;
	}

	public void setSynTime(Date synTime) {
		this.synTime = synTime;
	}

	public String getPicname() {
		return picname;
	}

	public void setPicname(String picname) {
		this.picname = picname;
	}

	public String getPicdesc() {
		return picdesc;
	}

	public void setPicdesc(String picdesc) {
		this.picdesc = picdesc;
	}

	public String getPictitle() {
		return pictitle;
	}

	public void setPictitle(String pictitle) {
		this.pictitle = pictitle;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderId() {
		return orderId;
	}    
	
}