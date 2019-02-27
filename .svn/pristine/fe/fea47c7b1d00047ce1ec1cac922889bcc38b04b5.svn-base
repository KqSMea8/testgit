package com.hanweb.jmp.filter;

import java.io.BufferedOutputStream;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import java.util.zip.DataFormatException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.blocking.BlockingCache;
import net.sf.ehcache.constructs.blocking.LockTimeoutException;
import net.sf.ehcache.constructs.web.AlreadyCommittedException;
import net.sf.ehcache.constructs.web.AlreadyGzippedException;
import net.sf.ehcache.constructs.web.GenericResponseWrapper;
import net.sf.ehcache.constructs.web.Header;
import net.sf.ehcache.constructs.web.PageInfo;
import net.sf.ehcache.constructs.web.ResponseHeadersNotModifiableException;
import net.sf.ehcache.constructs.web.ResponseUtil;
import net.sf.ehcache.constructs.web.SerializableCookie;
import net.sf.ehcache.constructs.web.filter.Filter;
import net.sf.ehcache.constructs.web.filter.FilterNonReentrantException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.StaticValues;
import com.hanweb.jmp.util.CacheUtil;

public abstract class CachingFilter extends Filter {

	/**
	 * LOG
	 */
    private static final Logger LOG = LoggerFactory
            .getLogger(CachingFilter.class);
    /**
     * BLOCKING_TIMEOUT_MILLIS
     */
    private static final String BLOCKING_TIMEOUT_MILLIS = "blockingTimeoutMillis";
    /**
     * CACHE_NAME
     */
    private static final String CACHE_NAME = "cacheName";

    /**
     * The cache name can be set through init parameters. If it is set it is
     * stored here.
     */
    protected String cacheName = "interface";

    /**
     * blockingCache
     */
    protected BlockingCache blockingCache;

    /**
     * visitLog
     */
    private final VisitLog visitLog = new VisitLog();
    
    /**
     * pageConfig
     */
    protected Map<String, Integer> pageConfig = new HashMap<String, Integer>();

    /**
     *  doInit
     * @param  filterConfig filterConfig
     */
    public void doInit(FilterConfig filterConfig) {
        
    }
 
    /**
     * parseBlockingCacheTimeoutMillis:(这里用一句话描述这个方法的作用).
     *
     * @param filterConfig  filterConfig
     * @return    设定参数 .
    */
    Integer parseBlockingCacheTimeoutMillis(FilterConfig filterConfig) {

        String timeout = filterConfig.getInitParameter(BLOCKING_TIMEOUT_MILLIS);
        try {
            return Integer.parseInt(timeout);
        } catch (NumberFormatException e) {
            return null;
        }

    }
 
    /**
     * setCacheNameIfAnyConfigured:(这里用一句话描述这个方法的作用).
     *
     * @param filterConfig    设定参数 .
    */
    protected void setCacheNameIfAnyConfigured(FilterConfig filterConfig) {
        this.cacheName = filterConfig.getInitParameter(CACHE_NAME);

    }

    /**
     * Destroys the filter.
     */
    protected void doDestroy() {
        // noop
    }
    /**
     * doFilter
     * @param  request  request
     * @param  response  response
     * @param  chain chain
     * @throws AlreadyGzippedException
     * @throws AlreadyCommittedException
     * @throws FilterNonReentrantException
     * @throws LockTimeoutException
     * @throws Exception
     */
    protected void doFilter(final HttpServletRequest request,
            final HttpServletResponse response, final FilterChain chain)
            throws AlreadyGzippedException, AlreadyCommittedException,
            FilterNonReentrantException, LockTimeoutException, Exception { 
        if (response.isCommitted()) {
            throw new AlreadyCommittedException(
                    "Response already committed before doing buildPage.");
        }
        logRequestHeaders(request); 
        PageInfo pageInfo = buildPageInfo(request, response, chain);  
        if (pageInfo!=null && pageInfo.isOk()) {
            if (response.isCommitted()) {
                throw new AlreadyCommittedException(
                        "Response already committed after doing buildPage"
                                + " but before writing response from PageInfo.");
            }
            writeResponse(request, response, pageInfo);
        }else if(pageInfo==null){   
        	 response.setCharacterEncoding("UTF-8");
        	 response.setHeader("content-type","text/html;charset=UTF-8");
        	 OutputStream out = new BufferedOutputStream(response.getOutputStream());  
             out.write(StaticValues.TIP_SAFE.getBytes("UTF-8"));
             out.flush();
             out.close();
        }
    }

    /**
     * Build page info either using the cache or building the page directly.
     * <p/>
     * Some requests are for page fragments which should never be gzipped, or
     * for other pages which are not gzipped.
     */
    /**
     * 新建缓存  
     * @param request  request
     * @param response  response
     * @param chain  chain
     * @return  PageInfo
     * @throws Exception
     */
    protected PageInfo buildPageInfo(final HttpServletRequest request,
            final HttpServletResponse response, final FilterChain chain)
            throws Exception {
        final String key = calculateKey(request);
        if("参数有误".equals(key)){ 
        	return null;
        }
        Integer time = pageConfig.get(request.getServletPath()); 
        if(time == null || time == 0 || 
        		NumberUtil.getInt(Configs.getConfigs().getInterfaceCache()) == 0){
        	return buildPage(request, response, chain);
        }
        PageInfo pageInfo = null;
        try { 
            checkNoReentry(request);   
        	if(StringUtil.getString(CacheUtil.getValue(StaticValues.CACHE_INTERFACE, key))
        			.isEmpty()){
        		 try {
                     pageInfo = buildPage(request, response, chain); 
                     if (pageInfo.isOk()) {
                         if (LOG.isDebugEnabled()) {
                             LOG.debug("PageInfo ok. Adding to cache "
                                     + blockingCache.getName() + " with key "
                                     + key);
                         } 
                         CacheUtil.setValue(StaticValues.CACHE_INTERFACE, key, pageInfo);
                         
                     } else {
                    	 CacheUtil.setValue(StaticValues.CACHE_INTERFACE, key, null);
                     } 
                 } catch (final Throwable throwable) {
                     blockingCache.put(new Element(key, null));
                     throw new Exception(throwable);
                 }
        	}else{
        		pageInfo=(PageInfo) CacheUtil.getValue(StaticValues.CACHE_INTERFACE, key);
        	} 
             
        } catch (LockTimeoutException e) {
            throw e;
        } finally {
            visitLog.clear();
        }
        return pageInfo;
    }

    /**
     * buildPage
     * @param request  request
     * @param response  response
     * @param chain  chain
     * @return  PageInfo
     * @throws AlreadyGzippedException
     * @throws Exception
     */
    protected PageInfo buildPage(final HttpServletRequest request,
            final HttpServletResponse response, final FilterChain chain)
            throws AlreadyGzippedException, Exception {

        final ByteArrayOutputStream outstr = new ByteArrayOutputStream();
        final GenericResponseWrapper wrapper = new GenericResponseWrapper(
                response, outstr);
        chain.doFilter(request, wrapper);
        wrapper.flush();
        return new PageInfo(wrapper.getStatus(), wrapper.getContentType(),
                wrapper.getCookies(), outstr.toByteArray(), true,
                600, wrapper.getAllHeaders());
    }

    protected void writeResponse(final HttpServletRequest request,
            final HttpServletResponse response, final PageInfo pageInfo)
            throws IOException, DataFormatException,
            ResponseHeadersNotModifiableException {
        boolean requestAcceptsGzipEncoding = acceptsGzipEncoding(request);

        setStatus(response, pageInfo);
        setContentType(response, pageInfo);
        setCookies(pageInfo, response);
        setHeaders(pageInfo, requestAcceptsGzipEncoding, response);
        writeContent(request, response, pageInfo);
    }

    /**
     * Set the content type.
     *   
     * @param response  response
     * @param pageInfo  pageInfo
     */
    protected void setContentType(final HttpServletResponse response,
            final PageInfo pageInfo) {
        String contentType = pageInfo.getContentType();
        if (contentType != null && contentType.length() > 0) {
            response.setContentType(contentType);
        }
    }

    /**
     * Set the serializableCookies
     * 
     * @param pageInfo  pageInfo
     * @param response  response
     */
    @SuppressWarnings("unchecked")
	protected void setCookies(final PageInfo pageInfo,
            final HttpServletResponse response) {

        final Collection cookies = pageInfo.getSerializableCookies();
        for (Iterator iterator = cookies.iterator(); iterator.hasNext();) {
            final Cookie cookie = ((SerializableCookie) iterator.next())
                    .toCookie();
            response.addCookie(cookie);
        }
    }

    /**
     * setStatus
     * @param response  response
     * @param pageInfo  pageInfo
     */
    protected void setStatus(final HttpServletResponse response,
            final PageInfo pageInfo) {
        response.setStatus(pageInfo.getStatusCode());
    }

    /**
     * setHeaders
     * @param pageInfo  pageInfo
     * @param requestAcceptsGzipEncoding  requestAcceptsGzipEncoding
     * @param response  response
     */
    protected void setHeaders(final PageInfo pageInfo,
            boolean requestAcceptsGzipEncoding,
            final HttpServletResponse response) {

        final Collection<Header<? extends Serializable>> headers = pageInfo
                .getHeaders();

        final TreeSet<String> setHeaders = new TreeSet<String>(
                String.CASE_INSENSITIVE_ORDER);

        for (final Header<? extends Serializable> header : headers) {
            final String name = header.getName();

            switch (header.getType()) {
            case STRING:
                if (setHeaders.contains(name)) {
                    response.addHeader(name, (String) header.getValue());
                } else {
                    setHeaders.add(name);
                    response.setHeader(name, (String) header.getValue());
                }
                break;
            case DATE:
                if (setHeaders.contains(name)) {
                    response.addDateHeader(name, (Long) header.getValue());
                } else {
                    setHeaders.add(name);
                    response.setDateHeader(name, (Long) header.getValue());
                }
                break;
            case INT:
                if (setHeaders.contains(name)) {
                    response.addIntHeader(name, (Integer) header.getValue());
                } else {
                    setHeaders.add(name);
                    response.setIntHeader(name, (Integer) header.getValue());
                }
                break;
            default:
                throw new IllegalArgumentException("No mapping for Header: "
                        + header);
            }
        }
    }

    
    protected String getCacheName() {
        return cacheName;
    }

    
    /**
     * getCacheManager
     * @return  CacheManager
     */
    protected abstract CacheManager getCacheManager();

    /**
     * calculateKey
     * @param httpRequest  httpRequest
     * @return  String
     */
    protected abstract String calculateKey(final HttpServletRequest httpRequest);

    /**
     * writeContent
     * @param request  request
     * @param response  response
     * @param pageInfo  pageInfo
     * @throws IOException
     * @throws ResponseHeadersNotModifiableException
     */
    protected void writeContent(final HttpServletRequest request,
            final HttpServletResponse response, final PageInfo pageInfo)
            throws IOException, ResponseHeadersNotModifiableException {
        byte[] body;

        boolean shouldBodyBeZero = ResponseUtil.shouldBodyBeZero(request,
                pageInfo.getStatusCode());
        String servletPath = request.getServletPath().replaceAll("\\W+", "/");
        if (shouldBodyBeZero) {
            body = new byte[0];
        } else if (servletPath.startsWith("/interface/jmp/") || acceptsGzipEncoding(request)) {
            body = pageInfo.getGzippedBody();
            if (ResponseUtil.shouldGzippedBodyBeZero(body, request)) {
                body = new byte[0];
            } else {
                ResponseUtil.addGzipHeader(response);
            }

        } else {
            body = pageInfo.getUngzippedBody();
        }

        response.setContentLength(body.length);
        OutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(body);
        out.flush();
        out.close();
    }
    /**
     * checkNoReentry
     * @param httpRequest  httpRequest
     * @throws FilterNonReentrantException
     */
    protected void checkNoReentry(final HttpServletRequest httpRequest)
            throws FilterNonReentrantException {
        String filterName = getClass().getName();
        if (visitLog.hasVisited()) {
            throw new FilterNonReentrantException(
                    "The request thread is attempting to reenter" + " filter "
                            + filterName + ". URL: "
                            + httpRequest.getRequestURL());
        } else {
            visitLog.markAsVisited();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Thread {}  has been marked as visited.", Thread
                        .currentThread().getName());
            }
        }
    }

    private static class VisitLog extends ThreadLocal<Boolean> {
        @Override
        protected Boolean initialValue() {
            return false;
        }

        /**
         * hasVisited
         * @return  boolean
         */
        public boolean hasVisited() {
            return get();
        }

        /**
         *  markAsVisited
         */
        public void markAsVisited() {
            set(true);
        }

        /**
         *  clear
         */
        public void clear() {
            super.remove();
        }
    }
}
