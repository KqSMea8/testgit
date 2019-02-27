package com.hanweb.jmp.cms.service.matters.video;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.cms.dao.matters.video.VideoColDAO;
import com.hanweb.jmp.cms.entity.matters.video.VideoCol;

public class VideoColService {

	@Autowired
	private VideoColDAO videoColDAO;
	
	/**
	 * 新增分类
	 * @param videoCol
	 * @return
	 * @throws OperationException
	 */
	public boolean add(VideoCol videoCol) throws OperationException{
		if(videoCol == null){
			return false;
		}
		int num = this.findNumOfSameName(0, videoCol.getName(), videoCol.getSiteId());
		if (num > 0) {
			throw new OperationException("分类名称已存在,请重新设置！");
		}
		Integer iid = videoColDAO.insert(videoCol);
		return NumberUtil.getInt(iid) > 0;
	}
	
	/**
	 * 根据iid查找信息
	 * @param iid
	 * @return
	 */
	public VideoCol findByIid(Integer iid) {
		if(NumberUtil.getInt(iid) <= 0){
			return null;
		}
		return videoColDAO.queryForEntityById(iid);
	}
	
	/**
	 * 根据siteId查找分类
	 * @param siteid
	 * @return
	 */
	public List<VideoCol> findBySiteId(Integer siteId) {
		List<VideoCol> list = null;
		if(NumberUtil.getInt(siteId) > 0){
			list = videoColDAO.findVideoCol(siteId);
		}
		if(list == null){
			list = new ArrayList<VideoCol>();
		}
		return list;
	}
	
	/**
	 * 修改分类
	 * @param videoCol
	 * @return
	 * @throws OperationException
	 */
	public boolean modify(VideoCol videoCol) throws OperationException{
		if(videoCol == null){
			return false;
		}
		return videoColDAO.update(videoCol);
	}
	
	/**
	 * 删除分类
	 * @param ids
	 * @param siteId
	 * @param isremove
	 * @return
	 * @throws OperationException
	 */
	public boolean removeByIds(String ids,Integer siteId,String  isremove) throws OperationException{
		boolean hasSubInfo = this.checkHaveInfo(ids);
		if (hasSubInfo) {
			throw new OperationException("所选分类或者回收站存在音视频,请先删除音视频!");
		}
		boolean hasRecycleInfo = this.checkRecycleInfo(isremove, siteId);
		if (hasRecycleInfo) {
			throw new OperationException("所选分类回收站存在音视频,请先删除音视频!");
		}
		return videoColDAO.deleteByIds(StringUtil.toIntegerList(ids));
	}
	
	/**
	 * 检查回收站是否有信息.
	 * @param ids 栏目ID串 如:1,2,3
	 * @param siteId 网站id
	 * @return    设定参数 .
	*/
	public boolean checkRecycleInfo(String isremove, Integer siteId) {
		List<Integer> idsList = StringUtil.toIntegerList(isremove, ",");
		if (CollectionUtils.isEmpty(idsList)) {
			return false;
		}
		int num = videoColDAO.findCountRecycleInfo(idsList, siteId);
		return num > 0;
	}
	
	/**
	 * 分类排序
	 * @return
	 */
	public int findMaxOrderId() {
		return NumberUtil.getInt(videoColDAO.findMaxOrderId());
	}
	
	/**
	 * 查找分类
	 * @param siteId
	 * @return
	 */
	public List<VideoCol> findVideoColBySiteId(Integer siteId) {
		if(NumberUtil.getInt(siteId) <= 0){
			return null;
		}
		return videoColDAO.findVideoCol(siteId);
	}
	
	/**
	 * 修改分类排序id
	 * @param ids ids
	 * @param orderids orderids
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean modifyOrderIdById(String ids, String orderids) throws OperationException{
		if(StringUtil.isEmpty(ids) || StringUtil.isEmpty(orderids)){
			return false;
		}
		List<Integer> idList = StringUtil.toIntegerList(ids);
		List<Integer> orderList = StringUtil.toIntegerList(orderids);
		if(idList.size() != orderList.size()){
			return false;
		}
		for(int i=0; i< idList.size(); i++){
			int id = NumberUtil.getInt(idList.get(i));
			int order = NumberUtil.getInt(orderList.get(i));
			videoColDAO.updateOrder(id, order);
		}
		return true;
	}
	
	/**
	 * 判重
	 * @param id id
	 * @param name 名称
	 * @param siteId 网站ID
	 * @return num
	 */
	public int findNumOfSameName(Integer id, String name, Integer siteId) {
		if (StringUtil.isEmpty(name)) {
			return 0;
		}
		int num = videoColDAO.findNumOfSameName(id, name, siteId);
		return num;
	}
	
	/**
	 * 判断分类下是否还有视频实体
	 * @param ids
	 * @return
	 */
	public boolean checkHaveInfo(String ids) {
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idsList)) {
			return false;
		}
		int num = videoColDAO.findCountSubInfo(idsList);
		return num > 0;
	}

	/**
	 * 系统自动生成一条音视频分类
	 * @param siteId
	 * @return 
	 * @throws ParseException 
	 */
    public Integer addFieldsBySiteId(Integer siteId) throws ParseException {
        String name = "个人分类";
        String spec = "新建站点时初始化个人分类";
        VideoCol videoCol = new VideoCol();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        videoCol.setPid(1);
        videoCol.setOrderId(1);
        videoCol.setName(name);
        videoCol.setSpec(spec);
        videoCol.setSiteId(siteId);
        videoCol.setCreateTime(sdf.parse(sdf.format(new Date())));
        return videoColDAO.insert(videoCol);
    }
	
}