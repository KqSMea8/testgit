package com.hanweb.jmp.apps.entity.broke;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.OnlyQuery;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.BROKE)
public class Broke implements Serializable {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -2085122619565275424L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;

	/**
	 * 站点id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer siteId;

	/**
	 * 信息分类
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer classId;

	/**
	 * 信息审核状态  0:未审核 1:审核
	 */
	@Column(type = ColumnType.INT, length = 2)
	private Integer isAudit; 

	/**
	 * 信息标题
	 */
	@Column(type = ColumnType.VARCHAR, length = 600, update = false)
	private String title;
	
	/**
	 * 文本内容
	 */
	@Column(type = ColumnType.VARCHAR, length = 3000)
	private String content;
 
	/**
	 * 信息入库时间
	 */
	@Column(type = ColumnType.CHAR, update = false)
	private Date createTime;

	/**
	 * 图片路径
	 */
	@Column(type = ColumnType.VARCHAR, update = false, length = 255)
	private String picPath;

	/**
	 * 图片路径
	 */
	@Column(type = ColumnType.VARCHAR, update = false, length = 255)
	private String picPath1;
	
	/**
	 * 图片路径
	 */
	@Column(type = ColumnType.VARCHAR, update = false, length = 255)
	private String picPath2;
	
	/**
	 * 图片路径
	 */
	@Column(type = ColumnType.VARCHAR, update = false, length = 255)
	private String picPath3;
	
	/**
	 * 音频路径
	 */
	@Column(type = ColumnType.VARCHAR, update = false, length = 255)
	private String audioPath;

	/**
	 * 视频路径
	 */
	@Column(type = ColumnType.VARCHAR, update = false, length = 255)
	private String videoPath; 

	/**
	 * 回复反馈内容
	 */
	@Column(type = ColumnType.VARCHAR)
	private String reply;
	
	/**
	 * 回复反馈时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date replyTime; 
	
	/**
	 * 用户登录名
	 */
	@Column(type = ColumnType.VARCHAR, length = 100, update = false)
	private String loginId;
	
	/**
	 * 惟一码
	 */
	@Column(type = ColumnType.VARCHAR, update = false)
	private String uuid;
	
	/**
	 * 是否公开
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isOpen;
	
	/**
	 * 客户端类型
	 */
	@Column(type = ColumnType.INT, length = 1, update = false)
	private Integer clientType;
	
	/**
	 * 联系方式
	 */
	@Column(type = ColumnType.VARCHAR, length = 255, update = false)
	private String contact;
	
	/**
	 *  0: 客户端      1：后台
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isBack = 0;
	
	/**
	 * ip地址
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String ip;

	/**
	 * 分类名称
	 */
	@OnlyQuery
	private String classify="";
	
	/**
	 * 后台管理员是否已回复
	 */
	private Boolean isReply = false;
	
	/**
	 * 用户姓名
	 */
	private String name;
	
	/**
	 * 用户头像
	 */
	private String headurl;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
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

	public Integer getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
  

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}  
	
	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getAudioPath() {
		return audioPath;
	}

	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public Boolean getIsReply() {
		return isReply;
	}

	public void setIsReply(Boolean isReply) {
		this.isReply = isReply;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public Integer getIsBack() {
		return isBack;
	}

	public void setIsBack(Integer isBack) {
		this.isBack = isBack;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPicPath1(String picPath1) {
		this.picPath1 = picPath1;
	}

	public String getPicPath1() {
		return picPath1;
	}

	public void setPicPath2(String picPath2) {
		this.picPath2 = picPath2;
	}

	public String getPicPath2() {
		return picPath2;
	}

	public void setPicPath3(String picPath3) {
		this.picPath3 = picPath3;
	}

	public String getPicPath3() {
		return picPath3;
	}
	
}