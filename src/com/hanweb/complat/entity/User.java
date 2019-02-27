package com.hanweb.complat.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.OnlyQuery;
import com.hanweb.common.annotation.Table;
import com.hanweb.complat.constant.Tables;

/**
 * 用户实体
 * 
 * @author ZhangC
 * 
 */
@Table(name = Tables.USER)
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3689648473909509892L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;

	/**
	 * uuid
	 */
	@Column(type = ColumnType.VARCHAR, length = 32, update = false, unique = true)
	private String uuid;

	/**
	 * 登陆名（唯一）
	 */
	@Column(type = ColumnType.VARCHAR, length = 100, update = false, unique = true)
	private String loginName;

	/**
	 * 密码
	 */
	@Column(type = ColumnType.VARCHAR, length = 100, update = false)
	private String pwd;

	/**
	 * 姓名
	 */
	@Column(type = ColumnType.VARCHAR, length = 100)
	private String name;

	/**
	 * 机构ID
	 */
	@Column(type = ColumnType.INT)
	private Integer groupId = 0;

	/**
	 * 年龄
	 */
	@Column(type = ColumnType.INT, length = 3)
	private Integer age = 0;

	/**
	 * 性别
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer sex = 0;

	/**
	 * 是否有效
	 */
	@Column(type = ColumnType.INT, length = 1, update = false)
	private Integer enable = 1;

	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createtime;

	/**
	 * 拼音的首字母
	 */
	@Column(type = ColumnType.VARCHAR)
	private String pinYin;

	/**
	 * 移动电话
	 */
	@Column(type = ColumnType.VARCHAR)
	private String mobile;

	/**
	 * 固定电话
	 */
	@Column(type = ColumnType.VARCHAR)
	private String phone;

	/**
	 * email
	 */
	@Column(type = ColumnType.VARCHAR)
	private String email;

	/**
	 * 地址
	 */
	@Column(type = ColumnType.VARCHAR)
	private String address;

	/**
	 * 联系方式
	 */
	@Column(type = ColumnType.VARCHAR)
	private String contact;

	/**
	 * 职务
	 */
	@Column(type = ColumnType.VARCHAR)
	private String headship;
	
	/**
	 * 常用登录地址
	 */
	@Column(type = ColumnType.VARCHAR)
	private String commonRegion;

	/**
	 * 动态码密钥
	 */
	@Column(type = ColumnType.VARCHAR, update = false)
	private String dynamicCodeKey;

	/**
	 * 用户类型0 后台 1 前台
	 */
	@Column(type = ColumnType.INT, length = 5, update = false)
	private Integer usertype;

	/**
	 * 提示问题
	 */
	@Column(type = ColumnType.VARCHAR, update = false)
	private String pwdquestion;

	/**
	 * 提示答案
	 */
	@Column(type = ColumnType.VARCHAR, update = false)
	private String pwdanswer;
	
	/**
	 * ip地址
	 */
	@Column(type = ColumnType.VARCHAR, update = false)
	private String ip;

	/**
	 * 登录时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date accesstime;
	
	/**
	 * 邮编
	 */
	@Column(type = ColumnType.VARCHAR)
	private String post;
	
	/**
	 * 排序id
	 */
	@Column(type = ColumnType.INT)
	private Integer orderid = 0;

	/**
	 * 用户昵称
	 */
	@Column(type = ColumnType.VARCHAR)
	private String nickname;
	
	/**
	 * 身份证号码
	 */
	@Column(type = ColumnType.VARCHAR)
	private String idcard;
	
	/**
	 * 密码重置验证码
	 */
	@Column(type = ColumnType.VARCHAR)
	private String verifycode;
	
	/**
	 * 网站ID，与网站表iid做关联
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer siteId = 0;
	
	/**
	 * 是否是APP工厂注册用户
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isappuser = 0;
	
	/**
	 * App用户激活状态
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer appstatus = 0;
	
	/**
	 * App应用是否有创建
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isappcreate = 0;
	
	/**
	 * App应用是否有上传
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isappupload = 0;
	
	/**
	 * 是否登录
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer isLogin = 0;
	
	/**
	 * 用户注册来源
	 * 0：后台注册  1：app注册  2：客户端注册
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer userRegFrom = 0;
	
	/**
	 * 邮编
	 */
	@Column(type = ColumnType.VARCHAR, length = 6)
	private String postCode = "";
	
	
	/** 
	 * 是否有效 0：不提醒，1：提醒
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer alertFlag = 0;
	
	/**
	 *  提醒间隔
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer alertInterval = 1;
	
	/** 用户登陆默认的网站ID */
	@Column(type = ColumnType.INT, length = 11)
	private Integer defaultWebId = 0; 
	
	/**
	 * 绑定微博的openid
	 */
	@Column(type = ColumnType.VARCHAR, length = 32, update = false)
	private String openId;
	
	/**
	 * 用户的微博类型0：sina 1：tencent 
     */
	@Column(type = ColumnType.INT, length = 1)
	private int weiboType = 0;
	
	/**
	 *客户端用户头像url
	 */
	@Column(type=ColumnType.VARCHAR, length=256)
	private String headPicUrl = "";
	/**
	 * 机构名称
	 */
	@OnlyQuery
	private String groupName;

	/**
	 * 机构编码
	 */
	private String codeId;

	/**
	 * 父机构名称
	 */
	@OnlyQuery
	private String parGroupName;

	/**
	 * 是否为系统管理员
	 */
	private Boolean isSysAdmin = false;

	/**
	 * 是否为机构管理员
	 */
	private Boolean isGroupAdmin = false;

	/**
	 * 机构管理员 管理的机构范围ID
	 */
	private Integer rangeId = 0;

	/**
	 * 机构管理员 管理的机构范围名称
	 */
	private String rangeName;

	/**
	 * 是否为网站管理员
	 */
	private Boolean isWebSiteAdmin = false;
	
	/**
	 * 用户的角色集合
	 */
	private List<Role> roleList;
	
	/**
	 * 原始密码
	 */
	private String oldpwd;

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	public String getPwdquestion() {
		return pwdquestion;
	}

	public void setPwdquestion(String pwdquestion) {
		this.pwdquestion = pwdquestion;
	}

	public String getPwdanswer() {
		return pwdanswer;
	}

	public void setPwdanswer(String pwdanswer) {
		this.pwdanswer = pwdanswer;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getAccesstime() {
		return accesstime;
	}

	public void setAccesstime(Date accesstime) {
		this.accesstime = accesstime;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getVerifycode() {
		return verifycode;
	}

	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getIsappuser() {
		return isappuser;
	}

	public void setIsappuser(Integer isappuser) {
		this.isappuser = isappuser;
	}

	public Integer getAppstatus() {
		return appstatus;
	}

	public void setAppstatus(Integer appstatus) {
		this.appstatus = appstatus;
	}

	public Integer getIsappcreate() {
		return isappcreate;
	}

	public void setIsappcreate(Integer isappcreate) {
		this.isappcreate = isappcreate;
	}

	public Integer getIsappupload() {
		return isappupload;
	}

	public void setIsappupload(Integer isappupload) {
		this.isappupload = isappupload;
	}

	public Integer getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(Integer isLogin) {
		this.isLogin = isLogin;
	}

	public Integer getUserRegFrom() {
		return userRegFrom;
	}

	public void setUserRegFrom(Integer userRegFrom) {
		this.userRegFrom = userRegFrom;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public Integer getAlertFlag() {
		return alertFlag;
	}

	public void setAlertFlag(Integer alertFlag) {
		this.alertFlag = alertFlag;
	}

	public Integer getAlertInterval() {
		return alertInterval;
	}

	public void setAlertInterval(Integer alertInterval) {
		this.alertInterval = alertInterval;
	}

	public Integer getDefaultWebId() {
		return defaultWebId;
	}

	public void setDefaultWebId(Integer defaultWebId) {
		this.defaultWebId = defaultWebId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getWeiboType() {
		return weiboType;
	}

	public void setWeiboType(int weiboType) {
		this.weiboType = weiboType;
	}

	public String getHeadPicUrl() {
		return headPicUrl;
	}

	public void setHeadPicUrl(String headPicUrl) {
		this.headPicUrl = headPicUrl;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getParGroupName() {
		return parGroupName;
	}

	public void setParGroupName(String parGroupName) {
		this.parGroupName = parGroupName;
	}

	public Boolean getIsSysAdmin() {
		return isSysAdmin;
	}

	public void setIsSysAdmin(Boolean isSysAdmin) {
		this.isSysAdmin = isSysAdmin;
	}

	public Boolean getIsGroupAdmin() {
		return isGroupAdmin;
	}

	public void setIsGroupAdmin(Boolean isGroupAdmin) {
		this.isGroupAdmin = isGroupAdmin;
	}

	public Integer getRangeId() {
		return rangeId;
	}

	public void setRangeId(Integer rangeId) {
		this.rangeId = rangeId;
	}

	public String getRangeName() {
		return rangeName;
	}

	public void setRangeName(String rangeName) {
		this.rangeName = rangeName;
	}

	public Boolean getIsWebSiteAdmin() {
		return isWebSiteAdmin;
	}

	public void setIsWebSiteAdmin(Boolean isWebSiteAdmin) {
		this.isWebSiteAdmin = isWebSiteAdmin;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<Right> getRightList() {
		return rightList;
	}

	public void setRightList(List<Right> rightList) {
		this.rightList = rightList;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 用户的权限集合
	 */
	private List<Right> rightList;

	/**
	 * 具有的权限字符串
	 */
	private Set<String> permissions = new HashSet<String>();

	
	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	public String getPinYin() {
		return pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHeadship() {
		return headship;
	}

	public void setHeadship(String headship) {
		this.headship = headship;
	}

	public String getCommonRegion() {
		return commonRegion;
	}

	public void setCommonRegion(String commonRegion) {
		this.commonRegion = commonRegion;
	}

	public String getDynamicCodeKey() {
		return dynamicCodeKey;
	}

	public void setDynamicCodeKey(String dynamicCodeKey) {
		this.dynamicCodeKey = dynamicCodeKey;
	}

	public String getOldpwd() {
		return oldpwd;
	}

	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}
	
	

}
