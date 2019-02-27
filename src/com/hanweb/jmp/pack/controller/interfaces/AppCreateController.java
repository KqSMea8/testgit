package com.hanweb.jmp.pack.controller.interfaces; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import com.hanweb.jmp.pack.service.ClientService;

@Controller
@RequestMapping("interfaces")
public class AppCreateController {
	 
	/**
	 * clientService
	 */
	@Autowired
	private ClientService clientService;
	
	/**
	 * 接收客户端参数，判断是否打包成功，然后取包
	 * @param strJSON 客户端传递的json串 
	 * @return
	 */
	@RequestMapping("resultcreateapp")
	@ResponseBody 
	public void obtResultCreateApp(String strJSON){
		try {  
			clientService.modifyClientReturn(strJSON);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}