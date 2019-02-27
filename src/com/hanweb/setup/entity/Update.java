package com.hanweb.setup.entity;

import java.util.ArrayList;
import java.util.List;

public class Update {

	/**
	 * index
	 */
	private int index = 1;
	
	/**
	 * version
	 */
	private String version;
	
	/**
	 * updateItemList
	 */
	private List<UpdateItem> updateItemList = new ArrayList<UpdateItem>();

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<UpdateItem> getUpdateItemList() {
		return updateItemList;
	}

	public void setUpdateItemList(List<UpdateItem> updateItemList) {
		this.updateItemList = updateItemList;
	}
		
}