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

import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.JsonUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.ControllerUtil;

import com.hanweb.jmp.cms.entity.comment.Comment;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.cms.service.comment.CommentService;
import com.hanweb.jmp.cms.service.infos.InfoService;
import com.hanweb.jmp.constant.InterfaceLogConfig;
import com.hanweb.jmp.sys.entity.parameter.Parameter;
import com.hanweb.jmp.sys.service.parameter.ParameterService;

@Controller
@RequestMapping("interfaces")
public class CommentController {

	/**
	 * parameterService
	 */
	@Autowired
	private ParameterService parameterService;
	
	/**
	 * commentService
	 */
	@Autowired
	private CommentService commentService; 
	
	@Autowired
	private InfoService infoService;
	
	@Autowired
	private ColService colService;
	
	/**
	 * 2.2.1 评论提交接口
	 * @param siteid 网站id
	 * @param clienttype 客户端类型
	 * @param titleid 信息id
	 * @param context 评论内存
	 * @param lgname 登录名
	 * @param ctype 评论类型  1：文章  2：报料
	 * @param address address
	 * @return 返回值
	 */
	@RequestMapping("commentadd")
	@ResponseBody
	public String addComment(Integer siteid, Integer clienttype, Integer titleid, String context, 
			                 String lgname, Integer ctype, String address){
		HashMap<String, Object> ret = new HashMap<String, Object>();
		Parameter parameter = parameterService.findBySiteId(siteid);
		if(parameter == null){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_INFOCOMMENT, 
					InterfaceLogConfig.ERROR_07); 
		}
		Info info = infoService.findByIid(titleid, siteid);
		boolean isSuccess = false;
		Comment comment = new Comment();
		comment.setSiteId(siteid);
		comment.setInfoId(titleid);
		comment.setCreateTime(new Date());
		comment.setType(NumberUtil.getInt(ctype, 1)); 
		comment.setContent(StringUtil.getString(context));
		comment.setLoginid(lgname);
		comment.setState(parameter.getCommentAutoAudit());
		comment.setClientType(clienttype); 
		comment.setAddress(address);
		comment.setIp(ControllerUtil.getIp());
		if(NumberUtil.getInt(ctype)==1){
			comment.setCateId(info.getColId());
		}
		try {
			isSuccess = commentService.add(comment);
			if(isSuccess && NumberUtil.getInt(ctype)==1 
					&& NumberUtil.getInt(info.getInfoListType())==10){
				colService.modifyFlag(StringUtil.getString(info.getColId()));
			}
			ret.put("result", "" + isSuccess);
			ret.put("autoaudit", "" + (NumberUtil.getInt(parameter.getCommentAutoAudit()) == 1));
		} catch (Exception e) {
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_INFOCOMMENT, 
					InterfaceLogConfig.ERROR_08); 
		}
		return JsonUtil.objectToString(ret);
	}
	
	/**
	 * 2.2.2 评论列表接口
	 * @param siteid 网站id
	 * @param titleid 信息id
	 * @param commentid 评论id（刷新为第一条，更多为最后一条），为0返回最新的page条
	 * @param page 每页显示页数
	 * @param ctype 评论类型   1：文章   2：报料
	 * @param type 操作类型
	 * @param uuid uuid
	 * @return 返回值
	 */
	@RequestMapping("commentlist")
	@ResponseBody
	public String listComment(Integer siteid, Integer titleid, Integer commentid, Integer page, 
			                  Integer ctype, Integer type, String uuid){ 
		HashMap<String, Object> ret = new HashMap<String, Object>();
		Parameter parameter = parameterService.findBySiteId(siteid);
		if(parameter == null){
			return InterfaceLogConfig.interfaceResult(false, InterfaceLogConfig.MOD_INFOCOMMENT,  
					InterfaceLogConfig.ERROR_07); 
		}
		ctype = NumberUtil.getInt(ctype, 1);
		List<Map<String, String>> infolist = new ArrayList<Map<String, String>>();
		Map<String, String> infoMap = null;
		String name = "";
		String nameimage = "";
		List<Comment> commentList = commentService.findByInfoId(siteid, titleid, commentid, page, ctype, type, uuid);
		if(commentList != null){
			String strisgood=""; 
			for (Comment comment2 : commentList) {
				if(comment2==null){
					continue;
				}  
				infoMap = new HashMap<String, String>();
				infoMap.put("commentid", "" + comment2.getIid());
				infoMap.put("clienttype", "" + comment2.getClientType());
				infoMap.put("context", "" + comment2.getContent());
				if(comment2.getCreateTime()==null){
					comment2.setCreateTime(new Date());
				}
				infoMap.put("infotime", "" + comment2.getCreateTime().getTime());
				if(StringUtil.isEmpty(comment2.getHeadurl())){
					nameimage = "";
				} else {
					nameimage = BaseInfo.getDomain() + comment2.getHeadurl();
				}
				infoMap.put("nameimage", nameimage);
				if(StringUtil.isEmpty(comment2.getLoginid())){
					name = "";
				} else if (StringUtil.isNotEmpty(comment2.getName())){
					name = comment2.getName();
				} else {
					name = comment2.getLoginid();
				}
				infoMap.put("name", name);
				infoMap.put("goodnum", StringUtil.getString(
						NumberUtil.getInt(comment2.getGoodCount())));
				if(NumberUtil.getInt(comment2.getMygoodCount())==0){
					strisgood="false";
				} else {
					strisgood="true";
				}
				infoMap.put("isgood", strisgood);
				infoMap.put("address", comment2.getAddress());
				infolist.add(infoMap);
			}
			}
			ret.put("infolist", infolist);
		
		return JsonUtil.objectToString(ret);
	}
	
}