package com.hanweb.jmp.cms.entity.infos;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.OnlyQuery;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 信息实体
 * @author hq
 */
@Table(name = Tables.INFO) 
public class Info implements Serializable, Cloneable {

	private static final long serialVersionUID = -195411541831362087L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11)
	private Integer iid;

	/**
	 * 栏目id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer colId;

	/**
	 * 网站id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer siteId;

	/**
	 * 标题
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String title;

	/**
	 * 副标题
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String subTitle;

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
	 * jget信息id
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String jgetId;

	/**
	 * 所属url
	 */
	@Column(type = ColumnType.VARCHAR, length = 600)
	private String url;

	/**
	 * 摘要
	 */
	@Column(type = ColumnType.VARCHAR, length = 900)
	private String abs;

	/**
	 * 状态
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer status;

	/**
	 * 原图路径（手动上传的首图）
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String firstPicPath;
	
	/**
	 * 原图路径(从正文中获取的首图)
	 */
	@Column(type = ColumnType.VARCHAR, length = 3000)
	private String orignalPicpath;

	/**
	 * 是否置顶，0-否，1-是
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer topId = 0;

	/**
	 * 信息首图变动标记
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String imguuid;
	
	/**
	 * 信息置顶时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date topTime;

	/**
	 * 信息排序ID
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer orderid = 0;

	/**
	 * 坐标类型 1-地点，2-路径，3-街景
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer pointType = 0;

	/**
	 * 坐标
	 */
	@Column(type = ColumnType.VARCHAR, length = 50)
	private String pointLocation;

	/**
	 * 地址
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String address;

	/** 信息推荐时间 */
	@Column(type = ColumnType.DATETIME)
	private Date recomtime;

	/** 是否被推荐 1、被推荐   0、未被推荐*/
	@Column(type = ColumnType.INT, length = 1)
	private Integer recommend = 0;

	/**
	 * 来源
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String source = "";

	/**
	 * 作者
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String author = "";

	/**
	 * 上传的视频/手动填写的视频地址
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String vedio = "";

	/**
	 * 场景式栏目上传的音频
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String audio = "";

	/**
	 * 音频时长
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String audioTime="";
	
	/**
	 * xml保存路径
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String path;
	
	/**
	 * 信息展现方式 
	 * 1.纯标题 2.标题+时间+来源  3.标题+时间+摘要
	 * 4.左侧一张图片 5.右侧一张图片 6.三张图文混排  7.一张大图+标题格式
	 * 8.纯图左一+右二  9.纯图左二+右一
	 */
	@Column(type = ColumnType.INT, length = 5)
	private Integer infoListType;
	
	/**
	 * 信息内容展现方式
	 * 1.经典正文页  2.斗状来源文章页 3.创意文章页 4.酷图文章页
	 * 5.外链展现 6.视频直接打开 7.导航 8.专题(栏目) 9.专题(频道) 10.专题(调查)
	 */
	@Column(type = ColumnType.INT, length = 5)
	private Integer infoContentType;
	
	/**
	 * 推送时间用于展示
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String pushtimeshow;
	
	/**
	 * 信息内容
	 */
	private String content = "";
	
	/**
	 * 摘要
	 */
	@Column(type = ColumnType.VARCHAR, length = 50)
	private String  summary = "";
	
	/**
	 * 专题ID
	 */
	@Column(type = ColumnType.INT)
	private Integer ztId = 0;
	
	/**
	 * 专题类型 0：栏目 1：频道
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer pushState = 0;
	
	/**
	 * 信息内容
	 */
	private String ztName = "";
	
	/**
	 * 信息推送时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date pushTime;
	
	/**
	 * 离线消息保留时间
	 */
	@Column(type = ColumnType.INT)
	private Integer pushOfl = 0;
	
	/**
	 * 信息删除状态   0代表未删除   1代表删除并放入回收站
	 */
	@Column(type = ColumnType.INT)
	private Integer isRemove = 0;
	
	/**
	 * 信息删除时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date removeTime;
	
	/**
	 * 视频路径
	 */
	private String vedioPath;
	
	/**
	 * 图片名称
	 */
	private String picname="";
	
	/**
	 * 图片描述
	 */
	private String picdesc=""; 
	 
	/**
	 * 栏目类型
	 */
	private Integer colType;
	
	/**
	 * 信息扩展字段
	 */
	private Map<String, Object> infoExpand = new HashMap<String, Object>();
	
	/**
	 * 标签  id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer tagid;

	/**
	 * 标签 名称
	 */ 
	private String tagname; 
	
	/**
	 * 标签颜色
	 */ 
	private String tagcolor; 
	 
	/**
	 * 评论数
	 */ 
	private Integer commentcount = 0;
	
	/**
     * 标签  namess
     */
    @OnlyQuery
    private String tagNames;
    
    /**
     * 源信息id
     */
    @Column(type = ColumnType.INT, length = 11)
    private Integer sourceid;
    
    /**
     * 信息类型 C:复制  Q:引用
     * @return
     */
    @Column(type = ColumnType.VARCHAR, length = 255)
    private String infoType = "";

	public String getTagNames() {
		return tagNames;
	}

	public void setTagNames(String tagNames) {
		this.tagNames = tagNames;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
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

	public String getJgetId() {
		return jgetId;
	}

	public void setJgetId(String jgetId) {
		this.jgetId = jgetId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrignalPicpath() {
		return orignalPicpath;
	}

	public void setOrignalPicpath(String orignalPicpath) {
		this.orignalPicpath = orignalPicpath;
	}

	public String getVedioPath() {
		return vedioPath;
	}

	public void setVedioPath(String vedioPath) {
		this.vedioPath = vedioPath;
	}


	public Integer getTopId() {
		return topId;
	}

	public void setTopId(Integer topId) {
		this.topId = topId;
	}

	public Date getTopTime() {
		return topTime;
	}

	public void setTopTime(Date topTime) {
		this.topTime = topTime;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public Integer getPointType() {
		return pointType;
	}

	public void setPointType(Integer pointType) {
		this.pointType = pointType;
	}

	public String getPointLocation() {
		return pointLocation;
	}

	public void setPointLocation(String pointLocation) {
		this.pointLocation = pointLocation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getRecomtime() {
		return recomtime;
	}

	public void setRecomtime(Date recomtime) {
		this.recomtime = recomtime;
	}

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getFirstPicPath() {
		return firstPicPath;
	}

	public void setFirstPicPath(String firstPicPath) {
		this.firstPicPath = firstPicPath;
	}

	public String getVedio() {
		return vedio;
	}

	public void setVedio(String vedio) {
		this.vedio = vedio;
	}

	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Map<String, Object> getInfoExpand() {
		return infoExpand;
	}

	public void setInfoExpand(Map<String, Object> infoExpand) {
		this.infoExpand = infoExpand;
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

	public Integer getZtId() {
		return ztId;
	}

	public void setZtId(Integer ztId) {
		this.ztId = ztId;
	}

	public Integer getPushState() {
		return pushState;
	}

	public void setPushState(Integer pushState) {
		this.pushState = pushState;
	}

	
	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	public Integer getPushOfl() {
		return pushOfl;
	}

	public void setPushOfl(Integer pushOfl) {
		this.pushOfl = pushOfl;
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

	public Integer getColType() {
		return colType;
	}

	public void setColType(Integer colType) {
		this.colType = colType;
	}

	public String getZtName() {
		return ztName;
	}

	public void setZtName(String ztName) {
		this.ztName = ztName;
	}

	public Integer getIsRemove() {
		return isRemove;
	}

	public void setIsRemove(Integer isRemove) {
		this.isRemove = isRemove;
	}

	public Date getRemoveTime() {
		return removeTime;
	}

	public void setRemoveTime(Date removeTime) {
		this.removeTime = removeTime;
	} 

	public Integer getTagid() {
		return tagid;
	}

	public void setTagid(Integer tagid) {
		this.tagid = tagid;
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	public Integer getCommentcount() {
		return commentcount;
	}

	public void setCommentcount(Integer commentcount) {
		this.commentcount = commentcount;
	}

	public String getTagcolor() {
		return tagcolor;
	}

	public void setTagcolor(String tagcolor) {
		this.tagcolor = tagcolor;
	}

	public String getAudioTime() {
		return audioTime;
	}

	public void setAudioTime(String audioTime) {
		this.audioTime = audioTime;
	}

    public void setImguuid(String imguuid) {
        this.imguuid = imguuid;
    }

    public String getImguuid() {
        return imguuid;
    }

    public void setSourceid(Integer sourceid) {
        this.sourceid = sourceid;
    }

    public Integer getSourceid() {
        return sourceid;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getInfoType() {
        return infoType;
    }
    
    public Object clone() {  
        Info o = null;  
        try {  
            o = (Info) super.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return o;  
    }

	public String getPushtimeshow() {
		return pushtimeshow;
	}

	public void setPushtimeshow(String pushtimeshow) {
		this.pushtimeshow = pushtimeshow;
	}
    
    
	
}