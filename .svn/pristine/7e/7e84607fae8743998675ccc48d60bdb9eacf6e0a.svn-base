package com.hanweb.jmp.apps.controller.numbersense.numsensephone;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

import com.hanweb.jmp.constant.Tables;

@Controller
@RequestMapping("manager/plugins/numsense/phone")
public class ListNumSensePhoneController implements GridViewDelegate {
	
	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;
	
	/**
	 * 通讯录号码列表页
	 * @param gridView gridView
	 * @param colId 分类ID
	 * @param colName 分类名称
	 * @return gridView
	 */
	@SuppressWarnings("deprecation")
    @Permission(function = "list")
	@RequestMapping("list")
	public GridView list(GridView gridView, String colId, String colName) {
		String searchText = gridView.getSearchText();
		searchText = StringUtil.getStringTrim(searchText);
		gridView.setSearchText(searchText);
		gridView.setShowAdvSearch(false);
		if(StringUtil.isNotEmpty(colId)){
			colId = colId.replace("p", "");
		}
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		gridView.setDelegate(this);
		gridView.setViewName("jmp/apps/numbersense/phone_list");
		gridView.addQueryParam("siteId", siteId);
		gridView.addQueryParam("colId", NumberUtil.getInt(colId) + "");
		gridView.addQueryParam("colName", colName);
		createButton(gridView);
		createHead(gridView);
		createBody(gridView, NumberUtil.getInt(colId), siteId, searchText);
		gridView.setSearchPlaceholder("请输入名称");
		return gridView;
	}
	
	/**
	 * 创建按钮
	 * @param colId  colId
	 * @param gridView	gridView
	 */
	private void createButton(GridView gridView) {
		gridView.addButton(Button.getAdd());
		gridView.addButton(Button.getRemove());
		Button sort = Button.getInstance("&#xf5031;", "order", "排序");
		gridView.addButton(sort);
	}
	
	/**
	 * 创建表头
	 * @param gridView	gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("name").setTitle("名称").setAlign("left").setWidth(100));
		gridView.addHead(Head.getInstance().setField("phone").setTitle("固定电话").setAlign("left").setWidth(100));
		gridView.addHead(Head.getInstance().setField("tel").setTitle("移动电话").setAlign("left").setWidth(100));
		gridView.addHead(Head.getInstance().setField("createtime").setTitle("创建时间").setAlign("center").setWidth(100));
	}
	
	/**
	 * 创建列表
	 * @param gridView gridView
	 * @param colId	分类ID
	 * @param siteId	网站ID
	 * @param searchText	查询条件
	 */
	private void createBody(GridView gridView, int colId, Integer siteId, String searchText) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("name").addSelectField("phone")
		           .addSelectField("tel").addSelectField("createtime").setTable(Tables.NUMSENSEPHONE);
		StringBuilder where = new StringBuilder();
		if(siteId > 0){
			where.append(" siteid=:siteid");
			gridViewSql.addParam("siteid", siteId);
		}
		if (colId > 0 && siteId > 0) {
			where.append(" AND colid = :colid");
			gridViewSql.addParam("colid", colId);
		}else if(colId > 0 && siteId <= 0){
			where.append(" colid = :colid");
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
		String phone = StringUtil.getString(rowData.get("phone"));
		String tel = StringUtil.getString(rowData.get("tel"));
		String createTime = DateUtil.dateToString((Date) rowData.get("createtime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		gridRow.addCell("iid", iid);
		gridRow.addCell("name", name, Script.createScript("edit", iid));
		gridRow.addCell("phone", phone);
		gridRow.addCell("tel", tel);
		gridRow.addCell("createtime", createTime);
	}
	
}