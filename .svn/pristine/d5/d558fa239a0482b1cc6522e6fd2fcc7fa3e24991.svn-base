package com.hanweb.complat.service;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.complat.dao.IpBanListDAO;
import com.hanweb.complat.entity.IpBanList;

/**
 * 封停
 * 
 * @author 王建卫
 * 
 */
public class IpBanListService {
	
	@Autowired
	private IpBanListDAO ipBanListDAO;
	
	
	public IpBanList findByIp(String ip){
		return ipBanListDAO.findByIp(ip);
	}
	
	public boolean removeByIp(String ip) {
		return ipBanListDAO.deleteByIp(ip);
	}
	
	public boolean addIpBanList(IpBanList ipBanList){
		ipBanList.setLoginDate(new Date());
		IpBanList ipbanlist = findByIp(ipBanList.getIpAddr());
		if(ipbanlist != null){
			ipBanList.setIid(ipbanlist.getIid());
			return ipBanListDAO.update(ipBanList);
		}else{
			return ipBanListDAO.insert(ipBanList)>0;
		}
	}
	
}
