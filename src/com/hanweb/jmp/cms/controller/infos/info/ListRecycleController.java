package com.hanweb.jmp.cms.controller.infos.info;

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
import com.hanweb.jmp.cms.entity.cols.Col;
import com.hanweb.jmp.cms.service.cols.ColService;
import com.hanweb.jmp.constant.Tables;
import com.hanweb.support.controller.CurrentUser;

@Controller
@RequestMapping("manager/recycle")
public class ListRecycleController implements GridViewDelegate{
	
	/**
	 * GridViewService
	 */
	@Autowired
	private GridViewService gridViewService;
	
	/**
	 * 栏目操作类.
	 */
	@Autowired
	private ColService colService; 
	
	/**
	 * 列表
	 * @param gridView   gridView
	 * @param colId      栏目id
	 * @param colName    栏目名称
	 * @return GridView
	 */
	@RequestMapping("list")
	public GridView list(GridView gridView, String colId, String colName) {
		Col colEn = null; 
		int siteId = UserSessionInfo.getCurrentUser().getSiteId();
		if(StringUtil.isNotEmpty(colId) && !"0".equals(colId)){
			colEn = colService.findByIid(NumberUtil.getInt(colId));
			colName = colEn.getName(); 
		}
		if(StringUtil.isNotEmpty(colId) && !"0".equals(colId)){
			colEn = colService.findByIid(NumberUtil.getInt(colId));
			colName = colEn.getName(); 
		}
		if (StringUtil.isEmpty(colName)) {
			gridView.addQueryParam("colName", "全部信息");
		} else {
			gridView.addQueryParam("colName", colName);
		}
		gridView.setDelegate(this);
		gridView.setViewName("jmp/cms/infos/recycle_list");
		gridView.setShowAdvSearch(false);
		gridView.setSearchPlaceholder("请输入信息标题");
		createButton(gridView, NumberUtil.getInt(colId));
		createHead(gridView);
		createBody(gridView, NumberUtil.getInt(colId), gridView.getSearchText());
		gridView.addQueryParam("colId", StringUtil.getString(colId)); 
		gridView.addObject("siteId", siteId);
		gridView.addObject("colId", NumberUtil.getInt(colId));
		return gridView;
	}
	
	/**
	 * 创建按钮
	 * @param gridView   gridView
	 * @param colId      栏目id
	 */
	private void createButton(GridView gridView, int colId) {
		gridView.addButton(Button.getRemove());
		if(UserSessionInfo.getCurrentUser().getIsWebSiteAdmin() && colId > 0){
			gridView.addButton(Button.getClean());
		}
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
		gridView.addHead(Head.getInstance().setField("title").setTitle("信息标题").setAlign("left").setResizable(true).setTip(true)); 
		gridView.addHead(Head.getInstance().setField("colname").setTitle("所属栏目").setAlign("center").setWidth(80));
		gridView.addHead(Head.getInstance().setField("removetime").setTitle("删除时间").setAlign("center").setWidth(100)); 
	}
	
	/**
	 * 创建列表
	 * @param gridView    gridView
	 * @param colId       栏目id
	 * @param searchText  搜索内容
	 */
	private void createBody(GridView gridView, int colId, String searchText) { 
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		int siteId = currentUser.getSiteId();
		gridViewSql.addSelectField("i.iid").addSelectField("i.title")
		           .addSelectField("(SELECT b.name FROM " + Tables.COL + " b  WHERE b.iid = i.colid) colname")
		           .addSelectField("i.removetime").setTable("jmp_info"+siteId + " i");
		String where = "i.siteid=:siteId AND isremove=1"; 
		gridViewSql.addParam("siteId", currentUser.getSiteId());
		//栏目
		if (NumberUtil.getInt(colId) > 0) {
			where += " AND  i.colid = :colId";
			gridViewSql.addParam("colId", colId);
		} 
		//检索
		if (StringUtil.isNotEmpty(searchText)) {
			where += " AND title LIKE :title";
			gridViewSql.addParam("title", "%" + searchText + "%");
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("iid", GridViewSql.SORT_DESC);
		gridViewService.find(gridViewSql);
	}
	
	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) { 
		String iid = StringUtil.getString(rowData.get("iid"));
		String title = StringUtil.getString(rowData.get("title"));
		String colname = StringUtil.getString(rowData.get("colname"));
		gridRow.addCell("iid", iid);
		gridRow.addCell("title", title);
		gridRow.addCell("colname", colname);
		gridRow.addCell("removetime", DateUtil.dateToString((Date) rowData.get("removetime"), DateUtil.YYYY_MM_DD_HH_MM_SS));  
	}

}