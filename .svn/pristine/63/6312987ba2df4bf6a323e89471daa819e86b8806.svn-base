package com.hanweb.jmp.cms.service.infos;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.complat.exception.OperationException;

import com.hanweb.jmp.cms.dao.infos.InfoCountDAO;
import com.hanweb.jmp.cms.entity.infos.InfoCount;

public class InfoCountService {
	
	/**
	 * infoCountDAO
	 */
	@Autowired
	private InfoCountDAO infoCountDAO;
	
	/**
	 * 修改阅读数（需要加缓存）
	 * @param titleid 信息id
	 * @param type 类型  1：信息  2：报料
	 * @param siteId 网站id
	 * @return 是否成功
	 */
	public boolean mVisitCount(Integer titleid, Integer type, Integer siteId){ 
		infoCountDAO.updateCountAdd(titleid, type, "visitcount", siteId);  
		return true;
	}
	
	/**
	 * 修改阅读数（需要考虑缓存）
	 * @param titleid 信息id
	 * @param type 类型  1：信息  2：报料
	 * @param count 数量
	 * @param siteId 网站id
	 * @return 是否成功
	 */
	public boolean modifyVisitCount(Integer titleid, Integer type, Integer count, Integer siteId){
	    infoCountDAO.updateCount(titleid, type, "visitcount", count, siteId); 
		return true;
	}
	
	/**
	 * 修改评论数
	 * @param titleid 信息id
	 * @param type 类型  1：信息  2：报料
	 * @param siteId 网站id
	 * @return 是否成功
	 */
	public boolean modifyCommentCount(Integer titleid, Integer type, Integer siteId){
		infoCountDAO.updateCountAdd(titleid, type, "commentcount", siteId);
		return true;
	}
	
	/**
	 * 删除已审核时修改评论数
	 * @param titleid 信息id
	 * @param type 类型  1：信息  2：报料
	 * @param siteId 网站id
	 * @return 是否成功
	 */
	public boolean modifyCommentCountDes(Integer titleid, Integer type, Integer siteId){
	   infoCountDAO.updateCountDes(titleid, type, "commentcount", siteId);
	   return true;
	}
	
	/**
	 * 删除未审核时修改评论数
	 * @param titleid 信息id
	 * @param type 类型  1：信息  2：报料
	 * @param siteId 网站id
	 * @return 是否成功
	 */
	public boolean modifyCommentCountDes1(Integer titleid, Integer type, Integer siteId){
	   infoCountDAO.updateCountDes1(titleid, type, "commentcount", siteId);
	   return true;
	}
	
	/**
	 * 修改评论数
	 * @param titleid 信息id
	 * @param type 类型  1：信息  2：报料
	 * @param count count
	 * @param siteId siteId
	 * @return 是否成功
	 */
	public boolean modifyCommentCount(Integer titleid, Integer type, Integer count, Integer siteId){
		infoCountDAO.updateCount(titleid, type, "commentcount", count, siteId);
		return true;
	}
	
	/**
	 * 修改点赞数
	 * @param titleid 信息id
	 * @param type 类型  1：信息  2：报料
	 * @param siteId siteId
	 * @return 是否成功
	 */
	public boolean modifyGoodCount(Integer titleid, Integer type, Integer siteId){
		infoCountDAO.updateCountAdd(titleid, type, "goodcount", siteId);
		return true;
	}
	
	/**
	 * 修改点赞数
	 * @param titleid 信息id
	 * @param type 类型  1：信息  2：报料
	 * @param count 数目
	 * @param siteId siteId
	 * @return 是否成功
	 */
	public boolean modifyGoodCount(Integer titleid, Integer type, Integer count, Integer siteId){
		boolean isSuccess = infoCountDAO.updateCount(titleid, type, "goodcount", count, siteId);
		return isSuccess;
	}
	
	/**
	 * 根据信息id和uuid查找信息(没有事务)
	 * @param infoId 信息id
	 * @param type 类型  1：信息  2：报料
	 * @param uuid 手机uuid
	 * @param siteId siteId
	 * @return  是否成功
	 */
	public InfoCount findByInfoId(Integer infoId, Integer type, String uuid, Integer siteId){
		return infoCountDAO.findByTitleIdAndType(infoId, type, uuid, siteId);
	}
	
	/**
	 * 查询信息数量
	 * @param titleId     信息ID
	 * @param type        类型
	 * @param siteId siteId
	 * @return       数量
	 */
	public int findCountByTitleid(Integer titleId, Integer type, Integer siteId) {
		int count = infoCountDAO.findCountByTitleid(titleId, type, siteId);
		return count;
	}
	
	/**
	 * 查询信息数量
	 * @param infoCountEn     infoCountEn
	 * @param siteId        siteId
	 * @return       数量
	 */
	public Integer add(InfoCount infoCountEn, Integer siteId) {
		int iid=0;
		if(infoCountEn != null){
			iid=infoCountDAO.insert(infoCountEn, this.getTableName(siteId)); 
		} 
		return iid;
	} 
	 
	/**
	 * removeByIds:删除信息/栏目时，关系表也要删除.
	 * @param ids ids
	 * @param type type
	 * @param siteId siteId
	 * @return boolean
	 * @throws OperationException    设定参数 .
	*/
	public boolean removeByIds(List<Integer> ids, Integer type, Integer siteId) throws OperationException {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return infoCountDAO.deleteByIds(ids, type, siteId);
	}
	 
	/**
	 * addTable:增加信息表.
	 * @param tableName  表名
	 * @return    设定参数 .
	*/
	public boolean addTable(String tableName){
		return infoCountDAO.addTable(tableName);
	}
	
	/**
	 * 删除信息表
	 * @param tableName 表名
	 * @return boolean
	 */
	public boolean deleteTable(String tableName){
		return infoCountDAO.deleteTable(tableName);
	}
	
	/**
	 * 获得不同网站的信息表名
	 * @param iid 网站Id
	 * @return String
	 */
	public String getTableName(Integer iid){
		String tableName = "jmp_info_count" + iid;
		return tableName;
	}
	
}