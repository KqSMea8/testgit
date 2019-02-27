package com.hanweb.jmp.sys.controller.collect;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hanweb.common.basedao.LikeType;
import com.hanweb.common.service.GridViewService;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.view.grid.GridRow;
import com.hanweb.common.view.grid.GridView;
import com.hanweb.common.view.grid.GridViewDelegate;
import com.hanweb.common.view.grid.GridViewSql;
import com.hanweb.common.view.grid.Head;
import com.hanweb.jmp.constant.Tables;

@Controller
@RequestMapping("manager/collect")
public class ListCollectController implements GridViewDelegate{
	
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
	public GridView list(GridView gridView, String username, String infoname) {
		gridView.setDelegate(this);
		gridView.setViewName("jmp/sys/collect/collect_list");
		createButton(gridView);
		createHead(gridView);
		createBody(gridView, username, infoname);
		gridView.setSearchPlaceholder("请输入用户名");
		gridView.setShowAdvSearch(true);
		gridView.addObject("username", username);
		gridView.addObject("infoname", infoname);
		return gridView;
	}
	
	private void createButton(GridView gridView) {
	}
	
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("username").setTitle("用户名").setAlign("left").setWidth(100));
		gridView.addHead(Head.getInstance().setField("type").setTitle("信息类型").setAlign("center").setWidth(200));
		gridView.addHead(Head.getInstance().setField("infoname").setTitle("信息名称").setAlign("center").setWidth(100));
		gridView.addHead(Head.getInstance().setField("createtime").setTitle("收藏时间").setAlign("center").setWidth(100));
	}

	private void createBody(GridView gridView, String username, String infoname) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid")
				   .addSelectField("type")
				   .addSelectField("username")
				   .addSelectField("createtime")
				   .addSelectField("infoname")
				   .setTable(Tables.COLLECT); 
		String where = "1=1"; 
		//检索
		String userName = gridView.getSearchText(); 
		if(StringUtil.isNotEmpty(userName)){
			where += " AND username LIKE :username";
			gridViewSql.addParam("username", userName, LikeType.LR);
		}
		if(StringUtil.isNotEmpty(username)){
			where += " AND username LIKE :username";
			gridViewSql.addParam("username", username, LikeType.LR);
		}
		if(StringUtil.isNotEmpty(infoname)){
			where += " AND infoname LIKE :infoname";
			gridViewSql.addParam("infoname", infoname, LikeType.LR);
		}
		
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("createtime", GridViewSql.SORT_DESC);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		Integer iid = NumberUtil.getInt(rowData.get("iid"));
		String username = StringUtil.getString(rowData.get("username"));
		Integer type = NumberUtil.getInt(rowData.get("type"));
		String infoname = StringUtil.getString(rowData.get("infoname"));
		String createtime = StringUtil.getString(rowData.get("createtime"));
		
		String typeName = "";
		if(type == 1){
			typeName = "应用";
		}else{
			typeName = "信息";
		}
		
		gridRow.addCell("iid", iid);
		gridRow.addCell("username", username);
		gridRow.addCell("type", typeName);
		gridRow.addCell("createtime", createtime);
		gridRow.addCell("infoname", infoname);
	}

}