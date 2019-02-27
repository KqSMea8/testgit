package com.hanweb.jmp.sys.entity.ditch;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.jmp.constant.Tables;

@Table(name = Tables.DITCH)
public class Ditch implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11)
	private Integer iid;
	
	/**
	 * 接口地址
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String url;
	
	/**
	 * 用户名
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String name;
	
	/**
	 * 密码
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String pwd;
	
	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createTime;
	
	/**
	 * 网站id
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer siteId;
	
	/**
	 * 同步类型   0:jget  1:jcms   2:其它
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer syntype;
	
	/**
	 * 是否展现,用于栏目树的展现 0:不展现  1：展现
	 * @return
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer enable;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getSyntype() {
		return syntype;
	}

	public void setSyntype(Integer syntype) {
		this.syntype = syntype;
	}
	
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
	
}