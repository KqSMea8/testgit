package com.hanweb.jmp.global.service; 

import org.apache.commons.mail.EmailException; 
import com.hanweb.common.util.MailSend; 
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.exception.OperationException; 
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.util.EncodeUtil;
import com.hanweb.jmp.util.HttpClientUtil;

public class MessageService {

	/**
	 * 发信息
	 * @param phone phone
	 * @param content content
	 * @return    设定参数 .
	*/
	public static boolean sendMesssage(String phone, String content){
		String url = StringUtil.getString(Configs.getConfigs().getMessageUrl());
		url = url.replace("{phone}", phone).replace("{content}", new EncodeUtil().encodeStr(content, "UTF-8"));
		HttpClientUtil.getInfo(url, null);
		return true;
	}
	
	/**
	 * 向用户发送激活邮件
	 * @param user 用户
	 * @return
	 * @throws OperationException
	 */
	public  static boolean sendQRCode(String email, String code) throws OperationException {  
		try {
			MailSend mail = new MailSend();
			mail.setHostName(Configs.getConfigs().getSmtp());
			mail.setAuthentication(Configs.getConfigs().getEmailAccount(), Configs.getConfigs().getEmailPwd());
			mail.setSubject("验证码");
			mail.setFrom(Configs.getConfigs().getEmailAccount(), Configs.getConfigs().getEmailName());
			mail.addTo(email);
			String content = Configs.getConfigs().getEmailContent();
			content = content.replaceAll("\\r\\n", "<br/>").replaceAll(" ", "&nbsp;").replaceAll("\\{code\\}", code); 
			mail.setHtmlMsg(content); 
			mail.setCharset("UTF-8");
			mail.send();
		} catch (EmailException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}  
		return true;
	}
	
}