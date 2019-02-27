package com.hanweb.jmp.util;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.jmp.constant.ClientVersion;
import com.hanweb.jmp.constant.Configs;

public class ParameterUtil {

	/**
	 * 接口中必填参数
	 */
	private static String[] arrPara = { "siteId", "clientType", "uuid", "version" };

	/**
	 * 接口参数
	 */
	private HashMap<String, String[]> hmPara = new HashMap<String, String[]>();

	/**
	 * 错误描述：版本不支持
	 */
	public static final String ERRORCODE_1 = "1";

	/**
	 * 错误描述：参数错误
	 */
	public static final String ERRORCODE_2 = "2";

	/**
	 * 错误描述：官方微博未授权
	 */
	public static final String ERRORCODE_3 = "3";

	/**
	 * 错误描述：网络连接错误
	 */
	public static final String ERRORCODE_4 = "4";

	/**
	 * 错误描述：服务器异常
	 */
	public static final String ERRORCODE_5 = "5";

	/**
	 * 客户端类型：UC
	 */
	public static final String CLIENT_UC = "1";

	/**
	 * 客户端类型：iphone
	 */
	public static final String CLIENT_IPHOEN = "2";

	/**
	 * 客户端类型：android
	 */
	public static final String CLIENT_ANDROID = "3";

	/**
	 * 客户端类型：ipad
	 */
	public static final String CLIENT_IPAD = "4";

	/** 日志记录 */
	private Log logger = LogFactory.getLog(getClass());

	public ParameterUtil(HashMap<String, String[]> hmPara) {
		this.hmPara = hmPara;
	}

	/**
	 * 
	 * checkParameter:(判断是否有必填参数).
	 * 
	 * @return 设定参数 .
	 */
	public boolean checkParameter() {
		String[] para = null;
		for (int i = 0; i < arrPara.length; i++) {
			para = (String[]) hmPara.get(arrPara[i]);
			if (para == null || "".equals(para[0])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * checkVesion:检查版本号是否支持.
	 * 
	 * @return true:支持(当前版本号》=最小版本号) false:不支持.
	 */
	public boolean checkVesion() {
		String clientType = getParaForString("clientType"); // 客户端类型
		String nowVersion = getParaForString("version"); // 当前版本号
		if (NumberUtil.getInt(Configs.getConfigs().getCanAppFactory()) == 1) {
			if (NumberUtil.getInt(nowVersion.split("||.")[0]) < 2) {
				nowVersion = "3.8";
			}
		}
		String supVersion = ""; // 支持的最低版本号
		if (CLIENT_UC.equals(clientType)) {
			supVersion = ClientVersion.getClientVersions().getUc();
		} else if (CLIENT_IPHOEN.equals(clientType)) {
			supVersion = ClientVersion.getClientVersions().getIphone();
		} else if (CLIENT_ANDROID.equals(clientType)) {
			supVersion = ClientVersion.getClientVersions().getAndroid();
		} else if (CLIENT_IPAD.equals(clientType)) {
			supVersion = ClientVersion.getClientVersions().getIpad();
		}

		return compareStr(supVersion, nowVersion);
	}

	/**
	 * 
	 * compareStr:(比较两个字符串大小).
	 * 
	 * @param str1
	 *            如2.1.1
	 * @param str2
	 *            如2.2
	 * @return 设定参数 .
	 */
	private boolean compareStr(String str1, String str2) {
		String[] ss = str1.split("\\.");
		String[] ns = str2.split("\\.");
		int sv = 0;
		int nv = 0;
		for (int i = 0; i < (Math.max(ss.length, ns.length)); i++) {
			if(i >= ss.length){
				sv=0;
			}else{
				sv=NumberUtil.getInt(ss[i], 0);
			} 
			if(i >= ns.length){
				nv=0;
			}else{
				nv=NumberUtil.getInt(ns[i], 0);
			}
			if (sv != nv) {
				return sv < nv;
			}
		}
		return true;
	}

	/**
	 * 
	 * toInt:(获取参数).
	 * 
	 * @param str
	 *            参数名称
	 * @return 设定参数 .
	 */
	public int getParaForInt(String str) {
		int para = 0;
		String[] paras = (String[]) hmPara.get(str);
		if (paras != null) {
			para = NumberUtil.getInt(paras[0], 0);
		}
		return para;
	}

	/**
	 * 
	 * toInt:(获取参数).
	 * 
	 * @param str
	 *            参数名称
	 * @param defult
	 *            默认值
	 * @return 设定参数 .
	 */
	public int getParaForInt(String str, int defult) {
		int para = defult;
		String[] paras = (String[]) hmPara.get(str);
		if (paras != null) {
			para = NumberUtil.getInt(paras[0], defult);
		}
		return para;
	}

	/**
	 * getParaForLong:取得long.
	 * 
	 * @param str
	 *            参数名称
	 * @return 设定参数 .
	 */
	public long getParaForLong(String str) {
		long para = 0l;
		try {
			if (!str.equals("")) {
				String[] paras = (String[]) hmPara.get(str);
				if (paras != null && !"".equals(paras[0])) {
					para = Long.parseLong(paras[0]);
				}
			}
		} catch (Exception e) {
			logger.error("getParaForLong Error:" + ParameterUtil.class.toString(), e);
		}
		return para;
	}

	/**
	 * getParaForLong: ToLong.
	 * 
	 * @param str
	 *            参数名称
	 * @param defult
	 *            默认值
	 * @return 设定参数 .
	 */
	public long getParaForLong(String str, long defult) {
		long para = defult;
		try {
			if (!str.equals("")) {
				String[] paras = (String[]) hmPara.get(str);
				if (paras != null && !"".equals(paras[0])) {
					para = (long) NumberUtil.getDouble(paras[0]);
				}
			}
		} catch (Exception e) {
			logger.error("getParaForLong Error:" + ParameterUtil.class.toString(), e);
		}
		return para;
	}

	/**
	 * 
	 * toInt:(获取参数).
	 * 
	 * @param str
	 *            参数名称
	 * @return 设定参数 .
	 */
	public String getParaForString(String str) {
		String para = "";
		String[] paras = (String[]) hmPara.get(str);
		if (paras != null && !"".equals(paras[0])) {
			para = StringUtil.getString(paras[0]);
		}

		return para;
	}
}
