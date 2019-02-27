package com.hanweb.jmp.sys.controller.feedback;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;

@Controller
@RequestMapping("manager/menu")
public class FeedBackMenuController {

	/**
	 * 初始化左侧树
	 * @param request request
	 * @return    设定参数 .
	*/
	@RequestMapping("feedbackmenu_show")
	public ModelAndView showPluginsMenu(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("jmp/sys/feedback/feedback_menu");
		// 组织树
		Tree tree = Tree.getInstance();
		tree.addNode(TreeNode.getInstance("feedback", null, "意见反馈", "/manager/feedback/list.do"));
		modelAndView.addObject("tree", tree.parse());
		return modelAndView;
	}
	
}
