package com.hanweb.jmp.pack.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.FileUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.cms.controller.channels.ChannelFormBean;
import com.hanweb.jmp.cms.dao.channels.ChannelDAO;
import com.hanweb.jmp.cms.entity.channels.Channel;
import com.hanweb.jmp.cms.entity.channels.ChannelCol;
import com.hanweb.jmp.cms.service.channels.ChannelColService;

public class ChannelService {
	
	/**
	 * channelDAO
	 */
	@Autowired
	private ChannelDAO channelDAO;
	
	/**
	 * channelColService
	 */
	@Autowired
	private ChannelColService channelColService;

	/**
	 * 频道新增 ：单栏目类
	 * @param channel 
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException 操作异常
	 */
	public boolean addChannels(ChannelFormBean channel) throws OperationException{
		List<Integer> colIds = StringUtil.toIntegerList(channel.getColIds());
		List<String> colNames = StringUtil.toStringList(channel.getColNames());
		int orderId = channelDAO.findMaxOrderId();
		ChannelCol channelCol;
		int colOrderId = 1;
		for (int i = 0, len = colIds.size(); i < len; i++) {
			//1、频道表入库
			if(NumberUtil.getInt(channel.getOrderId()) <= 0 || i>=1){
				channel.setOrderId(orderId);
				orderId++;
			}
			//2、单栏目类频道名称为栏目名称
			channel.setName(colNames.get(i));
			int iid = channelDAO.insert(channel);
			if(NumberUtil.getInt(channel.getOrderId()) > 0&&channel.getOrderId()>=orderId){
				orderId = channelDAO.findMaxOrderId();
			}
			//5、频道栏目关系表入库
			channelCol = new ChannelCol();
			channelCol.setSiteId(channel.getSiteId());
			channelCol.setChannelId(iid);
			channelCol.setColId(NumberUtil.getInt(colIds.get(i)));
			channelCol.setOrderId(colOrderId++);
			channelColService.add(channelCol);
		}
		return true;
	}
	
	/**
	 * 频道新增 ：信息聚合和微博群聚合类
	 * @param channel 
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException 操作异常
	 */
	public boolean addChannel(ChannelFormBean channel) throws OperationException{
		boolean isSuccess = true;
		
		//2、频道表入库
		int orderId = channelDAO.findMaxOrderId();
		channel.setOrderId(orderId);
		int iid = channelDAO.insert(channel);
		
		
		//3、频道首图处理
		String firsturl = "/web/site" + channel.getSiteId() 
		                  + "/channel/channel_first_" + iid + ".jpg";
	    File file = new File(BaseInfo.getRealPath() + firsturl);
	    File firstImage = new File(BaseInfo.getRealPath() + channel.getFirstPic());
	    boolean isCopy = FileUtil.copyFile(firstImage, file);
	
	    //4、更新频道首图图片地址
	    channel.setIid(iid);
	    if(isCopy){
	    	channel.setFirstPic(firsturl); 
	    }
	    this.modifyPath(channel);
		
		//5、频道栏目关系表入库
		ChannelCol channelCol;
		String colId = channel.getColIds();
		String[] colids = StringUtil.split(colId, ",");
		int colOrderId = 1;
		for (String colid : colids) {
			channelCol = new ChannelCol();
			channelCol.setSiteId(channel.getSiteId());
			channelCol.setChannelId(iid);
			channelCol.setColId(NumberUtil.getInt(colid));
			channelCol.setOrderId(colOrderId++);
			channelColService.add(channelCol);
		}
		return isSuccess;
	}
	
	/**
	 * 频道保存多份缩略图
	 * @param channel
	 *            频道实体
	 */
	public void modifyPath(Channel channel) {
		if (channel == null || channel.getIid() <= 0) {
			return;
		}
		int channelid = channel.getIid();
		String newPath = "/web/site" + channel.getSiteId() + "/channel/"; 
		String imgformat="";
		//首图
		if (!StringUtil.isEmpty(channel.getFirstPic())) { 
			imgformat = channel.getFirstPic().substring(
					channel.getFirstPic().lastIndexOf(".")+1);  
			String firstPicPath = newPath + "channel_first_" + channelid + "." + imgformat;
			channel.setFirstPic(firstPicPath); 
//			imgUtil.cutChanFirstPicImage(channel.getSiteId(), channelid, channel.getType(),
//					BaseInfo.getRealPath()+firstPicPath, BaseInfo.getRealPath()+newPath);
		}
	}
	
}