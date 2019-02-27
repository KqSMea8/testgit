package com.hanweb.jmp.cms.entity.cols;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.constant.Tables;

/**
 * 栏目实体
 * @author hq
 */
@Table(name = Tables.COL)
public class Col implements Serializable, Cloneable{

	private static final long serialVersionUID = 8020206996027596982L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11)
	private Integer iid;

	/**
	 * 标题
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String name;

	/**
	 * 用户ID,与用户表iid做关联
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer userId;

	/**
	 * 栏目类型 1、类目  2、信息列表  3、应用
	 */
	@Column(type = ColumnType.INT, length = 2)
	private Integer type;

	/**
	 * 首图展现方式 1、大图填充 2、按图片比例缩放空白 3、不展现第一张大图
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer firstPicShow;

	/**
	 * 父栏目id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer pid;

	/**
	 * 父栏目名称
	 */
	private String pname;
	
	/**
	 * 审核方式： 1：手动 2：立即 3：限时审核
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer auditType;

	/**
	 * 限时审核时间(分钟)
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer limitTime;

	/**
	 * 列表布局 1, 纯标题
		2, 标题+时间+来源
		3, 标题+时间+摘要
		4, 左侧一张图片
		5, 右侧一张图片
		6, 三张图文混排
		7, 一张大图+标题格式
		8, 纯图左一+右二
		9, 纯图左二+右一
		10, 标题+右图+评论数
	 */
	@Column(type = ColumnType.INT, length = 5)
	private Integer infoListType;
	
	/**
	 * 内容页布局
	 */
	@Column(type = ColumnType.INT, length = 5)
	private Integer infoContentType;
	
	/**
	 * 布局方式
	 */
	@Column(type = ColumnType.INT, length = 5)
	private Integer colListType;
	
	/**
	 * 信息标题
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String infoTitle;

	/**
	 * 栏目Icon路径
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String iconPath="";

	/**
	 * 状态： 1：启用 0：停用
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer enable;

	/**
	 * 栏目备注
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String spec;

	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createTime; 
	
	/**
	 * 互动类型 1=领导信箱2=意见征集3=网上调查5=天气预报6=报料7=微博25=公交小哥
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer hdType;
	
	/**
	 * 微博类型 1 新浪微博 2 腾讯微博
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer blogType;
	
	/**
	 * 微博昵称
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String nickName;
	
	/**
	 * 接口地址
	 */
	@Column(type = ColumnType.VARCHAR, length = 1000)
	private String actUrl;
	
	/**
	 * 系统域名
	 */
	@Column(type = ColumnType.VARCHAR, length = 1000)
	private String domain;
	
	/**
	 * 背景颜色
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String bgColor;

	/**
	 * 所属网站ID，与网站表做关联
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer siteId;

	/**
	 * 排序ID
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer orderId;

	/**
	 * 订阅排序ID
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer bookorderId;

	/**
	 * 栏目key值
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer flag = 0;

	/**
	 * 栏目下背景图、图标变动标记位
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String imgUuid;

	/**
	 * 信息同步周期（分钟）
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer synPeriod=10;

	/**
	 * 信息同步开始时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date startTime;

	/**
	 * 是否支持全文检索 0-否 1-是
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isJsearch;

	/**
	 * 栏目访问权限 0-匿名访问 1-登录访问
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isVisit=0;
	
	/**
	 * 离线信息条数：0条、20条、30条、50条、100条，默认0条
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer offlineNum=0;
	
	/**
	 * 信息变动总数
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer infoNum=0;
	
	/**
	 * 信息最后变动时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date lastUpdateTime;
	
	/**
	 * 信息列表展现方式 
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer listType;
	 
	/**
	 * 栏目层级
	 */
	@Column(type = ColumnType.INT, length = 10)
	private Integer collevel;
	
	/**
	 * 栏目下信息布局样式 1：无banner普通列表栏目2：有banner普通列表栏目3：微信列表栏目
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer commonType;
	
	/**
	 * 当commonType=2时，该栏目所绑定的banner栏目id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer bannerId;
	
	/**
	 * 轻应用id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer lightAppId;
	
	/**
	 * 新的轻应用id  绑定轻应用支持多选，改变数据类型可能会造成数据丢失，故新增字段
	 */
	@Column(type = ColumnType.VARCHAR)
	private String newLightAppId;
	
	/**
	 * 是否有子机构
	 */
	private Boolean isParent = false;

	/**
	 * 任务关联主键ID
	 */
	private Integer colRelationIid;
	
	/**
	 * 任务ID
	 */
	private Integer taskId;

	/**
	 * 任务名称
	 */
	private String taskName = ""; 

	/**
	 * 栏目首图发生变化标记量 
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String firstPicUuid = "";

	/**
	 * 已经撤审的信息id串
	 */
	private String unAuditInfoIds;

	/**
	 * 栏目下信息列表
	 */
	private ArrayList<Info> infoList = new ArrayList<Info>();
	
	/**
	 * 绑定banner栏目的栏目名称
	 */
	private String bannerName = "";
	
	/**
	 * 绑定轻应用的应用名称
	 */
	@Column(type = ColumnType.VARCHAR , length = 255)
	private String lightAppName = "";
	
	/**
	 * 绑定轻应用的应用url
	 */
	private String lightAppUrl = "";
	
	/**
	 * 订阅栏目所属角标id
	 */
	private String bookDimensionId = "";
	
	/**
	 * 是否支持评论： 1支持    0 不支持  
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isComment = 1;
	
	/**
	 * 栏目下信息的排序方式： 0自定义排序    1按照发布时间排序
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer sortType = 0;
	
	/**
	 *客户端栏目下面是否显示检索： 1：支持0：不支持
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer issearch;
	
	/**
	 * 卡片式角标展现信息条数    0=块显示4条及以下 1= 块显示5条 2= 块显示6条及以上
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer cardType = 0;
	
	/**
	 * 卡片式角标展现样式		0= 名称+时间	  1=时间		2=名称		3=无
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer showType = 0;
	
	/**
	 * 信息同步源url
	 */
	@Column(type = ColumnType.VARCHAR , length = 255)
	private String sourceurl;
	
	/**
	 * 信息同步源用户名
	 */
	@Column(type = ColumnType.VARCHAR , length = 255)
	private String sourcename;
	
	/**
	 * 信息同步源用户密码
	 */
	@Column(type = ColumnType.VARCHAR , length = 255)
	private String sourcepwd;
	
	/**
	 * 信息同步源类型  1：get   2：jcms
	 */
	@Column(type = ColumnType.INT , length = 11)
	private Integer sourcetype;
	
	/**
	 * 栏目类型  C:复制 Q:引用
	 * @return
	 */
	@Column(type = ColumnType.VARCHAR , length = 255)
    private String colType = "";
	
	/**
     * 应用Icon路径
     */
    @Column(type = ColumnType.VARCHAR, length = 255)
    private String appIconPath = "";
    
    /**
     * 栏目过滤规则
     */
    @Column(type = ColumnType.VARCHAR, length = 255)
    private String filterReg = "";
    
    /**
	 * 同步唯一标识
	 */
	@Column(type = ColumnType.VARCHAR )
	private String keyValue;
	
	/**
	 * 渠道id （为了相同渠道url）
	 */
	@Column(type = ColumnType.INT)
	private int ditchId;
	
	@Column(type = ColumnType.VARCHAR)
	private String appLayOut;
	
	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public Integer getShowType() {
		return showType;
	}

	public void setShowType(Integer showType) {
		this.showType = showType;
	}

	public String getFirstPicUuid() {
		return firstPicUuid;
	}

	public String getUnAuditInfoIds() {
		return unAuditInfoIds;
	}

	public void setUnAuditInfoIds(String unAuditInfoIds) {
		this.unAuditInfoIds = unAuditInfoIds;
	}

	public void setFirstPicUuid(String firstPicUuid) {
		this.firstPicUuid = firstPicUuid;
	}
 
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getFirstPicShow() {
		return firstPicShow;
	}

	public void setFirstPicShow(Integer firstPicShow) {
		this.firstPicShow = firstPicShow;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Integer getAuditType() {
		return auditType;
	}

	public void setAuditType(Integer auditType) {
		this.auditType = auditType;
	}

	public Integer getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Integer limitTime) {
		this.limitTime = limitTime;
	}

	public String getInfoTitle() {
		return infoTitle;
	}

	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}


	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	} 
 
	public Integer getBlogType() {
		return blogType;
	}

	public void setBlogType(Integer blogType) {
		this.blogType = blogType;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getImgUuid() {
		return imgUuid;
	}

	public void setImgUuid(String imgUuid) {
		this.imgUuid = imgUuid;
	}

	public Integer getSynPeriod() {
		return synPeriod;
	}

	public void setSynPeriod(Integer synPeriod) {
		this.synPeriod = synPeriod;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getIsJsearch() {
		return isJsearch;
	}

	public void setIsJsearch(Integer isJsearch) {
		this.isJsearch = isJsearch;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public Integer getColRelationIid() {
		return colRelationIid;
	}

	public void setColRelationIid(Integer colRelationIid) {
		this.colRelationIid = colRelationIid;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public ArrayList<Info> getInfoList() {
		return infoList;
	}

	public void setInfoList(ArrayList<Info> infoList) {
		this.infoList = infoList;
	}

	public Integer getIsVisit() {
		return isVisit;
	}

	public void setIsVisit(Integer isVisit) {
		this.isVisit = isVisit;
	}

	public Integer getOfflineNum() {
		return offlineNum;
	}

	public void setOfflineNum(Integer offlineNum) {
		this.offlineNum = offlineNum;
	}

	public Integer getListType() {
		return listType;
	}

	public void setListType(Integer listType) {
		this.listType = listType;
	}

	public Integer getCollevel() {
		return collevel;
	}

	public void setCollevel(Integer collevel) {
		this.collevel = collevel;
	}

	public Integer getInfoListType() {
		return infoListType;
	}

	public void setInfoListType(Integer infoListType) {
		this.infoListType = infoListType;
	}

	public Integer getInfoContentType() {
		return infoContentType;
	}

	public void setInfoContentType(Integer infoContentType) {
		this.infoContentType = infoContentType;
	}

	public Integer getColListType() {
		return colListType;
	}

	public void setColListType(Integer colListType) {
		this.colListType = colListType;
	}

	public Integer getHdType() {
		return hdType;
	}

	public void setHdType(Integer hdType) {
		this.hdType = hdType;
	}

	public String getActUrl() {
		return actUrl;
	}

	public void setActUrl(String actUrl) {
		this.actUrl = actUrl;
	}

	public Integer getCommonType() {
		return commonType;
	}

	public void setCommonType(Integer commonType) {
		this.commonType = commonType;
	}

	public Integer getBannerId() {
		return bannerId;
	}

	public void setBannerId(Integer bannerId) {
		this.bannerId = bannerId;
	}

	public String getBannerName() {
		return bannerName;
	}

	public void setBannerName(String bannerName) {
		this.bannerName = bannerName;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getIsComment() {
		return isComment;
	}

	public void setIsComment(Integer isComment) {
		this.isComment = isComment;
	}

	public Integer getSortType() {
		return sortType;
	}

	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}

	public Integer getIssearch() {
		return issearch;
	}

	public void setIssearch(Integer issearch) {
		this.issearch = issearch;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public Integer getInfoNum() {
		return infoNum;
	}

	public void setInfoNum(Integer infoNum) {
		this.infoNum = infoNum;
	}

	public Integer getLightAppId() {
		return lightAppId;
	}

	public void setLightAppId(Integer lightAppId) {
		this.lightAppId = lightAppId;
	}
	
	public String getNewLightAppId() {
		return newLightAppId;
	}

	public void setNewLightAppId(String newLightAppId) {
		this.newLightAppId = newLightAppId;
	}

	public String getLightAppName() {
		return lightAppName;
	}

	public void setLightAppName(String lightAppName) {
		this.lightAppName = lightAppName;
	}

	public String getLightAppUrl() {
		return lightAppUrl;
	}

	public void setLightAppUrl(String lightAppUrl) {
		this.lightAppUrl = lightAppUrl;
	}

	public void setBookorderId(Integer bookorderId) {
		this.bookorderId = bookorderId;
	}

	public Integer getBookorderId() {
		return bookorderId;
	}

	public void setBookDimensionId(String bookDimensionId) {
		this.bookDimensionId = bookDimensionId;
	}

	public String getBookDimensionId() {
		return bookDimensionId;
	}

	public String getSourceurl() {
		return sourceurl;
	}

	public void setSourceurl(String sourceurl) {
		this.sourceurl = sourceurl;
	}

	public String getSourcename() {
		return sourcename;
	}

	public void setSourcename(String sourcename) {
		this.sourcename = sourcename;
	}

	public String getSourcepwd() {
		return sourcepwd;
	}

	public void setSourcepwd(String sourcepwd) {
		this.sourcepwd = sourcepwd;
	}

	public Integer getSourcetype() {
		return sourcetype;
	}

	public void setSourcetype(Integer sourcetype) {
		this.sourcetype = sourcetype;
	}

    public void setColType(String colType) {
        this.colType = colType;
    }

    public String getColType() {
        return colType;
    }

    public void setAppIconPath(String appIconPath) {
        this.appIconPath = appIconPath;
    }

    public String getAppIconPath() {
        return appIconPath;
    }
    
    public Object clone() {  
        Col o = null;  
        try {  
            o = (Col) super.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return o;  
    }

    public void setFilterReg(String filterReg) {
        this.filterReg = filterReg;
    }

    public String getFilterReg() {
        return filterReg;
    }

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public int getDitchId() {
		return ditchId;
	}

	public void setDitchId(int ditchId) {
		this.ditchId = ditchId;
	}

	public String getAppLayOut() {
		return appLayOut;
	}

	public void setAppLayOut(String appLayOut) {
		this.appLayOut = appLayOut;
	}
    
    

}