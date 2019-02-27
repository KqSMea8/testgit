package com.hanweb.jmp.newspush.peoplelist.controller.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.annotation.Permission;
import com.hanweb.common.permission.Allowed;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.jmp.newspush.peoplelist.entity.PeopleList;
import com.hanweb.jmp.newspush.peoplelist.service.PeoplelistService;

/**
 * 群组相关的 群组树控制器<br>
 * (点击时触发的URL跳转为群组相关)
 * 
 * @author Wangjw
 * 
 */
@Controller
@Permission(allowedAdmin = Allowed.YES, allowedGroup = Allowed.NO)
@RequestMapping("manager/menu")
public class ClusterMenuController {
	
	
	@Autowired
	private PeoplelistService peoplelistService;
	
	/**
	 * 加载 初始群组树
	 * 
	 * @return
	 */
	@RequestMapping("clustermenu_show")
	public ModelAndView showGroupMenu() {
		ModelAndView modelAndView = new ModelAndView("/isms/cluster/cluster_menu");
		
		// 组织树
		Tree tree = Tree.getInstance("clusterId", "clusterName");

		tree.addNode(TreeNode.getInstance("0", "", "群组管理", "/manager/cluster/list.do"));

		List<PeopleList> peoplelists = peoplelistService.find();

		for (PeopleList peoplelist : peoplelists) {
			tree.addNode(TreeNode.getInstance(peoplelist.getIid() + "", "0" + "", peoplelist.getName(),
					"/manager/qunzu/list.do", false, false));
		}

		modelAndView.addObject("tree", tree.parse());

		return modelAndView;
	}
	
	/**
	 * 群组编辑时 加载父级群组树
	 * 
	 * @param isDisabled
	 *            是否可选<br>
	 *            true 可选<br>
	 *            false 不可选
	 * @return
	 */
	@RequestMapping("menuforcluster_search")
	@ResponseBody
	public String searchAsyncMenuForClassfication(String isDisabled) {
		// 组织树
		Tree tree = Tree.getInstance();
		TreeNode treeNode = null;
		List<PeopleList> peoplelists = peoplelistService.find();
		for (PeopleList peoplelist : peoplelists) {
			treeNode = TreeNode.getInstance(peoplelist.getIid() + "", 0 + "", peoplelist.getName())
				.setIsParent(false);
			tree.addNode(treeNode);
		}
		return tree.parse();
	}
	
}
