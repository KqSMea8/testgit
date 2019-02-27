package com.hanweb.jmp.apps.controller.survey;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.annotation.Permission;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.service.GridViewService;
import com.hanweb.common.util.DateUtil;
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

@Controller
@Permission
@RequestMapping("manager/plugins/survey")
public class ListSurveyController implements GridViewDelegate{
	
	/** 
	 *   gridViewService
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
		gridView.setViewName("jmp/apps/survey/survey_list");
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
		gridView.addButton(Button.getAdd()); 
		gridView.addButton(Button.getRemove());
		Button sort = Button.getInstance("&#xf5036;", "order", "排序");
	    gridView.addButton(sort);
	    
	}
	
	/**
	 * 创建表头
	 * @param gridView  gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("name").setTitle("创建主题").setAlign("left").setResizable(true));
		gridView.addHead(Head.getInstance().setField("createtime").setTitle("创建时间").setAlign("center").setWidth(100));
		gridView.addHead(Head.getInstance().setField("endtime").setTitle("截止时间").setAlign("center").setWidth(100));
		gridView.addHead(Head.getInstance().setField("showtype").setTitle("展现类型").setAlign("center").setWidth(100));	
	}
	
	/**
	 * 创建列表
	 * @param gridView   searchText
	 * @param searchText  searchText
	 */
	private void createBody(GridView gridView, String searchText) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("a.iid").addSelectField("a.name").addSelectField("a.createtime")
		           .addSelectField("a.endtime") .addSelectField("a.showtype").setTable(Tables.SURVEY + " a ");
		String where = "";
		if (StringUtil.isNotEmpty(searchText)) {
			where += " name LIKE :name  ";
			gridViewSql.addParam("name", searchText, LikeType.LR);	
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("orderid", "ASC");
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		String iid = StringUtil.getString(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("name"));
		int type = NumberUtil.getInt(rowData.get("showtype"));
		String showtype = "";
		String createtime = DateUtil.dateToString((Date) rowData.get("createtime"), 
				            DateUtil.YYYY_MM_DD_HH_MM_SS);
		String endtime = DateUtil.dateToString((Date) rowData.get("endtime"), 
			 	         DateUtil.YYYY_MM_DD_HH_MM_SS);
		gridRow.addCell("iid", iid);
		gridRow.addCell("name", name, Script.createScript("edit", iid, name));
		if(type == 1){
			showtype = "九宫格版";
		}else{
			showtype = "列表版";
		}
		gridRow.addCell("createtime", createtime);
		gridRow.addCell("endtime", endtime);
		gridRow.addCell("showtype", showtype);
	}

}