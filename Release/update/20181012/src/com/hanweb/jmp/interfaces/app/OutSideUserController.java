package com.hanweb.jmp.interfaces.app;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.ControllerUtil;
import com.hanweb.common.util.mvc.MultipartFileInfo;
import com.hanweb.complat.entity.OutsideUser;
import com.hanweb.complat.exception.OperationException;
import com.hanweb.complat.service.OutsideUserService;
import com.hanweb.jis.expansion.util.JsonJisUtil;

import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.InterfaceLogConfig;
import com.hanweb.jmp.constant.StaticValues;
import com.hanweb.jmp.global.service.MessageService;
import com.hanweb.jmp.util.ImageUtil;
import com.hanweb.sso.ldap.util.MD5;

/**
 * 用户相关接口
 * @author lgq
 *
 */
@Controller
@RequestMapping("interfaces")
public class OutSideUserController {

	/**
	 * outsideUserService
	 */
	@Autowired
	private OutsideUserService outsideUserService;

	/**
	 * 用户注册接口
	 * @param request request
	 * @param siteid siteid
	 * @param clienttype clienttype
	 * @param version version
	 * @param uuid uuid
	 * @param loginid loginid
	 * @param password password
	 * @param type type
	 * @param phonecode phonecode
	 * @param name name
	 * @param phone phone
	 * @param email email
	 * @param headurl headurl
	 * @param extraattrs extraattrs
	 * @return    设定参数 .
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("regist")
	@ResponseBody
	public String regist(HttpServletRequest request, Integer siteid, Integer clienttype, String version, String uuid,
			String loginid, String password, Integer type, String phonecode,
			String name, String phone, String email, String headurl, String extraattrs){ 
		if(StringUtil.isNotEmpty(password)){
			password = MD5.encodeByJavaSecurity(password);
		}
		OutsideUser outsideUser = new OutsideUser();
		outsideUser.setSiteId(siteid);
		outsideUser.setClientType(clienttype);
		outsideUser.setUuid(uuid);
		outsideUser.setLoginName(loginid);
		outsideUser.setPassword(password);
		outsideUser.setType(type);
		outsideUser.setName(name);
		outsideUser.setMobile(phone);
		outsideUser.setEmail(email);
		outsideUser.setHeadUrl(headurl);
		String message="";
		try {
			if(StringUtil.isNotEmpty(extraattrs)){
				Map<String, String> extraMap = JsonJisUtil.StringToObject(extraattrs, new HashMap<String, String>().getClass());
				if(extraMap != null){
					outsideUser.setAge(NumberUtil.getInt(extraMap.get("age")));
					outsideUser.setSex(NumberUtil.getInt(extraMap.get("sex")));
					outsideUser.setIdCard(StringUtil.getString(extraMap.get("idcard")));
					outsideUser.setFax(StringUtil.getString(extraMap.get("fax")));
					outsideUser.setQq(StringUtil.getString(extraMap.get("qq")));
					outsideUser.setAddress(StringUtil.getString(extraMap.get("address")));
					outsideUser.setPost(StringUtil.getString(extraMap.get("post")));
					outsideUser.setHeadship(StringUtil.getString(extraMap.get("headship")));
					outsideUser.setBirthdate1(StringUtil.getString(extraMap.get("birthdate")));
				}
			}
			if(NumberUtil.getInt(type) == 1){
				String codeTime = StaticValues.codeMap.get(siteid + "_" + loginid);
				if(StringUtil.isEmpty(codeTime)){
					throw new OperationException("验证码不正确！");
				}else{
					String[] str = StringUtil.split(codeTime, "_");
					if(str.length != 2){
						throw new OperationException("验证码不正确！");
					}else{
						String serviceCode = str[0];
						String time = str[1];
						if(!StringUtil.equals(serviceCode, phonecode) 
								|| DateUtil.timeSub(DateUtil.getCurrDateTime(), time) > 600){
							throw new OperationException("验证码不正确！");
						}
					}
				}
			}
			boolean bl = outsideUserService.addForInterface(outsideUser); 
			return InterfaceLogConfig.interfaceResult(bl, InterfaceLogConfig.MOD_REGIST, "");   
		} catch (OperationException e) {
			message = StringUtil.getString(e.getMessage());
			if(StringUtil.isEmpty(message)){
				message = InterfaceLogConfig.ERROR_08;
			}
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_REGIST, message);  
		} catch (Exception e) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_REGIST, InterfaceLogConfig.ERROR_08); 
		}
	}

	/**
	 * 普通用户上传头像接口
	 * @param siteId 站点id
	 * @param loginid 登录名
	 * @param headpic 头像
	 * @return result
	 */
	@RequestMapping("uploadheadpic")
	@ResponseBody
	public String uploadUserLogo(Integer siteid, String loginid, MultipartFile headpic){
		Map<String, String> result = new HashMap<String, String>();
		if(siteid <= 0 || "".equals(loginid) || loginid == null){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_REGIST, InterfaceLogConfig.ERROR_03);
		}
		OutsideUser outSideUser = outsideUserService.findByLoginName(loginid, siteid);
		String headUrl = null;
		if(outSideUser!=null){
			headUrl = MultipartFile(headpic, siteid);
		}
		outSideUser.setHeadUrl(headUrl);
		boolean isSuccess = false;
		try {
			isSuccess = outsideUserService.modify(outSideUser);
			result.put("result", "" + isSuccess);
			result.put("message", "上传成功！");
			result.put("headurl", BaseInfo.getDomain() + headUrl);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "false");
			result.put("message", "上传失败！"+e.getMessage());
		}
		return JsonUtil.objectToString(result);
	}

	/**
	 * 处理头像
	 * @param headpic
	 * @param siteId
	 * @return path
	 */
	private String MultipartFile(MultipartFile headpic, Integer siteid) {
		MultipartFileInfo info = null;
		MultipartFile multipartFile = headpic;
		String path = "";
		if(multipartFile == null){
			return null;
		}
		info = MultipartFileInfo.getInstance(multipartFile);
		// 附件上传格式检查
		if (!checkFileType(info.getFileType())) {
			throw new OperationException("暂不支持此文件类型！");
		}
		// 获取附件存放路径
		String filepath = getStaticFilePath(siteid);
		if (StringUtil.isEmpty(filepath)) {
			throw new OperationException("文件路径错误！");
		}
		// 文件名字,采用当前时间+随机数
		String filename = DateUtil.getCurrDate("yyyyMMddHHmmss") + new Random().nextInt(100);
		String filefullpath = filepath + filename + "." + info.getFileType();

		ControllerUtil.writeMultipartFileToFile(new File(filefullpath), multipartFile);
		ImageUtil.zoomBrokeImg(filefullpath, filename);

		// 存入数据库的相对路径
		path = filefullpath.replace((BaseInfo.getRealPath()).replaceAll("\\\\", "/"), "");

		return path;
	}

	/**
	 * 检查图片类型
	 * @param filetype
	 * @return true/false
	 */
	public boolean checkFileType(String filetype) {
		if (StringUtil.isEmpty(filetype)) {
			return false;
		}
		filetype = filetype.toLowerCase();
		String[] fileTypes = Configs.getConfigs().getPicFileType().split(",");
		for (String file : fileTypes) {
			if (file.equals(filetype)) {
				return true;
			}
		}
		fileTypes = Configs.getConfigs().getAudioFileType().split(",");
		for (String file : fileTypes) {
			if (file.equals(filetype)) {
				return true;
			}
		}
		fileTypes = Configs.getConfigs().getVideoFileType().split(",");
		for (String file : fileTypes) {
			if (file.equals(filetype)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 上传到本地服务器
	 * @param siteId
	 * @return filepath
	 */
	private String getStaticFilePath(Integer siteId) {
		String filepath = "/web/site" + siteId + "/headpic";
		filepath = BaseInfo.getRealPath() + filepath;
		File file = new File(filepath);
		boolean flag = true;
		if (!file.exists() || !file.isDirectory()) {
			flag = file.mkdirs();
		}
		if (!flag) {
			return "";
		}
		return (filepath + File.separator).replaceAll("\\\\", "/");
	}

	/**
	 * sendPhoneCode
	 * @param request request
	 * @param siteid siteid
	 * @param phone phone
	 * @return    设定参数 .
	 */
	@RequestMapping("sendcode")
	@ResponseBody
	public String sendPhoneCode(HttpServletRequest request, Integer siteid, String loginid, String phone){
		//0 邮件  1手机
		int type=1;
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtil.isEmpty(loginid) && StringUtil.isEmpty(phone)){
			result.put("result", "false"); 
			return JsonUtil.objectToString(result);
		}
		if(StringUtil.isEmpty(phone)){
			type=0;
		}else{
			loginid=phone;
		}
		boolean bl = true;
		OutsideUser user = outsideUserService.findByLoginName(loginid, siteid);
		if(user==null && NumberUtil.getInt(type)==0){
			result.put("result", "false"); 
			return JsonUtil.objectToString(result);
		}
		try {
			String code = StringUtil.getUUIDString().substring(0, 6);
			if(NumberUtil.getInt(type)==0){
				//发邮件
				bl = MessageService.sendQRCode(user.getEmail(), code);
			}else{
				//发短信
				bl = outsideUserService.sendCode(loginid, code);
			} 
			if(bl){
				if(NumberUtil.getInt(type)==0){
					StaticValues.codeMap.put(siteid+"_"+loginid, code + "_" + DateUtil.getCurrDateTime());
					result.put("result", "true");
					result.put("code", code);
					result.put("email", user.getEmail());
				}else{
					StaticValues.codeMap.put(siteid+"_"+loginid, code + "_" + DateUtil.getCurrDateTime());
					result.put("result", "true");
					result.put("code", code);
					result.put("phone", loginid);
				} 
			}else{
				result.put("result", "false"); 
				result.put("message", "验证码发送失败");
			}
		} catch (Exception e) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_REGIST, InterfaceLogConfig.ERROR_08); 
		}
		return JsonUtil.objectToString(result);
	}

	/**
	 * updatePass:密码修改.
	 * @param siteid 网站id
	 * @param loginid 用户登录名
	 * @param password 密码
	 * @param type 类型
	 * @param phonecode 验证码
	 * @return    设定参数 .
	 */
	@RequestMapping("updatepass")
	@ResponseBody
	public String updatePass(Integer siteid, String loginid, String password, Integer type, String phonecode){
		Map<String, Object> result = new HashMap<String, Object>();
		boolean bl = true;
		try { 
			String codeTime = StaticValues.codeMap.get(siteid + "_" + loginid);
			if(StringUtil.isEmpty(codeTime)){
				bl = false; 
			}else{
				String[] str = StringUtil.split(codeTime, "_");
				if(str.length != 2){
					bl = false;
				}else{
					String serviceCode = str[0];
					String time = str[1];
					if(!StringUtil.equals(serviceCode, phonecode) || DateUtil.timeSub(DateUtil.getCurrDateTime(), time) > 600){
						bl=false;
					}
				} 
			}
			if(!bl){
				result.put("result", "false"); 
				result.put("message", "验证码不正确"); 
				result.put("loginid", loginid);  
				return JsonUtil.objectToString(result);
			}
			OutsideUser user = outsideUserService.findByLoginName(loginid, siteid);
			if(user==null){
				result.put("result", "false"); 
				result.put("message", "用户不存在"); 
				result.put("loginid", loginid);  
				return JsonUtil.objectToString(result);
			}
			bl = false;
			if(StringUtil.isNotEmpty(password)){
				password = MD5.encodeByJavaSecurity(password);
				user.setPassword(password);
				bl=outsideUserService.modifyForInterface(user);
			}
			if(bl){
				result.put("result", "true"); 
				result.put("message", "修改成功"); 
				result.put("loginid", loginid);  
			}else{
				result.put("result", "true"); 
				result.put("message", "修改失败"); 
				result.put("loginid", loginid);  
			}
		} catch (Exception e) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_REGIST, InterfaceLogConfig.ERROR_08); 
		}
		return JsonUtil.objectToString(result);
	}


	/**
	 * modify
	 * @param siteid siteid
	 * @param clienttype clienttype
	 * @param version version
	 * @param uuid uuid
	 * @param loginid loginid
	 * @param password password
	 * @param type type
	 * @param phonecode phonecode
	 * @param name name
	 * @param phone phone
	 * @param email email
	 * @param headurl headurl
	 * @param extraattrs extraattrs
	 * @return    设定参数 .
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("modify")
	@ResponseBody
	public String modify(Integer siteid, Integer clienttype, String version, String uuid, String loginid, 
			String password, Integer type, String phonecode, String name, String phone, 
			String email, String headurl, String extraattrs){
		@SuppressWarnings("unused")
		Map<String, Object> result;
		OutsideUser outsideUser = new OutsideUser();
		outsideUser.setSiteId(siteid);
		outsideUser.setClientType(clienttype);
		outsideUser.setUuid(uuid);
		outsideUser.setLoginName(loginid);
		outsideUser.setPassword(password);
		outsideUser.setType(type);
		outsideUser.setName(name);
		outsideUser.setMobile(phone);
		outsideUser.setEmail(email);
		outsideUser.setHeadUrl(headurl);
		String message = "";
		try {
			Map<String, String> extraMap = JsonJisUtil.StringToObject(extraattrs, new HashMap<String, String>().getClass());
			if(extraMap != null){
				outsideUser.setAge(NumberUtil.getInt(extraMap.get("age")));
				outsideUser.setSex(NumberUtil.getInt(extraMap.get("sex")));
				outsideUser.setIdCard(StringUtil.getString(extraMap.get("idcard")));
				outsideUser.setFax(StringUtil.getString(extraMap.get("fax")));
				outsideUser.setQq(StringUtil.getString(extraMap.get("qq")));
				outsideUser.setAddress(StringUtil.getString(extraMap.get("address")));
				outsideUser.setPost(StringUtil.getString(extraMap.get("post")));
				outsideUser.setHeadship(StringUtil.getString(extraMap.get("headship")));
				outsideUser.setBirthdate1(StringUtil.getString(extraMap.get("birthdate")));
			}
			boolean bl = outsideUserService.modifyForInterface(outsideUser); 
			return InterfaceLogConfig.interfaceResult(bl, InterfaceLogConfig.MOD_REGIST, "");  
		} catch (OperationException e) {
			message = StringUtil.getString(e.getMessage());
			if(StringUtil.isEmpty(message)){
				message = InterfaceLogConfig.ERROR_08;
			}
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_REGIST, message); 
		} catch (Exception e) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_REGIST, InterfaceLogConfig.ERROR_08); 
		}
	}

	/**
	 * login
	 * @param siteid siteid
	 * @param clienttype clienttype
	 * @param version version
	 * @param uuid uuid
	 * @param loginid loginid
	 * @param password password
	 * @param type type
	 * @return    设定参数 .
	 */
	@RequestMapping("login")
	@ResponseBody
	public String login(Integer siteid, Integer clienttype, String version, String uuid, String loginid, 
			String password, Integer type){
		Map<String, Object> result = new HashMap<String, Object>();
		OutsideUser user = outsideUserService.findByLoginName(loginid, siteid);
		if(user != null){
			if(StringUtil.equals(password, user.getPassword())){
				result.put("result", "true");
				result.put("message", "");
				result.put("type", StringUtil.getString(user.getType()));
				result.put("loginid", StringUtil.getString(user.getLoginName()));
				result.put("name", StringUtil.getString(user.getName()));
				if(StringUtil.isNotEmpty(user.getHeadUrl())){
					result.put("headurl", BaseInfo.getDomain() + StringUtil.getString(user.getHeadUrl()));
				} else {
					result.put("headurl", StringUtil.getString(user.getHeadUrl()));
				}
				result.put("phone", StringUtil.getString(user.getMobile()));
				result.put("email", StringUtil.getString(user.getEmail()));
				Map<String, String> extraMap = new HashMap<String, String>();
				extraMap.put("age", StringUtil.getString(user.getAge()));
				extraMap.put("sex", StringUtil.getString(user.getSex()));
				extraMap.put("idcard", StringUtil.getString(user.getIdCard()));
				extraMap.put("fax", StringUtil.getString(user.getFax()));
				extraMap.put("qq", StringUtil.getString(user.getQq()));
				extraMap.put("address", StringUtil.getString(user.getAddress()));
				extraMap.put("post", StringUtil.getString(user.getPost()));
				extraMap.put("headship", StringUtil.getString(user.getHeadship()));
				extraMap.put("birthdate", StringUtil.getString(user.getBirthdate1()));
				result.put("extraattrs", extraMap);
				if(uuid!=null&&!"".equals(uuid)){
					if(!uuid.equals(user.getUuid())){
						user.setUuid(uuid);
						try {
							outsideUserService.modifyForInterface(user);
						} catch (OperationException e) {
							e.printStackTrace();
						}
					}
				}
			}else{ 
				return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_REGIST, 
						InterfaceLogConfig.ERROR_14);  
			}
		}else{
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_REGIST, 
					InterfaceLogConfig.ERROR_15);  
		}
		return JsonUtil.objectToString(result);
	}

}