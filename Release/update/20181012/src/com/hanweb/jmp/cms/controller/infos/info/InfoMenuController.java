package com.hanweb.jmp.cms.controller.infos.info;

import java.util.List;
import java.util.Set;

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
//@Permission(module = "/info", allowedAdmin = Allowed.YES)
@RequestMapping("manager/menu")
public class InfoMenuController {

	/**
	 * colService
	 */
	@Autowired
	private ColService colService;

	/**
	 * 初始化树
	 * @param colId colId
	 * @param siteId siteId
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "infomenu_show")
	public ModelAndView showDefaultTaskMenu(String colId, String siteId) {
		ModelAndView modelAndView = new ModelAndView("/jmp/cms/infos/info_menu");
		Tree tree = Tree.getInstance("colId", "colName");
		tree.addNode(TreeNode.getInstance("0", "", "信息管理", "/manager/info/list.do"));
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		siteId=currentUser.getSiteId()+"";
		boolean isWebSiteAdmin=currentUser.getIsWebSiteAdmin();
		List<Col> colList = colService.findChildColByIid(NumberUtil.getInt(colId), NumberUtil.getInt(siteId));
		if(colList !=null){
			Set<String> colRightids=currentUser.getColids();
			TreeNode treeNode =null;
			String[] colids=null;
			for (Col col : colList) {
				if(col.getType()==2){
					colids = new String[1];
					colids[0] = col.getIid()+""; 
					treeNode = TreeNode.getInstance(col.getIid() + "",
							   NumberUtil.getInt(col.getPid()) + "", col.getName(),
							   "/manager/info/list.do",
							   col.getIsParent(), false);
					treeNode = getTreeNode(treeNode, col);
					//网站管理员
					if(isWebSiteAdmin || colRightids.contains(StringUtil.getString(col.getIid()))){
						tree.addNode(treeNode);
					} 
				}else if(col.getType()==1){
					colids = new String[1];
					colids[0] = col.getIid()+""; 
					treeNode = TreeNode.getInstance(col.getIid() + "",
							   NumberUtil.getInt(col.getPid()) + "", col.getName(),"",
							   col.getIsParent(), false).setIsParent(true);
					treeNode = getTreeNode(treeNode, col);
					//网站管理员
					if(isWebSiteAdmin || colRightids.contains(
							StringUtil.getString(col.getIid()))){
						tree.addNode(treeNode);
					} 
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
	@RequestMapping("menuwithurlforinfo_search")
	@ResponseBody
	public String searchAsyncMenuWithUrl(String colId, String isDisabled) {  
		// 组织树
		Tree tree = Tree.getInstance();
		List<Col> colList = colService.findChildColByIid(NumberUtil.getInt(colId), 0);
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		boolean isWebSiteAdmin = currentUser.getIsWebSiteAdmin();
		Set<String> colRightids = currentUser.getColids();
		String[] colids = null;
		TreeNode treeNode = null;  
		for (Col col : colList) {
			if(col.getType() == 2){
				colids=new String[1];
				colids[0] = col.getIid()+""; 
				treeNode = TreeNode.getInstance(col.getIid() + "", colId, col.getName(),
						 "/manager/info/list.do", col.getIsParent(), false).addParam("colId",col.getIid())
						 .addParam("colName", col.getName());
				treeNode = getTreeNode(treeNode, col);
				//网站管理员
				if(isWebSiteAdmin || colRightids.contains(StringUtil.getString(col.getIid()))){
					tree.addNode(treeNode);
				} 
			} else if(col.getType()==1){
				colids = new String[1];
				colids[0] = col.getIid()+""; 
				treeNode = TreeNode.getInstance(col.getIid() + "",
						   NumberUtil.getInt(col.getPid()) + "", col.getName(),"",
						   col.getIsParent(), false).setIsParent(true);
				treeNode = getTreeNode(treeNode, col);
				//网站管理员
				if(isWebSiteAdmin || colRightids.contains(StringUtil.getString(col.getIid()))){
					tree.addNode(treeNode);
				} 
			} 
		}
		return tree.parse();
	}
	
	/**
	 * 推送信息获取目录树
	 * @param colId 栏目id
	 * @param typeId 推送分类ID
	 * @return ModelAndView
	 */
//	@RequestMapping("menuforpushchoose_show")
//	@ResponseBody
//	public ModelAndView showChooseInfoMenu(String colId, String typeId){
//		ModelAndView modelAndView = new ModelAndView("/jmp/push/info/choose_menu");
//		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
//		Set<String> colRightids=currentUser.getColids();
//		int siteId = currentUser.getSiteid();
//		Tree tree = Tree.getInstance("colIid", "colName");
//		TreeNode treeNode = null;
//		treeNode = TreeNode.getInstance("0", "", "栏目分类",
//				"/manager/push/info/choose/list.do").addParam("colId", colId)
//				.addParam("typeId", typeId).setTarget("choosepage");
//		tree.addNode(treeNode);
//		List<Col> colList = colService.findChildColByIid(
//				NumberUtil.getInt(colId), NumberUtil.getInt(siteId));
//		String[] colids=null;
//		if(colList !=null){
//			for (Col col : colList) {
//				colids=new String[1];
//				colids[0]=col.getIid()+""; 
//				treeNode = TreeNode.getInstance(col.getIid() + "",
//						NumberUtil.getInt(col.getPid())+ "", col.getName(),
//						"/manager/push/info/choose/list.do", col.getIsParent(), false).
//						addParam("colId", colId).addParam("typeId", typeId).setTarget("choosepage");
//				if(currentUser.getIsWebSiteAdmin() || colRightids.contains(
//						StringUtil.getString(col.getIid()))){
//					tree.addNode(treeNode);
//				} 
//			}
//		}
//		modelAndView.addObject("tree", tree.parse());
//		return modelAndView;
//	}
	
	/**
	 * 改变typename和state的值
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