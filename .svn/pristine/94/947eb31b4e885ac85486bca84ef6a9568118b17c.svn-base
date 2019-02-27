package com.hanweb.jmp.cms.entity.comment;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.COMMENT)
public class Comment implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;
	
	/**
	 * 信息（报料）id
	 */
	@Column(type = ColumnType.INT)
	private Integer infoId;
	
	/**
	 * 栏目id
	 */
	@Column(type = ColumnType.INT)
	private Integer cateId;
	
	/**
	 * 网站id
	 */
	@Column(type = ColumnType.INT)
	private Integer siteId;
	
	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date createTime;
	
	/**
	 * 评论内容
	 */
	@Column(type = ColumnType.VARCHAR, length = 500)
	private String content;
	
	/**
	 * 客户端类型
	 */
	@Column(type = ColumnType.INT)
	private Integer clientType;
	
	/**
	 * 审核状态  0：未审核  1：审核
	 */
	@Column(type = ColumnType.INT)
	private Integer state;
	
	/**
	 * 登录名
	 */
	@Column(type = ColumnType.VARCHAR)
	private String loginid;
	
	/**
	 * ip地址
	 */
	@Column(type = ColumnType.VARCHAR, length=255)
	private String ip;
	
	/**
	 * 用户姓名
	 */
	private String name;
	
	/**
	 * 用户头像
	 */
	private String headurl;
	
	/**
	 * 评论类型  1：信息  2：报料
	 */
	@Column(type = ColumnType.INT)
	private Integer type;
	
	/**
	 * 评论地址
	 */
	@Column(type = ColumnType.VARCHAR)
	private String address;
	
	/**
	 * 点赞数
	 */ 
	private Integer goodCount;
	
	/**
	 * 点赞数
	 */ 
	private Integer mygoodCount;
	
	
	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadurl() {
		return headurl;
	}

	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}

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

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getGoodCount() {
		return goodCount;
	}

	public void setGoodCount(Integer goodCount) {
		this.goodCount = goodCount;
	}

    public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	} 
	
	public Integer getMygoodCount() {
		return mygoodCount;
	}

	public void setMygoodCount(Integer mygoodCount) {
		this.mygoodCount = mygoodCount;
	}
	
}