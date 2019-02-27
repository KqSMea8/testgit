package com.hanweb.jmp.sys.controller.count;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping; 

import com.hanweb.common.service.GridViewService;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.view.grid.GridRow;
import com.hanweb.common.view.grid.GridView;
import com.hanweb.common.view.grid.GridViewDelegate;
import com.hanweb.common.view.grid.GridViewSql;
import com.hanweb.common.view.grid.Head;

import com.hanweb.jmp.constant.Tables;
import com.hanweb.jmp.sys.entity.sites.Site;
import com.hanweb.jmp.sys.service.sites.SiteService;

@Controller
@RequestMapping("manager/count")
public class ListCountController implements GridViewDelegate {
	
	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;

	/**
	 * siteService
	 */
	@Autowired
	private SiteService siteService;
	/**
	 * 统计列表
	 * 
	 * @param request   request
	 * @param gridView  gridView
	 * @return  GridView
	 */
	@RequestMapping("list")
	public GridView list(HttpServletRequest request, GridView gridView) {
		gridView.setDelegate(this);
		gridView.setShowAdvSearch(false);
		gridView.setViewName("jmp/sys/count/count_list");
		gridView.setSearchPlaceholder("请输入网站名称");
		createHead(gridView);
		createBody(gridView, gridView.getSearchText());
		return gridView;
	}

	/**
	 * 创建表头
	 * 
	 * @param gridView  gridView
	 */
	public void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid").setHidden(true));
		gridView.addHead(Head.getInstance().setField("siteName").setTitle("网站名称").setAlign("left"));
		gridView.addHead(Head.getInstance().setField("colTypeNum").setTitle("栏目分类数").setAlign("center").setWidth(70));
		gridView.addHead(Head.getInstance().setField("infoNum").setTitle("信息数").setAlign("center").setWidth(70));
	}

	/**
	 * 创建列表
	 * @param gridView  gridView
	 * @param searchName  searchName
	 */
	public void createBody(GridView gridView, String searchName) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("a.iid").addSelectField("a.name").addSelectField(
				"(SELECT COUNT(b.iid) FROM " 
				+ Tables.COL + " b WHERE b.siteid = a.iid) colTypeNum");
		List<Site> siteList = siteService.findAll();
		for(Site siteEn:siteList){
			gridViewSql.addSelectField("(SELECT COUNT(c.iid) FROM " 
					   + Tables.INFO +siteEn.getIid()+ " c) infoNum"+siteEn.getIid());
		}
		gridViewSql.setTable(Tables.SITE + " a");
		String where = "";
		if (StringUtil.isNotEmpty(searchName)) {
			where = "name like :name";
			gridViewSql.addParam("name", "%" + searchName + "%");
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("a.iid", GridViewSql.SORT_DESC);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer arg2) {
		Integer iid = NumberUtil.getInt(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("name"));
		Integer colTypeNum = NumberUtil.getInt(rowData.get("colTypeNum"));
		Integer infoNum = NumberUtil.getInt(rowData.get("infoNum"+iid));
		gridRow.addCell("iid", iid);
		gridRow.addCell("siteName", name);
		gridRow.addCell("colTypeNum", colTypeNum);
		gridRow.addCell("infoNum", infoNum);
	}
	
}