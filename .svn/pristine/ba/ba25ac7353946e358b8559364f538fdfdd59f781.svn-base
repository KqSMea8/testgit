package com.hanweb.jmp.cms.controller.matters.video;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.cms.entity.matters.video.VideoCol;
import com.hanweb.jmp.cms.service.matters.video.VideoColService;
import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping("manager/video/select")
public class OprVideoShowController {
	
	/**
	 * VideoColService
	 */
	@Autowired
	private VideoColService videoColService;
	
	/**
	 * 新增页面
	 * @param classId
	 * @param request   request
	 * @return   ModelAndView
	 */
	@RequestMapping(value = "videocol")
	public ModelAndView showAdd(HttpServletRequest request) {
		VideoCol videocol = new VideoCol();
		ModelAndView modelAndView = new ModelAndView("jmp/cms/matters/video/video_selectcol");
		modelAndView.addObject("videocol", videocol);
		this.addOtherObject(modelAndView);
		return modelAndView;
	}

	/**
	 * 组织音视频分类树
	 * @param modelAndView
	 */
	private void addOtherObject(ModelAndView modelAndView) {
		// 组织树
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		List<VideoCol> videoColList = videoColService.findBySiteId(siteId);
		Tree tree = Tree.getInstance();
		for (VideoCol videoCol : videoColList) {
			tree.addNode(TreeNode.getInstance( "" + videoCol.getIid(), "root",
					videoCol.getName()).setIsParent(false));
			modelAndView.addObject("classId", videoCol.getIid());
		} 
		modelAndView.addObject("lightAppMenu", tree.parse());
	}

}