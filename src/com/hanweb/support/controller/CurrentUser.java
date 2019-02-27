package com.hanweb.support.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.hanweb.complat.entity.Right;
import com.hanweb.complat.entity.Role;
import com.hanweb.complat.entity.User;

/**
 * 
 * @author cj
 *
 */
public class CurrentUser extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * iid
	 */
	private Integer iid;
	
	/**
	 * 惟一码
	 */
	private String uuid;
	
	/**
	 * 登录名
	 */
	private String loginName;
	
	/**
	 * 密码
	 */
	private String pwd;
	
	/**
	 * 密码等级
	 */
	private Integer pwdLevel;
	
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 机构id
	 */
	private Integer groupId;
	
	/**
	 * 年龄
	 */
	private Integer age;
	
	/**
	 * 姓名
	 */
	private Integer sex;
	
	/**
	 * 是否启用
	 */
	private Integer enable;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 联系方式
	 */
	private String phone;
	
	/**
	 * 邮箱地址
	 */
	private String email;
	
	/**
	 * 家庭地址
	 */
	private String address;
	
	/**
	 * 邮寄地址
	 */
	private String post;
	
	/**
	 * contact
	 */
	private String contact;
	
	/**
	 * ip
	 */
	private String ip;
	
	/**
	 * accessTime
	 */
	private Date accessTime;
	
	/**
	 * rangeId
	 */
	private Integer rangeId;
	
	/**
	 * rangeName
	 */
	private String rangeName;

	/**
	 * sessionId
	 */
	private String sessionId;

	/**
	 * session
	 */
	private HttpSession session;
	
	/**
	 * 是否为系统管理员
	 */
	private boolean sysAdmin = false;

	/**
	 * 是否为机构管理员
	 */
	private boolean groupAdmin = false;

	/**
	 * 用户的角色集合
	 */
	private List<Role> roleList;

	/**
	 * 用户的权限集合
	 */
	private List<Right> rightList;

	/**
	 * 具有的权限字符串
	 */
	private Set<String> permissions = new HashSet<String>();
	
	/**
	 * 具有的权限的栏目
	 */
	private Set<String> colids = new HashSet<String>();
	
	/**
	 * 网站id
	 */
	private Integer siteId;
	
	/**
	 * 是否为网站管理员
	 */
	private Boolean isWebSiteAdmin = false;
	
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

	public Integer getPwdLevel() {
		return pwdLevel;
	}

	public void setPwdLevel(Integer pwdLevel) {
		this.pwdLevel = pwdLevel;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
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

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public boolean isSysAdmin() {
		return sysAdmin;
	}

	public void setSysAdmin(boolean sysAdmin) {
		this.sysAdmin = sysAdmin;
	}

	public boolean isGroupAdmin() {
		return groupAdmin;
	}

	public void setGroupAdmin(boolean groupAdmin) {
		this.groupAdmin = groupAdmin;
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

	public Set<String> getColids() {
		return colids;
	}

	public void setColids(Set<String> colids) {
		this.colids = colids;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Boolean getIsWebSiteAdmin() {
		return isWebSiteAdmin;
	}

	public void setIsWebSiteAdmin(Boolean isWebSiteAdmin) {
		this.isWebSiteAdmin = isWebSiteAdmin;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}