package com.hanweb.jmp.sys.controller.log;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;

@Controller
@RequestMapping("manager/menu")
public class LogMenuController {
	
	/**
	 * 初始化左侧树
	 * @param request request
	 * @return    设定参数 .
	 */
	@RequestMapping("logmenu_show")
	public ModelAndView showLogMenu(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("jmp/sys/log/log_menu");
		// 组织树
		Tree tree = Tree.getInstance();
		tree.addNode(TreeNode.getInstance("log", null, "日志管理", "/manager/log/chart.do"));
		tree.addNode(TreeNode.getInstance("opr_log", "log", "操作日志", "/manager/log/chart.do"));
		tree.addNode(TreeNode.getInstance("push_log", "log", "推送日志", "/manager/pushlog/chart.do"));
		tree.addNode(TreeNode.getInstance("interfacelog", "log", "接口访问日志", "/manager/interfacelog/chart.do"));
		tree.addNode(TreeNode.getInstance("offlinezip_log", "log", "离线下载日志", "/manager/offlineziplog/list.do"));
		modelAndView.addObject("tree", tree.parse());
		return modelAndView;
	}
	
}
