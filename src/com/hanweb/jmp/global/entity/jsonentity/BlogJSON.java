package com.hanweb.jmp.global.entity.jsonentity;

import java.io.Serializable;
import java.util.ArrayList;

public class BlogJSON implements Serializable {
	
	/**
	 * 绑定微博
	 */
	private static final long serialVersionUID = -777155016159072470L;

	/**
	 * blogType
	 */
	private Integer blogType = 0;

	/**
	 * name
	 */
	private String name = "";

	/**
	 * json
	 */
	private ArrayList<String> json = new ArrayList<String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBlogType() {
		return blogType;
	}

	public void setBlogType(Integer blogType) {
		this.blogType = blogType;
	}

	public ArrayList<String> getJson() {
		return json;
	}

	public void setJson(ArrayList<String> json) {
		this.json = json;
	}

}