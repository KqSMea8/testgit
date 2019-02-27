package com.hanweb.complat.entity;

import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.complat.constant.Tables;

/**
 * 外网用户实体
 * 
 * @author ZhangC
 * 
 */
@Table(name = Tables.OUTSIDEUSER)
public class OutsideUser implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2506351625961775795L;

	/**
	 * 主键id
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;

	/**
	 * uuid
	 */
	@Column(type = ColumnType.VARCHAR, length = 50, update = false)
	private String uuid;

	/**
	 * 登录id
	 */
	@Column(type = ColumnType.VARCHAR, update = false)
	private String loginName;

	/**
	 * 登录密码
	 */
	@Column(type = ColumnType.VARCHAR, update = false)
	private String pwd;

	/**
	 * 姓名
	 */
	@Column(type = ColumnType.VARCHAR)
	private String name;

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
	@Column(type = ColumnType.INT, length = 1)
	private Integer enable = 1;

	/**
	 * 全拼
	 */
	@Column(type = ColumnType.VARCHAR)
	private String pinYin;

	/**
	 * 证件类型 1 身份证 <br>
	 * 2 教师证<br>
	 * 3 军官证<br>
	 * 4 其他
	 */
	@Column(type = ColumnType.INT, length = 1)
	private Integer papersType = 1;

	/**
	 * 证件编号
	 */
	@Column(type = ColumnType.VARCHAR)
	private String papersNumber;

	/**
	 * 备注
	 */
	@Column(type = ColumnType.VARCHAR)
	private String description;

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
	 * 联系方式
	 */
	@Column(type = ColumnType.VARCHAR)
	private String contact;

	/**
	 * 联系住址
	 */
	@Column(type = ColumnType.VARCHAR)
	private String address;

	/**
	 * 邮编
	 */
	@Column(type = ColumnType.VARCHAR)
	private String post;

	/**
	 * 工作单位
	 */
	@Column(type = ColumnType.VARCHAR)
	private String workUnit;

	/**
	 * 职务
	 */
	@Column(type = ColumnType.VARCHAR)
	private String headship;

	/**
	 * 生日
	 */
	@Column(type = ColumnType.DATETIME)
	private Date birthDate;
	

	/**
	 * 上次登录时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date loginTime;

	/**
	 * 上次登录ip
	 */
	@Column(type = ColumnType.VARCHAR)
	private String loginIp;

	/**
	 * 注册ip
	 */
	@Column(type = ColumnType.VARCHAR)
	private String regip;

	/**
	 * 注册时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date regtime;

	/**
	 * 注册地点
	 */
	@Column(type = ColumnType.VARCHAR)
	private String regsite;
	
	/**
	 * 网站id
	 */
	@Column(type = ColumnType.INT)
	private Integer siteId;
	
	/**
	 * 客户端类型   2：iphone   3：安卓   4：ipad
	 */
	@Column(type = ColumnType.INT)
	private Integer clientType;


	/**
	 * 登录密码
	 */
	@Column(type = ColumnType.VARCHAR)
	private String password;
	
	/**
	 * 重jis过来的密码
	 */
	@Column(type = ColumnType.VARCHAR)
	private String pwdForJis;
	
	/**
	 * 用户类型
	 * 		0：普通用户
	 * 		1：手机用户
	 * 		2：qq号用户
	 * 		3：新浪微博用户
	 * 		4：腾讯微博用户
	 */
	@Column(type = ColumnType.INT)
	private Integer type = 0;
	
	/**
	 * 头像url
	 */
	@Column(type = ColumnType.VARCHAR)
	private String headUrl;
	

	
	/**
	 * 身份证号 
	 */
	@Column(type = ColumnType.VARCHAR)
	private String idCard;
	
	/**
	 * 传真
	 */
	@Column(type = ColumnType.VARCHAR)
	private String fax;
	
	/**
	 * qq号码
	 */
	@Column(type = ColumnType.VARCHAR)
	private String qq;
	
	/**
	 * 出生年月
	 */
	@Column(type = ColumnType.VARCHAR)
	private String birthdate1;
	
	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createTime;

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPwdForJis() {
		return pwdForJis;
	}

	public void setPwdForJis(String pwdForJis) {
		this.pwdForJis = pwdForJis;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getBirthdate1() {
		return birthdate1;
	}

	public void setBirthdate1(String birthdate1) {
		this.birthdate1 = birthdate1;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


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

	public String getPinYin() {
		return pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public Integer getPapersType() {
		return papersType;
	}

	public void setPapersType(Integer papersType) {
		this.papersType = papersType;
	}

	public String getPapersNumber() {
		return papersNumber;
	}

	public void setPapersNumber(String papersNumber) {
		this.papersNumber = papersNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getHeadship() {
		return headship;
	}

	public void setHeadship(String headship) {
		this.headship = headship;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getRegip() {
		return regip;
	}

	public void setRegip(String regip) {
		this.regip = regip;
	}

	public Date getRegtime() {
		return regtime;
	}

	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}

	public String getRegsite() {
		return regsite;
	}

	public void setRegsite(String regsite) {
		this.regsite = regsite;
	}

}
