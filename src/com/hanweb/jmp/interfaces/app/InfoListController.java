package com.hanweb.jmp.interfaces.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;

import com.hanweb.interfaces.service.InfoInterfaceService;
import com.hanweb.jmp.annotation.InterfaceCache;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.cms.entity.sign.Sign;
import com.hanweb.jmp.cms.service.channels.ChannelService;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.cms.service.infos.InfoService;
import com.hanweb.jmp.cms.service.sign.SignService;
import com.hanweb.jmp.constant.Configs;
import com.hanweb.jmp.constant.InterfaceLogConfig; 
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.sites.SiteService;
import com.hanweb.jmp.util.HttpClientUtil;

@Controller
@RequestMapping("interfaces")
public class InfoListController {
	
	/**
	 * infoService
	 */
	@Autowired
	InfoService infoService;
	
	/**
	 * colService
	 */
	@Autowired
	ColService colService;
	
	/**
	 * siteService
	 */
	@Autowired
	SiteService siteService;
	
	/**
	 * channelService
	 */
	@Autowired
	ChannelService channelService;
	
	/**
	 * signService
	 */
	@Autowired
	SignService signService;
	
	/**
	 * channelService
	 */
	@Autowired
	InfoInterfaceService infoInterfaceService;
	
	/**
	 * 1.4	信息列表接口
	 * @param resourceid 栏目id
	 * 3.1.12修改：当resourceid个数为一时，返回单个栏目及信息，
	 * 当resourceid为多个时，返回多个栏目和信息
	 * @param page  分页条数
	 * @param topid 置顶id 
	 * @param orderid  排序id
	 * @param time  按时间排序，刷新和更多分别给出最新信息和最老信息时间戳19位
	 * @param ordertype  排序类型1=orderid 2=时间排序
	 * @param flag 信息变动标记位
	 * @param type 列表操作1=刷新  2=更多
	 * @param isbanner 是否需要banner图 1=banner图栏目  0=普通栏目
	 * @return String
	 */
	@RequestMapping("infolist")
	@ResponseBody
	@InterfaceCache
	public String infoList(String resourceid, Integer page, Integer topid, Integer orderid, Long time, Integer ordertype, 
			               Integer flag, Integer type, Integer isbanner){
		System.out.println("进入信息列表页");
		System.out.println("page:" + page);
		System.out.println("ordertype:" + ordertype);
		System.out.println("type:" + type);
		page = NumberUtil.getInt(page, 10);
		topid = NumberUtil.getInt(topid);
		System.out.println("topid:" + topid);
		//orderid = NumberUtil.getInt(orderid);
		//time = NumberUtil.getLong(time);
		ordertype = NumberUtil.getInt(ordertype, 1);
		type = NumberUtil.getInt(type, 1);
		isbanner=NumberUtil.getInt(isbanner, 0);
		List<Col> colList = colService.findByIds(resourceid);
		if(colList == null){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_INFOLIST, InterfaceLogConfig.ERROR_13);  
		}
		if(colList.size() == 1){
			Col col = colList.get(0);
			List<Info> infoList = null;
			if(col == null){
				return "";
			}
			if(type != 2 && flag != null && (NumberUtil.getInt(col.getFlag()) != NumberUtil.getInt(flag))){
				type = 1;
				orderid = null;
				time = null;
				topid = null;
			}
			Date date = null;
			if(time != null){
				date = new Date();
				date.setTime(NumberUtil.getLong(time));
			}
			if(ordertype == 1 && type == 1){//orderid 刷新
				System.out.println("123");
				infoList = infoService.findGTOrderid(col, orderid, topid, 0, page+1, col.getSiteId(),  isbanner);
			}else if(ordertype == 1 && type == 2){//orderid 更多
				System.out.println("456");
				System.out.println("topid:" + topid);
				infoList = infoService.findLTOrderid(col, orderid, topid, 0, page+1, col.getSiteId(),  isbanner);
				System.out.println("infoList:" + infoList.toString());
			}else if(ordertype == 2 && type == 1){//时间排序 刷新
				System.out.println("789");
				infoList = infoService.findGTTime(col, date, topid, 0, page+1, col.getSiteId(),  isbanner);
			}else if(ordertype == 2 && type == 2){//时间排序 更多
				System.out.println("100");
				infoList = infoService.findLTTime(col, date, topid, 0, page+1, col.getSiteId(),  isbanner);
			}
			if(infoList == null){
				System.out.println("哈哈哈哈哈哈");
				return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_INFOLIST, InterfaceLogConfig.ERROR_13);  
			}
			System.out.println("infoListSize:" + infoList.size());
			System.out.println("信息：" + infoInterfaceService.listToJson(infoList, page, col, true, 0));
			return infoInterfaceService.listToJson(infoList, page, col, true, 0);
		}else{
			return infoInterfaceService.listInfoToJson( page, colList, true, 0,flag,orderid,time,ordertype,topid,isbanner,type);
		}
		
	}
	
	/**
	 * 1.5	消息列表接口
	 * @param page 每页请求数量
	 * @param sendtime 按时间排序，刷新和更多分别给出最新信息和最老信息时间戳19位
	 * @param flag 变动标记位
	 * @param type 列表操作1=刷新 2=更多
	 * @param siteid 网站id
	 * @return String
	 */
	@RequestMapping("pushinfolist")
	@ResponseBody
	@InterfaceCache
	public String pushInfoList(Integer page, Long sendtime, Integer flag, Integer type, Integer siteid){
		page = NumberUtil.getInt(page, 10);
		sendtime = NumberUtil.getLong(sendtime);
		type = NumberUtil.getInt(type);
		siteid = NumberUtil.getInt(siteid);
		int sitePushFlag = siteService.findPushFlagById(siteid);
		if(flag != null && sitePushFlag != NumberUtil.getInt(flag)){
			type = 1;
			sendtime = Long.valueOf(0);
		}
		Col col = new Col();
		col.setIid(-1);		//客户端要求随便放个 
		col.setFlag(sitePushFlag);
		List<Info> infoList = null;
		if(NumberUtil.getInt(page) == 0){
			page = 20;
		}
		Date date = new Date();
		date.setTime(NumberUtil.getLong(sendtime));
		if(type == 1){
			infoList = infoService.findPushInfoGTtime(date, 0, page+1, siteid);
		}else if(type == 2){
			infoList = infoService.findPushInfoLTtime(date, 0, page+1, siteid);
		}
		if(infoList == null){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_PUSHINFOLIST, 
					InterfaceLogConfig.ERROR_13);  
		}
		return infoInterfaceService.listToJson(infoList, page, col, false, 0);
	}
	
	/**
	 * 1.6	全文检索接口
	 * @param resourceid 栏目id，不传即为检索全网站
	 * @param num 需要条数
	 * @param keyword 关键字
	 * @param page 第几页
	 * @param siteid siteid
	 * @param type 0：普通检索 1：高级检索
	 * @return String
	 */
	@RequestMapping("searchinfolist")
	@ResponseBody
	@InterfaceCache
	public String searchInfoList(Integer resourceid, Integer num, String keyword, Integer page, Integer siteid, Integer type){
		resourceid = NumberUtil.getInt(resourceid);
		num = NumberUtil.getInt(num);
		page = NumberUtil.getInt(page, 10);
		keyword = StringUtil.getString(keyword);
		siteid = NumberUtil.getInt(siteid);
		type = NumberUtil.getInt(type);
		if(NumberUtil.getInt(siteid)<=0){ 
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_SEARCHINFOLIST, 
					InterfaceLogConfig.ERROR_03); 
		}
		List<Info> infoList = new ArrayList<Info>();
		if(type == 1){
			Site site = siteService.findByIid(siteid);
			String jsearchUrl = Configs.getConfigs().getJsearchUrl() + "/interface/search.do?q="
				              + keyword + "&criteria_colid=" + resourceid 
				              + "&p=" + page + "&pg=" + num + "&webid="
			                  + StringUtil.getString(site.getSearchWebId());
			String html = HttpClientUtil.getInfo(jsearchUrl, "UTF-8");
			JsearchResult jsearchResult = JsonUtil.StringToObject(html, JsearchResult.class);
			if(jsearchResult != null){
				List<JsearchIteams> itemList = jsearchResult.getItems();
				if(itemList != null){
					for(JsearchIteams item : itemList){
						infoList.add(item.getData());
					}
				}
			}
		}else{
			infoList = infoService.findInfoByColid(resourceid, num, page, keyword, siteid);
		}
		if(infoList == null){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_SEARCHINFOLIST, 
					InterfaceLogConfig.ERROR_13);  
		}
		return infoInterfaceService.listToJson(infoList, num, new Col(), false, 0);
	}
	
	/**
	 * 1.4	多角标信息列表接口
	 * @param siteid 网站id
	 * @param resourceid 栏目id
	 * @param flag 变动标记位
	 * @param orderid 卡片的orderid
	 * @param type 1=刷新 2=更多(通过 ordered 进行刷新和更多)
	 * @param page 每次请求卡片的个数
	 * @return String
	 */
	@RequestMapping("cardinfolist")
	@ResponseBody
	@InterfaceCache
	public String cardInfoList(Integer siteid, Integer resourceid, Integer flag, Integer orderid, Integer type, Integer page){
		siteid = NumberUtil.getInt(siteid);
		resourceid = NumberUtil.getInt(resourceid);
		flag = NumberUtil.getInt(flag);
		orderid = NumberUtil.getInt(orderid);
		type = NumberUtil.getInt(type);
		page = NumberUtil.getInt(page, 2);
		Site site = siteService.findByIid(NumberUtil.getInt(siteid));
		Map<String, Object> result = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> tags = new ArrayList<HashMap<String, Object>>();
		Col col = colService.findByIid(resourceid);
		if(col == null || col.getIid() <= 0){ 
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_CARDINFOLIST, 
					InterfaceLogConfig.ERROR_03);  
		}
		result.put("flag", NumberUtil.getInt(col.getFlag()) + NumberUtil.getInt(site.getCardDimensionFlag()) + "");
		result.put("columnstatus", NumberUtil.getInt(col.getEnable()) + "");
		result.put("resname", StringUtil.getString(col.getName()));
		result.put("resourceid", resourceid + "");
		result.put("infonum", StringUtil.getString(col.getInfoNum()));
		result.put("tag", tags);
		List<Sign> list = signService.findByColId(NumberUtil.getInt(resourceid), 1
				, NumberUtil.getInt(siteid), orderid, NumberUtil.getInt(type)
				, NumberUtil.getInt(flag) != col.getFlag());
		if(list != null && list.size() > 0){
			long time=0;
			int pageSize=0;
			for(int i = 0; i < list.size() && i < page; i++){
				Sign d = list.get(i);
				HashMap<String, Object> tag = new HashMap<String, Object>();
				tag.put("tagid", NumberUtil.getInt(d.getIid()) + "");
				tag.put("tagname", StringUtil.getString(d.getDname()));
				if(d.getCreateTime() == null){
					time = new Date().getTime();
				}else{
					time = d.getCreateTime().getTime();
				}
				tag.put("time", time);
				tag.put("orderid", NumberUtil.getInt(d.getOrderId()) + "");
				tag.put("type", NumberUtil.getInt(d.getCardType()) + "");
				tag.put("showtype", NumberUtil.getInt(d.getShowType()) + "");
				if((NumberUtil.getInt(d.getCardType()) + 4) == 6){
					pageSize = 100;
				}else{
					pageSize = NumberUtil.getInt(d.getCardType()) + 4;
				}
				List<Info> infos = infoService.findCardInfoByColIdAndSignId(
						NumberUtil.getInt(d.getIid()), NumberUtil.getInt(resourceid)
						,  pageSize, NumberUtil.getInt(siteid));
				List<Map<String, String>> resourcetitle = new ArrayList<Map<String, String>>();
				if(infos != null && infos.size() > 0){
					resourcetitle = infoInterfaceService.infoList(infos, page, col, true, 0);
				}
				tag.put("resourcetitle", resourcetitle);
				tags.add(tag);
			}
		}
		return JsonUtil.objectToString(result);
	}
	
}