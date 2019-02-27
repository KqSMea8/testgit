package com.hanweb.jmp.sys.controller.log;

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
@RequestMapping("manager/pushlog")
public class ListPushInfoLogController implements GridViewDelegate{
	
	/**
	 * gridViewService
	 */
	@Autowired
	private GridViewService gridViewService;

	/**
	 * 操作日志列表
	 * @param gridView gridView
	 * @param iid iid 
	 * @param siteName siteName
	 * @param starttime starttime
	 * @param endtime endtime
	 * @return    设定参数 .
	 */
	@RequestMapping("list")
	public GridView list(GridView gridView, Integer iid, String siteName, String starttime, String endtime) {
		gridView.setDelegate(this);
		gridView.setViewName("jmp/sys/log/pushlog_list");
		gridView.addObject("siteName", siteName);
		gridView.addObject("starttime", starttime);
		gridView.addObject("endtime", endtime);
		createButton(gridView);
		createHead(gridView);
		createBody(gridView, iid, starttime, endtime);
		return gridView;
	}

	/**
	 * 创建按钮
	 * @param gridView gridView
	 */
	private void createButton(GridView gridView) {
		CurrentUser currentUser = UserSessionInfo.getCurrentUser();
		if (currentUser.isSysAdmin()) {
			gridView.addButton(Button.getRemove());
			gridView.addButton(Button.getClean());
		}
		Button chart = Button.getInstance("&#xf5043;", "chart", "统计图");
		gridView.addButton(chart);
	}

	/**
	 * 创建表头
	 * @param gridView gridView
	 */
	private void createHead(GridView gridView) {
		gridView.addHead(Head.getInstance().setCheckbox(true).setField("iid"));
		gridView.addHead(Head.getInstance().setField("infoname").setTitle("信息标题").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("username").setTitle("推送人").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("createtime").setTitle("推送时间").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("sitename").setTitle("站点").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("androidstatus").setTitle("Android推送状态").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("iosstatus").setTitle("iPhone推送状态").setAlign("center").setWidth(50));
		gridView.addHead(Head.getInstance().setField("ipadstatus").setTitle("iPad推送状态").setAlign("center").setWidth(50));
	}
 
	/**
	 * 创建列表
	 * @param gridView 创建列表
	 * @param siteId siteId
	 * @param starttime starttime
	 * @param endtime    设定参数 .
	 */
	private void createBody(GridView gridView, Integer siteId, String starttime, String endtime) {
		GridViewSql gridViewSql = GridViewSql.getInstance(gridView);
		if(NumberUtil.getInt(siteId) == 0){
			siteId = UserSessionInfo.getCurrentUser().getSiteId();
		}
		gridViewSql.addSelectField("a.iid").addSelectField("a.androidstatus").addSelectField("a.iosstatus")
		           .addSelectField("a.ipadstatus").addSelectField("b.title infoname").addSelectField("c.name username")
		           .addSelectField("a.createtime").addSelectField("d.name sitename").setTable(Tables.PUSH_INFO_LOG + " a, " 
		           + Tables.INFO+ siteId + " b,"
			       + com.hanweb.complat.constant.Tables.USER + " c," + Tables.SITE + " d");
		String where = "a.infoid=b.iid 	AND a.userid=c.iid AND a.siteid = d.iid";
		if (UserSessionInfo.getCurrentUser().getIsWebSiteAdmin()) {
			where += " AND a.siteid LIKE :siteid";
			gridViewSql.addParam("siteid", UserSessionInfo.getCurrentUser().getSiteId());
		}
		if (StringUtil.isNotEmpty(gridView.getSearchText())) {
			where += " AND b.title LIKE :content";
			gridViewSql.addParam("content", "%" + gridView.getSearchText() + "%");
		}
		if (StringUtil.isNotEmpty(starttime) && StringUtil.isNotEmpty(endtime)) {
			where += " AND a.createtime BETWEEN :starttime AND :endtime";
			gridViewSql.addParam("starttime", starttime);
			gridViewSql.addParam("endtime", endtime);
		} else if (StringUtil.isNotEmpty(starttime)) {
			where += " AND a.createtime >= :starttime";
			gridViewSql.addParam("starttime", starttime);
		} else if (StringUtil.isNotEmpty(endtime)) {
			where += " AND a.createtime <= :endtime";
			gridViewSql.addParam("endtime", endtime);
		}
		gridViewSql.setWhere(where);
		gridViewSql.addOrderBy("a.iid", GridViewSql.SORT_DESC);
		gridViewService.find(gridViewSql);
	}

	@Override
	public void createRow(GridRow gridRow, Map<String, Object> rowData, Integer index) {
		Integer iid = NumberUtil.getInt((rowData.get("iid")));
		String title = StringUtil.getString(rowData.get("infoname"));
		String username = StringUtil.getString(rowData.get("username"));
		String sitename = StringUtil.getString(rowData.get("sitename"));
		String createtime = DateUtil.dateToString((Date) rowData.get("createtime"), DateUtil.YYYY_MM_DD_HH_MM_SS);
		int androidstatus = NumberUtil.getInt((rowData.get("androidstatus")));
		int iosstatus = NumberUtil.getInt((rowData.get("iosstatus")));
		int ipadstatus = NumberUtil.getInt((rowData.get("ipadstatus")));
		String android = "";
		String iphone = "";
		String ipad = "";
		if(androidstatus == 1){
			android = "正在推送";
		}else if(androidstatus ==2){
			android = "推送成功";
		}else if(iosstatus == 3){
			android = "推送失败";
		}
		if(iosstatus == 1){
			iphone = "正在推送";
		}else if(iosstatus ==2){
			iphone = "推送成功";
		}else if(iosstatus == 3){
			iphone = "推送失败";
		}
		if(ipadstatus == 1){
			ipad = "正在推送";
		}else if(ipadstatus ==2){
			ipad = "推送成功";
		}else if(ipadstatus == 3){
			ipad = "推送失败";
		}
		gridRow.addCell("iid", iid);
		gridRow.addCell("infoname", title);
		gridRow.addCell("username", username);
		gridRow.addCell("sitename", sitename);
		gridRow.addCell("createtime", createtime);
		gridRow.addCell("androidstatus", android);
		gridRow.addCell("iosstatus", iphone);
		gridRow.addCell("ipadstatus", ipad);		
	}

}