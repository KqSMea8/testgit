package com.hanweb.jmp.sys.service.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil; 
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;

import com.hanweb.jmp.sys.dao.role.RolePushTypeDAO;
import com.hanweb.jmp.sys.entity.role.RolePushType;
import com.hanweb.support.controller.CurrentUser;

public class RolePushTypeService {
	
	/**
	 * rolePushTypeDAO
	 */
	@Autowired
	RolePushTypeDAO rolePushTypeDAO;
	
	/**
	 * 获得角色的栏目集合
	 * @param roleId
	 *            角色ID
	 * @return 栏目实体集合
	 */
	public List<RolePushType> findByRoleId(int roleId) {
		if (roleId <= 0) {
			return null;
		}
		List<RolePushType> rolePushType = rolePushTypeDAO.findByRoleId(roleId);

		return rolePushType;
	}
	
	/**
	 * 修改角色推送分类
	 * @param roleId
	 *            角色ID
	 * @param pushTypeList
	 *           推送分类ID集
	 * @return boolean
	 * @throws OperationException
	 *             错误提示信息.
	 */
	public boolean modifyRolePushType(int roleId, List<Integer> pushTypeList) throws OperationException {
		if (roleId <= 0) {
			return false;
		}
		List<Integer> roleIds = StringUtil.toIntegerList(roleId + "", ",");
		boolean isSuccess = rolePushTypeDAO.deleteByRoleIds(roleIds);
		if (!isSuccess) {
			throw new OperationException("删除角色原有推送分类失败");
		}
		RolePushType rolePushType = null;
		Integer iid = null;  
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		int siteId = currentUser.getSiteId();
		for (Integer pushTypeId : pushTypeList) {
			if (NumberUtil.getInt(pushTypeId) <= 0) {
				continue;
			} 
			rolePushType = new RolePushType();
			rolePushType.setRoleId(roleId);
			rolePushType.setPushTypeId(pushTypeId);
			rolePushType.setSiteId(siteId);
			iid = rolePushTypeDAO.insert(rolePushType);
			if (NumberUtil.getInt(iid) <= 0) {
				throw new OperationException("添加角色推送分类失败");
			}
		}
		return true;
	}
	
}