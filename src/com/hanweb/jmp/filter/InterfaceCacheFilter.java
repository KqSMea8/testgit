package com.hanweb.jmp.filter;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Iterator;
import java.util.Map.Entry;

import net.sf.ehcache.CacheManager;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.interfaces.service.CommonService;
import com.hanweb.jmp.annotation.InterfaceCache;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.sites.SiteService;


public class InterfaceCacheFilter extends CachingFilter {

	/**
	 * siteService
	 */
	private SiteService siteService = SpringUtil.getBean(SiteService.class);

	/**
	 * commonService
	 */
	private CommonService commonService = SpringUtil
			.getBean(CommonService.class);

	/**
	 * 获取缓存名称
	 * 
	 * @return String
	 */
	protected String getCacheName() {
		if ((this.cacheName != null) && (this.cacheName.length() > 0)) {
			return this.cacheName;
		}
		return "InterfacePageCachingFilter";
	}

	/**
	 * 初始化
	 * 
	 * @param filterConfig
	 *            filterConfig
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doInit(FilterConfig filterConfig) {
		super.doInit(filterConfig);
		try {
			String resourcePath = "classpath*:com/hanweb/*/controller/interfaces/*.class";
			String resourcePathReg = ".*(com.hanweb.*?.controller.interfaces.*?).class";
			ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resourcePatternResolver
					.getResources(resourcePath);
			Class clazz = null;
			String className = "";
			for (int i = 0; i < resources.length; i++) {
				className = resources[i].getURL().getPath();
				className = className.replace("\\\\", "/").replace("/", ".")
						.replaceAll(resourcePathReg, "$1");
				clazz = Class.forName(className);
				Method[] methods = clazz.getMethods();
				for (Method method : methods) {
					RequestMapping mapping = method
							.getAnnotation(RequestMapping.class);
					if (mapping != null) {
						InterfaceCache interfaceCache = method
								.getAnnotation(InterfaceCache.class);
						if (interfaceCache != null) {
							this.pageConfig.put("/interfaces/"
									+ mapping.value()[0] + ".do", 60);
						}
					}
				}
			}
		} catch (Exception e) {
			// 读取配置错误
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	protected String calculateKey(HttpServletRequest httpRequest) {
		StringBuffer stringBuffer = null;
		try {
			stringBuffer = new StringBuffer();
			stringBuffer.append(httpRequest.getRequestURI());
			Map parMap = httpRequest.getParameterMap();
			if (parMap != null) {
				Iterator<Entry<String, String[]>> it = parMap.entrySet().iterator();
				int siteid = 0;
				String uuid = "";
				// 时间戳
				String tokenuuid = "";
				// 时间戳
				String uniquecode = "";
				while (it.hasNext()) {
					Map.Entry<String, String[]> m = (Map.Entry<String, String[]>) it                 
							.next();
					String key = m.getKey();
					if (key.equals("siteid")) {
						siteid = NumberUtil.getInt(m.getValue()[0]);
					} else if (key.equals("uuid")) {
						uuid = StringUtil.getString(m.getValue()[0]);
					} else if (key.equals("tokenuuid")) {
						tokenuuid = StringUtil.getString(m.getValue()[0]);
					} else if (key.equals("uniquecode")) {
						uniquecode = StringUtil.getString(m.getValue()[0]);
					}
					if (key.equalsIgnoreCase("uuid")) {
						continue;
					}
					stringBuffer.append("&").append(key).append("=").append(
							m.getValue()[0]);
				}
				if (siteid > 0) {
					Site siteEn = siteService.findByIid(siteid);
					// 接口安全控制
					int isSafeControl = NumberUtil.getInt(siteEn.getIsSafeControl());
					if (isSafeControl == 1) {
						boolean bl = commonService.checkUuid(uniquecode, uuid,tokenuuid, httpRequest.getRequestURI());
						if (!bl) {
							return "参数有误";
						}
					}
				}
			}
		} catch (Exception e) {
			stringBuffer = new StringBuffer();
			stringBuffer.append(httpRequest.getMethod()).append(httpRequest.getRequestURI()).append(httpRequest.getQueryString());
		}
		return stringBuffer.toString();
	}

	@Override
	protected CacheManager getCacheManager() {
		return null;
	}

}
