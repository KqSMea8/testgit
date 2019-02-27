package com.hanweb.jmp.apps.controller.broke;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
import com.hanweb.jmp.constant.Tables;

@Controller
//@Permission(module = "broke", allowedAdmin = Allowed.YES)
@RequestMapping("manager/broke")
public class ListBrokeController implements GridViewDelegate {
	
	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;

	/**
	 * 报料信息列表
	 * @param request    request
	 * @param gridView   gridView
	 * @param typeName   typeName
	 * @param typeId     typeId
	 * @return  GridView
	 */
	@RequestMapping("list")
	public GridView list(HttpServletRequest request, GridView gridView, String typeName, Integer typeId) {
		gridView.setDelegate(this);
		gridView.setViewName("jmp/apps/broke/broke_list");
		gridView.setShowAdvSearch(false);
		gridView.setSearchPlaceholder("请输入内容");
		gridView.addQueryParam("typeName", typeName);
		gridView.addQueryParam("typeId", typeId);
		createButton(gridView);
		createHead(gridView);
		createBody(gridView, gridView.getSearchText(), typeId);
		return gridView;
	}

	/**
	 * 创建表头
	 * @param gridView  gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("title").setTitle("标题").setAlign("left"));
		gridView.addHead(Head.getInstance().setField("createtime").setTitle("创建时间").setAlign("center").setWidth(100));
		gridView.addHead(Head.getInstance().setField("isaudit").setTitle("审核状态").setAlign("center").setWidth(40));
		gridView.addHead(Head.getInstance().setField("reply").setTitle("回复状态").setAlign("center").setWidth(40));
		gridView.addHead(Head.getInstance().setField("isopen").setTitle("公开状态").setAlign("center").setWidth(40));
		gridView.addHead(Head.getInstance().setField("ip").setTitle("IP地址").setAlign("center").setWidth(60));
		gridView.addHead(Head.getInstance().setField("operator").setTitle("操作").setAlign("center").setWidth(60));
	}

	/**
	 * 创建按钮
	 * @param gridView  gridView
	 */
	private void createButton(GridView gridView) {
		gridView.addButton(Button.getAdd());
		gridView.addButton(Button.getRemove());
		gridView.addButton(Button.getInstance("&#xf3004;", "audit", "审核"));
		gridView.addButton(Button.getInstance("&#xf3003;", "unaudit", "撤审"));
		gridView.addButton(Button.getImport());
		gridView.addButton(Button.getExport());
	}

	/**
	 * 创建列表
	 * @param gridView    gridView
	 * @param searchName  searchName
	 * @param typeId      typeId
	 */
	private void createBody(GridView gridView, String searchName, Integer typeId) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("title").addSelectField("createtime")
				   .addSelectField("isopen").addSelectField("isaudit").addSelectField("ip")
				   .addSelectField("reply").setTable(Tables.BROKE);
		String where = " classid=:typeid";
		if (StringUtil.isNotEmpty(searchName)) {
			where += " AND content LIKE :content";
			gridViewSql.addParam("content", "%" + searchName + "%");
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("iid", GridViewSql.SORT_DESC);
		gridViewSql.addParam("typeid", typeId);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		String iid = StringUtil.getString(rowData.get("iid"));
		String title = StringUtil.getString(rowData.get("title"));
		String time = StringUtil.getString(rowData.get("createtime"));
		Integer isaudit = NumberUtil.getInt((rowData.get("isaudit")));
		Integer isOpen = NumberUtil.getInt((rowData.get("isopen")));
		String reply = StringUtil.getString(rowData.get("reply"));
		String ip = StringUtil.getString(rowData.get("ip"));
		if(StringUtil.isEmpty(reply)){
			gridRow.addCell("reply", "<font style=\"color: red;\">未回复</font>", false);
		}else{
			gridRow.addCell("reply", "<font style=\"color: blue;\">已回复</font>", false);
		}
		gridRow.addCell("iid", iid);
		gridRow.addCell("title", title, Script.createScript("edit", iid));
		gridRow.addCell("createtime", time);
		String isAudit = "<font style=\"color: red;\">未审核</font>";
		if(isaudit == 1){
			isAudit = "<font style=\"color: blue;\">已审核</font>";
		}
		gridRow.addCell("isaudit", isAudit, false);
		String isopen = "<font style=\"color: red;\">不公开</font>";
		if(isOpen == 1){
			isopen = "<font style=\"color: blue;\">公开</font>";
		}
		gridRow.addCell("isopen", isopen, false);
		gridRow.addCell("ip", ip);
		String auditState = "";  
		if (isaudit == 1) {
			auditState = "撤审";
		} else {
			auditState = "审核";
		}
		gridRow.addCell("operator",
				"<input type='button' class='btn btn-success btn-small' onclick='audit(" + iid
						+ "," + isaudit + ")' " + "value='" + auditState + "'></input>"
						+ "&nbsp;&nbsp;<input type='button' class='btn btn-success btn-small' "
						+ "onclick='comment(\"" + iid + "\")' value='评论'></input>", false);
	}
	
}