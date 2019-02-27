package com.hanweb.jmp.global.entity.jsonentity;

import java.util.ArrayList;

import com.hanweb.jmp.cms.entity.infos.Info;

/**
 * 信息推荐列表
 * @author hq
 */
public class RecomendInfoJSON {
	
	/**
	 * 
	 */
	private String dayTime = "";

	/**
	 * 
	 */
	private ArrayList<Info> infoList = new ArrayList<Info>();

	public String getDayTime() {
		return dayTime;
	}

	public void setDayTime(String dayTime) {
		this.dayTime = dayTime;
	}

	public ArrayList<Info> getInfoList() {
		return infoList;
	}

	public void setInfoList(ArrayList<Info> infoList) {
		this.infoList = infoList;
	}

}