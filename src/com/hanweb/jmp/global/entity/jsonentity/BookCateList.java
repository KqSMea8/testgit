package com.hanweb.jmp.global.entity.jsonentity;

import java.util.ArrayList;
import java.util.List;

/**
 * 栏目订阅列表json
 * @author hq
 *
 */
public class BookCateList {
	
	/**
	 * appresource
	 */
	private List<BookCates> appresource = new ArrayList<BookCates>();

	/**
	 * newsresource
	 */
	private List<BookCates> newsresource = new ArrayList<BookCates>();

	/**
	 * havemore
	 */
	private String havemore = "";

	/**
	 * resource
	 */
	private List<BookCates> resource = new ArrayList<BookCates>();

	public List<BookCates> getResource() {
		return resource;
	}

	public void setResource(List<BookCates> resource) {
		this.resource = resource;
	}

	public String getHavemore() {
		return havemore;
	}

	public void setHavemore(String havemore) {
		this.havemore = havemore;
	}

	public List<BookCates> getAppresource() {
		return appresource;
	}

	public void setAppresource(List<BookCates> appresource) {
		this.appresource = appresource;
	}

	public List<BookCates> getNewsresource() {
		return newsresource;
	}

	public void setNewsresource(List<BookCates> newsresource) {
		this.newsresource = newsresource;
	}
	
}