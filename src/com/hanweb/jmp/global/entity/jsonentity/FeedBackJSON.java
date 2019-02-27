package com.hanweb.jmp.global.entity.jsonentity;

import java.util.ArrayList;

import com.hanweb.jmp.sys.entity.feedback.FeedBack;

/**
 * 意见反馈JSON实体
 * @author WangFei
 */
public class FeedBackJSON {
	
	/**
	 * 是否有更多
	 */
	private String haveMore = "";
	
	/**
	 * 反馈信息集合
	 */
	private ArrayList<FeedBack> feedBack = new ArrayList<FeedBack>();

	public ArrayList<FeedBack> getFeedBack() {
		return this.feedBack;
	}

	public void setFeedBack(ArrayList<FeedBack> feedBack) {
		this.feedBack = feedBack;
	}

	public String getHaveMore() {
		return this.haveMore;
	}

	public void setHaveMore(String haveMore) {
		this.haveMore = haveMore;
	}
	
}