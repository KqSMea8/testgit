package com.hanweb.jmp.constant;

import com.hanweb.common.util.SpringUtil;

public class Configs {
	
	private String htpUrl;
	/**
	 * 微门户系统域名
	 */
	private String jmpUrl;
	/**
	 * jget接口地址
	 */
	private String jgetUrl;
	/**
	 * 
	 */
	private String jgetLogId;

	/**
	 * jget登录密码
	 */
	private String jgetPassword;

	/**
	 * jsearch服务器地址
	 */
	private String jsearchUrl;

	/**
	 * 一键打包(1-开启，0-关闭)
	 */
	private String canAppFactory;
	/**
	 * 信息同步周期（分钟）
	 */
	private String synPeroid;
	/**
	 * 信息保留方式(0-按信息数保留,1-按时间保留)
	 */
	private Integer infoSaveType;

	/**
	 * 
	 */
	private Integer infoSaveDays;

	/**
	 * 信息保留条数
	 */
	private Integer infoSaveCounts;
	
	/**
	 * 离线下载间隔时间
	 */
	private Integer intervalTime;
	
	/**
	 * 最新信息时间
	 */
	private String synLastTime;
	/**
	 * 同步时一次取信息条数(用于信息同步)
	 */
	private String synInfoNum;

	/**
	 * 访问控制1：打开 0：关闭
	 */
	private String accessControl;
	
	/**
	 * areaFlag
	 */
	private Integer areaFlag;
	
	/**
	 * 天气预报时间
	 */
	private String weathertime;

	
	/**
	 * 图片文件格式配置
	 */
	private String picFileType = "jpg,gif,png";
	
	/**
	 * 图片文件大小 单位为MB
	 */
	private Integer picFileSize = 1;

	/**
	 * 音频文件格式配置
	 */
	private String audioFileType = "mp3,amr";
	
	/**
	 * 音频文件大小
	 */
	private Integer audioFileSize = 10;

	/**
	 * 视频文件格式配置
	 */
	private String videoFileType = "mp4,rmvb,avi";
	
	/**
	 * 视频文件大小
	 */
	private Integer videoFileSize = 20;
	
	/**
	 * 文件格式配置
	 */
	private String fileType = ""; 
	
	/**
	 * 天气预报接口地址
	 */
	private String weatherurl="";
	
	/**
	 * 附件文件类型
	 */
	private String docFileType ="docx,html,txt,pdf,doc,zip,rar,xls,ppt";
	
	/**
	 * 附件文件大小
	 */
	private Integer fileSize = 10;
	
	/**
	 * 时间格式
	 */
	private String timeFormat=""; 
	
	/**
	 * 向iOS设备推送时必填，1表示推送生产环境；2表示推送开发环境。本字段对Android平台无效
	 */
	private Integer iosEnvironment=2;
	
	/**
	 * 服务端接口缓存是否开启   1：开启   0：不开启
	 */
	private Integer interfaceCache;
	
	/**
	 * 短信接口地址
	 */
	private String messageUrl;
	
	/**
	 * 发送短信的内容    {code}代表手机验证码
	 */
	private String messageContent;
	
	/**
	 * 运维平台允许访问的IP
	 */
	private String allowIp = "";
	
	/**
	 * 互动栏目附加类型json
	 */
	private String hudongtype = "";
	

	
	
	/**
	 * 微信分享接口
	 */
	private String weixinUrl ="";
	
	/**
	 * 天气服务器
	 */
	private String weatherServerUrl="http://www.pertool.com/jmportalwb";
	
	
	/**
	 * serviceCode
	 */
	private String serviceCode = "";
	
	/**
	 * key
	 */
	private String key = "";
	
	/**
	 * wxSupport
	 */
	private Integer wxSupport;
	
	/**
	 * smtp
	 */
	private String smtp = "";
	
	/**
	 * eport
	 */
	private String eport = "";
	
	/**
	 * emailAccount
	 */
	private String emailAccount = "";
	
	/**
	 * emailPwd
	 */
	private String emailPwd = "";
	
	/**
	 * emailName
	 */
	private String emailName = "";
	
	/**
	 * emailContent
	 */
	private String emailContent = "";
	
	/**
	 * jcms系统域名
	 */
	private String jcmsUrl;
	/**
	 * jcms用户名
	 */
	private String jcmsLogId;
	/**
	 *jcms密码
	 */
	private String jcmsPassword;
	 
	/**
	 * 微门户栏目id
	 */
	private Integer jmpcolId = 0;
	
	/**
	 * jcms栏目id
	 */
	private Integer jcmscolId = 0;
	
	
	/****************************素材部分************************************/
	
	
    /**
	 * 附件文件大小 单位为MB
	 */
	private Integer docFileSize = 5;
	
	/****************************自助建站部分************************************/
	//android apk文件url
	private String apkUrl ="";
	
	//iphone ipa文件url
	private String iosUrl ="";
	
	//网站一键打包类型 0:自动打包   1：后台上传
	private Integer createAppType = 0;
	
	//网站是否支持一键打包功能
	private Integer isAutoCreateApp = 0;
	
	//一键打包最大数量
	private Integer createAppNum = 1;
	
	
	public String getMessageUrl() {
		return messageUrl;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public String getIosUrl() {
		return iosUrl;
	}

	public String getWeixinUrl() {
		return weixinUrl;
	}

	public void setWeixinUrl(String weixinUrl) {
		this.weixinUrl = weixinUrl;
	}

	public void setIosUrl(String iosUrl) {
		this.iosUrl = iosUrl;
	}

	public void setMessageUrl(String messageUrl) {
		this.messageUrl = messageUrl;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Integer getInterfaceCache() {
		return interfaceCache;
	}

	public void setInterfaceCache(Integer interfaceCache) {
		this.interfaceCache = interfaceCache;
	} 

	public String getWeathertime() {
		return weathertime;
	}

	public void setWeathertime(String weathertime) {
		this.weathertime = weathertime;
	}

	public static Configs getConfigs() {
		return SpringUtil.getBean("jmp_Configs", Configs.class);
	}

	/**
	 * 
	 * getJmpUrl:(这里用一句话描述这个方法的作用).
	 *
	 * @return    设定参数 .
	 */
	public String getJmpUrl() {
		if(!jmpUrl.endsWith("/")){
			jmpUrl=jmpUrl+"/";
		}
		return jmpUrl;
	}

	public void setJmpUrl(String jmpUrl) {
		this.jmpUrl = jmpUrl;
	}

	public String getJgetUrl() {
		return jgetUrl;
	}

	public void setJgetUrl(String jgetUrl) {
		this.jgetUrl = jgetUrl;
	}

	public String getJgetLogId() {
		return jgetLogId;
	}

	public void setJgetLogId(String jgetLogId) {
		this.jgetLogId = jgetLogId;
	}

	public String getJgetPassword() {
		return jgetPassword;
	}

	public void setJgetPassword(String jgetPassword) {
		this.jgetPassword = jgetPassword;
	}

	public String getJsearchUrl() {
		return jsearchUrl;
	}

	public void setJsearchUrl(String jsearchUrl) {
		this.jsearchUrl = jsearchUrl;
	}

	public String getCanAppFactory() {
		return canAppFactory;
	}

	public void setCanAppFactory(String canAppFactory) {
		this.canAppFactory = canAppFactory;
	}

	public String getSynPeroid() {
		return synPeroid;
	}

	public void setSynPeroid(String synPeroid) {
		this.synPeroid = synPeroid;
	}

	public String getSynLastTime() {
		return synLastTime;
	}

	public void setSynLastTime(String synLastTime) {
		this.synLastTime = synLastTime;
	}

	public String getSynInfoNum() {
		return synInfoNum;
	}

	public void setSynInfoNum(String synInfoNum) {
		this.synInfoNum = synInfoNum;
	}

	public Integer getInfoSaveType() {
		return infoSaveType;
	}

	public void setInfoSaveType(Integer infoSaveType) {
		this.infoSaveType = infoSaveType;
	}

	public Integer getInfoSaveDays() {
		return infoSaveDays;
	}

	public void setInfoSaveDays(Integer infoSaveDays) {
		this.infoSaveDays = infoSaveDays;
	}

	public Integer getInfoSaveCounts() {
		return infoSaveCounts;
	}

	public void setInfoSaveCounts(Integer infoSaveCounts) {
		this.infoSaveCounts = infoSaveCounts;
	}

	public String getAccessControl() {
		return accessControl;
	}

	public void setAccessControl(String accessControl) {
		this.accessControl = accessControl;
	}

	public Integer getAreaFlag() {
		return areaFlag;
	}

	public void setAreaFlag(Integer areaFlag) {
		this.areaFlag = areaFlag;
	}
 

	public String getPicFileType() {
		return picFileType;
	}

	public void setPicFileType(String picFileType) {
		this.picFileType = picFileType;
	}

	public String getAudioFileType() {
		return audioFileType;
	}

	public void setAudioFileType(String audioFileType) {
		this.audioFileType = audioFileType;
	}

	public String getVideoFileType() {
		return videoFileType;
	}

	public void setVideoFileType(String videoFileType) {
		this.videoFileType = videoFileType;
	}

	public Integer getPicFileSize() {
		return picFileSize;
	}

	public void setPicFileSize(Integer picFileSize) {
		this.picFileSize = picFileSize;
	}
 
	public Integer getAudioFileSize() {
		return audioFileSize;
	}

	public void setAudioFileSize(Integer audioFileSize) {
		this.audioFileSize = audioFileSize;
	}
 

	public Integer getVideoFileSize() {
		return videoFileSize;
	}

	public void setVideoFileSize(Integer videoFileSize) {
		this.videoFileSize = videoFileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getWeatherurl() {
		return weatherurl;
	}

	public void setWeatherurl(String weatherurl) {
		this.weatherurl = weatherurl;
	}

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public Integer getIosEnvironment() {
		return iosEnvironment;
	}

	public void setIosEnvironment(Integer iosEnvironment) {
		this.iosEnvironment = iosEnvironment;
	}

	public String getAllowIp() {
		return allowIp;
	}

	public void setAllowIp(String allowIp) {
		this.allowIp = allowIp;
	}

	public String getHudongtype() {
		return hudongtype;
	}

	public void setHudongtype(String hudongtype) {
		this.hudongtype = hudongtype;
	}

	public Integer getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}

	public Integer getIsAutoCreateApp() {
		return isAutoCreateApp;
	}

	public void setIsAutoCreateApp(Integer isAutoCreateApp) {
		this.isAutoCreateApp = isAutoCreateApp;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getWxSupport() {
		return wxSupport;
	}

	public void setWxSupport(Integer wxSupport) {
		this.wxSupport = wxSupport;
	}


	public String getSmtp() {
		return smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	public String getEport() {
		return eport;
	}

	public void setEport(String eport) {
		this.eport = eport;
	}

	public String getEmailAccount() {
		return emailAccount;
	}

	public void setEmailAccount(String emailAccount) {
		this.emailAccount = emailAccount;
	}

	public String getEmailPwd() {
		return emailPwd;
	}

	public void setEmailPwd(String emailPwd) {
		this.emailPwd = emailPwd;
	}

	public String getEmailName() {
		return emailName;
	}

	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public Integer getCreateAppNum() {
		return createAppNum;
	}

	public void setCreateAppNum(Integer createAppNum) {
		this.createAppNum = createAppNum;
	}

	public Integer getCreateAppType() {
		return createAppType;
	}

	public void setCreateAppType(Integer createAppType) {
		this.createAppType = createAppType;
	}

	public String getJcmsUrl() {
		return jcmsUrl;
	}

	public void setJcmsUrl(String jcmsUrl) {
		this.jcmsUrl = jcmsUrl;
	}

	public String getJcmsLogId() {
		return jcmsLogId;
	}

	public void setJcmsLogId(String jcmsLogId) {
		this.jcmsLogId = jcmsLogId;
	}

	public String getJcmsPassword() {
		return jcmsPassword;
	}

	public void setJcmsPassword(String jcmsPassword) {
		this.jcmsPassword = jcmsPassword;
	}

	public Integer getJmpcolId() {
		return jmpcolId;
	}

	public void setJmpcolId(Integer jmpcolId) {
		this.jmpcolId = jmpcolId;
	}

	public Integer getJcmscolId() {
		return jcmscolId;
	}

	public void setJcmscolId(Integer jcmscolId) {
		this.jcmscolId = jcmscolId;
	}

	public void setWeatherServerUrl(String weatherServerUrl) {
		this.weatherServerUrl = weatherServerUrl;
	}

	public String getWeatherServerUrl() {
		return weatherServerUrl;
	}

	public void setDocFileType(String docFileType) {
		this.docFileType = docFileType;
	}

	public String getDocFileType() {
		return docFileType;
	}

	public void setDocFileSize(Integer docFileSize) {
		this.docFileSize = docFileSize;
	}

	public Integer getDocFileSize() {
		return docFileSize;
	}

	public String getHtpUrl() {
		return htpUrl;
	}

	public void setHtpUrl(String htpUrl) {
		this.htpUrl = htpUrl;
	}

	
}
