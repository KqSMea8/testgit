package com.hanweb.jmp.newspush.interfaces;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.util.Md5Util;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.entity.User;
import com.hanweb.complat.service.UserService;
import com.hanweb.jmp.cms.service.infos.InfoService;
import com.hanweb.jmp.constant.ErrorCode;
import com.hanweb.jmp.newspush.news.entity.NewsDetail;
import com.hanweb.jmp.newspush.news.service.NewsDetailService;
import com.hanweb.jmp.newspush.userdevice.service.InterfaceService;
import com.hanweb.jmp.newspush.userdevice.service.JsonService;



/**
 * 消息相关接口
 * 
 * @author Qianzq
 * 
 */
@Controller
@RequestMapping("interfaces/info")
public class InfoInterfacesController {

	@Autowired
	InterfaceService interfaceService;

	@Autowired
	JsonService jsonService;
	
	@Autowired
	InfoService infoService;
	
	@Autowired
	NewsDetailService newsDetailService;
	
	@Autowired
	UserService userService;
	
	
	
	
	
	
	
	 private final Log logger = LogFactory.getLog(getClass());
	
	
	
	/**
	 * 3.5	回执(已阅读，心跳)接口
	 * @param security_code 安全码
	 * @param client_type 客户端类型  1：android 2:iphone
	 * @param uuid 客户端uuid
	 * @param uname 用户名
	 * @param infoid 消息id 
	 * @param state 状态：1、已阅读2、用户在线
	 * @param third 第三方系统用户名(逗号分隔，与infoid一一对应)
	 * @return json
	 */
	@RequestMapping(value = "receipt", method=RequestMethod.POST)
	@ResponseBody
	public String recept(String security_code, Integer client_type, String uuid, String uname,
			String infoid, Integer state, String third){
		try {
			
			User u = interfaceService.checkUname(uname, security_code);
			if(u == null){
				return jsonService.getError("0106", ErrorCode.ERROR_CODE_0106);
			}
			state = NumberUtil.getInt(state, 0);
			if(state <= 0 || state > 2){
				return jsonService.getError("0103", ErrorCode.ERROR_CODE_0103);
			}
			//具体操作
			if(state == 1){
				String [] id = infoid.split(",");
				//IOS传过来的信息ID串，如果私有和应用都有，third会传，，1，，但是如果全是私有，会传空
				//IOS不好拼，这里如果third传空，帮其补齐
				String [] thi; 
				if(!"".equals(StringUtil.getString(third))){
					thi = StringUtil.getString(third).split(",");
				}else{
					thi = new String[id.length];
				}
				if(id.length != thi.length){
					return jsonService.getError("0103", ErrorCode.ERROR_CODE_0103);
				}
				for(int i=0; i<id.length; i++){
					List<NewsDetail> infoDetails = null;
					if("".equals(StringUtil.getString(thi[i]))){
						infoDetails = newsDetailService
							.findByInfoId(NumberUtil.getInt(id[i], 0), u.getIid(), 2);
					}
					/*if(infoDetails == null){
						return jsonService.getError("0103", ErrorCode.ERROR_CODE_0103);
					}*/
					for(NewsDetail infoDetail:infoDetails){
					if(infoDetail.getState() != 2){//未阅才改，已阅就不再改了
						infoDetail.setState(2);
						infoDetail.setReceiptTime(new Date());
						newsDetailService.update(infoDetail);
					}
					}
					//infoService.updateCount(NumberUtil.getInt(id[i]));
				}
			}
			
		} catch (Exception e) {
			return jsonService.getError("0108", ErrorCode.ERROR_CODE_0108);
		}
		return jsonService.retSuccess();
	}
	
	
	
	public static void main(String[] args) {
		//http://221.231.137.195:9090/isms/client/djNwRg==
		String s;
		try {
			s = URLEncoder.encode(Md5Util.md5encode("60"), "UTF-8").replace("*","*").replace("~", "~").replace("+"," ");
			System.out.println(s);
			System.out.println(URLDecoder.decode(s, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
