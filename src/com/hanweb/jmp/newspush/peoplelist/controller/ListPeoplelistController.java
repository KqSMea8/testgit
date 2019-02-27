package com.hanweb.jmp.newspush.peoplelist.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.annotation.Permission;
import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.permission.Allowed;
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
import com.hanweb.complat.listener.UserSessionInfo;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.support.controller.CurrentUser;

/**
 * 群组列表页控制器
 * 
 * @author ZhangC
 * 
 */
@Controller
@Permission(module = "group", allowedGroup = Allowed.YES)
@RequestMapping("manager/qunzu")
public class ListPeoplelistController implements GridViewDelegate {

	@Autowired
	private GridViewService gridViewService;

	

	@Permission(function = "list")
	@RequestMapping("list")
	public GridView list(GridView gridView, Integer groupId, String groupName) {
		
		
		
		gridView.setDelegate(this);
		gridView.setViewName("jmp/newspush/peoplelist/peoplelist_list");
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
        Integer siteId = currentUser.getSiteId(); //网站ID
		createButton(gridView);
		createHead(gridView);
		createBody(gridView, siteId);
		gridView.setShowAdvSearch(false);
		gridView.setPosition("群组管理");
		gridView.setSearchPlaceholder("请输入群组名称");
		return gridView;
	}

	/**
	 * 创建按钮
	 * @param gridView
	 */
	private void createButton(GridView gridView) {
		gridView.addButton(Button.getAdd());
		gridView.addButton(Button.getRemove());
	}

	/**
	 * 创建表头
	 * 
	 * @param gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("name").setTitle("群组名称").setAlign("left")
				.setWidth(80).setResizable(true));
		gridView.addHead(Head.getInstance().setField("type").setTitle("成员设置").setAlign("center")
				.setWidth(80));
	}
	
	/**
	 * 创建列表
	 * 
	 * @param gridView
	 */
	private void createBody(GridView gridView, int siteId) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("type").addSelectField("name")
				.setTable(Tables.PEOPLELIST + " a");

		if (NumberUtil.getInt(siteId) != 0) {
            gridViewSql.setWhere("siteid =:siteId");
            gridViewSql.addParam("siteId", siteId);
        }

		if (StringUtil.isNotEmpty(gridView.getSearchText())) {
			gridViewSql.setWhere("name LIKE :name");
			gridViewSql.addParam("name", gridView.getSearchText(), LikeType.LR);
		}
		gridViewSql.addOrderBy("iid", "ASC");
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		String iid = StringUtil.getString(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("name"));
		int type = NumberUtil.getInt(rowData.get("type"));

		gridRow.addCell("iid", iid);
		gridRow.addCell("name", name, Script.createScript("edit", iid, name));
		gridRow.addCell("type","<i class=\"iconfont link\">&#xf1012;</i>",Script.createScript("setMembers", iid), false);
	}
}