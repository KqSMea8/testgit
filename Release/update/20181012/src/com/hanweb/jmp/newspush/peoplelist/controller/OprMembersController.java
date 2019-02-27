package com.hanweb.jmp.newspush.peoplelist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.annotation.Permission;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.complat.service.RoleRelationService;
import com.hanweb.jmp.newspush.peoplelist.service.PeoplelistRelationService;

/**
 * 角色成员操作控制器
 * 
 * @author ZhangC
 * 
 */
@Controller
@Permission(module = "role_members")
@RequestMapping("manager/peoplelist/members")
public class OprMembersController {

	@Autowired
	private RoleRelationService relationService;
	
	@Autowired
	private PeoplelistRelationService peoplelistRelationService;

	/**
	 * 保存群组成员关系
	 * 
	 * @param roleId
	 *            角色ID
	 * @param groups
	 *            机构ID串 如:1,2,3,4
	 * @param users
	 *            用户ID串 如:1,2,3,4
	 * @return
	 */
	@Permission(function = "modify_submit")
	@RequestMapping(value = "modify_submit")
	@ResponseBody
	public JsonResult submitModifyMembers(Integer peoplelistid, String groups, String users) {
		List<Integer> groupIdList = StringUtil.toIntegerList(groups, ",");
		List<Integer> userIdList = StringUtil.toIntegerList(users, ",");

		boolean isSuccess = true;

		for (Integer groupId : groupIdList) {
			if (peoplelistRelationService.findGroupMemberSize(peoplelistid, groupId) == 0) {
				isSuccess = peoplelistRelationService.addGroup(peoplelistid, groupId);
				if (!isSuccess) {
					break;
				}
			}
		}

		if (isSuccess) {
			for (Integer userId : userIdList) {
				if (peoplelistRelationService.findUserMemberSize(peoplelistid, userId) == 0) {
					isSuccess = peoplelistRelationService.addUser(peoplelistid, userId);
					if (!isSuccess) {
						break;
					}
				}
			}
		}
		JsonResult jsonResult = JsonResult.getInstance();
		jsonResult.setSuccess(isSuccess);
		if (isSuccess) {
			jsonResult.setMessage("opr.success");
		} else {
			jsonResult.setMessage("opr.fail");
		}
		return jsonResult;
	}

	/**
	 * 移除对应的群组成员关系
	 * 
	 * @param roleId
	 *            角色ID
	 * @param groups
	 *            机构ID串 如:1,2,3,4
	 * @param users
	 *            用户ID串 如:1,2,3,4
	 * @return
	 */
	@Permission(function = "remove")
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(Integer peoplelistid, String userIds, String groupIds) {
		boolean isSuccess = true;

		List<Integer> userIdList = StringUtil.toIntegerList(userIds);
		if (userIdList.size() > 0) {
			isSuccess = peoplelistRelationService.removeUsersByRoleId(peoplelistid, userIdList);
		}

		if (isSuccess) {
			List<Integer> groupIdList = StringUtil.toIntegerList(groupIds);
			if (groupIdList.size() > 0) {
				isSuccess = peoplelistRelationService.removeGroupsByRoleId(peoplelistid, groupIdList);
			}
		}
		JsonResult jsonResult = JsonResult.getInstance();
		if (isSuccess) {
			jsonResult.set(ResultState.REMOVE_SUCCESS);
		} else {
			jsonResult.set(ResultState.REMOVE_FAIL);
		}
		return jsonResult;
	}

	/**
	 * 清空群组成员关系
	 * 
	 * @param roleId
	 *            角色ID
	 * @return
	 */
	@Permission(function = "clean")
	@RequestMapping(value = "clean")
	@ResponseBody
	public JsonResult clean(Integer peoplelistid) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = peoplelistRelationService.clean(peoplelistid);
		jsonResult.setSuccess(isSuccess);
		if (isSuccess) {
			jsonResult.setMessage("opr.success");
		} else {
			jsonResult.setMessage("opr.fail");
		}
		return jsonResult;
	}
}