package com.hanweb.jmp.sys.entity.log;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 * 日志实体
 * @author hq
 */
@Table(name = Tables.LOG)
public class Log implements Serializable {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -8177266631659256821L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11)
	private Integer iid;

	/**
	 * 用户id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer userId;

	/**
	 *用户名字
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String userName;

	/**
	 * 模块id
	 */
	@Column(type = ColumnType.INT, length = 5)
	private Integer moduleId;

	/**
	 * 功能id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer funcId;

	/**
	 * 网站id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer siteId;

	/**
	 * 操作内容
	 */
	@Column(type = ColumnType.VARCHAR, length = 2000)
	private String content;

	/**
	 * 操作时间
	 */
	@Column(type = ColumnType.DATETIME)
	private Date oprTime;

	/**
	 * ip
	 */
	@Column(type = ColumnType.CHAR, length = 255)
	private String ip;
	
	/**
	 * 操作人所属机构
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String groupName;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getFuncId() {
		return funcId;
	}

	public void setFuncId(Integer funcId) {
		this.funcId = funcId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getOprTime() {
		return oprTime;
	}

	public void setOprTime(Date oprTime) {
		this.oprTime = oprTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

}