package com.hanweb.jmp.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import com.hanweb.common.util.DateUtil;

public class HttpClientUtil {
	/**
	 * 字符集编码操作类
	 */
	private static EncodeUtil encodeUtil = new EncodeUtil();
	
	/**
	 * 超时时间
	 */
	private static int overtime = 30000;
	
	/**
	 * logger
	 */
	private static final Log LOGGER = LogFactory.getLog(Log.class);
	
	/**
	 * 获取页面源代码
	 * @param strUrl      要抓取页面的地址
	 * @param charset     页面的字符编码        可以为 null
	 * @return            页面源代码
	 */
	public static String getInfo(String strUrl, String charset) {
		if ((strUrl == null) || (strUrl.trim().equals(""))) {
			return "connect error";
		}
		String htmlContent = "";
		HttpClient httpClient = getHttpClient(overtime);
		/*生成 GetMethod 对象并设置参数*/
		HttpGet getMethod = getGetMethod(overtime);
		try {
			/*自动获取CHARSET*/
			if (charset == null || charset.equals("")) {
//				charset = encodeUtil.getURLEncoding(new URL(strUrl));
				charset = encodeUtil.getEncoding(strUrl);
			}
			/*处理URl带中文的问题*/
			strUrl = strUrl.replaceAll("&amp;", "&");
			strUrl = encodeUtil.encodeStr(strUrl, charset);
			getMethod.setURI(new URI(strUrl.trim()));
			/*执行 HTTP GET 请求*/
			HttpResponse response = httpClient.execute(getMethod);
			HttpEntity entity = response.getEntity();
			int statusCode = response.getStatusLine().getStatusCode();
			/*判断访问的状态码*/
			if (statusCode != HttpStatus.SC_OK) {
				htmlContent = "connect error";
			}else{
				htmlContent = EntityUtils.toString(entity, charset);
			}
		} catch (IOException e) {
			LOGGER.warn(e.getMessage());
			/*获取页面源代码时发生网络异常*/
			htmlContent = "connect error";
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
			/*获取页面源代码时发生程序错误*/
			htmlContent = "connect error";
		} finally {
			/*释放HttpGet*/
			getMethod.abort();
			releaseHttpClient(httpClient);
		}
		return htmlContent;
	}
	
	/**
	 * 获取页面源代码
	 * @param strUrl      要抓取页面的地址
	 * @param charset     页面的字符编码        可以为 null
	 * @return            页面源代码
	 */
	public static String getInfoTrustAll(String strUrl, String charset) {
		if ((strUrl == null) || (strUrl.trim().equals(""))) {
			return "connect error";
		}
		String htmlContent = "";
		HttpClient httpClient = getHttpClientWithSSLSelf(overtime);
		/*生成 GetMethod 对象并设置参数*/
		HttpGet getMethod = getGetMethod(overtime);
		try {
			/*自动获取CHARSET*/
			if (charset == null || charset.equals("")) {
				charset = encodeUtil.getURLEncoding(new URL(strUrl));
			}
			/*处理URl带中文的问题*/
			strUrl = strUrl.replaceAll("&amp;", "&");
			strUrl = encodeUtil.encodeStr(strUrl, charset);
			getMethod.setURI(new URI(strUrl.trim()));
			/*执行 HTTP GET 请求*/
			HttpResponse response = httpClient.execute(getMethod);
			HttpEntity entity = response.getEntity();
			int statusCode = response.getStatusLine().getStatusCode();
			/*判断访问的状态码*/
			if (statusCode != HttpStatus.SC_OK) {
				htmlContent = "connect error";
			}else{
				htmlContent = EntityUtils.toString(entity, charset);
			}
		} catch (IOException e) {
			LOGGER.warn(e.getMessage());
			/*获取页面源代码时发生网络异常*/
			htmlContent = "connect error";
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
			/*获取页面源代码时发生程序错误*/
			htmlContent = "connect error";
		} finally {
			/*释放HttpGet*/
			getMethod.abort();
			releaseHttpClient(httpClient);
		}
		return htmlContent;
	}

	/**
	 * 获取页面状态 测试连接使用
	 * @param strUrl strUrl
	 * @param overtime overtime
	 * @return int
	 */
	public static int getStatusCode(String strUrl, int overtime){
		HttpClient httpClient = getHttpClient(overtime);
		HttpGet getMethod = getGetMethod(overtime);
		int statusCode = 0;
		try{
			strUrl = strUrl.replaceAll("&amp;", "&");
			strUrl = encodeUtil.encodeStr(strUrl, null);
			getMethod.setURI(new URI(strUrl.trim()));
			/*执行 HTTP GET 请求*/
			HttpResponse response = httpClient.execute(getMethod);
			statusCode = response.getStatusLine().getStatusCode();
		}catch (Exception e){
			/**获取状态发生异常*/
			e.printStackTrace();
		}finally{
			/*释放HttpGet*/
			getMethod.abort();
			releaseHttpClient(httpClient);
		}
		return statusCode;
	}
	
	/**
	 * 下载文件
	 * @param strUrl      下载文件的地址
	 * @param filePath    存放文件的地址（包括文件名称）
	 * @return            下载文件是否成功
	 */
	public static boolean downloadFile(String strUrl, String filePath) {
		boolean isSuccess = false;
		HttpClient httpClient = getHttpClient(overtime);
		HttpGet get = getGetMethod(overtime);
		
		FileOutputStream output = null;
		InputStream in = null;
		try {
			URL url = new URL(strUrl);
			/*获得网站域名*/
			String referer = "http://" + url.getHost();
			strUrl = strUrl.replaceAll("&amp;", "&");
			strUrl = encodeUtil.encodeStr(strUrl, "UTF-8");
			get.setURI(new URI(strUrl.trim()));
			/*解决 TOO MANAY OPEN FILES 问题和防盗链问题*/
			get.setHeader("Referer", referer);
			get.setHeader("HTTP_REFERER", referer);
			HttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			int statusCode = response.getStatusLine().getStatusCode();
			String sufix = strUrl;
            if(sufix.indexOf("?")!=-1){
                sufix = sufix.substring(0, sufix.lastIndexOf("?")); 
            }
            if(sufix.lastIndexOf("/")!=-1){
            	sufix = sufix.substring(sufix.lastIndexOf("/")+1); 
            }
            if(sufix.lastIndexOf(".") != -1){
            	sufix = sufix.substring(sufix.lastIndexOf(".")+1);
            }else{
            	sufix = entity.getContentType().toString();
                if(sufix.lastIndexOf("/")!=-1){
                	sufix = sufix.substring(sufix.lastIndexOf("/")+1); 
                }
            }
            if(new File(filePath).isDirectory()){
            	if(!filePath.endsWith("/")){
            		filePath += "/";
            	}
            	filePath = filePath + DateUtil.getCurrDate("yyyyMMddHHmmssSSS") + "." + sufix;
            }
			if(statusCode == HttpStatus.SC_OK){
				File storeFile = new File(filePath);
				output = new FileOutputStream(storeFile);
				// 得到网络资源的字节数组,并写入文件
				in = entity.getContent();
				output.write(inputStreamToByte(in));
				isSuccess =  true;
			}
		} catch (IOException e) {
			/*下载文件时发生网络异常*/
			isSuccess = false;
		} catch (Exception e) {
			e.printStackTrace();
			/*下载文件时发生程序错误*/
			isSuccess = false;
		} finally {
			get.abort();
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					/*释放连接失败*/
					LOGGER.warn(e.getMessage());
				}
			}
			releaseHttpClient(httpClient);
		}
		return isSuccess;
	}
	
	/**
	 * getSufix
	 * @param strUrl   strUrl
	 * @return  String
	 */
	public static String getSufix(String strUrl){
		String sufix = strUrl;
        if(sufix.indexOf("?")!=-1){
            sufix = sufix.substring(0, sufix.lastIndexOf("?")); 
        }
        if(sufix.lastIndexOf("/")!=-1){
        	sufix = sufix.substring(sufix.lastIndexOf("/")+1); 
        }
        if(sufix.lastIndexOf(".") != -1){
        	return sufix.substring(sufix.lastIndexOf(".")+1);
        }
		HttpClient httpClient = getHttpClient(overtime);
		HttpGet get = getGetMethod(overtime);
		try{
			
			URL url = new URL(strUrl);
			/*获得网站域名*/
			String referer = "http://" + url.getHost();
			strUrl = strUrl.replaceAll("&amp;", "&");
			strUrl = encodeUtil.encodeStr(strUrl, "UTF-8");
			get.setURI(new URI(strUrl.trim()));
			/*解决 TOO MANAY OPEN FILES 问题和防盗链问题*/
			get.setHeader("Referer", referer);
			get.setHeader("HTTP_REFERER", referer);
			HttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			sufix = entity.getContentType().toString();
			if(sufix.lastIndexOf("/")!=-1){
	        	sufix = sufix.substring(sufix.lastIndexOf("/")+1); 
	        }
		}catch (Exception e) {
			e.printStackTrace();
			sufix = "";
		}finally {
			get.abort();
			releaseHttpClient(httpClient);
		}
		return sufix;
	}
	/**
	 * 向某页面POST数据
	 * @param strUrl      地址
	 * @param qparams     内容
	 * @param charset     字符编码
	 * @return          页面返回的数据
	 */
	public static String postInfo(String strUrl, List<NameValuePair> qparams, String charset){
		HttpClient httpClient = getHttpClient(overtime);
		HttpPost post = getPostMethod(overtime);
		HttpEntity entity = null;
		String htmlcontent = "";
		try{
			if(charset == null || "".equals(charset)){
				charset = encodeUtil.getURLEncoding(new URL(strUrl));
			}
			strUrl = strUrl.replaceAll("&amp;", "&");
			strUrl = encodeUtil.encodeStr(strUrl, charset);
			post.setURI(new URI(strUrl.trim()));
			post.setEntity(new UrlEncodedFormEntity(qparams, charset));
	
	        HttpResponse response = httpClient.execute(post);
	        //int statusCode = response.getStatusLine().getStatusCode();
			entity = response.getEntity();
			htmlcontent = EntityUtils.toString(entity, charset);
			EntityUtils.consume(entity);
		}catch(UnsupportedEncodingException e){
		    e.printStackTrace();
			htmlcontent = "connect error";
		}catch (Exception e) {
		    e.printStackTrace();
			htmlcontent = "connect error";
		}finally{
			post.abort();
			releaseHttpClient(httpClient);
		}
		return htmlcontent;
	}

	/**
	 * 获取HttpClient
	 * @param overtime 超时时间
	 * @return HttpClient
	 */
	private static HttpClient getHttpClient(int overtime) {
		SchemeRegistry supportedSchemes = new SchemeRegistry();
		supportedSchemes.register(new Scheme("http", 80, PlainSocketFactory
				.getSocketFactory()));
		supportedSchemes.register(new Scheme("https", 443, SSLSocketFactory
				.getSocketFactory()));

		ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(
				supportedSchemes);
		/*设置最大连接数*/
		manager.setMaxTotal(200);
		/*设置每个路由的默认最大连接到20*/
		manager.setDefaultMaxPerRoute(20);
		/*对LocalHost:80设置最大连接到50*/
		HttpHost localhost = new HttpHost("locahost", 80);
		manager.setMaxForRoute(new HttpRoute(localhost), 50);
		/*设置HTTPHEAD*/
		List<BasicHeader> headers = new ArrayList<BasicHeader>();
		headers.add(new BasicHeader("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"));
		headers.add(new BasicHeader("Accept-Language", "zh,en"));
		HttpParams httpParams = new BasicHttpParams();
		httpParams.setParameter("http.default-headers", headers);
		HttpProtocolParams.setContentCharset(httpParams, "GB18030");
		HttpProtocolParams.setHttpElementCharset(httpParams, "GB18030");
		httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				overtime);
		httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, overtime);
		httpParams.setParameter("http.protocol.expect-continue", false);
		HttpClient httpClient = new DefaultHttpClient(manager, httpParams);
		/*设置cookies标准 解决 采集163网站出现的警告*/
		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
				CookiePolicy.IGNORE_COOKIES);

		return httpClient;
	}
	
	/**
	 * 获取HttpClient，https将创建自定义ssl证书，并忽略校验
	 * @param overtime 超时时间
	 * @return HttpClient
	 */
	@SuppressWarnings("deprecation")
	private static HttpClient getHttpClientWithSSLSelf(int overtime) {
		SchemeRegistry supportedSchemes = new SchemeRegistry();
		supportedSchemes.register(new Scheme("http", 80, PlainSocketFactory
				.getSocketFactory()));
		supportedSchemes
			.register(new Scheme("https", SSLTrustAllSocketFactory.getSocketFactory(), 443));  

		ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(
				supportedSchemes);
		/*设置最大连接数*/
		manager.setMaxTotal(200);
		/*设置每个路由的默认最大连接到20*/
		manager.setDefaultMaxPerRoute(20);
		/*对LocalHost:80设置最大连接到50*/
		HttpHost localhost = new HttpHost("locahost", 80);
		manager.setMaxForRoute(new HttpRoute(localhost), 50);
		/*设置HTTPHEAD*/
		List<BasicHeader> headers = new ArrayList<BasicHeader>();
		headers.add(new BasicHeader("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"));
		headers.add(new BasicHeader("Accept-Language", "zh,en"));
		HttpParams httpParams = new BasicHttpParams();
		httpParams.setParameter("http.default-headers", headers);
		HttpProtocolParams.setContentCharset(httpParams, "GB18030");
		HttpProtocolParams.setHttpElementCharset(httpParams, "GB18030");
		httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				overtime);
		httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, overtime);
		httpParams.setParameter("http.protocol.expect-continue", false);
		HttpClient httpClient = new DefaultHttpClient(manager, httpParams);
		/*设置cookies标准 解决 采集163网站出现的警告*/
		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
				CookiePolicy.IGNORE_COOKIES);

		return httpClient;
	}

	/**
	 * 人为释放HttpClient
	 * @param httpClient   .
	 */
	private static void releaseHttpClient(HttpClient httpClient) {
		if (httpClient == null) {
			return;
		}
		if (httpClient.getConnectionManager() == null) {
			return;
		}
		httpClient.getConnectionManager().shutdown();
	}

	/**
	 * getGetMethod:(设置httpMethod的属性).
	 * @param  overtime 超时时间
	 * @return HttpGet  
	 */
	private static HttpGet getGetMethod(int overtime) {
		HttpGet getMethod = new HttpGet();
		getMethod.setHeader("Connection", "close");
		getMethod.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT,
				Integer.valueOf(overtime));
		getMethod.getParams().setParameter("http.protocol.head-body-timeout",
				Integer.valueOf(overtime));
		getMethod.getParams().setParameter(
				CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
		return getMethod;
	}

	/**
	 * getPostMethod:(设置httpMethod的属性).
	 * @param  overtime 超时时间
	 * @return HttpPost 
	 */
	private static HttpPost getPostMethod(int overtime) {
		HttpPost postMethod = new HttpPost();
		postMethod.setHeader("Connection", "close");
		postMethod.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT,
				Integer.valueOf(overtime));
		postMethod.getParams().setParameter("http.protocol.head-body-timeout",
				Integer.valueOf(overtime));
		postMethod.getParams().setParameter(
				CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
		return postMethod;
	}
	
	/**
	 * getHeadMethod:设置httpMethod的属性.
	 * @param  overtime 超时时间
	 * @return HttpHead 
	 */
	@SuppressWarnings("unused")
	private static HttpHead getHeadMethod(int overtime) {
		HttpHead head = new HttpHead();
		head.setHeader("Connection", "close");
		head.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT,
				Integer.valueOf(overtime));
		head.getParams().setParameter("http.protocol.head-body-timeout",
				Integer.valueOf(overtime));
		head.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE,
				false);
		return head;
	}
	
	/**
	 * inputStreamToByte:(这里用一句话描述这个方法的作用).
	 *
	 * @param is is
	 * @return byte[]
	 * @throws IOException    设定参数 .
	*/
	public static byte[] inputStreamToByte(InputStream is) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] bs = new byte[1024]; // 至少用1K的缓冲吧
		int len = -1;
		while ((len = is.read(bs)) != -1) {
			bos.write(bs, 0, len);
		}
		byte b[] = bos.toByteArray();
		bos.close();
		return b;
	}
	
	public static class SSLTrustAllSocketFactory extends SSLSocketFactory {

		/**
		 * TAG
		 */
	    @SuppressWarnings("unused")
		private static final String TAG = "SSLTrustAllSocketFactory";
	    
	    /**
	     * mCtx
	     */
	    private SSLContext mCtx;

	    public static class SSLTrustAllManager implements X509TrustManager {

	        @Override
	        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
	                throws CertificateException {
	        }

	        @Override
	        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
	                throws CertificateException {
	        }

	        @Override
	        public X509Certificate[] getAcceptedIssuers() {
	            return null;
	        }

	    }

	    @SuppressWarnings("deprecation")
		public SSLTrustAllSocketFactory(KeyStore truststore)
	            throws Throwable {
	        super(truststore);
	        try {
	            mCtx = SSLContext.getInstance("TLS");
	            mCtx.init(null, new TrustManager[] { new SSLTrustAllManager() },
	                    null);
	            setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	    }

	    @Override
	    public Socket createSocket(Socket socket, String host,
	                               int port, boolean autoClose)
	            throws IOException, UnknownHostException {
	        return mCtx.getSocketFactory().createSocket(socket, host, port, autoClose);
	    }

	    @Override
	    public Socket createSocket() throws IOException {
	        return mCtx.getSocketFactory().createSocket();
	    }

	    /**
	     * getSocketFactory:(这里用一句话描述这个方法的作用).
	     *
	     * @return    设定参数 .
	    */
	    public static SSLSocketFactory getSocketFactory() {
	        try {
	            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	            trustStore.load(null, null);
	            SSLSocketFactory factory = new SSLTrustAllSocketFactory(trustStore);
	            return factory;
	        } catch (Throwable e) {
//	            Log.d(TAG, e.getMessage());
	        	LOGGER.warn(e.getMessage());
	        }
	        return null;
	    }

	}
	
	/**
	 * 测试方法
	 * @param args 参数
	 */
	public static void main(String[] args) {

		String strCode = HttpClientUtil.getInfo("http://www.baidu.com"
				, null);
		//下载图片
		HttpClientUtil.downloadFile("http://www.baidu.com/img/baidu_sylogo1.gif"
				, "d:/baidu.gif");
		//POST数据
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user", "chen"));
		params.add(new BasicNameValuePair("pwd", "hanweb"));
		strCode = HttpClientUtil.postInfo("http://www.baidu.com", params, "GB2312");
	}
}
