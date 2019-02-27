package com.hanweb.jmp.cms.entity.channels;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.OnlyQuery;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 频道实体
 * @author hq
 */
@Table(name = Tables.CHANNEL)
public class Channel implements Serializable {

	private static final long serialVersionUID = -2514726131757607276L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;

	/**
	 * 频道名称
	 */
	@Column(type = ColumnType.VARCHAR)
	private String name;

	/**
	 * 频道首图
	 */
	@Column(type = ColumnType.VARCHAR)
	private String firstPic;

	/**
	 * 频道配图
	 */
	@Column(type = ColumnType.VARCHAR)
	private String compoundPic; 
	
	/**
	 * 频道类型 1、单栏目类; 7、混合类型
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer type = 1;
	
	/**
	 * 频道展现类型 1横向展现; 2平铺
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer channeltype = 1;

	/**
	 * 频道访问权限0:匿名访问 1:登录访问
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isVisit;

	/**
	 * 混合型栏目的展现方式 1:列表 2:九宫格
	 */
	@Column(type = ColumnType.INT, length = 2)
	private Integer colShowType; 

	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, length = 19, update = false)
	private Date createTime;

	/**
	 * 排序号
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer orderId;

	/**
	 * 所属网站ID
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer siteId;
	
	/**
	 * 频道下栏目增删改排序标记位
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer flag = 0;
	
	/**
	 * 0=停用 1=启用
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer state = 1;

	/**
	 * 栏目ids
	 */
	@OnlyQuery
	private String colIds;

	/**
	 * 栏目namess
	 */
	@OnlyQuery
	private String colNames;

	/**
	 * 栏目排序ids
	 */
	private String orderIds;

	/**
	 * 栏目下信息展现方式串
	 */
	private String infoTypes;

	/**
	 * 栏目类型串
	 */
	private String colTypes;

	/**
	 * 栏目URL串
	 */
	private String colUrls;

	/**
	 * 互动类展现类型串
	 */
	private String actShowTypes;

	/**
	 * 互动类型串
	 */
	private String actTypes;

	/**
	 * 栏目状态串
	 */
	private String colStates;

	/**
	 * 访问权限串
	 */
	private String colVisits;

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

	public String getFirstPic() {
		return firstPic;
	}

	public void setFirstPic(String firstPic) {
		this.firstPic = firstPic;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getColIds() {
		return colIds;
	}

	public void setColIds(String colIds) {
		this.colIds = colIds;
	}

	public String getColNames() {
		return colNames;
	}

	public void setColNames(String colNames) {
		this.colNames = colNames;
	}

	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public String getInfoTypes() {
		return infoTypes;
	}

	public void setInfoTypes(String infoTypes) {
		this.infoTypes = infoTypes;
	}

	public String getColTypes() {
		return colTypes;
	}

	public void setColTypes(String colTypes) {
		this.colTypes = colTypes;
	}

	public String getColUrls() {
		return colUrls;
	}

	public void setColUrls(String colUrls) {
		this.colUrls = colUrls;
	}

	public String getActShowTypes() {
		return actShowTypes;
	}

	public void setActShowTypes(String actShowTypes) {
		this.actShowTypes = actShowTypes;
	}

	public String getActTypes() {
		return actTypes;
	}

	public void setActTypes(String actTypes) {
		this.actTypes = actTypes;
	}

	public String getColStates() {
		return colStates;
	}

	public void setColStates(String colStates) {
		this.colStates = colStates;
	}

	public String getColVisits() {
		return colVisits;
	}

	public Integer getColShowType() {
		return colShowType;
	}

	public void setColShowType(Integer colShowType) {
		this.colShowType = colShowType;
	}

	public void setColVisits(String colVisits) {
		this.colVisits = colVisits;
	}
 

	public Integer getIsVisit() {
		return isVisit;
	}

	public void setIsVisit(Integer isVisit) {
		this.isVisit = isVisit;
	}

	public String getCompoundPic() {
		return compoundPic;
	}

	public void setCompoundPic(String compoundPic) {
		this.compoundPic = compoundPic;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getChanneltype() {
		return channeltype;
	}

	public void setChanneltype(Integer channeltype) {
		this.channeltype = channeltype;
	} 
	
}