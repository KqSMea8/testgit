package com.hanweb.jmp.sys.controller.count;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.StringUtil;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.sites.SiteService;

@Controller
@RequestMapping("manager/menu")
public class CountMenuController {
	
	/**
	 * siteService
	 */
	@Autowired
	private SiteService siteService;

	/**
	 * showPluginsMenu
	 * @param request request
	 * @return    设定参数 .
	 */
	@RequestMapping("countmenu_show")
	public ModelAndView showPluginsMenu(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("/jmp/sys/count/count_menu");
		// 组织树
		Tree tree = Tree.getInstance();
		tree.addNode(TreeNode.getInstance("count", null, "统计分析", "/manager/count/list.do"));
		tree.addNode(TreeNode.getInstance("all", "count", "汇总统计", "/manager/count/list.do"));
		tree.addNode(TreeNode.getInstance("col", "count", "按栏目分类统计", "", true, false));
		List<Site> siteList = siteService.findAll();
		for (Site site : siteList) {
			tree.addNode(TreeNode.getInstance(
					StringUtil.getString(site.getIid()), "col", site.getName(),
					"/manager/colcount/list.do?siteid="
					+site.getIid()+"&siteName=" + site.getName(), false, false));
		}  
		modelAndView.addObject("tree", tree.parse());
		return modelAndView;
	}
	
}