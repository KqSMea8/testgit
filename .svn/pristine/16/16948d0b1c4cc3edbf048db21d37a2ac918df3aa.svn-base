package com.hanweb.jmp.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil; 

import com.hanweb.jmp.cms.service.infos.InfoCountService;
import com.hanweb.jmp.constant.InterfaceLogConfig;
import com.hanweb.jmp.sys.entity.log.InterfaceLog;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.log.InterfaceLogService;
import com.hanweb.jmp.sys.service.sites.SiteService;

public class InterfaceFilter implements Filter {
	
	/**
	 * interfaceLogService
	 */
	private InterfaceLogService interfaceLogService = SpringUtil.getBean(
			InterfaceLogService.class);
	
	/**
	 * infoCountService
	 */
	private InfoCountService infoCountService = SpringUtil.getBean(InfoCountService.class);
	
	/**
	 * siteService
	 */
	private SiteService siteService = SpringUtil.getBean(SiteService.class);
	@Override
	public void destroy() {
		// TODO Auto-g	enerated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
		String servletPath = httpRequest.getServletPath();
		servletPath = servletPath.replaceAll("\\W+", "/");
		boolean isSuccess = true;
		if(BaseInfo.isPrepared()){
			try {
				ArrayList<String> al = interfaceLogService.getArrByInterface(servletPath);
				if(al != null && al.size() > 0){
					InterfaceLog interfaceLog = new InterfaceLog(); 
					interfaceLog.setOprTime(new Date());
					interfaceLog.setIno(al.get(0));
					interfaceLog.setName(al.get(1));
					interfaceLog.setHour(NumberUtil.getInt(DateUtil.getCurrDate("HH")));
					gEntity(httpRequest, interfaceLog, al);
					Site siteEn=siteService.findByIid(interfaceLog.getSiteId());
					if(siteEn != null){
						//接口日志记录
						int isRecord = NumberUtil.getInt(siteEn.getIsRecord());
						if(isRecord == 1){
							interfaceLogService.add(interfaceLog);
						}	
					}  		
				}
			} catch (Exception e) {
				Log logger = LogFactory.getLog(getClass());
				logger.error("接口错误：" + servletPath);
			} finally {
				if(isSuccess){
					filterChain.doFilter(request, response);
				}else{
					httpResponse.setCharacterEncoding("GBK");
					PrintWriter out = httpResponse.getWriter(); 
					out.println("<h1>" + InterfaceLogConfig.ERROR_03 + "</h1>");
					return;
				} 
			}
		} else {
			filterChain.doFilter(request, response);
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	/**
	 * gEntity
	 * @param httpRequest httpRequest
	 * @param interfaceLog interfaceLog
	 * @param al    设定参数 .
	 */
	@SuppressWarnings("unchecked")
	private void gEntity(HttpServletRequest httpRequest, InterfaceLog interfaceLog
			, ArrayList<String> al){
		Map parMap = httpRequest.getParameterMap();
		int siteId = 0;
		int titleId = 0;
		boolean mVisitCount=false;
		if (parMap != null) {
			Iterator<Entry<String, String[]>> it = parMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String[]> m = (Map.Entry<String, String[]>) it.next();
				String key = m.getKey();
				if (key.equalsIgnoreCase("uuid")) {
					interfaceLog.setUuid(m.getValue()[0]);
				}
				if (key.equalsIgnoreCase("uuidmd5")) {
					interfaceLog.setUuidmd5(m.getValue()[0]);
				}
				if (key.equalsIgnoreCase("clienttype")) {
					interfaceLog.setClientType(NumberUtil.getInt(m.getValue()[0]));
				}
				if (key.equalsIgnoreCase("version")) {
					interfaceLog.setVersion(m.getValue()[0]);
				}
				if (key.equalsIgnoreCase("siteid")) {
					interfaceLog.setSiteId(NumberUtil.getInt(m.getValue()[0]));
					siteId = NumberUtil.getInt(m.getValue()[0]);
				}
				if(InterfaceLogConfig.MOD_CHANCATES.equals(al.get(0))){ //取频道下子栏目接口
					if (key.equalsIgnoreCase("channelid")) {
						interfaceLog.setChanId(NumberUtil.getInt(m.getValue()[0]));
					}
				}else if(InterfaceLogConfig.MOD_CATES.equals(al.get(0))){ //通过父栏目取子栏目接口
					if (key.equalsIgnoreCase("parid")) {
						interfaceLog.setColId(NumberUtil.getInt(m.getValue()[0]));
					}
				}else if(InterfaceLogConfig.MOD_CARDINFOLIST.equals(al.get(0))){ //信息列表接口
					if (key.equalsIgnoreCase("resourceid")) {
						interfaceLog.setColId(NumberUtil.getInt(m.getValue()[0]));
					}
				}else if(InterfaceLogConfig.MOD_INFOCONTENT.equals(al.get(0))
						|| InterfaceLogConfig.MOD_PIC.equals(al.get(0))){ //信息正文和酷图接口
					if (key.equalsIgnoreCase("titleid")) {
						interfaceLog.setInfoId(NumberUtil.getInt(m.getValue()[0]));
						titleId = NumberUtil.getInt(m.getValue()[0]);
						mVisitCount=true;
					} 
				} 
			} 
			if(mVisitCount && titleId>0 && siteId>0){
				infoCountService.mVisitCount(titleId, 1, siteId);	
			}
		}
	}

}