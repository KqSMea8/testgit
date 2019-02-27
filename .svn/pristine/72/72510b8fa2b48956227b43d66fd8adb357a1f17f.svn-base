package com.hanweb.jmp.sys.service.role;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;

import com.hanweb.jmp.sys.dao.role.RoleColDAO;
import com.hanweb.jmp.sys.entity.role.RoleCol;
import com.hanweb.support.controller.CurrentUser;

public class RoleColService {
	
	/**
	 * roleColDAO
	 */
	@Autowired
	RoleColDAO roleColDAO;
	
	/**
	 * 获得角色的栏目集合
	 * @param roleId
	 *            角色ID
	 * @return 栏目实体集合
	 */
	public List<RoleCol> findByRoleId(int roleId) {
		if (roleId <= 0) {
			return null;
		}
		List<RoleCol> roleColList = roleColDAO.findByRoleId(roleId);
		return roleColList;
	}
	
	/**
	 * 修改角色栏目
	 * @param roleId
	 *            角色ID
	 * @param roleCols
	 *           栏目ID集
	 * @return boolean
	 * @throws OperationException
	 *             错误提示信息.
	 */
	public boolean modifyRoleCol(int roleId, Map<Integer, RoleCol> roleCols) throws OperationException {
		if (roleId <= 0) {
			return false;
		}
		List<Integer> roleIds = StringUtil.toIntegerList(roleId + "", ",");
		boolean isSuccess = roleColDAO.deleteByRoleIds(roleIds);
		if (!isSuccess) {
			throw new OperationException("删除角色原有栏目失败");
		}
		RoleCol roleCol = null;
		Integer iid = null;
		CurrentUser currentUser = UserSessionInfo.getCurrentUser(); 
		int siteid = currentUser.getSiteId();
		for (Integer colid : roleCols.keySet()) {
			
			if(NumberUtil.getInt(colid)<=0){
				continue;
			}
			roleCol = roleCols.get(colid);
			roleCol.setSiteId(siteid);
			iid = roleColDAO.insert(roleCol);
			if (NumberUtil.getInt(iid) <= 0) {
				throw new OperationException("添加角色栏目失败");
			} 
	    } 
		return true;
	}
	
}