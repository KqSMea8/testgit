package com.hanweb.jmp.cms.controller.matters.picture;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.cms.entity.matters.picture.PictureCol;
import com.hanweb.jmp.cms.service.matters.picture.PictureColService;

import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping("manager/picture/select")
public class OprPictureShowController {
	
	/*
	 * PictureColService
	 */
	@Autowired
	private PictureColService pictureColService;
	
	/**
	 * 新增页面
	 * @param classId
	 * @param request   request
	 * @return   ModelAndView
	 */
	@RequestMapping(value = "picturecol")
	public ModelAndView showAdd(HttpServletRequest request) {
		PictureCol picturecol = new PictureCol();
		ModelAndView modelAndView = new ModelAndView("jmp/cms/matters/picture/picture_selectcol");
		modelAndView.addObject("picturecol", picturecol);
		this.addOtherObject(modelAndView);
		return modelAndView;
	}

	/**
	 * 组织树
	 * @param modelAndView
	 */
	private void addOtherObject(ModelAndView modelAndView) {
		// 组织树
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		List<PictureCol> pictureColList = pictureColService.findBySiteId(siteId);
		Tree tree = Tree.getInstance();
		for (PictureCol pictureCol : pictureColList) {
			tree.addNode(TreeNode.getInstance( "" + pictureCol.getIid(), "root",
					pictureCol.getName()).setIsParent(false));
			modelAndView.addObject("classId", pictureCol.getIid());
		} 
		modelAndView.addObject("lightAppMenu", tree.parse());
	}

}
