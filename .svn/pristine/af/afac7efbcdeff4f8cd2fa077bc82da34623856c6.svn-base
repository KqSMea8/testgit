package com.hanweb.weibo.weibo4j.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.hanweb.weibo.weibo4j.model.PostParameter;



public class ArrayUtils {

	public static PostParameter[] mapToArray(Map<String, String> map) {
		PostParameter[] parList = new PostParameter[map.size()];
		Iterator<Entry<String, String>> iter = map.entrySet().iterator();
		int i = 0;
		while (iter.hasNext()) {
			String key = iter.next().getKey();
			String value = map.get(key);
			parList[i++] = new PostParameter(key, value);
		}
		return parList;
	}
	
}
