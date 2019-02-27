package com.hanweb.complat.cache;

import com.hanweb.common.cache.Cache;
import com.hanweb.common.cache.CacheManager;
import com.hanweb.common.util.StringUtil;

/**
 * 平台的缓存
 * 
 * @author 李杰
 *
 */
public class QrcodeCache {

	/**
	 * 缓存名称
	 */
	public final static String QRCODE_CACHE_NAME = "qrcode_cache_key";

	/**
	 * 存储二维码随机数的cache，1分钟失效
	 */
	private final static Cache qrcodeCache = CacheManager.getInstance(QRCODE_CACHE_NAME);

	/**
	 * 默认缓存值
	 */
	public final static String DEFAULT_CACHEVALUE = "_DEFAULT_CACHEVALUE";

	/**
	 * 有效期，秒
	 */
	public final static int EXPIRY_LIMIT = 60;

	/**
	 * 缓存
	 * 
	 * @param id
	 */
	public static void addQrcodeId(String id) {
		qrcodeCache.put(id, DEFAULT_CACHEVALUE, EXPIRY_LIMIT);
	}

	/**
	 * 验证成功缓存登录名
	 * 
	 * @param id
	 */
	public static void addQrcodeId(String id, String value) {
		qrcodeCache.put(id, value, EXPIRY_LIMIT);
	}

	/**
	 * 获得uuid对应的value
	 * 
	 * @param id
	 * @return
	 */
	public static String getQrcodeValue(String id) {
		return qrcodeCache.get(id);
	}

	/**
	 * uuid是否有效
	 * 
	 * @param id
	 * @return
	 */
	public static boolean validQrcodeId(String id) {
		boolean valid = false;
		String qid = qrcodeCache.get(id);
		if (StringUtil.isNotEmpty(qid)) {
			valid = true;
		}
		return valid;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public static void removeQrcodeId(String id) {
		if (StringUtil.isNotEmpty(id)) {
			qrcodeCache.remove(id);
		}
	}
}
