package com.hanweb.jmp.pack.backstage.controller.app;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.sites.SiteService;

@Controller
@RequestMapping("manager/menu")
public class AppMenuController {
	
	/**
	 * siteService
	 */
	@Autowired
	private SiteService siteService;
	
	/**
	 * showAppMenu:(这里用一句话描述这个方法的作用).
	 *
	 * @return    设定参数 .
	*/
	@RequestMapping("appmenu_show")
	public ModelAndView showAppMenu() {   
		ModelAndView modelAndView = new ModelAndView("/jmp/pack/backstage/app/app_menu");
		// 组织树
		Tree tree = Tree.getInstance();
		tree.addNode(TreeNode.getInstance("app", null, "版本管理",
				"/manager/app/list.do", true, false).setOpen(true));
		List<Site> siteList = siteService.findAll();
        for(Site site : siteList){
        	
    		tree.addNode(TreeNode.getInstance("site" + site.getIid() , "app", site.getName(),
    				"/manager/app/list.do?siteid=" + site.getIid()));
    			
    		}
        modelAndView.addObject("tree", tree.parse());
		return modelAndView;
	}

}
