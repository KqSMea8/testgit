package com.hanweb.jmp.appstype.controller;

import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.tools.ant.types.CommandlineJava.SysProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.appstype.entity.LightAppType;
import com.hanweb.jmp.appstype.service.LightAppTypeService;
import com.hanweb.support.controller.CurrentUser;

/**
 * 应用分类控制器
 * @author ZCC
 *
 */
@Controller
@RequestMapping("manager/lightapptype/menu")
public class LightAppTypeMenuController {
	
	@Autowired
	private LightAppTypeService lightAppTypeService;
	
	/**
	 * 加载机构管理中 初始机构树
	 * 
	 * @return
	 */
	@RequestMapping("typemenu_show")
	public ModelAndView showGroupMenu(String state) {
		ModelAndView modelAndView = new ModelAndView("/jmp/apps/lightapptype/lightapptype_menu");

		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer rangeId = currentUser.getRangeId();
		Integer siteId = currentUser.getSiteId();
		String rangeName = currentUser.getRangeName();
		String nodeName = StringUtil.isEmpty(rangeName) ? "分类管理" : rangeName;
		if (rangeId == null || rangeId < 0) {
			return modelAndView;
		}
		String linkUrl = "";
		if(NumberUtil.getInt(state)==2){
			linkUrl = "/manager/lightapp/list.do";
		}else{
			linkUrl = "/manager/lightapptype/list.do";
		}
		
		// 组织树
		Tree tree = Tree.getInstance("typeId", "typeName");

		tree.addNode(TreeNode.getInstance(rangeId + "", "null", nodeName, linkUrl+"?lightTypeId=-1"));

		List<LightAppType> typeList = lightAppTypeService.findChildByIid(rangeId, NumberUtil.getInt(siteId));

		for (LightAppType type : typeList) {
			tree.addNode(TreeNode.getInstance(type.getIid() + "", rangeId + "", type.getName(),
					linkUrl+"?lightTypeId="+type.getIid(), type.getIsParent(), false));
		}
		if(NumberUtil.getInt(state)==2){
			tree.addNode(TreeNode.getInstance("defaultGroup", rangeId + "", "默认分组",
					linkUrl+"?lightTypeId=0", false, false));
		}
		modelAndView.addObject("tree", tree.parse());
		modelAndView.addObject("state", state);
		return modelAndView;
	}

	/**
	 * 异步加载机构树
	 * 
	 * @param groupId
	 *            机构ID
	 * @param isDisabled
	 *            是否可选<br>
	 *            true 可选<br>
	 *            false 不可选
	 * @return
	 */
	@RequestMapping("menuwithurlfortype_search")
	@ResponseBody
	public String searchAsyncMenuWithUrl(int typeId, String isDisabled, String state) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
        Integer rangeId = currentUser.getRangeId();
		Integer siteId = currentUser.getSiteId();
		String linkUrl = "";
		if(NumberUtil.getInt(state)==2){
			linkUrl = "/manager/lightapp/list.do";
		}else{
			linkUrl = "/manager/lightapptype/list.do";
		}
		// 组织树
		Tree tree = Tree.getInstance("typeId", "typeName");

		List<LightAppType> typeList = lightAppTypeService.findChildByIid(typeId, NumberUtil.getInt(siteId));

		for (LightAppType type : typeList) {
			tree.addNode(TreeNode.getInstance("" + type.getIid(), "" + typeId, type.getName(),
					linkUrl+"?lightTypeId="+type.getIid(), type.getIsParent(), false));
		}
		if(NumberUtil.getInt(state)==2){
            tree.addNode(TreeNode.getInstance("defaultGroup", rangeId + "", "默认分组",
                    linkUrl+"?lightTypeId=0", false, false));
        }

		return tree.parse();
	}
	
	
	/**
	 * 机构编辑时 加载父机构树
	 * 
	 * @param groupId
	 *            机构ID
	 * @param isDisabled
	 *            是否可选<br>
	 *            true 可选<br>
	 *            false 不可选
	 * @param currentId
	 *            所在操作页面的机构ID
	 * @return
	 */
	@RequestMapping("menufortype_search")
	@ResponseBody
	public String searchAsyncMenuForGroup(Integer typeId, String isDisabled, String currentId) {
		typeId = NumberUtil.getInt(typeId);
		int temp = typeId;

		// 组织树
		Tree tree = Tree.getInstance();
		TreeNode treeNode = null;

		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		if (typeId == 0 && currentUser.isGroupAdmin()) {
			typeId = currentUser.getRangeId();
		}

		List<LightAppType> LightAppTypeList = lightAppTypeService.findChildByIid(typeId, NumberUtil.getInt(siteId));
		for (LightAppType alt : LightAppTypeList) {
			if (BooleanUtils.toBoolean(isDisabled)
					|| alt.getIid() == NumberUtil.getInt(currentId)) {
				treeNode = TreeNode.getInstance(alt.getIid() + "", typeId + "", alt.getName())
						.setIsParent(alt.getIsParent()).setIsDisabled(true); // 机构不能选择自身及其下属机构为父机构
			} else {
				treeNode = TreeNode.getInstance(alt.getIid() + "", typeId + "", alt.getName())
						.setIsParent(alt.getIsParent());
			}
			tree.addNode(treeNode);
		}

		if (temp == 0 && currentUser.isGroupAdmin()) {
			boolean isParent = treeNode != null;
			treeNode = TreeNode.getInstance(typeId + "", "", currentUser.getRangeName())
					.setIsParent(isParent);
			tree.addNode(treeNode);
		}

		return tree.parse();
	}

}