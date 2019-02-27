package com.hanweb.interceptor; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.interceptor.BaseInterceptor;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.log.LogWriter; 

public class PermissionInterceptor extends BaseInterceptor {

	@Override
	public void after(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2,
			ModelAndView arg3) {

	}
 
	@Override
	public boolean before(HttpServletRequest request, 
			HttpServletResponse response, Object handler) {
		String queryString="";
		if(StringUtil.isNotEmpty(request.getQueryString())){
			queryString="?" + request.getQueryString();
		}
		String url = request.getServletPath()
				+ queryString;
		LogWriter.debug("Operation Url:" + url);
		boolean hasRight = hasRight(request, url);
		if (hasRight) {
			return true;
		} else {
			try {
				response.sendRedirect(BaseInfo.getContextPath() + "/error/601.do");
			} catch (Exception e) {
				LogWriter.error("sendRedirect error", e);
			}
			return false;
		}
	}

	@Override
	public void complete(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2,
			Exception arg3) {
	}
	
	/**
	 * 判断url的权限
	 * @param request request
	 * @param url url
	 * @return boolean
	 */
	private boolean hasRight(HttpServletRequest request, String url){
		try{ 
			return false;
		}catch(Exception e){
			return false;
		}
	}
}
