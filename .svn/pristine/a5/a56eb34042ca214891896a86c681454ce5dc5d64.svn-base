package com.hanweb.jmp.apps.entity.read;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.OnlyQuery;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.EBOOK)
public class Read implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3296630734098196874L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;
	
	/**
	 * 名称
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String name;

	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createTime;
	
	/**
	 * 出版时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date pubTime;
	
	/**
	 * 阅读信息修改时间（非常重要，客户端用）
	 */
	@Column(type = ColumnType.DATETIME)
	private Date changeTime;
	
	/**
	 * 网站ID
	 */
	@Column(type = ColumnType.INT)
	private Integer siteId;
	
	/**
	 * 排序ID
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer orderId;
	
	/**
	 * 作者
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String author;
	
	/**
	 * 简介 
	 */
	@Column(type = ColumnType.VARCHAR, length = 900)
	private String spec;
	
	/**
	 * 类型
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer type;
	
	/**
	 * 父ID
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer pid;
	
	/**
	 * 图片路径
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String picsize;
	
	/**
	 * 封面图路径
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String picPath;
	
	/**
	 * 大图路径(书刊详情页图片)
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String bigPath;
	
	/**
	 * 标识位 
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer flag;
	
	/**
	 * 文件路径
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String filePath;
	
	/**
	 * 是否为父节点
	 */
	@OnlyQuery
	private Boolean isparent=false;
	
	@OnlyQuery
	private String pname;
	
	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Boolean getIsparent() {
		return isparent;
	}

	public void setIsparent(Boolean isparent) {
		this.isparent = isparent;
	}

	public Date getPubTime() {
		return pubTime;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

	public String getBigPath() {
		return bigPath;
	}

	public void setBigPath(String bigPath) {
		this.bigPath = bigPath;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

    public void setPicsize(String picsize) {
        this.picsize = picsize;
    }

    public String getPicsize() {
        return picsize;
    }
	
}