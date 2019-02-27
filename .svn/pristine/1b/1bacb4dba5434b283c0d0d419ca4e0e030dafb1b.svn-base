package com.hanweb.jmp.apps.controller.allcount;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hanweb.common.util.JsonUtil;

/**
 * 公积金、社保、纳税总额假接口
 * @author Administrator
 *
 */
@Controller
@RequestMapping("interfaces")
public class TotalController {
	
	/**
	 *  获取总额
	 * @return
	 */
	@RequestMapping(value = "getAllCount")
	@ResponseBody
	public String getAllCount(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("nashui", "1025.8");
		map.put("gongjijin", "5062.5");
		map.put("shebao", "2550.8");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("allCount", map);
		result.put("code", 0);
		result.put("msg", "查询成功");
		return JsonUtil.objectToString(result);		
	}
	
}
