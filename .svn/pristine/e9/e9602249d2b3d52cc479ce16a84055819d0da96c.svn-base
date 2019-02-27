package com.hanweb.jmp.pack.listener;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hanweb.common.util.SpringUtil;
import com.hanweb.complat.constant.Settings;
import com.hanweb.jmp.pack.entity.AppUser;

public class FrontUserSessionInfo {
	
	/**
	 * LOGGER
	 */
	private static final Log LOGGER = LogFactory.getLog(FrontUserSessionInfo.class);
 
	/**
	 * 获得当前用户--从session中
	 * @return    设定参数 .
	 */
	public static synchronized AppUser getCurrentUserInfo() {
		HttpSession session = SpringUtil.getRequest().getSession(false);
		AppUser user = null;
		if (session != null && session.getAttribute("appuser") != null) {
			user = (AppUser) session.getAttribute("appuser");
		}
		return user;
	}
 
	/**
	 * 保存用户信息到session和在线用户
	 * @param user  user  设定参数 .
	 */
	public static synchronized void setCurrentUserInfo(AppUser user) {
		try {
			if (user != null) {
				//SpringUtil.getRequest().getSession().invalidate();
				HttpSession session = SpringUtil.getRequest().getSession(true);
				// 设置用户session超时时间为半个小时
				session.setMaxInactiveInterval(60 * Settings.getSettings().getSessionTime());
				session.setAttribute("appuser", user);
			}
		} catch (Exception e) {
			LOGGER.error("setCurrentUserInfo Error:", e);
		}
	}
	
	/**
	 * 删除用户信息 ---从session中.
	 */
	public static synchronized void removeCurrentUserInfo() { 
		HttpSession session = SpringUtil.getRequest().getSession(true);
		session.removeAttribute("appuser");
	}

}