package com.hanweb.jmp.sys.entity.sites;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.OnlyQuery;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.entity.parameter.Parameter;

/**
 * 网站表
 * @author hq
 *
 */
@Table(name = Tables.SITE)
public class Site implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7416994770468683010L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;

	/**
	 * 网站名称
	 */
	@Column(type = ColumnType.VARCHAR, length = 100)
	private String name;

	/**
	 * 网站颜色
	 */
	@Column(type = ColumnType.VARCHAR, length = 30)
	private String color = "#008fd5";

	/**
	 * 推送权限等级送
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer pushLevel;

	/**
	 * jget分类或任务id
	 */
	@Column(type = ColumnType.VARCHAR, length = 100)
	private String taskId;

	/**
	 * jget分类或任务名称
	 */
	@Column(type = ColumnType.VARCHAR, length = 100)
	private String taskName;
	
	/**
	 * 同步类型  0：jget   1：jcms
	 */
	@Column(type= ColumnType.INT, length = 1)
	private Integer syntype=0;

	/**
	 * jgetLogId同步地址
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String jgetLogId;
	
	/**
	 * jget登录密码
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String jgetPassword;
	
	/**
	 * jget登录密码
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String jgetUrl;
	/**
	 * 网站url
	 */
	@Column(type = ColumnType.VARCHAR, update = false)
	private String url;

	/**
	 * 是否支持全文检索
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isSearch = 0;

	/**
	 * 全文检索索引标识
	 */
	@Column(type = ColumnType.VARCHAR)
	private String searchUrl;
	
	/**
	 * 全文检索索引网站id
	 */
	@Column(type = ColumnType.VARCHAR)
	private String searchWebId;
	
	/**
	 * 是否支持离线下载
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isOfflineZip = 0;
	
	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false, length = 19)
	private Date createTime;

	/**
	 * 应用来源:0.后台创建  1.自助创建
	 */
	@Column(type = ColumnType.INT, update = false, length = 1)
	private Integer appFrom = 0;

	/**
	 * 网站UUID
	 */
	@Column(type = ColumnType.VARCHAR, update = false, length = 100)
	private String uuid;

	/**
	 * 用户id
	 */
	@Column(type = ColumnType.INT, update = false, length = 11)
	private Integer userId;

	/**
	 * 是否订阅其他网站 0:否 1:是
	 */
	@Column(type = ColumnType.INT, update = false, length = 1)
	private Integer isBookOther;
	
	/**
	 * 是否支持注册
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isSupportReg = 0;
	
	/**
	 * 注册用户默认状态
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer regState;
	
	/**
	 * 网站下频道增删改排序变动标记位
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer chanFlag = 0;
	
	/**
	 * 网站下栏目增删改排序变动标记位
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer colFlag = 0;
	
	@Column(type = ColumnType.INT, length = 11)
	private Integer overall;
	
	/**
	 * 订阅栏目分类接口增删改排序变动标记位flag
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer bookColDimensionFlag = 0;
	
	/**
	 * 订阅栏目接口变动标记位flag
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer bookColFlag = 0;
	
	/**
	 * 卡片式角标变动标记位flag
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer cardDimensionFlag = 0;
	
	/**
	 * 网站详细表
	 */
	@OnlyQuery
	private Parameter parameter;

	/**
	 * 网站引导页表
	 */
	@OnlyQuery
	private SiteSplash siteSplash;

	/**
	 * 推送标记位
	 */
	@Column(type = ColumnType.INT, update = false)
	private Integer pushFlag = 0;
	
	/**
	 * 是否记录接口日志：0不记录    1记录
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isRecord = 0;
	
	/**
	 * 接口日志保留时间
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer holdTime;
	
	/**
	 * 是否对接口进行安全控制: 0不控制   1控制
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isSafeControl = 0;
	
	/**
	 * 是否支持商城: 0不支持   1支持
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isDiscount = 0;
	
	/**
	 * 订单过期天数
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer orderCancel = 3;
	
	/** 
	 * 手机登陆方式
	 *	1=手机登录
	 *	2=普通登录
	 *	3=第三方登录 
	 *	4=组合登录（支持手机登陆和第三方登录） 
	 */
	@Column(type = ColumnType.INT, length = 2)
	private Integer loginType = 2;
	
	/**
	 * 卡片维度操作标记位
	 */
	@Column(type = ColumnType.INT)
	private Integer cardsignflag;
	
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getPushLevel() {
		return pushLevel;
	}

	public void setPushLevel(Integer pushLevel) {
		this.pushLevel = pushLevel;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getIsSearch() {
		return isSearch;
	}

	public void setIsSearch(Integer isSearch) {
		this.isSearch = isSearch;
	}

	public String getSearchUrl() {
		return searchUrl;
	}

	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getAppFrom() {
		return appFrom;
	}

	public void setAppFrom(Integer appFrom) {
		this.appFrom = appFrom;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public SiteSplash getSiteSplash() {
		return siteSplash;
	}

	public void setSiteSplash(SiteSplash siteSplash) {
		this.siteSplash = siteSplash;
	}

	public Integer getIsBookOther() {
		return isBookOther;
	}

	public void setIsBookOther(Integer isBookOther) {
		this.isBookOther = isBookOther;
	}

	public Integer getIsSupportReg() {
		return isSupportReg;
	}

	public void setIsSupportReg(Integer isSupportReg) {
		this.isSupportReg = isSupportReg;
	}

	public Integer getRegState() {
		return regState;
	}

	public void setRegState(Integer regState) {
		this.regState = regState;
	}

	public Integer getPushFlag() {
		return pushFlag;
	}

	public void setPushFlag(Integer pushFlag) {
		this.pushFlag = pushFlag;
	}

	public Integer getChanFlag() {
		return chanFlag;
	}

	public void setChanFlag(Integer chanFlag) {
		this.chanFlag = chanFlag;
	}

	public Integer getColFlag() {
		return colFlag;
	}

	public void setColFlag(Integer colFlag) {
		this.colFlag = colFlag;
	}

	public String getSearchWebId() {
		return searchWebId;
	}

	public void setSearchWebId(String searchWebId) {
		this.searchWebId = searchWebId;
	}

	public Integer getIsOfflineZip() {
		return isOfflineZip;
	}

	public void setIsOfflineZip(Integer isOfflineZip) {
		this.isOfflineZip = isOfflineZip;
	}

	public Integer getIsRecord() {
		return isRecord;
	}

	public void setIsRecord(Integer isRecord) {
		this.isRecord = isRecord;
	}

	public Integer getHoldTime() {
		return holdTime;
	}

	public void setHoldTime(Integer holdTime) {
		this.holdTime = holdTime;
	}

	public Integer getIsSafeControl() {
		return isSafeControl;
	}

	public void setIsSafeControl(Integer isSafeControl) {
		this.isSafeControl = isSafeControl;
	}

	public Integer getIsDiscount() {
		return isDiscount;
	}

	public void setIsDiscount(Integer isDiscount) {
		this.isDiscount = isDiscount;
	}

	public Integer getOrderCancel() {
		return orderCancel;
	}

	public void setOrderCancel(Integer orderCancel) {
		this.orderCancel = orderCancel;
	}

	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	public Integer getSyntype() {
		return syntype;
	}

	public void setSyntype(Integer syntype) {
		this.syntype = syntype;
	}

	public String getJgetLogId() {
		return jgetLogId;
	}

	public void setJgetLogId(String jgetLogId) {
		this.jgetLogId = jgetLogId;
	}

	public String getJgetPassword() {
		return jgetPassword;
	}

	public void setJgetPassword(String jgetPassword) {
		this.jgetPassword = jgetPassword;
	}

	public String getJgetUrl() {
		return jgetUrl;
	}

	public void setJgetUrl(String jgetUrl) {
		this.jgetUrl = jgetUrl;
	}

	public Integer getOverall() {
		return overall;
	}

	public void setOverall(Integer overall) {
		this.overall = overall;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	public Parameter getParameter() {
		return parameter;
	}

	public void setBookColFlag(Integer bookColFlag) {
		this.bookColFlag = bookColFlag;
	}

	public Integer getBookColFlag() {
		return bookColFlag;
	}

	public void setBookColDimensionFlag(Integer bookColDimensionFlag) {
		this.bookColDimensionFlag = bookColDimensionFlag;
	}

	public Integer getBookColDimensionFlag() {
		return bookColDimensionFlag;
	}

	public void setCardDimensionFlag(Integer cardDimensionFlag) {
		this.cardDimensionFlag = cardDimensionFlag;
	}

	public Integer getCardDimensionFlag() {
		return cardDimensionFlag;
	}

    public void setCardsignflag(Integer cardsignflag) {
        this.cardsignflag = cardsignflag;
    }

    public Integer getCardsignflag() {
        return cardsignflag;
    }

	
}