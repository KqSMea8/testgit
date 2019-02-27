package com.hanweb.jmp.interfacesmanage.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.service.GridViewService;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
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
@RequestMapping("manager/interface")
public class ListInterfaceController implements GridViewDelegate {

	@Autowired
	private GridViewService gridViewService;

	@RequestMapping("list")
	public GridView list(GridView gridView, String typeId) {

		String searchText = gridView.getSearchText();
		searchText = StringUtil.trim(searchText);

		gridView.setSearchText(searchText);
		gridView.setDelegate(this);
		gridView.setViewName("jmp/interfacesmanage/interface_list");
		gridView.setSearchPlaceholder("请输入接口名称");
		gridView.setShowAdvSearch(false);
		gridView.addQueryParam("typeId", typeId);

		createButton(gridView);
		createHead(gridView);
		createBody(gridView, searchText, typeId);
		return gridView;
	}

	/**
	 * 创建按钮
	 * 
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
		
		gridView.addHead(Head.getInstance().setField("name").setTitle("接口名称")
				.setAlign("left").setAlign("left").setWidth(100)
				.setResizable(true));

		gridView.addHead(Head.getInstance().setField("typename")
				.setTitle("接口分类").setAlign("left").setWidth(80));

		gridView.addHead(Head.getInstance().setField("domain").setTitle("接口地址")
				.setAlign("left").setWidth(150));
		gridView.addHead(Head.getInstance().setField("type").setTitle("请求方式")
				.setAlign("left").setWidth(50));
		gridView.addHead(Head.getInstance().setField("interfaceid")
				.setTitle("操作").setWidth(50));
		

		

	}

	/**
	 * 创建列表
	 * 
	 * @param gridView
	 */
	private void createBody(GridView gridView, String searchText, String typeId) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql
				.addSelectField("iid")
				.addSelectField("name")
				.addSelectField("domain")
				.addSelectField("type")
				.addSelectField(
						"(SELECT name FROM " + Tables.INTERFACESTYPE
								+ " t WHERE typeid = t.iid) typename")
				.setTable(Tables.INTERFACES);
		StringBuilder where = new StringBuilder();
		where.append("1=1");
		int id = NumberUtil.getInt(typeId);
		if (id != 0) {
			where.append(" AND typeid = :typeid");
			gridViewSql.addParam("typeid", typeId);
		}
		if (StringUtil.isNotEmpty(gridView.getSearchText())) {
			where.append(" AND name LIKE :name");
			gridViewSql.addParam("name", gridView.getSearchText(), LikeType.LR);
		}
		
		gridViewSql.setWhere(SqlUtil.trimWhere(where));
		gridViewSql.addOrderBy("iid", "DESC");
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData,
			Integer index) {
		Integer iid = NumberUtil
				.getInt(StringUtil.getString(rowData.get("iid")));
		String name = StringUtil.getString(rowData.get("name"));
		String typeName = StringUtil.getString(rowData.get("typename"));
		String domain = StringUtil.getString(rowData.get("domain"));
		Integer type = NumberUtil.getInt(StringUtil.getString(rowData
				.get("type")));

		gridRow.addCell("iid", iid);
		

		gridRow.addCell("name", name, Script.createScript("edit", iid, name));
		gridRow.addCell("domain", domain);
		if (type == 0) {
			gridRow.addCell("type", "POST");
		} else if(type == 1) {
			gridRow.addCell("type", "GET");
		}else{
			gridRow.addCell("type", "WebService");
		}

		String button1 = "";
		button1 = "<input type='button' class='btn btn-success btn-small' "
				+ "  onclick='examine(" + iid + ")' value='请求'/>";
		gridRow.addCell("interfaceid", button1, false);
		gridRow.addCell("typename", typeName);
	}

}
