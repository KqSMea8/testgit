package com.hanweb.jmp.interfacesmanage.menu;

import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.jmp.interfacesmanage.entity.InterfacesType;
import com.hanweb.jmp.interfacesmanage.service.InterfacesTypeService;

@Controller
@RequestMapping("manager/typemenu")
public class InterfacesTypeMenuController {

	@Autowired
	private InterfacesTypeService interfaceTypeService;


	/**
	 * 加载类型管理中 初始类型树
	 * 
	 * @return
	 */
	@RequestMapping("interfacetypemenu_show")
	public ModelAndView showTypeMenu() {
		ModelAndView modelAndView = new ModelAndView(
				"jmp/interfacesmanage/interfaceType_menu");

		// 组织树
		Tree tree = Tree.getInstance("typeId", "typeName");
		tree.addNode(TreeNode.getInstance(0 + "", "0", "接口分类",
				"/manager/interfaceType/list.do"));
		// List<InterfaceType> typeList = interfaceTypeService.findAllType();
		List<InterfacesType> typeList = interfaceTypeService
				.findChildTypeByIid(0);
		for (InterfacesType type : typeList) {
			tree.addNode(TreeNode.getInstance(type.getIid() + "", type.getPid()
					+ "", type.getName(), "/manager/interfaceType/list.do",
					type.getIsParent(), false));

		}

		modelAndView.addObject("tree", tree.parse());

		return modelAndView;
	}

	/**
	 * 异步加载类型树
	 * 
	 * @param typeId
	 *            类型ID
	 * 
	 * @return
	 */
	@RequestMapping("menuwithurlfortype_search")
	@ResponseBody
	public String searchAsyncMenuWithUrl(Integer typeId) {

		// 组织树
		Tree tree = Tree.getInstance("typeId", "typeName");

		List<InterfacesType> typeList = interfaceTypeService
				.findChildTypeByIid(typeId);

		for (InterfacesType type : typeList) {
			tree.addNode(TreeNode.getInstance("" + type.getIid(), "" + typeId,
					type.getName(), "/manager/interfaceType/list.do",
					type.getIsParent(), false));
		}

		return tree.parse();
	}

	/**
	 * 类型编辑时 加载父类型树
	 * 
	 * @param typeId
	 *            类型ID
	 * @param isDisabled
	 *            是否可选<br>
	 *            true 可选<br>
	 *            false 不可选
	 * @param currentId
	 *            所在操作页面的类型ID
	 * @return
	 */
	@RequestMapping("menufortype_search")
	@ResponseBody
	public String searchAsyncMenuForType(Integer typeId, String isDisabled,
			String currentId) {
		typeId = NumberUtil.getInt(typeId);

		// 组织树
		Tree tree = Tree.getInstance();
		TreeNode treeNode = null;

		List<InterfacesType> typeList = interfaceTypeService
				.findChildTypeByIid(typeId);
		for (InterfacesType type : typeList) {
			if (BooleanUtils.toBoolean(isDisabled)
					|| type.getIid() == NumberUtil.getInt(currentId)) {
				treeNode = TreeNode
						.getInstance(type.getIid() + "", typeId + "",
								type.getName()).setIsParent(type.getIsParent())
						.setIsDisabled(true); // 类型不能选择自身及其下属类型为父类型
			} else {
				treeNode = TreeNode.getInstance(type.getIid() + "",
						typeId + "", type.getName()).setIsParent(
						type.getIsParent());
			}
			tree.addNode(treeNode);
		}
		return tree.parse();
	}

}
