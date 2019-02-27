package com.hanweb.jmp.pack.backstage.controller.app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hanweb.common.annotation.Permission;
import com.hanweb.common.service.GridViewService;
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
@Permission
@RequestMapping("manager/app")
public class ListAppController implements GridViewDelegate{
	
	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;
	
	/**
	 * 
	 * list:(这里用一句话描述这个方法的作用).
	 *
	 * @param gridView gridView
	 * @param siteid siteid
	 * @return    设定参数 .
	 */
	@RequestMapping("list")
	public GridView list(GridView gridView, Integer siteid) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		gridView.setDelegate(this);
		gridView.setViewName("jmp/pack/backstage/app/app_list");
		gridView.setShowAdvSearch(false);
		gridView.setSearchPlaceholder("请输入内容");	
		createHead(gridView);
		createButton(gridView);
		createBody(gridView, gridView.getSearchText(), siteid);
		gridView.addObject("siteId", currentUser.getSiteId());
		return gridView;
	}
	
	/**
	 * 创建表头
	 * 
	 * @param gridView gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("name").setTitle("应用名称").setAlign("left")
				.setResizable(true).setTip(true));
		gridView.addHead(Head.getInstance().setField("createtime").setTitle("创建时间").setAlign(
				"center").setWidth(80));
		gridView.addHead(Head.getInstance().setField("clienttype").setTitle("客户端类型")
				.setAlign("center")
				.setWidth(30));
		gridView.addHead(Head.getInstance().setField("version").setTitle("版本号")
				.setAlign("center")
				.setWidth(30));
		gridView.addHead(Head.getInstance().setField("status").setTitle("打包状态")
				.setAlign("center")
				.setWidth(30));
		gridView.addHead(Head.getInstance().setField("isaudit").setTitle("审核状态")
				.setAlign("center")
				.setWidth(30));
		gridView.addHead(Head.getInstance().setField("parameter").setTitle("打包日志")
				.setAlign("center")
				.setWidth(100));
		gridView.addHead(Head.getInstance().setField("operator").setTitle("操作").setAlign("center")
				.setWidth(40));
	}
	
	/**
	 * 
	 * createButton:(这里用一句话描述这个方法的作用).
	 *
	 * @param gridView    设定参数 .
	 */
	private void createButton(GridView gridView) {
		
		Button audit = Button.getInstance("&#xf3004;", "audit", "审核");
	    gridView.addButton(audit);
	    Button unaudit = Button.getInstance("&#xf3003;", "unaudit", "撤审");
	    gridView.addButton(unaudit);
		
	}
	
	/**
	 * 
	 * createBody:(这里用一句话描述这个方法的作用).
	 *
	 * @param gridView gridView
	 * @param searchName searchName
	 * @param siteId    设定参数 .
	 */
	private void createBody(GridView gridView, String searchName, Integer siteId) {	
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		if(NumberUtil.getInt(siteId) == 0){
			CurrentUser currentUser = UserSessionInfo.getCurrentUser();
			siteId = currentUser.getSiteId();
		}
		gridViewSql.addSelectField("a.iid").addSelectField("b.name").addSelectField("a.createtime")
		        .addSelectField("a.isaudit")
				.addSelectField("a.version")
				.addSelectField("a.clienttype").addSelectField("a.status")
				.setTable(Tables.CLIENT +" a, " + Tables.APPLICATION+ " b");
		String where = " a.appId=b.iid";
		if(siteId > 0){
			where += " AND a.siteid=:siteId";
			gridViewSql.addParam("siteId", siteId);
		}
		if (StringUtil.isNotEmpty(searchName)) {
			where += " AND a.name LIKE :name";
			gridViewSql.addParam("name", "%" + searchName + "%");
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("iid", GridViewSql.SORT_DESC);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		// TODO Auto-generated method stub
		Integer iid = NumberUtil.getInt((rowData.get("iid")));
		String name = StringUtil.getString(rowData.get("name"));
		String time = StringUtil.getString(rowData.get("createtime"));
		String version = StringUtil.getString(rowData.get("version"));	
		Integer clienttype = NumberUtil.getInt((rowData.get("clienttype")));
		Integer status = NumberUtil.getInt((rowData.get("status")));
		Integer isaudit = NumberUtil.getInt((rowData.get("isaudit")));
        String appStatus = "";
        if(status == 1){
        	appStatus ="正在打包";
        }else if(status == 2){
        	appStatus="打包成功";
        }else{
        	appStatus="打包失败";
        }
        String clientName = "";
        if(clienttype == 2){
        	clientName = "iPhone";
        }else if(clienttype == 3){
        	clientName = "Android";
        }else{
        	clientName = "iPad";
        }
        String isAudit = "";
        if(isaudit == 1){
        	isAudit="已审核";
        }else if(isaudit == 2){
        	isAudit="<font color=\" red\">已撤审</font>";
        }else{
        	isAudit="未审核";
        }
		gridRow.addCell("iid", iid);
		gridRow.addCell("name", name);
		gridRow.addCell("createtime", time);
		gridRow.addCell("version", version);
		gridRow.addCell("clienttype", clientName);
		gridRow.addCell("status", appStatus);
		gridRow.addCell("isaudit", isAudit, false);
		gridRow.addCell("parameter",
				"<input type='button' class='btn btn-success btn-small' " +
				"onclick='send(\"" + iid + "\")' value='发送参数'></input>&nbsp;" +
				"<input type='button' class='btn btn-success btn-small' " +
				"onclick='back("+iid+")' value='返回参数'></input>&nbsp;"+
				"<input type='button' class='btn btn-success btn-small' " +
				"onclick='callback("+iid+")' value='打包日志'></input>&nbsp;", false);
		gridRow.addCell("operator",
				"<input type='button' class='btn btn-success btn-small' " +
				"onclick='restart(\"" + iid + "\")' value='重新打包'></input>&nbsp;" , false);
		
	}

}
