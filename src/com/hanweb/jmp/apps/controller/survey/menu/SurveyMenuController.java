package com.hanweb.jmp.apps.controller.survey.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hanweb.common.view.tree.Tree;
import com.hanweb.common.view.tree.TreeNode;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.support.controller.CurrentUser;

import com.hanweb.jmp.apps.entity.survey.Survey;
import com.hanweb.jmp.apps.service.survey.SurveyService;

@Controller
@RequestMapping("manager/plugins/survey/menu/")
public class SurveyMenuController {
	
	@Autowired
	SurveyService surveyService;
	
	/**
	 * showRouteStationMenu
	 * @return    设定参数 .
	 */
	@RequestMapping("menu_show")
	public ModelAndView showRouteStationMenu() {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		int siteId = currentUser.getSiteId();
		ModelAndView modelAndView = new ModelAndView("jmp/apps/survey/survey_menu");
		// 组织树
		Tree tree = Tree.getInstance();
		tree.addNode(TreeNode.getInstance("survey", "", "调查管理", "/manager/plugins/survey/list.do").setOpen(true));
		List<Survey> surveyList = surveyService.findAll(siteId);
		TreeNode treeNode = null;  
		for(Survey SurveyEn : surveyList){
			treeNode = TreeNode.getInstance(SurveyEn.getIid() + "", "survey" , SurveyEn.getName(),
					"/manager/plugins/survey/question/list.do", false, false).addParam("surveyId",
							SurveyEn.getIid()).addParam("surveyName", SurveyEn.getName());
			tree.addNode(treeNode);
		}
		tree.addNode(TreeNode.getInstance("surveyresult", "", "调查结果", "/manager/plugins/surveyresult/list.do", false, false));
		modelAndView.addObject("tree", tree.parse());
		return modelAndView;
	}
	
	/**
	 * searchMenuBrokeType
	 * @return    设定参数 .
	 */
	@RequestMapping("menu_search")
	@ResponseBody
	public String searchMenuBrokeType() {
		Tree tree = Tree.getInstance();
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		int siteId = currentUser.getSiteId();
		List<Survey> surveyList = surveyService.findAll(siteId);
		TreeNode treeNode =null;  
		for(Survey SurveyEn : surveyList){
			treeNode=TreeNode.getInstance(SurveyEn.getIid() + "", "survey" , SurveyEn.getName(),
					"/manager/plugins/survey/question/list.do", false, false).addParam("surveyId",
							SurveyEn.getIid()).addParam("surveyName", SurveyEn.getName());
			tree.addNode(treeNode);
		}
		return tree.parse();
	}

}