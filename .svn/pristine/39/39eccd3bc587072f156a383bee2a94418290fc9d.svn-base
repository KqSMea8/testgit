package com.hanweb.jmp.cms.service.function;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.jmp.cms.dao.function.LayoutDAO;
import com.hanweb.jmp.cms.entity.function.Layout;

public class LayoutService {
	
	/**
	 * layoutDAO
	 */
	@Autowired
	private LayoutDAO layoutDAO;
	
	
	/**
	 * 查找导航排序id
	 * @param channelid channelid
	 * @return    设定参数 .
	 */
	public Integer findChannelOrderId(Integer channelid) {
		return layoutDAO.findChannelOrderId(channelid);
	}
	
	/**
	 * 查找导航类型
	 * @param channelid channelid
	 * @return    设定参数 .
	 */
	public Integer checkChannelIsMyDesk(Integer channelid) {
		return layoutDAO.checkChannelIsMyDesk(channelid);
	}
	
	/**
	 * 查找栏目排序id
	 * @param channelid channelid
	 * @param colIds colIds
	 * @return    设定参数 .
	 */
	public String findColOrderId(Integer channelid, String colIds) {
		ArrayList<Layout> layoutList = (ArrayList<Layout>) layoutDAO.findColOrderId(
				channelid, colIds);
		StringBuffer buffer = new StringBuffer();
		for (Layout layout : layoutList) {
			buffer.append(layout.getOrderId() + ",");
		}
		if (buffer.length() > 0) {
			buffer.delete(buffer.length() - 1, buffer.length());
		}
		return buffer.toString();
	}
	
}