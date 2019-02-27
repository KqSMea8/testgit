package com.hanweb.jmp.global.entity.jsonentity;

/**
 * 推送分类实体
 * @author hq
 */
public class PushTypeEntity {
	
	/**
	 * 
	 */
	private String id;
	
	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private String orderId;
	
	/**
	 * 
	 */
	private String publicSort;
	
	/**
	 * 
	 */
	private String state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPublicSort() {
		return publicSort;
	}

	public void setPublicSort(String publicSort) {
		this.publicSort = publicSort;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}