package com.hanweb.jmp.apps.controller.broke;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.service.GridViewService;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.Script;
import com.hanweb.common.view.grid.Button;
import com.hanweb.common.view.grid.GridRow;
import com.hanweb.common.view.grid.GridView;
import com.hanweb.common.view.grid.GridViewDelegate;
import com.hanweb.common.view.grid.GridViewSql;
import com.hanweb.common.view.grid.Head;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.support.controller.CurrentUser;

@Controller
//@Permission(module = "broke", allowedAdmin = Allowed.YES)
@RequestMapping("manager/broketype")
public class ListBrokeTypeController implements GridViewDelegate {
	
	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;

	/**
	 * 报料分类列表
	 * @param request  request
	 * @param gridView  gridView
	 * @param brokeTypeName  brokeTypeName
	 * @return GridView
	 */
	@RequestMapping("list")
	public GridView list(HttpServletRequest request, GridView gridView, String brokeTypeName) {
		gridView.setDelegate(this);
		gridView.setViewName("jmp/apps/broke/broketype_list");
		gridView.setSearchPlaceholder("请输入分类名称");
		gridView.setShowAdvSearch(false);
		gridView.addQueryParam("brokeTypeName", brokeTypeName);
		createButton(gridView);
		createHead(gridView);
		createBody(gridView, gridView.getSearchText());
		return gridView;
	}

	/**
	 * 创建按钮
	 * @param gridView  gridView
	 */
	private void createButton(GridView gridView) {
		gridView.addButton(Button.getAdd());
		gridView.addButton(Button.getRemove());
		gridView.addButton(Button.getInstance("&#xf5036;", "sort", "排序"));
	}

	/**
	 * 创建表头
	 * @param gridView   gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("name").setTitle("分类名称").setAlign("left"));
		gridView.addHead(Head.getInstance().setField("colname").setHidden(true));
		gridView.addHead(Head.getInstance().setField("createtime").setTitle("创建时间").setAlign("center").setWidth(120));
	}

	/**
	 * 创建列表
	 * @param gridView   gridView
	 * @param searchName searchName
	 */
	private void createBody(GridView gridView, String searchName) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("name").addSelectField("createtime").setTable(Tables.BROKETYPE);
		String where = " siteid=:siteid";
		if (StringUtil.isNotEmpty(searchName)) {
			where += " AND name LIKE :name";
			gridViewSql.addParam("name", "%" + searchName + "%");
		}
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		gridViewSql.addParam("siteid", currentUser.getSiteId());
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("orderid", GridViewSql.SORT_ASC);
		gridViewSql.addOrderBy("iid", GridViewSql.SORT_ASC);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		String iid = StringUtil.getString(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("name"));
		String createtime = DateUtil.dateToString((Date) rowData.get("createtime"), 
				            DateUtil.YYYY_MM_DD_HH_MM_SS);
		gridRow.addCell("iid", iid);
		gridRow.addCell("name", name, Script.createScript("edit", iid, name));
		gridRow.addCell("colname", name);
		gridRow.addCell("createtime", createtime);
	}
	
}