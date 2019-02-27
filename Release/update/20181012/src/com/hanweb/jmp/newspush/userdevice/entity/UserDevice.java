package com.hanweb.jmp.newspush.userdevice.entity;

import java.io.Serializable;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;
import com.hanweb.jmp.constant.Tables;


/**
 * 用户设备关系实体
 * 
 * @author Wangjw
 * 
 */
@Table(name = Tables.USER_DEVICE)
public class UserDevice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7147084749151566019L;
	
	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT)
	private Integer iid;
	
	/**
	 * 用户id
	 */
	@Column(type = ColumnType.INT)
	private Integer usid;
	
	/**
	 * 应用的用户ID，一个应用在多个端可以都属于同一用户。
	 * user id和channel id配合可以唯一指定一个应用的特定终端。
	 * 如果应用不是基于百度账户的账户体系，单独用user就通常指定了一个应用的特定终端。
	 * 客户端绑定调用返回值中可获得。
	 * 
	 * 在信鸽代表token
	 */
	@Column(type = ColumnType.VARCHAR)
	private String userid;
	
	/**
	 * 推送通道ID，通常指一个终端，如一台android系统手机。客户端绑定调用返回值中可获得。
	 */
	@Column(type = ColumnType.VARCHAR)
	private String channelid;
	
	/**
	 * 应用ID，就是百度开发者中心的应用基本信息中的应用ID。客户端绑定调用返回值中可获得。
	 */
	@Column(type = ColumnType.VARCHAR)
	private String appid;
	
	/**
	 * 设备码
	 */
	@Column(type = ColumnType.VARCHAR, length = 50)
	private String uuid;
	
	/**
	 * 1:android 2:iphone
	 */
	@Column(type = ColumnType.INT)
	private Integer clientType;

	public Integer getIid() {
		return this.iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getUsid() {
		return this.usid;
	}

	public void setUsid(Integer usid) {
		this.usid = usid;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getChannelid() {
		return this.channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}

	public String getAppid() {
		return this.appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getClientType() {
		return this.clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}
	
}
