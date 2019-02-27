package com.hanweb.jmp.appstype.controller;

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
import com.hanweb.common.util.mvc.Script;
import com.hanweb.common.view.grid.Button;
import com.hanweb.common.view.grid.GridRow;
import com.hanweb.common.view.grid.GridView;
import com.hanweb.common.view.grid.GridViewDelegate;
import com.hanweb.common.view.grid.GridViewSql;
import com.hanweb.common.view.grid.Head;
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.appstype.constant.Tables;
import com.hanweb.support.controller.CurrentUser;

/**
 * 应用分类列表页
 * @author ZCC
 *
 */
@Controller
@RequestMapping("manager/lightapptype")
public class ListLightAppTypeController implements GridViewDelegate{
	
	@Autowired
	private GridViewService gridViewService;
	
	/**
	 * 列表页
	 * @param gridView
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "list")
	public GridView list(GridView gridView, HttpServletRequest request, Integer typeId, String typeName) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser(); 
		typeId = NumberUtil.getInt(typeId);
		gridView.setDelegate(this);
		gridView.setViewName("/jmp/apps/lightapptype/lightapptype_list");
		createHead(gridView, request);
		createBody(gridView, currentUser.getSiteId(), typeId);
		createButton(gridView);
		gridView.addQueryParam("typeId", typeId);
		gridView.addObject("typeId", typeId);
		gridView.setSearchPlaceholder("请输入应用分类名称");
		gridView.setShowAdvSearch(false);
		return gridView;
	}
	
	/**
	 * 创建表头
	 * @param gridView  gridView
	 * @param request  request
	 */
	public void createHead(GridView gridView, HttpServletRequest request) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("id1").setTitle("ID").setAlign("center").setWidth(15));
		gridView.addHead(Head.getInstance().setField("name").setTitle("分类名称").setAlign("left").setResizable(true));
		gridView.addHead(Head.getInstance().setField("createTime").setTitle("创建时间").setAlign("center").setWidth(80));
	}
	
	/**
	 * 创建列表
	 * @param gridView  gridView
	 * @param name  name
	 * @param colId  colId
	 * @param siteId  siteId
	 * @param colText  colText
	 * @param colState  colState
	 * @param colType  colType
	 */
	private void createBody(GridView gridView, Integer siteId, Integer typeId) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("name").addSelectField("createtime")
		           .setTable(Tables.LIGHTAPPTYPE);
		String where = "";
		if (NumberUtil.getInt(siteId) != 0) {
			where += " siteid =:siteId";
			gridViewSql.addParam("siteId", siteId);
		}
		String name = gridView.getSearchText();
		if (StringUtil.isNotEmpty(name)) {
			where += " AND name like :name";
			gridViewSql.addParam("name", "%" + name + "%");
		}
		if(typeId>0){
			where += " AND pid =:typeid";
			gridViewSql.addParam("typeid", typeId);
		} else {
		    where += " AND pid =0";
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("orderid", GridViewSql.SORT_ASC);
		gridViewService.find(gridViewSql);
	}
	
	private boolean haveRight(CurrentUser currentUser, String url){
		if(currentUser.isSysAdmin() || currentUser.getIsWebSiteAdmin() 
				|| (currentUser.getPermissions() != null && currentUser.getPermissions().contains(url))){
			return true;
		}
		return false;
	}

	/**
	 * 创建按钮
	 * @param gridView  gridView
	 * @param colId  colId
	 */
	public void createButton(GridView gridView) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		if(currentUser.isSysAdmin() || currentUser.getIsWebSiteAdmin() 
				|| haveRight(currentUser, "lightapp") && !haveRight(currentUser, "broke") 
				&& !haveRight(currentUser, "numsense") && !haveRight(currentUser, "read")){
			
			gridView.addButton(Button.getAdd());
			gridView.addButton(Button.getRemove());
			Button sort = Button.getInstance("&#xf5036;", "order", "排序");
		    gridView.addButton(sort);
		}
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) { 
		String iid = StringUtil.getString(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("name")); 
		String createTime = DateUtil.dateToString((Date) rowData.get("createtime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		gridRow.addCell("iid", iid);
		gridRow.addCell("id1", iid);
		gridRow.addCell("name", name, Script.createScript("edit", iid));
		gridRow.addCell("createTime", createTime);
	}
	
}