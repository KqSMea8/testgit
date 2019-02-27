package com.hanweb.jmp.cms.service.channels;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;

import com.hanweb.jmp.cms.dao.channels.ChannelColDAO;
import com.hanweb.jmp.cms.dao.cols.ColDAO;
import com.hanweb.jmp.cms.entity.channels.Channel;
import com.hanweb.jmp.cms.entity.channels.ChannelCol;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.jmp.cms.controller.channels.ChannelColFormBean;

public class ChannelColService {
	
	/**
	 * channelColDAO
	 */
	@Autowired
	private ChannelColDAO channelColDAO;
	
	/**
	 * colDAO
	 */
	@Autowired
	private ColDAO colDAO;
	
	/**
	 * colService
	 */
	@Autowired
	private ColService colService;
	
	/**
	 * channelService
	 */
	@Autowired
	private ChannelService channelService;
	
	/**
	 * 新增
	 * @param channelCol 
	 * @return id 
	 */
	public Integer add(ChannelCol channelCol){
		return channelColDAO.insert(channelCol);
	}
	
	/**
	 * 修改
	 * @param channelCol 
	 * @return true 成功<b/>
	 * 			false 失败
	 */
	public boolean modify(ChannelCol channelCol){
		return channelColDAO.update(channelCol);
	}
	
	/**
	 * 删除
	 * @param id 
	 * @return true 成功<b/>
	 * 			false 失败
	 */
	public boolean remove(Integer id){
		return channelColDAO.deleteById(id);
	}
	
	/**
	 * 查找最大排序id
	 * @return int  最大排序id
	 */
	public int findMaxOrderId(){
		return  channelColDAO.findMaxOrderId();
	}
	
	/**
	 * 根据导航id查找导航栏目关系
	 * @param channelId 导航id
	 * @return List<ChannelCol>  
	 */
	public List<ChannelCol> findByChannelId(Integer channelId){
		return channelColDAO.findByChannelId(channelId);
	}
	
	/**
	 * 根据导航id和网站id删除
	 * @param channelId 导航id	
	 * @param siteId	网站id
	 * @return true 成功<b/>
	 * 			false 失败
	 */
	public boolean deleteByIidAndSiteId(Integer channelId, Integer siteId){
		return channelColDAO.deleteByChannelIidAndSiteId(channelId, siteId);
	}
	
	/**
	 * 根据导航ids和网站id删除
	 * @param channelIds 导航ids
	 * @param siteId	网站id
	 * @return true 成功<b/>
	 * 			false 失败
	 */
	public boolean deleteByIidsAndSiteId(List<Integer> channelIds, Integer siteId){
		return channelColDAO.deleteByChannelIidsAndSiteId(channelIds, siteId);
	}
	
	/**
	 * 根据网站id和导航id获取该导航选择过的网站id
	 * @param siteId 网站id
	 * @param iid 导航id
	 * @return String 网站ids 如 1,2,3
	 */
	public String findCheckedSiteIds(Integer siteId, Integer iid){
		List<ChannelCol> list = channelColDAO.findCheckedSiteIds(siteId, iid);
		StringBuffer buffer = new StringBuffer(128);
		String siteIds = "";
		Col col;
		for (ChannelCol channelCol : list) {
			col = colService.findByIid(channelCol.getColId());
			buffer.append(",").append(col.getSiteId());
		}
		if(buffer.length() > 0){
			siteIds = buffer.substring(1);
		}
		return siteIds;
	}
	
	/**
	 * 根据网站id和导航id获取该导航选择过的栏目
	 * @param siteId 网站id
	 * @param iid 导航id
	 * @return String 网站ids 如 1,2,3
	 */
	public String findCheckedColIds(Integer siteId, Integer iid){
		List<ChannelCol> list = channelColDAO.findCheckedSiteIds(siteId, iid);
		StringBuffer buffer = new StringBuffer(128);
		String siteIds = "";
		for (ChannelCol channelCol : list) {
			buffer.append(",").append(channelCol.getColId());
		}
		if(buffer.length() > 0){
			siteIds = buffer.substring(1);
		}
		return siteIds;
	}
	
	/**
	 * 根据导航id查找订阅的网站
	 * @param siteId 网站id
	 * @param channelId 导航id
	 * @return List<ChannelColFormBean> 
	 */
	public List<ChannelColFormBean> findColByIid(Integer siteId, Integer channelId){
		List<ChannelColFormBean> channelColList = new ArrayList<ChannelColFormBean>();
		Col col;
		ChannelColFormBean channelColBean;
		List<ChannelCol> list = channelColDAO.findByChannelId(siteId, channelId);
		for (ChannelCol channelCol : list) {
			channelColBean = new ChannelColFormBean();
			channelColBean.setChannelCol(channelCol);
			col = colService.findByIid(channelCol.getColId());
			channelColBean.setColName(col.getName());
			channelColList.add(channelColBean);
		}
		return channelColList;
	}

	/**
	 * 更新信息id的orderid
	 * @param orderids 排序id
	 * @param ids 信息id
	 * @return true 成功
	 * @throws OperationException 操作异常
	 */
	public boolean modifyOrderIdById(String ids, String orderids) throws OperationException {
		if (ids == null || ids.length() == 0 || orderids == null || orderids.length() == 0) {
			return false;
		}
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		List<Integer> ordersList = StringUtil.toIntegerList(orderids, ",");
		boolean isSuccess = false;
		for (int i = 0, len = idsList.size(); i < len; i++) {
			isSuccess =  channelColDAO.updateOrderIdById(ordersList.get(i), idsList.get(i));
			if(i == len - 1){
				isSuccess = isSuccess && channelService
					.modifyFlag(channelColDAO.findByIid(idsList.get(i)).getChannelId());
			}
		}
		return isSuccess;
	}
	
	/**
	 * 通过栏目更新导航
	 * @param channel channelService
	 * @param isSite isSite
	 * @param flagNew flagNew
	 * @return    设定参数 .
	*/
	public Channel complete(Channel channel, boolean isSite, boolean flagNew) {
		List<Col> colList = colDAO.complete(channel.getIid(), flagNew);
		StringBuffer cateids = new StringBuffer("");
		StringBuffer catenames = new StringBuffer("");
		StringBuffer orders = new StringBuffer("");
		StringBuffer infotypes = new StringBuffer("");
		StringBuffer catetypes = new StringBuffer("");
		StringBuffer vcurls = new StringBuffer("");
		StringBuffer showtypes = new StringBuffer("");
		StringBuffer hudongTypes = new StringBuffer("");
		StringBuffer columnStates = new StringBuffer(1024);  //栏目状态
		StringBuffer visits = new StringBuffer(1024); //栏目的访问权限
		SiteService siteService = new SiteService();
		for (Col col : colList) {
			String siteName = "";
			if (isSite) {
				siteName = siteService.findByIid(col.getSiteId()).getName();
			}
			cateids.append("," + col.getIid());
			catenames.append("," + siteName + col.getName());
			orders.append("," + col.getOrderId());
			infotypes.append("," + col.getInfoListType());
			catetypes.append("," + col.getType()); 
			columnStates.append("," + col.getEnable());
			visits.append("," + col.getIsVisit());
		}
		channel.setColIds(cateids.substring(1).toString());
		channel.setColNames(catenames.substring(1).toString());
		channel.setOrderIds(orders.substring(1).toString());
		channel.setInfoTypes(infotypes.substring(1).toString());
		channel.setColTypes(catetypes.substring(1).toString());
		channel.setColUrls(vcurls.substring(1).toString());
		channel.setActShowTypes(showtypes.substring(1).toString());
		channel.setActTypes(hudongTypes.substring(1).toString());
		channel.setColStates(columnStates.substring(1).toString());
		channel.setColVisits(visits.substring(1).toString());
		return channel;
	}
	
	/**
	 * 根据栏目id查询导航
	 * @param colId colId
	 * @return    设定参数 .
	 */
	public Integer findChannelColOrderId(Integer colId) {
		return channelColDAO.findChannelColOrderId(colId);
	}
	
}