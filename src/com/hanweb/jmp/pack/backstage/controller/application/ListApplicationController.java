package com.hanweb.jmp.pack.backstage.controller.application;


import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping(value = "manager/application")
public class ListApplicationController implements GridViewDelegate{
	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;
	
	
	@RequestMapping(value = "list")
	public GridView list(GridView gridView, HttpServletRequest request) {
		gridView.setDelegate(this);
		gridView.setViewName("jmp/pack/backstage/application/application_list");
		createHead(gridView, request);
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		Integer siteId = currentUser.getSiteId(); //网站ID
		
		createBody(gridView,currentUser.getSiteId());
		createButton(gridView);
		
		gridView.setSearchPlaceholder("请输入应用名称");
		gridView.setShowAdvSearch(false);
		return gridView;
	}
	
	/**
	 * 创建表头
	 * 
	 * @param gridView  gridView
	 * @param request  request
	 */
	public void createHead(GridView gridView, HttpServletRequest request) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("id1").setTitle("ID")
				.setAlign("center").setWidth(15));
		gridView.addHead(Head.getInstance().setField("name").setTitle("应用名称")
				.setAlign("left").setResizable(true));
		gridView.addHead(Head.getInstance().setField("isOpen").setTitle("启用状态")
				.setAlign("left").setResizable(true));
		gridView.addHead(Head.getInstance().setField("type").setTitle("同步用户")
				.setAlign("center").setWidth(40));
		gridView.addHead(Head.getInstance().setField("createTime").setTitle("创建时间")
				.setAlign("center").setWidth(80));
	}
	
	/**
	 * 创建列表
	 * @param gridView  gridView
	 * @param name  name
	 * @param colId  colId
	 * @param siteId  siteId
	 * @param colText  colText
	 * @param colState  colState
	 * @param colType  colType
	 */
	private void createBody(GridView gridView, Integer siteId) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		gridViewSql.addSelectField("iid").addSelectField("name")
		.addSelectField("type").addSelectField("createtime").addSelectField("isopen")
		.addSelectField("kind")
		.setTable(Tables.APPLICATIONS);
		String where = "";
		
		if (NumberUtil.getInt(siteId) != 0) {
			where += " siteid =:siteId";
			gridViewSql.addParam("siteId", siteId);
		}
		String name = gridView.getSearchText();
		if (StringUtil.isNotEmpty(name)) {
			where += " AND name like :name";
			gridViewSql.addParam("name", "%" + name + "%");
		}
		
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("orderid", GridViewSql.SORT_ASC);
		gridViewService.find(gridViewSql);
	}

	/**
	 * 创建按钮
	 * @param gridView  gridView
	 * @param colId  colId
	 */
	public void createButton(GridView gridView) {
	
		gridView.addButton(Button.getAdd());
		gridView.addButton(Button.getRemove());
		Button sort = Button.getInstance("&#xf5036;", "order", "排序");
	    gridView.addButton(sort);
		
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData,
			Integer index) { 
		String iid = StringUtil.getString(rowData.get("iid"));
		String name = StringUtil.getString(rowData.get("name"));
		int type = NumberUtil.getInt(rowData.get("type"));
		int isOpen = NumberUtil.getInt(rowData.get("isopen"));
		String kind = StringUtil.getString(rowData.get("kind"));
		String typeName = "";
		if(type==0){
			typeName = "统一用户";
		}else if(type==1){
			typeName = "单点登录";
		}else if(type==2){
			typeName = "不校验";
		}
		if(isOpen==1){
			gridRow.addCell("isOpen", "开启");
		}else{
			gridRow.addCell("isOpen", "停用");
		}
		String createTime = DateUtil.dateToString(
				(Date) rowData.get("createtime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		if(!"m".equals(kind)){
			gridRow.addCell("iid", iid).setDisabled(true);
		}else{
			gridRow.addCell("iid", iid);
		}
		gridRow.addCell("id1", iid);
		gridRow.addCell("name", name, Script.createScript("edit", iid));
		gridRow.addCell("type", typeName);
		gridRow.addCell("createTime", createTime);
	}
}
