package com.hanweb.interfaces.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.jmp.constant.Configs;

@Controller
@RequestMapping("interfaces")
public class ClientVerifyCodeController {
	
	/**
	 * findVerifyCodeImg
	 * @param uuid uuid
	 * @param width width
	 * @param height height
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "findVerifyCodeImg")
	@ResponseBody
	public String findVerifyCodeImg(String uuid, String width, String height) {
		String sysNetPath = Configs.getConfigs().getJmpUrl();
		String verifyCodeImg = "";
		try {
			if (StringUtils.isBlank(width) || StringUtils.isBlank(height)) {
				verifyCodeImg = sysNetPath + "/interfaces/verifyCode.do?code=1&var=" + uuid;
			} else {
				verifyCodeImg = sysNetPath + "/interfaces/verifyCode.do?code=1&var=" + uuid
						      + "&width=" + width + "&height=" + height;
			}
			return verifyCodeImg;
		} catch (Exception e) {
			e.printStackTrace();
			return verifyCodeImg;
		}
	}

	/**
	 * 验证码校验
	 * @param request request
	 * @param verifyCode verifyCode
	 * @param uuid uuid
	 * @return boolean
	 */
	@RequestMapping(value = "checkVerifyCode")
	@ResponseBody
	public boolean checkVerifyCode(HttpServletRequest request, String verifyCode, String uuid) {
		String code = (String) request.getSession().getAttribute(uuid);
		if (verifyCode.equals(code)) {
			return true;
		}
		return false;
	}
	
}