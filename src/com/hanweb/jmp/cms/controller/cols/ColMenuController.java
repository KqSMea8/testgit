package com.hanweb.jmp.cms.controller.cols;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
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

import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping(value = "manager/menu")
public class ColMenuController {

	/**
	 * colService
	 */
	@Autowired
	ColService colService;

	/**
	 * 初始化树
	 * @param colId colId
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "colmenu_show")
	@ResponseBody
	public ModelAndView showDefaultTaskMenu(String colId) {
		ModelAndView modelAndView = new ModelAndView("/jmp/cms/cols/col_menu");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		boolean isWebSiteAdmin =currentUser.getIsWebSiteAdmin();
		Integer siteId = currentUser.getSiteId(); //网站ID
		Tree tree = Tree.getInstance("colId", "colName");
		tree.addNode(TreeNode.getInstance("0", "", "栏目管理", "/manager/col/list.do"));
		List<Col> colList = colService.findChildColByIid(NumberUtil.getInt(colId), siteId); 
		TreeNode treeNode = null;
		Set<String> colRightids=currentUser.getColids();
		for (Col col : colList) {
			if(col.getType() == 1){
			    if("C".equals(col.getColType())){
			        treeNode = TreeNode.getInstance(col.getIid() + "",
	                        NumberUtil.getInt(col.getPid())+ "", col.getName() + "[复]",
	                        "/manager/col/list.do?type=" + col.getType(), col.getIsParent(), false).setIsParent(true);
			    } else if("Q".equals(col.getColType())){
			        treeNode = TreeNode.getInstance(col.getIid() + "",
                            NumberUtil.getInt(col.getPid())+ "", col.getName() + "[引]",
                            "/manager/col/list.do?type=" + col.getType(), col.getIsParent(), false).setIsParent(true);
			    } else {
			        treeNode = TreeNode.getInstance(col.getIid() + "",
                            NumberUtil.getInt(col.getPid())+ "", col.getName(),
                            "/manager/col/list.do?type=" + col.getType(), col.getIsParent(), false).setIsParent(true);
			    }
			    treeNode = getTreeNode(treeNode, col);
                // 网站管理员或具有该模块权限
                if(isWebSiteAdmin || currentUser.getPermissions().contains("col") || colRightids.contains(StringUtil.getString(col.getIid()))){
                    tree.addNode(treeNode);
                }
			}else if(col.getType() == 3){
				if("C".equals(col.getColType())){
				    treeNode = TreeNode.getInstance(col.getIid() + "",
	                        NumberUtil.getInt(col.getPid())+ "", col.getName() + "[复]",
	                        "", col.getIsParent(), false);
                } else if("Q".equals(col.getColType())){
                    treeNode = TreeNode.getInstance(col.getIid() + "",
                            NumberUtil.getInt(col.getPid())+ "", col.getName() + "[引]",
                            "", col.getIsParent(), false);
                } else {
                    treeNode = TreeNode.getInstance(col.getIid() + "",
                            NumberUtil.getInt(col.getPid())+ "", col.getName(),
                            "/manager/lightappforcol/list.do", col.getIsParent(), false);
                }
				treeNode = getTreeNode(treeNode, col);
				// 网站管理员或具有该模块权限
                if(isWebSiteAdmin || currentUser.getPermissions().contains("col") || colRightids.contains(StringUtil.getString(col.getIid()))){
                    tree.addNode(treeNode);
                }
			}else {
				if("C".equals(col.getColType())){
				    treeNode = TreeNode.getInstance(col.getIid() + "",
	                        NumberUtil.getInt(col.getPid())+ "", col.getName() + "[复]",
	                        "", col.getIsParent(), false);
                } else if("Q".equals(col.getColType())){
                    treeNode = TreeNode.getInstance(col.getIid() + "",
                            NumberUtil.getInt(col.getPid())+ "", col.getName() + "[引]",
                            "", col.getIsParent(), false);
                } else {
                    treeNode = TreeNode.getInstance(col.getIid() + "",
                            NumberUtil.getInt(col.getPid())+ "", col.getName(),
                            "", col.getIsParent(), false);
                }
				treeNode = getTreeNode(treeNode, col);
				// 网站管理员或具有该模块权限
                if(isWebSiteAdmin || currentUser.getPermissions().contains("col") || colRightids.contains(StringUtil.getString(col.getIid()))){
                    tree.addNode(treeNode);
                }
			}
		}
		modelAndView.addObject("tree", tree.parse());
		return modelAndView;
	}
	
	/**
	 * 刷新树
	 * @param colId colId
	 * @param isDisabled isDisabled
	 * @return    设定参数 .
	*/
	@RequestMapping("menuwithurlforcol_search")
	@ResponseBody
	public String searchAsyncMenuWithUrl(String colId, String isDisabled) {
		// 组织树
		Tree tree = Tree.getInstance();
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId(); //网站ID
        boolean isWebSiteAdmin =currentUser.getIsWebSiteAdmin();
		Set<String> colRightids=currentUser.getColids();
		List<Col> colList = colService.findChildColByIid(NumberUtil.getInt(colId), siteId);
		TreeNode treeNode = null;
		for (Col col : colList) {
 			if(col.getType() == 1){
			    if("C".equals(col.getColType())){
			        treeNode = TreeNode.getInstance(col.getIid() + "", colId, col.getName() + "[复]",
                            "/manager/col/list.do?type=" + col.getType(), col.getIsParent(), false)
                            .addParam("colId", col.getIid()).addParam("colName", col.getName()).setIsParent(true);
                } else if("Q".equals(col.getColType())){
                    treeNode = TreeNode.getInstance(col.getIid() + "", colId, col.getName() + "[引]",
                            "/manager/col/list.do?type=" + col.getType(), col.getIsParent(), false)
                            .addParam("colId", col.getIid()).addParam("colName", col.getName()).setIsParent(true);
                } else {
                    treeNode = TreeNode.getInstance(col.getIid() + "", colId, col.getName(),
                            "/manager/col/list.do?type=" + col.getType(), col.getIsParent(), false)
                            .addParam("colId", col.getIid()).addParam("colName", col.getName()).setIsParent(true);
                }
			    treeNode = getTreeNode(treeNode, col);
                // 网站管理员或具有该模块权限
                if(isWebSiteAdmin || currentUser.getPermissions().contains("col") || colRightids.contains(StringUtil.getString(col.getIid()))){
                    tree.addNode(treeNode);
                }
			}else if(col.getType() == 3){
				if("C".equals(col.getColType())){
				    treeNode = TreeNode.getInstance(col.getIid() + "",
	                        NumberUtil.getInt(col.getPid())+ "", col.getName() + "[复]",
	                        "", col.getIsParent(), false);
                } else if("Q".equals(col.getColType())){
                    treeNode = TreeNode.getInstance(col.getIid() + "",
                            NumberUtil.getInt(col.getPid())+ "", col.getName() + "[引]",
                            "", col.getIsParent(), false);
                } else {
                    treeNode = TreeNode.getInstance(col.getIid() + "",
                            NumberUtil.getInt(col.getPid())+ "", col.getName(),
                            "/manager/lightappforcol/list.do", col.getIsParent(), false);
                }
				treeNode = getTreeNode(treeNode, col);
				// 网站管理员或具有该模块权限
                if(isWebSiteAdmin || currentUser.getPermissions().contains("col") || colRightids.contains(StringUtil.getString(col.getIid()))){
                    tree.addNode(treeNode);
                }
			}else{
				if("C".equals(col.getColType())){
				    treeNode = TreeNode.getInstance(col.getIid() + "", colId, col.getName() + "[复]",false);
                } else if("Q".equals(col.getColType())){
                    treeNode = TreeNode.getInstance(col.getIid() + "", colId, col.getName() + "[引]",false);
                } else {
                    treeNode = TreeNode.getInstance(col.getIid() + "", colId, col.getName(),false);
                }
				treeNode = getTreeNode(treeNode, col);
                // 网站管理员或具有该模块权限
                if(isWebSiteAdmin || currentUser.getPermissions().contains("col") || colRightids.contains(StringUtil.getString(col.getIid()))){
                    tree.addNode(treeNode);
                }
			}
		}
		return tree.parse();
	}
	
	/**
	 * searchAsyncMenuForCol
	 * @param colId colId
	 * @param isDisabled isDisabled
	 * @param currentId currentId
	 * @param type type
	 * @return    设定参数 .
	 */
	@RequestMapping("menuforcol_search")
	@ResponseBody
	public String searchAsyncMenuForCol(String colId, String isDisabled, String currentId, String type) {
		// 组织树
		Tree tree = Tree.getInstance();
		TreeNode treeNode = null;
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId(); //网站ID
		List<Col> colList = colService.findChildColByIidAndType(NumberUtil.getInt(colId), siteId, type);
		for (Col col : colList) {
			if (BooleanUtils.toBoolean(isDisabled)
					|| col.getIid() == NumberUtil.getInt(currentId)) {
				// 任务不能选择自身及其子任务为父任务
				treeNode = TreeNode.getInstance(col.getIid() + "", colId, col.getName())
						           .setIsParent(col.getIsParent()).setIsDisabled(true); 
			} else { 
				treeNode = TreeNode.getInstance(col.getIid() + "", colId, col.getName()).setIsParent(col.getIsParent());
			}
			treeNode = getTreeNode(treeNode, col);
			tree.addNode(treeNode);
		}
		return tree.parse();
	}
	
	/**
	 * 添加栏目左侧树名称
	 * @param colId colId
	 * @param isDisabled isDisabled
	 * @param currentId currentId
	 * @return    设定参数 .
	*/
	@RequestMapping("menuforcolname_search")
	@ResponseBody
	public String searchAsyncMenuForCol(String colId, String isDisabled, String currentId) {
		// 组织树
		Tree tree = Tree.getInstance();
		TreeNode treeNode = null;
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId(); //网站ID
		List<Col> colList = colService.findChildColByIid(NumberUtil.getInt(colId), siteId);
		for (Col col : colList) {
			if (col.getType()==1) {
				// 不能选择虚拟栏目作为分类
				treeNode = TreeNode.getInstance(col.getIid() + "", colId, col.getName())
						           .setIsParent(col.getIsParent()).setIsDisabled(true); 
			} else {
				treeNode = TreeNode.getInstance(col.getIid() + "", colId, col.getName()).setIsParent(col.getIsParent());
			}
			treeNode = getTreeNode(treeNode, col);
			tree.addNode(treeNode);
		}
		return tree.parse();
	}
	
	/**
	 * 通过树节点改变state和typename的值
	 * @param treenode treenode
	 * @param col col
	 * @return    设定参数 .
	*/
	private TreeNode getTreeNode(TreeNode treenode, Col col){
		int enable = NumberUtil.getInt(col.getEnable()); 
		int type = NumberUtil.getInt(col.getType()); 
		String state = "停用"; 
		if(enable == 1){
			state = "启用"; 
		} 
		String typename = "";
		if(type == 1){
			typename = "虚拟栏目";
		}else if(type == 2){
			typename = "普通栏目";
		}else{
			typename = "互动栏目";
		}
		return treenode.addAttr("state", state).addAttr("typename", typename);
	}
	
}