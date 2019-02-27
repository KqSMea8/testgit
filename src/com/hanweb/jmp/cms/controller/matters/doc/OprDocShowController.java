package com.hanweb.jmp.cms.controller.matters.doc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.cms.entity.matters.doc.DocCol;
import com.hanweb.jmp.cms.service.matters.doc.DocColService;
import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping("manager/doc/select")
public class OprDocShowController {
	
	/**
	 * DocColService
	 */
	@Autowired
	private DocColService docColService;
	
	/**
	 * 新增页面
	 * @param classId
	 * @param request   request
	 * @return   ModelAndView
	 */
	@RequestMapping(value = "doccol")
	public ModelAndView showAdd(HttpServletRequest request) {
		DocCol doccol = new DocCol();
		ModelAndView modelAndView = new ModelAndView("jmp/cms/matters/doc/doc_selectcol");
		modelAndView.addObject("doccol", doccol);
		this.addOtherObject(modelAndView);
		return modelAndView;
	}

	/**
	 * 组织附件分类树
	 * @param modelAndView
	 */
	private void addOtherObject(ModelAndView modelAndView) {
		// 组织树
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		List<DocCol> docColList = docColService.findBySiteId(siteId);
		Tree tree = Tree.getInstance();
		for (DocCol docCol : docColList) {
			tree.addNode(TreeNode.getInstance( "" + docCol.getIid(), "root",
					docCol.getName()).setIsParent(false));
			modelAndView.addObject("classId", docCol.getIid());
		} 
		modelAndView.addObject("lightAppMenu", tree.parse());		
	}

}