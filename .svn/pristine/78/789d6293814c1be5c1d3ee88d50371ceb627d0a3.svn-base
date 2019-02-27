package com.hanweb.jmp.global.entity.normalentity;

import java.io.Serializable;
import java.util.Comparator;

public class ModulePermission implements Serializable, Comparator<ModulePermission> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5608948144627098787L;

	/**
	 * 模块标识
	 */
	private String uuid = "";

	/**
	 * 模块权限
	 */
	private String modName = "";

	/**
	 * 描述
	 */
	private String desc = "";

	/**
	 * 控制客户端网站是否有高级功能的权限 -1=不存在；0=隐藏不显示 ；1=试用 ；2=正常 其中 试用 主要用于提醒使用人
	 */
	private int state = 0;

	/**
	 * 限制个数，针对需创建栏目的功能，如反馈、天气预报、场景式 0=不允许创建； 正整数=允许创建的个数
	 */
	private int limitNum = 0;

	/**
	 * 网站ID
	 */
	private int siteId = 0;

	/**
	 * 用户id
	 */
	private String userId = "";

	/**
	 * 使用期限,单位：天 0=功能失效 ； 正整数=允许使用期限
	 */
	private int usePeriod = 90;

	/**
	 * 授权版的起始日期，用于计算截止日期
	 */
	private String startTime;
	
	/**
	 * 授权版的截止日期，根据使用期限自动计算
	 */
	private String endTime;
	
	/**
	 * 排序id，用于组织页面
	 */
	private int orderId = 0;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getModName() {
		return modName;
	}

	public void setModName(String modName) {
		this.modName = modName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(int limitNum) {
		this.limitNum = limitNum;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getUsePeriod() {
		return usePeriod;
	}

	public void setUsePeriod(int usePeriod) {
		this.usePeriod = usePeriod;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@Override
	public int compare(ModulePermission mod1, ModulePermission mod2) {
		Integer n1 = mod1.orderId;
		Integer n2 = mod2.orderId;
		return n1.compareTo(n2);
	}

}