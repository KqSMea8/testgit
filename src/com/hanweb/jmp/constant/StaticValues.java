package com.hanweb.jmp.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class StaticValues {
	/**
	 * 
	 * 时间格式
	 */
	public static String[] formats = null;

	

	/**
	 * 字段绑定数据
	 */
	private static Map<String, String> bindFields = null; 

	/**
	 * 首图格式
	 */
	public static final String JPG_TYPE = "jpg";

	/**
	 * 
	 * 图标格式
	 */
	public static final String PNG_TYPE = "png";

	/**
	 * 
	 * 压缩包文件格式
	 */
	public static final String ZIP_TYPE[] = { "zip", "ZIP" };
	/**
	 * 图片类型
	 */
	public static final String IMG_TYPE = "png,jpg,jpeg,bmp,gif";
	/**
	 * 附件类型
	 */
	public static final String FILE_TYPE = 
		"rar,doc,docx,zip,pdf,txt,swf,wmv,mp3,mp4,3gp,mov,m4v,ima4";
	/**
	 * 上传附件类型
	 */
	public static final HashSet<String> ALL_FILE_TYPE 
					= new HashSet<String>(Arrays.asList((IMG_TYPE+","+FILE_TYPE).split(",")));
	/**
	 * 栏目图标
	 */
	public static final String ICONPIC_NAME = "icon";
	
	/**
     * 栏目图标
     */
    public static final String APPICONPIC_NAME = "appicon";

	/**
	 * 栏目首图
	 */
	public static final String FIRSTPIC_NAME = "firstpic";

	/**
	 * 栏目背景图
	 */
	public static final String BACKPIC_NAME = "backpic";

	/**
	 * 互动手机背景图
	 */
	public static final String ACTMOBILEPIC_NAME = "mobilepic";
	/**
	 * 互动ipad背景图
	 */
	public static final String ACTIPADPIC_NAME = "ipadpic";
	/**
	 * 互动配置
	 */
	public static final String ACTXML_NAME = "config";
	
	/**
	 * 信息缓存区域名字
	 */
	public static final String CACHE_REGION ="jmpinfo";
	
	/**
	 * 接口缓存区域名称
	 */
	public static final String CACHE_INTERFACE = "jmpinterface";
	
	/**
	 * 网站缓存区域名称
	 */
	public static final String CACHE_SITE = "jmpsite";
	
	/**
	 * 栏目缓存名称
	 */
	public static final String CACHE_COL = "jmpcol";
	
	/**
	 *  接口安全缓存名称
	 */
	public static final String CACHE_SAFE = "accessControl";
	/**
	 * 网上调查缓存
	 */
	public static final String CACHE_ONLINESURVEY = "onlinesurvey";
	/**
	 * 意见征集缓存
	 */
	public static final String CACHE_SOLICITOPINION = "solicitopinion";

	
	/**
	 * windows模版路径
	 */
	private static String windows = "";

	/**
	 * iPhone模版路径
	 */
	private static String iphone = "";

	/**
	 * ipad模版路径
	 */
	private static String ipad = "";

	/**
	 * Android模版路径
	 */
	private static String android = "";

	/**
	 * 默认模版路径
	 */
	private static String defhtml = "";
	
	
	/**
	 * iphone正文模版路径
	 */
	private static String iphoneTemp = "";
	
	
	/**
	 * ipad正文模版路径
	 */
	private static String ipadTemp = "";
	
	/**
	 * 正文图文混排模版路径
	 */
	private static String ipadPicAndTextTemp = "";
	
	/**
	 * android带宽度模版路径
	 */
	private static String andrTempWithWidth = "";
	
	/**
	 * android纯图模版路径
	 */
	private static String androidTemp = "";
	
	/**
	 * android普通信息模版路径
	 */
	private static String normalAndroidTemp = "";
	
	/**
	 * 场景式摘要模版
	 */
	private static String sceneAbsTmpUrl = "";
	
	/**
	 * 正文vedio模版
	 */
	private static String sceneVedioTmpUrl = "";
	
	/**
	 *  正文audio模版
	 */
	private static String sceneAudioTmpUrl = "";
	
	/**
	 * android vedio模版
	 */
	private static String andrVedioTmpUrl = "";
	
	/**
	 * 新闻聚合类信息正文vedio模版
	 */
	private static String artVedioTmpUrl = "";
	
	/**
	 * 新闻聚合类信息正文摘要模版
	 */
	private static String artAbsTmpUrl = ""; 
	
	/**
	 * 接口安全提示
	 */
	public static final String TIP_SAFE = "您的参数有误，请联系管理员";
	/**
	 * codeMap
	 */
	public static Map<String, String> codeMap = new HashMap<String, String>();

	public static Map<String, String> getBindFields() {
		return bindFields;
	}

	public static void setBindFields(Map<String, String> bindFields) {
		StaticValues.bindFields = bindFields;
	} 

	public static String[] getFormats() {
		return formats;
	}

	public static void setFormats(String[] formats) {
		StaticValues.formats = formats;
	}

	public static String getWindows() {
		return windows;
	}

	public static void setWindows(String windows) {
		StaticValues.windows = windows;
	}

	public static String getIphone() {
		return iphone;
	}

	public static void setIphone(String iphone) {
		StaticValues.iphone = iphone;
	}

	public static String getIpad() {
		return ipad;
	}

	public static void setIpad(String ipad) {
		StaticValues.ipad = ipad;
	}

	public static String getAndroid() {
		return android;
	}

	public static void setAndroid(String android) {
		StaticValues.android = android;
	}

	public static String getDefhtml() {
		return defhtml;
	}

	public static void setDefhtml(String defhtml) {
		StaticValues.defhtml = defhtml;
	}

	public static String getIphoneTemp() {
		return iphoneTemp;
	}

	public static void setIphoneTemp(String iphoneTemp) {
		StaticValues.iphoneTemp = iphoneTemp;
	}

	public static String getIpadTemp() {
		return ipadTemp;
	}

	public static void setIpadTemp(String ipadTemp) {
		StaticValues.ipadTemp = ipadTemp;
	}

	public static String getIpadPicAndTextTemp() {
		return ipadPicAndTextTemp;
	}

	public static void setIpadPicAndTextTemp(String ipadPicAndTextTemp) {
		StaticValues.ipadPicAndTextTemp = ipadPicAndTextTemp;
	}

	public static String getAndrTempWithWidth() {
		return andrTempWithWidth;
	}

	public static void setAndrTempWithWidth(String andrTempWithWidth) {
		StaticValues.andrTempWithWidth = andrTempWithWidth;
	}

	public static String getAndroidTemp() {
		return androidTemp;
	}

	public static void setAndroidTemp(String androidTemp) {
		StaticValues.androidTemp = androidTemp;
	}

	public static String getNormalAndroidTemp() {
		return normalAndroidTemp;
	}
	
	public static void setNormalAndroidTemp(String normalAndroidTemp) {
		StaticValues.normalAndroidTemp = normalAndroidTemp;
	}

	public static String getSceneAbsTmpUrl() {
		return sceneAbsTmpUrl;
	}

	public static void setSceneAbsTmpUrl(String sceneAbsTmpUrl) {
		StaticValues.sceneAbsTmpUrl = sceneAbsTmpUrl;
	}

	public static String getSceneVedioTmpUrl() {
		return sceneVedioTmpUrl;
	}

	public static void setSceneVedioTmpUrl(String sceneVedioTmpUrl) {
		StaticValues.sceneVedioTmpUrl = sceneVedioTmpUrl;
	}

	public static String getSceneAudioTmpUrl() {
		return sceneAudioTmpUrl;
	}

	public static void setSceneAudioTmpUrl(String sceneAudioTmpUrl) {
		StaticValues.sceneAudioTmpUrl = sceneAudioTmpUrl;
	}

	public static String getAndrVedioTmpUrl() {
		return andrVedioTmpUrl;
	}

	public static void setAndrVedioTmpUrl(String andrVedioTmpUrl) {
		StaticValues.andrVedioTmpUrl = andrVedioTmpUrl;
	}

	public static String getArtVedioTmpUrl() {
		return artVedioTmpUrl;
	}

	public static void setArtVedioTmpUrl(String artVedioTmpUrl) {
		StaticValues.artVedioTmpUrl = artVedioTmpUrl;
	}

	public static String getArtAbsTmpUrl() {
		return artAbsTmpUrl;
	}

	public static void setArtAbsTmpUrl(String artAbsTmpUrl) {
		StaticValues.artAbsTmpUrl = artAbsTmpUrl;
	} 
}
