package com.hanweb.jmp.cms.service.comment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil; 

import com.hanweb.jmp.cms.dao.cols.ColDAO;
import com.hanweb.jmp.cms.dao.comment.CommentDAO;
import com.hanweb.jmp.cms.entity.comment.Comment;
import com.hanweb.jmp.cms.service.infos.InfoCountService;

public class CommentService {

	/**
	 * commentDAO
	 */
	@Autowired
	private CommentDAO commentDAO;
	
	/**
	 * infoCountService
	 */
	@Autowired 
	private InfoCountService infoCountService;
	
	@Autowired
	private ColDAO colDAO;
	
	/**
	 * 增加评论
	 * @param comment 评论实体
	 * @return 是否成功
	 */
	public boolean add(Comment comment){
		if(comment == null || StringUtil.isEmpty(comment.getContent())){
			return false;
		}
		boolean b = false;
		// 过滤emoji表情 
		String content = comment.getContent(); 
		comment.setContent(content);
		int iid = commentDAO.insert(comment);
		if(iid > 0){
			b = true;
		}else{
			b = false;
		} 
		if(comment.getState()==1){
			b = b && infoCountService.modifyCommentCount
			         (comment.getInfoId(), comment.getType(), comment.getSiteId());
		}
		return b;
	}
	
	/**
	 * 审核
	 * @param ids ids
	 * @return boolean
	 */
	public boolean audit(String ids){
		List<Integer> idList = StringUtil.toIntegerList(ids);
		List<Integer> colIds = new ArrayList<Integer>();
		if(CollectionUtils.isEmpty(idList)){
			return false;
		}
		for(Integer id : idList){
			Comment comment = findById(id);
			if(NumberUtil.getInt(comment.getState()) == 0 || NumberUtil.getInt(comment.getState()) == 2){
				infoCountService.modifyCommentCount(comment.getInfoId(), comment.getType(), comment.getSiteId());
				colIds.add(comment.getCateId());
			}
		}
		if(!CollectionUtils.isEmpty(colIds)){
			this.modifyColFlag(colIds);
		}
		return commentDAO.updateStateByIds(idList, 1);
	}
	
	/**
	 * 当评论撤审或审核后修改栏目的标记flag
	 * 
	 * @param colIds		栏目id集合
	 */
	private void modifyColFlag(List<Integer> colIds) {
		colDAO.updateFlag(colIds);
	}

	/**
	 * 撤审
	 * @param ids ids
	 * @return boolean
	 */
	public boolean unaudit(String ids){
		List<Integer> idList = StringUtil.toIntegerList(ids);
		List<Integer> colIds = new ArrayList<Integer>();
		if(CollectionUtils.isEmpty(idList)){
			return false;
		}
		for(Integer id : idList){
			Comment comment = findById(id);
			if(NumberUtil.getInt(comment.getState()) == 1){
				infoCountService.modifyCommentCountDes(comment.getInfoId(), comment.getType(), comment.getSiteId());
				colIds.add(comment.getCateId());
			}
		}
		if(!CollectionUtils.isEmpty(colIds)){
			this.modifyColFlag(colIds);
		}
		return commentDAO.updateStateByIds(idList, 2);
	}
	
	/**
	 * 根据id查实体
	 * @param iid id
	 * @return 实体
	 */
	public Comment findById(Integer iid){
		return commentDAO.queryForEntityById(iid);
	}
	
	/**
	 * 客户端刷新、更多方法
	 * @param siteid 网站id 
	 * @param titleid 信息id 
	 * @param commentid 评论id
	 * @param page 每页条数
	 * @param ctype 评论类型
	 * @param type 操作类型
	 * @return List<Comment>
	 */
	public List<Comment> findByInfoId(Integer siteid, Integer titleid, Integer commentid, 
			                          Integer page, Integer ctype, Integer type, String uuid){
		return commentDAO.findByInfoId(siteid, titleid, commentid, page, ctype, type, uuid);
	}
	
	/**
	 * 查找文章数
	 * @param titleid titleid
	 * @param ctype ctype
	 * @return int
	 */
	public int findCountByInfoId(Integer titleid, Integer ctype){
		return commentDAO.findCountByInfoId(titleid, ctype);
	}
	
	/**
	 * 根据id删除
	 * @param ids ids
	 * @return boolean
	 */
	public boolean removeByIds(String ids){
		List<Integer> idList = StringUtil.toIntegerList(ids);
		List<Integer> colIds = new ArrayList<Integer>();
		if(CollectionUtils.isEmpty(idList)){
			return false;
		}
		//评论数-1
		for(Integer id : idList){
			Comment comment = findById(id);
			if(comment.getState() == 1){
				infoCountService.modifyCommentCountDes(comment.getInfoId(), comment.getType(), comment.getSiteId());
				colIds.add(comment.getCateId());
			} else if(comment.getState() ==0){
				infoCountService.modifyCommentCountDes1(comment.getInfoId(), comment.getType(), comment.getSiteId());
			}
			
		}
		if(!CollectionUtils.isEmpty(colIds)){
			this.modifyColFlag(colIds);
		}
		return commentDAO.deleteByIds(idList);
	}
	
	/**
	 * 根据信息、报料ids删除评论
	 * @param infoIds ids
	 * @param type 类型 1：信息  2：报料
	 * @param siteId 网站id
	 * @return 是否成功
	 */
	public boolean removeByInfoIds(String infoIds, Integer type, Integer siteId){
		List<Integer> infoIdList = StringUtil.toIntegerList(infoIds);
		if(CollectionUtils.isEmpty(infoIdList) || NumberUtil.getInt(type) == 0){
			return false;
		}
		for(Integer id : infoIdList){
			infoCountService.modifyCommentCount(id, type, siteId);
		}
		return commentDAO.deleteByInfoIds(infoIdList, type);
	}
	
}