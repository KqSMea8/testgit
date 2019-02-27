package com.hanweb.jmp.apps.controller.security;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.StringUtil;

@Controller
@RequestMapping("interfaces")
public class SocialSecurityController {
     
	@RequestMapping("socialsecurity")
	@ResponseBody
	public String socialSecurityQuery(HttpServletResponse response, String location,String idnumber, String password){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(StringUtil.isEmpty(location) || StringUtil.isEmpty(idnumber) || StringUtil.isEmpty(password)) {
			map.put("result", "false");
			map.put("msg", "参数有误，请参考相关文档");
			list.add(map);
		} else {
			map.put("name", "张三");
			map.put("company", "南京大汉网络有限公司");
			map.put("account", "18856026561");
			map.put("securityLocation", "南京市本级");
			map.put("time", "2018/8");
			map.put("empMedicalInsurance", "1000");
			map.put("companyOldInsurance", "800");
			map.put("jobInjuredInsurance", "600");
			map.put("maternityInsurance", "700");
			map.put("losejobInsurance", "1000");
			list.add(map);
		}
		return JsonUtil.objectToString(list);
	}

	/**
	 * 职工医疗保险缴存明细接口
	 * @param response
	 * @param req
	 * @param idNumber
	 * @return
	 */
	@RequestMapping("empmedicalinsurancestore")
	@ResponseBody
	public String companyOldinsuranceStoreQuery(HttpServletResponse response, String idnumber, String name){
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(StringUtil.isEmpty(idnumber) || StringUtil.isEmpty(name)) {
			map.put("result", "false");
			map.put("msg", "参数有误，请参考相关文档");
			list.add(map);
		} else {
			map.put("balance", "8888");
			map.put("monthes", "20");
			map.put("when", "2018/1/11-2018/1/31");
			map.put("personerPay", "5000");
			map.put("companyPay", "5000");
			map.put("sum", "10000");
			map.put("basePay", "3000");
			map.put("participatingUnits", "南京大汉网络有限公司");
			list.add(map);
		}
		return JsonUtil.objectToString(list);
		
	}
	
	/**
	 * 职工医疗保险提取明细接口
	 * @param response
	 * @param req
	 * @param idNumber
	 * @return
	 */
	@RequestMapping("empmedicalinsuranceget")
	@ResponseBody
	public String empMedicalInsuranceGetQuery(HttpServletResponse response,HttpServletRequest req,String idnumber,String name){
		try {
			response.setHeader("Access-Control-Allow-Methods", "POST,GET");
			response.setHeader("Access-Control-Allow-Origin", "*");
			req.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(StringUtil.isEmpty(idnumber) || StringUtil.isEmpty(name)) {
			map.put("result", "false");
			map.put("msg", "参数有误，请参考相关文档");
			list.add(map);
		} else {
			map.put("balance", "8888");
			map.put("monthes", "20");
			map.put("operateDate", "2018-8");
			map.put("operateType", "单位缴纳");
			map.put("operatePlace", "市医院");
			map.put("money", "800");
			list.add(map);
		}
		return JsonUtil.objectToString(list);
		
	}
	
	/**
	 * 企业养老保险缴纳明细接口
	 * @param response
	 * @param req
	 * @param idNumber
	 * @return
	 */
	@RequestMapping("companyoldinsurancestore")
	@ResponseBody
	public String companyOldInsuranceStoreQuery(HttpServletResponse response,HttpServletRequest req,String idnumber,String name){
		try {
			response.setHeader("Access-Control-Allow-Methods", "POST,GET");
			response.setHeader("Access-Control-Allow-Origin", "*");
			req.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(StringUtil.isEmpty(idnumber) || StringUtil.isEmpty(name)) {
			map.put("result", "false");
			map.put("msg", "参数有误，请参考相关文档");
			list.add(map);
		} else {
			map.put("when", "2018/1/11-2018/1/31");
			map.put("personerPay", "5000");
			map.put("companyPay", "5000");
			map.put("sum", "10000");
			map.put("basePay", "3000");
			map.put("participatingUnits", "南京大汉网络有限公司");
			list.add(map);
		}
		return JsonUtil.objectToString(list);
		
	}
	
	/**
	 * 工伤保险缴纳明细接口
	 * @param response
	 * @param req
	 * @param idNumber
	 * @return
	 */
	@RequestMapping("jobinjuredinsurancestore")
	@ResponseBody
	public String jobInjuredInsuranceStoreQuery(HttpServletResponse response,HttpServletRequest req,String idnumber,String name){
		try {
			response.setHeader("Access-Control-Allow-Methods", "POST,GET");
			response.setHeader("Access-Control-Allow-Origin", "*");
			req.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(StringUtil.isEmpty(idnumber) || StringUtil.isEmpty(name)) {
			map.put("result", "false");
			map.put("msg", "参数有误，请参考相关文档");
			list.add(map);
		} else {
			map.put("when", "2018/1/11-2018/1/31");
			map.put("personerPay", "5000");
			map.put("companyPay", "5000");
			map.put("sum", "10000");
			map.put("basePay", "3000");
			map.put("participatingUnits", "南京大汉网络有限公司");
			list.add(map);
		}
		return JsonUtil.objectToString(list);
		
	}
	
	/**
	 * 生育保险缴纳明细接口
	 * @param response
	 * @param req
	 * @param idNumber
	 * @return
	 */
	@RequestMapping("maternityinsurancestore")
	@ResponseBody
	public String maternityInsuranceStoreQuery(HttpServletResponse response,HttpServletRequest req,String idnumber,String name){
		try {
			response.setHeader("Access-Control-Allow-Methods", "POST,GET");
			response.setHeader("Access-Control-Allow-Origin", "*");
			req.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(StringUtil.isEmpty(idnumber) || StringUtil.isEmpty(name)) {
			map.put("result", "false");
			map.put("msg", "参数有误，请参考相关文档");
			list.add(map);
		} else {
			map.put("when", "2018/1/11-2018/1/31");
			map.put("personerPay", "5000");
			map.put("companyPay", "5000");
			map.put("sum", "10000");
			map.put("basePay", "3000");
			map.put("participatingUnits", "南京大汉网络有限公司");
			list.add(map);
		}
		return JsonUtil.objectToString(list);
		
	}
	
	/**
	 * 失业保险缴纳明细接口
	 * @param response
	 * @param req
	 * @param idNumber
	 * @return
	 */
	@RequestMapping("losejobinsurancestore")
	@ResponseBody
	public String losejobInsuranceStoreQuery(HttpServletResponse response,HttpServletRequest req,String idnumber,String name){
		try {
			response.setHeader("Access-Control-Allow-Methods", "POST,GET");
			response.setHeader("Access-Control-Allow-Origin", "*");
			req.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if(StringUtil.isEmpty(idnumber) || StringUtil.isEmpty(name)) {
			map.put("result", "false");
			map.put("msg", "参数有误，请参考相关文档");
			list.add(map);
		} else {
			map.put("when", "2018/1/11-2018/1/31");
			map.put("personerPay", "5000");
			map.put("companyPay", "5000");
			map.put("sum", "10000");
			map.put("basePay", "3000");
			map.put("participatingUnits", "南京大汉网络有限公司");
			list.add(map);
		}
		return JsonUtil.objectToString(list);
		
	}
}