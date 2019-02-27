package com.hanweb.jmp.interfacesmanage.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.jmp.interfacesmanage.dao.InterfaceDao;
import com.hanweb.jmp.interfacesmanage.dao.InterfaceParamDao;
import com.hanweb.jmp.interfacesmanage.entity.Interfaces;
import com.hanweb.jmp.interfacesmanage.entity.InterfacesParam;

@Service
public class InterfaceService {

	@Autowired
	private InterfaceDao interfaceDao;

	@Autowired
	private InterfaceParamDao interfaceParamDao;

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 * @throws OperationException
	 */
	public boolean removeByIds(String ids) throws OperationException {
		List<Integer> idsLsit = StringUtil.toIntegerList(ids, ",");

		if (CollectionUtils.isEmpty(idsLsit)) {
			return false;
		}
		boolean isSuccess = false;
		interfaceDao.findByIds(idsLsit);
		isSuccess = interfaceDao.deleteByIds(idsLsit);/* 删除接口 */
		List<Integer> idList = new ArrayList<Integer>();
		List<InterfacesParam> params = interfaceParamDao
				.findParamsByIids(idsLsit);
		if (params != null && params.size() > 0) {
			for (InterfacesParam param : params) {
				idList.add(param.getIid());
			}
			interfaceParamDao.deleteByIds(idList);
		}

		if (!isSuccess) {
			throw new OperationException("删除接口失败!");
		}

		return isSuccess;
	}

	/**
	 * 新增方法
	 * 
	 * @param inter
	 * @return
	 */
	public boolean add(Interfaces inter) {
		if (inter == null) {
			return false;
		}

		int num = this.findNumOfSameName(inter.getIid(), inter.getName());
		if (num > 0) {
			throw new OperationException("接口名称已存在,请重新设置！");
		}

		interfaceDao.insert(inter);

		List<InterfacesParam> params = inter.getParams();
		if (params != null) {
			List<String> nameList = new ArrayList<String>();
			String name = "";
			for (InterfacesParam param : params) {
				name = param.getName().trim();
				if (name != null && name != "") {
					nameList.add(name);
				}
			}
			boolean hasSame = nameList.size() != new HashSet<String>(nameList)
					.size();
			if (hasSame) {
				throw new OperationException("接口参数名称重复");
			}
			for (InterfacesParam param : params) {
				name = param.getName().trim();
				if (name != null && name != "") {
					param.setInterfaceid(inter.getIid());
					param.setName(name);
					interfaceParamDao.insert(param);
				}
			}
		}

		boolean isSuccess = interfaceDao.updateInterface(inter);
		return isSuccess;
	}

	/**
	 * 通过接口信息ID、接口名获得同名接口的个数
	 * 
	 * @param iid
	 * @param name
	 * @return
	 */
	public int findNumOfSameName(Integer iid, String name) {
		if (StringUtil.isEmpty(name)) {
			return 0;
		}
		int num = interfaceDao.findNumOfSameName(iid, name);
		return num;
	}

	/**
	 * 通过接口信息ID获取接口信息
	 * 
	 * @param iid
	 * @return
	 */
	public Interfaces findByIid(Integer iid) {
		Interfaces inter = interfaceDao.findByIid(iid);
		return inter;
	}

	/**
	 * 修改
	 * 
	 * @param inter
	 * @return
	 * @throws OperationException
	 */
	public boolean modify(Interfaces inter) throws OperationException {
		if (inter == null || NumberUtil.getInt(inter.getIid()) == 0) {
			return false;
		}

		Integer id = inter.getIid();
		boolean isSuccess = false;

		List<Integer> interIdList = new ArrayList<Integer>();

		interIdList.add(id);

		int num = this.findNumOfSameName(id, inter.getName());
		if (num > 0) {
			throw new OperationException("接口名称已存在,请重新设置！");
		}

		isSuccess = interfaceDao.update(inter);

		if (!isSuccess) {
			throw new OperationException("更新操作失败！");
		}
		List<InterfacesParam> params = inter.getParams();
		List<InterfacesParam> params2 = interfaceParamDao.findParamsByIid(
				inter.getIid(), null);

		List<Integer> idList = new ArrayList<Integer>();
		if (!(params2 == null || params2.size() == 0)) {
			for (InterfacesParam param : params2) {
				idList.add(param.getIid());
			}
			interfaceParamDao.deleteByIds(idList);
		}
		if (params != null) {
			List<String> nameList = new ArrayList<String>();
			String name = "";
			for (InterfacesParam param : params) {
				name = param.getName().trim();
				if (name != null && name != "") {
					nameList.add(name);
				}
			}
			boolean hasSame = nameList.size() != new HashSet<String>(nameList)
					.size();
			if (hasSame) {
				throw new OperationException("接口参数名称重复");
			}
			for (InterfacesParam param : params) {
				name = param.getName().trim();
				if (name != null && name != "") {
					param.setInterfaceid(inter.getIid());
					param.setName(name);
					interfaceParamDao.insert(param);
				}
			}
		}

		isSuccess = interfaceDao.update(inter);

		return isSuccess;
	}

	/**
	 * 根据接口id查找接口参数
	 * 
	 * @param iid
	 * @return
	 */
	public List<InterfacesParam> findParamsByIid(Integer iid, Integer isRequired) {
		return interfaceParamDao.findParamsByIid(iid, isRequired);
	}
	
	
	/**
	 * 根据接口id查询参数
	 * @param interfaceid
	 * @return
	 */
	public List<InterfacesParam> findParamByName(Integer interfaceid) {
		
		return interfaceParamDao.findParamByName(interfaceid);
		
	}
	
	

}
