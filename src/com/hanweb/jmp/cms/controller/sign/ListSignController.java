package com.hanweb.jmp.cms.controller.sign;

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
@RequestMapping(value = "manager/sign")
public class ListSignController implements GridViewDelegate {
	
	/**
	 * gridViewService
	 */
	@Autowired
	GridViewService gridViewService;

	/**
	 * 列表
	 * @param gridView  gridView
	 * @param mid  mid
	 * @param colId  colId
	 * @return  GridView
	 */
	@RequestMapping("list")
	public GridView list(GridView gridView, Integer mid, Integer colId){
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId();
		gridView.setDelegate(this);
		if(mid == 2){
			gridView.setViewName("jmp/cms/subscribe/subscribe_list");
			gridView.setSearchPlaceholder("请输入订阅名称");
		}else{
			gridView.setViewName("jmp/cms/sign/sign_list");
			gridView.setSearchPlaceholder("请输入角标名称");
		}
		createButton(gridView, mid);
		if(mid == 2){
			createHead1(gridView);
		}else{
			createHead(gridView);
		}
		createBody(gridView, mid, siteId, NumberUtil.getInt(colId));
		gridView.setPosition("角标定义");
		
		gridView.setShowAdvSearch(false);
		gridView.addObject("mid", mid);
		gridView.addQueryParam("mid", mid);
		gridView.addQueryParam("colId", NumberUtil.getInt(colId));
		return gridView;
	}
	
	/**
	 * 创建按钮
	 * 
	 * @param gridView  gridView
	 * @param mid  mid
	 */
	private void createButton(GridView gridView, Integer mid) { 
		if(NumberUtil.getInt(mid) > 0){
			gridView.addButton(Button.getAdd());
			gridView.addButton(Button.getRemove());
			Button sort = Button.getInstance("&#xf5036;", "order", "排序");
		    gridView.addButton(sort);
		} 
	}
	
	/**
	 * 创建表头
	 * @param gridView  gridView
	 */
	private void createHead1(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("id1").setTitle("ID").setAlign("center").setWidth(15).setTip(true));
		gridView.addHead(Head.getInstance().setField("dname").setTitle("订阅名称 ").setAlign("left").setResizable(true).setTip(true));
		gridView.addHead(Head.getInstance().setField("createtime").setTitle("创建时间 ").setAlign("center").setWidth(120).setTip(true));
		gridView.addHead(Head.getInstance().setField("info").setTitle("操作 ").setAlign("center").setWidth(120).setTip(true));
	}
	
	/**
	 * 创建表头
	 * @param gridView  gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("id1").setTitle("ID").setAlign("center").setWidth(15).setTip(true));
		gridView.addHead(Head.getInstance().setField("dname").setTitle("角标名称 ").setAlign("left").setResizable(true).setTip(true));
		gridView.addHead(Head.getInstance().setField("createtime").setTitle("创建时间 ").setAlign("center").setWidth(120).setTip(true));
		gridView.addHead(Head.getInstance().setField("info").setTitle("操作 ").setAlign("center").setWidth(120).setTip(true));
	}

	/**
	 * 创建列表
	 * @param gridView  gridView
	 * @param mid  mid
	 * @param siteId  siteId
	 * @param colId  colId
	 */
	private void createBody(GridView gridView, Integer mid, Integer siteId, int colId) {
		mid = NumberUtil.getInt(mid);
		siteId = NumberUtil.getInt(siteId);
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("dname").addSelectField("mname")
				   .addSelectField("createtime").addSelectField("mid").setTable(Tables.DIMENSION);
		String where = " siteid=:siteId";
		if(mid > 0){
			where += " AND mid=:mid ";
		}
		if(colId > 0){
			where += " AND colid=:colid ";
		}
		String dname = gridView.getSearchText();
		if (StringUtil.isNotEmpty(dname)) {
			where += " AND dname like :dname";
			gridViewSql.addParam("dname", "%" + dname + "%");
		}
		gridViewSql.addParam("siteId", siteId);
		gridViewSql.addParam("mid", mid);
		gridViewSql.addParam("colid", colId);
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("mid", GridViewSql.SORT_ASC);
		gridViewSql.addOrderBy("orderid", GridViewSql.SORT_ASC);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		Integer iid = NumberUtil.getInt(StringUtil.getString(rowData.get("iid")));
		String dname = StringUtil.getString(rowData.get("dname"));
		int mid = NumberUtil.getInt(rowData.get("mid"));
		String createtime = DateUtil.dateToString((Date) rowData.get("createtime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		gridRow.addCell("iid", iid);
		gridRow.addCell("id1", iid); 
	    gridRow.addCell("dname", dname, Script.createScript("edit", iid)); 
		gridRow.addCell("createtime", createtime);
		String btn = "";
		btn = "<a class=\"btn btn-success btn-small\"><i class=\"icon-cog\"></i>查看详情</a>";
		gridRow.addCell("info", btn, Script.createScript("goDetail", iid, dname, mid), false);
	}
	
}