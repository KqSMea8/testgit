package com.hanweb.jmp.apps.controller.tax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.StringUtil;

@Controller
@RequestMapping("interfaces")
public class IncomeTaxController {

	@RequestMapping("incometaxquery")
	@ResponseBody
	public String incomeTaxQuery(HttpServletResponse response, String name, String idNumber, String year) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET");
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(StringUtil.isEmpty(name) || StringUtil.isEmpty(idNumber) || StringUtil.isEmpty(year)) {
			map.put("result", "false");
			map.put("msg", "参数有误，请参考相关文档");
			list.add(map);
		} else {
			for(int i=0; i<3; i++) {
				map = new HashMap<String, Object>();
				map.put("result", "true");
				if(i == 0) {
					map.put("date", "2018/01/01-2018/01/31");
				} else if(i == 1) {
					map.put("date", "2018/02/01-2018/02/28");
				} else if(i == 2) {
					map.put("date", "2018/03/01-2018/03/31");
				}
				map.put("income", 5000 + 1000*i + ""); //收入额
				map.put("TaxableIncome", 1333.0 + 100*i + "");
				map.put("TaxPayment", 32.1 + 100*i + "");
				map.put("TaxRate", "0.03");
				map.put("TaxItem", "正常工资薪金");
				map.put("company", "南京大汉网络有限公司");
				list.add(map);
			}
		}
		return JsonUtil.objectToString(list);
	}
	
}
