package com.hanweb.jmp.global.entity.jsonentity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 首页布局导航列表json
 * @author WangFei
 */
public class ChannelJSON implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2544595524680105353L;

	/**
	 * 
	 */
	private Integer channelId;

	/**
	 * 
	 */
	private String channelName;

	/**
	 * 
	 */
	private String channelfirstpic;

	/**
	 * 
	 */
	private String channelText;

	/**
	 * 
	 */
	private Integer orderId;

	/**
	 * 
	 */
	private Integer channelType;

	/**
	 * 
	 */
	private Integer channelshowtype;

	/**
	 * 
	 */
	private Integer channlevisit;

	/**
	 * 
	 */
	private String channelcompoundimg;

	/**
	 * 
	 */
	private Integer haveNew;

	/**
	 * 
	 */
	private ArrayList<ChannelResource> channelResource = new ArrayList<ChannelResource>();

	public Integer getChannelId() {
		return channelId;
	}

	public Integer getHaveNew() {
		return haveNew;
	}

	public void setHaveNew(Integer haveNew) {
		this.haveNew = haveNew;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public Integer getChannelshowtype() {
		return channelshowtype;
	}

	public void setChannelshowtype(Integer channelshowtype) {
		this.channelshowtype = channelshowtype;
	}

	public Integer getChannlevisit() {
		return channlevisit;
	}

	public void setChannlevisit(Integer channlevisit) {
		this.channlevisit = channlevisit;
	}

	public String getChannelcompoundimg() {
		return channelcompoundimg;
	}

	public void setChannelcompoundimg(String channelcompoundimg) {
		this.channelcompoundimg = channelcompoundimg;
	}

	public ArrayList<ChannelResource> getChannelResource() {
		return channelResource;
	}

	public void setChannelResource(ArrayList<ChannelResource> channelResource) {
		this.channelResource = channelResource;
	}

	public String getChannelfirstpic() {
		return channelfirstpic;
	}

	public void setChannelfirstpic(String channelfirstpic) {
		this.channelfirstpic = channelfirstpic;
	}

	public String getChannelText() {
		return channelText;
	}

	public void setChannelText(String channelText) {
		this.channelText = channelText;
	}

}