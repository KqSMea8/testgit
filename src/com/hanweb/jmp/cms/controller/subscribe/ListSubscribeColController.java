package com.hanweb.jmp.cms.controller.subscribe;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.service.GridViewService;
import com.hanweb.common.util.DateUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
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
@RequestMapping(value = "manager/sign/subscribecol")

public class ListSubscribeColController implements GridViewDelegate{
	
	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;

	/**
	 * 列表
	 * @param gridView  gridView
	 * @param request   request
	 * @param did      did
	 * @return  GridView
	 */
	@RequestMapping(value = "list")
	public GridView list(GridView gridView, HttpServletRequest request, Integer did,String name) {
		gridView.setDelegate(this);
		gridView.setViewName("/jmp/cms/subscribe/subscribecol_list");
		createHead(gridView, request);
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId(); //网站ID
		createBody(gridView, NumberUtil.getInt(did), siteId);
		createButton(gridView, NumberUtil.getInt(did));
		gridView.addQueryParam("did", did);
		gridView.addQueryParam("name", name);
		gridView.setSearchPlaceholder("请输入栏目名称");
		gridView.setShowAdvSearch(false);
		return gridView;
	}

	/**
	 * 创建表头
	 * @param gridView   gridView
	 * @param request    request
	 */
	public void createHead(GridView gridView, HttpServletRequest request) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("id1").setTitle("ID").setAlign("center").setWidth(15).setTip(true));
		gridView.addHead(Head.getInstance().setField("name").setTitle("栏目名称").setAlign("left").setWidth(200).setResizable(true));
		gridView.addHead(Head.getInstance().setField("enable").setTitle("启用状态").setAlign("center").setWidth(80)); 
		gridView.addHead(Head.getInstance().setField("createTime").setTitle("创建时间").setAlign("center").setWidth(100));
	}
	
	/**
	 * 创建列表
	 * @param gridView  gridView
	 * @param did       did
	 * @param siteId    siteId
	 */
	private void createBody(GridView gridView, Integer did, Integer siteId) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("c.iid ").addSelectField("a.name").addSelectField("a.enable")
			       .addSelectField("a.createtime").setTable(Tables.COL + " a, " + Tables.DIMENSIONREL + " c");
		String where = " a.iid=c.attrid AND c.siteid=:siteid AND c.moduleid=2 ";
		if(did > 0){
			where += "AND c.dimensionid=:did";
			gridViewSql.addParam("did", did);
		}
		gridViewSql.addParam("siteid", siteId);
		String colName = gridView.getSearchText();
		if (StringUtil.isNotEmpty(colName)) {
			where += " AND a.name LIKE :name";
			gridViewSql.addParam("name", "%" + colName + "%");
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("c.orderid", GridViewSql.SORT_ASC);
		gridViewService.find(gridViewSql);
	}

	/**
	 * 创建按钮
	 * @param gridView gridView
	 * @param did   did
	 */
	public void createButton(GridView gridView, int did) { 
		if(did > 0){
			gridView.addButton(Button.getAdd());
			Button sort = Button.getInstance("&#xf5036;", "order", "排序");
			gridView.addButton(sort);
		}
		gridView.addButton(Button.getRemove());
		
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		String iid = StringUtil.getString(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("name"));
		int enable = NumberUtil.getInt(rowData.get("enable"));
		String createTime = DateUtil.dateToString((Date) rowData.get("createtime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		gridRow.addCell("iid", iid);
		gridRow.addCell("id1", iid);
		gridRow.addCell("name", name);
		String isEnable = "停用";
		if(enable == 1){
			isEnable = "启用";
		}
		gridRow.addCell("enable", isEnable);
		gridRow.addCell("createTime", createTime);
	}
	
}