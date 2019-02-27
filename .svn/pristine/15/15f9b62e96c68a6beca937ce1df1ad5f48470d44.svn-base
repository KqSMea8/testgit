package com.hanweb.jmp.apps.controller.fund;

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
public class CommonAccumulationFundController {

	/**
	 * 公积金查询接口
	 * @param response
	 * @param req
	 * @param location
	 * @param idnumber
	 * @param password
	 * @return
	 */
	@RequestMapping("commonaccumulationfund")
	@ResponseBody
	public String commonAccumulationFundQuery(HttpServletResponse response, String location,String idnumber, String password){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(StringUtil.isEmpty(location) || StringUtil.isEmpty(idnumber) || StringUtil.isEmpty(password)) {
			map.put("result", "false");
			map.put("msg", "参数有误，请参考相关文档");
			list.add(map);
		} else {
			map.put("balance", "30000");//余额
			map.put("monthes", "20");//月数
			map.put("status", "正常");
			map.put("account", "18856026561");//公积金账号
			map.put("name", "王超");
			map.put("idNumber", "666");//身份证号
			map.put("bankCard", "3217396423668466399");//关联银行卡号
			map.put("companyPay", "1000");//单位缴存
			map.put("personalPay", "1000");//个人缴存
			map.put("lastDate", "2018/7");//最近存储日期
			list.add(map);
		}
		return JsonUtil.objectToString(list);
		
	}
	
	/**
	 * 公积金明细查询接口
	 * @param response
	 * @param req
	 * @param idNumber
	 * @return
	 */
	@RequestMapping("commonaccumulationfunddetail")
	@ResponseBody
	public String commonAccumulationFundDetailQuery(HttpServletResponse response, String idnumber,String name){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(StringUtil.isEmpty(idnumber) || StringUtil.isEmpty(name)) {
			map.put("result", "false");
			map.put("msg", "参数有误，请参考相关文档");
			list.add(map);
		} else {
			map.put("account", "14785544696");//账户
			map.put("balance", "8888");//余额
			map.put("operateDate", "2018-8");
			map.put("operateType", "单位缴纳");
			map.put("operatePlace", "市医院");
			map.put("money", "800");
			list.add(map);
		}
		return JsonUtil.objectToString(list);
		
	}
}
