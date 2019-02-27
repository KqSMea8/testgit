package com.hanweb.jmp.apps.controller.numbersense.menu;

import java.util.List;

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
import com.hanweb.support.controller.CurrentUser;

import com.hanweb.jmp.apps.entity.numbersense.NumSenseCol;
import com.hanweb.jmp.apps.service.numbersense.NumSenseColService;

/**
 * 通讯录相关树控制器<br> 
 * @author ZDJ
 */
@Controller
@RequestMapping("manager/plugins/numsense/menu")
public class NumSenseMenuController {

	/**
	 * numSenseColService
	 */
	@Autowired
	private NumSenseColService numSenseColService;

	/**
	 * 加载初始树
	 * @return 树json
	 */
	@RequestMapping("menu_show")
	public ModelAndView showNumsenseMenu() {
		ModelAndView modelAndView = new ModelAndView("jmp/apps/numbersense/menu");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId(); //网站ID
		Tree tree = Tree.getInstance("colId", "colName");
		tree.addNode(TreeNode.getInstance("c0", "", "分类管理",
				"/manager/plugins/numsense/col/list.do").setOpen(true).setTarget("colpage"));
		List<NumSenseCol> colList = numSenseColService.findChildByPid(0, siteId); 
		TreeNode treeNode = null;
		for (NumSenseCol col : colList) {
			if(col.getType()==1){
				treeNode = TreeNode.getInstance("c"+col.getIid() + "",
						"c"+NumberUtil.getInt(col.getPid()), col.getName(),
						"/manager/plugins/numsense/col/list.do", col.isIsparent(), false).setOpen(true).setTarget("colpage");
			}else {
				treeNode = TreeNode.getInstance(col.getIid() + "",
						"c"+NumberUtil.getInt(col.getPid()), col.getName(),
						"/manager/plugins/numsense/phone/list.do", col.isIsparent(), false).setTarget("colpage");
			}
			tree.addNode(treeNode);
		}
		modelAndView.addObject("tree", tree.parse());
		return modelAndView;
	}
	
	
	/**
	 * 异步加载树
	 * @param colId 分类id
	 * @param isDisabled 是否disabled
	 * @return tree.parase()
	 */
	@RequestMapping("menuwithurlforcol_search")
	@ResponseBody
	public String showUrlForCol(String colId, String isDisabled){
		String code=colId;
		if(StringUtil.isNotEmpty(colId)){
			colId = colId.replace("c", "");
		}
		Tree tree = Tree.getInstance("colId", "colName");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId(); //网站ID
		List<NumSenseCol> colList = numSenseColService.
					findChildByPid(NumberUtil.getInt(colId), siteId); 
		TreeNode treeNode = null;
		for (NumSenseCol col : colList) { 
			if(code.startsWith("c")){
				if(col.getType()==1){
					treeNode = TreeNode.getInstance("c"+col.getIid() + "",
							"c"+NumberUtil.getInt(col.getPid()), col.getName(),
							"/manager/plugins/numsense/col/list.do", col.isIsparent(), false).setOpen(true).setTarget("colpage");
				}else {
					treeNode = TreeNode.getInstance(col.getIid() + "",
							"c"+NumberUtil.getInt(col.getPid()), col.getName(),
							"/manager/plugins/numsense/phone/list.do", col.isIsparent(), false).setTarget("colpage");
				}
				tree.addNode(treeNode);
			}
		}
		return tree.parse();
		
	}
	
}
