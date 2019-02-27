package com.hanweb.jmp.newspush.userdevice.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.common.util.StringUtil;
import com.hanweb.jmp.newspush.userdevice.dao.UserDeviceDAO;
import com.hanweb.jmp.newspush.userdevice.entity.UserDevice;


/**
 * 用户设备Service
 * 
 * @author Qianzq
 * 
 */
public class UserDeviceService {

	@Autowired
	private UserDeviceDAO userDeviceDao;
	
	/**
	 * 根据用户Id获得实体 
	 * @param uid 用户id
	 * @return 实体
	 */
	public List<UserDevice> findByUid(int uid){
		if(uid <= 0){
			return null;
		}
		return userDeviceDao.findByUid(uid);
	}
	
	/**
	 * 根据用户id集合获得实体
	 * @param userIds
	 * @return
	 */
	public List<UserDevice> findByUserIds(List<String> userIds){
		if(userIds == null || userIds.size() <= 0){
			return null;
		}
		return userDeviceDao.findByUserid(userIds);
	}
	
	/**
	 * 根据用户id获得实体
	 * @param userIds
	 * @return
	 */
	public UserDevice findByUserId(String userId){
		if(userId == null || userId.length() <= 0){
			return null;
		}
		return userDeviceDao.findByUserid(userId);
	}
	
	/**
	 * 新增用户设备
	 * @param userDevice 实体
	 * @return true:成功 false:失败
	 */
	public boolean add(UserDevice userDevice){
		if(userDevice == null){
			return false;
		}
		int id = userDeviceDao.insert(userDevice);
		return id > 0 ? true : false;
	}
	
	/**
	 * 删除设备记录
	 * @param list 设备List
	 * @return true:成功 false:失败
	 */
	public boolean del(List<UserDevice> list){
		if(CollectionUtils.isEmpty(list)){
			return false;
		}
		StringBuilder sb = new StringBuilder(128);
		for(UserDevice ud : list){
			sb.append(ud.getIid() + ",");
		}
		return userDeviceDao.deleteByIds(StringUtil.toIntegerList(sb.toString()));
	}
	
	/**
	 * 根据uuid获得设备
	 * @param uuid 设备uuid
	 * @return 设备
	 */
	public UserDevice findByUuid(String uuid){
		if(StringUtil.isEmpty(uuid)){
			return null;
		}
		return userDeviceDao.findByUuid(uuid);
	}
	
	/**
	 * 获得服务器下所有设备
	 * @return
	 */
	public List<UserDevice> findAllDevice(){
		return userDeviceDao.findAllDevice();
	} 
	
	/**
	 * 根据uuid删除设备
	 * @param uuid
	 * @return
	 */
	public boolean delByUuid(String uuid){
		return userDeviceDao.delByUuid(uuid);
	}
}
