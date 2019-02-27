package com.hanweb.weather.controller.menu;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.StringUtil;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.weather.entity.Province;
import com.hanweb.weather.service.ProvinceService;


@Controller
@RequestMapping(value = "manager/menu")
public class ProvinceMenuController {
	@Autowired
	ProvinceService provinService;

	@RequestMapping(value = "provincemenu_show")
	public ModelAndView showProvinceMenu() {
		ModelAndView modelAndView = new ModelAndView("/jmp/area/province_menu"); 
		
		Tree tree = Tree.getInstance("provinceid", "provcn");
		tree.addNode(TreeNode.getInstance("0", "", "地区管理", "/manager/area/list.do"));
		List<Province> prolist=provinService.findAll();
		for(Province provinceEn : prolist){
			tree.addNode(TreeNode.getInstance(StringUtil.getString(provinceEn.getIid()), "0", 
					StringUtil.getString(provinceEn.getProvcn())
					, "/manager/area/list.do?procode="+provinceEn.getProCode(), false, false));
		} 
		modelAndView.addObject("tree", tree.parse());
		return modelAndView;
	}
	
}
