package com.hanweb.jmp.pack.entity;

import java.io.Serializable;
import java.util.Date;

import com.hanweb.common.annotation.Column;
import com.hanweb.common.annotation.ColumnType;
import com.hanweb.common.annotation.Id;
import com.hanweb.common.annotation.Table;

import com.hanweb.jmp.constant.Tables;

/**
 *一键打包应用实体
 */
@Table(name = Tables.APPLICATION)
public class App implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8020206996027596982L;

	/**
	 * 主键
	 */
	@Id
	@Column(type = ColumnType.INT, length = 11)
	private Integer iid;

	/**
	 * 应用名称
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String name;

	/**
	 * 网站ID,与用户表iid做关联
	 */
	@Column(type = ColumnType.INT, length = 11, update = false)
	private Integer siteId;
	
	
	/**
	 * 应用图标路径
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String logoPath;
	
	/**
	 * 应用说明
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String spec;
	
	/**
	 * logo图标路径
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String logoIcon;
	/**
	 * 开机图标路径
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String splashIcon;
	
	/**
	 * 创建时间
	 */
	@Column(type = ColumnType.DATETIME, update = false)
	private Date createTime;
	
	/**
	 * 应用唯一标识码
	 */
	@Column(type = ColumnType.VARCHAR, update = false)
	private String uuid;
	/**
	 * 应用创建人
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String creater;
	
	/**
	 * 用户id
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer userId;
	
	/**
	 * 二维码路径
	 */
	@Column(type = ColumnType.VARCHAR, length = 255)
	private String  qrcodePath;
	
	/**
	 * 界面风格
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer modelType; 
	
	/**
	 * 酷图组件
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer kuTu;
	
	/**
	 * 地图组件
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer map;
	
	/**
	 *街景组件 
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer streetView;
	  
	
	/**
	 * 微信卡片式组件
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer card;
	
	/**
	 * 左侧一张图组件
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer leftpic;

	/**
	 * 图文混排组件
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer picmix;
	
	/**
	 * 标题+时间+来源组件
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer titlemix;
	
	/**
	 * 标题+时间+摘要组件
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer titlemix1;
	
	/**
	 * 标题+时间+评论数组件
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer titlemix2;
	
	/**
	 * 图片新闻组件
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer picinfo; 
	
	/**
	 * 视频组件
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer video;
	
	/**
	 * 智能公交组件
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer intelbus;
	
	/**
	 * 报料组件
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer baoliao;
	
	/**
	 * 天气组件
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer weather;
	
	/**
	 * 通讯录
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer numsense;
	
	/**
	 * 阅读
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer ebook; 
	
	/**
	 * 瀑布流
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer waterflow;
	
	/**
	 * 场景式栏目
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer scene;
	
	/**
	 * 打折商城
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer ecommerce;
	
	/**
	 * 全局样式
	 */
	@Column(type = ColumnType.INT, length = 11)
	private Integer overall=1;
	
	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getSplashIcon() {
		return splashIcon;
	}

	public void setSplashIcon(String splashIcon) {
		this.splashIcon = splashIcon;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getQrcodePath() {
		return qrcodePath;
	}

	public void setQrcodePath(String qrcodePath) {
		this.qrcodePath = qrcodePath;
	}

	public Integer getModelType() {
		return modelType;
	}

	public void setModelType(Integer modelType) {
		this.modelType = modelType;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Integer getWeather() {
		return weather;
	}

	public void setWeather(Integer weather) {
		this.weather = weather;
	}

	public Integer getVideo() {
		return video;
	}

	public void setVideo(Integer video) {
		this.video = video;
	}

	public Integer getKuTu() {
		return kuTu;
	}

	public void setKuTu(Integer kuTu) {
		this.kuTu = kuTu;
	}

	public Integer getMap() {
		return map;
	}

	public void setMap(Integer map) {
		this.map = map;
	}

	public Integer getStreetView() {
		return streetView;
	}

	public void setStreetView(Integer streetView) {
		this.streetView = streetView;
	} 

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLogoIcon() {
		return logoIcon;
	}

	public void setLogoIcon(String logoIcon) {
		this.logoIcon = logoIcon;
	} 

	public Integer getCard() {
		return card;
	}

	public void setCard(Integer card) {
		this.card = card;
	}

	public Integer getLeftpic() {
		return leftpic;
	}

	public void setLeftpic(Integer leftpic) {
		this.leftpic = leftpic;
	}

	public Integer getPicmix() {
		return picmix;
	}

	public void setPicmix(Integer picmix) {
		this.picmix = picmix;
	}

	public Integer getTitlemix() {
		return titlemix;
	}

	public void setTitlemix(Integer titlemix) {
		this.titlemix = titlemix;
	} 

	public Integer getTitlemix1() {
		return titlemix1;
	}

	public void setTitlemix1(Integer titlemix1) {
		this.titlemix1 = titlemix1;
	}

	public Integer getTitlemix2() {
		return titlemix2;
	}

	public void setTitlemix2(Integer titlemix2) {
		this.titlemix2 = titlemix2;
	}

	public Integer getPicinfo() {
		return picinfo;
	}

	public void setPicinfo(Integer picinfo) {
		this.picinfo = picinfo;
	} 

	public Integer getIntelbus() {
		return intelbus;
	}

	public void setIntelbus(Integer intelbus) {
		this.intelbus = intelbus;
	}

	public Integer getBaoliao() {
		return baoliao;
	}

	public void setBaoliao(Integer baoliao) {
		this.baoliao = baoliao;
	}

	public Integer getNumsense() {
		return numsense;
	}

	public void setNumsense(Integer numsense) {
		this.numsense = numsense;
	}

	public Integer getEbook() {
		return ebook;
	}

	public void setEbook(Integer ebook) {
		this.ebook = ebook;
	} 

	public Integer getWaterflow() {
		return waterflow;
	}

	public void setWaterflow(Integer waterflow) {
		this.waterflow = waterflow;
	}

	public Integer getScene() {
		return scene;
	}

	public void setScene(Integer scene) {
		this.scene = scene;
	}

	public Integer getEcommerce() {
		return ecommerce;
	}

	public void setEcommerce(Integer ecommerce) {
		this.ecommerce = ecommerce;
	}

	public Integer getOverall() {
		return overall;
	}

	public void setOverall(Integer overall) {
		this.overall = overall;
	}
	
}