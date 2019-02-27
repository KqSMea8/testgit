package com.hanweb.jmp.sys.controller.version;

import java.util.Date;
import java.util.Map;

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
import com.hanweb.jmp.constant.Tables;
import com.hanweb.support.controller.CurrentUser;

@Controller
//@Permission(module = "version", allowedGroup = Allowed.YES)
@RequestMapping("manager/version")
public class ListVersionController implements GridViewDelegate {
	
	/**
	 * gridViewService
	 */
	@Autowired
	GridViewService gridViewService;

	/**
	 * 版本更新列表 
	 * @param gridView gridView
	 * @return GridView
	 */
	@RequestMapping("list")
	public GridView list(GridView gridView) {
		gridView.setDelegate(this);
		gridView.setViewName("jmp/sys/version/version_list");
		createButton(gridView);
		createHead(gridView);
		createBody(gridView);
		gridView.setSearchPlaceholder("请输入客户端类型");
		gridView.setShowAdvSearch(false);
		return gridView;
	}

	/**
	 * 创建按钮
	 * @param gridView gridView
	 */
	private void createButton(GridView gridView) {
		gridView.addButton(Button.getAdd());
		gridView.addButton(Button.getRemove());
	}

	/**
	 * 创建表头
	 * @param gridView gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("clienttype").setTitle("客户端类型").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("version").setTitle("版本").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("createtime").setTitle("创建时间").setAlign("center").setWidth(70));
		gridView.addHead(Head.getInstance().setField("msg").setTitle("更新说明").setAlign("center"));
	}

	/**
	 * 创建列表
	 * 
	 * @param gridView gridView
	 */
	private void createBody(GridView gridView) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("clienttype").addSelectField("version")
				   .addSelectField("msg").addSelectField("createtime").setTable(Tables.UPDATE); 
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		String where = " siteid=:siteId "; 
		gridViewSql.addParam("siteId", currentUser.getSiteId());
		String clienttype = gridView.getSearchText();
		if (StringUtil.isNotEmpty(clienttype)) {
			Integer type = 0;
			if ("windows".equals(clienttype.toLowerCase())) {
				type = 1;
			} else if ("iphone".equals(clienttype.toLowerCase())) {
				type = 2;
			} else if ("android".equals(clienttype.toLowerCase())) {
				type = 3;
			} else if ("ipad".equals(clienttype.toLowerCase())) {
				type = 4;
			}
			where +=" AND clienttype=:clienttype ";
			gridViewSql.addParam("clienttype", type);
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("iid", GridViewSql.SORT_DESC);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		Integer iid = NumberUtil.getInt(rowData.get("iid"));
		Integer clienttype = NumberUtil.getInt(rowData.get("clienttype"));
		String version = StringUtil.getString(rowData.get("version"));
		String msg = StringUtil.getString(rowData.get("msg"));
		String createtime = DateUtil.dateToString((Date) rowData.get("createtime"),DateUtil.YYYY_MM_DD_HH_MM_SS);
		String clientname = "";
		switch (clienttype) {
		case 1:
			clientname = "Windows";
			break;
		case 2:
			clientname = "iPhone";
			break;
		case 3:
			clientname = "Android";
			break;
		case 4:
			clientname = "iPad";
			break;
		}
		gridRow.addCell("iid", iid);
		gridRow.addCell("clienttype", clientname, Script.createScript("edit", iid));
		gridRow.addCell("version", version);
		gridRow.addCell("msg", msg);
		gridRow.addCell("createtime", createtime);
	}

}