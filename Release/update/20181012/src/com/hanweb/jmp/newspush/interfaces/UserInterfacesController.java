package com.hanweb.jmp.newspush.interfaces;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.entity.User;
import com.hanweb.complat.service.UserService;
import com.hanweb.jmp.constant.ErrorCode;
import com.hanweb.jmp.newspush.userdevice.entity.UserDevice;
import com.hanweb.jmp.newspush.userdevice.service.InterfaceService;
import com.hanweb.jmp.newspush.userdevice.service.JsonService;
import com.hanweb.jmp.newspush.userdevice.service.UserDeviceService;

/**
 * 用户相关接口
 * 
 * @author Qianzq
 * 
 */
@Controller
@RequestMapping("interfaces/user")
public class UserInterfacesController {
	
	@Autowired
	InterfaceService interfaceService;

	@Autowired
	JsonService jsonService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserDeviceService userDeviceService;
	
	
	
	/**
	 * 3.3用户激活接口
	 * @param security_code 安全码
	 * @param client_type 客户端类型  1：android 2:iphone
	 * @param uuid 客户端uuid
	 * @param uname 用户名
	 * @param state 0：激活 1：注销 (默认0)
	 * @param userid 百度userid，详见接口文档， 信鸽为token
	 * @return json
	 */
	@RequestMapping(value = "user_activate")
	@ResponseBody
	public String serverPar(String security_code, Integer client_type, String uuid, String uname,
			String state, String userid){
		try {
			User u = null;
			boolean isSuccess = true;
			if(NumberUtil.getInt(state) == 0){ //用户设备激活
				if(StringUtil.isNotEmpty(uname)){
					u = interfaceService.checkUname(uname, security_code);
					if(u == null){
						return jsonService.getError("0106", ErrorCode.ERROR_CODE_0106);
					}
				}
				if(u != null && NumberUtil.getInt(u.getIid()) > 0){
					//改设备原先如果是私有用户，也要注销
					UserDevice dud = userDeviceService.findByUuid(uuid);
					if(dud != null){
						userDeviceService.delByUuid(uuid);//删设备..
					}
				}
				
				//设备表新增
				UserDevice device = new UserDevice();
				if(userid != null && !"".equals(userid)){ //token没传不入库
					device = userDeviceService.findByUserId(userid);
				}
				if(device == null){
					device = new UserDevice();
					device.setUserid(userid);
					device.setUsid(u.getIid());
					device.setUuid(uuid);
					device.setClientType(client_type);
					isSuccess = userDeviceService.add(device);
				}
				
				
			}else{
				 //用户设备注销
				userDeviceService.delByUuid(uuid);//删设备
				if(!isSuccess){
					return jsonService.getError("0108", ErrorCode.ERROR_CODE_0108);
				}
			}
			
			return jsonService.retSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonService.getError("0108", ErrorCode.ERROR_CODE_0108);
		}
	}
	
	
}
