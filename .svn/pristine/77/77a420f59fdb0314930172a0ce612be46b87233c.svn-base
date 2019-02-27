package com.hanweb.complat.util;

/**
 * Sql语句工具
 * 
 * @author 陈健
 * 
 */
public class SqlUtil {
	/**
	 * 清理where条件头尾空格及AND、OR
	 * 
	 * @return
	 */
	public static String trimWhere(String sqlWhere) {
		sqlWhere = sqlWhere.trim();

		String temp = sqlWhere.toLowerCase();

		if (temp.startsWith("and ")) {
			sqlWhere = sqlWhere.substring(4);
		} else if (temp.startsWith("or ")) {
			sqlWhere = sqlWhere.substring(3);
		}

		temp = sqlWhere.toLowerCase();

		if (temp.endsWith(" and")) {
			sqlWhere = sqlWhere.substring(0, sqlWhere.length() - 4);
		} else if (temp.endsWith(" or")) {
			sqlWhere = sqlWhere.substring(0, sqlWhere.length() - 3);
		}

		return sqlWhere;
	}

	public static String trimWhere(StringBuilder sqlWhere) {
		return trimWhere(sqlWhere.toString());
	}
}
