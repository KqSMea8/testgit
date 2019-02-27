package com.hanweb.jmp.cms.entity.infos;

import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 信息操作表
 * @author zx
 */
@Table(name = Tables.INFO_OPERATE)
public class InfoOperate {

	private static final long serialVersionUID = -195411541831362087L;

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
	 * 信息id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer infoId;
	
	/**
	 * 标题
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String title;
	
	/**
	 * URL
	 */
	@Column(type = ColumnType.VARCHAR, length = 1000)
	private String url;
	
	/**
	 * 操作类型   1新增    2删除  3修改  4审核 5 撤审
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer oprType = 0;
	
	/**
	 * 是否同步到索引库  0未同步到索引库   1已经同步到索引库
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isSearch = 0;
	
	/**
	 * 是否离线下载  0未离线   1已经离线
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isOffline = 0;
	
	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createTime;

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

	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public Integer getOprType() {
		return oprType;
	}

	public void setOprType(Integer oprType) {
		this.oprType = oprType;
	}

	public Integer getIsSearch() {
		return isSearch;
	}

	public void setIsSearch(Integer isSearch) {
		this.isSearch = isSearch;
	}

	public Integer getIsOffline() {
		return isOffline;
	}

	public void setIsOffline(Integer isOffline) {
		this.isOffline = isOffline;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}  
	
}