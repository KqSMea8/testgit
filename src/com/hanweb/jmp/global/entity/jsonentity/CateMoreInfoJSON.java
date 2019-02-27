package com.hanweb.jmp.global.entity.jsonentity;

import java.util.ArrayList;

/**
 * 信息列表json
 * @author hq
 */
public class CateMoreInfoJSON {
	
	/**
	 * resource
	 */
	private ArrayList<CateMoreInfoCategory> resource = new ArrayList<CateMoreInfoCategory>();
	
	/**
	 * isnext
	 */
	private Integer isnext = 0;

	public ArrayList<CateMoreInfoCategory> getResource() {
		return this.resource;
	}

	public void setResource(ArrayList<CateMoreInfoCategory> resource) {
		this.resource = resource;
	}

	public Integer getIsnext() {
		return isnext;
	}

	public void setIsnext(Integer isnext) {
		this.isnext = isnext;
	}

}