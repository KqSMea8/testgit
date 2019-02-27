package com.hanweb.jmp.newspush.userdevice.service;


import org.springframework.beans.factory.annotation.Autowired;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.entity.User;
import com.hanweb.complat.service.UserService;

public class InterfaceService {
	
	@Autowired
	UserService userService;
	
	   
	/**
	 * 检查登录名/安全码是否正确，不正确接口不返回数据
	 * @param uname 登录名
	 * @param code 安全码
	 * @return true：正确 false：错误
	 */
	public User checkUname(String uname, String code){
		if(StringUtil.isEmpty(uname)){
			return null;
		}
		User u = userService.findByLoginName(uname);
		/*if(code.equals(u.getSecCode())){
			return u;
		}*/
		
		return u;
	}
	
	/**
	 * 检查必填参数
	 * @param clientType 客户端类型
	 * @param uuid 客户端uuid
	 * @return true：正确 false：错误
	 */
	public boolean checkPar(Integer clientType, String uuid){
		if(clientType == null || StringUtil.isEmpty(uuid) || clientType < 1 || clientType > 2){
			return false;
		}
		return true;
	}
	
	/**
	 * 接口3.3参数检查
	 * @param state 0：激活 1：注销 (默认0)
	 * @param appid 百度appid，详见接口文档
	 * @param channelid 百度channelid，详见接口文档
	 * @param userid 百度userid，详见接口文档
	 * @return json
	 */
	public boolean checkUserPar(String state, String appid, String channelid){
		int sta = NumberUtil.getInt(state, 0);
		if(sta < 0 || sta > 1){
			return false;
		}
		return true;
	}

	
}
