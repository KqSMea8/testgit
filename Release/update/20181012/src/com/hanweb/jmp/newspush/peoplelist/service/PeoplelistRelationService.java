package com.hanweb.jmp.newspush.peoplelist.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.complat.entity.RoleRelation;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.newspush.peoplelist.dao.PeoplelistRelationDAO;
import com.hanweb.jmp.newspush.peoplelist.entity.PeoplelistRelation;
import com.hanweb.support.controller.CurrentUser;

/**
 * 群组成员关系Service
 * 
 * @author Wangjw
 * 
 */
public class PeoplelistRelationService {

	
	@Autowired
	private PeoplelistRelationDAO peoplelistRelationDAO;

	
	public int findGroupMemberSize(Integer peoplelistId, Integer groupId) {
		if (NumberUtil.getInt(peoplelistId) == 0 || peoplelistId == null) {
			return 0;
		}
		return peoplelistRelationDAO.findGroupMemberSize(peoplelistId, groupId);
	}
	
	
	public boolean addGroup(Integer peoplelistId, Integer groupId) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser(); 
		PeoplelistRelation peoplelistRelation = new PeoplelistRelation();
		peoplelistRelation.setPeoplelistId(peoplelistId);
		peoplelistRelation.setGroupId(groupId);
		peoplelistRelation.setSiteId(currentUser.getSiteId());
		int iid = peoplelistRelationDAO.insert(peoplelistRelation);

		return iid > 0 ? true : false;
	}
	
	public int findUserMemberSize(Integer peoplelistId, Integer userId) {
		if (NumberUtil.getInt(peoplelistId) == 0 || NumberUtil.getInt(peoplelistId) == 0) {
			return 0;
		}

		return peoplelistRelationDAO.findUserMemberSize(peoplelistId, userId);
	}
	
	public boolean addUser(Integer peoplelistId, Integer userId) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser(); 
		PeoplelistRelation peoplelistRelation = new PeoplelistRelation();
		peoplelistRelation.setPeoplelistId(peoplelistId);
		peoplelistRelation.setUserId(userId);
		peoplelistRelation.setSiteId(currentUser.getSiteId());
		int iid = peoplelistRelationDAO.insert(peoplelistRelation);

		return iid > 0 ? true : false;
	}
	
	
	public boolean removeUsersByRoleId(Integer peoplelistId, List<Integer> userIdList) {

		boolean isSuccess = peoplelistRelationDAO.deleteUsers(peoplelistId, userIdList);

		return isSuccess;
	}
	
	public boolean removeGroupsByRoleId(Integer peoplelistId, List<Integer> groupIdList) {

		boolean isSuccess = peoplelistRelationDAO.deleteGroups(peoplelistId, groupIdList);

		return isSuccess;
	}
	
	public boolean clean(Integer peoplelistid) {
		if (NumberUtil.getInt(peoplelistid) == 0) {
			return false;
		}
		List<Integer> peoplelistIdList = new ArrayList<Integer>();
		peoplelistIdList.add(peoplelistid);

		boolean isSuccess = peoplelistRelationDAO.deleteByroleId(peoplelistIdList);

		return isSuccess;
	}
	
	/**
	 * 根据群组id查询关系实体
	 * @param peoplelistid
	 * @return
	 */
    public List<PeoplelistRelation> findBypeoplelistid(Integer peoplelistid) {
    	return peoplelistRelationDAO.findBypeoplelistid(peoplelistid);
    }


}
