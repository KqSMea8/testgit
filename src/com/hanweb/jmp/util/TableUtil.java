package com.hanweb.jmp.util;

public class TableUtil {

	/**
	 * 获得信息表名
	 * 
	 * @param siteid 网站Id
	 * @return String
	 */
	public static String getInfoTableName(Integer siteid){
		String tableName = "jmp_info" + siteid;
		return tableName;
	}
}
