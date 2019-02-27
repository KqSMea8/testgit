package com.hanweb.jmp.apps.controller.read;

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
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.complat.util.SqlUtil;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.support.controller.CurrentUser;

/**
 * 阅读列表页
 * @author ZDJ
 */
@Controller
@RequestMapping("manager/plugins/read")
public class ListReadController implements GridViewDelegate {
	
	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;
	
	/**
	 *  阅读列表
	 * @param gridView gridView
	 * @param colId colId
	 * @param colName  colName
	 * @param type  type
	 * @return gridView
	 */
	@Permission(function = "list")
	@RequestMapping("list")
	public GridView list(GridView gridView, String colId, String colName, String type) {
		String searchText = gridView.getSearchText();
		searchText = StringUtil.getStringTrim(searchText);
		gridView.setSearchText(searchText);
		gridView.setShowAdvSearch(false);
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		gridView.setDelegate(this);
		gridView.setViewName("/jmp/apps/read/read_list");
		gridView.addQueryParam("siteId", siteId);
		gridView.addQueryParam("colId", NumberUtil.getInt(colId));
		gridView.addObject("colId", colId);
		gridView.addObject("colName", colName);
		gridView.addObject("type", type);
		createButton(gridView, NumberUtil.getInt(type));
		createHead(gridView);
		createBody(gridView, NumberUtil.getInt(colId), searchText, siteId);
		gridView.setSearchPlaceholder("请输入名称");
		return gridView;
	}

	/**
	 * 创建按钮
	 * @param gridView gridView
	 * @param type type
	 */
	private void createButton(GridView gridView, Integer type) {
		if(type==0){
			gridView.addButton(Button.getAdd());
			gridView.addButton(Button.getRemove());
			Button sort = Button.getInstance("&#xf5036;", "order", "排序");
			gridView.addButton(sort);
		}
	}

	/**
	 * 创建表头
	 * @param gridView gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("name").setTitle("名称").setAlign("left").setWidth(200));
		gridView.addHead(Head.getInstance().setField("type").setTitle("类型").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("createTime").setTitle("创建时间").setAlign("center").setWidth(200));
	}
	
	/**
	 * 创建列表
	 * @param gridView gridView
	 * @param colId 分类ID
	 * @param searchText 查询条件
	 * @param siteId 网站ID
	 */
	private void createBody(GridView gridView, Integer colId, String searchText, int siteId) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("name").addSelectField("type")
		           .addSelectField("createtime").addSelectField("orderid").setTable(Tables.EBOOK);
		StringBuilder where = new StringBuilder();
		if(siteId > 0){
			where.append(" siteid=:siteid");
			gridViewSql.addParam("siteid", siteId);
		}
		if(colId >= 0){
			where.append(" AND pid=:pid");
			gridViewSql.addParam("pid", colId);
		}
		if (StringUtil.isNotEmpty(searchText)) {
			where.append(" AND (name LIKE :name)");
			gridViewSql.addParam("name", searchText, LikeType.LR);
		}
		gridViewSql.setWhere(SqlUtil.trimWhere(where));
		gridViewSql.addOrderBy("orderid", GridViewSql.SORT_ASC);
		gridViewSql.addOrderBy("iid", GridViewSql.SORT_DESC);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		String iid = StringUtil.getString(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("name"));
		String type = StringUtil.getString(rowData.get("type"));
		if("0".equals(type)){
			type = "分类";
		}else{
			type = "书籍";
		}
		String createTime = DateUtil.dateToString((Date) rowData.get("createtime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		gridRow.addCell("iid", iid);
		gridRow.addCell("name", name, Script.createScript("edit", iid));
		gridRow.addCell("type", type);
		gridRow.addCell("createTime", createTime);
	}
	
}