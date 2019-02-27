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

import com.hanweb.jmp.apps.entity.survey.Survey;
import com.hanweb.jmp.apps.service.survey.SurveyService;
import com.hanweb.jmp.constant.Tables;

@Controller
@Permission
@RequestMapping("manager/plugins/survey/question")
public class ListQuestionController implements GridViewDelegate{
	
	/** 
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;
	
	/**
	 * surveyService
	 */
	@Autowired
	private SurveyService surveyService;
	
	/**
	 * 列表
	 * @param gridView  gridView
	 * @return  GridView
	 */
	@RequestMapping("list")
	public GridView list(GridView gridView, String surveyId) {
		String searchText = gridView.getSearchText();
		searchText = StringUtil.getStringTrim(searchText);
		gridView.setSearchText(searchText);
		gridView.setShowAdvSearch(false);
		gridView.setDelegate(this);
		gridView.setViewName("jmp/apps/survey/question_list");
		Survey survey = surveyService.findByIid(NumberUtil.getInt(surveyId));
		gridView.addObject("survey", survey);
		gridView.addQueryParam("surveyId", surveyId);
		createButton(gridView);
		createHead(gridView);
		createBody(gridView, searchText, survey.getIid()); 
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
		Button sort = Button.getInstance("&#xf5036;", "order", "排序");
	    gridView.addButton(sort);
	    gridView.addButton(Button.getBack());
	}
	
	/**
	 * 创建表头
	 * 
	 * @param gridView  gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("name").setTitle("调查问题").setAlign("left").setResizable(true));
		gridView.addHead(Head.getInstance().setField("isshow").setTitle("是否显示").setAlign("center").setWidth(100));
		gridView.addHead(Head.getInstance().setField("type").setTitle("显示样式").setAlign("center").setWidth(100));
		gridView.addHead(Head.getInstance().setField("time").setTitle("截止时间").setAlign("center").setWidth(100));
		gridView.addHead(Head.getInstance().setField("operation").setTitle("操作").setAlign("center").setWidth(100));	
	}
	
	/**
	 * 创建列表
	 * @param gridView   searchText
	 * @param searchText  searchText
	 */
	private void createBody(GridView gridView, String searchText, int surveyId) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("a.iid").addSelectField("a.name").addSelectField("a.isshow")
		           .addSelectField("a.surveyid").addSelectField("(SELECT b.endtime FROM " + Tables.SURVEY  + " b  WHERE a.surveyid = b.iid) time ")
		           .addSelectField("a.type").setTable(Tables.QUESTION + " a ");
		String where = " a.surveyid=:surveyId";
		gridViewSql.addParam("surveyId", surveyId);
		if (StringUtil.isNotEmpty(searchText)) {
			where += " AND a.name LIKE :name  ";
			gridViewSql.addParam("name", searchText, LikeType.LR);	
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("orderid", "DESC");
		gridViewService.find(gridViewSql);
		
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		String iid = StringUtil.getString(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("name"));
		String surveyid = StringUtil.getString(rowData.get("surveyid"));
		int type = NumberUtil.getInt(rowData.get("type"));
		int isShow = NumberUtil.getInt(rowData.get("isshow"));
		String endtime = StringUtil.getString(rowData.get("time"));
		String showType= "";
		String show = "";
		gridRow.addCell("iid", iid);
		gridRow.addCell("name", name, Script.createScript("edit", iid, name));
		if(isShow == 0){
			show = "显示";
		}else{
		    show = "不显示";
		}
		gridRow.addCell("isshow", show);
		gridRow.addCell("time", endtime);
		if(type == 1){
			showType = "单选";
		}else if(type == 2){
			showType = "多选";
		}else{
			showType = "文本";
		}
		gridRow.addCell("type", showType);
		gridRow.addCell("operation",
				"<input type='button' class='btn btn-success btn-small' " 
				+ "onclick='surveyAnswer(\"" + iid + "\", \"" + type + "\", \"" + surveyid 
				+ "\")' value='调查答案'></input>&nbsp;"  , false);
	}

}