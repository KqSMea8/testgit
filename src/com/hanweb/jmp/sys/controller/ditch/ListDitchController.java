package com.hanweb.jmp.sys.controller.ditch;

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
@RequestMapping("manager/ditch")
public class ListDitchController implements GridViewDelegate{
	
	/**
	 * gridViewService
	 */
	@Autowired
	GridViewService gridViewService;
	
	/**
	 * 渠道管理列表 
	 * @param gridView gridView
	 * @return GridView
	 */
	@RequestMapping("list")
	public GridView list(GridView gridView) {
		gridView.setDelegate(this);
		gridView.setViewName("jmp/sys/ditch/ditch_list");
		createButton(gridView);
		createHead(gridView);
		createBody(gridView);
		gridView.setSearchPlaceholder("请输入接口地址");
		gridView.setShowAdvSearch(false);
		return gridView;
	}
	
	private void createButton(GridView gridView) {
		gridView.addButton(Button.getAdd());
		gridView.addButton(Button.getRemove());
	}
	
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("id").setTitle("ID").setAlign("center").setWidth(15).setTip(true));
		gridView.addHead(Head.getInstance().setField("url").setTitle("接口地址").setAlign("center").setWidth(200));
		gridView.addHead(Head.getInstance().setField("name").setTitle("用户名").setAlign("center").setWidth(100));
		gridView.addHead(Head.getInstance().setField("createtime").setTitle("创建时间").setAlign("center").setWidth(100));
		gridView.addHead(Head.getInstance().setField("enable").setTitle("是否展示").setAlign("center").setWidth(100));
		gridView.addHead(Head.getInstance().setField("operation").setTitle("同步字段设置").setAlign("center").setWidth(100));
	}

	private void createBody(GridView gridView) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("url").addSelectField("name")
				   .addSelectField("createtime").addSelectField("enable").setTable(Tables.DITCH); 
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		String where = " siteid=:siteId "; 
		//检索
		String url = gridView.getSearchText(); 
		if(StringUtil.isNotEmpty(url)){
			where += " AND url LIKE :url";
		}
		gridViewSql.addParam("url", "%" + url + "%");
		gridViewSql.addParam("siteId", currentUser.getSiteId());
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("iid", GridViewSql.SORT_ASC);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		Integer iid = NumberUtil.getInt(rowData.get("iid"));
		String url = StringUtil.getString(rowData.get("url"));
		String name = StringUtil.getString(rowData.get("name"));
		Integer enable = NumberUtil.getInt(rowData.get("enable"));
		String createtime = DateUtil.dateToString((Date) rowData.get("createtime"),DateUtil.YYYY_MM_DD_HH_MM_SS);
		gridRow.addCell("iid", iid);
		gridRow.addCell("id", iid);
		gridRow.addCell("url", url, Script.createScript("edit", iid));
		gridRow.addCell("name", name);
		gridRow.addCell("createtime", createtime);
		gridRow.addCell("operation",
                "<input type='button' class='btn btn-success btn-small' onclick='synField(" + iid
                        + ")' " + "value='" + "设置" + "'></input>", false);
		gridRow.addCell("enable", "<input name=\"enable\" type=\"hidden\" value=\"" + enable
                + "\" data=\"{iid:" + iid + "}\"/>", false);
	}

}