package com.hanweb.jmp.global.entity.jsonentity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 首图JSON实体
 * @author WangFei
 */
public class ColJSON implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7915757163936458761L;

	/**
	 * 
	 */
	private Integer resourId;

	/**
	 * 
	 */
	private String resName = "";

	/**
	 * 
	 */
	private Integer columnStatus;

	/**
	 * 
	 */
	private Integer visit;

	/**
	 * 
	 */
	private ArrayList<ResTitleJSON> resourceTitle = new ArrayList<ResTitleJSON>();

	public Integer getResourId() {
		return resourId;
	}

	public void setResourId(Integer resourId) {
		this.resourId = resourId;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public Integer getColumnStatus() {
		return columnStatus;
	}

	public void setColumnStatus(Integer columnStatus) {
		this.columnStatus = columnStatus;
	}

	public ArrayList<ResTitleJSON> getResourceTitle() {
		return resourceTitle;
	}

	public void setResourceTitle(ArrayList<ResTitleJSON> resourceTitle) {
		this.resourceTitle = resourceTitle;
	}

	public Integer getVisit() {
		return visit;
	}

	public void setVisit(Integer visit) {
		this.visit = visit;
	}

}