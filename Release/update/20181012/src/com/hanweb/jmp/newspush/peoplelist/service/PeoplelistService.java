package com.hanweb.jmp.newspush.peoplelist.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.PinyinUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.controller.role.RoleFormBean;
import com.hanweb.complat.dao.RoleDAO;
import com.hanweb.complat.dao.RoleRelationDAO;
import com.hanweb.complat.dao.RoleRightDAO;
import com.hanweb.complat.entity.Role;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.newspush.peoplelist.dao.PeoplelistDAO;
import com.hanweb.jmp.newspush.peoplelist.entity.PeopleList;
import com.hanweb.support.controller.CurrentUser;

/**
 * 群组Service
 * 
 * @author Wangjw
 * 
 */
public class PeoplelistService {

	
	@Autowired
	private PeoplelistDAO peoplelistDAO;

	/**
	 * 
	 * @param peoplelist
	 *            群组实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 */
	public boolean add(PeopleList peoplelist) throws OperationException {
		if (peoplelist == null) {
			return false;
		}
		List<PeopleList> list = peoplelistDAO.findByName(peoplelist.getName());

		if (!list.isEmpty()) {
			throw new OperationException("存在同名群组！");
		}

		int iid = peoplelistDAO.insert(peoplelist);

		return iid > 0 ? true : false;
	}

	/**
	 * 更新群组
	 * 
	 * @param peoplelist
	 *            群组实体
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 */
	public boolean modify(PeopleList peoplelist) throws OperationException {
		if (peoplelist == null) {
			return false;
		}
		List<PeopleList> list = peoplelistDAO.findByName(peoplelist.getName());

		if (!list.isEmpty()) {
			for(PeopleList peopleList:list){
				if(peopleList.getIid()!=peoplelist.getIid()){
					throw new OperationException("存在同名角色！");
				}
			}
		}

		return peoplelistDAO.update(peoplelist);
	}

	/**
	 * 删除群组
	 * 
	 * @param ids
	 *            群组ID串 如:1,2
	 * @return true - 成功<br/>
	 *         false - 失败
	 * @throws OperationException
	 */
	public boolean removeByIds(String ids) throws OperationException {
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");
		if (CollectionUtils.isEmpty(idsList)) {
			return false;
		}

		boolean isSuccess = false;
		isSuccess = peoplelistDAO.deleteByIds(idsList);// 删除角色
		if (!isSuccess) {
			throw new OperationException("删除角色失败！");
		}
		isSuccess = peoplelistDAO.deleteBypeoplelistId(idsList);
		if (!isSuccess) {
			throw new OperationException("删除群组成员关系失败！");
		}
		return isSuccess;
	}

	/**
	 * 通用群组id获得群组信息
	 * 
	 * @param iid
	 *            角色id
	 * @return
	 */
	public PeopleList findByIid(int iid) {
		return peoplelistDAO.findByIid(iid);
	}
	
	public List<PeopleList> find(){
		return peoplelistDAO.find();
	}

	

}
