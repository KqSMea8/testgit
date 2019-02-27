package com.hanweb.jmp.apps.controller.read.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.apps.entity.read.Read;
import com.hanweb.jmp.apps.service.read.ReadService;
import com.hanweb.support.controller.CurrentUser;

/**
 * 阅读的 树控制器<br> 
 * @author ZDJ
 * 
 */
@Controller
@RequestMapping("manager/plugins/read/menu")
public class ReadMenuController {

	/**
	 * readService
	 */
	@Autowired
	private ReadService readService;

	/**
	 * 阅读树
	 * @param colId 阅读ID
	 * @return json
	 */
	@RequestMapping("menu_show")
	public ModelAndView showAppMenu(String colId) {
		ModelAndView modelAndView = new ModelAndView("/jmp/apps/read/menu");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId(); //网站ID
		Tree tree = Tree.getInstance("colId", "colName");
		tree.addNode(TreeNode.getInstance("0", "", "阅读管理", "/manager/plugins/read/list.do").setTarget("readpage"));
		List<Read> colList = readService.findAllCol(NumberUtil.getInt(colId), siteId); 
		TreeNode treeNode = null;
		for (Read col : colList) { 
			treeNode = TreeNode.getInstance(col.getIid() + "", "" + col.getPid(), col.getName(),
					"/manager/plugins/read/list.do",  col.getIsparent(), false).setTarget("readpage");
			tree.addNode(treeNode);
		}
		modelAndView.addObject("tree", tree.parse());
		return modelAndView;
	}
	
	/**
	 * 异步加载树的控制器
	 * @param colId  分类ID
	 * @return 树
	 */
	@ResponseBody
	@RequestMapping("menuwithurlforcol_search")
	public String showUrlForCol(String colId){
		Tree tree = Tree.getInstance("colId", "colName");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId(); //网站ID
		List<Read> colList = readService.findAllCol(NumberUtil.getInt(colId), siteId); 
		TreeNode treeNode = null;
		for (Read col : colList) { 
			treeNode = TreeNode.getInstance(col.getIid() + "", col.getPid() + "", col.getName(),
					"/manager/plugins/read/list.do", col.getIsparent(), false).setTarget("readpage");
			tree.addNode(treeNode);
		}
		return tree.parse();
	}

}