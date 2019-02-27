package com.hanweb.interfaces.service;

import java.security.MessageDigest; 

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; 

import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;

import com.hanweb.jmp.global.entity.CaheData;
import com.hanweb.jmp.global.service.CacheDataService;

public class CommonService {
	
	/**
	 * logger
	 */
	private static final Log logger = LogFactory.getLog(CommonService.class);
	
	/**
	 * MD5 32位散列加密
	 * @param uuid uuid
	 * @return String
	 */
	public String md5(String uuid) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(uuid.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0){
					i += 256;
				}
				if (i < 16){
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	} 
	
	/**
	 * 根据uuid验证合法性
	 * @param uniquecode
	 *            客户端时间戳
	 *            客户端设备唯一码
	 * @param tokenuuid
	 *            uuid的加密值 格式：当前时间毫秒数+"&&&"+uuid的加密
	 * @return 验证合法,返回true;验证非法,返回false
	 */
	public boolean checkUuid(String uniquecode, String uuid, String tokenuuid, String pathUrl) {
		try {
			if(StringUtil.isEmpty(uniquecode) || StringUtil.isEmpty(uuid) 
					|| StringUtil.isEmpty(tokenuuid)){ 
				return false;
			}
			String encodeuuid=uniquecode+"318qwe"+uuid;  
			encodeuuid=md5(encodeuuid);    
			if(!encodeuuid.equals(tokenuuid)){ 
				return false;
			}
			String cacheKey=uniquecode+uuid+pathUrl;
			CacheDataService cacheDataService = SpringUtil.getBean("jmp_CacheDataService", CacheDataService.class);
			int num = cacheDataService.findByCacheKey(cacheKey);
			if(num > 0) {
				return false;
			}
			CaheData data = new CaheData();
			data.setName(cacheKey);
			data.setSpec(cacheKey);
			cacheDataService.add(data);
//			String value = StringUtil.getString(CacheUtil.getValue(StaticValues.CACHE_SAFE,
//					cacheKey));
			//缓存存在，意味着接口地址被截取，重复访问，返回false
//			if(StringUtil.isNotEmpty(value)){
//				return false;
//			} 
//			CacheUtil.setValue(StaticValues.CACHE_SAFE, 
//					cacheKey, cacheKey);  
			return true;
		} catch (Exception e) {
			logger.error("checkUuid error:", e); 
			return false;
		} 
	} 
	
}