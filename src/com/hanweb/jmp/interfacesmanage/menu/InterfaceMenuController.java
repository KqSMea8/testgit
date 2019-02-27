package com.hanweb.jmp.interfacesmanage.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.jmp.interfacesmanage.entity.InterfacesType;
import com.hanweb.jmp.interfacesmanage.service.InterfacesTypeService;

/**
 * 机构相关的 机构树控制器<br>
 * (点击时触发的URL跳转为机构相关)
 * 
 * @author ZhangC
 * 
 */
@Controller
@RequestMapping("manager/interfacemenu")
public class InterfaceMenuController {

	@Autowired
	private InterfacesTypeService interfaceTypeService;

	/**
	 * 加载接口管理中 初始接口类型树
	 * 
	 * @return
	 */
	@RequestMapping("interfacemenu_show")
	public ModelAndView showTypeMenu() {
		ModelAndView modelAndView = new ModelAndView("jmp/interfacesmanage/interface_menu");

		// 组织树
		Tree tree = Tree.getInstance("typeId", "typeName");
		tree.addNode(TreeNode.getInstance("0", "0", "接口分类", "/manager/interface/list.do"));
		List<InterfacesType> typeList = interfaceTypeService.findChildTypeByIid(0);
		for(InterfacesType type:typeList){
			tree.addNode(TreeNode.getInstance(type.getIid() + "", type.getPid()+"", type.getName(),
					"/manager/interface/list.do",type.getIsParent(), false));
		}
		modelAndView.addObject("tree", tree.parse());

		return modelAndView;
	}
	
	/**
	 * 异步加载接口类型树
	 * 
	 * @param groupId
	 *            机构ID
	 * @return
	 */
	@RequestMapping("interfacemenu_search")
	@ResponseBody
	public String searchAsyncMenuWithUrl(Integer typeId) {
		// 组织树
		Tree tree = Tree.getInstance("typeId", "groupName");

		List<InterfacesType> typeList=interfaceTypeService.findChildTypeByIid(typeId);
		
		
		for (InterfacesType type : typeList) {
				tree.addNode(TreeNode.getInstance("" + type.getIid(),""+typeId, type.getName(),
						"/manager/interface/list.do", type.getIsParent(), false));
		}

		return tree.parse();
	}
	
	@RequestMapping("interfacemenu_edit")
	@ResponseBody
	public String editAsyncMenuWithUrl(Integer typeId) {
		if(typeId==null){
			typeId =0;
		}
		// 组织树
		Tree tree = Tree.getInstance("typeId", "groupName");

		List<InterfacesType> typeList=interfaceTypeService.findChildTypeByIid(typeId);
		
		
		for (InterfacesType type : typeList) {
				tree.addNode(TreeNode.getInstance("" + type.getIid(),""+typeId, type.getName(),
						 type.getIsParent(), false));
		}

		return tree.parse();
	}
	
}
