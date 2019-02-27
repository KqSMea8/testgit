package com.hanweb.jmp.cms.controller.sign;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;

import com.hanweb.jmp.constant.SignConfig;

@Controller
@RequestMapping(value = "manager/menu")
public class SignMenuController {

	/**
	 * 初始化树
	 * @param colId colId
	 * @return    设定参数 .
	*/
	@RequestMapping(value = "signmenu_show")
	public ModelAndView showDefaultTaskMenu(String colId) {
		ModelAndView modelAndView = new ModelAndView("/jmp/cms/sign/sign_menu");
		HashMap<Integer, String> mod = (HashMap<Integer, String>) SignConfig.listType;
		Tree tree = Tree.getInstance("dimensionid", "signName");
		tree.addNode(TreeNode.getInstance("0", "", "角标定义", "/manager/sign/list.do?mid=3"));
		tree.addNode(TreeNode.getInstance("m1", "0", mod.get(1), "/manager/sign/list.do?mid=3", false, false));	
		modelAndView.addObject("tree", tree.parse());
		return modelAndView;
	}
	
}