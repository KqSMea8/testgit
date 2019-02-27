package com.hanweb.jmp.appstype.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.apps.dao.manage.LightAppDAO;
import com.hanweb.jmp.appstype.dao.LightAppTypeDAO;
import com.hanweb.jmp.appstype.entity.LightAppType;

/**
 * 应用分类操作类
 * @author ZCC
 *
 */
@Service
public class LightAppTypeService {

	@Autowired
	private LightAppTypeDAO lightAppTypeDAO;
	
	@Autowired
	private LightAppDAO lightAppDAO;
	
	/**
	 * 新增应用分类
	 * @param lightapptype
	 * @return
	 */
	public boolean add(LightAppType lightapptype) {
		if(lightapptype ==  null){
			return false;
		}
		//去重查询
		int num = lightAppTypeDAO.findNumByName(NumberUtil.getInt(lightapptype.getIid()), 
				lightapptype.getName(), NumberUtil.getInt(lightapptype.getSiteId()));
		if(num > 0){
			throw new OperationException("轻应用名已存在,请重新设置！");
		}
		lightapptype.setOrderId(findMinOrderIdBySiteId(lightapptype.getSiteId())-1);
		//数据入库
		int iid = lightAppTypeDAO.insert(lightapptype);
		return iid > 0;
	}
	
	/**
	 * 通过网站id查找最小排序id
	 * @param siteId
	 * @return
	 */
	public int findMinOrderIdBySiteId(Integer siteId){
		if(NumberUtil.getInt(siteId) <= 0){
			return 0;
		}
		int minOrderid=lightAppTypeDAO.findMinOrderIdBySiteId(siteId);
		return minOrderid;
	}

	/**
	 * 通过机构ID获得下属分类集合（树）
	 * @param appTypeId
	 * @param int1
	 * @return
	 */
	public List<LightAppType> findChildByIid(Integer appTypeId, int siteId) {
		return lightAppTypeDAO.findChildByIid(appTypeId, siteId);
	}

	/**
	 * 根据iid查找分类实体
	 * @param iid
	 * @return
	 */
	public LightAppType findByIid(int iid) {
		LightAppType type = lightAppTypeDAO.findByIid(iid);
		return type;
	}

	/**
	 * 修改机构
	 * 
	 * @param group
	 *            机构实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 *             界面异常
	 */
	public boolean modify(LightAppType lightapptype) throws OperationException {
		if (lightapptype == null || NumberUtil.getInt(lightapptype.getIid()) == 0) {
			return false;
		}
		Integer iid = lightapptype.getIid();
		boolean isSuccess = false;

		List<Integer> groupIdList = new ArrayList<Integer>();
		groupIdList.add(iid);

		int num = lightAppTypeDAO.findNumByName(NumberUtil.getInt(lightapptype.getIid()), 
				lightapptype.getName(), NumberUtil.getInt(lightapptype.getSiteId()));
		if (num > 0) {
			throw new OperationException("机构名称已存在,请重新设置！");
		}

		isSuccess = lightAppTypeDAO.update(lightapptype);

		if (!isSuccess) {
			throw new OperationException("更新操作失败！");
		}

		return isSuccess;
	}

	/**
	 * 根据siteid获取应用分类
	 * @param siteId
	 * @return
	 */
	public List<LightAppType> findBySiteId(Integer siteId) {
		List<LightAppType> appTypeList = new ArrayList<LightAppType>();
		appTypeList =  lightAppTypeDAO.findBySiteId(NumberUtil.getInt(siteId));
 		return appTypeList;
	}

	/**
	 * 删除应用分类
	 * @param ids
	 * @return
	 */
    public boolean removeByIds(String ids) {
        List<Integer> idList = StringUtil.toIntegerList(ids, ",");
        if (CollectionUtils.isEmpty(idList)) {
            return false;
        }
        // 查找分类下是否还有子分类
        int lightAppTypeNum = lightAppTypeDAO.findCountByTypeId(idList);
        if(lightAppTypeNum > 0){
            throw new OperationException("所选分类下存在子分类,请先删除子分类!");
        }
        
        // 查找分类下应用数量
        int lightAppNum = lightAppDAO.findCountByTypeId(idList);
        if (lightAppNum > 0) {
            throw new OperationException("所选分类下存在轻应用,请先删除轻应用!");
        }
        return lightAppTypeDAO.deleteByIds(idList);
    }

    /**
     * 按顺序查找子分类
     * @param siteId
     * @param pid
     * @return
     */
    public List<LightAppType> findOrderByPid(Integer siteId, String pid) {
        return lightAppTypeDAO.findOrderByPid(siteId, pid);
    }

    /**
     * 修改排序id
     * @param ids
     * @param orderids
     * @param siteId
     * @return
     */
    public boolean modifyOrderIdById(String ids, String orderids, String siteId) {
        if (ids == null || orderids == null) {
            return false;
        }
        List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
        List<Integer> ordersList = StringUtil.toIntegerList(orderids, ",");
        boolean isSuccess = false;
        for (int i = 0, len = idsList.size(); i < len; i++) {
            isSuccess = lightAppTypeDAO.updateOrderIdById(ordersList.get(i), idsList.get(i));
        }
        return isSuccess;
    }
    
    public boolean findCountByAppTypeName(String name, int siteId){
    	return lightAppTypeDAO.findCountByAppTypeName(name, siteId);
    }
    
    public int findIidByAppTypeName(String name, int siteId){
    	return lightAppTypeDAO.findIidByAppTypeName(name, siteId);
    }
    
    /**
	 * 通过id查找 pid
	 * @param id
	 * @return
	 */
	public Integer findPidByIid(Integer id){
		if(id == 0){
			return null;
		}
		return lightAppTypeDAO.findPidByIid(id);
	}

}