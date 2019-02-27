package com.hanweb.jmp.constant;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanweb.jmp.cms.service.cols.ColFieldService;

public class TypeConfig {
	
	/**
	 * colFieldService
	 */
	@Autowired
	private ColFieldService colFieldService;
	
	/**
	 * 获取信息列表类型
	 * @param siteId  网站id
	 * @return   Map<Integer, String>
	 */
	public  Map<Integer, String> getInfoListType(Integer siteId){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "纯标题");
		map.put(2, "标题+时间+来源"); 
		map.put(4, "左侧一张图片");
		map.put(5, "右侧一张图片");
		map.put(6, "三张图文混排");
		map.put(7, "一张大图+标题格式");
		map.put(8, "纯图左一+右二");
		map.put(9, "纯图左二+右一");
		map.put(10, "标题+左侧图+评论数");
		map.put(11, "视频大图+标题格式");
		map.put(12, "标题+左侧视频图");
		map.put(13, "标题+时间+图标");
		map.put(14, "左图+标题+音频");
		map.putAll(colFieldService.findByType(3, siteId));
		return map;
	}
	
	/**
	 * 获取信息内容类型
	 * @param siteId 网站id
	 * @return  Map<Integer, String>
	 */
	public Map<Integer, String> getInfoContentType(Integer siteId){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "经典正文页");
		map.put(2, "斗状来源文章页");
		map.put(3, "创意文章页");
		map.put(4, "酷图文章页");
		map.put(5, "外链展现");
		map.put(6, "视频直接打开");
		map.put(7, "导航");
		map.put(8, "专题(栏目)");
		map.put(9, "专题(频道)");
		map.put(10, "专题(调查)");

		map.putAll(colFieldService.findByType(4, siteId));
		return map;
	}
	
	/**
	 * 获取栏目类型
	 * @param siteId  网站id
	 * @return  Map<Integer, String>
	 */
	public  Map<Integer, String> getColHdType(Integer siteId){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "领导信箱");
		map.put(2, "意见征集");
		map.put(3, "网上调查");
		map.put(4, "第三方应用");
		map.put(5, "天气预报");
		map.put(6, "报料");
		map.put(7, "微博类");
		map.put(8, "公交查询");
		map.put(9, "打折");
		map.put(10, "阅读");
		map.put(11, "通讯录");
		map.put(12, "在线访谈");
//		Map<Integer, String> hudongAdd 
//			= JsonUtil.StringToObject(Configs.getConfigs().getHudongtype(), Map.class);
//		putAll(hudongAdd);
		map.putAll(colFieldService.findByType(5, siteId));
		return map;
	}
	
	/**
	 * 获取栏目展现类型
	 * @param siteId  网站id
	 * @return   Map<Integer, String>
	 */
	public Map<Integer, String> getColCommonType(Integer siteId){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "无banner普通列表栏目");
		map.put(2, "有banner普通列表栏目");
		map.put(3, "单信息栏目");
		map.put(4, "瀑布流");
		map.put(5, "图片新闻");
		map.put(6, "多图文");
		map.put(7, "无缩放banner");
		map.put(8, "有缩放banner");
		map.put(9, "通知公告");
		map.putAll(colFieldService.findByType(2, siteId));
		return map;
	}
	
	/**
	 * 获取栏目列表类型
	 * @param siteId  网站id
	 * @return  public Map<Integer, String>
	 */
	public Map<Integer, String> getColListType(Integer siteId){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "子栏目纵向平铺");
		map.put(2, "子栏目分类和分类下列表展现");
		map.put(3, "卡片式");
		map.putAll(colFieldService.findByType(1, siteId));
		return map;
	}
	
}