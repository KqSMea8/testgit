package com.hanweb.jmp.apps.controller.broke.interfaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hanweb.jmp.apps.entity.broke.Broke;

public class BrokeJSON implements Serializable {

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -7046798696391817157L;

	/**
	 * haveMore
	 */
	private boolean haveMore = false;

	/**
	 * 报料实体集合
	 */
	private List<Broke> brokeList = new ArrayList<Broke>();

	public boolean isHaveMore() {
		return haveMore;
	}

	public void setHaveMore(boolean haveMore) {
		this.haveMore = haveMore;
	}

	public List<Broke> getBrokeList() {
		return brokeList;
	}

	public void setBrokeList(List<Broke> brokeList) {
		this.brokeList = brokeList;
	}

}