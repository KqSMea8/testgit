package com.hanweb.jmp.cms.controller.channels;

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

@Controller 
//@Permission(module = "/channel", allowedAdmin = Allowed.YES)
@RequestMapping("manager/channel")
public class ListChannelController implements GridViewDelegate{

	/**
	 * gridViewService
	 */
	@Autowired
	GridViewService gridViewService;
	
	/**
	 * 列表
	 * @param gridView   gridView
	 * @return GridView
	 */
	@RequestMapping("list")
	public GridView list(GridView gridView){
		gridView.setDelegate(this);
		gridView.setViewName("jmp/cms/channels/channel_list");
		createButton(gridView);
		createHead(gridView);
		createBody(gridView);
		gridView.setPosition("功能管理");
		gridView.setSearchPlaceholder("请输入导航名称");
		gridView.setShowAdvSearch(false);
		return gridView;
	}
	
	/**
	 * 创建按钮
	 * @param gridView 
	 */
	private void createButton(GridView gridView) {
		gridView.addButton(Button.getAdd());
		gridView.addButton(Button.getRemove());
		Button sort = Button.getInstance("&#xf5036;", "order", "排序");
	    gridView.addButton(sort);
	}

	/**
	 * 创建表头
	 * @param gridView 
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("id1").setTitle("ID").setAlign("center").setWidth(15));
		gridView.addHead(Head.getInstance().setField("name").setTitle("导航名称 ").setAlign("left").setWidth(100).setResizable(true));
		gridView.addHead(Head.getInstance().setField("namejs").setHidden(true));
		gridView.addHead(Head.getInstance().setField("type").setTitle("类型").setAlign("center").setWidth(100));
		gridView.addHead(Head.getInstance().setField("colNum").setTitle("栏目数").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("createTime").setTitle("创建时间 ").setAlign("center").setWidth(100));
		gridView.addHead(Head.getInstance().setField("operate").setTitle("操作 ").setAlign("center").setWidth(100));
	}

	/**
	 * 创建列表
	 * @param gridView 
	 */
	private void createBody(GridView gridView) {
		Integer siteId = UserSessionInfo.getCurrentUser().getSiteId();
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql
				.addSelectField("a.iid")
				.addSelectField("a.name")
				.addSelectField("a.type")
				.addSelectField("a.state")
				.addSelectField("(SELECT COUNT(1) FROM " + Tables.CHANNEL_COLUMN + " b WHERE b.channelid = a.iid) colnum")
				.addSelectField("createtime")
				.setTable(Tables.CHANNEL + " a"); 
		String where =" siteid=:siteid ";
		gridViewSql.addParam("siteid", siteId);
		String name = gridView.getSearchText();
		if (StringUtil.isNotEmpty(name)) {
			where += " AND name like :name ";
			gridViewSql.addParam("name", "%" + name + "%");
		} 
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("orderid", GridViewSql.SORT_ASC)
		.addOrderBy("iid", GridViewSql.SORT_ASC);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		Integer iid = NumberUtil.getInt(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("name"));
		String type = StringUtil.getString(rowData.get("type"));
		int state = NumberUtil.getInt(rowData.get("state"));
		Integer colNum = NumberUtil.getInt(rowData.get("colnum"));
		String createTime = DateUtil.dateToString((Date) rowData.get("createtime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		String typeValue = "";
		String sortValue = "<input type='button' class='btn btn-success btn-small' " +
				"onclick='sort(\"" + iid + "\")' value='排序'></input>";
		if("1".equals(type)){
			typeValue = "单一类型";
			sortValue  ="";
		}else if("2".equals(type)){
			typeValue = "信息门户";
		}else if("3".equals(type)){
			typeValue = "用户中心";
		}else{
			typeValue = "综合门户";
		}
		gridRow.addCell("iid", iid);
		gridRow.addCell("id1", iid);
		gridRow.addCell("name", name);
		gridRow.addCell("namejs", name);
		String operateStr = "" ;
		operateStr +="<input type='button' class='btn btn-success btn-small' " +
		"onclick='edit(\"" + iid + "\",\"" + name+ "\")' value='编辑'></input>&nbsp;";
		if(state == 1){
			operateStr += "<input type='button' class='btn btn-success btn-small' style='color:white;' " +
			"onclick='modifyEnable(\"" + iid + "\",\"" + state+ "\")' value='已启用'></input>&nbsp;";
		}else{
			operateStr += "<input type='button' class='btn btn-success btn-small' style='color:gray;' " +
			"onclick='modifyEnable(\"" + iid + "\",\"" + state+ "\")' value='未启用'></input>&nbsp;";
		}
		if("2".equals(type)){
			operateStr += "<input type='button' class='btn btn-success btn-small' " +
			"onclick='sort(\"" + iid + "\")' value='排序'></input>";
		}
		gridRow.addCell("type", typeValue);
		gridRow.addCell("colNum", colNum);
		gridRow.addCell("createTime", createTime);
		gridRow.addCell("operate",	operateStr, false);
	}

}