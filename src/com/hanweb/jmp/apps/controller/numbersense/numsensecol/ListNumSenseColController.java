package com.hanweb.jmp.apps.controller.numbersense.numsensecol;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.annotation.Permission;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.service.GridViewService;
import com.hanweb.common.util.DateUtil;
import com.hanweb.complat.util.SqlUtil;
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
import com.hanweb.support.controller.CurrentUser;

import com.hanweb.jmp.apps.entity.numbersense.NumSenseCol;
import com.hanweb.jmp.apps.service.numbersense.NumSenseColService;
import com.hanweb.jmp.constant.Tables;

/**
 * 号码通分类列表页
 * @author ZDJ
 */
@Controller
@RequestMapping("manager/plugins/numsense/col")
public class ListNumSenseColController implements GridViewDelegate {
		
	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;
	
	/**
	 * numSenseColService
	 */
	@Autowired
	private NumSenseColService numSenseColService;

	/**
	 * 通讯录分类列表页
	 * @param gridView gridView
	 * @param colId colId
	 * @param colName colName
	 * @return gridView
	 */
	@Permission(function = "list")
	@RequestMapping("list")
	public GridView list(GridView gridView, String colId, String colName) {
		String searchText = gridView.getSearchText();
		searchText = StringUtil.getStringTrim(searchText);
		gridView.setSearchText(searchText);
		gridView.setShowAdvSearch(false);
		if(StringUtil.isNotEmpty(colId)){
			colId = colId.replace("c", "");
			colId = colId.replace("p", "");
		}
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		gridView.setDelegate(this);
		gridView.setViewName("jmp/apps/numbersense/col_list");
		gridView.addQueryParam("siteId", siteId);
		gridView.addQueryParam("colId", NumberUtil.getInt(colId));
		createButton(gridView, NumberUtil.getInt(colId));
		createHead(gridView);
		createBody(gridView, searchText, siteId, NumberUtil.getInt(colId));
		gridView.setSearchPlaceholder("请输入分类名称");
		return gridView;
	}

	/**
	 * 创建按钮
	 * @param gridView gridView
	 * @param colId colId
	 */
	private void createButton(GridView gridView, Integer colId) {
		NumSenseCol col = numSenseColService.findById(colId);
		if(colId == 0 || NumberUtil.getInt(col.getType()) == 1) {
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
		gridView.addHead(Head.getInstance().setField("name").setTitle("分类名称").setAlign("left").setWidth(200));
		gridView.addHead(Head.getInstance().setField("type").setTitle("类型").setAlign("center").setWidth(200));
		gridView.addHead(Head.getInstance().setField("createTime").setTitle("创建时间").setAlign("center").setWidth(200));
	}
	
	/**
	 * 创建表体
	 * @param gridView gridView
	 * @param searchText 查询体
	 * @param siteId 网站ID
	 * @param colId colId 分类ID
	 */
	private void createBody(GridView gridView, String searchText, int siteId, int colId) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("name").addSelectField("createtime")
			       .addSelectField("type").addSelectField("orderid").setTable(Tables.NUMSENSECOL);
		StringBuilder where = new StringBuilder();
		if(siteId > 0){
			where.append(" siteid=:siteid");
			gridViewSql.addParam("siteid", siteId);
		}
		if(colId >= 0) {
			where.append(" AND pid=:colid");
			gridViewSql.addParam("colid", colId);
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
		if(NumberUtil.getInt(type) == 1){
			type = "虚拟类型";
		}else if(NumberUtil.getInt(type) == 2){
			type = "普通类型";
		}
		String createTime = DateUtil.dateToString((Date) rowData.get("createtime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		gridRow.addCell("iid", iid);
		gridRow.addCell("name", name, Script.createScript("edit", iid));
		gridRow.addCell("type", type);
		gridRow.addCell("createTime", createTime);
	}
	
}