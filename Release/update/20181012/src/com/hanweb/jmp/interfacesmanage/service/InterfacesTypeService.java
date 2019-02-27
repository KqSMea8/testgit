package com.hanweb.jmp.interfacesmanage.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.interfacesmanage.dao.InterfacesTypeDao;
import com.hanweb.jmp.interfacesmanage.entity.InterfacesType;

@Service
public class InterfacesTypeService {

	@Autowired
	private InterfacesTypeDao interfaceTypeDao;

	/**
	 * 根据接口类型id 删除接口类型
	 * 
	 * @param iid
	 * @return
	 * @throws OperationException
	 */
	public boolean removeByids(String ids) throws OperationException {
		List<Integer> idsList = StringUtil.toIntegerList(ids, ",");

		
		if (CollectionUtils.isEmpty(idsList)) {
			return false;
		}

		if (idsList.size() > 1) {
			throw new OperationException("一次只能删除一个类型！");
		}
		
		boolean isSuccess = false;

		boolean type = this.checkSubType(ids);

		boolean pid = this.checkSubPid(ids);

		if (type) {
			throw new OperationException("删除类型失败，该类型下有接口存在！");
		} else
			if (pid) {
			throw new OperationException("删除类型失败，该类型下有子类型存在！");
		} else {
			isSuccess = interfaceTypeDao.deleteByIds(idsList);// 删除类型
		}

		if (!isSuccess) {
			throw new OperationException("删除接口类型失败！");
		}

		return isSuccess;
	}

	/**
	 * 查询接口里的接口类型id个数
	 * 
	 * @param ids
	 * @return
	 */
	public boolean checkSubType(String ids) {
		List<Integer> idsList = StringUtil.toIntegerList(ids, " ,");
		if (CollectionUtils.isEmpty(idsList)) {
			return false;
		}

		int num = interfaceTypeDao.findInterfaceTypeId(idsList);

		return num > 0 ? true : false;
	}

	/**
	 * 查询类型里的父id个数
	 * 
	 * @param ids
	 * @return
	 */
	public boolean checkSubPid(String ids) {
		List<Integer> idsList = StringUtil.toIntegerList(ids, " ,");
		if (CollectionUtils.isEmpty(idsList)) {
			return false;
		}

		int num = interfaceTypeDao.findTypeByPid(idsList);

		return num > 0 ? true : false;
	}

	/**
	 * 新增接口类型
	 * 
	 * @param interfaceType
	 * @return
	 */
	public boolean add(InterfacesType interfaceType) throws OperationException {

		if (interfaceType == null) {
			return false;
		}

		int num = this.findNumOfSameName(interfaceType.getIid(),
				interfaceType.getName(), interfaceType.getPid());
		if (num > 0) {
			throw new OperationException("接口类型已存在,请重新设置！");
		}

		int iid = interfaceTypeDao.insert(interfaceType);

		return iid > 0 ? true : false;

	}

	/**
	 * 通过接口类型ID、接口类型名称获得同名接口的个数
	 * 
	 * @param iid
	 * @param name
	 * @return
	 */
	public int findNumOfSameName(Integer iid, String name, Integer pid) {
		if (StringUtil.isEmpty(name)) {
			return 0;
		}
		int num = interfaceTypeDao.findNumBySameName(iid, name, pid);
		return num;
	}

	/**
	 * 修改接口类型
	 * 
	 * @param inter
	 * @return
	 * @throws OperationException
	 */
	public boolean modify(InterfacesType interfaceType)
			throws OperationException {
		if (interfaceType == null
				|| NumberUtil.getInt(interfaceType.getIid()) == 0) {
			return false;
		}
		Integer id = interfaceType.getIid();
		Integer pid = interfaceType.getPid();
		boolean isSuccess = false;

		List<Integer> interIdList = new ArrayList<Integer>();

		interIdList.add(id);

		int num = this.findNumOfSameName(id, interfaceType.getName(), pid);
		if (num > 0) {
			throw new OperationException("接口类型已存在,请重新设置！");
		}

		isSuccess = interfaceTypeDao.update(interfaceType);

		if (!isSuccess) {
			throw new OperationException("更新操作失败！");
		}

		return isSuccess;
	}

	/**
	 * 通过接口类型ID获取接口信息
	 * 
	 * @param iid
	 * @return
	 */
	public InterfacesType findByIid(Integer iid) {
		InterfacesType interfaceType = interfaceTypeDao.findByIid(iid);
		return interfaceType;
	}

	/**
	 * 查询所有接口类型集合
	 * 
	 * @return
	 */
	public List<InterfacesType> findAllType() {
		List<InterfacesType> typeList = interfaceTypeDao.findAllInterfaceType();
		return typeList;
	}

	/**
	 * 查询所有类型名称
	 */
	public List<InterfacesType> findTypeByName() {
		List<InterfacesType> listType = interfaceTypeDao.findTypeByName();
		return listType;
	}

	/**
	 * 通过类型ID获得下属类型集合（树）
	 */
	public List<InterfacesType> findChildTypeByIid(Integer iid) {
		return interfaceTypeDao.findChildTypeByIid(iid);
	}

}
