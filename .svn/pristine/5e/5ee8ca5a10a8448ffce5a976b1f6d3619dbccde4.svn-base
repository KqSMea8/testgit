package com.hanweb.jmp.apps.controller.survey;

import java.text.DecimalFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.annotation.Permission;
import com.hanweb.common.service.GridViewService;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.view.grid.GridRow;
import com.hanweb.common.view.grid.GridView;
import com.hanweb.common.view.grid.GridViewDelegate;
import com.hanweb.common.view.grid.GridViewSql;
import com.hanweb.common.view.grid.Head;
import com.hanweb.complat.listener.UserSessionInfo;

import com.hanweb.jmp.apps.entity.survey.SurveyResult;
import com.hanweb.jmp.apps.service.survey.SurveyResultService;
import com.hanweb.jmp.constant.Tables;

@Controller
@Permission
@RequestMapping("manager/plugins/surveyresult")
public class ListSurveyResultDetailController implements GridViewDelegate{
	
	/** 
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;
	
	/**
	 * surveyResultService
	 */
	@Autowired
	private SurveyResultService surveyResultService;
	
	/**
	 * 列表
	 * @param gridView  gridView
	 * @return  GridView
	 */
	@RequestMapping("detail_list")
	public GridView list(GridView gridView, Integer iid, Integer type) {
		int siteId = UserSessionInfo.getCurrentUser().getSiteId();
		SurveyResult surveyResult =  surveyResultService.findByIid(NumberUtil.getInt(iid), siteId);
		String searchText = gridView.getSearchText();
		searchText = StringUtil.getStringTrim(searchText);
		gridView.setSearchText(searchText);
		gridView.setShowAdvSearch(false);
		gridView.setShowSimpleSearch(false);
		gridView.setDelegate(this);
		gridView.setViewName("jmp/apps/survey/surveyresultdetail_list");
		gridView.addQueryParam("iid", iid);
		createButton(gridView);
		createHead(gridView, NumberUtil.getInt(type));
		createBody(gridView, searchText, surveyResult.getQuestionId(), NumberUtil.getInt(type)); 
		gridView.setSearchPlaceholder("请输入名称");
		return gridView;
	}
	
	/**
	 * 创建按钮
	 * @param gridView  gridView
	 */
	private void createButton(GridView gridView) {
	    
	}
	
	/**
	 * 创建表头
	 * @param gridView  gridView
	 */
	private void createHead(GridView gridView, Integer type) {
		if(type == 1 || type == 2){
			gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
			gridView.addHead(Head.getInstance().setField("name").setTitle("答案").setAlign("left") .setResizable(true));
			gridView.addHead(Head.getInstance().setField("count").setTitle("票数").setAlign("center").setWidth(100));
			gridView.addHead(Head.getInstance().setField("percent").setTitle("百分比").setAlign("center").setWidth(100));
		}else{
			gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
			gridView.addHead(Head.getInstance().setField("content").setTitle("问题答案").setAlign("left") .setResizable(true));
		}
		
		
	}
	
	/**
	 * 创建列表
	 * @param gridView   searchText
	 * @param searchText  searchText
	 */
	private void createBody(GridView gridView, String searchText, Integer questionId, Integer type) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		String where = "";
		if(type == 1 || type == 2){
			gridViewSql.addSelectField("a.iid").addSelectField("a.name").addSelectField("a.count")
			           .addSelectField("(SELECT b.sum FROM " + Tables.SURVEYRESULT 
					   + " b  WHERE a.questionid = b.questionid AND a.surveyid = b.surveyid) answersum")
			           .addSelectField("(SELECT c.type FROM " + Tables.QUESTION 
					   + " c  WHERE a.questionid = c.iid AND a.surveyid = c.surveyid) questiontype")		 
			           .setTable(Tables.ANWSER + " a " );
		}else{
			gridViewSql.addSelectField("a.iid").addSelectField("a.content").setTable(Tables.SURVEYANSWER + " a ");
		}
		where += " a.questionid=:questionId";
		gridViewSql.addParam("questionId", questionId);	
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("iid", "DESC");
		gridViewService.find(gridViewSql);
		
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		int type = NumberUtil.getInt(rowData.get("questiontype"));
		String iid = StringUtil.getString(rowData.get("iid"));
		if(type == 1 || type == 2){
			String answername = StringUtil.getString(rowData.get("name"));
			DecimalFormat df   = new DecimalFormat("######0.00"); 
			int answercount =  NumberUtil.getInt(rowData.get("count"));
			int questionsum =  NumberUtil.getInt(rowData.get("answersum"));
			int percent =  answercount*10000/questionsum;
			if(questionsum == 0){
				gridRow.addCell("percent", "0%");
			}else{
				gridRow.addCell("percent",  df.format((double)percent/100) + "%");
			}
			gridRow.addCell("name", answername);
			gridRow.addCell("count", answercount);
		}else{
			String content = StringUtil.getString(rowData.get("content"));
			gridRow.addCell("content", content);
		}
		gridRow.addCell("iid", iid);
	}

}