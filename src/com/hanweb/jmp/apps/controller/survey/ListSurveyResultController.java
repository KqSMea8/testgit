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
import com.hanweb.common.view.grid.GridRow;
import com.hanweb.common.view.grid.GridView;
import com.hanweb.common.view.grid.GridViewDelegate;
import com.hanweb.common.view.grid.GridViewSql;
import com.hanweb.common.view.grid.Head;

import com.hanweb.jmp.constant.Tables;

@Controller
@Permission
@RequestMapping("manager/plugins/surveyresult")
public class ListSurveyResultController implements GridViewDelegate{
	
	/** 
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;
	
	/**
	 * 列表
	 * @param gridView  gridView
	 * @return  GridView
	 */
	@RequestMapping("list")
	public GridView list(GridView gridView) {
		String searchText = gridView.getSearchText();
		searchText = StringUtil.getStringTrim(searchText);
		gridView.setSearchText(searchText);
		gridView.setShowAdvSearch(false);
		gridView.setDelegate(this);
		gridView.setViewName("jmp/apps/survey/surveyresult_list");
		createButton(gridView);
		createHead(gridView);
		createBody(gridView, searchText); 
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
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("surveyname").setTitle("调查名称").setAlign("left").setResizable(true));
		gridView.addHead(Head.getInstance().setField("questionname").setTitle("问题名称").setAlign("left").setWidth(200));
		gridView.addHead(Head.getInstance().setField("sum").setTitle("总票数").setAlign("center").setWidth(100));
		gridView.addHead(Head.getInstance().setField("detail").setTitle("操作").setAlign("center").setWidth(100));
		
	}
	
	/**
	 * 创建列表
	 * @param gridView   searchText
	 * @param searchText  searchText
	 */
	private void createBody(GridView gridView, String searchText) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("a.iid").addSelectField("a.sum")
		.addSelectField("(SELECT b.name FROM " + Tables.SURVEY 
				 + " b  WHERE a.surveyid = b.iid) surveyname")
		.addSelectField("(SELECT c.name FROM " + Tables.QUESTION 
				 + " c  WHERE a.questionid = c.iid) questionname")
		.addSelectField("(SELECT c.type FROM " + Tables.QUESTION 
				 + " c  WHERE a.questionid = c.iid) questiontype")
		.setTable(Tables.SURVEYRESULT + " a, " + Tables.SURVEY + " b");
		String where = " a.surveyid= b.iid";
		if (StringUtil.isNotEmpty(searchText)) {
			where += " AND b.name LIKE :name  ";
			gridViewSql.addParam("name", searchText, LikeType.LR);	
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("iid", "DESC");
		gridViewService.find(gridViewSql);
		
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		String iid = StringUtil.getString(rowData.get("iid"));
		String surveyname = StringUtil.getString(rowData.get("surveyname"));
		String questionname = StringUtil.getString(rowData.get("questionname"));
		String sum = StringUtil.getString(rowData.get("sum"));
		int type = NumberUtil.getInt(rowData.get("questiontype"));
		gridRow.addCell("iid", iid);
		gridRow.addCell("surveyname", surveyname);
		gridRow.addCell("questionname", questionname);
		gridRow.addCell("sum", sum);
		String btn = "";
		btn = "<a class=\"btn btn-success btn-small\"><i class=\"icon-cog\"></i>查看详情</a>";
		gridRow.addCell("detail", btn, Script.createScript("goDetail", iid, type), false);
	}

}