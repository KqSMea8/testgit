package com.hanweb.jmp.cms.controller.matters.doc;

import java.util.Date;
import java.util.Map;

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
//@Permission(module = "info", allowedAdmin = Allowed.YES)
@RequestMapping("manager/matter/doc/recycle")

public class ListDocRecycleController implements GridViewDelegate{
	
	/**
	 * GridViewService
	 */
	@Autowired
	private GridViewService gridViewService;
	
	/**
	 * 列表
	 * @param gridView
	 * @param classId
	 * @param siteId
	 * @return
	 */
	@RequestMapping("list")
	public GridView list(GridView gridView, Integer classId, Integer siteId,String name) {
		siteId = UserSessionInfo.getCurrentUser().getSiteId();
		gridView.setDelegate(this);
		gridView.setViewName("jmp/cms/matters/doc/recycle_list");
		gridView.setShowAdvSearch(false);
		gridView.setSearchPlaceholder("请输入名称");
		createButton(gridView, NumberUtil.getInt(classId));
		createHead(gridView);
		createBody(gridView, NumberUtil.getInt(classId), gridView.getSearchText());
		gridView.addObject("siteId", siteId);
		gridView.addQueryParam("classId", StringUtil.getString(classId)); 
		gridView.addObject("name", name);
		gridView.addObject("classId", NumberUtil.getInt(classId));
		return gridView;
	}
	
	/**
	 * 创建按钮
	 * @param gridView   gridView
	 * @param classId      id
	 */
	private void createButton(GridView gridView, int classId) {
		gridView.addButton(Button.getRemove());
		gridView.addButton(Button.getClean());
		Button restore = Button.getInstance("&#xf0035;", "restore", "还原");
	    gridView.addButton(restore);
	    gridView.addButton(Button.getBack());
	}
	
	/**
	 * 创建表头 
	 * @param gridView    gridView
	 */
	private void createHead(GridView gridView) { 
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("name").setTitle("附件名称").setAlign("left").setResizable(true).setTip(true).setWidth(100)); 
		gridView.addHead(Head.getInstance().setField("removetime").setTitle("删除时间").setAlign("center").setWidth(100)); 
	}
	
	/**
	 * 创建列表
	 * @param gridView    gridView
	 */
	private void createBody(GridView gridView, int classId, String searchText) { 
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		gridViewSql.addSelectField("b.iid").addSelectField("b.name")
				   .addSelectField("(select b.removetime from " +Tables.DOCCOL + " a where a.iid=b.classid)removetime ")
				   .setTable(Tables.DOC + " b");
		String where = "b.siteid=:siteId AND isremove=1"; 
		gridViewSql.addParam("siteId", currentUser.getSiteId());
		//分类
		if (NumberUtil.getInt(classId) > 0) {
			where += " AND  b.classId = :classId";
			gridViewSql.addParam("classId", classId);
		} 
		//检索
		if (StringUtil.isNotEmpty(searchText)) {
			where += " AND b.name LIKE :name ";
			gridViewSql.addParam("name", "%" + searchText + "%");
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("iid", GridViewSql.SORT_DESC);
		gridViewService.find(gridViewSql);
	}
	
	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) { 
		String iid = StringUtil.getString(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("name"));
		gridRow.addCell("iid", iid);
		gridRow.addCell("name", name);
		gridRow.addCell("removetime", DateUtil.dateToString((Date) rowData.get("removetime"), DateUtil.YYYY_MM_DD_HH_MM_SS));  
	}
	
}