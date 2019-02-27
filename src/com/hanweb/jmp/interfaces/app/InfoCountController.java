package com.hanweb.jmp.interfaces.app;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil; 

import com.hanweb.jmp.cms.entity.infos.InfoCount;
import com.hanweb.jmp.cms.service.infos.InfoCountService;
import com.hanweb.jmp.constant.InterfaceLogConfig;

@Controller
@RequestMapping("interfaces")
public class InfoCountController {
	
	/**
	 * infoCountService
	 */
	@Autowired
	private InfoCountService infoCountService;

	/**
	 * 2.3  阅读/评论/点赞数接口
	 * @param siteid 网站id
	 * @param uuid 手机uuid
	 * @param titleid 信息id
	 * @param type 类型  1：文章   2：报料
	 * @return 数目
	 */
	@RequestMapping("infocount")
	@ResponseBody
	public String infoCount(Integer siteid, String uuid, Integer titleid, Integer type){
		type = NumberUtil.getInt(type, 1);
		HashMap<String, Object> ret = new HashMap<String, Object>();
		if(NumberUtil.getInt(titleid) == 0){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_INFOCOUNT, 
					InterfaceLogConfig.ERROR_03); 
		}
		InfoCount infoCount = infoCountService.findByInfoId(titleid, type, uuid, siteid);
		try {
			ret.put("visitnum", "" + infoCount.getVisitCount());
			ret.put("commentnum", "" + infoCount.getCommentCount());
			ret.put("goodnum", "" + infoCount.getGoodCount());
			if(infoCount.getIsGood() > 0){
				ret.put("isgood", "" + true);
			}else{
				ret.put("isgood", "" + false);
			}
			
		} catch (Exception e) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_INFOCOUNT, 
					InterfaceLogConfig.ERROR_08); 
		}
		return JsonUtil.objectToString(ret);
	}
	
}