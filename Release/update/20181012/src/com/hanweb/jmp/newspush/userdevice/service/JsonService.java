package com.hanweb.jmp.newspush.userdevice.service;

import java.util.HashMap;
import com.hanweb.common.util.JsonUtil;


public class JsonService {

	/**
	 * 返回错误json及原因
	 * @param code 错误码
	 * @param msg  解释
	 * @return json
	 */
	public String getError(String code, String msg){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("result", "false");
		map.put("error_code", code);
		map.put("error", msg);
		return JsonUtil.objectToString(map);
	}
	
	/**
	 * 返回操作成功
	 * @return json
	 */
	public String retSuccess(){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("result", "true");
		return JsonUtil.objectToString(map);
	}
	
	
	
	
}
