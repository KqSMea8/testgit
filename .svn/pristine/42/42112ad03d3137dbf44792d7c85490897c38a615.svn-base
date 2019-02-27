package com.hanweb.jmp.sys.service.collect;
  

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;

import com.hanweb.jmp.sys.controller.field.FieldFormBean;
import com.hanweb.jmp.sys.dao.collect.CollectDAO;
import com.hanweb.jmp.sys.dao.field.FieldDAO;
import com.hanweb.jmp.sys.entity.collect.Collect;

/**
 * 收藏管理Service
 * @author hzz
 *
 */
@Service
public class CollectService { 
	
	
	@Autowired
	private CollectDAO collectDAO; 
	
	
	/**
	 * 新建收藏
	 * @param collect
	 * @return
	 */
	public boolean add(Collect collect) {
		if(collect == null){
			return false;
		}
		Integer iid = collectDAO.insert(collect);
		return iid > 0;
	}
	
	/**
	 * 删除收藏
	 * @param iid
	 * @return
	 */
	public boolean delete(Integer iid){
		if(iid == null){
			return false;
		}
		return collectDAO.deleteById(iid);
	}

	/**
	 * 查找收藏
	 * @param userId 用户id
	 * @param type   类型1应用 2信息
	 * @param collectId 收藏id
	 * @return
	 */
	public Collect findCollect(String userId, Integer type, Integer collectId, Integer siteId){
		if(userId == null || type == null || collectId == null || siteId == null){
			return null;
		}
		return collectDAO.findCollect(userId, type, collectId, siteId);
	}
	
	/**
	 * 通过用户和类型查找收藏实体
	 * @param userId
	 * @param type
	 * @param siteId
	 * @return
	 */
	public List<Collect> findCollectByUseridAndType(String userId, Integer type, Integer siteId){
		if(userId == null || type == null || siteId == null){
			return null;
		}
		return collectDAO.findCollectByUseridAndType(userId, type, siteId);
	}
	
	/**
	 * 查找收藏
	 * @param userId    用户id
	 * @param type      类型1应用 2信息
	 * @param siteId    站点
	 * @param collectId 收藏id
	 * @return
	 */
	public List<Collect> findRealCollect(String userId, Integer type, Integer siteId, String collectId){
		if(userId == null || type == null || siteId == null || collectId == null){
			return null;
		}
		return collectDAO.findRealCollect(userId, type, siteId, collectId);
	}
	
}