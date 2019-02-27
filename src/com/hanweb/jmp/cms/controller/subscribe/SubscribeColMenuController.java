package com.hanweb.jmp.cms.controller.subscribe;

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
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.entity.sign.Sign;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.cms.service.sign.SignService;

import com.hanweb.support.controller.CurrentUser;


@Controller
@RequestMapping(value = "manager/menu")
public class SubscribeColMenuController {

	/**
	 * colService
	 */
	@Autowired
	ColService colService;
	
	/**
	 * signService
	 */
	@Autowired
	SignService signService;

//	@RequestMapping(value = "subscribecolmenu_show")
//	public ModelAndView showDefaultTaskMenu(String colId) {
//		ModelAndView modelAndView = new ModelAndView("/jmp/cms/subscribe/col_menu");
//		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
//		Integer siteId = currentUser.getSiteid(); //网站ID
//		Tree tree = Tree.getInstance("colId", "colName");
//		tree.addNode(TreeNode.getInstance("0", "", "订阅栏目",
//				"/manager/subscribecol/list.do"));
//		List<Col> colList = colService.findChildColByIid(NumberUtil.getInt(colId), siteId);
//		for (Col col : colList) {
//			tree.addNode(TreeNode.getInstance(
//					col.getIid() + "", NumberUtil.getInt(col.getPid(), 0)+"", col.getName(),
//					"/manager/subscribecol/list.do?type="+col.getType(), col.getIsParent(), false));
//		}
//		modelAndView.addObject("tree", tree.parse());
//		return modelAndView;
//	}
	
	/**
	 * searchAsyncMenuWithUrl
	 * @param colId colId
	 * @param isDisabled isDisabled
	 * @return    设定参数 .
	 */
	@RequestMapping("menuwithurlforsubscribecol_search")
	@ResponseBody
	public String searchAsyncMenuWithUrl(String colId, String isDisabled) {
		// 组织树
		Tree tree = Tree.getInstance();
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId(); //网站ID
		List<Col> colList = colService.findChildColByIid(NumberUtil.getInt(colId), siteId);
		for (Col col : colList) {
			tree.addNode(TreeNode.getInstance(col.getIid() + "", colId, col.getName(),
					"/manager/subscribecol/list.do?type="+col.getType(), col.getIsParent(), false)
					.addParam("colId", col.getIid())
					.addParam("colName", col.getName()));
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
	@RequestMapping("menuforsubscribecol_search")
	@ResponseBody
	public String searchAsyncMenuForCol(String colId, String isDisabled, String currentId, String type) {
		// 组织树
		Tree tree = Tree.getInstance();
		TreeNode treeNode = null;
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId(); //网站ID
		List<Col> colList = colService.findChildColByIidAndType(NumberUtil.getInt(colId), siteId, type);
		for (Col col : colList) {
			if (BooleanUtils.toBoolean(isDisabled) || col.getIid() == NumberUtil.getInt(currentId)) {
				treeNode = TreeNode.getInstance(col.getIid() + "", colId, col.getName())
						 .setIsParent(col.getIsParent()).setIsDisabled(true); 
			} else {
				treeNode = TreeNode.getInstance(col.getIid() + "", colId,
						   col.getName()).setIsParent(col.getIsParent());
			}
			tree.addNode(treeNode);
		}
		return tree.parse();
	}
	
	/**
	 * 角标订阅
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "subscribesign_show")
	@ResponseBody
	public ModelAndView subscribeSign() {
		ModelAndView modelAndView = new ModelAndView("jmp/cms/sign/subscribesign_menu");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId(); //网站ID
		Tree tree = Tree.getInstance("subscribedimensionid", "subscribeSignName");
		tree.addNode(TreeNode.getInstance("subscribecol", "", "订阅管理", "/manager/sign/list.do?mid=2"));
		List<Sign> list = signService.findByMid(2, siteId, 0);
		for (Sign d : list) {
			tree.addNode(TreeNode.getInstance(d.getIid() + "", "subscribecol", d.getDname(),
					"/manager/sign/subscribecol/list.do?did=" + d.getIid()+"&name="+d.getDname(), false, false));
		}
		modelAndView.addObject("tree", tree.parse());
		return modelAndView;
	}
	
	
	/**
	 * 角标树
	 * @return    设定参数 .
	 */
	@RequestMapping(value = "menusubscribecol_search")
	@ResponseBody
	public String subscribeSignMenu() {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId(); //网站ID
		Tree tree = Tree.getInstance("subscribedimensionid", "subscribeSignName");
		List<Sign> list = signService.findByMid(2, siteId, 0);
		for (Sign d : list) {
			tree.addNode(TreeNode.getInstance(d.getIid() + "", "subscribecol", d.getDname(),
					"/manager/sign/subscribecol/list.do?did=" + d.getIid()+"&name="+d.getDname(), false, false));
		}
		return tree.parse();
	}
	
}