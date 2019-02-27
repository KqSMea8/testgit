package com.hanweb.jmp.cms.controller.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.common.util.mvc.ResultState;
import com.hanweb.jmp.apps.entity.broke.Broke;
import com.hanweb.jmp.apps.service.broke.BrokeService;
import com.hanweb.jmp.cms.entity.comment.Comment;
import com.hanweb.jmp.cms.entity.infos.Info;
import com.hanweb.jmp.cms.service.comment.CommentService;
import com.hanweb.jmp.cms.service.infos.InfoService;

/**
 * 机构操作控制器
 * @author ZhangC
 * 
 */
@Controller
//@Permission(module = "/comment", allowedAdmin = Allowed.YES)
@RequestMapping("manager/comment")
public class OprCommentController {

	/**
	 * commentService
	 */
	@Autowired
	private CommentService commentService;
	
	/**
	 * infoService
	 */
	@Autowired
	private InfoService infoService;
	
	/**
	 * brokeService
	 */
	@Autowired
	private BrokeService brokeService;
	
	/**
	 * 显示编辑机构页面
	 * @param iid  iid 
	 * @return  ModelAndView
	 */
	@RequestMapping(value = "modify_show")
	public ModelAndView showModify(Integer iid) {
		ModelAndView modelAndView = new ModelAndView("jmp/cms/comment/comment_opr");
		Comment comment = commentService.findById(iid);
		String typeName = "";
		String title = "";
		if(comment != null){
			if(comment.getType() == 1){
				typeName = "信息标题";
				Info info = infoService.findByIid(comment.getInfoId(), comment.getSiteId());
				if(info != null){
					title = info.getTitle();
				}
			}else{
				typeName = "报料内容"; 
				Broke broke = brokeService.findByIid(comment.getInfoId());
				if(broke != null){
					title = broke.getContent();
				}
			}
		}
		modelAndView.addObject("typeName", typeName);
		modelAndView.addObject("title", title);
		modelAndView.addObject("comment", comment);
		return modelAndView;
	}

	/**
	 * 审核
	 * @param ids
	 *            ID串 如:1,2,3,4
	 * @return  JsonResult
	 */
	@RequestMapping(value = "audit")
	@ResponseBody
	public JsonResult audit(String ids) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = commentService.audit(ids);
		if (isSuccess) {
			jsonResult.set(ResultState.OPR_SUCCESS);
		} else {
			jsonResult.set(ResultState.OPR_FAIL);
		}
		return jsonResult;
	}
	
	/**
	 * 撤审
	 * @param ids
	 *            ID串 如:1,2,3,4
	 * @return JsonResult
	 */
	@RequestMapping(value = "unaudit")
	@ResponseBody
	public JsonResult unaudit(String ids) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = commentService.unaudit(ids);
		if (isSuccess) {
			jsonResult.set(ResultState.OPR_SUCCESS);
		} else {
			jsonResult.set(ResultState.OPR_FAIL);
		}
		return jsonResult;
	}

	/**
	 * 删除
	 * @param ids
	 *            ID串 如:1,2,3,4
	 * @return JsonResult
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public JsonResult remove(String ids) {
		JsonResult jsonResult = JsonResult.getInstance();
		boolean isSuccess = commentService.removeByIds(ids);
		if (isSuccess) {
			jsonResult.set(ResultState.REMOVE_SUCCESS);
			jsonResult.addParam("remove", ids);
		} else {
			jsonResult.set(ResultState.REMOVE_FAIL);
		}
		return jsonResult;
	}

}