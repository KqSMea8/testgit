package com.hanweb.jmp.apps.controller.broke.menu;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanweb.jmp.util.EncodeUtil;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.complat.listener.UserSessionInfo;

import com.hanweb.jmp.apps.entity.broke.BrokeType;
import com.hanweb.jmp.apps.service.broke.BrokeTypeService;

@Controller
@RequestMapping("manager/menu")
public class BrokeTypeMenuController {
	
	/**
	 * brokeTypeService
	 */
	@Autowired
	private BrokeTypeService brokeTypeService;

	/**
	 * 加载初始化树
	 * @param request request
	 * @return    设定参数 .
	 */
	@RequestMapping("brokemenu_show")
	public ModelAndView showPluginsMenu(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("/jmp/apps/broke/broketype_menu");
		// 组织树
		Tree tree = Tree.getInstance();
		tree.addNode(TreeNode.getInstance("broke", null, "报料管理", "/manager/broketype/list.do").setTarget("broketypepage"));
		List<BrokeType> brokeTypeList = brokeTypeService.findBySiteId(UserSessionInfo.getCurrentUser().getSiteId());
		for (BrokeType brokeType : brokeTypeList) {
			tree.addNode(TreeNode.getInstance(brokeType.getIid() + "", "broke", brokeType.getName(),
					     "/manager/broke/list.do?typeName= "
					     + new EncodeUtil().encodeStr(brokeType.getName(), "UTF-8")
					     + "&typeId=" + brokeType.getIid() + "").setTarget("broketypepage"));
		}
		modelAndView.addObject("tree", tree.parse());
		return modelAndView;
	}

	/**
	 * searchMenuBrokeType:(这里用一句话描述这个方法的作用).
	 * @return    设定参数 .
	 */
	@RequestMapping("menubroketype_search")
	@ResponseBody
	public String searchMenuBrokeType() {
		Tree tree = Tree.getInstance();
		List<BrokeType> brokeTypeList = brokeTypeService.findBySiteId(
				UserSessionInfo.getCurrentUser().getSiteId());
		for (BrokeType brokeType : brokeTypeList) {
			tree.addNode(TreeNode.getInstance(brokeType.getIid() + "", "broke", brokeType.getName(),
					"/manager/broke/list.do?typeName=" 
					+ brokeType.getName() + "&typeId=" + brokeType.getIid() + "").setTarget("broketypepage"));
		}
		return tree.parse();
	}
}
