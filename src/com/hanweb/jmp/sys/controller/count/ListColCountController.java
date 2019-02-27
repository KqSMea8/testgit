package com.hanweb.jmp.sys.controller.count;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.service.GridViewService;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.view.grid.GridRow;
import com.hanweb.common.view.grid.GridView;
import com.hanweb.common.view.grid.GridViewDelegate;
import com.hanweb.common.view.grid.GridViewSql;
import com.hanweb.common.view.grid.Head;

import com.hanweb.jmp.constant.Tables;

@Controller
@RequestMapping("manager/colcount")
public class ListColCountController implements GridViewDelegate {
	
	/**
	 *  gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;

	/**
	 * 统计列表
	 * @param request    request
	 * @param gridView   gridView
	 * @param siteName   siteName
	 * @param siteid     siteid
	 * @return   GridView
	 */
	@RequestMapping("list")
	public GridView list(HttpServletRequest request, GridView gridView, String siteName, Integer siteid) {
		gridView.setDelegate(this);
		gridView.setShowAdvSearch(false);
		gridView.setViewName("jmp/sys/count/colcount_list");
		gridView.setSearchPlaceholder("请输入栏目名称");
		gridView.addQueryParam("siteName", siteName);
		gridView.addQueryParam("siteid", siteid);
		createHead(gridView);
		createBody(gridView, siteid);
		return gridView;
	}

	/**
	 * 创建表头
	 * @param gridView   gridView
	 */
	public void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid").setHidden(true));
		gridView.addHead(Head.getInstance().setField("colName").setTitle("栏目名称").setAlign("left"));
		gridView.addHead(Head.getInstance().setField("infoNum").setTitle("信息总数").setAlign("center").setWidth(70));
	}

	/**
	 * 创建列表
	 * 
	 * @param gridView  gridView
	 * @param searchName  searchName
	 * @param siteid  siteid
	 */
	public void createBody(GridView gridView, Integer siteid) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("a.iid").addSelectField("a.name").addSelectField(
				"(SELECT COUNT(b.iid) FROM " 
				+ Tables.INFO + siteid +" b WHERE b.colid = a.iid) infoNum")
				.setTable(Tables.COL + " a");
		String where = " siteid=:siteid";
		String name = gridView.getSearchText();
		if (StringUtil.isNotEmpty(name)) {
			where+= " AND name like :name";
			gridViewSql.addParam("name", "%" + name + "%");
		}
		gridViewSql.setWhere(where);
		gridViewSql.addParam("siteid", siteid);
		gridViewSql.addOrderBy("iid", GridViewSql.SORT_DESC);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer arg2) {
		Integer iid = NumberUtil.getInt(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("name"));
		Integer infoNum = NumberUtil.getInt(rowData.get("infoNum"));
		gridRow.addCell("iid", iid);
		gridRow.addCell("colName", name);
		gridRow.addCell("infoNum", infoNum);
	}
	
}