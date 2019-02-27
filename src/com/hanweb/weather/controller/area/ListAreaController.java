package com.hanweb.weather.controller.area;

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
import com.hanweb.common.view.grid.Button;
import com.hanweb.common.view.grid.GridRow;
import com.hanweb.common.view.grid.GridView;
import com.hanweb.common.view.grid.GridViewDelegate;
import com.hanweb.common.view.grid.GridViewSql;
import com.hanweb.common.view.grid.Head;
import com.hanweb.weather.constant.Tables;
import com.hanweb.weather.service.AreaService;



/**
 * 
 * @author
 * 
 */
@Controller
@Permission(module = "area", allowedGroup = Allowed.YES)
@RequestMapping("manager/area")
public class ListAreaController implements GridViewDelegate {

	@Autowired
	private GridViewService gridViewService;
	
	@Autowired
	private AreaService areaService;

	@RequestMapping("list")
	public GridView list(GridView gridView, String procode, String provcn, 
			String areaname, String areastate) {
		gridView.setDelegate(this);
		gridView.setViewName("jmp/area/area_list");
		createButton(gridView);
		createHead(gridView);
		
		String searchName = gridView.getSearchText();
		if (StringUtil.isEmpty(searchName)) {
			searchName = areaname;
		}
		
		createBody(gridView, procode, searchName, areastate);
		gridView.setShowAdvSearch(true);
		gridView.setSearchPlaceholder("请输入地区名称");
		if (StringUtil.isEmpty(provcn)) {
			gridView.addQueryParam("provcn", "地区管理");
		} else {
			gridView.addQueryParam("provcn", provcn);
		}
		gridView.addQueryParam("procode", procode);
		gridView.addObject("areaname", areaname);
		gridView.addObject("areastate", areastate);
		return gridView;
	}

	/**
	 * 创建按钮
	 * 
	 * @param gridView
	 */
	private void createButton(GridView gridView) {
		Button audit = Button.getInstance("icon-checkbox", "start", "启用");
	    gridView.addButton(audit);
	    Button unaudit = Button.getInstance("icon-checkbox2", "stop", "停用");
	    gridView.addButton(unaudit);
	    if(!areaService.isInitData()){
	    	Button init = Button.getInstance("icon-download", "init", "初始化数据");
		    gridView.addButton(init);
	    }
	}

	/**
	 * 创建表头
	 * 
	 * @param gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("name").setTitle("地区名称").setAlign("left")
				.setResizable(true));
		gridView.addHead(Head.getInstance().setField("state").setTitle("状态").setAlign("center")
				.setWidth(50));
		gridView.addHead(Head.getInstance().setField("operation").setTitle("操作")
				.setAlign("center").setWidth(60));
	}

	/**
	 * 创建列表
	 * 
	 * @param gridView
	 */
	private void createBody(GridView gridView, String procode, String areaname, String areastate) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("namecn")
				.addSelectField("state").setTable(Tables.AREA);
		String where = "";
		if(StringUtil.isNotEmpty(procode)){
			where = " parcode= :procode";
			gridViewSql.addParam("procode", StringUtil.getString(procode));
		} 
		if (StringUtil.isNotEmpty(areaname)) {
			if(StringUtil.isNotEmpty(where)){
				where += " AND namecn LIKE :name";
			}else{
				where += " namecn LIKE :name";
			} 
			gridViewSql.addParam("name", areaname, LikeType.LR);
		}
		
		if(StringUtil.isNotEmpty(areastate) && NumberUtil.getInt(areastate) >=0){
			if(StringUtil.isNotEmpty(where)){
				where += " AND state = :areastate";
			}else{
				where += " state = :areastate";
			} 
			gridViewSql.addParam("areastate", areastate);
		}
		
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("iid", GridViewSql.SORT_ASC);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		String iid = StringUtil.getString(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("namecn"));
		int state = NumberUtil.getInt(rowData.get("state"));
		String stateName = "";
		switch (state) {
		case 0:
			stateName = "<font color='red'>未启用</font>";
			break; 
		case 1:
			stateName = "<font color='green'>已启用</font>";
			break;
		}
		gridRow.addCell("iid", iid);
		gridRow.addCell("name", name);
		gridRow.addCell("state", stateName, false);
		if(state==1){
			gridRow.addCell("operation",
					"<input type='button' class='btn btn-success btn-small' " +
					"onclick='areaLook(\"" + iid + "\")' value='接口数据'></input>&nbsp;" +
					"<input type='button' class='btn btn-success btn-small' " +
					"onclick='baiduLook(\"" + iid + "\")' value='百度数据'></input>&nbsp;" +
					"<input type='button' class='btn btn-success btn-small' " +
					"onclick='syninfoWeather(\"" + iid + "\", \"" + name + "\")' value='更新'></input>" 
					, false);
		}  
	}
}