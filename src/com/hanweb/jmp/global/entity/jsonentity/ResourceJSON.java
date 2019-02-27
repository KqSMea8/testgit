package com.hanweb.jmp.global.entity.jsonentity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 首图JSON实体
 * @author WangFei
 */
public class ResourceJSON implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2568319081851215023L;

	/**
	 * 
	 */
	private ArrayList<ColJSON> resource = new ArrayList<ColJSON>();
	
	/**
	 * 
	 */
	private ArrayList<ChannelJSON> channel = new ArrayList<ChannelJSON>();

	public ArrayList<ColJSON> getResource() {
		return resource;
	}

	public void setResource(ArrayList<ColJSON> resource) {
		this.resource = resource;
	}

	public ArrayList<ChannelJSON> getChannel() {
		return channel;
	}

	public void setChannel(ArrayList<ChannelJSON> channel) {
		this.channel = channel;
	}

}