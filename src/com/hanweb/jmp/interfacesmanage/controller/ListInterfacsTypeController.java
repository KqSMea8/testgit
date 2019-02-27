package com.hanweb.jmp.interfacesmanage.controller;

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
import com.hanweb.complat.util.SqlUtil;
import com.hanweb.jmp.constant.Tables;

@Controller
@RequestMapping("manager/interfaceType")
public class ListInterfacsTypeController implements GridViewDelegate{
	
	@Autowired
	private GridViewService gridViewService;
	
	@Permission(function = "list")
	@RequestMapping("list")
	public GridView list(GridView gridView, Integer typeId, String typeName) {

		typeId = NumberUtil.getInt(typeId);
		String searchText = gridView.getSearchText();
		searchText = StringUtil.trim(searchText);

		gridView.setSearchText(searchText);
		gridView.setShowAdvSearch(false);

		gridView.setDelegate(this);
		gridView.setViewName("jmp/interfacesmanage/interfaceType_list");
		createButton(gridView, typeId);
		createHead(gridView);
		createBody(gridView, typeId);
		gridView.addQueryParam("typeId", typeId + "");

		gridView.addQueryParam("typeName", typeName);
		gridView.setSearchPlaceholder("请输入分类名称");
		return gridView;
	}
	
	
	
	/**
	 * 创建按钮
	 * 
	 * @param gridView
	 */
	private void createButton(GridView gridView, int typeId) {
		if (typeId >= 0) {
			gridView.addButton(Button.getAdd());
		}
		gridView.addButton(Button.getRemove());

	}

	/**
	 * 创建表头
	 * 
	 * @param gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("name").setTitle("分类名称")
				.setAlign("left").setWidth(200).setResizable(true));
		gridView.addHead(Head.getInstance().setField("pid").setTitle("上级分类id")
				.setHidden(true));
		gridView.addHead(Head.getInstance().setField("pname").setTitle("上级分类")
				.setAlign("left").setWidth(100));
	}

	/**
	 * 创建列表
	 * 
	 * @param gridView
	 */
	private void createBody(GridView gridView, int pid) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql
				.addSelectField("iid")
				.addSelectField("name")
				.addSelectField("pid")
				.addSelectField(
						"(SELECT name FROM " + Tables.INTERFACESTYPE
								+ " WHERE iid = a.pid) pname")
				.setTable(Tables.INTERFACESTYPE + " a");
		StringBuilder where = new StringBuilder();

		where.append("pid = :pid");
		gridViewSql.addParam("pid", pid);

		if (StringUtil.isNotEmpty(gridView.getSearchText())) {
			where.append(" AND name LIKE :name ");
			gridViewSql.addParam("name", gridView.getSearchText(), LikeType.LR);
		}
		gridViewSql.setWhere(SqlUtil.trimWhere(where));
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		String iid = StringUtil.getString(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("name"));
		String pid = StringUtil.getString(rowData.get("pid"));
		String pname = StringUtil.getString(rowData.get("pname"));

		gridRow.addCell("iid", iid);
		gridRow.addCell("name", name, Script.createScript("edit", iid, name));
		gridRow.addCell("pid", pid);
		gridRow.addCell("pname", pname);
	}

	
}
