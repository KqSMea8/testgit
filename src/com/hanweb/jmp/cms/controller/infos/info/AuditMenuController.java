package com.hanweb.jmp.cms.controller.infos.info;

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
import com.hanweb.support.controller.CurrentUser;

import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.service.cols.ColService;

@Controller
@RequestMapping("manager/menu")
public class AuditMenuController {
	
	/**
	 * colService
	 */
	@Autowired
	private ColService colService;

	/**
	 * 未审核信息树展现
	 * @param colId String
	 * @param siteId String
	 * @return ModelAndView
	 */
	@RequestMapping("todomenu_show")
	public ModelAndView showDefaultAuditMenu(String colId, String siteId){
		ModelAndView modelAndView = new ModelAndView("/jmp/cms/infos/audit_menu");
		Tree tree = Tree.getInstance("colId", "colName");
		tree.addNode(TreeNode.getInstance("0", "", "待审核信息", "/manager/todo/list.do"));
		CurrentUser currentUser = UserSessionInfo.getCurrentUser(); 
		siteId = currentUser.getSiteId()+"";
		List<Col> colList = colService.findChildColByIid(NumberUtil.getInt(colId),NumberUtil.getInt(siteId));
		if(colList != null){
			TreeNode treeNode = null;
			String[] colids = null;
			for (Col col : colList) {
				if(col.getType()==2){
					colids = new String[1];
					colids[0] = col.getIid()+"";
					treeNode = TreeNode.getInstance(col.getIid() + "",
							NumberUtil.getInt(col.getPid())+ "", col.getName(),
							"/manager/todo/list.do",
							col.getIsParent(), false);	
					treeNode = getTreeNode(treeNode, col); 
					tree.addNode(treeNode);	 
				}else if(col.getType() == 1){
					colids = new String[1];
					colids[0] = col.getIid()+"";
					treeNode = TreeNode.getInstance(col.getIid() + "",
							NumberUtil.getInt(col.getPid())+ "", col.getName(),
							"", col.getIsParent(), false).setIsParent(true);	
					treeNode = getTreeNode(treeNode, col); 
					tree.addNode(treeNode);
				}
			}
		}
		modelAndView.addObject("tree", tree.parse());
		return modelAndView;
	}
	
	/**
	 * 树下拉显示子节点
	 * @param colId String
	 * @param isDisabled String
	 * @return  String
	 */
	@RequestMapping("menuwithurlforaudit_search")
	@ResponseBody
	public String searchAsyncMenuWithUrl(String colId, String isDisabled){
			Tree tree  = Tree.getInstance();
			List<Col> colList = colService.findChildColByIid(NumberUtil.getInt(colId), 0);
			for(Col col : colList){
				if(col.getType() == 1){
					TreeNode treeNode = TreeNode.getInstance(col.getIid()+"", colId, col.getName(),
							"", col.getIsParent(), false)
							.addParam("colId", col.getIid()).addParam("colName", col.getName()).setIsParent(true);
					treeNode = getTreeNode(treeNode, col);
					tree.addNode(treeNode);
				} else if(col.getType() == 2){
					TreeNode treeNode = TreeNode.getInstance(col.getIid()+"", colId, col.getName(),
							"/manager/todo/list.do", col.getIsParent(), false)
							.addParam("colId", col.getIid()).addParam("colName", col.getName());
					treeNode = getTreeNode(treeNode, col);
					tree.addNode(treeNode);
				}
				
			}
			return tree.parse();
	}
	
	/**
	 * getTreeNode
	 * @param treenode treenode
	 * @param col col
	 * @return    设定参数 .
	 */
	private TreeNode getTreeNode(TreeNode treenode, Col col){
		int enable=NumberUtil.getInt(col.getEnable()); 
		int type=NumberUtil.getInt(col.getType()); 
		String state="停用";
		if(enable==1){
			state="启用";
		}
		String typename="";
		if(type==1){
			typename="虚拟栏目";
		}else if(type==2){
			typename="普通栏目";
		}else{
			typename="互动栏目";
		}
		return treenode.addAttr("state", state).addAttr("typename", typename);
	}
	
}