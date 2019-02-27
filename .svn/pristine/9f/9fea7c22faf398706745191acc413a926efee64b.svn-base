package com.hanweb.jmp.interfaces.app;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil; 

import com.hanweb.jmp.cms.service.infos.InfoCountService;
import com.hanweb.jmp.constant.InterfaceLogConfig;
import com.hanweb.jmp.global.entity.GoodRecord;
import com.hanweb.jmp.global.service.GoodRecordService;
import com.hanweb.jmp.sys.entity.parameter.Parameter;
import com.hanweb.jmp.sys.service.parameter.ParameterService;

@Controller
@RequestMapping("interfaces")
public class GoodController {
	
	/**
	 * parameterService
	 */
	@Autowired
	private ParameterService parameterService;

	/**
	 * goodRecordService
	 */
	@Autowired
	private GoodRecordService goodRecordService;
	
	/**
	 * infoCountService
	 */
	@Autowired
	private InfoCountService infoCountService;
	
	/**
	 * 2.4   点赞提交接口
	 * @param siteid 网站id
	 * @param uuid 手机uuid
	 * @param titleid 信息id（报料id、评论id）
	 * @param resourceid 栏目id
	 * @param type 点赞类型   1：文章(默认值) 2:报料   3:评论
	 * @return 返回值
	 */
	@RequestMapping("goodadd")
	@ResponseBody
	public String addComment(Integer siteid, String uuid, Integer titleid, Integer resourceid, Integer type){
		type = NumberUtil.getInt(type, 1);
		HashMap<String, Object> ret = new HashMap<String, Object>();
		Parameter parameter = parameterService.findBySiteId(siteid);
		if(parameter == null){ 
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_GOODADD, 
					InterfaceLogConfig.ERROR_07); 
		}
		if(NumberUtil.getInt(titleid) == 0 || StringUtil.isEmpty(uuid)){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_GOODADD, 
					InterfaceLogConfig.ERROR_03); 
		}
		boolean isSuccess = false;
		GoodRecord goodRecord = new GoodRecord();
		goodRecord.setTitleId(titleid);
		goodRecord.setSiteId(siteid);
		goodRecord.setType(type);
		goodRecord.setUuid(uuid);
		try {
			isSuccess = infoCountService.modifyGoodCount(titleid, type, siteid);
			if(isSuccess){
				isSuccess = goodRecordService.add(goodRecord);
			}
			ret.put("result", "" + isSuccess);
			ret.put("message", "");
		} catch (Exception e) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_GOODADD, 
					InterfaceLogConfig.ERROR_08); 
		}
		return JsonUtil.objectToString(ret);
	}
	
}