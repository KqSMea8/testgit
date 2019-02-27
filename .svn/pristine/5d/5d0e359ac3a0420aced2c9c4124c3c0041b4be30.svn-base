package com.hanweb.jmp.apps.controller.survey;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.annotation.Permission;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.service.GridViewService;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.common.view.grid.Button;
import com.hanweb.common.view.grid.GridRow;
import com.hanweb.common.view.grid.GridView;
import com.hanweb.common.view.grid.GridViewDelegate;
import com.hanweb.common.view.grid.GridViewSql;
import com.hanweb.common.view.grid.Head;

import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.apps.entity.survey.Question;
import com.hanweb.jmp.apps.entity.survey.Survey;
import com.hanweb.jmp.apps.service.survey.QuestionService;
import com.hanweb.jmp.apps.service.survey.SurveyService;

@Controller
@Permission
@RequestMapping("manager/plugins/survey/answer")
public class ListAnswerController implements GridViewDelegate{
	
	/** 
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;
	
	/**
	 * questionService
	 */
	@Autowired
	QuestionService questionService;
	
	/**
	 * surveyService
	 */
	@Autowired
	SurveyService surveyService;

	/**
	 * 列表
	 * @param gridView  gridView
	 * @return  GridView
	 */
	@RequestMapping("list")
	public GridView list(GridView gridView, String questionId, String surveyId) {
		String searchText = gridView.getSearchText();
		searchText = StringUtil.getStringTrim(searchText);
		gridView.setSearchText(searchText);
		gridView.setShowAdvSearch(false);
		gridView.setDelegate(this);
		gridView.setViewName("jmp/apps/survey/answer_list");
		Question question = questionService.findByIid(NumberUtil.getInt(questionId));
		gridView.addObject("question", question);
		Survey survey = surveyService.findByIid(NumberUtil.getInt(surveyId));
		gridView.addQueryParam("surveyId", surveyId);
		gridView.addQueryParam("questionId", questionId);
		gridView.addObject("survey", survey);
		createButton(gridView);
		createHead(gridView);
		createBody(gridView, searchText, NumberUtil.getInt(questionId)); 
		gridView.setSearchPlaceholder("请输入名称");
		return gridView;
	}
	
	/**
	 * 创建按钮
	 * @param gridView  gridView
	 */
	private void createButton(GridView gridView) {
		gridView.addButton(Button.getAdd());
		gridView.addButton(Button.getRemove());
		gridView.addButton(Button.getBack());
	}
	
	/**
	 * 创建表头
	 * @param gridView  gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("name").setTitle("调查答案").setAlign("left") .setResizable(true));
		gridView.addHead(Head.getInstance().setField("count").setTitle("投票数").setAlign("center").setWidth(100));
		
	}
	
	/**
	 * 创建列表
	 * @param gridView   searchText
	 * @param searchText  searchText
	 */
	private void createBody(GridView gridView, String searchText, Integer questionId) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("a.iid").addSelectField("a.name").addSelectField("a.count").setTable(Tables.ANWSER + " a ");
		String where = " a.questionid=:questionId";
		gridViewSql.addParam("questionId", questionId);	
		if (StringUtil.isNotEmpty(searchText)) {
			where += " AND a.name LIKE :name  ";
			gridViewSql.addParam("name", searchText, LikeType.LR);	
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("iid", "DESC");
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		String iid = StringUtil.getString(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("name"));
		int count = NumberUtil.getInt(rowData.get("count"));
		gridRow.addCell("iid", iid);
		gridRow.addCell("name", name, Script.createScript("edit", iid, name));
		gridRow.addCell("count", count);
	}

}