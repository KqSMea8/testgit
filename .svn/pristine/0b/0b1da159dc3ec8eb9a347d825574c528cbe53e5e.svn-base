package com.hanweb.jmp.newspush.news.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.newspush.news.dao.NewsDetailDAO;
import com.hanweb.jmp.newspush.news.entity.NewsDetail;

/**
 * 信息详情Service
 * 
 * @author Wangjw
 * 
 */
public class NewsDetailService {

	@Autowired
	private NewsDetailDAO newsDetailDAO;
	
	
	
	
	
	/**
	 * 
	 * @param infoFormBean
	 *            消息实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 */
	public boolean add(Info info, String users, int infoKind) throws OperationException {
		if (info == null) {
			return false;
		}
		boolean isSuccess = true;
		//公有
		if(infoKind == 1){
			NewsDetail newsDetail = new NewsDetail();
			newsDetail.setInfoId(info.getIid());
			newsDetail.setSendTime(info.getPushTime());
			newsDetail.setState(0);
			newsDetail.setUsid(0);
			newsDetail.setInfoKind(infoKind);
			int i = newsDetailDAO.insert(newsDetail);
			if(i <= 0){
				return false;
			}
			return true;
		}
		List<Integer> userList = StringUtil.toIntegerList(users);
		//私有、应用
		for(Integer usId : userList){
			NewsDetail infoDetail = new NewsDetail();
			infoDetail.setInfoId(info.getIid());
			infoDetail.setSendTime(info.getPushTime());
			infoDetail.setState(0);
			infoDetail.setUsid(usId);
			infoDetail.setInfoKind(infoKind);
			int i = newsDetailDAO.insert(infoDetail);
			if(i <= 0){
				isSuccess = isSuccess && false;
			}
		}
		
		return isSuccess;
	}
	
	/**
	 * 根据消息id删除详情
	 * @param infoId 消息id
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean removeByInfoId(int infoId){
		if(infoId <= 0){
			return false;
		}
		return newsDetailDAO.removeByInfoId(infoId);
	}
	
	/**
	 * 通过iid获取详情实体
	 * 
	 * @param iid
	 *            iid
	 * @return 消息实体
	 */
	public NewsDetail findByIid(int iid){
		if(iid <= 0){
			return null;
		}
		return newsDetailDAO.findByIid(iid);
	}
	
	/**
	 * 通过infoId，用户id获取详情实体
	 * 
	 * @param infoId
	 *            消息ID
	 * @return 消息实体
	 */
	public List<NewsDetail> findByInfoId(int infoId, int uid, int infoKind){
		if(infoId <= 0 || uid <= 0){
			return null;
		}
		return newsDetailDAO.findByInfoId(infoId, uid, infoKind);
	}
	
	/**
	 * 修改
	 * @param infoDetail 实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 */
	public boolean update(NewsDetail infoDetail){
		if(infoDetail == null){
			return false;
		}
		return newsDetailDAO.update(infoDetail);
	}
	
	/**
	 * 根据ids删除日志记录
	 * 
	 * @param ids
	 *            1,2,3
	 * @return 是否成功
	 * @throws OperationException
	 */
	public boolean removeByIds(String ids) throws OperationException {
		List<Integer> idList = StringUtil.toIntegerList(ids);
		if (CollectionUtils.isEmpty(idList)) {
			return false;
		}
		boolean isSuccess = false;
		isSuccess = newsDetailDAO.deleteByIds(idList);
		if (!isSuccess) {
			throw new OperationException("删除回执失败！");
		}
		return isSuccess;
	}

	/**
	 * 清空某条信息的详情
	 * @param infoId 信息id
	 * @return 是否成功
	 * @throws OperationException
	 *             操作异常
	 */
	public boolean clean(Integer infoId) throws OperationException {
		boolean isSuccess = true;
		newsDetailDAO.clean(infoId);
		return isSuccess;
	}
	
	/**
	 * 获得消息推给用户的总用户数量
	 * @param infoId 消息ID
	 * @return 数量
	 */
	public int getInfoAllCount(int infoId){
		if(infoId <= 0){
			return 0;
		}
		return newsDetailDAO.getInfoAllCount(infoId);
	}
	
	/**
	 * 获得消息推给用户的已阅读的用户数量
	 * @param infoId 消息ID
	 * @return 数量
	 */
	public int getInfoCount(int infoId, int state){
		if(infoId <= 0){
			return 0;
		}
		return newsDetailDAO.getInfoCount(infoId, state);
	}
	
	
	
	/**
	 * 获得该消息未阅读的实体
	 * @param infoId 消息ID
	 * @return 
	 */
	public List<NewsDetail> getNoReadByInfoId(int infoId){
		if(infoId == 0){
			return null;
		}
		return newsDetailDAO.getNoReadByInfoId(infoId);
	} 
}
