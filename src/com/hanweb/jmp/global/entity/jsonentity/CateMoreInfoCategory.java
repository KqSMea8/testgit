package com.hanweb.jmp.global.entity.jsonentity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 栏目列表
 * @author hq
 */
public class CateMoreInfoCategory implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5265510045177697836L;
	
	/**
	 * resourceId
	 */
	private Integer resourceId;
	
	/**
	 * key
	 */
	private Integer key;
	
	/**
	 * resName
	 */
	private String resName = "";
	
	/**
	 * columnStatus
	 */
	private Integer columnStatus;
	
	/**
	 * resourceTitle
	 */
	private ArrayList<CateMoreInfos> resourceTitle = new ArrayList<CateMoreInfos>();
	
	/**
	 * unaudit
	 */
	private String unaudit;
	
	/**
	 * visit
	 */
	private Integer visit = 0;

	public Integer getVisit() {
		return visit;
	}

	public void setVisit(Integer visit) {
		this.visit = visit;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getResName() {
		return this.resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public ArrayList<CateMoreInfos> getResourceTitle() {
		return this.resourceTitle;
	}

	public void setResourceTitle(ArrayList<CateMoreInfos> resourceTitle) {
		this.resourceTitle = resourceTitle;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getColumnStatus() {
		return columnStatus;
	}

	public void setColumnStatus(Integer columnStatus) {
		this.columnStatus = columnStatus;
	}

	public String getUnaudit() {
		return unaudit;
	}

	public void setUnaudit(String unaudit) {
		this.unaudit = unaudit;
	}

}