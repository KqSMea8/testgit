package com.hanweb.jmp.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import net.oschina.j2cache.CacheChannel;

public class CacheUtil {

	//管理器建立
	//public static CacheManager manager = null;
	
	//private static CacheUtil instance = null;

	//public static CacheManager getManager() {
	//	return manager;
	//}

	//public static void setManager(CacheManager manager) {
	//	CacheUtil.manager = manager;
	//}

	//public static void setInstance(CacheUtil instance) {
	//	CacheUtil.instance = instance;
	//}

	/**
	 * 管理器建立
	 */
	//private static CacheManager manager = new CacheManager();
	
	//public static CacheUtil getInstance() {
	//	if (instance == null){
	//		instance = new CacheUtil();
	//	}
	//	return instance;
	//}
	
	//public CacheUtil() {
		
	//}
	
	//public void createCache(String configFilePath){
	
		// 使用默认配置文件创建CacheManager
	//	CacheUtil.setManager(CacheManager.create(configFilePath));
	//}
	
	/**
	 * 持久化到磁盘
	 * @param cacheName 缓存区名称
	 * @return
	 */
	//public static boolean flush(String cacheName){
	//	CacheChannel cache = CacheChannel.getInstance();
	//	cache.;
	//	return true;
	//}
	
	/**
	 * 获得所有以**开头的key
	 * @param cacheName
	 * @return  List<String>
	 */
	public static List<String> getKey(String cacheName, String key){
		CacheChannel cache = CacheChannel.getInstance();
		List<String> keyList = new ArrayList<String>();
		List<String> list = cache.keys(cacheName);
		if(list!=null){
			Iterator<String> it = list.iterator();
			while(it.hasNext()){
				String key_ = it.next();
				if(key_.startsWith(key)){
					keyList.add(key_);
				}
			}
		}
		return keyList;
	}
	
	/**
	 * 删除缓存 (以**开头的key)
	 * @param cacheName 缓存区名称
	 * @param key
	 * @return  boolean
	 */
	public static boolean removeKeyStartsWith(String cacheName, String key){
		CacheChannel cache = CacheChannel.getInstance();
		List<String> list = cache.keys(cacheName);
		if(list!=null){
			Iterator<String> it = list.iterator();
			while(it.hasNext()){
				String key_ = it.next();
				if(key_.startsWith(key)){
					cache.evict(cacheName,key_);
				}
			}
		}
		return true;
	}
	
	/**
	 * 删除缓存
	 * @param cacheName 缓存区名称
	 * @param key
	 * @return  boolean
	 */
	public static boolean removeKey(String cacheName, String key){
		CacheChannel cache = CacheChannel.getInstance();
		if (cacheName == null || cacheName.trim().length() == 0 || key == null) {
			return false;
		}
		//if (!manager.cacheExists(cacheName)) {
		//	return false;
		//}
		cache.evict(cacheName, key);
		return true;
	}
	
	/**
	 * 删除所有缓存
	 * @param cacheName 缓存区名称
	 * @param key
	 * @return  boolean
	 * @throws IOException 
	 */
	public static boolean removeAll(String cacheName) throws  IOException{
		CacheChannel cache = CacheChannel.getInstance();
		cache.clear(cacheName);
		return true;
	}
	
	/**
	 * 将数据以键值对方式存入缓存
	 * @param strCacheName String 缓存名
	 * @param key          Object key值
	 * @param value        Object  value值
	 * @return             boolean 存入成功返回true，存入失败返回false
	 */
	public static boolean setValue(String strCacheName, Object key, Object value){
		if (strCacheName == null || strCacheName.trim().length() == 0 || key == null) {
			return false;
		} else {
			CacheChannel cache = CacheChannel.getInstance();
			cache.set(strCacheName, key, value);
			return true;
		}
	}
	
	/**
	 * 根据缓存名和key值从缓存中获取数据
	 * @param strCacheName String 缓存名
	 * @param key          Object key值
	 * @return             Object 获取到的数据
	 */
	public static Object getValue(String strCacheName, Object key){
		if (strCacheName == null || strCacheName.trim().length() == 0 || key == null) {
			return null;
		} else {
			CacheChannel cache = CacheChannel.getInstance();
			Object object = null;
			if(cache!=null){
				object = cache.getValue(strCacheName, key);
			}
			return object;
		}
	}
	
	/**
	 * 根据缓存名和key移除缓存中的数据
	 * @param strCacheName String 缓存名
	 * @param key          Object key值
	 * @return             boolean 移除成功返回true，移除失败返回false
	 */
	public boolean removeKey(String strCacheName, Object key){
		if (strCacheName == null || strCacheName.trim().length() == 0 || key == null) {
			return false;
		} else {
			CacheChannel cache = CacheChannel.getInstance();
			cache.evict(strCacheName, key);
			return true;
		}
	}
	
	/**
	 * 获取缓存管理器中所有的缓存名
	 * @return String[] 缓存管理器中所有的缓存名数组
	 */
	//public String[] getCacheNames(){
	//	return manager.getCacheNames();
	//}
	
	/**
	 * 获取缓存中的对象数量
	 * @param strCacheName String 缓存名
	 * @return             int    缓存中的对象数量
	 */
	//public int getSize(String strCacheName) {
	//	if (strCacheName == null || strCacheName.trim().length() == 0){
	//		return 0;
	//	} else {
	//		Cache cache = manager.getCache(strCacheName);
	//		if (cache == null) {
	//			return 0;
	//		} else {
	//			int iElementsInMemory = cache.getSize();
	//	        return iElementsInMemory;
	//		}
	//	}
	//}
	
	/**
	 * 获取缓存对象占用内存的数量
	 * @param strCacheName String 缓存名
	 * @return             long   缓存对象占用内存的数量
	 */
	//public long getMemoryStoreSize(String strCacheName) {
	//	if (strCacheName == null || strCacheName.trim().length() == 0){
	//		return 0;
	//	} else {
	//		Cache cache = manager.getCache(strCacheName);
	//		if (cache == null) {
	//			return 0;
	//		} else {
	//			long lMemoryStoreSize = cache.getMemoryStoreSize();
	//	        return lMemoryStoreSize;
	//		}
	//	}
	//}
	
	/**
	 * 获取缓存对象占用磁盘的数量
	 * @param strCacheName String 缓存名
	 * @return             long   对象占用磁盘的数量
	 */
	//public long getDiskStoreSize(String strCacheName) {
	//	if (strCacheName == null || strCacheName.trim().length() == 0){
	//		return 0;
	//	} else {
	//		Cache cache = manager.getCache(strCacheName);
	//		if (cache == null) {
	//			return 0;
	//		} else {
	//			long lDiskStoreSize = cache.getDiskStoreSize();
	//	        return lDiskStoreSize;
	//		}
	//	}
	//}
	
	/**
	 * 获取缓存读取的命中次数
	 * @param strCacheName String 缓存名
	 * @return             int    缓存读取的命中次数
	 */
//	public long getHitCount(String strCacheName) {
//		if (strCacheName == null || strCacheName.trim().length() == 0){
//			return 0;
//		} else {
//			Cache cache = manager.getCache(strCacheName);
//			if (cache == null) {
//				return 0;
//			} else {
//				long iHitCount = cache.getStatistics().getCacheHits();
//		        return iHitCount;
//			}
//		}
//	}
	
	/**
	 * 获取内存中缓存读取的命中次数
	 * @param strCacheName String 缓存名
	 * @return             int    内存中缓存读取的命中次数
	 */
//	public long getMemoryStoreHitCount(String strCacheName) {
//		if (strCacheName == null || strCacheName.trim().length() == 0){
//			return 0;
//		} else {
//			Cache cache = manager.getCache(strCacheName);
//			if (cache == null) {
//				return 0;
//			} else {
//				long iMemoryStoreHitCount = cache.getStatistics().getInMemoryHits();
//		        return iMemoryStoreHitCount;
//			}
//		}
//	}
	
	/**
	 * 获取磁盘中缓存读取的命中次数
	 * @param strCacheName String 缓存名
	 * @return             int    磁盘中缓存读取的命中次数
	 */
//	public long getDiskStoreHitCount(String strCacheName) {
//		if (strCacheName == null || strCacheName.trim().length() == 0){
//			return 0;
//		} else {
//			Cache cache = manager.getCache(strCacheName);
//			if (cache == null) {
//				return 0;
//			} else {
//				long iDiskStoreHitCount = cache.getStatistics().getOnDiskHits();
//		        return iDiskStoreHitCount;
//			}
//		}
//	}
	
	/**
	 * 获取缓存读取的丢失次数
	 * @param strCacheName String 缓存名
	 * @return             int    缓存读取的丢失次数
	 */
//	public long getMissCountNotFound(String strCacheName) {
//		if (strCacheName == null || strCacheName.trim().length() == 0){
//			return 0;
//		} else {
//			Cache cache = manager.getCache(strCacheName);
//			if (cache == null) {
//				return 0;
//			} else {
//				long iMissCountNotFound = cache.getStatistics().getCacheMisses();
//		        return iMissCountNotFound;
//			}
//		}
//	}
	
	/**
	 * 获取缓存读取的已经被销毁的对象丢失次数
	 * @param strCacheName String 缓存名
	 * @return             int    缓存读取的已经被销毁的对象丢失次数
	 */
//	PUBLIC LONG GETMISSCOUNTEXPIRED(STRING STRCACHENAME) {
//		IF (STRCACHENAME == NULL || STRCACHENAME.TRIM().LENGTH() == 0){
//			RETURN 0;
//		} ELSE {
//			CACHE CACHE = MANAGER.GETCACHE(STRCACHENAME);
//			RETURN CACHE.GETMISSCOUNTEXPIRED();
//		}
//	}
	
	/**
	 * 关闭缓存管理器 CacheManager
	 */
	//public static void close(){
        //关闭缓存管理器 CacheManager
    //    CacheManager.getInstance().shutdown();
	//}
	
	/**
	 * 移除当前缓存
	 * @param strCacheName String 缓存名
	 */
	//public void removeCache(String strCacheName){
	//	manager.removeCache(strCacheName);
	//}
	
	/**
	 * 移除所有缓存
	 */
	//public void removeAllCache(){
	//	manager.removalAll();
	//}
	
	/**
	 * 获取缓存持久化到硬盘的路径
	 * @return String 缓存持久化到硬盘的路径
	 */
	//public String getTempFilePath() {
	//	return System.getProperty("user.home");
	//}
	
	/**
	 * 获取缓存中所有的元素
	 * @param strCacheName
	 * @return
	 */
	public static HashSet getAllElement(String strCacheName){
		CacheChannel cache = CacheChannel.getInstance();
		HashSet returnSet = new HashSet();
		List<String> list = cache.keys(strCacheName);
		if(list!=null){
			Iterator<String> it = list.iterator();
			while(it.hasNext()){
				String key_ = it.next();
				Object value = cache.getValue(strCacheName, key_);
				returnSet.add(value);
			}
		}
		return returnSet;
	//	List list = null;
	//	HashSet returnSet = new HashSet();
	//	if (strCacheName != null && strCacheName.trim().length() > 0) {
	//		if(!manager.cacheExists(strCacheName)){
				//添加和删除缓存元素
	//			manager.addCache(strCacheName);
	//		}		
	//		Cache cache = manager.getCache(strCacheName);
	//		list = cache.getKeys();
	//		if(list != null && list.size() > 0){
	//			for(int i=0; i < list.size(); i++){
	//				returnSet.add(cache.get(list.get(i)));
	//			}
	//		}
	//	}
	//	return returnSet;
	}
	
}
