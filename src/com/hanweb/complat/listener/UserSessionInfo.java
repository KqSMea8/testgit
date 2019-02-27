package com.hanweb.complat.listener;

import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hanweb.common.util.CheckPwdStrong;
import com.hanweb.common.util.Md5Util;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.complat.constant.Settings;
import com.hanweb.complat.constant.StaticValues;
import com.hanweb.support.controller.CurrentUser;

public class UserSessionInfo {

	private static final Log LOGGER = LogFactory.getLog(UserSessionInfo.class);

	/**
	 * 获得当前用户--从session中.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 用户
	 */
	public static synchronized CurrentUser getCurrentUser() {
		HttpSession session = SpringUtil.getRequest().getSession(false);
		CurrentUser currentUser = null;
		if (session != null && session.getAttribute(StaticValues.USERINFO) != null) {
			currentUser = (CurrentUser) session.getAttribute(StaticValues.USERINFO);
		}
		return currentUser;
	}

	/**
	 * 保存用户信息到session和在线用户.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param user
	 *            用户
	 */
	public static synchronized void setCurrentUser(CurrentUser user) {
		try {
			if (user != null) {
				//SpringUtil.getRequest().getSession().invalidate();
				HttpSession session = SpringUtil.getRequest().getSession(true);
				// 设置用户session超时时间为半个小时
				session.setMaxInactiveInterval(60 * Settings.getSettings().getSessionTime());
				session.setAttribute(StaticValues.USERINFO, user);
			}
		} catch (Exception e) {
			LOGGER.error("setCurrentUser Error:", e);
		}
	}

	/**
	 * 删除用户信息 ---从session和在线用户中.
	 * 
	 * @param request
	 *            HttpServletRequest.
	 */
	public static synchronized void removeCurrentUser() {
		SpringUtil.getRequest().getSession().invalidate();
	}
	
	/**
	 * 获得当前用户--从session中.
	 * 
	 * @return 用户
	 */
	public static synchronized CurrentUser getAdminUserInfo() {
		HttpSession session = SpringUtil.getRequest().getSession(false);
		CurrentUser user = null;
		if (session != null && session.getAttribute(StaticValues.ADMIN_USER) != null) {
			user = (CurrentUser) session.getAttribute(StaticValues.ADMIN_USER);
		}
		return user;
	}
	
	/**
	 * 保存用户信息到session和在线用户.
	 * 
	 * @param user
	 *            用户
	 */
	public static synchronized void setAdminUserInfo(CurrentUser user) {
		try {
			if (user != null) {
				HttpSession session = SpringUtil.getRequest().getSession(true);
				// 设置用户session超时时间为半个小时
				session.setMaxInactiveInterval(60 * Settings.getSettings().getSessionTime());
				session.setAttribute(StaticValues.ADMIN_USER, user);
				session.setAttribute(StaticValues.PWDSLEVEL, CheckPwdStrong.check(Md5Util
						.md5decode(user.getPwd())));
			}
		} catch (Exception e) {
			LOGGER.error("setCurrentUserInfo Error:", e);
		}
	}
}
